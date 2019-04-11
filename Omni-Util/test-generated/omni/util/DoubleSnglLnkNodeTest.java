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
