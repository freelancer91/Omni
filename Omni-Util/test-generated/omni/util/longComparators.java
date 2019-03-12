package omni.util;
import omni.function.LongComparator;
public enum longComparators implements LongComparator
{
  Ascending
  {
    public int compare(long val1,long val2)
    {
      return Long.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(long val1,long val2)
    {
      return -Long.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(long val1,long val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int compare(long val1,long val2)
    {
      //even comes first
      if((val1&0b1L)==0L)
      {
        //val 1 is even
        if((val2&0b1L)!=0L)
        {
          //val2 is odd
          return -1;
        }
      }
      else if((val2&0b1L)==0L)
      {
        //val 2 is even, val 1 is odd
        return 1;
      }
      //both even or both odd
      return 0;
    }
  }
}
