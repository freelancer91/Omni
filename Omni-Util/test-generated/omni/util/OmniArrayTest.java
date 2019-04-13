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
import omni.function.BooleanPredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharUnaryOperator;
import omni.function.ShortUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import omni.function.FloatUnaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import java.util.Objects;
import java.util.Arrays;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Consumer;
import omni.function.CharConsumer;
import omni.function.FloatConsumer;
import omni.function.ShortConsumer;
import omni.function.ByteConsumer;
import omni.function.BooleanConsumer;
import java.util.ArrayDeque;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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
  private static class TestIO implements ObjectOutput,ObjectInput
  {
    ArrayDeque<Object> deque=new ArrayDeque<>();
    @Override public void close(){}
    @Override public void flush(){}
    @Override public void write(byte[] b){}
    @Override public void write(byte[] b,int off,int len){}
    @Override public void write(int b){}
    @Override public void writeBoolean(boolean v){}
    @Override public void writeBytes(String s){}
    @Override public void writeChars(String s){}
    @Override public void writeUTF(String s){}
    @Override public boolean readBoolean(){return false;}
    @Override public void readFully(byte[] b){}
    @Override public void readFully(byte[] b,int off,int len){}
    @Override public String readLine(){return null;}
    @Override public int readUnsignedShort(){return 0;}
    @Override public String readUTF(){return null;}
    @Override public int skipBytes(int n){return 0;}
    @Override public long skip(long n){return 0L;}
    @Override public int available(){return 0;}
    @Override public int read(){return 0;}
    @Override public int read(byte[] b){return 0;}
    @Override public int read(byte[] b,int off,int len){return 0;}
    @Override public byte readByte(){
      return (byte)deque.remove();
    }
    @Override public char readChar(){
      return (char)deque.remove();
    }
    @Override public double readDouble(){
      return (double)deque.remove();
    }
    @Override public float readFloat(){
      return (float)deque.remove();
    }
    @Override public int readInt(){
      return (int)deque.remove();
    }
    @Override public long readLong(){
      return (long)deque.remove();
    }
    @Override public short readShort(){
      return (short)deque.remove();
    }
    @Override public int readUnsignedByte(){
      return ((0xff)&(int)(byte)deque.remove());
    }
    @Override public Object readObject(){
      return deque.remove();
    }
    @Override public void writeObject(Object obj)
    {
      deque.add(obj);
    }
    @Override public void writeByte(int v)
    {
      deque.add((byte)v);
    }
    @Override public void writeChar(int v)
    {
      deque.add((char)v);
    }
    @Override public void writeShort(int v)
    {
      deque.add((short)v);
    }
    @Override public void writeInt(int v)
    {
      deque.add(v);
    }
    @Override public void writeLong(long v)
    {
      deque.add(v);
    }
    @Override public void writeFloat(float v)
    {
      deque.add(v);
    }
    @Override public void writeDouble(double v)
    {
      deque.add(v);
    }
  }
  @Test
  public void testwriteAndReadArray_ObjectArray() throws IOException,ClassNotFoundException
  {
    Object[] inArr=new Object[100];
    Object[] outArr=new Object[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertToObject(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfRef.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfRef.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertSame(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_booleanArray() throws IOException
  {
    boolean[] inArr=new boolean[100];
    boolean[] outArr=new boolean[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertToboolean(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfBoolean.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfBoolean.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_charArray() throws IOException
  {
    char[] inArr=new char[100];
    char[] outArr=new char[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertTochar(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfChar.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfChar.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_shortArray() throws IOException
  {
    short[] inArr=new short[100];
    short[] outArr=new short[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertToshort(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfShort.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfShort.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_intArray() throws IOException
  {
    int[] inArr=new int[100];
    int[] outArr=new int[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertToint(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfInt.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfInt.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_longArray() throws IOException
  {
    long[] inArr=new long[100];
    long[] outArr=new long[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertTolong(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfLong.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfLong.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_floatArray() throws IOException
  {
    float[] inArr=new float[100];
    float[] outArr=new float[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertTofloat(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfFloat.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfFloat.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
  }
  @Test
  public void testwriteAndReadArray_doubleArray() throws IOException
  {
    double[] inArr=new double[100];
    double[] outArr=new double[100];
    for(int i=0;i<100;++i)
    {
      inArr[i]=TypeConversionUtil.convertTodouble(i);
    }
    var inAndOut=new TestIO();
    OmniArray.OfDouble.writeArray(inArr,0,inArr.length-1,inAndOut);
    OmniArray.OfDouble.readArray(outArr,0,outArr.length-1,inAndOut);
    for(int i=0;i<100;++i)
    {
      Assertions.assertEquals(inArr[i],outArr[i]);
    }
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
  public void testAscendingOmniStringBuilderToStringboolean()
  {
    boolean[] arr=new boolean[10];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfBoolean.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringboolean()
  {
    boolean[] arr=new boolean[10];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfBoolean.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfBoolean.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringboolean()
  {
    boolean[] arr=new boolean[10];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfBoolean.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringboolean()
  {
    boolean[] arr=new boolean[10];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfBoolean.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfBoolean.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingOmniStringBuilderToStringbyte()
  {
    byte[] arr=new byte[10];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfByte.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringbyte()
  {
    byte[] arr=new byte[10];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfByte.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfByte.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringbyte()
  {
    byte[] arr=new byte[10];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfByte.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringbyte()
  {
    byte[] arr=new byte[10];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfByte.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfByte.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringchar()
  {
    char[] arr=new char[10];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] buffer=new char[arr.length*3];
    buffer[0]='[';
    buffer[buffer.length-1]=']';
    OmniArray.OfChar.ascendingToString(arr,0,arr.length-1,buffer,1);
    String result=new String(buffer,0,buffer.length);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringchar()
  {
    char[] arr=new char[10];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] buffer=new char[arr.length*3];
    buffer[0]='[';
    buffer[buffer.length-1]=']';
    OmniArray.OfChar.descendingToString(arr,0,arr.length-1,buffer,1);
    String result=new String(buffer,0,buffer.length);
    OmniArray.OfChar.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingOmniStringBuilderToStringshort()
  {
    short[] arr=new short[10];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfShort.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringshort()
  {
    short[] arr=new short[10];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfShort.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfShort.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringshort()
  {
    short[] arr=new short[10];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfShort.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringshort()
  {
    short[] arr=new short[10];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfShort.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfShort.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingOmniStringBuilderToStringint()
  {
    int[] arr=new int[10];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfInt.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringint()
  {
    int[] arr=new int[10];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfInt.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfInt.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringint()
  {
    int[] arr=new int[10];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfInt.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringint()
  {
    int[] arr=new int[10];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfInt.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfInt.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingOmniStringBuilderToStringlong()
  {
    long[] arr=new long[10];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfLong.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringlong()
  {
    long[] arr=new long[10];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfLong.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfLong.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringlong()
  {
    long[] arr=new long[10];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfLong.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringlong()
  {
    long[] arr=new long[10];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfLong.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfLong.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingOmniStringBuilderToStringfloat()
  {
    float[] arr=new float[10];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfFloat.ascendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingOmniStringBuilderToStringfloat()
  {
    float[] arr=new float[10];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[]{(byte)'['};
    ToStringUtil.OmniStringBuilderByte builder=new ToStringUtil.OmniStringBuilderByte(1,buffer);
    OmniArray.OfFloat.descendingToString(arr,0,arr.length-1,builder);
    builder.uncheckedAppendChar((byte)']');
    String result=builder.toString();
    OmniArray.OfFloat.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingFixedBufferToStringfloat()
  {
    float[] arr=new float[10];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfFloat.ascendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingFixedBufferToStringfloat()
  {
    float[] arr=new float[10];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] buffer=new byte[22*arr.length];
    buffer[0]=(byte)'[';
    int bufferOffset=OmniArray.OfFloat.descendingToString(arr,0,arr.length-1,buffer,1);
    buffer[bufferOffset++]=(byte)']';
    String result=new String(buffer,0,bufferOffset);
    OmniArray.OfFloat.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingStringBuilderToStringdouble()
  {
    double[] arr=new double[10];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    StringBuilder builder=new StringBuilder("[");
    OmniArray.OfDouble.ascendingToString(arr,0,arr.length-1,builder);
    builder.append(']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingStringBuilderToStringdouble()
  {
    double[] arr=new double[10];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    StringBuilder builder=new StringBuilder("[");
    OmniArray.OfDouble.descendingToString(arr,0,arr.length-1,builder);
    builder.append(']');
    String result=builder.toString();
    OmniArray.OfDouble.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testAscendingStringBuilderToStringInteger()
  {
    Integer[] arr=new Integer[10];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    StringBuilder builder=new StringBuilder("[");
    OmniArray.OfRef.ascendingToString(arr,0,arr.length-1,builder);
    builder.append(']');
    String result=builder.toString();
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testDescendingStringBuilderToStringInteger()
  {
    Integer[] arr=new Integer[10];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    StringBuilder builder=new StringBuilder("[");
    OmniArray.OfRef.descendingToString(arr,0,arr.length-1,builder);
    builder.append(']');
    String result=builder.toString();
    OmniArray.OfRef.reverseRange(arr,0,arr.length-1);
    String expected=Arrays.toString(arr);
    Assertions.assertEquals(result,expected);
  }
  @Test
  public void testRemoveRangeAndPullDownboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    boolean[] copy=new boolean[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfBoolean.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownbyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] copy=new byte[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfByte.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownchar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] copy=new char[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfChar.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    short[] copy=new short[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfShort.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int[] copy=new int[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfInt.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownlong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    long[] copy=new long[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfLong.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownfloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    float[] copy=new float[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfFloat.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDowndouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    double[] copy=new double[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfDouble.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
  }
  @Test
  public void testRemoveRangeAndPullDownInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    Integer[] copy=new Integer[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    int bound=OmniArray.OfRef.removeRangeAndPullDown(arr,index+100,arr.length,100);
    Assertions.assertEquals(bound,arr.length-100);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+100,bound-index);
    for(int i=bound;i<arr.length;++i)
    {
      Assertions.assertNull(arr[i]);
    }
  }
  @Test
  public void testRemoveIndexAndPullDownboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    boolean[] copy=new boolean[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfBoolean.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownbyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] copy=new byte[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfByte.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownchar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] copy=new char[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfChar.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    short[] copy=new short[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfShort.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int[] copy=new int[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfInt.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownlong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    long[] copy=new long[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfLong.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownfloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    float[] copy=new float[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfFloat.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDowndouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    double[] copy=new double[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfDouble.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
  }
  @Test
  public void testRemoveIndexAndPullDownInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    Integer[] copy=new Integer[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    int index=arr.length/2;
    OmniArray.OfRef.removeIndexAndPullDown(arr,index,arr.length-1);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,index);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,index,copy,index+1,arr.length-1-index);
    Assertions.assertNull(arr[arr.length-1]);
  }
  @Test
  public void testAscendingSeqHashCodeboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfBoolean.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        Boolean.hashCode(arr[i])
      );
    }
    Assertions.assertEquals(hash,expectedHash);
    OmniArray.OfBoolean.uncheckedReplaceAll(arr,0,arr.length,val->!val);
    hash=OmniArray.OfBoolean.ascendingSeqHashCode(arr,0,arr.length-1);
    expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(Boolean.hashCode(arr[i]));
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodeboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfBoolean.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        Boolean.hashCode(arr[i]);
    }
    Assertions.assertEquals(hash,expectedHash);
    OmniArray.OfBoolean.uncheckedReplaceAll(arr,0,arr.length,val->!val);
    hash=OmniArray.OfBoolean.descendingSeqHashCode(arr,0,arr.length-1);
    expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+(Boolean.hashCode(arr[i]));
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodebyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfByte.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        arr[i]
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodebyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfByte.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        arr[i];
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodechar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfChar.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        arr[i]
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodechar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfChar.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        arr[i];
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodeshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfShort.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        arr[i]
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodeshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfShort.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        arr[i];
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodeint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfInt.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        arr[i]
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodeint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfInt.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        arr[i];
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodelong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfLong.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        Long.hashCode(arr[i])
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodelong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfLong.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        Long.hashCode(arr[i]);
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodefloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfFloat.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        Float.hashCode(arr[i])
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodefloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfFloat.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        Float.hashCode(arr[i]);
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodedouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfDouble.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        Double.hashCode(arr[i])
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodedouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfDouble.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        Double.hashCode(arr[i]);
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testAscendingSeqHashCodeInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfRef.ascendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=0;i<arr.length;++i)
    {
      expectedHash=expectedHash*31+(
        Objects.hashCode(arr[i])
      );
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testDescendingSeqHashCodeInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int hash=OmniArray.OfRef.descendingSeqHashCode(arr,0,arr.length-1);
    int expectedHash=1;
    for(int i=arr.length-1;i>=0;--i)
    {
      expectedHash=expectedHash*31+
        Objects.hashCode(arr[i]);
    }
    Assertions.assertEquals(hash,expectedHash);
  }
  @Test
  public void testascendingForEachboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    boolean[] copy=new boolean[arr.length];
    var forwardCopier=new BooleanConsumer()
    {
      int currIndex=0;
      public void accept(boolean val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfBoolean.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    boolean[] copy=new boolean[arr.length];
    var reverseCopier=new BooleanConsumer()
    {
      int currIndex=arr.length;
      public void accept(boolean val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfBoolean.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachbyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] copy=new byte[arr.length];
    var forwardCopier=new ByteConsumer()
    {
      int currIndex=0;
      public void accept(byte val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfByte.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachbyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] copy=new byte[arr.length];
    var reverseCopier=new ByteConsumer()
    {
      int currIndex=arr.length;
      public void accept(byte val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfByte.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachchar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] copy=new char[arr.length];
    var forwardCopier=new CharConsumer()
    {
      int currIndex=0;
      public void accept(char val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfChar.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachchar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] copy=new char[arr.length];
    var reverseCopier=new CharConsumer()
    {
      int currIndex=arr.length;
      public void accept(char val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfChar.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    short[] copy=new short[arr.length];
    var forwardCopier=new ShortConsumer()
    {
      int currIndex=0;
      public void accept(short val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfShort.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    short[] copy=new short[arr.length];
    var reverseCopier=new ShortConsumer()
    {
      int currIndex=arr.length;
      public void accept(short val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfShort.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int[] copy=new int[arr.length];
    var forwardCopier=new IntConsumer()
    {
      int currIndex=0;
      public void accept(int val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfInt.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int[] copy=new int[arr.length];
    var reverseCopier=new IntConsumer()
    {
      int currIndex=arr.length;
      public void accept(int val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfInt.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachlong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    long[] copy=new long[arr.length];
    var forwardCopier=new LongConsumer()
    {
      int currIndex=0;
      public void accept(long val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfLong.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachlong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    long[] copy=new long[arr.length];
    var reverseCopier=new LongConsumer()
    {
      int currIndex=arr.length;
      public void accept(long val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfLong.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachfloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    float[] copy=new float[arr.length];
    var forwardCopier=new FloatConsumer()
    {
      int currIndex=0;
      public void accept(float val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfFloat.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachfloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    float[] copy=new float[arr.length];
    var reverseCopier=new FloatConsumer()
    {
      int currIndex=arr.length;
      public void accept(float val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfFloat.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachdouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    double[] copy=new double[arr.length];
    var forwardCopier=new DoubleConsumer()
    {
      int currIndex=0;
      public void accept(double val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfDouble.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachdouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    double[] copy=new double[arr.length];
    var reverseCopier=new DoubleConsumer()
    {
      int currIndex=arr.length;
      public void accept(double val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfDouble.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testascendingForEachInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    Integer[] copy=new Integer[arr.length];
    var forwardCopier=new Consumer<Integer>()
    {
      int currIndex=0;
      public void accept(Integer val)
      {
        copy[currIndex++]=val;
      };
    };
    OmniArray.OfRef.ascendingForEach(arr,0,arr.length-1,forwardCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testdescendingForEachInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    Integer[] copy=new Integer[arr.length];
    var reverseCopier=new Consumer<Integer>()
    {
      int currIndex=arr.length;
      public void accept(Integer val)
      {
        copy[--currIndex]=val;
      };
    };
    OmniArray.OfRef.descendingForEach(arr,0,arr.length-1,reverseCopier);
    EqualityUtil.uncheckedparallelassertarraysAreEqual(arr,0,copy,0,arr.length);
  }
  @Test
  public void testUncheckedReplaceAllboolean()
  {
    boolean[] arr=new boolean[1000];
    booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    boolean[] copy=new boolean[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    BooleanPredicate manipulator=val->
    {
      return !val;
    };
    OmniArray.OfBoolean.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.test(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllbyte()
  {
    byte[] arr=new byte[1000];
    byteTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    byte[] copy=new byte[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    ByteUnaryOperator manipulator=val->
    {
      return (byte)(val*val);
    };
    OmniArray.OfByte.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsByte(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllchar()
  {
    char[] arr=new char[1000];
    charTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    char[] copy=new char[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    CharUnaryOperator manipulator=val->
    {
      return (char)(val*val);
    };
    OmniArray.OfChar.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsChar(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllshort()
  {
    short[] arr=new short[1000];
    shortTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    short[] copy=new short[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    ShortUnaryOperator manipulator=val->
    {
      return (short)(val*val);
    };
    OmniArray.OfShort.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsShort(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllint()
  {
    int[] arr=new int[1000];
    intTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    int[] copy=new int[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    IntUnaryOperator manipulator=val->
    {
      return (int)(val*val);
    };
    OmniArray.OfInt.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsInt(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAlllong()
  {
    long[] arr=new long[1000];
    longTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    long[] copy=new long[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    LongUnaryOperator manipulator=val->
    {
      return (long)(val*val);
    };
    OmniArray.OfLong.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsLong(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllfloat()
  {
    float[] arr=new float[1000];
    floatTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    float[] copy=new float[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    FloatUnaryOperator manipulator=val->
    {
      return (float)(val*val);
    };
    OmniArray.OfFloat.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsFloat(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAlldouble()
  {
    double[] arr=new double[1000];
    doubleTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    double[] copy=new double[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    DoubleUnaryOperator manipulator=val->
    {
      return (double)(val*val);
    };
    OmniArray.OfDouble.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.applyAsDouble(copy[i]),arr[i]));
    }
  }
  @Test
  public void testUncheckedReplaceAllInteger()
  {
    Integer[] arr=new Integer[1000];
    IntegerTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,new Random(),0);
    Integer[] copy=new Integer[arr.length];
    ArrCopy.uncheckedCopy(arr,0,copy,0,arr.length);
    UnaryOperator<Integer> manipulator=val->
    {
      int i=(int)val;
      return (Integer)(i*i);
    };
    OmniArray.OfRef.uncheckedReplaceAll(arr,0,arr.length,manipulator);
    for(int i=0;i<arr.length;++i)
    {
      Assertions.assertTrue(EqualityUtil.isEqual(manipulator.apply(copy[i]),arr[i]));
    }
  }
    @Test
    public void testReverseRangeboolean()
    {
      Random rand=new Random(0);
      boolean[] arr;
      booleanTestDataBuilder.Randomized.buildUnchecked(arr=new boolean[100],0,arr.length,rand,0);
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
      byteTestDataBuilder.Randomized.buildUnchecked(arr=new byte[100],0,arr.length,rand,0);
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
      charTestDataBuilder.Randomized.buildUnchecked(arr=new char[100],0,arr.length,rand,0);
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
      shortTestDataBuilder.Randomized.buildUnchecked(arr=new short[100],0,arr.length,rand,0);
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
      intTestDataBuilder.Randomized.buildUnchecked(arr=new int[100],0,arr.length,rand,0);
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
      longTestDataBuilder.Randomized.buildUnchecked(arr=new long[100],0,arr.length,rand,0);
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
      floatTestDataBuilder.Randomized.buildUnchecked(arr=new float[100],0,arr.length,rand,0);
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
      doubleTestDataBuilder.Randomized.buildUnchecked(arr=new double[100],0,arr.length,rand,0);
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
      StringTestDataBuilder.Randomized.buildUnchecked(arr=new String[100],0,arr.length,rand,0);
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
  public void testUncheckedQueryboolean()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      boolean[] arr=new boolean[1000];
      if(willContain)
      {
        booleanTestDataBuilder.Randomized.buildUnchecked(arr,0,arr.length,rand,rand.nextInt());
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        boolean v=arr[index];
        Assertions.assertTrue(OmniArray.OfBoolean.uncheckedcontains(arr,0,arr.length-1,v));
        booleanTestDataBuilder.AllEquals.buildUnchecked(arr,0,arr.length,rand,0);
        v=arr[index];
        arr[index]=!v;
        Assertions.assertEquals(index,OmniArray.OfBoolean.uncheckedindexOf(arr,arr.length,!v));
        Assertions.assertEquals(index,OmniArray.OfBoolean.uncheckedlastIndexOf(arr,arr.length,!v));
        Assertions.assertEquals(index,OmniArray.OfBoolean.uncheckedindexOf(arr,0,arr.length,!v));
        Assertions.assertEquals(index,OmniArray.OfBoolean.uncheckedlastIndexOf(arr,0,arr.length,!v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfBoolean.uncheckedsearch(arr,arr.length,!v));
      }
      else
      {
        booleanTestDataBuilder.AllEquals.buildUnchecked(arr,0,arr.length,rand,rand.nextInt());
        boolean v=arr[0];
        Assertions.assertFalse(OmniArray.OfBoolean.uncheckedcontains(arr,0,arr.length-1,!v));
        Assertions.assertEquals(-1,OmniArray.OfBoolean.uncheckedindexOf(arr,arr.length,!v));
        Assertions.assertEquals(-1,OmniArray.OfBoolean.uncheckedlastIndexOf(arr,arr.length,!v));
        Assertions.assertEquals(-1,OmniArray.OfBoolean.uncheckedindexOf(arr,0,arr.length,!v));
        Assertions.assertEquals(-1,OmniArray.OfBoolean.uncheckedlastIndexOf(arr,0,arr.length,!v));
        Assertions.assertEquals(-1,OmniArray.OfBoolean.uncheckedsearch(arr,arr.length,!v));
      }
    }
  }
  @Test
  public void testUncheckedQuerybyte()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=byteTestDataBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfByte.uncheckedcontains(arr,0,arr.length-1,v));
        v=arr[index]=TypeConversionUtil.convertTobyte(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertEquals(index,OmniArray.OfByte.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfByte.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfByte.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfByte.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfByte.uncheckedsearch(arr,arr.length,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTobyte(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfByte.uncheckedcontains(arr,0,arr.length-1,v));
        Assertions.assertEquals(-1,OmniArray.OfByte.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfByte.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfByte.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfByte.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfByte.uncheckedsearch(arr,arr.length,v));
      }
    }
  }
  @Test
  public void testUncheckedQuerychar()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=charTestDataBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfChar.uncheckedcontains(arr,0,arr.length-1,v));
        v=arr[index]=TypeConversionUtil.convertTochar(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertEquals(index,OmniArray.OfChar.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfChar.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfChar.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfChar.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfChar.uncheckedsearch(arr,arr.length,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTochar(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfChar.uncheckedcontains(arr,0,arr.length-1,v));
        Assertions.assertEquals(-1,OmniArray.OfChar.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfChar.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfChar.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfChar.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfChar.uncheckedsearch(arr,arr.length,v));
      }
    }
  }
  @Test
  public void testUncheckedQueryshort()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=shortTestDataBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfShort.uncheckedcontains(arr,0,arr.length-1,v));
        v=arr[index]=TypeConversionUtil.convertToshort(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertEquals(index,OmniArray.OfShort.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfShort.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfShort.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfShort.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfShort.uncheckedsearch(arr,arr.length,v));
      }
      else
      {
        var v=TypeConversionUtil.convertToshort(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfShort.uncheckedcontains(arr,0,arr.length-1,v));
        Assertions.assertEquals(-1,OmniArray.OfShort.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfShort.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfShort.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfShort.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfShort.uncheckedsearch(arr,arr.length,v));
      }
    }
  }
  @Test
  public void testUncheckedQueryint()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=intTestDataBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfInt.uncheckedcontains(arr,0,arr.length-1,v));
        v=arr[index]=TypeConversionUtil.convertToint(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertEquals(index,OmniArray.OfInt.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfInt.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfInt.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfInt.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfInt.uncheckedsearch(arr,arr.length,v));
      }
      else
      {
        var v=TypeConversionUtil.convertToint(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfInt.uncheckedcontains(arr,0,arr.length-1,v));
        Assertions.assertEquals(-1,OmniArray.OfInt.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfInt.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfInt.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfInt.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfInt.uncheckedsearch(arr,arr.length,v));
      }
    }
  }
  @Test
  public void testUncheckedQuerylong()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=longTestDataBuilder.buildRandomArray(1000,1,100,rand);
      if(willContain)
      {
        int index=RandomUtil.randomIntBetween(0,arr.length-1,rand);
        var v=arr[index];
        Assertions.assertTrue(OmniArray.OfLong.uncheckedcontains(arr,0,arr.length-1,v));
        v=arr[index]=TypeConversionUtil.convertTolong(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertEquals(index,OmniArray.OfLong.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfLong.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfLong.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(index,OmniArray.OfLong.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(arr.length-index,OmniArray.OfLong.uncheckedsearch(arr,arr.length,v));
      }
      else
      {
        var v=TypeConversionUtil.convertTolong(RandomUtil.randomIntBetween(101,120,rand));
        Assertions.assertFalse(OmniArray.OfLong.uncheckedcontains(arr,0,arr.length-1,v));
        Assertions.assertEquals(-1,OmniArray.OfLong.uncheckedindexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfLong.uncheckedlastIndexOf(arr,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfLong.uncheckedindexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfLong.uncheckedlastIndexOf(arr,0,arr.length,v));
        Assertions.assertEquals(-1,OmniArray.OfLong.uncheckedsearch(arr,arr.length,v));
      }
    }
  }
  @Test
  public void testUncheckedQueryfloat()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=floatTestDataBuilder.buildRandomArray(1000,1,100,rand);
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
            v=arr[index]=TypeConversionUtil.convertTofloat(RandomUtil.randomIntBetween(101,120,rand));
            bits=Float.floatToRawIntBits(v);
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(arr.length-index,OmniArray.OfFloat.uncheckedsearchBits(arr,arr.length,bits));
          }
          else
          {
            var bits=Float.floatToRawIntBits(1000);
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontainsBits(arr,0,arr.length-1,bits));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedsearchBits(arr,arr.length,bits));
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
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOfNaN(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOfNaN(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(arr.length-index,OmniArray.OfFloat.uncheckedsearchNaN(arr,arr.length));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontainsNaN(arr,0,arr.length-1));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOfNaN(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOfNaN(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedsearchNaN(arr,arr.length));
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
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOf0(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOf0(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedindexOf0(arr,0,arr.length));
            Assertions.assertEquals(index,OmniArray.OfFloat.uncheckedlastIndexOf0(arr,0,arr.length));
            Assertions.assertEquals(arr.length-index,OmniArray.OfFloat.uncheckedsearch0(arr,arr.length));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfFloat.uncheckedcontains0(arr,0,arr.length-1));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOf0(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOf0(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedindexOf0(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedlastIndexOf0(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfFloat.uncheckedsearch0(arr,arr.length));
          }
        }
      }
    }
  }
  @Test
  public void testUncheckedQuerydouble()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=doubleTestDataBuilder.buildRandomArray(1000,1,100,rand);
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
            v=arr[index]=TypeConversionUtil.convertTodouble(RandomUtil.randomIntBetween(101,120,rand));
            bits=Double.doubleToRawLongBits(v);
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(arr.length-index,OmniArray.OfDouble.uncheckedsearchBits(arr,arr.length,bits));
          }
          else
          {
            var bits=Double.doubleToRawLongBits(1000);
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontainsBits(arr,0,arr.length-1,bits));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOfBits(arr,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOfBits(arr,0,arr.length,bits));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedsearchBits(arr,arr.length,bits));
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
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOfNaN(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOfNaN(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(arr.length-index,OmniArray.OfDouble.uncheckedsearchNaN(arr,arr.length));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontainsNaN(arr,0,arr.length-1));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOfNaN(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOfNaN(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOfNaN(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedsearchNaN(arr,arr.length));
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
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOf0(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOf0(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedindexOf0(arr,0,arr.length));
            Assertions.assertEquals(index,OmniArray.OfDouble.uncheckedlastIndexOf0(arr,0,arr.length));
            Assertions.assertEquals(arr.length-index,OmniArray.OfDouble.uncheckedsearch0(arr,arr.length));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfDouble.uncheckedcontains0(arr,0,arr.length-1));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOf0(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOf0(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedindexOf0(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedlastIndexOf0(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfDouble.uncheckedsearch0(arr,arr.length));
          }
        }
      }
    }
  }
  @Test
  public void testUncheckedQueryInteger()
  {
    Random rand=new Random(0);
    for(int i=0;i<100;++i)
    {
      boolean willContain=rand.nextBoolean();
      var arr=IntegerTestDataBuilder.buildRandomArray(1000,1,100,rand);
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
            v=arr[index]=TypeConversionUtil.convertToInteger(RandomUtil.randomIntBetween(101,120,rand));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOfNonNull(arr,arr.length,v));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOfNonNull(arr,arr.length,v));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOfNonNull(arr,0,arr.length,v));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOfNonNull(arr,0,arr.length,v));
            Assertions.assertEquals(arr.length-index,OmniArray.OfRef.uncheckedsearchNonNull(arr,arr.length,v));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontainsNonNull(arr,0,arr.length-1,new Object()));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOfNonNull(arr,arr.length,new Object()));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOfNonNull(arr,arr.length,new Object()));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOfNonNull(arr,0,arr.length,new Object()));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOfNonNull(arr,0,arr.length,new Object()));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedsearchNonNull(arr,arr.length,new Object()));
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
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOfNull(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOfNull(arr,arr.length));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOfNull(arr,0,arr.length));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOfNull(arr,0,arr.length));
            Assertions.assertEquals(arr.length-index,OmniArray.OfRef.uncheckedsearchNull(arr,arr.length));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontainsNull(arr,0,arr.length-1));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOfNull(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOfNull(arr,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOfNull(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOfNull(arr,0,arr.length));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedsearchNull(arr,arr.length));
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
            v=arr[index]=TypeConversionUtil.convertToInteger(RandomUtil.randomIntBetween(101,120,rand));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOf(arr,arr.length,v::equals));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOf(arr,arr.length,v::equals));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedindexOf(arr,0,arr.length,v::equals));
            Assertions.assertEquals(index,OmniArray.OfRef.uncheckedlastIndexOf(arr,0,arr.length,v::equals));
            Assertions.assertEquals(arr.length-index,OmniArray.OfRef.uncheckedsearch(arr,arr.length,v::equals));
          }
          else
          {
            Assertions.assertFalse(OmniArray.OfRef.uncheckedcontains(arr,0,arr.length-1,Objects::isNull));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOf(arr,arr.length,Objects::isNull));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOf(arr,arr.length,Objects::isNull));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedindexOf(arr,0,arr.length,Objects::isNull));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedlastIndexOf(arr,0,arr.length,Objects::isNull));
            Assertions.assertEquals(-1,OmniArray.OfRef.uncheckedsearch(arr,arr.length,Objects::isNull));
          }
        }
      }
    }
  }
}
