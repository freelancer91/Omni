package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.BooleanInputTestArgType;
import omni.impl.FunctionCallType;
import omni.function.BooleanConsumer;
abstract class BooleanItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfBoolean itr;
  int expectedCursor;
  int expectedLastRet;
  BooleanItrMonitor(OmniIterator.OfBoolean itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfBoolean)itr).previousBoolean();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,BooleanInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfBoolean)itr,v);
  }
  public void set(int v)
  {
    set(v,BooleanInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,BooleanInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,BooleanInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfBoolean)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfBoolean)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfBoolean)itr).previousIndex();
  }
  //TODO forEachRemaining
}
