package omni.impl.set;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.function.CharConsumer;
import omni.function.CharPredicate;
import omni.impl.AbstractCharItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
public class CharOpenAddressHashSetOld extends AbstractIntegralTypeOpenAddressHashSet implements OmniSet.OfChar{
    @Override
    void updateMaxTableSize(float loadFactor){
        char[] table;
        if((table=this.table) != null){
            this.maxTableSize=(int)(table.length * loadFactor);
        }
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,char[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=(char)valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,Character[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=(char)valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,double[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,float[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,int[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,long[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordCopyToArray(long word,int valOffset,int valBound,Object[] dst,int dstOffset,
            int dstBound){
        do{
            if((word & 1L << valOffset) != 0L){
                dst[dstOffset]=(char)valBound;
                if(++dstOffset == dstBound){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return dstOffset;
    }
    private static int processWordForEach(long word,int valOffset,int valBound,CharConsumer action,int numLeft){
        do{
            if((word & 1L << valOffset) != 0L){
                action.accept((char)valOffset);
                if(--numLeft == 0){
                    break;
                }
            }
        }while(++valOffset != valBound);
        return numLeft;
    }
    private static long processWordToString(long word,int valOffset,int valBound,char[] buffer,long magicWord){
        int bufferOffset=(int)(magicWord >>> 32);
        int numLeft=(int)magicWord;
        do{
            if((word & 1L << valOffset) != 0L){
                buffer[++bufferOffset]=(char)valOffset;
                if(--numLeft == 0){
                    break;
                }
                buffer[++bufferOffset]=',';
                buffer[++bufferOffset]=' ';
            }
        }while(++valOffset != valBound);
        return numLeft | (long)bufferOffset << 32;
    }
    private static void quickInsert(char[] table,char val){
        int tableLength;
        for(int hash=val & (tableLength=table.length - 1);;){
            if(table[hash] == 0){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }

    private static long wordRemoveIf(long word,int valOffset,CharPredicate filter){
        long marker=1L;
        for(;;){
            if((word & marker) != 0){
                if(filter.test((char)valOffset)){
                    word&=~marker;
                }
            }
            if((marker<<=1) == 0){
                return word;
            }
            ++valOffset;
        }
    }

    char[] table;

    CharOpenAddressHashSetOld(){
        super();
    }
    CharOpenAddressHashSetOld(CharOpenAddressHashSetOld that){
        int size;
        if((size=that.size) != 0){
            this.size=size;
            word0=that.word0;
            word1=that.word1;
            word2=that.word2;
            word3=that.word3;
            if((size=that.tableSize) != 0){
                tableSize=size;
                int tableLength;
                char[] table;
                this.table=table=new char[tableLength=tableSizeFor(size)];
                maxTableSize=(int)(tableLength * .75f);
                char[] thatTable;
                for(tableLength=(thatTable=that.table).length;;){
                    char tableVal;
                    if((tableVal=thatTable[--tableLength]) > 1){
                        quickInsert(table,tableVal);
                        if(--size == 0){
                            return;
                        }
                    }
                }
            }else{
                maxTableSize=1;
            }
        }else{
            maxTableSize=1;
        }
    }
    CharOpenAddressHashSetOld(int initialCapacity){
        super(initialCapacity);
    }
    @Override
    public boolean add(boolean val){
        long word;
        if((word=word0) != (word0=word | (val?2L:1L))){
            ++size;
            return true;
        }
        return false;
    }
    @Override
    public boolean add(char val){
        returnFalse:for(;;){
            long word;
            switch(val >> 6){
            case 0:
                if((word=word0) == (word0=word | 1L << val)){
                    break returnFalse;
                }
                break;
            case 1:
                if((word=word1) == (word1=word | 1L << val)){
                    break returnFalse;
                }
                break;
            case 2:
                if((word=word2) == (word2=word | 1L << val)){
                    break returnFalse;
                }
                break;
            case 3:
                if((word=word3) == (word3=word | 1L << val)){
                    break returnFalse;
                }
                break;
            default:
                if(!addToTable(val)){
                    break returnFalse;
                }
            }
            ++size;
            return true;
        }
    return false;
    }
    @Override
    public boolean add(Character val){
        return add((char)val);
    }
    @Override
    public Object clone(){
        return new CharOpenAddressHashSetOld(this);
    }
    @Override
    public boolean contains(boolean val){
        return (word0 & (val?2L:1L)) != 0;
    }
    @Override
    public boolean contains(byte val){
        switch(val >> 6){
        case 0:
            return (word0 & 1L << val) != 0;
        case 1:
            return (word1 & 1L << val) != 0;
        default:
            return false;
        }
    }
    @Override
    public boolean contains(char val){
        return uncheckedContainsChar(val);
    }
    @Override
    public boolean contains(double val){
        int v;
        return size != 0 && (v=(char)val) == val && uncheckedContainsChar(v);
    }
    @Override
    public boolean contains(float val){
        int v;
        return size != 0 && (v=(char)val) == val && uncheckedContainsChar(v);
    }
    @Override
    public boolean contains(int val){
        return val == (char)val && uncheckedContainsChar(val);
    }
    @Override
    public boolean contains(long val){
        int v;
        return size != 0 && (v=(char)val) == val && uncheckedContainsChar(v);
    }
    @Override
    public boolean contains(Object val){
        for(;;){
            if(size != 0){
                int v;
                if(val instanceof Character){
                    v=(char)val;
                }else if(val instanceof Integer){
                    if((v=(int)val) != (char)v){
                        break;
                    }
                }else if(val instanceof Long){
                    long l;
                    if((l=(long)val) != (v=(char)l)){
                        break;
                    }
                }else if(val instanceof Float){
                    float f;
                    if((f=(float)val) != (v=(char)f)){
                        break;
                    }
                }else if(val instanceof Double){
                    double d;
                    if((d=(double)val) != (v=(char)d)){
                        break;
                    }
                }else if(val instanceof Byte){
                    return contains((byte)val);
                }else if(val instanceof Short){
                    if((v=(short)val) < 0){
                        break;
                    }
                }else if(val instanceof Boolean){
                    return contains((boolean)val);
                }else{
                    break;
                }
                return uncheckedContainsChar(v);
            }
            break;
        }
        return false;
    }
    @Override
    public boolean contains(short val){
        return val >= 0 && uncheckedContainsChar(val);
    }
    @Override
    public boolean equals(Object val){
        // TODO
        return false;
    }
    @Override
    public void forEach(CharConsumer action){
        int size;
        if((size=this.size) != 0){
            forEachHelper(size,action);
        }
    }
    @Override
    public void forEach(Consumer<? super Character> action){
        int size;
        if((size=this.size) != 0){
            forEachHelper(size,action::accept);
        }
    }
    @Override
    public int hashCode(){
        int size;
        if((size=this.size) != 0){
            long magicWord;
            if((int)(magicWord=processWordHashCode(word0,0,64,size)) != 0){
                if((int)(magicWord=processWordHashCode(word1,64,128,magicWord)) != 0){
                    if((int)(magicWord=processWordHashCode(word2,128,192,magicWord)) != 0){
                        if((size=(int)(magicWord=processWordHashCode(word3,192,256,magicWord))) != 0){
                            int hash=(int)(magicWord >>> 32);
                            char[] table;
                            for(int i=(table=this.table).length;--i >= 0;){
                                char tableVal;
                                if((tableVal=table[i]) > 1){
                                    hash+=tableVal;
                                    if(--size == 0){
                                        return hash;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return (int)(magicWord >>> 32);
        }
        return 0;
    }
    @Override
    public OmniIterator.OfChar iterator(){
        return new Itr(this);
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException{
        int size;
        this.size=size=in.readInt();
        if(size != 0){
            word0=in.readLong();
            word1=in.readLong();
            word2=in.readLong();
            word3=in.readLong();
            tableSize=size=in.readInt();
            if(size != 0){
                int tableSize;
                maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
                char[] table;
                this.table=table=new char[tableSize];
                do{
                    quickInsert(table,in.readChar());
                }while(--size != 0);
            }else{
                maxTableSize=1;
            }
        }
    }
    @Override
    public boolean remove(Object val){
        returnFalse:for(;;){
            int size;
            if((size=this.size) != 0){
                returnTrue:for(;;){
                    int v;
                    long word;
                    if(val instanceof Character){
                        v=(char)val;
                    }else if(val instanceof Integer){
                        if((v=(int)val) != (char)v){
                            break returnFalse;
                        }
                    }else if(val instanceof Long){
                        long l;
                        if((l=(long)val) != (v=(char)l)){
                            break returnFalse;
                        }
                    }else if(val instanceof Float){
                        float f;
                        if((f=(float)val) != (v=(char)f)){
                            break returnFalse;
                        }
                    }else if(val instanceof Double){
                        double d;
                        if((d=(double)val) != (v=(char)d)){
                            break returnFalse;
                        }
                    }else if(val instanceof Byte){
                        switch((v=(byte)val) >> 6){
                        case 0:
                            if((word=word0) == (word0=word & ~(1L << v))){
                                break returnFalse;
                            }
                            break;
                        case 1:
                            if((word=word1) == (word1=word & ~(1L << v))){
                                break returnFalse;
                            }
                            break;
                        default:
                        }
                        break returnTrue;
                    }else if(val instanceof Short){
                        if((v=(short)val) < 0){
                            break returnFalse;
                        }
                    }else if(val instanceof Boolean){
                        if((word=word0) == (word0=word & ((boolean)val?~2L:~1L))){
                            break returnFalse;
                        }
                        break returnTrue;
                    }else{
                        break returnFalse;
                    }
                    switch(v >> 6){
                    case 0:
                        if((word=word0) == (word0=word & ~(1L << v))){
                            break returnFalse;
                        }
                        break;
                    case 1:
                        if((word=word1) == (word1=word & ~(1L << v))){
                            break returnFalse;
                        }
                        break;
                    case 2:
                        if((word=word2) == (word2=word & ~(1L << v))){
                            break returnFalse;
                        }
                        break;
                    case 3:
                        if((word=word3) == (word3=word & ~(1L << v))){
                            break returnFalse;
                        }
                        break;
                    default:
                        if(!uncheckedRemoveFromTable(v)){
                            break returnFalse;
                        }
                    }
                    break;
                }
            this.size=size - 1;
            return true;
            }
            break;
        }
    return false;
    }
    @Override
    public boolean removeIf(CharPredicate filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Character> filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
    }
    @Override
    public boolean removeVal(boolean val){
        int size;
        if((size=this.size) != 0){
            long word;
            if((word=word0) != (word0=word & (val?~2L:~1L))){
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(byte val){
        int size;
        if((size=this.size) != 0){
            long word;
            switch(val >> 6){
            case 0:
                if((word=word0) != (word0=word & ~(1L << val))){
                    this.size=size - 1;
                    return true;
                }
                break;
            case 1:
                if((word=word1) != (word1=word & ~(1L << val))){
                    this.size=size - 1;
                    return true;
                }
                break;
            default:
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(char val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                long word;
                switch(val >> 6){
                case 0:
                    if((word=word0) == (word0=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 1:
                    if((word=word1) == (word1=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 2:
                    if((word=word2) == (word2=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 3:
                    if((word=word3) == (word3=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                default:
                    if(!uncheckedRemoveFromTable(val)){
                        break returnFalse;
                    }
                }
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(double val){
        int v,size;
        return (size=this.size) != 0 && (v=(int)val) == val && uncheckedRemoveInt(v,size);
    }
    @Override
    public boolean removeVal(float val){
        int v,size;
        return (size=this.size) != 0 && (v=(int)val) == val && uncheckedRemoveInt(v,size);
    }
    @Override
    public boolean removeVal(int val){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveInt(val,size);
    }
    @Override
    public boolean removeVal(long val){
        int v,size;
        return (size=this.size) != 0 && (v=(int)val) == val && uncheckedRemoveInt(v,size);
    }
    @Override
    public boolean removeVal(short val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                long word;
                switch(val >> 6){
                case 0:
                    if((word=word0) == (word0=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 1:
                    if((word=word1) == (word1=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 2:
                    if((word=word2) == (word2=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                case 3:
                    if((word=word3) == (word3=word & ~(1L << val))){
                        break returnFalse;
                    }
                    break;
                default:
                    if(val < 0 || !uncheckedRemoveFromTable(val)){
                        break returnFalse;
                    }
                }
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public Character[] toArray(){
        int size;
        if((size=this.size) != 0){
            Character[] dst;
            uncheckedCopyIntoArray(size,dst=new Character[size]);
            return dst;
        }
        return OmniArray.OfChar.DEFAULT_BOXED_ARR;
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        final T[] arr=arrConstructor.apply(size=this.size);
        if(size != 0){
            uncheckedCopyIntoArray(size,arr);
        }
        return arr;
    }
    @Override
    public <T> T[] toArray(T[] dst){
        int size;
        if((size=this.size) != 0){
            uncheckedCopyIntoArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
        }else if(dst.length != 0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public char[] toCharArray(){
        int size;
        if((size=this.size) != 0){
            char[] dst;
            uncheckedCopyIntoArray(size,dst=new char[size]);
            return dst;
        }
        return OmniArray.OfChar.DEFAULT_ARR;
    }
    @Override
    public double[] toDoubleArray(){
        int size;
        if((size=this.size) != 0){
            double[] dst;
            uncheckedCopyIntoArray(size,dst=new double[size]);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public float[] toFloatArray(){
        int size;
        if((size=this.size) != 0){
            float[] dst;
            uncheckedCopyIntoArray(size,dst=new float[size]);
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
    }
    @Override
    public int[] toIntArray(){
        int size;
        if((size=this.size) != 0){
            int[] dst;
            uncheckedCopyIntoArray(size,dst=new int[size]);
            return dst;
        }
        return OmniArray.OfInt.DEFAULT_ARR;
    }
    @Override
    public long[] toLongArray(){
        int size;
        if((size=this.size) != 0){
            long[] dst;
            uncheckedCopyIntoArray(size,dst=new long[size]);
            return dst;
        }
        return OmniArray.OfLong.DEFAULT_ARR;
    }
    @Override
    public String toString(){
        int size;
        if((size=this.size) != 0){
            char[] buffer;
            (buffer=new char[size * 3])[0]='[';
            long magicWord;
            if((int)(magicWord=processWordToString(word0,0,64,buffer,size)) != 0){
                if((int)(magicWord=processWordToString(word1,64,128,buffer,magicWord)) != 0){
                    if((int)(magicWord=processWordToString(word2,128,192,buffer,magicWord)) != 0){
                        if((size=(int)(magicWord=processWordToString(word3,192,256,buffer,magicWord))) != 0){
                            int bufferOffset=(int)(magicWord >>> 32);
                            final var table=this.table;
                            for(int i=0;;++i){
                                char tableVal;
                                if((tableVal=table[i]) > 1){
                                    buffer[++bufferOffset]=tableVal;
                                    if(--size == 0){
                                        break;
                                    }
                                    buffer[++bufferOffset]=',';
                                    buffer[++bufferOffset]=' ';
                                }
                            }
                            buffer[++bufferOffset]=']';
                            return new String(buffer,0,bufferOffset + 1);
                        }
                    }
                }
            }
            buffer[size=(int)(magicWord >>> 32) + 1]=']';
            return new String(buffer,0,size + 1);
        }
        return "[]";
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        int size;
        out.writeInt(size=this.size);
        if(size != 0){
            out.writeLong(word0);
            out.writeLong(word1);
            out.writeLong(word2);
            out.writeLong(word3);
            out.writeInt(size=tableSize);
            if(size != 0){
                char[] table;
                for(int i=(table=this.table).length;--i >= 0;){
                    char val;
                    if((val=table[i]) > 1){
                        out.writeChar(val);
                        if(--size == 0){
                            break;
                        }
                    }
                }
            }
        }
    }
    boolean uncheckedRemoveIf(int size,CharPredicate filter){
        long word;
        int numRemoved=Long.bitCount((word=word0) ^ (word0=wordRemoveIf(word,0,filter)))
                + Long.bitCount((word=word1) ^ (word1=wordRemoveIf(word,64,filter)))
                + Long.bitCount((word=word2) ^ (word2=wordRemoveIf(word,128,filter)))
                + Long.bitCount((word=word3) ^ (word3=wordRemoveIf(word,192,filter)));
        int tableSize;
        if((tableSize=this.tableSize) != 0){
            char[] table;
            int newTableSize=0;
            for(int i=(table=this.table).length;;){
                char tableVal;
                if((tableVal=table[--i]) > 1){
                    if(filter.test(tableVal)){
                        table[i]=1;
                        ++numRemoved;
                    }else{
                        ++newTableSize;
                    }
                    if(--tableSize == 0){
                        break;
                    }
                }
            }
            this.tableSize=newTableSize;
        }
        if(numRemoved != 0){
            this.size=size - numRemoved;
            return true;
        }
        return false;
    }
    private boolean addToTable(char val){
        char[] table;
        if((table=this.table) != null){
            int tableLength;
            int hash;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash=val & (tableLength=table.length - 1);;){
                char tableVal;
                switch(tableVal=table[hash]){
                case 0:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case 1:
                    insertHere=hash;
                    break;
                default:
                    if(tableVal == val){
                        // already contains
                        return false;
                    }
                }
                if((hash=hash + 1 & tableLength) == initialHash){
                    break insertInTable;
                }
            }
            insert(table,insertHere,val);
            return true;
        }
        this.table=table=new char[maxTableSize];
        maxTableSize=12;
        tableSize=1;
        table[val & 15]=val;
        return true;
    }
    private void forEachHelper(int size,CharConsumer action){
        if((size=processWordForEach(word0,0,64,action,size)) != 0){
            if((size=processWordForEach(word1,64,128,action,size)) != 0){
                if((size=processWordForEach(word2,128,192,action,size)) != 0){
                    if((size=processWordForEach(word3,192,256,action,size)) != 0){
                        final var table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                action.accept(tableVal);
                                if(--size == 0){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void insert(char[] table,int hash,char val){
        int tableSize;
        if((tableSize=++this.tableSize) > maxTableSize){
            maxTableSize=(int)((hash=table.length << 1) * .75f);
            char[] newTable;
            this.table=newTable=new char[hash];
            for(int i=0;;++i){
                char tableVal;
                if((tableVal=table[i]) > 1){
                    quickInsert(newTable,tableVal);
                    if(--tableSize == 1){
                        quickInsert(newTable,val);
                        return;
                    }
                }
            }
        }else{
            table[hash]=val;
        }
    }
    private boolean tableContains(int val){
        if(tableSize != 0){
            char[] table;
            int tableLength,initialHash,tableVal;
            if((tableVal=(table=this.table)[initialHash=val & (tableLength=table.length - 1)]) != 0){
                int hash=initialHash;
                do{
                    if(tableVal == val){
                        return true;
                    }
                }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
            }
        }
        return false;
    }
    private boolean uncheckedContainsChar(int val){
        switch(val >> 6){
        case 0:
            return (word0 & 1L << val) != 0;
        case 1:
            return (word1 & 1L << val) != 0;
        case 2:
            return (word2 & 1L << val) != 0;
        case 3:
            return (word3 & 1L << val) != 0;
        default:
            return tableContains(val);
        }
    }
    private void uncheckedCopyIntoArray(int size,char[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,Character[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,double[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final var table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,float[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,int[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,long[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void uncheckedCopyIntoArray(int size,Object[] dst){
        int offset;
        if((offset=processWordCopyToArray(word0,0,64,dst,0,size)) != size){
            if((offset=processWordCopyToArray(word1,64,128,dst,offset,size)) != size){
                if((offset=processWordCopyToArray(word2,128,192,dst,offset,size)) != size){
                    if((offset=processWordCopyToArray(word3,192,256,dst,offset,size)) != size){
                        final char[] table=this.table;
                        for(int i=0;;++i){
                            char tableVal;
                            if((tableVal=table[i]) > 1){
                                dst[offset]=tableVal;
                                if(++offset == size){
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private boolean uncheckedRemoveFromTable(int val){
        int tableSize;
        if((tableSize=this.tableSize) != 0){
            char[] table;
            int tableLength,initialHash,tableVal;
            if((tableVal=(table=this.table)[initialHash=val & (tableLength=table.length - 1)]) != 0){
                int hash=initialHash;
                do{
                    if(tableVal == val){
                        table[hash]=1;
                        this.tableSize=tableSize - 1;
                        return true;
                    }
                }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
            }
        }
        return false;
    }
    private boolean uncheckedRemoveInt(int val,int size){
        returnFalse:for(;;){
            long word;
            switch(val >> 6){
            case 0:
                if((word=word0) == (word0=word & ~(1L << val))){
                    break returnFalse;
                }
                break;
            case 1:
                if((word=word1) == (word1=word & ~(1L << val))){
                    break returnFalse;
                }
                break;
            case 2:
                if((word=word2) == (word2=word & ~(1L << val))){
                    break returnFalse;
                }
                break;
            case 3:
                if((word=word3) == (word3=word & ~(1L << val))){
                    break returnFalse;
                }
                break;
            default:
                if(val != (char)val || !uncheckedRemoveFromTable(val)){
                    break returnFalse;
                }
            }
            this.size=size - 1;
            return true;
        }
    return false;
    }
    public static class Checked extends CharOpenAddressHashSetOld{
        int modCount;
        Checked(){
            super();
        }
        Checked(CharOpenAddressHashSetOld that){
            super(that);
        }
        Checked(int initialCapacity){
            super(initialCapacity);
        }
        @Override
        public boolean add(boolean val){
            if(super.add(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean add(char val){
            if(super.add(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public void clear(){
            if(size != 0){
                ++modCount;
                if(tableSize != 0){
                    char[] table;
                    for(int i=(table=this.table).length;--i >= 0;){
                        table[i]=0;
                    }
                    tableSize=0;
                }
                word0=0;
                word1=0;
                word2=0;
                word3=0;
                size=0;
            }
        }
        @Override
        public Object clone(){
            return new Checked(this);
        }
        @Override
        public boolean equals(Object val){
            // TODO
            return false;
        }
        @Override
        public void forEach(CharConsumer action){
            int size;
            if((size=this.size) != 0){
                final int modCount=this.modCount;
                try{
                    super.forEachHelper(size,action);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            }
        }
        @Override
        public void forEach(Consumer<? super Character> action){
            int size;
            if((size=this.size) != 0){
                final int modCount=this.modCount;
                try{
                    super.forEachHelper(size,action::accept);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            }
        }
        @Override
        public OmniIterator.OfChar iterator(){
            return new CheckedItr(this);
        }
        @Override
        public boolean remove(Object val){
            if(super.remove(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(boolean val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(byte val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(char val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(double val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(float val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(int val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(long val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public boolean removeVal(short val){
            if(super.removeVal(val)){
                ++modCount;
                return true;
            }
            return false;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray((arrSize)->{
                final int modCount=this.modCount;
                try{
                    return arrConstructor.apply(arrSize);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            });
        }
        @Override
        public void writeExternal(ObjectOutput out) throws IOException{
            final int modCount=this.modCount;
            try{
                super.writeExternal(out);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        boolean uncheckedRemoveIf(int size,CharPredicate filter){
            long word0;
            long word1;
            long word2;
            long word3;
            int[] tableIndicesRemoved=null;
            int numRemovedFromTable=0;
            int tableSize;
            final int modCount=this.modCount;
            int numRemoved;
            char[] table=null;
            try{
                numRemoved=Long.bitCount((word0=this.word0) ^ (word0=wordRemoveIf(word0,0,filter)))
                        + Long.bitCount((word1=this.word1) ^ (word1=wordRemoveIf(word1,64,filter)))
                        + Long.bitCount((word2=this.word2) ^ (word2=wordRemoveIf(word2,128,filter)))
                        + Long.bitCount((word3=this.word3) ^ (word3=wordRemoveIf(word3,192,filter)));
                if((tableSize=this.tableSize) != 0){
                    tableIndicesRemoved=new int[tableSize];
                    for(int i=(table=this.table).length;;){
                        char tableVal;
                        if((tableVal=table[--i]) > 1){
                            if(filter.test(tableVal)){
                                tableIndicesRemoved[numRemovedFromTable++]=i;
                                ++numRemoved;
                            }
                            if(--tableSize == 0){
                                break;
                            }
                        }
                    }
                }
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            if(numRemoved != 0){
                this.modCount=modCount + 1;
                this.size=size - numRemoved;
                this.word0=word0;
                this.word1=word1;
                this.word2=word2;
                this.word3=word3;
                if(numRemovedFromTable != 0){
                    this.tableSize=tableSize - numRemovedFromTable;
                    do{
                        table[tableIndicesRemoved[--numRemovedFromTable]]=1;
                    }while(numRemovedFromTable != 0);
                }
            }
            return false;
        }
        private static class CheckedItr extends AbstractCharItr{
            private static int forEachRemainingWordHelper(long word,int valOffset,int lastRet,CharConsumer action){
                for(long marker=1L << valOffset;;++valOffset){
                    if((word & marker) != 0){
                        action.accept((char)valOffset);
                        lastRet=valOffset;
                    }
                    if((marker<<=1) == 0){
                        return lastRet;
                    }
                }
            }
            private final Checked root;
            private int valOffset;
            private int modCount;
            private int lastRet;
            CheckedItr(Checked root){
                modCount=root.modCount;
                this.root=root;
                if(root.size != 0){
                    int i;
                    if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                        valOffset=i;
                    }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                        valOffset=i + 64;
                    }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                        valOffset=i + 128;
                    }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                        valOffset=i + 192;
                    }else{
                        final char[] table=root.table;
                        for(i=0;;++i){
                            if(table[i] > 1){
                                valOffset=256 + i;
                                return;
                            }
                        }
                    }
                }else{
                    valOffset=-1;
                }
                lastRet=-1;
            }
            @Override
            public void forEachRemaining(CharConsumer action){
                int valOffset;
                if((valOffset=this.valOffset) != -1){
                    if(valOffset < 256){
                        forEachRemainingFromWords(valOffset,action);
                    }else{
                        forEachRemainingFromTable(valOffset - 256,action);
                    }
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Character> action){
                int valOffset;
                if((valOffset=this.valOffset) != -1){
                    if(valOffset < 256){
                        forEachRemainingFromWords(valOffset,action::accept);
                    }else{
                        forEachRemainingFromTable(valOffset - 256,action::accept);
                    }
                }
            }
            @Override
            public boolean hasNext(){
                return valOffset != -1;
            }
            @Override
            public char nextChar(){
                Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                int valOffset;
                if((valOffset=this.valOffset) != -1){
                    lastRet=valOffset;
                    if(valOffset < 256){
                        return getNextFromWords(root,valOffset);
                    }else{
                        return getNextFromTable(root,valOffset - 256);
                    }
                }
                throw new NoSuchElementException();
            }
            @Override
            public void remove(){
                int lastRet;
                if((lastRet=this.lastRet) != -1){
                    int modCount;
                    Checked root;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                    root.modCount=++modCount;
                    this.modCount=modCount;
                    --root.size;
                    switch(lastRet >> 6){
                    case 0:
                        root.word0&=~(1L << lastRet);
                        break;
                    case 1:
                        root.word1&=~(1L << lastRet);
                        break;
                    case 2:
                        root.word2&=~(1L << lastRet);
                        break;
                    case 3:
                        root.word3&=~(1L << lastRet);
                        break;
                    default:
                        root.table[lastRet - 256]=1;
                        --root.tableSize;
                    }
                    this.lastRet=-1;
                }
            }
            private void forEachRemainingFromTable(int valOffset,CharConsumer action){
                final int modCount=this.modCount;
                final var root=this.root;
                int lastRet=this.lastRet;
                try{
                    char[] table;
                    for(final int tableLength=(table=root.table).length;;){
                        char tableVal;
                        if((tableVal=table[valOffset]) > 1){
                            action.accept(tableVal);
                            lastRet=valOffset + 256;
                        }
                        if(++valOffset == tableLength){
                            break;
                        }
                    }
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.valOffset=-1;
                this.lastRet=lastRet;
            }
            private void forEachRemainingFromWords(int valOffset,CharConsumer action){
                final var root=this.root;
                final int modCount=this.modCount;
                int lastRet=this.lastRet;
                try{
                    switch(valOffset >> 6){
                    case 0:
                        lastRet=forEachRemainingWordHelper(root.word0,valOffset,lastRet,action);
                        valOffset=64;
                    case 1:
                        lastRet=forEachRemainingWordHelper(root.word1,valOffset,lastRet,action);
                        valOffset=128;
                    case 2:
                        lastRet=forEachRemainingWordHelper(root.word2,valOffset,lastRet,action);
                        valOffset=192;
                    default:
                        lastRet=forEachRemainingWordHelper(root.word3,valOffset,lastRet,action);
                        int tableSize;
                        if((tableSize=root.tableSize) != 0){
                            final var table=root.table;
                            for(valOffset=0;;++valOffset){
                                char tableVal;
                                if((tableVal=table[valOffset]) > 1){
                                    action.accept(tableVal);
                                    lastRet=valOffset + 256;
                                    if(--tableSize == 0){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.lastRet=lastRet;
                this.valOffset=-1;
            }
            private char getNextFromTable(Checked root,int valOffset){
                char[] table;
                final var ret=(table=root.table)[valOffset];
                for(final int tableLength=table.length;;){
                    if(table[valOffset] > 1){
                        this.valOffset=valOffset + 256;
                        break;
                    }
                    if(++valOffset == tableLength){
                        this.valOffset=-1;
                        break;
                    }
                }
                return ret;
            }
            private char getNextFromWords(Checked root,int valOffset){
                final var ret=(char)valOffset;
                returnVal:for(;;){
                    switch(++valOffset >> 6){
                    case 0:
                        if((valOffset=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                            this.valOffset=valOffset;
                            break returnVal;
                        }
                        valOffset=0;
                    case 1:
                        if((valOffset=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                            this.valOffset=valOffset + 64;
                            break returnVal;
                        }
                        valOffset=0;
                    case 2:
                        if((valOffset=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                            this.valOffset=valOffset + 128;
                            break returnVal;
                        }
                        valOffset=0;
                    case 3:
                        if((valOffset=Long.numberOfTrailingZeros(root.word3 >>> valOffset)) != 64){
                            this.valOffset=valOffset + 192;
                            break returnVal;
                        }
                        valOffset=0;
                        break;
                    default:
                        valOffset-=256;
                    }
                    if(root.tableSize != 0){
                        final var table=root.table;
                        for(;;++valOffset){
                            if(table[valOffset] > 1){
                                this.valOffset=valOffset + 256;
                                break returnVal;
                            }
                        }
                    }else{
                        this.valOffset=-1;
                    }
                    break returnVal;
                }
                return ret;
            }
        }
    }
    private static class Itr extends AbstractCharItr{
        private static void forEachRemainingWordHelper(long word,int valOffset,CharConsumer action){
            for(long marker=1L << valOffset;;++valOffset){
                if((word & marker) != 0){
                    action.accept((char)valOffset);
                }
                if((marker<<=1) == 0){
                    break;
                }
            }
        }
        private final CharOpenAddressHashSetOld root;
        private int valOffset;
        Itr(CharOpenAddressHashSetOld root){
            this.root=root;
            if(root.size != 0){
                int i;
                if((i=Long.numberOfTrailingZeros(root.word0)) != 64){
                    valOffset=i;
                }else if((i=Long.numberOfTrailingZeros(root.word1)) != 64){
                    valOffset=i + 64;
                }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                    valOffset=i + 128;
                }else if((i=Long.numberOfTrailingZeros(root.word2)) != 64){
                    valOffset=i + 192;
                }else{
                    final char[] table=root.table;
                    for(i=0;;++i){
                        if(table[i] > 1){
                            valOffset=256 + i;
                            return;
                        }
                    }
                }
            }else{
                valOffset=-1;
            }
        }
        @Override
        public void forEachRemaining(CharConsumer action){
            int valOffset;
            if((valOffset=this.valOffset) < 256){
                forEachRemainingFromWords(valOffset,action);
            }else{
                forEachRemainingFromTable(valOffset - 256,action);
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Character> action){
            int valOffset;
            if((valOffset=this.valOffset) < 256){
                forEachRemainingFromWords(valOffset,action::accept);
            }else{
                forEachRemainingFromTable(valOffset - 256,action::accept);
            }
        }
        @Override
        public boolean hasNext(){
            return valOffset != -1;
        }
        @Override
        public char nextChar(){
            int valOffset;
            if((valOffset=this.valOffset) < 256){
                return getNextFromWords(valOffset);
            }else{
                return getNextFromTable(valOffset - 256);
            }
        }
        @Override
        public void remove(){
            CharOpenAddressHashSetOld root;
            long word;
            int valOffset;
            --(root=this.root).size;
            switch((valOffset=this.valOffset) - 1 >> 6){
            default:
                char[] table;
                if((table=root.table) != null){
                    if(valOffset == -1){
                        valOffset=table.length;
                    }else{
                        valOffset-=256;
                    }
                    for(;;){
                        if(table[--valOffset] > 1){
                            table[valOffset]=1;
                            --root.tableSize;
                            return;
                        }
                        if(valOffset == 0){
                            break;
                        }
                    }
                }else{
                    valOffset=0;
                }
            case 3:
                if((valOffset=Long.numberOfLeadingZeros((word=root.word3) << -valOffset)) != 64){
                    root.word3=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            case 2:
                if((valOffset=Long.numberOfLeadingZeros((word=root.word2) << -valOffset)) != 64){
                    root.word2=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            case 1:
                if((valOffset=Long.numberOfLeadingZeros((word=root.word1) << -valOffset)) != 64){
                    root.word1=word & ~(1L << -1 - valOffset);
                    return;
                }
                valOffset=0;
            case 0:
                root.word0=(word=root.word0) & ~(1L << -1 - Long.numberOfLeadingZeros(word << -valOffset));
            }
        }
        private void forEachRemainingFromTable(int valOffset,CharConsumer action){
            char[] table;
            for(final int tableLength=(table=root.table).length;;){
                char tableVal;
                if((tableVal=table[valOffset]) > 1){
                    action.accept(tableVal);
                }
                if(++valOffset == tableLength){
                    this.valOffset=-1;
                    return;
                }
            }
        }
        private void forEachRemainingFromWords(int valOffset,CharConsumer action){
            final var root=this.root;
            switch(valOffset >> 6){
            case 0:
                forEachRemainingWordHelper(root.word0,valOffset,action);
                valOffset=64;
            case 1:
                forEachRemainingWordHelper(root.word1,valOffset,action);
                valOffset=128;
            case 2:
                forEachRemainingWordHelper(root.word2,valOffset,action);
                valOffset=192;
            case 3:
                forEachRemainingWordHelper(root.word3,valOffset,action);
                int tableSize;
                if((tableSize=root.tableSize) != 0){
                    final var table=root.table;
                    for(valOffset=0;;++valOffset){
                        char tableVal;
                        if((tableVal=table[valOffset]) > 1){
                            action.accept(tableVal);
                            if(--tableSize == 0){
                                break;
                            }
                        }
                    }
                }
                this.valOffset=-1;
            default:
                return;
            }
        }
        private char getNextFromTable(int valOffset){
            char[] table;
            final var ret=(table=root.table)[valOffset];
            for(final int tableLength=table.length;;){
                if(table[valOffset] > 1){
                    this.valOffset=valOffset + 256;
                    break;
                }
                if(++valOffset == tableLength){
                    this.valOffset=-1;
                    break;
                }
            }
            return ret;
        }
        private char getNextFromWords(int valOffset){
            final var ret=(char)valOffset;
            returnVal:for(;;){
                final var root=this.root;
                switch(++valOffset >> 6){
                case 0:
                    if((valOffset=Long.numberOfTrailingZeros(root.word0 >>> valOffset)) != 64){
                        this.valOffset=valOffset;
                        break returnVal;
                    }
                    valOffset=0;
                case 1:
                    if((valOffset=Long.numberOfTrailingZeros(root.word1 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 64;
                        break returnVal;
                    }
                    valOffset=0;
                case 2:
                    if((valOffset=Long.numberOfTrailingZeros(root.word2 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 128;
                        break returnVal;
                    }
                    valOffset=0;
                case 3:
                    if((valOffset=Long.numberOfTrailingZeros(root.word3 >>> valOffset)) != 64){
                        this.valOffset=valOffset + 192;
                        break returnVal;
                    }
                    valOffset=0;
                    break;
                default:
                    valOffset-=256;
                }
                if(root.tableSize != 0){
                    final var table=root.table;
                    for(;;++valOffset){
                        if(table[valOffset] > 1){
                            this.valOffset=valOffset + 256;
                            break returnVal;
                        }
                    }
                }else{
                    this.valOffset=-1;
                }
                break returnVal;
            }
            return ret;
        }
    }
    @Override
    void clearTable(){
        char[] table;
        for(int i=(table=this.table).length;--i >= 0;){
            table[i]=0;
        }
    }
}
