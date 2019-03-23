package omni.impl.seq;
import org.junit.jupiter.api.Test;
import omni.util.TypeConversionUtil;
import omni.impl.seq.ShortArrSeq.UncheckedList;
import omni.impl.seq.ShortArrSeq.CheckedList;
import omni.impl.seq.ShortArrSeq.UncheckedStack;
import omni.impl.seq.ShortArrSeq.CheckedStack;
import omni.api.QueryTestUtil;
public class ShortArrSeqQueryTest
{
  //TODO place sanity checks for checked sequence modification behavior
  @Test
  public void testUncheckedListremoveValDouble()
  {
    QueryTestUtil.testremoveValDouble(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedStackremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedStackremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedSubListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValFloat()
  {
    QueryTestUtil.testremoveValFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValLong()
  {
    QueryTestUtil.testremoveValLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValInt()
  {
    QueryTestUtil.testremoveValInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValShort()
  {
    QueryTestUtil.testremoveValShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValChar()
  {
    QueryTestUtil.testremoveValChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValByte()
  {
    QueryTestUtil.testremoveValByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListremoveValBoolean()
  {
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testremoveValBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedSubListremoveValNull()
  {
    QueryTestUtil.testremoveValNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedStackcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedStackcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedStackcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedStackcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testCheckedSubListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testCheckedSubListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
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
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsFloat()
  {
    QueryTestUtil.testcontainsFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsLong()
  {
    QueryTestUtil.testcontainsLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsInt()
  {
    QueryTestUtil.testcontainsInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsShort()
  {
    QueryTestUtil.testcontainsShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsChar()
  {
    QueryTestUtil.testcontainsChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsByte()
  {
    QueryTestUtil.testcontainsByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    );
  }
  @Test
  public void testUncheckedSubListcontainsBoolean()
  {
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    );
    QueryTestUtil.testcontainsBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    );  
  }
  @Test
  public void testUncheckedSubListcontainsNull()
  {
    QueryTestUtil.testcontainsNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
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
  @Test
  public void testUncheckedListindexOfDouble()
  {
    QueryTestUtil.testindexOfDouble(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfFloat()
  {
    QueryTestUtil.testindexOfFloat(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfLong()
  {
    QueryTestUtil.testindexOfLong(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfInt()
  {
    QueryTestUtil.testindexOfInt(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfShort()
  {
    QueryTestUtil.testindexOfShort(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfChar()
  {
    QueryTestUtil.testindexOfChar(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfByte()
  {
    QueryTestUtil.testindexOfByte(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedListindexOfBoolean()
  {
    QueryTestUtil.testindexOfBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    ,1
    );
    QueryTestUtil.testindexOfBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    ,1
    );  
  }
  @Test
  public void testUncheckedListindexOfNull()
  {
    QueryTestUtil.testindexOfNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedListindexOf()
  {
    QueryTestUtil.testEmptyindexOf(()->
    {
      var seq=new UncheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedListindexOfDouble()
  {
    QueryTestUtil.testindexOfDouble(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfFloat()
  {
    QueryTestUtil.testindexOfFloat(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfLong()
  {
    QueryTestUtil.testindexOfLong(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfInt()
  {
    QueryTestUtil.testindexOfInt(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfShort()
  {
    QueryTestUtil.testindexOfShort(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfChar()
  {
    QueryTestUtil.testindexOfChar(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfByte()
  {
    QueryTestUtil.testindexOfByte(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedListindexOfBoolean()
  {
    QueryTestUtil.testindexOfBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    ,1
    );
    QueryTestUtil.testindexOfBoolean(()->
    {
      var seq=new CheckedList();
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    ,1
    );  
  }
  @Test
  public void testCheckedListindexOfNull()
  {
    QueryTestUtil.testindexOfNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedListindexOf()
  {
    QueryTestUtil.testEmptyindexOf(()->
    {
      var seq=new CheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedSubListindexOfDouble()
  {
    QueryTestUtil.testindexOfDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfFloat()
  {
    QueryTestUtil.testindexOfFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfLong()
  {
    QueryTestUtil.testindexOfLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfInt()
  {
    QueryTestUtil.testindexOfInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfShort()
  {
    QueryTestUtil.testindexOfShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfChar()
  {
    QueryTestUtil.testindexOfChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfByte()
  {
    QueryTestUtil.testindexOfByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testCheckedSubListindexOfBoolean()
  {
    QueryTestUtil.testindexOfBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    ,1
    );
    QueryTestUtil.testindexOfBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    ,1
    );  
  }
  @Test
  public void testCheckedSubListindexOfNull()
  {
    QueryTestUtil.testindexOfNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedSubListindexOf()
  {
    QueryTestUtil.testEmptyindexOf(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListindexOfDouble()
  {
    QueryTestUtil.testindexOfDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfFloat()
  {
    QueryTestUtil.testindexOfFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfLong()
  {
    QueryTestUtil.testindexOfLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfInt()
  {
    QueryTestUtil.testindexOfInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfShort()
  {
    QueryTestUtil.testindexOfShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfChar()
  {
    QueryTestUtil.testindexOfChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfByte()
  {
    QueryTestUtil.testindexOfByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add((short)0);
      return seq;
    }
    ,1
    );
  }
  @Test
  public void testUncheckedSubListindexOfBoolean()
  {
    QueryTestUtil.testindexOfBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(true);
      return seq;
    },true
    ,1
    );
    QueryTestUtil.testindexOfBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)3);
      seq.add(false);
      return seq;
    },false
    ,1
    );  
  }
  @Test
  public void testUncheckedSubListindexOfNull()
  {
    QueryTestUtil.testindexOfNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedSubListindexOf()
  {
    QueryTestUtil.testEmptyindexOf(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedListlastIndexOfDouble()
  {
    QueryTestUtil.testlastIndexOfDouble(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfFloat()
  {
    QueryTestUtil.testlastIndexOfFloat(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfLong()
  {
    QueryTestUtil.testlastIndexOfLong(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfInt()
  {
    QueryTestUtil.testlastIndexOfInt(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfShort()
  {
    QueryTestUtil.testlastIndexOfShort(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfChar()
  {
    QueryTestUtil.testlastIndexOfChar(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfByte()
  {
    QueryTestUtil.testlastIndexOfByte(()->
    {
      var seq=new UncheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedListlastIndexOfBoolean()
  {
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,0
    );
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var seq=new UncheckedList();
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,0
    );  
  }
  @Test
  public void testUncheckedListlastIndexOfNull()
  {
    QueryTestUtil.testlastIndexOfNullReturnNegative(()->
    {
      var seq=new UncheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedListlastIndexOf()
  {
    QueryTestUtil.testEmptylastIndexOf(()->
    {
      var seq=new UncheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedListlastIndexOfDouble()
  {
    QueryTestUtil.testlastIndexOfDouble(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfFloat()
  {
    QueryTestUtil.testlastIndexOfFloat(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfLong()
  {
    QueryTestUtil.testlastIndexOfLong(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfInt()
  {
    QueryTestUtil.testlastIndexOfInt(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfShort()
  {
    QueryTestUtil.testlastIndexOfShort(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfChar()
  {
    QueryTestUtil.testlastIndexOfChar(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfByte()
  {
    QueryTestUtil.testlastIndexOfByte(()->
    {
      var seq=new CheckedList();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedListlastIndexOfBoolean()
  {
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,0
    );
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var seq=new CheckedList();
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,0
    );  
  }
  @Test
  public void testCheckedListlastIndexOfNull()
  {
    QueryTestUtil.testlastIndexOfNullReturnNegative(()->
    {
      var seq=new CheckedList();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedListlastIndexOf()
  {
    QueryTestUtil.testEmptylastIndexOf(()->
    {
      var seq=new CheckedList();
      return seq;
    });
  }
  @Test
  public void testCheckedSubListlastIndexOfDouble()
  {
    QueryTestUtil.testlastIndexOfDouble(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfFloat()
  {
    QueryTestUtil.testlastIndexOfFloat(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfLong()
  {
    QueryTestUtil.testlastIndexOfLong(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfInt()
  {
    QueryTestUtil.testlastIndexOfInt(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfShort()
  {
    QueryTestUtil.testlastIndexOfShort(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfChar()
  {
    QueryTestUtil.testlastIndexOfChar(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfByte()
  {
    QueryTestUtil.testlastIndexOfByte(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testCheckedSubListlastIndexOfBoolean()
  {
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,0
    );
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,0
    );  
  }
  @Test
  public void testCheckedSubListlastIndexOfNull()
  {
    QueryTestUtil.testlastIndexOfNullReturnNegative(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedSubListlastIndexOf()
  {
    QueryTestUtil.testEmptylastIndexOf(()->
    {
      var root=new CheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedSubListlastIndexOfDouble()
  {
    QueryTestUtil.testlastIndexOfDouble(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfFloat()
  {
    QueryTestUtil.testlastIndexOfFloat(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfLong()
  {
    QueryTestUtil.testlastIndexOfLong(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfInt()
  {
    QueryTestUtil.testlastIndexOfInt(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfShort()
  {
    QueryTestUtil.testlastIndexOfShort(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfChar()
  {
    QueryTestUtil.testlastIndexOfChar(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfByte()
  {
    QueryTestUtil.testlastIndexOfByte(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,0
    );
  }
  @Test
  public void testUncheckedSubListlastIndexOfBoolean()
  {
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,0
    );
    QueryTestUtil.testlastIndexOfBoolean(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,0
    );  
  }
  @Test
  public void testUncheckedSubListlastIndexOfNull()
  {
    QueryTestUtil.testlastIndexOfNullReturnNegative(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedSubListlastIndexOf()
  {
    QueryTestUtil.testEmptylastIndexOf(()->
    {
      var root=new UncheckedList();
      var subList=root.subList(0,0);
      var seq=subList.subList(0,0);
      return seq;
    });
  }
  @Test
  public void testUncheckedStacksearchDouble()
  {
    QueryTestUtil.testsearchDouble(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchFloat()
  {
    QueryTestUtil.testsearchFloat(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchLong()
  {
    QueryTestUtil.testsearchLong(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchInt()
  {
    QueryTestUtil.testsearchInt(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchShort()
  {
    QueryTestUtil.testsearchShort(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchChar()
  {
    QueryTestUtil.testsearchChar(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchByte()
  {
    QueryTestUtil.testsearchByte(()->
    {
      var seq=new UncheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testUncheckedStacksearchBoolean()
  {
    QueryTestUtil.testsearchBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,2
    );
    QueryTestUtil.testsearchBoolean(()->
    {
      var seq=new UncheckedStack();
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,2
    );  
  }
  @Test
  public void testUncheckedStacksearchNull()
  {
    QueryTestUtil.testsearchNullReturnNegative(()->
    {
      var seq=new UncheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyUncheckedStacksearch()
  {
    QueryTestUtil.testEmptysearch(()->
    {
      var seq=new UncheckedStack();
      return seq;
    });
  }
  @Test
  public void testCheckedStacksearchDouble()
  {
    QueryTestUtil.testsearchDouble(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchFloat()
  {
    QueryTestUtil.testsearchFloat(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchLong()
  {
    QueryTestUtil.testsearchLong(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchInt()
  {
    QueryTestUtil.testsearchInt(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchShort()
  {
    QueryTestUtil.testsearchShort(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchChar()
  {
    QueryTestUtil.testsearchChar(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchByte()
  {
    QueryTestUtil.testsearchByte(()->
    {
      var seq=new CheckedStack();
      seq.add((short)0);
      seq.add((short)3);
      return seq;
    }
    ,2
    );
  }
  @Test
  public void testCheckedStacksearchBoolean()
  {
    QueryTestUtil.testsearchBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(true);
      seq.add((short)3);
      return seq;
    },true
    ,2
    );
    QueryTestUtil.testsearchBoolean(()->
    {
      var seq=new CheckedStack();
      seq.add(false);
      seq.add((short)3);
      return seq;
    },false
    ,2
    );  
  }
  @Test
  public void testCheckedStacksearchNull()
  {
    QueryTestUtil.testsearchNullReturnNegative(()->
    {
      var seq=new CheckedStack();
      seq.add(TypeConversionUtil.convertToshort(0));
      return seq;
    });
  }
  @Test
  public void testEmptyCheckedStacksearch()
  {
    QueryTestUtil.testEmptysearch(()->
    {
      var seq=new CheckedStack();
      return seq;
    });
  }
}
