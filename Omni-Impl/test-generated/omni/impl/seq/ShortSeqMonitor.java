package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import org.junit.jupiter.api.Assertions;
import omni.impl.ShortInputTestArgType;
import omni.impl.ShortOutputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.ArrayList;
import omni.function.ShortConsumer;
class ShortSeqMonitor{
  static final int DEFAULT_PRE_AND_POST_ALLOC=5;
  static enum StructType{
    ARRSEQ;
  }
  static enum SequenceLocation{
    BEGINNING,
    MIDDLE,
    END;
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
  static enum MonitoredConsumerGen{
    NoThrow(null,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer();
      }
    },
    Throw(IndexOutOfBoundsException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new ThrowingMonitoredConsumer();
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new ThrowingMonitoredConsumer();
      }
    },
    //TODO add this test scenario
    //ModItr(ConcurrentModificationException.class,false,false,true){
    //  @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
    //    return new MonitoredConsumer(){
    //      public void accept(short val){
    //        super.accept(val);
    //        itrMonitor.add(0);
    //      }
    //    };
    //  }
    //},
    ModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
          }
        };
      }
    },
    ModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
          }
        };
      }
    },
    ModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
          }
        };
      }
    },
    //TODO add this test scenario
    //ThrowModItr(ConcurrentModificationException.class,false,false,true){
    //  @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
    //    return new MonitoredConsumer(){
    //      public void accept(short val){
    //        super.accept(val);
    //        itrMonitor.add(0);
    //        throw new IndexOutOfBoundsException();
    //      }
    //    };
    //  }
    //},
    ThrowModSeq(ConcurrentModificationException.class,true,true,true){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModSeq);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModParent(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModParent);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    },
    ThrowModRoot(ConcurrentModificationException.class,false,true,false){
      @Override MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            seqMonitor.illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
      @Override MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
        return new MonitoredConsumer(){
          public void accept(short val){
            super.accept(val);
            itrMonitor.getSeqMonitor().illegalAdd(PreModScenario.ModRoot);
            throw new IndexOutOfBoundsException();
          }
        };
      }
    };
    final Class<? extends Throwable> expectedException;
    final boolean appliesToRoot;
    final boolean appliesToSubList;
    final boolean appliesToRootItr;
    MonitoredConsumerGen(Class<? extends Throwable> expectedException,boolean appliesToRoot,boolean appliesToSubList,boolean appliesToRootItr){
      this.expectedException=expectedException;
      this.appliesToRoot=appliesToRoot;
      this.appliesToSubList=appliesToSubList;
      this.appliesToRootItr=appliesToRootItr;
    }
    MonitoredConsumer getMonitoredConsumer(ShortSeqMonitor seqMonitor){
      throw new UnsupportedOperationException();
    }
    MonitoredConsumer getMonitoredConsumer(ItrMonitor itrMonitor){
      throw new UnsupportedOperationException();
    }
  }
  private static void initArray(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,short[] arr){
    for(int i=0,v=Integer.MIN_VALUE,bound=rootPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToshort(v);
    }
    for(int i=rootPreAlloc,v=Integer.MIN_VALUE+rootPreAlloc,bound=i+parentPreAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToshort(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc,bound=i+parentPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToshort(v);
    }
    for(int i=rootPreAlloc+parentPreAlloc+parentPostAlloc,v=Integer.MAX_VALUE-rootPostAlloc,bound=i+rootPostAlloc;i<bound;++i,++v){
      arr[i]=TypeConversionUtil.convertToshort(v);
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
  final OmniCollection.OfShort root;
  final OmniCollection.OfShort parent;
  final OmniCollection.OfShort seq;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  ShortSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType){
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
          short[] arr=new short[rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc];
          initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
          this.root=checkedType.checked
            ?new ShortArrSeq.CheckedList(rootSize,arr)
            :new ShortArrSeq.UncheckedList(rootSize,arr);
          this.parent=((OmniList.OfShort)root).subList(rootPreAlloc,rootSize-rootPostAlloc);
          this.seq=((OmniList.OfShort)parent).subList(parentPreAlloc,parentPreAlloc);
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
                ?new ShortArrSeq.CheckedStack()
                :new ShortArrSeq.UncheckedStack();
              break;
            case LIST:
              this.root=checkedType.checked
                ?new ShortArrSeq.CheckedList()
                :new ShortArrSeq.UncheckedList();
          }
          this.parent=root;
          this.seq=root;
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  ShortSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
    this(structType,nestedType,checkedType,initialCapacity,0,0,0,0);
  }
  ShortSeqMonitor(final StructType structType, final CheckedType checkedType,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this(structType,NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
  }
  ShortSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
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
        short[] arr;
        if(rootSize==0){
          switch(initialCapacity){
            case 0:
              arr=null;
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              arr=OmniArray.OfShort.DEFAULT_ARR;
              break;
            default:
              arr=new short[initialCapacity];
          }
        }else{
          arr=new short[Math.max(initialCapacity,rootSize)];
        }
        initArray(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,arr);
        this.root=nestedType==NestedType.STACK
          ?checkedType.checked
            ?new ShortArrSeq.CheckedStack(rootSize,arr)
            :new ShortArrSeq.UncheckedStack(rootSize,arr)
          :checkedType.checked
            ?new ShortArrSeq.CheckedList(rootSize,arr)
            :new ShortArrSeq.UncheckedList(rootSize,arr);
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    switch(nestedType){
      case SUBLIST:
        this.parent=((OmniList.OfShort)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfShort)parent).subList(parentPreAlloc,parentPreAlloc);
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
  static class MonitoredConsumer implements ShortConsumer
    ,Consumer
  {
    ArrayList encounteredValues=new ArrayList();
    public void accept(short val){
      encounteredValues.add(val);
    }
    public void accept(Object val){
      accept((short)val);
    }
  }
  public static class ThrowingMonitoredConsumer extends MonitoredConsumer{
    public void accept(short val){
      super.accept(val);
      throw new IndexOutOfBoundsException();
    }
  }
  abstract class ItrMonitor{
    final OmniIterator.OfShort itr;
    int expectedCursor;
    int expectedLastRet;
    public void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
      int expectedBound=nestedType.forwardIteration?expectedSeqSize:0;
      if(functionCallType==FunctionCallType.Boxed){
        itr.forEachRemaining((Consumer)action);
      }else
      {
        itr.forEachRemaining((ShortConsumer)action);
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
    ItrMonitor(OmniIterator.OfShort itr,int expectedCursor){
      this.itr=itr;
      this.expectedCursor=expectedCursor;
      this.expectedLastRet=-1;
    }
    public void iterateReverse(){
      ((OmniListIterator.OfShort)itr).previousShort();
      expectedLastRet=--expectedCursor;
    }
    public ShortSeqMonitor getSeqMonitor(){
      return ShortSeqMonitor.this;
    }
    public abstract void verifyNext(int expectedVal,ShortOutputTestArgType outputType);
    public void verifyPrevious(int expectedVal,ShortOutputTestArgType outputType){
      throw new UnsupportedOperationException();
    }
    public abstract void verifyIteratorState();
    public void set(int v,ShortInputTestArgType inputArgType){
       inputArgType.callListItrSet((OmniListIterator.OfShort)itr,v);
    }
    public void set(int v){
      set(v,ShortInputTestArgType.ARRAY_TYPE);
    }
    public void add(int v,ShortInputTestArgType inputArgType){
      throw new UnsupportedOperationException();
    }
    public void add(int v){
      add(v,ShortInputTestArgType.ARRAY_TYPE);
    }
    public abstract void iterateForward();
    public abstract void remove();
    public boolean hasNext(){
      return itr.hasNext();
    }
    public boolean hasPrevious(){
      return ((OmniListIterator.OfShort)itr).hasPrevious();
    }
    public int nextIndex(){
      return ((OmniListIterator.OfShort)itr).nextIndex();
    }
    public int previousIndex(){
      return ((OmniListIterator.OfShort)itr).previousIndex();
    }
  }
  private class UncheckedArrSeqItrMonitor extends ItrMonitor{
    private UncheckedArrSeqItrMonitor(OmniIterator.OfShort itr,int expectedCursor){
      super(itr,expectedCursor);
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.ShortArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case STACK:
          actualCursor=FieldAccessor.ShortArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.ShortArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.UncheckedSubList.Itr.parent(itr);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,ShortInputTestArgType inputArgType){
      inputArgType.callListItrAdd((OmniListIterator.OfShort)itr,v);
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
      itr.nextShort();
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyNext(int expectedVal,ShortOutputTestArgType outputType){
      outputType.verifyItrNext(itr,expectedVal);
      expectedLastRet=nestedType==NestedType.STACK
        ?--expectedCursor
        :expectedCursor++;
    }
    @Override public void verifyPrevious(int expectedVal,ShortOutputTestArgType outputType){
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
    private CheckedArrSeqItrMonitor(OmniIterator.OfShort itr,int expectedCursor){
      super(itr,expectedCursor);
      this.expectedItrModCount=expectedRootModCount;
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.ShortArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.ShortArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.ShortArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case STACK:
          actualCursor=FieldAccessor.ShortArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.ShortArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.ShortArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.ShortArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.ShortArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.ShortArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.ShortArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    @Override public void add(int v,ShortInputTestArgType inputArgType){
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
        int expectedCursor=nestedType==NestedType.STACK?((ShortArrSeq)root).size:0;
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,expectedCursor)
          :new UncheckedArrSeqItrMonitor(itr,expectedCursor);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public ItrMonitor getListItrMonitor(){
    var itr=((OmniList.OfShort)seq).listIterator();
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
    var itr=((OmniList.OfShort)seq).listIterator(index);
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
    public abstract void verifyIndexAndIterate(ShortInputTestArgType inputArgType,int val);
    public abstract SequenceVerificationItr getPositiveOffset(int i);
    public abstract boolean equals(Object val);
    final ShortSeqMonitor seqMonitor;
    SequenceVerificationItr(ShortSeqMonitor seqMonitor){
      this.seqMonitor=seqMonitor;
    }
    public SequenceVerificationItr verifyAscending(int v,ShortInputTestArgType inputArgType,int length){
      for(int i=0;i<length;++i,++v){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyAscending(int length){
      return verifyAscending(0,ShortInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(int v,int length){
      return verifyAscending(v,ShortInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyAscending(ShortInputTestArgType inputArgType,int length){
      return verifyAscending(0,inputArgType,length);
    }
    public SequenceVerificationItr verifyDescending(int length){
      return verifyDescending(ShortInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyDescending(ShortInputTestArgType inputArgType,int length){
      for(int i=0,v=length;i<length;++i){
        verifyIndexAndIterate(inputArgType,--v);
      }
      return this;
    }
    public SequenceVerificationItr verifyMidPointInsertion(int length){
      return verifyMidPointInsertion(ShortInputTestArgType.ARRAY_TYPE,length);
    }
    public SequenceVerificationItr verifyMidPointInsertion(ShortInputTestArgType inputArgType,final int length){
      int i=0;
      for(int v=0,halfLength=length/2;i<halfLength;++i,v+=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      for(int v=length-1;i<length;++i,v-=2){
        verifyIndexAndIterate(inputArgType,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyParentPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
        verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyRootPostAlloc(){
      for(int i=0,v=Integer.MAX_VALUE-seqMonitor.rootPostAlloc;i<seqMonitor.rootPostAlloc;++i,++v){
        verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
      }
      return this;
    }
    public SequenceVerificationItr verifyIllegalAdd(){
      verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,0);
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
    final short[] arr;
    int offset;
    private ArrSeqSequenceVerificationItr(ShortSeqMonitor seqMonitor,int offset,short[] arr){
      super(seqMonitor);
      this.arr=arr;
      this.offset=offset;
    }
    @Override public void verifyIndexAndIterate(ShortInputTestArgType inputArgType,int val){
      inputArgType.verifyVal(val,arr[offset++]);
    }
    @Override public SequenceVerificationItr getPositiveOffset(int i){
      if(i<0){
        throw new Error("offset cannot be negative: "+i);
      }
      return new ArrSeqSequenceVerificationItr(seqMonitor,i+offset,arr);
    }
    @Override public boolean equals(Object val){
      final ArrSeqSequenceVerificationItr that;
      return val==this || (val instanceof ArrSeqSequenceVerificationItr && (that=(ArrSeqSequenceVerificationItr)val).arr==this.arr && that.offset==this.offset);
    }
  }
  public SequenceVerificationItr verifyPreAlloc(){
    switch(structType){
      case ARRSEQ:{
        var arr=((ShortArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
          ShortInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
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
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case ModParent:
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        break;
      case ModRoot:
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public void add(int index,int val,ShortInputTestArgType inputArgType){
    inputArgType.callListAdd(seq,index,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void add(int index,int val){
    add(index,val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public boolean add(int val,ShortInputTestArgType inputArgType){
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
    return add(val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public void push(int val,ShortInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void push(int val){
    push(val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public String toString(){
    StringBuilder builder=new StringBuilder();
    switch(structType){
      case ARRSEQ:
        builder.append("ShortArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfShort actualSeqParent;
            ShortArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfShort actualParentParent;
            ShortArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.ShortArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAccessor.ShortArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAccessor.ShortArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAccessor.ShortArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.ShortArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAccessor.ShortArrSeq.UncheckedSubList.size(parent);
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
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.ShortArrSeq.size(root));
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
      seq.forEach((ShortConsumer)action);
    }
  }
}
