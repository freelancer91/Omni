package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class ByteSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      var node=new ByteSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static ByteSnglLnkNode buildAscendingSequence(int length,int initVal,ByteSnglLnkNode initNode)
  {
    var node=new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_ByteSnglLnkNode()
  {
    var next=new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTobyte(i);
      var node=new ByteSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertTobyte(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_ByteSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=ByteSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_ByteSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     ByteSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_ByteSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
    int expectedHash=31+(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+(currNode.val);
    }
    Assertions.assertEquals(expectedHash,ByteSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_ByteSnglLnkNode_ByteConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
    ByteSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_ByteSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
      Assertions.assertTrue(ByteSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTobyte(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
      Assertions.assertFalse(ByteSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTobyte(100)));
    }
  }
  @Test
  public void testuncheckedsearch_ByteSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
      Assertions.assertEquals(4,ByteSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTobyte(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
      Assertions.assertEquals(-1,ByteSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTobyte(100)));
    }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_byteArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     byte[] dst=new byte[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTobyte(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_ByteArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     Byte[] dst=new Byte[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToByte(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     Object[] dst=new Object[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToByte(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     double[] dst=new double[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     float[] dst=new float[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     long[] dst=new long[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_intArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     int[] dst=new int[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToint(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ByteSnglLnkNode_shortArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ByteSnglLnkNode(TypeConversionUtil.convertTobyte(0)));
     short[] dst=new short[11];
     ByteSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToshort(i),(Object)dst[10-i]);
     }
  }
}
