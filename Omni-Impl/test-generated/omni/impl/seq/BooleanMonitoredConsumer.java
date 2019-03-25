package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import omni.function.BooleanConsumer;
class BooleanMonitoredConsumer extends ArrayList<Object> implements BooleanConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(boolean val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((boolean)val);
  }
  static class Throwing extends BooleanMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(boolean val){
      super.accept((boolean)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends BooleanMonitoredConsumer{
    private static final long serialVersionUID=1L;
    BooleanArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(BooleanArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(boolean val){
      seq.modCount+=2;
      super.accept((boolean)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(BooleanArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(boolean val){
      super.accept((boolean)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends BooleanMonitoredConsumer{
    private static final long serialVersionUID=1L;
    BooleanArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(BooleanArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(boolean val){
      seq.modCount+=2;
      super.accept((boolean)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(BooleanArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(boolean val){
      super.accept((boolean)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
