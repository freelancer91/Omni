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

public class MonitoredObjectInputStream extends ObjectInputStream{
    private ObjectInputStream wrapped;
    public MonitoredObjectInputStream(File file) throws FileNotFoundException, IOException {
        super();
        wrapped=new ObjectInputStream(new FileInputStream(file));
    }
    public MonitoredObjectInputStream(InputStream inputStream) throws IOException{
        super();
        wrapped=new ObjectInputStream(inputStream);
    }
    protected void preModCall() {

    }
    public int numreadUnsignedByteCalls;
    @Override public int readUnsignedByte() throws IOException{
        ++numreadUnsignedByteCalls;
        preModCall();
        return wrapped.readUnsignedByte();
    }
    public int numreadCharCalls;
    @Override public char readChar() throws IOException{
        ++numreadCharCalls;
        preModCall();
        return wrapped.readChar();
    }
    public int numreadShortCalls;
    @Override public short readShort() throws IOException{
        ++numreadShortCalls;
        preModCall();
        return wrapped.readShort();
    }
    public int numreadIntCalls;
    @Override public int readInt() throws IOException{
        ++numreadIntCalls;
        preModCall();
        return wrapped.readInt();
    }
    public int numreadLongCalls;
    @Override public long readLong() throws IOException{
        ++numreadLongCalls;
        preModCall();
        return wrapped.readLong();
    }
    public int numreadFloatCalls;
    @Override public float readFloat() throws IOException{
        ++numreadFloatCalls;
        preModCall();
        return wrapped.readFloat();
    }
    public int numreadDoubleCalls;
    @Override public double readDouble() throws IOException{
        ++numreadDoubleCalls;
        preModCall();
        return wrapped.readDouble();
    }
    public int numreadFullyCalls;
    @Override public void readFully(byte[] buf) throws IOException{
        ++numreadFullyCalls;
        preModCall();
        wrapped.readFully(buf);
    }

    @Override public void readFully(byte[] buf,int off,int len) throws IOException{
        ++numreadFullyCalls;
        preModCall();
        wrapped.readFully(buf,off,len);
    }
    public int numreadObjectCalls;
    @Override protected Object readObjectOverride() throws IOException,ClassNotFoundException{
        ++numreadObjectCalls;
        preModCall();
        return wrapped.readObject();
    }
    public int numreadUnsharedCalls;
    @Override public Object readUnshared() throws IOException,ClassNotFoundException{
        ++numreadUnsharedCalls;
        preModCall();
        return wrapped.readUnshared();
    }
    public int numdefaultReadObjectCalls;
    @Override public void defaultReadObject() throws IOException,ClassNotFoundException{
        ++numdefaultReadObjectCalls;
        preModCall();
        wrapped.defaultReadObject();
    }
    public int numreadFieldsCalls;
    @Override public GetField readFields() throws IOException,ClassNotFoundException{
        ++numreadFieldsCalls;
        preModCall();
        return wrapped.readFields();
    }
    public int numregisterValidationCalls;
    @Override public void registerValidation(ObjectInputValidation obj,int prio)
            throws NotActiveException,InvalidObjectException{
        ++numregisterValidationCalls;
        preModCall();
        wrapped.registerValidation(obj,prio);
    }
    public int numreadCalls;
    @Override public int read() throws IOException{
        ++numreadCalls;
        preModCall();
        return wrapped.read();
    }
    public int numread_byteArrayCalls;
    @Override public int read(byte[] buf,int off,int len) throws IOException{
        ++numread_byteArrayCalls;
        preModCall();
        return wrapped.read(buf,off,len);
    }
    public int numavailableCalls;
    @Override public int available() throws IOException{
        ++numavailableCalls;
        preModCall();
        return wrapped.available();
    }
    public int numcloseCalls;
    @Override public void close() throws IOException{
        ++numcloseCalls;
        preModCall();
        wrapped.close();
    }
    public int numreadBooleanCalls;
    @Override public boolean readBoolean() throws IOException{
        ++numreadBooleanCalls;
        preModCall();
        return wrapped.readBoolean();
    }
    public int numreadByteCalls;
    @Override public byte readByte() throws IOException{
        ++numreadByteCalls;
        preModCall();
        return wrapped.readByte();
    }
    public int numreadUnsignedShortCalls;
    @Override public int readUnsignedShort() throws IOException{
        ++numreadUnsignedShortCalls;
        preModCall();
        return wrapped.readUnsignedShort();
    }
    public int numskipBytesCalls;
    @Override public int skipBytes(int len) throws IOException{
        ++numskipBytesCalls;
        preModCall();
        return wrapped.skipBytes(len);
    }
    public int numreadUTFCalls;
    @Override public String readUTF() throws IOException{
        ++numreadUTFCalls;
        preModCall();
        return wrapped.readUTF();
    }



}
