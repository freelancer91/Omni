package omni.util;
import java.util.Random;
import java.util.function.Predicate;
public enum StringPredicates
{
  MarkAll
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      final String mConverted=TypeConversionUtil.convertToString(m);
      return val->{
       return val.compareTo(mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      final String mConverted=TypeConversionUtil.convertToString(m);
      return val->{
       return val.compareTo(mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      final String mConverted=TypeConversionUtil.convertToString(m);
      return val->{
       return val.compareTo(mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      final String mConverted=TypeConversionUtil.convertToString(m);
      return val->{
       return val.compareTo(mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    Predicate
    <String>
    getPred(Random rand,int m)
    {
      final String mConverted=TypeConversionUtil.convertToString(m);
      return val->{
       return val.compareTo(mConverted)<=0;
      };
    }
  },
  ;
  abstract Predicate
  <String>
  getPred(Random rand,int m);
}
