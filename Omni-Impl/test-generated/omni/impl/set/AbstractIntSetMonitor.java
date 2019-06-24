package omni.impl.set;
import omni.impl.FunctionCallType;
import omni.impl.IntInputTestArgType;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.CheckedType;
import java.util.ConcurrentModificationException;
import java.io.File;
import java.io.IOException;
import java.util.function.IntPredicate;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import omni.api.OmniSet;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.HashSet;
import java.util.ArrayList;
import omni.api.OmniIterator;
import java.util.function.UnaryOperator;
import java.util.function.IntUnaryOperator;
@SuppressWarnings({"rawtypes","unchecked"})
abstract class AbstractIntSetMonitor<SET extends OmniSet.OfInt>{
  final CheckedType checkedType;
  SET set;
  AbstractIntSetMonitor(CheckedType checkedType)
  {
    this.checkedType=checkedType;
  }
  abstract void verifyAdd(IntInputTestArgType inputArgType,int val);
  abstract void illegalMod(PreModScenario preModScenario);
  abstract AbstractItrMonitor getItrMonitor();
  abstract void verifyStructuralIntegrity();
  abstract void writeObject(ObjectOutputStream oos) throws IOException;
  abstract void clear();
  abstract void verifyRemoveIf(MonitoredRemoveIfPredicate predicate,FunctionCallType functionCallType,int numExpectedCalls);
  void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      set.forEach((Consumer)action);
    }else
    {
      set.forEach((IntConsumer)action);
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
  static class MonitoredUnaryOperator implements IntUnaryOperator
    ,UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public int applyAsInt(int val){
      encounteredValues.add(val);
      return (int)(val+1);
    }
    public Object apply(Object val){
      return applyAsInt((int)val);
    }
  }
  static enum MonitoredRemoveIfPredicateGen
  {
   //TODO come up with a quicker way to test than using random sequences
    Random(null,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(int val){
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    RemoveAll(null,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(int val){
            return true;
          }
        };
      }
    },
    RemoveNone(null,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(int val){
            return false;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(int val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              throw new IndexOutOfBoundsException();
            }
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    ModSet(ConcurrentModificationException.class,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(int val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              setMonitor.illegalMod(PreModScenario.ModSet);
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ThrowModSet(ConcurrentModificationException.class,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(int val){
            if(callCounter>rand.nextInt(numExpectedCalls))
            {
              setMonitor.illegalMod(PreModScenario.ModSet);
              throw new IndexOutOfBoundsException();
            }
            return rand.nextBoolean();
          }
        };
      }
    },
    ;
    final Class<? extends Throwable> expectedException;
    final boolean isRandomized;
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,boolean isRandomized){
      this.expectedException=expectedException;
      this.isRandomized=isRandomized;
    }
    abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractIntSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractIntSetMonitor setMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractIntSetMonitor setMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractIntSetMonitor setMonitor){
        return new MonitoredArrayConstructor();
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          protected void throwingCall(){}
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.NoThrow;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          protected void throwingCall(){}
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.NoThrow;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractIntSetMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractIntSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public int applyAsInt(int val){
            super.applyAsInt(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractIntSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void throwingCall(){
            throw new IndexOutOfBoundsException();
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ThrowIOB;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void throwingCall(){
            throw new IndexOutOfBoundsException();
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ThrowIOB;
          }
        };
      }
    },
    ModItr(ConcurrentModificationException.class,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
          }
        };
      }
    },
    ModSet(ConcurrentModificationException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractIntSetMonitor setMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            itrMonitor.getSetMonitor().illegalMod(PreModScenario.ModSet);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractIntSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public int applyAsInt(int val){
            var ret=super.applyAsInt(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractIntSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            setMonitor.illegalMod(PreModScenario.ModSet);
            return new Integer[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void throwingCall(){
            setMonitor.illegalMod(PreModScenario.ModSet);
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ModCollection;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void throwingCall(){
            setMonitor.illegalMod(PreModScenario.ModSet);
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ModCollection;
          }
        };
      }
    },
    ThrowModItr(ConcurrentModificationException.class,true){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModSet(ConcurrentModificationException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractIntSetMonitor setMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(int val){
            super.accept(val);
            itrMonitor.getSetMonitor().illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractIntSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public int applyAsInt(int val){
            super.applyAsInt(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractIntSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Integer[] apply(int arrSize){
            ++numCalls;
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void throwingCall(){
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ThrowIOBModCollection;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          @Override protected void throwingCall(){
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ThrowIOBModCollection;
          }
        };
      }
    },
    ;
    final Class<? extends Throwable> expectedException;
    final boolean appliesToItr;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean appliesToItr){
      this.expectedException=expectedException;
      this.appliesToItr=appliesToItr;
    }
    MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractIntSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(AbstractIntSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor);
    MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractIntSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
    MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractIntSetMonitor setMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
  }
  static class MonitoredConsumer implements IntConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(int val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((int)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(int val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  static abstract class MonitoredRemoveIfPredicate implements IntPredicate
    ,Predicate<Integer>
  {
    final HashSet removedVals=new HashSet();
    final HashSet retainedVals=new HashSet();
    int callCounter;
    @Override public String toString(){
      return removedVals.toString();
    }
    abstract boolean testImpl(int val);
    @Override public MonitoredRemoveIfPredicate negate()
    {
      //not worth implementing but must declare
      return null;
    }
    @Override public boolean test(int val)
    {
      ++callCounter;
      if(retainedVals.contains(val))
      {
        return false;
      }
      if(removedVals.contains(val))
      {
        return true;
      }
      if(testImpl(val))
      {
        removedVals.add(val);
        return true;
      }
      retainedVals.add(val);
      return false;
    }
    @Override public boolean test(Integer val)
    {
      return test((int)val);
    }
  }
  //TODO queryTester
  //TODO verifyHashCode
  //TODO verifyToString
  //TODO verifyToArray
  abstract static class AbstractItrMonitor{
     OmniIterator.OfInt itr;
     AbstractItrMonitor(OmniIterator.OfInt itr){
       this.itr=itr;
     }
     void iterateForward(){
       itr.nextInt();
       moveExpectedIteratorStateForward();
     }
     abstract void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType);
     abstract void remove();
     abstract AbstractIntSetMonitor getSetMonitor();
     abstract void verifyIteratorState();
     abstract void moveExpectedIteratorStateForward();
  }
}
