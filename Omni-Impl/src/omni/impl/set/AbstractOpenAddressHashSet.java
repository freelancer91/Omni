package omni.impl.set;

import java.io.Externalizable;
import omni.api.OmniSet;
import omni.impl.AbstractOmniCollection;
public abstract class AbstractOpenAddressHashSet<E> extends AbstractOmniCollection<E> implements OmniSet<E>,Externalizable,Cloneable{
    static int tableSizeFor(int n){
        // TODO consider an implementation that allows table sizes to not necessarily be
        // powers of 2
        if((n=-1 >>> Integer.numberOfLeadingZeros(n - 1)) < 0){
            return 1;
        }else if(n >= 1 << 30){
            return 1 << 30;
        }
        return n + 1;
    }
    transient int maxTableSize;
    transient float loadFactor;
    AbstractOpenAddressHashSet(){
        this.maxTableSize=16;
        this.loadFactor=.75f;
    }
    AbstractOpenAddressHashSet(AbstractOpenAddressHashSet<? extends E> that){
        super(that.size);
        this.loadFactor=that.loadFactor;
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
    static int validateInitialCapacity(int initialCapacity){
        if(initialCapacity < 0){
            throw new IllegalArgumentException("The initial capacity " + initialCapacity + " is invalid. Must be >= 0");
        }
        return initialCapacity;
    }
    static float validateLoadFactor(float loadFactor){
        if(loadFactor <= 0.0f || loadFactor > 1.0f || loadFactor != loadFactor){
            throw new IllegalArgumentException(
                    "The loadFactor " + loadFactor + " is invalid. Must be between 0 < loadFactor <= 1");
        }
        return loadFactor;
    }
    abstract void updateMaxTableSize(float loadFactor);
    public void setLoadFactor(float loadFactor){
        updateMaxTableSize(loadFactor);
        this.loadFactor=loadFactor;
    }


}
