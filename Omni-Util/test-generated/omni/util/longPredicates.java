package omni.util;
import java.util.Random;
import java.util.function.LongPredicate;
public enum longPredicates
{
  MarkAll
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      final long mConverted=TypeConversionUtil.convertTolong(m);
      return val->{
       return Long.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      final long mConverted=TypeConversionUtil.convertTolong(m);
      return val->{
       return Long.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      final long mConverted=TypeConversionUtil.convertTolong(m);
      return val->{
       return Long.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      final long mConverted=TypeConversionUtil.convertTolong(m);
      return val->{
       return Long.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    LongPredicate
    getPred(Random rand,int m)
    {
      final long mConverted=TypeConversionUtil.convertTolong(m);
      return val->{
       return Long.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract LongPredicate
  getPred(Random rand,int m);
}
