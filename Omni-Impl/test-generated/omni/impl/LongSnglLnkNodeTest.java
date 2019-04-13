package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.util.ToStringUtil;
import java.util.ArrayList;
public class LongSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      var node=new LongSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static LongSnglLnkNode buildAscendingSequence(int length,int initVal,LongSnglLnkNode initNode)
  {
    var node=new LongSnglLnkNode(TypeConversionUtil.convertTolong(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new LongSnglLnkNode(TypeConversionUtil.convertTolong(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_LongSnglLnkNode()
  {
    var next=new LongSnglLnkNode(TypeConversionUtil.convertTolong(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTolong(i);
      var node=new LongSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertTolong(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_LongSnglLnkNode_byteArray()
  {
    var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
    byte[] actualBuffer=new byte[22*11];
    actualBuffer[0]=(byte)'[';
    int bufferOffset=LongSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[bufferOffset]=(byte)']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer,0,bufferOffset+1,ToStringUtil.IOS8859CharSet));
  }
  @Test
  public void testuncheckedToString_LongSnglLnkNode_OmniStringBuilderByte()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     ToStringUtil.OmniStringBuilderByte actualBuilder=new ToStringUtil.OmniStringBuilderByte(0,new byte[22*11]);
     actualBuilder.uncheckedAppendChar((byte)'[');
     LongSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
     actualBuilder.uncheckedAppendChar((byte)']');
     Assertions.assertEquals(buildExpectedString(),actualBuilder.toString());
  }
  @Test
  public void testuncheckedHashCode_LongSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
    int expectedHash=31+Long.hashCode(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+Long.hashCode(currNode.val);
    }
    Assertions.assertEquals(expectedHash,LongSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_LongSnglLnkNode_LongConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
    LongSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_LongSnglLnkNode_long()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
      Assertions.assertTrue(LongSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTolong(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
      Assertions.assertFalse(LongSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTolong(100)));
    }
  }
  @Test
  public void testuncheckedsearch_LongSnglLnkNode_long()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
      Assertions.assertEquals(4,LongSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTolong(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
      Assertions.assertEquals(-1,LongSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTolong(100)));
    }
  }
  @Test
  public void testuncheckedCopyInto_LongSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     long[] dst=new long[11];
     LongSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_LongSnglLnkNode_LongArray()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     Long[] dst=new Long[11];
     LongSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToLong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_LongSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     Object[] dst=new Object[11];
     LongSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToLong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_LongSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     double[] dst=new double[11];
     LongSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_LongSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new LongSnglLnkNode(TypeConversionUtil.convertTolong(0)));
     float[] dst=new float[11];
     LongSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testCompareTo()
  {
    long lessVal=0;
    long lessEqualsVal=0;
    long greaterVal=1;
    long greaterEqualsVal=1;
    var lessNode=new LongSnglLnkNode(lessVal);
    var lessEqualsNode=new LongSnglLnkNode(lessEqualsVal);
    var greaterNode=new LongSnglLnkNode(greaterVal);
    var greaterEqualsNode=new LongSnglLnkNode(greaterEqualsVal);
    Assertions.assertEquals(0,lessNode.compareTo(lessNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterNode));
    Assertions.assertEquals(0,lessNode.compareTo(lessEqualsNode));
    Assertions.assertEquals(0,greaterNode.compareTo(greaterEqualsNode));
    Assertions.assertEquals(-1,lessNode.compareTo(greaterNode));
    Assertions.assertEquals(1,greaterNode.compareTo(lessNode));
  }
}
