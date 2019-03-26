package omni.impl.seq;
import java.util.function.Predicate;
import java.util.function.IntPredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class IntMonitoredPredicate2 implements IntPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(int val);
  @Override public boolean test(int val){
    ++callCount;
    return testImpl((int)val);
  }
  public IntMonitoredPredicate2 negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((int)val);
  }
  static class RemoveAllPredicate extends IntMonitoredPredicate2{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(int val){
      return true;
    }
    void verifyArray(int[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends IntMonitoredPredicate2{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(int val){
      return false;
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends IntMonitoredPredicate2{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(int val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends IntMonitoredPredicate2{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(int val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1));
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends IntMonitoredPredicate2{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(int val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(seqLength-1));
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends IntMonitoredPredicate2{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(int val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(2));
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends IntMonitoredPredicate2{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(int val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0));
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends IntMonitoredPredicate2{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(int val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToint(seqLength-2));
    }
    void verifyArray(int[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      IntArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(int val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      IntArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      IntArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      IntArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      IntArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      IntArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      IntArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      IntArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(IntArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      IntArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(int val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToint(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      IntArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      IntArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      IntArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      IntArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      IntArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      IntArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      IntArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(IntArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(int val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(int[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToint(valOffset),arr[i++]);
        }
      }
    }
}
