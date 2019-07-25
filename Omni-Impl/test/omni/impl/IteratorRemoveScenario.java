package omni.impl;

import omni.impl.MonitoredCollection.MonitoredIterator;
import omni.impl.MonitoredList.MonitoredListIterator;
public enum IteratorRemoveScenario{
    PostInit(IllegalStateException.class){
        @Override
        public void initialize(MonitoredIterator<?,?> itrMonitor){
            // nothing to do
        }
    },
    PostNext(null){
        @Override
        public void initialize(MonitoredIterator<?,?> itrMonitor){
            if(!itrMonitor.nextWasJustCalled()){
                if(!itrMonitor.hasNext()){
                    if(!(itrMonitor instanceof MonitoredListIterator)){
                        throw super.cannotBeUsedOnDepletedIterator();
                    }
                    var cast=(MonitoredListIterator<?,?>)itrMonitor;
                    if(!cast.hasPrevious()){
                        var dataType=cast.getMonitoredCollection().getDataType();
                        cast.add(dataType.convertValUnchecked(0),dataType);
                    }
                    cast.iterateReverse();
                }
                itrMonitor.iterateForward();
            }
        }
    },
    PostPrev(null){
        @Override
        public void initialize(MonitoredIterator<?,?> itrMonitor){
            var cast=(MonitoredListIterator<?,?>)itrMonitor;
            if(!cast.previousWasJustCalled()){
                if(!cast.hasPrevious()){
                    if(!cast.hasNext()){
                        var dataType=cast.getMonitoredCollection().getDataType();
                        cast.add(dataType.convertValUnchecked(0),dataType);
                    }else{
                        cast.iterateForward();
                    }
                }
                cast.iterateReverse();
            }
        }
    },
    PostAdd(IllegalStateException.class){
        @Override
        public void initialize(MonitoredIterator<?,?> itrMonitor){
            var cast=(MonitoredListIterator<?,?>)itrMonitor;
            var dataType=itrMonitor.getMonitoredCollection().getDataType();
            cast.add(dataType.convertValUnchecked(0),dataType);
        }
    },
    PostRemove(IllegalStateException.class){
        @Override
        public void initialize(MonitoredIterator<?,?> itrMonitor){
            if(!itrMonitor.nextWasJustCalled()){
                if(!itrMonitor.hasNext()){
                    if(!(itrMonitor instanceof MonitoredListIterator)){
                        throw super.cannotBeUsedOnDepletedIterator();
                    }
                    var cast=(MonitoredListIterator<?,?>)itrMonitor;
                    if(!cast.hasPrevious()){
                        var dataType=cast.getMonitoredCollection().getDataType();
                        cast.add(dataType.convertValUnchecked(0),dataType);
                    }
                    cast.iterateReverse();
                }
                itrMonitor.iterateForward();
            }
            itrMonitor.remove();
        }
    };
    public final Class<? extends Throwable> expectedException;
    IteratorRemoveScenario(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
    }
    private final UnsupportedOperationException cannotBeUsedOnDepletedIterator(){
        return new UnsupportedOperationException(this + " cannot be used on a depleted iterator");
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid IteratorRemoveScenario " + this);
    }
    public abstract void initialize(MonitoredCollection.MonitoredIterator<?,?> itrMonitor);
}
