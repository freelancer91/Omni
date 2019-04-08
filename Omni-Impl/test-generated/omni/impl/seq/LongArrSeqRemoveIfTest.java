package omni.impl.seq;
import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import java.util.ConcurrentModificationException;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import java.util.Random;
import org.junit.jupiter.api.Test;
import omni.impl.LongMonitoredPredicate;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.function.LongPredicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
@SuppressWarnings({"rawtypes","unchecked"}) 
public class LongArrSeqRemoveIfTest
{
  private static final Field CheckedSubListmodCount;
  private static final Field CheckedSubListparent;
  private static final Field CheckedSubListroot;
  private static final Field CheckedSubListsize;
  private static final Field UncheckedSubListparent;
  private static final Field UncheckedSubListroot;
  private static final Field UncheckedSubListsize;
  static{
    try{
      //You must add the following switch to the VM arguments
      //--add-opens java.base/java.lang.reflect=omni.impl
      Field modifiersField=Field.class.getDeclaredField("modifiers");
      modifiersField.setAccessible(true);
      {
        Class<?> clazz=new LongArrSeq.CheckedList().subList(0,0).getClass();
        (CheckedSubListmodCount=clazz.getDeclaredField("modCount")).setAccessible(true);
        (CheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(CheckedSubListparent,CheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (CheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(CheckedSubListroot,CheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (CheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
      {
        Class<?> clazz=new LongArrSeq.UncheckedList().subList(0,0).getClass();
        (UncheckedSubListparent=clazz.getDeclaredField("parent")).setAccessible(true);
        modifiersField.setInt(UncheckedSubListparent,UncheckedSubListparent.getModifiers()&~Modifier.FINAL);
        (UncheckedSubListroot=clazz.getDeclaredField("root")).setAccessible(true);
        modifiersField.setInt(UncheckedSubListroot,UncheckedSubListroot.getModifiers()&~Modifier.FINAL);
        (UncheckedSubListsize=clazz.getDeclaredField("size")).setAccessible(true);
      }
    }catch(Exception e){
      throw new ExceptionInInitializerError(e);
    }
  }
  private static LongArrSeq.CheckedList getRoot(Object seq){
    try{
      return (LongArrSeq.CheckedList)CheckedSubListroot.get(seq);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  private static OmniList.OfLong getParent(Object seq){
    try{
      return (OmniList.OfLong)CheckedSubListparent.get(seq);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  private static LongArrSeq.UncheckedList assertUncheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj){
    if(!(obj instanceof LongArrSeq.UncheckedList)){
      try{
        Object parent=UncheckedSubListparent.get(obj);
        if(parent!=null){
           Assertions.assertEquals(expectedParentSize,UncheckedSubListsize.getInt(parent));
           obj=parent;
        }
        obj=UncheckedSubListroot.get(obj);
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    }
    return (LongArrSeq.UncheckedList)obj;
  }
  private static LongArrSeq.CheckedList assertCheckedSubListIntegrity(int expectedModCount,int expectedParentSize,Object obj){
    if(!(obj instanceof LongArrSeq.CheckedList)){
      try{
        Assertions.assertEquals(expectedModCount,CheckedSubListmodCount.getInt(obj));
        Object parent=CheckedSubListparent.get(obj);
        if(parent!=null){
           Assertions.assertEquals(expectedModCount,CheckedSubListmodCount.getInt(parent));
           Assertions.assertEquals(expectedParentSize,CheckedSubListsize.getInt(parent));
           obj=parent;
        }
        obj=CheckedSubListroot.get(obj);
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    }
    LongArrSeq.CheckedList root=(LongArrSeq.CheckedList)obj;
    Assertions.assertEquals(expectedModCount,root.modCount);
    return root;
  }
  static LongArrSeq.UncheckedStack buildArrSeqStackUnchecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.UncheckedStack(seqSize,arr);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static LongArrSeq.CheckedStack buildArrSeqStackChecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.CheckedStack(seqSize,arr);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static LongArrSeq.UncheckedList buildArrSeqListUnchecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.UncheckedList(seqSize,arr);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static LongArrSeq.CheckedList buildArrSeqListChecked(int seqSize)
  {
    long[] arr;
    if(seqSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[seqSize];
      for(int i=0;i<seqSize;++i)
      {
        arr[i]=TypeConversionUtil.convertTolong(i);
      }
    }
    return new LongArrSeq.CheckedList(seqSize,arr);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static OmniList.OfLong buildArrSeqSubListUnchecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    long[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i);
      }
    }
    return new LongArrSeq.UncheckedList(rootSize,arr).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static OmniList.OfLong buildArrSeqSubListChecked(int seqSize,int rootPreAlloc,int parentPreAlloc,int parentPostAlloc,int rootPostAlloc)
  {
    long[] arr;
    int rootSize=rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc+rootPostAlloc;
    if(rootSize==0)
    {
      arr=OmniArray.OfLong.DEFAULT_ARR;
    }
    else
    {
      arr=new long[rootSize];
      int dstOffset=0;
      for(int i=0;i<rootPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-rootPreAlloc-parentPreAlloc);
      }
      for(int i=0;i<parentPreAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i-parentPreAlloc);
      }
      for(int i=0;i<seqSize;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(i);
      }
      for(int i=0;i<parentPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+i);
      }
      for(int i=0;i<rootPostAlloc;++i,++dstOffset)
      {
        arr[dstOffset]=TypeConversionUtil.convertTolong(seqSize+parentPostAlloc+i);
      }
    }
    return new LongArrSeq.CheckedList(rootSize,arr).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+seqSize+parentPostAlloc).subList(parentPreAlloc,parentPreAlloc+seqSize);
  }
  //#MACRO TestImplHelper(LongPredicate)
  //#MACRO TestImplHelper(Predicate)
  static Stream<Arguments> subListAllocationArgumentProvider(){
    Arguments[] args=new Arguments[16];
    args[0]=Arguments.of(0,0,0,0);
    args[1]=Arguments.of(0,0,0,5);
    args[2]=Arguments.of(0,0,5,0);
    args[3]=Arguments.of(0,0,5,5);
    args[4]=Arguments.of(0,5,0,0);
    args[5]=Arguments.of(0,5,0,5);
    args[6]=Arguments.of(0,5,5,0);
    args[7]=Arguments.of(0,5,5,5);
    args[8]=Arguments.of(5,0,0,0);
    args[9]=Arguments.of(5,0,0,5);
    args[10]=Arguments.of(5,0,5,0);
    args[11]=Arguments.of(5,0,5,5);
    args[12]=Arguments.of(5,5,0,0);
    args[13]=Arguments.of(5,5,0,5);
    args[14]=Arguments.of(5,5,5,0);
    args[15]=Arguments.of(5,5,5,5);
    return Stream.of(args);
  }
}
