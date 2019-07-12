package omni.impl.seq;

import omni.api.OmniCollection;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredSequence;

public enum SequenceInitialization{
    Ascending{
        @Override public <SEQ extends MonitoredSequence<?,?>> SEQ initialize(SEQ seq,int numToAdd,int initVal){
            var dataType=seq.getDataType();
            switch(dataType) {
            case BOOLEAN:{
                var collection=(OmniCollection.OfBoolean)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((initVal&1)!=0);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case BYTE:{
                var collection=(OmniCollection.OfByte)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((byte)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case CHAR:{
                var collection=(OmniCollection.OfChar)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((char)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case DOUBLE:{
                var collection=(OmniCollection.OfDouble)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((double)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case FLOAT:{
                var collection=(OmniCollection.OfFloat)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((float)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case INT:{
                var collection=(OmniCollection.OfInt)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add(initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case LONG:{
                var collection=(OmniCollection.OfLong)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((long)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case REF:{
                @SuppressWarnings("unchecked") var collection=(OmniCollection.OfRef<Object>)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add(initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            case SHORT:{
                var collection=(OmniCollection.OfShort)seq.getCollection();
                while(numToAdd!=0) {
                    collection.add((short)initVal);
                    --numToAdd;
                    ++initVal;
                }
                break;
            }
            default:
                throw dataType.invalid();
            }
            seq.updateCollectionState();
            return seq;
        }
        @Override
        public <SEQ extends MonitoredSequence<?,?>> SEQ initializeWithMonitoredObj(SEQ seq,int numToAdd,int initVal,
                MonitoredObjectGen objGen,MonitoredObjectGen.ThrowSwitch throwSwitch){
            @SuppressWarnings("unchecked")
            var collection=(OmniCollection.OfRef<Object>)seq.getCollection();
            throwSwitch.doThrow=false;
            while(numToAdd != 0){
                collection.add(objGen.getMonitoredObject(seq,initVal,throwSwitch));
                --numToAdd;
                ++initVal;
            }
            seq.updateCollectionState();
            throwSwitch.doThrow=true;
            return seq;
        }


    };

    public abstract <SEQ extends MonitoredSequence<?,?>> SEQ initialize(SEQ seq,int numToAdd,int initVal);
    public abstract <SEQ extends MonitoredSequence<?,?>> SEQ initializeWithMonitoredObj(SEQ seq,int numToAdd,int initVal,
            MonitoredObjectGen objGen,MonitoredObjectGen.ThrowSwitch throwSwitch);
}
