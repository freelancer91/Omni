package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
@SuppressWarnings({"rawtypes","unchecked"}) 
abstract class RefMonitoredPredicate2 implements Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(Object val);
  @Override public boolean test(Object val){
    ++callCount;
    return testImpl((Object)val);
  }
  public RefMonitoredPredicate2 negate(){
    //don't care
    return null;
  }
  static class RemoveAllPredicate extends RefMonitoredPredicate2{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(Object val){
      return true;
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      for(int bound=offset+originalLength;offset<bound;++offset){
        Assertions.assertNull(arr[offset]);
      }
    }
  }
  static class RemoveNonePredicate extends RefMonitoredPredicate2{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(Object val){
      return false;
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends RefMonitoredPredicate2{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(Object val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends RefMonitoredPredicate2{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(Object val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(1));
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
      for(int bound=offset+originalLength;i<bound;++i){
        Assertions.assertNull(arr[i]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends RefMonitoredPredicate2{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(Object val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(seqLength-1));
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
      for(int bound=offset+originalLength;i<bound;++i){
        Assertions.assertNull(arr[i]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends RefMonitoredPredicate2{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(Object val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(2));
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
      for(int bound=offset+originalLength;i<bound;++i){
        Assertions.assertNull(arr[i]);
      }
    }
  }
  static class RemoveFirstPredicate  extends RefMonitoredPredicate2{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(Object val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0));
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
      for(int bound=offset+originalLength;i<bound;++i){
        Assertions.assertNull(arr[i]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends RefMonitoredPredicate2{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(Object val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertToObject(seqLength-2));
    }
    void verifyArray(Object[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
      }
      for(int bound=offset+originalLength;i<bound;++i){
        Assertions.assertNull(arr[i]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      RefArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(Object val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      RefArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      RefArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      RefArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      RefArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      RefArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      RefArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      RefArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(RefArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      RefArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(Object val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertToObject(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      RefArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      RefArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      RefArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      RefArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      RefArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      RefArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      RefArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(RefArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(Object val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(Object[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertToObject(valOffset),arr[i++]);
        }
      }
    }
}
