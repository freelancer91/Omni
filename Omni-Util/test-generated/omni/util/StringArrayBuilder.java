package omni.util;
import java.util.Collection;
import java.util.Random;
public enum StringArrayBuilder
{
  Randomized
  {
    @Override
    public int getNumRemoveIfReps(int arrLength)
    {
      return 10;
    }
    @Override
    public int getNumSortReps(int arrLength)
    {
      return 10;
    }
    @Override
    public boolean isRandomized()
    {
      return true;
    }
    @Override
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      length+=offset;
      do
      {
        arr[offset]=RandomUtil.getRandomString(rand);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertToString(m+i)){}
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;i<length;arr[(i++)+offset]=TypeConversionUtil.convertToString(length-m-i)){}
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      String val=TypeConversionUtil.convertToString(m);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertToString(++v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertToString(++v)){}
      for(int i=offset,bound=offset+length;i<bound;++i)
      {
        if(arr[i]==null)
        {
          arr[i]=TypeConversionUtil.convertToString(0);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int v=0;
      for(int i=0, k=0,period=length/m;k<m;++k)
      {
        v=0;
        for(int p=0;p<period;arr[(i++)+offset]=TypeConversionUtil.convertToString(--v),++p){}
      }
      for(int j=1;j<length-1;arr[j++]=TypeConversionUtil.convertToString(--v)){}
      for(int i=offset,bound=offset+length;i<bound;++i)
      {
        if(arr[i]==null)
        {
          arr[i]=TypeConversionUtil.convertToString(0);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      for(int incCount=1,decCount=length,i=0,period=m--;;period+=m)
      {
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertToString(incCount++))
        {
          if(i>=length)
          {
            return;
          }
        }
        period+=m;
        for(int k=0;++k<=period;arr[(i++)+offset]=TypeConversionUtil.convertToString(decCount--))
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      for(int period=length/m,i=0,k=0;;++k)
      {
        for(int t=0;++t<=period;arr[(i++)+offset]=TypeConversionUtil.convertToString(k))
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToString(i%m);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToString(rand.nextInt(m));
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      for(int i=0;;)
      {
        for(int t=0;++t<=m;arr[(i++)+offset]=TypeConversionUtil.convertToString(m))
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/(m+1);i<middle;arr[i+offset]=TypeConversionUtil.convertToString(i++)){}
      while(i<length)
      {
        arr[i+offset]=TypeConversionUtil.convertToString(length-(i++)-1);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToString((i*m+i)%length);
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
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      do
      {
        arr[offset+i]=TypeConversionUtil.convertToString(Math.min(i,m));
      }
      while(++i!=length);
    }
  }
  ,
  Shuffle
  {
    @Override
    public int getNumRemoveIfReps(int arrLength)
    {
      return 10;
    }
    @Override
    public int getNumSortReps(int arrLength)
    {
      return 10;
    }
    @Override
    public boolean isRandomized()
    {
      return true;
    }
    @Override
    public void buildUnchecked(String[] arr,int offset,int length,Random rand,int m)
    {
      int x=0,y=0;
      int bound=offset+length;
      do
      {
        arr[offset]=TypeConversionUtil.convertToString(rand.nextBoolean()?(x+2):(y+2));
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
  public int getNumSortReps(int arrLength)
  {
    return 1;
  }
  public int getNumRemoveIfReps(int arrLength)
  {
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
  public abstract void buildUnchecked(String[] arr,int offset,int length,Random rand,int m);
  public void build(String[] arr,Random rand,int m)
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
  public void build(String[] arr,int offset,int length,Random rand,int m)
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
    return "StringArrayBuilder."+this.name();
  }
  public void addArrays(long randSeed,int arrLength,Collection<String[]> arrays)
  {
    Random rand=new Random(randSeed);
    for(int m=getMLo(),mHi=getMHi(arrLength),numReps=getNumSortReps(arrLength);m<=mHi;m=incrementM(m))
    {
      for(int i=0;i<numReps;++i)
      {
        String[] arr=new String[arrLength];
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
  static String[] buildRandomArray(int arrLength,int minIncl,int maxIncl,Random rand)
  {
    String[] arr=new String[arrLength];
    for(int i=0;i<arrLength;++i)
    {
      arr[i]=TypeConversionUtil.convertToString(RandomUtil.randomIntBetween(minIncl,maxIncl,rand));
    }
    return arr;
  }
}
