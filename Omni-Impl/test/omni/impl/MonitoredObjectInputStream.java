package omni.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;

public abstract class MonitoredObjectInputStream extends ObjectInputStream{
    private ObjectInputStream wrapped;
    public MonitoredObjectInputStream(File file) throws FileNotFoundException, IOException {
        super();
        wrapped=new ObjectInputStream(new FileInputStream(file));
    }
    public MonitoredObjectInputStream(InputStream inputStream) throws IOException{
        super();
        wrapped=new ObjectInputStream(inputStream);
    }
    public abstract MonitoredFunctionGen getMonitoredFunctionGen();
    protected abstract void throwingCall();
    public int numreadUnsignedByteCalls;
    @Override public int readUnsignedByte() throws IOException{
        ++numreadUnsignedByteCalls;
        throwingCall();
        return wrapped.readUnsignedByte();
    }
    public int numreadCharCalls;
    @Override public char readChar() throws IOException{
        ++numreadCharCalls;
        throwingCall();
        return wrapped.readChar();
    }
    public int numreadShortCalls;
    @Override public short readShort() throws IOException{
        ++numreadShortCalls;
        throwingCall();
        return wrapped.readShort();
    }
    public int numreadIntCalls;
    @Override public int readInt() throws IOException{
        ++numreadIntCalls;
        throwingCall();
        return wrapped.readInt();
    }
    public int numreadLongCalls;
    @Override public long readLong() throws IOException{
        ++numreadLongCalls;
        throwingCall();
        return wrapped.readLong();
    }
    public int numreadFloatCalls;
    @Override public float readFloat() throws IOException{
        ++numreadFloatCalls;
        throwingCall();
        return wrapped.readFloat();
    }
    public int numreadDoubleCalls;
    @Override public double readDouble() throws IOException{
        ++numreadDoubleCalls;
        throwingCall();
        return wrapped.readDouble();
    }
    public int numreadFullyCalls;
    @Override public void readFully(byte[] buf) throws IOException{
        ++numreadFullyCalls;
        throwingCall();
        wrapped.readFully(buf);
    }

    @Override public void readFully(byte[] buf,int off,int len) throws IOException{
        ++numreadFullyCalls;
        throwingCall();
        wrapped.readFully(buf,off,len);
    }
    public int numreadObjectCalls;
    @Override protected Object readObjectOverride() throws IOException,ClassNotFoundException{
        ++numreadObjectCalls;
        throwingCall();
        return wrapped.readObject();
    }
    public int numreadUnsharedCalls;
    @Override public Object readUnshared() throws IOException,ClassNotFoundException{
        ++numreadUnsharedCalls;
        throwingCall();
        return wrapped.readUnshared();
    }
    public int numdefaultReadObjectCalls;
    @Override public void defaultReadObject() throws IOException,ClassNotFoundException{
        ++numdefaultReadObjectCalls;
        throwingCall();
        wrapped.defaultReadObject();
    }
    public int numreadFieldsCalls;
    @Override public GetField readFields() throws IOException,ClassNotFoundException{
        ++numreadFieldsCalls;
        throwingCall();
        return wrapped.readFields();
    }
    public int numregisterValidationCalls;
    @Override public void registerValidation(ObjectInputValidation obj,int prio)
            throws NotActiveException,InvalidObjectException{
        ++numregisterValidationCalls;
        throwingCall();
        wrapped.registerValidation(obj,prio);
    }
    public int numreadCalls;
    @Override public int read() throws IOException{
        ++numreadCalls;
        throwingCall();
        return wrapped.read();
    }
    public int numread_byteArrayCalls;
    @Override public int read(byte[] buf,int off,int len) throws IOException{
        ++numread_byteArrayCalls;
        throwingCall();
        return wrapped.read(buf,off,len);
    }
    public int numavailableCalls;
    @Override public int available() throws IOException{
        ++numavailableCalls;
        throwingCall();
        return wrapped.available();
    }
    public int numcloseCalls;
    @Override public void close() throws IOException{
        ++numcloseCalls;
        throwingCall();
        wrapped.close();
    }
    public int numreadBooleanCalls;
    @Override public boolean readBoolean() throws IOException{
        ++numreadBooleanCalls;
        throwingCall();
        return wrapped.readBoolean();
    }
    public int numreadByteCalls;
    @Override public byte readByte() throws IOException{
        ++numreadByteCalls;
        throwingCall();
        return wrapped.readByte();
    }
    public int numreadUnsignedShortCalls;
    @Override public int readUnsignedShort() throws IOException{
        ++numreadUnsignedShortCalls;
        throwingCall();
        return wrapped.readUnsignedShort();
    }
    public int numskipBytesCalls;
    @Override public int skipBytes(int len) throws IOException{
        ++numskipBytesCalls;
        throwingCall();
        return wrapped.skipBytes(len);
    }
    public int numreadUTFCalls;
    @Override public String readUTF() throws IOException{
        ++numreadUTFCalls;
        throwingCall();
        return wrapped.readUTF();
    }



}
