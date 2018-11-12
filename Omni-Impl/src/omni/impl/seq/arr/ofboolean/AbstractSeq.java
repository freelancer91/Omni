package omni.impl.seq.arr.ofboolean;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractBooleanList;
import omni.util.ArrCopy;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractBooleanList{
  transient boolean[] arr;
  // TODO redo toString implementation
  static void eraseIndexHelper(boolean[] arr,int index,int newSize){
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static int forwardHashCode(boolean[] arr,int offset,int bound){
    int hash=31+Boolean.hashCode(arr[offset]);
    while(++offset!=bound){
      hash=hash*31+Boolean.hashCode(arr[offset]);
    }
    return hash;
  }
  static void forwardToString(boolean[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
  }
  static int reverseHashCode(boolean[] arr,int offset,int bound){
    int hash=31+Boolean.hashCode(arr[--bound]);
    while(bound!=offset){
      hash=hash*31+Boolean.hashCode(arr[--bound]);
    }
    return hash;
  }
  static int reverseToString(boolean[] arr,int begin,int end,char[] buffer,int bufferOffset){
    for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',--end){
      bufferOffset=ToStringUtil.getStringBoolean(arr[end],buffer,bufferOffset);
      if(end==begin){ return bufferOffset; }
    }
  }
  static void reverseToString(boolean[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
  }
  static ToStringUtil.OmniStringBuilder reverseToString(boolean[] arr,int begin,int end,
      ToStringUtil.OmniStringBuilder builder){
    for(;;--end){
      builder.uncheckedAppend(arr[end]);
      if(end==begin){ return builder; }
      builder.uncheckedAppendCommaAndSpace();
    }
  }
  static boolean uncheckedAnyMatches(boolean[] arr,int offset,int bound,boolean val){
    while(arr[offset]!=val){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static void uncheckedForwardForEachInRange(boolean[] arr,int offset,int bound,BooleanConsumer action){
    do{
      action.accept(arr[offset]);
    }while(++offset!=bound);
  }
  static int uncheckedIndexOfMatch(boolean[] arr,int offset,int bound,boolean val){
    int index=offset;
    do{
      if(arr[index]==val){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedLastIndexOfMatch(boolean[] arr,int offset,int bound,boolean val){
    do{
      if(arr[--bound]==val){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static void uncheckedReplaceAll(boolean[] arr,int offset,int bound,BooleanPredicate operator){
    do{
      arr[offset]=operator.test(arr[offset]);
    }while(++offset!=bound);
  }
  static void uncheckedReverseArr(boolean[] arr,int begin,int end){
    do{
      final var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }while(++begin<--end);
  }
  static void uncheckedReverseForEachInRange(boolean[] arr,int offset,int bound,BooleanConsumer action){
    do{
      action.accept(arr[--bound]);
    }while(bound!=offset);
  }
  static void uncheckedReverseSort(boolean[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(boolean[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(boolean[] arr,int begin,int end,BooleanComparator sorter){
    // TODO
  }
  private static int forwardToString(boolean[] arr,int begin,int end,char[] buffer,int bufferOffset){
    for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',++begin){
      bufferOffset=ToStringUtil.getStringBoolean(arr[begin],buffer,bufferOffset);
      if(begin==end){ return bufferOffset; }
    }
  }
  private static ToStringUtil.OmniStringBuilder forwardToString(boolean[] arr,int begin,int end,
      ToStringUtil.OmniStringBuilder builder){
    for(;;++begin){
      builder.uncheckedAppend(arr[begin]);
      if(begin==end){ return builder; }
      builder.uncheckedAppendCommaAndSpace();
    }
  }
  private static int pullSurvivorsDown(boolean[] arr,int srcOffset,int srcBound,boolean v){
    for(int dstOffset=srcOffset;;arr[dstOffset++]=v){
      do{
        if(++srcOffset==srcBound){ return dstOffset; }
      }while(arr[srcOffset]^v);
    }
  }
  private static int pullSurvivorsDown(boolean[] arr,int dstOffset,int srcOffset,int srcBound,boolean v){
    for(;;){
      arr[dstOffset++]=v;
      do{
        if(++srcOffset==srcBound){ return dstOffset; }
      }while(arr[srcOffset]^v);
    }
  }
  private AbstractSeq(){
    super();
    arr=OmniArray.OfBoolean.DEFAULT_ARR;
  }
  private AbstractSeq(int capacity){
    super();
    switch(capacity){
      default:
        arr=new boolean[capacity];
        return;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        arr=OmniArray.OfBoolean.DEFAULT_ARR;
      case 0:
    }
  }
  private AbstractSeq(int size,boolean[] arr){
    super(size);
    this.arr=arr;
  }
  public boolean contains(boolean val){
    final int size;
    return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,val);
  }
  public boolean contains(double val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return false;
      }
      return uncheckedAnyMatches(arr,0,size,v);
    }
    return false;
  }
  public boolean contains(float val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      return uncheckedAnyMatches(arr,0,size,v);
    }
    return false;
  }
  public boolean contains(int val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(val){
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedAnyMatches(arr,0,size,v);
    }
    return false;
  }
  public boolean contains(long val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return false;
      }
      return uncheckedAnyMatches(arr,0,size,v);
    }
    return false;
  }
  public boolean contains(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Boolean&&uncheckedAnyMatches(arr,0,size,(boolean)val);
  }
  public void forEach(BooleanConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  public void forEach(Consumer<? super Boolean> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  @Override public boolean getBoolean(int index){
    return arr[index];
  }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){ return uncheckedHashCode(size); }
    return 1;
  }
  public int indexOf(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedIndexOfMatch(size,val); }
    return -1;
  }
  public int indexOf(double val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return -1;
      }
      return uncheckedIndexOfMatch(size,v);
    }
    return -1;
  }
  public int indexOf(float val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      return uncheckedIndexOfMatch(size,v);
    }
    return -1;
  }
  public int indexOf(int val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(val){
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedIndexOfMatch(size,v);
    }
    return -1;
  }
  public int indexOf(long val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return -1;
      }
      return uncheckedIndexOfMatch(size,v);
    }
    return -1;
  }
  public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Boolean){ return uncheckedIndexOfMatch(size,(boolean)val); }
    return -1;
  }
  public Boolean peek(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return null;
  }
  public boolean peekBoolean(){
    final int size;
    return (size=this.size)!=0&&arr[size-1];
  }
  public byte peekByte(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(arr[size-1]); }
    return Byte.MIN_VALUE;
  }
  public char peekChar(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToChar(arr[size-1]); }
    return Character.MIN_VALUE;
  }
  public double peekDouble(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToDouble(arr[size-1]); }
    return Double.NaN;
  }
  public float peekFloat(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToFloat(arr[size-1]); }
    return Float.NaN;
  }
  public int peekInt(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(arr[size-1]); }
    return Integer.MIN_VALUE;
  }
  public long peekLong(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToLong(arr[size-1]); }
    return Long.MIN_VALUE;
  }
  public short peekShort(){
    final int size;
    if((size=this.size)!=0){ return TypeUtil.castToByte(arr[size-1]); }
    return Short.MIN_VALUE;
  }
  public void push(boolean val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(val,size);
    }else{
      uncheckedInit(val);
    }
  }
  public void put(int index,boolean val){
    arr[index]=val;
  }
  public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Boolean&&uncheckedRemoveFirstMatch(size,(boolean)val);
  }
  public boolean removeIf(BooleanPredicate filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
  }
  public boolean removeIf(Predicate<? super Boolean> filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
  }
  public boolean removeVal(boolean val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(double val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return false;
      }
      return uncheckedRemoveFirstMatch(size,v);
    }
    return false;
  }
  public boolean removeVal(float val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return false;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      return uncheckedRemoveFirstMatch(size,v);
    }
    return false;
  }
  public boolean removeVal(int val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(val){
        default:
          return false;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedRemoveFirstMatch(size,v);
    }
    return false;
  }
  public boolean removeVal(long val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return false;
      }
      return uncheckedRemoveFirstMatch(size,v);
    }
    return false;
  }
  public void replaceAll(BooleanPredicate operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator);
    }
  }
  public void replaceAll(UnaryOperator<Boolean> operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator::apply);
    }
  }
  public int search(boolean val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearch(size,val); }
    return -1;
  }
  public int search(double val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      final long bits;
      if((bits=Double.doubleToRawLongBits(val))==0||bits==Long.MIN_VALUE){
        v=false;
      }else if(bits==TypeUtil.DBL_TRUE_BITS){
        v=true;
      }else{
        return -1;
      }
      return uncheckedSearch(size,v);
    }
    return -1;
  }
  public int search(float val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(Float.floatToRawIntBits(val)){
        default:
          return -1;
        case 0:
        case Integer.MIN_VALUE:
          v=false;
          break;
        case TypeUtil.FLT_TRUE_BITS:
          v=true;
      }
      return uncheckedSearch(size,v);
    }
    return -1;
  }
  public int search(int val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      switch(val){
        default:
          return -1;
        case 0:
          v=false;
          break;
        case 1:
          v=true;
      }
      return uncheckedSearch(size,v);
    }
    return -1;
  }
  public int search(long val){
    final int size;
    if((size=this.size)!=0){
      final boolean v;
      if(val==0){
        v=false;
      }else if(val==1){
        v=true;
      }else{
        return -1;
      }
      return uncheckedSearch(size,v);
    }
    return -1;
  }
  public int search(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Boolean){ return uncheckedSearch(size,(boolean)val); }
    return -1;
  }
  @Override public boolean set(int index,boolean val){
    final boolean[] arr;
    final var oldVal=(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  public void sort(BooleanComparator sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter);
    }
  }
  public void sort(Comparator<? super Boolean> sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter::compare);
    }
  }
  public Boolean[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Boolean[] dst;
      uncheckedCopyInto(dst=new Boolean[size],size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
  }
  public <T> T[] toArray(IntFunction<T[]> arrConstructor){
    final int size;
    final T[] dst=arrConstructor.apply(size=this.size);
    if(size!=0){
      uncheckedCopyInto(dst,size);
    }
    return dst;
  }
  public <T> T[] toArray(T[] dst){
    final int size;
    if((size=this.size)!=0){
      uncheckedCopyInto(dst=OmniArray.uncheckedArrResize(size,dst),size);
    }else if(dst.length!=0){
      dst[0]=null;
    }
    return dst;
  }
  public boolean[] toBooleanArray(){
    final int size;
    if((size=this.size)!=0){
      final boolean[] dst;
      uncheckedCopyInto(dst=new boolean[size],size);
      return dst;
    }
    return OmniArray.OfBoolean.DEFAULT_ARR;
  }
  public byte[] toByteArray(){
    final int size;
    if((size=this.size)!=0){
      final byte[] dst;
      uncheckedCopyInto(dst=new byte[size],size);
      return dst;
    }
    return OmniArray.OfByte.DEFAULT_ARR;
  }
  public char[] toCharArray(){
    final int size;
    if((size=this.size)!=0){
      final char[] dst;
      uncheckedCopyInto(dst=new char[size],size);
      return dst;
    }
    return OmniArray.OfChar.DEFAULT_ARR;
  }
  public double[] toDoubleArray(){
    final int size;
    if((size=this.size)!=0){
      final double[] dst;
      uncheckedCopyInto(dst=new double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_ARR;
  }
  public float[] toFloatArray(){
    final int size;
    if((size=this.size)!=0){
      final float[] dst;
      uncheckedCopyInto(dst=new float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_ARR;
  }
  public int[] toIntArray(){
    final int size;
    if((size=this.size)!=0){
      final int[] dst;
      uncheckedCopyInto(dst=new int[size],size);
      return dst;
    }
    return OmniArray.OfInt.DEFAULT_ARR;
  }
  public long[] toLongArray(){
    final int size;
    if((size=this.size)!=0){
      final long[] dst;
      uncheckedCopyInto(dst=new long[size],size);
      return dst;
    }
    return OmniArray.OfLong.DEFAULT_ARR;
  }
  public short[] toShortArray(){
    final int size;
    if((size=this.size)!=0){
      final short[] dst;
      uncheckedCopyInto(dst=new short[size],size);
      return dst;
    }
    return OmniArray.OfShort.DEFAULT_ARR;
  }
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  // abstract ToStringUtil.OmniStringBuilder uncheckedToString(int
  // size,ToStringUtil.OmniStringBuilder builder);
  // abstract int uncheckedToString(int size,char[] buffer,int bufferOffset);
  @Override protected int uncheckedLastIndexOfMatch(int size,boolean val){
    final var arr=this.arr;
    do{
      if(arr[--size]==val){ return size; }
    }while(size!=0);
    return -1;
  }
  abstract void uncheckedCopyInto(boolean[] dst,int length);
  abstract void uncheckedCopyInto(Boolean[] dst,int length);
  abstract void uncheckedCopyInto(byte[] dst,int length);
  abstract void uncheckedCopyInto(char[] dst,int length);
  abstract void uncheckedCopyInto(double[] dst,int length);
  abstract void uncheckedCopyInto(float[] dst,int length);
  abstract void uncheckedCopyInto(int[] dst,int length);
  abstract void uncheckedCopyInto(long[] dst,int length);
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedCopyInto(short[] dst,int length);
  abstract void uncheckedForEach(int size,BooleanConsumer action);
  abstract int uncheckedHashCode(int size);
  boolean uncheckedPopBoolean(int newSize){
    size=newSize;
    return arr[newSize];
  }
  abstract boolean uncheckedRemoveFirstMatch(int size,boolean val);
  abstract boolean uncheckedRemoveIf(int size,BooleanPredicate filter);
  abstract void uncheckedReplaceAll(int size,BooleanPredicate operator);
  abstract void uncheckedSort(int size,BooleanComparator sorter);
  abstract void uncheckedToString(int size,StringBuilder builder);
  private int finalizeSubListBatchRemove(boolean[] arr,int newBound,int oldBound){
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private boolean[] growInsert(boolean[] arr,int index,int size){
    if(arr.length==size){
      ArrCopy.semicheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(boolean val,int size){
    boolean[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new boolean[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private int uncheckedIndexOfMatch(int size,boolean val){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==val){ return index; }
    }while(++index!=size);
    return -1;
  }
  private void uncheckedInit(boolean val){
    boolean[] arr;
    if((arr=this.arr)==OmniArray.OfBoolean.DEFAULT_ARR){
      this.arr=arr=new boolean[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }else if(arr==null){
      this.arr=arr=new boolean[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(int index,boolean val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      boolean[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private int uncheckedSearch(int size,boolean val){
    final var arr=this.arr;
    int index;
    for(index=size-1;arr[index]!=val;--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  static abstract class Checked extends AbstractSeq{
    transient int modCount;
    Checked(){
      super();
    }
    Checked(int capacity){
      super(capacity);
    }
    Checked(int size,boolean[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(Boolean val){
      ++modCount;
      super.push(val);
      return true;
    }
    @Override public void add(int index,boolean val){
      CheckedCollection.checkLo(index);
      final int size;
      CheckedCollection.checkWriteHi(index,size=this.size);
      ++modCount;
      if(size!=0){
        super.uncheckedInsert(index,val,size);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override public void clear(){
      if(size!=0){
        ++modCount;
        size=0;
      }
    }
    @Override public boolean getBoolean(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      return super.getBoolean(index);
    }
    public boolean popBoolean(){
      final int size;
      if((size=this.size)!=0){
        ++modCount;
        return super.uncheckedPopBoolean(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override public void push(boolean val){
      ++modCount;
      super.push(val);
    }
    @Override public void put(int index,boolean val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      super.put(index,val);
    }
    @Override public boolean removeBooleanAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final boolean[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override public boolean set(int index,boolean val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      return super.set(index,val);
    }
    @Override public <T> T[] toArray(IntFunction<T[]> arrConstructor){
      return super.toArray(size->{
        final int expectedModCount=modCount;
        try{
          return arrConstructor.apply(size);
        }finally{
          CheckedCollection.checkModCount(expectedModCount,modCount);
        }
      });
    }
    @Override boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
      final int expectedModCount=modCount;
      final var arr=this.arr;
      int srcOffset=0;
      try{
        boolean v;
        if(filter.test(v=arr[0])){
          while(++srcOffset!=size){
            if(arr[srcOffset]^v){
              if(filter.test(!v)){
                break;
              }else{
                CheckedCollection.checkModCount(expectedModCount,modCount);
                this.size=pullSurvivorsDown(arr,0,srcOffset,size,v);
                return true;
              }
            }
          }
          CheckedCollection.checkModCount(expectedModCount,modCount);
          this.size=0;
          return true;
        }else{
          while(++srcOffset!=size){
            if(arr[srcOffset]^v){
              if(filter.test(!v)){
                CheckedCollection.checkModCount(expectedModCount,modCount);
                this.size=pullSurvivorsDown(arr,srcOffset,size,v);
                return true;
              }
            }
          }
        }
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      CheckedCollection.checkModCount(expectedModCount,modCount);
      return false;
    }
    @Override void uncheckedReplaceAll(int size,BooleanPredicate operator){
      final int expectedModCount=modCount;
      try{
        uncheckedReplaceAll(arr,0,size,operator);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
    @Override void uncheckedSort(int size,BooleanComparator sorter){
      final int expectedModCount=modCount;
      try{
        uncheckedSort(arr,0,size-1,sorter);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
    static abstract class AbstractBidirectionalItr{
      transient int lastRet=-1;
      transient final Checked root;
      transient int cursor;
      transient int modCount;
      AbstractBidirectionalItr(Checked root){
        this.root=root;
        modCount=root.modCount;
      }
      AbstractBidirectionalItr(Checked root,int cursor){
        this.root=root;
        modCount=root.modCount;
        this.cursor=cursor;
      }
      public void add(boolean val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0){
          ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
        }else{
          ((AbstractSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        lastRet=-1;
      }
    }
    static abstract class AbstractSubList extends AbstractBooleanList{
      transient final Checked root;
      transient final AbstractSubList parent;
      transient int modCount;
      transient final int rootOffset;
      int getBound(){
        return size+rootOffset;
      }
      @Override protected int uncheckedLastIndexOfMatch(int size,boolean val){
        Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int rootOffset;
        return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      AbstractSubList(Checked root,AbstractSubList parent,int rootOffset,int size,int modCount){
        super(size);
        this.rootOffset=rootOffset;
        this.root=root;
        this.parent=parent;
        this.modCount=modCount;
      }
      AbstractSubList(Checked root,int rootOffset,int size){
        super(size);
        this.rootOffset=rootOffset;
        this.root=root;
        parent=null;
        modCount=root.modCount;
      }
      static void bubbleUpDecrementSize(AbstractSubList parent){
        while(parent!=null){
          --parent.size;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove){
        while(parent!=null){
          parent.size-=numToRemove;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      private static void bubbleUpIncrementSize(AbstractSubList parent){
        while(parent!=null){
          ++parent.size;
          ++parent.modCount;
          parent=parent.parent;
        }
      }
      public boolean add(boolean val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0){
          ((AbstractSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }else{
          ((AbstractSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      @Override public void add(int index,boolean val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0){
          ((AbstractSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }else{
          ((AbstractSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
      }
      @Override public void clear(){
        final var root=this.root;
        final int modCount=this.modCount;
        final int size;
        if((size=this.size)!=0){
          CheckedCollection.checkModCount(modCount,root.modCount);
          this.modCount=modCount+1;
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          root.size=newRootSize=root.size-size;
          final boolean[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }else{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      @Override public boolean getBoolean(int index){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        return root.arr[index+rootOffset];
      }
      @Override public int hashCode(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        final int size;
        if((size=this.size)!=0){
          final int rootOffset;
          return forwardHashCode(root.arr,rootOffset=this.rootOffset,rootOffset+size);
        }
        return 1;
      }
      @Override public boolean isEmpty(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return size==0;
      }
      @Override public boolean removeBooleanAt(int index){
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final boolean[] arr;
        final var removed=(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.modCount=++modCount;
        root.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      public boolean removeIf(BooleanPredicate filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      public boolean removeIf(Predicate<? super Boolean> filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter::test); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      @Override public boolean set(int index,boolean val){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        final boolean[] arr;
        final var oldVal=(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      @Override public int size(){
        CheckedCollection.checkModCount(modCount,root.modCount);
        return size;
      }
      @Override public String toString(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int size;
        if((size=this.size)!=0){
          final char[] buffer;
          final var arr=root.arr;
          if(size>OmniArray.MAX_ARR_SIZE/7){
            // lets set the default scenario to be 95% false
            (buffer=new char[(int)(size*7*.95f)])[0]='[';
            return forwardToString(arr,0,size-1,new ToStringUtil.OmniStringBuilder(1,buffer)).uncheckedAppend(']')
                .toString();
          }else{
            (buffer=new char[size*7])[0]='[';
            buffer[size=forwardToString(arr,0,size-1,buffer,1)]=']';
            return new String(buffer,0,size+1);
          }
        }
        return "[]";
      }
      private boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
        final Checked root;
        int expectedModCount=modCount;
        final var arr=(root=this.root).arr;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        int dstOffset;
        try{
          boolean v;
          if(filter.test(v=arr[srcOffset])){
            dstOffset=srcOffset;
            for(;;){
              if(++srcOffset==srcBound){
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
                break;
              }
              if(arr[srcOffset]^v){
                if(filter.test(!v)){
                  CheckedCollection.checkModCount(expectedModCount,root.modCount);
                  break;
                }else{
                  CheckedCollection.checkModCount(expectedModCount,root.modCount);
                  dstOffset=pullSurvivorsDown(arr,dstOffset,srcOffset,srcBound,v);
                  break;
                }
              }
            }
          }else{
            do{
              if(++srcOffset==srcBound){
                CheckedCollection.checkModCount(expectedModCount,root.modCount);
                return false;
              }
            }while(arr[srcOffset]==v);
            CheckedCollection.checkModCount(expectedModCount,root.modCount);
            dstOffset=pullSurvivorsDown(arr,srcOffset,srcBound,v);
          }
        }catch(final RuntimeException e){
          throw CheckedCollection.checkModCount(expectedModCount,root.modCount,e);
        }
        root.modCount=++expectedModCount;
        modCount=expectedModCount;
        this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
        bubbleUpDecrementSize(parent,size);
        return true;
      }
      static abstract class AbstractBidirectionalItr{
        transient final AbstractSubList parent;
        transient int cursor;
        transient int lastRet=-1;
        transient int modCount;
        AbstractBidirectionalItr(AbstractSubList parent,int modCount){
          this.parent=parent;
          cursor=parent.rootOffset;
          this.modCount=modCount;
        }
        AbstractBidirectionalItr(AbstractSubList parent,int modCount,int cursor){
          this.parent=parent;
          this.cursor=cursor;
          this.modCount=modCount;
        }
        public void add(boolean val){
          final AbstractSubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0){
            ((AbstractSeq)root).uncheckedInsert(cursor,val,rootSize);
          }else{
            ((AbstractSeq)root).uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          parent.modCount=++modCount;
          root.modCount=modCount;
          ++parent.size;
          this.cursor=cursor+1;
          lastRet=-1;
        }
      }
    }
  }
  static abstract class Unchecked extends AbstractSeq{
    private static int pullSurvivorsDown(boolean[] arr,int srcBegin,int srcEnd,BooleanPredicate filter){
      int dstOffset=srcBegin;
      while(srcBegin!=srcEnd){
        final boolean v;
        if(!filter.test(v=arr[++srcBegin])){
          arr[dstOffset++]=v;
        }
      }
      return dstOffset;
    }
    Unchecked(){
      super();
    }
    Unchecked(int capacity){
      super(capacity);
    }
    Unchecked(int size,boolean[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      super.push(val);
      return true;
    }
    public boolean add(Boolean val){
      super.push(val);
      return true;
    }
    @Override public void add(int index,boolean val){
      final int size;
      if((size=this.size)!=0){
        super.uncheckedInsert(index,val,size);
      }else{
        super.uncheckedInit(val);
      }
    }
    @Override public boolean removeBooleanAt(int index){
      final boolean[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
      final var arr=this.arr;
      int srcOffset=0;
      do{
        if(filter.test(arr[srcOffset])){
          this.size=srcOffset=pullSurvivorsDown(arr,srcOffset,size-1,filter);
          return true;
        }
      }while(++srcOffset!=size);
      return false;
    }
    @Override void uncheckedReplaceAll(int size,BooleanPredicate operator){
      uncheckedReplaceAll(arr,0,size,operator);
    }
    @Override void uncheckedSort(int size,BooleanComparator sorter){
      uncheckedSort(arr,0,size-1,sorter);
    }
    static abstract class AbstractBidirectionalItr{
      transient final Unchecked root;
      transient int cursor;
      transient int lastRet;
      AbstractBidirectionalItr(Unchecked root){
        this.root=root;
      }
      AbstractBidirectionalItr(Unchecked root,int cursor){
        this.root=root;
        this.cursor=cursor;
      }
      public void add(boolean val){
        final AbstractSeq root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(cursor,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
    }
    static abstract class AbstractSubList extends AbstractBooleanList{
      @Override protected int uncheckedLastIndexOfMatch(int size,boolean val){
        int rootOffset;
        return AbstractSeq.uncheckedLastIndexOfMatch(root.arr,rootOffset=this.rootOffset,rootOffset+size,val);
      }
      transient final Unchecked root;
      transient final AbstractSubList parent;
      static void bubbleUpDecrementSize(AbstractSubList parent){
        while(parent!=null){
          --parent.size;
          parent=parent.parent;
        }
      }
      private static void bubbleUpDecrementSize(AbstractSubList parent,int numToRemove){
        while(parent!=null){
          parent.size-=numToRemove;
          parent=parent.parent;
        }
      }
      private static void bubbleUpIncrementSize(AbstractSubList parent){
        while(parent!=null){
          ++parent.size;
          parent=parent.parent;
        }
      }
      transient final int rootOffset;
      int getBound(){
        return rootOffset+size;
      }
      AbstractSubList(Unchecked root,AbstractSubList parent,int rootOffset,int size){
        super(size);
        this.rootOffset=rootOffset;
        this.root=root;
        this.parent=parent;
      }
      AbstractSubList(Unchecked root,int rootOffset,int size){
        super(size);
        this.rootOffset=rootOffset;
        this.root=root;
        parent=null;
      }
      public boolean add(boolean val){
        final AbstractSeq root;
        final int rootSize;
        final int size=this.size;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(size+rootOffset,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.size=size+1;
        bubbleUpIncrementSize(parent);
        return true;
      }
      @Override public void add(int index,boolean val){
        final AbstractSeq root;
        final int rootSize;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(index+rootOffset,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        ++size;
        bubbleUpIncrementSize(parent);
      }
      @Override public void clear(){
        int size;
        if((size=this.size)!=0){
          this.size=0;
          bubbleUpDecrementSize(parent,size);
          final int newRootSize;
          final Unchecked root;
          (root=this.root).size=newRootSize=root.size-size;
          final boolean[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      @Override public boolean getBoolean(int index){
        return root.arr[index+rootOffset];
      }
      public void put(int index,boolean val){
        root.arr[index+rootOffset]=val;
      }
      @Override public boolean removeBooleanAt(int index){
        final Unchecked root;
        final boolean[] arr;
        final var removed=(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      public boolean removeIf(BooleanPredicate filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
      }
      public boolean removeIf(Predicate<? super Boolean> filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
      }
      @Override public boolean set(int index,boolean val){
        final boolean[] arr;
        final var oldVal=(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      private boolean uncheckedRemoveIf(int size,BooleanPredicate filter){
        final AbstractSeq root;
        final var arr=(root=this.root).arr;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        do{
          if(filter.test(arr[srcOffset])){
            this.size=size-(size=root.finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),
                srcBound));
            bubbleUpDecrementSize(parent,size);
            return true;
          }
        }while(++srcOffset!=srcBound);
        return false;
      }
      static abstract class AbstractBidirectionalItr{
        transient final AbstractSubList parent;
        transient int cursor;
        transient int lastRet;
        AbstractBidirectionalItr(AbstractSubList parent){
          this.parent=parent;
          cursor=parent.rootOffset;
        }
        AbstractBidirectionalItr(AbstractSubList parent,int cursor){
          this.parent=parent;
          this.cursor=cursor;
        }
        public void add(boolean val){
          final AbstractSubList parent;
          final AbstractSeq root;
          final int rootSize,cursor=this.cursor;
          if((rootSize=(root=(parent=this.parent).root).size)!=0){
            root.uncheckedInsert(cursor,val,rootSize);
          }else{
            root.uncheckedInit(val);
          }
          bubbleUpIncrementSize(parent.parent);
          ++parent.size;
          this.cursor=cursor+1;
        }
      }
    }
  }
}
