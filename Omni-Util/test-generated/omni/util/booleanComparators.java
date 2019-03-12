package omni.util;
import omni.function.BooleanComparator;
public enum booleanComparators implements BooleanComparator
{
  Ascending
  {
    public int compare(boolean val1,boolean val2)
    {
      return Boolean.compare(val1,val2);
    }
  },
  Descending
  {
    public int compare(boolean val1,boolean val2)
    {
      return -Boolean.compare(val1,val2);
    }
  },
  Unsorted
  {
    public int compare(boolean val1,boolean val2)
    {
      return 0;
    }
  },
}
