package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class DoubleSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    double val1=TypeConversionUtil.convertTodouble(100);
    double val2=TypeConversionUtil.convertTodouble(101);
    var node1=new DoubleSnglLnkNode(val1);
    var node2=new DoubleSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    double val=TypeConversionUtil.convertTodouble(100);
    var expected=Double.toString(val);
    var node=new DoubleSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    double val=TypeConversionUtil.convertTodouble(100);
    double equalsVal=TypeConversionUtil.convertTodouble(100);
    double notEqualsVal=TypeConversionUtil.convertTodouble(101);
    var node=new DoubleSnglLnkNode(val);
    var equalsNode=new DoubleSnglLnkNode(equalsVal);
    var notEqualsNode=new DoubleSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    double val=TypeConversionUtil.convertTodouble(100);
    var expected=Double.hashCode(val);
    var node=new DoubleSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    double lessVal=0;
    double lessEqualsVal=0;
    double greaterVal=1;
    double greaterEqualsVal=1;
    var lessNode=new DoubleSnglLnkNode(lessVal);
    var lessEqualsNode=new DoubleSnglLnkNode(lessEqualsVal);
    var greaterNode=new DoubleSnglLnkNode(greaterVal);
    var greaterEqualsNode=new DoubleSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
