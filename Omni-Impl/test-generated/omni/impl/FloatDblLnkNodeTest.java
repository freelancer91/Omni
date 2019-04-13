package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class FloatDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    float val1=TypeConversionUtil.convertTofloat(100);
    float val2=TypeConversionUtil.convertTofloat(101);
    float val3=TypeConversionUtil.convertTofloat(102);
    float val4=TypeConversionUtil.convertTofloat(103);
    float val5=TypeConversionUtil.convertTofloat(104);
    var node1=new FloatDblLnkNode(val1);
    var node2=new FloatDblLnkNode(val2,node1);
    var node3=new FloatDblLnkNode(node1,val3);
    var node4=new FloatDblLnkNode(val4);
    var node5=new FloatDblLnkNode(node3,val5,node4);
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
  @Test
  public void testCompareTo()
  {
    float lessVal=0;
    float lessEqualsVal=0;
    float greaterVal=1;
    float greaterEqualsVal=1;
    var lessNode=new FloatDblLnkNode(lessVal);
    var lessEqualsNode=new FloatDblLnkNode(lessEqualsVal);
    var greaterNode=new FloatDblLnkNode(greaterVal);
    var greaterEqualsNode=new FloatDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
