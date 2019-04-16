package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import omni.function.ByteConsumer;
import omni.impl.ByteInputTestArgType;
import omni.impl.ByteOutputTestArgType;
import omni.function.BytePredicate;
import java.util.function.Consumer;
import omni.impl.FunctionCallType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import omni.api.OmniCollection;
import java.util.function.Predicate;
import omni.impl.ByteSnglLnkNode;
import omni.api.OmniIterator;
import omni.api.OmniStack;
import omni.util.TypeUtil;
import omni.impl.QueryCastType;
@SuppressWarnings({"rawtypes","unchecked"})
class ByteSnglLnkSeqMonitor implements ByteSeqMonitor{
  static enum NestedType{
    QUEUE,
    STACK;
  }
  static enum QueryTester
  {
  Booleannull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Boolean)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(Boolean)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytenull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Byte)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(Byte)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characternull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Character)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(Character)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Short)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(Short)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integernull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Integer)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(Integer)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Long)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(Long)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Float)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(Float)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublenull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Double)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(Double)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Objectnull(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(null));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(null));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Booleanfalse(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(boolean)(false));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(boolean)(false));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(false));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((boolean)(false));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)TypeUtil.castToByte(false));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Booleantrue(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(boolean)(true));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Boolean)(boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Boolean)(boolean)(true));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((boolean)(true));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((boolean)(true));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)TypeUtil.castToByte(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Byte0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytepos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Bytepos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Byteneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(byte)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Byte)(byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Byte)(byte)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((byte)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((byte)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Character0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characterpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Characterpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  CharacterMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((char)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  CharacterMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Character)(char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((char)(((char)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((char)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Short0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Shortneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  ShortMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((short)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  ShortMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Short)(short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((short)(((short)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((short)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integer0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Integerneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(((int)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((int)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MAX_SAFE_INT+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(TypeUtil.MAX_SAFE_INT+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  IntegerMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((int)(TypeUtil.MIN_SAFE_INT-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(TypeUtil.MIN_SAFE_INT-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Long0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(0));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(0));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(0));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(0));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Longneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMAX_SAFE_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  LongMIN_SAFE_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(0.0F));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(0.0F));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(0.0F));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatneg0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(-0.0F));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(-0.0F));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-0.0F));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(-0.0F));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatpos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Floatneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(((float)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((float)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  FloatNaN(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(float)(Float.NaN));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Float)(float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Float)(float)(Float.NaN));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((float)(Float.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((float)(Float.NaN));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(0.0D));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(0.0D));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(0.0D));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doubleneg0(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(-0.0D));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(-0.0D));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-0.0D));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(-0.0D));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)true);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doublepos2(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(2));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(2));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(2));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(2));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  Doubleneg1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_BYTE_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_BYTE_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Byte.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_SHORT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_SHORT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Short.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_CHAR_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Character.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_SAFE_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MAX_SAFE_INT)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_SAFE_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)TypeUtil.MIN_SAFE_INT)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_INT_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_INT_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Integer.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_LONG_PLUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MAX_VALUE)+1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_LONG_MINUS1(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(((double)Long.MIN_VALUE)-1));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(((double)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_FLOAT_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Float.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMIN_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MIN_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleMAX_DOUBLE_VALUE(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.MAX_VALUE));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
  },
  DoubleNaN(false){
    @Override boolean invokecontainsObject(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
    @Override boolean invokeremoveValObject(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.remove((Object)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchObject(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Object)(double)(Double.NaN));}
    @Override boolean invokecontainsBoxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
    @Override boolean invokeremoveValBoxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((Double)(double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchBoxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((Double)(double)(Double.NaN));}
    @Override boolean invokecontainsUnboxed(ByteArrSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
    @Override boolean invokeremoveValUnboxed(ByteArrSeqMonitor seqMonitor){
      boolean ret=seqMonitor.seq.removeVal((double)(Double.NaN));
      if(ret){
        ++seqMonitor.expectedSeqModCount;
        --seqMonitor.expectedSeqSize;
      }
      return ret;
    }
    @Override int invokesearchUnboxed(ByteArrSeqMonitor seqMonitor){return ((OmniStack.OfByte)seqMonitor.seq).search((double)(Double.NaN));}
    void addEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((byte)(Double.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
    }
    void addNotEqualsVal(ByteArrSeqMonitor seqMonitor){
      seqMonitor.seq.add((boolean)false);
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedSeqSize;
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
  NestedType nestedType;
  CheckedType checkedType;
  final ByteSnglLnkSeq seq;
  int expectedSeqSize;
  int expectedSeqModCount;
  ByteSnglLnkSeqMonitor(NestedType nestedType,CheckedType checkedType){
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    switch(nestedType){
      case QUEUE:
        this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedQueue():new ByteSnglLnkSeq.UncheckedQueue();
        break;
      case STACK:
        this.seq=checkedType.checked?new ByteSnglLnkSeq.CheckedStack():new ByteSnglLnkSeq.UncheckedStack();
        break;
      default:
        throw new Error("unknown nested type "+nestedType);
    }
  }
  public int getExpectedSeqSize(){
    return this.expectedSeqSize;
  }
  public void illegalAdd(PreModScenario preModScenario){
    switch(preModScenario){
      case ModSeq:
        ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public boolean add(int val,ByteInputTestArgType inputArgType){
    boolean ret=inputArgType.callCollectionAdd(seq,val);
    if(ret){
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    return ret;
  }
  public void push(int val,ByteInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedSeqSize;
    ++expectedSeqModCount;
  }
  public String toString(){
    StringBuilder builder=new StringBuilder();
    builder.append("ByteSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
    switch(nestedType){
      case STACK:
        builder.append("Stack");
        break;
      case QUEUE:
        builder.append("Queue");
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
    return builder.toString();
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
  public void clear(){
    int seqSize=expectedSeqSize;
    seq.clear();
    if(seqSize!=0){
      expectedSeqSize=0;
      ++expectedSeqModCount;
    }
  }
  public void pop(int expectedVal,ByteOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    --expectedSeqSize;
    ++expectedSeqModCount;
  }
  public void poll(int expectedVal,ByteOutputTestArgType outputType){
    outputType.verifyStackPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      --expectedSeqSize;
      ++expectedSeqModCount;
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
      int numRemoved;
      numRemoved=pred.numRemoved;
      for(var removedVal:pred.removedVals){
        Assertions.assertFalse(seq.contains(removedVal));
      }
      expectedSeqSize-=numRemoved;
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
    oos.writeObject(seq);
  }
  public Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
    return ois.readObject();
  }
  public void verifyStructuralIntegrity(){
    Assertions.assertEquals(expectedSeqSize,seq.size);
    if(checkedType.checked){
      switch(nestedType){
        case QUEUE:
          Assertions.assertEquals(expectedSeqModCount,((ByteSnglLnkSeq.CheckedQueue)seq).modCount);
          break;
        case STACK:
          Assertions.assertEquals(expectedSeqModCount,((ByteSnglLnkSeq.CheckedStack)seq).modCount);
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    if(expectedSeqSize==0){
      Assertions.assertNull(seq.head);
    }else{
      ByteSnglLnkNode node;
      Assertions.assertNotNull(node=seq.head);
      while(--expectedSeqSize!=0){
        Assertions.assertNotNull(node=node.next);
      }
      Assertions.assertNull(node.next);
    }
  }
  class UncheckedSnglLnkSeqItrMonitor implements ItrMonitor{
    final OmniIterator.OfByte itr;
    ByteSnglLnkNode expectedPrev;
    ByteSnglLnkNode expectedCurr;
    ByteSnglLnkNode expectedNext;
    UncheckedSnglLnkSeqItrMonitor(){
      this.expectedNext=seq.head;
      this.itr=seq.iterator();
    }
    public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
      if(functionCallType==FunctionCallType.Boxed){
        itr.forEachRemaining((Consumer)action);
      }else
      {
        itr.forEachRemaining((ByteConsumer)action);
      }
      ByteSnglLnkNode expectedNext;
      if((expectedNext=this.expectedNext)!=null){
        ByteSnglLnkNode expectedPrev,expectedCurr=this.expectedCurr;
        do{
          expectedPrev=expectedCurr;
        }while((expectedNext=(expectedCurr=expectedNext).next)!=null);
        this.expectedPrev=expectedPrev;
        this.expectedCurr=expectedCurr;
        this.expectedNext=null;
      }
    }
    public ByteSeqMonitor getSeqMonitor(){
      return ByteSnglLnkSeqMonitor.this;
    }
    public void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
      outputType.verifyItrNext(itr,expectedVal);
      final ByteSnglLnkNode expectedNext;
      this.expectedNext=(expectedNext=this.expectedNext).next;
      this.expectedPrev=this.expectedCurr;
      this.expectedCurr=expectedNext;
    }
    public void verifyIteratorState(){
      Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
      Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
      Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
      switch(nestedType)
      {
        case STACK:
          if(checkedType.checked)
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
          }
          else
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case QUEUE:
          if(checkedType.checked)
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
          }
          else
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    public void iterateForward(){
      itr.next();
      final ByteSnglLnkNode expectedNext;
      this.expectedNext=(expectedNext=this.expectedNext).next;
      this.expectedPrev=this.expectedCurr;
      this.expectedCurr=expectedNext;
    }
    public void remove(){
      itr.remove();
      --expectedSeqSize;
      ++expectedSeqModCount;
      this.expectedCurr=this.expectedPrev;
    }
    public boolean hasNext(){
      return itr.hasNext();
    }
  }
  class CheckedSnglLnkSeqItrMonitor extends UncheckedSnglLnkSeqItrMonitor
  {
    int expectedItrModCount;
    private CheckedSnglLnkSeqItrMonitor(){
      super();
      this.expectedItrModCount=expectedSeqModCount;
    }
    public void verifyIteratorState(){
      Assertions.assertSame(expectedPrev,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.prev(itr));
      Assertions.assertSame(expectedCurr,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.curr(itr));
      Assertions.assertSame(expectedNext,FieldAndMethodAccessor.ByteSnglLnkSeq.AbstractItr.next(itr));
      switch(nestedType)
      {
        case STACK:
          if(checkedType.checked)
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.parent(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedStack.Itr.modCount(itr));
          }
          else
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedStack.Itr.parent(itr));
          }
          break;
        case QUEUE:
          if(checkedType.checked)
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.parent(itr));
            Assertions.assertEquals(expectedItrModCount,FieldAndMethodAccessor.ByteSnglLnkSeq.CheckedQueue.Itr.modCount(itr));
          }
          else
          {
            Assertions.assertSame(seq,FieldAndMethodAccessor.ByteSnglLnkSeq.UncheckedQueue.Itr.parent(itr));
          }
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    @Override public void remove(){
      super.remove();
      ++expectedItrModCount;
    }
  }
  private static class SnglLnkSeqSequenceVerificationItr extends SequenceVerificationItr{
    ByteSnglLnkNode curr;
    final ByteSnglLnkSeqMonitor seqMonitor;
    private SnglLnkSeqSequenceVerificationItr(ByteSnglLnkSeqMonitor seqMonitor,ByteSnglLnkNode curr){
      this.seqMonitor=seqMonitor;
      this.curr=curr;
    }
    @Override public SequenceVerificationItr verifyPostAlloc(int expectedVal){
      return this;
    }
    @Override public void verifyLiteralIndexAndIterate(byte val){
      Assertions.assertEquals(val,curr.val);
      curr=curr.next;
    }
    @Override public void verifyIndexAndIterate(ByteInputTestArgType inputArgType,int val){
      inputArgType.verifyVal(val,curr.val);
      curr=curr.next;
    }
    @Override public SequenceVerificationItr getPositiveOffset(int i){
      if(i<0){
        throw new Error("offset cannot be negative: "+i);
      }
      var currCopy=curr;
      while(i>0)
      {
        --i;
        currCopy=currCopy.next;
      }
      return new SnglLnkSeqSequenceVerificationItr(seqMonitor,currCopy);
    }
    @Override public SequenceVerificationItr skip(int i){
      if(i<0){
        throw new Error("offset cannot be negative: "+i);
      }
      while(i>0)
      {
        --i;
        curr=curr.next;
      }
      return this;
    }
    @Override public boolean equals(Object val){
      final SnglLnkSeqSequenceVerificationItr that;
      return val==this || (val instanceof SnglLnkSeqSequenceVerificationItr && (that=(SnglLnkSeqSequenceVerificationItr)val).seqMonitor.seq==this.seqMonitor.seq && that.curr==this.curr);
    }
    @Override public SequenceVerificationItr verifyRootPostAlloc(){
      return this;
    }
    @Override public SequenceVerificationItr verifyParentPostAlloc(){
      return this;
    }
  }
  public UncheckedSnglLnkSeqItrMonitor getItrMonitor(){
    return checkedType.checked
      ?new CheckedSnglLnkSeqItrMonitor()
      :new UncheckedSnglLnkSeqItrMonitor();
  }
  public SequenceVerificationItr verifyPreAlloc(){
    return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
  }
  public SequenceVerificationItr verifyPreAlloc(int expectedVal){
    return new SnglLnkSeqSequenceVerificationItr(this,seq.head);
  }
}
