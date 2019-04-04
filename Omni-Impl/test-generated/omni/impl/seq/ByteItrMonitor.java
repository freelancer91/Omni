package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.ByteInputTestArgType;
import omni.impl.FunctionCallType;
import omni.function.ByteConsumer;
abstract class ByteItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfByte itr;
  int expectedCursor;
  int expectedLastRet;
  ByteItrMonitor(OmniIterator.OfByte itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfByte)itr).previousByte();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,ByteInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfByte)itr,v);
  }
  public void set(int v)
  {
    set(v,ByteInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,ByteInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,ByteInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfByte)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfByte)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfByte)itr).previousIndex();
  }
  //TODO forEachRemaining
}
