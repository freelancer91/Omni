package omni.util;
import java.util.Random;
import omni.function.BooleanPredicate;
public enum booleanPredicates
{
  MarkAll
  {
    BooleanPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    BooleanPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkTrue
  {
    BooleanPredicate getPred(Random rand,int m)
    {
      return val->val;
    }
  },
  MarkFalse
  {
    BooleanPredicate getPred(Random rand,int m)
    {
      return val->!val;
    }
  }
  ;
  abstract BooleanPredicate
  getPred(Random rand,int m);
}
