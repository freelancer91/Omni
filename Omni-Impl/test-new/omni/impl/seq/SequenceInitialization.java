package omni.impl.seq;

import java.util.function.IntConsumer;
import omni.api.OmniCollection;
import omni.impl.MonitoredObjectGen;
import omni.impl.MonitoredSequence;

enum SequenceInitialization{
    Ascending{
        @Override public <SEQ extends MonitoredSequence<?>> SEQ initialize(SEQ seq,int numToAdd,int initVal,int period){
            var dataType=seq.getDataType();
            IntConsumer valAdder;
            switch(dataType) {
            case BOOLEAN:{
                var collection=(OmniCollection.OfBoolean)seq.getCollection();
                valAdder=i->collection.add((i&1)!=0);
                break;
            }
            case BYTE:{
                var collection=(OmniCollection.OfByte)seq.getCollection();
                valAdder=i->collection.add((byte)i);
                break;
            }
            case CHAR:{
                var collection=(OmniCollection.OfChar)seq.getCollection();
                valAdder=i->collection.add((char)i);
                break;
            }
            case SHORT:{
                var collection=(OmniCollection.OfShort)seq.getCollection();
                valAdder=i->collection.add((short)i);
                break;
            }
            case DOUBLE:
            case FLOAT:
            case INT:
            case LONG:{
                var collection=(OmniCollection.IntInput<?>)seq.getCollection();
                valAdder=i->collection.add(i);
                break;
            }
            case REF:{
                @SuppressWarnings("unchecked") var collection=(OmniCollection.OfRef<Object>)seq.getCollection();
                valAdder=i->collection.add(i);
                
                break;
            }
            default:
                throw dataType.invalid();
            }
            addImpl(numToAdd,period,initVal,valAdder,seq);
            return seq;
        }
        private void addImpl(int numToAdd,int period,int initVal,IntConsumer valAdder,MonitoredSequence<?> seq) {
            for(;;) {
                for(int i=0;i<=period;++i) {
                    if(--numToAdd<0) {
                        seq.updateCollectionState();
                        return;
                    }
                    valAdder.accept(initVal);
                }
                ++initVal;
            }
        }
        
        
        @Override
        public <SEQ extends MonitoredSequence<?>> SEQ initializeWithMonitoredObj(SEQ seq,int numToAdd,int initVal,int period,
                MonitoredObjectGen objGen,MonitoredObjectGen.ThrowSwitch throwSwitch){
            @SuppressWarnings("unchecked")
            var collection=(OmniCollection.OfRef<Object>)seq.getCollection();
            throwSwitch.doThrow=false;
            addImpl(numToAdd,period,initVal,i->collection.add(objGen.getMonitoredObject(seq,i,throwSwitch)),seq);
            throwSwitch.doThrow=true;
            return seq;
        }
    };
    public abstract <SEQ extends MonitoredSequence<?>> SEQ initialize(SEQ seq,int numToAdd,int initVal,int period);
    public <SEQ extends MonitoredSequence<?>> SEQ initialize(SEQ seq,int numToAdd,int initVal) {
        return initialize(seq,numToAdd,initVal,0);
    }
    public <SEQ extends MonitoredSequence<?>> SEQ initializeWithMonitoredObj(SEQ seq,int numToAdd,int initVal,
            MonitoredObjectGen objGen,MonitoredObjectGen.ThrowSwitch throwSwitch) {
        return initializeWithMonitoredObj(seq,numToAdd,initVal,0,objGen,throwSwitch);
    }
    public abstract <SEQ extends MonitoredSequence<?>> SEQ initializeWithMonitoredObj(SEQ seq,int numToAdd,int initVal,int period,
            MonitoredObjectGen objGen,MonitoredObjectGen.ThrowSwitch throwSwitch);
}
