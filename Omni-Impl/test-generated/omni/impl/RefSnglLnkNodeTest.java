package omni.impl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import java.util.Objects;
import java.util.ArrayList;
@SuppressWarnings({"unchecked","rawtypes"})
public class RefSnglLnkNodeTest<E>
{
  @Test
  public void testConstructor_val()
  {
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToObject(i);
      var node=new RefSnglLnkNode<>(val);
      Assertions.assertSame(val,node.val);
      Assertions.assertNull(node.next);
    }
  }
  private static RefSnglLnkNode buildAscendingSequence(int length,int initVal,RefSnglLnkNode initNode)
  {
    var node=new RefSnglLnkNode(TypeConversionUtil.convertToObject(++initVal),initNode);
    for(int i=1;i<length;++i)
    {
      node=new RefSnglLnkNode(TypeConversionUtil.convertToObject(++initVal),node); 
    }
    return node;
  }
  @Test void testConstructor_val_RefSnglLnkNode()
  {
    var next=new RefSnglLnkNode(TypeConversionUtil.convertToObject(0));
    for(int i=0;i<10;++i)
    {
      var val=TypeConversionUtil.convertToObject(i);
      var node=new RefSnglLnkNode<>(val,next);
      Assertions.assertSame(val,node.val);
      Assertions.assertSame(node.next,next);
      next=node;
    }
  }
  private static String buildExpectedString()
  {
    StringBuilder expectedBuilder=new StringBuilder("[");
    for(int i=11;;)
    {
      expectedBuilder.append(TypeConversionUtil.convertToObject(--i));
      if(i==0)
      {
        return expectedBuilder.append(']').toString();
      }
      expectedBuilder.append(',').append(' ');
    }
  }
  @Test
  public void testuncheckedToString_RefSnglLnkNode_StringBuilder()
  {
    var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
    StringBuilder actualBuilder=new StringBuilder("[");
    RefSnglLnkNode.uncheckedToString(beginNode,actualBuilder);
    Assertions.assertEquals(buildExpectedString(),actualBuilder.append(']').toString());
  }
  @Test
  public void testuncheckedHashCode_RefSnglLnkNode()
  {
    var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
    int expectedHash=31+Objects.hashCode(beginNode.val);
    for(var currNode=beginNode.next;currNode!=null;currNode=currNode.next)
    {
      expectedHash=(expectedHash*31)+Objects.hashCode(currNode.val);
    }
    Assertions.assertEquals(expectedHash,RefSnglLnkNode.uncheckedHashCode(beginNode));
  }
  @Test
  public void testuncheckedForEach_RefSnglLnkNode_Consumer()
  {
    ArrayList<Object> consumer=new ArrayList<>();
    var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
    RefSnglLnkNode.uncheckedForEach(beginNode,consumer::add);
    var itr=consumer.iterator();
    for(;;)
    {
      Assertions.assertTrue(itr.hasNext());
      var itrVal=itr.next();
      var currVal=beginNode.val;
      Assertions.assertSame(itrVal,currVal);
      if((beginNode=beginNode.next)==null)
      {
        Assertions.assertFalse(itr.hasNext());
        return;
      }
    }
  }
  @Test
  public void testuncheckedcontainsNonNull_RefSnglLnkNode_Object()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertTrue(RefSnglLnkNode.uncheckedcontainsNonNull(beginNode,TypeConversionUtil.convertToObject(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertFalse(RefSnglLnkNode.uncheckedcontainsNonNull(beginNode,Long.valueOf(7)));
    }
  }
  @Test
  public void testuncheckedcontainsNull_RefSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new RefSnglLnkNode(null,buildAscendingSequence(5,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)))));
      Assertions.assertTrue(RefSnglLnkNode.uncheckedcontainsNull(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertFalse(RefSnglLnkNode.uncheckedcontainsNull(beginNode));
    }
  }
  @Test
  public void testuncheckedcontains_RefSnglLnkNode_Predicate()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertTrue(RefSnglLnkNode.uncheckedcontains(beginNode,TypeConversionUtil.convertToObject(7)::equals));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertFalse(RefSnglLnkNode.uncheckedcontains(beginNode,Long.valueOf(7)::equals));
    }
  }
  @Test
  public void testuncheckedsearchNonNull_RefSnglLnkNode_Object()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertEquals(4,RefSnglLnkNode.uncheckedsearchNonNull(beginNode,TypeConversionUtil.convertToObject(7)));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertEquals(-1,RefSnglLnkNode.uncheckedsearchNonNull(beginNode,Long.valueOf(7)));
    }
  }
  @Test
  public void testuncheckedsearchNull_RefSnglLnkNode()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(5,0,new RefSnglLnkNode(null,buildAscendingSequence(5,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)))));
      Assertions.assertEquals(6,RefSnglLnkNode.uncheckedsearchNull(beginNode));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertEquals(-1,RefSnglLnkNode.uncheckedsearchNull(beginNode));
    }
  }
  @Test
  public void testuncheckedsearch_RefSnglLnkNode_Predicate()
  {
    //positive return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertEquals(4,RefSnglLnkNode.uncheckedsearch(beginNode,TypeConversionUtil.convertToObject(7)::equals));
    }
    //negative return
    {
      var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
      Assertions.assertEquals(-1,RefSnglLnkNode.uncheckedsearch(beginNode,Long.valueOf(7)::equals));
    }
  }
  @Test
  public void testuncheckedCopyInto_RefSnglLnkNode_ObjectArray()
  {
     var beginNode=buildAscendingSequence(10,0,new RefSnglLnkNode(TypeConversionUtil.convertToObject(0)));
     Object[] dst=new Object[11];
     RefSnglLnkNode.uncheckedCopyInto(beginNode,dst);
     for(int i=10;i>=0;--i)
     {
       Assertions.assertEquals((Object)TypeConversionUtil.convertToObject(i),(Object)dst[10-i]);
     }
  }
}
