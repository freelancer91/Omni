package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import omni.impl.FunctionCallType;
import omni.impl.RefOutputTestArgType;
import omni.impl.RefInputTestArgType;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.function.UnaryOperator;
import java.util.ArrayList;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.HashSet;
import omni.api.OmniCollection;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
@SuppressWarnings({"rawtypes","unchecked"})
interface RefSeqMonitor
{
  interface ItrMonitor
  {
    void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType);
    default void iterateReverse(){
      throw new UnsupportedOperationException();
    }
    RefSeqMonitor getSeqMonitor();
    void verifyNext(int expectedVal,RefOutputTestArgType outputType);
    default void verifyPrevious(int expectedVal,RefOutputTestArgType outputType){
      throw new UnsupportedOperationException();
    }
    void verifyIteratorState();
    default void set(int v,RefInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    default void set(int v){
      set(v,RefInputTestArgType.ARRAY_TYPE);
    }
    default void add(int v,RefInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    default void add(int v){
      add(v,RefInputTestArgType.ARRAY_TYPE);
    }
    void iterateForward();
    void remove();
    boolean hasNext();
    default boolean hasPrevious(){
      throw new UnsupportedOperationException();
    }
    default int nextIndex(){
      throw new UnsupportedOperationException();
    }
    default int previousIndex(){
      throw new UnsupportedOperationException();
    }
  }
  static enum PreModScenario{
    ModSeq(ConcurrentModificationException.class,false,true),
    ModParent(ConcurrentModificationException.class,true,false),
    ModRoot(ConcurrentModificationException.class,true,false),
    NoMod(null,true,true);
    final Class<? extends Throwable> expectedException;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    PreModScenario(Class<? extends Throwable> expectedException,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
  }
  static enum ItrType{
    Itr,
    ListItr;
  }
  static enum SequenceLocation{
    BEGINNING(null,true),
    NEARBEGINNING(null,false),
    MIDDLE(null,false),
    NEAREND(null,false),
    END(null,false),
    IOBLO(IndexOutOfBoundsException.class,true),
    IOBHI(IndexOutOfBoundsException.class,true);
    final Class<? extends Throwable> expectedException;
    final boolean validForEmpty;
    SequenceLocation(Class<? extends Throwable> expectedException,boolean validForEmpty){
      this.expectedException=expectedException;
      this.validForEmpty=validForEmpty;
    }
  }
  static enum ListItrSetScenario{
    HappyPath(null,PreModScenario.NoMod),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot),
    ThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostAddThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostRemoveThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    ThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    ThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    ThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostAddThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostAddThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostAddThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostRemoveThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostRemoveThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostRemoveThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    ListItrSetScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
    }
  }
  static enum ItrRemoveScenario{
    PostNext(null,false,true),
    PostPrevious(null,false,false),
    PostInit(IllegalStateException.class,true,true),
    PostAdd(IllegalStateException.class,true,false),
    PostRemove(IllegalStateException.class,false,true);
    final Class<? extends Throwable> expectedException;
    final boolean validWithEmptySeq;
    final boolean validWithForwardItr;
    ItrRemoveScenario(Class<? extends Throwable> expectedException,boolean validWithEmptySeq,boolean validWithForwardItr){
      this.expectedException=expectedException;
      this.validWithEmptySeq=validWithEmptySeq;
      this.validWithForwardItr=validWithForwardItr;
    }
  }
  static enum IterationScenario{
    NoMod(NoSuchElementException.class,PreModScenario.NoMod,false),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq,false),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent,false),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot,false),
    ModSeqSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModSeq,true),
    ModParentSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModParent,true),
    ModRootSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModRoot,true);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    final boolean validWithEmptySeq;
    IterationScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario,boolean validWithEmptySeq){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
      this.validWithEmptySeq=validWithEmptySeq;
    }
  }
  static enum CheckedType{
    CHECKED(true),
    UNCHECKED(false);
    final boolean checked;
    CheckedType(boolean checked){
      this.checked=checked;
    }
  }
  static enum SequenceContentsScenario{
    EMPTY(false),
    NONEMPTY(true);
    final boolean nonEmpty;
    SequenceContentsScenario(boolean nonEmpty){
      this.nonEmpty=nonEmpty;
    }
  }
  static enum MonitoredRemoveIfPredicateGen
  {
    RemoveAll(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(Object val){
            return true;
          }
        };
      }
    },
    RemoveNone(null,true,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(Object val){
            return false;
          }
        };
      }
    },
    Random(null,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              throw new IndexOutOfBoundsException();
            }
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModSeq);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModParent);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModRoot);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModSeq);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModParent);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(Object val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              seqMonitor.illegalAdd(PreModScenario.ModRoot);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean isRandomized;
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean isRandomized){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.isRandomized=isRandomized;
    }
    abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(RefSeqMonitor seqMonitor,long randSeed,int numExpectedCalls,double threshold);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor();
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file);
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file);
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            super.apply(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ModItr(ConcurrentModificationException.class,false,false,true){
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            var ret=super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return new Integer[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            var ret=super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return new Integer[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            var ret=super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return new Integer[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
    },
    ThrowModItr(ConcurrentModificationException.class,false,false,true){
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(Object val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public Object apply(Object val){
            super.apply(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void preModCall(){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
    MonitoredUnaryOperator getMonitoredUnaryOperator(RefSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(RefSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor);
    MonitoredArrayConstructor getMonitoredArrayConstructor(RefSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredObjectInputStream getMonitoredObjectInputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
    MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,RefSeqMonitor seqMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
  }
  static class ModParentMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModParentMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModParent);
      return Integer.compare(compareVal,that.compareVal);
    }
  }
  static class AIOBThrowingMonitoredObject extends MonitoredObject{
    AIOBThrowingMonitoredObject(int compareVal){
      super(compareVal);
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  static class IOBThrowingMonitoredObject extends MonitoredObject{
    IOBThrowingMonitoredObject(int compareVal){
      super(compareVal);
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModSeqMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModSeqMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModSeq);
      return Integer.compare(compareVal,that.compareVal);
    }
  }
  static class ModParentIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModParentIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModParent);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModParentAIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModParentAIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModParent);
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  static class ModSeqIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModSeqIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModSeq);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModSeqAIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModSeqAIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModSeq);
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  static class ModRootMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModRootMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModRoot);
      return Integer.compare(compareVal,that.compareVal);
    }
  }
  static class ModRootIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModRootIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModRoot);
      throw new IndexOutOfBoundsException();
    }
  }
  static class ModRootAIOBThrowingMonitoredObject extends MonitoredObject{
    final RefSeqMonitor seqMonitor;
    ModRootAIOBThrowingMonitoredObject(RefSeqMonitor seqMonitor,int compareVal){
      super(compareVal);
      this.seqMonitor=seqMonitor;
    }
    @Override public int compareTo(MonitoredObject that){
      ++numCompareToCalls;
      seqMonitor.illegalAdd(PreModScenario.ModRoot);
      throw new ArrayIndexOutOfBoundsException();
    }
  }
  static class MonitoredObject implements Comparable<MonitoredObject>{
    int numEqualsCalls;
    int numHashCodeCalls;
    int numToStringCalls;
    int numCompareToCalls;
    int compareVal;
    MonitoredObject(){
    }
    MonitoredObject(int compareVal){
      this.compareVal=compareVal;
    }
    @Override public int compareTo(MonitoredObject that){
      return Integer.compare(compareVal,that.compareVal);
    }
    @Override public boolean equals(Object obj){
      ++numEqualsCalls;
      return super.equals(this);
    }
    @Override public int hashCode(){
      ++numHashCodeCalls;
      return super.hashCode();
    }
    @Override public String toString(){
      ++numToStringCalls;
      return super.toString();
    }
  }
  static abstract class MonitoredComparator implements Comparator
  {
    public abstract int compare(Object val1, Object val2);
  }
  static class MonitoredUnaryOperator implements UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public Object apply(Object val){
      encounteredValues.add(val);
      return Integer.valueOf((Integer)val)+1;
    }
  }
  static class MonitoredArrayConstructor
    implements IntFunction<Integer[]>
  {
    int numCalls;
    @Override public Integer[] apply(int arrSize){
      ++numCalls;
      return new Integer[arrSize];
    }
  }
  static class MonitoredConsumer implements Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(Object val){
      encounteredValues.add(val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(Object val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  static abstract class MonitoredRemoveIfPredicate implements Predicate
  {
    final HashSet removedVals=new HashSet();
    int callCounter;
    int numRemoved;
    abstract boolean testImpl(Object val);
    @Override public MonitoredRemoveIfPredicate negate()
    {
      //not worth implementing but must declare
      return null;
    }
    @Override public boolean test(Object val)
    {
      ++callCounter;
      if(removedVals.contains(val))
      {
        ++numRemoved;
        return true;
      }
      if(testImpl(val))
      {
        ++numRemoved;
        removedVals.add(val);
        return true;
      }
      return false;
    }
  }
  static enum MonitoredObjectGen{
    NoThrow(null,true){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject();
      }
    },
    Throw(IndexOutOfBoundsException.class,true){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            throw new IndexOutOfBoundsException();
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            throw new IndexOutOfBoundsException();
          }
          @Override public String toString(){
            ++numToStringCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ModSeq(ConcurrentModificationException.class,true){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            return obj==this;
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            return System.identityHashCode(this);
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            return obj==this;
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            return System.identityHashCode(this);
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            return obj==this;
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            return System.identityHashCode(this);
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));
          }
        };
      }
    },
    ThrowModSeq(ConcurrentModificationException.class,true){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false){
      MonitoredObject getMonitoredObject(RefSeqMonitor monitor){
        return new MonitoredObject(){
          @Override public boolean equals(Object obj){
            ++numEqualsCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
          @Override public int hashCode(){
            ++numHashCodeCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
          @Override public String toString(){
            ++numToStringCalls;
            monitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    MonitoredObjectGen(Class<? extends Throwable> expectedException,boolean appliesToRoot){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
    }
    abstract MonitoredObject getMonitoredObject(RefSeqMonitor monitor);
  }
  static enum MonitoredComparatorGen{
    NullComparatorThrowAIOB(IllegalArgumentException.class,true,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new AIOBThrowingMonitoredObject(3));
        seqMonitor.add(new AIOBThrowingMonitoredObject(2));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new AIOBThrowingMonitoredObject(2));
        seqMonitor.add(new AIOBThrowingMonitoredObject(3));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparatorThrowIOB(IndexOutOfBoundsException.class,true,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new IOBThrowingMonitoredObject(3));
        seqMonitor.add(new IOBThrowingMonitoredObject(2));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new IOBThrowingMonitoredObject(2));
        seqMonitor.add(new IOBThrowingMonitoredObject(3));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparatorModSeq(ConcurrentModificationException.class,true,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModSeqMonitoredObject(seqMonitor,2));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModSeqMonitoredObject(seqMonitor,3));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
         switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
         switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparatorModParent(ConcurrentModificationException.class,false,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModParentMonitoredObject(seqMonitor,2));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModParentMonitoredObject(seqMonitor,3));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
            verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    NullComparatorModRoot(ConcurrentModificationException.class,false,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModRootMonitoredObject(seqMonitor,2));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModRootMonitoredObject(seqMonitor,3));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    NullComparatorModSeqThrowAIOB(ConcurrentModificationException.class,true,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqAIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModSeqAIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqAIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModSeqAIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparatorModSeqThrowIOB(ConcurrentModificationException.class,true,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModSeqIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModSeqIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModSeqIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparatorModParentThrowAIOB(ConcurrentModificationException.class,false,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentAIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModParentAIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentAIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModParentAIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    NullComparatorModParentThrowIOB(ConcurrentModificationException.class,false,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModParentIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModParentIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModParentIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    NullComparatorModRootThrowAIOB(ConcurrentModificationException.class,false,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootAIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModRootAIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootAIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModRootAIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    NullComparatorModRootThrowIOB(ConcurrentModificationException.class,false,false,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootIOBThrowingMonitoredObject(seqMonitor,2));
        seqMonitor.add(new ModRootIOBThrowingMonitoredObject(seqMonitor,3));
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(new ModRootIOBThrowingMonitoredObject(seqMonitor,3));
        seqMonitor.add(new ModRootIOBThrowingMonitoredObject(seqMonitor,2));
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(new MonitoredObject(3));
        verifyItr.verifyIndexAndIterate(new MonitoredObject(2));
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    NoThrowAscending(null,true,true,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            return Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NoThrowDescending(null,true,false,true,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            return -Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparator(null,true,true,false,true){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void initReverseHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowAIOB(IllegalArgumentException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowIOB(IndexOutOfBoundsException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqAscending(ConcurrentModificationException.class,true,true,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqDescending(ConcurrentModificationException.class,true,false,true,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return -Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModParentAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModParentDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return -Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return -Integer.compare((int)val1,(int)val2);
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModSeqThrowAIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqThrowIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
          verifyItr.verifyIndexAndIterate(2);
          verifyItr.verifyIndexAndIterate(3);
          switch(preModScenario){
            case NoMod:
            case ModSeq:
              verifyItr.verifyIllegalAdd();
            case ModParent:
            case ModRoot:
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModParentThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModParentThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(Object val1,Object val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(RefSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean ascending;
    final boolean descending;
    final boolean nullComparator;
    MonitoredComparatorGen(Class<? extends Throwable> expectedException,final boolean appliesToRoot,final boolean ascending,final boolean descending,boolean nullComparator){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.ascending=ascending;
      this.descending=descending;
      this.nullComparator=nullComparator;
    }
    abstract void initHelper(RefSeqMonitor seqMonitor);
    void initReverseHelper(RefSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void init(RefSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      if(seqContentsScenario.nonEmpty){
        initHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void initReverse(RefSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      if(seqContentsScenario.nonEmpty){
        initReverseHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    abstract void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario);
    void assertSorted(RefSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertReverseSorted(RefSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertReverseSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    abstract MonitoredComparator getMonitoredComparator(RefSeqMonitor seqMonitor);
  }
  public static abstract class SequenceVerificationItr{
    public abstract void verifyLiteralIndexAndIterate(Object val);
    public abstract void verifyIndexAndIterate(MonitoredObject monitoredObject);
    public abstract void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val);
    public abstract SequenceVerificationItr getPositiveOffset(int i);
    public abstract SequenceVerificationItr skip(int i);
    public abstract boolean equals(Object val);
    public SequenceVerificationItr verifyNaturalAscending(int v,RefInputTestArgType inputArgType,int length){
      return verifyAscending(v,inputArgType,length);
    }
    public SequenceVerificationItr verifyAscending(int v,RefInputTestArgType inputArgType,int length){
      for(int i=0;i<length;++i,++v){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public void verifyIndexAndIterate(int val){
      verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,val);
    }
    public SequenceVerificationItr verifyNaturalAscending(int length)
    {
       return verifyNaturalAscending(0,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyNaturalAscending(int v,int length)
    {
       return verifyNaturalAscending(v,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyNaturalAscending(RefInputTestArgType inputArgType,int length){
      return verifyNaturalAscending(0,inputArgType,length);
    }
    public SequenceVerificationItr verifyAscending(int length){
      return verifyAscending(0,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(int v,int length){
      return verifyAscending(v,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(RefInputTestArgType inputArgType,int length){
      return verifyAscending(0,inputArgType,length);
    }
    public SequenceVerificationItr verifyDescending(int length){
      return verifyDescending(length,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyDescending(int v,int length)
    {
       return verifyDescending(v,RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyDescending(int v,RefInputTestArgType inputArgType,int length)
    {
      for(int i=0;i<length;++i){
        verifyIndexAndIterate(inputArgType,--v);
      }
      return this;
    }
    public SequenceVerificationItr verifyDescending(RefInputTestArgType inputArgType,int length){
      return verifyDescending(length,inputArgType,length);
    }
    public SequenceVerificationItr verifyNearBeginningInsertion(int length){
      return verifyNearBeginningInsertion(RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyNearBeginningInsertion(RefInputTestArgType inputArgType,final int length){
      int v=3;
      for(int i=0,bound=(length/4);i<bound;++i)
      {
        verifyIndexAndIterate(inputArgType,v);
        v+=4;
      }
      v-=4;
      for(int i=0,bound=(length-(length/4));i<bound;++i)
      {
        if((i%3)==0)
        {
          --v;
        }
        verifyIndexAndIterate(inputArgType,v);
        --v;
      }
      return this;
    }
    public SequenceVerificationItr verifyNearEndInsertion(int length){
      return verifyNearEndInsertion(RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyNearEndInsertion(RefInputTestArgType inputArgType,final int length){
      int v=0;
      for(int i=0,bound=(length/4)*3;i<bound;)
      {
        verifyIndexAndIterate(inputArgType,v);
        ++i;
        if((i%3)==0)
        {
          ++v;
        }
        ++v;
      }
      --v;
      for(int i=0,bound=(length-((length/4)*3));i<bound;++i)
      {
        verifyIndexAndIterate(inputArgType,v);
        v-=4;
      }
      return this;
    }
    public SequenceVerificationItr verifyMidPointInsertion(int length){
      return verifyMidPointInsertion(RefInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyMidPointInsertion(RefInputTestArgType inputArgType,final int length){
      int i=0;
      for(int v=1,halfLength=length/2;i<halfLength;++i,v+=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      for(int v=length-2;i<length;++i,v-=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public abstract SequenceVerificationItr verifyParentPostAlloc();
    public abstract SequenceVerificationItr verifyRootPostAlloc();
    public SequenceVerificationItr verifyIllegalAdd(){
      verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,0);
      return this;
    }
    public SequenceVerificationItr verifyPostAlloc(){
      return verifyPostAlloc(PreModScenario.NoMod);
    }
    public abstract SequenceVerificationItr verifyPostAlloc(int expectedVal);
    public SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
      if(preModScenario==PreModScenario.ModSeq){verifyIllegalAdd();}
      verifyParentPostAlloc();
      if(preModScenario==PreModScenario.ModParent){verifyIllegalAdd();}
      verifyRootPostAlloc();
      if(preModScenario==PreModScenario.ModRoot){verifyIllegalAdd();}
      return this;
    }
  }
  ItrMonitor getItrMonitor();
  default ItrMonitor getListItrMonitor(){
    throw new UnsupportedOperationException();
  }
  default ItrMonitor getListItrMonitor(int index){
    throw new UnsupportedOperationException();
  }
  default ItrMonitor getItrMonitor(ItrType itrType){
    switch(itrType){
      case Itr:
        return getItrMonitor();
      case ListItr:
        return getListItrMonitor();
      default:
        throw new Error("unknown itr type "+itrType);
    }
  }
  default ItrMonitor getItrMonitor(SequenceLocation seqLocation,ItrType itrType){
    switch(seqLocation){
      case BEGINNING:
        switch(itrType){
          case ListItr:
            return getListItrMonitor();
          case Itr:
            return getItrMonitor();
          default:
            throw new Error("Unknown itr type "+itrType);
        }
      case NEARBEGINNING:
        return getListItrMonitor(getExpectedSeqSize()/4);
      case MIDDLE:
        return getListItrMonitor(getExpectedSeqSize()/2);
      case NEAREND:
        return getListItrMonitor((getExpectedSeqSize()/4)*3);
      case END:
        return getListItrMonitor(getExpectedSeqSize());
      default:
        throw new Error("Unknown sequence location "+seqLocation);
    } 
  }
  default ItrMonitor getListItrMonitor(SequenceLocation seqLocation){
    switch(seqLocation){
      case BEGINNING:
        return getListItrMonitor();
      case NEARBEGINNING:
        return getListItrMonitor(getExpectedSeqSize()/4);
      case MIDDLE:
        return getListItrMonitor(getExpectedSeqSize()/2);
      case NEAREND:
        return getListItrMonitor((getExpectedSeqSize()/4)*3);
      case END:
        return getListItrMonitor(getExpectedSeqSize());
      default:
        throw new Error("Unknown sequence location "+seqLocation);
    }
  }
  SequenceVerificationItr verifyPreAlloc(int expectedVal);
  SequenceVerificationItr verifyPreAlloc();
  int getExpectedSeqSize();
  void illegalAdd(PreModScenario preModScenario);
  boolean add(Object obj);
  default void add(int index,int val,RefInputTestArgType inputArgType)
  {
    throw new UnsupportedOperationException();
  }
  default void add(int index,int val){
    add(index,val,RefInputTestArgType.ARRAY_TYPE);
  }
  boolean add(int val,RefInputTestArgType inputArgType);
  default boolean add(int val){
    return add(val,RefInputTestArgType.ARRAY_TYPE);
  }
  default void push(int val,RefInputTestArgType inputArgType)
  {
    throw new UnsupportedOperationException();
  }
  default void push(int val){
    push(val,RefInputTestArgType.ARRAY_TYPE);
  }
  default void put(int index,int val,RefInputTestArgType inputArgType)
  {
    throw new UnsupportedOperationException();
  }
  void verifyStructuralIntegrity();
  boolean isEmpty();
  void forEach(MonitoredConsumer action,FunctionCallType functionCallType);
  default void unstableSort(MonitoredComparator sorter){
    throw new UnsupportedOperationException();
  }
  default void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    throw new UnsupportedOperationException();
  }
  default void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
    throw new UnsupportedOperationException();
  }
  default void stableAscendingSort(){
    throw new UnsupportedOperationException();
  }
  default void stableDescendingSort(){
    throw new UnsupportedOperationException();
  }
  default void unstableAscendingSort(){
    throw new UnsupportedOperationException();
  }
  default void unstableDescendingSort(){
    throw new UnsupportedOperationException();
  }
  default void removeAt(int expectedVal,RefOutputTestArgType outputType,int index){
    throw new UnsupportedOperationException();
  }
  default void get(int expectedVal,RefOutputTestArgType outputType,int index){
    throw new UnsupportedOperationException();
  }
  void clear();
  default void pop(int expectedVal,RefOutputTestArgType outputType)
  {
    throw new UnsupportedOperationException();
  }
  default void poll(int expectedVal,RefOutputTestArgType outputType)
  {
    throw new UnsupportedOperationException();
  }
  default void verifySet(int index,int val,int expectedRet,FunctionCallType functionCallType){
     throw new UnsupportedOperationException();
  }
  void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfRef clone);
  void writeObject(ObjectOutputStream oos) throws IOException;
  Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException;
  private static int verifySingleStrElement(String str,int strOffset){
    Assertions.assertTrue(str.charAt(strOffset)=='1',"String fails at index "+strOffset);
    return strOffset;
  }
  private static void verifyLargeStr(String str,int offset,int bound,SequenceVerificationItr verifyItr){
    if(offset>=bound){
      return;
    }
    int strOffset;
    if(offset==0){
      strOffset=verifySingleStrElement(str,1);
    }else{
      strOffset=(3*offset)-2;
    }
    for(;;){
      verifyItr.verifyIndexAndIterate(1);
      if(++offset==bound){
        return;
      }
      if(str.charAt(++strOffset)!=',' || str.charAt(++strOffset)!=' '){
        break;
      }
      strOffset=verifySingleStrElement(str,++strOffset);
    }
    Assertions.fail("string fails at index "+strOffset);
  }
  String callToString();
  default void verifyMASSIVEString(){
    String string=callToString();
    verifyStructuralIntegrity();
    var verifyItr=verifyPreAlloc(1);
    Assertions.assertEquals('[',string.charAt(0));
    Assertions.assertEquals(']',string.charAt(string.length()-1));
    int numThreads=Runtime.getRuntime().availableProcessors();
    int seqSize=getExpectedSeqSize();
    int threadOffset=0;
    int threadSpan=seqSize/numThreads;
    Thread[] threads=new Thread[numThreads-1];
    for(int i=0;i<numThreads-1;++i){
      final int thisThreadOffset=threadOffset;
      final int thisThreadBound=thisThreadOffset+threadSpan;
      final var thisThreadVerifyItr=verifyItr.getPositiveOffset(thisThreadOffset);
      threadOffset=thisThreadBound;
      (threads[i]=new Thread(()->verifyLargeStr(string,thisThreadOffset,thisThreadBound,thisThreadVerifyItr))).start();
    }
    verifyLargeStr(string,threadOffset,threadOffset+threadSpan,verifyItr.getPositiveOffset(threadOffset));
    try{
      for(int i=0;i<numThreads-1;++i){
        threads[i].join();
      }
    }catch(InterruptedException e){
      Assertions.fail(e);
    }
    verifyItr.skip(seqSize).verifyPostAlloc(1);
  }
}
