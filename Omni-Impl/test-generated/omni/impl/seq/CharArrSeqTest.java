package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import omni.impl.CharInputTestArgType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import omni.impl.QueryTestPrimitiveInputType;
import omni.function.CharConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class CharArrSeqTest{
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
    final OmniCollection.OfChar seq;
    final OmniCollection.OfChar parent;
    final CharArrSeq root;
    ConstructionArguments(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,StructType structType){
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.rootPreAlloc=rootPreAlloc;
      this.rootPostAlloc=rootPostAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.structType=structType;
      final int rootSize;
      if((rootSize=parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc)==0){
        this.root=structType.checked?new CharArrSeq.CheckedList():new CharArrSeq.UncheckedList();
      }else{
        char[] arr=new char[rootSize];
        initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
        initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=structType.checked?new CharArrSeq.CheckedList(rootSize,arr):new CharArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfChar)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
      this.seq=((OmniList.OfChar)parent).subList(parentPreAlloc,parentPreAlloc);
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
          this.root=new CharArrSeq.CheckedStack(initialCapacity);
          break;
        case UNCHECKEDSTACK:
          this.root=new CharArrSeq.UncheckedStack(initialCapacity);
          break;
        case CHECKEDLIST:
          this.root=new CharArrSeq.CheckedList(initialCapacity);
          break;
        default:
          this.root=new CharArrSeq.UncheckedList(initialCapacity);
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
    private OmniListIterator.OfChar constructSeqListIterator(){return ((OmniList.OfChar)seq).listIterator();}
    private void verifyIteratorState(Object itr,int expectedCursor,int expectedLastRet,int expectedModCount){
      int actualCursor;
      Object actualParent;
      switch(structType){
        case CHECKEDLIST:
          actualCursor=FieldAccessor.CharArrSeq.CheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.CharArrSeq.CheckedList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedList.Itr.lastRet(itr));
          break;
        case UNCHECKEDLIST:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedList.Itr.parent(itr);
          break;
        case CHECKEDSTACK:
          actualCursor=FieldAccessor.CharArrSeq.CheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedStack.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.CharArrSeq.CheckedStack.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedStack.Itr.lastRet(itr));
          break;
        case UNCHECKEDSTACK:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedStack.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedStack.Itr.parent(itr);
          break;
        case CHECKEDSUBLIST:
          actualCursor=FieldAccessor.CharArrSeq.CheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.CheckedSubList.Itr.parent(itr);
          Assertions.assertEquals(expectedModCount,FieldAccessor.CharArrSeq.CheckedSubList.Itr.modCount(itr));
          Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+(rootPreAlloc+parentPreAlloc),FieldAccessor.CharArrSeq.CheckedSubList.Itr.lastRet(itr));
          break;
        default:
          actualCursor=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.cursor(itr);
          actualParent=FieldAccessor.CharArrSeq.UncheckedSubList.Itr.parent(itr);
      }
      Assertions.assertEquals(expectedCursor+(rootPreAlloc+parentPreAlloc),actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount){
      switch(structType){
        case CHECKEDSTACK:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedStack.modCount(root));
          break;
        case CHECKEDLIST:
          Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(root));
        case UNCHECKEDSTACK:
        case UNCHECKEDLIST:
          break;
        default:
          OmniList.OfChar actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfChar actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(structType.checked){
            actualSeqParent=FieldAccessor.CharArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.CharArrSeq.CheckedSubList.root(seq);
            actualParentParent=FieldAccessor.CharArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.CharArrSeq.CheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.CharArrSeq.CheckedSubList.size(seq);
            actualParentSize=FieldAccessor.CharArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAccessor.CharArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.CharArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=FieldAccessor.CharArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.CharArrSeq.UncheckedSubList.root(seq);
            actualParentParent=FieldAccessor.CharArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.CharArrSeq.UncheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(seq);
            actualParentSize=FieldAccessor.CharArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.CharArrSeq.size(root));
    }
    private int verifyPreAlloc(){
      var arr=root.arr;
      int offset=0;
      for(int bound=rootPreAlloc+parentPreAlloc,v=-bound;offset<bound;++offset,++v){CharInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyParentPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+parentPostAlloc,v=100;offset<bound;++offset,++v){CharInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyRootPostAlloc(int offset){
      var arr=root.arr;
      for(int bound=offset+rootPostAlloc,v=100+parentPostAlloc;offset<bound;++offset,++v){CharInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyIndex(int offset,CharInputTestArgType inputArgType,int v){
      inputArgType.verifyVal(v,root.arr[offset]);
      return offset+1;
    }
    private int verifyAscending(int offset,CharInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=0;offset<bound;++offset,++v){inputArgType.verifyVal(v,arr[offset]);}
      return offset;
    }
    private int verifyDescending(int offset,CharInputTestArgType inputArgType,int length){
      var arr=root.arr;
      for(int bound=offset+length,v=length;offset<bound;++offset){inputArgType.verifyVal(--v,arr[offset]);}
      return offset;
    }
    private int verifyMidPointInsertion(int offset,CharInputTestArgType inputArgType,int length){
      var arr=root.arr;
      int i;
      for(int v=1,b=(i=offset)+length/2;i<b;++i,v+=2){inputArgType.verifyVal(v,arr[i]);}
      for(int v=length-2,b=i+length/2;i<b;++i,v-=2){inputArgType.verifyVal(v,arr[i]);}
      return offset+length;
    }
  }
  private static void initAscendingArray(char[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){arr[i]=TypeConversionUtil.convertTochar(lo);}
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
    CharArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.CharArrSeq.CheckedList.modCount(seq=new CharArrSeq.CheckedList()):FieldAccessor.CharArrSeq.CheckedStack.modCount(seq=new CharArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new CharArrSeq.UncheckedList():new CharArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_chararr_happyPath(boolean checked,StructType structType){
    int size=5;
    char[] arr=new char[10];
    CharArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.CharArrSeq.CheckedList.modCount(seq=new CharArrSeq.CheckedList(size,arr)):FieldAccessor.CharArrSeq.CheckedStack.modCount(seq=new CharArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new CharArrSeq.UncheckedList(size,arr):new CharArrSeq.UncheckedStack(size,arr);
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
    CharArrSeq seq;
    if(structType.checked){
      Assertions.assertEquals(0,structType==StructType.CHECKEDLIST?FieldAccessor.CharArrSeq.CheckedList.modCount(seq=new CharArrSeq.CheckedList(initialCapacity)):FieldAccessor.CharArrSeq.CheckedStack.modCount(seq=new CharArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.UNCHECKEDLIST?new CharArrSeq.UncheckedList(initialCapacity):new CharArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity){
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfChar.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> getListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(CharInputTestArgType inputTestArgType:CharInputTestArgType.values()){
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
    for(CharInputTestArgType inputTestArgType:CharInputTestArgType.values()){
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
    for(CharInputTestArgType inputTestArgType:CharInputTestArgType.values()){
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
  public void testListItradd_val_happyPathInsertBegin(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previousChar();
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertBegin(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,0,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertEnd(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testadd_int_val_happyPathInsertEnd(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertMidPoint(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0){seqItr.previousChar();}
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertMidPoint(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItrset_val_happyPath(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=100;--i>=0;){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      seqItr.nextChar();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
    for(int i=0;i<100;++i){
      seqItr.previousChar();
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
  public void testput_int_val_happyPath(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyDescending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(CharInputTestArgType inputTestArgType:CharInputTestArgType.values()){
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
  public void testput_int_val_throwIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,finalIndex,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,finalIndex+1,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModSequencethrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModSequencethrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAset_val_emptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModSequencethrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModSequencethrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,100,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testListItrset_val_emptyListpostaddthrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
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
  public void testListItrset_val_emptyListthrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListthrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostaddthrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,1,-1,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(0,2,1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListpostremovethrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,-1,2);
    constructionArgs.verifyStructuralIntegrity(1,3);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListthrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(101,101,102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostaddthrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,101,-1,101);
    constructionArgs.verifyStructuralIntegrity(102,102);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModRootCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModParentCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(100,102,101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListpostremovethrowISEsupercedesModSequenceCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertTochar(i));}
    seqItr.add(TypeConversionUtil.convertTochar(0));
    seqItr.previousChar();
    seqItr.remove();
    //illegally modify the sequence
    constructionArgs.seq.add(TypeConversionUtil.convertTochar(0));
    Assertions.assertThrows(IllegalStateException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,100,-1,102);
    constructionArgs.verifyStructuralIntegrity(101,103);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getCheckedSubListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(CharInputTestArgType inputTestArgType:CharInputTestArgType.values()){
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
  public void testSubListadd_val_emptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCME(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModRootthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModParentthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertTochar(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyRootPostAlloc(offset);
    constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(CharInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertTochar(i));}
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertTochar(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,CharInputTestArgType.ARRAY_TYPE,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    offset=constructionArgs.verifyIndex(offset,CharInputTestArgType.ARRAY_TYPE,0);
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
