package omni.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    // TODO Auto-generated method stub
    return super.readObjectOverride();
  }
  public int numereadUnsharedCalls;
  @Override public Object readUnshared() throws IOException,ClassNotFoundException{
    // TODO Auto-generated method stub
    return super.readUnshared();
  }
  public int numdefaultReadObjectCalls;
  @Override public void defaultReadObject() throws IOException,ClassNotFoundException{
    // TODO Auto-generated method stub
    super.defaultReadObject();
  }
  public int numreadFieldsCalls;
  @Override public GetField readFields() throws IOException,ClassNotFoundException{
    // TODO Auto-generated method stub
    return super.readFields();
  }
  public int numregisterValidationCalls;
  @Override public void registerValidation(ObjectInputValidation obj,int prio)
      throws NotActiveException,InvalidObjectException{
    // TODO Auto-generated method stub
    super.registerValidation(obj,prio);
  }
  public int numreadCalls;
  @Override public int read() throws IOException{
    // TODO Auto-generated method stub
    return super.read();
  }
  public int numread_byteArrayCalls;
  @Override public int read(byte[] buf,int off,int len) throws IOException{
    // TODO Auto-generated method stub
    return super.read(buf,off,len);
  }
  public int numavailableCalls;
  @Override public int available() throws IOException{
    // TODO Auto-generated method stub
    return super.available();
  }
  public int numcloseCalls;
  @Override public void close() throws IOException{
    // TODO Auto-generated method stub
    super.close();
  }
  public int numreadBooleanCalls;
  @Override public boolean readBoolean() throws IOException{
    // TODO Auto-generated method stub
    return super.readBoolean();
  }
  public int numreadByteCalls;
  @Override public byte readByte() throws IOException{
    // TODO Auto-generated method stub
    return super.readByte();
  }
  public int numreadUnsignedShortCalls;
  @Override public int readUnsignedShort() throws IOException{
    // TODO Auto-generated method stub
    return super.readUnsignedShort();
  }
  public int numskipBytesCalls;
  @Override public int skipBytes(int len) throws IOException{
    // TODO Auto-generated method stub
    return super.skipBytes(len);
  }
  public int numreadLineCalls;
  @Override public String readLine() throws IOException{
    // TODO Auto-generated method stub
    return super.readLine();
  }
  public int numreadUTFCalls;
  @Override public String readUTF() throws IOException{
    // TODO Auto-generated method stub
    return super.readUTF();
  }
  

  
}
