package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class LongSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    long val1=TypeConversionUtil.convertTolong(100);
    long val2=TypeConversionUtil.convertTolong(101);
    var node1=new LongSnglLnkNode(val1);
    var node2=new LongSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    long val=TypeConversionUtil.convertTolong(100);
    var expected=Long.toString(val);
    var node=new LongSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    long val=TypeConversionUtil.convertTolong(100);
    long equalsVal=TypeConversionUtil.convertTolong(100);
    long notEqualsVal=TypeConversionUtil.convertTolong(101);
    var node=new LongSnglLnkNode(val);
    var equalsNode=new LongSnglLnkNode(equalsVal);
    var notEqualsNode=new LongSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    long val=TypeConversionUtil.convertTolong(100);
    var expected=Long.hashCode(val);
    var node=new LongSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    long lessVal=0;
    long lessEqualsVal=0;
    long greaterVal=1;
    long greaterEqualsVal=1;
    var lessNode=new LongSnglLnkNode(lessVal);
    var lessEqualsNode=new LongSnglLnkNode(lessEqualsVal);
    var greaterNode=new LongSnglLnkNode(greaterVal);
    var greaterEqualsNode=new LongSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}