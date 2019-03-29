package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.impl.DoubleInputTestArgType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import omni.impl.QueryTestPrimitiveInputType;
import java.util.function.DoubleConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class DoubleArrSeqTest{
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
    final OmniCollection.OfDouble seq;
    final OmniCollection.OfDouble parent;
    final DoubleArrSeq root;
    ConstructionArguments(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,StructType structType){
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.rootPreAlloc=rootPreAlloc;
      this.rootPostAlloc=rootPostAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.structType=structType;
      final int rootSize;
      if((rootSize=parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc)==0){
        this.root=structType.checked?new DoubleArrSeq.CheckedList():new DoubleArrSeq.UncheckedList();
      }else{
        double[] arr=new double[rootSize];
        initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
        initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=structType.checked?new DoubleArrSeq.CheckedList(rootSize,arr):new DoubleArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfDouble)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
      this.seq=((OmniList.OfDouble)parent).subList(parentPreAlloc,parentPreAlloc);
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
          this.root=new DoubleArrSeq.CheckedStack(initialCapacity);
          break;
        case UNCHECKEDSTACK:
          this.root=new DoubleArrSeq.UncheckedStack(initialCapacity);
          break;
        case CHECKEDLIST:
          this.root=new DoubleArrSeq.CheckedList(initialCapacity);
          break;
        default:
          this.root=new DoubleArrSeq.UncheckedList(initialCapacity);
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
    private OmniListIterator.OfDouble constructSeqListIterator(){return ((OmniList.OfDouble)seq).listIterator();}
    private void verifyIteratorState(Object itr,int expectedCursor,int expectedLastRet,int expectedModCount){
      int actualCursor;
      Object actualParent;
      switch(structType){
        case CHECKEDLIST:
          actualCursor=FieldAccessor.DoubleArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.DoubleArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.DoubleArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case UNCHECKEDLIST:
          actualCursor=FieldAccessor.DoubleArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case CHECKEDSTACK:
          actualCursor=FieldAccessor.DoubleArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.DoubleArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.DoubleArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case UNCHECKEDSTACK:
          actualCursor=FieldAccessor.DoubleArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case CHECKEDSUBLIST:
          actualCursor=FieldAccessor.DoubleArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.DoubleArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.DoubleArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          actualCursor=FieldAccessor.DoubleArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.DoubleArrSeq.UncheckedSubList.Itr.parent(itr);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount){
      switch(structType){
        case CHECKEDSTACK:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedStack.modCount(root));
          break;
        case CHECKEDLIST:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedList.modCount(root));
        case UNCHECKEDSTACK:
        case UNCHECKEDLIST:
          break;
        default:
          OmniList.OfDouble actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfDouble actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(structType.checked){
            actualSeqParent=FieldAccessor.DoubleArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.DoubleArrSeq.CheckedSubList.root(seq);
            actualParentParent=FieldAccessor.DoubleArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.DoubleArrSeq.CheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.DoubleArrSeq.CheckedSubList.size(seq);
            actualParentSize=FieldAccessor.DoubleArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedModCount,FieldAccessor.DoubleArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAccessor.DoubleArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=FieldAccessor.DoubleArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.DoubleArrSeq.UncheckedSubList.root(seq);
            actualParentParent=FieldAccessor.DoubleArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.DoubleArrSeq.UncheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.DoubleArrSeq.UncheckedSubList.size(seq);
            actualParentSize=FieldAccessor.DoubleArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.DoubleArrSeq.size(root));
    }
    private int verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=rootPreAlloc+parentPreAlloc,v=-bound;offset<bound;++offset,++v){DoubleInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyParentPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+parentPostAlloc,v=100;offset<bound;++offset,++v){DoubleInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyRootPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+rootPostAlloc,v=100+parentPostAlloc;offset<bound;++offset,++v){DoubleInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyIndex(int offset,DoubleInputTestArgType inputArgType,int v){
      inputArgType.verifyVal(v,root.arr[offset]);
      return offset+1;
    }
    private int verifyAscending(int offset,DoubleInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=0;offset<bound;++offset,++v){inputArgType.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyDescending(int offset,DoubleInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=length;offset<bound;++offset){inputArgType.verifyVal(--v,arr[offset]);}
      return offset;
    }
    private int verifyMidPointInsertion(int offset,DoubleInputTestArgType inputArgType,int length){
      var arr=root.arr;
      int i;
      for(int v=1,b=(i=offset)+length/2;i<b;++i,v+=2){inputArgType.verifyVal(v,arr[i]);}
      for(int v=length-2,b=i+length/2;i<b;++i,v-=2){inputArgType.verifyVal(v,arr[i]);}
      return offset+length;
    }
  }
  private static void initAscendingArray(double[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){arr[i]=TypeConversionUtil.convertTodouble(lo);}
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
    DoubleArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.DoubleArrSeq.CheckedList.modCount(seq=new DoubleArrSeq.CheckedList()):FieldAccessor.DoubleArrSeq.CheckedStack.modCount(seq=new DoubleArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new DoubleArrSeq.UncheckedList():new DoubleArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_doublearr_happyPath(boolean checked,StructType structType){
    int size=5;
    double[] arr=new double[10];
    DoubleArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.DoubleArrSeq.CheckedList.modCount(seq=new DoubleArrSeq.CheckedList(size,arr)):FieldAccessor.DoubleArrSeq.CheckedStack.modCount(seq=new DoubleArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new DoubleArrSeq.UncheckedList(size,arr):new DoubleArrSeq.UncheckedStack(size,arr);
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
    DoubleArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.DoubleArrSeq.CheckedList.modCount(seq=new DoubleArrSeq.CheckedList(initialCapacity)):FieldAccessor.DoubleArrSeq.CheckedStack.modCount(seq=new DoubleArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new DoubleArrSeq.UncheckedList(initialCapacity):new DoubleArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> getListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
  public void testListItradd_val_happyPathInsertBegin(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previousDouble();
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertBegin(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,0,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertEnd(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testadd_int_val_happyPathInsertEnd(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertMidPoint(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0){seqItr.previousDouble();}
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertMidPoint(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItrset_val_happyPath(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=100;--i>=0;){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      seqItr.nextDouble();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
    for(int i=0;i<100;++i){
      seqItr.previousDouble();
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
  public void testput_int_val_happyPath(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
  public void testput_int_val_throwIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,finalIndex,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,finalIndex+1,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModSequencethrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModSequencethrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAset_val_emptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModSequencethrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModSequencethrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testListItrset_val_emptyListpostaddthrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
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
  public void testListItrset_val_emptyListthrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModRootCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModParentCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModSequenceCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTodouble(i));}
    seqItr.add(TypeConversionUtil.convertTodouble(0));
    seqItr.previousDouble();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTodouble(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedSubListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
  public void testSubListadd_val_emptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCME(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModRootthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModParentthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTodouble(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTodouble(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
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
