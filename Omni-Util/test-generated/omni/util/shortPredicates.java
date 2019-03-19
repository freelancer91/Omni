package omni.util;
import java.util.Random;
import omni.function.ShortPredicate;
public enum shortPredicates
{
  MarkAll
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)<=0;
      };
    }
  }
  ,AlternatingTrueFirst
  {
     @Override public int getMHi()
     {
       return 999;
     }
     @Override public ShortPredicate
     getPred(Random rand,int m)
     {
       return new ShortPredicate
       ()
       {
         private int counter;
         private boolean currState=true;
         @Override
         public boolean test(short val)
         {
           boolean ret=currState;
           if(((++counter)%m)==0)
           {
             currState=!currState;
           }
           return ret;
         }
       };
     }
  }
  ,AlternatingFalseFirst
  {
     @Override public int getMHi()
     {
       return 999;
     }
     @Override public ShortPredicate
     getPred(Random rand,int m)
     {
       return new ShortPredicate
       ()
       {
         private int counter;
         private boolean currState=false;
         @Override
         public boolean test(short val)
         {
           boolean ret=currState;
           if(((++counter)%m)==0)
           {
             currState=!currState;
           }
           return ret;
         }
       };
     }
  }
  ,Randomized
  {
     @Override public int getNumReps()
     {
       return 100;
     }
     @Override public ShortPredicate
     getPred(Random rand,int m)
     {
       return (val)->rand.nextBoolean();
     }
  }
  ,StickyRandomizedFalseFirst
  {
     @Override public int getMHi()
     {
       return 999;
     }
     @Override public int incrementM(int m)
     {
       return m+10;
     }
     @Override public int getNumReps()
     {
       return 10;
     }
     @Override public ShortPredicate
     getPred(Random rand,int m)
     {
       //assert m>=0;
       //assert m<1000;
       return new ShortPredicate
       ()
       {
         private boolean currState=false;
         @Override
         public boolean test(short val)
         {
           boolean ret=currState;
           int randInt=rand.nextInt(1000);
           if(randInt>=m)
           {
             currState=!currState;
           }
           return ret;
         }
       };
     }
  }
  ,StickyRandomizedTrueFirst
  {
     @Override public int getMHi()
     {
       return 999;
     }
     @Override public int incrementM(int m)
     {
       return m+10;
     }
     @Override public int getNumReps()
     {
       return 10;
     }
     @Override public ShortPredicate
     getPred(Random rand,int m)
     {
       //assert m>=0;
       //assert m<1000;
       return new ShortPredicate
       ()
       {
         private boolean currState=true;
         @Override
         public boolean test(short val)
         {
           boolean ret=currState;
           int randInt=rand.nextInt(1000);
           if(randInt>=m)
           {
             currState=!currState;
           }
           return ret;
         }
       };
     }
  }
  ;
  public abstract ShortPredicate
  getPred(Random rand,int m);
  public int incrementM(int m)
  {
    return m+1;
  }
  public boolean isValueConsistent()
  {
    return false;
  }
  public int getMLo()
  {
    return 1;
  }
  public int getMHi()
  {
    return 1;
  }
  public int getNumReps()
  {
    return 1;
  }
}
