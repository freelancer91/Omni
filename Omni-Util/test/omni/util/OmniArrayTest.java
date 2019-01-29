package omni.util;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import org.junit.Assert;
import org.junit.Test;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
public class OmniArrayTest{

    private void testGrowBy100PctHelper(int currCapacityBaseLine){
        for(int i=-2;i<=2;++i){
            int currentCapacity=currCapacityBaseLine+i;
            if(currentCapacity>=0&&currentCapacity<Integer.MAX_VALUE){
                int expected;
                if(currentCapacity<=OmniArray.MAX_ARR_SIZE/2){
                    expected=Math.max(currentCapacity+1,currentCapacity*2);
                }else if(currentCapacity<OmniArray.MAX_ARR_SIZE){
                    expected=OmniArray.MAX_ARR_SIZE;
                }else{
                    expected=currentCapacity+1;
                }
                int actual=OmniArray.growBy100Pct(currentCapacity);
                Assert.assertTrue("currCapacity="+currentCapacity+"; expected="+expected+"; actual="+actual,
                        expected==actual);
            }
        }
    }

    @Test
    public void testGrowBy100Pctint(){
        testGrowBy100PctHelper(0);
        testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE/2);
        testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE);
        testGrowBy100PctHelper(Integer.MAX_VALUE);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy100PctintOOM(){
        OmniArray.growBy100Pct(Integer.MAX_VALUE);
    }
    private void testGrowBy100PctHelper(int currCapacityBaseLine,int minCapacityBaseline){
        for(int i=-2;i<=2;++i){
            int currCapacity=currCapacityBaseLine+i;
            if(currCapacity>=0&&currCapacityBaseLine<Integer.MAX_VALUE-1){
                for(int j=0;j<2;++j){
                    int minCapacity=minCapacityBaseline+j;
                    if(minCapacity<=currCapacity){
                        continue;
                    }
                    int expected;
                    if(minCapacity<=OmniArray.MAX_ARR_SIZE){
                        expected=Math.min(OmniArray.MAX_ARR_SIZE,Math.max(minCapacity,currCapacity*2));
                    }else{
                        expected=minCapacity;
                    }
                    int actual=OmniArray.growBy100Pct(currCapacity,minCapacity);
                    Assert.assertTrue("currCapacity = "+currCapacity+"; minCapacity="+minCapacity+"; expected="+expected
                            +"; actual = "+actual,expected==actual);
                }
            }
        }
    }
    @Test
    public void testGrowBy100Pctintint(){
        testGrowBy100PctHelper(0,1);
        testGrowBy100PctHelper(10,30);
        testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE/2,OmniArray.MAX_ARR_SIZE/2+1);
        testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE/2,OmniArray.MAX_ARR_SIZE);
        testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE,OmniArray.MAX_ARR_SIZE+1);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy100PctintintOOM1(){
        OmniArray.growBy100Pct(Integer.MAX_VALUE,Integer.MAX_VALUE+1);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy100PctintintOOM2(){
        OmniArray.growBy100Pct(0,Integer.MAX_VALUE+1);
    }
    private void testGrowBy50PctHelper(int currCapacityBaseLine){
        for(int i=-2;i<=2;++i){
            int currentCapacity=currCapacityBaseLine+i;
            if(currentCapacity>=0&&currentCapacity<Integer.MAX_VALUE){
                int expected;
                if(currentCapacity<=(int)(OmniArray.MAX_ARR_SIZE/1.5)){
                    expected=Math.max(currentCapacity+1,(int)(currentCapacity*1.5));
                }else if(currentCapacity<OmniArray.MAX_ARR_SIZE){
                    expected=OmniArray.MAX_ARR_SIZE;
                }else{
                    expected=currentCapacity+1;
                }
                int actual=OmniArray.growBy50Pct(currentCapacity);
                Assert.assertTrue("currCapacity="+currentCapacity+"; expected="+expected+"; actual="+actual,
                        expected==actual);
            }
        }
    }
    @Test
    public void testGrowBy50Pctint(){
        testGrowBy50PctHelper(0);
        testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE/3*2+1);
        testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE);
        testGrowBy50PctHelper(Integer.MAX_VALUE);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy50PctintOOM(){
        OmniArray.growBy50Pct(Integer.MAX_VALUE);
    }

    private void testGrowBy50PctHelper(int currCapacityBaseLine,int minCapacityBaseline){
        for(int i=-2;i<=2;++i){
            int currCapacity=currCapacityBaseLine+i;
            if(currCapacity>=0&&currCapacityBaseLine<Integer.MAX_VALUE-1){
                for(int j=0;j<2;++j){
                    int minCapacity=minCapacityBaseline+j;
                    if(minCapacity<=currCapacity){
                        continue;
                    }
                    int expected;
                    if(minCapacity<=OmniArray.MAX_ARR_SIZE){
                        expected=Math.min(OmniArray.MAX_ARR_SIZE,Math.max(minCapacity,(int)(currCapacity*1.5)));
                    }else{
                        expected=minCapacity;
                    }
                    int actual=OmniArray.growBy50Pct(currCapacity,minCapacity);
                    Assert.assertTrue("currCapacity = "+currCapacity+"; minCapacity="+minCapacity+"; expected="+expected
                            +"; actual = "+actual,expected==actual);
                }
            }
        }
    }
    @Test
    public void testGrowBy50Pctintint(){
        testGrowBy50PctHelper(0,1);
        testGrowBy50PctHelper(10,30);
        testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE/3*2+1,OmniArray.MAX_ARR_SIZE/3*2+2);
        testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE/3*2+1,OmniArray.MAX_ARR_SIZE);
        testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE,OmniArray.MAX_ARR_SIZE+1);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy50PctintintOOM1(){
        OmniArray.growBy50Pct(Integer.MAX_VALUE,Integer.MAX_VALUE+1);
    }
    @Test(expected=OutOfMemoryError.class)
    public void testGrowBy50PctintintOOM2(){
        OmniArray.growBy50Pct(0,Integer.MAX_VALUE+1);
    }
    @Test
    public void testGrowToArrSeqDefault(){
        Assert.assertTrue(OmniArray.MAX_ARR_SIZE==OmniArray.growToArrSeqDefault(OmniArray.MAX_ARR_SIZE));
        Assert.assertTrue(
                (int)(OmniArray.DEFAULT_ARR_SEQ_CAP*1.5)==OmniArray.growToArrSeqDefault(OmniArray.DEFAULT_ARR_SEQ_CAP));
        Assert.assertTrue(
                OmniArray.DEFAULT_ARR_SEQ_CAP==OmniArray.growToArrSeqDefault(OmniArray.DEFAULT_ARR_SEQ_CAP-1));
    }
    @Test
    public void testUncheckedArrResize(){
        {
            String[] input=new String[]{"a","b","c"};
            String[] output=OmniArray.uncheckedArrResize(2,input);
            Assert.assertTrue(input==output);
            Assert.assertTrue(input[2]==null);
        }
        {
            String[] input=new String[]{"a","b","c"};
            String[] output=OmniArray.uncheckedArrResize(3,input);
            Assert.assertTrue(input==output);
        }
        {
            String[] input=new String[]{"a","b","c"};
            String[] output=OmniArray.uncheckedArrResize(4,input);
            Assert.assertTrue(input!=output);
            Assert.assertTrue(output.length==4);
        }
    }
    @Test
    public void testDefaultArrays(){
        Assert.assertTrue(OmniArray.OfBoolean.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfByte.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfChar.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfShort.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfInt.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfLong.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfFloat.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfDouble.DEFAULT_ARR.length==0);
        Assert.assertTrue(OmniArray.OfRef.DEFAULT_ARR.length==0);
        // TODO uncomment when the module bug is fixed
        //        Assert.assertTrue(OmniArray.OfBoolean.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfByte.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfChar.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfShort.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfInt.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfLong.DEFAULT_BOXED_ARR.length==0);
        //        Assert.assertTrue(OmniArray.OfFloat.DEFAULT_BOXED_ARR.length==0);
        // Assert.assertTrue(OmniArray.OfDouble.DEFAULT_BOXED_ARR.length==0);
    }
    private static class CharIndexPredicate implements CharPredicate{
        char captured;
        @Override
        public boolean test(char val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateChar(){
        var inputPred=new CharIndexPredicate();
        OmniArray.OfChar.getIndexPredicate(inputPred,'3','2','1').test(2);
        Assert.assertTrue(inputPred.captured=='1');
    }
    private static class DoubleIndexPredicate implements DoublePredicate{
        double captured;
        @Override
        public boolean test(double val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateDouble(){
        var inputPred=new DoubleIndexPredicate();
        OmniArray.OfDouble.getIndexPredicate(inputPred,3.0,2.0,1.0).test(2);
        Assert.assertTrue(inputPred.captured==1.0);
    }
    @Test
    public void testGetDblBitsIndexPredicate(){
        var inputPred=new DoubleIndexPredicate();
        OmniArray.OfDouble.getDblBitsIndexPredicate(inputPred,Double.doubleToRawLongBits(3.0),
                Double.doubleToRawLongBits(2.0),Double.doubleToRawLongBits(1.0)).test(2);
        Assert.assertTrue(inputPred.captured==1.0);
    }
    private static class FloatIndexPredicate implements FloatPredicate{
        float captured;
        @Override
        public boolean test(float val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateFloat(){
        var inputPred=new FloatIndexPredicate();
        OmniArray.OfFloat.getIndexPredicate(inputPred,3.0f,2.0f,1.0f).test(2);
        Assert.assertTrue(inputPred.captured==1.0f);
    }
    @Test
    public void testGetFltBitsIndexPredicate(){
        var inputPred=new FloatIndexPredicate();
        OmniArray.OfFloat.getFltBitsIndexPredicate(inputPred,Float.floatToRawIntBits(3.0f),
                Float.floatToRawIntBits(2.0f),Float.floatToRawIntBits(1.0f)).test(2);
        Assert.assertTrue(inputPred.captured==1.0f);
    }
    private static class IntIndexPredicate implements IntPredicate{
        int captured;
        @Override
        public boolean test(int val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateInt(){
        var inputPred=new IntIndexPredicate();
        OmniArray.OfInt.getIndexPredicate(inputPred,3,2,1).test(2);
        Assert.assertTrue(inputPred.captured==1);
    }
    private static class LongIndexPredicate implements LongPredicate{
        long captured;
        @Override
        public boolean test(long val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateLong(){
        var inputPred=new LongIndexPredicate();
        OmniArray.OfLong.getIndexPredicate(inputPred,3L,2L,1L).test(2);
        Assert.assertTrue(inputPred.captured==1L);
    }
    private static class RefIndexPredicate<E> implements Predicate<E>{
        E captured;
        @Override
        public boolean test(E val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateRef(){
        // TODO uncomment when the module bug is fixed
        //        var inputPred=new RefIndexPredicate<Double>();
        //        Double testVal=Double.valueOf(1);
        //        OmniArray.OfRef.getIndexPredicate(inputPred,Double.valueOf(3),Double.valueOf(2),testVal).test(2);
        //        Assert.assertTrue(inputPred.captured==testVal);
    }
    private static class ShortIndexPredicate implements ShortPredicate{
        short captured;
        @Override
        public boolean test(short val){
            captured=val;
            return true;
        }
    }
    @Test
    public void testGetIndexPredicateShort(){
        var inputPred=new ShortIndexPredicate();
        OmniArray.OfShort.getIndexPredicate(inputPred,(short)3,(short)2,(short)1).test(2);
        Assert.assertTrue(inputPred.captured==(short)1);
    }
    @Test
    public void testNullifyRange()
    {
        String[] arr=new String[]{"a","b","c","d","e","f","g"};
        OmniArray.OfRef.nullifyRange(arr,2,4);
        Assert.assertTrue("a".equals(arr[0]));
        Assert.assertTrue("b".equals(arr[1]));
        Assert.assertTrue(arr[2]==null);
        Assert.assertTrue(arr[3]==null);
        Assert.assertTrue(arr[4]==null);
        Assert.assertTrue("f".equals(arr[5]));
        Assert.assertTrue("g".equals(arr[6]));
    }

}
