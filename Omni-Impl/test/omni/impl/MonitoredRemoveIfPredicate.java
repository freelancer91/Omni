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
    public int numExpectedCalls;
    MonitoredRemoveIfPredicate(int numExpectedCalls){
        this.numExpectedCalls=numExpectedCalls;
    }

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
        if(val instanceof Number) {
            if(val instanceof Long) {
                return testImpl(((Long)val).longValue());
            }else {
                return testImpl(((Number)val).doubleValue());
            }
        }else if(val instanceof Character) {
            return testImpl(((Character)val).charValue());
        }else if(val instanceof Boolean) {
            return testImpl(((Boolean)val).booleanValue());
        }else {
            return testImpl();
        }
        
    }
    @Override
    public boolean test(double value){
        ++numCalls;
        if(retainedVals.contains(value)){
            testImpl(value);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            testImpl(value);
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
            testImpl(val);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            testImpl(val);
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
            testImpl(value);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            testImpl(value);
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
            testImpl(value);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(value)){
            testImpl(value);
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
            testImpl(val);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            testImpl(val);
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
            testImpl(val);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            testImpl(val);
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
            testImpl(val);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            testImpl(val);
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
            testImpl(val);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(val)){
            testImpl(val);
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
            testImpl(t);
            ++numRetained;
            return false;
        }
        if(removedVals.contains(t)){
            testImpl(t);
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
    public void reset(MonitoredCollection<?> collection) {
        retainedVals.clear();
        removedVals.clear();
        numCalls=0;
        numRetained=0;
        numRemoved=0;
        if(collection.getDataType()==DataType.BOOLEAN) {
            var col=collection.getCollection();
            if(col.contains(true)) {
                if(col.contains(false)) {
                    numExpectedCalls=2;
                }else {
                    numExpectedCalls=1;
                }
            }else {
                if(col.contains(false)) {
                    numExpectedCalls=1;
                }else {
                    numExpectedCalls=0;
                }
            }
            
        }else {
            numExpectedCalls=collection.size();
        }
    }
    static int calculateNumExpetedCalls(MonitoredCollection<?> collection) {
        if(collection.getDataType()==DataType.BOOLEAN) {
            var col=collection.getCollection();
            if(col.contains(true)) {
                if(col.contains(false)) {
                    return 2;
                }else {
                    return 1;
                }
            }else {
                if(col.contains(false)) {
                    return 1;
                }else {
                    return 0;
                }
            }
            
        }else {
            return collection.size();
        }
    }
}
