package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class RefDblLnkNodeTest<E>
{
  @Test
  public void testConstructors()
  {
    var val1=TypeConversionUtil.convertToInteger(100);
    var val2=TypeConversionUtil.convertToInteger(101);
    var val3=TypeConversionUtil.convertToInteger(102);
    var val4=TypeConversionUtil.convertToInteger(103);
    var val5=TypeConversionUtil.convertToInteger(104);
    var node1=new RefDblLnkNode<Integer>(val1);
    var node2=new RefDblLnkNode<Integer>(val2,node1);
    var node3=new RefDblLnkNode<Integer>(node1,val3);
    var node4=new RefDblLnkNode<Integer>(val4);
    var node5=new RefDblLnkNode<Integer>(node3,val5,node4);
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
  public void testToString()
  {
    var val=TypeConversionUtil.convertToInteger(100);
    var expected=val.toString();
    var node=new RefDblLnkNode<Integer>(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    var val=TypeConversionUtil.convertToInteger(100);
    var equalsVal=TypeConversionUtil.convertToInteger(100);
    var notEqualsVal=TypeConversionUtil.convertToInteger(101);
    var node=new RefDblLnkNode<Integer>(val);
    var equalsNode=new RefDblLnkNode<Integer>(equalsVal);
    var notEqualsNode=new RefDblLnkNode<Integer>(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    var val=TypeConversionUtil.convertToInteger(100);
    var expected=val.hashCode();
    var node=new RefDblLnkNode<Integer>(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    var lessVal=TypeConversionUtil.convertToInteger(100);
    var lessEqualsVal=TypeConversionUtil.convertToInteger(100);
    var greaterVal=TypeConversionUtil.convertToInteger(101);
    var greaterEqualsVal=TypeConversionUtil.convertToInteger(101);
    var lessNode=new RefDblLnkNode<Integer>(lessVal);
    var lessEqualsNode=new RefDblLnkNode<Integer>(lessEqualsVal);
    var greaterNode=new RefDblLnkNode<Integer>(greaterVal);
    var greaterEqualsNode=new RefDblLnkNode<Integer>(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
