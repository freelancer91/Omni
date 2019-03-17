package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class ShortDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    short val1=TypeConversionUtil.convertToshort(100);
    short val2=TypeConversionUtil.convertToshort(101);
    short val3=TypeConversionUtil.convertToshort(102);
    short val4=TypeConversionUtil.convertToshort(103);
    short val5=TypeConversionUtil.convertToshort(104);
    var node1=new ShortDblLnkNode(val1);
    var node2=new ShortDblLnkNode(val2,node1);
    var node3=new ShortDblLnkNode(node1,val3);
    var node4=new ShortDblLnkNode(val4);
    var node5=new ShortDblLnkNode(node3,val5,node4);
    Assertions.assertTrue(node1.val==val1);
    Assertions.assertTrue(node2.val==val2);
    Assertions.assertTrue(node3.val==val3);
    Assertions.assertTrue(node4.val==val4);
    Assertions.assertTrue(node5.val==val5);
    Assertions.assertTrue(node1.next==null);
    Assertions.assertTrue(node1.prev==null);
    Assertions.assertTrue(node2.next==node1);
    Assertions.assertTrue(node2.prev==null);
    Assertions.assertTrue(node3.next==null);
    Assertions.assertTrue(node3.prev==node1);
    Assertions.assertTrue(node4.next==null);
    Assertions.assertTrue(node4.prev==null);
    Assertions.assertTrue(node5.next==node4);
    Assertions.assertTrue(node5.prev==node3);
  }
  @Test
  public void testToString()
  {
    short val=TypeConversionUtil.convertToshort(100);
    var expected=Short.toString(val);
    var node=new ShortDblLnkNode(val);
    Assertions.assertEquals(expected,node.toString());
  }
  @Test
  public void testEquals()
  {
    short val=TypeConversionUtil.convertToshort(100);
    short equalsVal=TypeConversionUtil.convertToshort(100);
    short notEqualsVal=TypeConversionUtil.convertToshort(101);
    var node=new ShortDblLnkNode(val);
    var equalsNode=new ShortDblLnkNode(equalsVal);
    var notEqualsNode=new ShortDblLnkNode(notEqualsVal);
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
    var node=new ShortDblLnkNode(val);
    Assertions.assertEquals(expected,node.hashCode());
  }
  @Test
  public void testCompareTo()
  {
    short lessVal=0;
    short lessEqualsVal=0;
    short greaterVal=1;
    short greaterEqualsVal=1;
    var lessNode=new ShortDblLnkNode(lessVal);
    var lessEqualsNode=new ShortDblLnkNode(lessEqualsVal);
    var greaterNode=new ShortDblLnkNode(greaterVal);
    var greaterEqualsNode=new ShortDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
