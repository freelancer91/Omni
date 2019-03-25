package omni.impl.seq;
import java.util.ArrayList;
import java.util.function.Consumer;
@SuppressWarnings({"rawtypes"}) 
class RefMonitoredConsumer extends ArrayList<Object> implements Consumer<Object>
{
  private static final long serialVersionUID=1L;
  @Override public void accept(Object val){
    super.add(val);
  }
  static class Throwing extends RefMonitoredConsumer
  {
    private static final long serialVersionUID=1L;
    @Override public void accept(Object val){
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedStackConsumer extends RefMonitoredConsumer{
    private static final long serialVersionUID=1L;
    RefArrSeq.CheckedStack seq;
    public ModifyingArrSeqCheckedStackConsumer(RefArrSeq.CheckedStack seq){
      this.seq=seq;
    }
    @Override public void accept(Object val){
      seq.modCount+=2;
      super.accept((Object)val);
    }
  }
  static class ModifyingArrSeqCheckedStackAndThrowingConsumer extends ModifyingArrSeqCheckedStackConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedStackAndThrowingConsumer(RefArrSeq.CheckedStack seq){
      super(seq);
    }
    @Override public void accept(Object val){
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModifyingArrSeqCheckedListConsumer extends RefMonitoredConsumer{
    private static final long serialVersionUID=1L;
    RefArrSeq.CheckedList seq;
    public ModifyingArrSeqCheckedListConsumer(RefArrSeq.CheckedList seq){
      this.seq=seq;
    }
    @Override public void accept(Object val){
      seq.modCount+=2;
      super.accept((Object)val);
    }
  }
  static class ModifyingArrSeqCheckedListAndThrowingConsumer extends ModifyingArrSeqCheckedListConsumer{
    private static final long serialVersionUID=1L;
    public ModifyingArrSeqCheckedListAndThrowingConsumer(RefArrSeq.CheckedList seq){
      super(seq);
    }
    @Override public void accept(Object val){
      super.accept((Object)val);
      throw new IndexOutOfBoundsException();
    }
  }
}
