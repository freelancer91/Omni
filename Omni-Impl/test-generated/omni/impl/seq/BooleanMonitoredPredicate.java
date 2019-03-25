package omni.impl.seq;
import java.util.function.Predicate;
import omni.function.BooleanPredicate;
abstract class BooleanMonitoredPredicate implements BooleanPredicate,Predicate<Object>
{
  int callCount;
  abstract boolean testImpl(boolean val);
  @Override public boolean test(boolean val){
    ++callCount;
    return testImpl((boolean)val);
  }
  public BooleanMonitoredPredicate negate(){
    //don't care
    return null;
  }
  @Override public boolean test(Object val){
    return test((boolean)val);
  }
  static class RemoveAllPredicate extends BooleanMonitoredPredicate{
    RemoveAllPredicate(){}
    RemoveAllPredicate(Object dummy){}
    boolean testImpl(boolean val){
      return true;
    }
  }
  static class RemoveNonePredicate extends BooleanMonitoredPredicate{
    RemoveNonePredicate(){}
    RemoveNonePredicate(Object dummy){}
    boolean testImpl(boolean val){
      return false;
    }
  }
  static class ThrowingPredicate extends BooleanMonitoredPredicate{
    ThrowingPredicate(){}
    ThrowingPredicate(Object dummy){}
    @Override boolean testImpl(boolean val){
      throw new IndexOutOfBoundsException();
    }
  }
  static class RemoveTruePredicate extends BooleanMonitoredPredicate{
    RemoveTruePredicate(){}
    RemoveTruePredicate(Object dummy){}
    boolean testImpl(boolean val){
      return val;
    }
  }
  static class RemoveFalsePredicate extends BooleanMonitoredPredicate{
    RemoveFalsePredicate(){}
    RemoveFalsePredicate(Object dummy){}
    boolean testImpl(boolean val){
      return !val;
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingPredicate extends ThrowingPredicate{
      BooleanArrSeq.CheckedStack seq;
      public ModifyingArrSeqCheckedStackAndThrowingPredicate(BooleanArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override boolean testImpl(boolean val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
  }
  static class RemoveAllArrSeqCheckedStackModifyingPredicate extends RemoveAllPredicate{
      BooleanArrSeq.CheckedStack seq;
      RemoveAllArrSeqCheckedStackModifyingPredicate(BooleanArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
  }
  static class RemoveNoneArrSeqCheckedStackModifyingPredicate extends RemoveNonePredicate{
      BooleanArrSeq.CheckedStack seq;
      RemoveNoneArrSeqCheckedStackModifyingPredicate(BooleanArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
  }
    static class RemoveTrueArrSeqCheckedStackModifyingPredicate extends RemoveTruePredicate{
      BooleanArrSeq.CheckedStack seq;
      RemoveTrueArrSeqCheckedStackModifyingPredicate(BooleanArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFalseArrSeqCheckedStackModifyingPredicate extends RemoveFalsePredicate{
      BooleanArrSeq.CheckedStack seq;
      RemoveFalseArrSeqCheckedStackModifyingPredicate(BooleanArrSeq.CheckedStack seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
    }
  static class ModifyingArrSeqCheckedListAndThrowingPredicate extends ThrowingPredicate{
      BooleanArrSeq.CheckedList seq;
      public ModifyingArrSeqCheckedListAndThrowingPredicate(BooleanArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override boolean testImpl(boolean val){
        seq.add(val);
        throw new IndexOutOfBoundsException();
      }
  }
  static class RemoveAllArrSeqCheckedListModifyingPredicate extends RemoveAllPredicate{
      BooleanArrSeq.CheckedList seq;
      RemoveAllArrSeqCheckedListModifyingPredicate(BooleanArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
  }
  static class RemoveNoneArrSeqCheckedListModifyingPredicate extends RemoveNonePredicate{
      BooleanArrSeq.CheckedList seq;
      RemoveNoneArrSeqCheckedListModifyingPredicate(BooleanArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
  }
    static class RemoveTrueArrSeqCheckedListModifyingPredicate extends RemoveTruePredicate{
      BooleanArrSeq.CheckedList seq;
      RemoveTrueArrSeqCheckedListModifyingPredicate(BooleanArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
    }
    static class RemoveFalseArrSeqCheckedListModifyingPredicate extends RemoveFalsePredicate{
      BooleanArrSeq.CheckedList seq;
      RemoveFalseArrSeqCheckedListModifyingPredicate(BooleanArrSeq.CheckedList seq){
        this.seq=seq;
      }
      @Override
      public boolean test(boolean val){
        seq.modCount+=2;
        return super.test(val);
      }
    }
}
