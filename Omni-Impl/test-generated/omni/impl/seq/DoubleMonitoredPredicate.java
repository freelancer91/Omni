package omni.impl.seq;
import java.util.function.Predicate;
import java.util.function.DoublePredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class DoubleMonitoredPredicate implements DoublePredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(double val);
  @Override public boolean test(double val){
    ++callCount;
    return testImpl((double)val);
  }
  public DoubleMonitoredPredicate negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((double)val);
  }
  static class RemoveAllPredicate extends DoubleMonitoredPredicate{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(double val){
      return true;
    }
    void verifyArray(double[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends DoubleMonitoredPredicate{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(double val){
      return false;
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends DoubleMonitoredPredicate{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(double val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends DoubleMonitoredPredicate{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(double val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1));
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends DoubleMonitoredPredicate{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(double val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(seqLength-1));
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends DoubleMonitoredPredicate{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(double val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(2));
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends DoubleMonitoredPredicate{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(double val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0));
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends DoubleMonitoredPredicate{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(double val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTodouble(seqLength-2));
    }
    void verifyArray(double[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      DoubleArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(double val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      DoubleArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      DoubleArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      DoubleArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      DoubleArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      DoubleArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      DoubleArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      DoubleArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(DoubleArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      DoubleArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(double val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTodouble(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      DoubleArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      DoubleArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      DoubleArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      DoubleArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      DoubleArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      DoubleArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      DoubleArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(DoubleArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(double val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(double[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTodouble(valOffset),arr[i++]);
        }
      }
    }
}
