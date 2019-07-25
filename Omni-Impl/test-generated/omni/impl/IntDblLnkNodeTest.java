package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class IntDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    int val1=TypeConversionUtil.convertToint(100);
    int val2=TypeConversionUtil.convertToint(101);
    int val3=TypeConversionUtil.convertToint(102);
    int val4=TypeConversionUtil.convertToint(103);
    int val5=TypeConversionUtil.convertToint(104);
    var node1=new IntDblLnkNode(val1);
    var node2=new IntDblLnkNode(val2,node1);
    var node3=new IntDblLnkNode(node1,val3);
    var node4=new IntDblLnkNode(val4);
    var node5=new IntDblLnkNode(node3,val5,node4);
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
    int lessVal=0;
    int lessEqualsVal=0;
    int greaterVal=1;
    int greaterEqualsVal=1;
    var lessNode=new IntDblLnkNode(lessVal);
    var lessEqualsNode=new IntDblLnkNode(lessEqualsVal);
    var greaterNode=new IntDblLnkNode(greaterVal);
    var greaterEqualsNode=new IntDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
  */
}
