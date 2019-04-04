package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.DoubleInputTestArgType;
import omni.impl.FunctionCallType;
import java.util.function.DoubleConsumer;
abstract class DoubleItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfDouble itr;
  int expectedCursor;
  int expectedLastRet;
  DoubleItrMonitor(OmniIterator.OfDouble itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfDouble)itr).previousDouble();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,DoubleInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfDouble)itr,v);
  }
  public void set(int v)
  {
    set(v,DoubleInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,DoubleInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,DoubleInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfDouble)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfDouble)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfDouble)itr).previousIndex();
  }
  //TODO forEachRemaining
}
