package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.TypeUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import org.junit.jupiter.api.Assertions;
import omni.impl.RefInputTestArgType;
import omni.impl.RefOutputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.function.UnaryOperator;
import omni.impl.QueryCastType;
import java.util.Comparator;
@SuppressWarnings({"rawtypes","unchecked"})
class RefArrSeqMonitor implements RefSeqMonitor{
  static final int DEFAULT_PRE_AND_POST_ALLOC=5;
  static void verifyRangeIsNull(Object[] arr,int offset,int bound){
    for(int i=offset;i<bound;++i){Assertions.assertNull(arr[i]);}
  }
  static enum NestedType{
    LIST(true,true),
    STACK(true,false),
    SUBLIST(false,true);
    final boolean rootType;
    final boolean forwardIteration;
    NestedType(boolean rootType,boolean forwardIteration){
      this.rootType=rootType;
      this.forwardIteration=forwardIteration;
    }
  }
  static enum QueryTester
  {
    ObjectNonNull(true){
      @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override void addEqualsVal(RefArrSeqMonitor seqMonitor){throw new UnsupportedOperationException();}
      @Override void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
        seqMonitor.seq.add(new Object());
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        ++seqMonitor.expectedSeqSize;
        ++seqMonitor.expectedParentSize;
        ++seqMonitor.expectedRootSize;
      }
      @Override boolean invokecontainsMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        return seqMonitor.seq.contains(monitoredObject);
      }
      @Override boolean invokeremoveValMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        boolean ret=seqMonitor.seq.remove(monitoredObject);
        if(ret){
          ++seqMonitor.expectedSeqModCount;
          ++seqMonitor.expectedParentModCount;
          ++seqMonitor.expectedRootModCount;
          --seqMonitor.expectedSeqSize;
          --seqMonitor.expectedParentSize;
          --seqMonitor.expectedRootSize;
          Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
        }
        return ret;
      }
      @Override int invokeindexOfMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        return ((OmniList.OfRef)seqMonitor.seq).indexOf(monitoredObject);
      }
      @Override int invokelastIndexOfMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf(monitoredObject);
      }
      @Override int invokesearchMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        return ((OmniStack.OfRef)seqMonitor.seq).search(monitoredObject);
      }
      @Override int initContainsEnd(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0;i<99;++i){
          seqMonitor.seq.add(new Object());
        }
        seqMonitor.seq.add(monitoredObject);
        seqMonitor.expectedSeqModCount+=100;
        seqMonitor.expectedParentModCount+=100;
        seqMonitor.expectedRootModCount+=100;
        seqMonitor.expectedSeqSize+=100;
        seqMonitor.expectedParentSize+=100;
        seqMonitor.expectedRootSize+=100;
        return seqMonitor.expectedSeqSize-1;
      }
      @Override int initContainsMiddle(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        for(int i=0;i<50;++i){
          seqMonitor.seq.add(new Object());
        }
        seqMonitor.seq.add(monitoredObject);
        for(int i=51;i<100;++i){
          seqMonitor.seq.add(new Object());
        }
        seqMonitor.expectedSeqModCount+=100;
        seqMonitor.expectedParentModCount+=100;
        seqMonitor.expectedRootModCount+=100;
        seqMonitor.expectedSeqSize+=100;
        seqMonitor.expectedParentSize+=100;
        seqMonitor.expectedRootSize+=100;
        return seqMonitor.expectedSeqSize/2;
      }
      @Override int initContainsBeginning(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){
        Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
        seqMonitor.seq.add(monitoredObject);
        for(int i=1;i<100;++i){
          seqMonitor.seq.add(new Object());
        }
        seqMonitor.expectedSeqModCount+=100;
        seqMonitor.expectedParentModCount+=100;
        seqMonitor.expectedRootModCount+=100;
        seqMonitor.expectedSeqSize+=100;
        seqMonitor.expectedParentSize+=100;
        seqMonitor.expectedRootSize+=100;
        return 0;
      }
    },
  Booleannull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Boolean)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Boolean)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Boolean)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Boolean)(Boolean)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Boolean)(Boolean)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Boolean)(Boolean)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Boolean)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Boolean)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Boolean)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Boolean)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytenull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Byte)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Byte)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Byte)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(Byte)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(Byte)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(Byte)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Byte)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characternull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Character)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Character)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Character)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(Character)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(Character)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(Character)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Character)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortnull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Short)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Short)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Short)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(Short)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(Short)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(Short)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Short)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integernull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Integer)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Integer)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Integer)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(Integer)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(Integer)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(Integer)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Integer)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longnull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Long)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Long)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Long)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(Long)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(Long)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(Long)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Long)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatnull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Float)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Float)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Float)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(Float)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(Float)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(Float)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Float)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublenull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Double)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Double)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Double)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(Double)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(Double)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(Double)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Double)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Objectnull(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(null));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(null));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(null));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(Object)(null));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Booleanfalse(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(boolean)(false));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(boolean)(false));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(boolean)(false));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Boolean)(boolean)(false));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(false));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Boolean)(boolean)(false));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((boolean)(false));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((boolean)(false));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((boolean)(false));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)(false));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Booleantrue(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(boolean)(true));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(boolean)(true));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(boolean)(true));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Boolean)(boolean)(true));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(true));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Boolean)(boolean)(true));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((boolean)(true));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((boolean)(true));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((boolean)(true));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Byte0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(byte)(0));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(byte)(0));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(byte)(0));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(byte)(0));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(byte)(0));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(byte)(0));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((byte)(0));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((byte)(0));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((byte)(0));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytepos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(byte)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(byte)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(byte)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(byte)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(byte)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(byte)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((byte)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((byte)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((byte)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytepos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(byte)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(byte)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(byte)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(byte)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(byte)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(byte)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((byte)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((byte)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((byte)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Byteneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(byte)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(byte)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(byte)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Byte)(byte)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Byte)(byte)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Byte)(byte)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((byte)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((byte)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((byte)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Character0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(char)(0));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(char)(0));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(char)(0));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(char)(0));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(char)(0));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(char)(0));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((char)(0));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((char)(0));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((char)(0));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characterpos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(char)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(char)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(char)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(char)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(char)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(char)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((char)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((char)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((char)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characterpos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(char)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(char)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(char)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(char)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(char)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(char)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((char)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((char)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((char)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  CharacterMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(char)(((char)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  CharacterMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(char)(((char)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Short0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(0));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(0));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(0));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(0));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(0));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(0));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(0));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(0));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(0));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortpos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortpos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  ShortMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(((short)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  ShortMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(short)(((short)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integer0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(0));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(0));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(0));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(0));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(0));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(0));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(0));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(0));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(0));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerpos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerpos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(((int)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(((int)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(((int)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(((int)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(((int)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(TypeUtil.MAX_SAFE_INT+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(int)(TypeUtil.MIN_SAFE_INT-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Long0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(0));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(0));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(0));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(0));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(0));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(0));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(0));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(0));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(0));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longpos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longpos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SAFE_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SAFE_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(0.0F));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(0.0F));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(0.0F));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(0.0F));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(0.0F));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(0.0F));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(0.0F));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(0.0F));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(0.0F));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatneg0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(-0.0F));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(-0.0F));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(-0.0F));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(-0.0F));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(-0.0F));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(-0.0F));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(-0.0F));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(-0.0F));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(-0.0F));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(-0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(((float)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatNaN(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(float)(Float.NaN));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(float)(Float.NaN));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(float)(Float.NaN));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Float)(float)(Float.NaN));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Float)(float)(Float.NaN));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Float)(float)(Float.NaN));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((float)(Float.NaN));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((float)(Float.NaN));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((float)(Float.NaN));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(float)(Float.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(0.0D));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(0.0D));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(0.0D));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(0.0D));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(0.0D));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(0.0D));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(0.0D));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(0.0D));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(0.0D));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doubleneg0(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(-0.0D));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(-0.0D));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(-0.0D));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(-0.0D));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(-0.0D));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(-0.0D));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(-0.0D));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(-0.0D));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(-0.0D));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(-0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos2(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(2));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(2));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(2));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(2));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(2));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(2));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(2));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(2));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(2));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doubleneg1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(((double)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(Double.MIN_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(Double.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(Double.MAX_VALUE));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(Double.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleNaN(false){
    @Override boolean invokecontainsObject(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
    @Override boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Object)(double)(Double.NaN));}
    @Override int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Object)(double)(Double.NaN));}
    @Override int invokesearchObject(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Object)(double)(Double.NaN));}
    @Override boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
    @Override boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((Double)(double)(Double.NaN));}
    @Override int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((Double)(double)(Double.NaN));}
    @Override int invokesearchBoxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((Double)(double)(Double.NaN));}
    @Override boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
    @Override boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
        Assertions.assertNull(((RefArrSeq)seqMonitor.root).arr[seqMonitor.rootPreAlloc+seqMonitor.parentPreAlloc+seqMonitor.expectedRootSize+seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc]);
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).indexOf((double)(Double.NaN));}
    @Override int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniList.OfRef)seqMonitor.seq).lastIndexOf((double)(Double.NaN));}
    @Override int invokesearchUnboxed(RefArrSeqMonitor seqMonitor){return ((OmniStack.OfRef)seqMonitor.seq).search((double)(Double.NaN));}
    void addEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(double)(Double.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(RefArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((Object)(boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  ;
    final boolean isObjectNonNull;
    QueryTester(boolean isObjectNonNull){
      this.isObjectNonNull=isObjectNonNull;
    }
    boolean invokecontains(RefArrSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokecontainsUnboxed(seqMonitor);
        case ToBoxed:
          return invokecontainsBoxed(seqMonitor);
        case ToObject:
          return invokecontainsObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    boolean invokeremoveVal(RefArrSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokeremoveValUnboxed(seqMonitor);
        case ToBoxed:
          return invokeremoveValBoxed(seqMonitor);
        case ToObject:
          return invokeremoveValObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    int invokeindexOf(RefArrSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokeindexOfUnboxed(seqMonitor);
        case ToBoxed:
          return invokeindexOfBoxed(seqMonitor);
        case ToObject:
          return invokeindexOfObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    int invokelastIndexOf(RefArrSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokelastIndexOfUnboxed(seqMonitor);
        case ToBoxed:
          return invokelastIndexOfBoxed(seqMonitor);
        case ToObject:
          return invokelastIndexOfObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    int invokesearch(RefArrSeqMonitor seqMonitor,QueryCastType queryCastType){
      switch(queryCastType){
        case Unboxed:
          return invokesearchUnboxed(seqMonitor);
        case ToBoxed:
          return invokesearchBoxed(seqMonitor);
        case ToObject:
          return invokesearchObject(seqMonitor);
        default:
          throw new Error("Unknown queryCastType "+queryCastType);
      }
    }
    abstract boolean invokecontainsObject(RefArrSeqMonitor seqMonitor);
    abstract boolean invokecontainsBoxed(RefArrSeqMonitor seqMonitor);
    abstract boolean invokecontainsUnboxed(RefArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValObject(RefArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValBoxed(RefArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValUnboxed(RefArrSeqMonitor seqMonitor);
    abstract int invokeindexOfObject(RefArrSeqMonitor seqMonitor);
    abstract int invokeindexOfBoxed(RefArrSeqMonitor seqMonitor);
    abstract int invokeindexOfUnboxed(RefArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfObject(RefArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfBoxed(RefArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfUnboxed(RefArrSeqMonitor seqMonitor);
    abstract int invokesearchObject(RefArrSeqMonitor seqMonitor);
    abstract int invokesearchBoxed(RefArrSeqMonitor seqMonitor);
    abstract int invokesearchUnboxed(RefArrSeqMonitor seqMonitor);
    abstract void addEqualsVal(RefArrSeqMonitor seqMonitor);
    abstract void addNotEqualsVal(RefArrSeqMonitor seqMonitor);
    void initDoesNotContain(RefArrSeqMonitor seqMonitor){
      for(int i=0;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
    }
    int initContainsEnd(RefArrSeqMonitor seqMonitor){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0;i<99;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      return seqMonitor.expectedSeqSize-1;
    }
    int initContainsMiddle(RefArrSeqMonitor seqMonitor){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0;i<50;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      for(int i=51;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
      return seqMonitor.expectedSeqSize/2;
    }
    int initContainsBeginning(RefArrSeqMonitor seqMonitor){
      addEqualsVal(seqMonitor);
      for(int i=1;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
      return 0;
    }
    boolean invokecontainsMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    boolean invokeremoveValMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int invokeindexOfMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int invokelastIndexOfMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int invokesearchMonitored(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int initContainsEnd(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int initContainsMiddle(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
    int initContainsBeginning(RefArrSeqMonitor seqMonitor,MonitoredObject monitoredObject){throw new UnsupportedOperationException();}
  };
  private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,Object[] arr){
    for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToObject(v);
    }
    for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToObject(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToObject(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToObject(v);
    }
  }
  final NestedType nestedType;
  final CheckedType checkedType;
  final int initialCapacity;
  final int rootPreAlloc;
  final int parentPreAlloc;
  final int parentPostAlloc;
  final int rootPostAlloc;
  final RefArrSeq root;
  final OmniCollection.OfRef parent;
  final OmniCollection.OfRef seq;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  RefArrSeqMonitor(NestedType nestedType,CheckedType checkedType,int seqLength,Object[] arr){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.expectedRootSize=seqLength;
    this.expectedParentSize=seqLength;
    this.expectedSeqSize=seqLength;
    this.initialCapacity=(arr==null)?0:(arr==OmniArray.OfRef.DEFAULT_ARR?OmniArray.DEFAULT_ARR_SEQ_CAP:arr.length);
    switch(nestedType){
      case SUBLIST:
        seqLength+=(4*DEFAULT_PRE_AND_POST_ALLOC);
      case LIST:
        this.root=checkedType.checked?new RefArrSeq.CheckedList(seqLength,arr):new RefArrSeq.UncheckedList(seqLength,arr);
        break;
      case STACK:
        this.root=checkedType.checked?new RefArrSeq.CheckedStack(seqLength,arr):new RefArrSeq.UncheckedStack(seqLength,arr);
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
    if(nestedType.rootType){
      this.rootPreAlloc=0;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.rootPostAlloc=0;
      this.parent=root;
      this.seq=root;
    }else{
      Assertions.assertTrue(arr!=null && seqLength<=arr.length);
      this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parent=((OmniList.OfRef)root).subList(rootPreAlloc,seqLength-rootPostAlloc);
      this.seq=((OmniList.OfRef)parent).subList(parentPreAlloc,seqLength-rootPreAlloc-parentPostAlloc-rootPostAlloc);
    }
  }
  RefArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
    if(nestedType==NestedType.SUBLIST){
      this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      int rootSize;
      Object[] arr=new Object[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
      initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
      this.root=checkedType.checked
        ?new RefArrSeq.CheckedList(rootSize,arr)
        :new RefArrSeq.UncheckedList(rootSize,arr);
      this.parent=((OmniList.OfRef)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
      this.seq=((OmniList.OfRef)parent).subList(parentPreAlloc,parentPreAlloc);
    }else{
      this.rootPreAlloc=0;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.rootPostAlloc=0;
      switch(nestedType){
        default:
          throw new Error("Unknown nestedType "+nestedType);
        case STACK:
          this.root=checkedType.checked
            ?new RefArrSeq.CheckedStack()
            :new RefArrSeq.UncheckedStack();
          break;
        case LIST:
          this.root=checkedType.checked
            ?new RefArrSeq.CheckedList()
            :new RefArrSeq.UncheckedList();
      }
      this.parent=root;
      this.seq=root;
    }
  }
  RefArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
    this(nestedType,checkedType,initialCapacity,0,0,0,0);
  }
  RefArrSeqMonitor(final CheckedType checkedType,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this(NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
  }
  RefArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=initialCapacity;
    this.rootPreAlloc=rootPreAlloc;
    this.parentPreAlloc=parentPreAlloc;
    this.parentPostAlloc=parentPostAlloc;
    this.rootPostAlloc=rootPostAlloc;
    int rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
    Object[] arr;
    if(rootSize==0){
      switch(initialCapacity){
        case 0:
          arr=null;
          break;
        case OmniArray.DEFAULT_ARR_SEQ_CAP:
          arr=OmniArray.OfRef.DEFAULT_ARR;
          break;
        default:
          arr=new Object[initialCapacity];
      }
    }else{
      arr=new Object[Math.max(initialCapacity,rootSize)];
    }
    initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
    this.root=nestedType==NestedType.STACK
      ?checkedType.checked
        ?new RefArrSeq.CheckedStack(rootSize,arr)
        :new RefArrSeq.UncheckedStack(rootSize,arr)
      :checkedType.checked
        ?new RefArrSeq.CheckedList(rootSize,arr)
        :new RefArrSeq.UncheckedList(rootSize,arr);
    switch(nestedType){
      case SUBLIST:
        this.parent=((OmniList.OfRef)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfRef)parent).subList(parentPreAlloc,parentPreAlloc);
        break;
      case LIST:
      case STACK:
        this.parent=root;
        this.seq=root;
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
  }
  class UncheckedArrSeqItrMonitor implements ItrMonitor{
    final OmniIterator.OfRef itr;
    int expectedCursor;
    int expectedLastRet;
    private UncheckedArrSeqItrMonitor(OmniIterator.OfRef itr,int expectedCursor){
      this.itr=itr;
      this.expectedCursor=expectedCursor;
      this.expectedLastRet=-1;
    }
    public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
      int expectedBound=nestedType.forwardIteration?expectedSeqSize:0;
      {
        itr.forEachRemaining((Consumer)action);
      }
      if(nestedType.forwardIteration){
        if(expectedCursor<expectedBound){
          expectedCursor=expectedBound;
          expectedLastRet=expectedCursor-1;
        }
      }else{
        if(expectedCursor>expectedBound){
          expectedCursor=expectedBound;
          expectedLastRet=expectedCursor;
        }
      }
    }
    public void iterateReverse(){
      ((OmniListIterator.OfRef)itr).previous();
      expectedLastRet=--expectedCursor;
    }
    public RefSeqMonitor getSeqMonitor(){
      return RefArrSeqMonitor.this;
    }
    public void set(int v,RefInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfRef)itr,v);
    }
    public boolean hasNext(){
      return itr.hasNext();
    }
    public boolean hasPrevious(){
      return ((OmniListIterator.OfRef)itr).hasPrevious();
    }
    public int nextIndex(){
      return ((OmniListIterator.OfRef)itr).nextIndex();
    }
    public int previousIndex(){
      return ((OmniListIterator.OfRef)itr).previousIndex();
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case STACK:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case SUBLIST:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.Itr.parent(itr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,RefInputTestArgType inputArgType){
      inputArgType.callListItrAdd((OmniListIterator.OfRef)itr,v);
      ++expectedCursor;
      ++expectedRootModCount;
      ++expectedParentModCount;
      ++expectedSeqModCount;
      ++expectedRootSize;
      ++expectedParentSize;
      ++expectedSeqSize;
      expectedLastRet=-1;
    }
    @Override public void iterateForward(){
      itr.next();
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyNext(int expectedVal,RefOutputTestArgType outputType){
      outputType.verifyItrNext(itr,expectedVal);
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyPrevious(int expectedVal,RefOutputTestArgType outputType){
      outputType.verifyItrPrevious(itr,expectedVal);
      expectedLastRet=--expectedCursor;
    }
    @Override public void remove(){
      itr.remove();
      --expectedRootSize;
      ++expectedRootModCount;
      --expectedParentSize;
      ++expectedParentModCount;
      --expectedSeqSize;
      ++expectedSeqModCount;
      expectedCursor=expectedLastRet;
      expectedLastRet=-1;
      Assertions.assertNull(root.arr[rootPreAlloc+parentPreAlloc+expectedRootSize+parentPostAlloc+rootPostAlloc]);
    }
  }
  class CheckedArrSeqItrMonitor extends UncheckedArrSeqItrMonitor{
    int expectedItrModCount;
    private CheckedArrSeqItrMonitor(OmniIterator.OfRef itr,int expectedCursor){
      super(itr,expectedCursor);
      this.expectedItrModCount=expectedRootModCount;
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.RefArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case STACK:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.RefArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case SUBLIST:
          actualCursor=FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.RefArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,RefInputTestArgType inputArgType){
      super.add(v,inputArgType);
      ++expectedItrModCount;
    }
    @Override public void remove(){
      super.remove();
      ++expectedItrModCount;
    }
  }
  public UncheckedArrSeqItrMonitor getItrMonitor(){
    var itr=seq.iterator();
    int expectedCursor=nestedType==NestedType.STACK?root.size:0;
    return checkedType.checked
      ?new CheckedArrSeqItrMonitor(itr,expectedCursor)
      :new UncheckedArrSeqItrMonitor(itr,expectedCursor);
  }
  public UncheckedArrSeqItrMonitor getListItrMonitor(){
    var itr=((OmniList.OfRef)seq).listIterator();
    return checkedType.checked
      ?new CheckedArrSeqItrMonitor(itr,0)
      :new UncheckedArrSeqItrMonitor(itr,0);
  }
  public UncheckedArrSeqItrMonitor getListItrMonitor(int index){
    var itr=((OmniList.OfRef)seq).listIterator(index);
    return checkedType.checked
      ?new CheckedArrSeqItrMonitor(itr,index)
      :new UncheckedArrSeqItrMonitor(itr,index);
  }
  private static class ArrSeqSequenceVerificationItr extends SequenceVerificationItr{
    final Object[] arr;
    int offset;
    final RefArrSeqMonitor seqMonitor;
    private ArrSeqSequenceVerificationItr(RefArrSeqMonitor seqMonitor,int offset,Object[] arr){
      this.seqMonitor=seqMonitor;
      this.arr=arr;
      this.offset=offset;
    }
    @Override public SequenceVerificationItr verifyPostAlloc(int expectedVal){
      for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
        verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,expectedVal);
      }
      return this;
    }
    @Override public void verifyLiteralIndexAndIterate(Object val){
      Assertions.assertSame(val,arr[offset++]);
    }
    @Override public void verifyIndexAndIterate(MonitoredObject monitoredObject){
      Object v;
      if((v=arr[offset++]) instanceof MonitoredObject){
        Assertions.assertEquals(monitoredObject.compareVal,((MonitoredObject)v).compareVal);
      }else{
        Assertions.assertEquals(monitoredObject.compareVal,(Object)v);
      }
    }
    @Override public void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val){
      inputArgType.verifyVal(val,arr[offset++]);
    }
    @Override public SequenceVerificationItr getPositiveOffset(int i){
      if(i<0){
        throw new Error("offset cannot be negative: "+i);
      }
      return new ArrSeqSequenceVerificationItr(seqMonitor,i+offset,arr);
    }
    @Override public SequenceVerificationItr skip(int i){
      if(i<0){
        throw new Error("offset cannot be negative: "+i);
      }
      this.offset+=i;
      return this;
    }
    @Override public boolean equals(Object val){
      final ArrSeqSequenceVerificationItr that;
      return val==this || (val instanceof ArrSeqSequenceVerificationItr && (that=(ArrSeqSequenceVerificationItr)val).arr==this.arr && that.offset==this.offset);
    }
    @Override public SequenceVerificationItr verifyRootPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc;i<seqMonitor.rootPostAlloc;++i,++v){
        verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    @Override public SequenceVerificationItr verifyParentPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
        verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
  }
  public int getExpectedSeqSize(){
    return this.expectedSeqSize;
  }
  public SequenceVerificationItr verifyPreAlloc(int expectedVal){
    var arr=root.arr;
    int offset=0;
    for(int bound=offset+rootPreAlloc+parentPreAlloc;offset<bound;++offset){
      RefInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,arr[offset]);
    }
    return new ArrSeqSequenceVerificationItr(this,offset,arr);
  }
  public SequenceVerificationItr verifyPreAlloc(){
    var arr=root.arr;
    int offset=0;
    for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
      RefInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
    }
    return new ArrSeqSequenceVerificationItr(this,offset,arr);
  }
  public void illegalAdd(PreModScenario preModScenario){
    switch(preModScenario){
      case ModSeq:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case ModParent:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        break;
      case ModRoot:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public boolean add(Object obj){
    boolean ret=seq.add(obj);
    if(ret){
      ++expectedSeqSize;
      ++expectedParentSize;
      ++expectedRootSize;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
    return ret;
  }
  public void add(int index,int val,RefInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public boolean add(int val,RefInputTestArgType inputArgType){
    boolean ret=inputArgType.callCollectionAdd(seq,val);
    if(ret){
      ++expectedRootSize;
      ++expectedParentSize;
      ++expectedSeqSize;
      ++expectedRootModCount;
      ++expectedParentModCount;
      ++expectedSeqModCount;
    }
    return ret;
  }
  public void push(int val,RefInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void put(int index,int val,RefInputTestArgType inputArgType){
    inputArgType.callListPut(seq,index,val);
  }
  public String toString(){
    var builder=new StringBuilder("RefArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
    switch(nestedType){
      case STACK:
        builder.append("Stack{").append(initialCapacity);
        break;
      case LIST:
        builder.append("List{").append(initialCapacity);
        break;
      case SUBLIST:
        builder.append("SubList{").append(rootPreAlloc).append(',').append(parentPreAlloc).append(',').append(parentPostAlloc).append(',').append(rootPostAlloc);
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
    return builder.append('}').toString();
  }
  public void verifyStructuralIntegrity(){
      switch(nestedType){
        case STACK:
          if(checkedType.checked){
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.RefArrSeq.CheckedStack.modCount(root));
          }
          break;
        case LIST:
          if(checkedType.checked){
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.RefArrSeq.CheckedList.modCount(root));
          }
          break;
        case SUBLIST:
          OmniList.OfRef actualSeqParent;
          RefArrSeq actualSeqRoot;
          int actualSeqSize;
          OmniList.OfRef actualParentParent;
          RefArrSeq  actualParentRoot;
          int actualParentSize;
          if(checkedType.checked){
            actualSeqParent=(OmniList.OfRef)FieldAndMethodAccessor.RefArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=(RefArrSeq)FieldAndMethodAccessor.RefArrSeq.CheckedSubList.root(seq);
            actualSeqSize=FieldAndMethodAccessor.RefArrSeq.CheckedSubList.size(seq);
            actualParentParent=(OmniList.OfRef)FieldAndMethodAccessor.RefArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=(RefArrSeq)FieldAndMethodAccessor.RefArrSeq.CheckedSubList.root(parent);
            actualParentSize=FieldAndMethodAccessor.RefArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.RefArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.RefArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=(OmniList.OfRef)FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=(RefArrSeq)FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.root(seq);
            actualSeqSize=FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.size(seq);
            actualParentParent=(OmniList.OfRef)FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=(RefArrSeq)FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.root(parent);
            actualParentSize=FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.RefArrSeq.size(root));
  }
  public boolean isEmpty(){
    return seq.isEmpty();
  }
  public void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    {
      seq.forEach((Consumer)action);
    }
  }
  public void unstableSort(MonitoredComparator sorter){
    int seqSize=expectedSeqSize;
    ((OmniList.OfRef)seq).unstableSort((Comparator)sorter);
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    {
      ((OmniList.OfRef)seq).replaceAll((UnaryOperator)operator);
    }
    if(seqSize!=0){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    {
      ((OmniList.OfRef)seq).sort((Comparator)sorter);
    }
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableAscendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfRef)seq).stableAscendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableDescendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfRef)seq).stableDescendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void unstableAscendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfRef)seq).unstableAscendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void unstableDescendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfRef)seq).unstableDescendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void removeAt(int expectedVal,RefOutputTestArgType outputType,int index){
    outputType.verifyListRemoveAt(seq,index,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
    Assertions.assertNull(root.arr[rootPreAlloc+parentPreAlloc+expectedRootSize+parentPostAlloc+rootPostAlloc]);
  }
  public void get(int expectedVal,RefOutputTestArgType outputType,int index){
    outputType.verifyListGet(seq,index,expectedVal);
  }
  public void clear(){
    int seqSize=expectedSeqSize;
    seq.clear();
    if(seqSize!=0){
      expectedSeqSize=0;
      expectedParentSize=0;
      expectedRootSize=0;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      int newBound=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
      int oldBound=newBound+seqSize;
      verifyRangeIsNull(root.arr,newBound,oldBound);
    }
  }
  public void pop(int expectedVal,RefOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
    Assertions.assertNull(root.arr[rootPreAlloc+parentPreAlloc+expectedRootSize+parentPostAlloc+rootPostAlloc]);
  }
  public void poll(int expectedVal,RefOutputTestArgType outputType){
    outputType.verifyPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      --expectedSeqSize;
      --expectedParentSize;
      --expectedRootSize;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      Assertions.assertNull(root.arr[rootPreAlloc+parentPreAlloc+expectedRootSize+parentPostAlloc+rootPostAlloc]);
    }
  }
  public void verifySet(int index,int val,int expectedRet,FunctionCallType functionCallType){
    {
      Assertions.assertEquals(TypeConversionUtil.convertToObject(expectedRet),((OmniList.OfRef)seq).set(index,TypeConversionUtil.convertToObject(val)));
    }
  }
  public void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfRef clone){
    int seqSize=expectedSeqSize;
    boolean retVal;
    {
      retVal=seq.removeIf((Predicate)pred);
    }
    if(retVal){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      int numRemoved;
      numRemoved=pred.numRemoved;
      verifyRangeIsNull(root.arr,(seqSize-numRemoved)+rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc,seqSize+rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc);
      for(var removedVal:pred.removedVals){
        Assertions.assertFalse(seq.contains(removedVal));
      }
      expectedSeqSize-=numRemoved;
      expectedParentSize-=numRemoved;
      expectedRootSize-=numRemoved;
      if(expectedNumRemoved!=-1){
        Assertions.assertEquals(expectedNumRemoved,numRemoved);
      }
    }else{
      Assertions.assertEquals(expectedSeqSize,clone.size());
      var seqItr=seq.iterator();
      var cloneItr=clone.iterator();
      for(int i=0;i<expectedSeqSize;++i){
        Assertions.assertSame(seqItr.next(),cloneItr.next());
      }
    }
    verifyStructuralIntegrity();
  }
  public void writeObject(ObjectOutputStream oos) throws IOException{
    switch(nestedType){
      case LIST:
        if(checkedType.checked){
          FieldAndMethodAccessor.RefArrSeq.CheckedList.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.RefArrSeq.UncheckedList.writeObject(seq,oos);
        }
        break;
      case STACK:
        if(checkedType.checked){
          FieldAndMethodAccessor.RefArrSeq.CheckedStack.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.RefArrSeq.UncheckedStack.writeObject(seq,oos);
        }
        break;
      case SUBLIST:
        if(checkedType.checked){
          FieldAndMethodAccessor.RefArrSeq.CheckedSubList.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.writeObject(seq,oos);
        }
        break;
      default:
        throw new Error("unknown nested type "+nestedType);
    }
  }
  public Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
    switch(nestedType){
      case LIST:
        if(checkedType.checked){
          return FieldAndMethodAccessor.RefArrSeq.CheckedList.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.RefArrSeq.UncheckedList.readObject(seq,ois);
        }
      case STACK:
        if(checkedType.checked){
          return FieldAndMethodAccessor.RefArrSeq.CheckedStack.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.RefArrSeq.UncheckedStack.readObject(seq,ois);
        }
      case SUBLIST:
        if(checkedType.checked){
          return FieldAndMethodAccessor.RefArrSeq.CheckedSubList.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.RefArrSeq.UncheckedSubList.readObject(seq,ois);
        }
      default:
        throw new Error("unknown nested type "+nestedType);
    }
  }
}
