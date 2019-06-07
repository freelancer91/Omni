package omni.impl.set;

public abstract class AbstractIntegralTypeOpenAddressHashSet extends AbstractOpenAddressHashSet{
    static long processWordHashCode(long word,int valOffset,int valBound,long magicWord){
        int hash=(int)(magicWord >>> 32);
        int numLeft=(int)magicWord;
        do{
            if((word & 1L << valOffset) != 0L){
                hash+=valOffset;
                if(--numLeft == 0){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return numLeft | (long)hash << 32;
    }
    transient long word0;
    transient long word1;
    transient long word2;
    transient long word3;
    AbstractIntegralTypeOpenAddressHashSet(){
        super();
    }
    AbstractIntegralTypeOpenAddressHashSet(int initialCapacity){
        super(initialCapacity);
    }
    AbstractIntegralTypeOpenAddressHashSet(float loadFactor){
        super(loadFactor);
    }
    AbstractIntegralTypeOpenAddressHashSet(AbstractIntegralTypeOpenAddressHashSet that){
        super(that);
        this.word0=that.word0;
        this.word1=that.word1;
        this.word2=that.word2;
        this.word3=that.word3;
    }
    AbstractIntegralTypeOpenAddressHashSet(int initialCapacity,float loadFactor){
        super(initialCapacity,loadFactor);
    }
    @Override
    public void clear(){
        if(size != 0){
            if(tableSize != 0){
                clearTable();
                tableSize=0;
            }
            word0=0;
            word1=0;
            word2=0;
            word3=0;
            size=0;
        }
    }
}
