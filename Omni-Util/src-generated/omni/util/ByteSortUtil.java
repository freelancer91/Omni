//TODO combine these implementations into one file
package omni.util;
import omni.function.ByteComparator;
public final class ByteSortUtil
{
  private ByteSortUtil()
  {
     super();
  }
  public static void uncheckedsort(byte[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert begin<end;
    if(end-begin<30)
    {
      //Use insertion sort on small arrays
      insertsort(arr,begin,end);
    }
    else
    {
      // Use counting sort on large arrays
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
      byte value=(byte)(i+Byte.MIN_VALUE);
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
  private static void insertsort(byte[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final byte ai=arr[i+1];
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
  public static void uncheckedreverseSort(byte[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin>=0;
    //assert end<arr.length;
    //assert begin<end;
    if(end-begin<30)
    {
      //Use insertion sort on small arrays
      insertreverseSort(arr,begin,end);
    }
    else
    {
      // Use counting sort on large arrays
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
      byte value=(byte)(i+Byte.MIN_VALUE);
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
  private static void insertreverseSort(byte[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final byte ai=arr[i+1];
      byte aj;
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
  private static void reverseRange(byte[] arr,int begin,int end)
  {
    //assert arr!=null;
    //assert begin<end;
    //assert begin>=0;
    //assert end<arr.length;
    do
    {
      byte tmp=arr[begin];
      arr[begin]=arr[end];
      arr[end]=tmp;
    }
    while(++begin<--end);
  }
  private static  int countRunAndMakeAscending(byte[] arr,int begin,int end,ByteComparator sorter)
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
        if(sorter.compare((byte)arr[runHi],(byte)arr[++runHi])<=0)
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
        if(sorter.compare((byte)arr[runHi],(byte)arr[++runHi])>0)
        {
          break;
        }
      }
    }
    return runHi-begin;
  }
  private static  void binarySort(byte[] arr,int lo,int hi,int begin,ByteComparator sorter)
  {
    //assert lo < begin;
    for(;begin<=hi;++begin)
    {
      final byte pivot=(byte)arr[begin];
      int left=lo;
      for(int right=begin;left<right;)
      {
        final int mid;
        if(sorter.compare(pivot,(byte)arr[mid=(left+right)>>>1])<0)
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
    final byte[] arr;
    final ByteComparator sorter;
    byte[] tmp;
    int tmpLen;
    int tmpBase;
    int stackSize;
    int minGallop;
    int[] runBase;
    int[] runLen;
    private TimSort(byte[] arr,ByteComparator sorter,int nRemaining)
    {
      this.arr=arr;
      this.sorter=sorter;
      int tmpLen;
      this.tmp=new byte[tmpLen=nRemaining<512?nRemaining>>>1:256];
      this.tmpLen=tmpLen;
      this.tmpBase=0;
      this.stackSize=0;
      this.minGallop=7;
      this.runBase=new int[nRemaining=(nRemaining<120?5:nRemaining<1542?10:nRemaining<119151?24:49)];
      this.runLen=new int[nRemaining];
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
    private static  int gallopRight(byte key,byte[] arr,int base,int len,int hint,ByteComparator sorter)
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
    private static  int gallopLeft(byte key,byte[] arr,int base,int len,int hint,ByteComparator sorter)
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
    private void mergeLo(byte[] arr,int base1,int len1,int base2,int len2,ByteComparator sorter)
    {
      byte[] tmp;
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
          if((count1=gallopRight((byte)arr[cursor2],tmp,cursor1,len1,0,sorter))!=0)
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
          if((count2=gallopLeft((byte)tmp[cursor1],arr,cursor2,len2,0,sorter))!=0)
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
    private void mergeHi(byte[] arr,int base1,int len1,int base2,int len2,ByteComparator sorter)
    {
      byte[] tmp;
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
          if((count1=len1-gallopRight((byte)tmp[cursor2],arr,base1,len1,len1-1,sorter))!=0)
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
          if((count2=len2-gallopLeft((byte)arr[cursor1],tmp,tmpBase,len2,len2-1,sorter))!=0)
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
      byte[] arr;
      int k;
      base1=(base1=runBase[i])+(k=gallopRight((byte)(arr=this.arr)[base2=runBase[i+1]],arr,base1,len1,0,sorter));
      if((len1-=k)!=0)
      {
         //assert k>=0;
         if((len2=gallopLeft((byte)arr[base1+len1-1],arr,base2,len2,len2-1,sorter))!=0)
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
  public static  void uncheckedcomparatorSort(byte[] arr,int begin,int end,ByteComparator sorter)
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
