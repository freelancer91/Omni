package omni.impl;
public abstract class AbstractDoubleItr{
    public Double next(){
        return nextDouble();
    }
    protected abstract double nextDouble();
}
