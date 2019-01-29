package omni.util;
import java.util.Comparator;
import java.util.function.IntBinaryOperator;
import omni.function.BooleanComparator;
import omni.function.ByteComparator;
import omni.function.CharComparator;
import omni.function.ShortComparator;
import omni.function.LongComparator;
import omni.function.FloatComparator;
import omni.function.DoubleComparator;
public final class SortUtil
{
    public static   void uncheckedsort(boolean[] arr,int begin,int end)
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
      uncheckedSortHelper(arr,begin,end,
      false
      );
    }
    public static   void uncheckedsort(byte[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin<30)
      {
        insertsort(arr,begin,end);
      }
      else
      {
        countingsort(arr,begin,end);
      }
    }
    public static   void uncheckedsort(char[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin>3200)
      {
        countingsort(arr,begin,end);
      }
      else
      {
        dosort(arr,begin,end);
      }
    }
    public static   void uncheckedsort(short[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin>3200)
      {
        countingsort(arr,begin,end);
      }
      else
      {
        dosort(arr,begin,end);
      }
    }
    //MACRO noncomparatorSortMethod( )
    //MACRO noncomparatorSortMethod( )
    public static   void uncheckedsort(float[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      for(;;)
      {
        if(!Float.isNaN(arr[end]))
        {
          break;
        }
        if(--end==begin)
        {
          return;
        }
      }
      for(int k=end;--k!=begin;)
      {
        float ak;
        if(Float.isNaN(ak=arr[k]))
        {
          arr[k]=arr[end];
          arr[end]=ak;
          --end;
        }
      }
      dosort(arr,begin,end);
      int hi=end;
      for(;;)
      {
        int middle;
        if(
        (arr[middle=(begin+hi)>>>1])<((float)0.0)
        )
        {
          begin=middle+1;
        }
        else
        {
          hi=middle;
        }
        if(begin<hi)
        {
          break;
        }
      }
      while(begin<=end &&
        (Float.floatToRawIntBits(arr[begin]))<(0)
      )
      {
        ++begin;
      }
      for(int k=begin;++k<=end;)
      {
        float ak;
        if((ak=arr[k])!=(float)0.0)
        {
          break;
        }
        if(
        (Float.floatToRawIntBits(ak))<(0)
        )
        {
          arr[k]=(float)0.0;
          arr[begin]=(float)-0.0;
          ++begin;
        }
      }
    }
    public static   void uncheckedsort(double[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      for(;;)
      {
        if(!Double.isNaN(arr[end]))
        {
          break;
        }
        if(--end==begin)
        {
          return;
        }
      }
      for(int k=end;--k!=begin;)
      {
        double ak;
        if(Double.isNaN(ak=arr[k]))
        {
          arr[k]=arr[end];
          arr[end]=ak;
          --end;
        }
      }
      dosort(arr,begin,end);
      int hi=end;
      for(;;)
      {
        int middle;
        if(
        (arr[middle=(begin+hi)>>>1])<((double)0.0)
        )
        {
          begin=middle+1;
        }
        else
        {
          hi=middle;
        }
        if(begin<hi)
        {
          break;
        }
      }
      while(begin<=end &&
        (Double.doubleToRawLongBits(arr[begin]))<(0)
      )
      {
        ++begin;
      }
      for(int k=begin;++k<=end;)
      {
        double ak;
        if((ak=arr[k])!=(double)0.0)
        {
          break;
        }
        if(
        (Double.doubleToRawLongBits(ak))<(0)
        )
        {
          arr[k]=(double)0.0;
          arr[begin]=(double)-0.0;
          ++begin;
        }
      }
    }
    public static <E> void uncheckedsort(Object[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      int nRemaining;
      if((nRemaining=end-begin)<31)
      {
         binarysort(arr,begin,end,begin+countRunAndMakeAscendingsort(arr,begin,end));
         return;
      }
      final AbstractTimSort ts=new sortObjectTimSort<E>(arr,++nRemaining);
      int minRun=minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscendingsort(arr,begin,end))<minRun)
        {
          int force;
          binarysort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen);
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert begin==end+1;
      ts.mergeForceCollapse();
    }
    public static   void uncheckedreverseSort(boolean[] arr,int begin,int end)
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
      uncheckedSortHelper(arr,begin,end,
      true
      );
    }
    public static   void uncheckedreverseSort(byte[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin<30)
      {
        insertreverseSort(arr,begin,end);
      }
      else
      {
        countingreverseSort(arr,begin,end);
      }
    }
    public static   void uncheckedreverseSort(char[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin>3200)
      {
        countingreverseSort(arr,begin,end);
      }
      else
      {
        doreverseSort(arr,begin,end);
      }
    }
    public static   void uncheckedreverseSort(short[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      if(end-begin>3200)
      {
        countingreverseSort(arr,begin,end);
      }
      else
      {
        doreverseSort(arr,begin,end);
      }
    }
    //MACRO noncomparatorSortMethod( )
    //MACRO noncomparatorSortMethod( )
    public static   void uncheckedreverseSort(float[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      for(;;)
      {
        if(!Float.isNaN(arr[begin]))
        {
          break;
        }
        if(++begin==end)
        {
          return;
        }
      }
      for(int k=begin;++k!=end;)
      {
        float ak;
        if(Float.isNaN(ak=arr[k]))
        {
          arr[k]=arr[begin];
          arr[begin]=ak;
          ++begin;
        }
      }
      doreverseSort(arr,begin,end);
      int hi=end;
      for(;;)
      {
        int middle;
        if(
        (arr[middle=(begin+hi)>>>1])>=((float)0.0)
        )
        {
          begin=middle+1;
        }
        else
        {
          hi=middle;
        }
        if(begin<hi)
        {
          break;
        }
      }
      while(begin<=end &&
        (Float.floatToRawIntBits(arr[begin]))>=(0)
      )
      {
        ++begin;
      }
      for(int k=begin;++k<=end;)
      {
        float ak;
        if((ak=arr[k])!=(float)0.0)
        {
          break;
        }
        if(
        (Float.floatToRawIntBits(ak))>=(0)
        )
        {
          arr[k]=(float)-0.0;
          arr[begin]=(float)0.0;
          ++begin;
        }
      }
    }
    public static   void uncheckedreverseSort(double[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      for(;;)
      {
        if(!Double.isNaN(arr[begin]))
        {
          break;
        }
        if(++begin==end)
        {
          return;
        }
      }
      for(int k=begin;++k!=end;)
      {
        double ak;
        if(Double.isNaN(ak=arr[k]))
        {
          arr[k]=arr[begin];
          arr[begin]=ak;
          ++begin;
        }
      }
      doreverseSort(arr,begin,end);
      int hi=end;
      for(;;)
      {
        int middle;
        if(
        (arr[middle=(begin+hi)>>>1])>=((double)0.0)
        )
        {
          begin=middle+1;
        }
        else
        {
          hi=middle;
        }
        if(begin<hi)
        {
          break;
        }
      }
      while(begin<=end &&
        (Double.doubleToRawLongBits(arr[begin]))>=(0)
      )
      {
        ++begin;
      }
      for(int k=begin;++k<=end;)
      {
        double ak;
        if((ak=arr[k])!=(double)0.0)
        {
          break;
        }
        if(
        (Double.doubleToRawLongBits(ak))>=(0)
        )
        {
          arr[k]=(double)-0.0;
          arr[begin]=(double)0.0;
          ++begin;
        }
      }
    }
    public static <E> void uncheckedreverseSort(Object[] arr,int begin,int end)
    {
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert begin<end;
      int nRemaining;
      if((nRemaining=end-begin)<31)
      {
         binaryreverseSort(arr,begin,end,begin+countRunAndMakeAscendingreverseSort(arr,begin,end));
         return;
      }
      final AbstractTimSort ts=new reverseSortObjectTimSort<E>(arr,++nRemaining);
      int minRun=minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscendingreverseSort(arr,begin,end))<minRun)
        {
          int force;
          binaryreverseSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen);
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert begin==end+1;
      ts.mergeForceCollapse();
    }
  public static   void uncheckedcomparatorSort(boolean[] arr,int begin,int end,BooleanComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    var firstVal=arr[begin];
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
  public static   void uncheckedcomparatorSort(byte[] arr,int begin,int end,ByteComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortbyteTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(char[] arr,int begin,int end,CharComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortcharTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(short[] arr,int begin,int end,ShortComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortshortTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(int[] arr,int begin,int end,IntBinaryOperator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortintTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(long[] arr,int begin,int end,LongComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortlongTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(float[] arr,int begin,int end,FloatComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortfloatTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static   void uncheckedcomparatorSort(double[] arr,int begin,int end,DoubleComparator sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortdoubleTimSort (arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  public static <E> void uncheckedcomparatorSort(Object[] arr,int begin,int end,Comparator<? super E> sorter)
  {
    //assert sorter!=null;
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert end-begin>0;
    int nRemaining;
    if((nRemaining=end-begin)<31)
    {
       binarycomparatorSort(arr,begin,end,begin+countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter),sorter);
       return;
    }
    final AbstractTimSort ts=new comparatorSortObjectTimSort<E>(arr,sorter,++nRemaining);
    int minRun=minRunLength(nRemaining);
    int runLen;
    do
    {
      if((runLen=countRunAndMakeAscendingcomparatorSort(arr,begin,end,sorter))<minRun)
      {
        int force;
        binarycomparatorSort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
        runLen=force;
      }
      ts.mergeCollapse(begin,runLen);
      begin+=runLen;
    }
    while((nRemaining-=runLen)!=0);
    //assert begin==end+1;
    ts.mergeForceCollapse();
  }
  private SortUtil()
  {
    super();
    //private constructor
  }
  private static int minRunLength(int n)
  {
    //assert n>=0;
    int r=0;
    while(n>31)
    {
      r|=(n&1);
      n>>=1;
    }
    return n+r;
  }
  private static void insertsort(byte[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      byte aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(char[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      char aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      short aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(int[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      int aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(long[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      long aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(float[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      float aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertsort(double[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      double aj;
      while(
      (ai)<(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(byte[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      byte aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(char[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      char aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      short aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(int[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      int aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(long[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      long aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(float[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      float aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void insertreverseSort(double[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      double aj;
      while(
      (ai)>=(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(--j==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(char[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      char a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      char ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    char ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertsort(short[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      short a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      short ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertsort(int[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      int a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      int ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    int ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertsort(long[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      long a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      long ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    long ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertsort(float[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      float a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      float ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    float ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertsort(double[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])<(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      double a1,a2;
      if(
      (a1=arr[k])<(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      double ak;
      while(
      (a1)<(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    double ae,last=arr[end];
    while(
    (last)<(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(char[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      char a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      char ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    char ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(short[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      short a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      short ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(int[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      int a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      int ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    int ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(long[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      long a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      long ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    long ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(float[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      float a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      float ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    float ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static void sentinelInsertreverseSort(double[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>=(arr[begin-1])
      )
      {
        break;
      }
    }
    for(int k=begin;++begin<=end;k=++begin)
    {
      double a1,a2;
      if(
      (a1=arr[k])>=(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      double ak;
      while(
      (a1)>=(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>=(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    double ae,last=arr[end];
    while(
    (last)>=(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  public static void dosort(char[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void dosort(short[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void uncheckedsort(int[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void uncheckedsort(long[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Long.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void dosort(float[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])>(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
        }
        else
        {
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void dosort(double[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quicksortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])<(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])>(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
        }
        else
        {
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])<(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quicksortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      sortmerge(arr,begin,end,run,count);
    }
  }
  public static void doreverseSort(char[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  public static void doreverseSort(short[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  public static void uncheckedreverseSort(int[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  public static void uncheckedreverseSort(long[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        switch(
        Long.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  public static void doreverseSort(float[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])>=(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])<=(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
        }
        else
        {
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  public static void doreverseSort(double[] arr,int begin,int end)
  {
    if(end-begin<286)
    {
      quickreverseSortleftmost(arr,begin,end);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=begin;
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])>=(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k])>=(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])<=(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k>end
            ||
            (arr[k-1])>=(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            var tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
        }
        else
        {
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>=(arr[run[count]-1])
        )
        {
          if(++count==67)
          {
            quickreverseSortleftmost(arr,begin,end);
            return;
          }
        }
        run[count]=k;
      }
      if(count==0 || (count==1 && run[count]>end))
      {
        return;
      }
      if(run[count]<++end)
      {
        run[++count]=end;
      }
      reverseSortmerge(arr,begin,end,run,count);
    }
  }
  private static void quicksortleftmostSinglePivot(char[] arr,int begin,int end,char pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      char ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          char ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      char ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        char ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          char ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(char[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    char val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortleftmostSinglePivot(short[] arr,int begin,int end,short pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          short ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          short ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortleftmostSinglePivot(int[] arr,int begin,int end,int pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      int ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          int ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      int ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        int ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          int ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(int[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    int val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortleftmostSinglePivot(long[] arr,int begin,int end,long pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      long ak;
      switch(
      Long.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          long ag;
          switch(
          Long.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      long ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        long ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          long ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(long[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    long val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortleftmostSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])<(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])>(pivot)
        )
        {
          --great;
        }
        if(
        (ag)<(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        float ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          float ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(float[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    float val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortleftmostSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])<(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])>(pivot)
        )
        {
          --great;
        }
        if(
        (ag)<(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksortleftmost(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksortleftmost(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        double ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          double ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksortleftmost(double[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    double val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(char[] arr,int begin,int end,char pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      char ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          char ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      char ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        char ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          char ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(char[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    char val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(short[] arr,int begin,int end,short pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          short ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          short ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(int[] arr,int begin,int end,int pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      int ak;
      switch(
      Integer.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          int ag;
          switch(
          Integer.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      int ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        int ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          int ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(int[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    int val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(long[] arr,int begin,int end,long pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      long ak;
      switch(
      Long.signum((ak=arr[k])-(pivot))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          long ag;
          switch(
          Long.signum((pivot)-(ag=arr[great]))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      long ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        long ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          long ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(long[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    long val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])<(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])>(pivot)
        )
        {
          --great;
        }
        if(
        (ag)<(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        float ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          float ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(float[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    float val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quicksortSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])<(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])>(pivot)
        )
        {
          --great;
        }
        if(
        (ag)<(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quicksort(arr,begin,less-1);
    quicksort(arr,great+1,end);
  }
  private static void quicksortDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<(pivot1)
    )
    {
    }
    while(
    (arr[--great])>(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])<(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)>(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])>(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)<(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quicksort(arr,begin,less-2);
    quicksort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        double ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          double ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quicksort(arr,less,great);
  }
  private static void quicksort(double[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertsort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    double val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)<(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])<(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)<(val2)
      )
      {
        val3=val2;
        if(
        (tmp)<(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])<(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)<(val3)
      )
      {
        val4=val3;
        if(
        (tmp)<(val2)
        )
        {
          val3=val2;
          if(
          (tmp)<(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quicksortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quicksortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(char[] arr,int begin,int end,char pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      char ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          char ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      char ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        char ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          char ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(char[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    char val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(short[] arr,int begin,int end,short pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          short ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          short ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(int[] arr,int begin,int end,int pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      int ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          int ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      int ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        int ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          int ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(int[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    int val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(long[] arr,int begin,int end,long pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      long ak;
      switch(
      Long.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          long ag;
          switch(
          Long.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      long ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        long ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          long ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(long[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    long val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>=(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])<=(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>=(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        float ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          float ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(float[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    float val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortleftmostSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>=(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])<=(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>=(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSortleftmost(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortleftmostDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSortleftmost(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        double ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          double ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSortleftmost(double[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    double val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(char[] arr,int begin,int end,char pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      char ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          char ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      char ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        char ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          char ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(char[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    char val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(short[] arr,int begin,int end,short pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          short ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          short ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(int[] arr,int begin,int end,int pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      int ak;
      switch(
      Integer.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          int ag;
          switch(
          Integer.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      int ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        int ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          int ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(int[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    int val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(long[] arr,int begin,int end,long pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      long ak;
      switch(
      Long.signum((pivot)-(ak=arr[k]))
      )
      {
      case -1:
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      case 0:
        break;
      default:
        forLoop: for(;;)
        {
          long ag;
          switch(
          Long.signum((ag=arr[great])-(pivot))
          )
          {
            case 0:
              arr[k]=pivot;
              break forLoop;
            default:
              arr[k]=arr[less];
              arr[less]=ag;
              ++less;
              break forLoop;
            case -1:
              --great;
          }
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      long ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        long ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          long ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(long[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    long val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>=(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])<=(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>=(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        float ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          float ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(float[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    float val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void quickreverseSortSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>=(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])<=(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>=(pivot)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=pivot;
        }
        arr[great]=ak;
        --great;
      }
    }
    quickreverseSort(arr,begin,less-1);
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])>=(pivot1)
    )
    {
    }
    while(
    (arr[--great])<=(pivot2)
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>=(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<=(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])<=(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>=(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ag;
          ++less;
        }
        else
        {
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    arr[begin]=arr[less-1];
    arr[less-1]=pivot1;
    arr[end]=arr[great+1];
    arr[great+1]=pivot2;
    quickreverseSort(arr,begin,less-2);
    quickreverseSort(arr,great+2,end);
    if(less<e1 && e5<great)
    {
      while(
      (arr[less])==(pivot1)
      )
      {
        ++less;
      }
      while(
      (arr[great])==(pivot2)
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        double ak;
        if(
        (ak=arr[k])==(pivot1)
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        (ak)==(pivot2)
        )
        {
          double ag;
          while(
          (ag=arr[great])==(pivot2)
          )
          {
            if(great--==k)
            {
              break outer;
            }
          }
          if(
          (ag)==(pivot1)
          )
          {
            arr[k]=arr[less];
            arr[less]=pivot1;
            ++less;
          }
          else
          {
            arr[k]=ag;
          }
          arr[great]=ak;
          --great;
        }
      }
    }
    quickreverseSort(arr,less,great);
  }
  private static void quickreverseSort(double[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      sentinelInsertreverseSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    double val1,val2,val3,val4,val5;
    if(
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>=(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>=(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>=(val1)
      )
      {
        val2=val1;
        val1=tmp;
      }
      else
      {
        val2=tmp;
      }
    }
    if(
    (val4=arr[e4=e3+seventh])>=(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>=(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>=(val1)
        )
        {
          val2=val1;
          val1=tmp;
        }
        else
        {
          val2=tmp;
        }
      }
      else
      {
        val3=tmp;
      }
    }
    if(
    (val5=arr[e5=e4+seventh])>=(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>=(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>=(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>=(val1)
          )
          {
            val2=val1;
            val1=tmp;
          }
          else
          {
            val2=tmp;
          }
        }
        else
        {
          val3=tmp;
        }
      }
      else
      {
        val4=tmp;
      }
    }
    arr[e1]=val1;
    arr[e3]=val3;
    arr[e5]=val5;
    if(
    (val1)==(val2)
    ||
    (val2)==(val3)
    ||
    (val3)==(val4)
    ||
    (val4)==(val5)
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickreverseSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
      quickreverseSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static void sortmerge(char[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    char[] b;
    int ao,bo,blen;
    var work=new char[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void sortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    var work=new short[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void sortmerge(int[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    int[] b;
    int ao,bo,blen;
    var work=new int[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void sortmerge(long[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    long[] b;
    int ao,bo,blen;
    var work=new long[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void sortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    var work=new float[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void sortmerge(double[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    double[] b;
    int ao,bo,blen;
    var work=new double[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])<(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(char[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    char[] b;
    int ao,bo,blen;
    var work=new char[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    var work=new short[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(int[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    int[] b;
    int ao,bo,blen;
    var work=new int[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(long[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    long[] b;
    int ao,bo,blen;
    var work=new long[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    var work=new float[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void reverseSortmerge(double[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    double[] b;
    int ao,bo,blen;
    var work=new double[blen=end-begin];
    if(odd==0)
    {
      ArrCopy.uncheckedCopy(arr,begin,work,0,blen);
      b=arr;
      bo=0;
      arr=work;
      ao=-begin;
    }
    else
    {
      b=work;
      ao=0;
      bo=-begin;
    }
    for(int last;count>1;count=last)
    {
      for(int k=(last=0)+2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>=(arr[p+ao])
          ))
          {
            b[i+bo]=arr[q++ +ao];
          }
          else
          {
            b[i+bo]=arr[p++ +ao];
          }
        }
        run[++last]=hi;
      }
      if((count&1)!=0)
      {
        for(int i=end,lo=run[count-1];--i>=lo;b[i+bo]=arr[i+ao]){}
        run[++last]=end;
      }
      var tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
    }
  }
  private static void countingsort(byte[] arr,int begin,int end)
  {
    int[] count=new int[(Byte.MAX_VALUE+1)-Byte.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]-Byte.MIN_VALUE];
      if(i==end)
      {
        break;
      }
    }
    for(int i=(Byte.MAX_VALUE+1)-Byte.MIN_VALUE;;)
    {
      int s;
      while((s=count[--i])==0){}
      var value=(byte)(i+Byte.MIN_VALUE);
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static void countingsort(char[] arr,int begin,int end)
  {
    int[] count=new int[(Character.MAX_VALUE+1)-Character.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]];
      if(i==end)
      {
        break;
      }
    }
    for(int i=(Character.MAX_VALUE+1)-Character.MIN_VALUE;;)
    {
      int s;
      while((s=count[--i])==0){}
      var value=(char)i;
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static void countingsort(short[] arr,int begin,int end)
  {
    int[] count=new int[(Short.MAX_VALUE+1)-Short.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]-Short.MIN_VALUE];
      if(i==end)
      {
        break;
      }
    }
    for(int i=(Short.MAX_VALUE+1)-Short.MIN_VALUE;;)
    {
      int s;
      while((s=count[--i])==0){}
      var value=(short)(i+Short.MIN_VALUE);
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static void countingreverseSort(byte[] arr,int begin,int end)
  {
    int[] count=new int[(Byte.MAX_VALUE+1)-Byte.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]-Byte.MIN_VALUE];
      if(i==end)
      {
        break;
      }
    }
    for(int i=-1;;)
    {
      int s;
      while((s=count[++i])==0){}
      var value=(byte)(i+Byte.MIN_VALUE);
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static void countingreverseSort(char[] arr,int begin,int end)
  {
    int[] count=new int[(Character.MAX_VALUE+1)-Character.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]];
      if(i==end)
      {
        break;
      }
    }
    for(int i=-1;;)
    {
      int s;
      while((s=count[++i])==0){}
      var value=(char)i;
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static void countingreverseSort(short[] arr,int begin,int end)
  {
    int[] count=new int[(Short.MAX_VALUE+1)-Short.MIN_VALUE];
    for(int i=begin;;++i)
    {
      ++count[arr[i]-Short.MIN_VALUE];
      if(i==end)
      {
        break;
      }
    }
    for(int i=-1;;)
    {
      int s;
      while((s=count[++i])==0){}
      var value=(short)(i+Short.MIN_VALUE);
      for(;;--end)
      {
        arr[end]=value;
        if(end==begin)
        {
          return;
        }
        if(--s==0)
        {
          break;
        }
      }
    }
  }
  private static class comparatorSortbyteTimSort 
    extends AbstractTimSort
  {
    final byte[] arr;
    byte[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new byte[tmpLen];
    }
    private byte[] ensureCapacity(int minCapacity)
    {
      byte[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new byte[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final ByteComparator sorter;
    comparatorSortbyteTimSort(byte[] arr,ByteComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      byte[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((byte)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((byte)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(byte[] arr,int base1,int len1,int base2,int len2
    ,ByteComparator sorter
    )
    {
      byte[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((byte)(arr[cursor2]),(byte)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((byte)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((byte)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(byte[] arr,int base1,int len1,int base2,int len2
    ,ByteComparator sorter
    )
    {
      byte[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((byte)(tmp[cursor2]),(byte)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((byte)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((byte)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortcharTimSort 
    extends AbstractTimSort
  {
    final char[] arr;
    char[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new char[tmpLen];
    }
    private char[] ensureCapacity(int minCapacity)
    {
      char[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new char[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final CharComparator sorter;
    comparatorSortcharTimSort(char[] arr,CharComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      char[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((char)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((char)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(char[] arr,int base1,int len1,int base2,int len2
    ,CharComparator sorter
    )
    {
      char[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((char)(arr[cursor2]),(char)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((char)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((char)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(char[] arr,int base1,int len1,int base2,int len2
    ,CharComparator sorter
    )
    {
      char[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((char)(tmp[cursor2]),(char)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((char)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((char)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortshortTimSort 
    extends AbstractTimSort
  {
    final short[] arr;
    short[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new short[tmpLen];
    }
    private short[] ensureCapacity(int minCapacity)
    {
      short[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new short[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final ShortComparator sorter;
    comparatorSortshortTimSort(short[] arr,ShortComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      short[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((short)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((short)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(short[] arr,int base1,int len1,int base2,int len2
    ,ShortComparator sorter
    )
    {
      short[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((short)(arr[cursor2]),(short)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((short)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((short)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(short[] arr,int base1,int len1,int base2,int len2
    ,ShortComparator sorter
    )
    {
      short[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((short)(tmp[cursor2]),(short)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((short)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((short)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortintTimSort 
    extends AbstractTimSort
  {
    final int[] arr;
    int[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new int[tmpLen];
    }
    private int[] ensureCapacity(int minCapacity)
    {
      int[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new int[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final IntBinaryOperator sorter;
    comparatorSortintTimSort(int[] arr,IntBinaryOperator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      int[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((int)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((int)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(int[] arr,int base1,int len1,int base2,int len2
    ,IntBinaryOperator sorter
    )
    {
      int[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.applyAsInt((int)(arr[cursor2]),(int)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((int)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((int)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(int[] arr,int base1,int len1,int base2,int len2
    ,IntBinaryOperator sorter
    )
    {
      int[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.applyAsInt((int)(tmp[cursor2]),(int)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((int)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((int)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortlongTimSort 
    extends AbstractTimSort
  {
    final long[] arr;
    long[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new long[tmpLen];
    }
    private long[] ensureCapacity(int minCapacity)
    {
      long[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new long[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final LongComparator sorter;
    comparatorSortlongTimSort(long[] arr,LongComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      long[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((long)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((long)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(long[] arr,int base1,int len1,int base2,int len2
    ,LongComparator sorter
    )
    {
      long[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((long)(arr[cursor2]),(long)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((long)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((long)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(long[] arr,int base1,int len1,int base2,int len2
    ,LongComparator sorter
    )
    {
      long[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((long)(tmp[cursor2]),(long)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((long)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((long)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortfloatTimSort 
    extends AbstractTimSort
  {
    final float[] arr;
    float[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new float[tmpLen];
    }
    private float[] ensureCapacity(int minCapacity)
    {
      float[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new float[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final FloatComparator sorter;
    comparatorSortfloatTimSort(float[] arr,FloatComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      float[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((float)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((float)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(float[] arr,int base1,int len1,int base2,int len2
    ,FloatComparator sorter
    )
    {
      float[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((float)(arr[cursor2]),(float)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((float)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((float)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(float[] arr,int base1,int len1,int base2,int len2
    ,FloatComparator sorter
    )
    {
      float[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((float)(tmp[cursor2]),(float)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((float)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((float)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortdoubleTimSort 
    extends AbstractTimSort
  {
    final double[] arr;
    double[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new double[tmpLen];
    }
    private double[] ensureCapacity(int minCapacity)
    {
      double[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new double[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    final DoubleComparator sorter;
    comparatorSortdoubleTimSort(double[] arr,DoubleComparator sorter,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.sorter=sorter;
    }
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      double[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((double)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((double)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    private void mergeLo(double[] arr,int base1,int len1,int base2,int len2
    ,DoubleComparator sorter
    )
    {
      double[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((double)(arr[cursor2]),(double)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((double)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((double)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(double[] arr,int base1,int len1,int base2,int len2
    ,DoubleComparator sorter
    )
    {
      double[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((double)(tmp[cursor2]),(double)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((double)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((double)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class comparatorSortObjectTimSort<E>
    extends AbstractObjectTimSort<E>
  {
    final Comparator<? super E> sorter;
    comparatorSortObjectTimSort(Object[] arr,Comparator<? super E> sorter,int nRemaining)
    {
      super(arr,nRemaining);
      this.sorter=sorter;
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      Object[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightcomparatorSort((E)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
        ,sorter
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftcomparatorSort((E)arr[base1+len1-1],arr,base2,len2,len2-1
           ,sorter
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
               ,sorter
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
               ,sorter
             );
           }
         }
      }
      return stackSize;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2
    ,Comparator<? super E> sorter
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          sorter.compare((E)(arr[cursor2]),(E)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightcomparatorSort((E)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftcomparatorSort((E)tmp[cursor1],arr,cursor2,len2,0
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2
    ,Comparator<? super E> sorter
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          sorter.compare((E)(tmp[cursor2]),(E)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightcomparatorSort((E)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftcomparatorSort((E)arr[cursor1],tmp,tmpBase,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class sortObjectTimSort<E>
    extends AbstractObjectTimSort<E>
  {
    sortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      Object[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightsort((Comparable<E>)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftsort((Comparable<E>)arr[base1+len1-1],arr,base2,len2,len2-1
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
             );
           }
         }
      }
      return stackSize;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          ((Comparable<E>)(arr[cursor2])).compareTo((E)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightsort((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftsort((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          ((Comparable<E>)(tmp[cursor2])).compareTo((E)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightsort((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftsort((Comparable<E>)arr[cursor1],tmp,tmpBase,len2,len2-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static class reverseSortObjectTimSort<E>
    extends AbstractObjectTimSort<E>
  {
    reverseSortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int i,int stackSize,int[] runLen)
    {
      //assert stackSize>=2;
      //assert i>=0;
      //assert i==stackSize-2 || i==stackSize-3;
      int base1,len1,base2,len2;
      int[] runBase=this.runBase;
      //assert runLen[i]>0 && runLen[i+1]>0;
      //assert runBase[i]+runLen[i]==runBase[i+1];
      runLen[i]=(len1=runLen[i])+(len2=runLen[i+1]);
      if(i==stackSize-3)
      {
        runBase[i+1]=runBase[i+2];
        runLen[i+1]=runLen[i+2];
      }
      --stackSize;
      Object[] arr;
      int k;
      base1=(base1=runBase[i])+(k=SortUtil.gallopRightreverseSort((Comparable<E>)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
      ));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=SortUtil.gallopLeftreverseSort((Comparable<E>)arr[base1+len1-1],arr,base2,len2,len2-1
         ))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2
             );
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2
             );
           }
         }
      }
      return stackSize;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpBase,len1);
      arr[dest++]=arr[cursor2++];
      if(--len2==0)
      {
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
        return;
      }
      if(len1==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        return;
      }
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len1>1 && len2>0;
          if(
          ((Comparable<E>)(arr[cursor2])).compareTo((E)(tmp[cursor1]))<0
          )
          {
            arr[dest++]=arr[cursor2++];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest++]=tmp[cursor1++];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len1>1 && len2>0;
          if((count1=SortUtil.gallopRightreverseSort((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=SortUtil.gallopLeftreverseSort((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
            dest+=count2;
            cursor2+=count2;
            if((len2-=count2)==0)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor1++];
          if(len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len1)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
        break;
      default:
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2
    )
    {
      Object[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpBase=this.tmpBase,len2);
      dest=base2+len2-1;
      cursor1=base1+len1-1;
      arr[dest--]=arr[cursor1--];
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        dest-=len1;
        cursor1-=len1;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[tmpBase];
        return;
      }
      int cursor2=tmpBase+len2-1;
      int minGallop=this.minGallop;
      outer:for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          //assert len2>1 && len1>0;
          if(
          ((Comparable<E>)(tmp[cursor2])).compareTo((E)(arr[cursor1]))<0
          )
          {
            arr[dest--]=arr[cursor1--];
            ++count1;
            count2=0;
            if(--len1==0)
            {
              break outer;
            }
          }
          else
          {
            arr[dest--]=tmp[cursor2--];
            ++count2;
            count1=0;
            if(--len2==0)
            {
              break;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          //assert len2>1 && len1>0;
          if((count1=len1-SortUtil.gallopRightreverseSort((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
            if((len1-=count1)==0)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(--len2==1)
          {
            break outer;
          }
          if((count2=len2-SortUtil.gallopLeftreverseSort((Comparable<E>)arr[cursor1],tmp,tmpBase,len2,len2-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<=1)
            {
              break outer;
            }
          }
          arr[dest--]=tmp[cursor2--];
          if(len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=0;
        }
        minGallop+=2;
      }
      this.minGallop=(minGallop<1?1:minGallop);
      switch(len2)
      {
        case 0:
          throw new IllegalArgumentException("Comparison method violates its general contract!");
        case 1:
          //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        break;
      default:
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
  }
  private static abstract class AbstractObjectTimSort<E> extends AbstractTimSort
  {
    final Object[] arr;
    Object[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new Object[tmpLen];
    }
    private Object[] ensureCapacity(int minCapacity)
    {
      Object[] tmp;
      if(tmpLen<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new Object[newSize];
        this.tmpLen=newSize;
        tmpBase=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    AbstractObjectTimSort(Object[] arr,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
    }
  }
  private static abstract class AbstractTimSort
  {
    int tmpLen;
    int tmpBase;
    int stackSize;
    int minGallop;
    int[] runBase;
    int[] runLen;
    abstract void initTmpArr(int tmpLen);
    AbstractTimSort(int nRemaining)
    {
      int tmpLen;
      initTmpArr(tmpLen=nRemaining<512?nRemaining>>>1:256);
      this.tmpLen=tmpLen;
      this.tmpBase=0;
      this.stackSize=0;
      this.minGallop=7;
      this.runBase=new int[nRemaining=(nRemaining<120?5:nRemaining<1542?10:nRemaining<119151?24:49)];
      this.runLen=new int[nRemaining];
    }
    abstract int mergeAt(int n,int stackSize,int[] runLen);
    private void mergeCollapse(int runBase,int runLength)
    {
      int stackSize;
      this.runBase[stackSize=this.stackSize]=runBase;
      int[] runLen;
      (runLen=this.runLen)[stackSize]=runLength;
      if(++stackSize>1)
      {
        int n;
        do
        {
          if((n=stackSize-2)>0 && runLen[n-1]<=runLen[n]+runLen[n+1] ||
                           n>1 && runLen[n-2]<=runLen[n]+runLen[n-1])
          {
            if(runLen[n-1]<runLen[n+1])
            {
              --n;
            }
          }
          else if(n<0 ||runLen[n]>runLen[n+1])
          {
            break;
          }
        }
        while((stackSize=mergeAt(n,stackSize,runLen))>1);
      }
      this.stackSize=stackSize;
    }
    private void mergeForceCollapse()
    {
      int stackSize;
      if((stackSize=this.stackSize)>1)
      {
        int[] runLen=this.runLen;
        int n;
        do
        {
          if((n=stackSize-2)>0 && runLen[n-1]<runLen[n+1])
          {
            --n;
          }
        }
        while((stackSize=mergeAt(n,stackSize,runLen))>1);
      }
    }
  }
  private static void reverseRange(byte[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(char[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(short[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(int[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(long[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(float[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(double[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static void reverseRange(Object[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      var tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static   void binarycomparatorSort(byte[] arr,int lo,int hi,int begin
  ,ByteComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(byte)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((byte)(pivot),(byte)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(char[] arr,int lo,int hi,int begin
  ,CharComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(char)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((char)(pivot),(char)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(short[] arr,int lo,int hi,int begin
  ,ShortComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(short)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((short)(pivot),(short)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(int[] arr,int lo,int hi,int begin
  ,IntBinaryOperator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(int)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.applyAsInt((int)(pivot),(int)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(long[] arr,int lo,int hi,int begin
  ,LongComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(long)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((long)(pivot),(long)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(float[] arr,int lo,int hi,int begin
  ,FloatComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(float)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((float)(pivot),(float)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   void binarycomparatorSort(double[] arr,int lo,int hi,int begin
  ,DoubleComparator sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(double)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((double)(pivot),(double)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  @SuppressWarnings("unchecked")
  private static <E> void binarycomparatorSort(Object[] arr,int lo,int hi,int begin
  ,Comparator<? super E> sorter
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(E)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        sorter.compare((E)(pivot),(E)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  @SuppressWarnings("unchecked")
  private static <E> void binarysort(Object[] arr,int lo,int hi,int begin
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(Comparable<E>)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        ((Comparable<E>)(pivot)).compareTo((E)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  @SuppressWarnings("unchecked")
  private static <E> void binaryreverseSort(Object[] arr,int lo,int hi,int begin
  )
  {
    //assert lo < begin;
    for(;begin<hi;++begin)
    {
      final var pivot=(Comparable<E>)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(
        ((Comparable<E>)(pivot)).compareTo((E)(arr[mid=(left+right)>>>1]))<0
        )
        {
          right=mid;
        }
        else
        {
          left=mid+1;
        }
      }
      ArrCopy.uncheckedCopy(arr,left,arr,left+1,begin-left);
      arr[left]=pivot;
    }
  }
  private static   int countRunAndMakeAscendingcomparatorSort(byte[] arr,int begin,int end
  ,ByteComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((byte)(arr[runHi=begin+1]),(byte)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((byte)(arr[runHi]),(byte)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((byte)(arr[runHi]),(byte)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(char[] arr,int begin,int end
  ,CharComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((char)(arr[runHi=begin+1]),(char)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((char)(arr[runHi]),(char)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((char)(arr[runHi]),(char)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(short[] arr,int begin,int end
  ,ShortComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((short)(arr[runHi=begin+1]),(short)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((short)(arr[runHi]),(short)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((short)(arr[runHi]),(short)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(int[] arr,int begin,int end
  ,IntBinaryOperator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.applyAsInt((int)(arr[runHi=begin+1]),(int)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.applyAsInt((int)(arr[runHi]),(int)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.applyAsInt((int)(arr[runHi]),(int)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(long[] arr,int begin,int end
  ,LongComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((long)(arr[runHi=begin+1]),(long)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((long)(arr[runHi]),(long)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((long)(arr[runHi]),(long)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(float[] arr,int begin,int end
  ,FloatComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((float)(arr[runHi=begin+1]),(float)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((float)(arr[runHi]),(float)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((float)(arr[runHi]),(float)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int countRunAndMakeAscendingcomparatorSort(double[] arr,int begin,int end
  ,DoubleComparator sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((double)(arr[runHi=begin+1]),(double)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((double)(arr[runHi]),(double)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((double)(arr[runHi]),(double)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  @SuppressWarnings("unchecked")
  private static <E> int countRunAndMakeAscendingcomparatorSort(Object[] arr,int begin,int end
  ,Comparator<? super E> sorter
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    sorter.compare((E)(arr[runHi=begin+1]),(E)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        sorter.compare((E)(arr[runHi]),(E)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        sorter.compare((E)(arr[runHi]),(E)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  @SuppressWarnings("unchecked")
  private static <E> int countRunAndMakeAscendingsort(Object[] arr,int begin,int end
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    ((Comparable<E>)(arr[runHi=begin+1])).compareTo((E)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  @SuppressWarnings("unchecked")
  private static <E> int countRunAndMakeAscendingreverseSort(Object[] arr,int begin,int end
  )
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    //assert sorter!=null;
    int runHi;
    if(
    ((Comparable<E>)(arr[runHi=begin+1])).compareTo((E)(arr[begin]))<0
    )
    {
      for(;;)
      {
        if(runHi==end)
        {
          reverseRange(arr,begin,runHi++);
          break;
        }
        if(
        ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[++runHi]))<=0
        )
        {
          reverseRange(arr,begin,runHi-1);
          break;
        }
      }
    }
    else
    {
      for(;;)
      {
        if(runHi==end)
        {
          ++runHi;
          break;
        }
        if(
        ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[++runHi]))>0
        )
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static   int gallopLeftcomparatorSort(byte key,byte[] arr,int base,int len,int hint
  ,ByteComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((byte)(arr[base+hint]),(byte)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((byte)(arr[base+hint+ofs]),(byte)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((byte)(arr[base+hint-ofs]),(byte)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((byte)(arr[base+(m=lastOfs+(diff>>>1))]),(byte)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(char key,char[] arr,int base,int len,int hint
  ,CharComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((char)(arr[base+hint]),(char)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((char)(arr[base+hint+ofs]),(char)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((char)(arr[base+hint-ofs]),(char)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((char)(arr[base+(m=lastOfs+(diff>>>1))]),(char)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(short key,short[] arr,int base,int len,int hint
  ,ShortComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((short)(arr[base+hint]),(short)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((short)(arr[base+hint+ofs]),(short)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((short)(arr[base+hint-ofs]),(short)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((short)(arr[base+(m=lastOfs+(diff>>>1))]),(short)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(int key,int[] arr,int base,int len,int hint
  ,IntBinaryOperator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.applyAsInt((int)(arr[base+hint]),(int)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.applyAsInt((int)(arr[base+hint+ofs]),(int)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.applyAsInt((int)(arr[base+hint-ofs]),(int)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.applyAsInt((int)(arr[base+(m=lastOfs+(diff>>>1))]),(int)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(long key,long[] arr,int base,int len,int hint
  ,LongComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((long)(arr[base+hint]),(long)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((long)(arr[base+hint+ofs]),(long)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((long)(arr[base+hint-ofs]),(long)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((long)(arr[base+(m=lastOfs+(diff>>>1))]),(long)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(float key,float[] arr,int base,int len,int hint
  ,FloatComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((float)(arr[base+hint]),(float)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((float)(arr[base+hint+ofs]),(float)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((float)(arr[base+hint-ofs]),(float)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((float)(arr[base+(m=lastOfs+(diff>>>1))]),(float)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopLeftcomparatorSort(double key,double[] arr,int base,int len,int hint
  ,DoubleComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((double)(arr[base+hint]),(double)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((double)(arr[base+hint+ofs]),(double)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((double)(arr[base+hint-ofs]),(double)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((double)(arr[base+(m=lastOfs+(diff>>>1))]),(double)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopLeftcomparatorSort(E key,Object[] arr,int base,int len,int hint
  ,Comparator<? super E> sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((E)(arr[base+hint]),(E)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      sorter.compare((E)(arr[base+hint+ofs]),(E)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((E)(arr[base+hint-ofs]),(E)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((E)(arr[base+(m=lastOfs+(diff>>>1))]),(E)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(byte key,byte[] arr,int base,int len,int hint
  ,ByteComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((byte)(key),(byte)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((byte)(key),(byte)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((byte)(key),(byte)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((byte)(key),(byte)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(char key,char[] arr,int base,int len,int hint
  ,CharComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((char)(key),(char)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((char)(key),(char)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((char)(key),(char)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((char)(key),(char)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(short key,short[] arr,int base,int len,int hint
  ,ShortComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((short)(key),(short)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((short)(key),(short)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((short)(key),(short)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((short)(key),(short)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(int key,int[] arr,int base,int len,int hint
  ,IntBinaryOperator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.applyAsInt((int)(key),(int)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.applyAsInt((int)(key),(int)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.applyAsInt((int)(key),(int)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.applyAsInt((int)(key),(int)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(long key,long[] arr,int base,int len,int hint
  ,LongComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((long)(key),(long)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((long)(key),(long)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((long)(key),(long)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((long)(key),(long)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(float key,float[] arr,int base,int len,int hint
  ,FloatComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((float)(key),(float)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((float)(key),(float)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((float)(key),(float)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((float)(key),(float)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  private static   int gallopRightcomparatorSort(double key,double[] arr,int base,int len,int hint
  ,DoubleComparator sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((double)(key),(double)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((double)(key),(double)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((double)(key),(double)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((double)(key),(double)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopRightcomparatorSort(E key,Object[] arr,int base,int len,int hint
  ,Comparator<? super E> sorter
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    sorter.compare((E)(key),(E)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      sorter.compare((E)(key),(E)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        sorter.compare((E)(key),(E)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        sorter.compare((E)(key),(E)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopLeftsort(Comparable<E> key,Object[] arr,int base,int len,int hint
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    ((Comparable<E>)(arr[base+hint])).compareTo((E)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      ((Comparable<E>)(arr[base+hint+ofs])).compareTo((E)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        ((Comparable<E>)(arr[base+hint-ofs])).compareTo((E)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        ((Comparable<E>)(arr[base+(m=lastOfs+(diff>>>1))])).compareTo((E)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopRightsort(Comparable<E> key,Object[] arr,int base,int len,int hint
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopLeftreverseSort(Comparable<E> key,Object[] arr,int base,int len,int hint
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    ((Comparable<E>)(arr[base+hint])).compareTo((E)(key))<0
    )
    {
      int maxOfs=len-hint;
      while(ofs < maxOfs &&
      ((Comparable<E>)(arr[base+hint+ofs])).compareTo((E)(key))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    else
    {
      final int maxOfs=hint+1;
      for(;;)
      {
        if(ofs>=maxOfs || 
        ((Comparable<E>)(arr[base+hint-ofs])).compareTo((E)(key))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        ((Comparable<E>)(arr[base+(m=lastOfs+(diff>>>1))])).compareTo((E)(key))<0
        )
        {
          lastOfs=m+1;
        }
        else
        {
          ofs=m;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
  }
  @SuppressWarnings("unchecked")
  private static <E> int gallopRightreverseSort(Comparable<E> key,Object[] arr,int base,int len,int hint
  )
  {
    //assert len > 0 && hint >= 0 && hint < len;
    int ofs=1;
    int lastOfs=0;
    if(
    ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
    )
    {
      int maxOfs=hint+1;
      while(ofs < maxOfs &&
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))<0
      )
      {
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      int tmp=lastOfs;
      lastOfs=hint-ofs;
      ofs=hint-tmp;
    }
    else
    {
      int maxOfs=len-hint;
      for(;;)
      {
        if(ofs>=maxOfs || 
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))<0
        )
        {
          break;
        }
        if((ofs=((lastOfs=ofs)<<1)+1)<=0)
        {
          ofs=maxOfs;
        }
      }
      if(ofs>maxOfs)
      {
        ofs=maxOfs;
      }
      lastOfs+=hint;
      ofs+=hint;
    }
    //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
    int diff;
    if((diff=ofs-(++lastOfs))>0)
    {
      do
      {
        int m;
        if(
        ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+(diff>>>1))]))<0
        )
        {
          ofs=m;
        }
        else
        {
          lastOfs=m+1;
        }
      }
      while((diff=ofs-lastOfs)>0);
    }
    //assert lastOfs==ofs;
    return ofs;
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
