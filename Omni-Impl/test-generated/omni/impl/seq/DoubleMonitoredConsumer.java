package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
class DoubleMonitoredConsumer extends ArrayList<Object> implements DoubleConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(double val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((double)val);
  }
  static class Throwing extends DoubleMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(double val){
      super.accept((double)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends DoubleMonitoredConsumer{
    private static final long serialVersionUID=1L;
    DoubleArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(DoubleArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(double val){
      seq.modCount+=2;
      super.accept((double)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(DoubleArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(double val){
      super.accept((double)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends DoubleMonitoredConsumer{
    private static final long serialVersionUID=1L;
    DoubleArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(DoubleArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(double val){
      seq.modCount+=2;
      super.accept((double)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(DoubleArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(double val){
      super.accept((double)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
