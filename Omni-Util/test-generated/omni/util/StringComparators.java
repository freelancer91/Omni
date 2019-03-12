package omni.util;
import java.util.Comparator;
public enum StringComparators implements Comparator
<String>
{
  Ascending
  {
    public int compare(String val1,String val2)
    {
      return val1.compareTo(val2);
    }
  },
  Descending
  {
    public int compare(String val1,String val2)
    {
      return -val1.compareTo(val2);
    }
  },
  Unsorted
  {
    public int compare(String val1,String val2)
    {
      return 0;
    }
  },
}
