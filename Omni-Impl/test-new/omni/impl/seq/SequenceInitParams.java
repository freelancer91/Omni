package omni.impl.seq;

import java.util.Arrays;
import java.util.EnumSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IllegalModification;
import omni.impl.StructType;

class SequenceInitParams{
//    private static final EnumSet<MonitoredRemoveIfPredicateGen> NOTHROWREMOVEIF=EnumSet.of(MonitoredRemoveIfPredicateGen.RemoveSpecificIndices,
//                    MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveTrue,
//                    MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveAll);
//    private static final EnumSet<MonitoredRemoveIfPredicateGen> THROWROOTREMOVEIF=EnumSet.of(MonitoredRemoveIfPredicateGen.RemoveSpecificIndices,MonitoredRemoveIfPredicateGen.ModCollection,
//            MonitoredRemoveIfPredicateGen.RemoveFalse,MonitoredRemoveIfPredicateGen.RemoveTrue,
//            MonitoredRemoveIfPredicateGen.RemoveNone,MonitoredRemoveIfPredicateGen.RemoveAll,
//            MonitoredRemoveIfPredicateGen.Throw,MonitoredRemoveIfPredicateGen.ThrowModCollection,
//            MonitoredRemoveIfPredicateGen.ModRoot,
//            MonitoredRemoveIfPredicateGen.ThrowModRoot);
//    private static final EnumSet<MonitoredComparatorGen> NOTHROWCOMPGEN=EnumSet.of(MonitoredComparatorGen.NoThrowAscending,MonitoredComparatorGen.NoThrowDescending,MonitoredComparatorGen.NullComparator);
//    private static final EnumSet<MonitoredComparatorGen> THROWROOTCOMPGEN=EnumSet.of(MonitoredComparatorGen.NoThrowAscending,MonitoredComparatorGen.NoThrowDescending,MonitoredComparatorGen.NullComparator,MonitoredComparatorGen.ModCollectionAscending,MonitoredComparatorGen.ModCollectionDescending,MonitoredComparatorGen.ModCollectionThrowAIOB,MonitoredComparatorGen.ModCollectionThrowIOB,MonitoredComparatorGen.ModRootAscending,MonitoredComparatorGen.ModRootDescending,MonitoredComparatorGen.ModRootThrowAIOB,MonitoredComparatorGen.ModRootThrowIOB,MonitoredComparatorGen.NullComparatorModCollection,MonitoredComparatorGen.NullComparatorModCollectionThrowAIOB,MonitoredComparatorGen.NullComparatorModCollectionThrowIOB,MonitoredComparatorGen.NullComparatorModRoot,MonitoredComparatorGen.NullComparatorModRootThrowAIOB,MonitoredComparatorGen.NullComparatorModRootThrowIOB,MonitoredComparatorGen.NullComparatorThrowAIOB,MonitoredComparatorGen.NullComparatorThrowIOB,MonitoredComparatorGen.ThrowAIOB,MonitoredComparatorGen.ThrowIOB);
//    private static final EnumSet<MonitoredFunctionGen> NOTHROWFUNCTIONGEN=EnumSet.of(MonitoredFunctionGen.NoThrow);
//    private static final EnumSet<MonitoredFunctionGen> THROWROOTFUNCTIONGEN=EnumSet.of(MonitoredFunctionGen.NoThrow,MonitoredFunctionGen.ModCollection,MonitoredFunctionGen.ModRoot,MonitoredFunctionGen.ThrowIOB,MonitoredFunctionGen.ThrowIOBModCollection,MonitoredFunctionGen.ThrowIOBModRoot);
//    private static final EnumSet<MonitoredObjectGen> NOTHROWOBJ=EnumSet.of(MonitoredObjectGen.NoThrow);
//    private static final EnumSet<MonitoredObjectGen> THROWROOTOBJ=EnumSet.of(MonitoredObjectGen.NoThrow,MonitoredObjectGen.ModCollection,MonitoredObjectGen.ModCollectionThrowAIOB,MonitoredObjectGen.ModCollectionThrowIOB,MonitoredObjectGen.ModRoot,MonitoredObjectGen.ModRootThrowAIOB,MonitoredObjectGen.ModRootThrowIOB,MonitoredObjectGen.ThrowAIOB,MonitoredObjectGen.ThrowIOB);
//    private static final EnumSet<IllegalModification> PREMODROOT=EnumSet.of(IllegalModification.NoMod,IllegalModification.ModRoot);

    private static final EnumSet<IllegalModification> NOPREMOD=EnumSet.of(IllegalModification.NoMod);
    final StructType structType;
    final DataType collectionType;
    final CheckedType checkedType;
    final int[] preAllocs;
    final int[] postAllocs;
    final int totalPreAlloc;
    final int totalPostAlloc;
//    final EnumSet<IllegalModification> validPreMods;
//    final EnumSet<MonitoredObjectGen> validMonitoredObjectGens;
//    final EnumSet<MonitoredFunctionGen> validMonitoredFunctionGens;
//    final EnumSet<MonitoredComparatorGen> validComparatorGens;
//    final EnumSet<MonitoredRemoveIfPredicateGen> validMonitoredRemoveIfPredicateGens;
    public SequenceInitParams(StructType structType,DataType collectionType,CheckedType checkedType,int[] preAllocs,
        int[] postAllocs){
      super();
      this.structType=structType;
      this.collectionType=collectionType;
      this.checkedType=checkedType;
      this.preAllocs=preAllocs;
      this.postAllocs=postAllocs;
//      this.validPreMods=checkedType.checked?structType.validPreMods:NOPREMOD;
//      
//      switch(structType) {
//      case DblLnkSubList:
//          if(preAllocs.length<2 && checkedType.checked) {
//              this.validPreMods=PREMODROOT;
////              this.validMonitoredObjectGens=THROWROOTOBJ;
////              this.validMonitoredFunctionGens=THROWROOTFUNCTIONGEN;
////              this.validComparatorGens=THROWROOTCOMPGEN;
////              this.validMonitoredRemoveIfPredicateGens=THROWROOTREMOVEIF;
//              break;
//          }
//          
//         
//      default:
//          if(checkedType.checked) {
//              this.validPreMods=structType.validPreMods;
////              this.validMonitoredObjectGens=structType.validMonitoredObjectGens;
////              this.validMonitoredFunctionGens=structType.validMonitoredFunctionGens;
////              this.validComparatorGens=structType.validComparatorGens;
////              this.validMonitoredRemoveIfPredicateGens=structType.validMonitoredRemoveIfPredicateGens;
//          }else {
//              this.validPreMods=NOPREMOD;
////              this.validMonitoredObjectGens=NOTHROWOBJ;
////              this.validMonitoredFunctionGens=NOTHROWFUNCTIONGEN;
////              this.validComparatorGens=NOTHROWCOMPGEN;
////              this.validMonitoredRemoveIfPredicateGens=NOTHROWREMOVEIF;
//          }
//      }
      int tmp=0;
      for(var preAlloc:preAllocs) {
          tmp+=preAlloc;
      }
      this.totalPreAlloc=tmp;
      tmp=0;
      for(var postAlloc:postAllocs) {
          tmp+=postAlloc;
      }
      this.totalPostAlloc=tmp;
    }
    @Override
    public int hashCode() {
        return (((structType.hashCode()*31+collectionType.hashCode())*31+checkedType.hashCode())*31+Arrays.hashCode(preAllocs))*31+Arrays.hashCode(postAllocs);
    }
    @Override
    public boolean equals(Object obj) {
        SequenceInitParams that;
        return obj==this || obj instanceof SequenceInitParams && structType==(that=(SequenceInitParams)obj).structType && collectionType==that.collectionType && checkedType==that.checkedType && Arrays.equals(preAllocs,that.preAllocs) && Arrays.equals(postAllocs,that.postAllocs);
    }
   
  }