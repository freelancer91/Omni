package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class LongDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    long val1=TypeConversionUtil.convertTolong(100);
    long val2=TypeConversionUtil.convertTolong(101);
    long val3=TypeConversionUtil.convertTolong(102);
    long val4=TypeConversionUtil.convertTolong(103);
    long val5=TypeConversionUtil.convertTolong(104);
    var node1=new LongDblLnkNode(val1);
    var node2=new LongDblLnkNode(val2,node1);
    var node3=new LongDblLnkNode(node1,val3);
    var node4=new LongDblLnkNode(val4);
    var node5=new LongDblLnkNode(node3,val5,node4);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node3.val==val3);
    Assertions.assertTrue(node4.val==val4);
    Assertions.assertTrue(node5.val==val5);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node1.prev==null);
    Assertions.assertTrue(node2.next==node1);
    Assertions.assertTrue(node2.prev==null);
    Assertions.assertTrue(node3.next==null);
    Assertions.assertTrue(node3.prev==node1);
    Assertions.assertTrue(node4.next==null);
    Assertions.assertTrue(node4.prev==null);
    Assertions.assertTrue(node5.next==node4);
    Assertions.assertTrue(node5.prev==node3);
  }
/*
  @Test
  public void testCompareTo()
  {
    long lessVal=0;
    long lessEqualsVal=0;
    long greaterVal=1;
    long greaterEqualsVal=1;
    var lessNode=new LongDblLnkNode(lessVal);
    var lessEqualsNode=new LongDblLnkNode(lessEqualsVal);
    var greaterNode=new LongDblLnkNode(greaterVal);
    var greaterEqualsNode=new LongDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
  */
}
