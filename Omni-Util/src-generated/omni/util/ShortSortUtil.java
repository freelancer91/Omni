//TODO combine these implementations into one file
package omni.util;
import omni.function.ShortComparator;
public final class ShortSortUtil
{
  private ShortSortUtil()
  {
     super();
  }
  public static void uncheckedsort(short[] arr,int begin,int end)
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
      short value=(short)(i+Short.MIN_VALUE);
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
  private static void insertsort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final short ai=arr[i+1];
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
            short tmp=arr[lo];
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
  private static void sortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    short[] work=new short[blen=end-begin];
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
      short[] tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
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
    (pivot2)<(arr[--great])
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
      (pivot2)<(ak)
      )
      {
        short ag;
        while(
        (pivot2)<(ag=arr[great])
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
      short tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      short tmp=val3;
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
      short tmp=val4;
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
      short tmp=val5;
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
    (pivot2)<(arr[--great])
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
      (pivot2)<(ak)
      )
      {
        short ag;
        while(
        (pivot2)<(ag=arr[great])
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
      short tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      short tmp=val3;
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
      short tmp=val4;
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
      short tmp=val5;
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
  public static void uncheckedreverseSort(short[] arr,int begin,int end)
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
      short value=(short)(i+Short.MIN_VALUE);
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
  private static void insertreverseSort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final short ai=arr[i+1];
      short aj;
      while(
      (ai)>(aj=arr[j])
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
  private static void sentinelInsertreverseSort(short[] arr,int begin,int end)
  {
    for(;;)
    {
      if(begin>=end)
      {
        return;
      }
      if(
      (arr[++begin])>(arr[begin-1])
      )
      {
        break;
      }
    }
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
            if(++k>end
            ||
            (arr[k-1])>(arr[k])
            )
            {
              break;
            }
          }
          for(int lo=run[count],hi=k;lo<--hi;++lo)
          {
            short tmp=arr[lo];
            arr[lo]=arr[hi];
            arr[hi]=tmp;
          }
          break;
        case 0:
          ++k;
          continue;
        }
        if(run[count]<=begin ||
        (arr[run[count]])>(arr[run[count]-1])
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
  private static void reverseSortmerge(short[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    short[] b;
    int ao,bo,blen;
    short[] work=new short[blen=end-begin];
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
      short[] tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
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
    (arr[++less])>(pivot1)
    )
    {
    }
    while(
    (pivot2)>(arr[--great])
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
      (pivot2)>(ak)
      )
      {
        short ag;
        while(
        (pivot2)>(ag=arr[great])
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      short tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      short tmp=val3;
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
      short tmp=val4;
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
      short tmp=val5;
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
    (arr[++less])>(pivot1)
    )
    {
    }
    while(
    (pivot2)>(arr[--great])
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
      (pivot2)>(ak)
      )
      {
        short ag;
        while(
        (pivot2)>(ag=arr[great])
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      short tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      short tmp=val3;
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
      short tmp=val4;
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
      short tmp=val5;
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
  private static void reverseRange(short[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      short tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static  int countRunAndMakeAscending(short[] arr,int begin,int end,ShortComparator sorter)
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
        if(sorter.compare((short)arr[runHi],(short)arr[++runHi])<=0)
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
        if(sorter.compare((short)arr[runHi],(short)arr[++runHi])>0)
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static  void binarySort(short[] arr,int lo,int hi,int begin,ShortComparator sorter)
  {
    //assert lo < begin;
    for(;begin<=hi;++begin)
    {
      final short pivot=(short)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(sorter.compare(pivot,(short)arr[mid=(left+right)>>>1])<0)
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
  private static class TimSort
  {
    final short[] arr;
    final ShortComparator sorter;
    short[] tmp;
    int tmpLen;
    int tmpBase;
    int stackSize;
    int minGallop;
    int[] runBase;
    int[] runLen;
    private TimSort(short[] arr,ShortComparator sorter,int nRemaining)
    {
      this.arr=arr;
      this.sorter=sorter;
      int tmpLen;
      this.tmp=new short[tmpLen=nRemaining<512?nRemaining>>>1:256];
      this.tmpLen=tmpLen;
      this.tmpBase=0;
      this.stackSize=0;
      this.minGallop=7;
      this.runBase=new int[nRemaining=(nRemaining<120?5:nRemaining<1542?10:nRemaining<119151?24:49)];
      this.runLen=new int[nRemaining];
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
    private static  int gallopRight(short key,short[] arr,int base,int len,int hint,ShortComparator sorter)
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
    private static  int gallopLeft(short key,short[] arr,int base,int len,int hint,ShortComparator sorter)
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
    private void mergeLo(short[] arr,int base1,int len1,int base2,int len2,ShortComparator sorter)
    {
      short[] tmp;
      int cursor1,dest;
      int cursor2=base2;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=ensureCapacity(len1),cursor1=this.tmpBase,len1);
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
          if((count1=gallopRight((short)arr[cursor2],tmp,cursor1,len1,0,sorter))!=0)
          {
            ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,count1);
            dest+=count1;
            cursor1+=count1;
            if((len1-=count1)<=1)
            {
              break outer;
            }
          }
          arr[dest++]=tmp[cursor2++];
          if(--len2==0)
          {
            break outer;
          }
          if((count2=gallopLeft((short)tmp[cursor1],arr,cursor2,len2,0,sorter))!=0)
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
    private void mergeHi(short[] arr,int base1,int len1,int base2,int len2,ShortComparator sorter)
    {
      short[] tmp;
      int cursor1,dest;
      int tmpBase;
      ArrCopy.uncheckedCopy(arr,base2,tmp=ensureCapacity(len2),tmpBase=this.tmpBase,len2);
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
          if((count1=len1-gallopRight((short)tmp[cursor2],arr,base1,len1,len1-1,sorter))!=0)
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
          if((count2=len2-gallopLeft((short)arr[cursor1],tmp,tmpBase,len2,len2-1,sorter))!=0)
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
      default:
        assert len1==0;
        assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpBase,arr,dest-(len2-1),len2);
      }
    }
    private int mergeAt(int i,int stackSize,int[] runLen)
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
      base1=(base1=runBase[i])+(k=gallopRight((short)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0,sorter));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=gallopLeft((short)arr[base1+len1-1],arr,base2,len2,len2-1,sorter))!=0)
         {
           //assert len2>0;
           if(len1>len2)
           {
             mergeHi(arr,base1,len1,base2,len2,sorter);
           }
           else
           {
             mergeLo(arr,base1,len1,base2,len2,sorter);
           }
         }
      }
      return stackSize;
    }
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
  public static  void uncheckedcomparatorSort(short[] arr,int begin,int end,ShortComparator sorter)
  {
      //assert sorter!=null;
      //assert arr!=null;
      //assert begin>=0;
      //assert end<arr.length;
      //assert end-begin>0;
      int nRemaining;
      if((nRemaining=end-begin)<31)
      {
         binarySort(arr,begin,end,begin+countRunAndMakeAscending(arr,begin,end,sorter),sorter);
         return;
      }
      TimSort ts=new TimSort(arr,sorter,++nRemaining);
      int minRun=minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=countRunAndMakeAscending(arr,begin,end,sorter))<minRun)
        {
          int force;
          binarySort(arr,begin,begin+(force=nRemaining<=minRun?nRemaining:minRun),begin+runLen,sorter);
          runLen=force;
        }
        ts.mergeCollapse(begin,runLen);
        begin+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert begin==end+1;
      ts.mergeForceCollapse();
  }
}
