package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class IntSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToint(i);
      var node=new IntSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static IntSnglLnkNode buildAscendingSequence(int length,int initVal,IntSnglLnkNode initNode)
  {
    var node=new IntSnglLnkNode(TypeConversionUtil.convertToint(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new IntSnglLnkNode(TypeConversionUtil.convertToint(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_IntSnglLnkNode()
  {
    var next=new IntSnglLnkNode(TypeConversionUtil.convertToint(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToint(i);
      var node=new IntSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertToint(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_IntSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=IntSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_IntSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     IntSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_IntSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
    int expectedHash=31+(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+(currNode.val);
    }
    Assertions.assertEquals(expectedHash,IntSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_IntSnglLnkNode_IntConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
    IntSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_IntSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
      Assertions.assertTrue(IntSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertToint(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
      Assertions.assertFalse(IntSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertToint(100)));
    }
  }
  @Test
  public void testuncheckedsearch_IntSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
      Assertions.assertEquals(4,IntSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertToint(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
      Assertions.assertEquals(-1,IntSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertToint(100)));
    }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_intArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     int[] dst=new int[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToint(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_IntegerArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     Integer[] dst=new Integer[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToInteger(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     Object[] dst=new Object[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToInteger(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     double[] dst=new double[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     float[] dst=new float[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_IntSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new IntSnglLnkNode(TypeConversionUtil.convertToint(0)));
     long[] dst=new long[11];
     IntSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolong(i),(Object)dst[10-i]);
     }
  }
}
