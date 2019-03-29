package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.impl.RefInputTestArgType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import omni.impl.QueryTestPrimitiveInputType;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class RefArrSeqTest{
  private static void verifyRangeIsNull(Object[] arr,int offset,int bound){
    for(int i=offset;i<bound;++i){Assertions.assertNull(arr[i]);}
  }
  private static enum StructType{
    CHECKEDSTACK(true),
    CHECKEDLIST(true),
    CHECKEDSUBLIST(true),
    UNCHECKEDSTACK(false),
    UNCHECKEDLIST(false),
    UNCHECKEDSUBLIST(false);
    boolean checked;
    StructType(boolean checked){this.checked=checked;}
  }
  private static class ConstructionArguments{
    final int initialCapacity;
    final int rootPreAlloc;
    final int rootPostAlloc;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final StructType structType;
    final OmniCollection.OfRef seq;
    final OmniCollection.OfRef parent;
    final RefArrSeq root;
    ConstructionArguments(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,StructType structType){
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.rootPreAlloc=rootPreAlloc;
      this.rootPostAlloc=rootPostAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.structType=structType;
      final int rootSize;
      if((rootSize=parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc)==0){
        this.root=structType.checked?new RefArrSeq.CheckedList():new RefArrSeq.UncheckedList();
      }else{
        Object[] arr=new Object[rootSize];
        initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
        initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=structType.checked?new RefArrSeq.CheckedList(rootSize,arr):new RefArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfRef)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
      this.seq=((OmniList.OfRef)parent).subList(parentPreAlloc,parentPreAlloc);
    }
    ConstructionArguments(int initialCapacity,StructType structType){
      this.initialCapacity=initialCapacity;
      this.rootPreAlloc=0;
      this.rootPostAlloc=0;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.structType=structType;
      switch(structType){
        case CHECKEDSTACK:
          this.root=new RefArrSeq.CheckedStack(initialCapacity);
          break;
        case UNCHECKEDSTACK:
          this.root=new RefArrSeq.UncheckedStack(initialCapacity);
          break;
        case CHECKEDLIST:
          this.root=new RefArrSeq.CheckedList(initialCapacity);
          break;
        default:
          this.root=new RefArrSeq.UncheckedList(initialCapacity);
      }
      this.parent=root;
      this.seq=root;
    }
    public String toString(){
      StringBuilder builder=new StringBuilder(structType.checked?"Checked":"Unchecked");
      switch(structType){
        case CHECKEDSTACK:
        case UNCHECKEDSTACK:
          builder.append("Stack{").append(initialCapacity);
          break;
        case CHECKEDLIST:
        case UNCHECKEDLIST:
          builder.append("List{").append(initialCapacity);
          break;
        default:
          builder.append("SubList{").append(rootPreAlloc).append(',').append(parentPreAlloc).append(',').append(parentPostAlloc).append(',').append(rootPostAlloc);
      }
      return builder.append('}').toString();
    }
    private OmniListIterator.OfRef constructSeqListIterator(){return ((OmniList.OfRef)seq).listIterator();}
    private void verifyIteratorState(Object itr,int expectedCursor,int expectedLastRet,int expectedModCount){
      int actualCursor;
      Object actualParent;
      switch(structType){
        case CHECKEDLIST:
          actualCursor=FieldAccessor.RefArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.RefArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case UNCHECKEDLIST:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case CHECKEDSTACK:
          actualCursor=FieldAccessor.RefArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.RefArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case UNCHECKEDSTACK:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case CHECKEDSUBLIST:
          actualCursor=FieldAccessor.RefArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.RefArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.RefArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          actualCursor=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.RefArrSeq.UncheckedSubList.Itr.parent(itr);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount){
      switch(structType){
        case CHECKEDSTACK:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedStack.modCount(root));
          break;
        case CHECKEDLIST:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(root));
        case UNCHECKEDSTACK:
        case UNCHECKEDLIST:
          break;
        default:
          OmniList.OfRef actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfRef actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(structType.checked){
            actualSeqParent=FieldAccessor.RefArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.RefArrSeq.CheckedSubList.root(seq);
            actualParentParent=FieldAccessor.RefArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.RefArrSeq.CheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.RefArrSeq.CheckedSubList.size(seq);
            actualParentSize=FieldAccessor.RefArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAccessor.RefArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.RefArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=FieldAccessor.RefArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.RefArrSeq.UncheckedSubList.root(seq);
            actualParentParent=FieldAccessor.RefArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.RefArrSeq.UncheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(seq);
            actualParentSize=FieldAccessor.RefArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.RefArrSeq.size(root));
    }
    private int verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=rootPreAlloc+parentPreAlloc,v=-bound;offset<bound;++offset,++v){RefInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyParentPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+parentPostAlloc,v=100;offset<bound;++offset,++v){RefInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyRootPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+rootPostAlloc,v=100+parentPostAlloc;offset<bound;++offset,++v){RefInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyIndex(int offset,RefInputTestArgType inputArgType,int v){
      inputArgType.verifyVal(v,root.arr[offset]);
      return offset+1;
    }
    private int verifyAscending(int offset,RefInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=0;offset<bound;++offset,++v){inputArgType.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyDescending(int offset,RefInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=length;offset<bound;++offset){inputArgType.verifyVal(--v,arr[offset]);}
      return offset;
    }
    private int verifyMidPointInsertion(int offset,RefInputTestArgType inputArgType,int length){
      var arr=root.arr;
      int i;
      for(int v=1,b=(i=offset)+length/2;i<b;++i,v+=2){inputArgType.verifyVal(v,arr[i]);}
      for(int v=length-2,b=i+length/2;i<b;++i,v-=2){inputArgType.verifyVal(v,arr[i]);}
      return offset+length;
    }
  }
  private static void initAscendingArray(Object[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){arr[i]=TypeConversionUtil.convertToObject(lo);}
  }
  private static final Arguments[] NON_SUBLIST_TYPES=new Arguments[]{
    Arguments.of(StructType.CHECKEDLIST),
    Arguments.of(StructType.UNCHECKEDLIST),
    Arguments.of(StructType.CHECKEDSTACK),
    Arguments.of(StructType.UNCHECKEDSTACK)
  };
  static Stream<Arguments> getArgsForNonSubListTypes(){return Stream.of(NON_SUBLIST_TYPES);}
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_happyPath(StructType structType){
    RefArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.RefArrSeq.CheckedList.modCount(seq=new RefArrSeq.CheckedList()):FieldAccessor.RefArrSeq.CheckedStack.modCount(seq=new RefArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new RefArrSeq.UncheckedList():new RefArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_Objectarr_happyPath(boolean checked,StructType structType){
    int size=5;
    Object[] arr=new Object[10];
    RefArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.RefArrSeq.CheckedList.modCount(seq=new RefArrSeq.CheckedList(size,arr)):FieldAccessor.RefArrSeq.CheckedStack.modCount(seq=new RefArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new RefArrSeq.UncheckedList(size,arr):new RefArrSeq.UncheckedStack(size,arr);
    }
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
  }
  static Stream<Arguments> getArgsFortestConstructor_int_happyPath(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
      builder.add(Arguments.of(initialCapacity,StructType.UNCHECKEDLIST));
      builder.add(Arguments.of(initialCapacity,StructType.CHECKEDLIST));
      builder.add(Arguments.of(initialCapacity,StructType.UNCHECKEDSTACK));
      builder.add(Arguments.of(initialCapacity,StructType.CHECKEDSTACK));
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getArgsFortestConstructor_int_happyPath")
  public void testConstructor_int_happyPath(int initialCapacity,StructType structType){
    RefArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.RefArrSeq.CheckedList.modCount(seq=new RefArrSeq.CheckedList(initialCapacity)):FieldAccessor.RefArrSeq.CheckedStack.modCount(seq=new RefArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new RefArrSeq.UncheckedList(initialCapacity):new RefArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfRef.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
        verifyRangeIsNull(seq.arr,0,initialCapacity);
    }
  }
  static Stream<Arguments> getListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getCollectionInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDSTACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDSTACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getStackInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDSTACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDSTACK)));
      }
    }
    return builder.build();
  }
  //TODO develop the parameterization of some of these tests to improve code dryness
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertBegin(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previous();
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertBegin(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,0,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertEnd(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,-1,i+1);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertEnd(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertMidPoint(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0){seqItr.previous();}
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertMidPoint(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItrset_val_happyPath(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=100;--i>=0;){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      seqItr.next();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
    for(int i=0;i<100;++i){
      seqItr.previous();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,100-i-1,100-i-1,100);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testput_int_val_happyPath(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));}
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));}
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testput_int_val_throwIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,finalIndex,0));
      constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,finalIndex+1,0));
      constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModSequencethrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModSequencethrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAset_val_emptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModSequencethrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModSequencethrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModRootCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModParentCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModSequenceCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToObject(i));}
    seqItr.add(TypeConversionUtil.convertToObject(0));
    seqItr.previous();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToObject(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedSubListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));}
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCME(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModRootthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModParentthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToObject(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToObject(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToObject(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,RefInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,RefInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static enum QueryTestScenario{
    EMPTY,
    CONTAINSBEGINNING,
    CONTAINSMIDDLE,
    CONTAINSEND,
    DOESNOTCONTAINS;
  }
  static Stream<Arguments> getPrimitiveQueryArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      for(QueryTestPrimitiveInputType argType:QueryTestPrimitiveInputType.values()){
        builder.add(Arguments.of(QueryTestScenario.EMPTY,argType,structType));
        builder.add(Arguments.of(QueryTestScenario.DOESNOTCONTAINS,argType,structType));
        switch(argType){
          case Booleanfalse:
          case Byte0:
          case Character0:
          case Short0:
          case Integer0:
          case Long0:
          case Floatpos0:
          case Floatneg0:
          case Doublepos0:
          case Doubleneg0:
          case Booleantrue:
          case Bytepos1:
          case Characterpos1:
          case Shortpos1:
          case Integerpos1:
          case Longpos1:
          case Floatpos1:
          case Doublepos1:
          //values beyond the range of boolean
          case Bytepos2:
          case Characterpos2:
          case Shortpos2:
          case Integerpos2:
          case Longpos2:
          case Floatpos2:
          case Doublepos2:
          //negative values beyond the range of char
          case Byteneg1:
          case Shortneg1:
          case Integerneg1:
          case Longneg1:
          case Floatneg1:
          case Doubleneg1:
          //negative values beyond the range of byte
          case ShortMIN_BYTE_MINUS1:
          case IntegerMIN_BYTE_MINUS1:
          case LongMIN_BYTE_MINUS1:
          case FloatMIN_BYTE_MINUS1:
          case DoubleMIN_BYTE_MINUS1:
          //negative values beyond the range of short
          case IntegerMIN_SHORT_MINUS1:
          case LongMIN_SHORT_MINUS1:
          case FloatMIN_SHORT_MINUS1:
          case DoubleMIN_SHORT_MINUS1:
          //negative values beyond the range of int
          case LongMIN_INT_MINUS1:
          case FloatMIN_INT_MINUS1:
          case DoubleMIN_INT_MINUS1:
          //negative values beyond MIN_SAFE_INT that are beyond the precision of float
          case IntegerMIN_SAFE_INT_MINUS1:
          case LongMIN_SAFE_INT_MINUS1:
          case DoubleMIN_SAFE_INT_MINUS1:
          //negative values beyond the range of int that are beyond the precision of float and double
          case LongMIN_SAFE_LONG_MINUS1:
          //positive values out of the range of byte
          case CharacterMAX_BYTE_PLUS1:
          case ShortMAX_BYTE_PLUS1:
          case IntegerMAX_BYTE_PLUS1:
          case LongMAX_BYTE_PLUS1:
          case FloatMAX_BYTE_PLUS1:
          case DoubleMAX_BYTE_PLUS1:
          //positive values out of the range of short
          case CharacterMAX_SHORT_PLUS1:
          case IntegerMAX_SHORT_PLUS1:
          case LongMAX_SHORT_PLUS1:
          case FloatMAX_SHORT_PLUS1:
          case DoubleMAX_SHORT_PLUS1:
          //positive values out of the range of char
          case IntegerMAX_CHAR_PLUS1:
          case LongMAX_CHAR_PLUS1:
          case FloatMAX_CHAR_PLUS1:
          case DoubleMAX_CHAR_PLUS1:
          //positive values out of the range of int
          case LongMAX_INT_PLUS1:
          case FloatMAX_INT_PLUS1:
          case DoubleMAX_INT_PLUS1:
          //positive values beyond MAX_SAFE_INT that are beyond the precision of float
          case IntegerMAX_SAFE_INT_PLUS1:
          case LongMAX_SAFE_INT_PLUS1:
          case DoubleMAX_SAFE_INT_PLUS1:
          //positive values beyond the range of int that are beyond the precision of float and double
          case LongMAX_SAFE_LONG_PLUS1:
          //floating-point values beyond the range any integral type
          case FloatMAX_LONG_PLUS1:
          case FloatMIN_LONG_MINUS1:
          case FloatMAX_FLOAT_VALUE:
          case DoubleMAX_FLOAT_VALUE:
          //fractional floating point values that cannot be matched with any integral type
          case FloatMIN_FLOAT_VALUE:
          case DoubleMIN_FLOAT_VALUE:
          //NaN values that can only be matches with NaN
          case FloatNaN:
          case DoubleNaN:
          //double-precision floating-point values beyond the range of any integral type and beyond the precision of float
          case DoubleMAX_LONG_PLUS1:
          case DoubleMIN_LONG_MINUS1:
          case DoubleMAX_DOUBLE_VALUE:
          //fractional floating point values that cannot be matched with any integral type and which are beyond the precision of float
          case DoubleMIN_DOUBLE_VALUE:
            //these input values cannot potentially return true
            builder.add(Arguments.of(QueryTestScenario.CONTAINSBEGINNING,argType,structType));
            builder.add(Arguments.of(QueryTestScenario.CONTAINSMIDDLE,argType,structType));
            builder.add(Arguments.of(QueryTestScenario.CONTAINSEND,argType,structType));
          default:
            //all other enumerated values MUST return false because they are either out of range or are too precise
        }
      }
    }
    return builder.build();
  }
}
