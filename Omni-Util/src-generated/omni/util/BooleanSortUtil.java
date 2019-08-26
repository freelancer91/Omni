package omni.util;
import omni.function.BooleanComparator;
public final class BooleanSortUtil
{
  private BooleanSortUtil()
  {
    super();
  }
  public static void uncheckedSort(boolean[] arr,int offset,int bound,BooleanComparator sorter)
  {
    final boolean lastVal;
    int i;
    if((lastVal=arr[--bound])==arr[i=bound-1])
    {
      do
      {
        if(i==offset)
        {
          //homogenous array
          return;
        }
      }
      while(arr[--i]==lastVal);
    }
    switch(Integer.signum(sorter.compare(lastVal,!lastVal)))
    {
    case 1:
      for(;i!=offset;++offset)
      {
        if(arr[offset]==lastVal)
        {
          uncheckedSortHelper(arr,offset,i,!lastVal);
          return;
        }
      }
    case 0:
      //already sorted
      return;
    default:
      int firstValCounter=1;
      while(i!=offset)
      {
        if(arr[--i]^lastVal)
        {
          ++firstValCounter;
        }
      }
      final boolean firstVal;
      for(arr[bound]=(firstVal=!lastVal);--firstValCounter!=0;arr[--bound]=firstVal){}
      do
      {
        arr[--bound]=lastVal;
      }
      while(offset!=bound);
    }
  }
  public static void uncheckedAscendingSort(boolean[] arr,int offset,int bound)
  {
  while(
      arr[--bound])
    {
      if(bound==offset)
      {
        //already sorted
        return;
      }
    }
    for(;offset!=bound;++offset)
    {
       if(
        arr[offset])
      {
        uncheckedSortHelper(arr,offset,bound,
          false
        );
        return;
      }
    }
    //already sorted
  }
  public static void uncheckedDescendingSort(boolean[] arr,int offset,int bound)
  {
  while(
      !
      arr[--bound])
    {
      if(bound==offset)
      {
        //already sorted
        return;
      }
    }
    for(;offset!=bound;++offset)
    {
       if(
        !
        arr[offset])
      {
        uncheckedSortHelper(arr,offset,bound,
          true
        );
        return;
      }
    }
    //already sorted
  }
  private static void uncheckedSortHelper(boolean[] arr,int begin,int end,boolean firstVal)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    assert firstVal!=arr[begin];
    assert firstVal==arr[end];
    int endValCounter=1;
    for(int curr=begin+1;curr!=end;++curr)
    {
      if(arr[curr]^firstVal)
      {
        ++endValCounter;
      }
    }
    for(final var endVal=!firstVal;;--end)
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
}
