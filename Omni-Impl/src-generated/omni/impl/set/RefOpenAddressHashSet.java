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
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import java.util.ConcurrentModificationException;
public class RefOpenAddressHashSet<E>
extends AbstractOpenAddressHashSet<E>
implements OmniSet.OfRef<E>{
  private static int tableHash(Object val){
  //TODO improve this hash function
    int tmp;
    return (tmp=val.hashCode())^(tmp>>>16);
  }
  private static int tableHash(long val){
  //TODO improve this hash function
    int tmp;
    return (tmp=(int)(val^(val>>>32)))^(tmp>>>16);
  }
  private static int tableHash(int val){
  //TODO improve this hash function
    return val^(val>>>16);
  }
  private static final Object NULL=new Object();
  private static final int NULLHASH=tableHash(NULL);
  private static final Object DELETED=new Object();
  private static void quickInsert(Object[] table,Object val){
    int tableLength;
    int hash;
    for(hash=((hash=val.hashCode()) ^ hash >>> 16) & (tableLength=table.length-1);;){
      if(table[hash]==null){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  transient Object[] table;
  public RefOpenAddressHashSet(){
    super();
  }
  public RefOpenAddressHashSet(RefOpenAddressHashSet<E> that){
    super(that);
    int size;
    if((size=that.size)!=0){
      Object[] table;
      int tableLength;
      this.table=table=new Object[tableLength=tableSizeFor(size)];
      this.maxTableSize=(int)(tableLength*loadFactor);
      Object[] thatTable;
      for(tableLength=(thatTable=that.table).length;;){
        Object tableVal;
        if((tableVal=thatTable[--tableLength]) == null || tableVal == DELETED){
          continue;
        }
        quickInsert(table,tableVal);
        if(--size==0) {
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
  @Override public boolean add(E val){
    if(val==null){
      return addToTable(NULL,NULLHASH);
    }
    return addToTable(val,tableHash(val));
  }
  @Override public void clear() {
    if(size!=0) {
      Object[] table;
      for(int i=(table=this.table).length;--i>=0;) {
        table[i]=null;
      }
      size=0;
    }
  }
  @Override public Object clone(){
    return new RefOpenAddressHashSet<E>(this);
  }
  @Override public boolean contains(boolean val){
    return size!=0 && tableContains(val,val?1231:1237);
  }
  @Override public boolean contains(byte val){
    return size!=0 && tableContains(val,tableHash(val));
  }
  @Override public boolean contains(char val){
    return size!=0 && tableContains(val,val);
  }
  @Override public boolean contains(short val){
    return size!=0 && tableContains(val,tableHash(val));
  }
  @Override public boolean contains(int val){
    return size!=0 && tableContains(val,tableHash(val));
  }
  @Override public boolean contains(long val){
    return size!=0 && tableContains(val,tableHash(val));
  }
  @Override public boolean contains(float val){
    if(size!=0){
      if(val==val){
        return tableContains(val,tableHash(Float.floatToRawIntBits(val)));
      }
      return tableContains(Float.NaN,0x7fc00000 ^ 0x7fc00000 >>> 16);
    }
    return false;
  }
  @Override public boolean contains(double val){
    if(size!=0){
      if(val==val){
        return tableContains(val,tableHash(Double.doubleToRawLongBits(val)));
      }
      return tableContains(Double.NaN,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16);
    }
    return false;
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      int hash;
      return tableContains(val,(hash=val.hashCode())^(hash>>>16));
    }
    return false;
  }
  @Override public boolean contains(Boolean val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }else{
        return tableContains(val,val.hashCode());
      }
    }
    return false;
  }
  @Override public boolean contains(Byte val){
    if(size!=0){
      int hash;
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      return tableContains(val,(hash=val.hashCode())^(hash>>>16));
    }
    return false;
  }
  @Override public boolean contains(Character val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      return tableContains(val,val.hashCode());
    }
    return false;
  }
  @Override public boolean contains(Short val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      int hash;
      return tableContains(val,(hash=val.hashCode())^(hash>>>16));
    }
    return false;
  }
  @Override public boolean contains(Integer val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      int hash;
      return tableContains(val,(hash=val.hashCode())^(hash>>>16));
    }
    return false;
  }
  @Override public boolean contains(Long val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }
      int hash;
      return tableContains(val,(hash=val.hashCode())^(hash>>>16));
    }
    return false;
  }
  @Override public boolean contains(Float val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }else{
        int hash;
        float f;
        if((f=(float)val)==f){
          hash=(hash=Float.floatToRawIntBits(f))^(hash>>>16);
        }else{
          val=Float.NaN;
          hash=0x7fc00000 ^ 0x7fc00000 >>> 16;
        }
        return tableContains(val,hash);
      }
    }
    return false;
  }
  @Override public boolean contains(Double val){
    if(size!=0){
      if(val==null){
        return tableContains(NULL,NULLHASH);
      }else{
        int hash;
        double d;
        if((d=(double)val)==d){
          long bits;
          hash=(hash=(int)((bits=Double.doubleToRawLongBits(d))^(bits>>>32)))^(hash>>>16);
        }else{
          val=Double.NaN;
          hash=(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16;
        }
        return tableContains(val,hash);
      } 
    }
    return false;
  }
  @Override public boolean equals(Object val){
    //TODO
                throw omni.util.NotYetImplementedException.getNYI();
  }
  @Override public void forEach(Consumer<? super E> action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
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
  @Override public OmniIterator.OfRef<E> iterator(){
    return new Itr<E>(this);
  }
  @Override
  public void readExternal(ObjectInput in) throws IOException
    ,ClassNotFoundException
  {
      int size;
      this.size=size=in.readInt();
      float loadFactor;
      this.loadFactor=loadFactor=in.readFloat();
      if(size != 0){
        int tableSize;
        maxTableSize=(int)((tableSize=tableSizeFor(size)) * loadFactor);
        Object[] table;
        this.table=table=new Object[tableSize];
        do {
          Object obj;
          if((obj=in.readObject())==null){
            obj=NULL;
          }
          quickInsert(table,obj);
        }while(--size != 0);
      }else{
        maxTableSize=1;
      }
  }
  @Override public boolean remove(Object val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            int hash;
            if(removeFromTable(val,(hash=val.hashCode())^(hash>>>16))){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override
  public boolean removeIf(Predicate<? super E> filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override public boolean removeVal(boolean val){
    int size;
    if((size=this.size)!=0) {
      returnFalse:for(;;) {
        returnTrue:for(;;) {
          if(removeFromTable(val,val?1231:1237)){
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
  @Override public boolean removeVal(byte val){
    int size;
    if((size=this.size)!=0 && removeFromTable(val,tableHash(val))){
      this.size=size-1;
      return true;
    }
    return false;
  }
  @Override public boolean removeVal(char val){
    int size;
    if((size=this.size)!=0) {
       if(removeFromTable(val,val)){
         this.size=size-1;
         return true;
       }
    }
    return false;
  }
  @Override public boolean removeVal(short val){
    int size;
    if((size=this.size)!=0){
      if(removeFromTable(val,tableHash(val))){
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(int val){
    int size;
    if((size=this.size)!=0){
      if(removeFromTable(val,tableHash(val))){
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(long val){
    int size;
    if((size=this.size)!=0)
    {
      if(removeFromTable(val,tableHash(val)))
      {
        this.size=size-1;
        return true;
      }
    }
    return false;
  }
  @Override public boolean removeVal(float val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==val){
            if(removeFromTable(val,tableHash(Float.floatToRawIntBits(val)))){
              break returnTrue;
            }
          }else if(removeFromTable(Float.NaN,0x7fc00000 ^ 0x7fc00000 >>> 16)){
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==val){
            if(removeFromTable(val,tableHash(Double.doubleToRawLongBits(val)))){
              break returnTrue;
            }
          }else if(removeFromTable(Double.NaN,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16)){
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
  @Override public boolean removeVal(Boolean val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            if(removeFromTable(val,val?1231:1237)){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Byte val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            int hash;
            if(removeFromTable(val,(hash=val.hashCode())^(hash>>>16))){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Character val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            if(removeFromTable(val,val.hashCode())){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Short val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            int hash;
            if(removeFromTable(val,(hash=val.hashCode())^(hash>>>16))){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Integer val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            int hash;
            if(removeFromTable(val,(hash=val.hashCode())^(hash>>>16))){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Long val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            int hash;
            if(removeFromTable(val,(hash=val.hashCode())^(hash>>>16))){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Float val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            float f;
            if((f=(float)val)==f){
              int hash;
              if(removeFromTable(val,(hash=Float.floatToRawIntBits(val))^(hash>>>16))){
                break returnTrue;
              }
            }else if(removeFromTable(Float.NaN,0x7fc00000 ^ 0x7fc00000 >>> 16)){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public boolean removeVal(Double val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val!=null){
            double d;
            if((d=(double)val)==d){
              long bits;
              int hash;
              if((removeFromTable(val,(hash=(int)((bits=Double.doubleToRawLongBits(d))^(bits>>>32)))^(hash>>>16)))){
                break returnTrue;
              }
            }else if(removeFromTable(Double.NaN,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16)){
              break returnTrue;
            }
          }else if(removeFromTable(NULL,NULLHASH)){
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
  @Override public Object[] toArray(){
    int size;
    if((size=this.size) != 0){
      Object[] dst;
      uncheckedCopyIntoArray(size,dst=new Object[size]);
      return dst;
    }
    return OmniArray.OfRef.DEFAULT_ARR;
  }
  private void uncheckedCopyIntoArray(int size,Object[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        Object tableVal;
        if((tableVal=table[i]) == null || tableVal == DELETED){
          continue;
        }else if(tableVal==NULL){
          dst[--size]=null;
        }else{
          dst[--size]=tableVal;
        }
        if(size==0) {
            return;
        }
    }
  }
  @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    int size;
    final T[] arr=arrConstructor.apply(size=this.size);
    if(size != 0){
        uncheckedCopyIntoArray(size,arr);
    }
    return arr;
  }
  @Override public <T> T[] toArray(T[] dst){
    int size;
    if((size=this.size) != 0){
        uncheckedCopyIntoArray(size,dst=OmniArray.uncheckedArrResize(size,dst));
    }else if(dst.length != 0){
        dst[0]=null;
    }
    return dst;
  }
  @Override
  public String toString(){
      int size;
      if((size=this.size) != 0){
        return uncheckedToString(size);
      }
      return "[]";
  }
  @Override
  public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      out.writeFloat(this.loadFactor);
      if(size != 0){
        Object[] table;
        for(int i=(table=this.table).length;;) {
          Object tableVal;
          if((tableVal=table[--i]) != null && tableVal != DELETED){
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
private boolean addToTable(Object val,int hash){
  Object[] table;
  if((table=this.table)!=null){
    int tableLength;
    int insertHere=-1;
    insertInTable:for(final int initialHash=hash&=tableLength=table.length - 1;;){
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
        break insertInTable;
      }
    }
    insert(table,insertHere,val);
    return true;
  }
  int maxTableSize;
  this.table=table=new Object[maxTableSize=this.maxTableSize];
  this.size=1;
  table[hash & maxTableSize - 1]=val;
  this.maxTableSize=(int)(maxTableSize*loadFactor);
  return true;
}
  boolean removeFromTable(
  Object val,int hash){
    Object[] table;
    Object tableVal;
    int tableLength;
    if((tableVal=(table=this.table)[hash&=(tableLength=table.length-1)])!=null){
      final int initialHash=hash;
      do{
        if(val.equals(tableVal)){
          table[hash]=DELETED;
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=null);
    }
    return false;
  }
  private boolean tableContains(
  Object val,int hash){
    Object[] table;
    Object tableVal;
    int tableLength;
    if((tableVal=(table=this.table)[hash&=(tableLength=table.length-1)])!=null){
      final int initialHash=hash;
      do{
        if(val.equals(tableVal)){
          return true;
        }
      }while((hash=(hash+1)&tableLength)!=initialHash&&(tableVal=table[hash])!=null);
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  void forEachHelper(int size,Consumer<? super E> action){
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
  void updateMaxTableSize(float loadFactor){
      Object[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
      }
  }
  private void insert(Object[] table,int hash,Object val){
    int size;
    if((size=++this.size) >= maxTableSize){
        maxTableSize=(int)((hash=table.length << 1) * loadFactor);
        Object[] newTable;
        this.table=newTable=new Object[hash];
        if(size!=1){
          for(int i=0;;++i){
            Object tableVal;
            if((tableVal=table[i]) == null || tableVal == DELETED){
              continue;
            }
            quickInsert(newTable,tableVal);
            if(--size == 1){
              break;
            }
          }
        }
        quickInsert(newTable,val);
    }else{
        table[hash]=val;
    }
  }
  private static class Itr<E>
  implements OmniIterator.OfRef<E>{
      private final RefOpenAddressHashSet<E> root;
      private int offset;
      Itr(Itr<E> itr){
        this.root=itr.root;
        this.offset=itr.offset;
      }
      Itr(RefOpenAddressHashSet<E> root){
          this.root=root;
          if(root.size != 0){
              final Object[] table;
              for(int offset=(table=root.table).length;;){
                  Object tableVal;
                  if((tableVal=table[--offset]) != null && tableVal != DELETED){
                      this.offset=offset;
                      return;
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override
      public Object clone(){
        return new Itr<E>(this);
      }
      @Override
      public boolean hasNext(){
          return offset != -1;
      }
      @SuppressWarnings("unchecked")
      @Override public void forEachRemaining(Consumer<? super E> action){
        int offset;
        if((offset=this.offset)!=-1){
          Object[] table;
          Object tableVal;
          action.accept((E)((tableVal=(table=root.table)[offset])==NULL?null:tableVal));
          while(--offset!=-1){
            if((tableVal=table[offset]) != null && tableVal != DELETED){
              action.accept((E)(tableVal==NULL?null:tableVal));
            }
          }
          this.offset=-1;
        }
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next(){
          int offset;
          Object[] table;
          var ret=(table=root.table)[offset=this.offset];
          setOffset:for(;;){
            if(--offset == -1){
              break setOffset;
            }
            Object tableVal;
            if((tableVal=table[offset]) != null && tableVal != DELETED){
                break;
            }
          }
          this.offset=offset;
          if(ret != NULL){
            return (E)ret;
          }
          return null;
      }
      @Override
      public void remove(){
          RefOpenAddressHashSet<E> root;
          --(root=this.root).size;
          var table=root.table;
          for(int offset=this.offset;;){
            Object tableVal;
            if((tableVal=table[++offset]) != null && tableVal != DELETED){
                table[offset]=DELETED;
                return;
            }
          }
      }
  }
  public static class Checked<E> extends RefOpenAddressHashSet<E>{
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(RefOpenAddressHashSet<E> that){
      super(that);
    }
    public Checked(int initialCapacity){
      super(validateInitialCapacity(initialCapacity));
    }
    public Checked(float loadFactor){
        super(validateLoadFactor(loadFactor));
    }
    public Checked(int initialCapacity,float loadFactor){
        super(validateInitialCapacity(initialCapacity),validateLoadFactor(loadFactor));
    }
    @Override void updateMaxTableSize(float loadFactor){
      super.updateMaxTableSize(validateLoadFactor(loadFactor));
    }
    @Override
    public boolean add(E val){
        int hash;
        if(val != null){
            int modCount=this.modCount;
            try{
                return addToTable(modCount,val,(hash=val.hashCode()) ^ hash >>> 16);
            }catch(ConcurrentModificationException e){
                throw e;
            }catch(RuntimeException e){
                throw CheckedCollection.checkModCount(modCount,this.modCount,e);
            }
        }else if(super.addToTable(NULL,NULLHASH)){
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
    @Override public void clear(){
      if(this.size != 0){
          ++this.modCount;
          this.size=0;
          Object[] table;
          for(int i=(table=this.table).length;--i >= 0;){
              table[i]=null;
          }
      }
    }
    @Override public Object clone(){
      return new Checked<E>(this);
    }
    @Override
    public boolean contains(Object val){
        if(size != 0){
            if(val != null){
                int modCount=this.modCount;
                try{
                    int hash;
                    return super.tableContains(val,(hash=val.hashCode())^(hash>>>16));
                }finally{
                    CheckedCollection.checkModCount(modCount,this.modCount);
                }
            }
            return super.tableContains(NULL,NULLHASH);
        }
        return false;
    }
    @Override public boolean equals(Object val){
      //TODO
                  throw omni.util.NotYetImplementedException.getNYI();
    }
    @Override public OmniIterator.OfRef<E> iterator(){
      return new Itr<E>(this);
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
                            int hash;
                            if(removeFromTable(modCount,val,(hash=val.hashCode())^(hash>>>16))){
                                break returnTrue;
                            }
                        }catch(ConcurrentModificationException e){
                            throw e;
                        }catch(RuntimeException e){
                            throw CheckedCollection.checkModCount(modCount,this.modCount,e);
                        }
                    }else if(super.removeFromTable(NULL,NULLHASH)){
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
    private boolean removeFromTable(int modCount,Object val,int hash){
        Object[] table;
        int tableLength,initialHash;
        Object tableVal;
        if((tableVal=(table=this.table)[initialHash=hash &= (tableLength=table.length - 1)]) != null){
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
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray((arrSize)->{
        final int modCount=this.modCount;
        try{
          return arrConstructor.apply(arrSize);
        }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
        }
      });
    }
    @Override public void writeExternal(ObjectOutput out) throws IOException{
      final int modCount=this.modCount;
      try{
        super.writeExternal(out);
      }finally{
        CheckedCollection.checkModCount(modCount,this.modCount);
      }
    }
    @Override
    boolean removeFromTable(Object val,int hash){
        if(super.removeFromTable(val,hash)){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override void forEachHelper(int size,Consumer<? super E> action){
        int modCount=this.modCount;
        try{
            super.forEachHelper(size,action);
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
    @SuppressWarnings("unchecked")
    @Override boolean uncheckedRemoveIf(int size,Predicate<? super E> filter){
      int numRemovedFromTable=0;
      final int modCount=this.modCount;
      int[] tableIndicesRemoved;
      Object[] table;
      try{
          for(int numLeft=size,i=(table=this.table).length;;){
              Object tableVal;
              if((tableVal=table[--i])==null || tableVal==DELETED) {
                  continue;
              }else if(tableVal==NULL){
                tableVal=null;
              }
              if(filter.test((E)tableVal)){
                  (tableIndicesRemoved=new int[numLeft])[0]=i;
                  outer:for(;;){
                    if(--numLeft==0){
                      break;
                    }
                    for(;;){
                      if((tableVal=table[--i])==null || tableVal==DELETED){
                        continue;
                      }else if(tableVal==NULL){
                        tableVal=null;
                      }
                      if(filter.test((E)tableVal)){
                        tableIndicesRemoved[++numRemovedFromTable]=i;
                      }
                      continue outer;
                    }
                  }
                  break;
              }
              if(--numLeft == 0){
                  return false;
              }
          }
      }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
      }
      this.modCount=modCount + 1;
      this.size=size - numRemovedFromTable-1;
      do{
        table[tableIndicesRemoved[numRemovedFromTable]]=DELETED;
      }while(--numRemovedFromTable!=-1);
      return true;
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
    private static class Itr<E>
    implements OmniIterator.OfRef<E>{
      private final Checked<E> root;
      private int offset;
      private int modCount;
      private int lastRet;
      Itr(Itr<E> itr){
        this.root=itr.root;
        this.modCount=itr.modCount;
        this.offset=itr.offset;
        this.lastRet=itr.lastRet;
      }
      Itr(Checked<E> root){
          this.root=root;
          this.modCount=root.modCount;
          this.lastRet=-1;
          if(root.size != 0){
              final Object[] table;
              for(int i=(table=root.table).length;;){
                  Object tableVal;
                  if((tableVal=table[--i])!=null && tableVal!=DELETED) {
                      this.offset=i;
                      return;
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override public Object clone(){
        return new Itr<E>(this);
      }
      @SuppressWarnings("unchecked")
      @Override public void forEachRemaining(Consumer<? super E> action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          var root=this.root;
          int modCount=this.modCount;
          int lastRet;
          int offset;
          try{
              var table=root.table;
              Object tableVal;
              action.accept((E)((tableVal=table[offset=lastRet=expectedOffset])==NULL?null:tableVal));
              while(--offset!=-1){
                if((tableVal=table[offset]) != null && tableVal != DELETED){
                    action.accept((E)(tableVal==NULL?null:tableVal));
                    lastRet=offset;
                }
              }
          }finally{
              CheckedCollection.checkModCount(modCount,root.modCount,expectedOffset,this.offset);
          }
          this.offset=-1;
          this.lastRet=lastRet;
        }
      }
      @Override
      public boolean hasNext(){
          return this.offset != -1;
      }
      @SuppressWarnings("unchecked")
      @Override
      public E next(){
          Checked<E> root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          int offset;
          if((offset=this.offset) != -1){
              this.lastRet=offset;
              Object table[];
              var ret=(table=root.table)[offset];
              setOffset:for(;;){
                  if(--offset == -1){
                      break setOffset;
                  }
                  Object tableVal;
                  if((tableVal=table[offset])!=null && tableVal!=DELETED) {
                      break;
                  }
              }
              this.offset=offset;
              if(ret != NULL){
                  return (E)ret;
              }
              return null;
          }
          throw new NoSuchElementException();
      }
      @Override
      public void remove(){
          int lastRet;
          if((lastRet=this.lastRet) != -1){
              int modCount;
              final Checked<E> root;
              CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              --root.size;
              root.table[lastRet]=DELETED;
              this.lastRet=-1;
              return;
          }
          throw new IllegalStateException();
      }
    }
  }
}
