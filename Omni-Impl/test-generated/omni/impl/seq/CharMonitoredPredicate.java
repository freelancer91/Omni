package omni.impl.seq;
import java.util.function.Predicate;
import omni.function.CharPredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class CharMonitoredPredicate implements CharPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(char val);
  @Override public boolean test(char val){
    ++callCount;
    return testImpl((char)val);
  }
  public CharMonitoredPredicate negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((char)val);
  }
  static class RemoveAllPredicate extends CharMonitoredPredicate{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(char val){
      return true;
    }
    void verifyArray(char[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends CharMonitoredPredicate{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(char val){
      return false;
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends CharMonitoredPredicate{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(char val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends CharMonitoredPredicate{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(char val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1));
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends CharMonitoredPredicate{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(char val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(seqLength-1));
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends CharMonitoredPredicate{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(char val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(2));
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends CharMonitoredPredicate{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(char val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0));
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends CharMonitoredPredicate{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(char val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTochar(seqLength-2));
    }
    void verifyArray(char[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      CharArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(char val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      CharArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      CharArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      CharArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      CharArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      CharArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      CharArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      CharArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(CharArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      CharArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(char val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTochar(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      CharArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      CharArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      CharArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      CharArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      CharArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      CharArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      CharArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(CharArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(char val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(char[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTochar(valOffset),arr[i++]);
        }
      }
    }
}
