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
  private SortUtil()
  {
    super();
    //private constructor
  }
  public static   void uncheckedsort(boolean[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    while(!arr[begin])
    {
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    do
    {
      if(!arr[end])
      {
        uncheckedSortHelper(arr,begin,end,
        false
        );
        return;
      }
    }
    while(--end!=begin);
    //already sorted
  }
  public static   void uncheckedreverseSort(boolean[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    while(arr[begin])
    {
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    do
    {
      if(arr[end])
      {
        uncheckedSortHelper(arr,begin,end,
        true
        );
        return;
      }
    }
    while(--end!=begin);
    //already sorted
  }
  public static void uncheckedcomparatorSort(boolean[] arr,int begin,int end,BooleanComparator sorter)
  {
    final boolean firstVal;
    int i;
    if((firstVal=arr[begin])==arr[i=begin+1])
    {
      do
      {
        if(i==end)
        {
          return;
        }
      }
      while(arr[++i]==firstVal);
    }
    switch(Integer.signum(sorter.compare(firstVal,!firstVal)))
    {
    case -1:
      for(;i!=end;--end)
      {
        if(arr[end]==firstVal)
        {
          uncheckedSortHelper(arr,i,end,firstVal);
          return;
        }
      }
    case 0:
      return;
    default:
      int firstValCounter=1;
      while(i!=end)
      {
        if(arr[++i]^firstVal)
        {
          ++firstValCounter;
        }
      }
      final boolean lastVal;
      for(arr[begin]=(lastVal=!firstVal);--firstValCounter!=0;arr[++begin]=lastVal){}
      do
      {
        arr[++begin]=firstVal;
      }
      while(begin!=end);
    }
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
  public static   void uncheckedsort(byte[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin<30)
    {
      insertsort(arr,begin,end);
    }
    else
    {
      countingsort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
    }
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  public static   void uncheckedreverseSort(byte[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin<30)
    {
      insertreverseSort(arr,begin,end);
    }
    else
    {
      countingreverseSort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
    }
  }
  private static void insertreverseSort(byte[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      byte aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  public static   void uncheckedsort(char[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin>3200)
    {
      countingsort(arr,begin,end);
    }
    else
    {
      dosort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
    }
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfChar.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void sortmerge(char[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    char[] b;
    int ao,bo,blen;
    var work=new char[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(char[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(char[] arr,int begin,int end,char pivot1, char pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  public static   void uncheckedreverseSort(char[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin>3200)
    {
      countingreverseSort(arr,begin,end);
    }
    else
    {
      doreverseSort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfChar.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void reverseSortmerge(char[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    char[] b;
    int ao,bo,blen;
    var work=new char[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(char[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      char aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(char[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      char a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      char ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    char ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        char ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
  }
  public static   void uncheckedsort(short[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin>3200)
    {
      countingsort(arr,begin,end);
    }
    else
    {
      dosort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfShort.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void sortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    var work=new short[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(short[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  public static   void uncheckedreverseSort(short[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    if(end-begin>3200)
    {
      countingreverseSort(arr,begin,end);
    }
    else
    {
      doreverseSort(arr,begin,end);
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
      do
      {
        arr[end]=value;
        if(--end<begin)
        {
          return;
        }
      }
      while(--s!=0);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfShort.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void reverseSortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    var work=new short[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      short aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(short[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      short a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      short ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        short ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfInt.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void sortmerge(int[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    int[] b;
    int ao,bo,blen;
    var work=new int[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(int[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(int[] arr,int begin,int end,int pivot1, int pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfInt.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void reverseSortmerge(int[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    int[] b;
    int ao,bo,blen;
    var work=new int[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(int[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      int aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(int[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      int a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      int ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    int ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        int ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Long.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfLong.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void sortmerge(long[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    long[] b;
    int ao,bo,blen;
    var work=new long[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(long[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(long[] arr,int begin,int end,long pivot1, long pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        switch(
        Long.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
          break;
        default:
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfLong.reverseRange(arr,run[count],k-1);
          break;
        case 0:
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void reverseSortmerge(long[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    long[] b;
    int ao,bo,blen;
    var work=new long[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(long[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      long aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(long[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      long a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      long ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    long ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        long ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
  }
  public static   void uncheckedsort(float[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    for(;;)
    {
      if(!Float.isNaN(arr[end]))
      {
        break;
      }
      if(--end==begin)
      {
        //already sorted
        return;
      }
    }
    for(int k=end;--k>=begin;)
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
    moveZerossort(arr,begin,end);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfFloat.reverseRange(arr,run[count],k-1);
        }
        else
        {
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void moveZerossort(float[] arr,int begin,int end)
  {
    int hi;
    switch(Integer.signum(begin-(hi=end)))
    {
      case -1:
        //find the first zero, or first positive, or last negative
        do
        {
          int middle;
          if(arr[middle=(begin+hi)>>>1]<0)
          {
            begin=middle+1;
          }
          else
          {
            hi=middle;
          }
        }
        while(begin<hi);
      case 0:
        //skip the last negative or all leading negative zeros
        while(Float.floatToRawIntBits(arr[begin])<0)
        {
          if(++begin>end)
          {
            return;
          }
        }
        //move the negative zeros to the beginning of the sub-range
        for(int p=begin-1;++begin<=end;)
        {
          switch(Float.floatToRawIntBits(arr[begin]))
          {
          default:
            return;
          case Integer.MIN_VALUE:
            arr[begin]=0.0f;
            arr[++p]=-0.0f;
          case 0:
          }
        }
      default:
    }
  }
  private static void sortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    var work=new float[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(float[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(float[] arr,int begin,int end,float pivot1, float pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  public static   void uncheckedreverseSort(float[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    for(;;)
    {
      if(!Float.isNaN(arr[begin]))
      {
        break;
      }
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    for(int k=begin;++k<=end;)
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
    moveZerosreverseSort(arr,begin,end);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])>(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfFloat.reverseRange(arr,run[count],k-1);
        }
        else
        {
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void moveZerosreverseSort(float[] arr,int begin,int end)
  {
    int hi;
    switch(Integer.signum(begin-(hi=end)))
    {
      case -1:
        //find the first zero, or first negative, or last positive
        do
        {
          int middle;
          if(arr[middle=(begin+hi)>>>1]>0)
          {
            begin=middle+1;
          }
          else
          {
            hi=middle;
          }
        }
        while(begin<hi);
      case 0:
        //skip the last positive or all leading positive zeros
        while(Float.floatToRawIntBits(arr[begin])>=0)
        {
          if(++begin>end)
          {
            return;
          }
        }
        //move the positive zeros to the beginning of the sub-range
        for(int p=begin-1;++begin<=end;)
        {
          switch(Float.floatToRawIntBits(arr[begin]))
          {
          default:
            return;
          case 0:
            arr[begin]=-0.0f;
            arr[++p]=0.0f;
          case Integer.MIN_VALUE:
          }
        }
      default:
    }
  }
  private static void reverseSortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    var work=new float[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(float[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      float aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(float[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      float a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      float ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    float ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
  private static void quickreverseSortleftmostSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])<(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>(pivot)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortSinglePivot(float[] arr,int begin,int end,float pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      float ak;
      if(
      (ak=arr[k])>(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot)
      )
      {
        float ag;
        while(
        (ag=arr[great])<(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>(pivot)
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        float ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
  }
  public static   void uncheckedsort(double[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    for(;;)
    {
      if(!Double.isNaN(arr[end]))
      {
        break;
      }
      if(--end==begin)
      {
        //already sorted
        return;
      }
    }
    for(int k=end;--k>=begin;)
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
    moveZerossort(arr,begin,end);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end ||
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
            if(++k==end||
            (arr[k-1])<(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfDouble.reverseRange(arr,run[count],k-1);
        }
        else
        {
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])<(arr[r-1])
        && ++count==67)
        {
          quicksortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        sortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void moveZerossort(double[] arr,int begin,int end)
  {
    int hi;
    switch(Integer.signum(begin-(hi=end)))
    {
      case -1:
        //find the first zero, or first positive, or last negative
        do
        {
          int middle;
          if(arr[middle=(begin+hi)>>>1]<0)
          {
            begin=middle+1;
          }
          else
          {
            hi=middle;
          }
        }
        while(begin<hi);
      case 0:
        //skip the last negative or all leading negative zeros
        while(Double.doubleToRawLongBits(arr[begin])<0)
        {
          if(++begin>end)
          {
            return;
          }
        }
        //move the negative zeros to the beginning of the sub-range
        for(int p=begin-1;++begin<=end;)
        {
         long bits;
         if((bits=Double.doubleToRawLongBits(arr[begin]))==Long.MIN_VALUE)
         {
            arr[begin]=0.0d;
            arr[++p]=-0.0d;
         }
         else if(bits!=0L)
         {
           return;
         }
        }
      default:
    }
  }
  private static void sortmerge(double[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    double[] b;
    int ao,bo,blen;
    var work=new double[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
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
    while((count=last)>1);
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
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertsort(double[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])<=(arr[++begin])
    )
    ;
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
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    if(--less>begin)
    {
    quicksortleftmost(arr,begin,less);
    }
    quicksort(arr,great+1,end);
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
          arr[k]=ag;
        }
        arr[great]=ak;
        --great;
      }
    }
    if(--less>begin)
    {
    quicksort(arr,begin,less);
    }
    quicksort(arr,great+1,end);
  }
  private static void quicksortleftmostDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  private static void quicksortDualPivot(double[] arr,int begin,int end,double pivot1, double pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    (arr[++less])<=(pivot1)
    )
    {
    }
    while(
    (arr[--great])>=(pivot2)
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
    }
    quicksort(arr,less,great);
  }
  public static   void uncheckedreverseSort(double[] arr,int begin,int end)
  {
    assert arr!=null;
    assert begin>=0;
    assert end<arr.length;
    assert begin<end;
    for(;;)
    {
      if(!Double.isNaN(arr[begin]))
      {
        break;
      }
      if(++begin==end)
      {
        //already sorted
        return;
      }
    }
    for(int k=begin;++k<=end;)
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
    moveZerosreverseSort(arr,begin,end);
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
      //checkIfSortedLoop:
      for(int k=begin;k!=end;)
      {
        if(
        (arr[k])>(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end ||
            (arr[k])>(arr[k-1])
            )
            {
              break;
            }
          }
        }
        else
        if(
        (arr[k])<(arr[k+1])
        )
        {
          for(;;)
          {
            if(++k==end||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          OmniArray.OfDouble.reverseRange(arr,run[count],k-1);
        }
        else
        {
          ++k;
          continue;
        }
        int r;
        if(count==0)
        {
          ++count;
        }
        else if(
        (arr[r=run[count]])>(arr[r-1])
        && ++count==67)
        {
          quickreverseSortleftmost(arr,begin,end);
          return;
        }
        run[count]=k;
      }
      if(count!=0)
      {
        reverseSortmerge(arr,begin,end,run,count);
      }
    }
  }
  private static void moveZerosreverseSort(double[] arr,int begin,int end)
  {
    int hi;
    switch(Integer.signum(begin-(hi=end)))
    {
      case -1:
        //find the first zero, or first negative, or last positive
        do
        {
          int middle;
          if(arr[middle=(begin+hi)>>>1]>0)
          {
            begin=middle+1;
          }
          else
          {
            hi=middle;
          }
        }
        while(begin<hi);
      case 0:
        //skip the last positive or all leading positive zeros
        while(Double.doubleToRawLongBits(arr[begin])>=0)
        {
          if(++begin>end)
          {
            return;
          }
        }
        //move the positive zeros to the beginning of the sub-range
        for(int p=begin-1;++begin<=end;)
        {
         long bits;
         if((bits=Double.doubleToRawLongBits(arr[begin]))==0L)
         {
            arr[begin]=-0.0d;
            arr[++p]=0.0d;
         }
         else if(bits!=Long.MIN_VALUE)
         {
           return;
         }
        }
      default:
    }
  }
  private static void reverseSortmerge(double[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    double[] b;
    int ao,bo,blen;
    var work=new double[blen=end-begin+1];
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
    run[++count]=++end;
    int last,k;
    do
    {
      for(last=0,k=2;k<=count;k+=2)
      {
        int hi=run[k];
        int mi;
        for(int i=run[k-2],p=i,q=(mi=run[k-1]);i<hi;++i)
        {
          if(q<hi && (p>=mi ||
          (arr[q+ao])>(arr[p+ao])
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
    while((count=last)>1);
  }
  private static void insertreverseSort(double[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final var ai=arr[i+1];
      double aj;
      while(
      (ai)>(aj=arr[j])
      )
      {
        arr[j+1]=aj;
        if(j--==begin)
        {
          break;
        }
      }
      arr[j+1]=ai;
    }
  }
  private static void sentinelInsertreverseSort(double[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    (arr[begin])>=(arr[++begin])
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      double a1,a2;
      if(
      (a1=arr[k])>(a2=arr[begin])
      )
      {
        a2=a1;
        a1=arr[begin];
      }
      double ak;
      while(
      (a1)>(ak=arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    double ae,last=arr[end];
    while(
    (last)>(ae=arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      var tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      var tmp=val3;
      val3=val2;
      if(
      (tmp)>(val1)
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
    (val4=arr[e4=e3+seventh])>(val3)
    )
    {
      var tmp=val4;
      val4=val3;
      if(
      (tmp)>(val2)
      )
      {
        val3=val2;
        if(
        (tmp)>(val1)
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
    (val5=arr[e5=e4+seventh])>(val4)
    )
    {
      var tmp=val5;
      val5=val4;
      if(
      (tmp)>(val3)
      )
      {
        val4=val3;
        if(
        (tmp)>(val2)
        )
        {
          val3=val2;
          if(
          (tmp)>(val1)
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
  private static void quickreverseSortleftmostSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])<(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>(pivot)
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
    if(--less>begin)
    {
    quickreverseSortleftmost(arr,begin,less);
    }
    quickreverseSort(arr,great+1,end);
  }
  private static void quickreverseSortSinglePivot(double[] arr,int begin,int end,double pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      double ak;
      if(
      (ak=arr[k])>(pivot)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot)
      )
      {
        double ag;
        while(
        (ag=arr[great])<(pivot)
        )
        {
          --great;
        }
        if(
        (ag)>(pivot)
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
    if(--less>begin)
    {
    quickreverseSort(arr,begin,less);
    }
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
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
      (ak=arr[k])>(pivot1)
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      (ak)<(pivot2)
      )
      {
        double ag;
        while(
        (ag=arr[great])<(pivot2)
        )
        {
          if(great--==k)
          {
            break outer;
          }
        }
        if(
        (ag)>(pivot1)
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
    }
    quickreverseSort(arr,less,great);
  }
  public static class comparatorSortbyteTimSort 
    extends AbstractTimSort
  {
    private transient final ByteComparator sorter;
    private transient final byte[] arr;
    private transient byte[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortbyteTimSort(byte[] arr,int nRemaining,ByteComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new byte[nRemaining];
      this.minGallop=7;
    }
    private byte[] ensureCapacity(int minCapacity)
    {
      byte[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new byte[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(byte[] arr,int begin,int end
      ,ByteComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortbyteTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      byte[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((byte)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((byte)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(byte[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      byte[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((byte)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((byte)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(byte[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      byte[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((byte)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((byte)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(byte key,byte[] arr,int base,int len,int hint
      ,ByteComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((byte)(key),(byte)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((byte)(key),(byte)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((byte)(key),(byte)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((byte)(key),(byte)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(byte key,byte[] arr,int base,int len,int hint
      ,ByteComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((byte)(key),(byte)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((byte)(key),(byte)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((byte)(key),(byte)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((byte)(key),(byte)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(byte[] arr,int lo,int hi,int start
      ,ByteComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final byte pivot=(byte)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(byte[] arr,int lo,int hi
      ,ByteComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((byte)(arr[runHi++]),(byte)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((byte)(arr[runHi]),(byte)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfByte.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((byte)(arr[runHi]),(byte)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortcharTimSort 
    extends AbstractTimSort
  {
    private transient final CharComparator sorter;
    private transient final char[] arr;
    private transient char[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortcharTimSort(char[] arr,int nRemaining,CharComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new char[nRemaining];
      this.minGallop=7;
    }
    private char[] ensureCapacity(int minCapacity)
    {
      char[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new char[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(char[] arr,int begin,int end
      ,CharComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortcharTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      char[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((char)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((char)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(char[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      char[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((char)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((char)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(char[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      char[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((char)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((char)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(char key,char[] arr,int base,int len,int hint
      ,CharComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((char)(key),(char)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((char)(key),(char)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((char)(key),(char)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((char)(key),(char)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(char key,char[] arr,int base,int len,int hint
      ,CharComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((char)(key),(char)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((char)(key),(char)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((char)(key),(char)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((char)(key),(char)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(char[] arr,int lo,int hi,int start
      ,CharComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final char pivot=(char)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(char[] arr,int lo,int hi
      ,CharComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((char)(arr[runHi++]),(char)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((char)(arr[runHi]),(char)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfChar.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((char)(arr[runHi]),(char)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortshortTimSort 
    extends AbstractTimSort
  {
    private transient final ShortComparator sorter;
    private transient final short[] arr;
    private transient short[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortshortTimSort(short[] arr,int nRemaining,ShortComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new short[nRemaining];
      this.minGallop=7;
    }
    private short[] ensureCapacity(int minCapacity)
    {
      short[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new short[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(short[] arr,int begin,int end
      ,ShortComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortshortTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      short[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((short)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((short)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(short[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      short[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((short)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((short)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(short[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      short[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((short)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((short)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(short key,short[] arr,int base,int len,int hint
      ,ShortComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((short)(key),(short)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((short)(key),(short)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((short)(key),(short)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((short)(key),(short)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(short key,short[] arr,int base,int len,int hint
      ,ShortComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((short)(key),(short)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((short)(key),(short)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((short)(key),(short)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((short)(key),(short)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(short[] arr,int lo,int hi,int start
      ,ShortComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final short pivot=(short)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(short[] arr,int lo,int hi
      ,ShortComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((short)(arr[runHi++]),(short)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((short)(arr[runHi]),(short)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfShort.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((short)(arr[runHi]),(short)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortintTimSort 
    extends AbstractTimSort
  {
    private transient final IntBinaryOperator sorter;
    private transient final int[] arr;
    private transient int[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortintTimSort(int[] arr,int nRemaining,IntBinaryOperator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new int[nRemaining];
      this.minGallop=7;
    }
    private int[] ensureCapacity(int minCapacity)
    {
      int[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new int[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(int[] arr,int begin,int end
      ,IntBinaryOperator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortintTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      int[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((int)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((int)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(int[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      int[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((int)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((int)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(int[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      int[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((int)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((int)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(int key,int[] arr,int base,int len,int hint
      ,IntBinaryOperator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.applyAsInt((int)(key),(int)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.applyAsInt((int)(key),(int)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.applyAsInt((int)(key),(int)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.applyAsInt((int)(key),(int)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(int key,int[] arr,int base,int len,int hint
      ,IntBinaryOperator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.applyAsInt((int)(key),(int)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.applyAsInt((int)(key),(int)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.applyAsInt((int)(key),(int)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.applyAsInt((int)(key),(int)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(int[] arr,int lo,int hi,int start
      ,IntBinaryOperator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final int pivot=(int)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(int[] arr,int lo,int hi
      ,IntBinaryOperator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.applyAsInt((int)(arr[runHi++]),(int)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.applyAsInt((int)(arr[runHi]),(int)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfInt.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.applyAsInt((int)(arr[runHi]),(int)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortlongTimSort 
    extends AbstractTimSort
  {
    private transient final LongComparator sorter;
    private transient final long[] arr;
    private transient long[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortlongTimSort(long[] arr,int nRemaining,LongComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new long[nRemaining];
      this.minGallop=7;
    }
    private long[] ensureCapacity(int minCapacity)
    {
      long[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new long[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(long[] arr,int begin,int end
      ,LongComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortlongTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      long[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((long)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((long)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(long[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      long[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((long)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((long)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(long[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      long[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((long)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((long)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(long key,long[] arr,int base,int len,int hint
      ,LongComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((long)(key),(long)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((long)(key),(long)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((long)(key),(long)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((long)(key),(long)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(long key,long[] arr,int base,int len,int hint
      ,LongComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((long)(key),(long)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((long)(key),(long)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((long)(key),(long)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((long)(key),(long)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(long[] arr,int lo,int hi,int start
      ,LongComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final long pivot=(long)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(long[] arr,int lo,int hi
      ,LongComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((long)(arr[runHi++]),(long)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((long)(arr[runHi]),(long)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfLong.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((long)(arr[runHi]),(long)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortfloatTimSort 
    extends AbstractTimSort
  {
    private transient final FloatComparator sorter;
    private transient final float[] arr;
    private transient float[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortfloatTimSort(float[] arr,int nRemaining,FloatComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new float[nRemaining];
      this.minGallop=7;
    }
    private float[] ensureCapacity(int minCapacity)
    {
      float[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new float[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(float[] arr,int begin,int end
      ,FloatComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortfloatTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      float[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((float)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((float)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(float[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      float[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((float)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((float)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(float[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      float[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((float)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((float)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(float key,float[] arr,int base,int len,int hint
      ,FloatComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((float)(key),(float)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((float)(key),(float)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((float)(key),(float)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((float)(key),(float)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(float key,float[] arr,int base,int len,int hint
      ,FloatComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((float)(key),(float)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((float)(key),(float)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((float)(key),(float)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((float)(key),(float)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(float[] arr,int lo,int hi,int start
      ,FloatComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final float pivot=(float)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(float[] arr,int lo,int hi
      ,FloatComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((float)(arr[runHi++]),(float)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((float)(arr[runHi]),(float)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfFloat.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((float)(arr[runHi]),(float)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortdoubleTimSort 
    extends AbstractTimSort
  {
    private transient final DoubleComparator sorter;
    private transient final double[] arr;
    private transient double[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    private comparatorSortdoubleTimSort(double[] arr,int nRemaining,DoubleComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new double[nRemaining];
      this.minGallop=7;
    }
    private double[] ensureCapacity(int minCapacity)
    {
      double[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new double[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    public static   void uncheckedsort(double[] arr,int begin,int end
      ,DoubleComparator sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortdoubleTimSort (arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      double[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((double)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((double)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    private void mergeLo(double[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      double[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((double)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((double)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(double[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      double[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((double)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((double)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopLeft(double key,double[] arr,int base,int len,int hint
      ,DoubleComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((double)(key),(double)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((double)(key),(double)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((double)(key),(double)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((double)(key),(double)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   int gallopRight(double key,double[] arr,int base,int len,int hint
      ,DoubleComparator sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((double)(key),(double)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((double)(key),(double)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((double)(key),(double)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((double)(key),(double)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    private static   void binarySort(double[] arr,int lo,int hi,int start
      ,DoubleComparator sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final double pivot=(double)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static   int countRunAndMakeAscending(double[] arr,int lo,int hi
      ,DoubleComparator sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((double)(arr[runHi++]),(double)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((double)(arr[runHi]),(double)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfDouble.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((double)(arr[runHi]),(double)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class comparatorSortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    private final Comparator<? super E> sorter;
    private comparatorSortObjectTimSort(Object[] arr,int nRemaining,Comparator<? super E> sorter)
    {
      super(arr,nRemaining);
      this.sorter=sorter;
    }
    public static <E> void uncheckedsort(Object[] arr,int begin,int end
      ,Comparator<? super E> sorter
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ,sorter),sorter);
         return;
      }
      final AbstractTimSort ts=new comparatorSortObjectTimSort<E>(arr,nRemaining
        ,sorter
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ,sorter
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          ,sorter
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((E)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((E)arr[base1+len1-1],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((E)arr[cursor2],tmp,cursor1,len1,0
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((E)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((E)tmp[cursor2],arr,base1,len1,len1-1
            ,sorter
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((E)arr[cursor1],tmp,tmpOffset,len2,len2-1
            ,sorter
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(E key,Object[] arr,int base,int len,int hint
      ,Comparator<? super E> sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((E)(key),(E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((E)(key),(E)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((E)(key),(E)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((E)(key),(E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(E key,Object[] arr,int base,int len,int hint
      ,Comparator<? super E> sorter
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      sorter.compare((E)(key),(E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          sorter.compare((E)(key),(E)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          sorter.compare((E)(key),(E)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          sorter.compare((E)(key),(E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
      ,Comparator<? super E> sorter
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final E pivot=(E)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int countRunAndMakeAscending(Object[] arr,int lo,int hi
      ,Comparator<? super E> sorter
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      sorter.compare((E)(arr[runHi++]),(E)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((E)(arr[runHi]),(E)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfRef.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          sorter.compare((E)(arr[runHi]),(E)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class sortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    private sortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    public static <E> void uncheckedsort(Object[] arr,int begin,int end
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ));
         return;
      }
      final AbstractTimSort ts=new sortObjectTimSort<E>(arr,nRemaining
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((Comparable<E>)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2,len2-1
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((Comparable<E>)arr[cursor1],tmp,tmpOffset,len2,len2-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))>=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final Comparable<E> pivot=(Comparable<E>)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
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
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int countRunAndMakeAscending(Object[] arr,int lo,int hi
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      ((Comparable<E>)(arr[runHi++])).compareTo((E)(arr[lo]))<0
      )
      {
        if(runHi<hi)
        {
          while(
          ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[runHi-1]))<0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfRef.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[runHi-1]))>=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  public static class reverseSortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    private reverseSortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    public static <E> void uncheckedsort(Object[] arr,int begin,int end
    )
    {
      int nRemaining;
      if((nRemaining=end-begin)<32)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end
         ));
         return;
      }
      final AbstractTimSort ts=new reverseSortObjectTimSort<E>(arr,nRemaining
      );
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end
        ))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen
          );
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      assert begin==end;
      ts.mergeForceCollapse(); 
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      assert stackSize>=4;
      assert n>=0;
      assert n==stackSize-4 || n==stackSize-6;
      assert runLenAndBase[n]>0;
      assert runLenAndBase[n+2]>0;
      assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((Comparable<E>)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
      ));
      assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2,len2-1
        ))!=0)
        {
          assert len2>0;
          if(len1<=len2)
          {
            mergeLo(arr,base1,len1,base2,len2);
          }
          else
          {
            mergeHi(arr,base1,len1,base2,len2);
          }
        }
      }
      return stackSize-2;
    }
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      int cursor2=base2;
      arr[dest++]=arr[cursor2++];
      //TODO make these pre-decrement for performance
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
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len1>1;
          assert len2>0;
          if(
          ((Comparable<E>)(arr[cursor2])).compareTo((E)(tmp[cursor1]))>0
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
            if(--len1==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len1>1;
          assert len2>0;
          if((
          count1=gallopRight((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0
            ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<2)
            {
              break outer;
            }
          }
          arr[dest++]=arr[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0
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
          if(--len1==1)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len1==1)
      {
        assert len2>0;
        ArrCopy.uncheckedSelfCopy(arr,cursor2,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        assert len2==0;
        assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      assert len1>0;
      assert len2>0;
      assert base1+len1==base2;
      Object[] tmp;
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+len1-1;
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      arr[dest--]=arr[cursor1--];
      //TODO make these pre-decrement for performance
      if(--len1==0)
      {
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
        return;
      }
      if(len2==1)
      {
        ArrCopy.uncheckedCopy(arr,cursor1-len1+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
        return;
      }
      int minGallop=this.minGallop;
      outer:
      for(;;)
      {
        int count1=0;
        int count2=0;
        do
        {
          assert len2>1;
          assert len1>0;
          if(
          ((Comparable<E>)(tmp[cursor2])).compareTo((E)(arr[cursor1]))>0
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
            if(--len2==1)
            {
              break outer;
            }
          }
        }
        while((count1|count2)<minGallop);
        do
        {
          assert len2>1;
          assert len1>0;
          if((
          count1=len1-gallopRight((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1
            ))!=0)
          {
            ArrCopy.uncheckedCopy(arr,(cursor1-=count1)+1,arr,(dest-=count1)+1,count1);
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
          if((count2=len2-gallopLeft((Comparable<E>)arr[cursor1],tmp,tmpOffset,len2,len2-1
          ))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
            if((len2-=count2)<2)
            {
              break outer;
            }
          }
          arr[dest--]=arr[cursor1--];
          if(--len1==0)
          {
            break outer;
          }
          --minGallop;
        }
        while(count1>=7 || count2>=7);
        if(minGallop<0)
        {
          minGallop=2;
        }
        else
        {
          minGallop+=2;
        }
      }
      this.minGallop = minGallop < 1 ? 1 : minGallop;
      if(len2==1)
      {
        assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))<0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))>=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))<0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      assert len>0;
      assert hint>=0;
      assert hint<len;
      int ofs=1;
      int lastOfs=0;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(ofs<(maxOfs=hint+1))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-ofs]))>0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        int tmp=lastOfs;
        lastOfs=hint-ofs;
        ofs=hint-tmp;
      }
      else
      {
        int maxOfs;
        if(ofs<(maxOfs=len-hint))
        {
          while(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+ofs]))<=0
          )
          {
            lastOfs=ofs;
            if((ofs=(ofs<<1)+1)<=0 || ofs>=maxOfs)
            {
              ofs=maxOfs;
              break;
            }
          }
        }
        else
        {
          ofs=maxOfs;
        }
        lastOfs+=hint;
        ofs+=hint;
      }
      assert -1<=lastOfs;
      assert lastOfs<ofs;
      assert ofs<=len;
      if(++lastOfs<ofs)
      {
        do
        {
          int m;
          if(
          ((Comparable<E>)(key)).compareTo((E)(arr[base+(m=lastOfs+((ofs-lastOfs)>>>1))]))<=0
          )
          {
            lastOfs=m+1;
          }
          else
          {
            ofs=m;
          }
        }
        while(lastOfs<ofs);
      }
      assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
    )
    {
      assert lo<=start;
      assert start<=hi;
      //TODO streamline
      if(start==lo)
      {
        ++start;
      }
      for(;start<hi;++start)
      {
        final Comparable<E> pivot=(Comparable<E>)arr[start];
        assert lo<=start;
        int left,right;
        if((left=lo)<(right=start))
        {
          do
          {
            int mid;
            if(
            ((Comparable<E>)(pivot)).compareTo((E)(arr[mid=(left+right)>>>1]))>0
            )
            {
              right=mid;
            }
            else
            {
              left=mid+1;
            }
          }
          while(left<right);
        }
        assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int countRunAndMakeAscending(Object[] arr,int lo,int hi
    )
    {
      //TODO streamline
      assert lo<hi;
      int runHi;
      if((runHi=lo+1)==hi)
      {
        return 1;
      }
      if(
      ((Comparable<E>)(arr[runHi++])).compareTo((E)(arr[lo]))>0
      )
      {
        if(runHi<hi)
        {
          while(
          ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[runHi-1]))>0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
        OmniArray.OfRef.reverseRange(arr,lo,runHi-1);
      }
      else
      {
        if(runHi<hi)
        {
          while(
          ((Comparable<E>)(arr[runHi])).compareTo((E)(arr[runHi-1]))<=0
          )
          {
            if(++runHi==hi)
            {
              break;
            }
          }
        }
      }
      return runHi-lo;
    }
  }
  private static abstract class AbstractTimSort
  {
    private transient int stackSize;
    private transient final int runLenAndBase[];
    AbstractTimSort(int nRemaining)
    {
      this.runLenAndBase=new int[nRemaining<120?10:nRemaining<1542?20:nRemaining<119151?48:98];
    }
    private static int minRunLength(int n)
    {
      assert n >= 0;
      int r = 0;      // Becomes 1 if any 1 bits are shifted off
      while (n >= 32)
      {
        r |= (n & 1);
        n >>= 1;
      }
      return n + r;
     }
    abstract int mergeAt(int n,int stackSize,int[] runLenAndBase);
    private void mergeCollapse(int runBase,int runLength)
    {
      int stackSize;
      int[] runLenAndBase;
      (runLenAndBase=this.runLenAndBase)[stackSize=this.stackSize]=runLength;
      runLenAndBase[++stackSize]=runBase;
      ++stackSize;
      int n;
      gotoReturn:
      do
      {
        gotoMergeAt:
        for(;;)
        {
          switch((n=stackSize-4)>>1)
          {
            default:
              if(runLenAndBase[n-4]<=runLenAndBase[n]+runLenAndBase[n-2])
              {
                break;
              }
            case 1:
              if(runLenAndBase[n-2]<=runLenAndBase[n]+runLenAndBase[n+2])
              {
                break;
              }
            case 0:
              if(runLenAndBase[n]<=runLenAndBase[n+2])
              {
                break gotoMergeAt;
              }
            case -1:
             break gotoReturn;
          }
          if(runLenAndBase[n-2]<runLenAndBase[n+2])
          {
            n-=2;
          }
          break gotoMergeAt;
        }
        //gotoMergeAt goes here
      }
      while((stackSize=mergeAt(n,stackSize,runLenAndBase))>2);
      //gotoReturn goes here
      this.stackSize=stackSize;
    }
    private void mergeForceCollapse()
    {
      int stackSize;
      if((stackSize=this.stackSize)>2)
      {
        final var runLenAndBase=this.runLenAndBase;
        int n;
        do
        {
          if((n=stackSize-4)>0&&runLenAndBase[n-2]<runLenAndBase[n+2])
          {
            n-=2;
          }
        }
        while((stackSize=mergeAt(n,stackSize,runLenAndBase))>2);
      }
      //no need to set the stack size field since we are done
    }
  }
  private static abstract class AbstractObjectTimSort<E> extends AbstractTimSort
  {
    transient final Object[] arr;
    transient Object[] tmp;
    transient int tmpLength;
    transient int tmpOffset;
    transient int minGallop;
    AbstractObjectTimSort(Object[] arr,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new Object[nRemaining];
      this.minGallop=7;
    }
    private Object[] ensureCapacity(int minCapacity)
    {
      Object[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new Object[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
  }
}
