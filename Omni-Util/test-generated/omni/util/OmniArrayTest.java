package omni.util;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.Random;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
import java.util.Objects;
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
              Assertions.assertTrue(expected==actual);
          }
      }
  }
  @Test
  public void testGrowBy100Pctint(){
      testGrowBy100PctHelper(0);
      testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE/2);
      testGrowBy100PctHelper(OmniArray.MAX_ARR_SIZE);
      testGrowBy100PctHelper(Integer.MAX_VALUE);
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy100Pct(Integer.MAX_VALUE));
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
                  Assertions.assertTrue(expected==actual);
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
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy100Pct(Integer.MAX_VALUE,Integer.MAX_VALUE+1));
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy100Pct(0,Integer.MAX_VALUE+1));
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
              Assertions.assertTrue(expected==actual);
          }
      }
  }
  @Test
  public void testGrowBy50Pctint(){
      testGrowBy50PctHelper(0);
      testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE/3*2+1);
      testGrowBy50PctHelper(OmniArray.MAX_ARR_SIZE);
      testGrowBy50PctHelper(Integer.MAX_VALUE);
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy50Pct(Integer.MAX_VALUE));
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
                  Assertions.assertTrue(expected==actual);
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
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy50Pct(Integer.MAX_VALUE,Integer.MAX_VALUE+1));
      Assertions.assertThrows(OutOfMemoryError.class,()->OmniArray.growBy50Pct(0,Integer.MAX_VALUE+1));
  }
  @Test
  public void testGrowToArrSeqDefault(){
      Assertions.assertTrue(OmniArray.MAX_ARR_SIZE==OmniArray.growToArrSeqDefault(OmniArray.MAX_ARR_SIZE));
      Assertions.assertTrue(
              (int)(OmniArray.DEFAULT_ARR_SEQ_CAP*1.5)==OmniArray.growToArrSeqDefault(OmniArray.DEFAULT_ARR_SEQ_CAP));
      Assertions.assertTrue(
              OmniArray.DEFAULT_ARR_SEQ_CAP==OmniArray.growToArrSeqDefault(OmniArray.DEFAULT_ARR_SEQ_CAP-1));
  }
  @Test
  public void testUncheckedArrResize(){
      {
          String[] input=new String[]{"a","b","c"};
          String[] output=OmniArray.uncheckedArrResize(2,input);
          Assertions.assertTrue(input==output);
          Assertions.assertTrue(input[2]==null);
      }
      {
          String[] input=new String[]{"a","b","c"};
          String[] output=OmniArray.uncheckedArrResize(3,input);
          Assertions.assertTrue(input==output);
      }
      {
          String[] input=new String[]{"a","b","c"};
          String[] output=OmniArray.uncheckedArrResize(4,input);
          Assertions.assertTrue(input!=output);
          Assertions.assertTrue(output.length==4);
      }
  }
  @Test
  public void testDefaultArrays(){
      Assertions.assertTrue(OmniArray.OfBoolean.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfByte.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfChar.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfShort.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfInt.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfLong.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfFloat.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfDouble.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfRef.DEFAULT_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfBoolean.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfByte.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfChar.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfShort.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfInt.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfLong.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfFloat.DEFAULT_BOXED_ARR.length==0);
      Assertions.assertTrue(OmniArray.OfDouble.DEFAULT_BOXED_ARR.length==0);
  }
  @Test
  public void testGetIndexPredicateChar()
  {
    var inputPred=new CharIndexPredicate();
    char testVal=(char)(1);
    OmniArray.OfChar.getIndexPredicate(inputPred,(char)(3),(char)(2),testVal).test(2);
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
    Assertions.assertTrue(inputPred.captured==testVal);
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
      booleanArrayBuilder.Randomized.buildUnchecked(arr=new boolean[100],0,arr.length,rand,0);
      boolean[] copy=new boolean[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfBoolean.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangebyte()
    {
      Random rand=new Random(0);
      byte[] arr;
      byteArrayBuilder.Randomized.buildUnchecked(arr=new byte[100],0,arr.length,rand,0);
      byte[] copy=new byte[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfByte.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangechar()
    {
      Random rand=new Random(0);
      char[] arr;
      charArrayBuilder.Randomized.buildUnchecked(arr=new char[100],0,arr.length,rand,0);
      char[] copy=new char[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfChar.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangeshort()
    {
      Random rand=new Random(0);
      short[] arr;
      shortArrayBuilder.Randomized.buildUnchecked(arr=new short[100],0,arr.length,rand,0);
      short[] copy=new short[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfShort.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangeint()
    {
      Random rand=new Random(0);
      int[] arr;
      intArrayBuilder.Randomized.buildUnchecked(arr=new int[100],0,arr.length,rand,0);
      int[] copy=new int[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfInt.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangelong()
    {
      Random rand=new Random(0);
      long[] arr;
      longArrayBuilder.Randomized.buildUnchecked(arr=new long[100],0,arr.length,rand,0);
      long[] copy=new long[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfLong.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangefloat()
    {
      Random rand=new Random(0);
      float[] arr;
      floatArrayBuilder.Randomized.buildUnchecked(arr=new float[100],0,arr.length,rand,0);
      float[] copy=new float[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfFloat.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangedouble()
    {
      Random rand=new Random(0);
      double[] arr;
      doubleArrayBuilder.Randomized.buildUnchecked(arr=new double[100],0,arr.length,rand,0);
      double[] copy=new double[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfDouble.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
    @Test
    public void testReverseRangeString()
    {
      Random rand=new Random(0);
      String[] arr;
      StringArrayBuilder.Randomized.buildUnchecked(arr=new String[100],0,arr.length,rand,0);
      String[] copy=new String[arr.length];
      ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
      OmniArray.OfRef.reverseRange(arr,0,arr.length-1);
      EqualityUtil.uncheckedparallelassertreversearraysAreEqual(arr,0,copy,0,arr.length);
    }
  @Test
  public void testGetFltBitsIndexPredicate()
  {
    var inputPred=new FloatIndexPredicate();
    OmniArray.OfFloat.getFltBitsIndexPredicate(inputPred,Float.floatToRawIntBits(3.0f),Float.floatToRawIntBits(2.0f),Float.floatToRawIntBits(1.0f)).test(2);
    Assertions.assertTrue(inputPred.captured==1.0f);
  }
  @Test
  public void testGetDblBitsIndexPredicate()
  {
    var inputPred=new DoubleIndexPredicate();
    OmniArray.OfDouble.getDblBitsIndexPredicate(inputPred,Double.doubleToRawLongBits(3.0),Double.doubleToRawLongBits(2.0),Double.doubleToRawLongBits(1.0)).test(2);
    Assertions.assertTrue(inputPred.captured==1.0);
  }
  @Test
  public void testNullifyRange()
  {
      String[] arr=new String[]{"a","b","c","d","e","f","g"};
      OmniArray.OfRef.nullifyRange(arr,4,2);
      Assertions.assertTrue("a".equals(arr[0]));
      Assertions.assertTrue("b".equals(arr[1]));
      Assertions.assertTrue(arr[2]==null);
      Assertions.assertTrue(arr[3]==null);
      Assertions.assertTrue(arr[4]==null);
      Assertions.assertTrue("f".equals(arr[5]));
      Assertions.assertTrue("g".equals(arr[6]));
  }
  @Test
  public void testUncheckedContainsboolean()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      boolean[] arr=new boolean[1000];
      if(willContain)
      {
        booleanArrayBuilder.Randomized.buildUnchecked(arr,0,arr.length,rand,rand.nextInt());
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        boolean v=arr[index];
        Assertions.assertTrue(OmniArray.OfBoolean.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        booleanArrayBuilder.AllEquals.buildUnchecked(arr,0,arr.length,rand,rand.nextInt());
        boolean v=arr[0];
        Assertions.assertFalse(OmniArray.OfBoolean.uncheckedcontains(arr,0,arr.length-1,!v));
      }
    }
  }
  @Test
  public void testUncheckedContainsbyte()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=byteArrayBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfByte.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTobyte(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfByte.uncheckedcontains(arr,0,arr.length-1,v));
      }
    }
  }
  @Test
  public void testUncheckedContainschar()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=charArrayBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfChar.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTochar(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfChar.uncheckedcontains(arr,0,arr.length-1,v));
      }
    }
  }
  @Test
  public void testUncheckedContainsshort()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=shortArrayBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfShort.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        var v=TypeConversionUtil.convertToshort(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfShort.uncheckedcontains(arr,0,arr.length-1,v));
      }
    }
  }
  @Test
  public void testUncheckedContainsint()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=intArrayBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfInt.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        var v=TypeConversionUtil.convertToint(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfInt.uncheckedcontains(arr,0,arr.length-1,v));
      }
    }
  }
  @Test
  public void testUncheckedContainslong()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=longArrayBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfLong.uncheckedcontains(arr,0,arr.length-1,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTolong(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfLong.uncheckedcontains(arr,0,arr.length-1,v));
      }
    }
  }
  @Test
  public void testUncheckedContainsfloat()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=floatArrayBuilder.buildRandomArray(1000,1,100,rand);
      switch(RandomUtil.randomIntBetween(0,2,rand))
      {
        case 0:
        {
          //test bits
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            var v=arr[index];
            var bits=Float.floatToRawIntBits(v);
            Assertions.assertTrue(OmniArray.OfFloat.uncheckedcontainsBits(arr,0,arr.length-1,bits));
          }
          else
          {
            var bits=Float.floatToRawIntBits(1000);
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontainsBits(arr,0,arr.length-1,bits));
          }
          break;
        }
        case 1:
        {
          //test NaN
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            arr[index]=Float.NaN;
            Assertions.assertTrue(OmniArray.OfFloat.uncheckedcontainsNaN(arr,0,arr.length-1));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontainsNaN(arr,0,arr.length-1));
          }
          break;
        }
        default:
        {
          //test 0
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            arr[index]=0;
            Assertions.assertTrue(OmniArray.OfFloat.uncheckedcontains0(arr,0,arr.length-1));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontains0(arr,0,arr.length-1));
          }
        }
      }
    }
  }
  @Test
  public void testUncheckedContainsdouble()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=doubleArrayBuilder.buildRandomArray(1000,1,100,rand);
      switch(RandomUtil.randomIntBetween(0,2,rand))
      {
        case 0:
        {
          //test bits
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            var v=arr[index];
            var bits=Double.doubleToRawLongBits(v);
            Assertions.assertTrue(OmniArray.OfDouble.uncheckedcontainsBits(arr,0,arr.length-1,bits));
          }
          else
          {
            var bits=Double.doubleToRawLongBits(1000);
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontainsBits(arr,0,arr.length-1,bits));
          }
          break;
        }
        case 1:
        {
          //test NaN
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            arr[index]=Double.NaN;
            Assertions.assertTrue(OmniArray.OfDouble.uncheckedcontainsNaN(arr,0,arr.length-1));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontainsNaN(arr,0,arr.length-1));
          }
          break;
        }
        default:
        {
          //test 0
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            arr[index]=0;
            Assertions.assertTrue(OmniArray.OfDouble.uncheckedcontains0(arr,0,arr.length-1));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontains0(arr,0,arr.length-1));
          }
        }
      }
    }
  }
  @Test
  public void testUncheckedContainsInteger()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=IntegerArrayBuilder.buildRandomArray(1000,1,100,rand);
      switch(RandomUtil.randomIntBetween(0,2,rand))
      {
        case 0:
        {
          //test non-null
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            var v=arr[index];
            Assertions.assertTrue(OmniArray.OfRef.uncheckedcontainsNonNull(arr,0,arr.length-1,v));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontainsNonNull(arr,0,arr.length-1,new Object()));
          }
          break;
        }
        case 1:
        {
          //test null
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            arr[index]=null;
            Assertions.assertTrue(OmniArray.OfRef.uncheckedcontainsNull(arr,0,arr.length-1));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontainsNull(arr,0,arr.length-1));
          }
          break;
        }
        default:
        {
          //test predicate
          if(willContain)
          {
            int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
            var v=arr[index];
            Assertions.assertTrue(OmniArray.OfRef.uncheckedcontains(arr,0,arr.length-1,v::equals));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontains(arr,0,arr.length-1,Objects::isNull));
          }
        }
      }
    }
  }
}
