package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
class IntMonitoredConsumer extends ArrayList<Object> implements IntConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(int val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((int)val);
  }
  static class Throwing extends IntMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(int val){
      super.accept((int)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends IntMonitoredConsumer{
    private static final long serialVersionUID=1L;
    IntArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(IntArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(int val){
      seq.modCount+=2;
      super.accept((int)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(IntArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(int val){
      super.accept((int)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends IntMonitoredConsumer{
    private static final long serialVersionUID=1L;
    IntArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(IntArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(int val){
      seq.modCount+=2;
      super.accept((int)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(IntArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(int val){
      super.accept((int)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
