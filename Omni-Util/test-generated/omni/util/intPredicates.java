package omni.util;
import java.util.Random;
import java.util.function.IntPredicate;
public enum intPredicates
{
  MarkAll
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      final int mConverted=TypeConversionUtil.convertToint(m);
      return val->{
       return Integer.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      final int mConverted=TypeConversionUtil.convertToint(m);
      return val->{
       return Integer.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      final int mConverted=TypeConversionUtil.convertToint(m);
      return val->{
       return Integer.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      final int mConverted=TypeConversionUtil.convertToint(m);
      return val->{
       return Integer.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    IntPredicate
    getPred(Random rand,int m)
    {
      final int mConverted=TypeConversionUtil.convertToint(m);
      return val->{
       return Integer.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract IntPredicate
  getPred(Random rand,int m);
}
