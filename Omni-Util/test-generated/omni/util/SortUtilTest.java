package omni.util;
import org.junit.Assert;
import org.junit.Test;
import java.util.Random;
import java.util.Arrays;
import java.util.Objects;
//TODO uncomment when the module bug is fixed
//import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.ShortComparator;
import omni.function.LongComparator;
import omni.function.FloatComparator;
import omni.function.DoubleComparator;
import java.util.function.Supplier;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.DoubleSupplier;
import omni.function.ByteSupplier;
import omni.function.CharSupplier;
import omni.function.ShortSupplier;
import omni.function.FloatSupplier;
public class SortUtilTest
{
  /*//TODO uncomment when they fix the module bug in eclipse
  private static Comparator<String> getAscendingStringComparator()
  {
    return (val1,val2)->
    {
      return
        (
      val1.compareTo(val2)
        );
    };
  }
  */
  private static BooleanComparator getAscendingbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Boolean.compare(val1,val2)
        );
    };
  }
  private static ByteComparator getAscendingbyteComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Byte.compare(val1,val2)
        );
    };
  }
  private static CharComparator getAscendingcharComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Character.compare(val1,val2)
        );
    };
  }
  private static ShortComparator getAscendingshortComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Short.compare(val1,val2)
        );
    };
  }
  private static IntBinaryOperator getAscendingintComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Integer.compare(val1,val2)
        );
    };
  }
  private static LongComparator getAscendinglongComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Long.compare(val1,val2)
        );
    };
  }
  private static FloatComparator getAscendingfloatComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Float.compare(val1,val2)
        );
    };
  }
  private static DoubleComparator getAscendingdoubleComparator()
  {
    return (val1,val2)->
    {
      return
        (
      Double.compare(val1,val2)
        );
    };
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static Comparator<String> getUnsortedStringComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  */
  private static BooleanComparator getUnsortedbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static ByteComparator getUnsortedbyteComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static CharComparator getUnsortedcharComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static ShortComparator getUnsortedshortComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static IntBinaryOperator getUnsortedintComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static LongComparator getUnsortedlongComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static FloatComparator getUnsortedfloatComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  private static DoubleComparator getUnsorteddoubleComparator()
  {
    return (val1,val2)->
    {
      return
        (
        0
        );
    };
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static Comparator<String> getDescendingStringComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      val1.compareTo(val2)
        );
    };
  }
  */
  private static BooleanComparator getDescendingbooleanComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Boolean.compare(val1,val2)
        );
    };
  }
  private static ByteComparator getDescendingbyteComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Byte.compare(val1,val2)
        );
    };
  }
  private static CharComparator getDescendingcharComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Character.compare(val1,val2)
        );
    };
  }
  private static ShortComparator getDescendingshortComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Short.compare(val1,val2)
        );
    };
  }
  private static IntBinaryOperator getDescendingintComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Integer.compare(val1,val2)
        );
    };
  }
  private static LongComparator getDescendinglongComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Long.compare(val1,val2)
        );
    };
  }
  private static FloatComparator getDescendingfloatComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Float.compare(val1,val2)
        );
    };
  }
  private static DoubleComparator getDescendingdoubleComparator()
  {
    return (val1,val2)->
    {
      return
        -
        (
      Double.compare(val1,val2)
        );
    };
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSortedsort(String[] arr,String[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSortedsort(boolean[] arr,boolean[] copy)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedsort(byte[] arr,byte[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedsort(char[] arr,char[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedsort(short[] arr,short[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedsort(int[] arr,int[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedsort(long[] arr,long[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedsort(float[] arr,float[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSortedsort(double[] arr,double[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSortedreverseSort(String[] arr,String[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfRef.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSortedreverseSort(boolean[] arr,boolean[] copy)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    OmniArray.OfBoolean.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedreverseSort(byte[] arr,byte[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfByte.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedreverseSort(char[] arr,char[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfChar.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedreverseSort(short[] arr,short[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfShort.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSortedreverseSort(int[] arr,int[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfInt.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedreverseSort(long[] arr,long[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfLong.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSortedreverseSort(float[] arr,float[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfFloat.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSortedreverseSort(double[] arr,double[] copy)
  {
    Arrays.sort(copy,0,copy.length);
    OmniArray.OfDouble.reverseRange(copy,0,copy.length-1);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  /*//TODO uncomment when they fix the module bug in eclipse
  private static void isSorted(String[] arr,String[] copy,Comparator<? super String> sorter)
  {
    Arrays.sort(copy,0,copy.length,sorter);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  */
  private static void isSorted(boolean[] arr,boolean[] copy,BooleanComparator sorter)
  {
    Boolean[] boxedCopy=new Boolean[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(byte[] arr,byte[] copy,ByteComparator sorter)
  {
    Byte[] boxedCopy=new Byte[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(char[] arr,char[] copy,CharComparator sorter)
  {
    Character[] boxedCopy=new Character[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(short[] arr,short[] copy,ShortComparator sorter)
  {
    Short[] boxedCopy=new Short[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(arr[i]==copy[i]);
    }
  }
  private static void isSorted(int[] arr,int[] copy,IntBinaryOperator sorter)
  {
    Integer[] boxedCopy=new Integer[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::applyAsInt);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSorted(long[] arr,long[] copy,LongComparator sorter)
  {
    Long[] boxedCopy=new Long[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(Objects.equals(arr[i],copy[i]));
    }
  }
  private static void isSorted(float[] arr,float[] copy,FloatComparator sorter)
  {
    Float[] boxedCopy=new Float[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.floatEquals(arr[i],copy[i]));
    }
  }
  private static void isSorted(double[] arr,double[] copy,DoubleComparator sorter)
  {
    Double[] boxedCopy=new Double[copy.length];
    ArrCopy.uncheckedCopy(copy,0,boxedCopy,0,copy.length);
    Arrays.sort(boxedCopy,0,copy.length,sorter::compare);
    ArrCopy.uncheckedCopy(boxedCopy,0,copy,0,copy.length);
    for(int i=0;i<copy.length;++i)
    {
      Assert.assertTrue(TypeUtil.doubleEquals(arr[i],copy[i]));
    }
  }
  private static boolean getRandboolean(Random rand)
  {
    return rand.nextBoolean();
  }
  private static byte getRandbyte(Random rand)
  {
    return (byte)rand.nextLong();
  }
  private static char getRandchar(Random rand)
  {
    return (char)rand.nextLong();
  }
  private static short getRandshort(Random rand)
  {
    return (short)rand.nextLong();
  }
  private static int getRandint(Random rand)
  {
    return (int)rand.nextLong();
  }
  private static long getRandlong(Random rand)
  {
    return (long)rand.nextLong();
  }
  private static float getRandfloat(Random rand)
  {
    return rand.nextFloat();
  }
  private static double getRanddouble(Random rand)
  {
    return rand.nextDouble();
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static String getRandString(Random rand)
  {
    return Long.toString(rand.nextLong());
  }
  */
  private static boolean convertToboolean(int val)
  {
    return val!=0;
  }
  private static byte convertTobyte(int val)
  {
    return (byte)val;
  }
  private static char convertTochar(int val)
  {
    return (char)val;
  }
  private static short convertToshort(int val)
  {
    return (short)val;
  }
  private static int convertToint(int val)
  {
    return (int)val;
  }
  private static long convertTolong(int val)
  {
    return (long)val;
  }
  private static float convertTofloat(int val)
  {
    return (float)val;
  }
  private static double convertTodouble(int val)
  {
    return (double)val;
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static String convertToString(int val)
  {
    return Integer.toString(val);
  }
  */
  private static enum ArrayBuilderboolean
  {
    RANDOM
    {
      void build(boolean[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandboolean(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int pivotPoint=(arr.length/4)+rand.nextInt(arr.length/2);
        for(int i=0;i<pivotPoint;++i)
        {
          arr[i]=false;
        }
        for(int i=pivotPoint;i<arr.length;++i)
        {
          arr[i]=true;
        }
      }
      int getNumRepetitions()
      {
        return 100;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    Descending
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int pivotPoint=(arr.length/4)+rand.nextInt(arr.length/2);
        for(int i=0;i<pivotPoint;++i)
        {
          arr[i]=true;
        }
        for(int i=pivotPoint;i<arr.length;++i)
        {
          arr[i]=false;
        }
      }
      int getNumRepetitions()
      {
        return 100;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ALL_EQUAL
    {
      void build(boolean[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToboolean(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToboolean(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToboolean(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToboolean(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToboolean(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToboolean(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToboolean(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToboolean(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(boolean[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToboolean(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(boolean[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToboolean(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(boolean[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToboolean(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(boolean[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToboolean(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToboolean(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(boolean[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToboolean((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(boolean[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToboolean(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(boolean[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToboolean(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(boolean[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderbyte
  {
    RANDOM
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandbyte(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(byte[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTobyte(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTobyte(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTobyte(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTobyte(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(byte[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(byte[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(byte[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTobyte(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(byte[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTobyte(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(byte[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTobyte(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(byte[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTobyte(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTobyte(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(byte[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(byte[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(byte[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTobyte(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(byte[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderchar
  {
    RANDOM
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandchar(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(char[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTochar(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTochar(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTochar(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTochar(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(char[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(char[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(char[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTochar(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(char[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTochar(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(char[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTochar(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(char[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTochar(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTochar(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(char[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(char[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(char[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTochar(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(char[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuildershort
  {
    RANDOM
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandshort(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(short[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToshort(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToshort(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToshort(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToshort(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(short[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(short[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(short[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToshort(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(short[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToshort(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(short[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToshort(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(short[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToshort(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToshort(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(short[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(short[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(short[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToshort(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(short[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderint
  {
    RANDOM
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandint(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(int[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToint(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToint(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToint(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToint(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(int[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(int[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(int[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToint(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(int[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToint(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(int[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToint(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(int[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToint(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToint(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(int[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(int[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(int[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToint(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(int[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderlong
  {
    RANDOM
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandlong(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(long[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTolong(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTolong(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTolong(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTolong(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(long[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(long[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(long[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTolong(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(long[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTolong(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(long[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTolong(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(long[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTolong(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTolong(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(long[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(long[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(long[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTolong(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(long[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderfloat
  {
    RANDOM
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandfloat(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(float[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTofloat(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTofloat(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTofloat(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTofloat(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(float[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(float[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(float[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTofloat(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(float[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTofloat(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(float[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTofloat(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(float[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTofloat(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTofloat(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(float[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(float[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(float[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTofloat(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(float[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  private static enum ArrayBuilderdouble
  {
    RANDOM
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRanddouble(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(double[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTodouble(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTodouble(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertTodouble(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertTodouble(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(double[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(double[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(double[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertTodouble(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(double[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertTodouble(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(double[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertTodouble(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(double[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertTodouble(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertTodouble(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(double[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(double[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(double[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertTodouble(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(double[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  /*//TODO uncomment when the eclipse module bug is fixed
  private static enum ArrayBuilderString
  {
    RANDOM
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=getRandString(rand);
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    },
    ASCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(m+i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    Descending
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(arr.length-m-i);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    },
    ALL_EQUAL
    {
      void build(String[] arr,int m,Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 0;
      }
      int getMHi(int arrLength)
      {
        return 0;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_ASCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToString(v++);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToString(v++);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,MERGE_DESCENDING
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int v=-1;
        int i=0;
        for(int k=0;k<m;++k)
        {
          v=-1;
          for(int p=0;p<period;++p)
          {
            arr[i++]=convertToString(v--);
          }
        }
        for(int j=1;j<arr.length-1;j++)
        {
          arr[j]=convertToString(v--);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 65;
      }
      int getMHi(int arrLength)
      {
        return 69;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,SAW
    {
      void build(String[] arr,int m,Random rand)
      {
        int incCount=1;
        int decCount=arr.length;
        int i=0;
        int period=m--;
        for(;;)
        {
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(incCount++);
          }
          period+=m;
          for(int k=1;k<=period;k++)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(decCount--);
          }
          period+=m;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_REPEATED
    {
      void build(String[] arr,int m,Random rand)
      {
        int period=arr.length/m;
        int i=0;
        int k=0;
        for(;;)
        {
          for(int t=1;t<=period;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(k);
          }
          if(i>=arr.length)
          {
            return;
          }
          ++k;
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    //FIXME REPEATED and DUPLICATED cause an AIOB exception
    ,REPEATED
    {
      void build(String[] arr, int m, Random rand)
      {
        for(int i=0;i<arr.length;++i)
        {
          arr[i]=convertToString(i%m);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,DUPLICATED
    {
      void build(String[] arr, int m, Random rand)
      {
        for (int i = 0; i < arr.length; i++)
        {
          arr[i] = convertToString(rand.nextInt(m));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SORTED_ORGAN_PIPES
    {
      void build(String[] arr,int m,Random rand)
      {
        int i=0;
        int k=m;
        for(;;)
        {
          for(int t=1;t<=m;++t)
          {
            if(i>=arr.length)
            {
              return;
            }
            arr[i++]=convertToString(k);
          }
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return Math.min(arrLength,7);
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ,ORGAN_PIPES
    {
      void build(String[] arr, int m, Random rand)
      {
        int middle = arr.length / (m + 1);
        for (int i = 0; i < middle; i++) {
            arr[i] = convertToString(i);
        }
        for (int i = middle; i < arr.length; i++) {
            arr[i] = convertToString(arr.length - i - 1);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,STAGGER
    {
      void build(String[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString((i * m + i) % arr.length);
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,PLATEAU
    {
      void build(String[] arr, int m, Random rand) {
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString(Math.min(i, m));
        }
      }
      int getNumRepetitions()
      {
        return 1;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return (2*arrLength)-1;
      }
      int incrementM(int m)
      {
        return m*2;
      }
    }
    ,SHUFFLE
    {
      void build(String[] arr, int m, Random rand) {
        int x = 0, y = 0;
        for (int i = 0; i < arr.length; i++) {
          arr[i] = convertToString(rand.nextBoolean() ? (x += 2) : (y += 2));
        }
      }
      int getNumRepetitions()
      {
        return 1000;
      }
      int getMLo()
      {
        return 1;
      }
      int getMHi(int arrLength)
      {
        return 1;
      }
      int incrementM(int m)
      {
        return m+1;
      }
    }
    ;
    abstract void build(String[] arr,int m,Random rand); 
    abstract int getNumRepetitions();
    abstract int getMLo();
    abstract int getMHi(int arrLength);
    abstract int incrementM(int m);
  }
  */
  private static void testuncheckedsortHelperboolean(int arrLength,Random rand)
  {
    boolean[] golden=new boolean[arrLength];
    for(ArrayBuilderboolean builder:ArrayBuilderboolean.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          boolean[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperbyte(int arrLength,Random rand)
  {
    byte[] golden=new byte[arrLength];
    for(ArrayBuilderbyte builder:ArrayBuilderbyte.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          byte[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperchar(int arrLength,Random rand)
  {
    char[] golden=new char[arrLength];
    for(ArrayBuilderchar builder:ArrayBuilderchar.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          char[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelpershort(int arrLength,Random rand)
  {
    short[] golden=new short[arrLength];
    for(ArrayBuildershort builder:ArrayBuildershort.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          short[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperint(int arrLength,Random rand)
  {
    int[] golden=new int[arrLength];
    for(ArrayBuilderint builder:ArrayBuilderint.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          int[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperlong(int arrLength,Random rand)
  {
    long[] golden=new long[arrLength];
    for(ArrayBuilderlong builder:ArrayBuilderlong.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          long[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperfloat(int arrLength,Random rand)
  {
    float[] golden=new float[arrLength];
    for(ArrayBuilderfloat builder:ArrayBuilderfloat.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          float[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedsortHelperdouble(int arrLength,Random rand)
  {
    double[] golden=new double[arrLength];
    for(ArrayBuilderdouble builder:ArrayBuilderdouble.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          double[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  /*/TODO uncomment when the eclipse module bug is fixed
  private static void testuncheckedsortHelperString(int arrLength,Random rand)
  {
    String[] golden=new String[arrLength];
    for(ArrayBuilderString builder:ArrayBuilderString.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          String[] test=golden.clone();
          SortUtil.uncheckedsort(test,0,arrLength-1);
          isSortedsort(test,golden);
        }
      }
    }
  }
  */
  private static void testuncheckedreverseSortHelperboolean(int arrLength,Random rand)
  {
    boolean[] golden=new boolean[arrLength];
    for(ArrayBuilderboolean builder:ArrayBuilderboolean.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          boolean[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperbyte(int arrLength,Random rand)
  {
    byte[] golden=new byte[arrLength];
    for(ArrayBuilderbyte builder:ArrayBuilderbyte.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          byte[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperchar(int arrLength,Random rand)
  {
    char[] golden=new char[arrLength];
    for(ArrayBuilderchar builder:ArrayBuilderchar.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          char[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelpershort(int arrLength,Random rand)
  {
    short[] golden=new short[arrLength];
    for(ArrayBuildershort builder:ArrayBuildershort.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          short[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperint(int arrLength,Random rand)
  {
    int[] golden=new int[arrLength];
    for(ArrayBuilderint builder:ArrayBuilderint.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          int[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperlong(int arrLength,Random rand)
  {
    long[] golden=new long[arrLength];
    for(ArrayBuilderlong builder:ArrayBuilderlong.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          long[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperfloat(int arrLength,Random rand)
  {
    float[] golden=new float[arrLength];
    for(ArrayBuilderfloat builder:ArrayBuilderfloat.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          float[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  private static void testuncheckedreverseSortHelperdouble(int arrLength,Random rand)
  {
    double[] golden=new double[arrLength];
    for(ArrayBuilderdouble builder:ArrayBuilderdouble.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          double[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  /*/TODO uncomment when the eclipse module bug is fixed
  private static void testuncheckedreverseSortHelperString(int arrLength,Random rand)
  {
    String[] golden=new String[arrLength];
    for(ArrayBuilderString builder:ArrayBuilderString.values())
    {
      for(int m=builder.getMLo(),mHi=builder.getMHi(arrLength);m<=mHi;m=builder.incrementM(m))
      {
        for(int i=0,numReps=builder.getNumRepetitions();i<numReps;++i)
        {
          builder.build(golden,m,rand);
          String[] test=golden.clone();
          SortUtil.uncheckedreverseSort(test,0,arrLength-1);
          isSortedreverseSort(test,golden);
        }
      }
    }
  }
  */
  @Test
  public void testUncheckedSortboolean()
  {
    var rand=new Random(0);
    testuncheckedsortHelperboolean(100,rand);
  }
  @Test
  public void testUncheckedReverseSortboolean()
  {
    var rand=new Random(0);
    testuncheckedreverseSortHelperboolean(100,rand);
  }
  /*//TODO uncomment when the module bug is fixed
  @Test
  public void testUncheckedComparatorSortboolean()
  {
    //TODO
  }
  */
  @Test
  public void testUncheckedSortbyte()
  {
    var rand=new Random(0);
    testuncheckedsortHelperbyte(29,rand);
    testuncheckedsortHelperbyte(1000,rand);
  }
  @Test
  public void testUncheckedReverseSortbyte()
  {
    var rand=new Random(0);
    testuncheckedreverseSortHelperbyte(29,rand);
    testuncheckedreverseSortHelperbyte(1000,rand);
  }
  @Test
  public void testUncheckedsortchar()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelperchar(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortchar()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelperchar(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedsortshort()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelpershort(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortshort()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelpershort(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedsortint()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelperint(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortint()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelperint(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedsortlong()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelperlong(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortlong()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelperlong(lengths[i],rand);
    }
  }
  /*
  @Test
  public void testUncheckedsortfloat()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelperfloat(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortfloat()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelperfloat(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedsortdouble()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedsortHelperdouble(lengths[i],rand);
    }
  }
  @Test
  public void testUncheckedReverseSortdouble()
  {
    var rand=new Random(0);
    int[] lengths=new int[]{286,3201,3202};
    for(int i=0;i<lengths.length;++i)
    {
      testuncheckedreverseSortHelperdouble(lengths[i],rand);
    }
  }
  */
}
