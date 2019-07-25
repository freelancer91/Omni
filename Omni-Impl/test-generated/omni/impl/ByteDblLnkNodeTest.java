package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class ByteDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    byte val1=TypeConversionUtil.convertTobyte(100);
    byte val2=TypeConversionUtil.convertTobyte(101);
    byte val3=TypeConversionUtil.convertTobyte(102);
    byte val4=TypeConversionUtil.convertTobyte(103);
    byte val5=TypeConversionUtil.convertTobyte(104);
    var node1=new ByteDblLnkNode(val1);
    var node2=new ByteDblLnkNode(val2,node1);
    var node3=new ByteDblLnkNode(node1,val3);
    var node4=new ByteDblLnkNode(val4);
    var node5=new ByteDblLnkNode(node3,val5,node4);
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
    byte lessVal=0;
    byte lessEqualsVal=0;
    byte greaterVal=1;
    byte greaterEqualsVal=1;
    var lessNode=new ByteDblLnkNode(lessVal);
    var lessEqualsNode=new ByteDblLnkNode(lessEqualsVal);
    var greaterNode=new ByteDblLnkNode(greaterVal);
    var greaterEqualsNode=new ByteDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
  */
}
