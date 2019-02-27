package omni.util;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.util.ToStringUtil.OmniStringBuilder;
public class ToStringUtilTest{
    private static void assertStringsEqual(char[] buffer,int length,String stockVal){
        Assertions.assertEquals(length,stockVal.length());
        for(int j=0;j < length;++j){
            Assertions.assertEquals(buffer[j],stockVal.charAt(j));
        }
    }
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

    @Test
    public void testGetStringFloat(){
        // TODO find more test cases that can be skipped
        char[] b=new char[15];
        float f=constructFloat(false,128,0);// -infinity
        assertStringsEqual(b,ToStringUtil.getStringFloat(f,b,0),Float.toString(f));
        f=constructFloat(false,-127,0);// -0.0f
        assertStringsEqual(b,ToStringUtil.getStringFloat(f,b,0),Float.toString(f));
        f=constructFloat(true,128,0);// +infinity
        assertStringsEqual(b,ToStringUtil.getStringFloat(f,b,0),Float.toString(f));
        f=constructFloat(true,128,1);// NaN
        assertStringsEqual(b,ToStringUtil.getStringFloat(f,b,0),Float.toString(f));
        
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
              assertStringsEqual(buffer,ToStringUtil.getStringFloat(f1=constructFloat(true,i,j),buffer,0), Float.toString(f1));
            }
          }
        });
        
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
    public void testGetStringShort(){
        final short[] testVals=new short[]{Short.MIN_VALUE,-123,1,12,123,1234,12345,Short.MAX_VALUE};
        final char[] buffer=new char[11];
        for(short val:testVals){
            assertStringsEqual(buffer,ToStringUtil.getStringShort(val,buffer,0),Short.toString(val));
        }
    }
    @Test
    public void testGetStringInt(){
        final int[] testVals=new int[]{Integer.MIN_VALUE,-123,1,12,123,1234,12345,123456,1234567,12345678,123456789,
                1234567890,Integer.MAX_VALUE};
        final char[] buffer=new char[11];
        for(int val:testVals){
            assertStringsEqual(buffer,ToStringUtil.getStringInt(val,buffer,0),Integer.toString(val));
        }
    }
    @Test
    public void testGetStringLong(){
        final long[] testVals=new long[]{Long.MIN_VALUE,-123L,1L,12L,123L,1234L,12345L,123456L,1234567L,12345678L,
                123456789L,1234567890L,12345678901L,123456789012L,1234567890123L,12345678901234L,123456789012345L,
                1234567890123456L,12345678901234567L,123456789012345678L,Long.MAX_VALUE};
        final char[] buffer=new char[20];
        for(long val:testVals){
            assertStringsEqual(buffer,ToStringUtil.getStringLong(val,buffer,0),Long.toString(val));
        }
    }
    @Test
    public void testGetStringBoolean(){
        final char[] buffer;
        assertStringsEqual(buffer=new char[5],ToStringUtil.getStringBoolean(false,buffer,0),Boolean.toString(false));
        assertStringsEqual(buffer,ToStringUtil.getStringBoolean(true,buffer,0),Boolean.toString(true));
    }
    @Test
    public void testOmniStringBuilder(){
        OmniStringBuilder builder=new OmniStringBuilder(0,new char[0]);
        builder.uncheckedAppend('a');
        builder.uncheckedAppend('b');
        builder.uncheckedAppend('c');
        builder.uncheckedAppend('d');
        builder.uncheckedAppend('e');
        Assertions.assertEquals(builder.size,5);
        Assertions.assertEquals(builder.buffer[0],'a');
        Assertions.assertEquals(builder.buffer[1],'b');
        Assertions.assertEquals(builder.buffer[2],'c');
        Assertions.assertEquals(builder.buffer[3],'d');
        Assertions.assertEquals(builder.buffer[4],'e');
        builder.size=0;
        builder.uncheckedAppend(false);
        assertStringsEqual(builder.buffer,builder.size,Boolean.toString(false));
        builder.size=0;
        builder.uncheckedAppend(true);
        assertStringsEqual(builder.buffer,builder.size,Boolean.toString(true));
        builder.size=0;
        builder.uncheckedAppendCommaAndSpace();
        Assertions.assertEquals(builder.size,2);
        Assertions.assertEquals(builder.buffer[0],',');
        Assertions.assertEquals(builder.buffer[1],' ');
        builder.size=0;
        builder.uncheckedAppendFloat(Float.MIN_VALUE);
        Assertions.assertEquals(builder.toString(),Float.toString(Float.MIN_VALUE));
        builder.size=0;
        builder.uncheckedAppendInt(Integer.MIN_VALUE);
        Assertions.assertEquals(builder.toString(),Integer.toString(Integer.MIN_VALUE));
        builder.size=0;
        builder.uncheckedAppendInt(-123);
        Assertions.assertEquals(builder.toString(),Integer.toString(-123));
        builder.size=0;
        builder.uncheckedAppendInt(123);
        Assertions.assertEquals(builder.toString(),Integer.toString(123));
        builder.size=0;
        builder.uncheckedAppendLong(Long.MIN_VALUE);
        Assertions.assertEquals(builder.toString(),Long.toString(Long.MIN_VALUE));
        builder.size=0;
        builder.uncheckedAppendLong(-123L);
        Assertions.assertEquals(builder.toString(),Long.toString(-123L));
        builder.size=0;
        builder.uncheckedAppendLong(123L);
        Assertions.assertEquals(builder.toString(),Long.toString(123L));
        builder.size=0;
        builder.uncheckedAppendShort(Short.MIN_VALUE);
        Assertions.assertEquals(builder.toString(),Short.toString(Short.MIN_VALUE));
        builder.size=0;
        builder.uncheckedAppendShort((short)-123);
        Assertions.assertEquals(builder.toString(),Short.toString((short)-123));
        builder.size=0;
        builder.uncheckedAppendShort((short)123);
        Assertions.assertEquals(builder.toString(),Short.toString((short)123));
    }
    @Test
    public void testGetStringConvenienceMethods(){
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
            assertStringsEqual(charBuffer.array(),charBuffer.position(),stockVal);
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
            assertStringsEqual(charBuffer.array(),charBuffer.position(),stockVal);
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
            assertStringsEqual(charBuffer.array(),charBuffer.position(),stockVal);
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
            assertStringsEqual(charBuffer.array(),charBuffer.position(),stockVal);
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
}
