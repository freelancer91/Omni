package omni.util;
import java.util.Comparator;
public enum IntegerComparators implements Comparator<Integer>
{
  Ascending
  {
    public int compare(Integer val1,Integer val2)
    {
      return val1.compareTo(val2);
    }
  },
  Descending
  {
    public int compare(Integer val1,Integer val2)
    {
      return -val1.compareTo(val2);
    }
  },
  Unsorted
  {
    public int compare(Integer val1,Integer val2)
    {
      return 0;
    }
  },
}
