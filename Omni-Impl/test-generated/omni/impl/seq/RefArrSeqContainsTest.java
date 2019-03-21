package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import java.util.ConcurrentModificationException;
import omni.util.TypeUtil;
import omni.api.QueryTestUtil;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqContainsTest
{
  @Test
  public void testEmptyUncheckedListContains()
  {
    var seq=new UncheckedList();
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testUncheckedListContainsNull()
  {
    var seq=new UncheckedList();
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testUncheckedListContainsBoolean()
  {
    var seq=new UncheckedList();
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testUncheckedListContainsByte()
  {
    var seq=new UncheckedList();
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testUncheckedListContainsChar()
  {
    var seq=new UncheckedList();
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testUncheckedListContainsShort()
  {
    var seq=new UncheckedList();
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testUncheckedListContainsInt()
  {
    var seq=new UncheckedList();
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testUncheckedListContainsLong()
  {
    var seq=new UncheckedList();
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
  @Test
  public void testEmptyUncheckedStackContains()
  {
    var seq=new UncheckedStack();
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testUncheckedStackContainsNull()
  {
    var seq=new UncheckedStack();
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testUncheckedStackContainsBoolean()
  {
    var seq=new UncheckedStack();
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testUncheckedStackContainsByte()
  {
    var seq=new UncheckedStack();
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testUncheckedStackContainsChar()
  {
    var seq=new UncheckedStack();
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testUncheckedStackContainsShort()
  {
    var seq=new UncheckedStack();
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testUncheckedStackContainsInt()
  {
    var seq=new UncheckedStack();
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testUncheckedStackContainsLong()
  {
    var seq=new UncheckedStack();
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
  @Test
  public void testEmptyCheckedListContains()
  {
    var seq=new CheckedList();
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testCheckedListContainsNull()
  {
    var seq=new CheckedList();
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testCheckedListContainsBoolean()
  {
    var seq=new CheckedList();
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testCheckedListContainsByte()
  {
    var seq=new CheckedList();
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testCheckedListContainsChar()
  {
    var seq=new CheckedList();
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testCheckedListContainsShort()
  {
    var seq=new CheckedList();
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testCheckedListContainsInt()
  {
    var seq=new CheckedList();
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testCheckedListContainsLong()
  {
    var seq=new CheckedList();
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
  @Test
  public void testEmptyCheckedStackContains()
  {
    var seq=new CheckedStack();
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testCheckedStackContainsNull()
  {
    var seq=new CheckedStack();
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testCheckedStackContainsBoolean()
  {
    var seq=new CheckedStack();
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testCheckedStackContainsByte()
  {
    var seq=new CheckedStack();
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testCheckedStackContainsChar()
  {
    var seq=new CheckedStack();
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testCheckedStackContainsShort()
  {
    var seq=new CheckedStack();
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testCheckedStackContainsInt()
  {
    var seq=new CheckedStack();
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testCheckedStackContainsLong()
  {
    var seq=new CheckedStack();
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
  @Test
  public void testEmptyCheckedSubListContains()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testCheckedSubListContainsNull()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testCheckedSubListContainsBoolean()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testCheckedSubListContainsByte()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testCheckedSubListContainsChar()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testCheckedSubListContainsShort()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testCheckedSubListContainsInt()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testCheckedSubListContainsLong()
  {
    var root=new CheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
  @Test
  public void testEmptyUncheckedSubListContains()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    QueryTestUtil.testEmptycontains(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsNull()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(TypeConversionUtil.convertToInteger(0));
    QueryTestUtil.testcontainsNullReturnNegative(()->seq);
    seq.add((Integer)null);
    QueryTestUtil.testcontainsNullReturnPositive(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsBoolean()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Boolean.TRUE);
    QueryTestUtil.testcontainsBoolean(()->seq,true);
    seq.clear();
    seq.add(Boolean.FALSE);
    QueryTestUtil.testcontainsBoolean(()->seq,false);
  }
  @Test
  public void testUncheckedSubListContainsByte()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Byte.valueOf((byte)0));
    QueryTestUtil.testcontainsByte(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsChar()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Character.valueOf((char)0));
    QueryTestUtil.testcontainsChar(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsShort()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Short.valueOf((short)0));
    QueryTestUtil.testcontainsShort(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsInt()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Integer.valueOf((int)0));
    QueryTestUtil.testcontainsInt(()->seq);
  }
  @Test
  public void testUncheckedSubListContainsLong()
  {
    var root=new UncheckedList();
    var subList=root.subList(0,0);
    var seq=subList.subList(0,0);
    seq.add(Long.valueOf((long)0));
    QueryTestUtil.testcontainsLong(()->seq);
  }
}
