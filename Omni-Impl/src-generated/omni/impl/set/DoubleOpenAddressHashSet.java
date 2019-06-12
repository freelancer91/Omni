//#TYPEDEF OfRef
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
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import omni.impl.AbstractDoubleItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class DoubleOpenAddressHashSet
extends AbstractOpenAddressHashSet
implements OmniSet.OfDouble{
  private static void quickInsert(long[] table,long val){
    int tableLength;
    int hash;
    for(hash=((hash=(int)(val ^ val >>> 32)) ^ hash >>> 16) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  transient long[] table;
  public DoubleOpenAddressHashSet(){
    super();
  }
  public DoubleOpenAddressHashSet(DoubleOpenAddressHashSet that){
    super(that);
    int size;
    if((size=that.size)!=0){
      long[] table;
      this.table=table=new long[tableSizeFor(size)];
      long[] thatTable;
      for(int i=(thatTable=that.table).length;;){
        long tableVal;
        if((tableVal=thatTable[--i]) == 0 || tableVal == 0x7ffc000000000000L){
          continue;
        }
        quickInsert(table,tableVal);
        if(--size==0) {
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
  @Override public boolean add(boolean val){
    if(val){
      return addToTable(TypeUtil.DBL_TRUE_BITS,(int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16);
    }
    return addPos0ToTable();
  }
  @Override public boolean add(int val){
    if(val == 0){
      return addPos0ToTable();
    }
    long bits;
    return addToTable(bits=Double.doubleToRawLongBits(val),(val=(int)(bits ^ bits >>> 32)) ^ val >>> 16);
  }
  @Override public boolean add(long val){
    if(val == 0){
      return addPos0ToTable();
    }
    int hash;
    return addToTable(val=Double.doubleToRawLongBits(val),(hash=(int)(val ^ val >>> 32)) ^ hash >>> 16);
  }
  @Override public boolean add(float val){
    if(val == val){
      long bits;
      if((bits=Double.doubleToRawLongBits(val)) == 0){
        return addPos0ToTable();
      }
      int hash;
      return addToTable(bits,(hash=(int)(bits ^ bits >>> 32)) ^ hash >>> 16);
    }
    return addToTable(0x7ff8000000000000L,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16);
  }
  @Override
  public boolean add(double val){
    if(val == val){
      long longBits;
      if((longBits=Double.doubleToRawLongBits(val)) == 0){
          return addPos0ToTable();
      }
      int hash;
      return addToTable(longBits,(hash=(int)(longBits ^ longBits >>> 32)) ^ hash >>> 16);
    }
    return addToTable(0x7ff8000000000000L,(int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32)
            ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16);
  }
  @Override public boolean add(Double val){
    return add((double)val);
  }
  @Override public void clear() {
    if(size!=0) {
      long[] table;
      for(int i=(table=this.table).length;--i>=0;) {
        table[i]=0;
      }
      size=0;
    }
  }
  @Override public Object clone(){
    return new DoubleOpenAddressHashSet(this);
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
  @Override public boolean contains(int val){
    if(size!=0) {
      if(val==0) {
        return tableContainsPos0();
      }
      {
        return tableContains(Double.doubleToRawLongBits(val));
      }
    }
    return false;
  }
  @Override public boolean contains(long val){
    if(size!=0) {
      if(val==0) {
        return tableContainsPos0();
      }else if(TypeUtil.checkCastToDouble(val)) {
        return tableContains(Double.doubleToRawLongBits(val));
      }
    }
    return false;
  }
  @Override public boolean contains(float val){
    if(size!=0) {
      if(val==val) {
        long bits;
        if((bits=Double.doubleToRawLongBits(val))!=0) {
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
      if(val==val){
        long bits;
        if((bits=Double.doubleToRawLongBits(val))==0)
        {
          return tableContainsPos0();
        }
        return tableContains(bits);
      }
      else
      {
        return tableContainsNaN();
      }
    }
    return false;
  }
  @Override public boolean contains(Object val){
    if(size!=0){
      returnFalse:for(;;){
        checkNaN:for(;;) {
          checkPos0:for(;;) {
            long bits;
            if(val instanceof Double) {
              double v;
              if((v=(double)val)==v) {
                if((bits=Double.doubleToRawLongBits(v))==0) {
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
              bits=Double.doubleToRawLongBits(v);
            }else if(val instanceof Long){
              if((bits=(long)val) == 0){
                  break checkPos0;
              }else if(!TypeUtil.checkCastToDouble(bits)){
                  break returnFalse;
              }
              bits=Double.doubleToRawLongBits(bits);
            }else if(val instanceof Float){
              float f;
              if((f=(float)val) == f){
                  if((bits=Double.doubleToRawLongBits(f)) == 0){
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
              bits=Double.doubleToRawLongBits(v);
            }else if(val instanceof Boolean) {
              if((boolean)val) {
                return tableContainsTrue();
              }else {
                break checkPos0;
              }
            }else {
              break returnFalse;
            }
            return tableContains(bits);
          }
          return tableContainsPos0();
        }
        return tableContainsNaN();
      }
    }
    return false;
  }
  @Override public boolean equals(Object val){
    //TODO
    return false;
  }
  @Override public void forEach(DoubleConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Double> action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action::accept);
    }
  }
  @Override
  public int hashCode(){
    int size;
    if((size=this.size) != 0){
      int hash=0;
      long[] table;
      for(int i=(table=this.table).length;;){
        long tableVal;
        if((tableVal=table[--i]) == 0 || tableVal == 0x7ffc000000000000L){
            continue;
        }else if(tableVal != 0xfffc000000000000L){
            hash+=(int)(tableVal ^ tableVal >>> 32);
        }
        if(--size == 0){
          return hash;
        }
      }
    }
    return 0;
  }
  @Override public OmniIterator.OfDouble iterator(){
    return new Itr(this);
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
        do {
          quickInsert(table,in.readLong());
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
          checkNaN:for(;;) {
            checkPos0:for(;;) {
              long bits;
              if(val instanceof Double) {
                double v;
                if((v=(double)val)==v) {
                  if((bits=Double.doubleToRawLongBits(v))==0) {
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
                bits=Double.doubleToRawLongBits(v);
              }else if(val instanceof Long){
                if((bits=(long)val) == 0){
                    break checkPos0;
                }else if(!TypeUtil.checkCastToDouble(bits)){
                    break returnFalse;
                }
                bits=Double.doubleToRawLongBits(bits);
              }else if(val instanceof Float){
                float f;
                if((f=(float)val) == f){
                    if((bits=Double.doubleToRawLongBits(f)) == 0){
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
                bits=Double.doubleToRawLongBits(v);
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
              if(removeFromTable(bits)) {
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
  @Override public boolean removeVal(int val){
    int size;
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==0) {
            if(removePos0FromTable()) {
              break returnTrue;
            }
          }
          {
            if(removeFromTable(Double.doubleToRawLongBits(val))) {
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==0) {
            if(removePos0FromTable()) {
              break returnTrue;
            }
          }else if(TypeUtil.checkCastToDouble(val)) {
            if(removeFromTable(Double.doubleToRawLongBits(val))) {
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==val) {
            long bits;
            if((bits=Double.doubleToRawLongBits(val))!=0) {
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==val){
            long bits;
            if((bits=Double.doubleToRawLongBits(val))==0) {
              if(removePos0FromTable()) {
                break returnTrue;
              }
            }else if(removeFromTable(bits)) {
              break returnTrue;
            }
          }
          else
          {
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
  @Override public Double[] toArray(){
    int size;
    if((size=this.size) != 0){
      Double[] dst;
      uncheckedCopyIntoArray(size,dst=new Double[size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
  }
  @Override public double[] toDoubleArray(){
    int size;
    if((size=this.size) != 0){
      double[] dst;
      uncheckedCopyIntoArray(size,dst=new double[size]);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  private void uncheckedCopyIntoArray(int size,Object[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        long tableVal;
        if((tableVal=table[i]) == 0 || tableVal == 0x7ffc000000000000L){
          continue;
        }else if(tableVal == 0xfffc000000000000L){
          dst[--size]=0.0d;
        }else{
          dst[--size]=Double.longBitsToDouble(tableVal);
        }
        if(size==0) {
            return;
        }
    }
  }
  private void uncheckedCopyIntoArray(int size,double[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        long tableVal;
        if((tableVal=table[i]) == 0 || tableVal == 0x7ffc000000000000L){
          continue;
        }else if(tableVal == 0xfffc000000000000L){
          dst[--size]=0.0d;
        }else{
          dst[--size]=Double.longBitsToDouble(tableVal);
        }
        if(size==0) {
            return;
        }
    }
  }
  private void uncheckedCopyIntoArray(int size,Double[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        long tableVal;
        if((tableVal=table[i]) == 0 || tableVal == 0x7ffc000000000000L){
          continue;
        }else if(tableVal == 0xfffc000000000000L){
          dst[--size]=0.0d;
        }else{
          dst[--size]=Double.longBitsToDouble(tableVal);
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
          var builder=new StringBuilder("[");
          long[] table;
          for(int i=(table=this.table).length;;){
              long tableVal;
              if((tableVal=table[--i]) == 0 || tableVal == 0x7ffc000000000000L){
                  continue;
              }else if(tableVal == 0xfffc000000000000L){
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
  public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      if(size != 0){
        long[] table;
        for(int i=(table=this.table).length;--i>=0;) {
          long tableVal;
          if((tableVal=table[i]) != 0 && tableVal != 0x7ffc000000000000L){
            out.writeLong(tableVal);
            if(--size == 0){
              return;
            }
          }
        }
      }
  }
  boolean addPos0ToTable(){
    long[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      tableLength=table.length;
      insertInTable:for(int hash=0;;){
        long tableVal;
        if((tableVal=table[hash]) == 0){
          if(insertHere == -1){
            insertHere=hash;
          }
          break;
        }else if(tableVal == 0x7ffc000000000000L){
          insertHere=hash;
        }else if(tableVal == 0xfffc000000000000L){
          // already contained
          return false;
        }
        if(++hash==tableLength){
          break insertInTable;
        }
      }
      insert(table,insertHere,0xfffc000000000000L);
      return true;
    }
    int maxTableSize;
    this.table=table=new long[maxTableSize=this.maxTableSize];
    this.size=1;
    table[0]=0xfffc000000000000L;
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  boolean addToTable(long val,int hash){
    long[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      insertInTable:for(final int initialHash=hash&=tableLength=table.length - 1;;){
        long tableVal;
        if((tableVal=table[hash]) == 0){
          if(insertHere == -1){
            insertHere=hash;
          }
          break;
        }else if(tableVal == 0x7ffc000000000000L){
          insertHere=hash;
        }else if(tableVal == val){
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
    this.table=table=new long[maxTableSize=this.maxTableSize];
    this.size=1;
    table[hash & maxTableSize - 1]=val;
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  boolean removePos0FromTable(){
    long[] table;
    long tableVal;
    if((tableVal=(table=this.table)[0]) != 0){
      int tableLength=table.length;
      int hash=0;
      do{
        if(tableVal==0xfffc000000000000L){
          table[hash]=0x7ffc000000000000L;
          return true;
        }
      }while(++hash != tableLength && (tableVal = table[hash]) != 0);
    }
    return false;
  }
  boolean removeNaNFromTable(){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == 0x7ff8000000000000L){
          table[hash]=0x7ffc000000000000L;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  boolean removeTrueFromTable(){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == TypeUtil.DBL_TRUE_BITS){
          table[hash]=0x7ffc000000000000L;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  boolean removeFromTable(long val){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((initialHash=(int)(val ^ val >>> 32)) ^ initialHash >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          table[hash]=0x7ffc000000000000L;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsPos0(){
    long[] table;
    long tableVal;
    if((tableVal=(table=this.table)[0]) != 0){
      int tableLength=table.length;
      int hash=0;
      do{
        if(tableVal==0xfffc000000000000L){
          return true;
        }
      }while(++hash != tableLength && (tableVal = table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsNaN(){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) ^ (int)(0x7ff8000000000000L ^ 0x7ff8000000000000L >>> 32) >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == 0x7ff8000000000000L){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsTrue(){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) ^ (int)(TypeUtil.DBL_TRUE_BITS ^ TypeUtil.DBL_TRUE_BITS >>> 32) >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == TypeUtil.DBL_TRUE_BITS){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContains(long val){
    long[] table;
    long tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=((initialHash=(int)(val ^ val >>> 32)) ^ initialHash >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  void forEachHelper(int size,DoubleConsumer action){
    long[] table;
    for(int i=(table=this.table).length;;){
        long tableVal;
        if((tableVal=table[--i]) == 0 || tableVal == 0x7ffc000000000000L){
            continue;
        }else if(tableVal == 0xfffc000000000000L){
            action.accept(0.0d);
        }else{
            action.accept(Double.longBitsToDouble(tableVal));
        }
        if(--size == 0){
            return;
        }
    }
  }
  boolean uncheckedRemoveIf(int size,DoublePredicate filter){
    int newSize=0;
    long[] table;
    for(int numLeft=size,i=(table=this.table).length;;){
        double v;
        long tableVal;
        if((tableVal=table[--i]) == 0 || tableVal == 0x7ffc000000000000L){
            continue;
        }else if(tableVal == 0xfffc000000000000L){
            v=0;
        }else{
            v=Double.longBitsToDouble(tableVal);
        }
        if(filter.test(v)){
            table[i]=0x7ffc000000000000L;
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
  void updateMaxTableSize(float loadFactor){
      long[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
      }
  }
  private void insert(long[] table,int hash,long val){
    int size;
    if((size=++this.size) >= maxTableSize){
        maxTableSize=(int)((hash=table.length << 1) * loadFactor);
        long[] newTable;
        this.table=newTable=new long[hash];
        for(int i=0;;++i){
            long tableVal;
            if((tableVal=table[i]) == 0 || tableVal == 0x7ffc000000000000L){
              continue;
            }
            quickInsert(newTable,tableVal);
            if(--size == 1){
              quickInsert(newTable,val);
              return;
            }
        }
    }else{
        table[hash]=val;
    }
  }
  private static class Itr extends AbstractDoubleItr{
      private final DoubleOpenAddressHashSet root;
      private int offset;
      Itr(DoubleOpenAddressHashSet root){
          this.root=root;
          if(root.size != 0){
              final long[] table;
              for(int offset=(table=root.table).length;;){
                  long tableVal;
                  if((tableVal=table[--offset]) != 0 && tableVal != 0x7ffc000000000000L){
                      this.offset=offset;
                      return;
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override
      public boolean hasNext(){
          return offset != -1;
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action::accept);
        }
      }
      @Override
      public double nextDouble(){
          int offset;
          long[] table;
          var ret=(table=root.table)[offset=this.offset];
          setOffset:for(;;){
            if(--offset == -1){
              break setOffset;
            }
            long tableVal;
            if((tableVal=table[offset]) != 0 && tableVal != 0x7ffc000000000000L){
                break;
            }
          }
          this.offset=offset;
          if(ret!=0xfffc000000000000L){
            return Double.longBitsToDouble(ret);
          }
          return 0;
      }
      @Override
      public void remove(){
          DoubleOpenAddressHashSet root;
          --(root=this.root).size;
          var table=root.table;
          for(int offset=this.offset;;){
            long tableVal;
            if((tableVal=table[++offset]) != 0 && tableVal != 0x7ffc000000000000L){
                table[offset]=0x7ffc000000000000L;
                return;
            }
          }
      }
      private void uncheckedForEachRemaining(int offset,DoubleConsumer action){
            var table=root.table;
            do{
                long tableVal;
                if((tableVal=table[offset]) != 0 && tableVal != 0x7ffc000000000000L){
                    if(tableVal == 0xfffc000000000000L){
                        action.accept(0.0d);
                    }else{
                        action.accept(Double.longBitsToDouble(tableVal));
                    }
                }
            }while(--offset != -1);
            this.offset=-1;
        }
  }
  public static class Checked extends DoubleOpenAddressHashSet{
    transient int modCount;
    Checked(){
      super();
    }
    Checked(DoubleOpenAddressHashSet that){
      super(that);
    }
    Checked(int initialCapacity){
      super(initialCapacity);
    }
    @Override public void clear(){
      if(this.size != 0){
          ++this.modCount;
          this.size=0;
          long[] table;
          for(int i=(table=this.table).length;--i >= 0;){
              table[i]=0;
          }
      }
    }
    @Override public Object clone(){
      return new Checked(this);
    }
    @Override public boolean equals(Object val){
      //TODO
      return false;
    }
    @Override public OmniIterator.OfDouble iterator(){
      return new Itr(this);
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
    @Override boolean addPos0ToTable(){
        if(super.addPos0ToTable()){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean addToTable(long val,int hash){
        if(super.addToTable(val,hash)){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean removeFromTable(long val){
        if(super.removeFromTable(val)){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean removeNaNFromTable(){
        if(super.removeNaNFromTable()){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean removePos0FromTable(){
        if(super.removePos0FromTable()){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean removeTrueFromTable(){
        if(super.removeTrueFromTable()){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override void forEachHelper(int size,DoubleConsumer action){
        int modCount=this.modCount;
        try{
            super.forEachHelper(size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override boolean uncheckedRemoveIf(int size,DoublePredicate filter){
      int[] tableIndicesRemoved=null;
      int numRemovedFromTable=0;
      final int modCount=this.modCount;
      long[] table;
      try{
          for(int numLeft=size,i=(table=this.table).length;;){
              double v;
              long tableVal;
              if((tableVal=table[--i])==0 || tableVal==0x7ffc000000000000L) {
                  continue;
              }else if(tableVal==0xfffc000000000000L) {
                  v=0;
              }else {
                  v=Double.longBitsToDouble(tableVal);
              }
              if(filter.test(v)){
                  (tableIndicesRemoved=new int[numLeft])[0]=i;
                  outer:for(;;){
                    if(--numLeft==0){
                      break;
                    }
                    for(;;){
                      if((tableVal=table[--i])==0 || tableVal==0x7ffc000000000000L){
                        continue;
                      }else if(tableVal==0xfffc000000000000L){
                        v=0;
                      }else{
                        v=Double.longBitsToDouble(tableVal);
                      }
                      if(filter.test(v)){
                        tableIndicesRemoved[++numRemovedFromTable]=i;
                      }
                      continue outer;
                    }
                  }
                  break;
              }
              if(--numLeft == 0){
                  break;
              }
          }
      }finally{
          CheckedCollection.checkModCount(modCount,this.modCount);
      }
      if(tableIndicesRemoved != null){
          this.modCount=modCount + 1;
          this.size=size - numRemovedFromTable-1;
          do{
            table[tableIndicesRemoved[numRemovedFromTable]]=0x7ffc000000000000L;
          }while(--numRemovedFromTable!=-1);
      }
      return false;
    }
    private static class Itr extends AbstractDoubleItr{
      private final Checked root;
      private int offset;
      private int modCount;
      private int lastRet;
      Itr(Checked root){
          this.root=root;
          this.modCount=root.modCount;
          this.lastRet=-1;
          if(root.size != 0){
              final long[] table;
              for(int i=(table=root.table).length;;){
                  long tableVal;
                  if((tableVal=table[--i])!=0 && tableVal!=0x7ffc000000000000L) {
                      this.offset=i;
                      return;
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override public void forEachRemaining(DoubleConsumer action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Double> action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action::accept);
        }
      }
      @Override
      public boolean hasNext(){
          return this.offset != -1;
      }
      @Override
      public double nextDouble(){
          Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          int offset;
          if((offset=this.offset) != -1){
              this.lastRet=offset;
              long table[];
              var ret=(table=root.table)[offset];
              setOffset:for(;;){
                  if(--offset == -1){
                      break setOffset;
                  }
                  long tableVal;
                  if((tableVal=table[offset])!=0 && tableVal!=0x7ffc000000000000L) {
                      break;
                  }
              }
              this.offset=offset;
              if(ret != 0xfffc000000000000L){
                  return Double.longBitsToDouble(ret);
              }
              return 0;
          }
          throw new NoSuchElementException();
      }
      @Override
      public void remove(){
          int lastRet;
          if((lastRet=this.lastRet) != -1){
              int modCount;
              final Checked root;
              CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
              root.modCount=++modCount;
              this.modCount=modCount;
              --root.size;
              root.table[lastRet]=0x7ffc000000000000L;
              this.lastRet=-1;
              return;
          }
          throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(int offset,DoubleConsumer action){
          var root=this.root;
          int modCount=this.modCount;
          int lastRet=offset;
          try{
              var table=root.table;
              do{
                  long tableVal;
                  if((tableVal=table[offset])!=0 && tableVal!=0x7ffc000000000000L) {
                      if(tableVal==0xfffc000000000000L) {
                          action.accept(0.0d);
                      }else {
                          action.accept(Double.longBitsToDouble(tableVal));
                      }
                      lastRet=offset;
                  }
              }while(--offset != -1);
          }finally{
              CheckedCollection.checkModCount(modCount,root.modCount);
          }
          this.offset=-1;
          this.lastRet=lastRet;
      }
    }
  }
}
