package omni.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MonitoredObjectOutputStream extends ObjectOutputStream{
    private ObjectOutputStream wrapped;
    public MonitoredObjectOutputStream(File file) throws FileNotFoundException, IOException {
        super();
        wrapped=new ObjectOutputStream(new FileOutputStream(file));
    }
    protected void preModCall() {

    }
    public int numwrite_byteArrayCalls;
    @Override public void write(byte[] buf) throws IOException{
        ++numwrite_byteArrayCalls;
        preModCall();
        wrapped.write(buf);
    }
    @Override public void write(byte[] buf,int off,int len) throws IOException{
        ++numwrite_byteArrayCalls;
        preModCall();
        wrapped.write(buf,off,len);
    }
    public int numwriteByteCalls;
    @Override public void writeByte(int val) throws IOException{
        ++numwriteByteCalls;
        preModCall();
        wrapped.writeByte(val);
    }
    public int numwriteShortCalls;
    @Override public void writeShort(int val) throws IOException{
        ++numwriteShortCalls;
        preModCall();
        wrapped.writeShort(val);
    }
    public int numwriteCharCalls;
    @Override public void writeChar(int val) throws IOException{
        ++numwriteCharCalls;
        preModCall();
        wrapped.writeChar(val);
    }
    public int numwriteIntalls;
    @Override public void writeInt(int val) throws IOException{
        ++numwriteIntalls;
        preModCall();
        wrapped.writeInt(val);
    }
    public int numwriteLongCalls;
    @Override public void writeLong(long val) throws IOException{
        ++numwriteLongCalls;
        preModCall();
        wrapped.writeLong(val);
    }
    public int numwriteFloatCalls;
    @Override public void writeFloat(float val) throws IOException{
        ++numwriteFloatCalls;
        preModCall();
        wrapped.writeFloat(val);
    }
    public int numwriteDoubleCalls;
    @Override public void writeDouble(double val) throws IOException{
        ++numwriteDoubleCalls;
        preModCall();
        wrapped.writeDouble(val);
    }
    public int numwriteObjectCalls;
    @Override protected void writeObjectOverride(Object obj) throws IOException{
        ++numwriteObjectCalls;
        preModCall();

        wrapped.writeObject(obj);
    }
    public int numuseProtocolVersionCalls;
    @Override public void useProtocolVersion(int version) throws IOException{
        ++numuseProtocolVersionCalls;
        preModCall();
        wrapped.useProtocolVersion(version);
    }
    public int numwriteUnsharedCalls;
    @Override public void writeUnshared(Object obj) throws IOException{
        ++numwriteUnsharedCalls;
        preModCall();
        wrapped.writeUnshared(obj);
    }
    public int numdefaultWriteObjectCalls;
    @Override public void defaultWriteObject() throws IOException{
        ++numdefaultWriteObjectCalls;
        preModCall();
        wrapped.defaultWriteObject();
    }
    public int numputFieldsCalls;
    @Override public PutField putFields() throws IOException{
        ++numputFieldsCalls;
        preModCall();
        return wrapped.putFields();
    }
    public int numwriteFieldsCalls;
    @Override public void writeFields() throws IOException{
        ++numwriteFieldsCalls;
        preModCall();
        wrapped.writeFields();
    }
    public int numresetCalls;
    @Override public void reset() throws IOException{
        ++numresetCalls;
        preModCall();
        wrapped.reset();
    }
    public int numwriteCalls;
    @Override public void write(int val) throws IOException{
        ++numwriteCalls;
        preModCall();
        wrapped.write(val);
    }
    public int numflushCalls;
    @Override public void flush() throws IOException{
        ++numflushCalls;
        preModCall();
        wrapped.flush();
    }
    public int numdrainCalls;
    @Override protected void drain() throws IOException{
        ++numdrainCalls;
        preModCall();
        super.drain();
    }
    public int numcloseCalls;
    @Override public void close() throws IOException{
        ++numcloseCalls;
        preModCall();
        wrapped.close();
    }
    public int numwriteBooleanCalls;
    @Override public void writeBoolean(boolean val) throws IOException{
        ++numwriteBooleanCalls;
        preModCall();
        wrapped.writeBoolean(val);
    }
    public int numwriteBytesCalls;
    @Override public void writeBytes(String str) throws IOException{
        ++numwriteBytesCalls;
        preModCall();
        wrapped.writeBytes(str);
    }
    public int numwriteCharsCalls;
    @Override public void writeChars(String str) throws IOException{
        ++numwriteCharsCalls;
        preModCall();
        wrapped.writeChars(str);
    }
    public int numwriteUTFCalls;
    @Override public void writeUTF(String str) throws IOException{
        ++numwriteUTFCalls;
        preModCall();
        wrapped.writeUTF(str);
    }



}
