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
  private static abstract class AbstractTimSort
  {
    transient int stackSize;
    transient final int runLenAndBase[];
    AbstractTimSort(int nRemaining)
    {
      this.runLenAndBase=new int[nRemaining<120?10:nRemaining<1542?20:nRemaining<119151?48:98];
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
              if(runLenAndBase[n-4]<=runLenAndBase[n] + runLenAndBase[n-2])
              {
                break;
              }
            case 1:
              if(runLenAndBase[n-2]<=runLenAndBase[n] + runLenAndBase[n+2])
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
          if((n=stackSize-4)>0 && runLenAndBase[n-2]<runLenAndBase[n+2])
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
  private static class comparatorSortbooleanTimSort 
    extends AbstractTimSort
  {
    private transient final BooleanComparator sorter;
    private transient final boolean[] arr;
    private transient boolean[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortbooleanTimSort(boolean[] arr,int nRemaining,BooleanComparator sorter)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new boolean[nRemaining];
      this.minGallop=7;
    }
    private boolean[] ensureCapacity(int minCapacity)
    {
      boolean[] tmp;
      if(tmpLength<minCapacity)
      {
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        this.tmp=tmp=new boolean[newSize];
        this.tmpLength=newSize;
        tmpOffset=0;
      }
      else
      {
        tmp=this.tmp;
      }
      return tmp;
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
      int len1,base1,len2,base2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      boolean[] arr;
      int k;
      base1=(base1=runLenAndBase[n+1])+(k=gallopRight((boolean)(arr=this.arr)[base2=runLenAndBase[n+3]],arr,base1,len1,0
        ,sorter
      ));
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((boolean)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
    private void mergeLo(boolean[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
      boolean[] tmp;
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
          //assert len1>1;
          //assert len2=0;
          if(
          sorter.compare((boolean)(arr[cursor2]),(boolean)(tmp[cursor1]))<0
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
          //assert len1>1;
          //assert len2>0;
          if((
          count1=gallopRight((boolean)arr[cursor2],tmp,cursor1,len1,0
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
          if((count2=gallopLeft((boolean)tmp[cursor1],arr,cursor2,len2,0
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(boolean[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
      boolean[] tmp;
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
          //assert len2>1;
          //assert len1=0;
          if(
          sorter.compare((boolean)(tmp[cursor2]),(boolean)(arr[cursor1]))<0
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
          //assert len2>1;
          //assert len1>0;
          if((
          count1=len1-gallopRight((boolean)tmp[cursor2],arr,base1,len1,len1-1
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
          if((count2=len2-gallopLeft((boolean)arr[cursor1],tmp,tmpOffset,len2,len2-1
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(boolean key,boolean[] arr,int base,int len,int hint
      ,BooleanComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(boolean key,boolean[] arr,int base,int len,int hint
      ,BooleanComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortbyteTimSort 
    extends AbstractTimSort
  {
    private transient final ByteComparator sorter;
    private transient final byte[] arr;
    private transient byte[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortbyteTimSort(byte[] arr,int nRemaining,ByteComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((byte)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(byte[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(byte key,byte[] arr,int base,int len,int hint
      ,ByteComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(byte key,byte[] arr,int base,int len,int hint
      ,ByteComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortcharTimSort 
    extends AbstractTimSort
  {
    private transient final CharComparator sorter;
    private transient final char[] arr;
    private transient char[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortcharTimSort(char[] arr,int nRemaining,CharComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((char)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(char[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(char key,char[] arr,int base,int len,int hint
      ,CharComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(char key,char[] arr,int base,int len,int hint
      ,CharComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortshortTimSort 
    extends AbstractTimSort
  {
    private transient final ShortComparator sorter;
    private transient final short[] arr;
    private transient short[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortshortTimSort(short[] arr,int nRemaining,ShortComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((short)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(short[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(short key,short[] arr,int base,int len,int hint
      ,ShortComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(short key,short[] arr,int base,int len,int hint
      ,ShortComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortintTimSort 
    extends AbstractTimSort
  {
    private transient final IntBinaryOperator sorter;
    private transient final int[] arr;
    private transient int[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortintTimSort(int[] arr,int nRemaining,IntBinaryOperator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((int)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(int[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(int key,int[] arr,int base,int len,int hint
      ,IntBinaryOperator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(int key,int[] arr,int base,int len,int hint
      ,IntBinaryOperator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortlongTimSort 
    extends AbstractTimSort
  {
    private transient final LongComparator sorter;
    private transient final long[] arr;
    private transient long[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortlongTimSort(long[] arr,int nRemaining,LongComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((long)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(long[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(long key,long[] arr,int base,int len,int hint
      ,LongComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(long key,long[] arr,int base,int len,int hint
      ,LongComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortfloatTimSort 
    extends AbstractTimSort
  {
    private transient final FloatComparator sorter;
    private transient final float[] arr;
    private transient float[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortfloatTimSort(float[] arr,int nRemaining,FloatComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((float)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(float[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(float key,float[] arr,int base,int len,int hint
      ,FloatComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(float key,float[] arr,int base,int len,int hint
      ,FloatComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortdoubleTimSort 
    extends AbstractTimSort
  {
    private transient final DoubleComparator sorter;
    private transient final double[] arr;
    private transient double[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    comparatorSortdoubleTimSort(double[] arr,int nRemaining,DoubleComparator sorter)
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
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((double)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    private void mergeHi(double[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static   int gallopRight(double key,double[] arr,int base,int len,int hint
      ,DoubleComparator sorter
    )
    {
      //TODO
      return 0;
    }
    private static   int gallopLeft(double key,double[] arr,int base,int len,int hint
      ,DoubleComparator sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class comparatorSortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    private final Comparator<? super E> sorter;
    comparatorSortObjectTimSort(Object[] arr,int nRemaining,Comparator<? super E> sorter)
    {
      super(arr,nRemaining);
      this.sorter=sorter;
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((E)arr[base1+len1-2],arr,base2,len2,len2-1
          ,sorter
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static <E> int gallopRight(E key,Object[] arr,int base,int len,int hint
      ,Comparator<? super E> sorter
    )
    {
      //TODO
      return 0;
    }
    private static <E> int gallopLeft(E key,Object[] arr,int base,int len,int hint
      ,Comparator<? super E> sorter
    )
    {
      //TODO
      return 0;
    }
  }
  private static class sortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    sortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((Comparable<E>)arr[base1+len1-2],arr,base2,len2,len2-1
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      //TODO
      return 0;
    }
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      //TODO
      return 0;
    }
  }
  private static class reverseSortObjectTimSort<E>
    extends  AbstractObjectTimSort<E>
  {
    reverseSortObjectTimSort(Object[] arr,int nRemaining)
    {
      super(arr,nRemaining);
    }
    @SuppressWarnings("unchecked")
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runBaseAndLen[n+1]==runBaseAndLen[n+3];
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
      //assert k>=0;
      if((len1-=k)!=0)
      {
        if((len2=gallopLeft((Comparable<E>)arr[base1+len1-2],arr,base2,len2,len2-1
        ))!=0)
        {
          //assert len2>0;
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
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len1>1;
          //assert len2=0;
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
          //assert len1>1;
          //assert len2>0;
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
        //assert len2>0;
        ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
        arr[dest+len2]=tmp[cursor1];
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      //TODO
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
          //assert len2>1;
          //assert len1=0;
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
          //assert len2>1;
          //assert len1>0;
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
        //assert len1>0;
        ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
        arr[dest]=tmp[cursor2];
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest+(len2-1),len2);
      }
    }
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      //TODO
      return 0;
    }
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint
    )
    {
      //TODO
      return 0;
    }
  }
/* 
MACRODEF TimSort<METHODNAME,ARRTYPE,COMPARATORTYPE,EXPOSEDTYPE>(TYPEPARAMETER)
private static class METHODNAMEARRTYPETimSortTYPEPARAMETER
IFSWITCH ARRTYPE==Object
  extends AbstractARRTYPETimSortTYPEPARAMETER
ELSE
  extends AbstractTimSort
ENDIF
{
IFNOTSWITCH ARRTYPE==Object
  final ARRTYPE[] arr;
  ARRTYPE[] tmp;
  @Override
  void initTmpArr(int tmpLen)
  {
    this.tmp=new ARRTYPE[tmpLen];
  }
  MACRO EnsureCapacity(ARRTYPE)
ENDIF
IFSWITCH METHODNAME==comparatorSort
  final COMPARATORTYPE sorter;
  METHODNAMEARRTYPETimSort(ARRTYPE[] arr,COMPARATORTYPE sorter,int nRemaining)
  {
  IFSWITCH ARRTYPE==Object
    super(arr,nRemaining);
  ELSE
    super(nRemaining);
    this.arr=arr;
  ENDIF
    this.sorter=sorter;
  }
ELSE
  METHODNAMEARRTYPETimSort(ARRTYPE[] arr,int nRemaining)
  {
    super(arr,nRemaining);
  }
ENDIF
  MACRO SuppressUnchecked()
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
    ARRTYPE[] arr;
    int k;
    base1=(base1=runBase[i])+(k=SortUtil.gallopRightMETHODNAME((EXPOSEDTYPE)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0
IFSWITCH METHODNAME==comparatorSort
      ,sorter
ENDIF
    ));
    if((len1-=k)!=0)
    {
       //assert k>=0;
       if((len2=SortUtil.gallopLeftMETHODNAME((EXPOSEDTYPE)arr[base1+len1-1],arr,base2,len2,len2-1
IFSWITCH METHODNAME==comparatorSort
         ,sorter
ENDIF
       ))!=0)
       {
         //assert len2>0;
         if(len1>len2)
         {
           mergeHi(arr,base1,len1,base2,len2
IFSWITCH METHODNAME==comparatorSort
             ,sorter
ENDIF
           );
         }
         else
         {
           mergeLo(arr,base1,len1,base2,len2
IFSWITCH METHODNAME==comparatorSort
             ,sorter
ENDIF
           );
         }
       }
    }
    return stackSize;
  }
  MACRO MergeMethod<Lo>()
  MACRO MergeMethod<Hi>()
}
ENDDEF
  MACRO noncomparatorSortMethod<sort,Object,Comparator<? super E>,E,Object,NULL>(<E>)
  MACRO noncomparatorSortMethod<reverseSort,Object,Comparator<? super E>,E,Object,NULL>(<E>)
  MACRO comparatorSortMethod<comparatorSort,boolean,BooleanComparator,boolean,Boolean,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,byte,ByteComparator,byte,Byte,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,char,CharComparator,char,Character,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,short,ShortComparator,short,Short,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,int,IntBinaryOperator,int,Integer,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,long,LongComparator,long,Long,NULL>( )
  MACRO comparatorSortMethod<comparatorSort,float,FloatComparator,float,Float,Float.floatToRawIntBits>( )
  MACRO comparatorSortMethod<comparatorSort,double,DoubleComparator,double,Double,Double.doubleToRawLongBits>( )
  MACRO comparatorSortMethod<comparatorSort,Object,Comparator<? super E>,E,Object,NULL>(<E>)
MACRODEF comparatorSortMethod<METHODNAME,ARRTYPE,COMPARATORTYPE,EXPOSEDTYPE,BOXEDTYPE,CONVERTTOBITS>(TYPEPARAMETER)
public static TYPEPARAMETER void uncheckedMETHODNAME(ARRTYPE[] arr,int begin,int end,COMPARATORTYPE sorter)
{
  //assert sorter!=null;
  //assert arr!=null;
  //assert begin>=0;
  //assert end<arr.length;
  //assert end-begin>0;
IFSWITCH ARRTYPE==boolean
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
ELSE
  //FIXME this sorting implementation is currently broken
  int nRemaining;
  if((nRemaining=++end-begin)<32)
  {
     binaryMETHODNAME(arr,begin,end,begin+countRunAndMakeAscendingMETHODNAME(arr,begin,end,sorter),sorter);
     return;
  }
  final AbstractTimSort ts=new METHODNAMEARRTYPETimSortTYPEPARAMETER(arr,sorter,nRemaining);
  int minRun=minRunLength(nRemaining);
  int runLen;
  do
  {
    if((runLen=countRunAndMakeAscendingMETHODNAME(arr,begin,end,sorter))<minRun)
    {
      int force;
      binaryMETHODNAME(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
      runLen=force;
    }
    ts.mergeCollapse(begin,runLen);
    begin+=runLen;
  }
  while((nRemaining-=runLen)!=0);
  //assert begin==end+1;
  ts.mergeForceCollapse();
ENDIF
}
ENDDEF
  //FIXME fix the TimSort methods
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
  MACRO TimSort<comparatorSort,byte,ByteComparator,byte>( )
  MACRO TimSort<comparatorSort,char,CharComparator,char>( )
  MACRO TimSort<comparatorSort,short,ShortComparator,short>( )
  MACRO TimSort<comparatorSort,int,IntBinaryOperator,int>( )
  MACRO TimSort<comparatorSort,long,LongComparator,long>( )
  MACRO TimSort<comparatorSort,float,FloatComparator,float>( )
  MACRO TimSort<comparatorSort,double,DoubleComparator,double>( )
  MACRO TimSort<comparatorSort,Object,Comparator<? super E>,E>(<E>)
  MACRO TimSort<sort,Object,Comparator<? super E>,Comparable<E>>(<E>)
  MACRO TimSort<reverseSort,Object,Comparator<? super E>,Comparable<E>>(<E>)
MACRODEF EnsureCapacity(ARRTYPE)
private ARRTYPE[] ensureCapacity(int minCapacity)
{
  ARRTYPE[] tmp;
  if(tmpLen<minCapacity)
  {
    int newSize;
    if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)<0 || newSize>(minCapacity=arr.length>>>1))
    {
      newSize=minCapacity;
    }
    this.tmp=tmp=new ARRTYPE[newSize];
    this.tmpLen=newSize;
    tmpBase=0;
  }
  else
  {
    tmp=this.tmp;
  }
  return tmp;
}
ENDDEF
MACRODEF MergeMethod<Side>()
MACRO SuppressUnchecked()
private void mergeSide(ARRTYPE[] arr,int base1,int len1,int base2,int len2
IFSWITCH METHODNAME==comparatorSort
,COMPARATORTYPE sorter
ENDIF
)
{
  ARRTYPE[] tmp;
  int cursor1,dest;
IFSWITCH Side==Lo
  int cursor2=base2;
  ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
IFSWITCH ARRTYPE==Object
  super.
ENDIF
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
  MACRO MergeImpl(len1,len2,++,cursor1,cursor2,count1,count2,arr,tmp)
    ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,len2);
    arr[dest+len2]=tmp[cursor1];
    break;
  default:
    assert len2==0;
    assert len1>1;
    ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
ELSE
  int tmpBase;
  ArrCopy.uncheckedCopy(arr,base2,tmp=
IFSWITCH ARRTYPE==Object
  super.
ENDIF
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
  MACRO MergeImpl(len2,len1,--,cursor2,cursor1,count2,count1,tmp,arr)
    ArrCopy.uncheckedCopy(arr,(cursor1-=len1)+1,arr,(dest-=len1)+1,len1);
    arr[dest]=tmp[cursor2];
    break;
  default:
    //assert len1==0;
    //assert len2>0;
    ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
ENDIF
  }
}
ENDDEF
MACRODEF MergeImpl(LEN1,LEN2,INCREMENT,CURSOR1,CURSOR2,COUNT1,COUNT2,ARR2,TMP)
int minGallop=this.minGallop;
outer:for(;;)
{
  int count1=0;
  int count2=0;
  do
  {
    //assert LEN1>1 && LEN2>0;
    MACRO IfClause(LessThan,ARR2[cursor2],TMP[cursor1])
    {
      arr[destINCREMENT]=arr[CURSOR2INCREMENT];
      ++COUNT2;
      COUNT1=0;
      if(--LEN2==0)
      {
        break outer;
      }
    }
    else
    {
      arr[destINCREMENT]=tmp[CURSOR1INCREMENT];
      ++COUNT1;
      COUNT2=0;
      if(--LEN1==0)
      {
        break;
      }
    }
  }
  while((count1|count2)<minGallop);
  do
  {
    //assert LEN1>1 && LEN2>0;
IFSWITCH Side==Lo
    if((count1=SortUtil.gallopRightMETHODNAME((EXPOSEDTYPE)arr[cursor2],tmp,cursor1,len1,0
IFSWITCH METHODNAME==comparatorSort
      ,sorter
ENDIF
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
ELSE
    if((count1=len1-SortUtil.gallopRightMETHODNAME((EXPOSEDTYPE)tmp[cursor2],arr,base1,len1,len1-1
IFSWITCH METHODNAME==comparatorSort
      ,sorter
ENDIF
    ))!=0)
    {
      ArrCopy.uncheckedCopy(arr,(cursor1-=count1),arr,(dest-=count1)+1,count1);
      if((len1-=count1)==0)
      {
        break outer;
      }
    }
ENDIF
    arr[destINCREMENT]=ARR2[cursor2INCREMENT];
IFSWITCH Side==Lo
    if(--len2==0)
ELSE
    if(--len2==1)
ENDIF
    {
      break outer;
    }
IFSWITCH Side==Lo
    if((count2=SortUtil.gallopLeftMETHODNAME((EXPOSEDTYPE)tmp[cursor1],arr,cursor2,len2,0
IFSWITCH METHODNAME==comparatorSort
      ,sorter
ENDIF
    ))!=0)
    {
      ArrCopy.uncheckedCopy(arr,cursor2,arr,dest,count2);
      dest+=count2;
      cursor2+=count2;
      if((len2-=count2)==0)
ELSE
    if((count2=len2-SortUtil.gallopLeftMETHODNAME((EXPOSEDTYPE)arr[cursor1],tmp,tmpBase,len2,len2-1
IFSWITCH METHODNAME==comparatorSort
      ,sorter
ENDIF
    ))!=0)
    {
      ArrCopy.uncheckedCopy(tmp,(cursor2-=count2)+1,arr,(dest-=count2)+1,count2);
      if((len2-=count2)<=1)
ENDIF
      {
        break outer;
      }
    }
    arr[destINCREMENT]=tmp[CURSOR1INCREMENT];
IFSWITCH Side==Lo
    if(len1==1)
ELSE
    if(len1==0)
ENDIF
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
switch(LEN1)
{
  case 0:
    throw new IllegalArgumentException("Comparison method violates its general contract!");
  case 1:
    //assert LEN2>0;
ENDDEF
  private static abstract class AbstractObjectTimSort<E> extends AbstractTimSort
  {
    final Object[] arr;
    Object[] tmp;
    @Override
    void initTmpArr(int tmpLen)
    {
      this.tmp=new Object[tmpLen];
    }
    MACRO EnsureCapacity(Object)
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
  MACRO ReverseRange<byte>()
  MACRO ReverseRange<char>()
  MACRO ReverseRange<short>()
  MACRO ReverseRange<int>()
  MACRO ReverseRange<long>()
  MACRO ReverseRange<float>()
  MACRO ReverseRange<double>()
  MACRO ReverseRange<Object>()
MACRODEF ReverseRange<ARRTYPE>()
private static void reverseRange(ARRTYPE[] arr,int begin,int end)
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
ENDDEF
  MACRO BinarySort<comparatorSort,byte,ByteComparator,byte>( )
  MACRO BinarySort<comparatorSort,char,CharComparator,char>( )
  MACRO BinarySort<comparatorSort,short,ShortComparator,short>( )
  MACRO BinarySort<comparatorSort,int,IntBinaryOperator,int>( )
  MACRO BinarySort<comparatorSort,long,LongComparator,long>( )
  MACRO BinarySort<comparatorSort,float,FloatComparator,float>( )
  MACRO BinarySort<comparatorSort,double,DoubleComparator,double>( )
  MACRO BinarySort<comparatorSort,Object,Comparator<? super E>,E>(<E>)
  MACRO BinarySort<sort,Object,Comparator<? super E>,Comparable<E>>(<E>)
  MACRO BinarySort<reverseSort,Object,Comparator<? super E>,Comparable<E>>(<E>)
MACRODEF BinarySort<METHODNAME,ARRTYPE,COMPARATORTYPE,EXPOSEDTYPE>(TYPEPARAMETER)
MACRO SuppressUnchecked()
private static TYPEPARAMETER void binaryMETHODNAME(ARRTYPE[] arr,int lo,int hi,int begin
IFSWITCH METHODNAME==comparatorSort
,COMPARATORTYPE sorter
ENDIF
)
{
  //assert lo < begin;
  for(;begin<hi;++begin)
  {
    final var pivot=(EXPOSEDTYPE)arr[begin];
    int left=lo;
    for(int right=begin;left<right;)
    {
      final int mid;
      MACRO IfClause(LessThan,pivot,arr[mid=(left+right)>>>1])
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
ENDDEF
  MACRO CountRunAndMakeAscending<comparatorSort,byte,ByteComparator,byte>( )
  MACRO CountRunAndMakeAscending<comparatorSort,char,CharComparator,char>( )
  MACRO CountRunAndMakeAscending<comparatorSort,short,ShortComparator,short>( )
  MACRO CountRunAndMakeAscending<comparatorSort,int,IntBinaryOperator,int>( )
  MACRO CountRunAndMakeAscending<comparatorSort,long,LongComparator,long>( )
  MACRO CountRunAndMakeAscending<comparatorSort,float,FloatComparator,float>( )
  MACRO CountRunAndMakeAscending<comparatorSort,double,DoubleComparator,double>( )
  MACRO CountRunAndMakeAscending<comparatorSort,Object,Comparator<? super E>,E>(<E>)
  MACRO CountRunAndMakeAscending<sort,Object,Comparator<? super E>,Comparable<E>>(<E>)
  MACRO CountRunAndMakeAscending<reverseSort,Object,Comparator<? super E>,Comparable<E>>(<E>)
MACRODEF CountRunAndMakeAscending<METHODNAME,ARRTYPE,COMPARATORTYPE,EXPOSEDTYPE>(TYPEPARAMETER)
MACRO SuppressUnchecked()
private static TYPEPARAMETER int countRunAndMakeAscendingMETHODNAME(ARRTYPE[] arr,int begin,int end
IFSWITCH METHODNAME==comparatorSort
,COMPARATORTYPE sorter
ENDIF
)
{
  //assert arr!=null;
  //assert begin<end;
  //assert begin>=0;
  //assert end<=arr.length;
  //assert sorter!=null;
  int runHi;
  if((runHi=begin+1)==end)
  {
    return 1;
  }
  MACRO IfClause(LessThan,arr[runHi++],arr[begin])
  {
    while(runHi<end &&
    MACRO METHODNAMELessThan(arr[runHi],arr[runHi-1])
    )
    {
      ++runHi;
    }
    reverseRange(arr,begin,runHi-1);
  }
  else
  {
    while(runHi<end &&
    MACRO METHODNAMEGreaterThanOrEqual(arr[runHi],arr[runHi-1])
    )
    {
      ++runHi;
    }
  }
  return runHi-begin;
}
ENDDEF
  MACRO Gallop<Left,comparatorSort,byte,byte,ByteComparator>( )
  MACRO Gallop<Left,comparatorSort,char,char,CharComparator>( )
  MACRO Gallop<Left,comparatorSort,short,short,ShortComparator>( )
  MACRO Gallop<Left,comparatorSort,int,int,IntBinaryOperator>( )
  MACRO Gallop<Left,comparatorSort,long,long,LongComparator>( )
  MACRO Gallop<Left,comparatorSort,float,float,FloatComparator>( )
  MACRO Gallop<Left,comparatorSort,double,double,DoubleComparator>( )
  MACRO Gallop<Left,comparatorSort,Object,E,Comparator<? super E>>(<E>)
  MACRO Gallop<Right,comparatorSort,byte,byte,ByteComparator>( )
  MACRO Gallop<Right,comparatorSort,char,char,CharComparator>( )
  MACRO Gallop<Right,comparatorSort,short,short,ShortComparator>( )
  MACRO Gallop<Right,comparatorSort,int,int,IntBinaryOperator>( )
  MACRO Gallop<Right,comparatorSort,long,long,LongComparator>( )
  MACRO Gallop<Right,comparatorSort,float,float,FloatComparator>( )
  MACRO Gallop<Right,comparatorSort,double,double,DoubleComparator>( )
  MACRO Gallop<Right,comparatorSort,Object,E,Comparator<? super E>>(<E>)
  MACRO Gallop<Left,sort,Object,Comparable<E>,Comparator<? super E>>(<E>)
  MACRO Gallop<Right,sort,Object,Comparable<E>,Comparator<? super E>>(<E>)
  MACRO Gallop<Left,reverseSort,Object,Comparable<E>,Comparator<? super E>>(<E>)
  MACRO Gallop<Right,reverseSort,Object,Comparable<E>,Comparator<? super E>>(<E>)
MACRODEF Gallop<Direction,METHODNAME,ARRTYPE,EXPOSEDTYPE,COMPARATORTYPE>(TYPEPARAMETER)
MACRO SuppressUnchecked()
private static TYPEPARAMETER int gallopDirectionMETHODNAME(EXPOSEDTYPE key,ARRTYPE[] arr,int base,int len,int hint
IFSWITCH METHODNAME==comparatorSort
,COMPARATORTYPE sorter
ENDIF
)
{
  //assert len > 0 && hint >= 0 && hint < len;
  int ofs=1;
  int lastOfs=0;
IFSWITCH Direction==Right
  MACRO IfClause(LessThan,key,arr[base+hint])
  {
    int maxOfs=hint+1;
    while(ofs < maxOfs &&
    MACRO METHODNAMELessThan(key,arr[base+hint-ofs])
ELSE
  MACRO IfClause(LessThan,arr[base+hint],key)
  {
    int maxOfs=len-hint;
    while(ofs < maxOfs &&
    MACRO METHODNAMELessThan(arr[base+hint+ofs],key)
ENDIF
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
IFSWITCH Direction==Right
    int tmp=lastOfs;
    lastOfs=hint-ofs;
    ofs=hint-tmp;
  }
  else
  {
    int maxOfs=len-hint;
ELSE
    lastOfs+=hint;
    ofs+=hint;
  }
  else
  {
    final int maxOfs=hint+1;
ENDIF
    for(;;)
    {
      if(ofs>=maxOfs || 
IFSWITCH Direction==Right
      MACRO METHODNAMELessThan(key,arr[base+hint+ofs])
ELSE
      MACRO METHODNAMELessThan(arr[base+hint-ofs],key)
ENDIF
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
IFSWITCH Direction==Right
    lastOfs+=hint;
    ofs+=hint;
ELSE
    int tmp=lastOfs;
    lastOfs=hint-ofs;
    ofs=hint-tmp;
ENDIF
  }
  //assert -1 <= lastOfs && lastOfs < ofs && ofs <=len;
  int diff;
  if((diff=ofs-(++lastOfs))>0)
  {
    do
    {
      int m;
IFSWITCH Direction==Right
      MACRO IfClause(LessThan,key,arr[base+(m=lastOfs+(diff>>>1))])
      {
        ofs=m;
      }
      else
      {
        lastOfs=m+1;
      }
ELSE
      MACRO IfClause(LessThan,arr[base+(m=lastOfs+(diff>>>1))],key)
      {
        lastOfs=m+1;
      }
      else
      {
        ofs=m;
      }
ENDIF
    }
    while((diff=ofs-lastOfs)>0);
  }
  //assert lastOfs==ofs;
  return ofs;
}
ENDDEF
*/
}
