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
