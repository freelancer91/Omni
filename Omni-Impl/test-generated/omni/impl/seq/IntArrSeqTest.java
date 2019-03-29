package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.impl.IntInputTestArgType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import omni.impl.QueryTestPrimitiveInputType;
import java.util.function.IntConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntArrSeqTest{
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
    final OmniCollection.OfInt seq;
    final OmniCollection.OfInt parent;
    final IntArrSeq root;
    ConstructionArguments(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,StructType structType){
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.rootPreAlloc=rootPreAlloc;
      this.rootPostAlloc=rootPostAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.structType=structType;
      final int rootSize;
      if((rootSize=parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc)==0){
        this.root=structType.checked?new IntArrSeq.CheckedList():new IntArrSeq.UncheckedList();
      }else{
        int[] arr=new int[rootSize];
        initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
        initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=structType.checked?new IntArrSeq.CheckedList(rootSize,arr):new IntArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfInt)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
      this.seq=((OmniList.OfInt)parent).subList(parentPreAlloc,parentPreAlloc);
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
          this.root=new IntArrSeq.CheckedStack(initialCapacity);
          break;
        case UNCHECKEDSTACK:
          this.root=new IntArrSeq.UncheckedStack(initialCapacity);
          break;
        case CHECKEDLIST:
          this.root=new IntArrSeq.CheckedList(initialCapacity);
          break;
        default:
          this.root=new IntArrSeq.UncheckedList(initialCapacity);
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
    private OmniListIterator.OfInt constructSeqListIterator(){return ((OmniList.OfInt)seq).listIterator();}
    private void verifyIteratorState(Object itr,int expectedCursor,int expectedLastRet,int expectedModCount){
      int actualCursor;
      Object actualParent;
      switch(structType){
        case CHECKEDLIST:
          actualCursor=FieldAccessor.IntArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.IntArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case UNCHECKEDLIST:
          actualCursor=FieldAccessor.IntArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case CHECKEDSTACK:
          actualCursor=FieldAccessor.IntArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.IntArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case UNCHECKEDSTACK:
          actualCursor=FieldAccessor.IntArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case CHECKEDSUBLIST:
          actualCursor=FieldAccessor.IntArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.IntArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          actualCursor=FieldAccessor.IntArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.IntArrSeq.UncheckedSubList.Itr.parent(itr);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount){
      switch(structType){
        case CHECKEDSTACK:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.IntArrSeq.CheckedStack.modCount(root));
          break;
        case CHECKEDLIST:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.IntArrSeq.CheckedList.modCount(root));
        case UNCHECKEDSTACK:
        case UNCHECKEDLIST:
          break;
        default:
          OmniList.OfInt actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfInt actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(structType.checked){
            actualSeqParent=FieldAccessor.IntArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.IntArrSeq.CheckedSubList.root(seq);
            actualParentParent=FieldAccessor.IntArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.IntArrSeq.CheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.IntArrSeq.CheckedSubList.size(seq);
            actualParentSize=FieldAccessor.IntArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAccessor.IntArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.IntArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=FieldAccessor.IntArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.IntArrSeq.UncheckedSubList.root(seq);
            actualParentParent=FieldAccessor.IntArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.IntArrSeq.UncheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.IntArrSeq.UncheckedSubList.size(seq);
            actualParentSize=FieldAccessor.IntArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.IntArrSeq.size(root));
    }
    private int verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=rootPreAlloc+parentPreAlloc,v=-bound;offset<bound;++offset,++v){IntInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyParentPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+parentPostAlloc,v=100;offset<bound;++offset,++v){IntInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyRootPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+rootPostAlloc,v=100+parentPostAlloc;offset<bound;++offset,++v){IntInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyIndex(int offset,IntInputTestArgType inputArgType,int v){
      inputArgType.verifyVal(v,root.arr[offset]);
      return offset+1;
    }
    private int verifyAscending(int offset,IntInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=0;offset<bound;++offset,++v){inputArgType.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyDescending(int offset,IntInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=length;offset<bound;++offset){inputArgType.verifyVal(--v,arr[offset]);}
      return offset;
    }
    private int verifyMidPointInsertion(int offset,IntInputTestArgType inputArgType,int length){
      var arr=root.arr;
      int i;
      for(int v=1,b=(i=offset)+length/2;i<b;++i,v+=2){inputArgType.verifyVal(v,arr[i]);}
      for(int v=length-2,b=i+length/2;i<b;++i,v-=2){inputArgType.verifyVal(v,arr[i]);}
      return offset+length;
    }
  }
  private static void initAscendingArray(int[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){arr[i]=TypeConversionUtil.convertToint(lo);}
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
    IntArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList()):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new IntArrSeq.UncheckedList():new IntArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_intarr_happyPath(boolean checked,StructType structType){
    int size=5;
    int[] arr=new int[10];
    IntArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList(size,arr)):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new IntArrSeq.UncheckedList(size,arr):new IntArrSeq.UncheckedStack(size,arr);
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
    IntArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList(initialCapacity)):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new IntArrSeq.UncheckedList(initialCapacity):new IntArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> getListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(IntInputTestArgType inputTestArgType:IntInputTestArgType.values()){
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
    for(IntInputTestArgType inputTestArgType:IntInputTestArgType.values()){
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
    for(IntInputTestArgType inputTestArgType:IntInputTestArgType.values()){
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
  public void testListItradd_val_happyPathInsertBegin(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previousInt();
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertBegin(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,0,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertEnd(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testadd_int_val_happyPathInsertEnd(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertMidPoint(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0){seqItr.previousInt();}
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertMidPoint(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItrset_val_happyPath(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=100;--i>=0;){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      seqItr.nextInt();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
    for(int i=0;i<100;++i){
      seqItr.previousInt();
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
  public void testput_int_val_happyPath(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(IntInputTestArgType inputTestArgType:IntInputTestArgType.values()){
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
  public void testput_int_val_throwIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,finalIndex,0));
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,finalIndex+1,0));
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModSequencethrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModSequencethrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAset_val_emptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModSequencethrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModSequencethrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testListItrset_val_emptyListpostaddthrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
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
  public void testListItrset_val_emptyListthrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModRootCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModParentCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModSequenceCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedSubListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(IntInputTestArgType inputTestArgType:IntInputTestArgType.values()){
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
  public void testSubListadd_val_emptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCME(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModRootthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModParentthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(IntInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,IntInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,IntInputTestArgType.ARRAY_TYPE,0);
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
          //negative values beyond MIN_SAFE_INT that are beyond the precision of float
          case IntegerMIN_SAFE_INT_MINUS1:
          case LongMIN_SAFE_INT_MINUS1:
          case DoubleMIN_SAFE_INT_MINUS1:
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
          //positive values beyond MAX_SAFE_INT that are beyond the precision of float
          case IntegerMAX_SAFE_INT_PLUS1:
          case LongMAX_SAFE_INT_PLUS1:
          case DoubleMAX_SAFE_INT_PLUS1:
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
