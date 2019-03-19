package omni.util;
import java.util.Collection;
import java.util.Random;
import java.util.function.BooleanSupplier;
public enum booleanArrayBuilder
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
    public BooleanSupplier getSupplier(int length,Random rand, int m)
    {
      return ()->
      {
        return RandomUtil.getRandomboolean(rand);
      };
    }
    @Override
    public void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int m)
    {
      length+=offset;
      do
      {
        arr[offset]=RandomUtil.getRandomboolean(rand);
      }
      while(++offset!=length);
    }
    @Override
    public void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int m)
    {
      length+=offset;
      do
      {
        arr[offset]=RandomUtil.getRandomBoolean(rand);
      }
      while(++offset!=length);
    }
  }
  ,
  Ascending
  {
    @Override
    public BooleanSupplier getSupplier(int length,Random rand, int m)
    {
      return new BooleanSupplier()
      {
        int i=0;
        final int middle=length/2;
        public boolean getAsBoolean()
        {
          boolean ret=TypeConversionUtil.convertToboolean(i<middle?0:1);
          if(++i==length)
          {
            i=0;
          }
          return ret;
        }
      };
    }
    @Override
    public void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/2;i<middle;arr[(i++)+offset]=TypeConversionUtil.convertToboolean(0)){}
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToboolean(1);
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/2;i<middle;arr[(i++)+offset]=TypeConversionUtil.convertToBoolean(0)){}
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToBoolean(1);
      }
      while(++i!=length);
    }
  }
  ,
  Descending
  {
    @Override
    public BooleanSupplier getSupplier(int length,Random rand, int m)
    {
      return new BooleanSupplier()
      {
        int i=0;
        final int middle=length/2;
        public boolean getAsBoolean()
        {
          boolean ret=TypeConversionUtil.convertToboolean(i<middle?1:0);
          if(++i==length)
          {
            i=0;
          }
          return ret;
        }
      };
    }
    @Override
    public void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/2;i<middle;arr[(i++)+offset]=TypeConversionUtil.convertToboolean(1)){}
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToboolean(0);
      }
      while(++i!=length);
    }
    @Override
    public void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int m)
    {
      int i=0;
      for(int middle=length/2;i<middle;arr[(i++)+offset]=TypeConversionUtil.convertToBoolean(1)){}
      do
      {
        arr[i+offset]=TypeConversionUtil.convertToBoolean(0);
      }
      while(++i!=length);
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
    public BooleanSupplier getSupplier(int length,Random rand, int m)
    {
      return ()->
      {
        return TypeConversionUtil.convertToboolean(m);
      };
    }
    @Override
    public void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int m)
    {
      boolean val=TypeConversionUtil.convertToboolean(m);
      length+=offset;
      do
      {
        arr[offset]=val;
      }
      while(++offset!=length);
    }
    @Override
    public void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int m)
    {
      Boolean val=TypeConversionUtil.convertToBoolean(m);
      length+=offset;
      do
      {
        arr[offset]=val;
      }
      while(++offset!=length);
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
  public abstract BooleanSupplier getSupplier(int length,Random rand, int m);
  public abstract void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int m);
  public void build(boolean[] arr,Random rand,int m)
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
  public void build(boolean[] arr,int offset,int length,Random rand,int m)
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
  public abstract void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int m);
  public void build(Boolean[] arr,Random rand,int m)
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
  public void build(Boolean[] arr,int offset,int length,Random rand,int m)
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
    return "booleanArrayBuilder."+this.name();
  }
  public void addArrays(long randSeed,int arrLength,Collection<boolean[]> arrays)
  {
    Random rand=new Random(randSeed);
    for(int m=getMLo(),mHi=getMHi(arrLength),numReps=getNumSortReps(arrLength);m<=mHi;m=incrementM(m))
    {
      for(int i=0;i<numReps;++i)
      {
        boolean[] arr=new boolean[arrLength];
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
  static boolean[] buildRandomArray(int arrLength,int minIncl,int maxIncl,Random rand)
  {
    boolean[] arr=new boolean[arrLength];
    for(int i=0;i<arrLength;++i)
    {
      arr[i]=TypeConversionUtil.convertToboolean(RandomUtil.randomIntBetween(minIncl,maxIncl,rand));
    }
    return arr;
  }
}
