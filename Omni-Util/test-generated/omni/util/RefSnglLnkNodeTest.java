package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class RefSnglLnkNodeTest<E>
{
  @Test
  public void testConstructors()
  {
    var val1=TypeConversionUtil.convertToInteger(100);
    var val2=TypeConversionUtil.convertToInteger(101);
    var node1=new RefSnglLnkNode<Integer>(val1);
    var node2=new RefSnglLnkNode<Integer>(val2,node1);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node2.next==node1);
  }
  @Test
  public void testCompareTo()
  {
    var lessVal=TypeConversionUtil.convertToInteger(100);
    var lessEqualsVal=TypeConversionUtil.convertToInteger(100);
    var greaterVal=TypeConversionUtil.convertToInteger(101);
    var greaterEqualsVal=TypeConversionUtil.convertToInteger(101);
    var lessNode=new RefSnglLnkNode<Integer>(lessVal);
    var lessEqualsNode=new RefSnglLnkNode<Integer>(lessEqualsVal);
    var greaterNode=new RefSnglLnkNode<Integer>(greaterVal);
    var greaterEqualsNode=new RefSnglLnkNode<Integer>(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
