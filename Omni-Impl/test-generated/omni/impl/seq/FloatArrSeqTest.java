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
import omni.function.FloatConsumer;
import java.util.ConcurrentModificationException;
import omni.util.OmniArray;
import omni.api.OmniList;
import omni.api.OmniStack;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FloatArrSeqTest{
  private static enum StructType{
    STACK,
    LIST,
    SUBLIST;
  }
  private static enum InputTestArgType{
     ARRAY_TYPE{
       void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
         itr.add(TypeConversionUtil.convertTofloat(valToConvert));
       }
       void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
         itr.set(TypeConversionUtil.convertTofloat(valToConvert));
       }
       void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
         ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTofloat(valToConvert));
       }
       void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
         ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTofloat(valToConvert));
       }
       void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
         ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTofloat(valToConvert));
       }
       boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
         return seq.add(TypeConversionUtil.convertTofloat(valToConvert));
       }
       void verifyIndex(int expectedValToConvert,float actualVal){
         Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
       }
     }
    ,
    BOXED_TYPE{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToFloat(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToFloat(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToFloat(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToFloat(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToFloat(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToFloat(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMITIVE_BOOLEAN{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToboolean(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToboolean(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToboolean(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToboolean(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToboolean(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToboolean(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_BOOLEAN{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToBoolean(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToBoolean(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToBoolean(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToBoolean(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToBoolean(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToBoolean(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloatboolean(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMITIVE_BYTE{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertTobyte(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertTobyte(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTobyte(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTobyte(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTobyte(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertTobyte(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_BYTE{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToByte(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToByte(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToByte(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToByte(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToByte(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToByte(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMTIVE_SHORT{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToshort(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToshort(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToshort(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToshort(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToshort(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToshort(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_SHORT{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToShort(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToShort(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToShort(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToShort(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToShort(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToShort(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMITIVE_CHAR{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertTochar(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertTochar(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTochar(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTochar(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTochar(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertTochar(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_CHAR{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToCharacter(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToCharacter(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToCharacter(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToCharacter(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToCharacter(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToCharacter(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMITIVE_INT{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToint(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToint(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToint(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToint(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToint(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToint(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_INT{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToInteger(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToInteger(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToInteger(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToInteger(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToInteger(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToInteger(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    PRIMITIVE_LONG{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertTolong(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertTolong(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertTolong(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertTolong(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertTolong(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertTolong(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ,
    BOXED_LONG{
      void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert){
        itr.add(TypeConversionUtil.convertToLong(valToConvert));
      }
      void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert){
        itr.set(TypeConversionUtil.convertToLong(valToConvert));
      }
      void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).put(index,TypeConversionUtil.convertToLong(valToConvert));
      }
      void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert){
        ((OmniList.OfFloat)seq).add(index,TypeConversionUtil.convertToLong(valToConvert));
      }
      void callStackPush(OmniCollection.OfFloat seq,int valToConvert){
        ((OmniStack.OfFloat)seq).push(TypeConversionUtil.convertToLong(valToConvert));
      }
      boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert){
        return seq.add(TypeConversionUtil.convertToLong(valToConvert));
      }
      void verifyIndex(int expectedValToConvert,float actualVal){
        Assertions.assertEquals(TypeConversionUtil.convertTofloat(expectedValToConvert),actualVal);
      }
    }
    ;
    abstract void callListItrAdd(OmniListIterator.OfFloat itr,int valToConvert);
    abstract void callListItrSet(OmniListIterator.OfFloat itr,int valToConvert);
    abstract void callListPut(OmniCollection.OfFloat seq,int index,int valToConvert);
    abstract void callListAdd(OmniCollection.OfFloat seq,int index,int valToConvert);
    abstract void callStackPush(OmniCollection.OfFloat seq,int valToConvert);
    abstract boolean callCollectionAdd(OmniCollection.OfFloat seq,int valToConvert);
    private int verifyAscending(ConstructionArguments constructionArgs,int offset,int length,int loVal){
      return verifyAscending(constructionArgs.root.arr,offset,length,loVal);
    }
    private int verifyDescending(ConstructionArguments constructionArgs,int offset,int length,int loVal){
      return verifyDescending(constructionArgs.root.arr,offset,length,loVal);
    }
    private int verifyMidPointInsertion(ConstructionArguments constructionArgs,int offset,int length,int loVal){
      return verifyMidPointInsertion(constructionArgs.root.arr,offset,length,loVal);
    }
    private int verifyIndex(ConstructionArguments constructionArgs,int index,int expectedValToConvert)
    {
      verifyIndex(expectedValToConvert,constructionArgs.root.arr[index]);
      return index+1; 
    }
    private int verifyAscending(float[] arr,int offset,int length,int loVal){
      int bound=offset+length;
      for(int i=offset;i<bound;++i,++loVal){
        verifyIndex(loVal,arr[i]);
      }
      return bound;
    }
    private int verifyDescending(float[] arr,int offset,int length,int loVal){
      int bound=offset+length;
      for(int i=bound;--i>=offset;++loVal){
        verifyIndex(loVal,arr[i]);
      }
      return bound;
    }
    private int verifyMidPointInsertion(float[] arr,int offset,int length,int loVal){
      int i;
      for(int v=loVal+1,b=(i=offset)+length/2;i<b;++i,v+=2){
        verifyIndex(v,arr[i]);
      }
      for(int v=loVal+length-2,b=i+length/2;i<b;++i,v-=2){
        verifyIndex(v,arr[i]);
      }
      return offset+length;
    }
    abstract void verifyIndex(int expectedValToConvert,float actualVal);
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
    final OmniCollection.OfFloat seq;
    final OmniCollection.OfFloat parent;
    final FloatArrSeq root;
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
        this.root=checked?new FloatArrSeq.CheckedList():new FloatArrSeq.UncheckedList();
      }else{
        float[] arr=new float[rootSize];
        initAscendingArray(arr,0,-preAlloc,0);
        initAscendingArray(arr,preAlloc,100,100+rootPostAlloc+parentPostAlloc);
        this.root=checked?new FloatArrSeq.CheckedList(rootSize,arr):new FloatArrSeq.UncheckedList(rootSize,arr);
      }
      this.parent=((OmniList.OfFloat)root).subList(rootPreAlloc,preAlloc+parentPostAlloc);
      this.seq=((OmniList.OfFloat)parent).subList(parentPreAlloc,parentPreAlloc);
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
        this.root=checked?new FloatArrSeq.CheckedStack(initialCapacity):new FloatArrSeq.UncheckedStack(initialCapacity);
      }else{
        this.root=checked?new FloatArrSeq.CheckedList(initialCapacity):new FloatArrSeq.UncheckedList(initialCapacity);
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
    private OmniListIterator.OfFloat constructSeqListIterator(){
      return ((OmniList.OfFloat)seq).listIterator();
    }
    private void verifyIteratorState(Object itr,int expectedCursor,int expectedLastRet,int expectedModCount){
      int actualCursor;
      Object actualParent;
      switch(structType){
        case LIST:
          if(checked){
             actualCursor=FieldAccessor.FloatArrSeq.CheckedList.Itr.cursor(itr);
             actualParent=FieldAccessor.FloatArrSeq.CheckedList.Itr.parent(itr);
             Assertions.assertEquals(expectedModCount,FieldAccessor.FloatArrSeq.CheckedList.Itr.modCount(itr));
             Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.FloatArrSeq.CheckedList.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.FloatArrSeq.UncheckedList.Itr.cursor(itr);
            actualParent=FieldAccessor.FloatArrSeq.UncheckedList.Itr.parent(itr);
          }
          break;
        case STACK:
          if(checked){
            actualCursor=FieldAccessor.FloatArrSeq.CheckedStack.Itr.cursor(itr);
            actualParent=FieldAccessor.FloatArrSeq.CheckedStack.Itr.parent(itr);
            Assertions.assertEquals(expectedModCount,FieldAccessor.FloatArrSeq.CheckedStack.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.FloatArrSeq.CheckedStack.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.FloatArrSeq.UncheckedStack.Itr.cursor(itr);
            actualParent=FieldAccessor.FloatArrSeq.UncheckedStack.Itr.parent(itr);
          }
          break;
        default:
          if(checked){
            actualCursor=FieldAccessor.FloatArrSeq.CheckedSubList.Itr.cursor(itr);
            actualParent=FieldAccessor.FloatArrSeq.CheckedSubList.Itr.parent(itr);
            Assertions.assertEquals(expectedModCount,FieldAccessor.FloatArrSeq.CheckedSubList.Itr.modCount(itr));
            Assertions.assertEquals(expectedLastRet<0?expectedLastRet:expectedLastRet+preAlloc,FieldAccessor.FloatArrSeq.CheckedSubList.Itr.lastRet(itr));
          }else{
            actualCursor=FieldAccessor.FloatArrSeq.UncheckedSubList.Itr.cursor(itr);
            actualParent=FieldAccessor.FloatArrSeq.UncheckedSubList.Itr.parent(itr);
          }
      }
      Assertions.assertEquals(expectedCursor+preAlloc,actualCursor);
      Assertions.assertSame(seq,actualParent);
    }
    private void verifyStructuralIntegrity(int expectedSize,int expectedModCount){
       verifyStructuralIntegrity(expectedSize,expectedModCount,expectedSize,expectedModCount,expectedSize,expectedModCount);
    }
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedSeqModCount,int expectedParentAndRootSize,int expectedParentAndRootModCount){
       verifyStructuralIntegrity(expectedSeqSize,expectedSeqModCount,expectedParentAndRootSize,expectedParentAndRootModCount,expectedParentAndRootSize,expectedParentAndRootModCount);
    }
    private void verifyStructuralIntegrity(int expectedSeqSize,int expectedModCount,int expectedParentSize,int expectedParentModCount,int expectedRootSize,int expectedRootModCount)
    {
      switch(structType)
      {
        case STACK:
          if(checked)
          {
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.FloatArrSeq.CheckedStack.modCount(root));
          }
          break;
        case LIST:
          if(checked)
          {
             Assertions.assertEquals(expectedRootModCount,FieldAccessor.FloatArrSeq.CheckedList.modCount(root));
          }
          break;
        default:
          OmniList.OfFloat actualSeqParent;
          Object actualSeqRoot;
          OmniList.OfFloat actualParentParent;
          Object actualParentRoot;
          int actualParentSize;
          int actualSeqSize;
          if(checked){
            actualSeqParent=FieldAccessor.FloatArrSeq.CheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.FloatArrSeq.CheckedSubList.root(seq);
            actualParentParent=FieldAccessor.FloatArrSeq.CheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.FloatArrSeq.CheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.FloatArrSeq.CheckedSubList.size(seq);
            actualParentSize=FieldAccessor.FloatArrSeq.CheckedSubList.size(parent);
            Assertions.assertEquals(expectedModCount,FieldAccessor.FloatArrSeq.CheckedSubList.modCount(seq));
            Assertions.assertEquals(expectedParentModCount,FieldAccessor.FloatArrSeq.CheckedSubList.modCount(parent));
            Assertions.assertEquals(expectedRootModCount,FieldAccessor.FloatArrSeq.CheckedList.modCount(root));
          }else{
            actualSeqParent=FieldAccessor.FloatArrSeq.UncheckedSubList.parent(seq);
            actualSeqRoot=FieldAccessor.FloatArrSeq.UncheckedSubList.root(seq);
            actualParentParent=FieldAccessor.FloatArrSeq.UncheckedSubList.parent(parent);
            actualParentRoot=FieldAccessor.FloatArrSeq.UncheckedSubList.root(parent);
            actualSeqSize=FieldAccessor.FloatArrSeq.UncheckedSubList.size(seq);
            actualParentSize=FieldAccessor.FloatArrSeq.UncheckedSubList.size(parent);
          }
          Assertions.assertSame(root,actualSeqRoot);
          Assertions.assertSame(root,actualParentRoot);
          Assertions.assertSame(parent,actualSeqParent);
          Assertions.assertNull(actualParentParent);
          Assertions.assertEquals(expectedSeqSize,actualSeqSize);
          Assertions.assertEquals(expectedParentSize+parentSize,actualParentSize);
      }
      Assertions.assertEquals(expectedRootSize+rootSize,FieldAccessor.FloatArrSeq.size(root));
    }
  }
  private static void initAscendingArray(float[] arr,int offset,int lo,int hi){
    int bound=offset+(hi-lo);
    for(int i=offset;i<bound;++i,++lo){
      arr[i]=TypeConversionUtil.convertTofloat(lo);
    }
  }
  private static final Arguments[] NON_SUBLIST_TYPES=new Arguments[]
  {
    Arguments.of(false,StructType.LIST),
    Arguments.of(true,StructType.LIST),
    Arguments.of(false,StructType.STACK),
    Arguments.of(true,StructType.STACK)
  };
  static Stream<Arguments> getArgsForNonSubListTypes()
  {
    return Stream.of(NON_SUBLIST_TYPES);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_happyPath(boolean checked,StructType structType)
  {
    FloatArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.FloatArrSeq.CheckedList.modCount(seq=new FloatArrSeq.CheckedList()):FieldAccessor.FloatArrSeq.CheckedStack.modCount(seq=new FloatArrSeq.CheckedStack()));
    }else{
      seq=structType==StructType.LIST?new FloatArrSeq.UncheckedList():new FloatArrSeq.UncheckedStack();
    }
    Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
  }
  @ParameterizedTest
  @MethodSource("getArgsForNonSubListTypes")
  public void testConstructor_int_floatarr_happyPath(boolean checked,StructType structType)
  {
    int size=5;
    float[] arr=new float[10];
    FloatArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.FloatArrSeq.CheckedList.modCount(seq=new FloatArrSeq.CheckedList(size,arr)):FieldAccessor.FloatArrSeq.CheckedStack.modCount(seq=new FloatArrSeq.CheckedStack(size,arr)));
    }else{
      seq=structType==StructType.LIST?new FloatArrSeq.UncheckedList(size,arr):new FloatArrSeq.UncheckedStack(size,arr);
    }
    Assertions.assertEquals(size,seq.size);
    Assertions.assertSame(arr,seq.arr);
  }
  static Stream<Arguments> getArgsFortestConstructor_int_happyPath()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
    {
      builder.add(Arguments.of(initialCapacity,false,StructType.LIST));
      builder.add(Arguments.of(initialCapacity,true,StructType.LIST));
      builder.add(Arguments.of(initialCapacity,false,StructType.STACK));
      builder.add(Arguments.of(initialCapacity,true,StructType.STACK));
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getArgsFortestConstructor_int_happyPath")
  public void testConstructor_int_happyPath(int initialCapacity,boolean checked,StructType structType)
  {
    FloatArrSeq seq;
    if(checked){
      Assertions.assertEquals(0,structType==StructType.LIST?FieldAccessor.FloatArrSeq.CheckedList.modCount(seq=new FloatArrSeq.CheckedList(initialCapacity)):FieldAccessor.FloatArrSeq.CheckedStack.modCount(seq=new FloatArrSeq.CheckedStack(initialCapacity)));
    }else{
      seq=structType==StructType.LIST?new FloatArrSeq.UncheckedList(initialCapacity):new FloatArrSeq.UncheckedStack(initialCapacity);
    }
    Assertions.assertEquals(0,seq.size);
    switch(initialCapacity)
    {
      case 0:
        Assertions.assertNull(seq.arr);
        break;
      case OmniArray.DEFAULT_ARR_SEQ_CAP:
        Assertions.assertSame(OmniArray.OfFloat.DEFAULT_ARR,seq.arr);
        break;
      default:
        Assertions.assertEquals(initialCapacity,seq.arr.length);
    }
  }
  static Stream<Arguments> getListInputMethodArgs()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values())
    {
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
      {
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.LIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5)
      {
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5)
        {
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5)
          {
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5)
            {
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,false)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getCollectionInputMethodArgs()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values())
    {
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
      {
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.LIST)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5)
      {
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5)
        {
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5)
          {
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5)
            {
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,false)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  static Stream<Arguments> getStackInputMethodArgs()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values())
    {
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
      {
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,false,StructType.STACK)));
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.STACK)));
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getListInputMethodArgs")
  public void testListItradd_val_happyPathInsertBegin(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,1,-1,i+1);
      seqItr.previousFloat();
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
  public void testadd_int_val_happyPathInsertBegin(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      inputArgType.callListAdd(constructionArgs.seq,0,i);
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
  public void testListItradd_val_happyPathInsertEnd(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
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
  public void testadd_int_val_happyPathInsertEnd(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size(),i);
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
  public void testListItradd_val_happyPathInsertMidPoint(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      inputArgType.callListItrAdd(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,(i/2)+1,-1,i+1);
      if((i&1)==0)
      {
        seqItr.previousFloat();
      }
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
  public void testadd_int_val_happyPathInsertMidPoint(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      inputArgType.callListAdd(constructionArgs.seq,constructionArgs.seq.size()/2,i);
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
  public void testListItrset_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=100;--i>=0;)
    {
      constructionArgs.seq.add(TypeConversionUtil.convertTofloat(i));
    }
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.nextFloat();
      inputArgType.callListItrSet(seqItr,i);
      constructionArgs.verifyIteratorState(seqItr,i+1,i,100);
    }
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
    for(int i=0;i<100;++i)
    {
      seqItr.previousFloat();
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
  public void testput_int_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      constructionArgs.seq.add(TypeConversionUtil.convertTofloat(i));
    }
    for(int i=0;i<100;++i)
    {
      inputArgType.callListPut(constructionArgs.seq,100-i-1,i);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyDescending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getStackInputMethodArgs")
  public void testpush_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      inputArgType.callStackPush(constructionArgs.seq,i);
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  @ParameterizedTest
  @MethodSource("getCollectionInputMethodArgs")
  public void testadd_val_happyPath(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      Assertions.assertTrue(inputArgType.callCollectionAdd(constructionArgs.seq,i));
    }
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,
      inputArgType.verifyAscending(constructionArgs,
        InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs,0,constructionArgs.preAlloc,-constructionArgs.preAlloc)
      ,100,0)
    ,constructionArgs.postAlloc,100);
  }
  static Stream<Arguments> getCheckedListInputMethodArgs()
  {
    Stream.Builder<Arguments> builder=Stream.builder();
    for(InputTestArgType inputTestArgType:InputTestArgType.values())
    {
      for(int initialCapacity=0;initialCapacity<=15;initialCapacity+=5)
      {
        builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(initialCapacity,true,StructType.LIST)));
      }
      for(int rootPreAlloc=0;rootPreAlloc<=5;rootPreAlloc+=5)
      {
        for(int rootPostAlloc=0;rootPostAlloc<=5;rootPostAlloc+=5)
        {
          for(int parentPreAlloc=0;parentPreAlloc<=5;parentPreAlloc+=5)
          {
            for(int parentPostAlloc=0;parentPostAlloc<=5;parentPostAlloc+=5)
            {
              builder.add(Arguments.of(inputTestArgType,new ConstructionArguments(rootPreAlloc,parentPreAlloc,parentPostAlloc,rootPostAlloc,true)));
            }
          }
        }
      }
    }
    return builder.build();
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testput_int_val_throwIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListPut(constructionArgs.seq,finalIndex,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTofloat(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs.root.arr,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testadd_int_val_throwIOBE(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    for(int i=0;i<100;++i)
    {
      //too low
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,-1,0));
      //too high
      final int finalIndex=i;
      Assertions.assertThrows(IndexOutOfBoundsException.class,()->inputArgType.callListAdd(constructionArgs.seq,finalIndex+1,0));
      constructionArgs.seq.add(TypeConversionUtil.convertTofloat(i));
    }
    //when method throws, verify no changes occurred
    constructionArgs.verifyStructuralIntegrity(100,100);
    InputTestArgType.ARRAY_TYPE.verifyAscending(constructionArgs.root.arr,0,constructionArgs.rootSize+100,-constructionArgs.preAlloc);
  }
  @ParameterizedTest
  @MethodSource("getCheckedListInputMethodArgs")
  public void testListItrAadd_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItradd_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItradd_val_emptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItradd_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItradd_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItradd_val_nonEmptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrAset_val_emptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrset_val_emptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrset_val_emptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrset_val_nonEmptyListModRootthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the root;
    constructionArgs.root.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrset_val_nonEmptyListModParentthrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the parent;
    constructionArgs.parent.add(TypeConversionUtil.convertTofloat(0));
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
  public void testListItrset_val_nonEmptyListModSequencethrowCME(InputTestArgType inputArgType,ConstructionArguments constructionArgs)
  {
    var seqItr=constructionArgs.constructSeqListIterator();
    for(int i=0;i<100;++i)
    {
      seqItr.add(TypeConversionUtil.convertTofloat(i));
    }
    seqItr.add(TypeConversionUtil.convertTofloat(0));
    seqItr.previousFloat();
    //illegally modify the sequence;
    constructionArgs.seq.add(TypeConversionUtil.convertTofloat(0));
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
  //TODO checked sublist CME add/put methods
  //TODO checked list iterator ISE add methods
}
