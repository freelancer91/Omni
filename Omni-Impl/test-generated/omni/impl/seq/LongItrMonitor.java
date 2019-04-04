package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.LongInputTestArgType;
import omni.impl.FunctionCallType;
import java.util.function.LongConsumer;
abstract class LongItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfLong itr;
  int expectedCursor;
  int expectedLastRet;
  LongItrMonitor(OmniIterator.OfLong itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfLong)itr).previousLong();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,LongInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfLong)itr,v);
  }
  public void set(int v)
  {
    set(v,LongInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,LongInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,LongInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfLong)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfLong)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfLong)itr).previousIndex();
  }
  //TODO forEachRemaining
}
