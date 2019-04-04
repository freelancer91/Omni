package omni.impl.seq;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import org.junit.jupiter.api.Assertions;
import omni.impl.RefInputTestArgType;
import omni.impl.FunctionCallType;
import java.util.function.Consumer;
abstract class RefItrMonitor
{
  static enum ItrType
  {
    Itr,
    ListItr;
  }
  final OmniIterator.OfRef itr;
  int expectedCursor;
  int expectedLastRet;
  RefItrMonitor(OmniIterator.OfRef itr,int expectedCursor){
    this.itr=itr;
    this.expectedCursor=expectedCursor;
    this.expectedLastRet=-1;
  }
  public void iterateReverse()
  {
    ((OmniListIterator.OfRef)itr).previous();
    expectedLastRet=--expectedCursor;
  }
  public abstract void verifyIteratorState();
  public void set(int v,RefInputTestArgType inputArgType){
     inputArgType.callListItrSet((OmniListIterator.OfRef)itr,v);
  }
  public void set(int v)
  {
    set(v,RefInputTestArgType.ARRAY_TYPE);
  }
  public void add(int v,RefInputTestArgType inputArgType){
    throw new UnsupportedOperationException();
  }
  public void add(int v)
  {
    add(v,RefInputTestArgType.ARRAY_TYPE);
  }
  public abstract void iterateForward();
  public abstract void remove();
  public boolean hasNext(){
    return itr.hasNext();
  }
  public boolean hasPrevious(){
    return ((OmniListIterator.OfRef)itr).hasPrevious();
  }
  public int nextIndex(){
    return ((OmniListIterator.OfRef)itr).nextIndex();
  }
  public int previousIndex(){
    return ((OmniListIterator.OfRef)itr).previousIndex();
  }
  //TODO forEachRemaining
}
