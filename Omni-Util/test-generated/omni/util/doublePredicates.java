package omni.util;
import java.util.Random;
import java.util.function.DoublePredicate;
public enum doublePredicates
{
  MarkAll
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      final double mConverted=TypeConversionUtil.convertTodouble(m);
      return val->{
       return Double.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      final double mConverted=TypeConversionUtil.convertTodouble(m);
      return val->{
       return Double.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      final double mConverted=TypeConversionUtil.convertTodouble(m);
      return val->{
       return Double.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      final double mConverted=TypeConversionUtil.convertTodouble(m);
      return val->{
       return Double.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    DoublePredicate
    getPred(Random rand,int m)
    {
      final double mConverted=TypeConversionUtil.convertTodouble(m);
      return val->{
       return Double.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract DoublePredicate
  getPred(Random rand,int m);
}
