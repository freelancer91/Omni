package omni.impl.seq;
import java.util.function.Predicate;
import omni.function.FloatPredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class FloatMonitoredPredicate implements FloatPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(float val);
  @Override public boolean test(float val){
    ++callCount;
    return testImpl((float)val);
  }
  public FloatMonitoredPredicate negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((float)val);
  }
  static class RemoveAllPredicate extends FloatMonitoredPredicate{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(float val){
      return true;
    }
    void verifyArray(float[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends FloatMonitoredPredicate{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(float val){
      return false;
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends FloatMonitoredPredicate{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(float val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends FloatMonitoredPredicate{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(float val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1));
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends FloatMonitoredPredicate{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(float val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(seqLength-1));
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends FloatMonitoredPredicate{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(float val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(2));
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends FloatMonitoredPredicate{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(float val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0));
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends FloatMonitoredPredicate{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(float val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTofloat(seqLength-2));
    }
    void verifyArray(float[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      FloatArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(float val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      FloatArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      FloatArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      FloatArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      FloatArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      FloatArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      FloatArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      FloatArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(FloatArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      FloatArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(float val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      FloatArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      FloatArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      FloatArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      FloatArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      FloatArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      FloatArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      FloatArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(FloatArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(float val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(float[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTofloat(valOffset),arr[i++]);
        }
      }
    }
}
