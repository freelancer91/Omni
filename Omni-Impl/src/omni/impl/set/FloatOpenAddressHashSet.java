package omni.impl.set;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniSet;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.util.OmniArray;
import omni.util.TypeUtil;

public class FloatOpenAddressHashSet extends AbstractOpenAddressHashSet implements OmniSet.OfFloat{
  int[] table;
  FloatOpenAddressHashSet(){
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
    for(int hash=(val^(val>>>16)) & (tableLength=table.length-1);;){
      if(table[hash]==EMPTY){
        table[hash]=val;
        return;
      }
      hash=(hash+1)&tableLength;
    }
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
  private boolean tableContainsPos0() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((POS0)^(POS0>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == POS0){
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean removePos0FromTable() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((POS0)^(POS0>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == POS0){
                table[hash]=DELETED;
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsNaN() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((0x7fc00000)^(0x7fc00000>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == 0x7fc00000){
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean removeNaNFromTable() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((0x7fc00000)^(0x7fc00000>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == 0x7fc00000){
                table[hash]=DELETED;
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContainsTrue() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((TypeUtil.FLT_TRUE_BITS)^(TypeUtil.FLT_TRUE_BITS>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == TypeUtil.FLT_TRUE_BITS){
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean removeTrueFromTable() {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((TypeUtil.FLT_TRUE_BITS)^(TypeUtil.FLT_TRUE_BITS>>>16)) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == TypeUtil.FLT_TRUE_BITS){
                table[hash]=DELETED;
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  private boolean tableContains(int intBits){
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((intBits^(intBits>>>16))) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == intBits){
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
    }
    return false;
  }
  
  private boolean removeFromTable(int intBits) {
    int[] table;
    int tableLength,initialHash;
    int tableVal;
    if((tableVal=(table=this.table)[initialHash=((intBits^(intBits>>>16))) & (tableLength=table.length - 1)]) != 0){
        int hash=initialHash;
        do{
            if(tableVal == intBits){
                table[hash]=DELETED;
                return true;
            }
        }while((hash=hash + 1 & tableLength) != initialHash && (tableVal=table[hash]) != 0);
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
              intBits=Float.floatToRawIntBits((float)intBits);
            }else if(val instanceof Long) {  
              long l;
              if((l=(long)val)==0) {
                break checkPos0;
              }else if(!TypeUtil.checkCastToFloat(l)) {
                break returnFalse;
              }else if((intBits=Float.floatToRawIntBits((float)l))==0) {
                break checkPos0;
              }
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
              intBits=Float.floatToRawIntBits((float)intBits);
            }else if(val instanceof Character) {
              if((intBits=(char)val)==0) {
                break checkPos0;
              }
              intBits=Float.floatToRawIntBits((float)intBits);
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
      if((boolean)val) {
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
      return tableContains(Float.floatToRawIntBits((float)val));
    }
    return false;
  }

  @Override public boolean contains(short val){
    if(size!=0) {
      if(val==0) {
        return tableContainsPos0();
      }
      return tableContains(Float.floatToRawIntBits((float)val));
    }
    return false;
  }

  @Override public boolean contains(int val){
    if(size!=0) {
      if(val==0) {
        return tableContainsPos0();
      }else if(TypeUtil.checkCastToFloat(val)) {
        return tableContains(Float.floatToRawIntBits((float)val));
      }
    }
    return false;
  }

  @Override public boolean contains(long val){
    if(size!=0) {
      if(val==0) {
        return tableContainsPos0();
      }else if(TypeUtil.checkCastToFloat(val)) {
        return tableContains(Float.floatToRawIntBits((float)val));
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
      if((d=(double)val)==(f=(float)d)) {
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
                intBits=Float.floatToRawIntBits((float)intBits);
              }else if(val instanceof Long) {  
                long l;
                if((l=(long)val)==0) {
                  break checkPos0;
                }else if(!TypeUtil.checkCastToFloat(l)) {
                  break returnFalse;
                }else if((intBits=Float.floatToRawIntBits((float)l))==0) {
                  break checkPos0;
                }
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
                intBits=Float.floatToRawIntBits((float)intBits);
              }else if(val instanceof Character) {
                if((intBits=(char)val)==0) {
                  break checkPos0;
                }
                intBits=Float.floatToRawIntBits((float)intBits);
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
          if((boolean)val) {
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
          }else if(removeFromTable(Float.floatToRawIntBits((float)val))) {
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
          }else if(removeFromTable(Float.floatToRawIntBits((float)val))) {
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
            if(removeFromTable(Float.floatToRawIntBits((float)val))) {
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
            if(removeFromTable(Float.floatToRawIntBits((float)val))) {
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
          if((d=(double)val)==(f=(float)d)) {
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
        dst[--size]=(double)Float.intBitsToFloat(tableVal);
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

  @Override public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException{
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

  @Override public String toString(){
    // TODO Auto-generated method stub
    return null;
  }

  @Override public int hashCode(){
    // TODO Auto-generated method stub
    return 0;
  }

  @Override public boolean add(boolean val){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean add(Float val){
    return add((float)val);
  }

  @Override public void forEach(FloatConsumer action){
    // TODO Auto-generated method stub
    
  }

  @Override public void forEach(Consumer<? super Float> action){
    // TODO Auto-generated method stub
    
  }

  @Override public boolean removeIf(FloatPredicate filter){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean removeIf(Predicate<? super Float> filter){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public omni.api.OmniIterator.OfFloat iterator(){
    // TODO Auto-generated method stub
    return null;
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
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean add(long val){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean add(short val){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean add(int val){
    // TODO Auto-generated method stub
    return false;
  }

  @Override public boolean add(float val){
    // TODO Auto-generated method stub
    return false;
  }
}
