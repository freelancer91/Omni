package omni.util;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.ToStringUtil.OmniStringBuilderChar;
import omni.util.ToStringUtil.OmniStringBuilderByte;
public class ToStringUtilTest{
  private static float constructFloat(boolean positive,int binaryExponent,int fractBits){
      binaryExponent+=127;
      binaryExponent<<=23;
      binaryExponent&=0x7f800000;
      fractBits|=binaryExponent;
      if(!positive){
          fractBits|=Integer.MIN_VALUE;
      }
      return Float.intBitsToFloat(fractBits);
  }
  private interface TestThreadGenerator{
      private Runnable getRunnable(int minIncl,int maxIncl){
          return ()->runInMain(minIncl,maxIncl);
      }
      void runInMain(int minIncl,int maxIncl);
  }
  private static void multiThreadedTest(int rangeMinIncl,int rangeMaxIncl,TestThreadGenerator threadGen){
      // TODO make this handle the range in dynamically sized batches (this is because
      // some ranges will go faster than
      // others and we want to keep all CPUs busy if some should finish early)
      // Of course, this could be done with IntStream etc, but we also want to avoid
      // using ThreadLocal for the character buffer
      final int numThreads;
      final Thread[] threads=new Thread[(numThreads=Runtime.getRuntime().availableProcessors()) - 1];
      final int sliceRange=(rangeMaxIncl - rangeMinIncl) / numThreads;
      int threadOffset=rangeMinIncl;
      for(int i=0;i < numThreads - 1;++i){
          final int maxIncl=threadOffset + sliceRange - 1;
          (threads[i]=new Thread(threadGen.getRunnable(threadOffset,maxIncl))).start();
          threadOffset=maxIncl + 1;
      }
      threadGen.runInMain(threadOffset,rangeMaxIncl);
      try{
          for(int i=0;i < numThreads - 1;++i){
              threads[i].join();
          }
      }catch(InterruptedException e){
          e.printStackTrace();
      }
  }
  @Test
  public void testGetStringConvenienceMethods(){
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Boolean.toString(true),ToStringUtil.getString(true));
          final Appendable appendable=new StringBuilder(4);
          try{
              ToStringUtil.getString(true,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter=new CharArrayWriter(4);
          try{
              ToStringUtil.getString(true,charArrayWriter);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(true,charBuffer=CharBuffer.allocate(4));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(true,byteBuffer=ByteBuffer.allocate(4));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(true,stringWriter1=new StringWriter(4));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(true,new PrintWriter(stringWriter2=new StringWriter(4)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer=new StringBuffer(4);
          try{
              ToStringUtil.getString(true,stringBuffer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder=new StringBuilder(4);
          try{
              ToStringUtil.getString(true,stringBuilder);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(4);
          try{
              ToStringUtil.getString(true,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Boolean.toString(false),ToStringUtil.getString(false));
          final Appendable appendable=new StringBuilder(5);
          try{
              ToStringUtil.getString(false,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter=new CharArrayWriter(5);
          try{
              ToStringUtil.getString(false,charArrayWriter);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(false,charBuffer=CharBuffer.allocate(5));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(false,byteBuffer=ByteBuffer.allocate(5));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(false,stringWriter1=new StringWriter(5));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(false,new PrintWriter(stringWriter2=new StringWriter(5)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer=new StringBuffer(5);
          try{
              ToStringUtil.getString(false,stringBuffer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder=new StringBuilder(5);
          try{
              ToStringUtil.getString(false,stringBuilder);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(5);
          try{
              ToStringUtil.getString(false,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Byte.toString(Byte.MIN_VALUE),ToStringUtil.getString(Byte.MIN_VALUE));
          final Appendable appendable=new StringBuilder(4);
          try{
              ToStringUtil.getString(Byte.MIN_VALUE,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter;
          ToStringUtil.getString(Byte.MIN_VALUE,charArrayWriter=new CharArrayWriter(4));
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(Byte.MIN_VALUE,charBuffer=CharBuffer.allocate(4));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(Byte.MIN_VALUE,byteBuffer=ByteBuffer.allocate(4));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(Byte.MIN_VALUE,stringWriter1=new StringWriter(4));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(Byte.MIN_VALUE,new PrintWriter(stringWriter2=new StringWriter(4)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer;
          ToStringUtil.getString(Byte.MIN_VALUE,stringBuffer=new StringBuffer(4));
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder;
          ToStringUtil.getString(Byte.MIN_VALUE,stringBuilder=new StringBuilder(4));
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(4);
          try{
              ToStringUtil.getString(Byte.MIN_VALUE,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Short.toString(Short.MIN_VALUE),ToStringUtil.getString(Short.MIN_VALUE));
          final Appendable appendable=new StringBuilder(6);
          try{
              ToStringUtil.getString(Short.MIN_VALUE,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter;
          ToStringUtil.getString(Short.MIN_VALUE,charArrayWriter=new CharArrayWriter(6));
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(Short.MIN_VALUE,charBuffer=CharBuffer.allocate(6));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(Short.MIN_VALUE,byteBuffer=ByteBuffer.allocate(6));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(Short.MIN_VALUE,stringWriter1=new StringWriter(6));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(Short.MIN_VALUE,new PrintWriter(stringWriter2=new StringWriter(6)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer;
          ToStringUtil.getString(Short.MIN_VALUE,stringBuffer=new StringBuffer(6));
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder;
          ToStringUtil.getString(Short.MIN_VALUE,stringBuilder=new StringBuilder(6));
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(6);
          try{
              ToStringUtil.getString(Short.MIN_VALUE,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Integer.toString(Integer.MIN_VALUE),
                  ToStringUtil.getString(Integer.MIN_VALUE));
          final Appendable appendable=new StringBuilder(11);
          try{
              ToStringUtil.getString(Integer.MIN_VALUE,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter;
          ToStringUtil.getString(Integer.MIN_VALUE,charArrayWriter=new CharArrayWriter(11));
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(Integer.MIN_VALUE,charBuffer=CharBuffer.allocate(11));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(Integer.MIN_VALUE,byteBuffer=ByteBuffer.allocate(11));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(Integer.MIN_VALUE,stringWriter1=new StringWriter(11));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(Integer.MIN_VALUE,new PrintWriter(stringWriter2=new StringWriter(11)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer;
          ToStringUtil.getString(Integer.MIN_VALUE,stringBuffer=new StringBuffer(11));
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder;
          ToStringUtil.getString(Integer.MIN_VALUE,stringBuilder=new StringBuilder(11));
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(11);
          try{
              ToStringUtil.getString(Integer.MIN_VALUE,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Long.toString(Long.MIN_VALUE),ToStringUtil.getString(Long.MIN_VALUE));
          final Appendable appendable=new StringBuilder(20);
          try{
              ToStringUtil.getString(Long.MIN_VALUE,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter;
          ToStringUtil.getString(Long.MIN_VALUE,charArrayWriter=new CharArrayWriter(20));
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(Long.MIN_VALUE,charBuffer=CharBuffer.allocate(20));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(Long.MIN_VALUE,byteBuffer=ByteBuffer.allocate(20));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(Long.MIN_VALUE,stringWriter1=new StringWriter(20));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(Long.MIN_VALUE,new PrintWriter(stringWriter2=new StringWriter(20)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer;
          ToStringUtil.getString(Long.MIN_VALUE,stringBuffer=new StringBuffer(20));
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder;
          ToStringUtil.getString(Long.MIN_VALUE,stringBuilder=new StringBuilder(20));
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(20);
          try{
              ToStringUtil.getString(Long.MIN_VALUE,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
      {
          final String stockVal;
          Assertions.assertEquals(stockVal=Float.toString(Float.MIN_VALUE),ToStringUtil.getString(Float.MIN_VALUE));
          final Appendable appendable=new StringBuilder(15);
          try{
              ToStringUtil.getString(Float.MIN_VALUE,appendable);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,appendable.toString());
          final CharArrayWriter charArrayWriter;
          ToStringUtil.getString(Float.MIN_VALUE,charArrayWriter=new CharArrayWriter(15));
          Assertions.assertEquals(stockVal,charArrayWriter.toString());
          final CharBuffer charBuffer;
          ToStringUtil.getString(Float.MIN_VALUE,charBuffer=CharBuffer.allocate(15));
          EqualityUtil.assertStringsAreEqual(stockVal,charBuffer.array(),0,charBuffer.position());
          final ByteBuffer byteBuffer;
          ToStringUtil.getString(Float.MIN_VALUE,byteBuffer=ByteBuffer.allocate(15));
          EqualityUtil.assertStringsAreEqual(stockVal,byteBuffer.array(),0,byteBuffer.position());
          final StringWriter stringWriter1;
          ToStringUtil.getString(Float.MIN_VALUE,stringWriter1=new StringWriter(15));
          Assertions.assertEquals(stockVal,stringWriter1.toString());
          StringWriter stringWriter2;
          ToStringUtil.getString(Float.MIN_VALUE,new PrintWriter(stringWriter2=new StringWriter(15)));
          Assertions.assertEquals(stockVal,stringWriter2.toString());
          final StringBuffer stringBuffer;
          ToStringUtil.getString(Float.MIN_VALUE,stringBuffer=new StringBuffer(15));
          Assertions.assertEquals(stockVal,stringBuffer.toString());
          final StringBuilder stringBuilder;
          ToStringUtil.getString(Float.MIN_VALUE,stringBuilder=new StringBuilder(15));
          Assertions.assertEquals(stockVal,stringBuilder.toString());
          final Writer writer=new StringWriter(15);
          try{
              ToStringUtil.getString(Float.MIN_VALUE,writer);
          }catch(IOException e){
              Assertions.fail(e);
          }
          Assertions.assertEquals(stockVal,writer.toString());
      }
  }
  @Test
  public void testGetStringFloatByte(){
      // TODO find more test cases that can be skipped
      byte[] b=new byte[15];
      float f=constructFloat(false,128,0);// -infinity
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(false,-127,0);// -0.0f
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(true,128,0);// +infinity
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(true,128,1);// NaN
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      int[] intervals=new int[255];
      intervals[-127+127]=1;
      for(int i=-126;i<=126;++i) {
          intervals[i+127]=17;
      }
      intervals[-9+127]=1;
      intervals[-1+127]=1;
      intervals[3+127]=1;
      intervals[86+127]=1;
      intervals[89+127]=1;
      intervals[102+127]=1;
      intervals[127+127]=1;
      multiThreadedTest(-127,127,(minIncl,maxIncl)->{
          final byte[] buffer=new byte[15];
          for(int i=minIncl;i<=maxIncl;++i) {
              for(int j=0,interval=intervals[i+127];j<=0x7fffff;j+=interval) {
                  final float f1;
                  EqualityUtil.assertStringsAreEqual(Float.toString(f1=constructFloat(true,i,j)),buffer,0,ToStringUtil.getStringFloat(f1,buffer,0));
              }
          }
      });
  }
  @Test
  public void testGetStringShortByte(){
      final short[] testVals=new short[]{Short.MIN_VALUE,-123,1,12,123,1234,12345,Short.MAX_VALUE};
      final byte[] buffer=new byte[11];
      for(short val:testVals){
          EqualityUtil.assertStringsAreEqual(Short.toString(val),buffer,0,ToStringUtil.getStringShort(val,buffer,0));
      }
  }
  @Test
  public void testGetStringIntByte(){
      final int[] testVals=new int[]{Integer.MIN_VALUE,-123,1,12,123,1234,12345,123456,1234567,12345678,123456789,
              1234567890,Integer.MAX_VALUE};
      final byte[] buffer=new byte[11];
      for(int val:testVals){
          EqualityUtil.assertStringsAreEqual(Integer.toString(val),buffer,0,ToStringUtil.getStringInt(val,buffer,0));
      }
  }
  @Test
  public void testGetStringLongByte(){
      final long[] testVals=new long[]{Long.MIN_VALUE,-123L,1L,12L,123L,1234L,12345L,123456L,1234567L,12345678L,
              123456789L,1234567890L,12345678901L,123456789012L,1234567890123L,12345678901234L,123456789012345L,
              1234567890123456L,12345678901234567L,123456789012345678L,Long.MAX_VALUE};
      final byte[] buffer=new byte[20];
      for(long val:testVals){
          EqualityUtil.assertStringsAreEqual(Long.toString(val),buffer,0,ToStringUtil.getStringLong(val,buffer,0));
      }
  }
  @Test
  public void testGetStringBooleanByte(){
      final byte[] buffer;
      EqualityUtil.assertStringsAreEqual(Boolean.toString(false),buffer=new byte[5],0,ToStringUtil.getStringBoolean(false,buffer,0));
      EqualityUtil.assertStringsAreEqual(Boolean.toString(true),buffer,0,ToStringUtil.getStringBoolean(true,buffer,0));
  }
  @Test
  public void testOmniStringBuilderByte(){
      OmniStringBuilderByte builder=new OmniStringBuilderByte(0,new byte[0]);
      builder.uncheckedAppendChar((byte)'a');
      builder.uncheckedAppendChar((byte)'b');
      builder.uncheckedAppendChar((byte)'c');
      builder.uncheckedAppendChar((byte)'d');
      builder.uncheckedAppendChar((byte)'e');
      Assertions.assertEquals(builder.size,5);
      Assertions.assertEquals(builder.buffer[0],(byte)'a');
      Assertions.assertEquals(builder.buffer[1],(byte)'b');
      Assertions.assertEquals(builder.buffer[2],(byte)'c');
      Assertions.assertEquals(builder.buffer[3],(byte)'d');
      Assertions.assertEquals(builder.buffer[4],(byte)'e');
      builder.size=0;
      builder.uncheckedAppendBoolean(false);
      EqualityUtil.assertStringsAreEqual(Boolean.toString(false),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendBoolean(true);
      EqualityUtil.assertStringsAreEqual(Boolean.toString(true),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendCommaAndSpace();
      Assertions.assertEquals(builder.size,2);
      Assertions.assertEquals(builder.buffer[0],(byte)',');
      Assertions.assertEquals(builder.buffer[1],(byte)' ');
      builder.size=0;
      builder.uncheckedAppendFloat(Float.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Float.toString(Float.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(Integer.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Integer.toString(Integer.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(-123);
      EqualityUtil.assertStringsAreEqual(Integer.toString(-123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(123);
      EqualityUtil.assertStringsAreEqual(Integer.toString(123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(Long.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Long.toString(Long.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(-123L);
      EqualityUtil.assertStringsAreEqual(Long.toString(-123L),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(123L);
      EqualityUtil.assertStringsAreEqual(Long.toString(123L),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort(Short.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Short.toString(Short.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort((short)-123);
      EqualityUtil.assertStringsAreEqual(Short.toString((short)-123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort((short)123);
      EqualityUtil.assertStringsAreEqual(Short.toString((short)123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.buffer=new byte[0];
      StringBuilder sb=new StringBuilder();
      for(int i=0;i<100;++i)
      {
        sb.append((float)i);
        builder.uncheckedAppendFloat((float)i);
      }
      EqualityUtil.parallelAssertStringsAreEqual(sb.toString(),builder.buffer,0,builder.size);
      Assertions.assertEquals(builder.toString(),sb.toString());
  }
  @Test
  public void testGetStringFloatChar(){
      // TODO find more test cases that can be skipped
      char[] b=new char[15];
      float f=constructFloat(false,128,0);// -infinity
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(false,-127,0);// -0.0f
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(true,128,0);// +infinity
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      f=constructFloat(true,128,1);// NaN
      EqualityUtil.assertStringsAreEqual(Float.toString(f),b,0,ToStringUtil.getStringFloat(f,b,0));
      int[] intervals=new int[255];
      intervals[-127+127]=1;
      for(int i=-126;i<=126;++i) {
          intervals[i+127]=17;
      }
      intervals[-9+127]=1;
      intervals[-1+127]=1;
      intervals[3+127]=1;
      intervals[86+127]=1;
      intervals[89+127]=1;
      intervals[102+127]=1;
      intervals[127+127]=1;
      multiThreadedTest(-127,127,(minIncl,maxIncl)->{
          final char[] buffer=new char[15];
          for(int i=minIncl;i<=maxIncl;++i) {
              for(int j=0,interval=intervals[i+127];j<=0x7fffff;j+=interval) {
                  final float f1;
                  EqualityUtil.assertStringsAreEqual(Float.toString(f1=constructFloat(true,i,j)),buffer,0,ToStringUtil.getStringFloat(f1,buffer,0));
              }
          }
      });
  }
  @Test
  public void testGetStringShortChar(){
      final short[] testVals=new short[]{Short.MIN_VALUE,-123,1,12,123,1234,12345,Short.MAX_VALUE};
      final char[] buffer=new char[11];
      for(short val:testVals){
          EqualityUtil.assertStringsAreEqual(Short.toString(val),buffer,0,ToStringUtil.getStringShort(val,buffer,0));
      }
  }
  @Test
  public void testGetStringIntChar(){
      final int[] testVals=new int[]{Integer.MIN_VALUE,-123,1,12,123,1234,12345,123456,1234567,12345678,123456789,
              1234567890,Integer.MAX_VALUE};
      final char[] buffer=new char[11];
      for(int val:testVals){
          EqualityUtil.assertStringsAreEqual(Integer.toString(val),buffer,0,ToStringUtil.getStringInt(val,buffer,0));
      }
  }
  @Test
  public void testGetStringLongChar(){
      final long[] testVals=new long[]{Long.MIN_VALUE,-123L,1L,12L,123L,1234L,12345L,123456L,1234567L,12345678L,
              123456789L,1234567890L,12345678901L,123456789012L,1234567890123L,12345678901234L,123456789012345L,
              1234567890123456L,12345678901234567L,123456789012345678L,Long.MAX_VALUE};
      final char[] buffer=new char[20];
      for(long val:testVals){
          EqualityUtil.assertStringsAreEqual(Long.toString(val),buffer,0,ToStringUtil.getStringLong(val,buffer,0));
      }
  }
  @Test
  public void testGetStringBooleanChar(){
      final char[] buffer;
      EqualityUtil.assertStringsAreEqual(Boolean.toString(false),buffer=new char[5],0,ToStringUtil.getStringBoolean(false,buffer,0));
      EqualityUtil.assertStringsAreEqual(Boolean.toString(true),buffer,0,ToStringUtil.getStringBoolean(true,buffer,0));
  }
  @Test
  public void testOmniStringBuilderChar(){
      OmniStringBuilderChar builder=new OmniStringBuilderChar(0,new char[0]);
      builder.uncheckedAppendChar((char)'a');
      builder.uncheckedAppendChar((char)'b');
      builder.uncheckedAppendChar((char)'c');
      builder.uncheckedAppendChar((char)'d');
      builder.uncheckedAppendChar((char)'e');
      Assertions.assertEquals(builder.size,5);
      Assertions.assertEquals(builder.buffer[0],(char)'a');
      Assertions.assertEquals(builder.buffer[1],(char)'b');
      Assertions.assertEquals(builder.buffer[2],(char)'c');
      Assertions.assertEquals(builder.buffer[3],(char)'d');
      Assertions.assertEquals(builder.buffer[4],(char)'e');
      builder.size=0;
      builder.uncheckedAppendBoolean(false);
      EqualityUtil.assertStringsAreEqual(Boolean.toString(false),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendBoolean(true);
      EqualityUtil.assertStringsAreEqual(Boolean.toString(true),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendCommaAndSpace();
      Assertions.assertEquals(builder.size,2);
      Assertions.assertEquals(builder.buffer[0],(char)',');
      Assertions.assertEquals(builder.buffer[1],(char)' ');
      builder.size=0;
      builder.uncheckedAppendFloat(Float.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Float.toString(Float.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(Integer.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Integer.toString(Integer.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(-123);
      EqualityUtil.assertStringsAreEqual(Integer.toString(-123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendInt(123);
      EqualityUtil.assertStringsAreEqual(Integer.toString(123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(Long.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Long.toString(Long.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(-123L);
      EqualityUtil.assertStringsAreEqual(Long.toString(-123L),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendLong(123L);
      EqualityUtil.assertStringsAreEqual(Long.toString(123L),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort(Short.MIN_VALUE);
      EqualityUtil.assertStringsAreEqual(Short.toString(Short.MIN_VALUE),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort((short)-123);
      EqualityUtil.assertStringsAreEqual(Short.toString((short)-123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.uncheckedAppendShort((short)123);
      EqualityUtil.assertStringsAreEqual(Short.toString((short)123),builder.buffer,0,builder.size);
      builder.size=0;
      builder.buffer=new char[0];
      StringBuilder sb=new StringBuilder();
      for(int i=0;i<100;++i)
      {
        sb.append((float)i);
        builder.uncheckedAppendFloat((float)i);
      }
      EqualityUtil.parallelAssertStringsAreEqual(sb.toString(),builder.buffer,0,builder.size);
      Assertions.assertEquals(builder.toString(),sb.toString());
  }
}
