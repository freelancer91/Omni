package omni.util;
import omni.function.ShortComparator;
public enum shortComparators implements ShortComparator
{
  Ascending
  {
    public int compare(short val1,short val2)
    {
      return Short.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(short val1,short val2)
    {
      return -Short.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(short val1,short val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int compare(short val1,short val2)
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
