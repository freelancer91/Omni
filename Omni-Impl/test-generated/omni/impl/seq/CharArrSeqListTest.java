package omni.impl.seq;
import java.util.ArrayList;
import omni.impl.seq.CharArrSeq.UncheckedList;
import omni.impl.seq.CharArrSeq.CheckedList;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqListTest
{
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(seq.size()-1),seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveCharAt_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListgetChar_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTochar(i);
      Assertions.assertEquals(expectedVal,seq.getChar(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(seq.size()-1),seq.remove(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.remove(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremove_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToCharacter(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToCharacter(i);
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTodouble(i);
      Assertions.assertEquals(expectedVal,seq.getDouble(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveFloatAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTofloat(i);
      Assertions.assertEquals(expectedVal,seq.getFloat(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(seq.size()-1),seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveLongAt_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListgetLong_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTolong(i);
      Assertions.assertEquals(expectedVal,seq.getLong(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtEnd()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToint(seq.size()-1),seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtBegin()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListremoveIntAt_seqIsNotEmpty_removeAtMidPoint()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
    }
  }
  @Test
  public void testUncheckedListgetInt_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToint(i);
      Assertions.assertEquals(expectedVal,seq.getInt(i));
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intchar_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(char)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
  }
  @Test
  public void testUncheckedListset_intCharacter_seqIsNotEmpty_NonThrowingIndex()
  {
    UncheckedList seq=new UncheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToCharacter(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(Character)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
    }
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTochar(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTochar(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      var expectedVal=TypeConversionUtil.convertTocharboolean(100-i-1);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[seq.size-i-1]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      arrayList.add(arrayList.size()/2,TypeConversionUtil.convertTocharboolean(i));
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
      Assertions.assertEquals(TypeConversionUtil.convertTocharboolean(i),seq.arr[i]);
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
  public void testCheckedListremoveCharAt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeCharAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetChar_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getChar(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(seq.size()-1),seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveCharAt_seqIsNotEmpty_removeAtMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeCharAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListgetChar_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTochar(i);
      Assertions.assertEquals(expectedVal,seq.getChar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(seq.size()-1),seq.remove(removeIndex));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.remove(removeIndex));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToCharacter(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToCharacter(i);
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
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
  public void testCheckedListremoveLongAt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeLongAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetLong_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getLong(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(seq.size()-1),seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertTolong(i),seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveLongAt_seqIsNotEmpty_removeAtMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertTolong(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeLongAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListgetLong_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertTolong(i);
      Assertions.assertEquals(expectedVal,seq.getLong(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.removeIntAt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(0));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListgetInt_seqIsEmpty_NegativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.getInt(-1));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtEnd()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()-1;
      Assertions.assertEquals(TypeConversionUtil.convertToint(seq.size()-1),seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtBegin()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=0;
      Assertions.assertEquals(TypeConversionUtil.convertToint(i),seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListremoveIntAt_seqIsNotEmpty_removeAtMidPoint()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    ArrayList arrayList=new ArrayList();
    for(int i=0;i<100;++i)
    {
      arrayList.add(TypeConversionUtil.convertToint(i));
    }
    for(int i=0;i<100;++i)
    {
      int removeIndex=seq.size()/2;
      Assertions.assertEquals(arrayList.remove(removeIndex),(Object)seq.removeIntAt(removeIndex));
      Assertions.assertEquals(100-i-1,seq.size());
      Assertions.assertEquals(100+i+1,seq.modCount);
    }
  }
  @Test
  public void testCheckedListgetInt_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var expectedVal=TypeConversionUtil.convertToint(i);
      Assertions.assertEquals(expectedVal,seq.getInt(i));
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intchar_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertTochar(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertTochar(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(char)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intchar_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intchar_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intchar_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertTochar(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertTochar(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsNotEmpty_NonThrowingIndex()
  {
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    for(int i=0;i<100;++i)
    {
      var inputVal=TypeConversionUtil.convertToCharacter(100-i-1);
      Assertions.assertEquals(TypeConversionUtil.convertToCharacter(i),seq.set(i,inputVal));
      Assertions.assertEquals(inputVal,(Character)seq.arr[i]);
      Assertions.assertEquals(100,seq.size());
      Assertions.assertEquals(100,seq.modCount);
    }
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(-1,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsEmpty_hiIndex()
  {
    CheckedList seq=new CheckedList();
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(0,TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(0,seq.size());
    Assertions.assertEquals(0,seq.modCount);
  }
  @Test
  public void testCheckedListset_intCharacter_seqIsNotEmpty_negativeIndex()
  {
    CheckedList seq=new CheckedList();
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
    CheckedList seq=new CheckedList();
    for(int i=0;i<100;++i)
    {
      seq.add(seq.size,TypeConversionUtil.convertToCharacter(i));
    }
    Assertions.assertThrows(IndexOutOfBoundsException.class,()->seq.set(seq.size(),TypeConversionUtil.convertToCharacter(5)));
    Assertions.assertEquals(100,seq.size());
    Assertions.assertEquals(100,seq.modCount);
  }
}
