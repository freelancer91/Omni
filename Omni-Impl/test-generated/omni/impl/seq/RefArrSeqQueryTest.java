package omni.impl.seq;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.impl.seq.RefArrSeq.UncheckedList;
import omni.impl.seq.RefArrSeq.CheckedList;
import omni.impl.seq.RefArrSeq.UncheckedStack;
import omni.impl.seq.RefArrSeq.CheckedStack;
import omni.api.QueryTestUtil;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqQueryTest
{
  //TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testUncheckedListremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new UncheckedList();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new UncheckedList();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedList();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedList();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new UncheckedList();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new UncheckedList();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new UncheckedList();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new UncheckedList();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new UncheckedList();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedListremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var seq=new UncheckedList();
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new UncheckedStack();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new UncheckedStack();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedStackremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedStackremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new UncheckedStack();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new UncheckedStack();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new UncheckedStack();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new UncheckedStack();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new UncheckedStack();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedStackremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedStackremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var seq=new UncheckedStack();
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new CheckedList();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new CheckedList();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedList();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedList();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new CheckedList();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new CheckedList();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new CheckedList();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new CheckedList();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new CheckedList();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedListremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var seq=new CheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new CheckedStack();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new CheckedStack();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedStackremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedStack();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedStack();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedStackremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new CheckedStack();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new CheckedStack();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new CheckedStack();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new CheckedStack();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new CheckedStack();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedStackremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedStackremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var seq=new CheckedStack();
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedSubListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedSubListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedSubListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedSubListremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testremoveValDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedSubListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedSubListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedSubListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testremoveValNullReturnPositive(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedSubListremoveVal()
  {
    QueryTestUtil.testEmptyremoveVal(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new UncheckedList();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new UncheckedList();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedList();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedList();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new UncheckedList();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new UncheckedList();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new UncheckedList();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new UncheckedList();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new UncheckedList();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedListcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var seq=new UncheckedList();
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new UncheckedStack();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new UncheckedStack();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedStackcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedStackcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new UncheckedStack();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new UncheckedStack();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new UncheckedStack();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new UncheckedStack();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new UncheckedStack();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedStackcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedStackcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedStackcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var seq=new UncheckedStack();
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new CheckedList();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new CheckedList();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedList();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedList();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new CheckedList();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new CheckedList();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new CheckedList();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new CheckedList();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new CheckedList();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedListcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var seq=new CheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new CheckedStack();
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var seq=new CheckedStack();
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedStackcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedStack();
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedStack();
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedStackcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new CheckedStack();
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new CheckedStack();
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new CheckedStack();
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new CheckedStack();
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new CheckedStack();
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedStackcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedStackcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedStackcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var seq=new CheckedStack();
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testCheckedSubListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testCheckedSubListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testCheckedSubListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testCheckedSubListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedSubListcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsDouble()
  {
    QueryTestUtil.testcontainsDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf((double)0));
      return seq;
    },(double)0);
    QueryTestUtil.testcontainsDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Double.valueOf(Double.NaN));
      return seq;
    },(double)Double.NaN);
  }
  @Test
  public void testUncheckedSubListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf((float)0));
      return seq;
    },(float)0);
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Float.valueOf(Float.NaN));
      return seq;
    },(float)Float.NaN);
  }
  @Test
  public void testUncheckedSubListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Long.valueOf((long)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Integer.valueOf((int)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Short.valueOf((short)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Character.valueOf((char)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Byte.valueOf((byte)0));
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.TRUE);
      return seq;
    },true);
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(Boolean.FALSE);
      return seq;
    },false);
  }
  @Test
  public void testUncheckedSubListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      return seq;
    });
    QueryTestUtil.testcontainsNullReturnPositive(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToInteger(0));
      seq.add((Integer)null);
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedSubListcontains()
  {
    QueryTestUtil.testEmptycontains(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
}
