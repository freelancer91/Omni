package omni.impl.set;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
public class RefOpenAddressHashSet<E>extends AbstractOpenAddressHashSet implements OmniSet.OfRef<E>{
    transient Object[] table;
    private static final Object DELETED=new Object();
    private static final Object NULL=new Object();
    public RefOpenAddressHashSet(){
        super();
    }
    public RefOpenAddressHashSet(RefOpenAddressHashSet<? extends E> that){
        super(that);
        int size;
        if((size=that.size) != 0){
            Object[] table;
            this.table=table=new Object[tableSizeFor(size)];
            Object[] thatTable;
            for(int i=(thatTable=that.table).length;;){
                Object tableVal;
                if((tableVal=thatTable[--i]) == null || tableVal == DELETED){
                    continue;
                }
                quickInsert(table,tableVal);
                if(--size == 0){
                    return;
                }
            }
        }
    }
    public RefOpenAddressHashSet(int initialCapacity){
        super(initialCapacity);
    }
    public RefOpenAddressHashSet(float loadFactor){
        super(loadFactor);
    }
    public RefOpenAddressHashSet(int initialCapacity,float loadFactor){
        super(initialCapacity,loadFactor);
    }
    private static void quickInsert(Object[] table,Object val){
        int tableLength;
        int hash;
        for(hash=((hash=val.hashCode()) ^ hash >>> 16) & (tableLength=table.length - 1);;){
            if(table[hash] == null){
                table[hash]=val;
                return;
            }
            hash=hash + 1 & tableLength;
        }
    }
    @Override
    public void clear(){
        if(size != 0){
            Object[] table;
            for(int i=(table=this.table).length;--i >= 0;){
                table[i]=null;
            }
            size=0;
        }
    }
    private boolean tableContainsNull(){
        Object[] table;
        Object tableVal;
        if((tableVal=(table=this.table)[0]) != null){
            int tableLength=table.length;
            int hash=0;
            do{
                if(tableVal == NULL){
                    return true;
                }
            }while(++hash != tableLength && (tableVal=table[hash]) != null);
        }
        return false;
    }
    boolean removeNullFromTable(){
        Object[] table;
        Object tableVal;
        if((tableVal=(table=this.table)[0]) != null){
            int tableLength=table.length;
            int hash=0;
            do{
                if(tableVal == NULL){
                    table[hash]=DELETED;
                    return true;
                }
            }while(++hash != tableLength && (tableVal=table[hash]) != null);
        }
        return false;
    }
    private boolean tableContains(Object val){
        Object[] table;
        int tableLength,initialHash;
        Object tableVal;
        if((tableVal=(table=this.table)[initialHash=((initialHash=val.hashCode()) ^ initialHash >>> 16)
                & (tableLength=table.length - 1)]) != null){
            int hash=initialHash;
            do{
                if(val.equals(tableVal)){
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != null);
        }
        return false;
    }
    boolean removeFromTable(Object val){
        Object[] table;
        int tableLength,initialHash;
        Object tableVal;
        if((tableVal=(table=this.table)[initialHash=((initialHash=val.hashCode()) ^ initialHash >>> 16)
                & (tableLength=table.length - 1)]) != null){
            int hash=initialHash;
            do{
                if(val.equals(tableVal)){
                    table[hash]=DELETED;
                    return true;
                }
            }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != null);
        }
        return false;
    }
    private void uncheckedCopyToArray(int size,Object[] dst){
        Object[] table=this.table;
        for(int i=0;;++i){
            Object tableVal;
            if((tableVal=table[i]) == null || tableVal == DELETED){
                continue;
            }else if(tableVal == NULL){
                dst[--size]=null;
            }else{
                dst[--size]=tableVal;
            }
            if(size == 0){
                return;
            }
        }
    }
    private void insert(Object[] table,int hash,Object val){
        int size;
        if((size=++this.size) >= maxTableSize){
            maxTableSize=(int)((hash=table.length << 1) * loadFactor);
            Object[] newTable;
            this.table=newTable=new Object[hash];
            for(int i=0;;++i){
                Object tableVal;
                if((tableVal=table[i]) == null || tableVal == DELETED){
                    continue;
                }else{
                    quickInsert(newTable,tableVal);
                    if(--size == 1){
                        quickInsert(newTable,val);
                        return;
                    }
                }
            }
        }else{
            table[hash]=val;
        }
    }
    private boolean addNullToTable(){
        Object[] table;
        if((table=this.table) != null){
            int tableLength=table.length;
            int insertHere=-1;
            int hash=0;
            for(;;){
                Object tableVal;
                if((tableVal=table[hash]) == null){
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break;
                }else if(tableVal == DELETED){
                    insertHere=hash;
                }else if(tableVal == NULL){
                    // already contained
                    return false;
                }
                if(++hash == tableLength){
                    break;
                }
            }
            insert(table,insertHere,NULL);
            return true;
        }
        int maxTableSize;
        this.table=table=new Object[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[0]=NULL;
        return true;
    }
    private boolean addToTable(Object val,int hash){
        Object[] table;
        if((table=this.table) != null){
            int tableLength;
            int insertHere=-1;
            for(final int initialHash=hash&=tableLength=table.length - 1;;){
                Object tableVal;
                if((tableVal=table[hash]) == null){
                    if(insertHere == -1){
                        insertHere=hash;
                    }
                    break;
                }else if(tableVal == DELETED){
                    insertHere=hash;
                }else if(val.equals(tableVal)){
                    // already contained
                    return false;
                }
                if((hash=hash + 1 & tableLength) == initialHash){
                    break;
                }
            }
            insert(table,insertHere,val);
            return true;
        }
        int maxTableSize;
        this.table=table=new Object[maxTableSize=this.maxTableSize];
        this.maxTableSize=(int)(maxTableSize * loadFactor);
        this.size=1;
        table[hash & maxTableSize - 1]=val;
        return true;
    }
    @SuppressWarnings("unchecked")
    void uncheckedForEach(int size,Consumer<? super E> action){
        Object[] table;
        for(int i=(table=this.table).length;;){
            Object tableVal;
            if((tableVal=table[--i]) == null || tableVal == DELETED){
                continue;
            }else if(tableVal == NULL){
                action.accept(null);
            }else{
                action.accept((E)tableVal);
            }
            if(--size == 0){
                return;
            }
        }
    }
    @Override
    public void forEach(Consumer<? super E> action){
        int size;
        if((size=this.size) != 0){
            uncheckedForEach(size,action);
        }
    }
    @SuppressWarnings("unchecked")
    boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
        int newSize=0;
        Object[] table;
        for(int numLeft=size,i=(table=this.table).length;;){
            Object tableVal;
            if((tableVal=table[--i]) == null || tableVal == DELETED){
                continue;
            }else if(tableVal == NULL){
                tableVal=null;
            }
            if(filter.test((E)tableVal)){
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
    public boolean removeIf(Predicate<? super E> filter){
        int size;
        return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
    }
    @Override
    public void writeExternal(ObjectOutput out) throws IOException{
        int size;
        out.writeInt(size=this.size);
        if(size != 0){
            Object[] table;
            for(int i=(table=this.table).length;--i >= 0;){
                Object tableVal;
                if((tableVal=table[i]) != null && tableVal != DELETED){
                    if(tableVal == NULL){
                        tableVal=null;
                    }
                    out.writeObject(tableVal);
                    if(--size == 0){
                        return;
                    }
                }
            }
        }
    }
    @Override
    public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException{
        int size;
        this.size=size=in.readInt();
        this.loadFactor=0.75f;
        if(size != 0){
            int tableSize;
            maxTableSize=(int)((tableSize=tableSizeFor(size)) * .75f);
            Object[] table;
            this.table=table=new Object[tableSize];
            do{
                Object obj;
                if((obj=in.readObject()) == null){
                    obj=NULL;
                }
                quickInsert(table,obj);
            }while(--size != 0);
        }else{
            maxTableSize=1;
        }
    }
    @Override
    void updateMaxTableSize(float loadFactor){
        Object[] table;
        if((table=this.table) != null){
            this.maxTableSize=(int)(table.length * loadFactor);
        }
    }
    @Override
    public Object clone(){
        return new RefOpenAddressHashSet<>(this);
    }
    @Override
    public boolean equals(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    String uncheckedToString(int size){
        var builder=new StringBuilder("[");
        Object[] table;
        for(int i=(table=this.table).length;;){
            Object tableVal;
            if((tableVal=table[--i]) == null || tableVal == DELETED){
                continue;
            }else if(tableVal == NULL){
                builder.append((String)null);
            }else{
                builder.append(tableVal);
            }
            if(--size == 0){
                return builder.append(']').toString();
            }
            builder.append(',').append(' ');
        }
    }
    @Override
    public String toString(){
        int size;
        if((size=this.size) != 0){
            return uncheckedToString(size);
        }
        return "[]";
    }
    int uncheckedHashCode(int size){
        int hash=0;
        Object[] table;
        for(int i=(table=this.table).length;;){
            Object tableVal;
            if((tableVal=table[--i]) == null || tableVal == DELETED){
                continue;
            }else if(tableVal != NULL){
                hash+=tableVal.hashCode();
            }
            if(--size == 0){
                return hash;
            }
        }
    }
    @Override
    public int hashCode(){
        int size;
        if((size=this.size) != 0){
            return uncheckedHashCode(size);
        }
        return 0;
    }
    @Override
    public boolean contains(Object val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(boolean val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(byte val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(char val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(short val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(int val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(long val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(float val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(double val){
        if(size != 0){
            return tableContains(val);
        }
        return false;
    }
    @Override
    public boolean contains(Boolean val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Integer val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Long val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Float val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Double val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Byte val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Character val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean contains(Short val){
        if(size != 0){
            if(val != null){
                return tableContains(val);
            }
            return tableContainsNull();
        }
        return false;
    }
    @Override
    public boolean removeVal(Byte val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Character val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Short val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean remove(Object val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
            if(removeFromTable(val)){
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
            if(removeFromTable(val)){
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(char val){
        int size;
        if((size=this.size) != 0){
            if(removeFromTable(val)){
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(short val){
        int size;
        if((size=this.size) != 0){
            if(removeFromTable(val)){
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
            if(removeFromTable(val)){
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
            if(removeFromTable(val)){
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
            if(removeFromTable(val)){
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
            if(removeFromTable(val)){
                this.size=size - 1;
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean removeVal(Boolean val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Integer val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Long val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Float val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public boolean removeVal(Double val){
        int size;
        if((size=this.size) != 0){
            returnFalse:for(;;){
                returnTrue:for(;;){
                    if(val != null){
                        if(removeFromTable(val)){
                            break returnTrue;
                        }
                    }else if(removeNullFromTable()){
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
    public Object[] toArray(){
        int size;
        if((size=this.size) != 0){
            Object[] dst;
            uncheckedCopyToArray(size,dst=new Object[size]);
            return dst;
        }
        return OmniArray.OfRef.DEFAULT_ARR;
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
            uncheckedCopyToArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
        }else if(dst.length != 0){
            dst[0]=null;
        }
        return dst;
    }
    @Override
    public boolean add(E val){
        if(val != null){
            int hash;
            return addToTable(val,(hash=val.hashCode()) ^ hash >>> 16);
        }
        return addNullToTable();
    }
    @Override
    public OmniIterator.OfRef<E> iterator(){
        return new Itr<>(this);
    }
    private static class Itr<E> implements OmniIterator.OfRef<E>{
        private final RefOpenAddressHashSet<E> root;
        private int tableIndex;
        Itr(RefOpenAddressHashSet<E> root){
            this.root=root;
            if(root.size != 0){
                final Object[] table;
                for(int i=(table=root.table).length;;){
                    Object tableVal;
                    if((tableVal=table[--i]) != null && tableVal != DELETED){
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
            RefOpenAddressHashSet<E> root;
            --(root=this.root).size;
            var table=root.table;
            for(int tableIndex=this.tableIndex;;){
                Object tableVal;
                if((tableVal=table[++tableIndex]) != null && tableVal != DELETED){
                    table[tableIndex]=DELETED;
                    return;
                }
            }
        }
        @SuppressWarnings("unchecked")
        @Override
        public void forEachRemaining(Consumer<? super E> action){
            int tableIndex;
            if((tableIndex=this.tableIndex) != -1){
                Object[] table=root.table;
                do{
                    Object tableVal;
                    if((tableVal=table[tableIndex]) != null && tableVal != DELETED){
                        if(tableVal == NULL){
                            action.accept(null);
                        }else{
                            action.accept((E)tableVal);
                        }
                    }
                }while(--tableIndex != -1);
                this.tableIndex=-1;
            }
        }
        @Override
        public boolean hasNext(){
            return this.tableIndex != -1;
        }
        @SuppressWarnings("unchecked")
        @Override
        public E next(){
            Object table[];
            int tableIndex;
            Object retVal=(table=root.table)[tableIndex=this.tableIndex];
            for(;;){
                if(--tableIndex == -1){
                    break;
                }
                Object tableVal;
                if((tableVal=table[tableIndex]) != null && tableVal != DELETED){
                    break;
                }
            }
            this.tableIndex=tableIndex;
            if(retVal != NULL){
                return (E)retVal;
            }
            return null;
        }
    }
    public static class Checked<E>extends RefOpenAddressHashSet<E>{
        transient int modCount;
        public Checked(){
            super();
        }
        public Checked(float loadFactor){
            super(loadFactor);
        }
        public Checked(RefOpenAddressHashSet<E> that){
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
                Object[] table;
                for(int i=(table=this.table).length;--i >= 0;){
                    table[i]=null;
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
            return new Checked<>(this);
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return super.equals(val);
        }
        @Override
        void uncheckedForEach(int size,Consumer<? super E> action){
            int modCount=this.modCount;
            try{
                super.uncheckedForEach(size,action);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        public OmniIterator.OfRef<E> iterator(){
            return new CheckedItr<>(this);
        }
        @Override
        boolean removeNullFromTable(){
            if(super.removeNullFromTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @Override
        boolean removeFromTable(Object val){
            if(super.removeFromTable(val)){
                ++this.modCount;
                return true;
            }
            return false;
        }
        private boolean addToTable(int modCount,Object val,int hash){
            Object[] table;
            if((table=this.table) != null){
                int tableLength;
                int insertHere=-1;
                for(final int initialHash=hash&=tableLength=table.length - 1;;){
                    Object tableVal;
                    if((tableVal=table[hash]) == null){
                        if(insertHere == -1){
                            insertHere=hash;
                        }
                        break;
                    }else if(tableVal == DELETED){
                        insertHere=hash;
                    }else if(val.equals(tableVal)){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        // already contained
                        return false;
                    }
                    if((hash=hash + 1 & tableLength) == initialHash){
                        break;
                    }
                }
                CheckedCollection.checkModCount(modCount,this.modCount);
                this.modCount=modCount + 1;
                super.insert(table,insertHere,val);
                return true;
            }
            CheckedCollection.checkModCount(modCount,this.modCount);
            this.modCount=modCount + 1;
            int maxTableSize;
            this.table=table=new Object[maxTableSize=this.maxTableSize];
            this.maxTableSize=(int)(maxTableSize * loadFactor);
            this.size=1;
            table[hash & maxTableSize - 1]=val;
            return true;
        }
        @Override
        public boolean add(E val){
            if(val != null){
                int modCount=this.modCount;
                try{
                    int hash;
                    return addToTable(modCount,val,(hash=val.hashCode()) ^ hash >>> 16);
                }catch(ConcurrentModificationException e){
                    throw e;
                }catch(RuntimeException e){
                    throw CheckedCollection.checkModCount(modCount,this.modCount,e);
                }
            }else if(super.addNullToTable()){
                ++this.modCount;
                return true;
            }
            return false;
        }
        @SuppressWarnings("unchecked")
        @Override
        boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
            Object[] table;
            int[] indicesToRemove=new int[size];
            int numRemoved=0;
            int modCount=this.modCount;
            try{
                for(int numLeft=size,i=(table=this.table).length;;){
                    Object tableVal;
                    if((tableVal=table[--i]) == null || tableVal == DELETED){
                        continue;
                    }else if(tableVal == NULL){
                        tableVal=null;
                    }
                    if(filter.test((E)tableVal)){
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
        private static class CheckedItr<E> implements OmniIterator.OfRef<E>{
            private final Checked<E> root;
            private int tableIndex;
            private int lastRet;
            private int modCount;
            CheckedItr(Checked<E> root){
                this.root=root;
                this.lastRet=-1;
                this.modCount=root.modCount;
                if(root.size != 0){
                    final Object[] table;
                    for(int i=(table=root.table).length;;){
                        Object tableVal;
                        if((tableVal=table[--i]) != null && tableVal != DELETED){
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
                    Checked<E> root;
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
            @SuppressWarnings("unchecked")
            @Override
            public void forEachRemaining(Consumer<? super E> action){
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    var root=this.root;
                    int modCount=this.modCount;
                    int lastRet=tableIndex;
                    try{
                        Object[] table=root.table;
                        do{
                            Object tableVal;
                            if((tableVal=table[tableIndex]) != null && tableVal != DELETED){
                                if(tableVal == NULL){
                                    action.accept(null);
                                }else{
                                    action.accept((E)tableVal);
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
            }
            @SuppressWarnings("unchecked")
            @Override
            public E next(){
                Checked<E> root;
                CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
                int tableIndex;
                if((tableIndex=this.tableIndex) != -1){
                    this.lastRet=tableIndex;
                    Object table[];
                    Object retVal=(table=root.table)[tableIndex];
                    for(;;){
                        if(--tableIndex == -1){
                            break;
                        }
                        Object tableVal;
                        if((tableVal=table[tableIndex]) != null && tableVal != DELETED){
                            break;
                        }
                    }
                    this.tableIndex=tableIndex;
                    if(retVal != NULL){
                        return (E)retVal;
                    }
                    return null;
                }
                throw new NoSuchElementException();
            }
            @Override
            public boolean hasNext(){
                return this.tableIndex != -1;
            }
        }
        @Override
        public boolean contains(Object val){
            if(size != 0){
                if(val != null){
                    int modCount=this.modCount;
                    try{
                        return super.tableContains(val);
                    }finally{
                        CheckedCollection.checkModCount(modCount,this.modCount);
                    }
                }
                return super.tableContainsNull();
            }
            return false;
        }
        private boolean removeFromTable(int modCount,Object val){
            Object[] table;
            int tableLength,initialHash;
            Object tableVal;
            if((tableVal=(table=this.table)[initialHash=((initialHash=val.hashCode()) ^ initialHash >>> 16)
                    & (tableLength=table.length - 1)]) != null){
                int hash=initialHash;
                do{
                    if(val.equals(tableVal)){
                        CheckedCollection.checkModCount(modCount,this.modCount);
                        this.modCount=modCount + 1;
                        table[hash]=DELETED;
                        return true;
                    }
                }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != null);
            }
            CheckedCollection.checkModCount(modCount,this.modCount);
            return false;
        }
        @Override
        public boolean remove(Object val){
            int size;
            if((size=this.size) != 0){
                returnFalse:for(;;){
                    returnTrue:for(;;){
                        if(val != null){
                            int modCount=this.modCount;
                            try{
                                if(removeFromTable(modCount,val)){
                                    break returnTrue;
                                }
                            }catch(ConcurrentModificationException e){
                                throw e;
                            }catch(RuntimeException e){
                                throw CheckedCollection.checkModCount(modCount,this.modCount,e);
                            }
                        }else if(super.removeNullFromTable()){
                            ++this.modCount;
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
        String uncheckedToString(int size){
            int modCount=this.modCount;
            try{
                return super.uncheckedToString(size);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
        @Override
        int uncheckedHashCode(int size){
            int modCount=this.modCount;
            try{
                return super.uncheckedHashCode(size);
            }finally{
                CheckedCollection.checkModCount(modCount,this.modCount);
            }
        }
    }
}
