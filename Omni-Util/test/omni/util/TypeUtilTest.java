package omni.util;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import org.junit.Assert;
import org.junit.Test;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
public class TypeUtilTest{
    @Test
    public void testCastToByte(){
        Assert.assertTrue(TypeUtil.castToByte(false)==(byte)0);
        Assert.assertTrue(TypeUtil.castToByte(true)==(byte)1);
    }
    @Test
    public void testCastToChar(){
        Assert.assertTrue(TypeUtil.castToChar(false)==(char)0);
        Assert.assertTrue(TypeUtil.castToChar(true)==(char)1);
    }
    @Test
    public void testCastToDouble(){
        Assert.assertTrue(TypeUtil.castToDouble(false)==0);
        Assert.assertTrue(TypeUtil.castToDouble(true)==1);
    }
    @Test
    public void testCastToFloat(){
        Assert.assertTrue(TypeUtil.castToFloat(false)==0);
        Assert.assertTrue(TypeUtil.castToFloat(true)==1);
    }
    @Test
    public void testCastToLong(){
        Assert.assertTrue(TypeUtil.castToLong(false)==0);
        Assert.assertTrue(TypeUtil.castToLong(true)==1);
    }
    @Test
    public void checkCastToFloatlong(){
        for(long mask=1L;mask!=0L;mask<<=1){
            testCheckCastToFloatHelper(0x7fffffffffffffffL^mask-1);
            testCheckCastToFloatHelper(0x8000000000000000L|mask);
        }
    }
    @Test
    public void checkCastToFloatint(){
        for(int mask=1;mask!=0;mask<<=1){
            testCheckCastToFloatHelper(0x7fffffff^mask-1);
            testCheckCastToFloatHelper(0x80000000|mask);
        }
    }
    private static void testCheckCastToFloatHelper(int val){
        for(int i=val-100;i<=val+100;++i){
            float f=i;
            long convertedBack=(long)f;
            boolean expected=i==convertedBack;
            boolean actual=TypeUtil.checkCastToFloat(i);
            Assert.assertTrue("val = "+val+"; i = "+i+"; f = "+f+" expected = "+expected+"; actual = "+actual,
                    expected==actual);
        }
    }
    private static void testCheckCastToFloatHelper(long val){
        for(long l=val-100;l<=val+100;++l){
            float f=l;
            long convertedBack=(long)f;
            boolean expected=l==convertedBack;
            boolean actual=TypeUtil.checkCastToFloat(l);
            Assert.assertTrue("val = "+val+"; l = "+l+"; f = "+f+" expected = "+expected+"; actual = "+actual,
                    expected==actual);
        }
    }
    @Test
    public void testDoubleEqualsdoubledouble(){
        Assert.assertFalse(TypeUtil.doubleEquals(0.0,-0.0));
        Assert.assertFalse(TypeUtil.doubleEquals(1.0,0.0));
        Assert.assertFalse(TypeUtil.doubleEquals(Double.longBitsToDouble(0x0fffffffffffffffL),
                Double.longBitsToDouble(0xfffffffffffffff0L)));
        Assert.assertTrue(TypeUtil.doubleEquals(1.0,1.0));
        Assert.assertTrue(TypeUtil.doubleEquals(Double.longBitsToDouble(0xffffffffffffffffL),
                Double.longBitsToDouble(0xfffffffffffffff0L)));
    }
    @Test
    public void testDoubleEqualsdoublefloat(){
        Assert.assertFalse(TypeUtil.doubleEquals(0.0,-0.0F));
        Assert.assertFalse(TypeUtil.doubleEquals(1.0,0.0F));
        Assert.assertFalse(
                TypeUtil.doubleEquals(Double.longBitsToDouble(0x0fffffffffffffffL),Float.intBitsToFloat(0xfffffff0)));
        Assert.assertTrue(TypeUtil.doubleEquals(1.0,1.0F));
        Assert.assertTrue(
                TypeUtil.doubleEquals(Double.longBitsToDouble(0xffffffffffffffffL),Float.intBitsToFloat(0xfffffff0)));
    }


    @Test
    public void testDoubleEqualsdoublelong(){
        Assert.assertFalse(TypeUtil.doubleEquals(1.0,0L));
        Assert.assertFalse(TypeUtil.doubleEquals((double)(TypeUtil.MAX_SAFE_LONG+1),TypeUtil.MAX_SAFE_LONG+1));
        Assert.assertFalse(TypeUtil.doubleEquals((double)(TypeUtil.MIN_SAFE_LONG-1),TypeUtil.MIN_SAFE_LONG-1));
        Assert.assertTrue(TypeUtil.doubleEquals((double)TypeUtil.MAX_SAFE_LONG,TypeUtil.MAX_SAFE_LONG));
        Assert.assertTrue(TypeUtil.doubleEquals((double)TypeUtil.MIN_SAFE_LONG,TypeUtil.MIN_SAFE_LONG));
        Assert.assertTrue(TypeUtil.doubleEquals((double)(TypeUtil.MAX_SAFE_LONG-1),TypeUtil.MAX_SAFE_LONG-1));
        Assert.assertTrue(TypeUtil.doubleEquals((double)(TypeUtil.MIN_SAFE_LONG+1),TypeUtil.MIN_SAFE_LONG+1));
        int shift=0;
        while(shift!=10){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertFalse("shift = "+shift,TypeUtil.doubleEquals((double)l,l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertFalse("shift = "+shift,TypeUtil.doubleEquals((double)l,l));
            ++shift;
        }
        while(shift!=64){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertTrue("shift = "+shift,TypeUtil.doubleEquals((double)l,l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertTrue("shift = "+shift,TypeUtil.doubleEquals((double)l,l));
            ++shift;
        }
    }
    @Test
    public void testFloatEqualsfloatfloat(){
        Assert.assertFalse(TypeUtil.floatEquals(0.0f,-0.0f));
        Assert.assertFalse(TypeUtil.floatEquals(1.0f,0.0f));
        Assert.assertFalse(TypeUtil.floatEquals(Float.intBitsToFloat(0x0fffffff),Float.intBitsToFloat(0xfffffff0)));
        Assert.assertTrue(TypeUtil.floatEquals(1.0f,1.0f));
        Assert.assertTrue(TypeUtil.floatEquals(Float.intBitsToFloat(0xffffffff),Float.intBitsToFloat(0xfffffff0)));
    }
    @Test
    public void testFloatEqualsfloatlong(){
        Assert.assertFalse(TypeUtil.floatEquals(1.0f,0L));
        Assert.assertFalse(TypeUtil.floatEquals((float)(TypeUtil.MAX_SAFE_INT+1),(long)(TypeUtil.MAX_SAFE_INT+1)));
        Assert.assertFalse(TypeUtil.floatEquals((float)(TypeUtil.MIN_SAFE_INT-1),(long)(TypeUtil.MIN_SAFE_INT-1)));
        Assert.assertTrue(TypeUtil.floatEquals((float)TypeUtil.MAX_SAFE_INT,(long)TypeUtil.MAX_SAFE_INT));
        Assert.assertTrue(TypeUtil.floatEquals((float)TypeUtil.MIN_SAFE_INT,(long)TypeUtil.MIN_SAFE_INT));
        Assert.assertTrue(TypeUtil.floatEquals((float)(TypeUtil.MAX_SAFE_INT-1),(long)TypeUtil.MAX_SAFE_INT-1));
        Assert.assertTrue(TypeUtil.floatEquals((float)(TypeUtil.MIN_SAFE_INT+1),(long)TypeUtil.MIN_SAFE_INT+1));
        int shift=0;
        while(shift!=39){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertFalse("shift = "+shift,TypeUtil.floatEquals((float)l,l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertFalse("shift = "+shift,TypeUtil.floatEquals((float)l,l));
            ++shift;
        }
        while(shift!=64){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertTrue("shift = "+shift,TypeUtil.floatEquals((float)l,l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertTrue("shift = "+shift,TypeUtil.floatEquals((float)l,l));
            ++shift;
        }
    }
    @Test
    @Deprecated
    public void testDoubleEqualsdoubleint(){
        Assert.assertTrue(TypeUtil.doubleEquals(1.0,1));
        Assert.assertFalse(TypeUtil.doubleEquals(1.0,0));
    }
    @Test
    @Deprecated
    public void testFloatEqualsfloatdouble(){
        Assert.assertFalse(TypeUtil.doubleEquals(-0.0F,0.0));
        Assert.assertFalse(TypeUtil.doubleEquals(0.0F,1.0));
        Assert.assertFalse(
                TypeUtil.doubleEquals(Float.intBitsToFloat(0xfffffff0),Double.longBitsToDouble(0x0fffffffffffffffL)));
        Assert.assertTrue(TypeUtil.doubleEquals(1.0F,1.0));
        Assert.assertTrue(
                TypeUtil.doubleEquals(Float.intBitsToFloat(0xfffffff0),Double.longBitsToDouble(0xffffffffffffffffL)));
    }
    private static class TestDoublePredicate implements DoublePredicate{
        double consumed;
        @Override
        public boolean test(double val){
            this.consumed=val;
            return true;
        }
    }
    private static class TestDoubleConsumer implements DoubleConsumer{
        double consumed;
        @Override
        public void accept(double val){
            this.consumed=val;
        }
    }
    private static class TestFloatPredicate implements FloatPredicate{
        float consumed;
        @Override
        public boolean test(float val){
            this.consumed=val;
            return true;
        }
    }
    private static class TestFloatConsumer implements FloatConsumer{
        float consumed;
        @Override
        public void accept(float val){
            this.consumed=val;
        }
    }
    private static class TestLongPredicate implements LongPredicate{
        long consumed;
        @Override
        public boolean test(long val){
            this.consumed=val;
            return true;
        }
    }
    private static class TestLongConsumer implements LongConsumer{
        long consumed;
        @Override
        public void accept(long val){
            this.consumed=val;
        }
    }
    private static class TestIntPredicate implements IntPredicate{
        int consumed;
        @Override
        public boolean test(int val){
            this.consumed=val;
            return true;
        }
    }
    private static class TestIntConsumer implements IntConsumer{
        int consumed;
        @Override
        public void accept(int val){
            this.consumed=val;
        }
    }
    @Test
    public void testDoubleToLongBitsConsumer(){
        var test=new TestDoubleConsumer();
        TypeUtil.doubleToLongBitsConsumer(test).accept(Double.doubleToRawLongBits(5.0D));
        Assert.assertTrue(test.consumed==5.0D);
    }
    @Test
    public void testDoubleToLongBitsPredicate(){
        var test=new TestDoublePredicate();
        TypeUtil.doubleToLongBitsPredicate(test).test(Double.doubleToRawLongBits(5.0D));
        Assert.assertTrue(test.consumed==5.0D);
    }
    @Test
    public void testFloatToIntBitsConsumer(){
        var test=new TestFloatConsumer();
        TypeUtil.floatToIntBitsConsumer(test).accept(Float.floatToRawIntBits(5.0F));
        Assert.assertTrue(test.consumed==5.0F);
    }
    @Test
    public void testFloatToIntBitsPredicate(){
        var test=new TestFloatPredicate();
        TypeUtil.floatToIntBitsPredicate(test).test(Float.floatToRawIntBits(5.0F));
        Assert.assertTrue(test.consumed==5.0F);
    }
    @Test
    public void testLongBitsToDoubleConsumer(){
        var test=new TestLongConsumer();
        TypeUtil.longBitsToDoubleConsumer(test).accept(Double.longBitsToDouble(5));
        Assert.assertTrue(test.consumed==5);
    }
    @Test
    public void testLongBitsToDoublePredicate(){
        var test=new TestLongPredicate();
        TypeUtil.longBitsToDoublePredicate(test).test(Double.longBitsToDouble(5));
        Assert.assertTrue(test.consumed==5);
    }
    @Test
    public void testIntBitsToFloatConsumer(){
        var test=new TestIntConsumer();
        TypeUtil.intBitsToFloatConsumer(test).accept(Float.intBitsToFloat(5));
        Assert.assertTrue(test.consumed==5);
    }
    @Test
    public void testIntBitsToFloatPredicate(){
        var test=new TestIntPredicate();
        TypeUtil.intBitsToFloatPredicate(test).test(Float.intBitsToFloat(5));
        Assert.assertTrue(test.consumed==5);
    }
    @Test
    public void testDblBitsEquals0(){
        Assert.assertTrue(TypeUtil.dblBitsEquals0(0L));
        Assert.assertFalse(TypeUtil.dblBitsEquals0(1L));
    }
    @Test
    public void testDblBitsEqualsNaN(){
        Assert.assertTrue(TypeUtil.dblBitsEqualsNaN(Double.doubleToRawLongBits(Double.NaN)));
        Assert.assertFalse(TypeUtil.dblBitsEqualsNaN(1L));
    }
    @Test
    public void testFltBitsEquals0(){
        Assert.assertTrue(TypeUtil.fltBitsEquals0(0));
        Assert.assertFalse(TypeUtil.fltBitsEquals0(1));
    }
    @Test
    public void testFltBitsEqualsNaN(){
        Assert.assertTrue(TypeUtil.fltBitsEqualsNaN(Float.floatToRawIntBits(Float.NaN)));
        Assert.assertFalse(TypeUtil.fltBitsEqualsNaN(1));
    }
    @Test
    public void testEqualsDbl0(){
        Assert.assertTrue(TypeUtil.equalsDbl0(0.0D));
        Assert.assertTrue(TypeUtil.equalsDbl0(-0.0D));
        Assert.assertFalse(TypeUtil.equalsDbl0(1.0D));
    }
    @Test
    public void testEqualsFlt0(){
        Assert.assertTrue(TypeUtil.equalsFlt0(0.0F));
        Assert.assertTrue(TypeUtil.equalsFlt0(-0.0F));
        Assert.assertFalse(TypeUtil.equalsFlt0(1.0F));
    }
    @Test
    public void testEqualsFalse(){
        Assert.assertTrue(TypeUtil.equalsFalse(false));
        Assert.assertFalse(TypeUtil.equalsFalse(true));
    }
    @Test
    public void testEqualsTrue(){
        Assert.assertFalse(TypeUtil.equalsTrue(false));
        Assert.assertTrue(TypeUtil.equalsTrue(true));
    }
    @Test
    @Deprecated
    public void testFloatEqualscharfloat(){
        Assert.assertTrue(TypeUtil.floatEquals((char)1,1.0F));
        Assert.assertFalse(TypeUtil.floatEquals((char)0,1.0F));
    }
    @Test
    @Deprecated
    public void testFloatEqualsfloatchar(){
        Assert.assertTrue(TypeUtil.floatEquals(1.0F,(char)1));
        Assert.assertFalse(TypeUtil.floatEquals(1.0F,(char)0));
    }
    @Test
    @Deprecated
    public void testFloatEqualsfloatint(){
        Assert.assertFalse(TypeUtil.floatEquals((float)Integer.MAX_VALUE,Integer.MAX_VALUE));
        Assert.assertTrue(TypeUtil.floatEquals((float)TypeUtil.MAX_SAFE_INT,TypeUtil.MAX_SAFE_INT));
    }
    @Test
    @Deprecated
    public void testFloatEqualsfloatshort(){
        Assert.assertTrue(TypeUtil.floatEquals(1.0F,(short)1));
        Assert.assertFalse(TypeUtil.floatEquals(1.0F,(short)0));
    }
    @Test
    @Deprecated
    public void testFloatEqualsshortfloat(){
        Assert.assertTrue(TypeUtil.floatEquals((short)1,1.0F));
        Assert.assertFalse(TypeUtil.floatEquals((short)0,1.0F));
    }
    @Test
    @Deprecated
    public void testFloatEqualsintfloat(){
        Assert.assertFalse(TypeUtil.floatEquals(Integer.MAX_VALUE,(float)Integer.MAX_VALUE));
        Assert.assertTrue(TypeUtil.floatEquals(TypeUtil.MAX_SAFE_INT,(float)TypeUtil.MAX_SAFE_INT));
    }
    @Test
    @Deprecated
    public void testFloatEqualslongfloat(){
        Assert.assertFalse(TypeUtil.floatEquals(0L,1.0f));
        Assert.assertFalse(TypeUtil.floatEquals((long)(TypeUtil.MAX_SAFE_INT+1),(float)(TypeUtil.MAX_SAFE_INT+1)));
        Assert.assertFalse(TypeUtil.floatEquals((long)(TypeUtil.MIN_SAFE_INT-1),(float)(TypeUtil.MIN_SAFE_INT-1)));
        Assert.assertTrue(TypeUtil.floatEquals((long)TypeUtil.MAX_SAFE_INT,(float)TypeUtil.MAX_SAFE_INT));
        Assert.assertTrue(TypeUtil.floatEquals((long)TypeUtil.MIN_SAFE_INT,(float)TypeUtil.MIN_SAFE_INT));
        Assert.assertTrue(TypeUtil.floatEquals((long)TypeUtil.MAX_SAFE_INT-1,(float)(TypeUtil.MAX_SAFE_INT-1)));
        Assert.assertTrue(TypeUtil.floatEquals((long)TypeUtil.MIN_SAFE_INT+1,(float)(TypeUtil.MIN_SAFE_INT+1)));
        int shift=0;
        while(shift!=39){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertFalse("shift = "+shift,TypeUtil.floatEquals(l,(float)l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertFalse("shift = "+shift,TypeUtil.floatEquals(l,(float)l));
            ++shift;
        }
        while(shift!=64){
            long l=0x7fffffffffffffffL^(1L<<shift)-1;
            Assert.assertTrue("shift = "+shift,TypeUtil.floatEquals(l,(float)l));
            l=0x8000000000000000L|1L<<shift;
            Assert.assertTrue("shift = "+shift,TypeUtil.floatEquals(l,(float)l));
            ++shift;
        }
    }

    @Test
    public void testRefEqualsObjectboolean(){
        Assert.assertFalse(TypeUtil.refEquals(null,false));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),false));
        Assert.assertFalse(TypeUtil.refEquals(Boolean.TRUE,false));
        Assert.assertTrue(TypeUtil.refEquals(Boolean.FALSE,false));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectBoolean(){
        Assert.assertFalse(TypeUtil.refEquals(null,Boolean.FALSE));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),Boolean.FALSE));
        Assert.assertFalse(TypeUtil.refEquals(Boolean.TRUE,Boolean.FALSE));
        Assert.assertTrue(TypeUtil.refEquals(null,(Boolean)null));
        Assert.assertTrue(TypeUtil.refEquals(new Boolean(false),new Boolean(false)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectbyte(){
        Assert.assertFalse(TypeUtil.refEquals(null,(byte)0));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),(byte)0));
        Assert.assertFalse(TypeUtil.refEquals(Byte.valueOf((byte)1),(byte)0));
        Assert.assertTrue(TypeUtil.refEquals(new Byte((byte)0),(byte)0));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectByte(){
        Assert.assertFalse(TypeUtil.refEquals(null,Byte.valueOf((byte)0)));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),Byte.valueOf((byte)0)));
        Assert.assertFalse(TypeUtil.refEquals(Byte.valueOf((byte)1),Byte.valueOf((byte)0)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Byte)null));
        Assert.assertTrue(TypeUtil.refEquals(new Byte((byte)0),new Byte((byte)0)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectchar(){
        Assert.assertFalse(TypeUtil.refEquals(null,(char)0));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),(char)0));
        Assert.assertFalse(TypeUtil.refEquals(Character.valueOf((char)1),(char)0));
        Assert.assertTrue(TypeUtil.refEquals(new Character((char)0),(char)0));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectCharacter(){
        Assert.assertFalse(TypeUtil.refEquals(null,Character.valueOf((char)0)));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),Character.valueOf((char)0)));
        Assert.assertFalse(TypeUtil.refEquals(Character.valueOf((char)1),Character.valueOf((char)0)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Character)null));
        Assert.assertTrue(TypeUtil.refEquals(new Character((char)0),new Character((char)0)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectshort(){
        Assert.assertFalse(TypeUtil.refEquals(null,(short)0));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),(short)0));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)1),(short)0));
        Assert.assertTrue(TypeUtil.refEquals(new Short((short)0),(short)0));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectShort(){
        Assert.assertFalse(TypeUtil.refEquals(null,Short.valueOf((short)0)));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(0),Short.valueOf((short)0)));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)1),Short.valueOf((short)0)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Short)null));
        Assert.assertTrue(TypeUtil.refEquals(new Short((short)0),new Short((short)0)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectint(){
        Assert.assertFalse(TypeUtil.refEquals(null,0));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),0));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(1),0));
        Assert.assertTrue(TypeUtil.refEquals(new Integer(0),0));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectInteger(){
        Assert.assertFalse(TypeUtil.refEquals(null,Integer.valueOf(0)));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),Integer.valueOf(0)));
        Assert.assertFalse(TypeUtil.refEquals(Integer.valueOf(1),Integer.valueOf(0)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Integer)null));
        Assert.assertTrue(TypeUtil.refEquals(new Integer(0),new Integer(0)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectlong(){
        Assert.assertFalse(TypeUtil.refEquals(null,0L));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),0L));
        Assert.assertFalse(TypeUtil.refEquals(Long.valueOf(1L),0L));
        Assert.assertTrue(TypeUtil.refEquals(new Long(0L),0L));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectLong(){
        Assert.assertFalse(TypeUtil.refEquals(null,Long.valueOf(0L)));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),Long.valueOf(0L)));
        Assert.assertFalse(TypeUtil.refEquals(Long.valueOf(1L),Long.valueOf(0L)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Long)null));
        Assert.assertTrue(TypeUtil.refEquals(new Long(0L),new Long(0L)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectfloat(){
        Assert.assertFalse(TypeUtil.refEquals(null,0F));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),0F));
        Assert.assertFalse(TypeUtil.refEquals(Float.valueOf(1F),0F));
        Assert.assertTrue(TypeUtil.refEquals(new Float(0F),0F));
        Assert.assertFalse(TypeUtil.refEquals(Float.valueOf(0.0f),-0.0f));
        Assert.assertFalse(
                TypeUtil.refEquals(Float.valueOf(Float.intBitsToFloat(0x0fffffff)),Float.intBitsToFloat(0xfffffff0)));
        Assert.assertTrue(
                TypeUtil.refEquals(new Float(Float.intBitsToFloat(0xffffffff)),Float.intBitsToFloat(0xfffffff0)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectFloat(){
        Assert.assertFalse(TypeUtil.refEquals(null,Float.valueOf(0F)));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),Float.valueOf(0F)));
        Assert.assertFalse(TypeUtil.refEquals(Float.valueOf(1F),Float.valueOf(0F)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Float)null));
        Assert.assertTrue(TypeUtil.refEquals(new Float(0F),new Float(0F)));
        Assert.assertFalse(TypeUtil.refEquals(Float.valueOf(0.0f),Float.valueOf(-0.0f)));
        Assert.assertFalse(TypeUtil.refEquals(Float.valueOf(Float.intBitsToFloat(0x0fffffff)),
                Float.valueOf(Float.intBitsToFloat(0xfffffff0))));
        Assert.assertTrue(TypeUtil.refEquals(new Float(Float.intBitsToFloat(0xffffffff)),
                new Float(Float.intBitsToFloat(0xfffffff0))));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectdouble(){
        Assert.assertFalse(TypeUtil.refEquals(null,0D));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),0D));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(1D),0D));
        Assert.assertTrue(TypeUtil.refEquals(new Double(0D),0D));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(0.0D),-0.0D));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(Double.longBitsToDouble(0x0fffffffffffffffL)),
                Double.longBitsToDouble(0xfffffffffffffff0L)));
        Assert.assertTrue(TypeUtil.refEquals(new Double(Double.longBitsToDouble(0xffffffffffffffffL)),
                Double.longBitsToDouble(0xfffffffffffffff0L)));
    }
    @SuppressWarnings("deprecation")
    @Test
    public void testRefEqualsObjectDouble(){
        Assert.assertFalse(TypeUtil.refEquals(null,Double.valueOf(0D)));
        Assert.assertFalse(TypeUtil.refEquals(Short.valueOf((short)0),Double.valueOf(0D)));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(1D),Double.valueOf(0D)));
        Assert.assertTrue(TypeUtil.refEquals(null,(Double)null));
        Assert.assertTrue(TypeUtil.refEquals(new Double(0D),new Double(0D)));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(0.0D),Double.valueOf(-0.0D)));
        Assert.assertFalse(TypeUtil.refEquals(Double.valueOf(Double.longBitsToDouble(0x0fffffffffffffffL)),
                Double.valueOf(Double.longBitsToDouble(0xfffffffffffffff0L))));
        Assert.assertTrue(TypeUtil.refEquals(new Double(Double.longBitsToDouble(0xffffffffffffffffL)),
                new Double(Double.longBitsToDouble(0xfffffffffffffff0L))));
    }
    @Test
    public void testRefEqualsDblBits(){
        Assert.assertFalse(TypeUtil.refEqualsDblBits(null,0L));
        Assert.assertFalse(TypeUtil.refEqualsDblBits(Short.valueOf((short)0),0L));
        Assert.assertFalse(TypeUtil.refEqualsDblBits(Double.valueOf(1D),Double.doubleToRawLongBits(0D)));
        Assert.assertTrue(TypeUtil.refEqualsDblBits(Double.valueOf(0D),Double.doubleToRawLongBits(0D)));
    }
    @Test
    public void testRefEqualsFltBits(){
        Assert.assertFalse(TypeUtil.refEqualsFltBits(null,0));
        Assert.assertFalse(TypeUtil.refEqualsFltBits(Short.valueOf((short)0),0));
        Assert.assertFalse(TypeUtil.refEqualsFltBits(Float.valueOf(1F),Float.floatToRawIntBits(0F)));
        Assert.assertTrue(TypeUtil.refEqualsFltBits(Float.valueOf(0F),Float.floatToRawIntBits(0F)));
    }
    @Test
    public void testRefEqualsDblNaN(){
        Assert.assertFalse(TypeUtil.refEqualsDblNaN(null));
        Assert.assertFalse(TypeUtil.refEqualsDblNaN(Float.valueOf(Float.NaN)));
        Assert.assertFalse(TypeUtil.refEqualsDblNaN(Double.valueOf(0D)));
        Assert.assertTrue(TypeUtil.refEqualsDblNaN(Double.valueOf(Double.longBitsToDouble(0xffffffffffffffffL))));
        Assert.assertTrue(TypeUtil.refEqualsDblNaN(Double.valueOf(Double.NaN)));
    }
    @Test
    public void testRefEqualsFltNaN(){
        Assert.assertFalse(TypeUtil.refEqualsFltNaN(null));
        Assert.assertFalse(TypeUtil.refEqualsFltNaN(Double.valueOf(Double.NaN)));
        Assert.assertFalse(TypeUtil.refEqualsFltNaN(Float.valueOf(0F)));
        Assert.assertTrue(TypeUtil.refEqualsFltNaN(Float.valueOf(Float.intBitsToFloat(0xffffffff))));
        Assert.assertTrue(TypeUtil.refEqualsFltNaN(Float.valueOf(Float.NaN)));
    }
    @Test
    public void testRefEqualsFalse(){
        Assert.assertFalse(TypeUtil.refEqualsFalse(null));
        Assert.assertFalse(TypeUtil.refEqualsFalse(Integer.valueOf(0)));
        Assert.assertFalse(TypeUtil.refEqualsFalse(Boolean.TRUE));
        Assert.assertTrue(TypeUtil.refEqualsFalse(Boolean.FALSE));
    }
    @Test
    public void testRefEqualsTrue(){
        Assert.assertFalse(TypeUtil.refEqualsTrue(null));
        Assert.assertFalse(TypeUtil.refEqualsTrue(Integer.valueOf(1)));
        Assert.assertFalse(TypeUtil.refEqualsTrue(Boolean.FALSE));
        Assert.assertTrue(TypeUtil.refEqualsTrue(Boolean.TRUE));
    }
    @Test
    public void testRefEqualsRawByte(){
        Assert.assertFalse(TypeUtil.refEqualsRawByte(null,0));
        Assert.assertFalse(TypeUtil.refEqualsRawByte(Integer.valueOf(0),0));
        Assert.assertFalse(TypeUtil.refEqualsRawByte(Byte.valueOf((byte)1),0));
        Assert.assertTrue(TypeUtil.refEqualsRawByte(Byte.valueOf((byte)0),0));
    }
    @Test
    public void testRefEqualsRawChar(){
        Assert.assertFalse(TypeUtil.refEqualsRawChar(null,0));
        Assert.assertFalse(TypeUtil.refEqualsRawChar(Integer.valueOf(0),0));
        Assert.assertFalse(TypeUtil.refEqualsRawChar(Character.valueOf((char)1),0));
        Assert.assertTrue(TypeUtil.refEqualsRawChar(Character.valueOf((char)0),0));
    }
    @Test
    public void testRefEqualsRawShrt(){
        Assert.assertFalse(TypeUtil.refEqualsRawShrt(null,0));
        Assert.assertFalse(TypeUtil.refEqualsRawShrt(Integer.valueOf(0),0));
        Assert.assertFalse(TypeUtil.refEqualsRawShrt(Short.valueOf((short)1),0));
        Assert.assertTrue(TypeUtil.refEqualsRawShrt(Short.valueOf((short)0),0));
    }
}
