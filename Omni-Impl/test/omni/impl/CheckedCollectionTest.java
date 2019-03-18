package omni.impl;

import java.util.ConcurrentModificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniCollection;
public class CheckedCollectionTest{
    @SuppressWarnings({"rawtypes","unchecked"})
    public static Object createCollectionModifyingObject(OmniCollection.OfRef collection){
        return new Object(){
            @Override
            public String toString(){
                collection.add(new Object());
                return super.toString();
            }
            @Override
            public boolean equals(Object val){
                collection.add(new Object());
                return super.equals(val);
            }
            @Override
            public int hashCode(){
                collection.add(new Object());
                return super.hashCode();
            }
        };
    }
    @Test
    public void testCheckLo(){
        Assertions.assertDoesNotThrow(()->{
            CheckedCollection.checkLo(0);
            CheckedCollection.checkLo(1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkLo(-1);
        });
    }
    @Test
    public void testCheckReadHi(){
        Assertions.assertDoesNotThrow(()->{
            CheckedCollection.checkReadHi(0,1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkReadHi(1,1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkReadHi(2,1);
        });
    }
    @Test
    public void testCheckWriteHi(){
        Assertions.assertDoesNotThrow(()->{
            CheckedCollection.checkWriteHi(0,1);
        });
        Assertions.assertDoesNotThrow(()->{
            CheckedCollection.checkWriteHi(1,1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkWriteHi(2,1);
        });
    }
    @Test
    public void testCheckSubListRange(){
        Assertions.assertEquals(5,CheckedCollection.checkSubListRange(3,8,9));
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(-1,-2,-3);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(-1,-2,-2);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(0,-2,-3);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(-1,0,-3);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(1,2,1);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(-1,2,3);
        });
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            CheckedCollection.checkSubListRange(1,0,3);
        });
    }
    @Test
    public void testCheckModCountintint(){
        Assertions.assertDoesNotThrow(()->{
            CheckedCollection.checkModCount(1,1);
        });
        Assertions.assertThrows(ConcurrentModificationException.class,()->{
            CheckedCollection.checkModCount(1,2);
        });
    }
    @Test
    public void testCheckModCountintintRuntimeException(){
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            throw CheckedCollection.checkModCount(1,1,new IndexOutOfBoundsException());
        });
        Assertions.assertThrows(ConcurrentModificationException.class,()->{
            throw CheckedCollection.checkModCount(1,2,new IndexOutOfBoundsException());
        });
    }
    @Test
    public void testCheckModCountintintThrowable(){
        Assertions.assertThrows(Error.class,()->{
            throw CheckedCollection.checkModCount(1,1,new Error());
        });
        Assertions.assertThrows(ConcurrentModificationException.class,()->{
            throw CheckedCollection.checkModCount(1,2,new Error());
        });
    }
    @Test
    public void testAbstractModCountChecker(){
        class ModCountCheckerTester{
            int modCount;
            ModCountCheckerTester(int modCount){
                this.modCount=modCount;
            }
            public CheckedCollection.AbstractModCountChecker getModCountChecker(int expected){
                return new CheckedCollection.AbstractModCountChecker(expected){
                    @Override
                    protected int getActualModCount(){
                        return modCount;
                    }
                };
            }
        }
        ModCountCheckerTester testClass=new ModCountCheckerTester(1);
        CheckedCollection.AbstractModCountChecker modCountChecker=testClass.getModCountChecker(testClass.modCount);
        Assertions.assertDoesNotThrow(modCountChecker::checkModCount);
        Assertions.assertThrows(IndexOutOfBoundsException.class,()->{
            throw modCountChecker.handleException(new IndexOutOfBoundsException());
        });
        Assertions.assertThrows(Error.class,()->{
            throw modCountChecker.handleException(new Error());
        });
        ++testClass.modCount;
        Assertions.assertThrows(ConcurrentModificationException.class,modCountChecker::checkModCount);
        Assertions.assertThrows(ConcurrentModificationException.class,()->{
            throw modCountChecker.handleException(new IndexOutOfBoundsException());
        });
        Assertions.assertThrows(ConcurrentModificationException.class,()->{
            throw modCountChecker.handleException(new Error());
        });
    }
}
