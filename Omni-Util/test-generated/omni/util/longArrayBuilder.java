package omni.util;
import java.util.Collection;
import java.util.Random;
public enum longArrayBuilder
{
  Randomized
  {
    @Override
    public boolean isRandomized()
    {
      return true;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      length+=offset;
      do
      {
        arr[offset]=RandomUtil.getRandomlong(rand);
      }
      while(++offset!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      length+=offset;
      do
      {
        arr[offset]=RandomUtil.getRandomLong(rand);
      }
      while(++offset!=length);
    }
  }
  ,
  Ascending
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertTolong(m+i)){}
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertToLong(m+i)){}
    }
  }
  ,
  Descending
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertTolong(length-m-i)){}
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertToLong(length-m-i)){}
    }
  }
  ,
  AllEquals
  {
    @Override
    public int getMLo()
    {
      return 0;
    }
    @Override
    public int getMHi(int arrLength)
    {
      return 0;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      long val=TypeConversionUtil.convertTolong(m);
      length+=offset;
      do
      {
        arr[offset]=val;
      }
      while(++offset!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      Long val=TypeConversionUtil.convertToLong(m);
      length+=offset;
      do
      {
        arr[offset]=val;
      }
      while(++offset!=length);
    }
  }
  ,
  MergeAscending
  {
    @Override
    public int getMLo()
    {
      return 65;
    }
    @Override
    public int getMHi(int arrLength)
    {
      return 69;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertTolong(++v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertTolong(++v)){}
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertToLong(++v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertToLong(++v)){}
      for(int i=offset,bound=offset+length;i<bound;++i)
      {
        if(arr[i]==null)
        {
          arr[i]=TypeConversionUtil.convertToLong(0);
        }
      }
    }
  }
  ,
  MergeDescending
  {
    @Override
    public int getMLo()
    {
      return 65;
    }
    @Override
    public int getMHi(int arrLength)
    {
      return 69;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertTolong(--v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertTolong(--v)){}
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertToLong(--v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertToLong(--v)){}
      for(int i=offset,bound=offset+length;i<bound;++i)
      {
        if(arr[i]==null)
        {
          arr[i]=TypeConversionUtil.convertToLong(0);
        }
      }
    }
  }
  ,
  Saw
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      for(int incCount=1,decCount=length,i=0,period=m--;;period+=m)
      {
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertTolong(incCount++))
        {
          if(i>=length)
          {
            return;
          }
        }
        period+=m;
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertTolong(decCount--))
        {
          if(i>=length)
          {
            return;
          }
        }
      }
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      for(int incCount=1,decCount=length,i=0,period=m--;;period+=m)
      {
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertToLong(incCount++))
        {
          if(i>=length)
          {
            return;
          }
        }
        period+=m;
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertToLong(decCount--))
        {
          if(i>=length)
          {
            return;
          }
        }
      }
    }
  }
  ,
  SortedRepeated
  {
    @Override
    public int getMHi(int arrLength)
    {
      return Math.min(arrLength,7);
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      for(int period=length/m,i=0,k=0;;++k)
      {
        for(int t=0;++t<=period;arr[(i++)+offset]=TypeConversionUtil.convertTolong(k))
        {
          if(i>=length)
          {
            return;
          }
        }
        if(i>=length)
        {
          return;
        }
      }
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      for(int period=length/m,i=0,k=0;;++k)
      {
        for(int t=0;++t<=period;arr[(i++)+offset]=TypeConversionUtil.convertToLong(k))
        {
          if(i>=length)
          {
            return;
          }
        }
        if(i>=length)
        {
          return;
        }
      }
    }
  }
  ,
  Repeated
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertTolong(i%m);
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToLong(i%m);
      }
      while(++i!=length);
    }
  }
  ,
  Duplicated
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public boolean isRandomized()
    {
      return true;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertTolong(rand.nextInt(m));
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToLong(rand.nextInt(m));
      }
      while(++i!=length);
    }
  }
  ,
  SortedOrganPipes
  {
    @Override
    public int getMHi(int arrLength)
    {
      return Math.min(arrLength,7);
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;;)
      {
        for(int t=0;++t<=m;arr[(i++)+offset]=TypeConversionUtil.convertTolong(m))
        {
          if(i>=length)
          {
            return;
          }
        }
      }
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;;)
      {
        for(int t=0;++t<=m;arr[(i++)+offset]=TypeConversionUtil.convertToLong(m))
        {
          if(i>=length)
          {
            return;
          }
        }
      }
    }
  }
  ,
  OrganPipes
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/(m+1);i<middle;arr[i+offset]=TypeConversionUtil.convertTolong(i++)){}
      while(i<length)
      {
        arr[i+offset]=TypeConversionUtil.convertTolong(length-(i++)-1);
      }
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/(m+1);i<middle;arr[i+offset]=TypeConversionUtil.convertToLong(i++)){}
      while(i<length)
      {
        arr[i+offset]=TypeConversionUtil.convertToLong(length-(i++)-1);
      }
    }
  }
  ,
  Stagger
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertTolong((i*m+i)%length);
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToLong((i*m+i)%length);
      }
      while(++i!=length);
    }
  }
  ,
  Plateau
  {
    @Override
    public int getMHi(int arrLength)
    {
      return (arrLength<<1)-1;
    }
    public int incrementM(int m)
    {
      return m<<1;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[offset+i]=TypeConversionUtil.convertTolong(Math.min(i,m));
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[offset+i]=TypeConversionUtil.convertToLong(Math.min(i,m));
      }
      while(++i!=length);
    }
  }
  ,
  Shuffle
  {
    @Override
    public boolean isRandomized()
    {
      return true;
    }
    @Override
    public void buildUnchecked(long[] arr,int offset,int length,Random rand,int m)
    {
      int x=0,y=0;
      int bound=offset+length;
      do
      {
        arr[offset]=TypeConversionUtil.convertTolong(rand.nextBoolean()?(x+2):(y+2));
      }
      while(++offset!=bound);
    }
    @Override
    public void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m)
    {
      int x=0,y=0;
      int bound=offset+length;
      do
      {
        arr[offset]=TypeConversionUtil.convertToLong(rand.nextBoolean()?(x+2):(y+2));
      }
      while(++offset!=bound);
    }
  }
  ;
  public int getMLo()
  {
    return 1;
  }
  public int getMHi(int arrLength)
  {
    return 1;
  }
  public int getNumReps(int arrLength)
  {
    if(isRandomized())
    {
      return 10;
    }
    return 1;
  }
  public int incrementM(int m)
  {
    return m+1;
  }
  public boolean isRandomized()
  {
    //most sub-types are not randomized, so make that the default
    return false;
  }
  public abstract void buildUnchecked(long[] arr,int offset,int length,Random rand,int m);
  public void build(long[] arr,Random rand,int m)
  {
    if(arr==null)
    {
      throw new NullPointerException("arr cannot be null");
    }
    int length;
    if((length=arr.length)!=0)
    {
      buildUnchecked(arr,0,length,rand,m);
    }
  }  
  public void build(long[] arr,int offset,int length,Random rand,int m)
  {
    if(arr==null)
    {
      throw new NullPointerException("arr cannot be null");
    }
    if(offset<0 || length<0 || offset+length>arr.length)
    {
      throw new ArrayIndexOutOfBoundsException("offset = "+offset+"; length="+length+"; arr.length="+arr.length);
    }
    if(length!=0)
    {
      buildUnchecked(arr,offset,length,rand,m);
    }
  }
  public abstract void buildUnchecked(Long[] arr,int offset,int length,Random rand,int m);
  public void build(Long[] arr,Random rand,int m)
  {
    if(arr==null)
    {
      throw new NullPointerException("arr cannot be null");
    }
    int length;
    if((length=arr.length)!=0)
    {
      buildUnchecked(arr,0,length,rand,m);
    }
  }  
  public void build(Long[] arr,int offset,int length,Random rand,int m)
  {
    if(arr==null)
    {
      throw new NullPointerException("arr cannot be null");
    }
    if(offset<0 || length<0 || offset+length>arr.length)
    {
      throw new ArrayIndexOutOfBoundsException("offset = "+offset+"; length="+length+"; arr.length="+arr.length);
    }
    if(length!=0)
    {
      buildUnchecked(arr,offset,length,rand,m);
    }
  }
  @Override
  public String toString()
  {
    return "longArrayBuilder."+this.name();
  }
  public void addArrays(long randSeed,int arrLength,Collection<long[]> arrays)
  {
    Random rand=new Random(randSeed);
    for(int m=getMLo(),mHi=getMHi(arrLength),numReps=getNumReps(arrLength);m<=mHi;m=incrementM(m))
    {
      for(int i=0;i<numReps;++i)
      {
        long[] arr=new long[arrLength];
        if(arrLength!=0)
        {
          buildUnchecked(arr,0,arrLength,rand,m);
        }
        synchronized(arrays)
        {
          arrays.add(arr);
        }
      }
    }
  }
  static long[] buildRandomArray(int arrLength,int minIncl,int maxIncl,Random rand)
  {
    long[] arr=new long[arrLength];
    for(int i=0;i<arrLength;++i)
    {
      arr[i]=TypeConversionUtil.convertTolong(RandomUtil.randomIntBetween(minIncl,maxIncl,rand));
    }
    return arr;
  }
}
