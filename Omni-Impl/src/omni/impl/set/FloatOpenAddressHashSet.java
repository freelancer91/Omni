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
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.impl.AbstractFloatItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;

public class FloatOpenAddressHashSet extends AbstractOpenAddressHashSet implements OmniSet.OfFloat{
    transient int[] table;
    public FloatOpenAddressHashSet(){
        super();
    }
    private static final int DELETED= 0x7fe00000;
    private static final int EMPTY=   0x00000000;
    private static final int POS0=    0xffe00000;
    public FloatOpenAddressHashSet(FloatOpenAddressHashSet that){
        super(that);
        int size;
        if((size=that.size)!=0){
            int[] table;
            this.table=table=new int[tableSizeFor(size)];
            int[] thatTable;
            for(int i=(thatTable=that.table).length;;){
                int tableVal;
                switch(tableVal=thatTable[--i]) {
                case DELETED:
                case EMPTY:
                    continue;
                default:
                    quickInsert(table,tableVal);
                }
                if(--size==0) {
                    return;
                }
            }
        }
    }
    public FloatOpenAddressHashSet(int initialCapacity) {
        super(initialCapacity);
    }
    public FloatOpenAddressHashSet(float loadFactor){
        super(loadFactor);
    }
    public FloatOpenAddressHashSet(int initialCapacity,float loadFactor){
        super(initialCapacity,loadFactor);
    }
    private static void quickInsert(int[] table,int val){
        int tableLength;
        for(int hash=(val^val>>>16) & (tableLength=table.length-1);;){
            if(table[hash]==EMPTY){
                table[hash]=val;
                return;
            }
            hash=hash+1&tableLength;
        }
    }
    @Override public void clear() {
        if(size!=0) {
            int[] table;
            for(int i=(table=this.table).length;--i>=0;) {
                table[i]=EMPTY;
            }
            size=0;
        }
    }
    private boolean tableContainsPos0() {
        int[] table;
        int tableVal;
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
        int[] table;
        int tableVal;
        if((tableVal=(table=this.table)[0]) != EMPTY){
            int tableLength=table.length;
            int hash=0;
            do{
                if(tableVal == POS0){
                    table[hash]=DELETED;
                    return true;
                }
            }while(++hash != tableLength && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContainsNaN() {
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(0x7fc00000 ^ 0x7fc00000 >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == 0x7fc00000){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removeNaNFromTable(){
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(0x7fc00000 ^ 0x7fc00000 >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == 0x7fc00000){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContainsTrue() {
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(TypeUtil.FLT_TRUE_BITS ^ TypeUtil.FLT_TRUE_BITS >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == TypeUtil.FLT_TRUE_BITS){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    boolean removeTrueFromTable(){
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(TypeUtil.FLT_TRUE_BITS ^ TypeUtil.FLT_TRUE_BITS >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == TypeUtil.FLT_TRUE_BITS){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    private boolean tableContains(int intBits){
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(intBits ^ intBits >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == intBits){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }

    boolean removeFromTable(int intBits){
        int[] table;
        int tableLength,initialHash;
        int tableVal;
        if((tableVal=(table=this.table)[initialHash=(intBits ^ intBits >>> 16)
                & (tableLength=table.length - 1)]) != EMPTY){
            int hash=initialHash;
            do{
                if(tableVal == intBits){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != EMPTY);
        }
        return false;
    }
    @Override public boolean contains(Object val){
        if(size!=0) {
            returnFalse:for(;;) {
                checkNaN:for(;;) {
                    checkPos0:for(;;) {
                        int intBits;
                        if(val instanceof Float) {
                            float f;
                            if((f=(float)val)==f) {
                                if((intBits=Float.floatToRawIntBits(f))==0) {
                                    break checkPos0;
                                }
                            }else{
                                break checkNaN;
                            }
                        }else if(val instanceof Integer) {
                            if((intBits=(int)val)==0) {
                                break checkPos0;
                            }else if(!TypeUtil.checkCastToFloat(intBits)) {
                                break returnFalse;
                            }
                            intBits=Float.floatToRawIntBits(intBits);
                        }else if(val instanceof Long) {
                            long l;
                            if((l=(long)val)==0) {
                                break checkPos0;
                            }else if(!TypeUtil.checkCastToFloat(l)) {
                                break returnFalse;
                            }
                            intBits=Float.floatToRawIntBits(l);
                        }else if(val instanceof Double) {
                            double d;
                            float f;
                            if((d=(double)val)==(f=(float)d)) {
                                if((intBits=Float.floatToRawIntBits(f))==0) {
                                    break checkPos0;
                                }
                            }else if(d!=d) {
                                break checkNaN;
                            }else {
                                break returnFalse;
                            }
                        }else if(val instanceof Short || val instanceof Byte) {
                            if((intBits=((Number)val).shortValue())==0) {
                                break checkPos0;
                            }
                            intBits=Float.floatToRawIntBits(intBits);
                        }else if(val instanceof Character) {
                            if((intBits=(char)val)==0) {
                                break checkPos0;
                            }
                            intBits=Float.floatToRawIntBits(intBits);
                        }else if(val instanceof Boolean) {
                            if((boolean)val) {
                                return tableContainsTrue();
                            }else {
                                break checkPos0;
                            }
                        }else {
                            break returnFalse;
                        }
                        return tableContains(intBits);
                    }
        return tableContainsPos0();
                }
        return tableContainsNaN();
            }
        }
        return false;
    }

    @Override public boolean contains(boolean val){
        if(size!=0) {
            if(val) {
                return tableContainsTrue();
            }
            return tableContainsPos0();
        }
        return false;
    }

    @Override public boolean contains(char val){
        if(size!=0) {
            if(val==0) {
                return tableContainsPos0();
            }
            return tableContains(Float.floatToRawIntBits(val));
        }
        return false;
    }

    @Override public boolean contains(short val){
        if(size!=0) {
            if(val==0) {
                return tableContainsPos0();
            }
            return tableContains(Float.floatToRawIntBits(val));
        }
        return false;
    }

    @Override public boolean contains(int val){
        if(size!=0) {
            if(val==0) {
                return tableContainsPos0();
            }else if(TypeUtil.checkCastToFloat(val)) {
                return tableContains(Float.floatToRawIntBits(val));
            }
        }
        return false;
    }

    @Override public boolean contains(long val){
        if(size!=0) {
            if(val==0) {
                return tableContainsPos0();
            }else if(TypeUtil.checkCastToFloat(val)) {
                return tableContains(Float.floatToRawIntBits(val));
            }
        }
        return false;
    }

    @Override public boolean contains(float val){
        if(size!=0) {
            if(val==val) {
                int bits;
                if((bits=Float.floatToRawIntBits(val))!=0) {
                    return tableContains(bits);
                }
                return tableContainsPos0();
            }
            return tableContainsNaN();
        }
        return false;
    }

    @Override public boolean contains(double val){
        if(size!=0) {
            double d;
            float f;
            if((d=val)==(f=(float)d)) {
                int bits;
                if((bits=Float.floatToRawIntBits(f))==0) {
                    return tableContainsPos0();
                }
                return tableContains(bits);
            }else if(d!=d) {
                return tableContainsNaN();
            }
        }
        return false;
    }

    @Override public boolean remove(Object val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    checkNaN:for(;;) {
                        checkPos0:for(;;) {
                            int intBits;
                            if(val instanceof Float) {
                                float f;
                                if((f=(float)val)==f) {
                                    if((intBits=Float.floatToRawIntBits(f))==0) {
                                        break checkPos0;
                                    }
                                }else{
                                    break checkNaN;
                                }
                            }else if(val instanceof Integer) {
                                if((intBits=(int)val)==0) {
                                    break checkPos0;
                                }else if(!TypeUtil.checkCastToFloat(intBits)) {
                                    break returnFalse;
                                }
                                intBits=Float.floatToRawIntBits(intBits);
                            }else if(val instanceof Long) {
                                long l;
                                if((l=(long)val)==0) {
                                    break checkPos0;
                                }else if(!TypeUtil.checkCastToFloat(l)) {
                                    break returnFalse;
                                }
                                intBits=Float.floatToRawIntBits(l);
                            }else if(val instanceof Double) {
                                double d;
                                float f;
                                if((d=(double)val)==(f=(float)d)) {
                                    if((intBits=Float.floatToRawIntBits(f))==0) {
                                        break checkPos0;
                                    }
                                }else if(d!=d) {
                                    break checkNaN;
                                }else {
                                    break returnFalse;
                                }
                            }else if(val instanceof Short || val instanceof Byte) {
                                if((intBits=((Number)val).shortValue())==0) {
                                    break checkPos0;
                                }
                                intBits=Float.floatToRawIntBits(intBits);
                            }else if(val instanceof Character) {
                                if((intBits=(char)val)==0) {
                                    break checkPos0;
                                }
                                intBits=Float.floatToRawIntBits(intBits);
                            }else if(val instanceof Boolean) {
                                if((boolean)val) {
                                    if(removeTrueFromTable()) {
                                        break returnTrue;
                                    }
                                    break returnFalse;
                                }else {
                                    break checkPos0;
                                }
                            }else {
                                break returnFalse;
                            }
                            if(removeFromTable(intBits)) {
                                break returnTrue;
                            }
                            break returnFalse;
                        }
        if(removePos0FromTable()) {
            break returnTrue;
        }
        break returnFalse;
                    }
        if(removeNaNFromTable()) {
            break returnTrue;
        }
        break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(boolean val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val) {
                        if(removeTrueFromTable()) {
                            break returnTrue;
                        }
                    }else if(removePos0FromTable()) {
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }


    @Override public boolean removeVal(char val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val==0) {
                        if(removePos0FromTable()) {
                            break returnTrue;
                        }
                    }else if(removeFromTable(Float.floatToRawIntBits(val))) {
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(short val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val==0) {
                        if(removePos0FromTable()) {
                            break returnTrue;
                        }
                    }else if(removeFromTable(Float.floatToRawIntBits(val))) {
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(int val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val==0) {
                        if(removePos0FromTable()) {
                            break returnTrue;
                        }
                    }else if(TypeUtil.checkCastToFloat(val)) {
                        if(removeFromTable(Float.floatToRawIntBits(val))) {
                            break returnTrue;
                        }
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(long val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val==0) {
                        if(removePos0FromTable()) {
                            break returnTrue;
                        }
                    }else if(TypeUtil.checkCastToFloat(val)) {
                        if(removeFromTable(Float.floatToRawIntBits(val))) {
                            break returnTrue;
                        }
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(float val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    if(val==val) {
                        int bits;
                        if((bits=Float.floatToRawIntBits(val))!=0) {
                            if(removeFromTable(bits)) {
                                break returnTrue;
                            }
                        }else if(removePos0FromTable()) {
                            break returnTrue;
                        }
                    }else if(removeNaNFromTable()) {
                        break returnTrue;
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }

    @Override public boolean removeVal(double val){
        int size;
        if((size=this.size)!=0) {
            returnFalse:for(;;) {
                returnTrue:for(;;) {
                    double d;
                    float f;
                    if((d=val)==(f=(float)d)) {
                        int bits;
                        if((bits=Float.floatToRawIntBits(f))==0) {
                            if(removePos0FromTable()) {
                                break returnTrue;
                            }
                        }else if(removeFromTable(bits)) {
                            break returnTrue;
                        }
                    }else if(d!=d) {
                        if(removeNaNFromTable()) {
                            break returnTrue;
                        }
                    }
                    break returnFalse;
                }
        this.size=size-1;
        return true;
            }
        }
        return false;
    }
    private void uncheckedCopyToArray(int size,Float[] dst) {
        int[] table=this.table;
        for(int i=0;;++i) {
            int tableVal;
            switch(tableVal=table[i]) {
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                dst[--size]=0.0f;
                break;
            default:
                dst[--size]=Float.intBitsToFloat(tableVal);
            }
            if(size==0) {
                return;
            }
        }
    }
    private void uncheckedCopyToArray(int size,Object[] dst) {
        int[] table=this.table;
        for(int i=0;;++i) {
            int tableVal;
            switch(tableVal=table[i]) {
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                dst[--size]=0.0f;
                break;
            default:
                dst[--size]=Float.intBitsToFloat(tableVal);
            }
            if(size==0) {
                return;
            }
        }
    }
    private void uncheckedCopyToArray(int size,float[] dst) {
        int[] table=this.table;
        for(int i=0;;++i) {
            int tableVal;
            switch(tableVal=table[i]) {
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                dst[--size]=0.0f;
                break;
            default:
                dst[--size]=Float.intBitsToFloat(tableVal);
            }
            if(size==0) {
                return;
            }
        }
    }
    private void uncheckedCopyToArray(int size,double[] dst) {
        int[] table=this.table;
        for(int i=0;;++i) {
            int tableVal;
            switch(tableVal=table[i]) {
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                dst[--size]=0.0d;
                break;
            default:
                dst[--size]=Float.intBitsToFloat(tableVal);
            }
            if(size==0) {
                return;
            }
        }
    }


    @Override public Float[] toArray(){
        int size;
        if((size=this.size)!=0) {
            Float[] dst;
            uncheckedCopyToArray(size,dst=new Float[size]);
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
    }

    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        int size;
        T[] dst=arrConstructor.apply(size=this.size);
        if(size!=0) {
            uncheckedCopyToArray(size,dst);
        }
        return dst;
    }

    @Override public <T> T[] toArray(T[] dst){
        int size;
        if((size=this.size)!=0) {
            uncheckedCopyToArray(size,OmniArray.uncheckedArrResize(size,dst));
        }else if(dst.length!=0) {
            dst[0]=null;
        }
        return dst;
    }

    @Override public void writeExternal(ObjectOutput out) throws IOException{
        int size;
        out.writeInt(size=this.size);
        if(size!=0) {
            int[] table;
            for(int i=(table=this.table).length;--i>=0;) {
                int tableVal;
                switch(tableVal=table[i]) {
                default:
                    out.writeInt(tableVal);
                    if(--size==0) {
                        return;
                    }
                case EMPTY:
                case DELETED:
                }
            }
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException{
        int size;
        this.size=size=in.readInt();
        this.loadFactor=0.75f;
        if(size!=0) {
            int tableSize;
            maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
            int[] table;
            this.table=table=new int[tableSize];
            do {
                quickInsert(table,in.readInt());
            }while(--size != 0);
        }else {
            maxTableSize=1;
        }
    }

    @Override void updateMaxTableSize(float loadFactor){
        int[] table;
        if((table=this.table) != null){
            this.maxTableSize=(int)(table.length * loadFactor);
        }
    }

    @Override public Object clone(){
        return new FloatOpenAddressHashSet(this);
    }

    @Override public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    private String quickToString(int size){
        byte[] buffer;
        (buffer=new byte[size * 17])[0]='[';
        int[] table;
        int bufferOffset=0;
        for(int i=(table=this.table).length;;){
            int tableVal;
            float f;
            switch(tableVal=table[--i]){
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                f=0.0f;
                break;
            default:
                f=Float.intBitsToFloat(tableVal);
            }
            bufferOffset=ToStringUtil.getStringFloat(f,buffer,++bufferOffset);
            if(--size == 0){
                break;
            }
            buffer[bufferOffset]=',';
            buffer[++bufferOffset]=' ';
        }
        buffer[bufferOffset]=']';
        return new String(buffer,0,bufferOffset + 1,ToStringUtil.IOS8859CharSet);
    }
    private String massiveToString(int size){
        var builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]);
        int[] table;
        for(int i=(table=this.table).length;;){
            int tableVal;
            float f;
            switch(tableVal=table[--i]){
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                f=0.0f;
                break;
            default:
                f=Float.intBitsToFloat(tableVal);
            }
            builder.uncheckedAppendFloat(f);
            if(--size == 0){
                break;
            }
            builder.uncheckedAppendCommaAndSpace();
        }
        builder.uncheckedAppendChar((byte)']');
        byte[] buffer;
        (buffer=builder.buffer)[0]=(byte)'[';
        return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
    }
    @Override public String toString(){
        int size;
        if((size=this.size) != 0){
            if(size <= OmniArray.MAX_ARR_SIZE / 17){
                return quickToString(size);
            }
            return massiveToString(size);
        }
        return "[]";
    }

    @Override public int hashCode(){
        int size;
        if((size=this.size) != 0){
            int hash=0;
            int[] table;
            for(int i=(table=this.table).length;;){
                int tableVal;
                switch(tableVal=table[--i]){
                case EMPTY:
                case DELETED:
                    continue;
                default:
                    hash+=tableVal;
                case POS0:
                }
                if(--size == 0){
                    return hash;
                }
            }
        }
        return 0;
    }
    private void insert(int[] table,int hash,int intBits){
        int size;
        if((size=++this.size) >= maxTableSize){
            maxTableSize=(int)((hash=table.length << 1) * loadFactor);
            int[] newTable;
            this.table=newTable=new int[hash];
            for(int i=0;;++i){
                int tableVal;
                switch(tableVal=table[i]){
                case EMPTY:
                case DELETED:
                    continue;
                default:
                    quickInsert(newTable,tableVal);
                    if(--size == 1){
                        quickInsert(newTable,intBits);
                        return;
                    }
                }
            }
        }else{
            table[hash]=intBits;
        }
    }
    boolean addToTable(int intBits,int hash){
        int[] table;
        if((table=this.table) != null){
            int tableLength;
            int insertHere=-1;
            insertInTable:for(final int initialHash=hash&=tableLength=table.length - 1;;){
                int tableVal;
                switch(tableVal=table[hash]){
                case EMPTY:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case DELETED:
                    insertHere=hash;
                    break;
                default:
                    if(tableVal == intBits){
                        // already contains;
                        return false;
                    }
                }
                if((hash=hash + 1 & tableLength) == initialHash){
                    break insertInTable;
                }
            }
            insert(table,insertHere,intBits);
            return true;
        }
        int maxTableSize;
        this.table=table=new int[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[hash & maxTableSize - 1]=intBits;
        return true;
    }
    boolean addPos0ToTable(){
        int[] table;
        if((table=this.table) != null){
            int tableLength=table.length;
            int insertHere=-1;
            int hash=0;
            insertInTable:for(;;){
                int tableVal;
                switch(tableVal=table[hash]){
                case EMPTY:
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break insertInTable;
                case DELETED:
                    insertHere=hash;
                    break;
                default:
                    if(tableVal == POS0){
                        // already contains;
                        return false;
                    }
                }
                if(++hash == tableLength){
                    break insertInTable;
                }
            }
            insert(table,insertHere,POS0);
            return true;
        }
        int maxTableSize;
        this.table=table=new int[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[0]=POS0;
        return true;
    }

    @Override public boolean add(boolean val){
        if(val){
            return addToTable(TypeUtil.FLT_TRUE_BITS,TypeUtil.FLT_TRUE_BITS ^ TypeUtil.FLT_TRUE_BITS >>> 16);
        }
        return addPos0ToTable();
    }

    @Override public boolean add(Float val){
        return add((float)val);
    }
    void uncheckedForEach(int size,FloatConsumer action){
        int[] table;
        for(int i=(table=this.table).length;;){
            int tableVal;
            switch(tableVal=table[--i]){
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                action.accept(0.0f);
                break;
            default:
                action.accept(Float.intBitsToFloat(tableVal));
            }
            if(--size == 0){
                return;
            }
        }
    }

    @Override public void forEach(FloatConsumer action){
        int size;
        if((size=this.size) != 0){
            uncheckedForEach(size,action);
        }
    }

    @Override public void forEach(Consumer<? super Float> action){
        int size;
        if((size=this.size) != 0){
            uncheckedForEach(size,action::accept);
        }
    }

    boolean uncheckedRemoveIf(int size,FloatPredicate filter){
        int newSize=0;
        int[] table;
        for(int numLeft=size,i=(table=this.table).length;;){
            float f;
            int tableVal;
            switch(tableVal=table[--i]){
            case EMPTY:
            case DELETED:
                continue;
            case POS0:
                f=0.0f;
                break;
            default:
                f=Float.intBitsToFloat(tableVal);
            }
            if(filter.test(f)){
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
    public boolean removeIf(FloatPredicate filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
    }

    @Override public boolean removeIf(Predicate<? super Float> filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter::test);
    }

    @Override
    public OmniIterator.OfFloat iterator(){
        return new Itr(this);
    }
    private static class Itr extends AbstractFloatItr{
        private final FloatOpenAddressHashSet root;
        private int tableIndex;
        Itr(FloatOpenAddressHashSet root){
            this.root=root;
            if(root.size != 0){
                final int[] table;
                for(int i=(table=root.table).length;;){
                    switch(table[--i]){
                    default:
                        this.tableIndex=i;
                        return;
                    case EMPTY:
                    case DELETED:
                    }
                }
            }else{
                this.tableIndex=-1;
            }
        }
        @Override
        public void remove(){
            FloatOpenAddressHashSet root;
            --(root=this.root).size;
            var table=root.table;
            for(int tableIndex=this.tableIndex;;){
                switch(table[++tableIndex]){
                default:
                    table[tableIndex]=DELETED;
                    return;
                case EMPTY:
                case DELETED:
                }
            }
        }
        @Override
        public boolean hasNext(){
            return this.tableIndex != -1;
        }
        @Override
        public float nextFloat(){
            int table[];
            int tableIndex;
            int retBits=(table=root.table)[tableIndex=this.tableIndex];
            setTableIndex:for(;;){
                if(--tableIndex == -1){
                    break setTableIndex;
                }
                switch(table[tableIndex]){
                default:
                    break setTableIndex;
                case DELETED:
                case EMPTY:
                }
            }
            this.tableIndex=tableIndex;
            if(retBits != POS0){
                return Float.intBitsToFloat(retBits);
            }
            return 0.0f;
        }
        private void uncheckedForEachRemaining(int tableIndex,FloatConsumer action){
            int[] table=root.table;
            do{
                int tableVal;
                switch(tableVal=table[tableIndex]){
                case POS0:
                    action.accept(0.0f);
                    break;
                default:
                    action.accept(Float.intBitsToFloat(tableVal));
                case EMPTY:
                case DELETED:
                }
            }while(--tableIndex != -1);
            this.tableIndex=-1;
        }
        @Override
        public void forEachRemaining(FloatConsumer action){
            int tableIndex;
            if((tableIndex=this.tableIndex) != -1){
                uncheckedForEachRemaining(tableIndex,action);
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Float> action){
            int tableIndex;
            if((tableIndex=this.tableIndex) != -1){
                uncheckedForEachRemaining(tableIndex,action::accept);
            }
        }
    }

    @Override public float[] toFloatArray(){
        int size;
        if((size=this.size)!=0) {
            float[] dst;
            uncheckedCopyToArray(size,dst=new float[size]);
            return dst;
        }
        return OmniArray.OfFloat.DEFAULT_ARR;
    }

    @Override public double[] toDoubleArray(){
        int size;
        if((size=this.size)!=0) {
            double[] dst;
            uncheckedCopyToArray(size,dst=new double[size]);
            return dst;
        }
        return OmniArray.OfDouble.DEFAULT_ARR;
    }

    @Override public boolean add(char val){
        if(val == 0){
            return addPos0ToTable();
        }
        int intBits;
        return addToTable(intBits=Float.floatToRawIntBits(val),intBits ^ intBits >>> 16);
    }

    @Override public boolean add(long val){
        if(val == 0){
            return addPos0ToTable();
        }
        int intBits;
        return addToTable(intBits=Float.floatToRawIntBits(val),intBits ^ intBits >>> 16);
    }

    @Override public boolean add(short val){
        if(val == 0){
            return addPos0ToTable();
        }
        int intBits;
        return addToTable(intBits=Float.floatToRawIntBits(val),intBits ^ intBits >>> 16);
    }

    @Override public boolean add(int val){
        if(val == 0){
            return addPos0ToTable();
        }
        return addToTable(val=Float.floatToRawIntBits(val),val ^ val >>> 16);
    }

    @Override public boolean add(float val){
        if(val == val){
            int intBits;
            if((intBits=Float.floatToRawIntBits(val)) == 0){
                return addPos0ToTable();
            }
            return addToTable(intBits,intBits ^ intBits >>> 16);
        }
        return addToTable(0x7fc00000,0x7fc00000 ^ 0x7fc00000 >>> 16);
    }
    public static class Checked extends FloatOpenAddressHashSet{
        transient int modCount;
        public Checked(){
            super();
        }
        public Checked(float loadFactor){
            super(loadFactor);
        }
        public Checked(FloatOpenAddressHashSet that){
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
                int[] table;
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
        void uncheckedForEach(int size,FloatConsumer action){
            int modCount=this.modCount;
            try{
                super.uncheckedForEach(size,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        public OmniIterator.OfFloat iterator(){
            return new CheckedItr(this);
        }
        private static class CheckedItr extends AbstractFloatItr{
            private final Checked root;
            private int tableIndex;
            private int lastRet;
            private int modCount;
            CheckedItr(Checked root){
                this.root=root;
                this.lastRet=-1;
                this.modCount=root.modCount;
                if(root.size != 0){
                    final int[] table;
                    for(int i=(table=root.table).length;;){
                        switch(table[--i]){
                        default:
                            this.tableIndex=i;
                            return;
                        case EMPTY:
                        case DELETED:
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
            private void uncheckedForEachRemaining(int tableIndex,FloatConsumer action){
                var root=this.root;
                int modCount=this.modCount;
                int lastRet=tableIndex;
                try{
                    int[] table=root.table;
                    do{
                        int tableVal;
                        switch(tableVal=table[tableIndex]){
                        case POS0:
                            action.accept(0.0f);
                            lastRet=tableIndex;
                            break;
                        default:
                            action.accept(Float.intBitsToFloat(tableVal));
                            lastRet=tableIndex;
                        case EMPTY:
                        case DELETED:
                        }
                    }while(--tableIndex != -1);
                }finally{
                    CheckedCollection.checkModCount(modCount,root.modCount);
                }
                this.tableIndex=-1;
                this.lastRet=lastRet;
            }
            @Override
            public void forEachRemaining(Consumer<? super Float> action){
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    uncheckedForEachRemaining(tableIndex,action::accept);
                }
            }
            @Override
            public void forEachRemaining(FloatConsumer action){
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    uncheckedForEachRemaining(tableIndex,action);
                }
            }
            @Override
            public float nextFloat(){
                Checked root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    this.lastRet=tableIndex;
                    int table[];
                    int retBits=(table=root.table)[tableIndex];
                    setTableIndex:for(;;){
                        if(--tableIndex == -1){
                            break setTableIndex;
                        }
                        switch(table[tableIndex]){
                        default:
                            break setTableIndex;
                        case DELETED:
                        case EMPTY:
                        }
                    }
                    this.tableIndex=tableIndex;
                    if(retBits != POS0){
                        return Float.intBitsToFloat(retBits);
                    }
                    return 0.0f;
                }
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext(){
                return this.tableIndex != -1;
            }
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
        boolean removeFromTable(int intBits){
            if(super.removeFromTable(intBits)){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean addToTable(int intBits,int hash){
            if(super.addToTable(intBits,hash)){
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
        boolean uncheckedRemoveIf(int size,FloatPredicate filter){
            int[] table;
            int[] indicesToRemove=new int[size];
            int numRemoved=0;
            int modCount=this.modCount;
            try{
                for(int numLeft=size,i=(table=this.table).length;;){
                    float f;
                    int tableVal;
                    switch(tableVal=table[--i]){
                    case EMPTY:
                    case DELETED:
                        continue;
                    case POS0:
                        f=0.0f;
                        break;
                    default:
                        f=Float.intBitsToFloat(tableVal);
                    }
                    if(filter.test(f)){
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
    }
}
