package omni.impl.seq.arr;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import omni.function.DoubleComparator;
import omni.impl.CheckedCollection;
import omni.impl.seq.AbstractDoubleList;
import omni.util.ArrCopy;
import omni.util.BitSetUtils;
import omni.util.HashUtils;
import omni.util.OmniArray;
import omni.util.TypeUtil;
abstract class AbstractDoubleArrSeq extends AbstractDoubleList{
  transient double[] arr;
  // TODO redo toString implementation
  static void eraseIndexHelper(double[] arr,int index,int newSize){
    ArrCopy.semicheckedCopy(arr,index+1,arr,index,newSize-index);
  }
  static int forwardHashCode(double[] arr,int offset,int bound){
    int hash=31+HashUtils.hashDouble(arr[offset]);
    while(++offset!=bound){
      hash=hash*31+HashUtils.hashDouble(arr[offset]);
    }
    return hash;
  }
  static void forwardToString(double[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[offset]);++offset!=bound;builder.append(',').append(' ').append(arr[offset])){}
  }
  static int reverseHashCode(double[] arr,int offset,int bound){
    int hash=31+HashUtils.hashDouble(arr[--bound]);
    while(bound!=offset){
      hash=hash*31+HashUtils.hashDouble(arr[--bound]);
    }
    return hash;
  }
  static void reverseToString(double[] arr,int offset,int bound,StringBuilder builder){
    for(builder.append(arr[--bound]);bound!=offset;builder.append(',').append(' ').append(arr[--bound])){}
  }
  static boolean uncheckedContains(double[] arr,int offset,int bound,double val){
    if(val==val){ return uncheckedContainsDblBits(arr,offset,bound,Double.doubleToRawLongBits(val)); }
    return uncheckedContainsDblNaN(arr,offset,bound);
  }
  static boolean uncheckedContains(double[] arr,int offset,int bound,int val){
    if(val!=0){ return uncheckedContainsDblBits(arr,offset,bound,Double.doubleToRawLongBits(val)); }
    return uncheckedContainsDbl0(arr,offset,bound);
  }
  static boolean uncheckedContainsDbl0(double[] arr,int offset,int bound){
    while(arr[offset]!=0){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static boolean uncheckedContainsDblBits(double[] arr,int offset,int bound,long dblBits){
    while(Double.doubleToRawLongBits(arr[offset])!=dblBits){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static boolean uncheckedContainsDblNaN(double[] arr,int offset,int bound){
    while(!Double.isNaN(arr[offset])){
      if(++offset==bound){ return false; }
    }
    return true;
  }
  static void uncheckedForwardForEachInRange(double[] arr,int offset,int bound,DoubleConsumer action){
    do{
      action.accept(arr[offset]);
    }while(++offset!=bound);
  }
  static int uncheckedIndexOf(double[] arr,int offset,int bound,double val){
    if(val==val){ return uncheckedIndexOfDblBits(arr,offset,bound,Double.doubleToRawLongBits(val)); }
    return uncheckedIndexOfDblNaN(arr,offset,bound);
  }
  static int uncheckedIndexOfDbl0(double[] arr,int offset,int bound){
    int index=offset;
    do{
      if(arr[index]==0){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedIndexOfDblBits(double[] arr,int offset,int bound,long dblBits){
    int index=offset;
    do{
      if(Double.doubleToRawLongBits(arr[index])==dblBits){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedIndexOfDblNaN(double[] arr,int offset,int bound){
    int index=offset;
    do{
      if(Double.isNaN(arr[index])){ return index-offset; }
    }while(++index!=bound);
    return -1;
  }
  static int uncheckedLastIndexOf(double[] arr,int offset,int bound,double val){
    if(val==val){ return uncheckedLastIndexOfDblBits(arr,offset,bound,Double.doubleToRawLongBits(val)); }
    return uncheckedLastIndexOfDblNaN(arr,offset,bound);
  }
  static int uncheckedLastIndexOfDbl0(double[] arr,int offset,int bound){
    do{
      if(arr[--bound]==0){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static int uncheckedLastIndexOfDblBits(double[] arr,int offset,int bound,long dblBits){
    do{
      if(Double.doubleToRawLongBits(arr[--bound])==dblBits){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static int uncheckedLastIndexOfDblNaN(double[] arr,int offset,int bound){
    do{
      if(Double.isNaN(arr[--bound])){ return bound-offset; }
    }while(bound!=offset);
    return -1;
  }
  static void uncheckedReplaceAll(double[] arr,int offset,int bound,DoubleUnaryOperator operator){
    do{
      arr[offset]=operator.applyAsDouble(arr[offset]);
    }while(++offset!=bound);
  }
  static void uncheckedReverseArr(double[] arr,int begin,int end){
    do{
      final var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }while(++begin<--end);
  }
  static void uncheckedReverseForEachInRange(double[] arr,int offset,int bound,DoubleConsumer action){
    do{
      action.accept(arr[--bound]);
    }while(bound!=offset);
  }
  static void uncheckedReverseSort(double[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(double[] arr,int begin,int end){
    // TODO
  }
  static void uncheckedSort(double[] arr,int begin,int end,DoubleComparator sorter){
    // TODO
  }
  private AbstractDoubleArrSeq(){
    super();
    arr=OmniArray.OfDouble.DEFAULT_ARR;
  }
  private AbstractDoubleArrSeq(int capacity){
    super();
    switch(capacity){
    default:
      arr=new double[capacity];
      return;
    case OmniArray.DEFAULT_ARR_SEQ_CAP:
      arr=OmniArray.OfDouble.DEFAULT_ARR;
    case 0:
    }
  }
  private AbstractDoubleArrSeq(int size,double[] arr){
    super(size);
    this.arr=arr;
  }
  public boolean contains(boolean val){
    final int size;
    if((size=this.size)!=0){
      final var arr=this.arr;
      if(val){ return uncheckedContainsDblBits(arr,0,size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedContainsDbl0(arr,0,size);
    }
    return false;
  }
  public boolean contains(byte val){
    final int size;
    return (size=this.size)!=0&&uncheckedContains(arr,0,size,val);
  }
  public boolean contains(char val){
    final int size;
    return (size=this.size)!=0&&uncheckedContains(arr,0,size,val);
  }
  public boolean contains(double val){
    final int size;
    return (size=this.size)!=0&&uncheckedContains(arr,0,size,val);
  }
  public boolean contains(float val){
    final int size;
    if((size=this.size)!=0){
      final var arr=this.arr;
      if(val==val){ return uncheckedContainsDblBits(arr,0,size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDblNaN(arr,0,size);
    }
    return false;
  }
  public boolean contains(int val){
    final int size;
    return (size=this.size)!=0&&uncheckedContains(arr,0,size,val);
  }
  public boolean contains(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedContainsDblBits(arr,0,size,Double.doubleToRawLongBits(val)); }
      return uncheckedContainsDbl0(arr,0,size);
    }
    return false;
  }
  public boolean contains(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Double&&uncheckedContains(arr,0,size,(double)val);
  }
  public boolean contains(short val){
    final int size;
    return (size=this.size)!=0&&uncheckedContains(arr,0,size,val);
  }
  public void forEach(Consumer<? super Double> action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action::accept);
    }
  }
  public void forEach(DoubleConsumer action){
    final int size;
    if((size=this.size)!=0){
      uncheckedForEach(size,action);
    }
  }
  public double getDouble(int index){
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
      if(val){ return uncheckedIndexOfDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  public int indexOf(double val){
    final int size;
    if((size=this.size)!=0){ return uncheckedIndexOf(size,val); }
    return -1;
  }
  public int indexOf(float val){
    final int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDblNaN(size);
    }
    return -1;
  }
  public int indexOf(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  public int indexOf(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedIndexOfDbl0(size);
    }
    return -1;
  }
  public int indexOf(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Double){ return uncheckedIndexOf(size,(double)val); }
    return -1;
  }
  public Double peek(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return null;
  }
  public double peekDouble(){
    final int size;
    if((size=this.size)!=0){ return arr[size-1]; }
    return Double.NaN;
  }
  public void push(double val){
    final int size;
    if((size=this.size)!=0){
      uncheckedAppend(val,size);
    }else{
      uncheckedInit(val);
    }
  }
  public void put(int index,double val){
    arr[index]=val;
  }
  public boolean remove(Object val){
    final int size;
    return (size=this.size)!=0&&val instanceof Double&&uncheckedRemoveFirstMatch(size,(double)val);
  }
  public boolean removeIf(DoublePredicate filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
  }
  public boolean removeIf(Predicate<? super Double> filter){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
  }
  public boolean removeVal(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedRemoveFirstDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedRemoveFirstDbl0(size);
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
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(float val){
    final int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedRemoveFirstDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDblNaN(size);
    }
    return false;
  }
  public boolean removeVal(int val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public boolean removeVal(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return TypeUtil.checkCastToDouble(val)
          &&uncheckedRemoveFirstDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedRemoveFirstDbl0(size);
    }
    return false;
  }
  public boolean removeVal(short val){
    final int size;
    return (size=this.size)!=0&&uncheckedRemoveFirstMatch(size,val);
  }
  public void replaceAll(DoubleUnaryOperator operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator);
    }
  }
  public void replaceAll(UnaryOperator<Double> operator){
    final int size;
    if((size=this.size)!=0){
      uncheckedReplaceAll(size,operator::apply);
    }
  }
  public int search(boolean val){
    final int size;
    if((size=this.size)!=0){
      if(val){ return uncheckedSearchDblBits(size,TypeUtil.DBL_TRUE_BITS); }
      return uncheckedSearchDbl0(size);
    }
    return -1;
  }
  public int search(double val){
    final int size;
    if((size=this.size)!=0){ return uncheckedSearch(size,val); }
    return -1;
  }
  public int search(float val){
    final int size;
    if((size=this.size)!=0){
      if(val==val){ return uncheckedSearchDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedSearchDblNaN(size);
    }
    return -1;
  }
  public int search(int val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){ return uncheckedSearchDblBits(size,Double.doubleToRawLongBits(val)); }
      return uncheckedSearchDbl0(size);
    }
    return -1;
  }
  public int search(long val){
    final int size;
    if((size=this.size)!=0){
      if(val!=0){
        if(!TypeUtil.checkCastToDouble(val)){ return -1; }
        return uncheckedSearchDblBits(size,Double.doubleToRawLongBits(val));
      }
      return uncheckedSearchDbl0(size);
    }
    return -1;
  }
  public int search(Object val){
    final int size;
    if((size=this.size)!=0&&val instanceof Double){ return uncheckedSearch(size,(double)val); }
    return -1;
  }
  public double set(int index,double val){
    final double[] arr;
    final var oldVal=(arr=this.arr)[index];
    arr[index]=val;
    return oldVal;
  }
  public void sort(Comparator<? super Double> sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter::compare);
    }
  }
  public void sort(DoubleComparator sorter){
    final int size;
    if((size=this.size)>1){
      uncheckedSort(size,sorter);
    }
  }
  public Double[] toArray(){
    final int size;
    if((size=this.size)!=0){
      final Double[] dst;
      uncheckedCopyInto(dst=new Double[size],size);
      return dst;
    }
    return OmniArray.OfDouble.DEFAULT_BOXED_ARR;
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
  @Override public String toString(){
    final int size;
    if((size=this.size)!=0){
      final StringBuilder builder;
      uncheckedToString(size,builder=new StringBuilder("["));
      return builder.append(']').toString();
    }
    return "[]";
  }
  @Override protected int uncheckedLastIndexOfDbl0(int size){
    final var arr=this.arr;
    do{
      if(arr[--size]==0){ return size; }
    }while(size!=0);
    return -1;
  }
  @Override protected int uncheckedLastIndexOfDblBits(int size,long dblBits){
    final var arr=this.arr;
    do{
      if(Double.doubleToRawLongBits(arr[--size])==dblBits){ return size; }
    }while(size!=0);
    return -1;
  }
  @Override protected int uncheckedLastIndexOfDblNaN(int size){
    final var arr=this.arr;
    do{
      if(Double.isNaN(arr[--size])){ return size; }
    }while(size!=0);
    return -1;
  }
  abstract void uncheckedCopyInto(double[] dst,int length);
  abstract void uncheckedCopyInto(Double[] dst,int length);
  abstract void uncheckedCopyInto(Object[] dst,int length);
  abstract void uncheckedForEach(int size,DoubleConsumer action);
  abstract int uncheckedHashCode(int size);
  double uncheckedPopDouble(int newSize){
    size=newSize;
    return arr[newSize];
  }
  abstract boolean uncheckedRemoveFirstDbl0(int size);
  abstract boolean uncheckedRemoveFirstDblBits(int size,long dblBits);
  abstract boolean uncheckedRemoveFirstDblNaN(int size);
  abstract boolean uncheckedRemoveIf(int size,DoublePredicate filter);
  abstract void uncheckedReplaceAll(int size,DoubleUnaryOperator operator);
  abstract void uncheckedSort(int size,DoubleComparator sorter);
  abstract void uncheckedToString(int size,StringBuilder builder);
  private int finalizeSubListBatchRemove(double[] arr,int newBound,int oldBound){
    final int newRootSize,numRemoved;
    size=newRootSize=size-(numRemoved=oldBound-newBound);
    ArrCopy.semicheckedCopy(arr,oldBound,arr,newBound,newRootSize-newBound);
    return numRemoved;
  }
  private double[] growInsert(double[] arr,int index,int size){
    if(arr.length==size){
      ArrCopy.semicheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,index);
      this.arr=arr;
    }
    return arr;
  }
  private void uncheckedAppend(double val,int size){
    double[] arr;
    if((arr=this.arr).length==size){
      ArrCopy.uncheckedCopy(arr,0,arr=new double[OmniArray.growBy50Pct(size)],0,size);
    }
    arr[size]=val;
    this.size=size+1;
  }
  private int uncheckedIndexOf(int size,double val){
    if(val==val){ return uncheckedIndexOfDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedIndexOfDblNaN(size);
  }
  private int uncheckedIndexOfDbl0(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(arr[index]==0){ return index; }
    }while(++index!=size);
    return -1;
  }
  private int uncheckedIndexOfDblBits(int size,long dblBits){
    final var arr=this.arr;
    int index=0;
    do{
      if(Double.doubleToRawLongBits(arr[index])==dblBits){ return index; }
    }while(++index!=size);
    return -1;
  }
  private int uncheckedIndexOfDblNaN(int size){
    final var arr=this.arr;
    int index=0;
    do{
      if(Double.isNaN(arr[index])){ return index; }
    }while(++index!=size);
    return -1;
  }
  private void uncheckedInit(double val){
    double[] arr;
    if((arr=this.arr)==OmniArray.OfDouble.DEFAULT_ARR){
      this.arr=arr=new double[OmniArray.DEFAULT_ARR_SEQ_CAP];
    }else if(arr==null){
      this.arr=arr=new double[1];
    }
    arr[0]=val;
    size=1;
  }
  private void uncheckedInsert(int index,double val,int size){
    final int tailDist;
    if((tailDist=size-index)==0){
      uncheckedAppend(val,size);
    }else{
      double[] arr;
      ArrCopy.uncheckedCopy(arr=this.arr,index,arr=growInsert(arr,index,size),index+1,tailDist);
      arr[index]=val;
      this.size=size+1;
    }
  }
  private boolean uncheckedRemoveFirstMatch(int size,double val){
    if(val==val){ return uncheckedRemoveFirstDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveFirstDblNaN(size);
  }
  private boolean uncheckedRemoveFirstMatch(int size,int val){
    if(val!=0){ return uncheckedRemoveFirstDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedRemoveFirstDbl0(size);
  }
  private int uncheckedSearch(int size,double val){
    if(val==val){ return uncheckedSearchDblBits(size,Double.doubleToRawLongBits(val)); }
    return uncheckedSearchDblNaN(size);
  }
  private int uncheckedSearchDbl0(int size){
    final var arr=this.arr;
    int index;
    for(index=size-1;arr[index]!=0;--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  private int uncheckedSearchDblBits(int size,long dblBits){
    final var arr=this.arr;
    int index;
    for(index=size-1;Double.doubleToRawLongBits(arr[index])!=dblBits;--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  private int uncheckedSearchDblNaN(int size){
    final var arr=this.arr;
    int index;
    for(index=size-1;!Double.isNaN(arr[index]);--index){
      if(index==0){ return -1; }
    }
    return size-index;
  }
  static abstract class Checked extends AbstractDoubleArrSeq{
    transient int modCount;
    private static int markSurvivors(double[] arr,long[] survivorSet,int offset,int bound,DoublePredicate filter){
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
    private static int pullSurvivorsDown(double[] arr,long[] survivorSet,int dstOffset,int srcOffset,int numSurvivors){
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
    Checked(int size,double[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      ++modCount;
      super.push(TypeUtil.castToDouble(val));
      return true;
    }
    public boolean add(char val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(double val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(Double val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(float val){
      ++modCount;
      super.push(val);
      return true;
    }
    public boolean add(int val){
      ++modCount;
      super.push(val);
      return true;
    }
    public void add(int index,double val){
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
    @Override public double getDouble(int index){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      return super.getDouble(index);
    }
    public double popDouble(){
      final int size;
      if((size=this.size)!=0){
        ++modCount;
        return super.uncheckedPopDouble(size-1);
      }
      throw new NoSuchElementException();
    }
    @Override public void push(double val){
      ++modCount;
      super.push(val);
    }
    @Override public void put(int index,double val){
      CheckedCollection.checkLo(index);
      CheckedCollection.checkReadHi(index,size);
      super.put(index,val);
    }
    public double removeDoubleAt(int index){
      CheckedCollection.checkLo(index);
      int size;
      CheckedCollection.checkReadHi(index,size=this.size);
      ++modCount;
      final double[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      this.size=size;
      return removed;
    }
    @Override public double set(int index,double val){
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
    @Override boolean uncheckedRemoveIf(int size,DoublePredicate filter){
      final int expectedModCount=modCount;
      final var arr=this.arr;
      int srcOffset=0;
      try{
        do{
          if(filter.test(arr[srcOffset])){
            int dstOffset=srcOffset;
            while(++srcOffset!=size){
              final double v;
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
    @Override void uncheckedReplaceAll(int size,DoubleUnaryOperator operator){
      final int expectedModCount=modCount;
      try{
        uncheckedReplaceAll(arr,0,size,operator);
        CheckedCollection.checkModCount(expectedModCount,modCount);
      }catch(final RuntimeException e){
        throw CheckedCollection.checkModCount(expectedModCount,modCount,e);
      }
      modCount=expectedModCount+1;
    }
    @Override void uncheckedSort(int size,DoubleComparator sorter){
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
      public void add(double val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize,cursor=this.cursor;
        if((rootSize=root.size)!=0){
          ((AbstractDoubleArrSeq)root).uncheckedInsert(cursor,val,rootSize);
        }else{
          ((AbstractDoubleArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        this.cursor=cursor+1;
        lastRet=-1;
      }
    }
    static abstract class AbstractDescendingItr{
      transient final Checked root;
      transient int cursor;
      transient int lastRet=-1;
      transient int modCount;
      AbstractDescendingItr(Checked root){
        this.root=root;
        cursor=root.size;
        modCount=root.modCount;
      }
      public double nextDouble(){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        int cursor;
        if((cursor=this.cursor)!=0){
          lastRet=--cursor;
          this.cursor=cursor;
          return root.arr[cursor];
        }
        throw new NoSuchElementException();
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
      public boolean add(double val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        final int rootSize;
        final int size=this.size;
        if((rootSize=root.size)!=0){
          ((AbstractDoubleArrSeq)root).uncheckedInsert(rootOffset+size,val,rootSize);
        }else{
          ((AbstractDoubleArrSeq)root).uncheckedInit(val);
        }
        root.modCount=++modCount;
        this.modCount=modCount;
        bubbleUpIncrementSize(parent);
        this.size=size+1;
        return true;
      }
      public void add(int index,double val){
        final Checked root;
        int modCount;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkWriteHi(index,size=this.size);
        final int rootSize;
        if((rootSize=root.size)!=0){
          ((AbstractDoubleArrSeq)root).uncheckedInsert(rootOffset+index,val,rootSize);
        }else{
          ((AbstractDoubleArrSeq)root).uncheckedInit(val);
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
          final double[] arr;
          final int rootOffset;
          ArrCopy.semicheckedCopy(arr=root.arr,(rootOffset=this.rootOffset)+size,arr,rootOffset,newRootSize-rootOffset);
        }else{
          CheckedCollection.checkModCount(modCount,root.modCount);
        }
      }
      public double getDouble(int index){
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
      public double removeDoubleAt(int index){
        int modCount;
        final Checked root;
        CheckedCollection.checkModCount(modCount=this.modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        final int size;
        CheckedCollection.checkReadHi(index,size=this.size);
        final double[] arr;
        final var removed=(arr=root.arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        this.modCount=++modCount;
        root.modCount=modCount;
        this.size=size-1;
        return removed;
      }
      public boolean removeIf(DoublePredicate filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      public boolean removeIf(Predicate<? super Double> filter){
        final int size;
        if((size=this.size)!=0){ return uncheckedRemoveIf(size,filter::test); }
        CheckedCollection.checkModCount(modCount,root.modCount);
        return false;
      }
      public double set(int index,double val){
        final Checked root;
        CheckedCollection.checkModCount(modCount,(root=this.root).modCount);
        CheckedCollection.checkLo(index);
        CheckedCollection.checkReadHi(index,size);
        final double[] arr;
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
      private boolean uncheckedRemoveIf(int size,DoublePredicate filter){
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
                final double v;
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
                  this.size=size-(size=((AbstractDoubleArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
                  bubbleUpDecrementSize(parent,size);
                  return true;
                }
              }
              CheckedCollection.checkModCount(expectedModCount,root.modCount);
              root.modCount=++expectedModCount;
              modCount=expectedModCount;
              this.size=size-(size=((AbstractDoubleArrSeq)root).finalizeSubListBatchRemove(arr,dstOffset,srcBound));
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
        public void add(double val){
          final AbstractSubList parent;
          final Checked root;
          int modCount;
          CheckedCollection.checkModCount(modCount=this.modCount,(root=(parent=this.parent).root).modCount);
          final int rootSize;
          final int cursor=this.cursor;
          if((rootSize=root.size)!=0){
            ((AbstractDoubleArrSeq)root).uncheckedInsert(cursor,val,rootSize);
          }else{
            ((AbstractDoubleArrSeq)root).uncheckedInit(val);
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
  static abstract class Unchecked extends AbstractDoubleArrSeq{
    private static int pullSurvivorsDown(double[] arr,int srcBegin,int srcEnd,DoublePredicate filter){
      int dstOffset=srcBegin;
      while(srcBegin!=srcEnd){
        final double v;
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
    Unchecked(int size,double[] arr){
      super(size,arr);
    }
    public boolean add(boolean val){
      super.push(TypeUtil.castToDouble(val));
      return true;
    }
    public boolean add(char val){
      super.push(val);
      return true;
    }
    public boolean add(double val){
      super.push(val);
      return true;
    }
    public boolean add(Double val){
      super.push(val);
      return true;
    }
    public boolean add(float val){
      super.push(val);
      return true;
    }
    public boolean add(int val){
      super.push(val);
      return true;
    }
    public void add(int index,double val){
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
    public double removeDoubleAt(int index){
      final double[] arr;
      final var removed=(arr=this.arr)[index];
      eraseIndexHelper(arr,index,--size);
      return removed;
    }
    @Override boolean uncheckedRemoveIf(int size,DoublePredicate filter){
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
    @Override void uncheckedReplaceAll(int size,DoubleUnaryOperator operator){
      uncheckedReplaceAll(arr,0,size,operator);
    }
    @Override void uncheckedSort(int size,DoubleComparator sorter){
      uncheckedSort(arr,0,size-1,sorter);
    }
    static abstract class AbstractAscendingItr{
      transient final Unchecked root;
      transient int cursor;
      AbstractAscendingItr(Unchecked root){
        this.root=root;
      }
      public double nextDouble(){
        return root.arr[cursor++];
      }
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
      public void add(double val){
        final AbstractDoubleArrSeq root;
        final int rootSize,cursor=this.cursor;
        if((rootSize=(root=this.root).size)!=0){
          root.uncheckedInsert(cursor,val,rootSize);
        }else{
          root.uncheckedInit(val);
        }
        this.cursor=cursor+1;
      }
    }
    static abstract class AbstractDescendingItr{
      transient final Unchecked root;
      transient int cursor;
      AbstractDescendingItr(Unchecked root){
        this.root=root;
        cursor=root.size;
      }
      public double nextDouble(){
        return root.arr[--cursor];
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
      public boolean add(double val){
        final AbstractDoubleArrSeq root;
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
      public void add(int index,double val){
        final AbstractDoubleArrSeq root;
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
          final double[] arr;
          ArrCopy.semicheckedCopy(arr=root.arr,size+(size=rootOffset),arr,size,newRootSize-size);
        }
      }
      public double getDouble(int index){
        return root.arr[index+rootOffset];
      }
      public void put(int index,double val){
        root.arr[index+rootOffset]=val;
      }
      public double removeDoubleAt(int index){
        final Unchecked root;
        final double[] arr;
        final var removed=(arr=(root=this.root).arr)[index+=rootOffset];
        eraseIndexHelper(arr,index,--root.size);
        bubbleUpDecrementSize(parent);
        --size;
        return removed;
      }
      public boolean removeIf(DoublePredicate filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter);
      }
      public boolean removeIf(Predicate<? super Double> filter){
        final int size;
        return (size=this.size)!=0&&uncheckedRemoveIf(size,filter::test);
      }
      public double set(int index,double val){
        final double[] arr;
        final var oldVal=(arr=root.arr)[index+=rootOffset];
        arr[index]=val;
        return oldVal;
      }
      private boolean uncheckedRemoveIf(int size,DoublePredicate filter){
        final AbstractDoubleArrSeq root;
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
      static abstract class AbstractAscendingItr{
        transient final AbstractSubList parent;
        transient int cursor;
        AbstractAscendingItr(AbstractSubList parent){
          this.parent=parent;
          cursor=parent.rootOffset;
        }
        public double nextDouble(){
          return parent.root.arr[cursor++];
        }
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
        public void add(double val){
          final AbstractSubList parent;
          final AbstractDoubleArrSeq root;
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
