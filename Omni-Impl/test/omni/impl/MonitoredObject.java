package omni.impl;

public class MonitoredObject implements Comparable<Comparable<?>>{
    int numEqualsCalls;
    int numHashCodeCalls;
    int numToStringCalls;
    int numCompareCalls;
    int comparisonVal;
    MonitoredObject(int comparisonVal){
        this.comparisonVal=comparisonVal;
    }
    MonitoredObject(){
    }
    protected void throwingCall(){
    }
    @Override
    public int compareTo(Comparable<?> that){
        ++numCompareCalls;
        throwingCall();
        if(that instanceof MonitoredObject){
            return Integer.compare(this.comparisonVal,((MonitoredObject)that).comparisonVal);
        }
        if(that instanceof Long){
            return Long.compare(this.comparisonVal,((Long)that).longValue());
        }
        if(that instanceof Number){
            return Double.compare(this.comparisonVal,((Number)that).doubleValue());
        }
        if(that instanceof Character){
            return Integer.compare(this.comparisonVal,((Character)that).charValue());
        }
        if(that instanceof Boolean){
            return Integer.compare(this.comparisonVal,((Boolean)that).booleanValue()?1:0);
        }
        throw new UnsupportedOperationException("Cannot compare to " + that);
    }
    @Override
    public boolean equals(Object that){
        ++numEqualsCalls;
        throwingCall();
        if(that instanceof MonitoredObject){
            return this.comparisonVal == ((MonitoredObject)that).comparisonVal;
        }
        if(that instanceof Long){
            return this.comparisonVal == ((Long)that).longValue();
        }
        if(that instanceof Number){
            return this.comparisonVal == ((Number)that).doubleValue();
        }
        if(that instanceof Character){
            return this.comparisonVal == ((Character)that).charValue();
        }
        if(that instanceof Boolean){
            return this.comparisonVal == (((Boolean)that).booleanValue()?1:0);
        }
        return false;
    }
    @Override
    public int hashCode(){
        ++numHashCodeCalls;
        throwingCall();
        return this.comparisonVal;
    }
    @Override
    public String toString(){
        ++numToStringCalls;
        throwingCall();
        return "MonitoredObject{comparisonVal=" + comparisonVal + "; numEqualsCalls=" + numEqualsCalls
                + "; numHashCodeCalls=" + numHashCodeCalls + "; numCompareCalls=" + numCompareCalls
                + "; numToStringCalls=" + numToStringCalls + "}";
    }
}
