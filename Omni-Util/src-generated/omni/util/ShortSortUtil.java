package omni.util;
import omni.function.ShortComparator;
public final class ShortSortUtil
{
  private ShortSortUtil()
  {
    super();
  }
    public static  void uncheckedUnstableSort(short[] arr,int offset,int bound,ShortComparator sorter)
    {
       if((--bound)-offset<286)
       {
         quickSortleftmost(arr,offset,bound,sorter);
       }
       else
       {
         int[] run;
         int count=0;
         (run=new int[68])[0]=offset;
         //checkIfSortedLoop:
         for(int k=offset;;)
         {
           if(k==bound)
           {
             if(count!=0)
             {
               merge(arr,offset,bound,run,count,sorter);
             }
             break;
           }
           switch(
           Integer.signum(sorter.compare((short)(arr[k]),(short)(arr[k+1])))
           )
           {
           case -1:
             for(;;)
             {
               if(++k==bound ||
               sorter.compare((short)(arr[k]),(short)(arr[k-1]))<0
               )
               {
                 break;
               }
             }
             break;
           default:
             for(;;)
             {
               if(++k==bound||
               sorter.compare((short)(arr[k-1]),(short)(arr[k]))<0
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
           sorter.compare((short)(arr[r=run[count]]),(short)(arr[r-1]))<0
           && ++count==67)
           {
             quickSortleftmost(arr,offset,bound,sorter);
             break;
           }
           run[count]=k;
         }
       }
    }
  public static void uncheckedAscendingSort(short[] arr,int offset,int bound)
  {
    int nRemaining;
    if((nRemaining=(--bound)-offset)>3200)
    {
      AscendingcountingSort(arr,offset,bound);
      return;
    }
    if(nRemaining<286)
    {
      quickAscendingSortleftmost(arr,offset,bound);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=offset;
      //checkIfSortedLoop:
      for(int k=offset;;)
      {
        if(k==bound)
        {
          if(count!=0)
          {
            Ascendingmerge(arr,offset,bound,run,count);
          }
          break;
        }
        switch(
        Integer.signum((arr[k])-(arr[k+1]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==bound ||
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
            if(++k==bound||
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
          quickAscendingSortleftmost(arr,offset,bound);
          break;
        }
        run[count]=k;
      }
    }
  }
  public static void uncheckedDescendingSort(short[] arr,int offset,int bound)
  {
    int nRemaining;
    if((nRemaining=(--bound)-offset)>3200)
    {
      DescendingcountingSort(arr,offset,bound);
      return;
    }
    if(nRemaining<286)
    {
      quickDescendingSortleftmost(arr,offset,bound);
    }
    else
    {
      int[] run;
      int count=0;
      (run=new int[68])[0]=offset;
      //checkIfSortedLoop:
      for(int k=offset;;)
      {
        if(k==bound)
        {
          if(count!=0)
          {
            Descendingmerge(arr,offset,bound,run,count);
          }
          break;
        }
        switch(
        Integer.signum((arr[k+1])-(arr[k]))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==bound ||
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
            if(++k==bound||
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
          quickDescendingSortleftmost(arr,offset,bound);
          break;
        }
        run[count]=k;
      }
    }
  }
  public static  void uncheckedStableSort(short[] arr,int offset,int bound,ShortComparator sorter)
  {
    int nRemaining;
    if((nRemaining=bound-offset)<32)
    {
      ShortTimSort.binarySort(arr,offset,bound,offset+ShortTimSort.countRunAndMakeAscending(arr,offset,bound,sorter),sorter);
      return;
    }
    new ShortTimSort(arr,nRemaining,sorter,offset,bound);
  }
  private static class ShortTimSort
    extends AbstractTimSort
  {
    private transient final ShortComparator sorter;
    private transient final short[] arr;
    private transient short[] tmp;
    private transient int tmpOffset;
    private transient int tmpLength;
    private transient int minGallop;
    /**
     * Ensures that the external array tmp has at least the specified
     * number of elements, increasing its size if necessary.  The size
     * increases exponentially to ensure amortized linear time complexity.
     *
     * @param minCapacity the minimum required capacity of the tmp array
     * @return tmp, whether or not it grew
     */
    private short[] ensureCapacity(int minCapacity)
    {
      short[] tmp;
      if(tmpLength<minCapacity)
      {
        //compute the smallest power of 2 > minCapacity
        //Integer overflow is almost impossible given that every implementation of the JVM I've seen won't allow
        //an array with a length of Integer.MAX_VALUE. Still, we cover out bases with this check
        //Unfortunately, I have no idea how to coverage this branch with Junits.
        int newSize;
        if((newSize=(-1>>>Integer.numberOfLeadingZeros(minCapacity))+1)>(minCapacity=arr.length>>>1))
        {
          newSize=minCapacity;
        }
        //assert newSize>0;
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
    private ShortTimSort(short[] arr,int nRemaining,ShortComparator sorter,int offset,int bound)
    {
      super(nRemaining);
      this.sorter=sorter;
      this.arr=arr;
      int tmpLength;
      this.tmpLength=tmpLength=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new short[tmpLength];
      this.minGallop=7;
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=ShortTimSort.countRunAndMakeAscending(arr,offset,bound,sorter))<minRun)
        {
          int force;
          ShortTimSort.binarySort(arr,offset,offset+(force=nRemaining<=minRun?nRemaining:minRun),offset+runLen,sorter);
          runLen=force;
        }
        super.mergeCollapse(offset,runLen);
        offset+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert offset==bound;
      super.mergeForceCollapse(); 
    }
    @Override
    int mergeAt(int n,int stackSize,int[] runLenAndBase)
    {
      //assert stackSize>=4;
      //assert n>=0;
      //assert n==stackSize-4 || n==stackSize-6;
      //assert runLenAndBase[n]>0;
      //assert runLenAndBase[n+2]>0;
      //assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,len2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      int base2=runLenAndBase[n+3];
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      short[] arr;
      int k,base1;
      //TODO streamline this?
      base1=(base1=runLenAndBase[n+1])+(k=mergeAtGallopRight((short)(arr=this.arr)[base2],arr,base1,len1,sorter));
      //assert k>=0;
      if((len1-=k)!=0)
      {
        //if((len2=mergeAtGallopLeft((short)arr[base1+len1-1],arr,base2,len2),sorter)!=0)
        //{
        //  if(len1<=len2)
        if(len1<=(len2=mergeAtGallopLeft((short)arr[base1+len1-1],arr,base2,len2,sorter)))
        {
          mergeLo(arr,base1,len1,base2,len2);
        }
        else
        {
          mergeHi(arr,base1,len1,base2,len2);
        }
        //}
      }
      return stackSize-2;
    }
    private static  int mergeAtGallopLeft(short key,short[] arr,int base,int len,ShortComparator sorter)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((short)(key),(short)(arr[base+len-1]))>0
      )
      {
        return len;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO LessThanOrEqualTo(key,arr[base+len-2])
      //)
      if(
      sorter.compare((short)(key),(short)(arr[base+len-2]))<=0
      )
      {
        lastOfs=-1;
      }
      else
      {
        lastOfs=len-2;
      }
      //assert -1<=lastOfs;
      //assert lastOfs<(len-1);
      if(++lastOfs<(ofs=(len-1)))
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
      //assert lastOfs==ofs;
      return ofs;
    }
    private static  int mergeAtGallopRight(short key,short[] arr,int base,int len,ShortComparator sorter)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((short)(key),(short)(arr[base]))<0
      )
      {
        return 0;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO GreaterThanOrEqualTo(key,arr[base+1])
      //)
      if(
      sorter.compare((short)(key),(short)(arr[base+1]))>=0
      )
      {
        ofs=len;
      }
      else
      {
        ofs=1;
      }
      lastOfs=0;
      //assert -1<=lastOfs;
      //assert lastOfs<ofs;
      //assert ofs<=len;
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
      //assert lastOfs==ofs;
      return ofs;
    }
    /**
     * Merges two adjacent runs in place in a stable fashion. THe first element of the first run
     * must be greater than the first element of the second run (arr[base1]>arr[base2]), and th elast element of the
     * first run (arr[base1+len1-1]) must be greater than all the elements of the second run.
     * For performance, this method should only be called when len1<=len2. It's twin, mergeHi, should be called win len1>=len2
     * (Either may be called when they are equal)
     * @param arr the array to merge
     * @param base1 the index of the first element in the first run to be merged
     * @param len1 the length of the first run to be merged
     * @param base2 the index of the first element in the second run to be merged
     * @param len2 the length of the second run to be merged
     */
    private void mergeLo(short[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final short[] tmp;
      //copy the first run into the tmp array
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      ensureCapacity(len1),cursor1=this.tmpOffset,len1);
      //Move the first element of the second run and deal with degenerate cases
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
        ArrCopy.uncheckedSelfCopy(arr,dest,cursor2,len2);
        arr[dest+len2]=tmp[cursor1]; //last element of run 1 to the end of the merge
        return;
      }
      int minGallop=this.minGallop;
      final var sorter=this.sorter;
      outer:
      for(;;)
      {
        int count1=0; //The number of times in a row that the first run won.
        int count2=0; //The number of times in a row that the second run won
        //Do the straightforward thing until (if ever) one run starts winning consistently
        do
        {
          //assert len1>1;
          //assert len2>0;
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len1>1;
          //assert len2>0;
          if((count1=gallopRight((short)arr[cursor2],tmp,cursor1,len1,0,sorter))!=0)
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
          if((count2=gallopLeft((short)tmp[cursor1],arr,cursor2,len2,0,sorter))!=0)
          {
            ArrCopy.uncheckedSelfCopy(arr,dest,cursor2,count2);
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
        //penalize for leaving gallop mode
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
        ArrCopy.uncheckedSelfCopy(arr,dest,cursor2,len2);
        arr[dest+len2]=tmp[cursor1]; //last element of run 1 to the end of the merge
      }
      else
      {
        //assert len2==0;
        //assert len1>1;
        ArrCopy.uncheckedCopy(tmp,cursor1,arr,dest,len1);
      }
    }
    /**
     * Like mergeLo, except that this method should be called only if
     * len1 >= len2; mergeLo should be called if len1 <= len2.  (Either method
     * may be called if len1 == len2.)
     *
     * @param base1 index of first element in first run to be merged
     * @param len1  length of first run to be merged (must be > 0)
     * @param base2 index of first element in second run to be merged
     *        (must be aBase + aLen)
     * @param len2  length of second run to be merged (must be > 0)
     */
    private void mergeHi(short[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final short[] tmp;
      //copy the second run into the tmp array
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      ensureCapacity(len2),tmpOffset=this.tmpOffset,len2);
      int cursor1=base1+(--len1);
      int cursor2=tmpOffset+len2-1;
      int dest=base2+len2-1;
      //TODO make these pre-decrement for performance
      //Move the last element of the first run and deal with degenerate cases
      arr[dest--]=arr[cursor1--];
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
        int count1=0; //The number of times in a row that the first run won.
        int count2=0; //The number of times in a row that the second run won
        //Do the straightforward thing until (if ever) one run starts winning consistently
        do
        {
          //assert len2>1;
          //assert len1>0;
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len2>1;
          //assert len1>0;
          if((count1=len1-gallopRight((short)tmp[cursor2],arr,base1,len1,len1-1,sorter))!=0)
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
          if((count2=len2-gallopLeft((short)arr[cursor1],tmp,tmpOffset,len2,len2-1,sorter))!=0)
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
        //penalize for leaving gallop mode
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
        arr[dest]=tmp[cursor2]; //move the first element of run2 to the front of the merge
      }
      else
      {
        //assert len1==0;
        //assert len2>0;
        ArrCopy.uncheckedCopy(tmp,tmpOffset,arr,dest-(len2-1),len2);
      }
    }
    /** 
     * Locates the position at which to insert the specified key into the specified sorted range;
     * if the range contains and element equal to the key, return the index of the leftmost equal element.
     *
     * @param key the key to find the insertion point for
     * @param arr The array in which to search
     * @param base The index of the first element in the range
     * @param len The length of the range (must be >0)
     * @param hint The index at which to begin the search, 0 <= hint < len. The closer hint is to the result,
     *   this faster this method will run.
     * @param sorter The comparator used to order the range and search
     * @return the int k, 0<=k<=len such that arr[base+k-1]< key<=arr[base+k],
     *   pretending that arr[base-1] is minus infinity or add[base+len] is infinity.
     *   In other words, key belonds at index base + k; or in other words, the first k elements should precede the key,
     *   and the last len-k elements should follow it.
     */
    //TODO split this up into mergeLo and mergeHi versions
    private static  int gallopLeft(short key,short[] arr,int base,int len,int hint,ShortComparator sorter)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((short)(key),(short)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(2>(maxOfs=len-hint)||
        sorter.compare((short)(key),(short)(arr[base+hint+1]))>0
        )
        {
          ofs=maxOfs+hint;
        }
        else
        {
          ofs=hint+1;
        }
        lastOfs=hint;
      }
      else
      {
        int maxOfs;
        if(2>(maxOfs=hint+1)||
        sorter.compare((short)(key),(short)(arr[base+hint-1]))<=0
        )
        {
          lastOfs=hint-maxOfs;
        }
        else
        {
          lastOfs=hint-1;
        }
        ofs=hint;
      }
      //assert -1<=lastOfs;
      //assert lastOfs<ofs;
      //assert ofs<=len;
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
      //assert lastOfs==ofs;
      return ofs;
    }
    /**
     * Like gallopLeft, except that if the range contains an element equal to key, gallopRight returns the index
     * after the rightmost equal element
     *
     * @param key the key to find the insertion point for
     * @param arr The array in which to search
     * @param base The index of the first element in the range
     * @param len The length of the range (must be >0)
     * @param hint The index at which to begin the search, 0 <= hint < len. The closer hint is to the result,
     *   this faster this method will run.
     * @param sorter The comparator used to order the range and search
     * @return the int k, 0<=k<=len such that arr[base+k-1]<= key<arr[base+k]
     */
    //TODO split this up into mergeLo and mergeHi versions
    private static  int gallopRight(short key,short[] arr,int base,int len,int hint,ShortComparator sorter)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((short)(key),(short)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(2>(maxOfs=hint+1)||
        sorter.compare((short)(key),(short)(arr[base+hint-1]))<0
        )
        {
          lastOfs=hint-maxOfs;
        }
        else
        {
          lastOfs=hint-1;
        }
        ofs=hint;
      }
      else
      {
        int maxOfs;
        if(2>(maxOfs=len-hint)||
        sorter.compare((short)(key),(short)(arr[base+hint+1]))>=0
        )
        {
          ofs=maxOfs+hint;
        }
        else
        {
          ofs=hint+1;
        }
        lastOfs=hint;
      }
      //assert -1<=lastOfs;
      //assert lastOfs<ofs;
      //assert ofs<=len;
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
      //assert lastOfs==ofs;
      return ofs;
    }
    private static  void binarySort(short[] arr,int lo,int hi,int start
      ,ShortComparator sorter
    )
    {
      //assert lo<=start;
      //assert start<=hi;
      //TODO streamline
      for(;start<hi;++start)
      {
        final
        short pivot=(short)arr[start];
        //assert lo<start;
        int left=lo;
        int right=start;
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
        //assert left==right;
        ArrCopy.semicheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    private static  int countRunAndMakeAscending(short[] arr,int lo,int hi
      ,ShortComparator sorter
    )
    {
      //TODO streamline
      //assert lo<hi;
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
  private static  void AscendinginsertSort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final short ai=(short)arr[i+1];
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
  private static  void DescendinginsertSort(short[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final short ai=(short)arr[i+1];
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
  private static  void insertSort(short[] arr,int begin,int end,ShortComparator sorter)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final short ai=(short)arr[i+1];
      short aj;
      while(
      sorter.compare((short)(ai),(short)(aj=arr[j]))<0
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
  private static void AscendingcountingSort(short[] arr,int begin,int end)
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
  private static void DescendingcountingSort(short[] arr,int begin,int end)
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
  private static  void sentinelInsertSort(short[] arr,int begin,int end,ShortComparator sorter)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    sorter.compare((short)(arr[begin]),(short)(arr[++begin]))<=0
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      short a1,a2;
      if(
      sorter.compare((short)(a1=(short)arr[k]),(short)(a2=(short)arr[begin]))<0
      )
      {
        a2=a1;
        a1=(short)arr[begin];
      }
      short ak;
      while(
      sorter.compare((short)(a1),(short)(ak=(short)arr[--k]))<0
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      sorter.compare((short)(a2),(short)(ak=(short)arr[--k]))<0
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae;
    short last=(short)arr[end];
    while(
    sorter.compare((short)(last),(short)(ae=(short)arr[--end]))<0
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static  void AscendingsentinelInsertSort(short[] arr,int begin,int end)
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
      (a1=(short)arr[k])<(a2=(short)arr[begin])
      )
      {
        a2=a1;
        a1=(short)arr[begin];
      }
      short ak;
      while(
      (a1)<(ak=(short)arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)<(ak=(short)arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae;
    short last=(short)arr[end];
    while(
    (last)<(ae=(short)arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static  void DescendingsentinelInsertSort(short[] arr,int begin,int end)
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
      (a1=(short)arr[k])>(a2=(short)arr[begin])
      )
      {
        a2=a1;
        a1=(short)arr[begin];
      }
      short ak;
      while(
      (a1)>(ak=(short)arr[--k])
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      (a2)>(ak=(short)arr[--k])
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    short ae;
    short last=(short)arr[end];
    while(
    (last)>(ae=(short)arr[--end])
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  private static  void quickSortleftmost(short[] arr,int begin,int end,ShortComparator sorter)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertSort(arr,begin,end,sorter);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    sorter.compare((short)(val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)]),(short)(val1=(short)arr[e1=e2-seventh]))<0
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    sorter.compare((short)(val3=(short)arr[e3]),(short)(val2))<0
    )
    {
      short tmp=(short)val3;
      val3=val2;
      if(
      sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val4=(short)arr[e4=e3+seventh]),(short)(val3))<0
    )
    {
      short tmp=(short)val4;
      val4=val3;
      if(
      sorter.compare((short)(tmp),(short)(val2))<0
      )
      {
        val3=val2;
        if(
        sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val5=(short)arr[e5=e4+seventh]),(short)(val4))<0
    )
    {
      short tmp=(short)val5;
      val5=val4;
      if(
      sorter.compare((short)(tmp),(short)(val3))<0
      )
      {
        val4=val3;
        if(
        sorter.compare((short)(tmp),(short)(val2))<0
        )
        {
          val3=val2;
          if(
          sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val1),(short)(val2))==0
    ||
    sorter.compare((short)(val2),(short)(val3))==0
    ||
    sorter.compare((short)(val3),(short)(val4))==0
    ||
    sorter.compare((short)(val4),(short)(val5))==0
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickSortleftmostSinglePivot(arr,begin,end,val3,sorter);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5,sorter);
    }
  }
  private static  void quickSort(short[] arr,int begin,int end,ShortComparator sorter)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       sentinelInsertSort(arr,begin,end,sorter);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    sorter.compare((short)(val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)]),(short)(val1=(short)arr[e1=e2-seventh]))<0
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    sorter.compare((short)(val3=(short)arr[e3]),(short)(val2))<0
    )
    {
      short tmp=(short)val3;
      val3=val2;
      if(
      sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val4=(short)arr[e4=e3+seventh]),(short)(val3))<0
    )
    {
      short tmp=(short)val4;
      val4=val3;
      if(
      sorter.compare((short)(tmp),(short)(val2))<0
      )
      {
        val3=val2;
        if(
        sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val5=(short)arr[e5=e4+seventh]),(short)(val4))<0
    )
    {
      short tmp=(short)val5;
      val5=val4;
      if(
      sorter.compare((short)(tmp),(short)(val3))<0
      )
      {
        val4=val3;
        if(
        sorter.compare((short)(tmp),(short)(val2))<0
        )
        {
          val3=val2;
          if(
          sorter.compare((short)(tmp),(short)(val1))<0
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
    sorter.compare((short)(val1),(short)(val2))==0
    ||
    sorter.compare((short)(val2),(short)(val3))==0
    ||
    sorter.compare((short)(val3),(short)(val4))==0
    ||
    sorter.compare((short)(val4),(short)(val5))==0
    )
    {
      arr[e2]=val2;
      arr[e4]=val4;
      quickSortSinglePivot(arr,begin,end,val3,sorter);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickSortDualPivot(arr,begin,end,val2,val4,e1,e5,sorter);
    }
  }
  private static  void quickAscendingSortleftmost(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      AscendinginsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=(short)arr[e1=e2-seventh])
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=(short)arr[e3])<(val2)
    )
    {
      short tmp=(short)val3;
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
    (val4=(short)arr[e4=e3+seventh])<(val3)
    )
    {
      short tmp=(short)val4;
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
    (val5=(short)arr[e5=e4+seventh])<(val4)
    )
    {
      short tmp=(short)val5;
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
      quickAscendingSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickAscendingSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static  void quickAscendingSort(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       AscendingsentinelInsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])<(val1=(short)arr[e1=e2-seventh])
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=(short)arr[e3])<(val2)
    )
    {
      short tmp=(short)val3;
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
    (val4=(short)arr[e4=e3+seventh])<(val3)
    )
    {
      short tmp=(short)val4;
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
    (val5=(short)arr[e5=e4+seventh])<(val4)
    )
    {
      short tmp=(short)val5;
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
      quickAscendingSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickAscendingSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static  void quickDescendingSortleftmost(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      DescendinginsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=(short)arr[e1=e2-seventh])
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=(short)arr[e3])>(val2)
    )
    {
      short tmp=(short)val3;
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
    (val4=(short)arr[e4=e3+seventh])>(val3)
    )
    {
      short tmp=(short)val4;
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
    (val5=(short)arr[e5=e4+seventh])>(val4)
    )
    {
      short tmp=(short)val5;
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
      quickDescendingSortleftmostSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickDescendingSortleftmostDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static  void quickDescendingSort(short[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       DescendingsentinelInsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    short val1,val2,val3,val4,val5;
    if(
    (val2=(short)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])>(val1=(short)arr[e1=e2-seventh])
    )
    {
      short tmp=(short)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    (val3=(short)arr[e3])>(val2)
    )
    {
      short tmp=(short)val3;
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
    (val4=(short)arr[e4=e3+seventh])>(val3)
    )
    {
      short tmp=(short)val4;
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
    (val5=(short)arr[e5=e4+seventh])>(val4)
    )
    {
      short tmp=(short)val5;
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
      quickDescendingSortSinglePivot(arr,begin,end,val3);
    }
    else
    {
       arr[e2]=arr[begin];
       arr[e4]=arr[end];
       quickDescendingSortDualPivot(arr,begin,end,val2,val4,e1,e5);
    }
  }
  private static  void quickSortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5,ShortComparator sorter)
  {
    int less=begin;
    int great=end;
    while(
    sorter.compare((short)(arr[++less]),(short)(pivot1))<=0
    )
    {
    }
    while(
    sorter.compare((short)(arr[--great]),(short)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      sorter.compare((short)(ak=(short)arr[k]),(short)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      sorter.compare((short)(ak),(short)(pivot2))>0
      )
      {
        short ag;
        while(
        sorter.compare((short)(ag=(short)arr[great]),(short)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        sorter.compare((short)(ag),(short)(pivot1))<0
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
    quickSortleftmost(arr,begin,less-2,sorter);
    quickSort(arr,great+2,end,sorter);
    if(less<e1 && e5<great)
    {
      while(
      sorter.compare((short)(arr[less]),(short)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      sorter.compare((short)(arr[great]),(short)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        sorter.compare((short)(ak=(short)arr[k]),(short)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        sorter.compare((short)(ak),(short)(pivot2))==0
        )
        {
          short ag;
          while(
          sorter.compare((short)(ag=(short)arr[great]),(short)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          sorter.compare((short)(ag),(short)(pivot1))==0
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
      quickSort(arr,less,great,sorter);
  }
  private static  void quickSortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5,ShortComparator sorter)
  {
    int less=begin;
    int great=end;
    while(
    sorter.compare((short)(arr[++less]),(short)(pivot1))<=0
    )
    {
    }
    while(
    sorter.compare((short)(arr[--great]),(short)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      short ak;
      if(
      sorter.compare((short)(ak=(short)arr[k]),(short)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      sorter.compare((short)(ak),(short)(pivot2))>0
      )
      {
        short ag;
        while(
        sorter.compare((short)(ag=(short)arr[great]),(short)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        sorter.compare((short)(ag),(short)(pivot1))<0
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
    quickSort(arr,begin,less-2,sorter);
    quickSort(arr,great+2,end,sorter);
    if(less<e1 && e5<great)
    {
      while(
      sorter.compare((short)(arr[less]),(short)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      sorter.compare((short)(arr[great]),(short)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        short ak;
        if(
        sorter.compare((short)(ak=(short)arr[k]),(short)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        sorter.compare((short)(ak),(short)(pivot2))==0
        )
        {
          short ag;
          while(
          sorter.compare((short)(ag=(short)arr[great]),(short)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          sorter.compare((short)(ag),(short)(pivot1))==0
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
      quickSort(arr,less,great,sorter);
  }
  private static  void quickAscendingSortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
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
      (ak=(short)arr[k])<(pivot1)
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
        (ag=(short)arr[great])>(pivot2)
        )
        {
          if(--great<k)
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
    quickAscendingSortleftmost(arr,begin,less-2);
    quickAscendingSort(arr,great+2,end);
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
        (ak=(short)arr[k])==(pivot1)
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
          (ag=(short)arr[great])==(pivot2)
          )
          {
            if(--great<k)
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
      quickAscendingSort(arr,less,great);
  }
  private static  void quickAscendingSortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
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
      (ak=(short)arr[k])<(pivot1)
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
        (ag=(short)arr[great])>(pivot2)
        )
        {
          if(--great<k)
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
    quickAscendingSort(arr,begin,less-2);
    quickAscendingSort(arr,great+2,end);
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
        (ak=(short)arr[k])==(pivot1)
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
          (ag=(short)arr[great])==(pivot2)
          )
          {
            if(--great<k)
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
      quickAscendingSort(arr,less,great);
  }
  private static  void quickDescendingSortleftmostDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
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
      (ak=(short)arr[k])>(pivot1)
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
        (ag=(short)arr[great])<(pivot2)
        )
        {
          if(--great<k)
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
    quickDescendingSortleftmost(arr,begin,less-2);
    quickDescendingSort(arr,great+2,end);
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
        (ak=(short)arr[k])==(pivot1)
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
          (ag=(short)arr[great])==(pivot2)
          )
          {
            if(--great<k)
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
      quickDescendingSort(arr,less,great);
  }
  private static  void quickDescendingSortDualPivot(short[] arr,int begin,int end,short pivot1, short pivot2,int e1,int e5)
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
      (ak=(short)arr[k])>(pivot1)
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
        (ag=(short)arr[great])<(pivot2)
        )
        {
          if(--great<k)
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
    quickDescendingSort(arr,begin,less-2);
    quickDescendingSort(arr,great+2,end);
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
        (ak=(short)arr[k])==(pivot1)
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
          (ag=(short)arr[great])==(pivot2)
          )
          {
            if(--great<k)
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
      quickDescendingSort(arr,less,great);
  }
  private static  void quickSortleftmostSinglePivot(short[] arr,int begin,int end,short pivot,ShortComparator sorter)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum(sorter.compare((short)(ak=arr[k]),(short)(pivot)))
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
          Integer.signum(sorter.compare((short)(pivot),(short)(ag=arr[great])))
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
    quickSortleftmost(arr,begin,less,sorter);
    }
    quickSort(arr,great+1,end,sorter);
  }
  private static  void quickSortSinglePivot(short[] arr,int begin,int end,short pivot,ShortComparator sorter)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      short ak;
      switch(
      Integer.signum(sorter.compare((short)(ak=arr[k]),(short)(pivot)))
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
          Integer.signum(sorter.compare((short)(pivot),(short)(ag=arr[great])))
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
    quickSort(arr,begin,less,sorter);
    }
    quickSort(arr,great+1,end,sorter);
  }
  private static  void quickAscendingSortleftmostSinglePivot(short[] arr,int begin,int end,short pivot)
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
    quickAscendingSortleftmost(arr,begin,less);
    }
    quickAscendingSort(arr,great+1,end);
  }
  private static  void quickAscendingSortSinglePivot(short[] arr,int begin,int end,short pivot)
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
    quickAscendingSort(arr,begin,less);
    }
    quickAscendingSort(arr,great+1,end);
  }
  private static  void quickDescendingSortleftmostSinglePivot(short[] arr,int begin,int end,short pivot)
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
    quickDescendingSortleftmost(arr,begin,less);
    }
    quickDescendingSort(arr,great+1,end);
  }
  private static  void quickDescendingSortSinglePivot(short[] arr,int begin,int end,short pivot)
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
    quickDescendingSort(arr,begin,less);
    }
    quickDescendingSort(arr,great+1,end);
  }
  private static  void merge(short[] arr,int begin,int end,int[] run,int count,ShortComparator sorter)
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
          sorter.compare((short)(arr[q+ao]),(short)(arr[p+ao]))<0
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
  private static  void Ascendingmerge(short[] arr,int begin,int end,int[] run,int count)
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
  private static  void Descendingmerge(short[] arr,int begin,int end,int[] run,int count)
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
}
