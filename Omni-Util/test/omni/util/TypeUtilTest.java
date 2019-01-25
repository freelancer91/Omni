package omni.util;
import org.junit.Assert;
import org.junit.Test;
public class TypeUtilTest{
  @Test public void testCastToByte(){
    Assert.assertTrue(TypeUtil.castToByte(false)==(byte)0);
    Assert.assertTrue(TypeUtil.castToByte(true)==(byte)1);
  }
  @Test public void testCastToChar(){
    Assert.assertTrue(TypeUtil.castToChar(false)==(char)0);
    Assert.assertTrue(TypeUtil.castToChar(true)==(char)1);
  }
  @Test public void testCastToDouble(){
    Assert.assertTrue(TypeUtil.castToDouble(false)==0);
    Assert.assertTrue(TypeUtil.castToDouble(true)==1);
  }
  @Test public void testCastToFloat(){
    Assert.assertTrue(TypeUtil.castToFloat(false)==0);
    Assert.assertTrue(TypeUtil.castToFloat(true)==1);
  }
  @Test public void testCastToLong(){
    Assert.assertTrue(TypeUtil.castToLong(false)==0);
    Assert.assertTrue(TypeUtil.castToLong(true)==1);
  }
  @Test public void testCheckCastToDouble(){
    Assert.assertTrue(TypeUtil.checkCastToDouble(TypeUtil.MAX_SAFE_LONG));
    Assert.assertTrue(TypeUtil.checkCastToDouble(TypeUtil.MAX_SAFE_LONG-1));
    Assert.assertTrue(TypeUtil.checkCastToDouble(TypeUtil.MIN_SAFE_LONG));
    Assert.assertTrue(TypeUtil.checkCastToDouble(TypeUtil.MIN_SAFE_LONG+1));
    Assert.assertFalse(TypeUtil.checkCastToDouble(TypeUtil.MAX_SAFE_LONG+1));
    Assert.assertFalse(TypeUtil.checkCastToDouble(TypeUtil.MIN_SAFE_LONG-1));
    Assert.assertFalse(TypeUtil.checkCastToDouble(0x7FFFFFFFFFFFFE00L));
    Assert.assertTrue(TypeUtil.checkCastToDouble(0x7FFFFFFFFFFFFC00L));
    Assert.assertTrue(TypeUtil.checkCastToDouble(0x7FFFFFFFFFFFF800L));
    Assert.assertFalse(TypeUtil.checkCastToDouble(0x8000000000000400L));
    Assert.assertTrue(TypeUtil.checkCastToDouble(0x8000000000000800L));
    Assert.assertTrue(TypeUtil.checkCastToDouble(0x8000000000001000L));
  }
}
