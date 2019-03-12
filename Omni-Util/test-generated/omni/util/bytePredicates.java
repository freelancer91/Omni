package omni.util;
import java.util.Random;
import omni.function.BytePredicate;
public enum bytePredicates
{
  MarkAll
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      final byte mConverted=TypeConversionUtil.convertTobyte(m);
      return val->{
       return Byte.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      final byte mConverted=TypeConversionUtil.convertTobyte(m);
      return val->{
       return Byte.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      final byte mConverted=TypeConversionUtil.convertTobyte(m);
      return val->{
       return Byte.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      final byte mConverted=TypeConversionUtil.convertTobyte(m);
      return val->{
       return Byte.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    BytePredicate
    getPred(Random rand,int m)
    {
      final byte mConverted=TypeConversionUtil.convertTobyte(m);
      return val->{
       return Byte.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract BytePredicate
  getPred(Random rand,int m);
}
