package omni.impl.set;
import omni.impl.FunctionCallType;
import omni.impl.ShortInputTestArgType;
import omni.impl.MonitoredObjectInputStream;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.CheckedType;
import java.util.ConcurrentModificationException;
import java.io.File;
import java.io.IOException;
import omni.function.ShortPredicate;
import omni.function.ShortConsumer;
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
import omni.function.ShortUnaryOperator;
@SuppressWarnings({"rawtypes","unchecked"})
abstract class AbstractShortSetMonitor<SET extends OmniSet.OfShort>{
  final CheckedType checkedType;
  SET set;
  AbstractShortSetMonitor(CheckedType checkedType)
  {
    this.checkedType=checkedType;
  }
  abstract void verifyAdd(ShortInputTestArgType inputArgType,int val);
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
      set.forEach((ShortConsumer)action);
    }
  }
  static class MonitoredArrayConstructor
    implements IntFunction<Short[]>
  {
    int numCalls;
    @Override public Short[] apply(int arrSize){
      ++numCalls;
      return new Short[arrSize];
    }
  }
  static class MonitoredUnaryOperator implements ShortUnaryOperator
    ,UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public short applyAsShort(short val){
      encounteredValues.add(val);
      return (short)(val+1);
    }
    public Object apply(Object val){
      return applyAsShort((short)val);
    }
  }
  static enum MonitoredRemoveIfPredicateGen
  {
   //TODO come up with a quicker way to test than using random sequences
    Random(null,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(short val){
            return rand.nextDouble()>=threshold;
          }
        };
      }
    },
    RemoveAll(null,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(short val){
            return true;
          }
        };
      }
    },
    RemoveNone(null,false){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          @Override boolean testImpl(short val){
            return false;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,true){
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(short val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(short val){
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
      @Override MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold){
        return new MonitoredRemoveIfPredicate(){
          final Random rand=new Random(randSeed);
          @Override boolean testImpl(short val){
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
    abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(AbstractShortSetMonitor setMonitor,long randSeed,int numExpectedCalls,double threshold);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractShortSetMonitor setMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractShortSetMonitor setMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractShortSetMonitor setMonitor){
        return new MonitoredArrayConstructor();
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          protected void throwingCall(){}
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.NoThrow;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectOutputStream(file){
          protected void throwingCall(){}
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.NoThrow;
          }
        };
      }
    },
    Throw(IndexOutOfBoundsException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractShortSetMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractShortSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public short applyAsShort(short val){
            super.applyAsShort(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractShortSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Short[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void throwingCall(){
            throw new IndexOutOfBoundsException();
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ThrowIOB;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
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
          public void accept(short val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
          }
        };
      }
    },
    ModSet(ConcurrentModificationException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractShortSetMonitor setMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSetMonitor().illegalMod(PreModScenario.ModSet);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractShortSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public short applyAsShort(short val){
            var ret=super.applyAsShort(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractShortSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Short[] apply(int arrSize){
            ++numCalls;
            setMonitor.illegalMod(PreModScenario.ModSet);
            return new Short[arrSize];
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
        return new MonitoredObjectInputStream(file){
          @Override protected void throwingCall(){
            setMonitor.illegalMod(PreModScenario.ModSet);
          }
          public omni.impl.MonitoredFunctionGen getMonitoredFunctionGen(){
            return omni.impl.MonitoredFunctionGen.ModCollection;
          }
        };
      }
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
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
          public void accept(short val){
            super.accept(val);
            itrMonitor.iterateForward();
            itrMonitor.remove();
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModSet(ConcurrentModificationException.class,false){
      @Override MonitoredConsumer getMonitoredConsumer(AbstractShortSetMonitor setMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSetMonitor().illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractShortSetMonitor setMonitor){
        return new MonitoredUnaryOperator(){
          @Override public short applyAsShort(short val){
            super.applyAsShort(val);
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractShortSetMonitor setMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Short[] apply(int arrSize){
            ++numCalls;
            setMonitor.illegalMod(PreModScenario.ModSet);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
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
      @Override MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
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
    MonitoredUnaryOperator getMonitoredUnaryOperator(AbstractShortSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(AbstractShortSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(AbstractItrMonitor itrMonitor);
    MonitoredArrayConstructor getMonitoredArrayConstructor(AbstractShortSetMonitor setMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredObjectInputStream getMonitoredObjectInputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
    MonitoredObjectOutputStream getMonitoredObjectOutputStream(File file,AbstractShortSetMonitor setMonitor) throws IOException{
      throw new UnsupportedOperationException();
    }
  }
  static class MonitoredConsumer implements ShortConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(short val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((short)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(short val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  static abstract class MonitoredRemoveIfPredicate implements ShortPredicate
    ,Predicate<Short>
  {
    final HashSet removedVals=new HashSet();
    final HashSet retainedVals=new HashSet();
    int callCounter;
    @Override public String toString(){
      return removedVals.toString();
    }
    abstract boolean testImpl(short val);
    @Override public MonitoredRemoveIfPredicate negate()
    {
      //not worth implementing but must declare
      return null;
    }
    @Override public boolean test(short val)
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
    @Override public boolean test(Short val)
    {
      return test((short)val);
    }
  }
  //TODO queryTester
  //TODO verifyHashCode
  //TODO verifyToString
  //TODO verifyToArray
  abstract static class AbstractItrMonitor{
     OmniIterator.OfShort itr;
     AbstractItrMonitor(OmniIterator.OfShort itr){
       this.itr=itr;
     }
     void iterateForward(){
       itr.nextShort();
       moveExpectedIteratorStateForward();
     }
     abstract void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType);
     abstract void remove();
     abstract AbstractShortSetMonitor getSetMonitor();
     abstract void verifyIteratorState();
     abstract void moveExpectedIteratorStateForward();
  }
}
