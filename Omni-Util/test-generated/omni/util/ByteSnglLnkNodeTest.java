package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class ByteSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    byte val1=TypeConversionUtil.convertTobyte(100);
    byte val2=TypeConversionUtil.convertTobyte(101);
    var node1=new ByteSnglLnkNode(val1);
    var node2=new ByteSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    byte val=TypeConversionUtil.convertTobyte(100);
    var expected=Byte.toString(val);
    var node=new ByteSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    byte val=TypeConversionUtil.convertTobyte(100);
    byte equalsVal=TypeConversionUtil.convertTobyte(100);
    byte notEqualsVal=TypeConversionUtil.convertTobyte(101);
    var node=new ByteSnglLnkNode(val);
    var equalsNode=new ByteSnglLnkNode(equalsVal);
    var notEqualsNode=new ByteSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    byte val=TypeConversionUtil.convertTobyte(100);
    var expected=Byte.hashCode(val);
    var node=new ByteSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    byte lessVal=0;
    byte lessEqualsVal=0;
    byte greaterVal=1;
    byte greaterEqualsVal=1;
    var lessNode=new ByteSnglLnkNode(lessVal);
    var lessEqualsNode=new ByteSnglLnkNode(lessEqualsVal);
    var greaterNode=new ByteSnglLnkNode(greaterVal);
    var greaterEqualsNode=new ByteSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}