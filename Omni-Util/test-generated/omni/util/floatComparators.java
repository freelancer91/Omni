package omni.util;
import omni.function.FloatComparator;
public enum floatComparators implements FloatComparator
{
  Ascending
  {
    public int compare(float val1,float val2)
    {
      return Float.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(float val1,float val2)
    {
      return -Float.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(float val1,float val2)
    {
      return 0;
    }
  },
  Unstable
  {
    public int compare(float val1,float val2)
    {
      //even bits come first
      var bits2=Float.floatToRawIntBits(val2);
      if((Float.floatToRawIntBits(val1)&0b1)==0)
      {
        //val1 is even
        if((bits2&0b1)!=0)
        {
          //val2 is odd
          return -1;
        }
      }
      else if((bits2&0b1)==0)
      {
        //val2 is even, val1 is odd
        return 1; 
      }
      //both even or both odd
      return 0;
    }
  }
}
