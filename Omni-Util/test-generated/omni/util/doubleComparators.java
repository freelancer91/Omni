package omni.util;
import omni.function.DoubleComparator;
public enum doubleComparators implements DoubleComparator
{
  Ascending
  {
    public int compare(double val1,double val2)
    {
      return Double.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(double val1,double val2)
    {
      return -Double.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(double val1,double val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int compare(double val1,double val2)
    {
      //even bits come first
      var bits2=Double.doubleToRawLongBits(val2);
      if((Double.doubleToRawLongBits(val1)&0b1L)==0L)
      {
        //val1 is even
        if((bits2&0b1L)!=0L)
        {
          //val2 is odd
          return -1;
        }
      }
      else if((bits2&0b1L)==0L)
      {
        //val2 is even, val1 is odd
        return 1; 
      }
      //both even or both odd
      return 0;
    }
  }
}
