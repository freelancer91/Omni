package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.ShortInputTestArgType;
import omni.impl.FunctionCallType;
import omni.function.ShortConsumer;
abstract class ShortItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfShort itr;
  int expectedCursor;
  int expectedLastRet;
  ShortItrMonitor(OmniIterator.OfShort itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfShort)itr).previousShort();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,ShortInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfShort)itr,v);
  }
  public void set(int v)
  {
    set(v,ShortInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,ShortInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,ShortInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfShort)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfShort)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfShort)itr).previousIndex();
  }
  //TODO forEachRemaining
}
