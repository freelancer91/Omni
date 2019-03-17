package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class CharSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    char val1=TypeConversionUtil.convertTochar(100);
    char val2=TypeConversionUtil.convertTochar(101);
    var node1=new CharSnglLnkNode(val1);
    var node2=new CharSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    char val=TypeConversionUtil.convertTochar(100);
    var expected=Character.toString(val);
    var node=new CharSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    char val=TypeConversionUtil.convertTochar(100);
    char equalsVal=TypeConversionUtil.convertTochar(100);
    char notEqualsVal=TypeConversionUtil.convertTochar(101);
    var node=new CharSnglLnkNode(val);
    var equalsNode=new CharSnglLnkNode(equalsVal);
    var notEqualsNode=new CharSnglLnkNode(notEqualsVal);
    Assertions.assertTrue(node.equals(node));
    Assertions.assertFalse(node.equals(null));
    Assertions.assertFalse(node.equals(new Object()));
    Assertions.assertTrue(node.equals(equalsNode));
    Assertions.assertFalse(node.equals(notEqualsNode));
  }
  @Test
  public void testHashCode()
  {
    char val=TypeConversionUtil.convertTochar(100);
    var expected=Character.hashCode(val);
    var node=new CharSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    char lessVal=0;
    char lessEqualsVal=0;
    char greaterVal=1;
    char greaterEqualsVal=1;
    var lessNode=new CharSnglLnkNode(lessVal);
    var lessEqualsNode=new CharSnglLnkNode(lessEqualsVal);
    var greaterNode=new CharSnglLnkNode(greaterVal);
    var greaterEqualsNode=new CharSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
