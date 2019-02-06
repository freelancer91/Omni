package omni.util;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.Objects;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
public class OmniArrayTest
{
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
      Assert.assertTrue(OmniArray.OfBoolean.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfByte.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfChar.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfShort.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfInt.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfLong.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfFloat.DEFAULT_BOXED_ARR.length==0);
      Assert.assertTrue(OmniArray.OfDouble.DEFAULT_BOXED_ARR.length==0);
  }
  @Test
  public void testGetIndexPredicateChar()
  {
    var inputPred=new CharIndexPredicate();
    char testVal=(char)(1);
    OmniArray.OfChar.getIndexPredicate(inputPred,(char)(3),(char)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class CharIndexPredicate implements CharPredicate
  {
    char captured;
    @Override
    public boolean test(char val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testGetIndexPredicateShort()
  {
    var inputPred=new ShortIndexPredicate();
    short testVal=(short)(1);
    OmniArray.OfShort.getIndexPredicate(inputPred,(short)(3),(short)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class ShortIndexPredicate implements ShortPredicate
  {
    short captured;
    @Override
    public boolean test(short val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testGetIndexPredicateInt()
  {
    var inputPred=new IntIndexPredicate();
    int testVal=(int)(1);
    OmniArray.OfInt.getIndexPredicate(inputPred,(int)(3),(int)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class IntIndexPredicate implements IntPredicate
  {
    int captured;
    @Override
    public boolean test(int val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testGetIndexPredicateLong()
  {
    var inputPred=new LongIndexPredicate();
    long testVal=(long)(1);
    OmniArray.OfLong.getIndexPredicate(inputPred,(long)(3),(long)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class LongIndexPredicate implements LongPredicate
  {
    long captured;
    @Override
    public boolean test(long val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testGetIndexPredicateFloat()
  {
    var inputPred=new FloatIndexPredicate();
    float testVal=(float)(1);
    OmniArray.OfFloat.getIndexPredicate(inputPred,(float)(3),(float)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class FloatIndexPredicate implements FloatPredicate
  {
    float captured;
    @Override
    public boolean test(float val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testGetIndexPredicateDouble()
  {
    var inputPred=new DoubleIndexPredicate();
    double testVal=(double)(1);
    OmniArray.OfDouble.getIndexPredicate(inputPred,(double)(3),(double)(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class DoubleIndexPredicate implements DoublePredicate
  {
    double captured;
    @Override
    public boolean test(double val)
    {
      captured=val;
      return true;
    }
  }
  @SuppressWarnings("deprecation")
  @Test
  public void testGetIndexPredicateRef()
  {
    var inputPred=new RefIndexPredicate();
    Double testVal=(Double)new Double(1);
    OmniArray.OfRef.getIndexPredicate(inputPred,(Double)new Double(3),(Double)new Double(2),testVal).test(2);
    Assert.assertTrue(inputPred.captured==testVal);
  }
  private static class RefIndexPredicate implements Predicate<Double>
  {
    Double captured;
    @Override
    public boolean test(Double val)
    {
      captured=val;
      return true;
    }
  }
  @Test
  public void testReverseRangeboolean()
  {
    Random rand=new Random(0);
    boolean[] arr;
    JunitUtil.booleanArrayBuilder.Randomized.build(arr=new boolean[100],rand);
    boolean[] copy=new boolean[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfBoolean.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangebyte()
  {
    Random rand=new Random(0);
    byte[] arr;
    JunitUtil.byteArrayBuilder.Randomized.build(arr=new byte[100],rand);
    byte[] copy=new byte[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfByte.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangechar()
  {
    Random rand=new Random(0);
    char[] arr;
    JunitUtil.charArrayBuilder.Randomized.build(arr=new char[100],rand);
    char[] copy=new char[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfChar.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangeshort()
  {
    Random rand=new Random(0);
    short[] arr;
    JunitUtil.shortArrayBuilder.Randomized.build(arr=new short[100],rand);
    short[] copy=new short[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfShort.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangeint()
  {
    Random rand=new Random(0);
    int[] arr;
    JunitUtil.intArrayBuilder.Randomized.build(arr=new int[100],rand);
    int[] copy=new int[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfInt.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangelong()
  {
    Random rand=new Random(0);
    long[] arr;
    JunitUtil.longArrayBuilder.Randomized.build(arr=new long[100],rand);
    long[] copy=new long[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfLong.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(arr[i]==copy[j]);
    }
  }
  @Test
  public void testReverseRangefloat()
  {
    Random rand=new Random(0);
    float[] arr;
    JunitUtil.floatArrayBuilder.Randomized.build(arr=new float[100],rand);
    float[] copy=new float[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfFloat.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[j]));
    }
  }
  @Test
  public void testReverseRangedouble()
  {
    Random rand=new Random(0);
    double[] arr;
    JunitUtil.doubleArrayBuilder.Randomized.build(arr=new double[100],rand);
    double[] copy=new double[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfDouble.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[j]));
    }
  }
  @Test
  public void testReverseRangeString()
  {
    Random rand=new Random(0);
    String[] arr;
    JunitUtil.StringArrayBuilder.Randomized.build(arr=new String[100],rand);
    String[] copy=new String[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    OmniArray.OfRef.reverseRange(arr,0,arr.length-1);
    for(int i=0,j=arr.length-1;i<arr.length;++i,--j)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[j]));
    }
  }
  @Test
  public void testGetFltBitsIndexPredicate()
  {
    var inputPred=new FloatIndexPredicate();
    OmniArray.OfFloat.getFltBitsIndexPredicate(inputPred,Float.floatToRawIntBits(3.0f),Float.floatToRawIntBits(2.0f),Float.floatToRawIntBits(1.0f)).test(2);
    Assert.assertTrue(inputPred.captured==1.0f);
  }
  @Test
  public void testGetDblBitsIndexPredicate()
  {
    var inputPred=new DoubleIndexPredicate();
    OmniArray.OfDouble.getDblBitsIndexPredicate(inputPred,Double.doubleToRawLongBits(3.0),Double.doubleToRawLongBits(2.0),Double.doubleToRawLongBits(1.0)).test(2);
    Assert.assertTrue(inputPred.captured==1.0);
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
