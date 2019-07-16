package omni.impl.seq;

import java.util.Arrays;
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
    final int totalPreAlloc;
    final int totalPostAlloc;
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