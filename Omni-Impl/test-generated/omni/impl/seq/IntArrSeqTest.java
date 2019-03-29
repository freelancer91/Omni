package omni.impl.seq;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.function.IntFunction;
import java.util.function.Consumer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import java.util.function.IntConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class IntArrSeqTest{
  private static enum StructType{STACK,LIST,SUBLIST;}
  private static enum InputTestArgType{
     ARRAY_TYPE{
       void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToint(valToConvert));}
       void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToint(valToConvert));}
       void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToint(valToConvert));}
       void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToint(valToConvert));}
       void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToint(valToConvert));}
       boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToint(valToConvert));}
       void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
     }
    ,
    BOXED_TYPE{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToInteger(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToInteger(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToInteger(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToInteger(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToInteger(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToInteger(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    PRIMITIVE_BOOLEAN{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToboolean(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToboolean(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToboolean(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToboolean(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedValToConvert),actualVal);}
    }
    ,
    BOXED_BOOLEAN{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToBoolean(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToBoolean(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertTointboolean(expectedValToConvert),actualVal);}
    }
    ,
    PRIMITIVE_BYTE{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertTobyte(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertTobyte(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertTobyte(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertTobyte(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    BOXED_BYTE{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToByte(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToByte(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToByte(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToByte(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    PRIMTIVE_SHORT{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToshort(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToshort(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToshort(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToshort(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToshort(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToshort(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    BOXED_SHORT{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToShort(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToShort(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToShort(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToShort(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToShort(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToShort(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    PRIMITIVE_CHAR{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertTochar(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertTochar(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertTochar(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertTochar(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertTochar(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertTochar(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ,
    BOXED_CHAR{
      void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert){itr.add(TypeConversionUtil.convertToCharacter(valToConvert));}
      void callListItrSet(OmniListIterator.OfInt itr,int valToConvert){itr.set(TypeConversionUtil.convertToCharacter(valToConvert));}
      void callListPut(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).put(index,TypeConversionUtil.convertToCharacter(valToConvert));}
      void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert){((OmniList.OfInt)seq).add(index,TypeConversionUtil.convertToCharacter(valToConvert));}
      void callStackPush(OmniCollection.OfInt seq,int valToConvert){((OmniStack.OfInt)seq).push(TypeConversionUtil.convertToCharacter(valToConvert));}
      boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert){return seq.add(TypeConversionUtil.convertToCharacter(valToConvert));}
      void verifyIndex(int expectedValToConvert,int actualVal){Assertions.assertEquals(TypeConversionUtil.convertToint(expectedValToConvert),actualVal);}
    }
    ;
    abstract void callListItrAdd(OmniListIterator.OfInt itr,int valToConvert);
    abstract void callListItrSet(OmniListIterator.OfInt itr,int valToConvert);
    abstract void callListPut(OmniCollection.OfInt seq,int index,int valToConvert);
    abstract void callListAdd(OmniCollection.OfInt seq,int index,int valToConvert);
    abstract void callStackPush(OmniCollection.OfInt seq,int valToConvert);
    abstract boolean callCollectionAdd(OmniCollection.OfInt seq,int valToConvert);
    private int verifyAscending(ConstructionArguments constructionArgs,int offset,int length,int loVal){return verifyAscending(constructionArgs.root.arr,offset,length,loVal);}
    private int verifyDescending(ConstructionArguments constructionArgs,int offset,int length,int loVal){return verifyDescending(constructionArgs.root.arr,offset,length,loVal);}
    private int verifyMidPointInsertion(ConstructionArguments constructionArgs,int offset,int length,int loVal){return verifyMidPointInsertion(constructionArgs.root.arr,offset,length,loVal);}
    private int verifyIndex(ConstructionArguments constructionArgs,int index,int expectedValToConvert){
      verifyIndex(expectedValToConvert,constructionArgs.root.arr[index]);
      return index+1; 
    }
    private int verifyAscending(int[] arr,int offset,int length,int loVal){
      int bound=offset+length;
      for(int i=offset;i<bound;++i,++loVal){verifyIndex(loVal,arr[i]);}
      return bound;
    }
    private int verifyDescending(int[] arr,int offset,int length,int loVal){
      int bound=offset+length;
      for(int i=bound;--i>=offset;++loVal){verifyIndex(loVal,arr[i]);}
      return bound;
    }
    private int verifyMidPointInsertion(int[] arr,int offset,int length,int loVal){
      int i;
      for(int v=loVal+1,b=(i=offset)+length/2;i<b;++i,v+=2){verifyIndex(v,arr[i]);}
      for(int v=loVal+length-2,b=i+length/2;i<b;++i,v-=2){verifyIndex(v,arr[i]);}
      return offset+length;
    }
    abstract void verifyIndex(int expectedValToConvert,int actualVal);
  }
  private static class ConstructionArguments{
    final int initialCapacity;
    final int rootPreAlloc;
    final int rootPostAlloc;
    final int parentPreAlloc;
    final int parentPostAlloc;
    final StructType structType;
    final int rootSize;
    final int parentSize;
    final int preAlloc;
    final int postAlloc;
    final boolean checked;
    final OmniCollection.OfInt seq;
    final OmniCollection.OfInt parent;
    final IntArrSeq root;
    ConstructionArguments(int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc,boolean checked){
      this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
      this.rootPreAlloc=rootPreAlloc;
      this.rootPostAlloc=rootPostAlloc;
      this.parentPreAlloc=parentPreAlloc;
      this.parentPostAlloc=parentPostAlloc;
      this.parentSize=parentPreAlloc+parentPostAlloc;
      this.rootSize=parentSize+rootPreAlloc+rootPostAlloc;
      this.preAlloc=rootPreAlloc+parentPreAlloc;
      this.postAlloc=rootPostAlloc+parentPostAlloc;
      this.structType=StructType.SUBLIST;
      this.checked=checked;
      if(rootSize==0){
        this.root=checked?new IntArrSeq.CheckedList():new IntArrSeq.UncheckedList();
      }else{
        int[] arr=new int[rootSize];
        initAscendingArray(arr,0,-preAlloc,0);
        initAscendingArray(arr,preAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=checked?new IntArrSeq.CheckedList(rootSize,arr):new IntArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfInt)root).subList(rootPreAlloc,preAlloc+parentPostAlloc);
      this.seq=((OmniList.OfInt)parent).subList(parentPreAlloc,parentPreAlloc);
    }
    ConstructionArguments(int initialCapacity,boolean checked,StructType structType){
      this.initialCapacity=initialCapacity;
      this.rootPreAlloc=0;
      this.rootPostAlloc=0;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.parentSize=0;
      this.rootSize=0;
      this.preAlloc=0;
      this.postAlloc=0;
      this.structType=structType;
      this.checked=checked;
      if(structType==StructType.STACK){
        this.root=checked?new IntArrSeq.CheckedStack(initialCapacity):new IntArrSeq.UncheckedStack(initialCapacity);
      }else{
        this.root=checked?new IntArrSeq.CheckedList(initialCapacity):new IntArrSeq.UncheckedList(initialCapacity);
      }
      this.parent=root;
      this.seq=root;
    }
    public String toString(){
      StringBuilder builder=new StringBuilder(checked?"Checked":"Unchecked");
      switch(structType){
        case STACK:
          builder.append("Stack{").append(initialCapacity);
          break;
        case LIST:
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
        case LIST:
          if(checked){
             actualCursor=FieldAccessor.IntArrSeq.CheckedList.Itr.cursor(itr);
             actualParent=FieldAccessor.IntArrSeq.CheckedList.Itr.parent(itr);
             Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedList.Itr.modCount(itr));
             Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.IntArrSeq.CheckedList.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.IntArrSeq.UncheckedList.Itr.cursor(itr);
            actualParent=FieldAccessor.IntArrSeq.UncheckedList.Itr.parent(itr);
          }
          break;
        case STACK:
          if(checked){
            actualCursor=FieldAccessor.IntArrSeq.CheckedStack.Itr.cursor(itr);
            actualParent=FieldAccessor.IntArrSeq.CheckedStack.Itr.parent(itr);
            Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedStack.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.IntArrSeq.CheckedStack.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.IntArrSeq.UncheckedStack.Itr.cursor(itr);
            actualParent=FieldAccessor.IntArrSeq.UncheckedStack.Itr.parent(itr);
          }
          break;
        default:
          if(checked){
            actualCursor=FieldAccessor.IntArrSeq.CheckedSubList.Itr.cursor(itr);
            actualParent=FieldAccessor.IntArrSeq.CheckedSubList.Itr.parent(itr);
            Assertions.assertEquals(expectedModCount,FieldAccessor.IntArrSeq.CheckedSubList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.IntArrSeq.CheckedSubList.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.IntArrSeq.UncheckedSubList.Itr.cursor(itr);
            actualParent=FieldAccessor.IntArrSeq.UncheckedSubList.Itr.parent(itr);
          }
      }
      Assertions.assertEquals(expectedCursor+preAlloc,actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);}
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount){
      switch(structType){
        case STACK:
          if(checked){Assertions.assertEquals(expectedRootModCount,FieldAccessor.IntArrSeq.CheckedStack.modCount(root));}
          break;
        case LIST:
          if(checked){Assertions.assertEquals(expectedRootModCount,FieldAccessor.IntArrSeq.CheckedList.modCount(root));}
          break;
        default:
          OmniList.OfInt actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfInt actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(checked){
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
          Assertions.assertEquals(expectedParentSize+parentSize,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+rootSize,FieldAccessor.IntArrSeq.size(root));
    }
  }
  private static void initAscendingArray(int[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){arr[i]=TypeConversionUtil.convertToint(lo);}
  }
  private static final Arguments[] NON_SUBLIST_TYPES=new Arguments[]{
    Arguments.of(false,StructType.LIST),
    Arguments.of(true,StructType.LIST),
    Arguments.of(false,StructType.STACK),
    Arguments.of(true,StructType.STACK)
  };
  static Stream<Arguments> getArgsForNonSubListTypes(){return Stream.of(NON_SUBLIST_TYPES);}
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_happyPath(boolean checked,StructType structType){
    IntArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList()):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.LIST?new IntArrSeq.UncheckedList():new IntArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfInt.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_intarr_happyPath(boolean checked,StructType structType){
    int size=5;
    int[] arr=new int[10];
    IntArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList(size,arr)):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.LIST?new IntArrSeq.UncheckedList(size,arr):new IntArrSeq.UncheckedStack(size,arr);
    }
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
  }
  static Stream<Arguments> getArgsFortestConstructor_int_happyPath(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
      builder.add(Arguments.of(initialCapacity,false,StructType.LIST));
      builder.add(Arguments.of(initialCapacity,true,StructType.LIST));
      builder.add(Arguments.of(initialCapacity,false,StructType.STACK));
      builder.add(Arguments.of(initialCapacity,true,StructType.STACK));
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getArgsFortestConstructor_int_happyPath")
  public void testConstructor_int_happyPath(int initialCapacity,boolean checked,StructType structType){
    IntArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.IntArrSeq.CheckedList.modCount(seq=new IntArrSeq.CheckedList(initialCapacity)):FieldAccessor.IntArrSeq.CheckedStack.modCount(seq=new IntArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.LIST?new IntArrSeq.UncheckedList(initialCapacity):new IntArrSeq.UncheckedStack(initialCapacity);
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
    for(InputTestArgType inputTestArgType:InputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.LIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,false)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getCollectionInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.LIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,false)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getStackInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.STACK)));
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertBegin(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previousInt();
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyDescending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertBegin(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,0,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyDescending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertEnd(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,-1,i+1);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertEnd(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertMidPoint(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0){seqItr.previousInt();}
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyMidPointInsertion(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testadd_int_val_happyPathInsertMidPoint(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyMidPointInsertion(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItrset_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=100;--i>=0;){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){
      seqItr.nextInt();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
    for(int i=0;i<100;++i){
      seqItr.previousInt();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,100-i-1,100-i-1,100);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyDescending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testput_int_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){constructionArgs.seq.add(TypeConversionUtil.convertToint(i));}
    for(int i=0;i<100;++i){inputArgType.callListPut(constructionArgs.seq,100-i-1,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyDescending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){inputArgType.callStackPush(constructionArgs.seq,i);}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));}
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values()){
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));}
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));}
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testput_int_val_throwIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs.root.arr,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs.root.arr,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_emptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,-1,0);
    constructionArgs.verifyStructuralIntegrity(1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItradd_val_nonEmptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i){seqItr.add(TypeConversionUtil.convertToint(i));}
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrAdd(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,100,-1,100);
    constructionArgs.verifyStructuralIntegrity(101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAset_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,1));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,1,1,2,2);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,0)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(1,1,2,2);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
            InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
          ,0)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_emptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertToint(0));
    seqItr.previousInt();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertToint(0));
    //attempt to set
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListItrSet(seqItr,0));
    constructionArgs.verifyIteratorState(seqItr,0,0,1);
    constructionArgs.verifyStructuralIntegrity(2,2);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,0)
      ,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100,-constructionArgs.preAlloc)
        ,0)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
            InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100,-constructionArgs.preAlloc)
          ,0)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrset_val_nonEmptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
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
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100,-constructionArgs.preAlloc)
        ,0)
      ,0)
    ,constructionArgs.postAlloc,100);
  }
  static Stream<Arguments> getCheckedSubListInputMethodArgs(){
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values()){
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5){
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5){
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5){
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5){builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));}
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,constructionArgs.parentPostAlloc+100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callCollectionAdd(constructionArgs.seq,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,constructionArgs.parentPostAlloc+100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,99,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModRootthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_emptyListModParentthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,1,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,constructionArgs.parentPostAlloc+100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListadd_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt an insertion too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
    //attempt an insertion too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListAdd(constructionArgs.seq,101,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModRootthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,constructionArgs.postAlloc,100)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_emptyListModParentthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,0,0));
    constructionArgs.verifyStructuralIntegrity(0,0,1,1);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
          InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
        ,constructionArgs.parentPostAlloc,100)
      ,0)
    ,constructionArgs.rootPostAlloc,constructionArgs.parentPostAlloc+100);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModRootthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the root
    constructionArgs.root.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc)
    ,0);
  }
  @ParameterizedTest
  @MethodSource("getCheckedSubListInputMethodArgs")
  public void testSubListput_int_val_nonEmptyListModParentthrowCMEsupercedesIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs){
    for(int i=0;i<100;++i){
      constructionArgs.seq.add(TypeConversionUtil.convertToint(i));
    }
    //illegally modify the parent
    constructionArgs.parent.add(TypeConversionUtil.convertToint(0));
    //attempt a put too low
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
    //attempt a put too high
    Assertions.assertThrows(ConcurrentModificationException.class,()->inputArgType.callListPut(constructionArgs.seq,100,0));
    constructionArgs.verifyStructuralIntegrity(100,100,101,101);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      InputTestArgType.ARRAY_TYPE.verifyIndex(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc+100+constructionArgs.parentPostAlloc,-constructionArgs.preAlloc)
      ,0)
    ,constructionArgs.rootPostAlloc,100+constructionArgs.parentPostAlloc);
  }
  //TODO checked list/sublist iterator ISE add methods
}
