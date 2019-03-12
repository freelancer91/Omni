package omni.util;
import java.lang.reflect.Array;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;
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
    public static int removeRangeAndPullDown(boolean[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(byte[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(char[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(short[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(int[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(long[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(float[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(double[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
    public static int removeRangeAndPullDown(Object[] arr,int index,int oldBound,int numRemoved)
    {
      ArrCopy.semicheckedSelfCopy(arr,index,index+=numRemoved,oldBound-index);
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
