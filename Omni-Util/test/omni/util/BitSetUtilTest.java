package omni.util;
import org.junit.Assert;
import org.junit.Test;
public class BitSetUtilTest{
    @Test
    public void tesGetBitSet(){
        for(int count=0;count<=100;++count){
            Assert.assertArrayEquals(new long[(count-1>>6)+1],BitSetUtil.getBitSet(count));
        }
    }
    @Test
    public void testContainsWord(){
        long word=0xAAAAAAAAAAAAAAAAL;
        for(int i=Byte.MIN_VALUE;i<=Byte.MAX_VALUE;i+=2){
            Assert.assertFalse(BitSetUtil.containsword(word,i));
            Assert.assertTrue(BitSetUtil.containsword(word,i+1));
        }
    }
}
