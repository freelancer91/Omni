package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import java.util.ArrayList;
public class CharSnglLnkNodeTest
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      var node=new CharSnglLnkNode(val);
      Assertions.assertEquals(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static CharSnglLnkNode buildAscendingSequence(int length,int initVal,CharSnglLnkNode initNode)
  {
    var node=new CharSnglLnkNode(TypeConversionUtil.convertTochar(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new CharSnglLnkNode(TypeConversionUtil.convertTochar(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_CharSnglLnkNode()
  {
    var next=new CharSnglLnkNode(TypeConversionUtil.convertTochar(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertTochar(i);
      var node=new CharSnglLnkNode(val,next);
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
      expectedBuilder.append(TypeConversionUtil.convertTochar(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_CharSnglLnkNode_charArray()
  {
    var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
    char[] actualBuffer=new char[11*3];
    actualBuffer[0]='[';
    CharSnglLnkNode.uncheckedToString(beginNode,actualBuffer);
    actualBuffer[actualBuffer.length-1]=']';
    Assertions.assertEquals(buildExpectedString(),new String(actualBuffer));
  }
  @Test
  public void testuncheckedHashCode_CharSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
    int expectedHash=31+(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+(currNode.val);
    }
    Assertions.assertEquals(expectedHash,CharSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_CharSnglLnkNode_CharConsumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
    CharSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
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
  public void testuncheckedcontains_CharSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
      Assertions.assertTrue(CharSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTochar(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
      Assertions.assertFalse(CharSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertTochar(100)));
    }
  }
  @Test
  public void testuncheckedsearch_CharSnglLnkNode_int()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
      Assertions.assertEquals(4,CharSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTochar(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
      Assertions.assertEquals(-1,CharSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertTochar(100)));
    }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_charArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     char[] dst=new char[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTochar(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_CharacterArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     Character[] dst=new Character[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToCharacter(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     Object[] dst=new Object[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToCharacter(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_doubleArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     double[] dst=new double[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTodouble(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_floatArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     float[] dst=new float[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTofloat(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_longArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     long[] dst=new long[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertTolong(i),(Object)dst[10-i]);
     }
  }
  @Test
  public void testuncheckedCopyInto_CharSnglLnkNode_intArray()
  {
     var beginNode=buildAscendingSequence(10,0,new CharSnglLnkNode(TypeConversionUtil.convertTochar(0)));
     int[] dst=new int[11];
     CharSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToint(i),(Object)dst[10-i]);
     }
  }
}
