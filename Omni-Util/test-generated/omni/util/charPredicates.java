package omni.util;
import java.util.Random;
import omni.function.CharPredicate;
public enum charPredicates
{
  MarkAll
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      return val->true;
    }
  },
  MarkNone
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      return val->false;
    }
  },
  MarkGreaterThan
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      final char mConverted=TypeConversionUtil.convertTochar(m);
      return val->{
       return Character.compare(val,mConverted)>0;
      };
    }
  },
  MarkGreaterThanOrEqualTo
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      final char mConverted=TypeConversionUtil.convertTochar(m);
      return val->{
       return Character.compare(val,mConverted)>=0;
      };
    }
  },
  MarkEqualTo
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      final char mConverted=TypeConversionUtil.convertTochar(m);
      return val->{
       return Character.compare(val,mConverted)==0;
      };
    }
  },
  MarkLessThan
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      final char mConverted=TypeConversionUtil.convertTochar(m);
      return val->{
       return Character.compare(val,mConverted)<0;
      };
    }
  },
  MarkLessThanOrEqualTo
  {
    CharPredicate
    getPred(Random rand,int m)
    {
      final char mConverted=TypeConversionUtil.convertTochar(m);
      return val->{
       return Character.compare(val,mConverted)<=0;
      };
    }
  },
  ;
  abstract CharPredicate
  getPred(Random rand,int m);
}
