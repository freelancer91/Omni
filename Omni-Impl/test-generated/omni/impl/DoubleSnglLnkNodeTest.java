package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.HashUtil;
import java.util.ArrayList;
public class DoubleSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      var node=new DoubleSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static DoubleSnglLnkNode buildAscendingSequence(int length,int initVal,DoubleSnglLnkNode initNode)
  {
    var node=new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_DoubleSnglLnkNode()
  {
    var next=new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTodouble(i);
      var node=new DoubleSnglLnkNode(val,next);
      Assertions.assertEquals(val,node.val);
      Assertions.assertSame(node.next,next);
      next=node;
    }
  }
  private static String buildExpectedString()
  {
    StringBuilder expectedBuilder=new StringBuilder("[");
    for(int i=11;;)
    {
      expectedBuilder.append(TypeConversionUtil.convertTodouble(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_DoubleSnglLnkNode_StringBuilder()
  {
    var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
    StringBuilder actualBuilder=new StringBuilder("[");
    DoubleSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
    Assertions.assertEquals(buildExpectedString(),actualBuilder.append(']').toString());
  }
  @Test
  public void testuncheckedHashCode_DoubleSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
    int expectedHash=31+HashUtil.hashDouble(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+HashUtil.hashDouble(currNode.val);
    }
    Assertions.assertEquals(expectedHash,DoubleSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_DoubleSnglLnkNode_DoubleConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
    DoubleSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
    var itr=consumer.iterator();
    for(;;)
    {
      Assertions.assertTrue(itr.hasNext());
      var itrVal=itr.next();
      var currVal=beginNode.val;
      Assertions.assertEquals(itrVal,(Object)currVal);
      if((beginNode=beginNode.next)==null)
      {
        Assertions.assertFalse(itr.hasNext());
        return;
      }
    }
  }
  @Test
  public void testuncheckedcontainsBits_DoubleSnglLnkNode_long()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertTrue(DoubleSnglLnkNode.uncheckedcontainsBits(beginNode,Double.doubleToRawLongBits(TypeConversionUtil.convertTodouble(7))));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertFalse(DoubleSnglLnkNode.uncheckedcontainsBits(beginNode,Double.doubleToRawLongBits(TypeConversionUtil.convertTodouble(100))));
    }
  }
  @Test
  public void testuncheckedcontainsNaN_DoubleSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new DoubleSnglLnkNode(Double.NaN,buildAscendingSequence(5,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)))));
      Assertions.assertTrue(DoubleSnglLnkNode.uncheckedcontainsNaN(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertFalse(DoubleSnglLnkNode.uncheckedcontainsNaN(beginNode));
    }
  }
  @Test
  public void testuncheckedcontains0_DoubleSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,5,new DoubleSnglLnkNode(0,buildAscendingSequence(5,5,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(5)))));
      Assertions.assertTrue(DoubleSnglLnkNode.uncheckedcontains0(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,10,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(10)));
      Assertions.assertFalse(DoubleSnglLnkNode.uncheckedcontains0(beginNode));
    }
  }
  @Test
  public void testuncheckedsearchBits_DoubleSnglLnkNode_long()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertEquals(4,DoubleSnglLnkNode.uncheckedsearchBits(beginNode,Double.doubleToRawLongBits(TypeConversionUtil.convertTodouble(7))));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertEquals(-1,DoubleSnglLnkNode.uncheckedsearchBits(beginNode,Double.doubleToRawLongBits(TypeConversionUtil.convertTodouble(100))));
    }
  }
  @Test
  public void testuncheckedsearchNaN_DoubleSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new DoubleSnglLnkNode(Double.NaN,buildAscendingSequence(5,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)))));
      Assertions.assertEquals(6,DoubleSnglLnkNode.uncheckedsearchNaN(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
      Assertions.assertEquals(-1,DoubleSnglLnkNode.uncheckedsearchNaN(beginNode));
    }
  }
  @Test
  public void testuncheckedsearch0_DoubleSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,5,new DoubleSnglLnkNode(0,buildAscendingSequence(5,5,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(5)))));
      Assertions.assertEquals(6,DoubleSnglLnkNode.uncheckedsearch0(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,10,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(10)));
      Assertions.assertEquals(-1,DoubleSnglLnkNode.uncheckedsearch0(beginNode));
    }
  }
  @Test
  public void testuncheckedCopyInto_DoubleSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
     double[] dst=new double[11];
     DoubleSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_DoubleSnglLnkNode_DoubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
     Double[] dst=new Double[11];
     DoubleSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToDouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_DoubleSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new DoubleSnglLnkNode(TypeConversionUtil.convertTodouble(0)));
     Object[] dst=new Object[11];
     DoubleSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToDouble(i),(Object)dst[10-i]);
     }
  }
}
