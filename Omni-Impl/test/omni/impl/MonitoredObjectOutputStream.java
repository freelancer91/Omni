package omni.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public abstract class MonitoredObjectOutputStream extends ObjectOutputStream{
    private ObjectOutputStream wrapped;
    public MonitoredObjectOutputStream(File file) throws FileNotFoundException, IOException {
        super();
        wrapped=new ObjectOutputStream(new FileOutputStream(file));
    }
    public MonitoredObjectOutputStream(OutputStream outputStream) throws IOException{
        super();
        wrapped=new ObjectOutputStream(outputStream);
    }
    public abstract MonitoredFunctionGen getMonitoredFunctionGen();
    protected abstract void throwingCall();
    public int numwrite_byteArrayCalls;
    @Override public void write(byte[] buf) throws IOException{
        ++numwrite_byteArrayCalls;
        throwingCall();
        wrapped.write(buf);
    }
    @Override public void write(byte[] buf,int off,int len) throws IOException{
        ++numwrite_byteArrayCalls;
        throwingCall();
        wrapped.write(buf,off,len);
    }
    public int numwriteByteCalls;
    @Override public void writeByte(int val) throws IOException{
        ++numwriteByteCalls;
        throwingCall();
        wrapped.writeByte(val);
    }
    public int numwriteShortCalls;
    @Override public void writeShort(int val) throws IOException{
        ++numwriteShortCalls;
        throwingCall();
        wrapped.writeShort(val);
    }
    public int numwriteCharCalls;
    @Override public void writeChar(int val) throws IOException{
        ++numwriteCharCalls;
        throwingCall();
        wrapped.writeChar(val);
    }
    public int numwriteIntalls;
    @Override public void writeInt(int val) throws IOException{
        ++numwriteIntalls;
        throwingCall();
        wrapped.writeInt(val);
    }
    public int numwriteLongCalls;
    @Override public void writeLong(long val) throws IOException{
        ++numwriteLongCalls;
        throwingCall();
        wrapped.writeLong(val);
    }
    public int numwriteFloatCalls;
    @Override public void writeFloat(float val) throws IOException{
        ++numwriteFloatCalls;
        throwingCall();
        wrapped.writeFloat(val);
    }
    public int numwriteDoubleCalls;
    @Override public void writeDouble(double val) throws IOException{
        ++numwriteDoubleCalls;
        throwingCall();
        wrapped.writeDouble(val);
    }
    public int numwriteObjectCalls;
    @Override protected void writeObjectOverride(Object obj) throws IOException{
        ++numwriteObjectCalls;
        throwingCall();
        wrapped.writeObject(obj);
    }
    public int numuseProtocolVersionCalls;
    @Override public void useProtocolVersion(int version) throws IOException{
        ++numuseProtocolVersionCalls;
        throwingCall();
        wrapped.useProtocolVersion(version);
    }
    public int numwriteUnsharedCalls;
    @Override public void writeUnshared(Object obj) throws IOException{
        ++numwriteUnsharedCalls;
        throwingCall();
        wrapped.writeUnshared(obj);
    }
    public int numdefaultWriteObjectCalls;
    @Override public void defaultWriteObject() throws IOException{
        ++numdefaultWriteObjectCalls;
        throwingCall();
        wrapped.defaultWriteObject();
    }
    public int numputFieldsCalls;
    @Override public PutField putFields() throws IOException{
        ++numputFieldsCalls;
        throwingCall();
        return wrapped.putFields();
    }
    public int numwriteFieldsCalls;
    @Override public void writeFields() throws IOException{
        ++numwriteFieldsCalls;
        throwingCall();
        wrapped.writeFields();
    }
    public int numresetCalls;
    @Override public void reset() throws IOException{
        ++numresetCalls;
        throwingCall();
        wrapped.reset();
    }
    public int numwriteCalls;
    @Override public void write(int val) throws IOException{
        ++numwriteCalls;
        throwingCall();
        wrapped.write(val);
    }
    public int numflushCalls;
    @Override public void flush() throws IOException{
        ++numflushCalls;
        throwingCall();
        wrapped.flush();
    }
    public int numdrainCalls;
    @Override protected void drain() throws IOException{
        ++numdrainCalls;
        throwingCall();
        super.drain();
    }
    public int numcloseCalls;
    @Override public void close() throws IOException{
        ++numcloseCalls;
        throwingCall();
        wrapped.close();
    }
    public int numwriteBooleanCalls;
    @Override public void writeBoolean(boolean val) throws IOException{
        ++numwriteBooleanCalls;
        throwingCall();
        wrapped.writeBoolean(val);
    }
    public int numwriteBytesCalls;
    @Override public void writeBytes(String str) throws IOException{
        ++numwriteBytesCalls;
        throwingCall();
        wrapped.writeBytes(str);
    }
    public int numwriteCharsCalls;
    @Override public void writeChars(String str) throws IOException{
        ++numwriteCharsCalls;
        throwingCall();
        wrapped.writeChars(str);
    }
    public int numwriteUTFCalls;
    @Override public void writeUTF(String str) throws IOException{
        ++numwriteUTFCalls;
        throwingCall();
        wrapped.writeUTF(str);
    }



}
