package omni.util;
import java.util.Random;
public interface JunitUtil
{
  public static boolean convertToboolean(int val)
  {
    return (val&0b1)!=0;
  }
  public static byte convertTobyte(int val)
  {
    return (byte)val;
  }
  public static char convertTochar(int val)
  {
    return (char)val;
  }
  public static short convertToshort(int val)
  {
    return (short)val;
  }
  public static int convertToint(int val)
  {
    return (int)val;
  }
  public static long convertTolong(int val)
  {
    return (long)val;
  }
  public static float convertTofloat(int val)
  {
    return (float)val;
  }
  public static double convertTodouble(int val)
  {
    return (double)val;
  }
  @SuppressWarnings("deprecation")
  public static Boolean convertToBoolean(int val)
  {
    return new Boolean((val&0b1)!=0);
  }
  @SuppressWarnings("deprecation")
  public static Byte convertToByte(int val)
  {
    return new Byte((byte)val);
  }
  @SuppressWarnings("deprecation")
  public static Character convertToCharacter(int val)
  {
    return new Character((char)val);
  }
  @SuppressWarnings("deprecation")
  public static Short convertToShort(int val)
  {
    return new Short((short)val);
  }
  @SuppressWarnings("deprecation")
  public static Integer convertToInteger(int val)
  {
    return new Integer(val);
  }
  @SuppressWarnings("deprecation")
  public static Long convertToLong(int val)
  {
    return new Long(val);
  }
  @SuppressWarnings("deprecation")
  public static Float convertToFloat(int val)
  {
    return new Float(val);
  }
  @SuppressWarnings("deprecation")
  public static Double convertToDouble(int val)
  {
    return new Double(val);
  }
  public static String convertToString(int val)
  {
    return Integer.toString(val);
  }
  public static boolean getRandomboolean(Random rand)
  {
    return rand.nextBoolean();
  }
  public static byte getRandombyte(Random rand)
  {
    return (byte)rand.nextLong();
  }
  public static char getRandomchar(Random rand)
  {
    return (char)rand.nextLong();
  }
  public static short getRandomshort(Random rand)
  {
    return (short)rand.nextLong();
  }
  public static int getRandomint(Random rand)
  {
    return (int)rand.nextLong();
  }
  public static long getRandomlong(Random rand)
  {
    return (long)rand.nextLong();
  }
  public static float getRandomfloat(Random rand)
  {
    return rand.nextFloat();
  }
  public static double getRandomdouble(Random rand)
  {
    return rand.nextDouble();
  }
  @SuppressWarnings("deprecation")
  public static Boolean getRandomBoolean(Random rand)
  {
    return new Boolean(rand.nextBoolean());
  }
  @SuppressWarnings("deprecation")
  public static Byte getRandomByte(Random rand)
  {
    return new Byte((byte)rand.nextInt());
  }
  @SuppressWarnings("deprecation")
  public static Character getRandomCharacter(Random rand)
  {
    return new Character((char)rand.nextInt());
  }
  @SuppressWarnings("deprecation")
  public static Short getRandomShort(Random rand)
  {
    return new Short((short)rand.nextInt());
  }
  @SuppressWarnings("deprecation")
  public static Integer getRandomInteger(Random rand)
  {
    return new Integer(rand.nextInt());
  }
  @SuppressWarnings("deprecation")
  public static Long getRandomLong(Random rand)
  {
    return new Long(rand.nextLong());
  }
  @SuppressWarnings("deprecation")
  public static Float getRandomFloat(Random rand)
  {
    return new Float(rand.nextFloat());
  }
  @SuppressWarnings("deprecation")
  public static Double getRandomDouble(Random rand)
  {
    return new Double(rand.nextDouble());
  }
  public static String getRandomString(Random rand)
  {
    return Long.toString(rand.nextLong());
  }
  public static enum booleanArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomboolean(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomBoolean(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int...params)
      {
        int i=0;
        for(int middle=length/2;i<middle;arr[(i++)+offset]=convertToboolean(0)){}
        do
        {
          arr[i+offset]=convertToboolean(1);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int...params)
      {
        int i=0;
        for(int middle=length/2;i<middle;arr[(i++)+offset]=convertToBoolean(0)){}
        do
        {
          arr[i+offset]=convertToBoolean(1);
        }
        while(++i!=length);
      }
    }
    ,
    Descending
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int...params)
      {
        int i=0;
        for(int middle=length/2;i<middle;arr[(i++)+offset]=convertToboolean(1)){}
        do
        {
          arr[i+offset]=convertToboolean(0);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int...params)
      {
        int i=0;
        for(int middle=length/2;i<middle;arr[(i++)+offset]=convertToBoolean(1)){}
        do
        {
          arr[i+offset]=convertToBoolean(0);
        }
        while(++i!=length);
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        boolean val=convertToboolean(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Boolean val=convertToBoolean(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(boolean[] arr,int offset,int length,Random rand,int...params);
    public void build(boolean[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(boolean[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Boolean[] arr,int offset,int length,Random rand,int...params);
    public void build(Boolean[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Boolean[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "booleanArrayBuilder."+this.name();
    }
  }
  public static enum byteArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandombyte(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomByte(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTobyte(m+i)){}
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToByte(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTobyte(length-m-i)){}
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToByte(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        byte val=convertTobyte(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Byte val=convertToByte(params[0]);
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
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTobyte(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTobyte(++v)){}
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToByte(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToByte(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTobyte(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTobyte(--v)){}
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToByte(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToByte(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTobyte(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTobyte(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToByte(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToByte(decCount--))
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
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertTobyte(k))
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
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToByte(k))
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
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTobyte(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToByte(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTobyte(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToByte(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertTobyte(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToByte(m))
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
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertTobyte(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertTobyte(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToByte(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToByte(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTobyte((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToByte((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertTobyte(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToByte(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertTobyte(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToByte(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(byte[] arr,int offset,int length,Random rand,int...params);
    public void build(byte[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(byte[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Byte[] arr,int offset,int length,Random rand,int...params);
    public void build(Byte[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Byte[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "byteArrayBuilder."+this.name();
    }
  }
  public static enum charArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomchar(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomCharacter(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTochar(m+i)){}
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToCharacter(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTochar(length-m-i)){}
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToCharacter(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        char val=convertTochar(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Character val=convertToCharacter(params[0]);
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
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTochar(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTochar(++v)){}
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToCharacter(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToCharacter(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTochar(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTochar(--v)){}
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToCharacter(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToCharacter(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTochar(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTochar(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToCharacter(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToCharacter(decCount--))
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
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertTochar(k))
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
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToCharacter(k))
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
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTochar(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToCharacter(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTochar(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToCharacter(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertTochar(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToCharacter(m))
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
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertTochar(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertTochar(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToCharacter(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToCharacter(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTochar((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToCharacter((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertTochar(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToCharacter(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertTochar(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToCharacter(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(char[] arr,int offset,int length,Random rand,int...params);
    public void build(char[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(char[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Character[] arr,int offset,int length,Random rand,int...params);
    public void build(Character[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Character[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "charArrayBuilder."+this.name();
    }
  }
  public static enum shortArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomshort(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomShort(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToshort(m+i)){}
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToShort(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToshort(length-m-i)){}
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToShort(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        short val=convertToshort(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Short val=convertToShort(params[0]);
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
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToshort(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToshort(++v)){}
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToShort(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToShort(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToshort(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToshort(--v)){}
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToShort(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToShort(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToshort(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToshort(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToShort(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToShort(decCount--))
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
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToshort(k))
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
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToShort(k))
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
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToshort(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToShort(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToshort(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToShort(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToshort(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToShort(m))
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
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToshort(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToshort(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToShort(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToShort(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToshort((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToShort((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToshort(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToShort(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToshort(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToShort(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(short[] arr,int offset,int length,Random rand,int...params);
    public void build(short[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(short[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Short[] arr,int offset,int length,Random rand,int...params);
    public void build(Short[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Short[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "shortArrayBuilder."+this.name();
    }
  }
  public static enum intArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomint(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomInteger(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToint(m+i)){}
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToInteger(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToint(length-m-i)){}
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToInteger(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int val=convertToint(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Integer val=convertToInteger(params[0]);
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
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToint(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToint(++v)){}
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToInteger(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToInteger(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToint(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToint(--v)){}
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToInteger(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToInteger(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToint(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToint(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToInteger(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToInteger(decCount--))
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
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToint(k))
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
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToInteger(k))
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
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToint(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToInteger(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToint(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToInteger(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToint(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToInteger(m))
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
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToint(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToint(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToInteger(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToInteger(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToint((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToInteger((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToint(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToInteger(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToint(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToInteger(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(int[] arr,int offset,int length,Random rand,int...params);
    public void build(int[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(int[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Integer[] arr,int offset,int length,Random rand,int...params);
    public void build(Integer[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Integer[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "intArrayBuilder."+this.name();
    }
  }
  public static enum longArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomlong(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomLong(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTolong(m+i)){}
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToLong(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTolong(length-m-i)){}
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToLong(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        long val=convertTolong(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Long val=convertToLong(params[0]);
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
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTolong(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTolong(++v)){}
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToLong(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToLong(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTolong(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTolong(--v)){}
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToLong(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToLong(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTolong(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTolong(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToLong(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToLong(decCount--))
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
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertTolong(k))
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
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToLong(k))
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
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTolong(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToLong(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTolong(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToLong(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertTolong(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToLong(m))
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
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertTolong(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertTolong(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToLong(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToLong(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTolong((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToLong((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertTolong(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToLong(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertTolong(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToLong(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(long[] arr,int offset,int length,Random rand,int...params);
    public void build(long[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(long[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Long[] arr,int offset,int length,Random rand,int...params);
    public void build(Long[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Long[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "longArrayBuilder."+this.name();
    }
  }
  public static enum floatArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomfloat(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomFloat(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTofloat(m+i)){}
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToFloat(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTofloat(length-m-i)){}
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToFloat(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        float val=convertTofloat(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Float val=convertToFloat(params[0]);
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
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTofloat(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTofloat(++v)){}
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToFloat(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToFloat(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTofloat(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTofloat(--v)){}
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToFloat(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToFloat(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTofloat(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTofloat(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToFloat(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToFloat(decCount--))
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
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertTofloat(k))
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
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToFloat(k))
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
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTofloat(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToFloat(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTofloat(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToFloat(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertTofloat(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToFloat(m))
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
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertTofloat(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertTofloat(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToFloat(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToFloat(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTofloat((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToFloat((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertTofloat(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToFloat(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertTofloat(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToFloat(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ,
    WithNaNsAndZeros
    {
      @Override
      public int numRequiredParams()
      {
        return 5;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<5)
        {
          throw new IllegalArgumentException(this.toString()+" requires 5 parameters");
        }
        float negVal=-getRandomfloat(rand);
        float posVal=getRandomfloat(rand);
        for(int toIndex=offset+params[0];offset<toIndex;++offset)
        {
          arr[offset]=negVal;
        }
        for(int toIndex=offset+params[1];offset<toIndex;++offset)
        {
          arr[offset]=(float)-0.0;
        }
        for(int toIndex=offset+params[2];offset<toIndex;++offset)
        {
          arr[offset]=(float)0.0;
        }
        for(int toIndex=offset+params[3];offset<toIndex;++offset)
        {
          arr[offset]=posVal;
        }
        for(int toIndex=offset+params[4];offset<toIndex;++offset)
        {
          arr[offset]=(float)Float.NaN;
        }
      }
      @SuppressWarnings("deprecation")
      @Override
      void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<5)
        {
          throw new IllegalArgumentException(this.toString()+" requires 5 parameters");
        }
        Float negVal=-getRandomFloat(rand);
        Float posVal=getRandomFloat(rand);
        for(int toIndex=offset+params[0];offset<toIndex;++offset)
        {
          arr[offset]=negVal;
        }
        for(int toIndex=offset+params[1];offset<toIndex;++offset)
        {
          arr[offset]=new Float(-0.0f);
        }
        for(int toIndex=offset+params[2];offset<toIndex;++offset)
        {
          arr[offset]=new Float(0.0f);
        }
        for(int toIndex=offset+params[3];offset<toIndex;++offset)
        {
          arr[offset]=posVal;
        }
        for(int toIndex=offset+params[4];offset<toIndex;++offset)
        {
          arr[offset]=new Float(Float.NaN);
        }
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(float[] arr,int offset,int length,Random rand,int...params);
    public void build(float[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(float[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Float[] arr,int offset,int length,Random rand,int...params);
    public void build(Float[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Float[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "floatArrayBuilder."+this.name();
    }
  }
  public static enum doubleArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomdouble(rand);
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomDouble(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTodouble(m+i)){}
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToDouble(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertTodouble(length-m-i)){}
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToDouble(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        double val=convertTodouble(params[0]);
        length+=offset;
        do
        {
          arr[offset]=val;
        }
        while(++offset!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        Double val=convertToDouble(params[0]);
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
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTodouble(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTodouble(++v)){}
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToDouble(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToDouble(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertTodouble(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertTodouble(--v)){}
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToDouble(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToDouble(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTodouble(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertTodouble(decCount--))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToDouble(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToDouble(decCount--))
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
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertTodouble(k))
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
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToDouble(k))
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
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTodouble(i%m);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToDouble(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTodouble(rand.nextInt(m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToDouble(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertTodouble(m))
          {
            if(i>=length)
            {
              return;
            }
          }
        }
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToDouble(m))
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
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertTodouble(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertTodouble(length-(i++)-1);
        }
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToDouble(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToDouble(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertTodouble((i*m+i)%length);
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToDouble((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertTodouble(Math.min(i,m));
        }
        while(++i!=length);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToDouble(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertTodouble(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToDouble(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ,
    WithNaNsAndZeros
    {
      @Override
      public int numRequiredParams()
      {
        return 5;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<5)
        {
          throw new IllegalArgumentException(this.toString()+" requires 5 parameters");
        }
        double negVal=-getRandomdouble(rand);
        double posVal=getRandomdouble(rand);
        for(int toIndex=offset+params[0];offset<toIndex;++offset)
        {
          arr[offset]=negVal;
        }
        for(int toIndex=offset+params[1];offset<toIndex;++offset)
        {
          arr[offset]=(double)-0.0;
        }
        for(int toIndex=offset+params[2];offset<toIndex;++offset)
        {
          arr[offset]=(double)0.0;
        }
        for(int toIndex=offset+params[3];offset<toIndex;++offset)
        {
          arr[offset]=posVal;
        }
        for(int toIndex=offset+params[4];offset<toIndex;++offset)
        {
          arr[offset]=(double)Double.NaN;
        }
      }
      @SuppressWarnings("deprecation")
      @Override
      void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<5)
        {
          throw new IllegalArgumentException(this.toString()+" requires 5 parameters");
        }
        Double negVal=-getRandomDouble(rand);
        Double posVal=getRandomDouble(rand);
        for(int toIndex=offset+params[0];offset<toIndex;++offset)
        {
          arr[offset]=negVal;
        }
        for(int toIndex=offset+params[1];offset<toIndex;++offset)
        {
          arr[offset]=new Double(-0.0);
        }
        for(int toIndex=offset+params[2];offset<toIndex;++offset)
        {
          arr[offset]=new Double(0.0);
        }
        for(int toIndex=offset+params[3];offset<toIndex;++offset)
        {
          arr[offset]=posVal;
        }
        for(int toIndex=offset+params[4];offset<toIndex;++offset)
        {
          arr[offset]=new Double(Double.NaN);
        }
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(double[] arr,int offset,int length,Random rand,int...params);
    public void build(double[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(double[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    abstract void buildUnchecked(Double[] arr,int offset,int length,Random rand,int...params);
    public void build(Double[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(Double[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "doubleArrayBuilder."+this.name();
    }
  }
  public static enum StringArrayBuilder
  {
    Randomized
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        length+=offset;
        do
        {
          arr[offset]=getRandomString(rand);
        }
        while(++offset!=length);
      }
    }
    ,
    Ascending
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToString(m+i)){}
      }
    }
    ,
    Descending
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int i=0,m=params[0];i<length;arr[(i++)+offset]=convertToString(length-m-i)){}
      }
    }
    ,
    AllEquals
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        String val=convertToString(params[0]);
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
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToString(++v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToString(++v)){}
      }
    }
    ,
    MergeDescending
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int v=0;
        for(int m=params[0],i=0, k=0,period=length/m;k<m;++k)
        {
          v=0;
          for(int p=0;p<period;arr[(i++)+offset]=convertToString(--v),++p){}
        }
        for(int j=1;j<length-1;arr[j++]=convertToString(--v)){}
      }
    }
    ,
    Saw
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],incCount=1,decCount=length,i=0,period=m--;;period+=m)
        {
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToString(incCount++))
          {
            if(i>=length)
            {
              return;
            }
          }
          period+=m;
          for(int k=0;++k<=period;arr[(i++)+offset]=convertToString(decCount--))
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
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int period=length/params[0],i=0,k=0;;++k)
        {
          for(int t=0;++t<=period;arr[(i++)+offset]=convertToString(k))
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
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToString(i%m);
        }
        while(++i!=length);
      }
    }
    ,
    Duplicated
    {
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToString(rand.nextInt(m));
        }
        while(++i!=length);
      }
    }
    ,
    SortedOrganPipes
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        for(int m=params[0],i=0;;)
        {
          for(int t=0;++t<=m;arr[(i++)+offset]=convertToString(m))
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
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int i=0;
        for(int middle=length/(params[0]+1);i<middle;arr[i+offset]=convertToString(i++)){}
        while(i<length)
        {
          arr[i+offset]=convertToString(length-(i++)-1);
        }
      }
    }
    ,
    Stagger
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[i+offset]=convertToString((i*m+i)%length);
        }
        while(++i!=length);
      }
    }
    ,
    Plateau
    {
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        if(params.length<1)
        {
          throw new IllegalArgumentException(this.toString()+" requires 1 parameter");
        }
        int m=params[0];
        int i=0;
        do
        {
          arr[offset+i]=convertToString(Math.min(i,m));
        }
        while(++i!=length);
      }
    }
    ,
    Shuffle
    {
      @Override
      public int numRequiredParams()
      {
        return 0;
      }
      @Override
      public boolean isRandomized()
      {
        return true;
      }
      @Override
      void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params)
      {
        int x=0,y=0;
        int bound=offset+length;
        do
        {
          arr[offset]=convertToString(rand.nextBoolean()?(x+2):(y+2));
        }
        while(++offset!=bound);
      }
    }
    ;
    public int numRequiredParams()
    {
      //most sub-types require 1, so make that the default
      return 1;
    }
    public boolean isRandomized()
    {
      //most sub-types are not randomized, so make that the default
      return false;
    }
    abstract void buildUnchecked(String[] arr,int offset,int length,Random rand,int...params);
    public void build(String[] arr,Random rand,int...params)
    {
      if(arr==null)
      {
        throw new NullPointerException("arr cannot be null");
      }
      int length;
      if((length=arr.length)!=0)
      {
        buildUnchecked(arr,0,length,rand,params);
      }
    }  
    public void build(String[] arr,int offset,int length,Random rand,int...params)
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
        buildUnchecked(arr,offset,length,rand,params);
      }
    }
    @Override
    public String toString()
    {
      return "StringArrayBuilder."+this.name();
    }
  }
  /*
  public static boolean[] getAscendingbooleanArray(Random rand,int length)
  {
    return getAscendingbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getAscendingbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    return arr;
  }
  public static byte[] getAscendingbyteArray(Random rand,int length)
  {
    return getAscendingbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getAscendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static char[] getAscendingcharArray(Random rand,int length)
  {
    return getAscendingcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getAscendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static short[] getAscendingshortArray(Random rand,int length)
  {
    return getAscendingshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getAscendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static int[] getAscendingintArray(Random rand,int length)
  {
    return getAscendingintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getAscendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static long[] getAscendinglongArray(Random rand,int length)
  {
    return getAscendinglongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getAscendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static float[] getAscendingfloatArray(Random rand,int length)
  {
    return getAscendingfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getAscendingfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static double[] getAscendingdoubleArray(Random rand,int length)
  {
    return getAscendingdoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getAscendingdoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static String[] getAscendingStringArray(Random rand,int length)
  {
    return getAscendingStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getAscendingStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static boolean[] getDescendingbooleanArray(Random rand,int length)
  {
    return getDescendingbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getDescendingbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    Boolean[] boxedCopy=new Boolean[length];
    ArrCopy.uncheckedCopy(arr,0,boxedCopy,0,length);
    Arrays.sort(boxedCopy,0,length);
    ArrCopy.uncheckedCopy(boxedCopy,0,arr,0,length);
    OmniArray.OfBoolean.reverseRange(arr,0,length-1);
    return arr;
  }
  public static byte[] getDescendingbyteArray(Random rand,int length)
  {
    return getDescendingbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getDescendingbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfByte.reverseRange(arr,0,length-1);
    return arr;
  }
  public static char[] getDescendingcharArray(Random rand,int length)
  {
    return getDescendingcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getDescendingcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfChar.reverseRange(arr,0,length-1);
    return arr;
  }
  public static short[] getDescendingshortArray(Random rand,int length)
  {
    return getDescendingshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getDescendingshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfShort.reverseRange(arr,0,length-1);
    return arr;
  }
  public static int[] getDescendingintArray(Random rand,int length)
  {
    return getDescendingintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getDescendingintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfInt.reverseRange(arr,0,length-1);
    return arr;
  }
  public static long[] getDescendinglongArray(Random rand,int length)
  {
    return getDescendinglongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getDescendinglongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfLong.reverseRange(arr,0,length-1);
    return arr;
  }
  public static float[] getDescendingfloatArray(Random rand,int length)
  {
    return getDescendingfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getDescendingfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfFloat.reverseRange(arr,0,length-1);
    return arr;
  }
  public static double[] getDescendingdoubleArray(Random rand,int length)
  {
    return getDescendingdoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getDescendingdoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfDouble.reverseRange(arr,0,length-1);
    return arr;
  }
  public static String[] getDescendingStringArray(Random rand,int length)
  {
    return getDescendingStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getDescendingStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static boolean[] getUnsortedbooleanArray(Random rand,int length)
  {
    return getUnsortedbooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static boolean[] getUnsortedbooleanArray(BooleanSupplier supplier,int length)
  {
    boolean[] arr=new boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(boolean)supplier.getAsBoolean();
    }
    return arr;
  }
  public static byte[] getUnsortedbyteArray(Random rand,int length)
  {
    return getUnsortedbyteArray(
      ()->(byte)
      rand.nextLong()
    ,length);
  }
  public static byte[] getUnsortedbyteArray(ByteSupplier supplier,int length)
  {
    byte[] arr=new byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(byte)supplier.getAsByte();
    }
    return arr;
  }
  public static char[] getUnsortedcharArray(Random rand,int length)
  {
    return getUnsortedcharArray(
      ()->(char)
      rand.nextLong()
    ,length);
  }
  public static char[] getUnsortedcharArray(CharSupplier supplier,int length)
  {
    char[] arr=new char[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(char)supplier.getAsChar();
    }
    return arr;
  }
  public static short[] getUnsortedshortArray(Random rand,int length)
  {
    return getUnsortedshortArray(
      ()->(short)
      rand.nextLong()
    ,length);
  }
  public static short[] getUnsortedshortArray(ShortSupplier supplier,int length)
  {
    short[] arr=new short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(short)supplier.getAsShort();
    }
    return arr;
  }
  public static int[] getUnsortedintArray(Random rand,int length)
  {
    return getUnsortedintArray(
      ()->(int)
      rand.nextLong()
    ,length);
  }
  public static int[] getUnsortedintArray(IntSupplier supplier,int length)
  {
    int[] arr=new int[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(int)supplier.getAsInt();
    }
    return arr;
  }
  public static long[] getUnsortedlongArray(Random rand,int length)
  {
    return getUnsortedlongArray(
      ()->(long)
      rand.nextLong()
    ,length);
  }
  public static long[] getUnsortedlongArray(LongSupplier supplier,int length)
  {
    long[] arr=new long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(long)supplier.getAsLong();
    }
    return arr;
  }
  public static float[] getUnsortedfloatArray(Random rand,int length)
  {
    return getUnsortedfloatArray(
      rand::nextFloat
    ,length);
  }
  public static float[] getUnsortedfloatArray(FloatSupplier supplier,int length)
  {
    float[] arr=new float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(float)supplier.getAsFloat();
    }
    return arr;
  }
  public static double[] getUnsorteddoubleArray(Random rand,int length)
  {
    return getUnsorteddoubleArray(
      rand::nextDouble
    ,length);
  }
  public static double[] getUnsorteddoubleArray(DoubleSupplier supplier,int length)
  {
    double[] arr=new double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(double)supplier.getAsDouble();
    }
    return arr;
  }
  public static String[] getUnsortedStringArray(Random rand,int length)
  {
    return getUnsortedStringArray(
      ()->Long.toString(rand.nextLong())
    ,length);
  }
  public static String[] getUnsortedStringArray(Supplier<? extends String> supplier,int length)
  {
    String[] arr=new String[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(String)supplier.get();
    }
    return arr;
  }
  public static Boolean[] getAscendingBooleanArray(Random rand,int length)
  {
    return getAscendingBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getAscendingBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Byte[] getAscendingByteArray(Random rand,int length)
  {
    return getAscendingByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getAscendingByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Character[] getAscendingCharacterArray(Random rand,int length)
  {
    return getAscendingCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getAscendingCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Short[] getAscendingShortArray(Random rand,int length)
  {
    return getAscendingShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getAscendingShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Integer[] getAscendingIntegerArray(Random rand,int length)
  {
    return getAscendingIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getAscendingIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Long[] getAscendingLongArray(Random rand,int length)
  {
    return getAscendingLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getAscendingLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Float[] getAscendingFloatArray(Random rand,int length)
  {
    return getAscendingFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getAscendingFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Double[] getAscendingDoubleArray(Random rand,int length)
  {
    return getAscendingDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getAscendingDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    return arr;
  }
  public static Boolean[] getDescendingBooleanArray(Random rand,int length)
  {
    return getDescendingBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getDescendingBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Byte[] getDescendingByteArray(Random rand,int length)
  {
    return getDescendingByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getDescendingByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Character[] getDescendingCharacterArray(Random rand,int length)
  {
    return getDescendingCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getDescendingCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Short[] getDescendingShortArray(Random rand,int length)
  {
    return getDescendingShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getDescendingShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Integer[] getDescendingIntegerArray(Random rand,int length)
  {
    return getDescendingIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getDescendingIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Long[] getDescendingLongArray(Random rand,int length)
  {
    return getDescendingLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getDescendingLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Float[] getDescendingFloatArray(Random rand,int length)
  {
    return getDescendingFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getDescendingFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Double[] getDescendingDoubleArray(Random rand,int length)
  {
    return getDescendingDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getDescendingDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    Arrays.sort(arr,0,length);
    OmniArray.OfRef.reverseRange(arr,0,length-1);
    return arr;
  }
  public static Boolean[] getUnsortedBooleanArray(Random rand,int length)
  {
    return getUnsortedBooleanArray(
      rand::nextBoolean
    ,length);
  }
  public static Boolean[] getUnsortedBooleanArray(BooleanSupplier supplier,int length)
  {
    Boolean[] arr=new Boolean[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Boolean)supplier.getAsBoolean();
    }
    return arr;
  }
  public static Byte[] getUnsortedByteArray(Random rand,int length)
  {
    return getUnsortedByteArray(
      ()->(Byte)
      (byte)
      rand.nextLong()
    ,length);
  }
  public static Byte[] getUnsortedByteArray(ByteSupplier supplier,int length)
  {
    Byte[] arr=new Byte[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Byte)supplier.getAsByte();
    }
    return arr;
  }
  public static Character[] getUnsortedCharacterArray(Random rand,int length)
  {
    return getUnsortedCharacterArray(
      ()->(Character)
      (char)
      rand.nextLong()
    ,length);
  }
  public static Character[] getUnsortedCharacterArray(CharSupplier supplier,int length)
  {
    Character[] arr=new Character[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Character)supplier.getAsChar();
    }
    return arr;
  }
  public static Short[] getUnsortedShortArray(Random rand,int length)
  {
    return getUnsortedShortArray(
      ()->(Short)
      (short)
      rand.nextLong()
    ,length);
  }
  public static Short[] getUnsortedShortArray(ShortSupplier supplier,int length)
  {
    Short[] arr=new Short[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Short)supplier.getAsShort();
    }
    return arr;
  }
  public static Integer[] getUnsortedIntegerArray(Random rand,int length)
  {
    return getUnsortedIntegerArray(
      ()->(Integer)
      (int)
      rand.nextLong()
    ,length);
  }
  public static Integer[] getUnsortedIntegerArray(IntSupplier supplier,int length)
  {
    Integer[] arr=new Integer[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Integer)supplier.getAsInt();
    }
    return arr;
  }
  public static Long[] getUnsortedLongArray(Random rand,int length)
  {
    return getUnsortedLongArray(
      ()->(Long)
      rand.nextLong()
    ,length);
  }
  public static Long[] getUnsortedLongArray(LongSupplier supplier,int length)
  {
    Long[] arr=new Long[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Long)supplier.getAsLong();
    }
    return arr;
  }
  public static Float[] getUnsortedFloatArray(Random rand,int length)
  {
    return getUnsortedFloatArray(
      rand::nextFloat
    ,length);
  }
  public static Float[] getUnsortedFloatArray(FloatSupplier supplier,int length)
  {
    Float[] arr=new Float[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Float)supplier.getAsFloat();
    }
    return arr;
  }
  public static Double[] getUnsortedDoubleArray(Random rand,int length)
  {
    return getUnsortedDoubleArray(
      rand::nextDouble
    ,length);
  }
  public static Double[] getUnsortedDoubleArray(DoubleSupplier supplier,int length)
  {
    Double[] arr=new Double[length];
    for(int i=0;i<length;++i)
    {
      arr[i]=(Double)supplier.getAsDouble();
    }
    return arr;
  }
  */
}
