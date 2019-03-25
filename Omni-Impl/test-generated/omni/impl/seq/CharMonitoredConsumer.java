package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
import omni.function.CharConsumer;
class CharMonitoredConsumer extends ArrayList<Object> implements CharConsumer,Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(char val){
    super.add(val);
  }
  @Override public void accept(Object val){
    accept((char)val);
  }
  static class Throwing extends CharMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(char val){
      super.accept((char)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends CharMonitoredConsumer{
    private static final long serialVersionUID=1L;
    CharArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(CharArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(char val){
      seq.modCount+=2;
      super.accept((char)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(CharArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(char val){
      super.accept((char)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends CharMonitoredConsumer{
    private static final long serialVersionUID=1L;
    CharArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(CharArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(char val){
      seq.modCount+=2;
      super.accept((char)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(CharArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(char val){
      super.accept((char)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
