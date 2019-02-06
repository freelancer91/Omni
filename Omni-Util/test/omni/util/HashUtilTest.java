package omni.util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class HashUtilTest{
    @Test
    public void testHashDouble(){
        Assertions.assertEquals(Double.hashCode(Double.NaN),HashUtil.hashDouble(Double.NaN));
        Assertions.assertEquals(Double.hashCode(0.0D),HashUtil.hashDouble(0.0D));
        Assertions.assertEquals(Double.hashCode(-0.0D),HashUtil.hashDouble(-0.0D));
        Assertions.assertNotEquals(HashUtil.hashDouble(-0.0D),HashUtil.hashDouble(0.0D));
        Assertions.assertEquals(Double.hashCode(1.0D),HashUtil.hashDouble(1.0D));
    }
    @Test
    public void testHashFloat(){
        Assertions.assertEquals(Float.hashCode(Float.NaN),HashUtil.hashFloat(Float.NaN));
        Assertions.assertEquals(Float.hashCode(0.0F),HashUtil.hashFloat(0.0F));
        Assertions.assertEquals(Float.hashCode(-0.0F),HashUtil.hashFloat(-0.0F));
        Assertions.assertNotEquals(HashUtil.hashFloat(-0.0F),HashUtil.hashFloat(0.0F));
        Assertions.assertEquals(Float.hashCode(1.0F),HashUtil.hashFloat(1.0F));
    }
}
