package omni.impl;
import java.util.ConcurrentModificationException;
public interface CheckedCollection{
    static void checkLo(int index) throws IndexOutOfBoundsException{
        if(index<0){ throw new IndexOutOfBoundsException(index); }
    }
    static <X extends RuntimeException> ConcurrentModificationException checkModCount(int expectedModCount,int actualModCount,int expectedCursor,int actualCursor,X e)
            throws X{
      if(expectedModCount==actualModCount) {
        return checkModCount(expectedCursor,actualCursor,e);
      }
      return new ConcurrentModificationException(getCMEMessage(expectedCursor,actualCursor),e);
    }
    static void checkModCount(int expectedModCount,int actualModCount,int expectedCursor,int actualCursor)
            throws ConcurrentModificationException{
    	checkModCount(expectedModCount,actualModCount);
    	checkModCount(expectedCursor,actualCursor);
        
    }
    static void checkModCount(int expectedModCount,int actualModCount,Object expectedNode,Object actualNode){
        if(expectedModCount != actualModCount || expectedNode != actualNode){
            throw new ConcurrentModificationException("modCount{expected=" + expectedModCount + ",actual="
                    + actualModCount + "};node{expected=" + expectedNode + ",actual=" + actualNode + '}');
        }
    }
    static void checkModCount(int expectedModCount,int actualModCount) throws ConcurrentModificationException{
        if(actualModCount==expectedModCount){ return; }
        throw new ConcurrentModificationException(getCMEMessage(expectedModCount,actualModCount));
    }
    static <X extends RuntimeException> ConcurrentModificationException checkModCount(int expectedModCount,
            int actualModCount,X e) throws X{
        if(expectedModCount == actualModCount){
            throw e;
        }
        return new ConcurrentModificationException(getCMEMessage(expectedModCount,actualModCount),e);
    }
    static <X extends Throwable> ConcurrentModificationException checkModCount(int expectedModCount,int actualModCount,
            X e) throws X{
        if(expectedModCount == actualModCount){
            throw e;
        }
        return new ConcurrentModificationException(getCMEMessage(expectedModCount,actualModCount),e);
    }

    static void checkReadHi(int index,int size) throws IndexOutOfBoundsException{
        if(index<size){ return; }
        throw new IndexOutOfBoundsException("Invalid read index : index = "+index+"; size = "+size);
    }
    static int checkSubListRange(int fromIndex,int toIndex,int size){
        final int subListSize;
        if((subListSize=toIndex - fromIndex) < 0 || fromIndex < 0 || size < toIndex){
            throw new IndexOutOfBoundsException(
                    "Invalid sublist range : fromIndex=" + fromIndex + "; toIndex=" + toIndex + "; size=" + size);
        }
        return subListSize;
    }
    static void checkWriteHi(int index,int size) throws IndexOutOfBoundsException{
        if(index<=size){ return; }
        throw new IndexOutOfBoundsException("Invalid write index : index = "+index+"; size = "+size);
    }
    private static String getCMEMessage(int expected,int actual){
        return "Expected = "+expected+"; Actual = "+actual;
    }

    static abstract class AbstractModCountChecker{
        private transient final int expectedModCount;
        public AbstractModCountChecker(int expectedModCount){
            this.expectedModCount=expectedModCount;
        }
        public final void checkModCount(){
            CheckedCollection.checkModCount(expectedModCount,getActualModCount());
        }
        public final <X extends RuntimeException> ConcurrentModificationException handleException(X e)
                throws ConcurrentModificationException,X{
            return CheckedCollection.checkModCount(expectedModCount,getActualModCount(),e);
        }
        public final <X extends Throwable> ConcurrentModificationException handleException(X e)
                throws ConcurrentModificationException,X{
            return CheckedCollection.checkModCount(expectedModCount,getActualModCount(),e);
        }
        protected abstract int getActualModCount();
    }
}
