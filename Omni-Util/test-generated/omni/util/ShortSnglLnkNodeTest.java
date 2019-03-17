package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class ShortSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    short val1=TypeConversionUtil.convertToshort(100);
    short val2=TypeConversionUtil.convertToshort(101);
    var node1=new ShortSnglLnkNode(val1);
    var node2=new ShortSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    short val=TypeConversionUtil.convertToshort(100);
    var expected=Short.toString(val);
    var node=new ShortSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    short val=TypeConversionUtil.convertToshort(100);
    short equalsVal=TypeConversionUtil.convertToshort(100);
    short notEqualsVal=TypeConversionUtil.convertToshort(101);
    var node=new ShortSnglLnkNode(val);
    var equalsNode=new ShortSnglLnkNode(equalsVal);
    var notEqualsNode=new ShortSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    short val=TypeConversionUtil.convertToshort(100);
    var expected=Short.hashCode(val);
    var node=new ShortSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    short lessVal=0;
    short lessEqualsVal=0;
    short greaterVal=1;
    short greaterEqualsVal=1;
    var lessNode=new ShortSnglLnkNode(lessVal);
    var lessEqualsNode=new ShortSnglLnkNode(lessEqualsVal);
    var greaterNode=new ShortSnglLnkNode(greaterVal);
    var greaterEqualsNode=new ShortSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}