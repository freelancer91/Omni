package omni.impl.seq.arr.offloat;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.function.FloatComparator;
import omni.function.FloatConsumer;
import omni.function.FloatPredicate;
import omni.function.FloatUnaryOperator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractFloatList;
import omni.impl.seq.arr.AbstractSubArrSeq;
import omni.util.ArrCopy;
import omni.util.BitSetUtils;
import omni.util.HashUtils;
import omni.util.OmniArray;
import omni.util.ToStringUtil;
import omni.util.TypeUtil;
abstract class AbstractSeq extends AbstractFloatList{
  transient float[] arr;
  // TODO redo toString implementation
  static void eraseIndexHelper(float[] arr,int index,int newSize){
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static int forwardHashCode(float[] arr,int offset,int bound){
    int hash=31+HashUtils.hashFloat(arr[offset]);
    while(++offset!=bound){
      hash=hash*31+HashUtils.hashFloat(arr[offset]);
    }
    return hash;
  }
  static int forwardToString(float[] arr,int begin,int end,char[] buffer,int bufferOffset){
    for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',++begin){
      bufferOffset=ToStringUtil.getStringFloat(arr[begin],buffer,bufferOffset);
      if(begin==end){ return bufferOffset; }
    }
  }
  static void forwardToString(float[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
  }
  static void forwardToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder){
    for(;;++begin){
      builder.uncheckedAppendFloat(arr[begin]);
      if(begin==end){ return; }
      builder.uncheckedAppendCommaAndSpace();
    }
  }
  static int reverseHashCode(float[] arr,int offset,int bound){
    int hash=31+HashUtils.hashFloat(arr[--bound]);
    while(bound!=offset){
      hash=hash*31+HashUtils.hashFloat(arr[--bound]);
    }
    return hash;
  }
  static int reverseToString(float[] arr,int begin,int end,char[] buffer,int bufferOffset){
    for(;;buffer[bufferOffset++]=',',buffer[bufferOffset++]=' ',--end){
      bufferOffset=ToStringUtil.getStringFloat(arr[end],buffer,bufferOffset);
      if(end==begin){ return bufferOffset; }
    }
  }
  static void reverseToString(float[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
  }
  static void reverseToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilder builder){
    for(;;--end){
      builder.uncheckedAppendFloat(arr[end]);
      if(end==begin){ return; }
      builder.uncheckedAppendCommaAndSpace();
    }
  }
  static boolean uncheckedAnyMatches(float[] arr,int offset,int bound,float val){
    if(val==val){ return uncheckedAnyMatchesFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedAnyMatchesFltNaN(arr,offset,bound);
  }
  static boolean uncheckedAnyMatchesFlt0(float[] arr,int offset,int bound){
    while(arr[offset]!=0){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static boolean uncheckedAnyMatchesFltBits(float[] arr,int offset,int bound,int fltBits){
    while(Float.floatToRawIntBits(arr[offset])!=fltBits){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static boolean uncheckedAnyMatchesFltNaN(float[] arr,int offset,int bound){
    while(!Float.isNaN(arr[offset])){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static boolean uncheckedAnyMatchesRawInt(float[] arr,int offset,int bound,int val){
    if(val!=0){ return uncheckedAnyMatchesFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedAnyMatchesFlt0(arr,offset,bound);
  }
  static void uncheckedForwardForEachInRange(float[] arr,int offset,int bound,FloatConsumer action){
    do{
      action.accept(arr[offset]);
    }while(++offset!=bound);
  }
  static int uncheckedIndexOfFlt0(float[] arr,int offset,int bound){
    int index=offset;
    do{
      if(arr[index]==0){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedIndexOfFltBits(float[] arr,int offset,int bound,int fltBits){
    int index=offset;
    do{
      if(Float.floatToRawIntBits(arr[index])==fltBits){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedIndexOfFltNaN(float[] arr,int offset,int bound){
    int index=offset;
    do{
      if(Float.isNaN(arr[index])){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedIndexOfMatch(float[] arr,int offset,int bound,float val){
    if(val==val){ return uncheckedIndexOfFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFltNaN(arr,offset,bound);
  }
  static int uncheckedIndexOfRawInt(float[] arr,int offset,int bound,int val){
    if(val!=0){ return uncheckedIndexOfFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFlt0(arr,offset,bound);
  }
  static int uncheckedLastIndexOfFlt0(float[] arr,int offset,int bound){
    do{
      if(arr[--bound]==0){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static int uncheckedLastIndexOfFltBits(float[] arr,int offset,int bound,int fltBits){
    do{
      if(Float.floatToRawIntBits(arr[--bound])==fltBits){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static int uncheckedLastIndexOfFltNaN(float[] arr,int offset,int bound){
    do{
      if(Float.isNaN(arr[--bound])){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static int uncheckedLastIndexOfMatch(float[] arr,int offset,int bound,float val){
    if(val==val){ return uncheckedLastIndexOfFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFltNaN(arr,offset,bound);
  }
  static int uncheckedLastIndexOfRawInt(float[] arr,int offset,int bound,int val){
    if(val!=0){ return uncheckedLastIndexOfFltBits(arr,offset,bound,Float.floatToRawIntBits(val)); }
    return uncheckedLastIndexOfFlt0(arr,offset,bound);
  }
  static void uncheckedReplaceAll(float[] arr,int offset,int bound,FloatUnaryOperator operator){
    do{
      arr[offset]=operator.applyAsFloat(arr[offset]);
    }while(++offset!=bound);
  }
  static void uncheckedReverseArr(float[] arr,int begin,int end){
    do{
      final var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }while(++begin<--end);
  }
  static void uncheckedReverseForEachInRange(float[] arr,int offset,int bound,FloatConsumer action){
    do{
      action.accept(arr[--bound]);
    }while(bound!=offset);
  }
  static void uncheckedReverseSort(float[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(float[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(float[] arr,int begin,int end,FloatComparator sorter){
    // TODO
  }
  private AbstractSeq(){
    super();
    arr=OmniArray.OfFloat.DEFAULT_ARR;
  }
  private AbstractSeq(int capacity){
    super();
    switch(capacity){
    default:
      arr=new float[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      arr=OmniArray.OfFloat.DEFAULT_ARR;
    case 0:
    }
  }
  private AbstractSeq(int size,float[] arr){
    super(size);
    this.arr=arr;
  }
  public boolean contains(boolean val){
    final int size;
    if((size=this.size)!=0){
      final var arr=this.arr;
      if(val){ return uncheckedAnyMatchesFltBits(arr,0,size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedAnyMatchesFlt0(arr,0,size);
    }
    return false;
  }
  public boolean contains(byte val){
    final int size;
    return (size=this.size)!=0&&uncheckedAnyMatchesRawInt(arr,0,size,val);
  }
  public boolean contains(char val){
    final int size;
    return (size=this.size)!=0&&uncheckedAnyMatchesRawInt(arr,0,size,val);
  }
  public boolean contains(double val){
    final int size;
    if((size=this.size)!=0){
      final float v;
      if((v=(float)val)==val){ return uncheckedAnyMatchesFltBits(arr,0,size,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedAnyMatchesFltNaN(arr,0,size);
    }
    return false;
  }
  public boolean contains(float val){
    final int size;
    return (size=this.size)!=0&&uncheckedAnyMatches(arr,0,size,val);
  }
  public boolean contains(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedAnyMatchesFltBits(arr,0,size,Float.floatToRawIntBits(val)); }
      return uncheckedAnyMatchesFlt0(arr,0,size);
    }
    return false;
  }
  public boolean contains(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedAnyMatchesFltBits(arr,0,size,Float.floatToRawIntBits(val)); }
      return uncheckedAnyMatchesFlt0(arr,0,size);
    }
    return false;
  }
  public boolean contains(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Float&&uncheckedAnyMatches(arr,0,size,(float)val);
  }
  public boolean contains(short val){
    final int size;
    return (size=this.size)!=0&&uncheckedAnyMatchesRawInt(arr,0,size,val);
  }
  public void forEach(Consumer<? super Float> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  public void forEach(FloatConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  public float getFloat(int index){
    return arr[index];
  }
  @Override public int hashCode(){
    final int size;
    if((size=this.size)!=0){ return uncheckedHashCode(size); }
    return 1;
  }
  public int indexOf(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedIndexOfFltBits(size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedIndexOfFlt0(size);
    }
    return -1;
  }
  public int indexOf(char val){
    final int size;
    if((size=this.size)!=0){ return uncheckedIndexOfRawInt(size,val); }
    return -1;
  }
  public int indexOf(double val){
    final int size;
    if((size=this.size)!=0){
      float v;
      if((v=(float)val)==val){
        return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedIndexOfFlt0(size); }
    }
    return -1;
  }
  public int indexOf(float val){
    final int size;
    if((size=this.size)!=0){ return uncheckedIndexOfMatch(size,val); }
    return -1;
  }
  public int indexOf(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(size);
    }
    return -1;
  }
  public int indexOf(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedIndexOfFlt0(size);
    }
    return -1;
  }
  public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedIndexOfMatch(size,(float)val); }
    return -1;
  }
  public int indexOf(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedIndexOfRawInt(size,val); }
    return -1;
  }
  public Float peek(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return null;
  }
  public double peekDouble(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return Double.NaN;
  }
  public float peekFloat(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return Float.NaN;
  }
  public void push(float val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(val,size);
    }else{
      uncheckedInit(val);
    }
  }
  public void put(int index,float val){
    arr[index]=val;
  }
  public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Float&&uncheckedRemoveFirstMatch(size,(float)val);
  }
  public boolean removeIf(FloatPredicate filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
  }
  public boolean removeIf(Predicate<? super Float> filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
  }
  public boolean removeVal(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedRemoveFirstFltBits(size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedRemoveFirstFlt0(size);
    }
    return false;
  }
  public boolean removeVal(byte val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(char val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(double val){
    final int size;
    if((size=this.size)!=0){
      float v;
      if(val==(v=(float)val)){ return uncheckedRemoveFirstFltBits(size,Float.floatToRawIntBits(v)); }
      return v!=v&&uncheckedRemoveFirstFltNaN(size);
    }
    return false;
  }
  public boolean removeVal(float val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(size,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(size);
    }
    return false;
  }
  public boolean removeVal(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToFloat(val)
          &&uncheckedRemoveFirstFltBits(size,Float.floatToRawIntBits(val)); }
      return uncheckedRemoveFirstFlt0(size);
    }
    return false;
  }
  public boolean removeVal(short val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public void replaceAll(FloatUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator);
    }
  }
  public void replaceAll(UnaryOperator<Float> operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator::apply);
    }
  }
  public int search(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedSearchFltBits(size,TypeUtil.FLT_TRUE_BITS); }
      return uncheckedSearchFlt0(size);
    }
    return -1;
  }
  public int search(char val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearchRawInt(size,val); }
    return -1;
  }
  public int search(double val){
    final int size;
    if((size=this.size)!=0){
      float v;
      if((v=(float)val)==val){
        return uncheckedSearchFltBits(size,Float.floatToRawIntBits(v));
      }else if(v!=v){ return uncheckedSearchFlt0(size); }
    }
    return -1;
  }
  public int search(float val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearch(size,val); }
    return -1;
  }
  public int search(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedSearchFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedSearchFlt0(size);
    }
    return -1;
  }
  public int search(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToFloat(val)){ return -1; }
        return uncheckedSearchFltBits(size,Float.floatToRawIntBits(val));
      }
      return uncheckedSearchFlt0(size);
    }
    return -1;
  }
  public int search(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Float){ return uncheckedSearch(size,(float)val); }
    return -1;
  }
  public int search(short val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearchRawInt(size,val); }
    return -1;
  }
  public float set(int index,float val){
    final float[] arr;
    final var oldVal=(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  public void sort(Comparator<? super Float> sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter::compare);
    }
  }
  public void sort(FloatComparator sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter);
    }
  }
  public Float[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Float[] dst;
      uncheckedCopyInto(dst=new Float[size],size);
      return dst;
    }
    return OmniArray.OfFloat.DEFAULT_BOXED_ARR;
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
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override protected int uncheckedLastIndexOfFlt0(int size){
    final var arr=this.arr;
    do{
      if(arr[--size]==0){ return size; }
    }while(size!=0);
    return -1;
  }
  @Override protected int uncheckedLastIndexOfFltBits(int size,int fltBits){
    final var arr=this.arr;
    do{
      if(Float.floatToRawIntBits(arr[--size])==fltBits){ return size; }
    }while(size!=0);
    return -1;
  }
  @Override protected int uncheckedLastIndexOfFltNaN(int size){
    final var arr=this.arr;
    do{
      if(Float.isNaN(arr[--size])){ return size; }
    }while(size!=0);
    return -1;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
  abstract void uncheckedCopyInto(float[] dst,int length);
  abstract void uncheckedCopyInto(Float[] dst,int length);
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedForEach(int size,FloatConsumer action);
  abstract int uncheckedHashCode(int size);
  float uncheckedPopFloat(int newSize){
    size=newSize;
    return arr[newSize];
  }
  abstract boolean uncheckedRemoveFirstFlt0(int size);
  abstract boolean uncheckedRemoveFirstFltBits(int size,int fltBits);
  abstract boolean uncheckedRemoveFirstFltNaN(int size);
  abstract boolean uncheckedRemoveIf(int size,FloatPredicate filter);
  abstract void uncheckedReplaceAll(int size,FloatUnaryOperator operator);
  abstract void uncheckedSort(int size,FloatComparator sorter);
  abstract void uncheckedToString(int size,StringBuilder builder);
  private int finalizeSubListBatchRemove(float[] arr,int newBound,int oldBound){
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private float[] growInsert(float[] arr,int index,int size){
    if(arr.length==size){
      ArrCopy.semicheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(float val,int size){
    float[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new float[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private int uncheckedIndexOfFlt0(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==0){ return index; }
    }while(++index!=size);
    return -1;
  }
  private int uncheckedIndexOfFltBits(int size,int fltBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.floatToRawIntBits(arr[index])==fltBits){ return index; }
    }while(++index!=size);
    return -1;
  }
  private int uncheckedIndexOfFltNaN(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(Float.isNaN(arr[index])){ return index; }
    }while(++index!=size);
    return -1;
  }
  private int uncheckedIndexOfMatch(int size,float val){
    if(val==val){ return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFltNaN(size);
  }
  private int uncheckedIndexOfRawInt(int size,int val){
    if(val!=0){ return uncheckedIndexOfFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedIndexOfFlt0(size);
  }
  private void uncheckedInit(float val){
    float[] arr;
    if((arr=this.arr)==OmniArray.OfFloat.DEFAULT_ARR){
      this.arr=arr=new float[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }else if(arr==null){
      this.arr=arr=new float[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(int index,float val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      float[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private boolean uncheckedRemoveFirstMatch(int size,float val){
    if(val==val){ return uncheckedRemoveFirstFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFirstFltNaN(size);
  }
  private boolean uncheckedRemoveFirstMatch(int size,int val){
    if(val!=0){ return uncheckedRemoveFirstFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedRemoveFirstFlt0(size);
  }
  private int uncheckedSearch(int size,float val){
    if(val==val){ return uncheckedSearchFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedSearchFltNaN(size);
  }
  private int uncheckedSearchFlt0(int size){
    final var arr=this.arr;
    int index;
    for(index=size-1;arr[index]!=0;--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  private int uncheckedSearchFltBits(int size,int fltBits){
    final var arr=this.arr;
    int index;
    for(index=size-1;Float.floatToRawIntBits(arr[index])!=fltBits;--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  private int uncheckedSearchFltNaN(int size){
    final var arr=this.arr;
    int index;
    for(index=size-1;!Float.isNaN(arr[index]);--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  private int uncheckedSearchRawInt(int size,int val){
    if(val!=0){ return uncheckedSearchFltBits(size,Float.floatToRawIntBits(val)); }
    return uncheckedSearchFlt0(size);
  }
  static abstract class Checked extends AbstractSeq{
    transient int modCount;
    private static int markSurvivors(float[] arr,long[] survivorSet,int offset,int bound,FloatPredicate filter){
      for(int numSurvivors=0,wordOffset=0;;){
        long word=0L,marker=1L;
        do{
          if(!filter.test(arr[offset])){
            ++numSurvivors;
            word|=marker;
          }
          if(++offset==bound){
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }while((marker<<=1)!=0);
        survivorSet[wordOffset++]=word;
      }
    }
    private static int pullSurvivorsDown(float[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
      for(int survivorSetOffset=0;;++survivorSetOffset,srcOffset+=64){
        long survivorWord;
        int runLength;
        if((runLength=Long.numberOfTrailingZeros(survivorWord=survivorSet[survivorSetOffset]))!=64){
          int wordSrcOffset=srcOffset;
          do{
            ArrCopy.uncheckedCopy(arr,wordSrcOffset+=runLength,arr,dstOffset,
                runLength=Long.numberOfTrailingZeros(~(survivorWord>>>=runLength)));
            dstOffset+=runLength;
            if((numSurvivors-=runLength)==0){ return dstOffset; }
            wordSrcOffset+=runLength;
          }while((runLength=Long.numberOfTrailingZeros(survivorWord>>>=runLength))!=64);
        }
      }
    }
    Checked(){
      super();
    }
    Checked(int capacity){
      super(capacity);
    }
    Checked(int size,float[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      ++modCount;
      super.push(TypeUtil.castToFloat(val));
      return true;
    }
    public boolean add(char val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(float val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(Float val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(int val){
      ++modCount;
      super.push(val);
      return true;
    }
    public void add(int index,float val){
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
    public boolean add(long val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(short val){
      ++modCount;
      super.push(val);
      return true;
    }
    @Override public void clear(){
      if(size!=0){
        ++modCount;
        size=0;
      }
    }
    @Override public float getFloat(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      return super.getFloat(index);
    }
    public float popFloat(){
      final int size;
      if((size=this.size)!=0){
        ++modCount;
        return super.uncheckedPopFloat(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override public void push(float val){
      ++modCount;
      super.push(val);
    }
    @Override public void put(int index,float val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      super.put(index,val);
    }
    public float removeFloatAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final float[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override public float set(int index,float val){
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
    @Override boolean uncheckedRemoveIf(int size,FloatPredicate filter){
      final int expectedModCount=modCount;
      final var arr=this.arr;
      int srcOffset=0;
      try{
        do{
          if(filter.test(arr[srcOffset])){
            int dstOffset=srcOffset;
            while(++srcOffset!=size){
              final float v;
              if(!filter.test(v=arr[srcOffset])){
                final long[] survivors;
                int numSurvivors;
                if((numSurvivors=size-++srcOffset)!=0){
                  numSurvivors=markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,size,filter);
                  CheckedCollection.checkModCount(expectedModCount,modCount);
                  arr[dstOffset++]=v;
                  if(numSurvivors!=0){
                    dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                  }
                }else{
                  CheckedCollection.checkModCount(expectedModCount,modCount);
                  arr[dstOffset++]=v;
                }
                this.size=size-1;
                return true;
              }
            }
            CheckedCollection.checkModCount(expectedModCount,modCount);
            this.size=size-1;
            return true;
          }
        }while(++srcOffset!=size);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      CheckedCollection.checkModCount(expectedModCount,modCount);
      return false;
    }
    @Override void uncheckedReplaceAll(int size,FloatUnaryOperator operator){
      final int expectedModCount=modCount;
      try{
        uncheckedReplaceAll(arr,0,size,operator);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
    @Override void uncheckedSort(int size,FloatComparator sorter){
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
      public void add(float val){
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
    static abstract class AbstractSubList extends AbstractSubArrSeq{
      transient final Checked root;
      transient final AbstractSubList parent;
      transient int modCount;
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
      AbstractSubList(Checked root,AbstractSubList parent,int rootOffset,int size,int modCount){
        super(rootOffset,size);
        this.root=root;
        this.parent=parent;
        this.modCount=modCount;
      }
      AbstractSubList(Checked root,int rootOffset,int size){
        super(rootOffset,size);
        this.root=root;
        parent=null;
        modCount=root.modCount;
      }
      public boolean add(float val){
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
      public void add(int index,float val){
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
          final float[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }else{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      public float getFloat(int index){
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
      public float removeFloatAt(int index){
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final float[] arr;
        final var removed=(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.modCount=++modCount;
        root.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      public boolean removeIf(FloatPredicate filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      public boolean removeIf(Predicate<? super Float> filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter::test); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      public float set(int index,float val){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        final float[] arr;
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
        final int size;
        if((size=this.size)!=0){
          final StringBuilder builder;
          final int rootOffset;
          forwardToString(root.arr,rootOffset=this.rootOffset,rootOffset+size,builder=new StringBuilder("["));
          return builder.append(']').toString();
        }
        return "[]";
      }
      private boolean uncheckedRemoveIf(int size,FloatPredicate filter){
        final Checked root;
        int expectedModCount=modCount;
        final var arr=(root=this.root).arr;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        try{
          do{
            if(filter.test(arr[srcOffset])){
              int dstOffset=srcOffset;
              while(++srcOffset!=srcBound){
                final float v;
                if(!filter.test(v=arr[srcOffset])){
                  final long[] survivors;
                  int numSurvivors;
                  if((numSurvivors=size-++srcOffset)!=0){
                    numSurvivors
                        =markSurvivors(arr,survivors=BitSetUtils.getBitSet(numSurvivors),srcOffset,srcBound,filter);
                    CheckedCollection.checkModCount(expectedModCount,root.modCount);
                    arr[dstOffset++]=v;
                    if(numSurvivors!=0){
                      dstOffset=pullSurvivorsDown(arr,survivors,dstOffset,srcOffset,numSurvivors);
                    }
                  }else{
                    CheckedCollection.checkModCount(expectedModCount,root.modCount);
                    arr[dstOffset++]=v;
                  }
                  root.modCount=++expectedModCount;
                  modCount=expectedModCount;
                  this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
                  bubbleUpDecrementSize(parent,size);
                  return true;
                }
              }
              CheckedCollection.checkModCount(expectedModCount,root.modCount);
              root.modCount=++expectedModCount;
              modCount=expectedModCount;
              this.size=size-(size=((AbstractSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
              bubbleUpDecrementSize(parent,size);
              return true;
            }
          }while(++srcOffset!=srcBound);
        }catch(final RuntimeException e){
          throw CheckedCollection.checkModCount(expectedModCount,root.modCount,e);
        }
        CheckedCollection.checkModCount(expectedModCount,root.modCount);
        return false;
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
        public void add(float val){
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
    private static int pullSurvivorsDown(float[] arr,int srcBegin,int srcEnd,FloatPredicate filter){
      int dstOffset=srcBegin;
      while(srcBegin!=srcEnd){
        final float v;
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
    Unchecked(int size,float[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      super.push(TypeUtil.castToFloat(val));
      return true;
    }
    public boolean add(char val){
      super.push(val);
      return true;
    }
    public boolean add(float val){
      super.push(val);
      return true;
    }
    public boolean add(Float val){
      super.push(val);
      return true;
    }
    public boolean add(int val){
      super.push(val);
      return true;
    }
    public void add(int index,float val){
      final int size;
      if((size=this.size)!=0){
        super.uncheckedInsert(index,val,size);
      }else{
        super.uncheckedInit(val);
      }
    }
    public boolean add(long val){
      super.push(val);
      return true;
    }
    public boolean add(short val){
      super.push(val);
      return true;
    }
    public float removeFloatAt(int index){
      final float[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override boolean uncheckedRemoveIf(int size,FloatPredicate filter){
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
    @Override void uncheckedReplaceAll(int size,FloatUnaryOperator operator){
      uncheckedReplaceAll(arr,0,size,operator);
    }
    @Override void uncheckedSort(int size,FloatComparator sorter){
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
      public void add(float val){
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
    static abstract class AbstractSubList extends AbstractSubArrSeq{
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
      AbstractSubList(Unchecked root,AbstractSubList parent,int rootOffset,int size){
        super(rootOffset,size);
        this.root=root;
        this.parent=parent;
      }
      AbstractSubList(Unchecked root,int rootOffset,int size){
        super(rootOffset,size);
        this.root=root;
        parent=null;
      }
      public boolean add(float val){
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
      public void add(int index,float val){
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
          final float[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      public float getFloat(int index){
        return root.arr[index+rootOffset];
      }
      public void put(int index,float val){
        root.arr[index+rootOffset]=val;
      }
      public float removeFloatAt(int index){
        final Unchecked root;
        final float[] arr;
        final var removed=(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      public boolean removeIf(FloatPredicate filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
      }
      public boolean removeIf(Predicate<? super Float> filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
      }
      public float set(int index,float val){
        final float[] arr;
        final var oldVal=(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      private boolean uncheckedRemoveIf(int size,FloatPredicate filter){
        final AbstractSeq root;
        final var arr=(root=this.root).arr;
        int srcOffset;
        final int srcBound=(srcOffset=rootOffset)+size;
        do{
          if(filter.test(arr[srcOffset])){
            this.size=size-(size
                =root.finalizeSubListBatchRemove(arr,pullSurvivorsDown(arr,srcOffset,srcBound-1,filter),srcBound));
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
        public void add(float val){
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
