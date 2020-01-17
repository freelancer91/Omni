package omni.util;
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.Consumer;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
import omni.function.CharConsumer;
import omni.function.FloatConsumer;
import omni.function.ShortConsumer;
import omni.function.ByteConsumer;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.function.ByteUnaryOperator;
import omni.function.CharUnaryOperator;
import omni.function.ShortUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import omni.function.FloatUnaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import java.io.ObjectInput;
import java.io.DataInput;
import java.io.ObjectOutput;
import java.io.DataOutput;
import java.io.IOException;
public final class OmniArray
{
  private OmniArray()
  {
    super();
  }
  public static final int DEFAULT_ARR_SEQ_CAP=10;
  private static final int DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT=DEFAULT_ARR_SEQ_CAP+(DEFAULT_ARR_SEQ_CAP>>>1);
  public static final int MAX_ARR_SIZE=Integer.MAX_VALUE-8;
  private static final int MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD=(MAX_ARR_SIZE/3<<1)+1;
  public static int growBy100Pct(int currentCapacity){
    if(currentCapacity!=0){
      if(currentCapacity<MAX_ARR_SIZE){
        if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
        return currentCapacity<<1;
      }else if(currentCapacity==Integer.MAX_VALUE){
        throw new OutOfMemoryError();
      }
    }
    return currentCapacity+1;
  }
  public static int growBy100Pct(int currentCapacity,int minCapacity){
    if(currentCapacity!=0){
      if(minCapacity<MAX_ARR_SIZE){
        if(minCapacity<0){
          throw new OutOfMemoryError();
        }
        if(currentCapacity>MAX_ARR_SIZE>>>1){ return MAX_ARR_SIZE; }
        if((currentCapacity<<=1)>=minCapacity){ return currentCapacity; }
      }
    }else if(minCapacity<0){
      throw new OutOfMemoryError();
    }
    return minCapacity;
  }
  public static int growBy50Pct(int currentCapacity){
    if(currentCapacity>1){
      if(currentCapacity<MAX_ARR_SIZE){
        if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
        return currentCapacity+(currentCapacity>>>1);
      }else if(currentCapacity==Integer.MAX_VALUE){
        throw new OutOfMemoryError();
      }
    }
    return currentCapacity+1;
  }
  public static int growBy50Pct(int currentCapacity,int minCapacity){
    if(currentCapacity>1){
      if(minCapacity<MAX_ARR_SIZE){
        if(minCapacity<0){
          throw new OutOfMemoryError();
        }
        if(currentCapacity>MAX_ARR_SIZE_50_PCT_GROWTH_THRESHOLD){ return MAX_ARR_SIZE; }
        if((currentCapacity+=currentCapacity>>>1)>=minCapacity){ return currentCapacity; }
      }
    }else if(minCapacity<0){
      throw new OutOfMemoryError();
    }
    return minCapacity;
  }
  public static int growToArrSeqDefault(int minCapacity){
    if(minCapacity>=DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT){ return minCapacity; }
    if(minCapacity>=DEFAULT_ARR_SEQ_CAP){ return DEFAULT_ARR_SEQ_CAP_PLUS_50_PCT; }
    return DEFAULT_ARR_SEQ_CAP;
  }
  @SuppressWarnings("unchecked") public static <T> T[] uncheckedArrResize(int size,T[] arr){
    switch(Integer.signum(size-arr.length)){
    case -1:
        arr[size]=null;
    default:
        break;
    case 1:
        arr=(T[])Array.newInstance(arr.getClass().getComponentType(),size);
    }
    return arr;
  }
  public interface OfBoolean
  {
    public static final boolean[] DEFAULT_ARR=new boolean[]{};
    public static final Boolean[] DEFAULT_BOXED_ARR=new Boolean[]{};
    public static void fill(boolean[] arr,int begin,int end,boolean val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(boolean[] arr,int begin,int end,DataOutput out) throws IOException{
      for(int word=TypeUtil.castToByte(arr[begin]),marker=1;;){
        if(begin==end){
          out.writeByte(word);
          return;
        }
        if((marker<<=1)==(1<<8)){
          out.writeByte(word);
          word=0;
          marker=1;
        }
        if(arr[++begin]){
          word|=marker;
        }
      }
    }
    public static void readArray(boolean[] arr,int begin,int end,DataInput in) throws IOException{
      for(int word=in.readUnsignedByte(),marker=1;;++begin){
        if((marker&word)!=0){
          arr[begin]=true;
        }
        if(begin==end){
          return;
        }
        if((marker<<=1)==(1<<8)){
          word=in.readUnsignedByte();
          marker=1;
        }
      }
    }
    public static   void uncheckedReplaceAll(boolean[] arr,int offset,int bound,BooleanPredicate operator)
    {
      for(;;)
      {
        arr[offset]=operator.test((boolean)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;++begin)
      {
        action.accept((boolean)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(boolean[] arr,int begin,int end,BooleanConsumer action)
    {
      for(;;--end)
      {
        action.accept((boolean)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(boolean[] arr,int begin,int end)
    {
      for(int hash=31+
        ((arr[begin])?1231:1237)
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          ((arr[++begin])?1231:1237)
          );
      }
    }
    public static int descendingSeqHashCode(boolean[] arr,int begin,int end)
    {
      for(int hash=31+
        ((arr[end])?1231:1237)
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          ((arr[--end])?1231:1237)
          );
      }
    }
    public static void ascendingToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendBoolean(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(boolean[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringBoolean(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(boolean[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendBoolean(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(boolean[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringBoolean(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains (boolean[] arr,int begin,int end
    ,boolean val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (boolean[] arr,int bound
    ,boolean val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (boolean[] arr,int bound
    ,boolean val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (boolean[] arr,int offset,int length
    ,boolean val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (boolean[] arr,int offset,int length
    ,boolean val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (boolean[] arr,int bound
    ,boolean val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(boolean[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(boolean[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(boolean[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
  }
  public interface OfByte
  {
    public static final byte[] DEFAULT_ARR=new byte[]{};
    public static final Byte[] DEFAULT_BOXED_ARR=new Byte[]{};
    public static void fill(byte[] arr,int begin,int end,byte val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static   void uncheckedReplaceAll(byte[] arr,int offset,int bound,ByteUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsByte((byte)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(byte[] arr,int begin,int end,ByteConsumer action)
    {
      for(;;++begin)
      {
        action.accept((byte)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(byte[] arr,int begin,int end,ByteConsumer action)
    {
      for(;;--end)
      {
        action.accept((byte)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(byte[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[begin]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[++begin]
          );
      }
    }
    public static int descendingSeqHashCode(byte[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[end]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[--end]
          );
      }
    }
    public static void ascendingToString(byte[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendShort(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(byte[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(byte[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendShort(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(byte[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains (byte[] arr,int begin,int end
    ,int val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (byte[] arr,int bound
    ,int val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (byte[] arr,int bound
    ,int val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (byte[] arr,int offset,int length
    ,int val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (byte[] arr,int offset,int length
    ,int val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (byte[] arr,int bound
    ,int val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(byte[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(byte[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(byte[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
  }
  public interface OfChar
  {
    public static final char[] DEFAULT_ARR=new char[]{};
    public static final Character[] DEFAULT_BOXED_ARR=new Character[]{};
    public static void fill(char[] arr,int begin,int end,char val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(char[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeChar(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(char[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readChar();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(char[] arr,int offset,int bound,CharUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsChar((char)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;++begin)
      {
        action.accept((char)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(char[] arr,int begin,int end,CharConsumer action)
    {
      for(;;--end)
      {
        action.accept((char)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(char[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[begin]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[++begin]
          );
      }
    }
    public static int descendingSeqHashCode(char[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[end]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[--end]
          );
      }
    }
    public static void ascendingToString(char[] arr,int begin,int end,char[] buffer,int bufferOffset)
    {
      for(;;++bufferOffset,++begin)
      {
        buffer[bufferOffset]=arr[begin];
        if(begin==end)
        {
          return;
        }
        buffer[++bufferOffset]=',';
        buffer[++bufferOffset]=' ';
      }
    }
    public static void descendingToString(char[] arr,int begin,int end,char[] buffer,int bufferOffset)
    {
      for(;;++bufferOffset,--end)
      {
        buffer[bufferOffset]=arr[end];
        if(begin==end)
        {
          return;
        }
        buffer[++bufferOffset]=',';
        buffer[++bufferOffset]=' ';
      }
    }
    public static boolean uncheckedcontains (char[] arr,int begin,int end
    ,int val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (char[] arr,int bound
    ,int val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (char[] arr,int bound
    ,int val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (char[] arr,int offset,int length
    ,int val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (char[] arr,int offset,int length
    ,int val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (char[] arr,int bound
    ,int val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(char[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(char[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(char[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(CharPredicate predicate,char...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfShort
  {
    public static final short[] DEFAULT_ARR=new short[]{};
    public static final Short[] DEFAULT_BOXED_ARR=new Short[]{};
    public static void fill(short[] arr,int begin,int end,short val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(short[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeShort(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(short[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readShort();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(short[] arr,int offset,int bound,ShortUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsShort((short)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;++begin)
      {
        action.accept((short)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(short[] arr,int begin,int end,ShortConsumer action)
    {
      for(;;--end)
      {
        action.accept((short)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(short[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[begin]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[++begin]
          );
      }
    }
    public static int descendingSeqHashCode(short[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[end]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[--end]
          );
      }
    }
    public static void ascendingToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendShort(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(short[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringShort(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(short[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendShort(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(short[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringShort(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains (short[] arr,int begin,int end
    ,int val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (short[] arr,int bound
    ,int val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (short[] arr,int bound
    ,int val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (short[] arr,int offset,int length
    ,int val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (short[] arr,int offset,int length
    ,int val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (short[] arr,int bound
    ,int val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(short[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(short[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(short[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(ShortPredicate predicate,short...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfInt
  {
    public static final int[] DEFAULT_ARR=new int[]{};
    public static final Integer[] DEFAULT_BOXED_ARR=new Integer[]{};
    public static void fill(int[] arr,int begin,int end,int val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(int[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeInt(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(int[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readInt();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(int[] arr,int offset,int bound,IntUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsInt((int)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;++begin)
      {
        action.accept((int)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(int[] arr,int begin,int end,IntConsumer action)
    {
      for(;;--end)
      {
        action.accept((int)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(int[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[begin]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[++begin]
          );
      }
    }
    public static int descendingSeqHashCode(int[] arr,int begin,int end)
    {
      for(int hash=31+
        arr[end]
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          arr[--end]
          );
      }
    }
    public static void ascendingToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendInt(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(int[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringInt(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(int[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendInt(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(int[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringInt(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains (int[] arr,int begin,int end
    ,int val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (int[] arr,int bound
    ,int val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (int[] arr,int bound
    ,int val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (int[] arr,int offset,int length
    ,int val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (int[] arr,int offset,int length
    ,int val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (int[] arr,int bound
    ,int val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(int[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(int[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(int[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(IntPredicate predicate,int...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfLong
  {
    public static final long[] DEFAULT_ARR=new long[]{};
    public static final Long[] DEFAULT_BOXED_ARR=new Long[]{};
    public static void fill(long[] arr,int begin,int end,long val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(long[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeLong(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(long[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readLong();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(long[] arr,int offset,int bound,LongUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsLong((long)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;++begin)
      {
        action.accept((long)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(long[] arr,int begin,int end,LongConsumer action)
    {
      for(;;--end)
      {
        action.accept((long)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(long[] arr,int begin,int end)
    {
      for(int hash=31+
        Long.hashCode(arr[begin])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          Long.hashCode(arr[++begin])
          );
      }
    }
    public static int descendingSeqHashCode(long[] arr,int begin,int end)
    {
      for(int hash=31+
        Long.hashCode(arr[end])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          Long.hashCode(arr[--end])
          );
      }
    }
    public static void ascendingToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendLong(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(long[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringLong(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(long[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendLong(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(long[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringLong(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains (long[] arr,int begin,int end
    ,long val
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==val
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (long[] arr,int bound
    ,long val
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==val
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (long[] arr,int bound
    ,long val
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==val
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (long[] arr,int offset,int length
    ,long val
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==val
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (long[] arr,int offset,int length
    ,long val
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==val
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (long[] arr,int bound
    ,long val
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==val
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(long[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(long[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(long[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(LongPredicate predicate,long...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
  public interface OfFloat
  {
    public static final float[] DEFAULT_ARR=new float[]{};
    public static final Float[] DEFAULT_BOXED_ARR=new Float[]{};
    public static void fill(float[] arr,int begin,int end,float val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(float[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeFloat(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(float[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readFloat();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(float[] arr,int offset,int bound,FloatUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsFloat((float)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;++begin)
      {
        action.accept((float)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(float[] arr,int begin,int end,FloatConsumer action)
    {
      for(;;--end)
      {
        action.accept((float)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(float[] arr,int begin,int end)
    {
      for(int hash=31+
        HashUtil.hashFloat(arr[begin])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          HashUtil.hashFloat(arr[++begin])
          );
      }
    }
    public static int descendingSeqHashCode(float[] arr,int begin,int end)
    {
      for(int hash=31+
        HashUtil.hashFloat(arr[end])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          HashUtil.hashFloat(arr[--end])
          );
      }
    }
    public static void ascendingToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;++begin)
      {
        builder.uncheckedAppendFloat(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int ascendingToString(float[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;++begin,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringFloat(arr[begin],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static void descendingToString(float[] arr,int begin,int end,ToStringUtil.OmniStringBuilderByte builder)
    {
      for(;;--end)
      {
        builder.uncheckedAppendFloat(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.uncheckedAppendCommaAndSpace();
      }
    }
    public static int descendingToString(float[] arr,int begin,int end,byte[] buffer,int bufferOffset)
    {
      for(;;--end,++bufferOffset)
      {
        bufferOffset=ToStringUtil.getStringFloat(arr[end],buffer,bufferOffset);
        if(begin==end)
        {
          return bufferOffset;
        }
        buffer[bufferOffset]=(byte)',';
        buffer[++bufferOffset]=(byte)' ';
      }
    }
    public static boolean uncheckedcontains0(float[] arr,int begin,int end
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==0
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf0(float[] arr,int bound
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==0
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf0(float[] arr,int bound
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==0
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf0(float[] arr,int offset,int length
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==0
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf0(float[] arr,int offset,int length
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==0
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch0(float[] arr,int bound
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==0
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsNaN(float[] arr,int begin,int end
    )
    {
      for(;;++begin)
      {
        if
        (
        Float.isNaN(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfNaN(float[] arr,int bound
    )
    {
      for(int offset=0;;)
      {
        if
        (
        Float.isNaN(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNaN(float[] arr,int bound
    )
    {
      for(;;)
      {
        if
        (
        Float.isNaN(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfNaN(float[] arr,int offset,int length
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        Float.isNaN(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNaN(float[] arr,int offset,int length
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        Float.isNaN(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchNaN(float[] arr,int bound
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        Float.isNaN(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsBits(float[] arr,int begin,int end
    ,int bits
    )
    {
      for(;;++begin)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfBits(float[] arr,int bound
    ,int bits
    )
    {
      for(int offset=0;;)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfBits(float[] arr,int bound
    ,int bits
    )
    {
      for(;;)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfBits(float[] arr,int offset,int length
    ,int bits
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfBits(float[] arr,int offset,int length
    ,int bits
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchBits(float[] arr,int bound
    ,int bits
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        bits==Float.floatToRawIntBits(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(float[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(float[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(float[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(FloatPredicate predicate,float...arr)
    {
      return index->predicate.test(arr[index]);
    }
    public static IntPredicate getFltBitsIndexPredicate(FloatPredicate predicate,int...arr)
    {
      return index->predicate.test(Float.intBitsToFloat(arr[index]));
    }
  }
  public interface OfDouble
  {
    public static final double[] DEFAULT_ARR=new double[]{};
    public static final Double[] DEFAULT_BOXED_ARR=new Double[]{};
    public static void fill(double[] arr,int begin,int end,double val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(double[] arr,int begin,int end,DataOutput out) throws IOException{
      for(;;++begin){
        out.writeDouble(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(double[] arr,int begin,int end,DataInput in) throws IOException{
      for(;;++begin){
        arr[begin]=in.readDouble();
        if(begin==end){
          return;
        }
      }
    }
    public static   void uncheckedReplaceAll(double[] arr,int offset,int bound,DoubleUnaryOperator operator)
    {
      for(;;)
      {
        arr[offset]=operator.applyAsDouble((double)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    public static   void ascendingForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;++begin)
      {
        action.accept((double)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static   void descendingForEach(double[] arr,int begin,int end,DoubleConsumer action)
    {
      for(;;--end)
      {
        action.accept((double)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(double[] arr,int begin,int end)
    {
      for(int hash=31+
        HashUtil.hashDouble(arr[begin])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          HashUtil.hashDouble(arr[++begin])
          );
      }
    }
    public static int descendingSeqHashCode(double[] arr,int begin,int end)
    {
      for(int hash=31+
        HashUtil.hashDouble(arr[end])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          HashUtil.hashDouble(arr[--end])
          );
      }
    }
    public static void ascendingToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(;;++begin)
      {
        builder.append(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.append(',').append(' ');
      }
    }
    public static void descendingToString(double[] arr,int begin,int end,StringBuilder builder)
    {
      for(;;--end)
      {
        builder.append(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.append(',').append(' ');
      }
    }
    public static boolean uncheckedcontains0(double[] arr,int begin,int end
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==0
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf0(double[] arr,int bound
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==0
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf0(double[] arr,int bound
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==0
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf0(double[] arr,int offset,int length
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==0
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf0(double[] arr,int offset,int length
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==0
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch0(double[] arr,int bound
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==0
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsNaN(double[] arr,int begin,int end
    )
    {
      for(;;++begin)
      {
        if
        (
        Double.isNaN(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfNaN(double[] arr,int bound
    )
    {
      for(int offset=0;;)
      {
        if
        (
        Double.isNaN(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNaN(double[] arr,int bound
    )
    {
      for(;;)
      {
        if
        (
        Double.isNaN(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfNaN(double[] arr,int offset,int length
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        Double.isNaN(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNaN(double[] arr,int offset,int length
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        Double.isNaN(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchNaN(double[] arr,int bound
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        Double.isNaN(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsBits(double[] arr,int begin,int end
    ,long bits
    )
    {
      for(;;++begin)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfBits(double[] arr,int bound
    ,long bits
    )
    {
      for(int offset=0;;)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfBits(double[] arr,int bound
    ,long bits
    )
    {
      for(;;)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfBits(double[] arr,int offset,int length
    ,long bits
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfBits(double[] arr,int offset,int length
    ,long bits
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchBits(double[] arr,int bound
    ,long bits
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        bits==Double.doubleToRawLongBits(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(double[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
    }
    public static int removeRangeAndPullDown(double[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      return oldBound-numRemoved;
    }
    public static void reverseRange(double[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static IntPredicate getIndexPredicate(DoublePredicate predicate,double...arr)
    {
      return index->predicate.test(arr[index]);
    }
    public static IntPredicate getDblBitsIndexPredicate(DoublePredicate predicate,long...arr)
    {
      return index->predicate.test(Double.longBitsToDouble(arr[index]));
    }
  }
  public interface OfRef
  {
    public static final Object[] DEFAULT_ARR=new Object[]{};
    @SuppressWarnings("rawtypes")
    public static final Comparable[] DEFAULT_COMPARABLE_ARR=new Comparable[]{};
    public static void fill(Object[] arr,int begin,int end,Object val){
      for(;;){
        arr[begin]=val;
        if(begin==end)
        {
          return;
        }
        ++begin;
      }
    }
    public static void writeArray(Object[] arr,int begin,int end,ObjectOutput out) throws IOException{
      for(;;++begin){
        out.writeObject(arr[begin]);
        if(begin==end){
          return;
        }
      }
    }
    public static void readArray(Object[] arr,int begin,int end,ObjectInput in) throws IOException,ClassNotFoundException{
      for(;;++begin){
        arr[begin]=in.readObject();
        if(begin==end){
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    public static <E> void uncheckedReplaceAll(Object[] arr,int offset,int bound,UnaryOperator<E> operator)
    {
      for(;;)
      {
        arr[offset]=operator.apply((E)arr[offset]);
        if(++offset==bound)
        {
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    public static <E> void ascendingForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;++begin)
      {
        action.accept((E)arr[begin]);
        if(begin==end)
        {
          return;
        }
      }
    }
    @SuppressWarnings("unchecked")
    public static <E> void descendingForEach(Object[] arr,int begin,int end,Consumer<? super E> action)
    {
      for(;;--end)
      {
        action.accept((E)arr[end]);
        if(begin==end)
        {
          return;
        }
      }
    }
    public static int ascendingSeqHashCode(Object[] arr,int begin,int end)
    {
      for(int hash=31+
        Objects.hashCode(arr[begin])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          Objects.hashCode(arr[++begin])
          );
      }
    }
    public static int descendingSeqHashCode(Object[] arr,int begin,int end)
    {
      for(int hash=31+
        Objects.hashCode(arr[end])
        ;;)
      {
        if(begin==end)
        {
          return hash;
        }
        hash=hash*31+(
          Objects.hashCode(arr[--end])
          );
      }
    }
    public static void ascendingToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(;;++begin)
      {
        builder.append(arr[begin]);
        if(begin==end)
        {
          return;
        }
        builder.append(',').append(' ');
      }
    }
    public static void descendingToString(Object[] arr,int begin,int end,StringBuilder builder)
    {
      for(;;--end)
      {
        builder.append(arr[end]);
        if(begin==end)
        {
          return;
        }
        builder.append(',').append(' ');
      }
    }
    public static boolean uncheckedcontains (Object[] arr,int begin,int end
    ,Predicate<Object> pred
    )
    {
      for(;;++begin)
      {
        if
        (
        pred.test(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOf (Object[] arr,int bound
    ,Predicate<Object> pred
    )
    {
      for(int offset=0;;)
      {
        if
        (
        pred.test(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (Object[] arr,int bound
    ,Predicate<Object> pred
    )
    {
      for(;;)
      {
        if
        (
        pred.test(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOf (Object[] arr,int offset,int length
    ,Predicate<Object> pred
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        pred.test(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOf (Object[] arr,int offset,int length
    ,Predicate<Object> pred
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        pred.test(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearch (Object[] arr,int bound
    ,Predicate<Object> pred
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        pred.test(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsNull(Object[] arr,int begin,int end
    )
    {
      for(;;++begin)
      {
        if
        (
        arr[begin]==null
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfNull(Object[] arr,int bound
    )
    {
      for(int offset=0;;)
      {
        if
        (
        arr[offset]==null
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNull(Object[] arr,int bound
    )
    {
      for(;;)
      {
        if
        (
        arr[--bound]==null
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfNull(Object[] arr,int offset,int length
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        arr[i]==null
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNull(Object[] arr,int offset,int length
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        arr[length]==null
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchNull(Object[] arr,int bound
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        arr[i]==null
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static boolean uncheckedcontainsNonNull(Object[] arr,int begin,int end
    ,Object nonNull
    )
    {
      for(;;++begin)
      {
        if
        (
        nonNull.equals(arr[begin])
        )
        {
          return true;
        }
        if(begin==end)
        {
          return false;
        }
      }
    }
    public static int uncheckedindexOfNonNull(Object[] arr,int bound
    ,Object nonNull
    )
    {
      for(int offset=0;;)
      {
        if
        (
        nonNull.equals(arr[offset])
        )
        {
          return offset;
        }
        if(++offset==bound)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNonNull(Object[] arr,int bound
    ,Object nonNull
    )
    {
      for(;;)
      {
        if
        (
        nonNull.equals(arr[--bound])
        )
        {
          return bound;
        }
        if(bound==0)
        {
          return -1;
        }
      }
    }
    public static int uncheckedindexOfNonNull(Object[] arr,int offset,int length
    ,Object nonNull
    )
    {
      int i;
      for(i=offset,length+=offset;;)
      {
        if
        (
        nonNull.equals(arr[i])
        )
        {
          return i-offset;
        }
        if(++i==length)
        {
          return -1;
        }
      }
    }
    public static int uncheckedlastIndexOfNonNull(Object[] arr,int offset,int length
    ,Object nonNull
    )
    {
      for(length+=(offset-1);;--length)
      {
        if
        (
        nonNull.equals(arr[length])
        )
        {
          return length-offset;
        }
        if(length==offset)
        {
          return -1;
        }
      }
    }
    public static int uncheckedsearchNonNull(Object[] arr,int bound
    ,Object nonNull
    )
    {
      for(int i=bound-1;;--i)
      {
        if
        (
        nonNull.equals(arr[i])
        )
        {
          return bound-i;
        }
        if(i==0)
        {
          return -1;
        }
      }
    }
    public static void removeIndexAndPullDown(Object[] arr,int index,int newBound)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+1,newBound-index);
      arr[newBound]=null;
    }
    public static int removeRangeAndPullDown(Object[] arr,int rangeHi,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,rangeHi-numRemoved,rangeHi,oldBound-rangeHi);
      nullifyRange(arr,oldBound-1,oldBound-=numRemoved);
      return oldBound;
    }
    public static void reverseRange(Object[] arr,int begin,int end)
    {
      do
      {
        var tmp=arr[begin];
        arr[begin]=arr[end];
        arr[end]=tmp;
      }
      while(++begin<--end);
    }
    public static void nullifyRange(Object[] arr,int end,int begin)
    {
      for(;;++begin){
        arr[begin]=null;
        if(begin==end){ return; }
      }
    }
    @SafeVarargs
    public static <E> IntPredicate getIndexPredicate(Predicate<? super E> predicate,E...arr)
    {
      return index->predicate.test(arr[index]);
    }
  }
}
