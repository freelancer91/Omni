package omni.impl.seq;
import java.util.function.Predicate;
import omni.function.ShortPredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class ShortMonitoredPredicate implements ShortPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(short val);
  @Override public boolean test(short val){
    ++callCount;
    return testImpl((short)val);
  }
  public ShortMonitoredPredicate negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((short)val);
  }
  static class RemoveAllPredicate extends ShortMonitoredPredicate{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(short val){
      return true;
    }
    void verifyArray(short[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends ShortMonitoredPredicate{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(short val){
      return false;
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends ShortMonitoredPredicate{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(short val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends ShortMonitoredPredicate{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(short val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(1));
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends ShortMonitoredPredicate{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(short val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(seqLength-1));
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends ShortMonitoredPredicate{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(short val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(2));
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends ShortMonitoredPredicate{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(short val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(0));
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends ShortMonitoredPredicate{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(short val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToshort(seqLength-2));
    }
    void verifyArray(short[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      ShortArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(short val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      ShortArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      ShortArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      ShortArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      ShortArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      ShortArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      ShortArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      ShortArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(ShortArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      ShortArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(short val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToshort(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      ShortArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      ShortArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      ShortArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      ShortArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      ShortArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      ShortArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      ShortArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(ShortArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(short val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(short[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToshort(valOffset),arr[i++]);
        }
      }
    }
}
