package omni.util;
import java.util.Comparator;
public final class RefSortUtil
{
  private RefSortUtil()
  {
    super();
  }
    @SuppressWarnings("unchecked")
    public static <E> void uncheckedUnstableSort(Object[] arr,int offset,int bound,Comparator<? super E> sorter)
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
           Integer.signum(sorter.compare((E)(arr[k]),(E)(arr[k+1])))
           )
           {
           case -1:
             for(;;)
             {
               if(++k==bound ||
               sorter.compare((E)(arr[k]),(E)(arr[k-1]))<0
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
               sorter.compare((E)(arr[k-1]),(E)(arr[k]))<0
               )
               {
                 break;
               }
             }
             OmniArray.OfRef.reverseRange(arr,run[count],k-1);
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
           sorter.compare((E)(arr[r=run[count]]),(E)(arr[r-1]))<0
           && ++count==67)
           {
             quickSortleftmost(arr,offset,bound,sorter);
             break;
           }
           run[count]=k;
         }
       }
    }
  @SuppressWarnings("unchecked")
  public static <E> void uncheckedUnstableAscendingSort(Object[] arr,int offset,int bound)
  {
    if((--bound)-offset<286)
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
        Integer.signum(((Comparable<E>)(arr[k])).compareTo((E)(arr[k+1])))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==bound ||
            ((Comparable<E>)(arr[k])).compareTo((E)(arr[k-1]))<0
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
            ((Comparable<E>)(arr[k-1])).compareTo((E)(arr[k]))<0
            )
            {
              break;
            }
          }
          OmniArray.OfRef.reverseRange(arr,run[count],k-1);
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
        ((Comparable<E>)(arr[r=run[count]])).compareTo((E)(arr[r-1]))<0
        && ++count==67)
        {
          quickAscendingSortleftmost(arr,offset,bound);
          break;
        }
        run[count]=k;
      }
    }
  }
  @SuppressWarnings("unchecked")
  public static <E> void uncheckedUnstableDescendingSort(Object[] arr,int offset,int bound)
  {
    if((--bound)-offset<286)
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
        Integer.signum(((Comparable<E>)(arr[k+1])).compareTo((E)(arr[k])))
        )
        {
        case -1:
          for(;;)
          {
            if(++k==bound ||
            ((Comparable<E>)(arr[k])).compareTo((E)(arr[k-1]))>0
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
            ((Comparable<E>)(arr[k-1])).compareTo((E)(arr[k]))>0
            )
            {
              break;
            }
          }
          OmniArray.OfRef.reverseRange(arr,run[count],k-1);
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
        ((Comparable<E>)(arr[r=run[count]])).compareTo((E)(arr[r-1]))>0
        && ++count==67)
        {
          quickDescendingSortleftmost(arr,offset,bound);
          break;
        }
        run[count]=k;
      }
    }
  }
  public static <E> void uncheckedStableSort(Object[] arr,int offset,int bound,Comparator<? super E> sorter)
  {
    int nRemaining;
    if((nRemaining=bound-offset)<32)
    {
      RefTimSort.binarySort(arr,offset,bound,offset+RefTimSort.countRunAndMakeAscending(arr,offset,bound,sorter),sorter);
      return;
    }
    new RefTimSort<E>(arr,nRemaining,sorter,offset,bound);
  }
  private static class RefTimSort<E>
    extends AbstractRefTimSort<E>
  {
    private transient final Comparator<? super E> sorter;
    private RefTimSort(Object[] arr,int nRemaining,Comparator<? super E> sorter,int offset,int bound)
    {
      super(arr,nRemaining);
      this.sorter=sorter;
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=RefTimSort.countRunAndMakeAscending(arr,offset,bound,sorter))<minRun)
        {
          int force;
          RefTimSort.binarySort(arr,offset,offset+(force=nRemaining<=minRun?nRemaining:minRun),offset+runLen,sorter);
          runLen=force;
        }
        super.mergeCollapse(offset,runLen);
        offset+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert offset==bound;
      super.mergeForceCollapse(); 
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
      //assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,len2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      int base2=runLenAndBase[n+3];
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k,base1;
      //TODO streamline this?
      base1=(base1=runLenAndBase[n+1])+(k=mergeAtGallopRight((E)(arr=this.arr)[base2],arr,base1,len1,sorter));
      //assert k>=0;
      if((len1-=k)!=0)
      {
        //if((len2=mergeAtGallopLeft((E)arr[base1+len1-1],arr,base2,len2),sorter)!=0)
        //{
        //  if(len1<=len2)
        if(len1<=(len2=mergeAtGallopLeft((E)arr[base1+len1-1],arr,base2,len2,sorter)))
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
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopLeft(E key,Object[] arr,int base,int len,Comparator<? super E> sorter)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((E)(key),(E)(arr[base+len-1]))>0
      )
      {
        return len;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO LessThanOrEqualTo(key,arr[base+len-2])
      //)
      if(
      sorter.compare((E)(key),(E)(arr[base+len-2]))<=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopRight(E key,Object[] arr,int base,int len,Comparator<? super E> sorter)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((E)(key),(E)(arr[base]))<0
      )
      {
        return 0;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO GreaterThanOrEqualTo(key,arr[base+1])
      //)
      if(
      sorter.compare((E)(key),(E)(arr[base+1]))>=0
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
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the first run into the tmp array
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len1>1;
          //assert len2>0;
          if((count1=gallopRight((E)arr[cursor2],tmp,cursor1,len1,0,sorter))!=0)
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
          if((count2=gallopLeft((E)tmp[cursor1],arr,cursor2,len2,0,sorter))!=0)
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
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the second run into the tmp array
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len2>1;
          //assert len1>0;
          if((count1=len1-gallopRight((E)tmp[cursor2],arr,base1,len1,len1-1,sorter))!=0)
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
          if((count2=len2-gallopLeft((E)arr[cursor1],tmp,tmpOffset,len2,len2-1,sorter))!=0)
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
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(E key,Object[] arr,int base,int len,int hint,Comparator<? super E> sorter)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((E)(key),(E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(2>(maxOfs=len-hint)||
        sorter.compare((E)(key),(E)(arr[base+hint+1]))>0
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
        sorter.compare((E)(key),(E)(arr[base+hint-1]))<=0
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
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(E key,Object[] arr,int base,int len,int hint,Comparator<? super E> sorter)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      sorter.compare((E)(key),(E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(2>(maxOfs=hint+1)||
        sorter.compare((E)(key),(E)(arr[base+hint-1]))<0
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
        sorter.compare((E)(key),(E)(arr[base+hint+1]))>=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
      ,Comparator<? super E> sorter
    )
    {
      //assert lo<=start;
      //assert start<=hi;
      //TODO streamline
      for(;start<hi;++start)
      {
        final
        E pivot=(E)arr[start];
        //assert lo<start;
        int left=lo;
        int right=start;
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
        //assert left==right;
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
      //assert lo<hi;
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
  public static <E> void uncheckedStableAscendingSort(Object[] arr,int offset,int bound)
  {
    int nRemaining;
    if((nRemaining=bound-offset)<32)
    {
      AscendingRefTimSort.binarySort(arr,offset,bound,offset+AscendingRefTimSort.countRunAndMakeAscending(arr,offset,bound));
      return;
    }
    new AscendingRefTimSort<E>(arr,nRemaining,offset,bound);
  }
  private static class AscendingRefTimSort<E> extends AbstractRefTimSort<E>
  {
    private AscendingRefTimSort(Object[] arr,int nRemaining,int offset,int bound)
    {
      super(arr,nRemaining);
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=AscendingRefTimSort.countRunAndMakeAscending(arr,offset,bound))<minRun)
        {
          int force;
          AscendingRefTimSort.binarySort(arr,offset,offset+(force=nRemaining<=minRun?nRemaining:minRun),offset+runLen);
          runLen=force;
        }
        super.mergeCollapse(offset,runLen);
        offset+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert offset==bound;
      super.mergeForceCollapse(); 
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
      //assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,len2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      int base2=runLenAndBase[n+3];
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k,base1;
      //TODO streamline this?
      base1=(base1=runLenAndBase[n+1])+(k=mergeAtGallopRight((Comparable<E>)(arr=this.arr)[base2],arr,base1,len1));
      //assert k>=0;
      if((len1-=k)!=0)
      {
        //if((len2=mergeAtGallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2))!=0)
        //{
        //  if(len1<=len2)
        if(len1<=(len2=mergeAtGallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2)))
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
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopLeft(Comparable<E> key,Object[] arr,int base,int len)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+len-1]))>0
      )
      {
        return len;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO LessThanOrEqualTo(key,arr[base+len-2])
      //)
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+len-2]))<=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopRight(Comparable<E> key,Object[] arr,int base,int len)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base]))<0
      )
      {
        return 0;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO GreaterThanOrEqualTo(key,arr[base+1])
      //)
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+1]))>=0
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
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the first run into the tmp array
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len1>1;
          //assert len2>0;
          if((count1=gallopRight((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0))!=0)
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
          if((count2=gallopLeft((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0))!=0)
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
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the second run into the tmp array
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len2>1;
          //assert len1>0;
          if((count1=len1-gallopRight((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1))!=0)
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
          if((count2=len2-gallopLeft((Comparable<E>)arr[cursor1],tmp,tmpOffset,len2,len2-1))!=0)
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
     * @return the int k, 0<=k<=len such that arr[base+k-1]< key<=arr[base+k],
     *   pretending that arr[base-1] is minus infinity or add[base+len] is infinity.
     *   In other words, key belonds at index base + k; or in other words, the first k elements should precede the key,
     *   and the last len-k elements should follow it.
     */
    //TODO split this up into mergeLo and mergeHi versions
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(2>(maxOfs=len-hint)||
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+1]))>0
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
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-1]))<=0
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
     * @return the int k, 0<=k<=len such that arr[base+k-1]<= key<arr[base+k]
     */
    //TODO split this up into mergeLo and mergeHi versions
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(2>(maxOfs=hint+1)||
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-1]))<0
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
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+1]))>=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
    )
    {
      //assert lo<=start;
      //assert start<=hi;
      //TODO streamline
      for(;start<hi;++start)
      {
        final
        Comparable<E> pivot=(Comparable<E>)arr[start];
        //assert lo<start;
        int left=lo;
        int right=start;
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
        //assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int countRunAndMakeAscending(Object[] arr,int lo,int hi
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
  public static <E> void uncheckedStableDescendingSort(Object[] arr,int offset,int bound)
  {
    int nRemaining;
    if((nRemaining=bound-offset)<32)
    {
      DescendingRefTimSort.binarySort(arr,offset,bound,offset+DescendingRefTimSort.countRunAndMakeAscending(arr,offset,bound));
      return;
    }
    new DescendingRefTimSort<E>(arr,nRemaining,offset,bound);
  }
  private static class DescendingRefTimSort<E> extends AbstractRefTimSort<E>
  {
    private DescendingRefTimSort(Object[] arr,int nRemaining,int offset,int bound)
    {
      super(arr,nRemaining);
      int minRun=AbstractTimSort.minRunLength(nRemaining);
      int runLen;
      do
      {
        if((runLen=DescendingRefTimSort.countRunAndMakeAscending(arr,offset,bound))<minRun)
        {
          int force;
          DescendingRefTimSort.binarySort(arr,offset,offset+(force=nRemaining<=minRun?nRemaining:minRun),offset+runLen);
          runLen=force;
        }
        super.mergeCollapse(offset,runLen);
        offset+=runLen;
      }
      while((nRemaining-=runLen)!=0);
      //assert offset==bound;
      super.mergeForceCollapse(); 
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
      //assert runLenAndBase[n]+runLenAndBase[n+1]==runLenAndBase[n+3];
      int len1,len2;
      runLenAndBase[n]=(len1=runLenAndBase[n])+(len2=runLenAndBase[n+2]);
      int base2=runLenAndBase[n+3];
      if(n==stackSize-6)
      {
        runLenAndBase[n+3]=runLenAndBase[n+5];
        runLenAndBase[n+2]=runLenAndBase[n+4];
      }
      Object[] arr;
      int k,base1;
      //TODO streamline this?
      base1=(base1=runLenAndBase[n+1])+(k=mergeAtGallopRight((Comparable<E>)(arr=this.arr)[base2],arr,base1,len1));
      //assert k>=0;
      if((len1-=k)!=0)
      {
        //if((len2=mergeAtGallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2))!=0)
        //{
        //  if(len1<=len2)
        if(len1<=(len2=mergeAtGallopLeft((Comparable<E>)arr[base1+len1-1],arr,base2,len2)))
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
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopLeft(Comparable<E> key,Object[] arr,int base,int len)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+len-1]))<0
      )
      {
        return len;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO LessThanOrEqualTo(key,arr[base+len-2])
      //)
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+len-2]))>=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> int mergeAtGallopRight(Comparable<E> key,Object[] arr,int base,int len)
    {
      //assert len>0;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base]))>0
      )
      {
        return 0;
      }
      //assert len>=2;
      //#if(len<2 ||
      //#MACRO GreaterThanOrEqualTo(key,arr[base+1])
      //)
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+1]))<=0
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
    @SuppressWarnings("unchecked")
    private void mergeLo(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the first run into the tmp array
      int cursor1;
      int dest;
      ArrCopy.uncheckedCopy(arr,dest=base1,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len1>1;
          //assert len2>0;
          if((count1=gallopRight((Comparable<E>)arr[cursor2],tmp,cursor1,len1,0))!=0)
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
          if((count2=gallopLeft((Comparable<E>)tmp[cursor1],arr,cursor2,len2,0))!=0)
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
    @SuppressWarnings("unchecked")
    private void mergeHi(Object[] arr,int base1,int len1,int base2,int len2)
    {
      //assert len1>0;
      //assert len2>0;
      //assert base1+len1==base2;
      final Object[] tmp;
      //copy the second run into the tmp array
      int tmpOffset;
      ArrCopy.uncheckedCopy(arr,base2,tmp=
      super.
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
        //One run is winning so consistently that galloping may be a huge win.
        //So try that, an continue galloping until (if ever) neither run appears to be wnning consistently anymore
        do
        {
          //assert len2>1;
          //assert len1>0;
          if((count1=len1-gallopRight((Comparable<E>)tmp[cursor2],arr,base1,len1,len1-1))!=0)
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
          if((count2=len2-gallopLeft((Comparable<E>)arr[cursor1],tmp,tmpOffset,len2,len2-1))!=0)
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
     * @return the int k, 0<=k<=len such that arr[base+k-1]< key<=arr[base+k],
     *   pretending that arr[base-1] is minus infinity or add[base+len] is infinity.
     *   In other words, key belonds at index base + k; or in other words, the first k elements should precede the key,
     *   and the last len-k elements should follow it.
     */
    //TODO split this up into mergeLo and mergeHi versions
    @SuppressWarnings("unchecked")
    private static <E> int gallopLeft(Comparable<E> key,Object[] arr,int base,int len,int hint)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))<0
      )
      {
        int maxOfs;
        if(2>(maxOfs=len-hint)||
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+1]))<0
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
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-1]))>=0
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
     * @return the int k, 0<=k<=len such that arr[base+k-1]<= key<arr[base+k]
     */
    //TODO split this up into mergeLo and mergeHi versions
    @SuppressWarnings("unchecked")
    private static <E> int gallopRight(Comparable<E> key,Object[] arr,int base,int len,int hint)
    {
      //assert len>0;
      //assert hint>=0;
      //assert hint<len;
      int ofs;
      int lastOfs;
      if(
      ((Comparable<E>)(key)).compareTo((E)(arr[base+hint]))>0
      )
      {
        int maxOfs;
        if(2>(maxOfs=hint+1)||
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint-1]))>0
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
        ((Comparable<E>)(key)).compareTo((E)(arr[base+hint+1]))<=0
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
      //assert lastOfs==ofs;
      return ofs;
    }
    @SuppressWarnings("unchecked")
    private static <E> void binarySort(Object[] arr,int lo,int hi,int start
    )
    {
      //assert lo<=start;
      //assert start<=hi;
      //TODO streamline
      for(;start<hi;++start)
      {
        final
        Comparable<E> pivot=(Comparable<E>)arr[start];
        //assert lo<start;
        int left=lo;
        int right=start;
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
        //assert left==right;
        ArrCopy.uncheckedCopy(arr,left,arr,left+1,start-left);
        arr[left]=pivot;
      }
    }
    @SuppressWarnings("unchecked")
    private static <E> int countRunAndMakeAscending(Object[] arr,int lo,int hi
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
  @SuppressWarnings("unchecked")
  private static <E> void AscendinginsertSort(Object[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final Comparable<E> ai=(Comparable<E>)arr[i+1];
      Object aj;
      while(
      ((Comparable<E>)(ai)).compareTo((E)(aj=arr[j]))<0
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
  @SuppressWarnings("unchecked")
  private static <E> void DescendinginsertSort(Object[] arr,int begin,int end)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final Comparable<E> ai=(Comparable<E>)arr[i+1];
      Object aj;
      while(
      ((Comparable<E>)(ai)).compareTo((E)(aj=arr[j]))>0
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
  @SuppressWarnings("unchecked")
  private static <E> void insertSort(Object[] arr,int begin,int end,Comparator<? super E> sorter)
  {
    for(int i=begin,j=i;i!=end;j=++i)
    {
      final E ai=(E)arr[i+1];
      Object aj;
      while(
      sorter.compare((E)(ai),(E)(aj=arr[j]))<0
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
  @SuppressWarnings("unchecked")
  private static <E> void sentinelInsertSort(Object[] arr,int begin,int end,Comparator<? super E> sorter)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    sorter.compare((E)(arr[begin]),(E)(arr[++begin]))<=0
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      E a1,a2;
      if(
      sorter.compare((E)(a1=(E)arr[k]),(E)(a2=(E)arr[begin]))<0
      )
      {
        a2=a1;
        a1=(E)arr[begin];
      }
      E ak;
      while(
      sorter.compare((E)(a1),(E)(ak=(E)arr[--k]))<0
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      sorter.compare((E)(a2),(E)(ak=(E)arr[--k]))<0
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    E ae;
    E last=(E)arr[end];
    while(
    sorter.compare((E)(last),(E)(ae=(E)arr[--end]))<0
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  @SuppressWarnings("unchecked")
  private static <E> void AscendingsentinelInsertSort(Object[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    ((Comparable<E>)(arr[begin])).compareTo((E)(arr[++begin]))<=0
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      Comparable<E> a1,a2;
      if(
      ((Comparable<E>)(a1=(Comparable<E>)arr[k])).compareTo((E)(a2=(Comparable<E>)arr[begin]))<0
      )
      {
        a2=a1;
        a1=(Comparable<E>)arr[begin];
      }
      E ak;
      while(
      ((Comparable<E>)(a1)).compareTo((E)(ak=(E)arr[--k]))<0
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      ((Comparable<E>)(a2)).compareTo((E)(ak=(E)arr[--k]))<0
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    E ae;
    Comparable<E> last=(Comparable<E>)arr[end];
    while(
    ((Comparable<E>)(last)).compareTo((E)(ae=(E)arr[--end]))<0
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  @SuppressWarnings("unchecked")
  private static <E> void DescendingsentinelInsertSort(Object[] arr,int begin,int end)
  {
    do
    {
      if(begin>=end)
      {
        return;
      }
    }
    while(
    ((Comparable<E>)(arr[begin])).compareTo((E)(arr[++begin]))>=0
    )
    ;
    for(int k=begin;++begin<=end;k=++begin)
    {
      Comparable<E> a1,a2;
      if(
      ((Comparable<E>)(a1=(Comparable<E>)arr[k])).compareTo((E)(a2=(Comparable<E>)arr[begin]))>0
      )
      {
        a2=a1;
        a1=(Comparable<E>)arr[begin];
      }
      E ak;
      while(
      ((Comparable<E>)(a1)).compareTo((E)(ak=(E)arr[--k]))>0
      )
      {
        arr[k+2]=ak;
      }
      arr[++k+1]=a1;
      while(
      ((Comparable<E>)(a2)).compareTo((E)(ak=(E)arr[--k]))>0
      )
      {
        arr[k+1]=ak;
      }
      arr[k+1]=a2;
    }
    E ae;
    Comparable<E> last=(Comparable<E>)arr[end];
    while(
    ((Comparable<E>)(last)).compareTo((E)(ae=(E)arr[--end]))>0
    )
    {
      arr[end+1]=ae;
    }
    arr[end+1]=last;
  }
  @SuppressWarnings("unchecked")
  private static <E> void quickSortleftmost(Object[] arr,int begin,int end,Comparator<? super E> sorter)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      insertSort(arr,begin,end,sorter);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    E val1,val2,val3,val4,val5;
    if(
    sorter.compare((E)(val2=(E)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)]),(E)(val1=(E)arr[e1=e2-seventh]))<0
    )
    {
      E tmp=(E)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    sorter.compare((E)(val3=(E)arr[e3]),(E)(val2))<0
    )
    {
      E tmp=(E)val3;
      val3=val2;
      if(
      sorter.compare((E)(tmp),(E)(val1))<0
      )
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
    sorter.compare((E)(val4=(E)arr[e4=e3+seventh]),(E)(val3))<0
    )
    {
      E tmp=(E)val4;
      val4=val3;
      if(
      sorter.compare((E)(tmp),(E)(val2))<0
      )
      {
        val3=val2;
        if(
        sorter.compare((E)(tmp),(E)(val1))<0
        )
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
    sorter.compare((E)(val5=(E)arr[e5=e4+seventh]),(E)(val4))<0
    )
    {
      E tmp=(E)val5;
      val5=val4;
      if(
      sorter.compare((E)(tmp),(E)(val3))<0
      )
      {
        val4=val3;
        if(
        sorter.compare((E)(tmp),(E)(val2))<0
        )
        {
          val3=val2;
          if(
          sorter.compare((E)(tmp),(E)(val1))<0
          )
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
    sorter.compare((E)(val1),(E)(val2))==0
    ||
    sorter.compare((E)(val2),(E)(val3))==0
    ||
    sorter.compare((E)(val3),(E)(val4))==0
    ||
    sorter.compare((E)(val4),(E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickSort(Object[] arr,int begin,int end,Comparator<? super E> sorter)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       sentinelInsertSort(arr,begin,end,sorter);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    E val1,val2,val3,val4,val5;
    if(
    sorter.compare((E)(val2=(E)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)]),(E)(val1=(E)arr[e1=e2-seventh]))<0
    )
    {
      E tmp=(E)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    sorter.compare((E)(val3=(E)arr[e3]),(E)(val2))<0
    )
    {
      E tmp=(E)val3;
      val3=val2;
      if(
      sorter.compare((E)(tmp),(E)(val1))<0
      )
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
    sorter.compare((E)(val4=(E)arr[e4=e3+seventh]),(E)(val3))<0
    )
    {
      E tmp=(E)val4;
      val4=val3;
      if(
      sorter.compare((E)(tmp),(E)(val2))<0
      )
      {
        val3=val2;
        if(
        sorter.compare((E)(tmp),(E)(val1))<0
        )
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
    sorter.compare((E)(val5=(E)arr[e5=e4+seventh]),(E)(val4))<0
    )
    {
      E tmp=(E)val5;
      val5=val4;
      if(
      sorter.compare((E)(tmp),(E)(val3))<0
      )
      {
        val4=val3;
        if(
        sorter.compare((E)(tmp),(E)(val2))<0
        )
        {
          val3=val2;
          if(
          sorter.compare((E)(tmp),(E)(val1))<0
          )
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
    sorter.compare((E)(val1),(E)(val2))==0
    ||
    sorter.compare((E)(val2),(E)(val3))==0
    ||
    sorter.compare((E)(val3),(E)(val4))==0
    ||
    sorter.compare((E)(val4),(E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSortleftmost(Object[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      AscendinginsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    Comparable<E> val1,val2,val3,val4,val5;
    if(
    ((Comparable<E>)(val2=(Comparable<E>)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])).compareTo((E)(val1=(Comparable<E>)arr[e1=e2-seventh]))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    ((Comparable<E>)(val3=(Comparable<E>)arr[e3])).compareTo((E)(val2))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val3;
      val3=val2;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
      )
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
    ((Comparable<E>)(val4=(Comparable<E>)arr[e4=e3+seventh])).compareTo((E)(val3))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val4;
      val4=val3;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val2))<0
      )
      {
        val3=val2;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
        )
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
    ((Comparable<E>)(val5=(Comparable<E>)arr[e5=e4+seventh])).compareTo((E)(val4))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val5;
      val5=val4;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val3))<0
      )
      {
        val4=val3;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val2))<0
        )
        {
          val3=val2;
          if(
          ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
          )
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
    ((Comparable<E>)(val1)).compareTo((E)(val2))==0
    ||
    ((Comparable<E>)(val2)).compareTo((E)(val3))==0
    ||
    ((Comparable<E>)(val3)).compareTo((E)(val4))==0
    ||
    ((Comparable<E>)(val4)).compareTo((E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSort(Object[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       AscendingsentinelInsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    Comparable<E> val1,val2,val3,val4,val5;
    if(
    ((Comparable<E>)(val2=(Comparable<E>)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])).compareTo((E)(val1=(Comparable<E>)arr[e1=e2-seventh]))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    ((Comparable<E>)(val3=(Comparable<E>)arr[e3])).compareTo((E)(val2))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val3;
      val3=val2;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
      )
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
    ((Comparable<E>)(val4=(Comparable<E>)arr[e4=e3+seventh])).compareTo((E)(val3))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val4;
      val4=val3;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val2))<0
      )
      {
        val3=val2;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
        )
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
    ((Comparable<E>)(val5=(Comparable<E>)arr[e5=e4+seventh])).compareTo((E)(val4))<0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val5;
      val5=val4;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val3))<0
      )
      {
        val4=val3;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val2))<0
        )
        {
          val3=val2;
          if(
          ((Comparable<E>)(tmp)).compareTo((E)(val1))<0
          )
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
    ((Comparable<E>)(val1)).compareTo((E)(val2))==0
    ||
    ((Comparable<E>)(val2)).compareTo((E)(val3))==0
    ||
    ((Comparable<E>)(val3)).compareTo((E)(val4))==0
    ||
    ((Comparable<E>)(val4)).compareTo((E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSortleftmost(Object[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
      DescendinginsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    Comparable<E> val1,val2,val3,val4,val5;
    if(
    ((Comparable<E>)(val2=(Comparable<E>)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])).compareTo((E)(val1=(Comparable<E>)arr[e1=e2-seventh]))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    ((Comparable<E>)(val3=(Comparable<E>)arr[e3])).compareTo((E)(val2))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val3;
      val3=val2;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
      )
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
    ((Comparable<E>)(val4=(Comparable<E>)arr[e4=e3+seventh])).compareTo((E)(val3))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val4;
      val4=val3;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val2))>0
      )
      {
        val3=val2;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
        )
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
    ((Comparable<E>)(val5=(Comparable<E>)arr[e5=e4+seventh])).compareTo((E)(val4))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val5;
      val5=val4;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val3))>0
      )
      {
        val4=val3;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val2))>0
        )
        {
          val3=val2;
          if(
          ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
          )
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
    ((Comparable<E>)(val1)).compareTo((E)(val2))==0
    ||
    ((Comparable<E>)(val2)).compareTo((E)(val3))==0
    ||
    ((Comparable<E>)(val3)).compareTo((E)(val4))==0
    ||
    ((Comparable<E>)(val4)).compareTo((E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSort(Object[] arr,int begin,int end)
  {
    int length;
    if((length=end-begin+1)<47)
    {
       DescendingsentinelInsertSort(arr,begin,end);
      return;
    }
    int seventh,e1,e2,e3,e4,e5;
    Comparable<E> val1,val2,val3,val4,val5;
    if(
    ((Comparable<E>)(val2=(Comparable<E>)arr[e2=(e3=(begin+end)>>>1)-(seventh=(length>>3)+(length>>6)+1)])).compareTo((E)(val1=(Comparable<E>)arr[e1=e2-seventh]))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val2;
      val2=val1;
      val1=tmp;
    }
    if(
    ((Comparable<E>)(val3=(Comparable<E>)arr[e3])).compareTo((E)(val2))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val3;
      val3=val2;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
      )
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
    ((Comparable<E>)(val4=(Comparable<E>)arr[e4=e3+seventh])).compareTo((E)(val3))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val4;
      val4=val3;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val2))>0
      )
      {
        val3=val2;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
        )
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
    ((Comparable<E>)(val5=(Comparable<E>)arr[e5=e4+seventh])).compareTo((E)(val4))>0
    )
    {
      Comparable<E> tmp=(Comparable<E>)val5;
      val5=val4;
      if(
      ((Comparable<E>)(tmp)).compareTo((E)(val3))>0
      )
      {
        val4=val3;
        if(
        ((Comparable<E>)(tmp)).compareTo((E)(val2))>0
        )
        {
          val3=val2;
          if(
          ((Comparable<E>)(tmp)).compareTo((E)(val1))>0
          )
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
    ((Comparable<E>)(val1)).compareTo((E)(val2))==0
    ||
    ((Comparable<E>)(val2)).compareTo((E)(val3))==0
    ||
    ((Comparable<E>)(val3)).compareTo((E)(val4))==0
    ||
    ((Comparable<E>)(val4)).compareTo((E)(val5))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickSortleftmostDualPivot(Object[] arr,int begin,int end,Object pivot1, Object pivot2,int e1,int e5,Comparator<? super E> sorter)
  {
    int less=begin;
    int great=end;
    while(
    sorter.compare((E)(arr[++less]),(E)(pivot1))<=0
    )
    {
    }
    while(
    sorter.compare((E)(arr[--great]),(E)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      E ak;
      if(
      sorter.compare((E)(ak=(E)arr[k]),(E)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      sorter.compare((E)(ak),(E)(pivot2))>0
      )
      {
        E ag;
        while(
        sorter.compare((E)(ag=(E)arr[great]),(E)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        sorter.compare((E)(ag),(E)(pivot1))<0
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
      sorter.compare((E)(arr[less]),(E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      sorter.compare((E)(arr[great]),(E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        E ak;
        if(
        sorter.compare((E)(ak=(E)arr[k]),(E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        sorter.compare((E)(ak),(E)(pivot2))==0
        )
        {
          E ag;
          while(
          sorter.compare((E)(ag=(E)arr[great]),(E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          sorter.compare((E)(ag),(E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickSortDualPivot(Object[] arr,int begin,int end,Object pivot1, Object pivot2,int e1,int e5,Comparator<? super E> sorter)
  {
    int less=begin;
    int great=end;
    while(
    sorter.compare((E)(arr[++less]),(E)(pivot1))<=0
    )
    {
    }
    while(
    sorter.compare((E)(arr[--great]),(E)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      E ak;
      if(
      sorter.compare((E)(ak=(E)arr[k]),(E)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      sorter.compare((E)(ak),(E)(pivot2))>0
      )
      {
        E ag;
        while(
        sorter.compare((E)(ag=(E)arr[great]),(E)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        sorter.compare((E)(ag),(E)(pivot1))<0
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
      sorter.compare((E)(arr[less]),(E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      sorter.compare((E)(arr[great]),(E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        E ak;
        if(
        sorter.compare((E)(ak=(E)arr[k]),(E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        sorter.compare((E)(ak),(E)(pivot2))==0
        )
        {
          E ag;
          while(
          sorter.compare((E)(ag=(E)arr[great]),(E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          sorter.compare((E)(ag),(E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSortleftmostDualPivot(Object[] arr,int begin,int end,Object pivot1, Comparable<E> pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    ((Comparable<E>)(arr[++less])).compareTo((E)(pivot1))<=0
    )
    {
    }
    while(
    ((Comparable<E>)(arr[--great])).compareTo((E)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      Comparable<E> ak;
      if(
      ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      ((Comparable<E>)(ak)).compareTo((E)(pivot2))>0
      )
      {
        Comparable<E> ag;
        while(
        ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        ((Comparable<E>)(ag)).compareTo((E)(pivot1))<0
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
      ((Comparable<E>)(arr[less])).compareTo((E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      ((Comparable<E>)(arr[great])).compareTo((E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        Comparable<E> ak;
        if(
        ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        ((Comparable<E>)(ak)).compareTo((E)(pivot2))==0
        )
        {
          Comparable<E> ag;
          while(
          ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          ((Comparable<E>)(ag)).compareTo((E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSortDualPivot(Object[] arr,int begin,int end,Object pivot1, Comparable<E> pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    ((Comparable<E>)(arr[++less])).compareTo((E)(pivot1))<=0
    )
    {
    }
    while(
    ((Comparable<E>)(arr[--great])).compareTo((E)(pivot2))>=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      Comparable<E> ak;
      if(
      ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))<0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      ((Comparable<E>)(ak)).compareTo((E)(pivot2))>0
      )
      {
        Comparable<E> ag;
        while(
        ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))>0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        ((Comparable<E>)(ag)).compareTo((E)(pivot1))<0
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
      ((Comparable<E>)(arr[less])).compareTo((E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      ((Comparable<E>)(arr[great])).compareTo((E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        Comparable<E> ak;
        if(
        ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        ((Comparable<E>)(ak)).compareTo((E)(pivot2))==0
        )
        {
          Comparable<E> ag;
          while(
          ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          ((Comparable<E>)(ag)).compareTo((E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSortleftmostDualPivot(Object[] arr,int begin,int end,Object pivot1, Comparable<E> pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    ((Comparable<E>)(arr[++less])).compareTo((E)(pivot1))>=0
    )
    {
    }
    while(
    ((Comparable<E>)(arr[--great])).compareTo((E)(pivot2))<=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      Comparable<E> ak;
      if(
      ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))>0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      ((Comparable<E>)(ak)).compareTo((E)(pivot2))<0
      )
      {
        Comparable<E> ag;
        while(
        ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))<0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        ((Comparable<E>)(ag)).compareTo((E)(pivot1))>0
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
      ((Comparable<E>)(arr[less])).compareTo((E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      ((Comparable<E>)(arr[great])).compareTo((E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        Comparable<E> ak;
        if(
        ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        ((Comparable<E>)(ak)).compareTo((E)(pivot2))==0
        )
        {
          Comparable<E> ag;
          while(
          ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          ((Comparable<E>)(ag)).compareTo((E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSortDualPivot(Object[] arr,int begin,int end,Object pivot1, Comparable<E> pivot2,int e1,int e5)
  {
    int less=begin;
    int great=end;
    while(
    ((Comparable<E>)(arr[++less])).compareTo((E)(pivot1))>=0
    )
    {
    }
    while(
    ((Comparable<E>)(arr[--great])).compareTo((E)(pivot2))<=0
    )
    {
    }
    outer: for(int k=less;k<=great;++k)
    {
      Comparable<E> ak;
      if(
      ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))>0
      )
      {
        arr[k]=arr[less];
        arr[less]=ak;
        ++less;
      }
      else
      if(
      ((Comparable<E>)(ak)).compareTo((E)(pivot2))<0
      )
      {
        Comparable<E> ag;
        while(
        ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))<0
        )
        {
          if(--great<k)
          {
            break outer;
          }
        }
        if(
        ((Comparable<E>)(ag)).compareTo((E)(pivot1))>0
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
      ((Comparable<E>)(arr[less])).compareTo((E)(pivot1))==0
      )
      {
        ++less;
      }
      while(
      ((Comparable<E>)(arr[great])).compareTo((E)(pivot2))==0
      )
      {
        --great;
      }
      outer: for(int k=less;k<=great;++k)
      {
        Comparable<E> ak;
        if(
        ((Comparable<E>)(ak=(Comparable<E>)arr[k])).compareTo((E)(pivot1))==0
        )
        {
          arr[k]=arr[less];
          arr[less]=ak;
          ++less;
        }
        else
        if(
        ((Comparable<E>)(ak)).compareTo((E)(pivot2))==0
        )
        {
          Comparable<E> ag;
          while(
          ((Comparable<E>)(ag=(Comparable<E>)arr[great])).compareTo((E)(pivot2))==0
          )
          {
            if(--great<k)
            {
              break outer;
            }
          }
          if(
          ((Comparable<E>)(ag)).compareTo((E)(pivot1))==0
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
  @SuppressWarnings("unchecked")
  private static <E> void quickSortleftmostSinglePivot(Object[] arr,int begin,int end,Object pivot,Comparator<? super E> sorter)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(sorter.compare((E)(ak=arr[k]),(E)(pivot)))
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
          Object ag;
          switch(
          Integer.signum(sorter.compare((E)(pivot),(E)(ag=arr[great])))
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
  @SuppressWarnings("unchecked")
  private static <E> void quickSortSinglePivot(Object[] arr,int begin,int end,Object pivot,Comparator<? super E> sorter)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(sorter.compare((E)(ak=arr[k]),(E)(pivot)))
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
          Object ag;
          switch(
          Integer.signum(sorter.compare((E)(pivot),(E)(ag=arr[great])))
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSortleftmostSinglePivot(Object[] arr,int begin,int end,Comparable<E> pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(((Comparable<E>)(ak=arr[k])).compareTo((E)(pivot)))
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
          Object ag;
          switch(
          Integer.signum(((Comparable<E>)(pivot)).compareTo((E)(ag=arr[great])))
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
  @SuppressWarnings("unchecked")
  private static <E> void quickAscendingSortSinglePivot(Object[] arr,int begin,int end,Comparable<E> pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(((Comparable<E>)(ak=arr[k])).compareTo((E)(pivot)))
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
          Object ag;
          switch(
          Integer.signum(((Comparable<E>)(pivot)).compareTo((E)(ag=arr[great])))
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSortleftmostSinglePivot(Object[] arr,int begin,int end,Comparable<E> pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(((Comparable<E>)(pivot)).compareTo((E)(ak=arr[k])))
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
          Object ag;
          switch(
          Integer.signum(((Comparable<E>)(ag=arr[great])).compareTo((E)(pivot)))
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
  @SuppressWarnings("unchecked")
  private static <E> void quickDescendingSortSinglePivot(Object[] arr,int begin,int end,Comparable<E> pivot)
  {
    int less=begin;
    int great=end;
    for(int k=less;k<=great;++k)
    {
      Object ak;
      switch(
      Integer.signum(((Comparable<E>)(pivot)).compareTo((E)(ak=arr[k])))
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
          Object ag;
          switch(
          Integer.signum(((Comparable<E>)(ag=arr[great])).compareTo((E)(pivot)))
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
  @SuppressWarnings("unchecked")
  private static <E> void merge(Object[] arr,int begin,int end,int[] run,int count,Comparator<? super E> sorter)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    Object[] b;
    int ao,bo,blen;
    var work=new Object[blen=end-begin+1];
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
          sorter.compare((E)(arr[q+ao]),(E)(arr[p+ao]))<0
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
  @SuppressWarnings("unchecked")
  private static <E> void Ascendingmerge(Object[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    Object[] b;
    int ao,bo,blen;
    var work=new Object[blen=end-begin+1];
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
          ((Comparable<E>)(arr[q+ao])).compareTo((E)(arr[p+ao]))<0
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
  @SuppressWarnings("unchecked")
  private static <E> void Descendingmerge(Object[] arr,int begin,int end,int[] run,int count)
  {
    byte odd=0;
    for(int n=1;(n<<=1)<=count;odd^=1){}
    Object[] b;
    int ao,bo,blen;
    var work=new Object[blen=end-begin+1];
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
          ((Comparable<E>)(arr[q+ao])).compareTo((E)(arr[p+ao]))>0
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
  private static abstract class AbstractRefTimSort<E> extends AbstractTimSort
  {
    transient final Object[] arr;
    transient Object[] tmp;
    transient int tmpLength;
    transient int tmpOffset;
    transient int minGallop;
    AbstractRefTimSort(Object[] arr,int nRemaining)
    {
      super(nRemaining);
      this.arr=arr;
      this.tmpLength=nRemaining=nRemaining<512?nRemaining>>>1:256;
      this.tmp=new Object[nRemaining];
      this.minGallop=7;
    }
    /**
     * Ensures that the external array tmp has at least the specified
     * number of elements, increasing its size if necessary.  The size
     * increases exponentially to ensure amortized linear time complexity.
     *
     * @param minCapacity the minimum required capacity of the tmp array
     * @return tmp, whether or not it grew
     */
    private Object[] ensureCapacity(int minCapacity)
    {
      Object[] tmp;
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
