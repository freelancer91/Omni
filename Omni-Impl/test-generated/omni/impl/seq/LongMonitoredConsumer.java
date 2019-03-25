package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
class LongMonitoredConsumer extends ArrayList<Object> implements LongConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(long val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((long)val);
  }
  static class Throwing extends LongMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(long val){
      super.accept((long)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends LongMonitoredConsumer{
    private static final long serialVersionUID=1L;
    LongArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(LongArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(long val){
      seq.modCount+=2;
      super.accept((long)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(LongArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(long val){
      super.accept((long)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends LongMonitoredConsumer{
    private static final long serialVersionUID=1L;
    LongArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(LongArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(long val){
      seq.modCount+=2;
      super.accept((long)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(LongArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(long val){
      super.accept((long)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
