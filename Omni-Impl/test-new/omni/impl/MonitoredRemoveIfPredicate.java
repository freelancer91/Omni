package omni.impl;

import java.util.HashSet;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import omni.function.BooleanPredicate;
import omni.function.BytePredicate;
import omni.function.CharPredicate;
import omni.function.FloatPredicate;
import omni.function.ShortPredicate;

@SuppressWarnings("rawtypes")
public abstract class MonitoredRemoveIfPredicate
implements
Predicate,
BooleanPredicate,
BytePredicate,
CharPredicate,
ShortPredicate,
IntPredicate,
LongPredicate,
FloatPredicate,
DoublePredicate{

    public final HashSet<Object> retainedVals=new HashSet<>();
    public final HashSet<Object> removedVals=new HashSet<>();
    public int numCalls;
    public int numRetained;
    public int numRemoved;

    protected boolean testImpl(){
        throw new UnsupportedOperationException();
    }
    protected boolean testImpl(double val){
        return testImpl();
    }
    protected boolean testImpl(float val){
        return testImpl();
    }
    protected boolean testImpl(long val){
        return testImpl();
    }
    protected boolean testImpl(int val){
        return testImpl();
    }
    protected boolean testImpl(short val){
        return testImpl();
    }
    protected boolean testImpl(char val){
        return testImpl();
    }
    protected boolean testImpl(byte val){
        return testImpl();
    }
    protected boolean testImpl(boolean val){
        return testImpl();
    }
    protected boolean testImpl(Object val){
        return testImpl();
    }
    @Override
    public boolean test(double value){
        ++numCalls;
        if(retainedVals.contains(value)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            ++numRemoved;
            return true;
        }
        if(testImpl(value)){
            ++numRemoved;
            removedVals.add(value);
            return true;
        }
        ++numRetained;
        retainedVals.add(value);
        return false;
    }

    @Override
    public boolean test(float val){
        ++numCalls;
        if(retainedVals.contains(val)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            ++numRemoved;
            return true;
        }
        if(testImpl(val)){
            ++numRemoved;
            removedVals.add(val);
            return true;
        }
        ++numRetained;
        retainedVals.add(val);
        return false;
    }

    @Override
    public boolean test(long value){
        ++numCalls;
        if(retainedVals.contains(value)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            ++numRemoved;
            return true;
        }
        if(testImpl(value)){
            ++numRemoved;
            removedVals.add(value);
            return true;
        }
        ++numRetained;
        retainedVals.add(value);
        return false;
    }

    @Override
    public boolean test(int value){
        ++numCalls;
        if(retainedVals.contains(value)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            ++numRemoved;
            return true;
        }
        if(testImpl(value)){
            ++numRemoved;
            removedVals.add(value);
            return true;
        }
        ++numRetained;
        retainedVals.add(value);
        return false;
    }

    @Override
    public boolean test(short val){
        ++numCalls;
        if(retainedVals.contains(val)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            ++numRemoved;
            return true;
        }
        if(testImpl(val)){
            ++numRemoved;
            removedVals.add(val);
            return true;
        }
        ++numRetained;
        retainedVals.add(val);
        return false;
    }

    @Override
    public boolean test(char val){
        ++numCalls;
        if(retainedVals.contains(val)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            ++numRemoved;
            return true;
        }
        if(testImpl(val)){
            ++numRemoved;
            removedVals.add(val);
            return true;
        }
        ++numRetained;
        retainedVals.add(val);
        return false;
    }

    @Override
    public boolean test(byte val){
        ++numCalls;
        if(retainedVals.contains(val)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            ++numRemoved;
            return true;
        }
        if(testImpl(val)){
            ++numRemoved;
            removedVals.add(val);
            return true;
        }
        ++numRetained;
        retainedVals.add(val);
        return false;
    }

    @Override
    public boolean test(boolean val){
        ++numCalls;
        if(retainedVals.contains(val)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            ++numRemoved;
            return true;
        }
        if(testImpl(val)){
            ++numRemoved;
            removedVals.add(val);
            return true;
        }
        ++numRetained;
        retainedVals.add(val);
        return false;
    }

    @Override
    public boolean test(Object t){
        ++numCalls;
        if(retainedVals.contains(t)){
            ++numRetained;
            return false;
        }
        if(removedVals.contains(t)){
            ++numRemoved;
            return true;
        }
        if(testImpl(t)){
            ++numRemoved;
            removedVals.add(t);
            return true;
        }
        ++numRetained;
        retainedVals.add(t);
        return false;
    }
    @Override
    public MonitoredRemoveIfPredicate negate(){
        // not worth implementing
        return null;
    }

}
