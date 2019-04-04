package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.IntInputTestArgType;
import omni.impl.FunctionCallType;
import java.util.function.IntConsumer;
abstract class IntItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfInt itr;
  int expectedCursor;
  int expectedLastRet;
  IntItrMonitor(OmniIterator.OfInt itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfInt)itr).previousInt();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,IntInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfInt)itr,v);
  }
  public void set(int v)
  {
    set(v,IntInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,IntInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,IntInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfInt)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfInt)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfInt)itr).previousIndex();
  }
  //TODO forEachRemaining
}
