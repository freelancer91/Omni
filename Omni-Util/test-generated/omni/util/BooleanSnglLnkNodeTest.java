package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class BooleanSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    boolean val1=TypeConversionUtil.convertToboolean(100);
    boolean val2=TypeConversionUtil.convertToboolean(101);
    var node1=new BooleanSnglLnkNode(val1);
    var node2=new BooleanSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testToString()
  {
    boolean val=TypeConversionUtil.convertToboolean(100);
    var expected=Boolean.toString(val);
    var node=new BooleanSnglLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    boolean val=TypeConversionUtil.convertToboolean(100);
    boolean equalsVal=TypeConversionUtil.convertToboolean(100);
    boolean notEqualsVal=TypeConversionUtil.convertToboolean(101);
    var node=new BooleanSnglLnkNode(val);
    var equalsNode=new BooleanSnglLnkNode(equalsVal);
    var notEqualsNode=new BooleanSnglLnkNode(notEqualsVal);
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
    var node=new BooleanSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
    val=false;
    expected=Boolean.hashCode(val);
    node=new BooleanSnglLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    var lessVal=false;
    var lessEqualsVal=false;
    var greaterVal=true;
    var greaterEqualsVal=true;
    var lessNode=new BooleanSnglLnkNode(lessVal);
    var lessEqualsNode=new BooleanSnglLnkNode(lessEqualsVal);
    var greaterNode=new BooleanSnglLnkNode(greaterVal);
    var greaterEqualsNode=new BooleanSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
