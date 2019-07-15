package omni.impl.seq;

import java.util.EnumSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.IllegalModification;
import omni.impl.StructType;

class SequenceInitParams{
    private static final EnumSet<IllegalModification> NOMOD=EnumSet.of(IllegalModification.NoMod);
    final StructType structType;
    final DataType collectionType;
    final CheckedType checkedType;
    final int[] preAllocs;
    final int[] postAllocs;
    final EnumSet<IllegalModification> validPreMods;
    public SequenceInitParams(StructType structType,DataType collectionType,CheckedType checkedType,int[] preAllocs,
        int[] postAllocs){
      super();
      this.structType=structType;
      this.collectionType=collectionType;
      this.checkedType=checkedType;
      this.preAllocs=preAllocs;
      this.postAllocs=postAllocs;
      this.validPreMods=checkedType.checked?structType.validPreMods:NOMOD;
    }
    
   
  }