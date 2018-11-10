package omni.impl;
public abstract class AbstractByteItr{
    public Byte next(){
        return nextByte();
    }
    protected abstract byte nextByte();
}
