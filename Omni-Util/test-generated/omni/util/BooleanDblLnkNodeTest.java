package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class BooleanDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    boolean val1=TypeConversionUtil.convertToboolean(100);
    boolean val2=TypeConversionUtil.convertToboolean(101);
    boolean val3=TypeConversionUtil.convertToboolean(102);
    boolean val4=TypeConversionUtil.convertToboolean(103);
    boolean val5=TypeConversionUtil.convertToboolean(104);
    var node1=new BooleanDblLnkNode(val1);
    var node2=new BooleanDblLnkNode(val2,node1);
    var node3=new BooleanDblLnkNode(node1,val3);
    var node4=new BooleanDblLnkNode(val4);
    var node5=new BooleanDblLnkNode(node3,val5,node4);
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
    boolean val=TypeConversionUtil.convertToboolean(100);
    var expected=Boolean.toString(val);
    var node=new BooleanDblLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    boolean val=TypeConversionUtil.convertToboolean(100);
    boolean equalsVal=TypeConversionUtil.convertToboolean(100);
    boolean notEqualsVal=TypeConversionUtil.convertToboolean(101);
    var node=new BooleanDblLnkNode(val);
    var equalsNode=new BooleanDblLnkNode(equalsVal);
    var notEqualsNode=new BooleanDblLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    var val=true;
    var expected=Boolean.hashCode(val);
    var node=new BooleanDblLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
    val=false;
    expected=Boolean.hashCode(val);
    node=new BooleanDblLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    var lessVal=false;
    var lessEqualsVal=false;
    var greaterVal=true;
    var greaterEqualsVal=true;
    var lessNode=new BooleanDblLnkNode(lessVal);
    var lessEqualsNode=new BooleanDblLnkNode(lessEqualsVal);
    var greaterNode=new BooleanDblLnkNode(greaterVal);
    var greaterEqualsNode=new BooleanDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}