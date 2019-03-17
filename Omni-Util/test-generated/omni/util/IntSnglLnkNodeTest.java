package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class IntSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    int val1=TypeConversionUtil.convertToint(100);
    int val2=TypeConversionUtil.convertToint(101);
    var node1=new IntSnglLnkNode(val1);
    var node2=new IntSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    int val=TypeConversionUtil.convertToint(100);
    var expected=Integer.toString(val);
    var node=new IntSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    int val=TypeConversionUtil.convertToint(100);
    int equalsVal=TypeConversionUtil.convertToint(100);
    int notEqualsVal=TypeConversionUtil.convertToint(101);
    var node=new IntSnglLnkNode(val);
    var equalsNode=new IntSnglLnkNode(equalsVal);
    var notEqualsNode=new IntSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    int val=TypeConversionUtil.convertToint(100);
    var expected=Integer.hashCode(val);
    var node=new IntSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    int lessVal=0;
    int lessEqualsVal=0;
    int greaterVal=1;
    int greaterEqualsVal=1;
    var lessNode=new IntSnglLnkNode(lessVal);
    var lessEqualsNode=new IntSnglLnkNode(lessEqualsVal);
    var greaterNode=new IntSnglLnkNode(greaterVal);
    var greaterEqualsNode=new IntSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
