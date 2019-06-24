package omni.impl.set;

import java.io.Externalizable;
import omni.api.OmniSet;
public abstract class AbstractOpenAddressHashSet<E> implements OmniSet<E>,Externalizable,Cloneable{
    static int tableSizeFor(int n){
        if((n=-1 >>> Integer.numberOfLeadingZeros(n - 1)) < 0){
            return 1;
        }else if(n >= 1 << 30){
            return 1 << 30;
        }
        return n + 1;
    }
    int size;
    int maxTableSize;
    float loadFactor;
    AbstractOpenAddressHashSet(){
        this.maxTableSize=16;
        this.loadFactor=.75f;
    }
    AbstractOpenAddressHashSet(AbstractOpenAddressHashSet<? extends E> that){
        this.maxTableSize=that.maxTableSize;
        this.loadFactor=that.loadFactor;
        this.size=that.size;
    }
    AbstractOpenAddressHashSet(int initialCapacity){
        this.maxTableSize=tableSizeFor(initialCapacity);
        this.loadFactor=.75f;
    }
    AbstractOpenAddressHashSet(float loadFactor){
        this.maxTableSize=16;
        this.loadFactor=loadFactor;
    }
    AbstractOpenAddressHashSet(int initialCapacity,float loadFactor){
        this.maxTableSize=tableSizeFor(initialCapacity);
        this.loadFactor=loadFactor;
    }
    abstract void updateMaxTableSize(float loadFactor);
    public void setLoadFactor(float loadFactor){
        updateMaxTableSize(loadFactor);
        this.loadFactor=loadFactor;
    }
    
    @Override
    public abstract Object clone();
    @Override
    public abstract boolean equals(Object val);
    @Override
    public boolean isEmpty(){
        return size == 0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public abstract String toString();
    @Override
    public abstract int hashCode();
}
