package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import org.junit.jupiter.api.Assertions;
import omni.impl.CharInputTestArgType;
import omni.impl.FunctionCallType;
import omni.api.OmniIterator;
import omni.api.OmniListIterator;
import java.util.ConcurrentModificationException;
class CharSeqMonitor{
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
  PreModScenario activePreModScenario;
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
  public static abstract class SeqMonitorItr{
    public abstract void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val);
    public abstract SeqMonitorItr getPositiveOffset(int i);
    public abstract boolean equals(Object val);
    private static class OfArrSeq extends SeqMonitorItr{
      final char[] arr;
      int offset;
      private OfArrSeq(int offset,char[] arr){
        this.arr=arr;
        this.offset=offset;
      }
      public void verifyIndexAndIterate(CharInputTestArgType inputArgType,int val){
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
  private class UncheckedArrSeqItrMonitor extends CharItrMonitor{
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
  public CharItrMonitor getItrMonitor(){
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
  public CharItrMonitor getListItrMonitor(){
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
  public CharItrMonitor getListItrMonitor(int index){
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
  public SeqMonitorItr verifyPreAlloc(){
    switch(structType){
      case ARRSEQ:{
        var arr=((CharArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v){
          CharInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
        }
        return new SeqMonitorItr.OfArrSeq(offset,arr);
      }
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,int length){
    return verifyAscending(0,monitorItr,CharInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyAscending(int v,SeqMonitorItr monitorItr,int length){
    return verifyAscending(v,monitorItr,CharInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,CharInputTestArgType inputArgType,int length){
    return verifyAscending(0,monitorItr,inputArgType,length);
  }
  public SeqMonitorItr verifyAscending(int v,SeqMonitorItr monitorItr,CharInputTestArgType inputArgType,int length){
    for(int i=0;i<length;++i,++v){
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,int length){
     return verifyDescending(monitorItr,CharInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,CharInputTestArgType inputArgType,int length){
    for(int i=0,v=length;i<length;++i){
      monitorItr.verifyIndexAndIterate(inputArgType,--v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,int length){
     return verifyMidPointInsertion(monitorItr,CharInputTestArgType.ARRAY_TYPE,length);
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,CharInputTestArgType inputArgType,final int length){
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
      monitorItr.verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyRootPostAlloc(SeqMonitorItr monitorItr){
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyIllegalAdd(SeqMonitorItr monitorItr){
    monitorItr.verifyIndexAndIterate(CharInputTestArgType.ARRAY_TYPE,0);
    return monitorItr;
  }
  public void illegalAdd(PreModScenario preModScenario){
    if(activePreModScenario!=null){
      throw new Error("Cannot modify sequence with "+preModScenario+" because it has already been modified with "+activePreModScenario);
    }
    switch(preModScenario){
      case ModSeq:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        activePreModScenario=preModScenario;
        break;
      case ModParent:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        activePreModScenario=preModScenario;
        break;
      case ModRoot:
        CharInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
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
}
