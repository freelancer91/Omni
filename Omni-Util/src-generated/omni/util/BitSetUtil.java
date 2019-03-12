package omni.util;
import omni.function.BytePredicate;
import omni.function.CharPredicate;
import java.util.function.DoublePredicate;
import omni.function.FloatPredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.ShortPredicate;
public interface BitSetUtil
{
  static long[] getBitSet(int count){
    return new long[(count-1>>6)+1];
  }
  static boolean containsword(long word,int val){
    return (word&1L<<val)!=0;
  }
    public static  long markSurvivors(byte[] arr,int srcOffset,int srcBound,BytePredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((byte)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(byte[] arr,int srcOffset,int srcBound,BytePredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((byte)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(char[] arr,int srcOffset,int srcBound,CharPredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((char)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(char[] arr,int srcOffset,int srcBound,CharPredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((char)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(short[] arr,int srcOffset,int srcBound,ShortPredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((short)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(short[] arr,int srcOffset,int srcBound,ShortPredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((short)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(int[] arr,int srcOffset,int srcBound,IntPredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((int)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(int[] arr,int srcOffset,int srcBound,IntPredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((int)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(long[] arr,int srcOffset,int srcBound,LongPredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((long)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(long[] arr,int srcOffset,int srcBound,LongPredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((long)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(float[] arr,int srcOffset,int srcBound,FloatPredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((float)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(float[] arr,int srcOffset,int srcBound,FloatPredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((float)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    public static  long markSurvivors(double[] arr,int srcOffset,int srcBound,DoublePredicate filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((double)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    public static  int markSurvivors(double[] arr,int srcOffset,int srcBound,DoublePredicate filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((double)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
    @SuppressWarnings("unchecked")
    public static <E> long markSurvivors(Object[] arr,int srcOffset,int srcBound,Predicate<E> filter)
    {
      for(long word=0L,marker=1L;;marker<<=1)
      {
        if(!filter.test((E)arr[srcOffset]))
        {
          word|=marker;
        }
        if(++srcOffset==srcBound)
        {
          return word;
        }
      }
    }
    @SuppressWarnings("unchecked")
    public static <E> int markSurvivors(Object[] arr,int srcOffset,int srcBound,Predicate<E> filter,long[] survivorSet)
    {
      for(int numSurvivors=0,wordOffset=0;;)
      {
        long word=0L,marker=1L;
        do
        {
          if(!filter.test((E)arr[srcOffset]))
          {
            word|=marker;
            ++numSurvivors;
          }
          if(++srcOffset==srcBound)
          {
            survivorSet[wordOffset]=word;
            return numSurvivors;
          }
        }
        while((marker<<=1)!=0L);
        survivorSet[wordOffset++]=word;
      }
    }
  private static int pullWordDown(byte[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(byte[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(byte[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(char[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(char[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(char[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(short[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(short[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(short[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(int[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(int[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(int[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(long[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(long[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(long[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(float[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(float[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(float[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(double[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(double[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(double[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
  private static int pullWordDown(Object[] arr,int srcOffset,int dstOffset,long word)
  {
    for(int numTail0s;(numTail0s=Long.numberOfTrailingZeros(word))!=64;ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s))),srcOffset+=numTail0s,dstOffset+=numTail0s,word>>>=numTail0s){}
    return dstOffset;
  }
  public static void pullSurvivorsDown(Object[] arr,int srcOffset,int dstOffset,int dstBound,long word)
  {
    int numTail0s=Long.numberOfTrailingZeros(word);
    do{
      ArrCopy.uncheckedSelfCopy(arr,dstOffset,srcOffset+=numTail0s,numTail0s=Long.numberOfTrailingZeros(~(word>>>=numTail0s)));
      srcOffset+=numTail0s;
      dstOffset+=numTail0s;
    }while((numTail0s=Long.numberOfTrailingZeros(word>>>=numTail0s))!=64);
  }
  public static void pullSurvivorsDown(Object[] arr,int srcOffset,int dstOffset,int dstBound,long[] survivorSet)
  {
    for(int wordOffset=0;(dstOffset=pullWordDown(arr,srcOffset,dstOffset,survivorSet[wordOffset]))!=dstBound;++wordOffset,srcOffset+=64){}
  }
}
