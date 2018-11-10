package omni.impl;
public abstract class AbstractIntItr{
    public Integer next(){
        return nextInt();
    }
    protected abstract int nextInt();
}
