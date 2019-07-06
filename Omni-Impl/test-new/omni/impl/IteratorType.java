package omni.impl;

import java.util.EnumSet;
public enum IteratorType{
    AscendingItr("AscendingItr"),DescendingItr("DescendingItr"),BidirectionalItr("BidirectionalItr"),
    SubAscendingItr("SubAscendingItr"),SubBidirectionalItr("SubBidirectionalItr");
    public final String name;
    public final EnumSet<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final EnumSet<IllegalModification> validPreMods;
    public final EnumSet<IteratorRemoveScenario> validItrRemoveScenarios;
    IteratorType(String name){
        this.name=name;
        this.validMonitoredFunctionGens=initValidMonitoredFunctionGens(this);
        this.validPreMods=initValidPreMods(this);
        this.validItrRemoveScenarios=initValidItrRemoveScenarios(this);
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid IteratorType " + this);
    }
    private static EnumSet<IteratorRemoveScenario> initValidItrRemoveScenarios(IteratorType itrType){
        switch(itrType.name){
        case "AscendingItr":
        case "DescendingItr":
        case "SubAscendingItr":
            return EnumSet.of(IteratorRemoveScenario.PostInit,IteratorRemoveScenario.PostNext,
                    IteratorRemoveScenario.PostRemove);
        case "BidirectionalItr":
        case "SubBidirectionalItr":
            return EnumSet.of(IteratorRemoveScenario.PostAdd,IteratorRemoveScenario.PostInit,
                    IteratorRemoveScenario.PostNext,IteratorRemoveScenario.PostPrev,IteratorRemoveScenario.PostRemove);
        }
        throw itrType.invalid();
    }
    private static EnumSet<IllegalModification> initValidPreMods(IteratorType itrType){
        switch(itrType.name){
        case "AscendingItr":
        case "BidirectionalItr":
        case "DescendingItr":
            return EnumSet.of(IllegalModification.ModCollection,IllegalModification.NoMod);
        case "SubAscendingItr":
        case "SubBidirectionalItr":
            return EnumSet.of(IllegalModification.ModCollection,IllegalModification.NoMod,IllegalModification.ModParent,
                    IllegalModification.ModRoot);
        }
        throw itrType.invalid();
    }
    private static EnumSet<MonitoredFunctionGen> initValidMonitoredFunctionGens(IteratorType itrType){
        switch(itrType.name){
        case "AscendingItr":
        case "BidirectionalItr":
        case "DescendingItr":
            return EnumSet.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.ModItr,
                    MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,MonitoredFunctionGen.ThrowIOBModCollection,
                    MonitoredFunctionGen.ThrowIOBModItr);
        case "SubAscendingItr":
        case "SubBidirectionalItr":
            return EnumSet.of(MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.ModItr,
                    MonitoredFunctionGen.NoThrow,
                    MonitoredFunctionGen.ThrowIOB,MonitoredFunctionGen.ThrowIOBModCollection,
                    MonitoredFunctionGen.ThrowIOBModItr,MonitoredFunctionGen.ModParent,MonitoredFunctionGen.ModRoot,
                    MonitoredFunctionGen.ThrowIOBModParent,MonitoredFunctionGen.ThrowIOBModRoot);
        }
        throw itrType.invalid();
    }
}
