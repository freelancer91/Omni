package omni.impl;

import java.util.Set;
public enum IteratorType{
    AscendingItr,DescendingItr,BidirectionalItr,SubAscendingItr,SubBidirectionalItr;
    public final Set<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final Set<IllegalModification> validPreMods;
    public final Set<IteratorRemoveScenario> validItrRemoveScenarios;
    IteratorType(){
        this.validMonitoredFunctionGens=initValidMonitoredFunctionGens(this);
        this.validPreMods=initValidPreMods(this);
        this.validItrRemoveScenarios=initValidItrRemoveScenarios(this);
    }
    private static Set<IteratorRemoveScenario> initValidItrRemoveScenarios(IteratorType itrType){
        switch(itrType){
        case AscendingItr:
        case DescendingItr:
        case SubAscendingItr:
            return Set.of(IteratorRemoveScenario.PostInit,IteratorRemoveScenario.PostNext,
                    IteratorRemoveScenario.PostRemove);
        case BidirectionalItr:
        case SubBidirectionalItr:
            return Set.of(IteratorRemoveScenario.PostAdd,IteratorRemoveScenario.PostInit,
                    IteratorRemoveScenario.PostNext,IteratorRemoveScenario.PostPrev,IteratorRemoveScenario.PostRemove);
        }
        throw new UnsupportedOperationException("Unknown itrType " + itrType);
    }
    private static Set<IllegalModification> initValidPreMods(IteratorType itrType){
        switch(itrType){
        case AscendingItr:
        case BidirectionalItr:
        case DescendingItr:
            return Set.of(IllegalModification.ModCollection,IllegalModification.NoMod);
        case SubAscendingItr:
        case SubBidirectionalItr:
            return Set.of(IllegalModification.ModCollection,IllegalModification.NoMod,IllegalModification.ModParent,
                    IllegalModification.ModRoot);
        }
        throw new UnsupportedOperationException("Unknown itrType " + itrType);
    }
    private static Set<MonitoredFunctionGen> initValidMonitoredFunctionGens(IteratorType itrType){
        switch(itrType){
        case AscendingItr:
        case BidirectionalItr:
        case DescendingItr:
            return Set.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.ModItr,MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,MonitoredFunctionGen.ThrowIOBModCollection,
                    MonitoredFunctionGen.ThrowIOBModItr);
        case SubAscendingItr:
        case SubBidirectionalItr:
            return Set.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.ModItr,MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,MonitoredFunctionGen.ThrowIOBModCollection,
                    MonitoredFunctionGen.ThrowIOBModItr,MonitoredFunctionGen.ModParent,MonitoredFunctionGen.ModRoot,
                    MonitoredFunctionGen.ThrowIOBModParent,MonitoredFunctionGen.ThrowIOBModRoot);
        }
        throw new UnsupportedOperationException("Unknown itrType " + itrType);
    }
}
