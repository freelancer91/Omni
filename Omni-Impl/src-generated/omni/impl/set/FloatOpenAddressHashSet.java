package omni.impl.set;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.ConcurrentModificationException;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.impl.AbstractFloatItr;
import omni.impl.CheckedCollection;
import omni.util.OmniArray;
import omni.util.TypeUtil;
import omni.util.ToStringUtil;
public class FloatOpenAddressHashSet
extends AbstractOpenAddressHashSet<Float>
implements OmniSet.OfFloat{
  //TODO long hash codes for negative values are different from int hashcodes of the same value. This is a problem
  private static void quickInsert(int[] table,int val){
    int tableLength;
    int hash;
    for(hash=(val^val>>>16) & (tableLength=table.length-1);;){
      if(table[hash]==0){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
  }
  transient int[] table;
  public FloatOpenAddressHashSet(){
    super();
  }
  public FloatOpenAddressHashSet(FloatOpenAddressHashSet that){
    super(that);
    int size;
    if((size=that.size)!=0){
      int[] table;
      int tableLength;
      this.table=table=new int[tableLength=tableSizeFor(size)];
      this.maxTableSize=(int)(tableLength*loadFactor);
      int[] thatTable;
      for(tableLength=(thatTable=that.table).length;;){
        int tableVal;
        switch(tableVal=thatTable[--tableLength]) {
        case 0x7fe00000:
        case 0:
          continue;
        default:
        }
        quickInsert(table,tableVal);
        if(--size==0) {
          return;
        }
      }
    }
  }
  public FloatOpenAddressHashSet(int initialCapacity){
    super(initialCapacity);
  }
  public FloatOpenAddressHashSet(float loadFactor){
    super(loadFactor);
  }
  public FloatOpenAddressHashSet(int initialCapacity,float loadFactor){
    super(initialCapacity,loadFactor);
  }
  @Override public boolean add(boolean val){
    if(val){
      return addToTable(TypeUtil.FLT_TRUE_BITS,TypeUtil.FLT_TRUE_BITS^ TypeUtil.FLT_TRUE_BITS >>> 16);
    }
    return addPos0ToTable();
  }
  @Override public boolean add(char val){
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
  @Override public boolean add(long val){
    if(val == 0){
      return addPos0ToTable();
    }
    int hash;
    return addToTable(hash=Float.floatToRawIntBits(val),hash ^ hash >>> 16);
  }
  @Override public boolean add(float val){
    if(val == val){
      int bits;
      if((bits=Float.floatToRawIntBits(val)) == 0){
        return addPos0ToTable();
      }
      return addToTable(bits,bits ^ bits >>> 16);
    }
    return addToTable(0x7fc00000,0x7fc00000 ^ 0x7fc00000 >>> 16);
  }
  @Override public boolean add(Float val){
    return add((float)val);
  }
  @Override public void clear() {
    if(size!=0) {
      int[] table;
      for(int i=(table=this.table).length;--i>=0;) {
        table[i]=0;
      }
      size=0;
    }
  }
  @Override public Object clone(){
    return new FloatOpenAddressHashSet(this);
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
      }
      else if(TypeUtil.checkCastToFloat(val))
      {
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
      if((d=val)==(f=(float)d))
      {
        int bits;
        if((bits=Float.floatToRawIntBits(f))==0)
        {
          return tableContainsPos0();
        }
        return tableContains(bits);
      }
      else
      if(d!=d)
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
            int bits;
            if(val instanceof Float) {
              float v;
              if((v=(float)val)==v) {
                if((bits=Float.floatToRawIntBits(v))==0) {
                  break checkPos0;
                }
              }else{
                break checkNaN;
              }
            }else if(val instanceof Integer) {
              if((bits=(int)val)==0) {
                break checkPos0;
              }else if(!TypeUtil.checkCastToFloat(bits)) {
                break returnFalse;
              }
              bits=Float.floatToRawIntBits(bits);
            }else if(val instanceof Long) {
              long l;
              if((l=(long)val)==0) {
                break checkPos0;
              }else if(!TypeUtil.checkCastToFloat(l)) {
                break returnFalse;
              }
              bits=Float.floatToRawIntBits(l);
            }else if(val instanceof Double) {
              double d;
              float f;
              if((d=(double)val)==(f=(float)d)) {
                if((bits=Float.floatToRawIntBits(f))==0) {
                  break checkPos0;
                }
              }else if(d!=d) {
                break checkNaN;
              }else {
                break returnFalse;
              }
            }else if(val instanceof Short || val instanceof Byte) {
              if((bits=((Number)val).shortValue())==0) {
                break checkPos0;
              }
              bits=Float.floatToRawIntBits(bits);
            }else if(val instanceof Character) {
              if((bits=(char)val)==0) {
                break checkPos0;
              }
              bits=Float.floatToRawIntBits(bits);
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
  @Override public void forEach(FloatConsumer action){
    int size;
    if((size=this.size)!=0){
      forEachHelper(size,action);
    }
  }
  @Override public void forEach(Consumer<? super Float> action){
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
      int[] table;
      for(int i=(table=this.table).length;;){
        int tableVal;
        switch(tableVal=table[--i]){
          case 0:
          case 0x7fe00000:
            continue;
          default:
            hash+=tableVal;
          case 0xffe00000:
        }
        if(--size == 0){
          return hash;
        }
      }
    }
    return 0;
  }
  @Override public OmniIterator.OfFloat iterator(){
    return new Itr(this);
  }
  @Override
  public void readExternal(ObjectInput in) throws IOException
  {
      int size;
      this.size=size=in.readInt();
      float loadFactor;
      this.loadFactor=loadFactor=in.readFloat();
      if(size != 0){
        int tableSize;
        maxTableSize=(int)((tableSize=tableSizeFor(size)) * loadFactor);
        int[] table;
        this.table=table=new int[tableSize];
        do {
          quickInsert(table,in.readInt());
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
              int bits;
              if(val instanceof Float) {
                float v;
                if((v=(float)val)==v) {
                  if((bits=Float.floatToRawIntBits(v))==0) {
                    break checkPos0;
                  }
                }else{
                  break checkNaN;
                }
              }else if(val instanceof Integer) {
                if((bits=(int)val)==0) {
                  break checkPos0;
                }else if(!TypeUtil.checkCastToFloat(bits)) {
                  break returnFalse;
                }
                bits=Float.floatToRawIntBits(bits);
              }else if(val instanceof Long) {
                long l;
                if((l=(long)val)==0) {
                  break checkPos0;
                }else if(!TypeUtil.checkCastToFloat(l)) {
                  break returnFalse;
                }
                bits=Float.floatToRawIntBits(l);
              }else if(val instanceof Double) {
                double d;
                float f;
                if((d=(double)val)==(f=(float)d)) {
                  if((bits=Float.floatToRawIntBits(f))==0) {
                    break checkPos0;
                  }
                }else if(d!=d) {
                  break checkNaN;
                }else {
                  break returnFalse;
                }
              }else if(val instanceof Short || val instanceof Byte) {
                if((bits=((Number)val).shortValue())==0) {
                  break checkPos0;
                }
                bits=Float.floatToRawIntBits(bits);
              }else if(val instanceof Character) {
                if((bits=(char)val)==0) {
                  break checkPos0;
                }
                bits=Float.floatToRawIntBits(bits);
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
  public boolean removeIf(FloatPredicate filter){
    int size;
    return (size=this.size) != 0 && uncheckedRemoveIf(size,filter);
  }  
  @Override
  public boolean removeIf(Predicate<? super Float> filter){
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          if(val==0) {
            if(removePos0FromTable()) {
              break returnTrue;
            }
          }
          else if(TypeUtil.checkCastToFloat(val))
          {
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
    if((size=this.size)!=0)
    {
      returnFalse:for(;;)
      {
        returnTrue:for(;;)
        {
          if(val==0)
          {
            if(removePos0FromTable())
            {
              break returnTrue;
            }
          }
          else if(TypeUtil.checkCastToFloat(val))
          {
            if(removeFromTable(Float.floatToRawIntBits(val)))
            {
              break returnTrue;
            }
          }
          break returnFalse;
        }
        //returnTrue
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
    if((size=this.size)!=0){
      returnFalse:for(;;){
        returnTrue:for(;;){
          double d;
          float f;
          if((d=val)==(f=(float)d)){
            int bits;
            if((bits=Float.floatToRawIntBits(f))==0) {
              if(removePos0FromTable()) {
                break returnTrue;
              }
            }else if(removeFromTable(bits)) {
              break returnTrue;
            }
          }
          else
          if(d!=d)
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
  @Override public float[] toFloatArray(){
    int size;
    if((size=this.size) != 0){
      float[] dst;
      uncheckedCopyIntoArray(size,dst=new float[size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  @Override public Float[] toArray(){
    int size;
    if((size=this.size) != 0){
      Float[] dst;
      uncheckedCopyIntoArray(size,dst=new Float[size]);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  private void uncheckedCopyIntoArray(int size,float[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        int tableVal;
        switch(tableVal=table[i]) {
        case 0:
        case 0x7fe00000:
          continue;
        case 0xffe00000:
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
  private void uncheckedCopyIntoArray(int size,Object[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        int tableVal;
        switch(tableVal=table[i]) {
        case 0:
        case 0x7fe00000:
          continue;
        case 0xffe00000:
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
  private void uncheckedCopyIntoArray(int size,Float[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        int tableVal;
        switch(tableVal=table[i]) {
        case 0:
        case 0x7fe00000:
          continue;
        case 0xffe00000:
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
  private void uncheckedCopyIntoArray(int size,double[] dst){
    var table=this.table;
    for(int i=0;;++i) {
        int tableVal;
        switch(tableVal=table[i]) {
        case 0:
        case 0x7fe00000:
          continue;
        case 0xffe00000:
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
        if(size<=(OmniArray.MAX_ARR_SIZE/17)){
          return quickToString(size);
        }else{
          return massiveToString(size);
        }
      }
      return "[]";
  }
  @Override
  public void writeExternal(ObjectOutput out) throws IOException{
      int size;
      out.writeInt(size=this.size);
      out.writeFloat(this.loadFactor);
      if(size != 0){
        int[] table;
        for(int i=(table=this.table).length;--i>=0;) {
          int tableVal;
          switch(tableVal=table[i]) {
            default:
              out.writeInt(tableVal);
              if(--size==0) {
                return;
              }
            case 0:
            case 0x7fe00000:
          }
        }
      }
  }
  boolean addPos0ToTable(){
    int[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      tableLength=table.length;
      insertInTable:for(int hash=0;;){
        int tableVal;
        switch(tableVal=table[hash]){
          case 0:
            if(insertHere == -1){
              insertHere=hash;
            }
            break insertInTable;
          case 0x7fe00000:
            insertHere=hash;
            break;
          default:
            if(tableVal==0xffe00000){
              //already contains
              return false;
            }
        }
        if(++hash==tableLength){
          break insertInTable;
        }
      }
      insert(table,insertHere,0xffe00000);
      return true;
    }
    int maxTableSize;
    this.table=table=new int[maxTableSize=this.maxTableSize];
    this.size=1;
    table[0]=0xffe00000;
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  boolean addToTable(int val,int hash){
    int[] table;
    if((table=this.table)!=null){
      int tableLength;
      int insertHere=-1;
      insertInTable:for(final int initialHash=hash&=tableLength=table.length - 1;;){
        int tableVal;
        switch(tableVal=table[hash]){
          case 0:
            if(insertHere == -1){
              insertHere=hash;
            }
            break insertInTable;
          case 0x7fe00000:
            insertHere=hash;
            break;
          default:
            if(tableVal==val){
              //already contains
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
    int maxTableSize;
    this.table=table=new int[maxTableSize=this.maxTableSize];
    this.size=1;
    table[hash & maxTableSize - 1]=val;
    this.maxTableSize=(int)(maxTableSize*loadFactor);
    return true;
  }
  boolean removePos0FromTable(){
    int[] table;
    int tableVal;
    if((tableVal=(table=this.table)[0]) != 0){
      int tableLength=table.length;
      int hash=0;
      do{
        if(tableVal==0xffe00000){
          table[hash]=0x7fe00000;
          return true;
        }
      }while(++hash != tableLength && (tableVal = table[hash]) != 0);
    }
    return false;
  }
  boolean removeNaNFromTable(){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(0x7fc00000 ^ 0x7fc00000 >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == 0x7fc00000){
          table[hash]=0x7fe00000;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  boolean removeTrueFromTable(){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(TypeUtil.FLT_TRUE_BITS ^ TypeUtil.FLT_TRUE_BITS >>> 16) & (tableLength=table.length - 1)]) != 0){ 
      int hash=initialHash;
      do{
        if(tableVal == TypeUtil.FLT_TRUE_BITS){
          table[hash]=0x7fe00000;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  boolean removeFromTable(int val){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(val ^ val >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          table[hash]=0x7fe00000;
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsPos0(){
    int[] table;
    int tableVal;
    if((tableVal=(table=this.table)[0]) != 0){
      int tableLength=table.length;
      int hash=0;
      do{
        if(tableVal==0xffe00000){
          return true;
        }
      }while(++hash != tableLength && (tableVal = table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsNaN(){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(0x7fc00000 ^ 0x7fc00000 >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == 0x7fc00000){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsTrue(){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(TypeUtil.FLT_TRUE_BITS ^ TypeUtil.FLT_TRUE_BITS >>> 16) & (tableLength=table.length - 1)]) != 0){ 
      int hash=initialHash;
      do{
        if(tableVal == TypeUtil.FLT_TRUE_BITS){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContains(int val){
    int[] table;
    int tableVal;
    int tableLength,initialHash;
    if((tableVal=(table=this.table)[initialHash=(val ^ val >>> 16) & (tableLength=table.length - 1)]) != 0){
      int hash=initialHash;
      do{
        if(tableVal == val){
          return true;
        }
      }while((hash=(hash + 1) & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  void forEachHelper(int size,FloatConsumer action){
    int[] table;
    for(int i=(table=this.table).length;;){
        int tableVal;
        switch(tableVal=table[--i]){
        case 0:
        case 0x7fe00000:
            continue;
        case 0xffe00000:
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
  boolean uncheckedRemoveIf(int size,FloatPredicate filter){
    int newSize=0;
    int[] table;
    for(int numLeft=size,i=(table=this.table).length;;){
        int tableVal;
        float v;
        switch(tableVal=table[--i]){
        case 0:
        case 0x7fe00000:
            continue;
        case 0xffe00000:
            v=0;
            break;
        default:
            v=Float.intBitsToFloat(tableVal);
        }
        if(filter.test(v)){
            table[i]=0x7fe00000;
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
      int[] table;
      if((table=this.table) != null){
          this.maxTableSize=(int)(table.length * loadFactor);
      }
  }
  private void insert(int[] table,int hash,int val){
    int size;
    if((size=++this.size) >= maxTableSize){
        maxTableSize=(int)((hash=table.length << 1) * loadFactor);
        int[] newTable;
        this.table=newTable=new int[hash];
        if(size!=1){
          for(int i=0;;++i){
            int tableVal;
            switch(tableVal=table[i]){
            case 0:
            case 0x7fe00000:
              continue;
            default:
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
  private String massiveToString(int size){
    byte[] buffer;
    ToStringUtil.OmniStringBuilderByte builder;
    builder=new ToStringUtil.OmniStringBuilderByte(1,new byte[OmniArray.MAX_ARR_SIZE]);
    int[] table;
    for(int i=(table=this.table).length;;){
      int tableVal;
      float f;
      switch(tableVal=table[--i]){
      case 0:
      case 0x7fe00000:
        continue;
      case 0xffe00000:
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
    (buffer=builder.buffer)[0]=(byte)'[';
    return new String(buffer,0,builder.size,ToStringUtil.IOS8859CharSet);
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
      case 0:
      case 0x7fe00000:
        continue;
      case 0xffe00000:
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
  private static class Itr
  extends AbstractFloatItr{
      private final FloatOpenAddressHashSet root;
      private int offset;
      Itr(Itr itr){
        this.root=itr.root;
        this.offset=itr.offset;
      }
      Itr(FloatOpenAddressHashSet root){
          this.root=root;
          if(root.size != 0){
              final int[] table;
              for(int offset=(table=root.table).length;;){
                  switch(table[--offset]){
                  default:
                      this.offset=offset;
                      return;
                  case 0:
                  case 0x7fe00000:
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override
      public Object clone(){
        return new Itr(this);
      }
      @Override
      public boolean hasNext(){
          return offset != -1;
      }
      @Override public void forEachRemaining(FloatConsumer action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        int offset;
        if((offset=this.offset)!=-1){
          uncheckedForEachRemaining(offset,action::accept);
        }
      }
      @Override
      public float nextFloat(){
          int offset;
          int[] table;
          var ret=(table=root.table)[offset=this.offset];
          setOffset:for(;;){
            if(--offset == -1){
              break setOffset;
            }
            switch(table[offset]){
              default:
                break setOffset;
              case 0x7fe00000:
              case 0:
            }
          }
          this.offset=offset;
          if(ret!=0xffe00000){
            return Float.intBitsToFloat(ret);
          }
          return 0;
      }
      @Override
      public void remove(){
          FloatOpenAddressHashSet root;
          --(root=this.root).size;
          var table=root.table;
          for(int offset=this.offset;;){
            switch(table[++offset]){
              default:
                table[offset]=0x7fe00000;
                return;
              case 0:
              case 0x7fe00000:
            }
          }
      }
      private void uncheckedForEachRemaining(int offset,FloatConsumer action){
        int[] table;
        int tableVal;
        action.accept((tableVal=(table=root.table)[offset])==0xffe00000?((float)0):Float.intBitsToFloat(tableVal));
        while(--offset!=-1){    
          switch(tableVal=table[offset]){
            case 0xffe00000:
                action.accept(0.0f);
                break;
            default:
                action.accept(Float.intBitsToFloat(tableVal));
            case 0:
            case 0x7fe00000:
          }
        }
        this.offset=-1;
      }
  }
  public static class Checked extends FloatOpenAddressHashSet{
    transient int modCount;
    public Checked(){
      super();
    }
    public Checked(FloatOpenAddressHashSet that){
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
    @Override public void clear(){
      if(this.size != 0){
          ++this.modCount;
          this.size=0;
          int[] table;
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
    @Override public OmniIterator.OfFloat iterator(){
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
    @Override boolean addToTable(int val,int hash){
        if(super.addToTable(val,hash)){
            ++this.modCount;
            return true;
        }
        return false;
    }
    @Override boolean removeFromTable(int val){
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
    @Override void forEachHelper(int size,FloatConsumer action){
        int modCount=this.modCount;
        try{
            super.forEachHelper(size,action);
        }finally{
            CheckedCollection.checkModCount(modCount,this.modCount);
        }
    }
    @Override boolean uncheckedRemoveIf(int size,FloatPredicate filter){
      int[] tableIndicesRemoved=null;
      int numRemovedFromTable=0;
      final int modCount=this.modCount;
      int[] table;
      try{
          for(int numLeft=size,i=(table=this.table).length;;){
              float v;
              int tableVal;
              switch(tableVal=table[--i]){
              case 0:
              case 0x7fe00000:
                  continue;
              case 0xffe00000:
                  v=0;
                  break;
              default:
                  v=Float.intBitsToFloat(tableVal);
              }
              if(filter.test(v)){
                  (tableIndicesRemoved=new int[numLeft])[0]=i;
                  outer:for(;;){
                    if(--numLeft==0){
                      break;
                    }
                    for(;;){
                      switch(tableVal=table[--i]){
                        case 0:
                        case 0x7fe00000:
                          continue;
                        case 0xffe00000:
                          v=0;
                          break;
                        default:
                          v=Float.intBitsToFloat(tableVal);
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
            table[tableIndicesRemoved[numRemovedFromTable]]=0x7fe00000;
          }while(--numRemovedFromTable!=-1);
          return true;
      }
      return false;
    }
    private static class Itr
    extends AbstractFloatItr{
      private final Checked root;
      private int offset;
      private int modCount;
      private int lastRet;
      Itr(Itr itr){
        this.root=itr.root;
        this.modCount=itr.modCount;
        this.offset=itr.offset;
        this.lastRet=itr.lastRet;
      }
      Itr(Checked root){
          this.root=root;
          this.modCount=root.modCount;
          this.lastRet=-1;
          if(root.size != 0){
              final int[] table;
              for(int i=(table=root.table).length;;){
                  switch(table[--i]){
                  default:
                      this.offset=i;
                      return;
                  case 0:
                  case 0x7fe00000:
                  }
              }
          }else{
              this.offset=-1;
          }
      }
      @Override public Object clone(){
        return new Itr(this);
      }
      @Override public void forEachRemaining(FloatConsumer action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          uncheckedForEachRemaining(expectedOffset,action);
        }
      }
      @Override public void forEachRemaining(Consumer<? super Float> action){
        final int expectedOffset;
        if((expectedOffset=this.offset)!=-1){
          uncheckedForEachRemaining(expectedOffset,action::accept);
        }
      }
      @Override
      public boolean hasNext(){
          return this.offset != -1;
      }
      @Override
      public float nextFloat(){
          Checked root;
          CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
          int offset;
          if((offset=this.offset) != -1){
              this.lastRet=offset;
              int table[];
              var ret=(table=root.table)[offset];
              setOffset:for(;;){
                  if(--offset == -1){
                      break setOffset;
                  }
                  switch(table[offset]){
                  default:
                      break setOffset;
                  case 0x7fe00000:
                  case 0:
                  }
              }
              this.offset=offset;
              if(ret != 0xffe00000){
                  return Float.intBitsToFloat(ret);
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
              root.table[lastRet]=0x7fe00000;
              this.lastRet=-1;
              return;
          }
          throw new IllegalStateException();
      }
      private void uncheckedForEachRemaining(final int expectedOffset,FloatConsumer action){
          var root=this.root;
          int modCount=this.modCount;
          int lastRet;
          int offset;
          try{
              int[] table;
              int tableVal;
              action.accept((tableVal=(table=root.table)[offset=lastRet=expectedOffset])==0xffe00000?((float)0):Float.intBitsToFloat(tableVal));
              while(--offset!=-1){
                  switch(tableVal=table[offset]){
                  case 0xffe00000:
                      action.accept(0.0f);
                      lastRet=offset;
                      break;
                  default:
                      action.accept(Float.intBitsToFloat(tableVal));
                      lastRet=offset;
                  case 0:
                  case 0x7fe00000:
                  }
              }
          }finally{
              if(modCount!=root.modCount || expectedOffset!=this.offset){
                throw new ConcurrentModificationException("modCount{expected="+modCount+",actual="+root.modCount+"},offset{expected="+expectedOffset+";actual="+this.offset+"}");
              }
          }
          this.offset=-1;
          this.lastRet=lastRet;
      }
    }
  }
}
