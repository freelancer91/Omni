//TODO combine these implementations into one file
package omni.util;
import omni.function.BooleanComparator;
public final class BooleanSortUtil
{
  private BooleanSortUtil()
  {
     super();
  }
  private static void uncheckedSortHelper(boolean[] arr,int begin,int end,boolean firstVal)
  {
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert begin<end;
    //assert firstVal!=arr[begin];
    //assert firstVal==arr[end];
    int endValCounter=1;
    for(int curr=begin+1;curr!=end;++curr)
    {
      if(arr[curr]^firstVal)
      {
        ++endValCounter;
      }
    }
    for(final boolean endVal=!firstVal;;--end)
    {
      arr[end]=endVal;
      if(--endValCounter==0)
      {
        do
        {
          arr[--end]=firstVal;
        }
        while(end!=begin);
        return;
      }
    }
  }
  public static void uncheckedsort(boolean[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert begin<end;
    while(!arr[begin])
    {
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    while(arr[end])
    {
      --end;
    }
    if(begin==end)
    {
      //already sorted
      return;
    }
    uncheckedSortHelper(arr,begin,end,false);
  }
  public static void uncheckedreverseSort(boolean[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert begin<end;
    while(arr[begin])
    {
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    while(!arr[end])
    {
      --end;
    }
    if(begin==end)
    {
      //already sorted
      return;
    }
    uncheckedSortHelper(arr,begin,end,true);
  }
  public static  void uncheckedcomparatorSort(boolean[] arr,int begin,int end,BooleanComparator sorter)
  {
      //assert sorter!=null;
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert end-begin>0;
      boolean firstVal=arr[begin];
      int newBegin;
      for(newBegin=begin+1;arr[newBegin]==firstVal;++newBegin)
      {
        if(newBegin==end)
        {
          //already sorted
          return;
        }
      }
      switch(Integer.signum(sorter.compare(firstVal,!firstVal)))
      {
        case -1:
          for(int newEnd=end;newEnd!=newBegin;--newEnd)
          {
            if(arr[newEnd]==firstVal)
            {
              uncheckedSortHelper(arr,newBegin,newEnd,firstVal);
              return;
            }
          }
          //already sorted
        case 0:
          //unsorted comparator
          return;
        default:
          int endValCounter=newBegin-begin;
          while(newBegin!=end)
          {
            if(arr[++newBegin]==firstVal)
            {
              ++endValCounter;
            }
          }
          for(;;--end)
          {
            arr[end]=firstVal;
            if(--endValCounter==0)
            {
              do
              {
                arr[--end]=!firstVal;
              }
              while(end!=begin);
              return;
            }
          }
      }
  }
}
