package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.TypeUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import org.junit.jupiter.api.Assertions;
import omni.impl.ByteInputTestArgType;
import omni.impl.ByteOutputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import omni.function.BytePredicate;
import java.util.function.UnaryOperator;
import omni.function.ByteUnaryOperator;
import omni.impl.QueryCastType;
import java.util.Comparator;
import omni.function.ByteComparator;
import omni.function.ByteConsumer;
@SuppressWarnings({"rawtypes","unchecked"})
class ByteArrSeqMonitor implements ByteSeqMonitor{
  static final int DEFAULT_PRE_AND_POST_ALLOC=5;
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
  Booleannull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Boolean)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Boolean)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Boolean)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Boolean)(Boolean)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Boolean)(Boolean)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(Boolean)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Boolean)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Boolean)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytenull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Byte)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Byte)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Byte)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(Byte)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(Byte)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(Byte)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characternull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Character)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Character)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Character)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(Character)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(Character)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(Character)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Short)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Short)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Short)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(Short)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(Short)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(Short)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integernull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Integer)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Integer)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Integer)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(Integer)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(Integer)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(Integer)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Long)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Long)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Long)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(Long)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(Long)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(Long)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Float)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Float)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Float)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(Float)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(Float)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(Float)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublenull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Double)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Double)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Double)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(Double)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(Double)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(Double)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Objectnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(null));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(null));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Booleanfalse(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(boolean)(false));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(boolean)(false));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(boolean)(false));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Boolean)(boolean)(false));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(false));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(boolean)(false));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((boolean)(false));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((boolean)(false));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((boolean)(false));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)TypeUtil.castToByte(false));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Booleantrue(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(boolean)(true));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(boolean)(true));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(boolean)(true));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Boolean)(boolean)(true));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(true));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(boolean)(true));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((boolean)(true));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((boolean)(true));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((boolean)(true));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)TypeUtil.castToByte(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Byte0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(byte)(0));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(byte)(0));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(byte)(0));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(byte)(0));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((byte)(0));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((byte)(0));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytepos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(byte)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(byte)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(byte)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(byte)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((byte)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((byte)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Bytepos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(byte)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(byte)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(byte)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(byte)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((byte)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((byte)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Byteneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(byte)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(byte)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Byte)(byte)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Byte)(byte)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((byte)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((byte)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Character0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(char)(0));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(char)(0));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(char)(0));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(char)(0));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((char)(0));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((char)(0));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characterpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(char)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(char)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(char)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(char)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((char)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((char)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Characterpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(char)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(char)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(char)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(char)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((char)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((char)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  CharacterMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((char)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  CharacterMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((char)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Short0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(0));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(0));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(0));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(0));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(0));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(0));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Shortneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  ShortMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((short)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  ShortMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((short)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integer0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(0));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(0));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(0));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(0));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(0));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(0));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Integerneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(TypeUtil.MAX_SAFE_INT+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(TypeUtil.MIN_SAFE_INT-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Long0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(0));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(0));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(0));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(0));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(0));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(0));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Longneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMAX_SAFE_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  LongMIN_SAFE_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(0.0F));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(0.0F));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(0.0F));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(0.0F));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(0.0F));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(0.0F));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(0.0F));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(0.0F));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(0.0F));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatneg0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(-0.0F));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(-0.0F));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(-0.0F));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(-0.0F));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(-0.0F));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(-0.0F));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(-0.0F));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(-0.0F));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(-0.0F));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Floatneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  FloatNaN(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(float)(Float.NaN));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(float)(Float.NaN));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.NaN));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Float)(float)(Float.NaN));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Float)(float)(Float.NaN));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.NaN));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((float)(Float.NaN));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((float)(Float.NaN));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.NaN));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(0.0D));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(0.0D));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(0.0D));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(0.0D));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(0.0D));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(0.0D));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(0.0D));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(0.0D));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(0.0D));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doubleneg0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(-0.0D));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(-0.0D));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(-0.0D));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(-0.0D));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(-0.0D));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(-0.0D));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(-0.0D));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(-0.0D));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(-0.0D));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doublepos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(2));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(2));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(2));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(2));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(2));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(2));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  Doubleneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMIN_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(Double.MIN_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleMAX_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(Double.MAX_VALUE));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
  },
  DoubleNaN(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Object)(double)(Double.NaN));}
    @Override int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Object)(double)(Double.NaN));}
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.NaN));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((Double)(double)(Double.NaN));}
    @Override int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((Double)(double)(Double.NaN));}
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.NaN));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        ++seqMonitor.expectedParentModCount;
        ++seqMonitor.expectedRootModCount;
        --seqMonitor.expectedSeqSize;
        --seqMonitor.expectedParentSize;
        --seqMonitor.expectedRootSize;
      }
      return ret;
    }
    @Override int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).indexOf((double)(Double.NaN));}
    @Override int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniList.OfByte)seqMonitor.seq).lastIndexOf((double)(Double.NaN));}
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.NaN));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
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
    boolean invokecontains(ByteArrSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    boolean invokeremoveVal(ByteArrSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokeindexOf(ByteArrSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokelastIndexOf(ByteArrSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokesearch(ByteArrSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    abstract boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor);
    abstract boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor);
    abstract boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor);
    abstract boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokeindexOfObject(ByteArrSeqMonitor seqMonitor);
    abstract int invokeindexOfBoxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokeindexOfUnboxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfObject(ByteArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfBoxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokelastIndexOfUnboxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokesearchObject(ByteArrSeqMonitor seqMonitor);
    abstract int invokesearchBoxed(ByteArrSeqMonitor seqMonitor);
    abstract int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor);
    abstract void addEqualsVal(ByteArrSeqMonitor seqMonitor);
    abstract void addNotEqualsVal(ByteArrSeqMonitor seqMonitor);
    void initDoesNotContain(ByteArrSeqMonitor seqMonitor){
      for(int i=0;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
    }
    int initContainsEnd(ByteArrSeqMonitor seqMonitor){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0;i<99;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      return seqMonitor.expectedSeqSize-1;
    }
    int initContainsMiddle(ByteArrSeqMonitor seqMonitor){
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
    int initContainsBeginning(ByteArrSeqMonitor seqMonitor){
      addEqualsVal(seqMonitor);
      for(int i=1;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
      return 0;
    }
  };
  private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,byte[] arr){
    for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTobyte(v);
    }
    for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTobyte(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTobyte(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTobyte(v);
    }
  }
  final NestedType nestedType;
  final CheckedType checkedType;
  final int initialCapacity;
  final int rootPreAlloc;
  final int parentPreAlloc;
  final int parentPostAlloc;
  final int rootPostAlloc;
  final ByteArrSeq root;
  final OmniCollection.OfByte parent;
  final OmniCollection.OfByte seq;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  ByteArrSeqMonitor(NestedType nestedType,CheckedType checkedType,int seqLength,byte[] arr){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.expectedRootSize=seqLength;
    this.expectedParentSize=seqLength;
    this.expectedSeqSize=seqLength;
    this.initialCapacity=(arr==null)?0:(arr==OmniArray.OfByte.DEFAULT_ARR?OmniArray.DEFAULT_ARR_SEQ_CAP:arr.length);
    switch(nestedType){
      case SUBLIST:
        seqLength+=(4*DEFAULT_PRE_AND_POST_ALLOC);
      case LIST:
        this.root=checkedType.checked?new ByteArrSeq.CheckedList(seqLength,arr):new ByteArrSeq.UncheckedList(seqLength,arr);
        break;
      case STACK:
        this.root=checkedType.checked?new ByteArrSeq.CheckedStack(seqLength,arr):new ByteArrSeq.UncheckedStack(seqLength,arr);
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
      this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,seqLength-rootPostAlloc);
      this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,seqLength-rootPreAlloc-parentPostAlloc-rootPostAlloc);
    }
  }
  ByteArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
    if(nestedType==NestedType.SUBLIST){
      this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
      int rootSize;
      byte[] arr=new byte[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
      initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
      this.root=checkedType.checked
        ?new ByteArrSeq.CheckedList(rootSize,arr)
        :new ByteArrSeq.UncheckedList(rootSize,arr);
      this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
      this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,parentPreAlloc);
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
            ?new ByteArrSeq.CheckedStack()
            :new ByteArrSeq.UncheckedStack();
          break;
        case LIST:
          this.root=checkedType.checked
            ?new ByteArrSeq.CheckedList()
            :new ByteArrSeq.UncheckedList();
      }
      this.parent=root;
      this.seq=root;
    }
  }
  ByteArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
    this(nestedType,checkedType,initialCapacity,0,0,0,0);
  }
  ByteArrSeqMonitor(final CheckedType checkedType,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this(NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
  }
  ByteArrSeqMonitor(final NestedType nestedType,final CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=initialCapacity;
    this.rootPreAlloc=rootPreAlloc;
    this.parentPreAlloc=parentPreAlloc;
    this.parentPostAlloc=parentPostAlloc;
    this.rootPostAlloc=rootPostAlloc;
    int rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
    byte[] arr;
    if(rootSize==0){
      switch(initialCapacity){
        case 0:
          arr=null;
          break;
        case OmniArray.DEFAULT_ARR_SEQ_CAP:
          arr=OmniArray.OfByte.DEFAULT_ARR;
          break;
        default:
          arr=new byte[initialCapacity];
      }
    }else{
      arr=new byte[Math.max(initialCapacity,rootSize)];
    }
    initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
    this.root=nestedType==NestedType.STACK
      ?checkedType.checked
        ?new ByteArrSeq.CheckedStack(rootSize,arr)
        :new ByteArrSeq.UncheckedStack(rootSize,arr)
      :checkedType.checked
        ?new ByteArrSeq.CheckedList(rootSize,arr)
        :new ByteArrSeq.UncheckedList(rootSize,arr);
    switch(nestedType){
      case SUBLIST:
        this.parent=((OmniList.OfByte)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfByte)parent).subList(parentPreAlloc,parentPreAlloc);
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
    final OmniIterator.OfByte itr;
    int expectedCursor;
    int expectedLastRet;
    private UncheckedArrSeqItrMonitor(OmniIterator.OfByte itr,int expectedCursor){
      this.itr=itr;
      this.expectedCursor=expectedCursor;
      this.expectedLastRet=-1;
    }
    public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
      int expectedBound=nestedType.forwardIteration?expectedSeqSize:0;
      if(functionCallType==FunctionCallType.Boxed){
        itr.forEachRemaining((Consumer)action);
      }else
      {
        itr.forEachRemaining((ByteConsumer)action);
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
      ((OmniListIterator.OfByte)itr).previousByte();
      expectedLastRet=--expectedCursor;
    }
    public ByteSeqMonitor getSeqMonitor(){
      return ByteArrSeqMonitor.this;
    }
    public void set(int v,ByteInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfByte)itr,v);
    }
    public boolean hasNext(){
      return itr.hasNext();
    }
    public boolean hasPrevious(){
      return ((OmniListIterator.OfByte)itr).hasPrevious();
    }
    public int nextIndex(){
      return ((OmniListIterator.OfByte)itr).nextIndex();
    }
    public int previousIndex(){
      return ((OmniListIterator.OfByte)itr).previousIndex();
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case STACK:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case SUBLIST:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.Itr.parent(itr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,ByteInputTestArgType inputArgType){
      inputArgType.callListItrAdd((OmniListIterator.OfByte)itr,v);
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
      itr.nextByte();
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
      outputType.verifyItrNext(itr,expectedVal);
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyPrevious(int expectedVal,ByteOutputTestArgType outputType){
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
    }
  }
  class CheckedArrSeqItrMonitor extends UncheckedArrSeqItrMonitor{
    int expectedItrModCount;
    private CheckedArrSeqItrMonitor(OmniIterator.OfByte itr,int expectedCursor){
      super(itr,expectedCursor);
      this.expectedItrModCount=expectedRootModCount;
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case STACK:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case SUBLIST:
          actualCursor=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,ByteInputTestArgType inputArgType){
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
    var itr=((OmniList.OfByte)seq).listIterator();
    return checkedType.checked
      ?new CheckedArrSeqItrMonitor(itr,0)
      :new UncheckedArrSeqItrMonitor(itr,0);
  }
  public UncheckedArrSeqItrMonitor getListItrMonitor(int index){
    var itr=((OmniList.OfByte)seq).listIterator(index);
    return checkedType.checked
      ?new CheckedArrSeqItrMonitor(itr,index)
      :new UncheckedArrSeqItrMonitor(itr,index);
  }
  private static class ArrSeqSequenceVerificationItr extends SequenceVerificationItr{
    final byte[] arr;
    int offset;
    final ByteArrSeqMonitor seqMonitor;
    private ArrSeqSequenceVerificationItr(ByteArrSeqMonitor seqMonitor,int offset,byte[] arr){
      this.seqMonitor=seqMonitor;
      this.arr=arr;
      this.offset=offset;
    }
    @Override public SequenceVerificationItr verifyPostAlloc(int expectedVal){
      for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.rootPostAlloc;i<bound;++i){
        verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,expectedVal);
      }
      return this;
    }
    @Override public void verifyLiteralIndexAndIterate(byte val){
      Assertions.assertEquals(val,arr[offset++]);
    }
    @Override public void verifyIndexAndIterate(ByteInputTestArgType inputArgType,int val){
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
        verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    @Override public SequenceVerificationItr verifyParentPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
        verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
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
      ByteInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,arr[offset]);
    }
    return new ArrSeqSequenceVerificationItr(this,offset,arr);
  }
  public SequenceVerificationItr verifyPreAlloc(){
    var arr=root.arr;
    int offset=0;
    for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
      ByteInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
    }
    return new ArrSeqSequenceVerificationItr(this,offset,arr);
  }
  public void illegalAdd(PreModScenario preModScenario){
    switch(preModScenario){
      case ModSeq:
        ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case ModParent:
        ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        break;
      case ModRoot:
        ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public void add(int index,int val,ByteInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public boolean add(int val,ByteInputTestArgType inputArgType){
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
  public void push(int val,ByteInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void put(int index,int val,ByteInputTestArgType inputArgType){
    inputArgType.callListPut(seq,index,val);
  }
  public String toString(){
    var builder=new StringBuilder("ByteArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.modCount(root));
          }
          break;
        case LIST:
          if(checkedType.checked){
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
          }
          break;
        case SUBLIST:
          OmniList.OfByte actualSeqParent;
          ByteArrSeq actualSeqRoot;
          int actualSeqSize;
          OmniList.OfByte actualParentParent;
          ByteArrSeq  actualParentRoot;
          int actualParentSize;
          if(checkedType.checked){
            actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(seq);
            actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(seq);
            actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(parent);
            actualParentSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(seq);
            actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(seq);
            actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(parent);
            actualParentSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(parent);
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
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.ByteArrSeq.size(root));
  }
  public boolean isEmpty(){
    return seq.isEmpty();
  }
  public void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      seq.forEach((Consumer)action);
    }else
    {
      seq.forEach((ByteConsumer)action);
    }
  }
  public void unstableSort(MonitoredComparator sorter){
    int seqSize=expectedSeqSize;
    ((OmniList.OfByte)seq).unstableSort((ByteComparator)sorter);
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfByte)seq).replaceAll((UnaryOperator)operator);
    }else
    {
      ((OmniList.OfByte)seq).replaceAll((ByteUnaryOperator)operator);
    }
    if(seqSize!=0){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void sort(MonitoredComparator sorter,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfByte)seq).sort((Comparator)sorter);
    }else
    {
      ((OmniList.OfByte)seq).sort((ByteComparator)sorter);
    }
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableAscendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfByte)seq).stableAscendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableDescendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfByte)seq).stableDescendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void removeAt(int expectedVal,ByteOutputTestArgType outputType,int index){
    outputType.verifyListRemoveAt(seq,index,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
  }
  public void get(int expectedVal,ByteOutputTestArgType outputType,int index){
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
    }
  }
  public void pop(int expectedVal,ByteOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
  }
  public void poll(int expectedVal,ByteOutputTestArgType outputType){
    outputType.verifyPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      --expectedSeqSize;
      --expectedParentSize;
      --expectedRootSize;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void verifySet(int index,int val,int expectedRet,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      Assertions.assertEquals(TypeConversionUtil.convertToByte(expectedRet),((OmniList.OfByte)seq).set(index,TypeConversionUtil.convertToByte(val)));
    }
    else
    {
      Assertions.assertEquals(TypeConversionUtil.convertTobyte(expectedRet),((OmniList.OfByte)seq).set(index,TypeConversionUtil.convertTobyte(val)));
    }
  }
  public void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfByte clone){
    boolean retVal;
    if(functionCallType==FunctionCallType.Boxed){
      retVal=seq.removeIf((Predicate)pred);
    }
    else
    {
      retVal=seq.removeIf((BytePredicate)pred);
    }
    if(retVal){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      int numRemoved;
      numRemoved=pred.numRemoved;
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
        Assertions.assertEquals(seqItr.nextByte(),cloneItr.nextByte());
      }
    }
    verifyStructuralIntegrity();
  }
  public void writeObject(ObjectOutputStream oos) throws IOException{
    switch(nestedType){
      case LIST:
        if(checkedType.checked){
          FieldAndMethodAccessor.ByteArrSeq.CheckedList.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.ByteArrSeq.UncheckedList.writeObject(seq,oos);
        }
        break;
      case STACK:
        if(checkedType.checked){
          FieldAndMethodAccessor.ByteArrSeq.CheckedStack.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.writeObject(seq,oos);
        }
        break;
      case SUBLIST:
        if(checkedType.checked){
          FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.writeObject(seq,oos);
        }else{
          FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.writeObject(seq,oos);
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
          return FieldAndMethodAccessor.ByteArrSeq.CheckedList.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.ByteArrSeq.UncheckedList.readObject(seq,ois);
        }
      case STACK:
        if(checkedType.checked){
          return FieldAndMethodAccessor.ByteArrSeq.CheckedStack.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.readObject(seq,ois);
        }
      case SUBLIST:
        if(checkedType.checked){
          return FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.readObject(seq,ois);
        }else{
          return FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.readObject(seq,ois);
        }
      default:
        throw new Error("unknown nested type "+nestedType);
    }
  }
}
