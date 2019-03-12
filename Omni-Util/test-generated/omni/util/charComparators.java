package omni.util;
import omni.function.CharComparator;
public enum charComparators implements CharComparator
{
  Ascending
  {
    public int compare(char val1,char val2)
    {
      return Character.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(char val1,char val2)
    {
      return -Character.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(char val1,char val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int compare(char val1,char val2)
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
