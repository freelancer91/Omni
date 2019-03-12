package omni.util;
import java.util.Random;
import java.util.function.Predicate;
public enum IntegerPredicates
{
  MarkAll
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      final Integer mConverted=TypeConversionUtil.convertToInteger(m);
      return val->{
       return val.compareTo(mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      final Integer mConverted=TypeConversionUtil.convertToInteger(m);
      return val->{
       return val.compareTo(mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      final Integer mConverted=TypeConversionUtil.convertToInteger(m);
      return val->{
       return val.compareTo(mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      final Integer mConverted=TypeConversionUtil.convertToInteger(m);
      return val->{
       return val.compareTo(mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    Predicate
    <Integer>
    getPred(Random rand,int m)
    {
      final Integer mConverted=TypeConversionUtil.convertToInteger(m);
      return val->{
       return val.compareTo(mConverted)<=0;
      };
    }
  },
  ;
  abstract Predicate
  <Integer>
  getPred(Random rand,int m);
}
