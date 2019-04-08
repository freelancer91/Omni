package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.TypeUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import omni.api.OmniStack;
import org.junit.jupiter.api.Assertions;
import omni.impl.CharInputTestArgType;
import omni.impl.CharOutputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import omni.function.CharPredicate;
import java.util.function.UnaryOperator;
import omni.function.CharUnaryOperator;
import omni.impl.QueryCastType;
import java.util.Comparator;
import omni.function.CharComparator;
import omni.function.CharConsumer;
@SuppressWarnings({"rawtypes","unchecked"})
class CharSeqMonitor{
  static final int DEFAULT_PRE_AND_POST_ALLOC=5;
  static enum StructType{
    ARRSEQ;
  }
  static enum SequenceLocation{
    BEGINNING(null,true),
    MIDDLE(null,false),
    END(null,false),
    IOBLO(IndexOutOfBoundsException.class,true),
    IOBHI(IndexOutOfBoundsException.class,true);
    final Class<? extends Throwable> expectedException;
    final boolean validForEmpty;
    SequenceLocation(Class<? extends Throwable> expectedException,boolean validForEmpty){
      this.expectedException=expectedException;
      this.validForEmpty=validForEmpty;
    }
  }
  static enum SequenceContentsScenario{
    EMPTY(false),
    NONEMPTY(true);
    final boolean nonEmpty;
    SequenceContentsScenario(boolean nonEmpty){
      this.nonEmpty=nonEmpty;
    }
  }
  static enum CheckedType{
    CHECKED(true),
    UNCHECKED(false);
    final boolean checked;
    CheckedType(boolean checked){
      this.checked=checked;
    }
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
  static enum ItrType{
    Itr,
    ListItr;
  }
  static enum PreModScenario{
    ModSeq(ConcurrentModificationException.class,false,true),
    ModParent(ConcurrentModificationException.class,true,false),
    ModRoot(ConcurrentModificationException.class,true,false),
    NoMod(null,true,true);
    final Class<? extends Throwable> expectedException;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    PreModScenario(Class<? extends Throwable> expectedException,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
  }
  static enum ListItrSetScenario{
    HappyPath(null,PreModScenario.NoMod),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot),
    ThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostAddThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    PostRemoveThrowISE(IllegalStateException.class,PreModScenario.NoMod),
    ThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    ThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    ThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostAddThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostAddThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostAddThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq),
    PostRemoveThrowISESupercedesModRootCME(IllegalStateException.class,PreModScenario.ModRoot),
    PostRemoveThrowISESupercedesModParentCME(IllegalStateException.class,PreModScenario.ModParent),
    PostRemoveThrowISESupercedesModSeqCME(IllegalStateException.class,PreModScenario.ModSeq);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    ListItrSetScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
    }
  }
  static enum ItrRemoveScenario{
    PostNext(null,false,true),
    PostPrevious(null,false,false),
    PostInit(IllegalStateException.class,true,true),
    PostAdd(IllegalStateException.class,true,false),
    PostRemove(IllegalStateException.class,false,true);
    final Class<? extends Throwable> expectedException;
    final boolean validWithEmptySeq;
    final boolean validWithForwardItr;
    ItrRemoveScenario(Class<? extends Throwable> expectedException,boolean validWithEmptySeq,boolean validWithForwardItr){
      this.expectedException=expectedException;
      this.validWithEmptySeq=validWithEmptySeq;
      this.validWithForwardItr=validWithForwardItr;
    }
  }
  static enum IterationScenario{
    NoMod(NoSuchElementException.class,PreModScenario.NoMod,false),
    ModSeq(ConcurrentModificationException.class,PreModScenario.ModSeq,false),
    ModParent(ConcurrentModificationException.class,PreModScenario.ModParent,false),
    ModRoot(ConcurrentModificationException.class,PreModScenario.ModRoot,false),
    ModSeqSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModSeq,true),
    ModParentSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModParent,true),
    ModRootSupercedesThrowNSEE(ConcurrentModificationException.class,PreModScenario.ModRoot,true);
    final Class<? extends Throwable> expectedException;
    final PreModScenario preModScenario;
    final boolean validWithEmptySeq;
    IterationScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario,boolean validWithEmptySeq){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
      this.validWithEmptySeq=validWithEmptySeq;
    }
  }
  static enum MonitoredComparatorGen{
    NoThrowAscending(null,true,true,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NoThrowDescending(null,true,false,true,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    NullComparator(null,true,true,false,true){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return null;
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario);
      }
      @Override void initReverseHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowAIOB(IllegalArgumentException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ThrowIOB(IndexOutOfBoundsException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqAscending(ConcurrentModificationException.class,true,true,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqDescending(ConcurrentModificationException.class,true,false,true,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd();
            break;
          case ModParent:
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModParentAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModParentDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        switch(preModScenario){
          case NoMod:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModSeq:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIllegalAdd().verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyIndexAndIterate(2);
            verifyItr.verifyIndexAndIterate(3);
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootAscending(ConcurrentModificationException.class,false,true,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(3);
        seqMonitor.add(2);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootDescending(ConcurrentModificationException.class,false,false,true,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return -Character.compare(val1,val2);
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModSeqThrowAIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case NoMod:
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case ModParent:
          case ModRoot:
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
        verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModSeqThrowIOB(ConcurrentModificationException.class,true,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
          verifyItr.verifyIndexAndIterate(2);
          verifyItr.verifyIndexAndIterate(3);
          switch(preModScenario){
            case NoMod:
            case ModSeq:
              verifyItr.verifyIllegalAdd();
            case ModParent:
            case ModRoot:
              break;
            default:
              throw new Error("Unknown preModScenario "+preModScenario);
          }
          verifyItr.verifyPostAlloc(preModScenario);
      }
    },
    ModParentThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModParentThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        switch(preModScenario){
          case ModSeq:
            verifyItr.verifyIllegalAdd();
          case NoMod:
            verifyItr.verifyPostAlloc(PreModScenario.ModParent);
            break;
          case ModParent:
            verifyItr.verifyParentPostAlloc().verifyIllegalAdd().verifyIllegalAdd().verifyRootPostAlloc();
            break;
          case ModRoot:
            verifyItr.verifyPostAlloc(preModScenario);
            break;
          default:
            throw new Error("Unknown preModScenario "+preModScenario);
        }
      }
    },
    ModRootThrowAIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new ArrayIndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    },
    ModRootThrowIOB(ConcurrentModificationException.class,false,false,false,false){
      MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor){
        return new MonitoredComparator(){
          @Override public int compare(char val1,char val2){
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override void initHelper(CharSeqMonitor seqMonitor){
        seqMonitor.add(2);
        seqMonitor.add(3);
      }
      @Override void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
        verifyItr.verifyIndexAndIterate(2);
        verifyItr.verifyIndexAndIterate(3);
        verifyItr.verifyPostAlloc(preModScenario).verifyIllegalAdd();
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean ascending;
    final boolean descending;
    final boolean nullComparator;
    MonitoredComparatorGen(Class<? extends Throwable> expectedException,final boolean appliesToRoot,final boolean ascending,final boolean descending,boolean nullComparator){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.ascending=ascending;
      this.descending=descending;
      this.nullComparator=nullComparator;
    }
    abstract void initHelper(CharSeqMonitor seqMonitor);
    void initReverseHelper(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void init(CharSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      Assertions.assertEquals(0,seqMonitor.expectedSeqModCount);
      Assertions.assertEquals(0,seqMonitor.expectedParentSize);
      Assertions.assertEquals(0,seqMonitor.expectedParentModCount);
      Assertions.assertEquals(0,seqMonitor.expectedRootSize);
      Assertions.assertEquals(0,seqMonitor.expectedRootModCount);
      if(seqContentsScenario.nonEmpty){
        initHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void initReverse(CharSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      Assertions.assertEquals(0,seqMonitor.expectedSeqModCount);
      Assertions.assertEquals(0,seqMonitor.expectedParentSize);
      Assertions.assertEquals(0,seqMonitor.expectedParentModCount);
      Assertions.assertEquals(0,seqMonitor.expectedRootSize);
      Assertions.assertEquals(0,seqMonitor.expectedRootModCount);
      if(seqContentsScenario.nonEmpty){
        initReverseHelper(seqMonitor);
      }else{
        seqMonitor.add(1);
      }
      seqMonitor.illegalAdd(preModScenario);
    }
    void assertReverseSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario){
      throw new UnsupportedOperationException();
    }
    abstract void assertSortedHelper(SequenceVerificationItr verifyItr,PreModScenario preModScenario);
    void assertSorted(CharSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    void assertReverseSorted(CharSeqMonitor seqMonitor,SequenceContentsScenario seqContentsScenario,PreModScenario preModScenario){
      seqMonitor.verifyStructuralIntegrity();
      var verifyItr=seqMonitor.verifyPreAlloc();
      if(seqContentsScenario.nonEmpty){
        assertReverseSortedHelper(verifyItr,preModScenario);
      }else{
        verifyItr.verifyIndexAndIterate(1);
        verifyItr.verifyPostAlloc(preModScenario);
      }
    }
    abstract MonitoredComparator getMonitoredComparator(CharSeqMonitor seqMonitor);
  }
  static enum MonitoredFunctionGen{
    NoThrow(null,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator();
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor();
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    //TODO add this test scenario
    //ModItr(ConcurrentModificationException.class,false,false,true){
    //  @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
    //    return new MonitoredConsumer(){
    //      public void accept(char val){
    //        super.accept(val);
    //        itrMonitor.add(0);
    //      }
    //    };
    //  }
    //},
    ModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            return new Character[arrSize];
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            return new Character[arrSize];
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            var ret=super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return ret;
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            return new Character[arrSize];
          }
        };
      }
    },
    //TODO add this test scenario
    //ThrowModItr(ConcurrentModificationException.class,false,false,true){
    //  @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
    //    return new MonitoredConsumer(){
    //      public void accept(char val){
    //        super.accept(val);
    //        itrMonitor.add(0);
    //        throw new IndexOutOfBoundsException();
    //      }
    //    };
    //  }
    //},
    ThrowModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(char val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor){
        return new MonitoredUnaryOperator(){
          @Override public char applyAsChar(char val){
            super.applyAsChar(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor){
        return new MonitoredArrayConstructor(){
          @Override public Character[] apply(int arrSize){
            ++numCalls;
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
    abstract MonitoredUnaryOperator getMonitoredUnaryOperator(CharSeqMonitor seqMonitor);
    MonitoredConsumer getMonitoredConsumer(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    abstract MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor);
    abstract MonitoredArrayConstructor getMonitoredArrayConstructor(CharSeqMonitor seqMonitor);
  }
  static enum QueryTester
  {
  Booleannull(false){
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Boolean)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Boolean)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Boolean)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Boolean)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(Boolean)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(Boolean)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(Boolean)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(Boolean)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Byte)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Byte)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Byte)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Byte)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(Byte)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(Byte)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(Byte)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(Byte)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Character)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Character)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Character)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Character)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(Character)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(Character)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(Character)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(Character)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Short)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Short)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Short)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Short)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(Short)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(Short)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(Short)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(Short)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Integer)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Integer)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Integer)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Integer)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(Integer)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(Integer)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(Integer)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(Integer)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Long)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Long)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Long)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Long)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(Long)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(Long)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(Long)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(Long)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Float)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Float)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Float)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Float)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(Float)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(Float)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(Float)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(Float)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Double)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Double)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Double)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Double)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(Double)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(Double)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(Double)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(Double)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(Object)(null));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(Object)(null));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(Object)(null));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(Object)(null));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(null));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(null));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(null));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(null));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(false));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(boolean)(false));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(boolean)(false));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(boolean)(false));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(false));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(boolean)(false));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(false));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(boolean)(false));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(false));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((boolean)(false));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((boolean)(false));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((boolean)(false));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)TypeUtil.castToChar(false));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(boolean)(true));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(boolean)(true));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(boolean)(true));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(boolean)(true));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Boolean)(boolean)(true));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Boolean)(boolean)(true));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Boolean)(boolean)(true));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Boolean)(boolean)(true));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((boolean)(true));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((boolean)(true));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((boolean)(true));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((boolean)(true));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)TypeUtil.castToChar(true));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(0));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(0));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(0));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(0));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(0));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(0));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(0));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(0));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(0));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(0));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(0));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(0));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(byte)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(byte)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(byte)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(byte)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Byte)(byte)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Byte)(byte)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Byte)(byte)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Byte)(byte)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((byte)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((byte)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((byte)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((byte)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(0));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(0));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(0));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(0));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(0));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(0));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(0));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(0));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(0));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(0));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(0));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(0));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(((char)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(((char)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((char)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Character)(char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((char)(((char)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((char)(((char)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((char)(((char)Short.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((char)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(0));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(0));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(0));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(0));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(0));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(0));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(0));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(0));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(0));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(0));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(0));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(0));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(((short)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((short)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Short)(short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((short)(((short)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((short)(((short)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((short)(((short)Byte.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((short)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(0));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(0));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(0));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(0));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(0));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(0));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(0));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(0));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(0));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(0));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(0));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(0));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((int)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Byte.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((int)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Short.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((int)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Short.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((int)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(((int)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(((int)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(((int)Character.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((int)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MAX_SAFE_INT+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(TypeUtil.MAX_SAFE_INT+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(TypeUtil.MAX_SAFE_INT+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Integer)(int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((int)(TypeUtil.MIN_SAFE_INT-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((int)(TypeUtil.MIN_SAFE_INT-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(TypeUtil.MIN_SAFE_INT-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(0));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(0));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(0));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(0));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(0));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(0));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(0));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(0));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(0));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(0));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(0));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(0));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Byte.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Short.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Short.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Character.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Integer.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)Integer.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MAX_SAFE_LONG)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)TypeUtil.MAX_SAFE_LONG)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Long)(long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((long)(((long)TypeUtil.MIN_SAFE_LONG)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((long)TypeUtil.MIN_SAFE_LONG)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(0.0F));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(0.0F));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(0.0F));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(0.0F));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(0.0F));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(0.0F));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(0.0F));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(0.0F));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(0.0F));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(0.0F));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(0.0F));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(0.0F));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-0.0F));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(-0.0F));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(-0.0F));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(-0.0F));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-0.0F));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(-0.0F));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(-0.0F));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(-0.0F));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-0.0F));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(-0.0F));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(-0.0F));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(-0.0F));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-0.0F));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Byte.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Short.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Short.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Character.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Integer.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Integer.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Long.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(((float)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(((float)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(((float)Long.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((float)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MIN_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.MIN_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.MAX_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.MAX_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(float)(Float.NaN));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(float)(Float.NaN));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(float)(Float.NaN));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(float)(Float.NaN));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Float)(float)(Float.NaN));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Float)(float)(Float.NaN));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Float)(float)(Float.NaN));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Float)(float)(Float.NaN));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((float)(Float.NaN));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((float)(Float.NaN));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((float)(Float.NaN));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((float)(Float.NaN));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Float.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(0.0D));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(0.0D));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(0.0D));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(0.0D));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(0.0D));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(0.0D));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(0.0D));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(0.0D));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(0.0D));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(0.0D));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(0.0D));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(0.0D));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-0.0D));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(-0.0D));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(-0.0D));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(-0.0D));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-0.0D));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(-0.0D));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(-0.0D));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(-0.0D));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-0.0D));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(-0.0D));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(-0.0D));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(-0.0D));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-0.0D));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(2));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(2));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(2));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(2));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(2));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(2));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(2));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(2));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(2));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(2));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(2));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(2));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(2));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Byte.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Byte.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Byte.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Byte.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Byte.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Byte.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Short.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Short.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Short.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Short.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Short.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Short.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Short.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Character.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Character.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Character.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Character.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)TypeUtil.MAX_SAFE_INT)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)TypeUtil.MAX_SAFE_INT)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)TypeUtil.MIN_SAFE_INT)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)TypeUtil.MIN_SAFE_INT)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Integer.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Integer.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Integer.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Integer.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Integer.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Integer.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MAX_VALUE)+1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Long.MAX_VALUE)+1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Long.MAX_VALUE)+1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Long.MAX_VALUE)+1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(((double)Long.MIN_VALUE)-1));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(((double)Long.MIN_VALUE)-1));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(((double)Long.MIN_VALUE)-1));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(((double)Long.MIN_VALUE)-1));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MIN_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MIN_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Float.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Float.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Float.MIN_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Float.MIN_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Float.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Float.MAX_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Float.MAX_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Float.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Float.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Float.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Float.MAX_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Float.MAX_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Float.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MIN_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MIN_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.MIN_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MIN_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.MIN_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.MIN_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.MIN_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Double.MIN_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.MAX_VALUE));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.MAX_VALUE));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.MAX_VALUE));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.MAX_VALUE));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.MAX_VALUE));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.MAX_VALUE));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.MAX_VALUE));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Double.MAX_VALUE));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    @Override boolean invokecontainsObject(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Object)(double)(Double.NaN));}
    @Override boolean invokeremoveValObject(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Object)(double)(Double.NaN));}
    @Override int invokelastIndexOfObject(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Object)(double)(Double.NaN));}
    @Override int invokesearchObject(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Object)(double)(Double.NaN));}
    @Override boolean invokecontainsBoxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((Double)(double)(Double.NaN));}
    @Override boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((Double)(double)(Double.NaN));}
    @Override int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((Double)(double)(Double.NaN));}
    @Override int invokesearchBoxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((Double)(double)(Double.NaN));}
    @Override boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor){return seqMonitor.seq.contains((double)(Double.NaN));}
    @Override boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor){
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
    @Override int invokeindexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).indexOf((double)(Double.NaN));}
    @Override int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor){return ((OmniList.OfChar)seqMonitor.seq).lastIndexOf((double)(Double.NaN));}
    @Override int invokesearchUnboxed(CharSeqMonitor seqMonitor){return ((OmniStack.OfChar)seqMonitor.seq).search((double)(Double.NaN));}
    void addEqualsVal(CharSeqMonitor seqMonitor){
      seqMonitor.seq.add((char)(Double.NaN));
      ++seqMonitor.expectedSeqModCount;
      ++seqMonitor.expectedParentModCount;
      ++seqMonitor.expectedRootModCount;
      ++seqMonitor.expectedSeqSize;
      ++seqMonitor.expectedParentSize;
      ++seqMonitor.expectedRootSize;
    }
    void addNotEqualsVal(CharSeqMonitor seqMonitor){
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
    boolean invokecontains(CharSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    boolean invokeremoveVal(CharSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokeindexOf(CharSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokelastIndexOf(CharSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    int invokesearch(CharSeqMonitor seqMonitor,QueryCastType queryCastType){
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
    abstract boolean invokecontainsObject(CharSeqMonitor seqMonitor);
    abstract boolean invokecontainsBoxed(CharSeqMonitor seqMonitor);
    abstract boolean invokecontainsUnboxed(CharSeqMonitor seqMonitor);
    abstract boolean invokeremoveValObject(CharSeqMonitor seqMonitor);
    abstract boolean invokeremoveValBoxed(CharSeqMonitor seqMonitor);
    abstract boolean invokeremoveValUnboxed(CharSeqMonitor seqMonitor);
    abstract int invokeindexOfObject(CharSeqMonitor seqMonitor);
    abstract int invokeindexOfBoxed(CharSeqMonitor seqMonitor);
    abstract int invokeindexOfUnboxed(CharSeqMonitor seqMonitor);
    abstract int invokelastIndexOfObject(CharSeqMonitor seqMonitor);
    abstract int invokelastIndexOfBoxed(CharSeqMonitor seqMonitor);
    abstract int invokelastIndexOfUnboxed(CharSeqMonitor seqMonitor);
    abstract int invokesearchObject(CharSeqMonitor seqMonitor);
    abstract int invokesearchBoxed(CharSeqMonitor seqMonitor);
    abstract int invokesearchUnboxed(CharSeqMonitor seqMonitor);
    abstract void addEqualsVal(CharSeqMonitor seqMonitor);
    abstract void addNotEqualsVal(CharSeqMonitor seqMonitor);
    void initDoesNotContain(CharSeqMonitor seqMonitor){
      for(int i=0;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
    }
    int initContainsEnd(CharSeqMonitor seqMonitor){
      Assertions.assertEquals(0,seqMonitor.expectedSeqSize);
      for(int i=0;i<99;++i){
        addNotEqualsVal(seqMonitor);
      }
      addEqualsVal(seqMonitor);
      return seqMonitor.expectedSeqSize-1;
    }
    int initContainsMiddle(CharSeqMonitor seqMonitor){
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
    int initContainsBeginning(CharSeqMonitor seqMonitor){
      addEqualsVal(seqMonitor);
      for(int i=1;i<100;++i){
        addNotEqualsVal(seqMonitor);
      }
      return 0;
    }
  };
  private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,char[] arr){
    for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTochar(v);
    }
    for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTochar(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTochar(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertTochar(v);
    }
  }
  final StructType structType;
  final NestedType nestedType;
  final CheckedType checkedType;
  final int initialCapacity;
  final int rootPreAlloc;
  final int parentPreAlloc;
  final int parentPostAlloc;
  final int rootPostAlloc;
  final OmniCollection.OfChar root;
  final OmniCollection.OfChar parent;
  final OmniCollection.OfChar seq;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  CharSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType){
    this.structType=structType;
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
    switch(structType){
      case ARRSEQ:
        if(nestedType==NestedType.SUBLIST){
          this.rootPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
          this.parentPreAlloc=DEFAULT_PRE_AND_POST_ALLOC;
          this.parentPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
          this.rootPostAlloc=DEFAULT_PRE_AND_POST_ALLOC;
          int rootSize;
          char[] arr=new char[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
          initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
          this.root=checkedType.checked
            ?new CharArrSeq.CheckedList(rootSize,arr)
            :new CharArrSeq.UncheckedList(rootSize,arr);
          this.parent=((OmniList.OfChar)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
          this.seq=((OmniList.OfChar)parent).subList(parentPreAlloc,parentPreAlloc);
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
                ?new CharArrSeq.CheckedStack()
                :new CharArrSeq.UncheckedStack();
              break;
            case LIST:
              this.root=checkedType.checked
                ?new CharArrSeq.CheckedList()
                :new CharArrSeq.UncheckedList();
          }
          this.parent=root;
          this.seq=root;
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  CharSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
    this(structType,nestedType,checkedType,initialCapacity,0,0,0,0);
  }
  CharSeqMonitor(final StructType structType, final CheckedType checkedType,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this(structType,NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
  }
  CharSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this.structType=structType;
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    this.initialCapacity=initialCapacity;
    this.rootPreAlloc=rootPreAlloc;
    this.parentPreAlloc=parentPreAlloc;
    this.parentPostAlloc=parentPostAlloc;
    this.rootPostAlloc=rootPostAlloc;
    switch(structType){
      case ARRSEQ:
        int rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
        char[] arr;
        if(rootSize==0){
          switch(initialCapacity){
            case 0:
              arr=null;
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              arr=OmniArray.OfChar.DEFAULT_ARR;
              break;
            default:
              arr=new char[initialCapacity];
          }
        }else{
          arr=new char[Math.max(initialCapacity,rootSize)];
        }
        initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
        this.root=nestedType==NestedType.STACK
          ?checkedType.checked
            ?new CharArrSeq.CheckedStack(rootSize,arr)
            :new CharArrSeq.UncheckedStack(rootSize,arr)
          :checkedType.checked
            ?new CharArrSeq.CheckedList(rootSize,arr)
            :new CharArrSeq.UncheckedList(rootSize,arr);
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    switch(nestedType){
      case SUBLIST:
        this.parent=((OmniList.OfChar)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfChar)parent).subList(parentPreAlloc,parentPreAlloc);
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
  static abstract class MonitoredComparator implements CharComparator
   ,Comparator
  {
    public abstract int compare(char val1, char val2);
    public int compare(Object val1,Object val2){
      return compare((char)val1,(char)val2);
    }
  }
  static class MonitoredUnaryOperator implements CharUnaryOperator
    ,UnaryOperator
  {
    ArrayList encounteredValues=new ArrayList();
    public char applyAsChar(char val){
      encounteredValues.add(val);
      return (char)(val+1);
    }
    public Object apply(Object val){
      return applyAsChar((char)val);
    }
  }
  static class MonitoredArrayConstructor
    implements IntFunction<Character[]>
  {
    int numCalls;
    @Override public Character[] apply(int arrSize){
      ++numCalls;
      return new Character[arrSize];
    }
  }
  static class MonitoredConsumer implements CharConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(char val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((char)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(char val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  abstract class ItrMonitor{
    final OmniIterator.OfChar itr;
    int expectedCursor;
    int expectedLastRet;
    public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
      int expectedBound=nestedType.forwardIteration?expectedSeqSize:0;
      if(functionCallType==FunctionCallType.Boxed){
        itr.forEachRemaining((Consumer)action);
      }else
      {
        itr.forEachRemaining((CharConsumer)action);
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
    ItrMonitor(OmniIterator.OfChar itr,int expectedCursor){
      this.itr=itr;
      this.expectedCursor=expectedCursor;
      this.expectedLastRet=-1;
    }
    public void iterateReverse(){
      ((OmniListIterator.OfChar)itr).previousChar();
      expectedLastRet=--expectedCursor;
    }
    public CharSeqMonitor getSeqMonitor(){
      return CharSeqMonitor.this;
    }
    public abstract void verifyNext(int expectedVal,CharOutputTestArgType outputType);
    public void verifyPrevious(int expectedVal,CharOutputTestArgType outputType){
      throw new UnsupportedOperationException();
    }
    public abstract void verifyIteratorState();
    public void set(int v,CharInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfChar)itr,v);
    }
    public void set(int v){
      set(v,CharInputTestArgType.ARRAY_TYPE);
    }
    public void add(int v,CharInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    public void add(int v){
      add(v,CharInputTestArgType.ARRAY_TYPE);
    }
    public abstract void iterateForward();
    public abstract void remove();
    public boolean hasNext(){
      return itr.hasNext();
    }
    public boolean hasPrevious(){
      return ((OmniListIterator.OfChar)itr).hasPrevious();
    }
    public int nextIndex(){
      return ((OmniListIterator.OfChar)itr).nextIndex();
    }
    public int previousIndex(){
      return ((OmniListIterator.OfChar)itr).previousIndex();
    }
  }
  private class UncheckedArrSeqItrMonitor extends ItrMonitor{
    private UncheckedArrSeqItrMonitor(OmniIterator.OfChar itr,int expectedCursor){
      super(itr,expectedCursor);
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case STACK:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.parent(itr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,CharInputTestArgType inputArgType){
      inputArgType.callListItrAdd((OmniListIterator.OfChar)itr,v);
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
      itr.nextChar();
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyNext(int expectedVal,CharOutputTestArgType outputType){
      outputType.verifyItrNext(itr,expectedVal);
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyPrevious(int expectedVal,CharOutputTestArgType outputType){
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
  private class CheckedArrSeqItrMonitor extends UncheckedArrSeqItrMonitor{
    int expectedItrModCount;
    private CheckedArrSeqItrMonitor(OmniIterator.OfChar itr,int expectedCursor){
      super(itr,expectedCursor);
      this.expectedItrModCount=expectedRootModCount;
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.CharArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case STACK:
          actualCursor=FieldAccessor.CharArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.CharArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.CharArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,CharInputTestArgType inputArgType){
      super.add(v,inputArgType);
      ++expectedItrModCount;
    }
    @Override public void remove(){
      super.remove();
      ++expectedItrModCount;
    }
  }
  public ItrMonitor getItrMonitor(){
    var itr=seq.iterator();
    switch(structType){
      case ARRSEQ:
        int expectedCursor=nestedType==NestedType.STACK?((CharArrSeq)root).size:0;
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,expectedCursor)
          :new UncheckedArrSeqItrMonitor(itr,expectedCursor);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public ItrMonitor getListItrMonitor(){
    var itr=((OmniList.OfChar)seq).listIterator();
    switch(structType){
      case ARRSEQ:
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,0)
          :new UncheckedArrSeqItrMonitor(itr,0);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public ItrMonitor getListItrMonitor(int index){
    var itr=((OmniList.OfChar)seq).listIterator(index);
    switch(structType){
      case ARRSEQ:
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,index)
          :new UncheckedArrSeqItrMonitor(itr,index);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public ItrMonitor getItrMonitor(ItrType itrType){
    switch(itrType){
      case Itr:
        return getItrMonitor();
      case ListItr:
        return getListItrMonitor();
      default:
        throw new Error("unknown itr type "+itrType);
    }
  }
  public ItrMonitor getItrMonitor(SequenceLocation seqLocation,ItrType itrType){
    switch(seqLocation){
      case BEGINNING:
        switch(itrType){
          case ListItr:
            return getListItrMonitor();
          case Itr:
            return getItrMonitor();
          default:
            throw new Error("Unknown itr type "+itrType);
        }
      case MIDDLE:
        return getListItrMonitor(expectedSeqSize/2);
      case END:
        return getListItrMonitor(expectedSeqSize);
      default:
        throw new Error("Unknown sequence location "+seqLocation);
    } 
  }
  public ItrMonitor getListItrMonitor(SequenceLocation seqLocation){
    switch(seqLocation){
      case BEGINNING:
        return getListItrMonitor();
      case MIDDLE:
        return getListItrMonitor(expectedSeqSize/2);
      case END:
        return getListItrMonitor(expectedSeqSize);
      default:
        throw new Error("Unknown sequence location "+seqLocation);
    }
  }
  public static abstract class SequenceVerificationItr{
    public abstract void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val);
    public abstract SequenceVerificationItr getPositiveOffset(int i);
    public abstract SequenceVerificationItr skip(int i);
    public abstract boolean equals(Object val);
    final CharSeqMonitor seqMonitor;
    SequenceVerificationItr(CharSeqMonitor seqMonitor){
      this.seqMonitor=seqMonitor;
    }
    public SequenceVerificationItr verifyAscending(int v,CharInputTestArgType inputArgType,int length){
      for(int i=0;i<length;++i,++v){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public void verifyIndexAndIterate(int val){
      verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,val);
    }
    public SequenceVerificationItr verifyAscending(int length){
      return verifyAscending(0,CharInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(int v,int length){
      return verifyAscending(v,CharInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(CharInputTestArgType inputArgType,int length){
      return verifyAscending(0,inputArgType,length);
    }
    public SequenceVerificationItr verifyDescending(int length){
      return verifyDescending(CharInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyDescending(CharInputTestArgType inputArgType,int length){
      for(int i=0,v=length;i<length;++i){
        verifyIndexAndIterate(inputArgType,--v);
      }
      return this;
    }
    public SequenceVerificationItr verifyMidPointInsertion(int length){
      return verifyMidPointInsertion(CharInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyMidPointInsertion(CharInputTestArgType inputArgType,final int length){
      int i=0;
      for(int v=1,halfLength=length/2;i<halfLength;++i,v+=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      for(int v=length-2;i<length;++i,v-=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyParentPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
        verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyRootPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc;i<seqMonitor.rootPostAlloc;++i,++v){
        verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyIllegalAdd(){
      verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,0);
      return this;
    }
    public SequenceVerificationItr verifyPostAlloc(){
      return verifyPostAlloc(PreModScenario.NoMod);
    }
    public SequenceVerificationItr verifyPostAlloc(PreModScenario preModScenario){
      if(preModScenario==PreModScenario.ModSeq){verifyIllegalAdd();}
      verifyParentPostAlloc();
      if(preModScenario==PreModScenario.ModParent){verifyIllegalAdd();}
      verifyRootPostAlloc();
      if(preModScenario==PreModScenario.ModRoot){verifyIllegalAdd();}
      return this;
    }
  }
  private static class ArrSeqSequenceVerificationItr extends SequenceVerificationItr{
    final char[] arr;
    int offset;
    private ArrSeqSequenceVerificationItr(CharSeqMonitor seqMonitor,int offset,char[] arr){
      super(seqMonitor);
      this.arr=arr;
      this.offset=offset;
    }
    @Override public void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val){
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
  }
  public SequenceVerificationItr verifyPreAlloc(){
    switch(structType){
      case ARRSEQ:{
        var arr=((CharArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
          CharInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
        }
        return new ArrSeqSequenceVerificationItr(this,offset,arr);
      }
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public void illegalAdd(PreModScenario preModScenario){
    switch(preModScenario){
      case ModSeq:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case ModParent:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        break;
      case ModRoot:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public void add(int index,int val,CharInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void add(int index,int val){
    add(index,val,CharInputTestArgType.ARRAY_TYPE);
  }
  public boolean add(int val,CharInputTestArgType inputArgType){
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
  public boolean add(int val){
    return add(val,CharInputTestArgType.ARRAY_TYPE);
  }
  public void push(int val,CharInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void push(int val){
    push(val,CharInputTestArgType.ARRAY_TYPE);
  }
  public void put(int index,int val,CharInputTestArgType inputArgType){
    inputArgType.callListPut(seq,index,val);
  }
  public String toString(){
    StringBuilder builder=new StringBuilder();
    switch(structType){
      case ARRSEQ:
        builder.append("CharArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    return builder.append('}').toString();
  }
  public void verifyStructuralIntegrity(){
    switch(structType){
      case ARRSEQ:
        switch(nestedType){
          case STACK:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfChar actualSeqParent;
            CharArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfChar actualParentParent;
            CharArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfChar)FieldAccessor.CharArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(CharArrSeq)FieldAccessor.CharArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.CharArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfChar)FieldAccessor.CharArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(CharArrSeq)FieldAccessor.CharArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAccessor.CharArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfChar)FieldAccessor.CharArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(CharArrSeq)FieldAccessor.CharArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfChar)FieldAccessor.CharArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(CharArrSeq)FieldAccessor.CharArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(parent);
            }
            Assertions.assertSame(root,actualSeqRoot);
            Assertions.assertSame(root,actualParentRoot);
            Assertions.assertSame(parent,actualSeqParent);
            Assertions.assertNull(actualParentParent);
            Assertions.assertEquals(expectedSeqSize,actualSeqSize);
            Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
            break;
          default:
            throw new Error("Unknown structType "+structType);
        }
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.CharArrSeq.size(root));
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public boolean isEmpty(){
    return seq.isEmpty();
  }
  public void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      seq.forEach((Consumer)action);
    }else
    {
      seq.forEach((CharConsumer)action);
    }
  }
  public void unstableSort(MonitoredComparator sorter){
    int seqSize=expectedSeqSize;
    ((OmniList.OfChar)seq).unstableSort((CharComparator)sorter);
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void replaceAll(MonitoredUnaryOperator operator,FunctionCallType functionCallType){
    int seqSize=expectedSeqSize;
    if(functionCallType==FunctionCallType.Boxed){
      ((OmniList.OfChar)seq).replaceAll((UnaryOperator)operator);
    }else
    {
      ((OmniList.OfChar)seq).replaceAll((CharUnaryOperator)operator);
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
      ((OmniList.OfChar)seq).sort((Comparator)sorter);
    }else
    {
      ((OmniList.OfChar)seq).sort((CharComparator)sorter);
    }
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableAscendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfChar)seq).stableAscendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void stableDescendingSort(){
    int seqSize=expectedSeqSize;
    ((OmniList.OfChar)seq).stableDescendingSort();
    if(seqSize>1){
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
  public void removeAt(int expectedVal,CharOutputTestArgType outputType,int index){
    outputType.verifyListRemoveAt(seq,index,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
  }
  public void get(int expectedVal,CharOutputTestArgType outputType,int index){
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
  public void pop(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    --expectedSeqSize;
    --expectedParentSize;
    --expectedRootSize;
    ++expectedSeqModCount;
    ++expectedParentModCount;
    ++expectedRootModCount;
  }
  public void poll(int expectedVal,CharOutputTestArgType outputType){
    outputType.verifyStackPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      --expectedSeqSize;
      --expectedParentSize;
      --expectedRootSize;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
    }
  }
}
