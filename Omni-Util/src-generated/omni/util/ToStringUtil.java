package omni.util;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
public class ToStringUtil{
    public static final Charset IOS8859CharSet=Charset.forName("ISO-8859-1");
    private ToStringUtil(){}
    public static String getString(boolean b){
        if(b){
            return "true";
        }
        return "false";
    }
    public static void getString(boolean b,Appendable appendable) throws IOException{
        appendable.append(b?"true":"false");
    }
    public static void getString(boolean b,CharArrayWriter writer) throws IOException{
        writer.write(b?"true":"false");
    }
    public static void getString(boolean b,CharBuffer buffer){
        buffer.put(b?"true":"false");
    }
    public static void getString(boolean b,ByteBuffer buffer){
        buffer.put(b?new byte[]{(byte)'t',(byte)'r',(byte)'u',(byte)'e'}:new byte[]{(byte)'f',(byte)'a',(byte)'l',(byte)'s',(byte)'e'});
    }
    public static void getString(boolean b,PrintWriter writer){
        writer.write(b?"true":"false");
    }
    public static void getString(boolean b,StringWriter writer){
        writer.write(b?"true":"false");
    }
    public static void getString(boolean b,Writer writer) throws IOException{
        writer.write(b?"true":"false");
    }
    public static String getString(byte i){
        byte[] buffer;
        return new String(buffer=getByteByteBuffer(),0,getStringShort(i,buffer,0));
    }
    public static void getString(byte i,Appendable appendable) throws IOException{
        appendable.append(getString(i));
    }
    public static void getString(byte i,CharArrayWriter writer){
        char[] arr;
        writer.write(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,CharBuffer buffer){
        char[] arr;
        buffer.put(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,ByteBuffer buffer){
        byte[] arr;
        buffer.put(arr=getByteByteBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,PrintWriter writer){
        char[] arr;
        writer.write(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,StringBuffer sBuffer){
        char[] arr;
        sBuffer.append(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,StringBuilder sBuilder){
        char[] arr;
        sBuilder.append(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,StringWriter writer){
        char[] arr;
        writer.write(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(byte i,Writer writer) throws IOException{
        char[] arr;
        writer.write(arr=getByteCharBuffer(),0,getStringShort(i,arr,0));
    }
    /** Return the string representation of the provided single-precision float value.
     *
     * @param f
     *          The float value.
     * @return The string for that float value. */
    public static String getString(float f){
        byte[] buffer;
        return new String(buffer=getFloatByteBuffer(),0,getStringFloat(f,buffer,0));
    }
    public static void getString(float f,Appendable appendable) throws IOException{
        appendable.append(getString(f));
    }
    public static void getString(float f,CharArrayWriter writer){
        char[] arr;
        writer.write(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,CharBuffer buffer){
        char[] arr;
        buffer.put(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,ByteBuffer buffer){
        byte[] arr;
        buffer.put(arr=getFloatByteBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,PrintWriter writer){
        char[] arr;
        writer.write(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    /** Write a float string representation to a StringBuffer.
     *
     * @param f
     * @param sBuffer
     */
    public static void getString(float f,StringBuffer sBuffer){
        char[] arr;
        sBuffer.append(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,StringBuilder sBuilder){
        char[] arr;
        sBuilder.append(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,StringWriter writer){
        char[] arr;
        writer.write(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static void getString(float f,Writer writer) throws IOException{
        char[] arr;
        writer.write(arr=getFloatCharBuffer(),0,getStringFloat(f,arr,0));
    }
    public static String getString(int i){
        byte[] buffer;
        return new String(buffer=getIntByteBuffer(),0,getStringInt(i,buffer,0));
    }
    public static void getString(int i,Appendable appendable) throws IOException{
        appendable.append(getString(i));
    }
    public static void getString(int i,CharArrayWriter writer){
        char[] arr;
        writer.write(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,CharBuffer buffer){
        char[] arr;
        buffer.put(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,ByteBuffer buffer){
        byte[] arr;
        buffer.put(arr=getIntByteBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,PrintWriter writer){
        char[] arr;
        writer.write(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,StringBuffer sBuffer){
        char[] arr;
        sBuffer.append(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,StringBuilder sBuilder){
        char[] arr;
        sBuilder.append(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,StringWriter writer){
        char[] arr;
        writer.write(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static void getString(int i,Writer writer) throws IOException{
        char[] arr;
        writer.write(arr=getIntCharBuffer(),0,getStringInt(i,arr,0));
    }
    public static String getString(long i){
        byte[] buffer;
        return new String(buffer=getLongByteBuffer(),0,getStringLong(i,buffer,0));
    }
    public static void getString(long i,Appendable appendable) throws IOException{
        appendable.append(getString(i));
    }
    public static void getString(long i,CharArrayWriter writer){
        char[] arr;
        writer.write(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,CharBuffer buffer){
        char[] arr;
        buffer.put(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,ByteBuffer buffer){
        byte[] arr;
        buffer.put(arr=getLongByteBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,PrintWriter writer){
        char[] arr;
        writer.write(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,StringBuffer sBuffer){
        char[] arr;
        sBuffer.append(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,StringBuilder sBuilder){
        char[] arr;
        sBuilder.append(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,StringWriter writer){
        char[] arr;
        writer.write(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static void getString(long i,Writer writer) throws IOException{
        char[] arr;
        writer.write(arr=getLongCharBuffer(),0,getStringLong(i,arr,0));
    }
    public static String getString(short i){
        byte[] buffer;
        return new String(buffer=getShortByteBuffer(),0,getStringShort(i,buffer,0));
    }
    public static void getString(short i,Appendable appendable) throws IOException{
        appendable.append(getString(i));
    }
    public static void getString(short i,CharArrayWriter writer){
        char[] arr;
        writer.write(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,CharBuffer buffer){
        char[] arr;
        buffer.put(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,ByteBuffer buffer){
        byte[] arr;
        buffer.put(arr=getShortByteBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,PrintWriter writer){
        char[] arr;
        writer.write(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,StringBuffer sBuffer){
        char[] arr;
        sBuffer.append(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,StringBuilder sBuilder){
        char[] arr;
        sBuilder.append(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,StringWriter writer){
        char[] arr;
        writer.write(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    public static void getString(short i,Writer writer) throws IOException{
        char[] arr;
        writer.write(arr=getShortCharBuffer(),0,getStringShort(i,arr,0));
    }
    /** A streamlined method for getting the number of trailing 0 bits in the fraction of a single-precision
     * floating-point number
     *
     * @param fractBits
     *          The fraction bits of a floating point number.
     * @return the number of trailing zeros. */
    private static int fpFractNumTrailing0s(final int fractBits){
        int shift=31;
        for(;;){
            if(fractBits<<shift!=0){ return 31-shift; }
            --shift;
        }
    }
    // TODO benchmark
    // private static int div10(int val){
    // // TODO optimize the multiplication here
    // return val*52429>>>19;
    // // int tmp=((tmp=((tmp=(val<<3)+(val<<4))<<4)+tmp)<<8)+tmp+val>>>19;
    // // return tmp;
    // }
    // // TODO benchmark
    // static int mult10(int val){
    // return (val<<3)+(val<<1);
    // }
    // // TODO benchmark
    // static long mult10(long val){
    // return (val<<3)+(val<<1);
    // }
    // // TODO benchmark
    // static int mult100(int val){
    // return (val<<6)+(val<<5)+(val<<2);
    // }
    // // TODO benchmark
    // static long mult100(long val){
    // return (val<<6)+(val<<5)+(val<<2);
    // }
    private static int getStringSize(int val){
        final int[] sizeTable=INT_SIZE_TABLE;
        int index=0;
        for(;;){
            if(val<=sizeTable[index]){ return index+1; }
            ++index;
        }
    }
    private static int getStringSize(long val){
        long p=10L;
        int i=1;
        for(;;){
            if(val<p){ return i; }
            // multiply by 10
            // p=mult10(p);
            p*=10;
            if(++i==19){ return 19; }
        }
    }
    public static class OmniStringBuilderByte{
        public transient byte[] buffer;
        public transient int size;
        public OmniStringBuilderByte(int size,byte[] buffer){
            this.size=size;
            this.buffer=buffer;
        }
        @Override
        public String toString(){
            return new String(buffer,0,size);
        }
        public void uncheckedAppendBoolean(boolean val){
            if(val){
                uncheckedAppendTrue();
            }else{
                uncheckedAppendFalse();
            }
        }
        public void uncheckedAppendChar(byte val){
            byte[] buffer;
            final int currSize;
            if((currSize=this.size) == (buffer=this.buffer).length){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new byte[OmniArray.growBy100Pct(currSize)],0,currSize);
                this.buffer=buffer;
            }
            buffer[currSize]=val;
            this.size=currSize + 1;
        }
        public void uncheckedAppendCommaAndSpace(){
            final int currSize;
            final byte[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 2))[currSize]=',';
            buffer[currSize + 1]=(byte)' ';
        }
        private byte[] growBuffer(int currSize,int newSize){
            final int capacity;
            byte[] buffer;
            if(newSize - (capacity=(buffer=this.buffer).length) > 0){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new byte[OmniArray.growBy100Pct(capacity,newSize)],0,currSize);
                this.buffer=buffer;
            }
            this.size=newSize;
            return buffer;
        }
        public void uncheckedAppendFloat(float val){
            // TODO handle overflow better
            byte[] buffer;
            final int currSize,capacity,newSize;
            if((newSize=(currSize=this.size) + 15) - (capacity=(buffer=this.buffer).length) > 0){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new byte[OmniArray.growBy100Pct(capacity,newSize)],0,currSize);
                this.buffer=buffer;
            }
            this.size=getStringFloat(val,buffer,currSize);
        }
        // TODO uncheckedAppendDouble
        public void uncheckedAppendInt(int val){
            final byte[] buffer;
            final int newSize,currSize=this.size;
            if(val < 0){
                if(val == Integer.MIN_VALUE){
                    uncheckedAppendIntMinVal(currSize);
                    return;
                }
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            mediumDigits(val,buffer,newSize);
        }
        public void uncheckedAppendLong(long val){
            final byte[] buffer;
            final int newSize,currSize=size;
            if(val < 0L){
                if(val == Long.MIN_VALUE){
                    uncheckedAppendLongMinVal(currSize);
                    return;
                }
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            largeDigits(val,buffer,newSize);
        }
        public void uncheckedAppendShort(int val){
            final byte[] buffer;
            final int newSize,currSize=size;
            if(val < 0){
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            smallDigits(val,buffer,newSize);
        }
        private void uncheckedAppendFalse(){
            final byte[] buffer;
            final int currSize;
            (buffer=growBuffer(currSize=this.size,currSize + 5))[currSize]=(byte)'f';
            buffer[currSize + 1]=(byte)'a';
            buffer[currSize + 2]=(byte)'l';
            buffer[currSize + 3]=(byte)'s';
            buffer[currSize + 4]=(byte)'e';
        }
        private void uncheckedAppendIntMinVal(int currSize){
            final byte[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 11))[currSize]=(byte)'-';
            buffer[++currSize]=(byte)'2';
            buffer[++currSize]=(byte)'1';
            buffer[++currSize]=(byte)'4';
            buffer[++currSize]=(byte)'7';
            buffer[++currSize]=(byte)'4';
            buffer[++currSize]=(byte)'8';
            buffer[++currSize]=(byte)'3';
            buffer[++currSize]=(byte)'6';
            buffer[++currSize]=(byte)'4';
            buffer[++currSize]=(byte)'8';
        }
        private void uncheckedAppendLongMinVal(int currSize){
            final byte[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 20))[currSize]=(byte)'-';
            buffer[++currSize]=(byte)'9';
            buffer[++currSize]=(byte)'2';
            buffer[++currSize]=(byte)'2';
            buffer[++currSize]=(byte)'3';
            buffer[++currSize]=(byte)'3';
            buffer[++currSize]=(byte)'7';
            buffer[++currSize]=(byte)'2';
            buffer[++currSize]=(byte)'0';
            buffer[++currSize]=(byte)'3';
            buffer[++currSize]=(byte)'6';
            buffer[++currSize]=(byte)'8';
            buffer[++currSize]=(byte)'5';
            buffer[++currSize]=(byte)'4';
            buffer[++currSize]=(byte)'7';
            buffer[++currSize]=(byte)'7';
            buffer[++currSize]=(byte)'5';
            buffer[++currSize]=(byte)'8';
            buffer[++currSize]=(byte)'0';
            buffer[++currSize]=(byte)'8';
        }
        private void uncheckedAppendTrue(){
            final byte[] buffer;
            final int currSize;
            (buffer=growBuffer(currSize=this.size,currSize + 4))[currSize]=(byte)'t';
            buffer[currSize + 1]=(byte)'r';
            buffer[currSize + 2]=(byte)'u';
            buffer[currSize + 3]=(byte)'e';
        }
    }
    public static class OmniStringBuilderChar{
        public transient char[] buffer;
        public transient int size;
        public OmniStringBuilderChar(int size,char[] buffer){
            this.size=size;
            this.buffer=buffer;
        }
        @Override
        public String toString(){
            return new String(buffer,0,size);
        }
        public void uncheckedAppendBoolean(boolean val){
            if(val){
                uncheckedAppendTrue();
            }else{
                uncheckedAppendFalse();
            }
        }
        public void uncheckedAppendChar(char val){
            char[] buffer;
            final int currSize;
            if((currSize=this.size) == (buffer=this.buffer).length){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new char[OmniArray.growBy100Pct(currSize)],0,currSize);
                this.buffer=buffer;
            }
            buffer[currSize]=val;
            this.size=currSize + 1;
        }
        public void uncheckedAppendCommaAndSpace(){
            final int currSize;
            final char[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 2))[currSize]=',';
            buffer[currSize + 1]=(char)' ';
        }
        private char[] growBuffer(int currSize,int newSize){
            final int capacity;
            char[] buffer;
            if(newSize - (capacity=(buffer=this.buffer).length) > 0){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new char[OmniArray.growBy100Pct(capacity,newSize)],0,currSize);
                this.buffer=buffer;
            }
            this.size=newSize;
            return buffer;
        }
        public void uncheckedAppendFloat(float val){
            // TODO handle overflow better
            char[] buffer;
            final int currSize,capacity,newSize;
            if((newSize=(currSize=this.size) + 15) - (capacity=(buffer=this.buffer).length) > 0){
                ArrCopy.semicheckedCopy(buffer,0,buffer=new char[OmniArray.growBy100Pct(capacity,newSize)],0,currSize);
                this.buffer=buffer;
            }
            this.size=getStringFloat(val,buffer,currSize);
        }
        // TODO uncheckedAppendDouble
        public void uncheckedAppendInt(int val){
            final char[] buffer;
            final int newSize,currSize=this.size;
            if(val < 0){
                if(val == Integer.MIN_VALUE){
                    uncheckedAppendIntMinVal(currSize);
                    return;
                }
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            mediumDigits(val,buffer,newSize);
        }
        public void uncheckedAppendLong(long val){
            final char[] buffer;
            final int newSize,currSize=size;
            if(val < 0L){
                if(val == Long.MIN_VALUE){
                    uncheckedAppendLongMinVal(currSize);
                    return;
                }
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            largeDigits(val,buffer,newSize);
        }
        public void uncheckedAppendShort(int val){
            final char[] buffer;
            final int newSize,currSize=size;
            if(val < 0){
                (buffer=growBuffer(currSize,newSize=currSize + 1 + getStringSize(val=-val)))[currSize]='-';
            }else{
                buffer=growBuffer(currSize,newSize=currSize + getStringSize(val));
            }
            smallDigits(val,buffer,newSize);
        }
        private void uncheckedAppendFalse(){
            final char[] buffer;
            final int currSize;
            (buffer=growBuffer(currSize=this.size,currSize + 5))[currSize]=(char)'f';
            buffer[currSize + 1]=(char)'a';
            buffer[currSize + 2]=(char)'l';
            buffer[currSize + 3]=(char)'s';
            buffer[currSize + 4]=(char)'e';
        }
        private void uncheckedAppendIntMinVal(int currSize){
            final char[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 11))[currSize]=(char)'-';
            buffer[++currSize]=(char)'2';
            buffer[++currSize]=(char)'1';
            buffer[++currSize]=(char)'4';
            buffer[++currSize]=(char)'7';
            buffer[++currSize]=(char)'4';
            buffer[++currSize]=(char)'8';
            buffer[++currSize]=(char)'3';
            buffer[++currSize]=(char)'6';
            buffer[++currSize]=(char)'4';
            buffer[++currSize]=(char)'8';
        }
        private void uncheckedAppendLongMinVal(int currSize){
            final char[] buffer;
            (buffer=growBuffer(currSize=this.size,currSize + 20))[currSize]=(char)'-';
            buffer[++currSize]=(char)'9';
            buffer[++currSize]=(char)'2';
            buffer[++currSize]=(char)'2';
            buffer[++currSize]=(char)'3';
            buffer[++currSize]=(char)'3';
            buffer[++currSize]=(char)'7';
            buffer[++currSize]=(char)'2';
            buffer[++currSize]=(char)'0';
            buffer[++currSize]=(char)'3';
            buffer[++currSize]=(char)'6';
            buffer[++currSize]=(char)'8';
            buffer[++currSize]=(char)'5';
            buffer[++currSize]=(char)'4';
            buffer[++currSize]=(char)'7';
            buffer[++currSize]=(char)'7';
            buffer[++currSize]=(char)'5';
            buffer[++currSize]=(char)'8';
            buffer[++currSize]=(char)'0';
            buffer[++currSize]=(char)'8';
        }
        private void uncheckedAppendTrue(){
            final char[] buffer;
            final int currSize;
            (buffer=growBuffer(currSize=this.size,currSize + 4))[currSize]=(char)'t';
            buffer[currSize + 1]=(char)'r';
            buffer[currSize + 2]=(char)'u';
            buffer[currSize + 3]=(char)'e';
        }
    }
        private final static char[] DIGIT_TENSChar={(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'0',(char)'1',(char)'1',(char)'1',(char)'1',(char)'1',(char)'1',(char)'1',(char)'1',(char)'1',
                (char)'1',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'2',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'3',(char)'4',(char)'4',(char)'4',(char)'4',(char)'4',(char)'4',
                (char)'4',(char)'4',(char)'4',(char)'4',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'5',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'6',(char)'7',(char)'7',(char)'7',
                (char)'7',(char)'7',(char)'7',(char)'7',(char)'7',(char)'7',(char)'7',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'8',(char)'9',(char)'9',(char)'9',(char)'9',(char)'9',(char)'9',(char)'9',(char)'9',(char)'9',
                (char)'9',};
        private final static char[] DIGIT_ONESChar={(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',
                (char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',
                (char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',
                (char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',(char)'9',(char)'0',(char)'1',(char)'2',(char)'3',(char)'4',(char)'5',(char)'6',(char)'7',(char)'8',
                (char)'9',};
        /** Place the string representation of the provided boolean value into the provided char buffer.
         *
         * @param i
         *          The boolean value to get the string of.
         * @param buffer
         *          The char buffer.
         * @param offset
         *          The first index to begin inserting chars in the char buffer.
         * @return The last index into which a char was inserted PLUS ONE. */
        public static int getStringBoolean(boolean i,char[] buffer,int offset){
            if(i){
                buffer[offset]=(char)'t';
                buffer[++offset]=(char)'r';
                buffer[++offset]=(char)'u';
            }else{
                buffer[offset]=(char)'f';
                buffer[++offset]=(char)'a';
                buffer[++offset]=(char)'l';
                buffer[++offset]=(char)'s';
            }
            buffer[++offset]=(char)'e';
            return ++offset;
        }
        /** Place the string representation of the provided single-precision float value into the provided char buffer.
         *
         * @param f
         *          The single-precision float value to get the string of.
         * @param buffer
         *          The char buffer.
         * @param offset
         *          The first index to begin inserting chars in the char buffer.
         * @return The last index into which a char was inserted PLUS ONE. */
        public static int getStringFloat(final float f,final char[] buffer,int offset){
            final int rawBits;
            final int binaryExponent=(((rawBits=Float.floatToRawIntBits(f))&0x7f800000)>>23)-127;
            if(binaryExponent==128){ return infinityOrNaNToASCII(buffer,offset,rawBits); }
            if((rawBits&Integer.MIN_VALUE)!=0){
                buffer[offset++]=(char)'-';
            }
            final int fractBits=rawBits&0x7fffff;
            if(binaryExponent==-127){
                if(fractBits==0){
                    buffer[offset]=(char)'0';
                    buffer[++offset]=(char)'.';
                    buffer[++offset]=(char)'0';
                    return ++offset;
                }
                return FltDenorm.toASCII(fractBits,buffer,offset);
            }
            if(binaryExponent<0){ return FltLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            return FltGTE1.toASCII(fractBits,binaryExponent,buffer,offset);
        }
        /** Place the string representation of the provided int value into the provided char buffer.
         *
         * @param i
         *          The int value to get the string of.
         * @param buffer
         *          The char buffer.
         * @param offset
         *          The first index to begin inserting chars in the char buffer.
         * @return The last index into which a char was inserted PLUS ONE. */
        public static int getStringInt(int i,char[] buffer,int offset){
            if(i<0){
                buffer[offset++]=(char)'-';
                if(i==Integer.MIN_VALUE){
                    buffer[offset]=(char)'2';
                    buffer[++offset]=(char)'1';
                    buffer[++offset]=(char)'4';
                    buffer[++offset]=(char)'7';
                    buffer[++offset]=(char)'4';
                    buffer[++offset]=(char)'8';
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'6';
                    buffer[++offset]=(char)'4';
                    buffer[++offset]=(char)'8';
                    return ++offset;
                }
                i=-i;
            }
            mediumDigits(i,buffer,offset+=getStringSize(i));
            return offset;
        }
        /** Place the string representation of the provided long value into the provided char buffer.
         *
         * @param l
         *          The long value to get the string of.
         * @param buffer
         *          The char buffer.
         * @param offset
         *          The first index to begin inserting chars in the char buffer.
         * @return The last index into which a char was inserted PLUS ONE. */
        public static int getStringLong(long l,char[] buffer,int offset){
            if(l<0L){
                buffer[offset++]=(char)'-';
                if(l==Long.MIN_VALUE){
                    buffer[offset]=(char)'9';
                    buffer[++offset]=(char)'2';
                    buffer[++offset]=(char)'2';
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'7';
                    buffer[++offset]=(char)'2';
                    buffer[++offset]=(char)'0';
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'6';
                    buffer[++offset]=(char)'8';
                    buffer[++offset]=(char)'5';
                    buffer[++offset]=(char)'4';
                    buffer[++offset]=(char)'7';
                    buffer[++offset]=(char)'7';
                    buffer[++offset]=(char)'5';
                    buffer[++offset]=(char)'8';
                    buffer[++offset]=(char)'0';
                    buffer[++offset]=(char)'8';
                    return ++offset;
                }
                l=-l;
            }
            largeDigits(l,buffer,offset+=getStringSize(l));
            return offset;
        }
        /** Place the string representation of the provided long value into the provided char buffer.
         *
         * @param l
         *          The long value to get the string of.
         * @param buffer
         *          The char buffer.
         * @param offset
         *          The first index to begin inserting chars in the char buffer.
         * @return The last index into which a char was inserted PLUS ONE. */
        public static int getStringShort(int i,char[] buffer,int offset){
            if(i<0){
                buffer[offset++]=(char)'-';
                i=-i;
            }
            smallDigits(i,buffer,offset+=getStringSize(i));
            return offset;
        }
        /** Insert 0's into a char buffer.
         *
         * @param offset
         *          The index in the buffer to insert the first 0.
         * @param buffer
         *          The buffer to insert 0's into.
         * @param bound
         *          The last index into which a zero will be placed PLUS ONE.
         * @return The {@code bound} parameter. */
        private static int fillZeros(int offset,final char[] buffer,final int bound){
            for(;;){
                if(offset==bound){ return offset; }
                buffer[++offset]=(char)'0';
            }
        }
        private static char[] getByteCharBuffer(){
            return new char[4];
        }
        private static char[] getFloatCharBuffer(){
            // TODO see if this can/should be made thread-localized
            // (preferably without using ThreadLocal, which is too slow).
            return new char[15];
        }
        private static char[] getIntCharBuffer(){
            // TODO see if this can/should be made thread-localized
            // (preferably without using ThreadLocal, which is too slow).
            return new char[11];
        }
        private static char[] getLongCharBuffer(){
            return new char[20];
        }
        private static char[] getShortCharBuffer(){
            return new char[6];
        }
        /** Print the characters for "Infinity" or "NaN" (positive or negative).
         *
         * @param buffer
         *          The char buffer to point to.
         * @param offset
         *          The first index to insert chars.
         * @param rawBits
         *          The raw bits provided by Float.floatToRawIntBits(float)
         * @return The offset in the buffer just after the index where the last character was inserted. */
        private static int infinityOrNaNToASCII(final char[] buffer,int offset,final int rawBits){
            if((rawBits&0x7fffff)==0){
                if((rawBits&Integer.MIN_VALUE)!=0){
                    buffer[offset++]=(char)'-';
                }
                buffer[offset]=(char)'I';
                buffer[++offset]=(char)'n';
                buffer[++offset]=(char)'f';
                buffer[++offset]=(char)'i';
                buffer[++offset]=(char)'n';
                buffer[++offset]=(char)'i';
                buffer[++offset]=(char)'t';
                buffer[++offset]=(char)'y';
            }else{
                buffer[offset]=(char)'N';
                buffer[++offset]=(char)'a';
                buffer[++offset]=(char)'N';
            }
            return ++offset;
        }
        private static void largeDigits(long val,char[] buffer,int digitOffset){
            for(;;){
                if(val<2147483648L){
                    mediumDigits((int)val,buffer,digitOffset);
                    break;
                }
                // TODO we could use multiplication to do this for values less than
                // 4908534099L. Would this be faster?
                //// final int tmp=(int)(((long)val*1374389535)>>>37);
                // final long tmp=val/100;
                // final int r=(int)(val-(tmp<<6)+(tmp<<5)+(tmp<<2));
                long tmp;
                int r;
                buffer[--digitOffset]=DIGIT_ONESChar[r=(int)(val-(tmp=val/100)*100)];
                buffer[--digitOffset]=DIGIT_TENSChar[r];
                val=tmp;
            }
        }
        private static void mediumDigits(int val,char[] buffer,int digitOffset){
            for(;;){
                // TODO the stock Integer class uses 66536 as the threshold, but values
                // should
                // be accurate up to 81920 even if the multplication technique were used.
                // Would performance benefit if this were changed?
                if(val<81920)
                    // if(val<65536)
                {
                    smallDigits(val,buffer,digitOffset);
                    break;
                }
                // two digits per iteration
                // TODO Would it be faster to promote to long and use multiplication to
                // divide?
                // TODO if this is okay, then perhaps consider the same multiplication
                // with the shift-add technique
                // (valid for val < 4908534099L
                // final int tmp=(int)(((long)val*1374389535)>>>37);
                int tmp;
                // buffer[--digitOffset]=DIGIT_ONESChar[val-=mult100(tmp=val/100)];
                buffer[--digitOffset]=DIGIT_ONESChar[val-=(tmp=val/100)*100];
                buffer[--digitOffset]=DIGIT_TENSChar[val];
                val=tmp;
            }
        }
        /**
         * <p>
         * This shift-add technique was shamelessly ripped from {@link java.lang.Integer Integer}. It is equivalent to:
         *
         * <pre>
         * int tmp=(val*52429)>>>19;
         * char c=(char)(((tmp<<3)+(q<<1))+'0');
         * </pre>
         *
         * This is as it appears in {@link java.lang.Integer Integer}. It is, in turn, equivalent to
         *
         * <pre>
         *   int tmp = val / 10;
         *   char c  = (char)(( val - ( tmp * 10 ) + '0' );
         * </pre>
         *
         * which is in turn equivalent to
         *
         * <pre>
         * char c=val%10;
         * </pre>
         *
         * We want to avoid div and mod at all costs.
         * </p>
         * TODO: check the performance of this against other techniques
         *
         * @param buffer
         *          The character buffer into which the character will be placed.
         * @param digitOffset
         *          The index at which to place the character.
         * @param val
         *          The integer value from which to pull the lowest-order decimal digit. */
        private static void smallDigits(int val,char[] buffer,int digitOffset){
            for(;;){
                if(val<10){
                    buffer[--digitOffset]=(char)(val+'0');
                    return;
                }
                buffer[--digitOffset]=(char)(val-(val=val*52429>>>19)*10+'0');
            }
        }
        private final static byte[] DIGIT_TENSByte={(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'0',(byte)'1',(byte)'1',(byte)'1',(byte)'1',(byte)'1',(byte)'1',(byte)'1',(byte)'1',(byte)'1',
                (byte)'1',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'2',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'3',(byte)'4',(byte)'4',(byte)'4',(byte)'4',(byte)'4',(byte)'4',
                (byte)'4',(byte)'4',(byte)'4',(byte)'4',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'5',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'6',(byte)'7',(byte)'7',(byte)'7',
                (byte)'7',(byte)'7',(byte)'7',(byte)'7',(byte)'7',(byte)'7',(byte)'7',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'8',(byte)'9',(byte)'9',(byte)'9',(byte)'9',(byte)'9',(byte)'9',(byte)'9',(byte)'9',(byte)'9',
                (byte)'9',};
        private final static byte[] DIGIT_ONESByte={(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',
                (byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',
                (byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',
                (byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',(byte)'9',(byte)'0',(byte)'1',(byte)'2',(byte)'3',(byte)'4',(byte)'5',(byte)'6',(byte)'7',(byte)'8',
                (byte)'9',};
        /** Place the string representation of the provided boolean value into the provided byte buffer.
         *
         * @param i
         *          The boolean value to get the string of.
         * @param buffer
         *          The byte buffer.
         * @param offset
         *          The first index to begin inserting bytes in the byte buffer.
         * @return The last index into which a byte was inserted PLUS ONE. */
        public static int getStringBoolean(boolean i,byte[] buffer,int offset){
            if(i){
                buffer[offset]=(byte)'t';
                buffer[++offset]=(byte)'r';
                buffer[++offset]=(byte)'u';
            }else{
                buffer[offset]=(byte)'f';
                buffer[++offset]=(byte)'a';
                buffer[++offset]=(byte)'l';
                buffer[++offset]=(byte)'s';
            }
            buffer[++offset]=(byte)'e';
            return ++offset;
        }
        /** Place the string representation of the provided single-precision float value into the provided byte buffer.
         *
         * @param f
         *          The single-precision float value to get the string of.
         * @param buffer
         *          The byte buffer.
         * @param offset
         *          The first index to begin inserting bytes in the byte buffer.
         * @return The last index into which a byte was inserted PLUS ONE. */
        public static int getStringFloat(final float f,final byte[] buffer,int offset){
            final int rawBits;
            final int binaryExponent=(((rawBits=Float.floatToRawIntBits(f))&0x7f800000)>>23)-127;
            if(binaryExponent==128){ return infinityOrNaNToASCII(buffer,offset,rawBits); }
            if((rawBits&Integer.MIN_VALUE)!=0){
                buffer[offset++]=(byte)'-';
            }
            final int fractBits=rawBits&0x7fffff;
            if(binaryExponent==-127){
                if(fractBits==0){
                    buffer[offset]=(byte)'0';
                    buffer[++offset]=(byte)'.';
                    buffer[++offset]=(byte)'0';
                    return ++offset;
                }
                return FltDenorm.toASCII(fractBits,buffer,offset);
            }
            if(binaryExponent<0){ return FltLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            return FltGTE1.toASCII(fractBits,binaryExponent,buffer,offset);
        }
        /** Place the string representation of the provided int value into the provided byte buffer.
         *
         * @param i
         *          The int value to get the string of.
         * @param buffer
         *          The byte buffer.
         * @param offset
         *          The first index to begin inserting bytes in the byte buffer.
         * @return The last index into which a byte was inserted PLUS ONE. */
        public static int getStringInt(int i,byte[] buffer,int offset){
            if(i<0){
                buffer[offset++]=(byte)'-';
                if(i==Integer.MIN_VALUE){
                    buffer[offset]=(byte)'2';
                    buffer[++offset]=(byte)'1';
                    buffer[++offset]=(byte)'4';
                    buffer[++offset]=(byte)'7';
                    buffer[++offset]=(byte)'4';
                    buffer[++offset]=(byte)'8';
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'6';
                    buffer[++offset]=(byte)'4';
                    buffer[++offset]=(byte)'8';
                    return ++offset;
                }
                i=-i;
            }
            mediumDigits(i,buffer,offset+=getStringSize(i));
            return offset;
        }
        /** Place the string representation of the provided long value into the provided byte buffer.
         *
         * @param l
         *          The long value to get the string of.
         * @param buffer
         *          The byte buffer.
         * @param offset
         *          The first index to begin inserting bytes in the byte buffer.
         * @return The last index into which a byte was inserted PLUS ONE. */
        public static int getStringLong(long l,byte[] buffer,int offset){
            if(l<0L){
                buffer[offset++]=(byte)'-';
                if(l==Long.MIN_VALUE){
                    buffer[offset]=(byte)'9';
                    buffer[++offset]=(byte)'2';
                    buffer[++offset]=(byte)'2';
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'7';
                    buffer[++offset]=(byte)'2';
                    buffer[++offset]=(byte)'0';
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'6';
                    buffer[++offset]=(byte)'8';
                    buffer[++offset]=(byte)'5';
                    buffer[++offset]=(byte)'4';
                    buffer[++offset]=(byte)'7';
                    buffer[++offset]=(byte)'7';
                    buffer[++offset]=(byte)'5';
                    buffer[++offset]=(byte)'8';
                    buffer[++offset]=(byte)'0';
                    buffer[++offset]=(byte)'8';
                    return ++offset;
                }
                l=-l;
            }
            largeDigits(l,buffer,offset+=getStringSize(l));
            return offset;
        }
        /** Place the string representation of the provided long value into the provided byte buffer.
         *
         * @param l
         *          The long value to get the string of.
         * @param buffer
         *          The byte buffer.
         * @param offset
         *          The first index to begin inserting bytes in the byte buffer.
         * @return The last index into which a byte was inserted PLUS ONE. */
        public static int getStringShort(int i,byte[] buffer,int offset){
            if(i<0){
                buffer[offset++]=(byte)'-';
                i=-i;
            }
            smallDigits(i,buffer,offset+=getStringSize(i));
            return offset;
        }
        /** Insert 0's into a byte buffer.
         *
         * @param offset
         *          The index in the buffer to insert the first 0.
         * @param buffer
         *          The buffer to insert 0's into.
         * @param bound
         *          The last index into which a zero will be placed PLUS ONE.
         * @return The {@code bound} parameter. */
        private static int fillZeros(int offset,final byte[] buffer,final int bound){
            for(;;){
                if(offset==bound){ return offset; }
                buffer[++offset]=(byte)'0';
            }
        }
        private static byte[] getByteByteBuffer(){
            return new byte[4];
        }
        private static byte[] getFloatByteBuffer(){
            // TODO see if this can/should be made thread-localized
            // (preferably without using ThreadLocal, which is too slow).
            return new byte[15];
        }
        private static byte[] getIntByteBuffer(){
            // TODO see if this can/should be made thread-localized
            // (preferably without using ThreadLocal, which is too slow).
            return new byte[11];
        }
        private static byte[] getLongByteBuffer(){
            return new byte[20];
        }
        private static byte[] getShortByteBuffer(){
            return new byte[6];
        }
        /** Print the characters for "Infinity" or "NaN" (positive or negative).
         *
         * @param buffer
         *          The byte buffer to point to.
         * @param offset
         *          The first index to insert bytes.
         * @param rawBits
         *          The raw bits provided by Float.floatToRawIntBits(float)
         * @return The offset in the buffer just after the index where the last character was inserted. */
        private static int infinityOrNaNToASCII(final byte[] buffer,int offset,final int rawBits){
            if((rawBits&0x7fffff)==0){
                if((rawBits&Integer.MIN_VALUE)!=0){
                    buffer[offset++]=(byte)'-';
                }
                buffer[offset]=(byte)'I';
                buffer[++offset]=(byte)'n';
                buffer[++offset]=(byte)'f';
                buffer[++offset]=(byte)'i';
                buffer[++offset]=(byte)'n';
                buffer[++offset]=(byte)'i';
                buffer[++offset]=(byte)'t';
                buffer[++offset]=(byte)'y';
            }else{
                buffer[offset]=(byte)'N';
                buffer[++offset]=(byte)'a';
                buffer[++offset]=(byte)'N';
            }
            return ++offset;
        }
        private static void largeDigits(long val,byte[] buffer,int digitOffset){
            for(;;){
                if(val<2147483648L){
                    mediumDigits((int)val,buffer,digitOffset);
                    break;
                }
                // TODO we could use multiplication to do this for values less than
                // 4908534099L. Would this be faster?
                //// final int tmp=(int)(((long)val*1374389535)>>>37);
                // final long tmp=val/100;
                // final int r=(int)(val-(tmp<<6)+(tmp<<5)+(tmp<<2));
                long tmp;
                int r;
                buffer[--digitOffset]=DIGIT_ONESByte[r=(int)(val-(tmp=val/100)*100)];
                buffer[--digitOffset]=DIGIT_TENSByte[r];
                val=tmp;
            }
        }
        private static void mediumDigits(int val,byte[] buffer,int digitOffset){
            for(;;){
                // TODO the stock Integer class uses 66536 as the threshold, but values
                // should
                // be accurate up to 81920 even if the multplication technique were used.
                // Would performance benefit if this were changed?
                if(val<81920)
                    // if(val<65536)
                {
                    smallDigits(val,buffer,digitOffset);
                    break;
                }
                // two digits per iteration
                // TODO Would it be faster to promote to long and use multiplication to
                // divide?
                // TODO if this is okay, then perhaps consider the same multiplication
                // with the shift-add technique
                // (valid for val < 4908534099L
                // final int tmp=(int)(((long)val*1374389535)>>>37);
                int tmp;
                // buffer[--digitOffset]=DIGIT_ONESByte[val-=mult100(tmp=val/100)];
                buffer[--digitOffset]=DIGIT_ONESByte[val-=(tmp=val/100)*100];
                buffer[--digitOffset]=DIGIT_TENSByte[val];
                val=tmp;
            }
        }
        /**
         * <p>
         * This shift-add technique was shamelessly ripped from {@link java.lang.Integer Integer}. It is equivalent to:
         *
         * <pre>
         * int tmp=(val*52429)>>>19;
         * byte c=(byte)(((tmp<<3)+(q<<1))+'0');
         * </pre>
         *
         * This is as it appears in {@link java.lang.Integer Integer}. It is, in turn, equivalent to
         *
         * <pre>
         *   int tmp = val / 10;
         *   byte c  = (byte)(( val - ( tmp * 10 ) + '0' );
         * </pre>
         *
         * which is in turn equivalent to
         *
         * <pre>
         * byte c=val%10;
         * </pre>
         *
         * We want to avoid div and mod at all costs.
         * </p>
         * TODO: check the performance of this against other techniques
         *
         * @param buffer
         *          The character buffer into which the character will be placed.
         * @param digitOffset
         *          The index at which to place the character.
         * @param val
         *          The integer value from which to pull the lowest-order decimal digit. */
        private static void smallDigits(int val,byte[] buffer,int digitOffset){
            for(;;){
                if(val<10){
                    buffer[--digitOffset]=(byte)(val+'0');
                    return;
                }
                buffer[--digitOffset]=(byte)(val-(val=val*52429>>>19)*10+'0');
            }
        }
    private interface BigMathFltToASCII{
        int getChars(int decimalExponent,char[] buffer,int offset);
        int getBytes(int decimalExponent,byte[] buffer,int offset);
    }
    /** This class is responsible for printing the digits of a single-precision floating point number when the floating
     * point number is de-normalized.
     *
     * @author lyonste */
    private static class FltDenorm{
        private static int estimateDecimalExponent(final int fractBits){
            if(fractBits>7136237){ return 38; }
            if(fractBits>713623){ return 39; }
            if(fractBits>71361){ return 40; }
            if(fractBits>7135){ return 41; }
            if(fractBits>713){ return 42; }
            if(fractBits>70){ return 43; }
            if(fractBits>7){ return 44; }
            return 45;
        }
        private static int fpFractLeading0s(final int fractionBits){
            int mask=1<<22;
            int result=9;
            for(;;){
                if((fractionBits&mask)!=0){ return result; }
                ++result;
                mask>>>=1;
            }
        }
        static int toASCII(int fractBits,char[] buffer,int offset){
            int lead0s;
            final int decimalExponent=estimateDecimalExponent(fractBits);
            BigMathFltToASCII bsm;
            if(31-fpFractNumTrailing0s(fractBits)-(lead0s=fpFractLeading0s(fractBits))==0){
                bsm=new BigMathDenorm32Bit(decimalExponent,lead0s);
            }else{
                bsm=new BigMathDenorm64Bit(fractBits,decimalExponent);
            }
            return bsm.getChars(decimalExponent,buffer,offset);
        }
        private static int printDenormalizedFltExponent(int estimatedDecimalExponent,final char[] buffer,int offset){
            buffer[++offset]=(char)'E';
            buffer[++offset]=(char)'-';
            if(estimatedDecimalExponent<40){
                buffer[++offset]=(char)'3';
                estimatedDecimalExponent+=10;
            }else{
                buffer[++offset]=(char)'4';
            }
            buffer[++offset]=(char)(estimatedDecimalExponent+8);
            return ++offset;
        }
        static int toASCII(int fractBits,byte[] buffer,int offset){
            int lead0s;
            final int decimalExponent=estimateDecimalExponent(fractBits);
            BigMathFltToASCII bsm;
            if(31-fpFractNumTrailing0s(fractBits)-(lead0s=fpFractLeading0s(fractBits))==0){
                bsm=new BigMathDenorm32Bit(decimalExponent,lead0s);
            }else{
                bsm=new BigMathDenorm64Bit(fractBits,decimalExponent);
            }
            return bsm.getBytes(decimalExponent,buffer,offset);
        }
        private static int printDenormalizedFltExponent(int estimatedDecimalExponent,final byte[] buffer,int offset){
            buffer[++offset]=(byte)'E';
            buffer[++offset]=(byte)'-';
            if(estimatedDecimalExponent<40){
                buffer[++offset]=(byte)'3';
                estimatedDecimalExponent+=10;
            }else{
                buffer[++offset]=(byte)'4';
            }
            buffer[++offset]=(byte)(estimatedDecimalExponent+8);
            return ++offset;
        }
        /** This class is used to perform the math necessary to produce the decimal digits for de-normalized numbers
         * whenever it is possible to do so with only 32 bits of resolution.
         *
         * @author lyonste */
        private static class BigMathDenorm32Bit implements BigMathFltToASCII{
            int b;
            int m;
            public BigMathDenorm32Bit(final int decimalExponent,final int lead0s){
                long[] pow5Arr;
                int index;
                b=(int)((pow5Arr=POW_5_128)[index=(decimalExponent-26<<1)+1]<<5-lead0s+decimalExponent>>>32);
                m=(int)(pow5Arr[index+2]>>>59-decimalExponent);
            }
            @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                int b;
                int c=(b=this.b)>>>27;
                if(c==8){
                    buffer[offset]=(byte)'9';
                    buffer[++offset]=(byte)'.';
                    buffer[++offset]=(byte)'0';
                }else{
                    b&=(1<<27)-1;
                    buffer[offset]=(byte)(c+'0');
                    buffer[++offset]=(byte)'.';
                    int m=this.m;
                    for(;;){
                        c=(b*=10)>>>27;
                    if((b&=(1<<27)-1)+m>1<<27){
                        if(b>1<<26){
                            ++c;
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    m*=10;
                    buffer[++offset]=(byte)(c+'0');
                    }
                    buffer[++offset]=(byte)(c+'0');
                }
                return printDenormalizedFltExponent(decimalExponent,buffer,offset);
            }
            @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                int b;
                int c=(b=this.b)>>>27;
                if(c==8){
                    buffer[offset]=(char)'9';
                    buffer[++offset]=(char)'.';
                    buffer[++offset]=(char)'0';
                }else{
                    b&=(1<<27)-1;
                    buffer[offset]=(char)(c+'0');
                    buffer[++offset]=(char)'.';
                    int m=this.m;
                    for(;;){
                        c=(b*=10)>>>27;
                    if((b&=(1<<27)-1)+m>1<<27){
                        if(b>1<<26){
                            ++c;
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    m*=10;
                    buffer[++offset]=(char)(c+'0');
                    }
                    buffer[++offset]=(char)(c+'0');
                }
                return printDenormalizedFltExponent(decimalExponent,buffer,offset);
            }
        }
        /** This class is used to perform the math necessary to produce the decimal digits for de-normalized numbers
         * whenever 64 bits of resolution are required.
         *
         * @author lyonste */
        private static class BigMathDenorm64Bit implements BigMathFltToASCII{
            long b;
            long m;
            public BigMathDenorm64Bit(int fractBits,int decimalExponent){
                long[] pow5Arr;
                int index;
                b=(((pow5Arr=POW_5_128)[index=(decimalExponent-=26)<<1]>>>32)*fractBits>>>32)
                        +pow5Arr[++index]*fractBits<<decimalExponent;
                m=pow5Arr[index+2]<<decimalExponent;
            }
            @Override public int getBytes(int decimalExponent,byte[] buffer,int offset){
                long b;
                long m=this.m;
                buffer[offset]=(byte)((int)((b=this.b)>>>59)+'0');
                buffer[++offset]=(byte)'.';
                b=(b&(1L<<59)-1)*10;
                int c;
                for(;;){
                    c=(int)(b>>>59);
                    if((b&=(1L<<59)-1)+m>1L<<59){
                        if(b>1L<<58){
                            if(c==9){
                                c=0;
                                ++buffer[offset-1];
                            }else{
                                ++c;
                            }
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    buffer[++offset]=(byte)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(byte)(c+'0');
                return printDenormalizedFltExponent(decimalExponent,buffer,offset);
            }
            @Override public int getChars(int decimalExponent,char[] buffer,int offset){
                long b;
                long m=this.m;
                buffer[offset]=(char)((int)((b=this.b)>>>59)+'0');
                buffer[++offset]=(char)'.';
                b=(b&(1L<<59)-1)*10;
                int c;
                for(;;){
                    c=(int)(b>>>59);
                    if((b&=(1L<<59)-1)+m>1L<<59){
                        if(b>1L<<58){
                            if(c==9){
                                c=0;
                                ++buffer[offset-1];
                            }else{
                                ++c;
                            }
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    buffer[++offset]=(char)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(char)(c+'0');
                return printDenormalizedFltExponent(decimalExponent,buffer,offset);
            }
        }
    }
    /** This class is responsible for printing the digits of a single-precision floating point number when the absolute
     * value of that floating point number is greater than or equal to 1.0.
     *
     * @author lyonste */
    private static class FltGTE1{
        private static final byte[] ESTIMATE_DECIMAL_EXPONENT=new byte[]{18,// 63
                19,19,19,// 64,64,66
                20,20,20,// 67,68,69
                21,21,21,21,// 70,71,72,73
                22,22,22,// 74,75,76
                23,23,23,// 77,78,79
                24,24,24,// 80,81,82
                25,25,25,25,// 83,84,85,86
                26,26,26,// 87,88,89
                27,27,27,27,// 90,91,92,93
                28,28,28,// 94,95,96
                29,29,29,// 97,98,99
                30,30,30,// 100,101,102
                31,31,31,31,// 103,104,105,106
                32,32,32,// 107,108,109
                33,33,33,// 110,111,112
                34,34,34,34,// 113,114,115,116
                35,35,35,// 117,118,119
                36,36,36,// 120,121,122
                37,37,37,37,// 123,124,125,126
                38,// 127
        };
        static int toASCII(int fractBits,final int binaryExponent,final byte[] buffer,int offset){
            if(fractBits==0){
                if(binaryExponent>86){ return BigMathGTE1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent>62){ return LongMathGTE1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent>23){ return Pow2GTE1.toASCII(binaryExponent,buffer,offset); }
                mediumDigits(fractBits=1<<binaryExponent,buffer,offset+=getStringSize(fractBits));
                buffer[offset]=(byte)'.';
                buffer[++offset]=(byte)'0';
                return ++offset;
            }else{
                if(fpFractNumTrailing0s(fractBits|=0x800000)+binaryExponent>22
                        &&binaryExponent<63){ return Pow2GTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                if(binaryExponent>86||binaryExponent==86
                        &&fractBits>10709187){ return BigMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                if(binaryExponent>22){ return LongMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                return IntMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset);
            }
        }
        static int toASCII(int fractBits,final int binaryExponent,final char[] buffer,int offset){
            if(fractBits==0){
                if(binaryExponent>86){ return BigMathGTE1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent>62){ return LongMathGTE1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent>23){ return Pow2GTE1.toASCII(binaryExponent,buffer,offset); }
                mediumDigits(fractBits=1<<binaryExponent,buffer,offset+=getStringSize(fractBits));
                buffer[offset]=(char)'.';
                buffer[++offset]=(char)'0';
                return ++offset;
            }else{
                if(fpFractNumTrailing0s(fractBits|=0x800000)+binaryExponent>22
                        &&binaryExponent<63){ return Pow2GTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                if(binaryExponent>86||binaryExponent==86
                        &&fractBits>10709187){ return BigMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                if(binaryExponent>22){ return LongMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset); }
                return IntMathGTE1.toASCII(fractBits,binaryExponent,buffer,offset);
            }
        }
        private static abstract class BigMathGTE1 implements BigMathFltToASCII{
            long b;
            long m;
            private static final byte[] SHIFT_BIAS=new byte[]{1,31,29,26,24,22,20,17,15,13,10,8,6,3};
            static int toASCII(int binaryExponent,final byte[] buffer,int offset){
                final int decimalExponent=ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63];
                int c;
                long b=1L<<binaryExponent-decimalExponent+(c=SHIFT_BIAS[decimalExponent-25])-32;
                long m=5L<<binaryExponent-56-decimalExponent+c;
                long[] pow5Arr;
                final long s=(pow5Arr=POW_5_128)[binaryExponent=decimalExponent-26<<1]>>>32-c|pow5Arr[++binaryExponent]<<32+c;
                long sShift;
                b=(b-(c=(int)((b>>>32)/(sShift=s>>>32)))*s)*10;
                buffer[offset]=(byte)(c+'0');
                buffer[++offset]=(byte)'.';
                for(;;){
                    if((c=(int)((b>>>32)/sShift))!=0){
                        b-=c*s;
                    }
                    if(b+m>s){
                        if(b>s>>1){
                            ++c;
                        }
                        break;
                    }else if(b<m){
                        break;
                    }
                    buffer[++offset]=(byte)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(byte)(c+'0');
                return printBigIntExponent(buffer,offset,decimalExponent);
            }
            static int toASCII(final int fractBits,final int binaryExponent,final byte[] buffer,final int offset){
                int decimalExponent;
                BigMathFltToASCII bsm;
                switch(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)){
                case 25:
                    bsm=new BigMathGTE1DecExp25(fractBits);
                    break;
                case 26:
                    bsm=new BigMathGTE1DecExp26(fractBits,binaryExponent+14);
                    break;
                case 31:
                case 36:
                case 37:
                case 38:
                    bsm=new BigMathGTE1EasyCase(fractBits,decimalExponent,binaryExponent);
                    break;
                default:
                    bsm=new BigMathGTE1HardCase(fractBits,decimalExponent,binaryExponent);
                    break;
                }
                return bsm.getBytes(decimalExponent,buffer,offset);
            }
            private static int printBigIntExponent(final byte[] buffer,int offset,int decimalExponent){
                buffer[++offset]=(byte)'E';
                if(decimalExponent<30){
                    buffer[++offset]=(byte)'2';
                    decimalExponent+=28;
                }else{
                    buffer[++offset]=(byte)'3';
                    decimalExponent+=18;
                }
                buffer[++offset]=(byte)decimalExponent;
                return ++offset;
            }
            static int toASCII(int binaryExponent,final char[] buffer,int offset){
                final int decimalExponent=ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63];
                int c;
                long b=1L<<binaryExponent-decimalExponent+(c=SHIFT_BIAS[decimalExponent-25])-32;
                long m=5L<<binaryExponent-56-decimalExponent+c;
                long[] pow5Arr;
                final long s=(pow5Arr=POW_5_128)[binaryExponent=decimalExponent-26<<1]>>>32-c|pow5Arr[++binaryExponent]<<32+c;
                long sShift;
                b=(b-(c=(int)((b>>>32)/(sShift=s>>>32)))*s)*10;
                buffer[offset]=(char)(c+'0');
                buffer[++offset]=(char)'.';
                for(;;){
                    if((c=(int)((b>>>32)/sShift))!=0){
                        b-=c*s;
                    }
                    if(b+m>s){
                        if(b>s>>1){
                            ++c;
                        }
                        break;
                    }else if(b<m){
                        break;
                    }
                    buffer[++offset]=(char)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(char)(c+'0');
                return printBigIntExponent(buffer,offset,decimalExponent);
            }
            static int toASCII(final int fractBits,final int binaryExponent,final char[] buffer,final int offset){
                int decimalExponent;
                BigMathFltToASCII bsm;
                switch(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)){
                case 25:
                    bsm=new BigMathGTE1DecExp25(fractBits);
                    break;
                case 26:
                    bsm=new BigMathGTE1DecExp26(fractBits,binaryExponent+14);
                    break;
                case 31:
                case 36:
                case 37:
                case 38:
                    bsm=new BigMathGTE1EasyCase(fractBits,decimalExponent,binaryExponent);
                    break;
                default:
                    bsm=new BigMathGTE1HardCase(fractBits,decimalExponent,binaryExponent);
                    break;
                }
                return bsm.getChars(decimalExponent,buffer,offset);
            }
            private static int printBigIntExponent(final char[] buffer,int offset,int decimalExponent){
                buffer[++offset]=(char)'E';
                if(decimalExponent<30){
                    buffer[++offset]=(char)'2';
                    decimalExponent+=28;
                }else{
                    buffer[++offset]=(char)'3';
                    decimalExponent+=18;
                }
                buffer[++offset]=(char)decimalExponent;
                return ++offset;
            }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                switch(binaryExponent){
                case 86:
                    if(fractBits>10842021){ return 26; }
                    break;
                case 89:
                    if(fractBits>13552526){ return 27; }
                    break;
                case 93:
                    if(fractBits>8470328){ return 28; }
                    break;
                case 96:
                    if(fractBits>10587911){ return 29; }
                    break;
                case 99:
                    if(fractBits>13234889){ return 30; }
                    break;
                case 102:
                    if(fractBits>16543611){ return 31; }
                    break;
                case 106:
                    if(fractBits>10339757){ return 32; }
                    break;
                case 109:
                    if(fractBits>12924696){ return 33; }
                    break;
                case 112:
                    if(fractBits>16155870){ return 34; }
                    break;
                case 116:
                    if(fractBits>10097419){ return 35; }
                    break;
                case 119:
                    if(fractBits>12621773){ return 36; }
                    break;
                case 122:
                    if(fractBits>15777217){ return 37; }
                    break;
                case 126:
                    if(fractBits>9860760){ return 38; }
                default:
                    break;
                }
                return ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63];
            }
            private static class BigMathGTE1DecExp25 extends BigMathGTE1{
                public BigMathGTE1DecExp25(final int fractBits){
                    b=(long)fractBits<<39;
                    m=5L<<39;
                }
                @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                    long b;
                    int c;
                    b=((b=this.b)-(c=(int)(b/596046447753906250L))*596046447753906250L)*10;
                    long m=this.m;
                    buffer[offset]=(byte)(c+'0');
                    buffer[++offset]=(byte)'.';
                    for(;;){
                        if((b-=(c=(int)(b/596046447753906250L))*596046447753906250L)+m>596046447753906250L){
                            if(b>298023223876953125L){
                                ++c;
                            }
                            break;
                        }
                        if(b<m){
                            break;
                        }
                        b*=10;
                        m*=10;
                        buffer[++offset]=(byte)(c+'0');
                    }
                    buffer[++offset]=(byte)(c+'0');
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
                @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                    long b;
                    int c;
                    b=((b=this.b)-(c=(int)(b/596046447753906250L))*596046447753906250L)*10;
                    long m=this.m;
                    buffer[offset]=(char)(c+'0');
                    buffer[++offset]=(char)'.';
                    for(;;){
                        if((b-=(c=(int)(b/596046447753906250L))*596046447753906250L)+m>596046447753906250L){
                            if(b>298023223876953125L){
                                ++c;
                            }
                            break;
                        }
                        if(b<m){
                            break;
                        }
                        b*=10;
                        m*=10;
                        buffer[++offset]=(char)(c+'0');
                    }
                    buffer[++offset]=(char)(c+'0');
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
            }
            private static class BigMathGTE1DecExp26 extends BigMathGTE1{
                public BigMathGTE1DecExp26(final int fractBits,final int mShift){
                    b=(long)fractBits<<mShift;
                    m=5L<<mShift;
                }
                @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                    long b;
                    long m;
                    int c;
                    if(0x6765C793FA10079DL<(b=((b=this.b)-(c=(int)(b/0xA56FA5B99019A5CL))*0xA56FA5B99019A5CL)*10)+(m=this.m)){
                        buffer[offset]=(byte)(c+'1');
                        buffer[++offset]=(byte)'.';
                        buffer[++offset]=(byte)'0';
                    }else{
                        buffer[offset]=(byte)(c+'0');
                        buffer[++offset]=(byte)'.';
                        for(;;){
                            if(0xA56FA5B99019A5CL<(b-=(c=(int)(b/0xA56FA5B99019A5CL))*0xA56FA5B99019A5CL)+m){
                                if(b>0x52B7D2DCC80CD2EL){
                                    ++c;
                                }
                                break;
                            }
                            if(b<m){
                                break;
                            }
                            b*=10;
                            m*=10;
                            buffer[++offset]=(byte)(c+'0');
                        }
                        buffer[++offset]=(byte)(c+'0');
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
                @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                    long b;
                    long m;
                    int c;
                    if(0x6765C793FA10079DL<(b=((b=this.b)-(c=(int)(b/0xA56FA5B99019A5CL))*0xA56FA5B99019A5CL)*10)+(m=this.m)){
                        buffer[offset]=(char)(c+'1');
                        buffer[++offset]=(char)'.';
                        buffer[++offset]=(char)'0';
                    }else{
                        buffer[offset]=(char)(c+'0');
                        buffer[++offset]=(char)'.';
                        for(;;){
                            if(0xA56FA5B99019A5CL<(b-=(c=(int)(b/0xA56FA5B99019A5CL))*0xA56FA5B99019A5CL)+m){
                                if(b>0x52B7D2DCC80CD2EL){
                                    ++c;
                                }
                                break;
                            }
                            if(b<m){
                                break;
                            }
                            b*=10;
                            m*=10;
                            buffer[++offset]=(char)(c+'0');
                        }
                        buffer[++offset]=(char)(c+'0');
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
            }
            private abstract static class BigMathGTE1WithS extends BigMathGTE1{
                final long s;
                public BigMathGTE1WithS(final long fractBits,int decimalExponent,final int binaryExponent){
                    final int shiftBias=SHIFT_BIAS[decimalExponent-25];
                    final int mShift=binaryExponent-23-decimalExponent+shiftBias;
                    b=fractBits<<mShift+32;
                    m=1L<<mShift+31;
                    long[] pow5Arr;
                    s=(pow5Arr=POW_5_128)[decimalExponent=decimalExponent-26<<1]>>>32-shiftBias
                            |pow5Arr[decimalExponent+1]<<32+shiftBias;
                }
            }
            private static class BigMathGTE1EasyCase extends BigMathGTE1WithS{
                public BigMathGTE1EasyCase(final int fractBits,final int decimalExponent,final int binaryExponent){
                    super(fractBits,decimalExponent,binaryExponent);
                }
                @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                    long b;
                    long s;
                    long hiS;
                    int c;
                    if((c=(int)(((b=this.b)>>>32)/(hiS=(s=this.s)>>>32)))==0){
                        buffer[offset]=(byte)'1';
                        buffer[++offset]=(byte)'.';
                        buffer[++offset]=(byte)'0';
                    }else{
                        long m;
                        if(s<(b-=c*s)+(m=this.m)){
                            buffer[offset]=(byte)(c+'1');
                            buffer[++offset]=(byte)'.';
                            buffer[++offset]=(byte)'0';
                        }else{
                            buffer[offset]=(byte)(c+'0');
                            buffer[++offset]=(byte)'.';
                            for(;;){
                                if(s<(b=(b*=10)-(c=(int)((b>>>32)/hiS))*s)+(m*=10)){
                                    if(b<<1>s){
                                        ++c;
                                    }
                                    break;
                                }
                                if(b<m){
                                    break;
                                }
                                buffer[++offset]=(byte)(c+'0');
                            }
                            buffer[++offset]=(byte)(c+'0');
                        }
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
                @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                    long b;
                    long s;
                    long hiS;
                    int c;
                    if((c=(int)(((b=this.b)>>>32)/(hiS=(s=this.s)>>>32)))==0){
                        buffer[offset]=(char)'1';
                        buffer[++offset]=(char)'.';
                        buffer[++offset]=(char)'0';
                    }else{
                        long m;
                        if(s<(b-=c*s)+(m=this.m)){
                            buffer[offset]=(char)(c+'1');
                            buffer[++offset]=(char)'.';
                            buffer[++offset]=(char)'0';
                        }else{
                            buffer[offset]=(char)(c+'0');
                            buffer[++offset]=(char)'.';
                            for(;;){
                                if(s<(b=(b*=10)-(c=(int)((b>>>32)/hiS))*s)+(m*=10)){
                                    if(b<<1>s){
                                        ++c;
                                    }
                                    break;
                                }
                                if(b<m){
                                    break;
                                }
                                buffer[++offset]=(char)(c+'0');
                            }
                            buffer[++offset]=(char)(c+'0');
                        }
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
            }
            private static class BigMathGTE1HardCase extends BigMathGTE1WithS{
                public BigMathGTE1HardCase(final int fractBits,final int decimalExponent,final int binaryExponent){
                    super(fractBits,decimalExponent,binaryExponent);
                }
                private static int fractionLoop(long b,long s,long m,final byte[] buffer,int offset){
                    int c;
                    final long hiS=s>>>32;
                    final long loS=s&0xffffffffL;
                    for(;;){
                        long hiB;
                        if((c=(int)((hiB=(b*=10)>>>32)/hiS))!=0){
                            long carry;
                            if((b=((carry=(b&0xffffffffL)-c*loS)>>32)+hiB-c*hiS)>>32!=0){
                                break;
                            }
                            b=(b<<32)+(0xffffffffL&carry);
                        }
                        if(s<b+(m*=10)){
                            if(b<<1>s){
                                ++c;
                            }
                            break;
                        }
                        if(b<m){
                            break;
                        }
                        buffer[++offset]=(byte)(c+'0');
                    }
                    buffer[++offset]=(byte)(c+'0');
                    return offset;
                }
                @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                    long b;
                    long s;
                    long hiS;
                    int c;
                    long hiB;
                    if((c=(int)((hiB=(b=this.b)>>>32)/(hiS=(s=this.s)>>>32)))==0){
                        buffer[offset]=(byte)'1';
                        buffer[++offset]=(byte)'.';
                        buffer[++offset]=(byte)'0';
                    }else{
                        long carry;
                        if((b=((carry=(b&0xffffffffL)-c*(s&0xffffffffL))>>32)+hiB-c*hiS)>>32==0){
                            long m;
                            if(s<(b=(b<<32)+(0xffffffffL&carry))+(m=this.m)){
                                buffer[offset]=(byte)(c+'1');
                                buffer[++offset]=(byte)'.';
                                buffer[++offset]=(byte)'0';
                            }else{
                                buffer[offset]=(byte)(c+'0');
                                buffer[++offset]=(byte)'.';
                                offset=fractionLoop(b,s,m,buffer,offset);
                            }
                        }else{
                            buffer[offset]=(byte)(c+'0');
                            buffer[++offset]=(byte)'.';
                            buffer[++offset]=(byte)'0';
                        }
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
                private static int fractionLoop(long b,long s,long m,final char[] buffer,int offset){
                    int c;
                    final long hiS=s>>>32;
                    final long loS=s&0xffffffffL;
                    for(;;){
                        long hiB;
                        if((c=(int)((hiB=(b*=10)>>>32)/hiS))!=0){
                            long carry;
                            if((b=((carry=(b&0xffffffffL)-c*loS)>>32)+hiB-c*hiS)>>32!=0){
                                break;
                            }
                            b=(b<<32)+(0xffffffffL&carry);
                        }
                        if(s<b+(m*=10)){
                            if(b<<1>s){
                                ++c;
                            }
                            break;
                        }
                        if(b<m){
                            break;
                        }
                        buffer[++offset]=(char)(c+'0');
                    }
                    buffer[++offset]=(char)(c+'0');
                    return offset;
                }
                @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                    long b;
                    long s;
                    long hiS;
                    int c;
                    long hiB;
                    if((c=(int)((hiB=(b=this.b)>>>32)/(hiS=(s=this.s)>>>32)))==0){
                        buffer[offset]=(char)'1';
                        buffer[++offset]=(char)'.';
                        buffer[++offset]=(char)'0';
                    }else{
                        long carry;
                        if((b=((carry=(b&0xffffffffL)-c*(s&0xffffffffL))>>32)+hiB-c*hiS)>>32==0){
                            long m;
                            if(s<(b=(b<<32)+(0xffffffffL&carry))+(m=this.m)){
                                buffer[offset]=(char)(c+'1');
                                buffer[++offset]=(char)'.';
                                buffer[++offset]=(char)'0';
                            }else{
                                buffer[offset]=(char)(c+'0');
                                buffer[++offset]=(char)'.';
                                offset=fractionLoop(b,s,m,buffer,offset);
                            }
                        }else{
                            buffer[offset]=(char)(c+'0');
                            buffer[++offset]=(char)'.';
                            buffer[++offset]=(char)'0';
                        }
                    }
                    return printBigIntExponent(buffer,offset,decimalExponent);
                }
            }
        }
        private static class IntMathGTE1{
            private static final byte[] ESTIMATE_DECIMAL_EXPONENT=new byte[]{0,0,0,0,// 0,1,2,3
                    1,1,1,// 4,5,6
                    2,2,2,// 7,8,9
                    3,3,3,3,// 10,11,12,13
                    4,4,4,// 14,15,16
                    5,5,5,// 17,18,19
                    6,6,6,// 20,21,22,
                    // //the rest not currently used
                    // 6,//23
                    // 7,7,7,//24,25,26
                    // 8,8,8,//27,28,29
                    // 9,9,9,9,//30,31,32,33
                    // 10,10,10,//34,35,36
                    // 11,11,11,//37,38,39
                    // 12,12,12,12,//40,41,42,43
                    // 13,13,13,//44,45,46
                    // 14,14,14,//47,48,49
                    // 15,15,15,15,//50,51,52,53
                    // 16,16,16,//54,55,56
                    // 17,17,17,//57,58,59,
                    // 18,18,18,//60,61,62
            };
            static int toASCII(int fractBits,final int binaryExponent,final byte[] buffer,int offset){
                int decimalExponent;
                int m;
                final int s=(m=POW_5_32[decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)]<<decimalExponent)<<24
                        -binaryExponent;
                int c;
                buffer[offset]=(byte)((c=(fractBits<<=1)/s)+'0');
                fractBits-=c*s;
                for(;;){
                    if(decimalExponent--==0){
                        buffer[++offset]=(byte)'.';
                        break;
                    }
                    buffer[++offset]=(byte)((c=(fractBits*=10)/s)+'0');
                    fractBits-=c*s;
                }
                for(;;){
                    c=(fractBits*=10)/s;
                    if((fractBits-=c*s)+(m*=10)>s){
                        if(fractBits>=m||(decimalExponent=(fractBits<<1)-s)>0||decimalExponent==0&&c==7){
                            ++c;
                        }
                        break;
                    }
                    if(fractBits<m){
                        break;
                    }
                    buffer[++offset]=(byte)(c+'0');
                }
                buffer[++offset]=(byte)(c+'0');
                return ++offset;
            }
            static int toASCII(int fractBits,final int binaryExponent,final char[] buffer,int offset){
                int decimalExponent;
                int m;
                final int s=(m=POW_5_32[decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)]<<decimalExponent)<<24
                        -binaryExponent;
                int c;
                buffer[offset]=(char)((c=(fractBits<<=1)/s)+'0');
                fractBits-=c*s;
                for(;;){
                    if(decimalExponent--==0){
                        buffer[++offset]=(char)'.';
                        break;
                    }
                    buffer[++offset]=(char)((c=(fractBits*=10)/s)+'0');
                    fractBits-=c*s;
                }
                for(;;){
                    c=(fractBits*=10)/s;
                    if((fractBits-=c*s)+(m*=10)>s){
                        if(fractBits>=m||(decimalExponent=(fractBits<<1)-s)>0||decimalExponent==0&&c==7){
                            ++c;
                        }
                        break;
                    }
                    if(fractBits<m){
                        break;
                    }
                    buffer[++offset]=(char)(c+'0');
                }
                buffer[++offset]=(char)(c+'0');
                return ++offset;
            }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                switch(binaryExponent){
                case 3:
                    if(fractBits>10485759){ return 1; }
                    break;
                case 6:
                    if(fractBits>13107199){ return 2; }
                    break;
                case 9:
                    if(fractBits>16383999){ return 3; }
                    break;
                case 13:
                    if(fractBits>10239999){ return 4; }
                    break;
                case 16:
                    if(fractBits>12799999){ return 5; }
                    break;
                case 19:
                    if(fractBits>15999999){ return 6; }
                default:
                    break;
                }
                return ESTIMATE_DECIMAL_EXPONENT[binaryExponent];
            }
        }
        private static class LongMathGTE1{
          static int toASCII(final int binaryExponent,final byte[] buffer,int offset){
              int decimalExponent;
              long b;
              long m;
              final long s;
              final long tenS=(s=POW_5_64[(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63])-1])*10L;
              int c=(int)((b=1L<<binaryExponent-decimalExponent)/s);
              b=(b-c*s)*10L;
              if(c==0){
                  // TODO investigate how we can avoid adjusting the decimal exponent without
                  // overflowing long
                  b=(b-(c=(int)(b/s))*s)*10L;
                  m=100L<<binaryExponent-25-decimalExponent--;
              }else{
                  m=10L<<binaryExponent-25-decimalExponent;
              }
              buffer[offset]=(byte)(c+'0');
              buffer[++offset]=(byte)'.';
              for(;;){
                  if((b=(b-(c=(int)(b/s))*s)*10L)+(m*=10)>tenS){
                      if(b<<1>tenS){
                          ++c;
                      }
                      break;
                  }
                  if(b<m){
                      break;
                  }
                  buffer[++offset]=(byte)(c+'0');
              }
              buffer[++offset]=(byte)(c+'0');
              return printLongGTE1Exponent(buffer,offset,decimalExponent);
          }
          static int toASCII(final int fractBits,final int binaryExponent,final byte[] buffer,int offset){
              int decimalExponent;
              long b=(long)fractBits<<binaryExponent-23-(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent));
              long s;
              long m;
              int c;
              if((b-=(c=(int)(b/(s=POW_5_64[decimalExponent-1])))*s)+(m=1L<<binaryExponent-24-decimalExponent)>s){
                  buffer[offset]=(byte)(c+'1');
                  buffer[++offset]=(byte)'.';
                  buffer[++offset]=(byte)'0';
              }else{
                  if(c==0){
                      // TODO investigate how we can avoid adjusting the decimal exponent without
                      // overflowing long
                      --decimalExponent;
                      m*=10;
                      c=(int)((b*=10)/s);
                      b-=c*s;
                  }
                  buffer[offset]=(byte)(c+'0');
                  buffer[++offset]=(byte)'.';
                  if(b<m){
                      buffer[++offset]=(byte)'0';
                  }else{
                      b*=10;
                      m*=10;
                      final long tenS=s*10L;
                      for(;;){
                          b-=(c=(int)(b/s))*s;
                          if(m>922337203685477580L){
                              if(b<<1>s){
                                  ++c;
                              }
                              break;
                          }
                          if((b*=10)+(m*=10)>tenS){
                              if(b<<1>tenS){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(byte)(c+'0');
                      }
                      buffer[++offset]=(byte)(c+'0');
                  }
              }
              return printLongGTE1Exponent(buffer,offset,decimalExponent);
          }
          private static int printLongGTE1Exponent(final byte[] buffer,int offset,int decimalExponent){
              buffer[++offset]=(byte)'E';
              if(decimalExponent<20){
                  buffer[++offset]=(byte)'1';
                  decimalExponent+=10;
              }else{
                  buffer[++offset]=(byte)'2';
              }
              buffer[++offset]=(byte)(decimalExponent+28);
              return ++offset;
          }
          static int toASCII(final int binaryExponent,final char[] buffer,int offset){
              int decimalExponent;
              long b;
              long m;
              final long s;
              final long tenS=(s=POW_5_64[(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63])-1])*10L;
              int c=(int)((b=1L<<binaryExponent-decimalExponent)/s);
              b=(b-c*s)*10L;
              if(c==0){
                  // TODO investigate how we can avoid adjusting the decimal exponent without
                  // overflowing long
                  b=(b-(c=(int)(b/s))*s)*10L;
                  m=100L<<binaryExponent-25-decimalExponent--;
              }else{
                  m=10L<<binaryExponent-25-decimalExponent;
              }
              buffer[offset]=(char)(c+'0');
              buffer[++offset]=(char)'.';
              for(;;){
                  if((b=(b-(c=(int)(b/s))*s)*10L)+(m*=10)>tenS){
                      if(b<<1>tenS){
                          ++c;
                      }
                      break;
                  }
                  if(b<m){
                      break;
                  }
                  buffer[++offset]=(char)(c+'0');
              }
              buffer[++offset]=(char)(c+'0');
              return printLongGTE1Exponent(buffer,offset,decimalExponent);
          }
          static int toASCII(final int fractBits,final int binaryExponent,final char[] buffer,int offset){
              int decimalExponent;
              long b=(long)fractBits<<binaryExponent-23-(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent));
              long s;
              long m;
              int c;
              if((b-=(c=(int)(b/(s=POW_5_64[decimalExponent-1])))*s)+(m=1L<<binaryExponent-24-decimalExponent)>s){
                  buffer[offset]=(char)(c+'1');
                  buffer[++offset]=(char)'.';
                  buffer[++offset]=(char)'0';
              }else{
                  if(c==0){
                      // TODO investigate how we can avoid adjusting the decimal exponent without
                      // overflowing long
                      --decimalExponent;
                      m*=10;
                      c=(int)((b*=10)/s);
                      b-=c*s;
                  }
                  buffer[offset]=(char)(c+'0');
                  buffer[++offset]=(char)'.';
                  if(b<m){
                      buffer[++offset]=(char)'0';
                  }else{
                      b*=10;
                      m*=10;
                      final long tenS=s*10L;
                      for(;;){
                          b-=(c=(int)(b/s))*s;
                          if(m>922337203685477580L){
                              if(b<<1>s){
                                  ++c;
                              }
                              break;
                          }
                          if((b*=10)+(m*=10)>tenS){
                              if(b<<1>tenS){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(char)(c+'0');
                      }
                      buffer[++offset]=(char)(c+'0');
                  }
              }
              return printLongGTE1Exponent(buffer,offset,decimalExponent);
          }
          private static int printLongGTE1Exponent(final char[] buffer,int offset,int decimalExponent){
              buffer[++offset]=(char)'E';
              if(decimalExponent<20){
                  buffer[++offset]=(char)'1';
                  decimalExponent+=10;
              }else{
                  buffer[++offset]=(char)'2';
              }
              buffer[++offset]=(char)(decimalExponent+28);
              return ++offset;
          }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                switch(binaryExponent){
                case 63:
                    if(fractBits>9094946){ return 19; }
                    break;
                case 66:
                    if(fractBits>11368683){ return 20; }
                    break;
                case 69:
                    if(fractBits>14210854){ return 21; }
                    break;
                case 73:
                    if(fractBits>8881783){ return 22; }
                    break;
                case 76:
                    if(fractBits>11102229){ return 23; }
                    break;
                case 79:
                    if(fractBits>13877787){ return 24; }
                    break;
                case 82:
                    if(fractBits>16623202){
                        // an anomaly
                        return 25;
                    }
                    break;
                case 83:
                    if(fractBits>8673616){ return 25; }
                default:
                    break;
                }
                return ESTIMATE_DECIMAL_EXPONENT[binaryExponent-63];
            }
        }
        private static class Pow2GTE1{
          private static final byte[] INSIGNIFICANT_DIGITS=new byte[]{1,1,1,// 29,30,31
                  2,2,2,// 32,33,34
                  3,3,3,3,// 35,36,37,38
                  4,4,4,// 39,40,41
                  5,5,5,// 42,43,44
                  6,6,6,6,// 45,46,47,48
                  7,7,7,// 49,50,51
                  8,8,8,// 52,53,54,
                  9,9,9,9,// 55,56,57,58
                  10,10,10,// 59,60,61
                  11// 62
          };
          static int toASCII(final int binaryExponent,final byte[] buffer,int offset){
              // TODO maybe one day, write a more straight forward method than this
              int decimalExponent;
              int c;
              int fractBits;
              if(binaryExponent>28){
                  decimalExponent=INSIGNIFICANT_DIGITS[binaryExponent-29];
                  if(binaryExponent>30){
                      long lFractBits,pow10;
                      if((lFractBits=1L<<binaryExponent)
                              -(lFractBits/=pow10=POW_5_64[decimalExponent-1]<<decimalExponent)*pow10>=pow10>>1){
                          c=(fractBits=(int)lFractBits+1)-(fractBits/=10)*10;
                      }
                      else if((c=(fractBits=(int)lFractBits) - (fractBits/=10) * 10) == 0){
                          ++decimalExponent;
                          c=fractBits - (fractBits/=10) * 10;
                      }
                  }else{
                      c=(fractBits=(1<<binaryExponent)/10)-(fractBits/=10)*10;
                  }
              }else{
                  decimalExponent=0;
                  c=(fractBits=1<<binaryExponent)-(fractBits/=10)*10;
              }
              int strSize;
              decimalExponent+=strSize=getStringSize(fractBits);
              eFormDigits(fractBits,buffer,offset+=strSize+1);
              buffer[offset]=(byte)(c+'0');
              return printExponent(buffer,offset,decimalExponent);
          }
          static int toASCII(int fractBits,final int binaryExponent,final byte[] buffer,int offset){
              // TODO maybe one day, write a more straight forward method than this
              int decimalExponent;
              if(binaryExponent>28){
                  final long pow10;
                  long lFractBits;
                  if((lFractBits=(long)fractBits<<binaryExponent-23)-(lFractBits=lFractBits
                          /(pow10=POW_5_64[(decimalExponent=INSIGNIFICANT_DIGITS[binaryExponent-29])-1]<<decimalExponent))
                          *pow10>=pow10>>1){
                      ++lFractBits;
                  }
                  fractBits=(int)lFractBits;
              }else{
                  decimalExponent=0;
                  if(binaryExponent>22){
                      fractBits<<=binaryExponent-23;
                  }else{
                      fractBits>>>=23-binaryExponent;
                  }
              }
              int c;
              for(;;){
                  if((c=fractBits-(fractBits/=10)*10)!=0||fractBits<10){
                      break;
                  }
                  ++decimalExponent;
              }
              int strSize;
              if((decimalExponent+=strSize=getStringSize(fractBits))<7){
                  // F form
                  if(fractBits!=0){
                      mediumDigits(fractBits,buffer,offset+=strSize);
                  }
                  buffer[offset]=(byte)(c+'0');
                  offset=fillZeros(offset,buffer,offset+decimalExponent-strSize)+1;
                  buffer[offset]=(byte)'.';
                  buffer[++offset]=(byte)'0';
                  return ++offset;
              }
              eFormDigits(fractBits,buffer,offset+=strSize+1);
              buffer[offset]=(byte)(c+'0');
              return printExponent(buffer,offset,decimalExponent);
          }
          private static void eFormDigits(int val,final byte[] buffer,int digitOffset){
              int tmp;
              for(;;){
                  if(val<81920){
                      for(;;){
                          if(val<10){
                              buffer[--digitOffset]=(byte)'.';
                              buffer[--digitOffset]=(byte)(val+'0');
                              return;
                          }
                          buffer[--digitOffset]=(byte)(val-(val=val*52429>>>19)*10+'0');
                      }
                  }
                  // two digits per iteration
                  // TODO Would it be faster to promote to long and use multiplication
                  // to
                  // divide?
                  // TODO if this is okay, then perhaps consider the same multiplication
                  // with the shift-add technique
                  // (valid for val < 4908534099L
                  // final int tmp=(int)(((long)val*1374389535)>>>37);
                  buffer[--digitOffset]=DIGIT_ONESByte[val-=(tmp=val/100)*100];
                  buffer[--digitOffset]=DIGIT_TENSByte[val];
                  val=tmp;
              }
          }
          private static int printExponent(final byte[] buffer,int offset,int decimalExponent){
              buffer[++offset]=(byte)'E';
              if(decimalExponent>9){
                  buffer[++offset]=(byte)'1';
                  decimalExponent-=10;
              }
              buffer[++offset]=(byte)(decimalExponent+'0');
              return ++offset;
          }
          static int toASCII(final int binaryExponent,final char[] buffer,int offset){
              // TODO maybe one day, write a more straight forward method than this
              int decimalExponent;
              int c;
              int fractBits;
              if(binaryExponent>28){
                  decimalExponent=INSIGNIFICANT_DIGITS[binaryExponent-29];
                  if(binaryExponent>30){
                      long lFractBits,pow10;
                      if((lFractBits=1L<<binaryExponent)
                              -(lFractBits/=pow10=POW_5_64[decimalExponent-1]<<decimalExponent)*pow10>=pow10>>1){
                          c=(fractBits=(int)lFractBits+1)-(fractBits/=10)*10;
                      }
                      else if((c=(fractBits=(int)lFractBits) - (fractBits/=10) * 10) == 0){
                          ++decimalExponent;
                          c=fractBits - (fractBits/=10) * 10;
                      }
                  }else{
                      c=(fractBits=(1<<binaryExponent)/10)-(fractBits/=10)*10;
                  }
              }else{
                  decimalExponent=0;
                  c=(fractBits=1<<binaryExponent)-(fractBits/=10)*10;
              }
              int strSize;
              decimalExponent+=strSize=getStringSize(fractBits);
              eFormDigits(fractBits,buffer,offset+=strSize+1);
              buffer[offset]=(char)(c+'0');
              return printExponent(buffer,offset,decimalExponent);
          }
          static int toASCII(int fractBits,final int binaryExponent,final char[] buffer,int offset){
              // TODO maybe one day, write a more straight forward method than this
              int decimalExponent;
              if(binaryExponent>28){
                  final long pow10;
                  long lFractBits;
                  if((lFractBits=(long)fractBits<<binaryExponent-23)-(lFractBits=lFractBits
                          /(pow10=POW_5_64[(decimalExponent=INSIGNIFICANT_DIGITS[binaryExponent-29])-1]<<decimalExponent))
                          *pow10>=pow10>>1){
                      ++lFractBits;
                  }
                  fractBits=(int)lFractBits;
              }else{
                  decimalExponent=0;
                  if(binaryExponent>22){
                      fractBits<<=binaryExponent-23;
                  }else{
                      fractBits>>>=23-binaryExponent;
                  }
              }
              int c;
              for(;;){
                  if((c=fractBits-(fractBits/=10)*10)!=0||fractBits<10){
                      break;
                  }
                  ++decimalExponent;
              }
              int strSize;
              if((decimalExponent+=strSize=getStringSize(fractBits))<7){
                  // F form
                  if(fractBits!=0){
                      mediumDigits(fractBits,buffer,offset+=strSize);
                  }
                  buffer[offset]=(char)(c+'0');
                  offset=fillZeros(offset,buffer,offset+decimalExponent-strSize)+1;
                  buffer[offset]=(char)'.';
                  buffer[++offset]=(char)'0';
                  return ++offset;
              }
              eFormDigits(fractBits,buffer,offset+=strSize+1);
              buffer[offset]=(char)(c+'0');
              return printExponent(buffer,offset,decimalExponent);
          }
          private static void eFormDigits(int val,final char[] buffer,int digitOffset){
              int tmp;
              for(;;){
                  if(val<81920){
                      for(;;){
                          if(val<10){
                              buffer[--digitOffset]=(char)'.';
                              buffer[--digitOffset]=(char)(val+'0');
                              return;
                          }
                          buffer[--digitOffset]=(char)(val-(val=val*52429>>>19)*10+'0');
                      }
                  }
                  // two digits per iteration
                  // TODO Would it be faster to promote to long and use multiplication
                  // to
                  // divide?
                  // TODO if this is okay, then perhaps consider the same multiplication
                  // with the shift-add technique
                  // (valid for val < 4908534099L
                  // final int tmp=(int)(((long)val*1374389535)>>>37);
                  buffer[--digitOffset]=DIGIT_ONESChar[val-=(tmp=val/100)*100];
                  buffer[--digitOffset]=DIGIT_TENSChar[val];
                  val=tmp;
              }
          }
          private static int printExponent(final char[] buffer,int offset,int decimalExponent){
              buffer[++offset]=(char)'E';
              if(decimalExponent>9){
                  buffer[++offset]=(char)'1';
                  decimalExponent-=10;
              }
              buffer[++offset]=(char)(decimalExponent+'0');
              return ++offset;
          }
        }
    }
    /** This class is responsible for printing the digits of a single-precision floating point number when the absolute
     * value of that floating point number is less than 1.0 but not equal to zero and when the value is NOT de-normalized.
     *
     * @author lyonste */
    private static class FltLT1{
        /** This table provides an estimate of the decimal exponent (absolute value) given a binary exponent. */
        private static final byte[] ESTIMATE_DECIMAL_EXPONENT=new byte[]{1,1,1,// -1,-2,-3
                2,2,2,// -4,-5,-6
                3,3,3,// -7,-8,-9
                4,4,4,4, // -10,-11,-12,-13
                5,5,5, // -14,-15,-16
                6,6,6,// -17,-18,-19
                7,7,7,7, // -20,-21,-22,-23
                8,8,8, // -24,-25,-26
                9,9,9,// -27,-28,-29
                10,10,10,10, // -30,-31,-32,-33
                11,11,11, // -34,-35-,36
                12,12,12, // -37,-38,-39
                13,13,13,13,// -40,-41,-42,-43
                14,14,14, // -44,-45,-46
                15,15,15, // -47,-48,-49
                16,16,16,16,// -50,-51,-52,-53
                17,17,17, // -54,-55,-56
                18,18,18, // -57,-58,-59
                19,19,19,19,// -60
                20,20,20, // -64
                21,21,21, // -67
                22,22,22,22,// -70
                23,23,23, // -74
                24,24,24, // -77
                25,25,25,25,// -80
                26,26,26, // -84
                27,27,27, // -87
                28,28,28,28,// -90
                29,29,29, // -94
                30,30,30, // -97
                31,31,31,// -100
                32,32,32,32, // -103
                33,33,33, // -107
                34,34,34,// -110
                35,35,35,35, // -113
                36,36,36, // -117
                37,37,37,// -120
                38,38,38,38, // -123
        };
        /** Print the characters of the floating point number into a byte buffer.
         *
         * @param fractBits
         *          The fraction bits of the floating point number.
         * @param binaryExponent
         *          The biased binary exponent of the floating point number.
         * @param buffer
         *          The buffer to insert the characters into.
         * @param offset
         *          The first offset in the buffer to place characters at.
         * @return The index in the buffer where the last character was placed PLUS ONE. */
        static int toASCII(int fractBits,final int binaryExponent,final byte[] buffer,final int offset){
            if(fractBits==0){
                if(binaryExponent<-50){ return BigMathLT1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent<-9){ return LongMathLT1.toASCIIEForm(binaryExponent,buffer,offset); }
                return pow2FForm(binaryExponent,buffer,offset);
            }
            fractBits|=0x800000;
            if(binaryExponent<-51){ return BigMathLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            if(binaryExponent<-5){ return LongMathLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            return IntMathLT1.toASCII(fractBits,binaryExponent,buffer,offset);
        }
        /** A specialized case where the fraction bits normalized to 1.
         *
         * @param binaryExponent
         *          The biased binary exponent of the floating point number.
         * @param buffer
         *          The buffer to insert the characters into.
         * @param offset
         *          The first offset in the buffer to place characters at.
         * @return The index in the buffer where the last character was placed PLUS ONE. */
        private static int pow2FForm(final int binaryExponent,final byte[] buffer,int offset){
            buffer[offset]=(byte)'0';
            buffer[++offset]=(byte)'.';
            switch(binaryExponent){
            case -1:
                break;
            case -7:
            case -8:
            case -9:
                buffer[++offset]=(byte)'0';
            default:
                buffer[++offset]=(byte)'0';
                switch(binaryExponent){
                case -8:
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'9';
                    buffer[++offset]=(byte)'0';
                    buffer[++offset]=(byte)'6';
                    break;
                case -7:
                    buffer[++offset]=(byte)'7';
                    buffer[++offset]=(byte)'8';
                    buffer[++offset]=(byte)'1';
                    break;
                case -6:
                    buffer[++offset]=(byte)'1';
                    buffer[++offset]=(byte)'5';
                case -4:
                    buffer[++offset]=(byte)'6';
                    break;
                case -9:
                    buffer[++offset]=(byte)'1';
                    buffer[++offset]=(byte)'9';
                    buffer[++offset]=(byte)'5';
                default:
                    buffer[++offset]=(byte)'3';
                    buffer[++offset]=(byte)'1';
                }
                buffer[++offset]=(byte)'2';
                break;
            case -3:
                buffer[++offset]=(byte)'1';
            case -2:
                buffer[++offset]=(byte)'2';
            }
            buffer[++offset]=(byte)'5';
            return ++offset;
        }
        /** Print the characters of the floating point number into a char buffer.
         *
         * @param fractBits
         *          The fraction bits of the floating point number.
         * @param binaryExponent
         *          The biased binary exponent of the floating point number.
         * @param buffer
         *          The buffer to insert the characters into.
         * @param offset
         *          The first offset in the buffer to place characters at.
         * @return The index in the buffer where the last character was placed PLUS ONE. */
        static int toASCII(int fractBits,final int binaryExponent,final char[] buffer,final int offset){
            if(fractBits==0){
                if(binaryExponent<-50){ return BigMathLT1.toASCII(binaryExponent,buffer,offset); }
                if(binaryExponent<-9){ return LongMathLT1.toASCIIEForm(binaryExponent,buffer,offset); }
                return pow2FForm(binaryExponent,buffer,offset);
            }
            fractBits|=0x800000;
            if(binaryExponent<-51){ return BigMathLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            if(binaryExponent<-5){ return LongMathLT1.toASCII(fractBits,binaryExponent,buffer,offset); }
            return IntMathLT1.toASCII(fractBits,binaryExponent,buffer,offset);
        }
        /** A specialized case where the fraction bits normalized to 1.
         *
         * @param binaryExponent
         *          The biased binary exponent of the floating point number.
         * @param buffer
         *          The buffer to insert the characters into.
         * @param offset
         *          The first offset in the buffer to place characters at.
         * @return The index in the buffer where the last character was placed PLUS ONE. */
        private static int pow2FForm(final int binaryExponent,final char[] buffer,int offset){
            buffer[offset]=(char)'0';
            buffer[++offset]=(char)'.';
            switch(binaryExponent){
            case -1:
                break;
            case -7:
            case -8:
            case -9:
                buffer[++offset]=(char)'0';
            default:
                buffer[++offset]=(char)'0';
                switch(binaryExponent){
                case -8:
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'9';
                    buffer[++offset]=(char)'0';
                    buffer[++offset]=(char)'6';
                    break;
                case -7:
                    buffer[++offset]=(char)'7';
                    buffer[++offset]=(char)'8';
                    buffer[++offset]=(char)'1';
                    break;
                case -6:
                    buffer[++offset]=(char)'1';
                    buffer[++offset]=(char)'5';
                case -4:
                    buffer[++offset]=(char)'6';
                    break;
                case -9:
                    buffer[++offset]=(char)'1';
                    buffer[++offset]=(char)'9';
                    buffer[++offset]=(char)'5';
                default:
                    buffer[++offset]=(char)'3';
                    buffer[++offset]=(char)'1';
                }
                buffer[++offset]=(char)'2';
                break;
            case -3:
                buffer[++offset]=(char)'1';
            case -2:
                buffer[++offset]=(char)'2';
            }
            buffer[++offset]=(char)'5';
            return ++offset;
        }
        private static abstract class BigMathLT1 implements BigMathFltToASCII{
            long b;
            long m;
            static int toASCII(int binaryExponent,final byte[] buffer,int offset){
                int decimalExponent;
                int s=25-binaryExponent-(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent]);
                long b,m;
                long[] pow5Arr;
                if(decimalExponent<27){
                    b=(pow5Arr=POW_5_64)[decimalExponent-1]>>>(s-=60)<<24;
                    m=pow5Arr[decimalExponent]>>>s;
                }else{
                    b=(pow5Arr=POW_5_128)[binaryExponent=decimalExponent-26<<1]>>>s-84|pow5Arr[++binaryExponent]<<148-s;
                    m=pow5Arr[binaryExponent+2]<<124-s;
                }
                s=(int)(b>>>59);
                b=(b&(1L<<59)-1)*10;
                buffer[offset]=(byte)(s+'0');
                buffer[++offset]=(byte)'.';
                return printBigIntLT1Exp(decimalExponent,buffer,fractionLoop(b,m,buffer,offset));
            }
            static int toASCII(final int fractBits,final int binaryExponent,final byte[] buffer,final int offset){
                int decimalExponent;
                int shiftBias;
                BigMathFltToASCII bsm;
                if((shiftBias=24-binaryExponent-(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)))<92){
                    bsm=new BigMathLT1LowBias(fractBits,decimalExponent,92-shiftBias);
                }else if(shiftBias==92){
                    bsm=new BigMathLT1Unbiased(fractBits,decimalExponent);
                }else{
                    bsm=new BigMathLT1HighBias(fractBits,decimalExponent,124-shiftBias);
                }
                return bsm.getBytes(decimalExponent,buffer,offset);
            }
            private static int fractionLoop(long b,long m,final byte[] buffer,int offset){
                int c;
                for(;;){
                    c=(int)(b>>>59);
                    if((b&=(1L<<59)-1)+m>1L<<59){
                        if(b>1L<<58){
                            ++c;
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    buffer[++offset]=(byte)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(byte)(c+'0');
                return offset;
            }
            private static int printBigIntLT1Exp(int decimalExponent,final byte[] buffer,int offset){
                buffer[++offset]=(byte)'E';
                buffer[++offset]=(byte)'-';
                if(decimalExponent>29){
                    buffer[++offset]=(byte)'3';
                    decimalExponent-=30;
                }else if(decimalExponent>19){
                    buffer[++offset]=(byte)'2';
                    decimalExponent-=20;
                }else{
                    buffer[++offset]=(byte)'1';
                    decimalExponent-=10;
                }
                buffer[++offset]=(byte)(decimalExponent+'0');
                return ++offset;
            }
            private static int printFraction(final long b,final long m,final byte[] buffer,int offset){
                buffer[++offset]=(byte)'.';
                if(b<m){
                    buffer[++offset]=(byte)'0';
                }else{
                    offset=fractionLoop(b,m,buffer,offset);
                }
                return offset;
            }
            static int toASCII(int binaryExponent,final char[] buffer,int offset){
                int decimalExponent;
                int s=25-binaryExponent-(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent]);
                long b,m;
                long[] pow5Arr;
                if(decimalExponent<27){
                    b=(pow5Arr=POW_5_64)[decimalExponent-1]>>>(s-=60)<<24;
                    m=pow5Arr[decimalExponent]>>>s;
                }else{
                    b=(pow5Arr=POW_5_128)[binaryExponent=decimalExponent-26<<1]>>>s-84|pow5Arr[++binaryExponent]<<148-s;
                    m=pow5Arr[binaryExponent+2]<<124-s;
                }
                s=(int)(b>>>59);
                b=(b&(1L<<59)-1)*10;
                buffer[offset]=(char)(s+'0');
                buffer[++offset]=(char)'.';
                return printBigIntLT1Exp(decimalExponent,buffer,fractionLoop(b,m,buffer,offset));
            }
            static int toASCII(final int fractBits,final int binaryExponent,final char[] buffer,final int offset){
                int decimalExponent;
                int shiftBias;
                BigMathFltToASCII bsm;
                if((shiftBias=24-binaryExponent-(decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)))<92){
                    bsm=new BigMathLT1LowBias(fractBits,decimalExponent,92-shiftBias);
                }else if(shiftBias==92){
                    bsm=new BigMathLT1Unbiased(fractBits,decimalExponent);
                }else{
                    bsm=new BigMathLT1HighBias(fractBits,decimalExponent,124-shiftBias);
                }
                return bsm.getChars(decimalExponent,buffer,offset);
            }
            private static int fractionLoop(long b,long m,final char[] buffer,int offset){
                int c;
                for(;;){
                    c=(int)(b>>>59);
                    if((b&=(1L<<59)-1)+m>1L<<59){
                        if(b>1L<<58){
                            ++c;
                        }
                        break;
                    }
                    if(b<m){
                        break;
                    }
                    buffer[++offset]=(char)(c+'0');
                    b*=10;
                    m*=10;
                }
                buffer[++offset]=(char)(c+'0');
                return offset;
            }
            private static int printBigIntLT1Exp(int decimalExponent,final char[] buffer,int offset){
                buffer[++offset]=(char)'E';
                buffer[++offset]=(char)'-';
                if(decimalExponent>29){
                    buffer[++offset]=(char)'3';
                    decimalExponent-=30;
                }else if(decimalExponent>19){
                    buffer[++offset]=(char)'2';
                    decimalExponent-=20;
                }else{
                    buffer[++offset]=(char)'1';
                    decimalExponent-=10;
                }
                buffer[++offset]=(char)(decimalExponent+'0');
                return ++offset;
            }
            private static int printFraction(final long b,final long m,final char[] buffer,int offset){
                buffer[++offset]=(char)'.';
                if(b<m){
                    buffer[++offset]=(char)'0';
                }else{
                    offset=fractionLoop(b,m,buffer,offset);
                }
                return offset;
            }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                // TODO this method is probably too big.
                // consider refactoring
                switch(binaryExponent){
                case -54:
                    if(fractBits>15111572){ return 16; }
                    break;
                case -57:
                    if(fractBits>12089257){ return 17; }
                    break;
                case -60:
                    if(fractBits>9671406){ return 18; }
                    break;
                case -64:
                    if(fractBits>15474249){ return 19; }
                    break;
                case -67:
                    if(fractBits>12379399){ return 20; }
                    break;
                case -70:
                    if(fractBits>9903519){ return 21; }
                    break;
                case -74:
                    if(fractBits>15845632){ return 22; }
                    break;
                case -77:
                    if(fractBits>12676505){ return 23; }
                    break;
                case -80:
                    if(fractBits>10141204){ return 24; }
                    break;
                case -84:
                    if(fractBits>16225927){ return 25; }
                    break;
                case -87:
                    if(fractBits>12980741){ return 26; }
                    break;
                case -90:
                    if(fractBits>10384593){ return 27; }
                    break;
                case -94:
                    if(fractBits>16615349){ return 28; }
                    break;
                case -97:
                    if(fractBits>13292279){ return 29; }
                    break;
                case -100:
                    if(fractBits>10633823){ return 30; }
                    break;
                case -103:
                    if(fractBits>8507058){ return 31; }
                    break;
                case -107:
                    if(fractBits>13611294){ return 32; }
                    break;
                case -110:
                    if(fractBits>10889035){ return 33; }
                    break;
                case -113:
                    if(fractBits>8711228){ return 34; }
                    break;
                case -117:
                    if(fractBits>13937965){ return 35; }
                    break;
                case -120:
                    if(fractBits>11150372){ return 36; }
                    break;
                case -123:
                    if(fractBits>8920297){ return 37; }
                default:
                    break;
                }
                return ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent];
            }
            private static class BigMathLT1HighBias extends BigMathLT1ShortCircuit{
                public BigMathLT1HighBias(final int fractBits,int decimalExponent,final int shiftBias){
                    long[] pow5Arr;
                    long carry;
                    b=(((carry=(pow5Arr=POW_5_128)[decimalExponent=decimalExponent-26<<1])>>>32)*fractBits
                            +((carry&0xffffffffL)*fractBits>>>32)>>>32-shiftBias)+(pow5Arr[++decimalExponent]*fractBits<<shiftBias);
                    m=pow5Arr[++decimalExponent]>>>64-shiftBias|pow5Arr[++decimalExponent]<<shiftBias;
                }
            }
            private static class BigMathLT1LowBias extends BigMathLT1ShortCircuit{
                public BigMathLT1LowBias(final int fractBits,int decimalExponent,int shiftBias){
                    long[] pow5Arr;
                    long carry;
                    if(decimalExponent<27){
                        b=((carry=(pow5Arr=POW_5_64)[decimalExponent-1])>>32)*fractBits
                                +((carry=(carry&0xffffffffL)*fractBits)>>32)<<shiftBias|(carry&0xffffffffL)>>>(shiftBias=32-shiftBias);
                        m=pow5Arr[decimalExponent]>>>shiftBias;
                    }else{
                        b=(((carry=(pow5Arr=POW_5_128)[decimalExponent=decimalExponent-26<<1])>>>32)*fractBits
                                +((carry&0xffffffffL)*fractBits>>>32)<<shiftBias)+(pow5Arr[++decimalExponent]*fractBits<<shiftBias+32);
                        m=pow5Arr[++decimalExponent]>>>32-shiftBias|pow5Arr[++decimalExponent]<<32+shiftBias;
                    }
                }
            }
            private static class BigMathLT1NonShortCircuit extends BigMathLT1{
              @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                  long b;
                  final int c=(int)((b=this.b)>>>59);
                  b=(b&(1L<<59)-1)*10;
                  buffer[offset]=(byte)(c+'0');
                  offset=printFraction(b,m,buffer,offset);
                  return printBigIntLT1Exp(decimalExponent,buffer,offset);
              }
              @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                  long b;
                  final int c=(int)((b=this.b)>>>59);
                  b=(b&(1L<<59)-1)*10;
                  buffer[offset]=(char)(c+'0');
                  offset=printFraction(b,m,buffer,offset);
                  return printBigIntLT1Exp(decimalExponent,buffer,offset);
              }
            }
            private static class BigMathLT1ShortCircuit extends BigMathLT1{
              @Override public int getBytes(final int decimalExponent,final byte[] buffer,int offset){
                  long b,m;
                  final int c=(int)((b=this.b)>>>59);
                  if((b=(b&(1L<<59)-1)*10)+(m=this.m)>10L<<59){
                      buffer[offset]=(byte)(c+'1');
                      buffer[++offset]=(byte)'.';
                      buffer[++offset]=(byte)'0';
                  }else{
                      buffer[offset]=(byte)(c+'0');
                      offset=printFraction(b,m,buffer,offset);
                  }
                  return printBigIntLT1Exp(decimalExponent,buffer,offset);
              }
              @Override public int getChars(final int decimalExponent,final char[] buffer,int offset){
                  long b,m;
                  final int c=(int)((b=this.b)>>>59);
                  if((b=(b&(1L<<59)-1)*10)+(m=this.m)>10L<<59){
                      buffer[offset]=(char)(c+'1');
                      buffer[++offset]=(char)'.';
                      buffer[++offset]=(char)'0';
                  }else{
                      buffer[offset]=(char)(c+'0');
                      offset=printFraction(b,m,buffer,offset);
                  }
                  return printBigIntLT1Exp(decimalExponent,buffer,offset);
              }
            }
            private static class BigMathLT1Unbiased extends BigMathLT1NonShortCircuit{
                public BigMathLT1Unbiased(final int fractBits,int decimalExponent){
                    long[] pow5Arr;
                    long carry;
                    b=((carry=(pow5Arr=POW_5_128)[decimalExponent=decimalExponent-26<<1])>>>32)*fractBits
                            +((carry&0xffffffffL)*fractBits>>>32)+(pow5Arr[++decimalExponent]*fractBits<<32);
                    m=pow5Arr[++decimalExponent]>>>32|pow5Arr[++decimalExponent]<<32;
                }
            }
        }
        private static class IntMathLT1{
          static int toASCII(final int fractBits,final int binaryExponent,final byte[] buffer,int offset){
              final int decimalExponent;
              int b,m;
              final int sPow;
              int c=(b=(m=POW_5_32[decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)])*fractBits<<1)>>>(sPow
                      =24-binaryExponent-decimalExponent);
              int sModMask;
              int s;
              b&=sModMask=(s=1<<sPow)-1;
              buffer[offset]=(byte)'0';
              buffer[++offset]=(byte)'.';
              offset=fillZeros(offset,buffer,offset+decimalExponent-1)+1;
              if(b+m>s){
                  buffer[offset]=(byte)(c+'1');
              }else{
                  buffer[offset]=(byte)(c+'0');
                  if(b>=m){
                      for(;;){
                          c=(b*=10)>>>sPow;
                          if((b&=sModMask)+(m*=10)>s){
                              int lowDigitDif;
                              if(b>=m||(lowDigitDif=(b<<1)-s)>0||lowDigitDif==0&&(c&1)!=0){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(byte)(c+'0');
                      }
                      buffer[++offset]=(byte)(c+'0');
                  }
              }
              return ++offset;
          }
          static int toASCII(final int fractBits,final int binaryExponent,final char[] buffer,int offset){
              final int decimalExponent;
              int b,m;
              final int sPow;
              int c=(b=(m=POW_5_32[decimalExponent=estimateDecimalExponent(fractBits,binaryExponent)])*fractBits<<1)>>>(sPow
                      =24-binaryExponent-decimalExponent);
              int sModMask;
              int s;
              b&=sModMask=(s=1<<sPow)-1;
              buffer[offset]=(char)'0';
              buffer[++offset]=(char)'.';
              offset=fillZeros(offset,buffer,offset+decimalExponent-1)+1;
              if(b+m>s){
                  buffer[offset]=(char)(c+'1');
              }else{
                  buffer[offset]=(char)(c+'0');
                  if(b>=m){
                      for(;;){
                          c=(b*=10)>>>sPow;
                          if((b&=sModMask)+(m*=10)>s){
                              int lowDigitDif;
                              if(b>=m||(lowDigitDif=(b<<1)-s)>0||lowDigitDif==0&&(c&1)!=0){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(char)(c+'0');
                      }
                      buffer[++offset]=(char)(c+'0');
                  }
              }
              return ++offset;
          }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                if(binaryExponent==-4&&fractBits>13421772){ return 1; }
                return ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent];
            }
        }
        private static class LongMathLT1{
          static int toASCII(final int fractBits,final int binaryExponent,final byte[] buffer,final int offset){
              final int decimalExponent;
              if((decimalExponent=estimateDecimalExponent(fractBits,binaryExponent))<4){ return toASCIIFForm(fractBits,binaryExponent,buffer,offset,decimalExponent); }
              return toASCIIEForm(fractBits,binaryExponent,buffer,offset,decimalExponent);
          }
          static int toASCIIEForm(final int binaryExponent,final byte[] buffer,int offset){
              int decimalExponent;
              long b,m,s,sModMask;
              int sPow;
              buffer[offset]
                      =(byte)(((b=(m=POW_5_64[(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent])-1])<<25)>>>(sPow
                              =25-binaryExponent-decimalExponent))+'0');
              buffer[++offset]=(byte)'.';
              b&=sModMask=(s=1L<<sPow)-1L;
              int c;
              for(;;){
                  c=(int)((b*=10L)>>>sPow);
                  if((b&=sModMask)+(m*=10L)>s){
                      if(b<<1>s){
                          ++c;
                      }
                      break;
                  }
                  if(b<m){
                      break;
                  }
                  buffer[++offset]=(byte)(c+'0');
              }
              buffer[++offset]=(byte)(c+'0');
              return printLongLT1Exp(decimalExponent,buffer,offset);
          }
          private static int printLongLT1Exp(int decimalExponent,final byte[] buffer,int offset){
              buffer[++offset]=(byte)'E';
              buffer[++offset]=(byte)'-';
              if(decimalExponent>9){
                  buffer[++offset]=(byte)'1';
                  decimalExponent-=10;
              }
              buffer[++offset]=(byte)(decimalExponent+'0');
              return ++offset;
          }
          private static int toASCIIEForm(final int fractBits,final int binaryExponent,final byte[] buffer,int offset,
                  final int decimalExponent){
              long m;
              final int sPow;
              long b;
              int c=(int)((b=fractBits*(m=POW_5_64[decimalExponent-1])<<1)>>>(sPow=24-binaryExponent-decimalExponent));
              long s;
              long sModMask;
              if((b&=sModMask=(s=1L<<sPow)-1L)+m>s){
                  buffer[offset]=(byte)(c+'1');
                  buffer[++offset]=(byte)'.';
                  buffer[++offset]=(byte)'0';
              }else{
                  buffer[offset]=(byte)(c+'0');
                  buffer[++offset]=(byte)'.';
                  if(b<m){
                      buffer[++offset]=(byte)'0';
                  }else{
                      for(;;){
                          c=(int)((b*=10L)>>>sPow);
                          if((b&=sModMask)+(m*=10L)>s){
                              if(b<<1>s){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(byte)(c+'0');
                      }
                      buffer[++offset]=(byte)(c+'0');
                  }
              }
              return printLongLT1Exp(decimalExponent,buffer,offset);
          }
          private static int toASCIIFForm(final int fractBits,final int binaryExponent,final byte[] buffer,int offset,
                  final int decimalExponent){
              long m;
              final int sPow;
              long b;
              final long s;
              int c=(int)((b=fractBits*(m=POW_5_64[decimalExponent-1])<<1)>>>(sPow=24-binaryExponent-decimalExponent));
              buffer[offset]=(byte)'0';
              buffer[++offset]=(byte)'.';
              offset=fillZeros(offset,buffer,offset+decimalExponent-1);
              final long sModMask;
              if((b&=sModMask=(s=1L<<sPow)-1)+m>s){
                  buffer[++offset]=(byte)(c+'1');
              }else{
                  buffer[++offset]=(byte)(c+'0');
                  if(b>=m){
                      for(;;){
                          c=(int)((b*=10)>>>sPow);
                          if((b&=sModMask)+(m*=10)>s){
                              int lowDigitDif;
                              if(b>=m||(lowDigitDif=(int)((b<<1)-s))>0||lowDigitDif==0&&(c&1)!=0){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(byte)(c+'0');
                      }
                      buffer[++offset]=(byte)(c+'0');
                  }
              }
              return ++offset;
          }
          static int toASCII(final int fractBits,final int binaryExponent,final char[] buffer,final int offset){
              final int decimalExponent;
              if((decimalExponent=estimateDecimalExponent(fractBits,binaryExponent))<4){ return toASCIIFForm(fractBits,binaryExponent,buffer,offset,decimalExponent); }
              return toASCIIEForm(fractBits,binaryExponent,buffer,offset,decimalExponent);
          }
          static int toASCIIEForm(final int binaryExponent,final char[] buffer,int offset){
              int decimalExponent;
              long b,m,s,sModMask;
              int sPow;
              buffer[offset]
                      =(char)(((b=(m=POW_5_64[(decimalExponent=ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent])-1])<<25)>>>(sPow
                              =25-binaryExponent-decimalExponent))+'0');
              buffer[++offset]=(char)'.';
              b&=sModMask=(s=1L<<sPow)-1L;
              int c;
              for(;;){
                  c=(int)((b*=10L)>>>sPow);
                  if((b&=sModMask)+(m*=10L)>s){
                      if(b<<1>s){
                          ++c;
                      }
                      break;
                  }
                  if(b<m){
                      break;
                  }
                  buffer[++offset]=(char)(c+'0');
              }
              buffer[++offset]=(char)(c+'0');
              return printLongLT1Exp(decimalExponent,buffer,offset);
          }
          private static int printLongLT1Exp(int decimalExponent,final char[] buffer,int offset){
              buffer[++offset]=(char)'E';
              buffer[++offset]=(char)'-';
              if(decimalExponent>9){
                  buffer[++offset]=(char)'1';
                  decimalExponent-=10;
              }
              buffer[++offset]=(char)(decimalExponent+'0');
              return ++offset;
          }
          private static int toASCIIEForm(final int fractBits,final int binaryExponent,final char[] buffer,int offset,
                  final int decimalExponent){
              long m;
              final int sPow;
              long b;
              int c=(int)((b=fractBits*(m=POW_5_64[decimalExponent-1])<<1)>>>(sPow=24-binaryExponent-decimalExponent));
              long s;
              long sModMask;
              if((b&=sModMask=(s=1L<<sPow)-1L)+m>s){
                  buffer[offset]=(char)(c+'1');
                  buffer[++offset]=(char)'.';
                  buffer[++offset]=(char)'0';
              }else{
                  buffer[offset]=(char)(c+'0');
                  buffer[++offset]=(char)'.';
                  if(b<m){
                      buffer[++offset]=(char)'0';
                  }else{
                      for(;;){
                          c=(int)((b*=10L)>>>sPow);
                          if((b&=sModMask)+(m*=10L)>s){
                              if(b<<1>s){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(char)(c+'0');
                      }
                      buffer[++offset]=(char)(c+'0');
                  }
              }
              return printLongLT1Exp(decimalExponent,buffer,offset);
          }
          private static int toASCIIFForm(final int fractBits,final int binaryExponent,final char[] buffer,int offset,
                  final int decimalExponent){
              long m;
              final int sPow;
              long b;
              final long s;
              int c=(int)((b=fractBits*(m=POW_5_64[decimalExponent-1])<<1)>>>(sPow=24-binaryExponent-decimalExponent));
              buffer[offset]=(char)'0';
              buffer[++offset]=(char)'.';
              offset=fillZeros(offset,buffer,offset+decimalExponent-1);
              final long sModMask;
              if((b&=sModMask=(s=1L<<sPow)-1)+m>s){
                  buffer[++offset]=(char)(c+'1');
              }else{
                  buffer[++offset]=(char)(c+'0');
                  if(b>=m){
                      for(;;){
                          c=(int)((b*=10)>>>sPow);
                          if((b&=sModMask)+(m*=10)>s){
                              int lowDigitDif;
                              if(b>=m||(lowDigitDif=(int)((b<<1)-s))>0||lowDigitDif==0&&(c&1)!=0){
                                  ++c;
                              }
                              break;
                          }
                          if(b<m){
                              break;
                          }
                          buffer[++offset]=(char)(c+'0');
                      }
                      buffer[++offset]=(char)(c+'0');
                  }
              }
              return ++offset;
          }
            private static int estimateDecimalExponent(final int fractBits,final int binaryExponent){
                switch(binaryExponent){
                case -7:
                    if(fractBits>10737417){ return 2; }
                    break;
                case -10:
                    if(fractBits>8589934){ return 3; }
                    break;
                case -14:
                    if(fractBits>13743894){ return 4; }
                    break;
                case -17:
                    if(fractBits>10995115){ return 5; }
                    break;
                case -20:
                    if(fractBits>8796092){ return 6; }
                    break;
                case -24:
                    if(fractBits>14073748){ return 7; }
                    break;
                case -27:
                    if(fractBits>11258998){ return 8; }
                    break;
                case -30:
                    if(fractBits>9007198){ return 9; }
                    break;
                case -34:
                    if(fractBits>14411518){ return 10; }
                    break;
                case -37:
                    if(fractBits>11529214){ return 11; }
                    break;
                case -40:
                    if(fractBits>9223371){ return 12; }
                    break;
                case -44:
                    if(fractBits>14757394){ return 13; }
                    break;
                case -47:
                    if(fractBits>11805915){ return 14; }
                    break;
                case -50:
                    if(fractBits>9444732){ return 15; }
                default:
                    break;
                }
                return ESTIMATE_DECIMAL_EXPONENT[-1-binaryExponent];
            }
        }
    }
    private final static int[] INT_SIZE_TABLE={9,99,999,9999,99999,999999,9999999,99999999,999999999,Integer.MAX_VALUE};
    /** Powers of 5. */
    private static final int[] POW_5_32={1,// pow5=0;
            5,// pow5=1
            5 * 5,// pow5=2;
            5 * 5 * 5,// pow5=3;
            5 * 5 * 5 * 5,// pow5=4;
            5 * 5 * 5 * 5 * 5,// pow5=5
            5 * 5 * 5 * 5 * 5 * 5,// pow5=6
    };
    /** Powers of 5. */
    private static final long[] POW_5_64={5L,// pow5=1
            5L * 5,// pow5=2
            5L * 5 * 5,// pow5=3
            5L * 5 * 5 * 5,// pow5=4
            5L * 5 * 5 * 5 * 5,// pow5=5
            5L * 5 * 5 * 5 * 5 * 5,// pow5=6
            5L * 5 * 5 * 5 * 5 * 5 * 5,// pow5=7
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=8
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=9
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=10
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=11
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=12
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=13
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=14
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=15
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=16
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=17
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=18
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=29
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=20
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=21
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=22
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=23
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=24
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=25
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=26
            5L * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5 * 5,// pow5=27
    };
    /** Powers of 5 (each power takes up two long values) */
    private static final long[] POW_5_128=new long[]{0x14ADF4B7320334B9L,0x0000000000000000L,// pow5=26
            0x6765C793FA10079DL,0x0000000000000000L,// pow5=27
            0x04FCE5E3E2502611L,0x0000000000000002L,// pow5=28
            0x18F07D736B90BE55L,0x000000000000000AL,// pow5=29
            0x7CB2734119D3B7A9L,0x0000000000000032L,// pow5=30
            0x6F7C40458122964DL,0x00000000000000FCL,// pow5=31
            0x2D6D415B85ACEF81L,0x00000000000004EEL,// pow5=32
            0xE32246C99C60AD85L,0x00000000000018A6L,// pow5=33
            0x6FAB61F00DE36399L,0x0000000000007B42L,// pow5=34
            0x2E58E9B04570F1FDL,0x000000000002684CL,// pow5=35
            0xE7BC90715B34B9F1L,0x00000000000C097CL,// pow5=36
            0x86AED236C807A1B5L,0x00000000003C2F70L,// pow5=37
            0xA16A1B11E8262889L,0x00000000012CED32L,// pow5=38
            0x2712875988BECAADL,0x0000000005E0A1FDL,// pow5=39
            0xC35CA4BFABB9F561L,0x000000001D6329F1L,// pow5=40
            0xD0CF37BE5AA1CAE5L,0x0000000092EFD1B8L,// pow5=41
            0x140C16B7C528F679L,0x00000002DEAF189CL,// pow5=42
            0x643C7196D9CCD05DL,0x0000000E596B7B0CL,// pow5=43
            0xF52E37F2410011D1L,0x00000047BF19673DL,// pow5=44
            0xC9E717BB45005915L,0x00000166BB7F0435L,// pow5=45
            0xF18376A85901BD69L,0x00000701A97B150CL,// pow5=46
    };
}
