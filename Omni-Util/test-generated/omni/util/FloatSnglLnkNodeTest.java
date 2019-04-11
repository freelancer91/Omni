package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class FloatSnglLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    float val1=TypeConversionUtil.convertTofloat(100);
    float val2=TypeConversionUtil.convertTofloat(101);
    var node1=new FloatSnglLnkNode(val1);
    var node2=new FloatSnglLnkNode(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testCompareTo()
  {
    float lessVal=0;
    float lessEqualsVal=0;
    float greaterVal=1;
    float greaterEqualsVal=1;
    var lessNode=new FloatSnglLnkNode(lessVal);
    var lessEqualsNode=new FloatSnglLnkNode(lessEqualsVal);
    var greaterNode=new FloatSnglLnkNode(greaterVal);
    var greaterEqualsNode=new FloatSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
