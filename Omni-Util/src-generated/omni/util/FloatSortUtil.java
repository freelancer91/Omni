package omni.util;
import omni.function.FloatComparator;
public final class FloatSortUtil
{
  private FloatSortUtil()
  {
     super();
  }
  public static void uncheckedsort(float[] arr,int begin,int end)
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
  private static void insertsort(float[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final float ai=arr[i+1];
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
        (arr[k+1])<(arr[k])
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
            float tmp=arr[lo];
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
  private static void sortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    float[] work=new float[blen=end-begin];
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
      float[] tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
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
      (pivot)<(ak)
      )
      {
        float ag;
        while(
        (pivot)<(ag=arr[great])
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
    (pivot2)<(arr[--great])
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
      (pivot2)<(ak)
      )
      {
        float ag;
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
      float tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      float tmp=val3;
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
      float tmp=val4;
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
      float tmp=val5;
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
      (pivot)<(ak)
      )
      {
        float ag;
        while(
        (pivot)<(ag=arr[great])
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
    (pivot2)<(arr[--great])
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
      (pivot2)<(ak)
      )
      {
        float ag;
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
      float tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])<(val2)
    )
    {
      float tmp=val3;
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
      float tmp=val4;
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
      float tmp=val5;
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
  public static void uncheckedreverseSort(float[] arr,int begin,int end)
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
      (arr[middle=(begin+hi)>>>1])>((float)0.0)
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
    (Float.floatToRawIntBits(arr[begin]))>(0)
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
      (Float.floatToRawIntBits(ak))>(0)
      )
      {
        arr[k]=(float)-0.0;
        arr[begin]=(float)0.0;
        ++begin;
      }
    }
  }
  private static void insertreverseSort(float[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final float ai=arr[i+1];
      float aj;
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
  private static void sentinelInsertreverseSort(float[] arr,int begin,int end)
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
        (arr[k])>(arr[k+1])
        )
        {
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
        }
        else
        if(
        (arr[k+1])>(arr[k])
        )
        {
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
            float tmp=arr[lo];
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
  private static void reverseSortmerge(float[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<count;odd^=1){}
    float[] b;
    int ao,bo,blen;
    float[] work=new float[blen=end-begin];
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
      float[] tmp=arr;
      arr=b;
      b=tmp;
      int o=ao;
      ao=bo;
      bo=o;
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
      (pivot)>(ak)
      )
      {
        float ag;
        while(
        (pivot)>(ag=arr[great])
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
      (pivot2)>(ak)
      )
      {
        float ag;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      float tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      float tmp=val3;
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
      float tmp=val4;
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
      float tmp=val5;
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
      (pivot)>(ak)
      )
      {
        float ag;
        while(
        (pivot)>(ag=arr[great])
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
      (pivot2)>(ak)
      )
      {
        float ag;
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
    (val2=arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=arr[e1=e2-seventh])
    )
    {
      float tmp=val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=arr[e3])>(val2)
    )
    {
      float tmp=val3;
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
      float tmp=val4;
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
      float tmp=val5;
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
  private static void reverseRange(float[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      float tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static  int countRunAndMakeAscending(float[] arr,int begin,int end,FloatComparator sorter)
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
        if(sorter.compare((float)arr[runHi],(float)arr[++runHi])<=0)
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
        if(sorter.compare((float)arr[runHi],(float)arr[++runHi])>0)
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static  void binarySort(float[] arr,int lo,int hi,int begin,FloatComparator sorter)
  {
    //assert lo < begin;
    for(;begin<=hi;++begin)
    {
      final float pivot=(float)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(sorter.compare(pivot,(float)arr[mid=(left+right)>>>1])<0)
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
    final float[] arr;
    final FloatComparator sorter;
    float[] tmp;
    int tmpLen;
    int tmpBase;
    int stackSize;
    int minGallop;
    int[] runBase;
    int[] runLen;
    private TimSort(float[] arr,FloatComparator sorter,int nRemaining)
    {
      this.arr=arr;
      this.sorter=sorter;
      int tmpLen;
      this.tmp=new float[tmpLen=nRemaining<512?nRemaining>>>1:256];
      this.tmpLen=tmpLen;
      this.tmpBase=0;
      this.stackSize=0;
      this.minGallop=7;
      this.runBase=new int[nRemaining=(nRemaining<120?5:nRemaining<1542?10:nRemaining<119151?24:49)];
      this.runLen=new int[nRemaining];
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
    private static  int gallopRight(float key,float[] arr,int base,int len,int hint,FloatComparator sorter)
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
    private static  int gallopLeft(float key,float[] arr,int base,int len,int hint,FloatComparator sorter)
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
    private void mergeLo(float[] arr,int base1,int len1,int base2,int len2,FloatComparator sorter)
    {
      float[] tmp;
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
          if((count1=gallopRight((float)arr[cursor2],tmp,cursor1,len1,0,sorter))!=0)
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
          if((count2=gallopLeft((float)tmp[cursor1],arr,cursor2,len2,0,sorter))!=0)
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
    private void mergeHi(float[] arr,int base1,int len1,int base2,int len2,FloatComparator sorter)
    {
      float[] tmp;
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
          if((count1=len1-gallopRight((float)tmp[cursor2],arr,base1,len1,len1-1,sorter))!=0)
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
          if((count2=len2-gallopLeft((float)arr[cursor1],tmp,tmpBase,len2,len2-1,sorter))!=0)
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
      float[] arr;
      int k;
      base1=(base1=runBase[i])+(k=gallopRight((float)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0,sorter));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=gallopLeft((float)arr[base1+len1-1],arr,base2,len2,len2-1,sorter))!=0)
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
  public static  void uncheckedcomparatorSort(float[] arr,int begin,int end,FloatComparator sorter)
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
