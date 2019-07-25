package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
public class CharDblLnkNodeTest
{
  @Test
  public void testConstructors()
  {
    char val1=TypeConversionUtil.convertTochar(100);
    char val2=TypeConversionUtil.convertTochar(101);
    char val3=TypeConversionUtil.convertTochar(102);
    char val4=TypeConversionUtil.convertTochar(103);
    char val5=TypeConversionUtil.convertTochar(104);
    var node1=new CharDblLnkNode(val1);
    var node2=new CharDblLnkNode(val2,node1);
    var node3=new CharDblLnkNode(node1,val3);
    var node4=new CharDblLnkNode(val4);
    var node5=new CharDblLnkNode(node3,val5,node4);
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
/*
  @Test
  public void testCompareTo()
  {
    char lessVal=0;
    char lessEqualsVal=0;
    char greaterVal=1;
    char greaterEqualsVal=1;
    var lessNode=new CharDblLnkNode(lessVal);
    var lessEqualsNode=new CharDblLnkNode(lessEqualsVal);
    var greaterNode=new CharDblLnkNode(greaterVal);
    var greaterEqualsNode=new CharDblLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
  */
}
