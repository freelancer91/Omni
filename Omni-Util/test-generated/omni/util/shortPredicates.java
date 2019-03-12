package omni.util;
import java.util.Random;
import omni.function.ShortPredicate;
public enum shortPredicates
{
  MarkAll
  {
    ShortPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    ShortPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    ShortPredicate
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
    ShortPredicate
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
    ShortPredicate
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
    ShortPredicate
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
    ShortPredicate
    getPred(Random rand,int m)
    {
      final short mConverted=TypeConversionUtil.convertToshort(m);
      return val->{
       return Short.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract ShortPredicate
  getPred(Random rand,int m);
}
