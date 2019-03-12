package omni.util;
import java.util.Random;
import omni.function.FloatPredicate;
public enum floatPredicates
{
  MarkAll
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      final float mConverted=TypeConversionUtil.convertTofloat(m);
      return val->{
       return Float.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      final float mConverted=TypeConversionUtil.convertTofloat(m);
      return val->{
       return Float.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      final float mConverted=TypeConversionUtil.convertTofloat(m);
      return val->{
       return Float.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      final float mConverted=TypeConversionUtil.convertTofloat(m);
      return val->{
       return Float.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    FloatPredicate
    getPred(Random rand,int m)
    {
      final float mConverted=TypeConversionUtil.convertTofloat(m);
      return val->{
       return Float.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract FloatPredicate
  getPred(Random rand,int m);
}
