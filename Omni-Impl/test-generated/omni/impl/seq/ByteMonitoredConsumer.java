package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import omni.function.ByteConsumer;
class ByteMonitoredConsumer extends ArrayList<Object> implements ByteConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(byte val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((byte)val);
  }
  static class Throwing extends ByteMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(byte val){
      super.accept((byte)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends ByteMonitoredConsumer{
    private static final long serialVersionUID=1L;
    ByteArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(ByteArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(byte val){
      seq.modCount+=2;
      super.accept((byte)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(ByteArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(byte val){
      super.accept((byte)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends ByteMonitoredConsumer{
    private static final long serialVersionUID=1L;
    ByteArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(ByteArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(byte val){
      seq.modCount+=2;
      super.accept((byte)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(ByteArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(byte val){
      super.accept((byte)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
