package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.CharInputTestArgType;
import omni.impl.FunctionCallType;
import omni.function.CharConsumer;
abstract class CharItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfChar itr;
  int expectedCursor;
  int expectedLastRet;
  CharItrMonitor(OmniIterator.OfChar itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfChar)itr).previousChar();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,CharInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfChar)itr,v);
  }
  public void set(int v)
  {
    set(v,CharInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,CharInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,CharInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfChar)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfChar)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfChar)itr).previousIndex();
  }
  //TODO forEachRemaining
}
