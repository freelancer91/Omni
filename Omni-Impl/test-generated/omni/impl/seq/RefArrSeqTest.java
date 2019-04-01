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
import omni.impl.QueryTestScenario;
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
    ConstructionArguments(StructType structType){
      initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.structType=structType;
      Object[] arr;
      switch(structType){
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
          rootPreAlloc=5;
          parentPreAlloc=5;
          parentPostAlloc=5;
          rootPostAlloc=5;
          arr=new Object[rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc];
          initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
          initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
          break;
        default:
          rootPreAlloc=0;
          parentPreAlloc=0;
          parentPostAlloc=0;
          rootPostAlloc=0;
          arr=OmniArray.OfRef.DEFAULT_ARR;
      }
      switch(structType){
        case CHECKEDLIST:
        case CHECKEDSUBLIST:
          this.root=new RefArrSeq.CheckedList(rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc,arr);
          break;
        case UNCHECKEDLIST:
        case UNCHECKEDSUBLIST:
          this.root=new RefArrSeq.UncheckedList(rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc,arr);
          break;
        case CHECKEDSTACK:
          this.root=new RefArrSeq.CheckedStack();
          break;
        default:
          this.root=new RefArrSeq.UncheckedStack();
      }
      switch(structType){
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
          this.parent=((OmniList.OfRef)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
          this.seq=((OmniList.OfRef)parent).subList(parentPreAlloc,parentPreAlloc);
          break;
        default:
          this.parent=root;
          this.seq=root;
      }
    }
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
  static class InputTestMonitor{
    int expectedItrModCount=0;
    int expectedSeqModCount=0;
    int expectedParentModCount=0;
    int expectedRootModCount=0;
    int expectedSeqSize=0;
    int expectedParentSize=0;
    int expectedRootSize=0;
    int expectedCursor=0;
    int expectedLastRet=-1;
    public void seqItrAdd(OmniListIterator.OfRef seqItr,RefInputTestArgType inputArgType,int valToAdd){
      inputArgType.callListItrAdd(seqItr,valToAdd);
      ++expectedItrModCount;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      ++expectedSeqSize;
      ++expectedParentSize;
      ++expectedRootSize;
      ++expectedCursor;
      expectedLastRet=-1;
    }
    public void seqItrPrevious(OmniListIterator.OfRef seqItr){
      seqItr.previous();
      expectedLastRet=--expectedCursor; 
    }
    public void seqItrNext(OmniListIterator.OfRef seqItr){
      seqItr.next();
      expectedLastRet=expectedCursor++;
    }
    public void seqItrRemove(OmniListIterator.OfRef seqItr){
      seqItr.remove();
      expectedCursor=expectedLastRet;
      expectedLastRet=-1;
      ++expectedItrModCount;
      ++expectedSeqModCount;
      ++expectedParentModCount;
      ++expectedRootModCount;
      --expectedSeqSize;
      --expectedParentSize;
      --expectedRootSize;
    }
    public void rootMod(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      inputArgType.callCollectionAdd(constructionArgs.root,0);
    }
    public void parentMod(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      inputArgType.callCollectionAdd(constructionArgs.parent,0);
    }
    public void seqMod(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callCollectionAdd(constructionArgs.seq,0);
    }
    public void collectionAdd(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType,int val){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callCollectionAdd(constructionArgs.seq,val);
    }
    public void listAdd(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType,int index,int val){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callListAdd(constructionArgs.seq,index,val);
    }
    public void illegalMod(ConstructionArguments constructionArgs,RefInputTestArgType inputArgType,CMEScenario modScenario){
      switch(modScenario){
        case ModRoot:
          rootMod(constructionArgs,inputArgType);
          break;
        case ModParent:
          parentMod(constructionArgs,inputArgType);
          break;
        case ModSeq:
          seqMod(constructionArgs,inputArgType);
        default:
      }
    }
    public void verifyItrState(OmniListIterator.OfRef seqItr,ConstructionArguments constructionArgs){
      constructionArgs.verifyIteratorState(seqItr,expectedCursor,expectedLastRet,expectedItrModCount);
    }
    public void verifyStructuralIntegrity(ConstructionArguments constructionArgs){
      constructionArgs.verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentSize,expectedParentModCount,expectedRootSize,expectedRootModCount);
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
  static enum CMEScenario{
    NoMod,
    ModSeq,
    ModParent,
    ModRoot;
  }
  static Stream<Arguments> getListAddIntValArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(ListAddIntValTestScenario testScenario: ListAddIntValTestScenario.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));}
          if(testScenario.expectedException!=ConcurrentModificationException.class){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));}
        }
        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
            for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
              for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
                if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));}
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getStackPushArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDSTACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDSTACK)));
      }
    }
    return builder.build();
  }
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
  public void testConstructor_int_Objectarr_happyPath(StructType structType){
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
  static enum ListItrAddTestScenario{
    HappyPathInsertBegin(false,CMEScenario.NoMod,null),
    HappyPathInsertEnd(false,CMEScenario.NoMod,null),
    HappyPathInsertMidPoint(false,CMEScenario.NoMod,null),
    EmptyModRootThrowCME(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyModParentThrowCME(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    EmptyModSeqThrowCME(false,CMEScenario.ModSeq,ConcurrentModificationException.class),
    NonEmptyModRootThrowCME(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyModParentThrowCME(true,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyModSeqThrowCME(true,CMEScenario.ModSeq,ConcurrentModificationException.class);
    final boolean nonEmpty;
    final CMEScenario modScenario;
    final Class<? extends Throwable> expectedException;
    ListItrAddTestScenario(boolean nonEmpty,CMEScenario modScenario,Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
      this.nonEmpty=nonEmpty;
      this.modScenario=modScenario;
    }
  }
  static Stream<Arguments> getListItrAddArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(ListItrAddTestScenario testScenario:ListItrAddTestScenario.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));}
          builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));
        }
        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
            for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
              for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
                if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));}
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getListItrAddArgs")
  public void testListItrAdd(ListItrAddTestScenario testScenario,RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    var testMonitor=new InputTestMonitor();
    if(testScenario.nonEmpty){
      for(int i=0;i<100;++i){
        testMonitor.seqItrAdd(seqItr,inputArgType,i);
      }
    }
    var expectedException=testScenario.expectedException;
    if(expectedException==null){
      for(int i=0;i<100;++i){
        testMonitor.seqItrAdd(seqItr,inputArgType,i);
        testMonitor.verifyItrState(seqItr,constructionArgs);
        switch(testScenario){
          case HappyPathInsertBegin:
            testMonitor.seqItrPrevious(seqItr);
            break;
          case HappyPathInsertMidPoint:
            if((i&1)==0){testMonitor.seqItrPrevious(seqItr);}
            break;
          default:
        }
      }
    }else{
      testMonitor.illegalMod(constructionArgs,inputArgType,testScenario.modScenario);
      Assertions.assertThrows(expectedException,()->inputArgType.callListItrAdd(seqItr,0));
    }
    testMonitor.verifyItrState(seqItr,constructionArgs);
    testMonitor.verifyStructuralIntegrity(constructionArgs);
    int offset=constructionArgs.verifyPreAlloc();
    switch(testScenario){
      case HappyPathInsertBegin:
        offset=constructionArgs.verifyDescending(offset,inputArgType,100);
        break;
      case HappyPathInsertMidPoint:
        offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
        break;
      case HappyPathInsertEnd:
        offset=constructionArgs.verifyAscending(offset,inputArgType,100);
        break;
      default:
        if(testScenario.nonEmpty){offset=constructionArgs.verifyAscending(offset,inputArgType,100);}
    }
    if(testScenario.modScenario==CMEScenario.ModSeq){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,inputArgType,0);}
  }
  static enum ListItrSetTestScenario{
    HappyPath(true,CMEScenario.NoMod,null),
    EmptyModRootThrowCME(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyModParentThrowCME(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    EmptyModSeqThrowCME(false,CMEScenario.ModSeq,ConcurrentModificationException.class),
    NonEmptyModRootThrowCME(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyModParentThrowCME(true,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyModSeqThrowCME(true,CMEScenario.ModSeq,ConcurrentModificationException.class),
    EmptyThrowISE(false,CMEScenario.NoMod,IllegalStateException.class),
    EmptyPostAddThrowISE(false,CMEScenario.NoMod,IllegalStateException.class),
    EmptyPostRemoveThrowISE(false,CMEScenario.NoMod,IllegalStateException.class),
    EmptyThrowISEModRootCME(false,CMEScenario.ModRoot,IllegalStateException.class),
    EmptyThrowISEModParentCME(false,CMEScenario.ModParent,IllegalStateException.class),
    EmptyThrowISEModSeqCME(false,CMEScenario.ModSeq,IllegalStateException.class),
    EmptyPostAddThrowISESupercedesModRootCME(false,CMEScenario.ModRoot,IllegalStateException.class),
    EmptyPostAddThrowISESupercedesModParentCME(false,CMEScenario.ModParent,IllegalStateException.class),
    EmptyPostAddThrowISESupercedesModSeqCME(false,CMEScenario.ModSeq,IllegalStateException.class),
    EmptyPostRemoveThrowISESupercedesModRootCME(false,CMEScenario.ModRoot,IllegalStateException.class),
    EmptyPostRemoveThrowISESupercedesModParentCME(false,CMEScenario.ModParent,IllegalStateException.class),
    EmptyPostRemoveThrowISESupercedesModSeqCME(false,CMEScenario.ModSeq,IllegalStateException.class),
    NonEmptyThrowISE(true,CMEScenario.NoMod,IllegalStateException.class),
    NonEmptyPostAddThrowISE(true,CMEScenario.NoMod,IllegalStateException.class),
    NonEmptyPostRemoveThrowISE(true,CMEScenario.NoMod,IllegalStateException.class),
    NonEmptyThrowISEModRootCME(true,CMEScenario.ModRoot,IllegalStateException.class),
    NonEmptyThrowISEModParentCME(true,CMEScenario.ModParent,IllegalStateException.class),
    NonEmptyThrowISEModSeqCME(true,CMEScenario.ModSeq,IllegalStateException.class),
    NonEmptyPostAddThrowISESupercedesModRootCME(true,CMEScenario.ModRoot,IllegalStateException.class),
    NonEmptyPostAddThrowISESupercedesModParentCME(true,CMEScenario.ModParent,IllegalStateException.class),
    NonEmptyPostAddThrowISESupercedesModSeqCME(true,CMEScenario.ModSeq,IllegalStateException.class),
    NonEmptyPostRemoveThrowISESupercedesModRootCME(true,CMEScenario.ModRoot,IllegalStateException.class),
    NonEmptyPostRemoveThrowISESupercedesModParentCME(true,CMEScenario.ModParent,IllegalStateException.class),
    NonEmptyPostRemoveThrowISESupercedesModSeqCME(true,CMEScenario.ModSeq,IllegalStateException.class);
    final boolean nonEmpty;
    final CMEScenario modScenario;
    final Class<? extends Throwable> expectedException;
    ListItrSetTestScenario(boolean nonEmpty,CMEScenario modScenario,Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
      this.nonEmpty=nonEmpty;
      this.modScenario=modScenario;
    }
  }
  static Stream<Arguments> getListItrSetArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(ListItrSetTestScenario testScenario: ListItrSetTestScenario.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));}
          builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));
        }
        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
            for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
              for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
                if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));}
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getListItrSetArgs")
  public void testListItrSet(ListItrSetTestScenario testScenario,RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    var testMonitor=new InputTestMonitor();
    if(testScenario.nonEmpty){for(int i=0;i<100;++i){testMonitor.seqItrAdd(seqItr,inputArgType,i);}}
    var expectedException=testScenario.expectedException;
    if(expectedException==null){
      for(int i=0;i<100;++i){
        testMonitor.seqItrPrevious(seqItr);
        inputArgType.callListItrSet(seqItr,i);
        testMonitor.verifyItrState(seqItr,constructionArgs);
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyDescending(offset,inputArgType,testMonitor.expectedRootSize);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      constructionArgs.verifyRootPostAlloc(offset);
      for(int i=0;i<100;++i){
        testMonitor.seqItrNext(seqItr);
        inputArgType.callListItrSet(seqItr,i);
        testMonitor.verifyItrState(seqItr,constructionArgs);
      }
    }else{
      if(expectedException==ConcurrentModificationException.class)
      {
        testMonitor.seqItrAdd(seqItr,inputArgType,0);
        testMonitor.seqItrPrevious(seqItr);
      }else{
        switch(testScenario){
        case EmptyPostAddThrowISE:
        case EmptyPostAddThrowISESupercedesModRootCME:
        case EmptyPostAddThrowISESupercedesModParentCME:
        case EmptyPostAddThrowISESupercedesModSeqCME:
        case NonEmptyPostAddThrowISE:
        case NonEmptyPostAddThrowISESupercedesModRootCME:
        case NonEmptyPostAddThrowISESupercedesModParentCME:
        case NonEmptyPostAddThrowISESupercedesModSeqCME:
          testMonitor.seqItrAdd(seqItr,inputArgType,0);
          break;
        case EmptyPostRemoveThrowISE:
        case EmptyPostRemoveThrowISESupercedesModRootCME:
        case EmptyPostRemoveThrowISESupercedesModParentCME:
        case EmptyPostRemoveThrowISESupercedesModSeqCME:
        case NonEmptyPostRemoveThrowISE:
        case NonEmptyPostRemoveThrowISESupercedesModRootCME:
        case NonEmptyPostRemoveThrowISESupercedesModParentCME:
        case NonEmptyPostRemoveThrowISESupercedesModSeqCME:
          testMonitor.seqItrAdd(seqItr,inputArgType,0);
          testMonitor.seqItrPrevious(seqItr);
          testMonitor.seqItrRemove(seqItr);
        default:
        }
      }
      testMonitor.illegalMod(constructionArgs,inputArgType,testScenario.modScenario);
      Assertions.assertThrows(expectedException,()->inputArgType.callListItrSet(seqItr,1));
    }
    testMonitor.verifyItrState(seqItr,constructionArgs);
    testMonitor.verifyStructuralIntegrity(constructionArgs);
    int offset=constructionArgs.verifyPreAlloc();
    if(testScenario.nonEmpty){offset=constructionArgs.verifyAscending(offset,inputArgType,100);}
    switch(testScenario){
      case EmptyModRootThrowCME:
      case EmptyModParentThrowCME:
      case EmptyModSeqThrowCME:
      case NonEmptyModRootThrowCME:
      case NonEmptyModParentThrowCME:
      case NonEmptyModSeqThrowCME:
      case EmptyPostAddThrowISE:
      case EmptyPostAddThrowISESupercedesModRootCME:
      case EmptyPostAddThrowISESupercedesModParentCME:
      case EmptyPostAddThrowISESupercedesModSeqCME:
      case NonEmptyPostAddThrowISE:
      case NonEmptyPostAddThrowISESupercedesModRootCME:
      case NonEmptyPostAddThrowISESupercedesModParentCME:
      case NonEmptyPostAddThrowISESupercedesModSeqCME:
        offset=constructionArgs.verifyIndex(offset,inputArgType,0);
      default:
    }
    if(testScenario.modScenario==CMEScenario.ModSeq){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,inputArgType,0);}
  }
  static enum ListAddIntValTestScenario{
    HappyPathInsertBegin(false,CMEScenario.NoMod,null),
    HappyPathInsertEnd(false,CMEScenario.NoMod,null),
    HappyPathInsertMidPoint(false,CMEScenario.NoMod,null),
    ThrowIOBE(false,CMEScenario.NoMod,IndexOutOfBoundsException.class),
    EmptyModRootThrowCME(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyModParentThrowCME(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyModRootThrowCME(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyModParentThrowCME(true,CMEScenario.ModParent,ConcurrentModificationException.class),
    EmptyModRootThrowCMESupercedesIOBE(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyModRootThrowCMESupercedesIOBE(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyModParentThrowCMESupercedesIOBE(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyModParentThrowCMESupercedesIOBE(true,CMEScenario.ModParent,ConcurrentModificationException.class);
    final boolean nonEmpty;
    final CMEScenario modScenario;
    final Class<? extends Throwable> expectedException;
    ListAddIntValTestScenario(boolean nonEmpty,CMEScenario modScenario,Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
      this.nonEmpty=nonEmpty;
      this.modScenario=modScenario;
    }
  }
  @ParameterizedTest
  @MethodSource("getListAddIntValArgs")
  public void testListadd_int_val(ListAddIntValTestScenario testScenario,RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var testMonitor=new InputTestMonitor();
    if(testScenario.nonEmpty){for(int i=0;i<100;++i){testMonitor.collectionAdd(constructionArgs,inputArgType,i);}}
    var expectedException=testScenario.expectedException;
    if(expectedException==ConcurrentModificationException.class){
      testMonitor.illegalMod(constructionArgs,inputArgType,testScenario.modScenario);
      switch(testScenario){
        case EmptyModRootThrowCMESupercedesIOBE:
        case NonEmptyModRootThrowCMESupercedesIOBE:
        case EmptyModParentThrowCMESupercedesIOBE:
        case NonEmptyModParentThrowCMESupercedesIOBE:
          //attempt an insertion too low
          Assertions.assertThrows(expectedException,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
          //attempt an insertion too high
          Assertions.assertThrows(expectedException,()->inputArgType.callListAdd(constructionArgs.seq,testMonitor.expectedSeqSize+1,0));
          break;
        default:
          Assertions.assertThrows(expectedException,()->inputArgType.callListAdd(constructionArgs.seq,testMonitor.expectedSeqSize,0));
      }
    }else{
      switch(testScenario){
        case HappyPathInsertBegin:
          for(int i=0;i<100;++i){testMonitor.listAdd(constructionArgs,inputArgType,0,i);}
          break;
        case HappyPathInsertEnd:
          for(int i=0;i<100;++i){testMonitor.listAdd(constructionArgs,inputArgType,testMonitor.expectedSeqSize,i);}
          break;
        case HappyPathInsertMidPoint:
          for(int i=0;i<100;++i){testMonitor.listAdd(constructionArgs,inputArgType,testMonitor.expectedSeqSize/2,i);}
          break;
        case ThrowIOBE:
          for(int i=0;i<100;++i){
            //too low
            Assertions.assertThrows(expectedException,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
            //too high
            Assertions.assertThrows(expectedException,()->inputArgType.callListAdd(constructionArgs.seq,testMonitor.expectedSeqSize+1,0));
            testMonitor.collectionAdd(constructionArgs,inputArgType,i);
          }
        default:
      }
    }
    testMonitor.verifyStructuralIntegrity(constructionArgs);
    int offset=constructionArgs.verifyPreAlloc();
    switch(testScenario){
      case HappyPathInsertBegin:
        offset=constructionArgs.verifyDescending(offset,inputArgType,100);
        break;
      case HappyPathInsertMidPoint:
        offset=constructionArgs.verifyMidPointInsertion(offset,inputArgType,100);
        break;
      default:
        if(!testScenario.nonEmpty){break;}
      case HappyPathInsertEnd:
      case ThrowIOBE:
        offset=constructionArgs.verifyAscending(offset,inputArgType,100);
        break;
    }
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,inputArgType,0);}
  }
  static enum ListPutIntValTestScenario{
    HappyPath(true,CMEScenario.NoMod,null),
    ThrowIOBE(false,CMEScenario.NoMod,IndexOutOfBoundsException.class),
    NonEmptyListModRootThrowCME(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyListModParentThrowCME(true,CMEScenario.ModParent,ConcurrentModificationException.class),
    EmptyListModRootThrowCMESupercedesIOBE(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyListModParentThrowCMESupercedesIOBE(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyListModRootThrowCMESupercedesIOBE(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyListModParentThrowCMESupercedesIOBE(true,CMEScenario.ModParent,ConcurrentModificationException.class);
    final boolean nonEmpty;
    final CMEScenario modScenario;
    final Class<? extends Throwable> expectedException;
    ListPutIntValTestScenario(boolean nonEmpty,CMEScenario modScenario,Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
      this.nonEmpty=nonEmpty;
      this.modScenario=modScenario;
    }
  }
  static Stream<Arguments> getListPutIntValArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(ListPutIntValTestScenario testScenario: ListPutIntValTestScenario.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));}
          if(testScenario.expectedException!=ConcurrentModificationException.class){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));}
        }
        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
            for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
              for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
                if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));}
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getListPutIntValArgs")
  public void testListput_int_val(ListPutIntValTestScenario testScenario,RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var testMonitor=new InputTestMonitor();
    if(testScenario.nonEmpty){for(int i=0;i<100;++i){testMonitor.collectionAdd(constructionArgs,inputArgType,i);}}
    var expectedException=testScenario.expectedException;
    if(expectedException==null){
      for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    }else if(expectedException==IndexOutOfBoundsException.class){
      for(int i=0;i<100;++i){
        //attempt a put too low
        Assertions.assertThrows(expectedException,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
        //attempt a put too high
        Assertions.assertThrows(expectedException,()->inputArgType.callListPut(constructionArgs.seq,testMonitor.expectedSeqSize,0));
        testMonitor.collectionAdd(constructionArgs,inputArgType,i);
      }
    }else{
      testMonitor.illegalMod(constructionArgs,inputArgType,testScenario.modScenario);
      switch(testScenario){
        case NonEmptyListModRootThrowCME:
        case NonEmptyListModParentThrowCME:
          Assertions.assertThrows(expectedException,()->inputArgType.callListPut(constructionArgs.seq,0,0));
          break;
        case EmptyListModRootThrowCMESupercedesIOBE:
        case EmptyListModParentThrowCMESupercedesIOBE:
        case NonEmptyListModRootThrowCMESupercedesIOBE:
        case NonEmptyListModParentThrowCMESupercedesIOBE:
          //attempt a put too low
          Assertions.assertThrows(expectedException,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
          //attempt a put too high
          Assertions.assertThrows(expectedException,()->inputArgType.callListPut(constructionArgs.seq,testMonitor.expectedSeqSize,0));
        default:
      }
    }
    testMonitor.verifyStructuralIntegrity(constructionArgs);
    int offset=constructionArgs.verifyPreAlloc();
    switch(testScenario){
      case HappyPath:
        offset=constructionArgs.verifyDescending(offset,inputArgType,100);
        break;
      default:
        if(!testScenario.nonEmpty){break;}
      case ThrowIOBE:
        offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    }
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,inputArgType,0);}
  }
  //TODO develop the parameterization of some of these tests to improve code dryness
  static enum CollectionAddValTestScenario
  {
    HappyPath(false,CMEScenario.NoMod,null),
    EmptyListModRootThrowCME(false,CMEScenario.ModRoot,ConcurrentModificationException.class),
    EmptyListModParentThrowCME(false,CMEScenario.ModParent,ConcurrentModificationException.class),
    NonEmptyListModRootThrowCME(true,CMEScenario.ModRoot,ConcurrentModificationException.class),
    NonEmptyListModParentThrowCME(true,CMEScenario.ModParent,ConcurrentModificationException.class);
    final boolean nonEmpty;
    final CMEScenario modScenario;
    final Class<? extends Throwable> expectedException;
    CollectionAddValTestScenario(boolean nonEmpty,CMEScenario modScenario,Class<? extends Throwable> expectedException){
      this.expectedException=expectedException;
      this.nonEmpty=nonEmpty;
      this.modScenario=modScenario;
    }
  }
  static Stream<Arguments> getCollectionAddValArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(RefInputTestArgType inputTestArgType:RefInputTestArgType.values()){
      for(CollectionAddValTestScenario testScenario: CollectionAddValTestScenario.values()){
        for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
          if(testScenario.expectedException==null){
            builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDLIST)));
            builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDLIST)));
            builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDSTACK)));
            builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDSTACK)));
          }
        }
        for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
          for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
            for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
              for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
                builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.CHECKEDSUBLIST)));
                if(testScenario.expectedException==null){builder.add(Arguments.of(testScenario,inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,StructType.UNCHECKEDSUBLIST)));}
              }
            }
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCollectionAddValArgs")
  public void testCollectionadd_val(CollectionAddValTestScenario testScenario,RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var testMonitor=new InputTestMonitor();
    if(testScenario.nonEmpty){for(int i=0;i<100;++i){testMonitor.collectionAdd(constructionArgs,inputArgType,i);}}
    var expectedException=testScenario.expectedException;
    if(expectedException==null){
      for(int i=0;i<100;++i){
        testMonitor.collectionAdd(constructionArgs,inputArgType,i);
        testMonitor.verifyStructuralIntegrity(constructionArgs);
      }
    }else{
      testMonitor.illegalMod(constructionArgs,inputArgType,testScenario.modScenario);
      Assertions.assertThrows(expectedException,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
      testMonitor.verifyStructuralIntegrity(constructionArgs);
    }
    int offset=constructionArgs.verifyPreAlloc();
    switch(testScenario){
      default:
        if(!testScenario.nonEmpty){break;}
      case HappyPath:
        offset=constructionArgs.verifyAscending(offset,inputArgType,100);
        break;
    }
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,inputArgType,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(testScenario.modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,inputArgType,0);}
  }
  @ParameterizedTest
  @MethodSource("getStackPushArgs")
  public void testpush_val_happyPath(RefInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static Stream<Arguments> getPrimitiveQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      for(QueryTestPrimitiveInputType argType:QueryTestPrimitiveInputType.values()){
        builder.add(Arguments.of(QueryTestScenario.EMPTY,argType,structType));
        builder.add(Arguments.of(QueryTestScenario.DOESNOTCONTAIN,argType,structType));
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
          case FloatMIN_INT_MINUS1:
          //negative values beyond the range of int and beyond the precision of float
          case LongMIN_INT_MINUS1:
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
  @ParameterizedTest
  @MethodSource("getPrimitiveQueryCollectionArguments")
  public void testremoveVal_ObjectprimitiveVal(QueryTestScenario testScenario,QueryTestPrimitiveInputType inputArgType,StructType structType)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    switch(testScenario){
      case CONTAINSBEGINNING:
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=1;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(0,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(100,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSMIDDLE:
        for(int i=0;i<49;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=50;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(49,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(51,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSEND:
        for(int i=0;i<99;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(99,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(1,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case DOESNOTCONTAIN:
        for(int i=0;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
      default:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        break;
    }
    boolean removedVal=false;
     switch(testScenario){
      default:
        Assertions.assertTrue(inputArgType.invokeObjectremoveVal(constructionArgs.seq));
        removedVal=true;
      case EMPTY:
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokeObjectremoveVal(constructionArgs.seq));
    }
    switch(testScenario){
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokeObjectcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(100,100);
        int offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+100);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      case EMPTY:
        Assertions.assertFalse(inputArgType.invokeObjectcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(0,0);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      default:
        Assertions.assertFalse(inputArgType.invokeObjectcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(removedVal?99:100,removedVal?101:100);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+(removedVal?99:100));
        offset=constructionArgs.verifyRootPostAlloc(offset);
        Assertions.assertEquals(removedVal,constructionArgs.root.arr[offset]==null);
    }
  }
  @ParameterizedTest
  @MethodSource("getPrimitiveQueryCollectionArguments")
  public void testremoveVal_BoxedprimitiveVal(QueryTestScenario testScenario,QueryTestPrimitiveInputType inputArgType,StructType structType)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    switch(testScenario){
      case CONTAINSBEGINNING:
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=1;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(0,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(100,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSMIDDLE:
        for(int i=0;i<49;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=50;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(49,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(51,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSEND:
        for(int i=0;i<99;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(99,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(1,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case DOESNOTCONTAIN:
        for(int i=0;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
      default:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        break;
    }
     switch(testScenario){
      default:
        Assertions.assertTrue(inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
      case EMPTY:
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
    }
    switch(testScenario){
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokeBoxedcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(100,100);
        int offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+100);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      case EMPTY:
        Assertions.assertFalse(inputArgType.invokeBoxedcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(0,0);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      default:
        Assertions.assertFalse(inputArgType.invokeBoxedcontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(99,101);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+99);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        Assertions.assertNull(constructionArgs.root.arr[offset]);
    }
  }
  @ParameterizedTest
  @MethodSource("getPrimitiveQueryCollectionArguments")
  public void testremoveVal_primitiveVal(QueryTestScenario testScenario,QueryTestPrimitiveInputType inputArgType,StructType structType)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    switch(testScenario){
      case CONTAINSBEGINNING:
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=1;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(0,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(100,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSMIDDLE:
        for(int i=0;i<49;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=50;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(49,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(51,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case CONTAINSEND:
        for(int i=0;i<99;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        switch(structType){
          case UNCHECKEDLIST:
          case CHECKEDLIST:
          case UNCHECKEDSUBLIST:
          case CHECKEDSUBLIST:
            Assertions.assertEquals(99,inputArgType.invokeindexOf(constructionArgs.seq));
            break;
          default:
            Assertions.assertEquals(1,inputArgType.invokesearch(constructionArgs.seq));
        }
        break;
      case DOESNOTCONTAIN:
        for(int i=0;i<100;++i)
        {
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
      default:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        break;
    }
    switch(testScenario){
      default:
        Assertions.assertTrue(inputArgType.invokeremoveVal(constructionArgs.seq));
      case EMPTY:
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokeremoveVal(constructionArgs.seq));
    }
    switch(testScenario){
      case DOESNOTCONTAIN:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(100,100);
        int offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+100);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      case EMPTY:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(0,0);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        break;
      default:
        Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        constructionArgs.verifyStructuralIntegrity(99,101);
        offset=constructionArgs.verifyPreAlloc();
        offset=constructionArgs.verifyParentPostAlloc(offset+99);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        Assertions.assertNull(constructionArgs.root.arr[offset]);
    }
  }
}
