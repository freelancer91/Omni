package omni.util;
import org.junit.Assert;
import org.junit.Test;
public class OmniPredTest{
    @Test
    public void testGetEqualsDblBitsPred(){
        Assert.assertTrue(
                OmniPred.OfRef.getEqualsDblBitsPred(Double.doubleToRawLongBits(5.0)).test(Double.valueOf(5.0)));
    }
    @Test
    public void testGetEqualsFltBitsPred(){
        Assert.assertTrue(OmniPred.OfRef.getEqualsFltBitsPred(Float.floatToRawIntBits(5.0f)).test(Float.valueOf(5.0f)));
    }
    @Test
    public void testGetEqualsPredboolean(){
        {
            var pred=OmniPred.OfRef.getEqualsPred(true);
            Assert.assertTrue(pred.test(true));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(false);
            Assert.assertTrue(pred.test(false));
        }
    }
    @Test
    public void testGetEqualsPredBoolean(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Boolean)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Boolean.TRUE);
            Assert.assertTrue(pred.test(true));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Boolean.FALSE);
            Assert.assertTrue(pred.test(false));
        }
    }
    @Test
    public void testGetEqualsPredbyte(){
        var pred=OmniPred.OfRef.getEqualsPred((byte)0);
        Assert.assertTrue(pred.test((byte)0));
    }
    @Test
    public void testGetEqualsPredByte(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Byte)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Byte.valueOf((byte)0));
            Assert.assertTrue(pred.test((byte)0));
        }
    }
    @Test
    public void testGetEqualsPredchar(){
        var pred=OmniPred.OfRef.getEqualsPred((char)0);
        Assert.assertTrue(pred.test((char)0));
    }
    @Test
    public void testGetEqualsPredCharacter(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Character)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Character.valueOf((char)0));
            Assert.assertTrue(pred.test((char)0));
        }
    }
    @Test
    public void testGetEqualsPredshort(){
        var pred=OmniPred.OfRef.getEqualsPred((short)0);
        Assert.assertTrue(pred.test((short)0));
    }
    @Test
    public void testGetEqualsPredShort(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Short)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Short.valueOf((short)0));
            Assert.assertTrue(pred.test((short)0));
        }
    }
    @Test
    public void testGetEqualsPredint(){
        var pred=OmniPred.OfRef.getEqualsPred(0);
        Assert.assertTrue(pred.test(0));
    }
    @Test
    public void testGetEqualsPredInteger(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Integer)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Integer.valueOf(0));
            Assert.assertTrue(pred.test(0));
        }
    }
    @Test
    public void testGetEqualsPredlong(){
        var pred=OmniPred.OfRef.getEqualsPred((long)0);
        Assert.assertTrue(pred.test((long)0));
    }
    @Test
    public void testGetEqualsPredLong(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Long)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Long.valueOf(0));
            Assert.assertTrue(pred.test((long)0));
        }
    }
    @Test
    public void testGetEqualsPredfloat(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((float)0);
            Assert.assertTrue(pred.test((float)0));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Float.NaN);
            Assert.assertTrue(pred.test(Float.NaN));
        }
    }
    @Test
    public void testGetEqualsPredFloat(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Float)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Float.valueOf(0));
            Assert.assertTrue(pred.test((float)0));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Float.valueOf(Float.NaN));
            Assert.assertTrue(pred.test(Float.NaN));
        }
    }
    @Test
    public void testGetEqualsPreddouble(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((double)0);
            Assert.assertTrue(pred.test((double)0));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Double.NaN);
            Assert.assertTrue(pred.test(Double.NaN));
        }
    }
    @Test
    public void testGetEqualsPredDouble(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((Double)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Double.valueOf(0));
            Assert.assertTrue(pred.test((double)0));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(Double.valueOf(Double.NaN));
            Assert.assertTrue(pred.test(Double.NaN));
        }
    }
    @Test
    public void testGetEqualsPredObject(){
        {
            var pred=OmniPred.OfRef.getEqualsPred((String)null);
            Assert.assertTrue(pred.test(null));
        }
        {
            var pred=OmniPred.OfRef.getEqualsPred(new String("abc"));
            Assert.assertTrue(pred.test(new String("abc")));
        }
    }
}
