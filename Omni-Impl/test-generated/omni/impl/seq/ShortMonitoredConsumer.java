package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import omni.function.ShortConsumer;
class ShortMonitoredConsumer extends ArrayList<Object> implements ShortConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(short val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((short)val);
  }
  static class Throwing extends ShortMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(short val){
      super.accept((short)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends ShortMonitoredConsumer{
    private static final long serialVersionUID=1L;
    ShortArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(ShortArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(short val){
      seq.modCount+=2;
      super.accept((short)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(ShortArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(short val){
      super.accept((short)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends ShortMonitoredConsumer{
    private static final long serialVersionUID=1L;
    ShortArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(ShortArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(short val){
      seq.modCount+=2;
      super.accept((short)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(ShortArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(short val){
      super.accept((short)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
