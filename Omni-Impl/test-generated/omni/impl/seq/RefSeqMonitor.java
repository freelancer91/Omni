package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import org.junit.jupiter.api.Assertions;
import omni.impl.RefInputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.ConcurrentModificationException;
class RefSeqMonitor{
  static final int DEFAULT_PRE_AND_POST_ALLOC=5;
  static void verifyRangeIsNull(Object[] arr,int offset,int bound){
    for(int i=offset;i<bound;++i){Assertions.assertNull(arr[i]);}
  }
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
    LIST,
    STACK,
    SUBLIST,
  }
  static enum PreModScenario{
    ModSeq(ConcurrentModificationException.class),
    ModParent(ConcurrentModificationException.class),
    ModRoot(ConcurrentModificationException.class),
    NoMod(null);
    final Class<? extends Throwable> expectedException;
    PreModScenario(Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
    }
  }
  static enum ListItrSetExceptionScenario{
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
    ListItrSetExceptionScenario(Class<? extends Throwable> expectedException,PreModScenario preModScenario){
      this.expectedException=expectedException;
      this.preModScenario=preModScenario;
    }
  }
  static enum FunctionExceptionScenario{
    ModItr(ConcurrentModificationException.class),
    ModItrThrow(ConcurrentModificationException.class),
    ModSeq(ConcurrentModificationException.class),
    ModSeqThrow(ConcurrentModificationException.class),
    ModParent(ConcurrentModificationException.class),
    ModParentThrow(ConcurrentModificationException.class),
    ModRoot(ConcurrentModificationException.class),
    ModRootThrow(ConcurrentModificationException.class),
    Throw(IndexOutOfBoundsException.class),
    NoMod(null);
    final Class<? extends Throwable> expectedException;
    FunctionExceptionScenario(Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
    }
  }
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
  final StructType structType;
  final NestedType nestedType;
  final CheckedType checkedType;
  final int initialCapacity;
  final int rootPreAlloc;
  final int parentPreAlloc;
  final int parentPostAlloc;
  final int rootPostAlloc;
  final OmniCollection.OfRef root;
  final OmniCollection.OfRef parent;
  final OmniCollection.OfRef seq;
  PreModScenario activePreModScenario;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  RefSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType){
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
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  RefSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity){
    this(structType,nestedType,checkedType,initialCapacity,0,0,0,0);
  }
  RefSeqMonitor(final StructType structType, final CheckedType checkedType,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
    this(structType,NestedType.SUBLIST,checkedType,OmniArray.DEFAULT_ARR_SEQ_CAP,rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc);
  }
  RefSeqMonitor(final StructType structType, final NestedType nestedType,final CheckedType checkedType,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc){
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
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
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
  public static abstract class SeqMonitorItr{
    public abstract void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val);
    public abstract SeqMonitorItr getPositiveOffset(int i);
    public abstract boolean equals(Object val);
    private static class OfArrSeq extends SeqMonitorItr{
      final Object[] arr;
      int offset;
      private OfArrSeq(int offset,Object[] arr){
        this.arr=arr;
        this.offset=offset;
      }
      public void verifyIndexAndIterate(RefInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,arr[offset++]);
      }
      public SeqMonitorItr getPositiveOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        return new OfArrSeq(i+offset,arr);
      }
      public boolean equals(Object val){
        final OfArrSeq that;
        return val==this || (val instanceof OfArrSeq && (that=(OfArrSeq)val).arr==this.arr && that.offset==this.offset);
      }
    }
  }
  private class UncheckedArrSeqItrMonitor extends RefItrMonitor{
    private UncheckedArrSeqItrMonitor(OmniIterator.OfRef itr,int expectedCursor){
      super(itr,expectedCursor);
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case STACK:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.parent(itr);
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
    private CheckedArrSeqItrMonitor(OmniIterator.OfRef itr,int expectedCursor){
      super(itr,expectedCursor);
      this.expectedItrModCount=expectedRootModCount;
    }
    @Override public void verifyIteratorState(){
      int actualCursor;
      Object actualParent;
      switch(nestedType){
        case LIST:
          actualCursor=FieldAccessor.RefArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case STACK:
          actualCursor=FieldAccessor.RefArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case SUBLIST:
          actualCursor=FieldAccessor.RefArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedItrModCount,FieldAccessor.RefArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedSubList.Itr.lastRet(itr));
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
  public RefItrMonitor getItrMonitor(){
    var itr=seq.iterator();
    switch(structType){
      case ARRSEQ:
        int expectedCursor=nestedType==NestedType.STACK?((RefArrSeq)root).size:0;
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,expectedCursor)
          :new UncheckedArrSeqItrMonitor(itr,expectedCursor);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public RefItrMonitor getListItrMonitor(){
    var itr=((OmniList.OfRef)seq).listIterator();
    switch(structType){
      case ARRSEQ:
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,0)
          :new UncheckedArrSeqItrMonitor(itr,0);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public RefItrMonitor getListItrMonitor(int index){
    var itr=((OmniList.OfRef)seq).listIterator(index);
    switch(structType){
      case ARRSEQ:
        return checkedType.checked
          ?new CheckedArrSeqItrMonitor(itr,index)
          :new UncheckedArrSeqItrMonitor(itr,index);
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public SeqMonitorItr verifyPreAlloc(){
    switch(structType){
      case ARRSEQ:{
        var arr=((RefArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
          RefInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
        }
        return new SeqMonitorItr.OfArrSeq(offset,arr);
      }
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,int length){
    return verifyAscending(0,monitorItr,RefInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyAscending(int v,SeqMonitorItr monitorItr,int length){
    return verifyAscending(v,monitorItr,RefInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,RefInputTestArgType inputArgType,int length){
    return verifyAscending(0,monitorItr,inputArgType,length);
  }
  public SeqMonitorItr verifyAscending(int v,SeqMonitorItr monitorItr,RefInputTestArgType inputArgType,int length){
    for(int i=0;i<length;++i,++v){
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,int length){
     return verifyDescending(monitorItr,RefInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,RefInputTestArgType inputArgType,int length){
    for(int i=0,v=length;i<length;++i){
      monitorItr.verifyIndexAndIterate(inputArgType,--v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,int length){
     return verifyMidPointInsertion(monitorItr,RefInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,RefInputTestArgType inputArgType,final int length){
    int i=0;
    for(int v=0,halfLength=length/2;i<halfLength;++i,v+=2){
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    for(int v=length-1;i<length;++i,v-=2){
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyParentPostAlloc(SeqMonitorItr monitorItr){
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc;i<parentPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyRootPostAlloc(SeqMonitorItr monitorItr){
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyIllegalAdd(SeqMonitorItr monitorItr){
    monitorItr.verifyIndexAndIterate(RefInputTestArgType.ARRAY_TYPE,0);
    return monitorItr;
  }
  public void illegalAdd(PreModScenario preModScenario){
    if(activePreModScenario!=null){
      throw new Error("Cannot modify sequence with "+preModScenario+" because it has already been modified with "+activePreModScenario);
    }
    switch(preModScenario){
      case ModSeq:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        activePreModScenario=preModScenario;
        break;
      case ModParent:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        activePreModScenario=preModScenario;
        break;
      case ModRoot:
        RefInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        activePreModScenario=preModScenario;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
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
  public void add(int index,int val){
    add(index,val,RefInputTestArgType.ARRAY_TYPE);
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
  public boolean add(int val){
    return add(val,RefInputTestArgType.ARRAY_TYPE);
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
  public void push(int val){
    push(val,RefInputTestArgType.ARRAY_TYPE);
  }
  public String toString(){
    StringBuilder builder=new StringBuilder();
    switch(structType){
      case ARRSEQ:
        builder.append("RefArrSeq.").append(checkedType.checked?"Checked":"Unchecked");
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
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(root));
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
              actualSeqParent=(OmniList.OfRef)FieldAccessor.RefArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(RefArrSeq)FieldAccessor.RefArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.RefArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfRef)FieldAccessor.RefArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(RefArrSeq)FieldAccessor.RefArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAccessor.RefArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfRef)FieldAccessor.RefArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(RefArrSeq)FieldAccessor.RefArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfRef)FieldAccessor.RefArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(RefArrSeq)FieldAccessor.RefArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(parent);
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
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.RefArrSeq.size(root));
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
}
