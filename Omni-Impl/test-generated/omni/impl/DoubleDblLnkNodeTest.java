package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class DoubleDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    double val1=TypeConversionUtil.convertTodouble(100);
    double val2=TypeConversionUtil.convertTodouble(101);
    double val3=TypeConversionUtil.convertTodouble(102);
    double val4=TypeConversionUtil.convertTodouble(103);
    double val5=TypeConversionUtil.convertTodouble(104);
    var node1=new DoubleDblLnkNode(val1);
    var node2=new DoubleDblLnkNode(val2,node1);
    var node3=new DoubleDblLnkNode(node1,val3);
    var node4=new DoubleDblLnkNode(val4);
    var node5=new DoubleDblLnkNode(node3,val5,node4);
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
    double lessVal=0;
    double lessEqualsVal=0;
    double greaterVal=1;
    double greaterEqualsVal=1;
    var lessNode=new DoubleDblLnkNode(lessVal);
    var lessEqualsNode=new DoubleDblLnkNode(lessEqualsVal);
    var greaterNode=new DoubleDblLnkNode(greaterVal);
    var greaterEqualsNode=new DoubleDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
