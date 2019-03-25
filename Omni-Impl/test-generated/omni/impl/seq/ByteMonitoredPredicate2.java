package omni.impl.seq;
import java.util.function.Predicate;
import omni.function.BytePredicate;
import org.junit.jupiter.api.Assertions;
import omni.util.TypeConversionUtil;
import omni.util.EqualityUtil;
abstract class ByteMonitoredPredicate2 implements BytePredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(byte val);
  @Override public boolean test(byte val){
    ++callCount;
    return testImpl((byte)val);
  }
  public ByteMonitoredPredicate2 negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((byte)val);
  }
  static class RemoveAllPredicate extends ByteMonitoredPredicate2{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(byte val){
      return true;
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
    }
  }
  static class RemoveNonePredicate extends ByteMonitoredPredicate2{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(byte val){
      return false;
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class ThrowingPredicate extends ByteMonitoredPredicate2{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(byte val){
      throw new IndexOutOfBoundsException();
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondPredicate extends ByteMonitoredPredicate2{
    RetainSecondPredicate(){}
    RetainSecondPredicate(Object dummy){}
    boolean testImpl(byte val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1));
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+1,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class RetainSecondAndLastPredicate extends ByteMonitoredPredicate2{
    int seqLength;
    RetainSecondAndLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(byte val){
      return !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(1)) && !EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(seqLength-1));
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+2,valOffset=0;i<bound;++valOffset){
        if(valOffset!=1 && valOffset!=originalLength-1){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndThirdPredicate extends ByteMonitoredPredicate2{
    RemoveFirstAndThirdPredicate(){}
    RemoveFirstAndThirdPredicate(Object dummy){}
    boolean testImpl(byte val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(2));
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstPredicate  extends ByteMonitoredPredicate2{
    RemoveFirstPredicate(){}
    RemoveFirstPredicate(Object dummy){}
    boolean testImpl(byte val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0));
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-1,valOffset=0;i<bound;++valOffset){
        if(valOffset==0){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class RemoveFirstAndSecondToLastPredicate extends ByteMonitoredPredicate2{
    int seqLength;
    RemoveFirstAndSecondToLastPredicate(int seqLength){
      this.seqLength=seqLength;
    }
    boolean testImpl(byte val){
      return EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(0)) || EqualityUtil.isEqual(val,TypeConversionUtil.convertTobyte(seqLength-2));
    }
    void verifyArray(byte[] arr,int offset,int originalLength){
      int i=offset;
      for(int bound=offset+originalLength-2,valOffset=0;i<bound;++valOffset){
        if(valOffset==0 || valOffset==originalLength-2){
          continue;
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
      }
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      ByteArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(byte val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      ByteArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      ByteArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedStackModifyingPredicate extends RetainSecondPredicate{
      ByteArrSeq.CheckedStack seq;
      RetainSecondArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedStackModifyingPredicate extends RetainSecondAndLastPredicate{
      ByteArrSeq.CheckedStack seq;
      RetainSecondAndLastArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndThirdPredicate{
      ByteArrSeq.CheckedStack seq;
      RemoveFirstAndThirdArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedStackModifyingPredicate  extends RemoveFirstPredicate{
      ByteArrSeq.CheckedStack seq;
      RemoveFirstArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      ByteArrSeq.CheckedStack seq;
      RemoveFirstAndSecondToLastArrSeqCheckedStackModifyingPredicate(ByteArrSeq.CheckedStack seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      ByteArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(byte val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
        Assertions.assertEquals(TypeConversionUtil.convertTobyte(0),arr[i]);
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      ByteArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      ByteArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
  }
    static class RetainSecondArrSeqCheckedListModifyingPredicate extends RetainSecondPredicate{
      ByteArrSeq.CheckedList seq;
      RetainSecondArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RetainSecondAndLastArrSeqCheckedListModifyingPredicate extends RetainSecondAndLastPredicate{
      ByteArrSeq.CheckedList seq;
      RetainSecondAndLastArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset)
        {
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate extends RemoveFirstAndThirdPredicate{
      ByteArrSeq.CheckedList seq;
      RemoveFirstAndThirdArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstArrSeqCheckedListModifyingPredicate  extends RemoveFirstPredicate{
      ByteArrSeq.CheckedList seq;
      RemoveFirstArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
    static class RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate extends RemoveFirstAndSecondToLastPredicate{
      ByteArrSeq.CheckedList seq;
      RemoveFirstAndSecondToLastArrSeqCheckedListModifyingPredicate(ByteArrSeq.CheckedList seq){
        super(seq.size);
        this.seq=seq;
      }
      @Override
      public boolean test(byte val){
        seq.modCount+=2;
        return super.test(val);
      }
      void verifyArray(byte[] arr,int offset,int originalLength){
        int i=offset;
        for(int bound=offset+originalLength,valOffset=0;i<bound;++valOffset){
          Assertions.assertEquals(TypeConversionUtil.convertTobyte(valOffset),arr[i++]);
        }
      }
    }
}
