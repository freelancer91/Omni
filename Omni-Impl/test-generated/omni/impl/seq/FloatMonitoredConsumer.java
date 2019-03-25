package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import omni.function.FloatConsumer;
class FloatMonitoredConsumer extends ArrayList<Object> implements FloatConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(float val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((float)val);
  }
  static class Throwing extends FloatMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(float val){
      super.accept((float)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends FloatMonitoredConsumer{
    private static final long serialVersionUID=1L;
    FloatArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(FloatArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(float val){
      seq.modCount+=2;
      super.accept((float)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(FloatArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(float val){
      super.accept((float)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends FloatMonitoredConsumer{
    private static final long serialVersionUID=1L;
    FloatArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(FloatArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(float val){
      seq.modCount+=2;
      super.accept((float)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(FloatArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(float val){
      super.accept((float)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
