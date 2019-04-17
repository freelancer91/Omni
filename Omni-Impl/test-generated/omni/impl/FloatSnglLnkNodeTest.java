package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.HashUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class FloatSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTofloat(i);
      var node=new FloatSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static FloatSnglLnkNode buildAscendingSequence(int length,int initVal,FloatSnglLnkNode initNode)
  {
    var node=new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_FloatSnglLnkNode()
  {
    var next=new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTofloat(i);
      var node=new FloatSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertTofloat(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_FloatSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=FloatSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_FloatSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     FloatSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_FloatSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
    int expectedHash=31+HashUtil.hashFloat(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+HashUtil.hashFloat(currNode.val);
    }
    Assertions.assertEquals(expectedHash,FloatSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_FloatSnglLnkNode_FloatConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
    FloatSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontainsBits_FloatSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertTrue(FloatSnglLnkNode.uncheckedcontainsBits(beginNode,Float.floatToRawIntBits(TypeConversionUtil.convertTofloat(7))));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertFalse(FloatSnglLnkNode.uncheckedcontainsBits(beginNode,Float.floatToRawIntBits(TypeConversionUtil.convertTofloat(100))));
    }
  }
  @Test
  public void testuncheckedcontainsNaN_FloatSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new FloatSnglLnkNode(Float.NaN,buildAscendingSequence(5,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)))));
      Assertions.assertTrue(FloatSnglLnkNode.uncheckedcontainsNaN(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertFalse(FloatSnglLnkNode.uncheckedcontainsNaN(beginNode));
    }
  }
  @Test
  public void testuncheckedcontains0_FloatSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,5,new FloatSnglLnkNode(0,buildAscendingSequence(5,5,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(5)))));
      Assertions.assertTrue(FloatSnglLnkNode.uncheckedcontains0(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,10,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(10)));
      Assertions.assertFalse(FloatSnglLnkNode.uncheckedcontains0(beginNode));
    }
  }
  @Test
  public void testuncheckedsearchBits_FloatSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertEquals(4,FloatSnglLnkNode.uncheckedsearchBits(beginNode,Float.floatToRawIntBits(TypeConversionUtil.convertTofloat(7))));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertEquals(-1,FloatSnglLnkNode.uncheckedsearchBits(beginNode,Float.floatToRawIntBits(TypeConversionUtil.convertTofloat(100))));
    }
  }
  @Test
  public void testuncheckedsearchNaN_FloatSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new FloatSnglLnkNode(Float.NaN,buildAscendingSequence(5,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)))));
      Assertions.assertEquals(6,FloatSnglLnkNode.uncheckedsearchNaN(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
      Assertions.assertEquals(-1,FloatSnglLnkNode.uncheckedsearchNaN(beginNode));
    }
  }
  @Test
  public void testuncheckedsearch0_FloatSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,5,new FloatSnglLnkNode(0,buildAscendingSequence(5,5,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(5)))));
      Assertions.assertEquals(6,FloatSnglLnkNode.uncheckedsearch0(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,10,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(10)));
      Assertions.assertEquals(-1,FloatSnglLnkNode.uncheckedsearch0(beginNode));
    }
  }
  @Test
  public void testuncheckedCopyInto_FloatSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
     float[] dst=new float[11];
     FloatSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_FloatSnglLnkNode_FloatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
     Float[] dst=new Float[11];
     FloatSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToFloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_FloatSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
     Object[] dst=new Object[11];
     FloatSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToFloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_FloatSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new FloatSnglLnkNode(TypeConversionUtil.convertTofloat(0)));
     double[] dst=new double[11];
     FloatSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
}
