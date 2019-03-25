package omni.impl.seq;
import java.util.ArrayList;
import omni.impl.seq.FloatArrSeq.UncheckedList;
import omni.impl.seq.FloatArrSeq.CheckedList;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FloatArrSeqListTest
{
  @Test
  public void testUncheckedListput_intfloat_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intfloat_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intfloat_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intfloat_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intfloat_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intfloat_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intFloat_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intFloat_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intFloat_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intFloat_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intFloat_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intFloat_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intboolean_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intBoolean_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intbyte_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intbyte_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intbyte_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intByte_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intByte_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intByte_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intByte_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intByte_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intchar_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intchar_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intchar_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intchar_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intchar_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intCharacter_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intCharacter_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intCharacter_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intshort_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intshort_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intshort_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intshort_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intshort_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intShort_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intShort_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intShort_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intShort_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intShort_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intint_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intint_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intint_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intint_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intint_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intint_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intint_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intint_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intInteger_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intInteger_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intInteger_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intInteger_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intInteger_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intInteger_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intlong_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intlong_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intlong_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intlong_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intlong_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intlong_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intlong_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intlong_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intLong_initialCapacityDEFAULT_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityDEFAULT_InsertBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityDEFAULT_InsertEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intLong_initialCapacityNULL_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityNULL_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityNULL_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacityNULL_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intLong_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intLong_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testUncheckedListput_intLong_initialCapacity50_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacity50_InsertBegin()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacity50_InsertEnd()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testUncheckedListadd_intLong_initialCapacity50_InsertMidPoint()
  {
    UncheckedList seq=new UncheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testUncheckedListadd_intLong_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testUncheckedListadd_intLong_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(seq.size()-1),seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListgetFloat_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTofloat(i);
      Assertions.assertEquals(expectedVal,seq.getFloat(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(seq.size()-1),seq.remove(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(i),seq.remove(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.remove(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListget_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToFloat(i);
      Assertions.assertEquals(expectedVal,seq.get(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(seq.size()-1),seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveDoubleAt_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTodouble(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListgetDouble_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTodouble(i);
      Assertions.assertEquals(expectedVal,seq.getDouble(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intfloat_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(float)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intFloat_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToFloat(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(Float)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intfloat_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intfloat_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTofloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTofloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intfloat_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intfloat_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intfloat_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intfloat_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intFloat_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intFloat_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToFloat(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToFloat(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intFloat_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intFloat_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intFloat_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intFloat_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intboolean_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intboolean_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToboolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToboolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intboolean_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intboolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToboolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToboolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intBoolean_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intBoolean_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToBoolean(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloatboolean(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToBoolean(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloatboolean(i));
    }
    System.out.println("BEGIN testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intBoolean_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intBoolean_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToBoolean(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToBoolean(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intbyte_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intbyte_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTobyte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTobyte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intbyte_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intbyte_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intbyte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTobyte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTobyte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intByte_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intByte_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intByte_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToByte(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToByte(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intByte_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intByte_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intByte_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToByte(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToByte(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intchar_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intchar_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intchar_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTochar(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTochar(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intchar_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intchar_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intchar_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intCharacter_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intCharacter_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToCharacter(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToCharacter(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intCharacter_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intCharacter_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intCharacter_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intshort_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intshort_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intshort_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToshort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToshort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intshort_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intshort_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intshort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToshort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToshort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intShort_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intShort_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intShort_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToShort(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToShort(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intShort_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intShort_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intShort_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToShort(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToShort(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intint_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intint_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intint_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToint(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToint(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intint_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intint_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intint_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intint_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToint(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToint(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intInteger_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intInteger_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
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
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToInteger(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToInteger(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intInteger_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intInteger_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intInteger_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intInteger_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
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
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToInteger(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToInteger(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intlong_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intlong_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intlong_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intlong_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertTolong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertTolong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intlong_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intlong_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intlong_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intlong_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intlong_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intlong_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTolong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertTolong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityDEFAULT_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_InsertBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_InsertEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intLong_initialCapacityDEFAULT_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityDEFAULT_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityNULL_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_InsertBegin()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_InsertEnd()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intLong_initialCapacityNULL_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intLong_initialCapacityNULL_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intLong_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacityNULL_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(0,null);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacity50_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    for(int i=0;i<100;++i)
    {
      var putVal=TypeConversionUtil.convertToLong(100-i-1);
      seq.put(i,putVal);
      var expectedVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(expectedVal,seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_InsertBegin()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(0,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[seq.size-i-1]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_InsertEnd()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<seqSize;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_InsertMidPoint()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size/2,TypeConversionUtil.convertToLong(i));
    }
    int seqSize=seq.size();
    Assertions.assertEquals(100,seqSize);
    Assertions.assertNotNull(seq.arr);
    Assertions.assertEquals(100,seq.modCount);
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<seqSize;++i)
    {
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTofloat(i));
    }
    System.out.println("BEGIN testCheckedListadd_intLong_initialCapacity50_InsertMidPoint");
    for(int i=0;i<seqSize;++i)
    {
      System.out.println(arrayList.get(i));
      Assertions.assertEquals(arrayList.get(i),(Object)seq.arr[i]);
    }
    System.out.println("END testCheckedListadd_intLong_initialCapacity50_InsertMidPoint");
  }
  @Test
  public void testCheckedListput_intLong_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(0,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListput_intLong_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(seq.size(),TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListput_intLong_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.put(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.arr[i]);
    }
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(-1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListadd_intLong_initialCapacity50_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList(50);
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToLong(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.add(seq.size+1,TypeConversionUtil.convertToLong(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeFloatAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getFloat(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(seq.size()-1),seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveFloatAt_seqIsNotEmpty_removeAtMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeFloatAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListgetFloat_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTofloat(i);
      Assertions.assertEquals(expectedVal,seq.getFloat(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(seq.size()-1),seq.remove(removeIndex));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(i),seq.remove(removeIndex));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.remove(removeIndex));
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
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToFloat(i);
      Assertions.assertEquals(expectedVal,seq.get(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeDoubleAt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(seq.size()));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getDouble(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(seq.size()-1),seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTodouble(i),seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveDoubleAt_seqIsNotEmpty_removeAtMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTodouble(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeDoubleAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListgetDouble_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTodouble(i);
      Assertions.assertEquals(expectedVal,seq.getDouble(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intfloat_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTofloat(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTofloat(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(float)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intfloat_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intfloat_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intfloat_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intfloat_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTofloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertTofloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intFloat_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToFloat(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToFloat(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(Float)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intFloat_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intFloat_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intFloat_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intFloat_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToFloat(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToFloat(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
}
