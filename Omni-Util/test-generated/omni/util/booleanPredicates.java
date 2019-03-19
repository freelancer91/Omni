package omni.util;
import java.util.Random;
import omni.function.BooleanPredicate;
public enum booleanPredicates
{
  MarkAll
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public BooleanPredicate
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
    @Override public BooleanPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkTrue
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public BooleanPredicate getPred(Random rand,int m)
    {
      return val->val;
    }
  },
  MarkFalse
  {
    @Override
    public boolean isValueConsistent()
    {
      return true;
    }
    @Override public BooleanPredicate getPred(Random rand,int m)
    {
      return val->!val;
    }
  }
  ;
  public abstract BooleanPredicate
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
