package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class BooleanSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToboolean(i);
      var node=new BooleanSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static BooleanSnglLnkNode buildAscendingSequence(int length,int initVal,BooleanSnglLnkNode initNode)
  {
    var node=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_BooleanSnglLnkNode()
  {
    var next=new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToboolean(i);
      var node=new BooleanSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertToboolean(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_BooleanSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=BooleanSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_BooleanSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     BooleanSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_BooleanSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
    int expectedHash=31+Boolean.hashCode(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+Boolean.hashCode(currNode.val);
    }
    Assertions.assertEquals(expectedHash,BooleanSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_BooleanSnglLnkNode_BooleanConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
    BooleanSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_BooleanSnglLnkNode_boolean()
  {
    //positive return
    {
      var node=new BooleanSnglLnkNode(false);
      for(int i=0;i<5;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      node=new BooleanSnglLnkNode(true,node);
      for(int i=0;i<5;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      Assertions.assertTrue(BooleanSnglLnkNode.uncheckedcontains(node,true));
    }
    //negative return
    {
      var node=new BooleanSnglLnkNode(false);
      for(int i=0;i<10;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      Assertions.assertFalse(BooleanSnglLnkNode.uncheckedcontains(node,true));
    }
  }
  @Test
  public void testuncheckedsearch_BooleanSnglLnkNode_boolean()
  {
    //positive return
    {
      var node=new BooleanSnglLnkNode(false);
      for(int i=0;i<5;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      node=new BooleanSnglLnkNode(true,node);
      for(int i=0;i<5;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      Assertions.assertEquals(6,BooleanSnglLnkNode.uncheckedsearch(node,true));
    }
    //negative return
    {
      var node=new BooleanSnglLnkNode(false);
      for(int i=0;i<10;++i)
      {
        node=new BooleanSnglLnkNode(false,node);
      }
      Assertions.assertEquals(-1,BooleanSnglLnkNode.uncheckedsearch(node,true));
    }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_booleanArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     boolean[] dst=new boolean[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_BooleanArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     Boolean[] dst=new Boolean[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToBoolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     Object[] dst=new Object[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToBoolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     double[] dst=new double[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodoubleboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     float[] dst=new float[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloatboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     long[] dst=new long[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolongboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_intArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     int[] dst=new int[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTointboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_shortArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     short[] dst=new short[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToshortboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_byteArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     byte[] dst=new byte[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTobyteboolean(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_BooleanSnglLnkNode_charArray()
  {
     var beginNode=buildAscendingSequence(10,0,new BooleanSnglLnkNode(TypeConversionUtil.convertToboolean(0)));
     char[] dst=new char[11];
     BooleanSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTocharboolean(i),(Object)dst[10-i]);
     }
  }
}
