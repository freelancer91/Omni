package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.FloatInputTestArgType;
import omni.impl.FunctionCallType;
import omni.function.FloatConsumer;
abstract class FloatItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfFloat itr;
  int expectedCursor;
  int expectedLastRet;
  FloatItrMonitor(OmniIterator.OfFloat itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfFloat)itr).previousFloat();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,FloatInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfFloat)itr,v);
  }
  public void set(int v)
  {
    set(v,FloatInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,FloatInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,FloatInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfFloat)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfFloat)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfFloat)itr).previousIndex();
  }
  //TODO forEachRemaining
}
