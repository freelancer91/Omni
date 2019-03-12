package omni.util;
import java.util.function.IntBinaryOperator;
public enum intComparators implements IntBinaryOperator
{
  Ascending
  {
    public int applyAsInt(int val1,int val2)
    {
      return Integer.compare(val1,val2);
    }
  },
  Descending
  {
    public int applyAsInt(int val1,int val2)
    {
      return -Integer.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int applyAsInt(int val1,int val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int applyAsInt(int val1,int val2)
    {
      //even comes first
      if((val1&0b1)==0)
      {
        //val 1 is even
        if((val2&0b1)!=0)
        {
          //val2 is odd
          return -1;
        }
      }
      else if((val2&0b1)==0)
      {
        //val 2 is even, val 1 is odd
        return 1;
      }
      //both even or both odd
      return 0;
    }
  }
}
