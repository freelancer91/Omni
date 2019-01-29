package omni.util;
import org.junit.Assert;
import org.junit.Test;
public class OmniPredTest
{
  @Test
  public void testGetEqualsFltBitsPred()
  {
    Assert.assertTrue(OmniPred.OfRef.getEqualsFltBitsPred(Float.floatToRawIntBits((float)5.0)).test(Float.valueOf((float)5.0)));
  }
  @Test
  public void testGetEqualsDblBitsPred()
  {
    Assert.assertTrue(OmniPred.OfRef.getEqualsDblBitsPred(Double.doubleToRawLongBits((double)5.0)).test(Double.valueOf((double)5.0)));
  }
  @Test
  public void testGetEqualsPred()
  {
    {
      var pred=OmniPred.OfRef.getEqualsPred((Boolean)null);
      Assert.assertTrue(pred.test((Boolean)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Byte)null);
      Assert.assertTrue(pred.test((Byte)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Character)null);
      Assert.assertTrue(pred.test((Character)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Short)null);
      Assert.assertTrue(pred.test((Short)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Integer)null);
      Assert.assertTrue(pred.test((Integer)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Long)null);
      Assert.assertTrue(pred.test((Long)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Float)null);
      Assert.assertTrue(pred.test((Float)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((Double)null);
      Assert.assertTrue(pred.test((Double)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((String)null);
      Assert.assertTrue(pred.test((String)null));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(true);
      Assert.assertTrue(pred.test(true));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(false);
      Assert.assertTrue(pred.test(false));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((byte)0);
      Assert.assertTrue(pred.test((byte)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((char)0);
      Assert.assertTrue(pred.test((char)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((short)0);
      Assert.assertTrue(pred.test((short)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((int)0);
      Assert.assertTrue(pred.test((int)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((long)0);
      Assert.assertTrue(pred.test((long)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((float)0);
      Assert.assertTrue(pred.test((float)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred((double)0);
      Assert.assertTrue(pred.test((double)0));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Float.NaN);
      Assert.assertTrue(pred.test(Float.NaN));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Double.NaN);
      Assert.assertTrue(pred.test(Double.NaN));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(new String("abc"));
      Assert.assertTrue(pred.test(new String("abc")));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Boolean.TRUE);
      Assert.assertTrue(pred.test(Boolean.TRUE));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Boolean.FALSE);
      Assert.assertTrue(pred.test(Boolean.FALSE));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Byte.valueOf((byte)0));
      Assert.assertTrue(pred.test(Byte.valueOf((byte)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Character.valueOf((char)0));
      Assert.assertTrue(pred.test(Character.valueOf((char)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Short.valueOf((short)0));
      Assert.assertTrue(pred.test(Short.valueOf((short)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Integer.valueOf((int)0));
      Assert.assertTrue(pred.test(Integer.valueOf((int)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Long.valueOf((long)0));
      Assert.assertTrue(pred.test(Long.valueOf((long)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Float.valueOf((float)0));
      Assert.assertTrue(pred.test(Float.valueOf((float)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Double.valueOf((double)0));
      Assert.assertTrue(pred.test(Double.valueOf((double)0)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Float.valueOf(Float.NaN));
      Assert.assertTrue(pred.test(Float.valueOf(Float.NaN)));
    }
    {
      var pred=OmniPred.OfRef.getEqualsPred(Double.valueOf(Double.NaN));
      Assert.assertTrue(pred.test(Double.valueOf(Double.NaN)));
    }
  }
}
