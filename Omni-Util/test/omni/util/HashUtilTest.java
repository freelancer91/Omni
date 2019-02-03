package omni.util;
import org.junit.Assert;
import org.junit.Test;
public class HashUtilTest{
    @Test
    public void testHashDouble(){
        Assert.assertEquals(Double.hashCode(Double.NaN),HashUtil.hashDouble(Double.NaN));
        Assert.assertEquals(Double.hashCode(0.0D),HashUtil.hashDouble(0.0D));
        Assert.assertEquals(Double.hashCode(-0.0D),HashUtil.hashDouble(-0.0D));
        Assert.assertNotEquals(HashUtil.hashDouble(-0.0D),HashUtil.hashDouble(0.0D));
        Assert.assertEquals(Double.hashCode(1.0D),HashUtil.hashDouble(1.0D));
    }
    @Test
    public void testHashFloat(){
        Assert.assertEquals(Float.hashCode(Float.NaN),HashUtil.hashFloat(Float.NaN));
        Assert.assertEquals(Float.hashCode(0.0F),HashUtil.hashFloat(0.0F));
        Assert.assertEquals(Float.hashCode(-0.0F),HashUtil.hashFloat(-0.0F));
        Assert.assertNotEquals(HashUtil.hashFloat(-0.0F),HashUtil.hashFloat(0.0F));
        Assert.assertEquals(Float.hashCode(1.0F),HashUtil.hashFloat(1.0F));
    }
}
