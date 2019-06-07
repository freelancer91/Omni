package omni.impl.set;

import java.io.Externalizable;
import omni.api.OmniSet;
public abstract class AbstractOpenAddressHashSet implements OmniSet,Externalizable,Cloneable{

    static int tableSizeFor(int n){
        if((n=-1 >>> Integer.numberOfLeadingZeros(n - 1)) < 0){
            return 1;
        }else if(n >= 1 << 30){
            return 1 << 30;
        }
        return n + 1;
    }

    int size;
    int tableSize;
    int maxTableSize;
    float loadFactor;
    AbstractOpenAddressHashSet(){
        this.maxTableSize=16;
        this.loadFactor=.75f;
    }
    AbstractOpenAddressHashSet(AbstractOpenAddressHashSet that){
        this.maxTableSize=that.maxTableSize;
        this.loadFactor=that.loadFactor;
        this.size=that.size;
        this.tableSize=that.tableSize;
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
    public void clear(){
        if(size != 0){
            if(tableSize != 0){
                clearTable();
                tableSize=0;
            }
            size=0;
        }
    }
    abstract void clearTable();
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
