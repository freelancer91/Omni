package omni.impl;
import java.util.ConcurrentModificationException;
public interface CheckedCollection{
  static void checkLo(int index) throws IndexOutOfBoundsException{
    if(index<0){ throw new IndexOutOfBoundsException(index); }
  }
  static void checkModCount(int expectedModCount,int actualModCount) throws ConcurrentModificationException{
    if(actualModCount==expectedModCount){ return; }
    throw new ConcurrentModificationException(getCMEMessage(expectedModCount,actualModCount));
  }
  static RuntimeException checkModCount(int expectedModCount,int actualModCount,RuntimeException e){
    if(actualModCount==expectedModCount){ return e; }
    return new ConcurrentModificationException(getCMEMessage(expectedModCount,actualModCount),e);
  }
  static void checkReadHi(int index,int size) throws IndexOutOfBoundsException{
    if(index<size){ return; }
    throw new IndexOutOfBoundsException("Invalid read index : index = "+index+"; size = "+size);
  }
  static void CheckSubListRange(int fromIndex,int toIndex,int size){
    if(fromIndex>=0&&toIndex<=size&&fromIndex<=toIndex){ return; }
    throw new IndexOutOfBoundsException(
        "Invalid sublist range : fromIndex = "+fromIndex+"; toIndex = "+toIndex+"; size = "+size);
  }
  static void checkWriteHi(int index,int size) throws IndexOutOfBoundsException{
    if(index<=size){ return; }
    throw new IndexOutOfBoundsException("Invalid write index : index = "+index+"; size = "+size);
  }
  private static String getCMEMessage(int expectedModCount,int actualModCount){
    return "Expected modCount = "+expectedModCount+"; Actual modCount = "+actualModCount;
  }
}
