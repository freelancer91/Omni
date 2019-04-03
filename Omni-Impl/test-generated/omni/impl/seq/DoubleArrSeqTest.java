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
import java.util.Objects;
import omni.impl.QueryTestInputType;
import omni.impl.QueryTestScenario;
import omni.impl.ModCheckTestObject;
import java.util.function.DoubleConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import omni.api.OmniIterator;
import omni.impl.MonitoredArrayConstructor;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
//TODO replace this with a custom collection
import java.util.ArrayList;
@SuppressWarnings({"rawtypes","unchecked"})
@Execution(ExecutionMode.CONCURRENT)
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
  /*
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
    ConstructionArguments(StructType structType){
      initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.structType=structType;
      double[] arr;
      switch(structType){
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
          rootPreAlloc=5;
          parentPreAlloc=5;
          parentPostAlloc=5;
          rootPostAlloc=5;
          arr=new double[rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc];
          initAscendingArray(arr,0,-(rootPreAlloc+parentPreAlloc),0);
          initAscendingArray(arr,rootPreAlloc+parentPreAlloc,100,100+rootPostAlloc+parentPostAlloc);
          break;
        default:
          rootPreAlloc=0;
          parentPreAlloc=0;
          parentPostAlloc=0;
          rootPostAlloc=0;
          arr=OmniArray.OfDouble.DEFAULT_ARR;
      }
      switch(structType){
        case UNCHECKEDLIST:
        case UNCHECKEDSUBLIST:
          this.root=new DoubleArrSeq.UncheckedList(rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc,arr);
          break;
        case CHECKEDSTACK:
          this.root=new DoubleArrSeq.CheckedStack();
          break;
        case UNCHECKEDSTACK:
          this.root=new DoubleArrSeq.UncheckedStack();
          break;
        default:
          this.root=new DoubleArrSeq.CheckedList(rootPreAlloc+rootPostAlloc+parentPreAlloc+parentPostAlloc,arr);
      }
      switch(structType){
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
          this.parent=((OmniList.OfDouble)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
          this.seq=((OmniList.OfDouble)parent).subList(parentPreAlloc,parentPreAlloc);
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
        case CHECKEDSUBLIST:
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
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
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
        case CHECKEDSUBLIST:
        case UNCHECKEDSUBLIST:
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
  */
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
    public void seqItrAdd(OmniListIterator.OfDouble seqItr,DoubleInputTestArgType inputArgType,int valToAdd){
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
    public void seqItrPrevious(OmniListIterator.OfDouble seqItr){
      seqItr.previousDouble();
      expectedLastRet=--expectedCursor; 
    }
    public void seqItrNext(OmniListIterator.OfDouble seqItr){
      seqItr.nextDouble();
      expectedLastRet=expectedCursor++;
    }
    public void seqItrRemove(OmniListIterator.OfDouble seqItr){
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
    public void rootMod(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      inputArgType.callCollectionAdd(constructionArgs.root,0);
    }
    public void parentMod(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      inputArgType.callCollectionAdd(constructionArgs.parent,0);
    }
    public void seqMod(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callCollectionAdd(constructionArgs.seq,0);
    }
    public void collectionAdd(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType,int val){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callCollectionAdd(constructionArgs.seq,val);
    }
    public void listAdd(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType,int index,int val){
      ++expectedRootSize;
      ++expectedRootModCount;
      ++expectedParentSize;
      ++expectedParentModCount;
      ++expectedSeqSize;
      ++expectedSeqModCount;
      inputArgType.callListAdd(constructionArgs.seq,index,val);
    }
    public void illegalMod(ConstructionArguments constructionArgs,DoubleInputTestArgType inputArgType,CMEScenario modScenario){
      switch(modScenario){
        case ModRoot:
          rootMod(constructionArgs,inputArgType);
          break;
        case ModParent:
          parentMod(constructionArgs,inputArgType);
          break;
        case ModSeq:
          seqMod(constructionArgs,inputArgType);
        case NoMod:
      }
    }
    public void verifyItrState(OmniListIterator.OfDouble seqItr,ConstructionArguments constructionArgs){
      constructionArgs.verifyIteratorState(seqItr,expectedCursor,expectedLastRet,expectedItrModCount);
    }
    public void verifyStructuralIntegrity(ConstructionArguments constructionArgs){
      constructionArgs.verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentSize,expectedParentModCount,expectedRootSize,expectedRootModCount);
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
  static enum CMEScenario{
    NoMod,
    ModSeq,
    ModParent,
    ModRoot;
  }
  static Stream<Arguments> getListAddIntValArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    return builder.build().parallel();
  }
  static Stream<Arguments> getStackPushArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.CHECKEDSTACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,StructType.UNCHECKEDSTACK)));
      }
    }
    return builder.build().parallel();
  }
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
  public void testConstructor_int_doublearr_happyPath(StructType structType){
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
    return builder.build().parallel();
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItrAddArgs")
  public void testListItrAdd(ListItrAddTestScenario testScenario,DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListItrSetArgs")
  public void testListItrSet(ListItrSetTestScenario testScenario,DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testListadd_int_val(ListAddIntValTestScenario testScenario,DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getListPutIntValArgs")
  public void testListput_int_val(ListPutIntValTestScenario testScenario,DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  static enum CollectionAddValTestScenario{
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
    for(DoubleInputTestArgType inputTestArgType:DoubleInputTestArgType.values()){
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
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getCollectionAddValArgs")
  public void testCollectionadd_val(CollectionAddValTestScenario testScenario,DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
  public void testpush_val_happyPath(DoubleInputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,inputArgType,100);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    constructionArgs.verifyRootPostAlloc(offset);
  }
  static enum QueryCast{
    AsIs,
    ToBoxed,
    ToObject;
  }
  static void buildQueryArguments(Stream.Builder<Arguments> builder,StructType structType){
    for(QueryTestInputType argType:QueryTestInputType.values()){
        for(QueryCast queryCast:QueryCast.values()){
          if(structType==StructType.CHECKEDSUBLIST){
            for(CMEScenario modScenario:CMEScenario.values()){
              builder.add(Arguments.of(modScenario,queryCast,QueryTestScenario.EMPTY,argType,structType));
              builder.add(Arguments.of(modScenario,queryCast,QueryTestScenario.DOESNOTCONTAIN,argType,structType));
            }
          }else{
            builder.add(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.EMPTY,argType,structType));
            builder.add(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.DOESNOTCONTAIN,argType,structType));
          }
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
              if(structType==StructType.CHECKEDSUBLIST){
                for(CMEScenario modScenario:CMEScenario.values()){
                  builder.add(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSBEGINNING,argType,structType));
                  builder.add(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSMIDDLE,argType,structType));
                  builder.add(Arguments.of(modScenario,queryCast,QueryTestScenario.CONTAINSEND,argType,structType));
                }
              }else{
                builder.add(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSBEGINNING,argType,structType));
                builder.add(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSMIDDLE,argType,structType));
                builder.add(Arguments.of(CMEScenario.NoMod,queryCast,QueryTestScenario.CONTAINSEND,argType,structType));
              }
            default:
              //all other enumerated values MUST return false because they are either out of range or are too precise
          }
        }
      }
  }
  static Stream<Arguments> getQueryStackArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType){
        default:
          continue;
        case CHECKEDSTACK:
        case UNCHECKEDSTACK:
      }
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryListArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType){
        case CHECKEDSTACK:
        case UNCHECKEDSTACK:
          continue;
        default:
      }
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  static Stream<Arguments> getQueryCollectionArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      buildQueryArguments(builder,structType);
    }
    return builder.build().parallel();
  }
  private static boolean illegallyModForQuery(QueryCast queryCast,CMEScenario modScenario,QueryTestInputType inputArgType,QueryTestScenario testScenario,ConstructionArguments constructionArgs){
    boolean expectThrow=false;
    switch(modScenario){
      case ModParent:
      case ModRoot:
          switch(inputArgType){
            default:
              expectThrow=true;
              break;
            case Booleannull:
            case Bytenull:
            case Characternull:
            case Shortnull:
            case Integernull:
            case Longnull:
            case Floatnull:
            case Doublenull:
              expectThrow=(queryCast==QueryCast.ToObject);
          }
      default:
    }
    switch(modScenario){
      case ModParent:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.parent,0);
        break;
      case ModRoot:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.root,0);
        break;
      default:
    }
    return expectThrow;
  }
  private static ConstructionArguments initializeSeqForQuery(QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    switch(testScenario){
      case CONTAINSBEGINNING:
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=1;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        break;
      case CONTAINSMIDDLE:
        for(int i=0;i<49;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        for(int i=50;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        break;
      case CONTAINSEND:
        for(int i=0;i<99;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
        Assertions.assertTrue(inputArgType.attemptAdd(constructionArgs.seq));
        break;
      case DOESNOTCONTAIN:
        for(int i=0;i<100;++i){
          inputArgType.addNotEqualsVal(constructionArgs.seq);
        }
      default:
        break;
    }
    return constructionArgs;
  }
  private static void verifyQueryDidNotModify(CMEScenario modScenario,ConstructionArguments constructionArgs,QueryTestScenario testScenario){
    int offset=constructionArgs.verifyPreAlloc();
    if(testScenario==QueryTestScenario.EMPTY){
      switch(modScenario){
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(0,0,1,1);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
          constructionArgs.verifyRootPostAlloc(offset);
          break;
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          offset=constructionArgs.verifyRootPostAlloc(offset);
          constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(0,0);
          offset=constructionArgs.verifyParentPostAlloc(offset);
          constructionArgs.verifyRootPostAlloc(offset);
      }
    }else{
      switch(modScenario){
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(100,100,101,101);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
          constructionArgs.verifyRootPostAlloc(offset);
          break;
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          offset=constructionArgs.verifyRootPostAlloc(offset);
          constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(100,100);
          offset=constructionArgs.verifyParentPostAlloc(offset+100);
          constructionArgs.verifyRootPostAlloc(offset);
      }
    }
  }
  static enum ObjModScenario{
    ModSeq,
    ModParent,
    ModRoot,
    ModSeqThrow,
    ModParentThrow,
    ModRootThrow,
    NoMod,
    Throw;
  }
  private static class ModCheckTestData{
    final ModCheckTestObject modCheckTestObject;
    final ConstructionArguments constructionArgs;
    ModCheckTestData(ModCheckTestObject modCheckTestObject,ConstructionArguments constructionArgs){
      this.modCheckTestObject=modCheckTestObject;
      this.constructionArgs=constructionArgs;
    }
  }
  @ParameterizedTest
  @MethodSource("getQueryListArguments")
  public void testindexOf_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=0;
        break;
      case CONTAINSMIDDLE:
        expectedRet=49;
        break;
      case CONTAINSEND:
        expectedRet=99;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedindexOf(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectindexOf(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeindexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeindexOf(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryListArguments")
  public void testlastIndexOf_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=0;
        break;
      case CONTAINSMIDDLE:
        expectedRet=49;
        break;
      case CONTAINSEND:
        expectedRet=99;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedlastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedlastIndexOf(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectlastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectlastIndexOf(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokelastIndexOf(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokelastIndexOf(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryStackArguments")
  public void testsearch_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    int expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
        expectedRet=100;
        break;
      case CONTAINSMIDDLE:
        expectedRet=51;
        break;
      case CONTAINSEND:
        expectedRet=1;
        break;
      default:
        expectedRet=-1;
    }
    switch(queryCast){
    case ToBoxed:
      Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedsearch(constructionArgs.seq));
      break;
    case ToObject:
      Assertions.assertEquals(expectedRet,inputArgType.invokeObjectsearch(constructionArgs.seq));
      break;
    case AsIs:
      Assertions.assertEquals(expectedRet,inputArgType.invokesearch(constructionArgs.seq));
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryCollectionArguments")
  public void testcontains_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    boolean expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
      case CONTAINSMIDDLE:
      case CONTAINSEND:
        expectedRet=true;
        break;
      default:
        expectedRet=false;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }
        break;
      case ToObject:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectcontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectcontains(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokecontains(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokecontains(constructionArgs.seq));
        }
    }
    verifyQueryDidNotModify(modScenario,constructionArgs,testScenario);
  }
  @ParameterizedTest
  @MethodSource("getQueryCollectionArguments")
  public void testremoveVal_val(CMEScenario modScenario,QueryCast queryCast,QueryTestScenario testScenario,QueryTestInputType inputArgType,StructType structType){
    ConstructionArguments constructionArgs=initializeSeqForQuery(testScenario,inputArgType,structType);
    boolean expectThrow=illegallyModForQuery(queryCast,modScenario,inputArgType,testScenario,constructionArgs);
    boolean expectedRet;
    switch(testScenario){
      case CONTAINSBEGINNING:
      case CONTAINSMIDDLE:
      case CONTAINSEND:
        expectedRet=true;
        break;
      default:
        expectedRet=false;
    }
    switch(queryCast){
      case ToBoxed:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeBoxedremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokeBoxedcontains(constructionArgs.seq));
        }
        break;
      case ToObject:
       if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeObjectremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeObjectremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokeObjectcontains(constructionArgs.seq));
        }
        break;
      case AsIs:
        if(expectThrow){
          Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.invokeremoveVal(constructionArgs.seq));
        }else{
          Assertions.assertEquals(expectedRet,inputArgType.invokeremoveVal(constructionArgs.seq));
          Assertions.assertFalse(inputArgType.invokecontains(constructionArgs.seq));
        }
    }
    int offset=constructionArgs.verifyPreAlloc();
    switch(modScenario){
      case ModParent:
        if(testScenario==QueryTestScenario.EMPTY){
          constructionArgs.verifyStructuralIntegrity(0,0,1,1);
        }else{
          constructionArgs.verifyStructuralIntegrity(100,100,101,101);
          offset+=100;
        }
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
        constructionArgs.verifyRootPostAlloc(offset);
        break;
      case ModRoot:
        if(testScenario==QueryTestScenario.EMPTY){
          constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
        }else{
          constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
          offset+=100;
        }
        offset=constructionArgs.verifyParentPostAlloc(offset);
        offset=constructionArgs.verifyRootPostAlloc(offset);
        constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
        break;
      case NoMod:
        switch(testScenario){
          case DOESNOTCONTAIN:
            constructionArgs.verifyStructuralIntegrity(100,100);
            offset=constructionArgs.verifyParentPostAlloc(offset+100);
            constructionArgs.verifyRootPostAlloc(offset);
            break;
          case EMPTY:
            constructionArgs.verifyStructuralIntegrity(0,0);
            offset=constructionArgs.verifyParentPostAlloc(offset);
            constructionArgs.verifyRootPostAlloc(offset);
            break;
          case CONTAINSBEGINNING:
          case CONTAINSMIDDLE:
          case CONTAINSEND:
            constructionArgs.verifyStructuralIntegrity(99,101);
            offset=constructionArgs.verifyParentPostAlloc(offset+99);
            offset=constructionArgs.verifyRootPostAlloc(offset);
        }
    }
  }
  static Stream<Arguments> getBasicCollectionTestArguments(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      builder.add(Arguments.of(structType,0,CMEScenario.NoMod));
      builder.add(Arguments.of(structType,100,CMEScenario.NoMod));
      if(structType==StructType.CHECKEDSUBLIST){
        builder.add(Arguments.of(structType,0,CMEScenario.ModParent));
        builder.add(Arguments.of(structType,100,CMEScenario.ModParent));
        builder.add(Arguments.of(structType,0,CMEScenario.ModRoot));
        builder.add(Arguments.of(structType,100,CMEScenario.ModRoot));
      }
    }
    return builder.build().parallel();
  }
  static void illegallyMod(CMEScenario modScenario,ConstructionArguments constructionArgs){
    switch(modScenario){
      case ModSeq:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,0);
        break;
      case ModParent:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.parent,0);
        break;
      case ModRoot:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.root,0);
        break;
      default:
    }
  }
  @ParameterizedTest
  @MethodSource("getBasicCollectionTestArguments")
  public void testclear_void(StructType structType,int numToAdd,CMEScenario modScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i){
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(modScenario,constructionArgs);
    if(modScenario==CMEScenario.NoMod){
      Assertions.assertDoesNotThrow(()->constructionArgs.seq.clear());
      if(numToAdd==0){
        constructionArgs.verifyStructuralIntegrity(0,0);
      }else{
        constructionArgs.verifyStructuralIntegrity(0,numToAdd+1);
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyParentPostAlloc(offset);
      offset=constructionArgs.verifyRootPostAlloc(offset);
    }else{
      Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.clear());
      if(modScenario==CMEScenario.ModParent){
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }else{
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,numToAdd);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      if(modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
      offset=constructionArgs.verifyRootPostAlloc(offset);
      if(modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
    }
  }
  @ParameterizedTest
  @MethodSource("getBasicCollectionTestArguments")
  public void testclone_void(StructType structType,int numToAdd,CMEScenario modScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i){
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(modScenario,constructionArgs);
    if(modScenario==CMEScenario.NoMod){
      var clone=constructionArgs.seq.clone();
      constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd);
      Assertions.assertNotSame(constructionArgs.seq,clone);
      Assertions.assertNotSame(constructionArgs.parent,clone);
      Assertions.assertNotSame(constructionArgs.root,clone);
      switch(structType)
      {
        case CHECKEDLIST:
        case CHECKEDSUBLIST:
          Assertions.assertTrue(clone instanceof DoubleArrSeq.CheckedList);
          Assertions.assertEquals(0,((DoubleArrSeq.CheckedList)clone).modCount);
          break;
        case UNCHECKEDLIST:
        case UNCHECKEDSUBLIST:
          Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedList);
          break;
        case CHECKEDSTACK:
          Assertions.assertTrue(clone instanceof DoubleArrSeq.CheckedStack);
          Assertions.assertEquals(0,((DoubleArrSeq.CheckedStack)clone).modCount);
          break;
        case UNCHECKEDSTACK:
          Assertions.assertTrue(clone instanceof DoubleArrSeq.UncheckedStack);
      }
      var cloneArr=((DoubleArrSeq)clone).arr;
      var cloneSize=((DoubleArrSeq)clone).size;
      Assertions.assertEquals(numToAdd,cloneSize);
      if(numToAdd==0)
      {
        Assertions.assertSame(OmniArray.OfDouble.DEFAULT_ARR,cloneArr);
      }
      else
      {
        Assertions.assertNotSame(constructionArgs.root.arr,cloneArr);
        Assertions.assertEquals(numToAdd,cloneArr.length);
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(constructionArgs.root.arr[i+constructionArgs.rootPreAlloc+constructionArgs.parentPreAlloc],cloneArr[i]);
        }
      }
    }
    else
    {
      Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.clone());
      if(modScenario==CMEScenario.ModParent)
      {
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
      else
      {
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,numToAdd);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(modScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(modScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getToArrayArrayArguments()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values()){
      for(var preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq || (preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST)){
          continue;
        }
        builder.add(Arguments.of(0,0,structType,preModScenario));
        builder.add(Arguments.of(0,5,structType,preModScenario));
        builder.add(Arguments.of(3,5,structType,preModScenario));
        builder.add(Arguments.of(5,5,structType,preModScenario));
        builder.add(Arguments.of(8,5,structType,preModScenario));
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getToArrayArrayArguments")
  public void testtoArray_ObjectArray(int seqSize,int arrayLength,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i)
    {
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    final Double[] paramArr=new Double[arrayLength];
    for(int i=0;i<arrayLength;++i)
    {
      paramArr[i]=TypeConversionUtil.convertToDouble(100);
    }
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToDouble(100),paramArr[i]);
        }
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.toArray(paramArr));
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
        for(int i=0;i<arrayLength;++i)
        {
          Assertions.assertEquals(TypeConversionUtil.convertToDouble(100),paramArr[i]);
        }
        break;
      }
      default:
      {
        Object[] outputArr=constructionArgs.seq.toArray(paramArr);
        constructionArgs.verifyStructuralIntegrity(seqSize,seqSize);
        if(seqSize<arrayLength)
        {
          Assertions.assertSame(outputArr,paramArr);
          Assertions.assertNull(outputArr[seqSize]);
          for(int i=seqSize+1;i<arrayLength;++i)
          {
            Assertions.assertEquals(TypeConversionUtil.convertToDouble(100),outputArr[i]);
          }
        }
        else if(seqSize==arrayLength)
        {
          Assertions.assertSame(outputArr,paramArr);
        }
        else
        {
          Assertions.assertNotSame(outputArr,paramArr);
          for(int i=0;i<arrayLength;++i)
          {
            Assertions.assertEquals(TypeConversionUtil.convertToDouble(100),paramArr[i]);
          }
          Assertions.assertEquals(seqSize,outputArr.length);
        }
        var itr=constructionArgs.seq.iterator();
        for(int i=0;i<seqSize;++i)
        {
          Assertions.assertEquals(itr.next(),outputArr[i]);
        }
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,seqSize);
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getSizeAndIsEmptyTestArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values()){
      for(var preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq || (preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST)){
          continue;
        }
        builder.add(Arguments.of(0,structType,preModScenario));
        builder.add(Arguments.of(100,structType,preModScenario));
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getSizeAndIsEmptyTestArgs")
  public void testsize_void(int numToAdd,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i)
    {
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      default:
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(numToAdd-i,constructionArgs.seq.size());
          constructionArgs.verifyStructuralIntegrity(numToAdd-i,i+numToAdd);
          switch(structType)
          {
            case CHECKEDSTACK:
            case UNCHECKEDSTACK:
              ((OmniStack.OfDouble)constructionArgs.seq).popDouble();
              break;
            default:
              ((OmniList.OfDouble)constructionArgs.seq).removeDoubleAt(numToAdd-i-1);
          }
        }
        Assertions.assertEquals(0,constructionArgs.seq.size());
        constructionArgs.verifyStructuralIntegrity(0,numToAdd*2);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    if(preModScenario!=CMEScenario.NoMod){offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,numToAdd);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
  }
  @ParameterizedTest
  @MethodSource("getSizeAndIsEmptyTestArgs")
  public void testisEmpty_void(int numToAdd,StructType structType,CMEScenario preModScenario)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<numToAdd;++i)
    {
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    switch(preModScenario)
    {
      case ModParent:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      case ModRoot:
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        break;
      }
      default:
      {
        for(int i=0;i<numToAdd;++i)
        {
          Assertions.assertEquals(numToAdd-i==0,constructionArgs.seq.isEmpty());
          constructionArgs.verifyStructuralIntegrity(numToAdd-i,i+numToAdd);
          switch(structType)
          {
            case CHECKEDSTACK:
            case UNCHECKEDSTACK:
              ((OmniStack.OfDouble)constructionArgs.seq).popDouble();
              break;
            default:
              ((OmniList.OfDouble)constructionArgs.seq).removeDoubleAt(numToAdd-i-1);
          }
        }
        Assertions.assertTrue(constructionArgs.seq.isEmpty());
        constructionArgs.verifyStructuralIntegrity(0,numToAdd*2);
      }
    }
    int offset=constructionArgs.verifyPreAlloc();
    if(preModScenario!=CMEScenario.NoMod){offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,numToAdd);}
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(preModScenario==CMEScenario.ModRoot){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getHashCodeTestArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      switch(structType)
      {
        case CHECKEDSUBLIST:
          builder.add(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.ModParent));
          builder.add(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.ModParent));
          builder.add(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.ModRoot));
          builder.add(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.ModRoot));
        case CHECKEDSTACK:
        case CHECKEDLIST:
        default:
          builder.add(Arguments.of(structType,0,ObjModScenario.NoMod,CMEScenario.NoMod));
          builder.add(Arguments.of(structType,100,ObjModScenario.NoMod,CMEScenario.NoMod));
      }
    }
    return builder.build().parallel();
  }
  private static ModCheckTestData initializeForBasicModCheckableTest(StructType structType,int numToAdd,ObjModScenario objModScenario,CMEScenario preModScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    ModCheckTestObject modCheckTestObject=null;
    if(objModScenario==ObjModScenario.NoMod){
       for(int i=0;i<numToAdd;++i){
          DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
        }
    }else{
      switch(objModScenario){
        case ModSeq:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.seq);
          break;
        case ModSeqThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.seq);
          break;
        case ModParent:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.parent);
          break;
        case ModParentThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.parent);
          break;
        case ModRoot:
          modCheckTestObject=new ModCheckTestObject.Modding((OmniCollection.OfRef<?>)constructionArgs.root);
          break;
        case ModRootThrow:
          modCheckTestObject=new ModCheckTestObject.ModdingAndThrowing((OmniCollection.OfRef<?>)constructionArgs.root);
          break;
        default:
          modCheckTestObject=new ModCheckTestObject();
      }
      for(int i=0;i<numToAdd;++i){
        ((OmniCollection.OfRef)constructionArgs.seq).add(modCheckTestObject);
      }
    }
    illegallyMod(preModScenario,constructionArgs);
    return new ModCheckTestData(modCheckTestObject,constructionArgs);
  }
  @ParameterizedTest
  @MethodSource("getHashCodeTestArgs")
  public void testhashCode_void(StructType structType,int numToAdd,ObjModScenario objModScenario,CMEScenario preModScenario){
    ModCheckTestData modCheckTestData=initializeForBasicModCheckableTest(structType,numToAdd,objModScenario,preModScenario);
    ConstructionArguments constructionArgs=modCheckTestData.constructionArgs;
      if(preModScenario==CMEScenario.NoMod){
        int expectedHash=1;
        switch(structType){
          case CHECKEDSTACK:
          case UNCHECKEDSTACK:
            for(int i=numToAdd;--i>=0;)
            {
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertTodouble(i));
            }
            break;
          default:
            for(int i=0;i<numToAdd;++i)
            {
              expectedHash=(expectedHash*31)+Objects.hashCode(TypeConversionUtil.convertTodouble(i));
            }
        }
        Assertions.assertEquals(expectedHash,constructionArgs.seq.hashCode());
        constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd);
      }
      else
      {
        Assertions.assertThrows(ConcurrentModificationException.class,()->constructionArgs.seq.hashCode());
        if(preModScenario==CMEScenario.ModParent)
        {
          constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        }
        else
        {
          constructionArgs.verifyStructuralIntegrity(numToAdd,numToAdd,numToAdd,numToAdd,numToAdd+1,numToAdd+1);
        }
      }
      int offset=constructionArgs.verifyPreAlloc();
      offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,numToAdd);
      offset=constructionArgs.verifyParentPostAlloc(offset);
      if(preModScenario==CMEScenario.ModParent){offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
      offset=constructionArgs.verifyRootPostAlloc(offset);
      if(preModScenario==CMEScenario.ModRoot){constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);}
  }
  static Stream<Arguments> getToArrayIntFunctionArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(StructType structType:StructType.values()){
      for(CMEScenario preModScenario:CMEScenario.values()){
        if(preModScenario==CMEScenario.ModSeq){
          continue;
        }
        if(preModScenario!=CMEScenario.NoMod && structType!=StructType.CHECKEDSUBLIST){
          continue;
        }
        for(int seqSize=0;seqSize<=5;seqSize+=5){
          switch(structType){
            case CHECKEDSUBLIST:
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModParentThrow,preModScenario));
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModRootThrow,preModScenario));
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModParent,preModScenario));
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModRoot,preModScenario));
            case CHECKEDLIST:
            case CHECKEDSTACK:
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModSeqThrow,preModScenario));
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.ModSeq,preModScenario));
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.Throw,preModScenario));
            default:
              builder.add(Arguments.of(structType,seqSize,ObjModScenario.NoMod,preModScenario));
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getToArrayIntFunctionArgs")
  public void testtoArray_IntFunction(StructType structType,int seqSize,ObjModScenario arrConstructorModScenario,CMEScenario preModScenario){
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i){
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    illegallyMod(preModScenario,constructionArgs);
    MonitoredArrayConstructor monitoredArrConstructor;
    Class<Double> componentClass=Double.class;
    switch(arrConstructorModScenario){
      case Throw:
        monitoredArrConstructor=new MonitoredArrayConstructor.Throwing<>(componentClass);
        break;
      case ModSeqThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.seq);
        break;
      case ModParentThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.parent);
        break;
      case ModRootThrow:
        monitoredArrConstructor=new MonitoredArrayConstructor.ModdingAndThrowing<>(componentClass,constructionArgs.root);
        break;
      case ModSeq:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.seq);
        break;
      case ModParent:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.parent);
        break;
      case ModRoot:
        monitoredArrConstructor=new MonitoredArrayConstructor.Modding<>(componentClass,constructionArgs.root);
        break;
      default:
        monitoredArrConstructor=new MonitoredArrayConstructor<>(componentClass);
    }
    Class<? extends Throwable> expectedException=preModScenario==CMEScenario.NoMod?monitoredArrConstructor.expectedException:ConcurrentModificationException.class;
    if(expectedException==null){
      var outputArray=constructionArgs.seq.toArray(monitoredArrConstructor);
      constructionArgs.verifyStructuralIntegrity(seqSize,seqSize);
      Assertions.assertNotSame(outputArray,constructionArgs.root.arr);
      Assertions.assertEquals(seqSize,outputArray.length);
      var itr=constructionArgs.seq.iterator();
      for(int i=0;i<seqSize;++i)
      {
        Assertions.assertEquals(itr.next(),outputArray[i]);
      }
    }else{
      Assertions.assertThrows(expectedException,()->constructionArgs.seq.toArray(monitoredArrConstructor));
    }
    int offset=constructionArgs.verifyPreAlloc();
    offset=constructionArgs.verifyAscending(offset,DoubleInputTestArgType.ARRAY_TYPE,seqSize);
    if(arrConstructorModScenario==ObjModScenario.ModSeq||arrConstructorModScenario==ObjModScenario.ModSeqThrow){
      switch(preModScenario)
      {
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize+1,seqSize+1);
          offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
      }
    }
    offset=constructionArgs.verifyParentPostAlloc(offset);
    if(arrConstructorModScenario==ObjModScenario.ModParent||arrConstructorModScenario==ObjModScenario.ModParentThrow){
      switch(preModScenario){
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+2,seqSize+2);
          offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1);
          offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
      }
    }
    if(preModScenario==CMEScenario.ModParent){
      offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    }
    offset=constructionArgs.verifyRootPostAlloc(offset);
    if(arrConstructorModScenario==ObjModScenario.ModRoot||arrConstructorModScenario==ObjModScenario.ModRootThrow){
      switch(preModScenario){
        case ModRoot:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+2,seqSize+2);
          break;
        case ModParent:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize+1,seqSize+1,seqSize+2,seqSize+2);
          break;
        default:
          constructionArgs.verifyStructuralIntegrity(seqSize,seqSize,seqSize,seqSize,seqSize+1,seqSize+1);
      }
      offset=constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    }
    if(preModScenario==CMEScenario.ModRoot){
      constructionArgs.verifyIndex(offset,DoubleInputTestArgType.ARRAY_TYPE,0);
    }
    Assertions.assertEquals(1,monitoredArrConstructor.numCalls);
  }
  static enum FunctionCallType
  {
    BoxedType,
    AsIs;
  }
  static enum ItrType
  {
    ListItr,
    Itr;
  }
  static enum ItrForEachModScenario
  {
    ModItr,
    ModItrThrow,
    ModSeq,
    ModSeqThrow,
    ModParent,
    ModParentThrow,
    ModRoot,
    ModRootThrow,
    Throw,
    NoMod;
  }
  static Stream<Arguments> getItrForEachArguments()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(var structType:StructType.values())
    {
      for(var itrType:ItrType.values())
      {
        if(itrType==ItrType.ListItr && (structType==StructType.CHECKEDSTACK || structType==StructType.UNCHECKEDSTACK))
        {
          continue;
        }
        for(var preModScenario:CMEScenario.values())
        {
          if((!structType.checked && preModScenario!=CMEScenario.NoMod) || (structType!=StructType.CHECKEDSUBLIST && (preModScenario==CMEScenario.ModParent||preModScenario==CMEScenario.ModRoot)))
          {
            continue;
          }
          for(var itrModScenario:ItrForEachModScenario.values())
          {
            if((!structType.checked && itrModScenario!=ItrForEachModScenario.NoMod) || (structType!=StructType.CHECKEDSUBLIST && (itrModScenario==ItrForEachModScenario.ModRoot || itrModScenario==ItrForEachModScenario.ModParent || itrModScenario==ItrForEachModScenario.ModRootThrow || itrModScenario==ItrForEachModScenario.ModParentThrow)))
            {
              continue;
            }
            for(var functionCallType:FunctionCallType.values())
            {
              for(int seqSize=0;seqSize<=5;seqSize+=5)
              {
                builder.add(Arguments.of(structType,seqSize,preModScenario,itrModScenario,itrType,functionCallType));
              }
            }
          }
        }
      }
    }
    return builder.build().parallel();
  }
  @ParameterizedTest
  @MethodSource("getItrForEachArguments")
  public void testItrforEachRemaining(StructType structType,int seqSize,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario,ItrType itrType,FunctionCallType functionCallType)
  {
    ConstructionArguments constructionArgs=new ConstructionArguments(structType);
    for(int i=0;i<seqSize;++i){
      DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(constructionArgs.seq,i);
    }
    OmniIterator.OfDouble itr;
    if(itrType==ItrType.ListItr)
    {
      itr=((OmniList.OfDouble)constructionArgs.seq).listIterator();
    }
    else
    {
      itr=constructionArgs.seq.iterator();
    }
    illegallyMod(preModScenario,constructionArgs);
    ArrayList consumerMonitor=new ArrayList();
    final DoubleConsumer consumer;
    Class<? extends Throwable> expectedException;
    switch(itrForEachModScenario)
    {
      case ModItr:
        consumer=(v)->
        {
          itr.nextDouble();
          itr.remove();
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModItrThrow:
        consumer=(v)->
        {
          itr.nextDouble();
          itr.remove();
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModSeq:
        consumer=(v)->
        {
          constructionArgs.seq.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModSeqThrow:
        consumer=(v)->
        {
          constructionArgs.seq.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModParent:
        consumer=(v)->
        {
          constructionArgs.parent.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModParentThrow:
        consumer=(v)->
        {
          constructionArgs.parent.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModRoot:
        consumer=(v)->
        {
          constructionArgs.root.remove(v);
          consumerMonitor.add(v);
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case ModRootThrow:
        consumer=(v)->
        {
          constructionArgs.root.remove(v);
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:ConcurrentModificationException.class;
        break;
      case Throw:
        consumer=(v)->
        {
          consumerMonitor.add(v);
          throw new IndexOutOfBoundsException();
        };
        expectedException=seqSize==0?null:IndexOutOfBoundsException.class;
        break;
      default:
        consumer=(v)->
        {
          consumerMonitor.add(v);
        };
        expectedException=null;
    }
    if(expectedException==null)
    {
      if(functionCallType==FunctionCallType.AsIs)
      {
        itr.forEachRemaining(consumer);
      }
      else
      {
        itr.forEachRemaining((Consumer<? super Double>)consumer::accept);
      }
    }
    else
    {
      if(functionCallType==FunctionCallType.AsIs)
      {
        Assertions.assertThrows(expectedException,()->itr.forEachRemaining(consumer));
      }
      else
      {
        Assertions.assertThrows(expectedException,()->itr.forEachRemaining((Consumer<? super Double>)consumer::accept));
      }
    }
    verifyIteratorState(constructionArgs,itr,seqSize,preModScenario,itrForEachModScenario);
    verifyStructuralIntegrity(seqSize,constructionArgs,preModScenario,itrForEachModScenario);
    verifyConsumerMonitor(consumerMonitor,seqSize,constructionArgs,preModScenario,itrForEachModScenario); 
  }
  private static void verifyIteratorState(ConstructionArguments constructionArgs,Object iterator,int seqSize,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
            switch(constructionArgs.structType)
            {
              case CHECKEDSTACK:
              case UNCHECKEDSTACK:
              default:
            }
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
  private static void verifyStructuralIntegrity(int seqSize,ConstructionArguments constructionArgs,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
  private static void verifyConsumerMonitor(ArrayList consumerMonitor,int seqSize,ConstructionArguments constructionArgs,CMEScenario preModScenario,ItrForEachModScenario itrForEachModScenario)
  {
    switch(itrForEachModScenario)
    {
      case ModItr:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModItrThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeq:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModSeqThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParent:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModParentThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRoot:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case ModRootThrow:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      case Throw:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
        break;
      default:
        switch(preModScenario)
        {
          case ModSeq:
          case ModParent:
          case ModRoot:
          default:
        }
    }
  }
}
