package omni.impl;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniList;
import omni.api.OmniStack;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeUtil;
import java.util.function.Supplier;
public final class QueryTestUtil
{
  private QueryTestUtil()
  {
    super();
  }
  public static void testcontainsDouble(Supplier<? extends OmniCollection> collectionSupplier,double containsVal
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.contains((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)0.0));
    }
  }
  public static void testcontainsDouble(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)Double.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)0.0));
    }
  }
  public static void testcontainsDouble(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(false,col.contains((double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testcontainsDouble(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((double)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Double)(double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Double)(double)1.0));
    }
  }
  public static void testremoveValDouble(Supplier<? extends OmniCollection> collectionSupplier,double containsVal
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeVal((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().remove((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().remove((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    }
  }
  public static void testremoveValDouble(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)Double.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    }
  }
  public static void testremoveValDouble(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(false,col.removeVal((double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testremoveValDouble(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)(double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)(double)1.0));
    }
  }
  public static void testcontainsFloat(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)Float.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    }
  }
  public static void testcontainsFloat(Supplier<? extends OmniCollection> collectionSupplier,float containsVal
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.contains((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    }
  }
  public static void testcontainsFloat(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.contains((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.contains((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
  }
  public static void testcontainsFloat(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((float)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().contains((Float)(float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Float)(float)1.0));
    }
  }
  public static void testremoveValFloat(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)Float.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    }
  }
  public static void testremoveValFloat(Supplier<? extends OmniCollection> collectionSupplier,float containsVal
  )
  {
    OmniCollection col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeVal((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().remove((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().remove((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    }
  }
  public static void testremoveValFloat(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.removeVal((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeVal((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
  }
  public static void testremoveValFloat(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)(float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)(float)1.0));
    }
  }
  public static void testcontainsLong(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)0)));
    }
  }
  public static void testcontainsLong(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf((long)0)));
    }
  }
  public static void testcontainsLong(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.contains((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)0));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(true,col.contains((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((long)0));
  }
  public static void testcontainsLong(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testremoveValLong(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveValLong(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveValLong(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.remove((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)0));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(true,col.remove((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)0));
  }
  public static void testremoveValLong(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testcontainsInt(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testcontainsInt(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testcontainsInt(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.contains((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.contains((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((int)0));
  }
  public static void testcontainsInt(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValInt(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveValInt(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveValInt(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.remove((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.remove((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)0));
  }
  public static void testremoveValInt(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsShort(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    }
  }
  public static void testcontainsShort(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    }
  }
  public static void testcontainsShort(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.contains((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.contains((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.contains((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((short)0));
  }
  public static void testcontainsShort(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValShort(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveValShort(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveValShort(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.remove((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.remove((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.remove((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)0));
  }
  public static void testremoveValShort(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsChar(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    }
  }
  public static void testcontainsChar(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    }
  }
  public static void testcontainsChar(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.contains((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().contains((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.contains((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.contains((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)0));
  }
  public static void testcontainsChar(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testremoveValChar(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveValChar(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveValChar(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.remove((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.remove((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.remove((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)0));
  }
  public static void testremoveValChar(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testcontainsByte(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testcontainsByte(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().contains((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().contains(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testcontainsByte(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.contains((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.contains((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.contains((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().contains((byte)0));
  }
  public static void testcontainsByte(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveValByte(Supplier<? extends OmniCollection.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveValByte(Supplier<? extends OmniCollection.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeVal(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveValByte(Supplier<? extends OmniCollection> collectionSupplier
  )
  {
    OmniCollection col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.remove((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.remove((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.remove((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)0));
  }
  public static void testremoveValByte(Supplier<? extends OmniCollection.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testcontainsBooleanReturnPositive(Supplier<? extends OmniCollection> collectionSupplier,boolean containsVal
  )
  {
    OmniCollection collection;
    if((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef)
    {
      Assertions.assertEquals(true,collection.contains((Object)containsVal));
    }
    Assertions.assertEquals(true,collectionSupplier.get().contains(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Boolean)containsVal));
  }
  public static void testremoveValBooleanReturnPositive(Supplier<? extends OmniCollection> collectionSupplier,boolean containsVal
  )
  {
    OmniCollection collection;
    if((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef)
    {
      Assertions.assertEquals(true,collection.remove((Object)containsVal));
    }
    Assertions.assertEquals(true,collectionSupplier.get().removeVal(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Boolean)containsVal));
  }
  public static void testcontainsBooleanReturnNegative(Supplier<? extends OmniCollection> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)!containsVal));
    OmniCollection collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef))
    {
      Assertions.assertEquals(false,collection.contains((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().contains(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)!containsVal));
  }
  public static void testremoveValBooleanReturnNegative(Supplier<? extends OmniCollection> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)!containsVal));
    OmniCollection collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniCollection.OfBoolean || collection instanceof OmniCollection.OfRef))
    {
      Assertions.assertEquals(false,collection.remove((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)!containsVal));
  }
  public static <E> void testcontainsNullReturnPositive(Supplier<? extends OmniCollection.OfRef<E>> collectionSupplier
  )
  {
    Assertions.assertEquals(true,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().contains((Double)null));
  }
  public static <E> void testremoveValNullReturnPositive(Supplier<? extends OmniCollection.OfRef<E>> collectionSupplier
  )
  {
    Assertions.assertEquals(true,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeVal((Double)null));
  }
  public static void testcontainsNullReturnNegative(Supplier<? extends OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)null));
  }
  public static void testremoveValNullReturnNegative(Supplier<? extends OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)null));
  }
  public static void testEmptycontains(Supplier<? extends OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(true));
    Assertions.assertEquals(false,collectionSupplier.get().contains(false));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(0));
    Assertions.assertEquals(false,collectionSupplier.get().contains(1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().contains(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().contains((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().contains(Double.NaN));
  }
  public static void testEmptyremoveVal(Supplier<? extends OmniCollection> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().remove((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(true));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(false));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeVal(Double.NaN));
  }
  public static void testremoveFirstOccurrenceDouble(Supplier<? extends OmniDeque> collectionSupplier,double containsVal
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeFirstOccurrence((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)0.0));
    }
  }
  public static void testremoveFirstOccurrenceDouble(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)Double.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)0.0));
    }
  }
  public static void testremoveFirstOccurrenceDouble(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(false,col.removeFirstOccurrence((double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testremoveFirstOccurrenceDouble(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)(double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)(double)1.0));
    }
  }
  public static void testremoveLastOccurrenceDouble(Supplier<? extends OmniDeque> collectionSupplier,double containsVal
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeLastOccurrence((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)0.0));
    }
  }
  public static void testremoveLastOccurrenceDouble(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(1.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Double.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)Double.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)0.0));
    }
  }
  public static void testremoveLastOccurrenceDouble(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(false,col.removeLastOccurrence((double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testremoveLastOccurrenceDouble(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)(double)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)(double)1.0));
    }
  }
  public static void testremoveFirstOccurrenceFloat(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)Float.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)0.0));
    }
  }
  public static void testremoveFirstOccurrenceFloat(Supplier<? extends OmniDeque> collectionSupplier,float containsVal
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeFirstOccurrence((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)0.0));
    }
  }
  public static void testremoveFirstOccurrenceFloat(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeFirstOccurrence((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-0.0));
  }
  public static void testremoveFirstOccurrenceFloat(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)(float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)(float)1.0));
    }
  }
  public static void testremoveLastOccurrenceFloat(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)Float.NaN));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)0.0));
    }
  }
  public static void testremoveLastOccurrenceFloat(Supplier<? extends OmniDeque> collectionSupplier,float containsVal
  )
  {
    OmniDeque col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(false,col.removeLastOccurrence((float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)Float.NaN));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Object)(float)containsVal));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)0.0));
    }
  }
  public static void testremoveLastOccurrenceFloat(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeLastOccurrence((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)1.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)0.0));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)1.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-0.0));
  }
  public static void testremoveLastOccurrenceFloat(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)2.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)1.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)-0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)1.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)0.0));
      Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)(float)-0.0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)(float)1.0));
    }
  }
  public static void testremoveFirstOccurrenceLong(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveFirstOccurrenceLong(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveFirstOccurrenceLong(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)0));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(true,col.removeFirstOccurrence((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)0));
  }
  public static void testremoveFirstOccurrenceLong(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testremoveLastOccurrenceLong(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveLastOccurrenceLong(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf((long)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf((long)0)));
    }
  }
  public static void testremoveLastOccurrenceLong(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)0));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Long.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(true,col.removeLastOccurrence((Object)(long)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Long)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)0));
  }
  public static void testremoveLastOccurrenceLong(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testremoveFirstOccurrenceInt(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveFirstOccurrenceInt(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveFirstOccurrenceInt(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.removeFirstOccurrence((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)0));
  }
  public static void testremoveFirstOccurrenceInt(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveLastOccurrenceInt(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveLastOccurrenceInt(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf((int)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testremoveLastOccurrenceInt(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Integer.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(true,col.removeLastOccurrence((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(int)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Integer)(int)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)0));
  }
  public static void testremoveLastOccurrenceInt(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveFirstOccurrenceShort(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveFirstOccurrenceShort(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveFirstOccurrenceShort(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.removeFirstOccurrence((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)0));
  }
  public static void testremoveFirstOccurrenceShort(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveLastOccurrenceShort(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveLastOccurrenceShort(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)0)));
    }
  }
  public static void testremoveLastOccurrenceShort(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)Short.MIN_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(true,col.removeLastOccurrence((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(short)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Short)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)0));
  }
  public static void testremoveLastOccurrenceShort(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveFirstOccurrenceChar(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveFirstOccurrenceChar(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveFirstOccurrenceChar(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.removeFirstOccurrence((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)0));
  }
  public static void testremoveFirstOccurrenceChar(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testremoveLastOccurrenceChar(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveLastOccurrenceChar(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)0)));
    }
  }
  public static void testremoveLastOccurrenceChar(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(true,col.removeLastOccurrence((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(char)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Character)(char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Character)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)0));
  }
  public static void testremoveLastOccurrenceChar(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testremoveFirstOccurrenceByte(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveFirstOccurrenceByte(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveFirstOccurrenceByte(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.removeFirstOccurrence((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeFirstOccurrence((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)0));
  }
  public static void testremoveFirstOccurrenceByte(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveLastOccurrenceByte(Supplier<? extends OmniDeque.OfDouble> collectionSupplier,double containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveLastOccurrenceByte(Supplier<? extends OmniDeque.OfFloat> collectionSupplier,float containsVal
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)1));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)1)));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)containsVal));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)0));
        Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)0)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testremoveLastOccurrenceByte(Supplier<? extends OmniDeque> collectionSupplier
  )
  {
    OmniDeque col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)(byte)-1));
      Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(true,col.removeLastOccurrence((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(false,col.removeLastOccurrence((Object)(byte)0));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)(byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Byte)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)1));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)0));
  }
  public static void testremoveLastOccurrenceByte(Supplier<? extends OmniDeque.OfBoolean> collectionSupplier,boolean containsVal
  )
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testremoveFirstOccurrenceBooleanReturnPositive(Supplier<? extends OmniDeque> collectionSupplier,boolean containsVal
  )
  {
    OmniDeque collection;
    if((collection=collectionSupplier.get()) instanceof OmniDeque.OfBoolean || collection instanceof OmniDeque.OfRef)
    {
      Assertions.assertEquals(true,collection.removeFirstOccurrence((Object)containsVal));
    }
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Boolean)containsVal));
  }
  public static void testremoveLastOccurrenceBooleanReturnPositive(Supplier<? extends OmniDeque> collectionSupplier,boolean containsVal
  )
  {
    OmniDeque collection;
    if((collection=collectionSupplier.get()) instanceof OmniDeque.OfBoolean || collection instanceof OmniDeque.OfRef)
    {
      Assertions.assertEquals(true,collection.removeLastOccurrence((Object)containsVal));
    }
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence(containsVal));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Boolean)containsVal));
  }
  public static void testremoveFirstOccurrenceBooleanReturnNegative(Supplier<? extends OmniDeque> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)!containsVal));
    OmniDeque collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniDeque.OfBoolean || collection instanceof OmniDeque.OfRef))
    {
      Assertions.assertEquals(false,collection.removeFirstOccurrence((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Boolean)!containsVal));
  }
  public static void testremoveLastOccurrenceBooleanReturnNegative(Supplier<? extends OmniDeque> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)!containsVal));
    OmniDeque collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniDeque.OfBoolean || collection instanceof OmniDeque.OfRef))
    {
      Assertions.assertEquals(false,collection.removeLastOccurrence((Object)containsVal));
    }
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(!containsVal));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Boolean)!containsVal));
  }
  public static <E> void testremoveFirstOccurrenceNullReturnPositive(Supplier<? extends OmniDeque.OfRef<E>> collectionSupplier
  )
  {
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeFirstOccurrence((Double)null));
  }
  public static <E> void testremoveLastOccurrenceNullReturnPositive(Supplier<? extends OmniDeque.OfRef<E>> collectionSupplier
  )
  {
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Object)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Boolean)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Byte)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Character)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Short)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Integer)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Long)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Float)null));
    Assertions.assertEquals(true,collectionSupplier.get().removeLastOccurrence((Double)null));
  }
  public static void testremoveFirstOccurrenceNullReturnNegative(Supplier<? extends OmniDeque> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)null));
  }
  public static void testremoveLastOccurrenceNullReturnNegative(Supplier<? extends OmniDeque> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)null));
  }
  public static void testEmptyremoveFirstOccurrence(Supplier<? extends OmniDeque> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(true));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(false));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeFirstOccurrence(Double.NaN));
  }
  public static void testEmptyremoveLastOccurrence(Supplier<? extends OmniDeque> collectionSupplier)
  {
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)true));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)false));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Object)Double.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Boolean)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Byte)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Character)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Short)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Integer)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Long)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Float)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((Double)null));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Boolean.TRUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Boolean.FALSE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf((byte)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf((short)2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf((float)0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(-2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(-1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(-0.0)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(2)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(true));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(false));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((byte)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((char)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((short)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((int)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((long)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Float.NaN));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Long.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Integer.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Short.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Byte.MIN_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)-2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)-1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(-0.0));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)1));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)2));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Byte.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Short.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Character.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Integer.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)Long.MAX_VALUE));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(false,collectionSupplier.get().removeLastOccurrence(Double.NaN));
  }
  public static void testindexOfDouble(Supplier<? extends OmniList> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.indexOf((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)0.0));
    }
  }
  public static void testindexOfDouble(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(1.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)Double.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)0.0));
    }
  }
  public static void testindexOfDouble(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(-1,col.indexOf((double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testindexOfDouble(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)(double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)(double)1.0));
    }
  }
  public static void testlastIndexOfDouble(Supplier<? extends OmniList> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.lastIndexOf((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)0.0));
    }
  }
  public static void testlastIndexOfDouble(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(1.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)Double.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)0.0));
    }
  }
  public static void testlastIndexOfDouble(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(-1,col.lastIndexOf((double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testlastIndexOfDouble(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)(double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)(double)1.0));
    }
  }
  public static void testindexOfFloat(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)Float.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)0.0));
    }
  }
  public static void testindexOfFloat(Supplier<? extends OmniList> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.indexOf((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)0.0));
    }
  }
  public static void testindexOfFloat(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.indexOf((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.indexOf((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-0.0));
  }
  public static void testindexOfFloat(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)(float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)(float)1.0));
    }
  }
  public static void testlastIndexOfFloat(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)Float.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)0.0));
    }
  }
  public static void testlastIndexOfFloat(Supplier<? extends OmniList> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    OmniList col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.lastIndexOf((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)0.0));
    }
  }
  public static void testlastIndexOfFloat(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.lastIndexOf((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-0.0));
  }
  public static void testlastIndexOfFloat(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)(float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)(float)1.0));
    }
  }
  public static void testindexOfLong(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)0)));
    }
  }
  public static void testindexOfLong(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf((long)0)));
    }
  }
  public static void testindexOfLong(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)0));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(expectedIndex,col.indexOf((Object)(long)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Long)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)0));
  }
  public static void testindexOfLong(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testlastIndexOfLong(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)0)));
    }
  }
  public static void testlastIndexOfLong(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf((long)0)));
    }
  }
  public static void testlastIndexOfLong(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)0));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(expectedIndex,col.lastIndexOf((Object)(long)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Long)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)0));
  }
  public static void testlastIndexOfLong(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testindexOfInt(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testindexOfInt(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testindexOfInt(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(expectedIndex,col.indexOf((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.indexOf((Object)(int)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Integer)(int)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)0));
  }
  public static void testindexOfInt(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testlastIndexOfInt(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testlastIndexOfInt(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testlastIndexOfInt(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(expectedIndex,col.lastIndexOf((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(int)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Integer)(int)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)0));
  }
  public static void testlastIndexOfInt(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testindexOfShort(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)0)));
    }
  }
  public static void testindexOfShort(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)0)));
    }
  }
  public static void testindexOfShort(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(expectedIndex,col.indexOf((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.indexOf((Object)(short)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Short)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)0));
  }
  public static void testindexOfShort(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testlastIndexOfShort(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)0)));
    }
  }
  public static void testlastIndexOfShort(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)0)));
    }
  }
  public static void testlastIndexOfShort(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(expectedIndex,col.lastIndexOf((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(short)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Short)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)0));
  }
  public static void testlastIndexOfShort(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testindexOfChar(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)0)));
    }
  }
  public static void testindexOfChar(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)0)));
    }
  }
  public static void testindexOfChar(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(expectedIndex,col.indexOf((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.indexOf((Object)(char)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Character)(char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Character)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)0));
  }
  public static void testindexOfChar(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testlastIndexOfChar(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)0)));
    }
  }
  public static void testlastIndexOfChar(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)0)));
    }
  }
  public static void testlastIndexOfChar(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(expectedIndex,col.lastIndexOf((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(char)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Character)(char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Character)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)0));
  }
  public static void testlastIndexOfChar(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testindexOfByte(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testindexOfByte(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testindexOfByte(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.indexOf((Object)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(expectedIndex,col.indexOf((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.indexOf((Object)(byte)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)(byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Byte)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)0));
  }
  public static void testindexOfByte(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testlastIndexOfByte(Supplier<? extends OmniList.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testlastIndexOfByte(Supplier<? extends OmniList.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testlastIndexOfByte(Supplier<? extends OmniList> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniList col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(expectedIndex,col.lastIndexOf((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.lastIndexOf((Object)(byte)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)(byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Byte)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)0));
  }
  public static void testlastIndexOfByte(Supplier<? extends OmniList.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testindexOfBooleanReturnPositive(Supplier<? extends OmniList> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    OmniList collection;
    if((collection=collectionSupplier.get()) instanceof OmniList.OfBoolean || collection instanceof OmniList.OfRef)
    {
      Assertions.assertEquals(expectedIndex,collection.indexOf((Object)containsVal));
    }
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf(containsVal));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Boolean)containsVal));
  }
  public static void testlastIndexOfBooleanReturnPositive(Supplier<? extends OmniList> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    OmniList collection;
    if((collection=collectionSupplier.get()) instanceof OmniList.OfBoolean || collection instanceof OmniList.OfRef)
    {
      Assertions.assertEquals(expectedIndex,collection.lastIndexOf((Object)containsVal));
    }
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf(containsVal));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Boolean)containsVal));
  }
  public static void testindexOfBooleanReturnNegative(Supplier<? extends OmniList> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)!containsVal));
    OmniList collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniList.OfBoolean || collection instanceof OmniList.OfRef))
    {
      Assertions.assertEquals(-1,collection.indexOf((Object)containsVal));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(!containsVal));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Boolean)!containsVal));
  }
  public static void testlastIndexOfBooleanReturnNegative(Supplier<? extends OmniList> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)!containsVal));
    OmniList collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniList.OfBoolean || collection instanceof OmniList.OfRef))
    {
      Assertions.assertEquals(-1,collection.lastIndexOf((Object)containsVal));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(!containsVal));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Boolean)!containsVal));
  }
  public static <E> void testindexOfNullReturnPositive(Supplier<? extends OmniList.OfRef<E>> collectionSupplier
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Object)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Boolean)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Byte)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Character)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Short)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Integer)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Long)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Float)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().indexOf((Double)null));
  }
  public static <E> void testlastIndexOfNullReturnPositive(Supplier<? extends OmniList.OfRef<E>> collectionSupplier
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Object)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Boolean)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Byte)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Character)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Short)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Integer)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Long)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Float)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().lastIndexOf((Double)null));
  }
  public static void testindexOfNullReturnNegative(Supplier<? extends OmniList> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)null));
  }
  public static void testlastIndexOfNullReturnNegative(Supplier<? extends OmniList> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)null));
  }
  public static void testEmptyindexOf(Supplier<? extends OmniList> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)true));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)false));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Object)Double.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((Double)null));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(true));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(false));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().indexOf(Double.NaN));
  }
  public static void testEmptylastIndexOf(Supplier<? extends OmniList> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)true));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)false));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Object)Double.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((Double)null));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(true));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(false));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().lastIndexOf(Double.NaN));
  }
  public static void testsearchDouble(Supplier<? extends OmniStack> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    OmniStack col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfDouble || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.search((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)0.0));
    }
  }
  public static void testsearchDouble(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(1.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)Double.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Double.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Double.MIN_VALUE));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)Double.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)Double.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Double.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)0.0));
    }
  }
  public static void testsearchDouble(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
    Assertions.assertEquals(-1,col.search((double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)(TypeUtil.MIN_SAFE_LONG-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MAX_SAFE_LONG+1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MIN_SAFE_LONG-1)));
  }
  public static void testsearchDouble(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((double)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)(double)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Double)(double)1.0));
    }
  }
  public static void testsearchFloat(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)Float.NaN));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)0.0));
    }
  }
  public static void testsearchFloat(Supplier<? extends OmniStack> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    OmniStack col;
    Assertions.assertTrue((col=collectionSupplier.get()) instanceof OmniCollection.OfFloat || col instanceof OmniCollection.OfRef);
    Assertions.assertEquals(-1,col.search((float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-0.0));
    if(containsVal==containsVal)
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)Float.NaN));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Float.NaN));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Object)(float)containsVal));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)0.0));
    }
  }
  public static void testsearchFloat(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte||col instanceof OmniCollection.OfChar||col instanceof OmniCollection.OfShort||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.search((float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)(TypeUtil.MIN_SAFE_INT-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MAX_SAFE_INT+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MIN_SAFE_INT-1)));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.search((float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)(TypeUtil.MIN_SAFE_LONG-1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MAX_SAFE_LONG+1)));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MIN_SAFE_LONG-1)));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)1.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)0.0));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)1.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-0.0));
  }
  public static void testsearchFloat(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)2.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)1.0));
    if(containsVal)
    {
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)1.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)-0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)1.0));
    }
    else
    {
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((float)1.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)0.0));
      Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)(float)-0.0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Float)(float)1.0));
    }
  }
  public static void testsearchLong(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)0)));
    }
  }
  public static void testsearchLong(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((long)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf((long)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((long)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf((long)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf((long)0)));
    }
  }
  public static void testsearchLong(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar || col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(-1,col.search((Object)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)0));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((long)Long.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)Long.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((long)Long.MAX_VALUE));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong || col instanceof OmniCollection.OfRef);
      Assertions.assertEquals(expectedIndex,col.search((Object)(long)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Long)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)0));
  }
  public static void testsearchLong(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((long)TypeUtil.castToLong(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)(long)TypeUtil.castToLong(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Long)(long)TypeUtil.castToLong(containsVal)));
  }
  public static void testsearchInt(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testsearchInt(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)TypeUtil.MIN_SAFE_INT-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)TypeUtil.MAX_SAFE_INT+1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)TypeUtil.MIN_SAFE_INT-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)TypeUtil.MAX_SAFE_INT+1)));
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((int)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf((int)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((int)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf((int)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf((int)0)));
    }
  }
  public static void testsearchInt(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfByte || col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.search((Object)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((int)Integer.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)Integer.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((int)Integer.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfInt)
    {
      Assertions.assertEquals(expectedIndex,col.search((Object)(int)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.search((Object)(int)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Integer)(int)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)0));
  }
  public static void testsearchInt(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((int)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)(int)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Integer)(int)TypeUtil.castToByte(containsVal)));
  }
  public static void testsearchShort(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)0)));
    }
  }
  public static void testsearchShort(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((short)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((short)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)0)));
    }
  }
  public static void testsearchShort(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.search((Object)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().search((short)-1));
    }
    else if(col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.search((Object)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((short)Short.MIN_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)Short.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((short)Short.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfShort)
    {
      Assertions.assertEquals(expectedIndex,col.search((Object)(short)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.search((Object)(short)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Short)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)0));
  }
  public static void testsearchShort(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((short)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Short)(short)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Short)(short)TypeUtil.castToByte(containsVal)));
  }
  public static void testsearchChar(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)0)));
    }
  }
  public static void testsearchChar(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((char)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((char)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)0)));
    }
  }
  public static void testsearchChar(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(-1,col.search((Object)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Character)(char)Character.MAX_VALUE));
      Assertions.assertEquals(-1,collectionSupplier.get().search((char)Character.MAX_VALUE));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(expectedIndex,col.search((Object)(char)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.search((Object)(char)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Character)(char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Character)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)0));
  }
  public static void testsearchChar(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)TypeUtil.castToChar(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)(char)TypeUtil.castToChar(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((char)(char)TypeUtil.castToChar(containsVal)));
  }
  public static void testsearchByte(Supplier<? extends OmniStack.OfDouble> collectionSupplier,double containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testsearchByte(Supplier<? extends OmniStack.OfFloat> collectionSupplier,float containsVal
  ,int expectedIndex
  )
  {
    if(containsVal == 0){
        Assertions.assertEquals(-1,collectionSupplier.get().search((byte)1));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)1)));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)1)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)containsVal)));
    }else{
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)containsVal));
        Assertions.assertEquals(-1,collectionSupplier.get().search((byte)0));
        Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)0)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)containsVal)));
        Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)0)));
    }
  }
  public static void testsearchByte(Supplier<? extends OmniStack> collectionSupplier
  ,int expectedIndex
  )
  {
    OmniStack col;
    if((col=collectionSupplier.get()) instanceof OmniCollection.OfChar)
    {
      Assertions.assertEquals(-1,col.search((Object)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)(byte)-1));
      Assertions.assertEquals(-1,collectionSupplier.get().search((byte)-1));
    }
    else if(col instanceof OmniCollection.OfRef||col instanceof OmniCollection.OfByte)
    {
      Assertions.assertEquals(expectedIndex,col.search((Object)(byte)0));
    }
    else
    {
      Assertions.assertTrue(col instanceof OmniCollection.OfShort || col instanceof OmniCollection.OfInt || col instanceof OmniCollection.OfLong);
      Assertions.assertEquals(-1,col.search((Object)(byte)0));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)(byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Byte)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)1));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)0));
  }
  public static void testsearchByte(Supplier<? extends OmniStack.OfBoolean> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((byte)TypeUtil.castToByte(containsVal)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)(byte)TypeUtil.castToByte(!containsVal)));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Byte)(byte)TypeUtil.castToByte(containsVal)));
  }
  public static void testsearchBooleanReturnPositive(Supplier<? extends OmniStack> collectionSupplier,boolean containsVal
  ,int expectedIndex
  )
  {
    OmniStack collection;
    if((collection=collectionSupplier.get()) instanceof OmniStack.OfBoolean || collection instanceof OmniStack.OfRef)
    {
      Assertions.assertEquals(expectedIndex,collection.search((Object)containsVal));
    }
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search(containsVal));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Boolean)containsVal));
  }
  public static void testsearchBooleanReturnNegative(Supplier<? extends OmniStack> collectionSupplier,boolean containsVal)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)!containsVal));
    OmniStack collection;
    if(!((collection=collectionSupplier.get()) instanceof OmniStack.OfBoolean || collection instanceof OmniStack.OfRef))
    {
      Assertions.assertEquals(-1,collection.search((Object)containsVal));
    }
    Assertions.assertEquals(-1,collectionSupplier.get().search(!containsVal));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Boolean)!containsVal));
  }
  public static <E> void testsearchNullReturnPositive(Supplier<? extends OmniStack.OfRef<E>> collectionSupplier
  ,int expectedIndex
  )
  {
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Object)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Boolean)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Byte)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Character)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Short)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Integer)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Long)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Float)null));
    Assertions.assertEquals(expectedIndex,collectionSupplier.get().search((Double)null));
  }
  public static void testsearchNullReturnNegative(Supplier<? extends OmniStack> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)null));
  }
  public static void testEmptysearch(Supplier<? extends OmniStack> collectionSupplier)
  {
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)true));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)false));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)(double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Object)Double.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Boolean)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Byte)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Character)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Short)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Integer)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Long)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Float)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search((Double)null));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Boolean.TRUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Boolean.FALSE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf((byte)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf((char)Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf((short)2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Float.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf((float)0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf((float)-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Float.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.valueOf(Float.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Double.NEGATIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MIN_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MIN_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Long.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Integer.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Short.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Byte.MIN_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(-2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(-1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(-0.0)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(2)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Byte.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Short.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Character.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Integer.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Long.MAX_VALUE)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MAX_SAFE_INT)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MAX_SAFE_LONG)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Double.POSITIVE_INFINITY)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.valueOf(Double.NaN)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(true));
    Assertions.assertEquals(-1,collectionSupplier.get().search(false));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((byte)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((char)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((short)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search(-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search(0));
    Assertions.assertEquals(-1,collectionSupplier.get().search(1));
    Assertions.assertEquals(-1,collectionSupplier.get().search(2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((int)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((long)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((float)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Float.NaN));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.NEGATIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MIN_SAFE_LONG - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)TypeUtil.MIN_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MIN_SAFE_INT - 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)TypeUtil.MIN_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Long.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Integer.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Short.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Byte.MIN_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)-2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)-1));
    Assertions.assertEquals(-1,collectionSupplier.get().search(0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search(-0.0));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)1));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)2));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Byte.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Short.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Character.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Integer.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)Long.MAX_VALUE));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)TypeUtil.MAX_SAFE_INT));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MAX_SAFE_INT + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)TypeUtil.MAX_SAFE_LONG));
    Assertions.assertEquals(-1,collectionSupplier.get().search((double)(TypeUtil.MAX_SAFE_LONG + 1)));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.POSITIVE_INFINITY));
    Assertions.assertEquals(-1,collectionSupplier.get().search(Double.NaN));
  }
}
//TODO add sanity checks (mod checks, empty/not empty) to the tests
