package omni.impl.set;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.AbstractDoubleItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class DoubleOpenAddressHashSet extends AbstractOpenAddressHashSet implements OmniSet.OfDouble{
    transient long[] table;
    private static final long DELETED=0x7ffc000000000000L;
    private static final long EMPTY=0x0000000000000000L;
    private static final long POS0=0xfffc000000000000L;
    public DoubleOpenAddressHashSet(){
        super();
    }
    public DoubleOpenAddressHashSet(DoubleOpenAddressHashSet that){
        super(that);
        int size;
        if((size=that.size) != 0){
            long[] table;
            this.table=table=new long[tableSizeFor(size)];
            long[] thatTable;
            for(int i=(thatTable=that.table).length;;){
                long tableVal;
                if((tableVal=thatTable[--i]) == EMPTY || tableVal == DELETED){
                    continue;
                }
                quickInsert(table,tableVal);
                if(--size == 0){
                    return;
                }
            }
        }
    }
    public DoubleOpenAddressHashSet(int initialCapacity){
        super(initialCapacity);
    }
    public DoubleOpenAddressHashSet(float loadFactor){
        super(loadFactor);
    }
    public DoubleOpenAddressHashSet(int initialCapacity,float loadFactor){
        super(initialCapacity,loadFactor);
    }
    private static void quickInsert(long[] table,long val){
        int tableLength;
        int hash;
        for(hash=((hash=(int)(val ^ val >>> 32)) ^ hash >>> 16) & (tableLength=table.length - 1);;){
            if(table[hash] == EMPTY){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    @Override
    public void clear(){
        if(size != 0){
            long[] table;
            for(int i=(table=this.table).length;--i >= 0;){
                table[i]=EMPTY;
            }
            size=0;
        }
    }
    private boolean tableContainsPos0(){
        long[] table;
        long tableVal;
        if((tableVal=(table=this.table)[0]) != EMPTY){
            int tableLength=table.length;
            int hash=0;
            do{
                if(tableVal == POS0){
                    return true;
                }
            }while(++hash != tableLength && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removePos0FromTable(){
        long[] table;
        long tableVal;
        if((tableVal=(table=this.table)[0]) != EMPTY){
            int hash=0;
            int tableLength=table.length;
            do{
                if(tableVal == POS0){
                    table[hash]=DELETED;
                    return true;
                }
            }while(++hash != tableLength && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContainsNaN(){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32)
                ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == 0x7ff8000000000000L){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removeNaNFromTable(){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32)
                ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == 0x7ff8000000000000L){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContainsTrue(){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32)
                ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == TypeUtil.DBL_TRUE_BITS){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removeTrueFromTable(){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32)
                ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == TypeUtil.DBL_TRUE_BITS){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContains(long longBits){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((initialHash=(int)(longBits ^ longBits >>> 32))
                ^ initialHash >>> 16) & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == longBits){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removeFromTable(long longBits){
        long[] table;
        int tableLength,initialHash;
        long tableVal;
        if((tableVal=(table=this.table)[initialHash=((initialHash=(int)(longBits ^ longBits >>> 32))
                ^ initialHash >>> 16) & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == longBits){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    @Override
    public boolean contains(Object val){
        if(size != 0){
            returnFalse:for(;;){
                checkNaN:for(;;){
                    checkPos0:for(;;){
                        long longBits;
                        if(val instanceof Double){
                            double d;
                            if((d=(double)val) == d){
                                if((longBits=Double.doubleToRawLongBits(d)) == 0){
                                    break checkPos0;
                                }
                            }else{
                                break checkNaN;
                            }
                        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                            int v;
                            if((v=((Number)val).intValue()) == 0){
                                break checkPos0;
                            }
                            longBits=Double.doubleToRawLongBits(v);
                        }else if(val instanceof Long){
                            if((longBits=(long)val) == 0){
                                break checkPos0;
                            }else if(!TypeUtil.checkCastToDouble(longBits)){
                                break returnFalse;
                            }
                            longBits=Double.doubleToRawLongBits(longBits);
                        }else if(val instanceof Float){
                            float f;
                            if((f=(float)val) == f){
                                if((longBits=Double.doubleToRawLongBits(f)) == 0){
                                    break checkPos0;
                                }
                            }else{
                                break checkNaN;
                            }
                        }else if(val instanceof Character){
                            int v;
                            if((v=(char)val) == 0){
                                break checkPos0;
                            }
                            longBits=Double.doubleToRawLongBits(v);
                        }else if(val instanceof Boolean){
                            if((boolean)val){
                                return tableContainsTrue();
                            }else{
                                break checkPos0;
                            }
                        }else{
                            break returnFalse;
                        }
                        return tableContains(longBits);
                    }
        return tableContainsPos0();
                }
        return tableContainsNaN();
            }
        }
        return false;
    }
    @Override
    public boolean contains(boolean val){
        if(size != 0){
            if(val){
                return tableContainsTrue();
            }
            return tableContainsPos0();
        }
        return false;
    }
    @Override
    public boolean contains(int val){
        if(size != 0){
            if(val == 0){
                return tableContainsPos0();
            }
            return tableContains(Double.doubleToRawLongBits(val));
        }
        return false;
    }
    @Override
    public boolean contains(long val){
        if(size != 0){
            if(val == 0){
                return tableContainsPos0();
            }else if(TypeUtil.checkCastToDouble(val)){
                return tableContains(Double.doubleToRawLongBits(val));
            }
        }
        return false;
    }
    @Override
    public boolean contains(float val){
        if(size != 0){
            if(val == val){
                long bits;
                if((bits=Double.doubleToRawLongBits(val)) != 0){
                    return tableContains(bits);
                }
                return tableContainsPos0();
            }
            return tableContainsNaN();
        }
        return false;
    }
    @Override
    public boolean contains(double val){
        if(size != 0){
            if(val == val){
                long bits;
                if((bits=Double.doubleToRawLongBits(val)) != 0){
                    return tableContains(bits);
                }
                return tableContainsPos0();
            }
            return tableContainsNaN();
        }
        return false;
    }
    @Override
    public boolean remove(Object val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    checkNaN:for(;;){
                        checkPos0:for(;;){
                            long longBits;
                            if(val instanceof Double){
                                double d;
                                if((d=(double)val) == d){
                                    if((longBits=Double.doubleToRawLongBits(d)) == 0){
                                        break checkPos0;
                                    }
                                }else{
                                    break checkNaN;
                                }
                            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                                int v;
                                if((v=((Number)val).intValue()) == 0){
                                    break checkPos0;
                                }
                                longBits=Double.doubleToRawLongBits(v);
                            }else if(val instanceof Long){
                                if((longBits=(long)val) == 0){
                                    break checkPos0;
                                }else if(!TypeUtil.checkCastToDouble(longBits)){
                                    break returnFalse;
                                }
                                longBits=Double.doubleToRawLongBits(longBits);
                            }else if(val instanceof Float){
                                float f;
                                if((f=(float)val) == f){
                                    if((longBits=Double.doubleToRawLongBits(f)) == 0){
                                        break checkPos0;
                                    }
                                }else{
                                    break checkNaN;
                                }
                            }else if(val instanceof Character){
                                int v;
                                if((v=(char)val) == 0){
                                    break checkPos0;
                                }
                                longBits=Double.doubleToRawLongBits(v);
                            }else if(val instanceof Boolean){
                                if((boolean)val){
                                    if(removeTrueFromTable()){
                                        break returnTrue;
                                    }
                                    break returnFalse;
                                }else{
                                    break checkPos0;
                                }
                            }else{
                                break returnFalse;
                            }
                            if(removeFromTable(longBits)){
                                break returnTrue;
                            }
                            break returnFalse;
                        }
        if(removePos0FromTable()){
            break returnTrue;
        }
        break returnFalse;
                    }
        if(removeNaNFromTable()){
            break returnTrue;
        }
        break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val){
                        if(removeTrueFromTable()){
                            break returnTrue;
                        }
                    }else if(removePos0FromTable()){
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(int val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val == 0){
                        if(removePos0FromTable()){
                            break returnTrue;
                        }
                    }else if(removeFromTable(Double.doubleToRawLongBits(val))){
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(long val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val == 0){
                        if(removePos0FromTable()){
                            break returnTrue;
                        }
                    }else if(TypeUtil.checkCastToDouble(val)){
                        if(removeFromTable(Double.doubleToRawLongBits(val))){
                            break returnTrue;
                        }
                    }
                    break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(float val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val == val){
                        long bits;
                        if((bits=Double.doubleToRawLongBits(val)) != 0){
                            if(removeFromTable(bits)){
                                break returnTrue;
                            }
                        }else if(removePos0FromTable()){
                            break returnTrue;
                        }
                    }else if(removeNaNFromTable()){
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(double val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val == val){
                        long bits;
                        if((bits=Double.doubleToRawLongBits(val)) != 0){
                            if(removeFromTable(bits)){
                                break returnTrue;
                            }
                        }else if(removePos0FromTable()){
                            break returnTrue;
                        }
                    }else if(removeNaNFromTable()){
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size - 1;
        return true;
            }
        }
        return false;
    }
    private void uncheckedCopyToArray(int size,Double[] dst){
        long[] table=this.table;
        for(int i=0;;++i){
            long tableVal;
            if((tableVal=table[i]) == EMPTY || tableVal == DELETED){
                continue;
            }else if(tableVal == POS0){
                dst[--size]=0.0d;
            }else{
                dst[--size]=Double.longBitsToDouble(tableVal);
            }
            if(size == 0){
                return;
            }
        }
    }
    private void uncheckedCopyToArray(int size,double[] dst){
        long[] table=this.table;
        for(int i=0;;++i){
            long tableVal;
            if((tableVal=table[i]) == EMPTY || tableVal == DELETED){
                continue;
            }else if(tableVal == POS0){
                dst[--size]=0.0d;
            }else{
                dst[--size]=Double.longBitsToDouble(tableVal);
            }
            if(size == 0){
                return;
            }
        }
    }
    private void uncheckedCopyToArray(int size,Object[] dst){
        long[] table=this.table;
        for(int i=0;;++i){
            long tableVal;
            if((tableVal=table[i]) == EMPTY || tableVal == DELETED){
                continue;
            }else if(tableVal == POS0){
                dst[--size]=0.0d;
            }else{
                dst[--size]=Double.longBitsToDouble(tableVal);
            }
            if(size == 0){
                return;
            }
        }
    }
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        T[] dst=arrConstructor.apply(size=this.size);
        if(size != 0){
            uncheckedCopyToArray(size,dst);
        }
        return dst;
    }
    @Override
    public <T> T[] toArray(T[] dst){
        int size;
        if((size=this.size) != 0){
            uncheckedCopyToArray(size,OmniArray.uncheckedArrResize(size,dst));
        }else if(dst.length != 0){
            dst[0]=null;
        }
        return dst;
    }
    private void insert(long[] table,int hash,long longBits){
        int size;
        if((size=++this.size) >= maxTableSize){
            maxTableSize=(int)((hash=table.length << 1) * loadFactor);
            long[] newTable;
            this.table=newTable=new long[hash];
            for(int i=0;;++i){
                long tableVal;
                if((tableVal=table[i]) == EMPTY || tableVal == DELETED){
                    continue;
                }else{
                    quickInsert(newTable,tableVal);
                    if(--size == 1){
                        quickInsert(newTable,longBits);
                        return;
                    }
                }
            }
        }else{
            table[hash]=longBits;
        }
    }
    boolean addPos0ToTable(){
        long[] table;
        if((table=this.table) != null){
            int tableLength=table.length;
            int insertHere=-1;
            int hash=0;
            for(;;){
                long tableVal;
                if((tableVal=table[hash]) == EMPTY){
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break;
                }else if(tableVal == DELETED){
                    insertHere=hash;
                }else if(tableVal == POS0){
                    // already contained
                    return false;
                }
                if(++hash == tableLength){
                    break;
                }
            }
            insert(table,insertHere,POS0);
            return true;
        }
        int maxTableSize;
        this.table=table=new long[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[0]=POS0;
        return true;
    }
    boolean addToTable(long longBits,int hash){
        long[] table;
        if((table=this.table) != null){
            int tableLength;
            int insertHere=-1;
            for(final int initialHash=hash&=tableLength=table.length - 1;;){
                long tableVal;
                if((tableVal=table[hash]) == EMPTY){
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break;
                }else if(tableVal == DELETED){
                    insertHere=hash;
                }else if(tableVal == longBits){
                    // already contained
                    return false;
                }
                if((hash=hash + 1 & tableLength) == initialHash){
                    break;
                }
            }
            insert(table,insertHere,longBits);
            return true;
        }
        int maxTableSize;
        this.table=table=new long[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[hash & maxTableSize - 1]=longBits;
        return true;
    }

    @Override
    public boolean add(boolean val){
        if(val){
            return addToTable(TypeUtil.DBL_TRUE_BITS,(int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32)
                    ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16);
        }
        return addPos0ToTable();
    }
    @Override
    public boolean add(Double val){
        return add((double)val);
    }
    void uncheckedForEach(int size,DoubleConsumer action){
        long[] table;
        for(int i=(table=this.table).length;;){
            long tableVal;
            if((tableVal=table[--i]) == EMPTY || tableVal == DELETED){
                continue;
            }else if(tableVal == POS0){
                action.accept(0.0d);
            }else{
                action.accept(Double.longBitsToDouble(tableVal));
            }
            if(--size == 0){
                return;
            }
        }
    }

    @Override
    public void forEach(DoubleConsumer action){
        int size;
        if((size=this.size) != 0){
            uncheckedForEach(size,action);
        }
    }
    @Override
    public void forEach(Consumer<? super Double> action){
        int size;
        if((size=this.size) != 0){
            uncheckedForEach(size,action::accept);
        }
    }
    boolean uncheckedRemoveIf(int size,DoublePredicate filter){
        int newSize=0;
        long[] table;
        for(int numLeft=size,i=(table=this.table).length;;){
            double d;
            long tableVal;
            if((tableVal=table[--i]) == EMPTY || tableVal == DELETED){
                continue;
            }else if(tableVal == POS0){
                d=0.0d;
            }else{
                d=Double.longBitsToDouble(tableVal);
            }
            if(filter.test(d)){
                table[i]=DELETED;
            }else{
                ++newSize;
            }
            if(--numLeft == 0){
                break;
            }
        }
        if(newSize != size){
            this.size=newSize;
            return true;
        }
        return false;
    }
    @Override
    public boolean removeIf(DoublePredicate filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
    }
    @Override
    public boolean removeIf(Predicate<? super Double> filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
    }
    @Override
    public OmniIterator.OfDouble iterator(){
        return new Itr(this);
    }
    private static class Itr extends AbstractDoubleItr{
        private final DoubleOpenAddressHashSet root;
        private int tableIndex;
        Itr(DoubleOpenAddressHashSet root){
            this.root=root;
            if(root.size != 0){
                final long[] table;
                for(int i=(table=root.table).length;;){
                    long tableVal;
                    if((tableVal=table[--i]) != EMPTY && tableVal != DELETED){
                        this.tableIndex=i;
                        return;
                    }
                }
            }else{
                this.tableIndex=-1;
            }
        }
        @Override
        public void remove(){
            DoubleOpenAddressHashSet root;
            --(root=this.root).size;
            var table=root.table;
            for(int tableIndex=this.tableIndex;;){
                long tableVal;
                if((tableVal=table[++tableIndex]) != EMPTY && tableVal != DELETED){
                    table[tableIndex]=DELETED;
                    return;
                }
            }
        }
        @Override
        public boolean hasNext(){
            return this.tableIndex != -1;
        }
        @Override
        public double nextDouble(){
            long table[];
            int tableIndex;
            long retBits=(table=root.table)[tableIndex=this.tableIndex];
            for(;;){
                if(--tableIndex == -1){
                    break;
                }
                long tableVal;
                if((tableVal=table[tableIndex]) != EMPTY && tableVal != DELETED){
                    break;
                }
            }
            this.tableIndex=tableIndex;
            if(retBits != POS0){
                return Double.longBitsToDouble(retBits);
            }
            return 0.0d;
        }
        private void uncheckedForEachRemaining(int tableIndex,DoubleConsumer action){
            long[] table=root.table;
            do{
                long tableVal;
                if((tableVal=table[tableIndex]) != EMPTY && tableVal != DELETED){
                    if(tableVal == POS0){
                        action.accept(0.0d);
                    }else{
                        action.accept(Double.longBitsToDouble(tableVal));
                    }
                }
            }while(--tableIndex != -1);
            this.tableIndex=-1;
        }
        @Override
        public void forEachRemaining(DoubleConsumer action){
            int tableIndex;
            if((tableIndex=this.tableIndex) != -1){
                uncheckedForEachRemaining(tableIndex,action);
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Double> action){
            int tableIndex;
            if((tableIndex=this.tableIndex) != -1){
                uncheckedForEachRemaining(tableIndex,action::accept);
            }
        }
    }
    @Override
    public Double[] toArray(){
        int size;
        if((size=this.size) != 0){
            Double[] dst;
            uncheckedCopyToArray(size,dst=new Double[size]);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
    }
    @Override
    public double[] toDoubleArray(){
        int size;
        if((size=this.size) != 0){
            double[] dst;
            uncheckedCopyToArray(size,dst=new double[size]);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }
    @Override
    public boolean add(float val){
        if(val == val){
            long longBits;
            if((longBits=Double.doubleToRawLongBits(val)) == 0){
                addPos0ToTable();
            }
            int hash;
            return addToTable(longBits,(hash=(int)(longBits ^ longBits >>> 32)) ^ hash >>> 16);
        }
        return addToTable(0x7ff8000000000000L,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32)
                ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16);
    }
    @Override
    public boolean add(long val){
        if(val == 0){
            addPos0ToTable();
        }
        int hash;
        return addToTable(val=Double.doubleToRawLongBits(val),(hash=(int)(val ^ val >>> 32)) ^ hash >>> 16);
    }
    @Override
    public boolean add(int val){
        if(val == 0){
            addPos0ToTable();
        }
        long bits;
        return addToTable(bits=Double.doubleToRawLongBits(val),(val=(int)(bits ^ bits >>> 32)) ^ val >>> 16);
    }
    @Override
    public boolean add(double val){
        if(val == val){
            long longBits;
            if((longBits=Double.doubleToRawLongBits(val)) == 0){
                addPos0ToTable();
            }
            int hash;
            return addToTable(longBits,(hash=(int)(longBits ^ longBits >>> 32)) ^ hash >>> 16);
        }
        return addToTable(0x7ff8000000000000L,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32)
                ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16);
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        int size;
        out.writeInt(size=this.size);
        if(size != 0){
            long[] table;
            for(int i=(table=this.table).length;--i >= 0;){
                long tableVal;
                if((tableVal=table[i]) != EMPTY && tableVal != DELETED){
                    out.writeLong(tableVal);
                    if(--size == 0){
                        return;
                    }
                }
            }
        }
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException{
        int size;
        this.size=size=in.readInt();
        this.loadFactor=0.75f;
        if(size != 0){
            int tableSize;
            maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
            long[] table;
            this.table=table=new long[tableSize];
            do{
                quickInsert(table,in.readLong());
            }while(--size != 0);
        }else{
            maxTableSize=1;
        }
    }
    @Override
    void updateMaxTableSize(float loadFactor){
        long[] table;
        if((table=this.table) != null){
            this.maxTableSize=(int)(table.length * loadFactor);
        }
    }
    @Override
    public Object clone(){
        return new DoubleOpenAddressHashSet(this);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public String toString(){
        int size;
        if((size=this.size) != 0){
            var builder=new StringBuilder("[");
            long[] table;
            for(int i=(table=this.table).length;;){
                long tableVal;
                if((tableVal=table[--i]) == EMPTY || tableVal == DELETED){
                    continue;
                }else if(tableVal == POS0){
                    builder.append(0.0d);
                }else{
                    builder.append(Double.longBitsToDouble(tableVal));
                }
                if(--size == 0){
                    return builder.append(']').toString();
                }
                builder.append(',').append(' ');
            }
        }
        return "[]";
    }
    @Override
    public int hashCode(){
        int size;
        if((size=this.size) != 0){
            int hash=0;
            long[] table;
            for(int i=(table=this.table).length;;){
                long tableVal;
                if((tableVal=table[--i]) == EMPTY || tableVal == DELETED){
                    continue;
                }else if(tableVal != POS0){
                    hash+=(int)(tableVal ^ tableVal >>> 32);
                }
                if(--size == 0){
                    return hash;
                }
            }
        }
        return 0;
    }
    public static class Checked extends DoubleOpenAddressHashSet{
        transient int modCount;
        public Checked(){
            super();
        }
        public Checked(float loadFactor){
            super(loadFactor);
        }
        public Checked(DoubleOpenAddressHashSet that){
            super(that);
        }
        public Checked(int initialCapacity,float loadFactor){
            super(initialCapacity,loadFactor);
        }
        public Checked(int initialCapacity){
            super(initialCapacity);
        }
        @Override
        public void clear(){
            if(size != 0){
                ++this.modCount;
                long[] table;
                for(int i=(table=this.table).length;--i >= 0;){
                    table[i]=0;
                }
                size=0;
            }
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            return super.toArray(arrSize->{
                int modCount=this.modCount;
                try{
                    return arrConstructor.apply(arrSize);
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            });
        }
        @Override
        public void writeExternal(ObjectOutput out) throws IOException{
            int modCount=this.modCount;
            try{
                super.writeExternal(out);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        public Object clone(){
            return new Checked(this);
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return super.equals(val);
        }
        @Override
        void uncheckedForEach(int size,DoubleConsumer action){
            int modCount=this.modCount;
            try{
                super.uncheckedForEach(size,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        public OmniIterator.OfDouble iterator(){
            return new CheckedItr(this);
        }
        @Override
        boolean removePos0FromTable(){
            if(super.removePos0FromTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean removeNaNFromTable(){
            if(super.removeNaNFromTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean removeTrueFromTable(){
            if(super.removeTrueFromTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean removeFromTable(long longBits){
            if(super.removeFromTable(longBits)){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean addToTable(long longBits,int hash){
            if(super.addToTable(longBits,hash)){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean addPos0ToTable(){
            if(super.addPos0ToTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean uncheckedRemoveIf(int size,DoublePredicate filter){
            long[] table;
            int[] indicesToRemove=new int[size];
            int numRemoved=0;
            int modCount=this.modCount;
            try{
                for(int numLeft=size,i=(table=this.table).length;;){
                    double d;
                    long tableVal;
                    if((tableVal=table[--i])==EMPTY || tableVal==DELETED) {
                        continue;
                    }else if(tableVal==POS0) {
                        d=0.0d;
                    }else {
                        d=Double.longBitsToDouble(tableVal);
                    }
                    if(filter.test(d)){
                        indicesToRemove[numRemoved++]=i;
                    }
                    if(--numLeft == 0){
                        break;
                    }
                }
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
            if(numRemoved != 0){
                this.size=size - numRemoved;
                this.modCount=modCount + 1;
                for(;;){
                    table[indicesToRemove[--numRemoved]]=DELETED;
                    if(numRemoved == 0){
                        return true;
                    }
                }
            }
            return false;
        }
        private static class CheckedItr extends AbstractDoubleItr{
            private final Checked root;
            private int tableIndex;
            private int lastRet;
            private int modCount;
            CheckedItr(Checked root){
                this.root=root;
                this.lastRet=-1;
                this.modCount=root.modCount;
                if(root.size != 0){
                    final long[] table;
                    for(int i=(table=root.table).length;;){
                        long tableVal;
                        if((tableVal=table[--i])!=EMPTY && tableVal!=DELETED) {
                            this.tableIndex=i;
                            return;
                        }
                    }
                }else{
                    this.tableIndex=-1;
                }
            }
            @Override
            public void remove(){
                int lastRet;
                if((lastRet=this.lastRet) != -1){
                    Checked root;
                    int modCount;
                    CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
                    --(root=this.root).size;
                    root.modCount=++modCount;
                    this.modCount=modCount;
                    root.table[lastRet]=DELETED;
                    this.lastRet=-1;
                    return;
                }
                throw new IllegalStateException();
            }
            private void uncheckedForEachRemaining(int tableIndex,DoubleConsumer action){
                var root=this.root;
                int modCount=this.modCount;
                int lastRet=tableIndex;
                try{
                    long[] table=root.table;
                    do{
                        long tableVal;
                        if((tableVal=table[tableIndex])!=EMPTY && tableVal!=DELETED) {
                            if(tableVal==POS0) {
                                action.accept(0.0d);
                            }else {
                                action.accept(Double.longBitsToDouble(tableVal));
                            }
                            lastRet=tableIndex;
                        }
                    }while(--tableIndex != -1);
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.tableIndex=-1;
                this.lastRet=lastRet;
            }
            @Override
            public void forEachRemaining(Consumer<? super Double> action){
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    uncheckedForEachRemaining(tableIndex,action::accept);
                }
            }
            @Override
            public void forEachRemaining(DoubleConsumer action){
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    uncheckedForEachRemaining(tableIndex,action);
                }
            }
            @Override
            public double nextDouble(){
                Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    this.lastRet=tableIndex;
                    long table[];
                    long retBits=(table=root.table)[tableIndex];
                    for(;;){
                        if(--tableIndex == -1){
                            break;
                        }
                        long tableVal;
                        if((tableVal=table[tableIndex])!=EMPTY && tableVal!=DELETED) {
                            break;
                        }
                    }
                    this.tableIndex=tableIndex;
                    if(retBits != POS0){
                        return Double.longBitsToDouble(retBits);
                    }
                    return 0.0d;
                }
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext(){
                return this.tableIndex != -1;
            }
        }
    }

}
