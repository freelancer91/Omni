package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class ShortSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      var node=new ShortSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static ShortSnglLnkNode buildAscendingSequence(int length,int initVal,ShortSnglLnkNode initNode)
  {
    var node=new ShortSnglLnkNode(TypeConversionUtil.convertToshort(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new ShortSnglLnkNode(TypeConversionUtil.convertToshort(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_ShortSnglLnkNode()
  {
    var next=new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToshort(i);
      var node=new ShortSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertToshort(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_ShortSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=ShortSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_ShortSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     ShortSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_ShortSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
    int expectedHash=31+(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+(currNode.val);
    }
    Assertions.assertEquals(expectedHash,ShortSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_ShortSnglLnkNode_ShortConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
    ShortSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_ShortSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
      Assertions.assertTrue(ShortSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertToshort(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
      Assertions.assertFalse(ShortSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertToshort(100)));
    }
  }
  @Test
  public void testuncheckedsearch_ShortSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
      Assertions.assertEquals(4,ShortSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertToshort(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
      Assertions.assertEquals(-1,ShortSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertToshort(100)));
    }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_shortArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     short[] dst=new short[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToshort(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_ShortArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     Short[] dst=new Short[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToShort(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     Object[] dst=new Object[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToShort(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     double[] dst=new double[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     float[] dst=new float[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     long[] dst=new long[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_ShortSnglLnkNode_intArray()
  {
     var beginNode=buildAscendingSequence(10,0,new ShortSnglLnkNode(TypeConversionUtil.convertToshort(0)));
     int[] dst=new int[11];
     ShortSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToint(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testCompareTo()
  {
    short lessVal=0;
    short lessEqualsVal=0;
    short greaterVal=1;
    short greaterEqualsVal=1;
    var lessNode=new ShortSnglLnkNode(lessVal);
    var lessEqualsNode=new ShortSnglLnkNode(lessEqualsVal);
    var greaterNode=new ShortSnglLnkNode(greaterVal);
    var greaterEqualsNode=new ShortSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
