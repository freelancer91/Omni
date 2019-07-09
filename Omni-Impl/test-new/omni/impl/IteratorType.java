package omni.impl;

import java.util.EnumSet;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
public enum IteratorType{
    AscendingItr("AscendingItr",IterationDirection.Ascending,OmniIterator.class),
    DescendingItr("DescendingItr",IterationDirection.Descending,OmniIterator.class),
    BidirectionalItr("BidirectionalItr",IterationDirection.Ascending,OmniListIterator.class),
    SubAscendingItr("SubAscendingItr",IterationDirection.Ascending,OmniIterator.class),
    SubBidirectionalItr("SubBidirectionalItr",IterationDirection.Ascending,OmniListIterator.class);
    public final String name;
    public final EnumSet<MonitoredFunctionGen> validMonitoredFunctionGens;
    public final EnumSet<IllegalModification> validPreMods;
    public final EnumSet<IteratorRemoveScenario> validItrRemoveScenarios;
    public final EnumSet<IterationDirection> validItrDirections;
    public final IterationDirection naturalItrDirection;
    @SuppressWarnings("rawtypes")
    public final Class<? extends OmniIterator> iteratorInterface;
    IteratorType(String name,IterationDirection naturalItrDirection,
            @SuppressWarnings("rawtypes") Class<? extends OmniIterator> iteratorInterface){
        this.name=name;
        this.validMonitoredFunctionGens=initValidMonitoredFunctionGens(this);
        this.validPreMods=initValidPreMods(this);
        this.validItrRemoveScenarios=initValidItrRemoveScenarios(this);
        this.validItrDirections=initValidItrDirections(this);
        this.naturalItrDirection=naturalItrDirection;
        this.iteratorInterface=iteratorInterface;
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid IteratorType " + this);
    }
    private static EnumSet<IterationDirection> initValidItrDirections(IteratorType itrType){
        switch(itrType.name){
        case "AscendingItr":
        case "SubAscendingItr":
        case "DescendingItr":
            return EnumSet.of(IterationDirection.Ascending);
        case "BidirectionalItr":
        case "SubBidirectionalItr":
            return EnumSet.of(IterationDirection.Ascending,IterationDirection.Descending);
        }
        throw itrType.invalid();
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
    public static enum IterationDirection{
        Ascending,Descending
    }
}
