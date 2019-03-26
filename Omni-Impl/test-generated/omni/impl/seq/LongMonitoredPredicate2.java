package omni.impl.seq;
import java.util.function.Predicate;
import java.util.function.LongPredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class LongMonitoredPredicate2 implements LongPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(long val);
  @Override public boolean test(long val){
    ++callCount;
    return testImpl((long)val);
  }
  public LongMonitoredPredicate2 negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((long)val);
  }
  static class RemoveAllPredicate extends LongMonitoredPredicate2{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(long val){
      return true;
    }
    void verifyArray(long[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends LongMonitoredPredicate2{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(long val){
      return false;
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends LongMonitoredPredicate2{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(long val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends LongMonitoredPredicate2{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(long val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1));
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends LongMonitoredPredicate2{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(long val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(seqLength-1));
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends LongMonitoredPredicate2{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(long val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(2));
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends LongMonitoredPredicate2{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(long val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0));
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends LongMonitoredPredicate2{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(long val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTolong(seqLength-2));
    }
    void verifyArray(long[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      LongArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(long val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      LongArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      LongArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      LongArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      LongArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      LongArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      LongArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      LongArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(LongArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      LongArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(long val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTolong(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      LongArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      LongArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      LongArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      LongArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      LongArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      LongArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      LongArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(LongArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(long val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(long[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTolong(valOffset),arr[i++]);
        }
      }
    }
}
