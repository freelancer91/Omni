package omni.impl.set;
import static omni.impl.set.FieldAndMethodAccessor.RefOpenAddressHashSet.DELETED;
import static omni.impl.set.FieldAndMethodAccessor.RefOpenAddressHashSet.NULL;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen;
import omni.impl.MonitoredSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.StructType;
import omni.util.OmniArray;
import omni.util.TestExecutorService;
public class OpenAddressHashSetTest{
  private interface BasicTest{
    default void runAllTests(String testName){
      for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
        for(final var checkedType:CheckedType.values()){
          for(final var initSeq:VALID_INIT_SEQS){
            for(final var loadFactor:LOAD_FACTORS){
              if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
                for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                  TestExecutorService
                      .submitTest(()->runTest(loadFactor,initCapacity,collectionType,checkedType,initSeq));
                }
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(float loadFactor,int initCapacity,DataType collectionType,CheckedType checkedType,
        SetInitializationSequence initSeq);
  }
  private interface MonitoredFunctionGenTest{
    default void runAllTests(String testName){
      for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
        for(final var functionGen:StructType.OpenAddressHashSet.validMonitoredFunctionGens){
          for(final var checkedType:CheckedType.values()){
            if(checkedType.checked || functionGen.expectedException == null){
              for(final var initSeq:VALID_INIT_SEQS){
                TestExecutorService.submitTest(()->runTest(collectionType,functionGen,checkedType,initSeq));
              }
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    void runTest(DataType collectionType,MonitoredFunctionGen functionGen,CheckedType checkedType,
        SetInitializationSequence initSeq);
  }
  private static class OpenAddressHashSetMonitor implements MonitoredSet<AbstractOpenAddressHashSet<?>>{
    private class CheckedItrMonitor extends AbstractItrMonitor{
      int expectedItrModCount;
      int expectedItrLastRet;
      CheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft,int expectedItrModCount){
        super(itr,expectedOffset,expectedNumLeft);
        this.expectedItrModCount=expectedItrModCount;
        expectedItrLastRet=-1;
      }
      @Override public void updateIteratorState(){
        switch(dataType){
        case FLOAT:
          expectedOffset=FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.offset(itr);
          expectedItrModCount=FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.modCount(itr);
          expectedItrLastRet=FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.lastRet(itr);
          break;
        case DOUBLE:
          expectedOffset=FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.offset(itr);
          expectedItrModCount=FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.modCount(itr);
          expectedItrLastRet=FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.lastRet(itr);
          break;
        case REF:
          expectedOffset=FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.offset(itr);
          expectedItrModCount=FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.modCount(itr);
          expectedItrLastRet=FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.lastRet(itr);
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
      @Override public void updateItrNextState(){
        expectedItrLastRet=expectedOffset;
        super.updateItrNextState();
      }
      @Override public void updateItrRemoveState(){
        ++expectedModCount;
        ++expectedItrModCount;
        --expectedSize;
        switch(dataType){
        case FLOAT:
          ((int[])expectedTable)[expectedItrLastRet]=0x7fe00000;
          break;
        case DOUBLE:
          ((long[])expectedTable)[expectedItrLastRet]=0x7ffc000000000000L;
          break;
        case REF:
          ((Object[])expectedTable)[expectedItrLastRet]=DELETED;
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
        expectedItrLastRet=-1;
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        expectedItrLastRet=super.verifyForEachRemainingHelper(function,expectedItrLastRet);
      }
      @Override public void verifyIteratorState(Object itr){
        switch(dataType){
        case FLOAT:
          Assertions.assertSame(set,FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,
              FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.offset(itr));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.modCount(itr));
          Assertions.assertEquals(expectedItrLastRet,
              FieldAndMethodAccessor.FloatOpenAddressHashSet.Checked.Itr.lastRet(itr));
          break;
        case DOUBLE:
          Assertions.assertSame(set,FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,
              FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.offset(itr));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.modCount(itr));
          Assertions.assertEquals(expectedItrLastRet,
              FieldAndMethodAccessor.DoubleOpenAddressHashSet.Checked.Itr.lastRet(itr));
          break;
        case REF:
          Assertions.assertSame(set,FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.offset(itr));
          Assertions.assertEquals(expectedItrModCount,
              FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.modCount(itr));
          Assertions.assertEquals(expectedItrLastRet,
              FieldAndMethodAccessor.RefOpenAddressHashSet.Checked.Itr.lastRet(itr));
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
    }
    private class UncheckedItrMonitor extends AbstractItrMonitor{
      UncheckedItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
        super(itr,expectedOffset,expectedNumLeft);
      }
      @Override public void updateIteratorState(){
        switch(dataType){
        case FLOAT:
          expectedOffset=FieldAndMethodAccessor.FloatOpenAddressHashSet.Itr.offset(itr);
          break;
        case DOUBLE:
          expectedOffset=FieldAndMethodAccessor.DoubleOpenAddressHashSet.Itr.offset(itr);
          break;
        case REF:
          expectedOffset=FieldAndMethodAccessor.RefOpenAddressHashSet.Itr.offset(itr);
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
      @Override public void updateItrRemoveState(){
        --expectedSize;
        switch(dataType){
        case FLOAT:{
          final int[] table=(int[])expectedTable;
          for(int offset=expectedOffset;;){
            switch(table[++offset]){
            default:
              table[offset]=0x7fe00000;
              return;
            case 0:
            case 0x7fe00000:
            }
          }
        }
        case DOUBLE:{
          final long[] table=(long[])expectedTable;
          for(int offset=expectedOffset;;){
            long tableVal;
            if((tableVal=table[++offset]) != 0 && tableVal != 0x7ffc000000000000L){
              table[offset]=0x7ffc000000000000L;
              return;
            }
          }
        }
        case REF:{
          final Object[] table=(Object[])expectedTable;
          for(int offset=expectedOffset;;){
            Object tableVal;
            if((tableVal=table[++offset]) != null && tableVal != DELETED){
              table[offset]=DELETED;
              return;
            }
          }
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
      @Override public void verifyForEachRemaining(MonitoredFunction function){
        super.verifyForEachRemainingHelper(function,-1);
      }
      @Override public void verifyIteratorState(Object itr){
        switch(dataType){
        case FLOAT:
          Assertions.assertSame(set,FieldAndMethodAccessor.FloatOpenAddressHashSet.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.FloatOpenAddressHashSet.Itr.offset(itr));
          break;
        case DOUBLE:
          Assertions.assertSame(set,FieldAndMethodAccessor.DoubleOpenAddressHashSet.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.DoubleOpenAddressHashSet.Itr.offset(itr));
          break;
        case REF:
          Assertions.assertSame(set,FieldAndMethodAccessor.RefOpenAddressHashSet.Itr.root(itr));
          Assertions.assertEquals(expectedOffset,FieldAndMethodAccessor.RefOpenAddressHashSet.Itr.offset(itr));
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
    }
    abstract class AbstractItrMonitor
        implements MonitoredSet.MonitoredSetIterator<OmniIterator<?>,AbstractOpenAddressHashSet<?>>{
      final OmniIterator<?> itr;
      int expectedOffset;
      int expectedNumLeft;
      AbstractItrMonitor(OmniIterator<?> itr,int expectedOffset,int expectedNumLeft){
        this.itr=itr;
        this.expectedOffset=expectedOffset;
        this.expectedNumLeft=expectedNumLeft;
      }
      @Override public OmniIterator<?> getIterator(){
        return itr;
      }
      @Override public MonitoredCollection<AbstractOpenAddressHashSet<?>> getMonitoredCollection(){
        return OpenAddressHashSetMonitor.this;
      }
      @Override public int getNumLeft(){
        return expectedNumLeft;
      }
      @Override public boolean hasNext(){
        return expectedOffset != -1;
      }
      @Override public void iterateForward(){
        MonitoredSet.MonitoredSetIterator.super.iterateForward();
        --expectedNumLeft;
      }
      @Override public void modItr(){
        MonitoredSet.MonitoredSetIterator.super.modItr();
        --expectedNumLeft;
      }
      @Override public void updateItrNextState(){
        int expectedOffset=this.expectedOffset;
        switch(dataType){
        case FLOAT:{
          final int[] table=(int[])expectedTable;
          setOffset:for(;;){
            if(--expectedOffset == -1){
              break;
            }
            switch(table[expectedOffset]){
            default:
              break setOffset;
            case 0x7fe00000:
            case 0:
            }
          }
          break;
        }
        case DOUBLE:{
          final long[] table=(long[])expectedTable;
          for(;;){
            if(--expectedOffset == -1){
              break;
            }
            long tableVal;
            if((tableVal=table[expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
              break;
            }
          }
          break;
        }
        case REF:{
          final Object[] table=(Object[])expectedTable;
          for(;;){
            if(--expectedOffset == -1){
              break;
            }
            Object tableVal;
            if((tableVal=table[expectedOffset]) != null && tableVal != DELETED){
              break;
            }
          }
          break;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
        this.expectedOffset=expectedOffset;
        --expectedNumLeft;
      }
      @Override public void verifyNextResult(DataType outputType,Object result){
        Object expectedResult;
        switch(dataType){
        case FLOAT:{
          int bits;
          if((bits=((int[])expectedTable)[expectedOffset]) == 0xffe00000){
            bits=0;
          }
          expectedResult=outputType.convertVal(Float.intBitsToFloat(bits));
          break;
        }
        case DOUBLE:{
          long bits;
          if((bits=((long[])expectedTable)[expectedOffset]) == 0xfffc000000000000L){
            bits=0;
          }
          expectedResult=Double.longBitsToDouble(bits);
          break;
        }
        case REF:{
          if((expectedResult=((Object[])expectedTable)[expectedOffset]) == NULL){
            expectedResult=null;
          }
          break;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
        Assertions.assertEquals(expectedResult,result);
      }
      private int verifyForEachRemainingHelper(MonitoredFunction function,int expectedLastRet){
        final var monitoredFunctionItr=function.iterator();
        int expectedOffset;
        if((expectedOffset=this.expectedOffset) != -1){
          switch(dataType){
          case FLOAT:{
            final int[] table=(int[])expectedTable;
            for(;;--expectedOffset){
              int tableVal;
              switch(tableVal=table[expectedOffset]){
              case 0:
              case 0x7fe00000:
                continue;
              case 0xffe00000:
                tableVal=0;
              default:
                final var v=(Float)monitoredFunctionItr.next();
                Assertions.assertEquals(v.floatValue(),Float.intBitsToFloat(tableVal));
                expectedLastRet=expectedOffset;
                if(--expectedNumLeft != 0){
                  continue;
                }
              }
              break;
            }
            break;
          }
          case DOUBLE:{
            final long[] table=(long[])expectedTable;
            for(;;--expectedOffset){
              long tableVal;
              if((tableVal=table[expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
                if(tableVal == 0xfffc000000000000L){
                  tableVal=0;
                }
                final var v=(Double)monitoredFunctionItr.next();
                Assertions.assertEquals(v.doubleValue(),Double.longBitsToDouble(tableVal));
                expectedLastRet=expectedOffset;
                if(--expectedNumLeft == 0){
                  break;
                }
              }
            }
            break;
          }
          case REF:{
            final Object[] table=(Object[])expectedTable;
            for(;;--expectedOffset){
              Object tableVal;
              if((tableVal=table[expectedOffset]) != null && tableVal != DELETED){
                if(tableVal == NULL){
                  tableVal=null;
                }
                final var v=monitoredFunctionItr.next();
                Assertions.assertEquals(v,tableVal);
                expectedLastRet=expectedOffset;
                if(--expectedNumLeft == 0){
                  break;
                }
              }
            }
            break;
          }
          default:
            throw DataType.invalidDataType(dataType);
          }
        }
        Assertions.assertFalse(monitoredFunctionItr.hasNext());
        this.expectedOffset=-1;
        return expectedLastRet;
      }
    }
    private static int getTableHash(int bits){
      return bits ^ bits >>> 16;
    }
    private static int getTableHash(long bits){
      return getTableHash((int)(bits ^ bits >>> 32));
    }
    private static int getTableHash(Object v){
      return getTableHash(v.hashCode());
    }
    private static long getTableVal(double v){
      long bits;
      if(v == v){
        bits=Double.doubleToRawLongBits(v);
        if(bits == 0){
          bits=0xfffc000000000000L;
        }
      }else{
        bits=0x7ff8000000000000L;
      }
      return bits;
    }
    private static int getTableVal(float v){
      int bits;
      if(v == v){
        bits=Float.floatToRawIntBits(v);
        if(bits == 0){
          bits=0xffe00000;
        }
      }else{
        bits=0x7fc00000;
      }
      return bits;
    }
    private static Object getTableVal(Object v){
      if(v == null){
        v=NULL;
      }
      return v;
    }
    private static void quickInsert(int[] table,int val){
      int tableLength;
      int hash;
      for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
        if(table[hash] == 0){
          table[hash]=val;
          return;
        }
        hash=hash + 1 & tableLength;
      }
    }
    private static void quickInsert(long[] table,long val){
      int tableLength;
      int hash;
      for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
        if(table[hash] == 0){
          table[hash]=val;
          return;
        }
        hash=hash + 1 & tableLength;
      }
    }
    private static void quickInsert(Object[] table,Object val){
      int tableLength;
      int hash;
      for(hash=getTableHash(val) & (tableLength=table.length - 1);;){
        if(table[hash] == null){
          table[hash]=val;
          return;
        }
        hash=hash + 1 & tableLength;
      }
    }
    final AbstractOpenAddressHashSet<?> set;
    final CheckedType checkedType;
    final DataType dataType;
    int expectedSize;
    int expectedMaxTableSize;
    float expectedLoadFactor;
    int expectedModCount;
    int expectedTableLength;
    Object expectedTable;
    OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType){
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          set=new FloatOpenAddressHashSet.Checked();
        }else{
          set=new FloatOpenAddressHashSet();
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          set=new DoubleOpenAddressHashSet.Checked();
        }else{
          set=new DoubleOpenAddressHashSet();
        }
        break;
      case REF:
        if(checkedType.checked){
          set=new RefOpenAddressHashSet.Checked<>();
        }else{
          set=new RefOpenAddressHashSet<>();
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      this.checkedType=checkedType;
      this.dataType=dataType;
      updateCollectionState();
    }
    OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,float loadFactor){
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          set=new FloatOpenAddressHashSet.Checked(loadFactor);
        }else{
          set=new FloatOpenAddressHashSet(loadFactor);
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          set=new DoubleOpenAddressHashSet.Checked(loadFactor);
        }else{
          set=new DoubleOpenAddressHashSet(loadFactor);
        }
        break;
      case REF:
        if(checkedType.checked){
          set=new RefOpenAddressHashSet.Checked<>(loadFactor);
        }else{
          set=new RefOpenAddressHashSet<>(loadFactor);
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      this.checkedType=checkedType;
      this.dataType=dataType;
      updateCollectionState();
    }
    OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity){
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          set=new FloatOpenAddressHashSet.Checked(initialCapacity);
        }else{
          set=new FloatOpenAddressHashSet(initialCapacity);
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          set=new DoubleOpenAddressHashSet.Checked(initialCapacity);
        }else{
          set=new DoubleOpenAddressHashSet(initialCapacity);
        }
        break;
      case REF:
        if(checkedType.checked){
          set=new RefOpenAddressHashSet.Checked<>(initialCapacity);
        }else{
          set=new RefOpenAddressHashSet<>(initialCapacity);
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      this.checkedType=checkedType;
      this.dataType=dataType;
      updateCollectionState();
    }
    OpenAddressHashSetMonitor(DataType dataType,CheckedType checkedType,int initialCapacity,float loadFactor){
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          set=new FloatOpenAddressHashSet.Checked(initialCapacity,loadFactor);
        }else{
          set=new FloatOpenAddressHashSet(initialCapacity,loadFactor);
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          set=new DoubleOpenAddressHashSet.Checked(initialCapacity,loadFactor);
        }else{
          set=new DoubleOpenAddressHashSet(initialCapacity,loadFactor);
        }
        break;
      case REF:
        if(checkedType.checked){
          set=new RefOpenAddressHashSet.Checked<>(initialCapacity,loadFactor);
        }else{
          set=new RefOpenAddressHashSet<>(initialCapacity,loadFactor);
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      this.checkedType=checkedType;
      this.dataType=dataType;
      updateCollectionState();
    }
    @Override public CheckedType getCheckedType(){
      return checkedType;
    }
    @Override public AbstractOpenAddressHashSet<?> getCollection(){
      return set;
    }
    @Override public DataType getDataType(){
      return dataType;
    }
    @Override public MonitoredIterator<? extends OmniIterator<?>,AbstractOpenAddressHashSet<?>> getMonitoredIterator(){
      int expectedOffset;
      int numLeft;
      if((numLeft=expectedSize) != 0){
        outer:for(;;){
          switch(dataType){
          case FLOAT:{
            final int[] table=(int[])expectedTable;
            for(expectedOffset=table.length;;){
              switch(table[--expectedOffset]){
              default:
                break outer;
              case 0:
              case 0x7fe00000:
              }
            }
          }
          case DOUBLE:{
            final long[] table=(long[])expectedTable;
            for(expectedOffset=table.length;;){
              long tableVal;
              if((tableVal=table[--expectedOffset]) != 0 && tableVal != 0x7ffc000000000000L){
                break outer;
              }
            }
          }
          case REF:{
            final Object[] table=(Object[])expectedTable;
            for(expectedOffset=table.length;;){
              Object tableVal;
              if((tableVal=table[--expectedOffset]) != null && tableVal != DELETED){
                break outer;
              }
            }
          }
          default:
            throw DataType.invalidDataType(dataType);
          }
        }
      }else{
        expectedOffset=-1;
      }
      final var itr=set.iterator();
      if(checkedType.checked){ return new CheckedItrMonitor(itr,expectedOffset,numLeft,expectedModCount); }
      return new UncheckedItrMonitor(itr,expectedOffset,numLeft);
    }
    @Override public StructType getStructType(){
      return StructType.OpenAddressHashSet;
    }
    @Override public boolean isEmpty(){
      return expectedSize == 0;
    }
    @SuppressWarnings("unchecked") @Override public void modCollection(){
      outer:for(;;){
        switch(dataType){
        case FLOAT:{
          final var cast=(FloatOpenAddressHashSet)set;
          if(cast.add(Float.NaN)){
            break outer;
          }
          if(cast.add(-0.0f)){
            break outer;
          }
          if(cast.add(Float.NEGATIVE_INFINITY)){
            break outer;
          }
          if(cast.add(Float.POSITIVE_INFINITY)){
            break outer;
          }
          float f=-Float.MAX_VALUE;
          for(;;){
            if(cast.add(f)){
              break outer;
            }
            f=Math.nextUp(f);
            if(f == Float.POSITIVE_INFINITY){
              break;
            }
          }
          break;
        }
        case DOUBLE:{
          final var cast=(DoubleOpenAddressHashSet)set;
          if(cast.add(Double.NaN)){
            break outer;
          }
          if(cast.add(-0.0d)){
            break outer;
          }
          if(cast.add(Double.NEGATIVE_INFINITY)){
            break outer;
          }
          if(cast.add(Double.POSITIVE_INFINITY)){
            break outer;
          }
          double d=-Double.MAX_VALUE;
          for(;;){
            if(cast.add(d)){
              break outer;
            }
            d=Math.nextUp(d);
            if(d == Double.POSITIVE_INFINITY){
              break;
            }
          }
          break;
        }
        case REF:{
          ((RefOpenAddressHashSet<Object>)set).add(new Object());
          break outer;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
        set.clear();
        break;
      }
      updateCollectionState();
    }
    @Override public boolean verifyRemoveVal(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
        QueryValModification modification){
    Object inputVal=queryVal.getInputVal(inputType,modification);
    int sizeBefore=set.size();
    boolean containsBefore=queryCastType.callcontains(set,inputVal,inputType);
    boolean result=queryCastType.callremoveVal(set,inputVal,inputType);
    boolean containsAfter=queryCastType.callcontains(set,inputVal,inputType);
    int sizeAfter=set.size();
    if(result) {
        Assertions.assertNotEquals(containsBefore,containsAfter);
        Assertions.assertEquals(sizeBefore,sizeAfter+1);
        switch(dataType) {
        case FLOAT:
        case DOUBLE:
          removeFromExpectedState(queryVal,modification);
          break;
        case REF:
          removeFromExpectedState(inputType,queryVal,modification);
          break;
        default:
          throw DataType.invalidDataType(dataType);
        }
    }else {
        Assertions.assertEquals(containsBefore,containsAfter);
        Assertions.assertEquals(sizeBefore,sizeAfter);
    }
    return result;
}
    private void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
      final Object inputVal=queryVal.getInputVal(inputType,modification);
      switch(dataType){
      case FLOAT:{
        removeVal((float)inputVal);
        break;
      }
      case DOUBLE:{
        removeVal((double)inputVal);
        break;
      }
      case REF:{
        removeVal(inputVal);
        break;
      }
      default:
        throw DataType.invalidDataType(dataType);
      }
      ++expectedModCount;
    }
    @Override public void removeFromExpectedState(QueryVal queryVal,QueryValModification modification){
      removeFromExpectedState(dataType,queryVal,modification);
    }
    @Override public int size(){
      return expectedSize;
    }
    @Override public void updateAddState(Object inputVal,DataType inputType,boolean result){
      if(result){
        switch(dataType){
        case FLOAT:{
          float v;
          switch(inputType){
          case BOOLEAN:
            v=(boolean)inputVal?1.0f:0.0f;
            break;
          case BYTE:
            v=(byte)inputVal;
            break;
          case CHAR:
            v=(char)inputVal;
            break;
          case SHORT:
            v=(short)inputVal;
            break;
          case INT:
            v=(int)inputVal;
            break;
          case LONG:
            v=(long)inputVal;
            break;
          case FLOAT:
            v=(float)inputVal;
            break;
          default:
            throw DataType.invalidDataType(inputType);
          }
          insertInTable(v);
          break;
        }
        case DOUBLE:{
          double v;
          switch(inputType){
          case BOOLEAN:
            v=(boolean)inputVal?1.0d:0.0d;
            break;
          case BYTE:
            v=(byte)inputVal;
            break;
          case CHAR:
            v=(char)inputVal;
            break;
          case SHORT:
            v=(short)inputVal;
            break;
          case INT:
            v=(int)inputVal;
            break;
          case LONG:
            v=(long)inputVal;
            break;
          case FLOAT:
            v=(float)inputVal;
            break;
          case DOUBLE:
            v=(double)inputVal;
            break;
          default:
            throw DataType.invalidDataType(inputType);
          }
          insertInTable(v);
          break;
        }
        case REF:{
          insertInTable(inputVal);
          break;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
        ++expectedModCount;
      }
    }
    @Override public void updateCollectionState(){
      AbstractOpenAddressHashSet<?> set;
      expectedSize=(set=this.set).size;
      expectedLoadFactor=set.loadFactor;
      expectedMaxTableSize=set.maxTableSize;
      switch(dataType){
      case FLOAT:{
        final var castSet=(FloatOpenAddressHashSet)set;
        if(checkedType.checked){
          expectedModCount=((FloatOpenAddressHashSet.Checked)castSet).modCount;
        }
        int[] table;
        if((table=castSet.table) == null){
          expectedTable=null;
          expectedTableLength=0;
        }else{
          int expectedTableLength,tableLength;
          if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
            Object newExpectedTable;
            System.arraycopy(table,0,newExpectedTable=new int[tableLength],0,tableLength);
            expectedTable=newExpectedTable;
            this.expectedTableLength=tableLength;
          }else{
            System.arraycopy(table,0,expectedTable,0,expectedTableLength);
          }
        }
        break;
      }
      case DOUBLE:{
        final var castSet=(DoubleOpenAddressHashSet)set;
        if(checkedType.checked){
          expectedModCount=((DoubleOpenAddressHashSet.Checked)castSet).modCount;
        }
        long[] table;
        if((table=castSet.table) == null){
          expectedTable=null;
          expectedTableLength=0;
        }else{
          int expectedTableLength,tableLength;
          if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
            Object newExpectedTable;
            System.arraycopy(table,0,newExpectedTable=new long[tableLength],0,tableLength);
            expectedTable=newExpectedTable;
            this.expectedTableLength=tableLength;
          }else{
            System.arraycopy(table,0,expectedTable,0,expectedTableLength);
          }
        }
        break;
      }
      case REF:{
        final var castSet=(RefOpenAddressHashSet<?>)set;
        if(checkedType.checked){
          expectedModCount=((RefOpenAddressHashSet.Checked<?>)castSet).modCount;
        }
        Object[] table;
        if((table=castSet.table) == null){
          expectedTable=null;
          expectedTableLength=0;
        }else{
          int expectedTableLength,tableLength;
          if((expectedTableLength=this.expectedTableLength) != (tableLength=table.length)){
            Object newExpectedTable;
            System.arraycopy(table,0,newExpectedTable=new Object[tableLength],0,tableLength);
            expectedTable=newExpectedTable;
            this.expectedTableLength=tableLength;
          }else{
            System.arraycopy(table,0,expectedTable,0,expectedTableLength);
          }
        }
        break;
      }
      default:
        throw DataType.invalidDataType(dataType);
      }
    }
    @Override public boolean verifyAdd(Object inputVal,DataType inputType,FunctionCallType functionCallType){
      final boolean containsBefore=inputType.callcontains(set,inputVal,functionCallType);
      final int sizeBefore=expectedSize;
      final boolean result=inputType.callCollectionAdd(inputVal,set,functionCallType);
      final int sizeAfter=set.size();
      final boolean containsAfter=inputType.callcontains(set,inputVal,functionCallType);
      Assertions.assertEquals(!result,containsBefore);
      Assertions.assertTrue(containsAfter);
      Assertions.assertEquals(sizeBefore,result?sizeAfter - 1:sizeAfter);
      updateAddState(inputVal,inputType,result);
      Assertions.assertTrue(inputType.callremoveVal(set,inputVal,functionCallType));
      updateRemoveState(inputType,inputVal,functionCallType);
      Assertions.assertTrue(inputType.callCollectionAdd(inputVal,set,functionCallType));
      Assertions.assertFalse(inputType.callCollectionAdd(inputVal,set,functionCallType));
      updateAddState(inputVal,inputType,true);
      verifyCollectionState();
      return result;
    }
    @Override public void verifyArrayIsCopy(Object arr){
      switch(dataType){
      case FLOAT:
        Assertions.assertNotSame(((FloatOpenAddressHashSet)set).table,arr);
        break;
      case DOUBLE:
        Assertions.assertNotSame(((DoubleOpenAddressHashSet)set).table,arr);
        break;
      case REF:
        Assertions.assertNotSame(((RefOpenAddressHashSet<?>)set).table,arr);
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
    }
    @Override public void verifyClear(){
      set.clear();
      if(expectedSize != 0){
        switch(dataType){
        case FLOAT:{
          final var table=(int[])expectedTable;
          for(int i=table.length;--i >= 0;){
            table[i]=0;
          }
          break;
        }
        case DOUBLE:{
          final var table=(long[])expectedTable;
          for(int i=table.length;--i >= 0;){
            table[i]=0;
          }
          break;
        }
        case REF:{
          final var table=(Object[])expectedTable;
          for(int i=table.length;--i >= 0;){
            table[i]=null;
          }
          break;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
        expectedSize=0;
        ++expectedModCount;
      }
      verifyCollectionState();
    }
    @Override public void verifyClone(Object clone){
      AbstractOpenAddressHashSet<?> cloneSet;
      Assertions.assertEquals(expectedSize,(cloneSet=(AbstractOpenAddressHashSet<?>)clone).size);
      Assertions.assertEquals(expectedLoadFactor,cloneSet.loadFactor);
      int expectedTableLength=this.expectedTableLength;
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          Assertions.assertEquals(0,((FloatOpenAddressHashSet.Checked)cloneSet).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((FloatOpenAddressHashSet)cloneSet).table);
        }else{
          final var expectedTable=(int[])this.expectedTable;
          while(--expectedTableLength >= 0){
            int v;
            switch(v=expectedTable[expectedTableLength]){
            case 0xffe00000: // pos0
              v=0;
            default:
              Assertions.assertTrue(cloneSet.contains(Float.intBitsToFloat(v)));
            case 0: // empty
            case 0x7fe00000: // deleted
            }
          }
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          Assertions.assertEquals(0,((DoubleOpenAddressHashSet.Checked)cloneSet).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((DoubleOpenAddressHashSet)cloneSet).table);
        }else{
          final var expectedTable=(long[])this.expectedTable;
          while(--expectedTableLength >= 0){
            long v;
            if((v=expectedTable[expectedTableLength]) != 0L && v != 0x7ffc000000000000L){
              if(v == 0xfffc000000000000L){
                v=0L;
              }
              Assertions.assertTrue(cloneSet.contains(Double.longBitsToDouble(v)));
            }
          }
        }
        break;
      case REF:
        if(checkedType.checked){
          Assertions.assertEquals(0,((RefOpenAddressHashSet.Checked<?>)cloneSet).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((RefOpenAddressHashSet<?>)cloneSet).table);
        }else{
          final var expectedTable=(Object[])this.expectedTable;
          while(--expectedTableLength >= 0){
            Object v;
            if((v=expectedTable[expectedTableLength]) != null && v != DELETED){
              if(v == NULL){
                v=null;
              }
              Assertions.assertTrue(cloneSet.contains(v));
            }
          }
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      verifyStructuralIntegrity(cloneSet);
    }
    @Override public void verifyCollectionState(){
      AbstractOpenAddressHashSet<?> set;
      Assertions.assertEquals(expectedSize,(set=this.set).size);
      Assertions.assertEquals(expectedMaxTableSize,set.maxTableSize);
      Assertions.assertEquals(expectedLoadFactor,set.loadFactor);
      switch(dataType){
      case FLOAT:
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((FloatOpenAddressHashSet.Checked)set).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((FloatOpenAddressHashSet)set).table);
        }else{
          Assertions.assertArrayEquals((int[])expectedTable,((FloatOpenAddressHashSet)set).table);
        }
        break;
      case DOUBLE:
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((DoubleOpenAddressHashSet.Checked)set).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((DoubleOpenAddressHashSet)set).table);
        }else{
          Assertions.assertArrayEquals((long[])expectedTable,((DoubleOpenAddressHashSet)set).table);
        }
        break;
      case REF:
        if(checkedType.checked){
          Assertions.assertEquals(expectedModCount,((RefOpenAddressHashSet.Checked<?>)set).modCount);
        }
        if(expectedTableLength == 0){
          Assertions.assertNull(((RefOpenAddressHashSet<?>)set).table);
        }else{
          Assertions.assertArrayEquals((Object[])expectedTable,((RefOpenAddressHashSet<?>)set).table);
        }
        break;
      default:
        throw DataType.invalidDataType(dataType);
      }
      verifyStructuralIntegrity(set);
    }
    @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
      Assertions.assertEquals(expectedSize,filter.numCalls);
      Assertions.assertEquals(filter.numRemoved,filter.removedVals.size());
      Assertions.assertEquals(filter.numRetained,filter.retainedVals.size());
      if(result){
        ++expectedModCount;
        final var removedValsItr=filter.removedVals.iterator();
        Assertions.assertTrue(removedValsItr.hasNext());
        final var retainedValsItr=filter.retainedVals.iterator();
        switch(dataType){
        case FLOAT:{
          do{
            final var v=(float)removedValsItr.next();
            Assertions.assertFalse(set.contains(v));
            removeVal(v);
          }while(removedValsItr.hasNext());
          while(retainedValsItr.hasNext()){
            final var v=(float)retainedValsItr.next();
            Assertions.assertTrue(set.contains(v));
          }
          break;
        }
        case DOUBLE:{
          do{
            final var v=(double)removedValsItr.next();
            Assertions.assertFalse(set.contains(v));
            removeVal(v);
          }while(removedValsItr.hasNext());
          while(retainedValsItr.hasNext()){
            final var v=(double)retainedValsItr.next();
            Assertions.assertTrue(set.contains(v));
          }
          break;
        }
        case REF:{
          do{
            final var v=removedValsItr.next();
            Assertions.assertFalse(set.contains(v));
            removeVal(v);
          }while(removedValsItr.hasNext());
          while(retainedValsItr.hasNext()){
            final var v=retainedValsItr.next();
            Assertions.assertTrue(set.contains(v));
          }
          break;
        }
        default:
          throw DataType.invalidDataType(dataType);
        }
      }
    }
    public void verifySetLoadFactor(float newLoadFactor){
      if(newLoadFactor <= 0.0f || newLoadFactor > 1.0f || newLoadFactor != newLoadFactor){
        Assertions.assertThrows(IllegalArgumentException.class,()->set.setLoadFactor(newLoadFactor));
      }else{
        set.setLoadFactor(newLoadFactor);
        expectedLoadFactor=newLoadFactor;
        if(expectedTable != null){
          int tableLength;
          switch(dataType){
          case FLOAT:
            tableLength=((int[])expectedTable).length;
            break;
          case DOUBLE:
            tableLength=((long[])expectedTable).length;
            break;
          case REF:
            tableLength=((Object[])expectedTable).length;
            break;
          default:
            throw DataType.invalidDataType(dataType);
          }
          expectedMaxTableSize=(int)(tableLength * newLoadFactor);
        }
      }
      verifyCollectionState();
    }
    @Override public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
      set.writeExternal(oos);
    }
    private void insert(int[] table,int hash,int val){
      int tableSize;
      if((tableSize=++expectedSize) >= expectedMaxTableSize){
        expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
        int[] newTable;
        expectedTable=newTable=new int[expectedTableLength=hash];
        if(tableSize != 1){
          for(int i=0;;++i){
            int tableVal;
            switch(tableVal=table[i]){
            case 0: // empty
            case 0x7fe00000: // deleted
              continue;
            default:
            }
            quickInsert(newTable,tableVal);
            if(--tableSize == 1){
              break;
            }
          }
        }
        quickInsert(newTable,val);
      }else{
        table[hash]=val;
      }
    }
    private void insert(long[] table,int hash,long val){
      int tableSize;
      if((tableSize=++expectedSize) >= expectedMaxTableSize){
        expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
        long[] newTable;
        expectedTable=newTable=new long[expectedTableLength=hash];
        if(tableSize != 1){
          for(int i=0;;++i){
            long tableVal;
            if((tableVal=table[i]) != 0 && tableVal != 0x7ffc000000000000L){
              quickInsert(newTable,tableVal);
              if(--tableSize == 1){
                break;
              }
            }
          }
        }
        quickInsert(newTable,val);
      }else{
        table[hash]=val;
      }
    }
    private void insert(Object[] table,int hash,Object val){
      int tableSize;
      if((tableSize=++expectedSize) >= expectedMaxTableSize){
        expectedMaxTableSize=(int)((hash=table.length << 1) * expectedLoadFactor);
        Object[] newTable;
        expectedTable=newTable=new Object[expectedTableLength=hash];
        if(tableSize != 1){
          for(int i=0;;++i){
            Object tableVal;
            if((tableVal=table[i]) != null && tableVal != DELETED){
              quickInsert(newTable,tableVal);
              if(--tableSize == 1){
                break;
              }
            }
          }
        }
        quickInsert(newTable,val);
      }else{
        table[hash]=val;
      }
    }
    private void insertInTable(double v){
      long tableVal;
      int hash=getTableHash(tableVal=getTableVal(v));
      long[] t;
      if((t=(long[])expectedTable) == null){
        int expectedTableLength;
        expectedTable=t=new long[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[hash & expectedTableLength - 1]=tableVal;
        expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
        expectedSize=1;
      }else{
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
          long existingVal;
          if((existingVal=t[hash]) == 0){
            if(insertHere == -1){
              insertHere=hash;
            }
            break insertInTable;
          }else{
            if(existingVal == 0x7ffc000000000000L){
              insertHere=hash;
            }
            if((hash=hash + 1 & expectedTableLength) == initialHash){
              break insertInTable;
            }
          }
        }
        insert(t,insertHere,tableVal);
      }
    }
    private void insertInTable(float v){
      int tableVal;
      int hash=getTableHash(tableVal=getTableVal(v));
      int[] t;
      if((t=(int[])expectedTable) == null){
        int expectedTableLength;
        expectedTable=t=new int[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[hash & expectedTableLength - 1]=tableVal;
        expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
        expectedSize=1;
      }else{
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
          switch(t[hash]){
          case 0:
            if(insertHere == -1){
              insertHere=hash;
            }
            break insertInTable;
          case 0x7fe00000:
            insertHere=hash;
          default:
            if((hash=hash + 1 & expectedTableLength) == initialHash){
              break insertInTable;
            }
          }
        }
        insert(t,insertHere,tableVal);
      }
    }
    private void insertInTable(Object v){
      int hash=getTableHash(v=getTableVal(v));
      Object[] t;
      if((t=(Object[])expectedTable) == null){
        int expectedTableLength;
        expectedTable=t=new Object[this.expectedTableLength=expectedTableLength=expectedMaxTableSize];
        t[hash & expectedTableLength - 1]=v;
        expectedMaxTableSize=(int)(expectedTableLength * expectedLoadFactor);
        expectedSize=1;
      }else{
        int insertHere=-1;
        int expectedTableLength;
        insertInTable:for(final int initialHash=hash&=expectedTableLength=this.expectedTableLength - 1;;){
          Object existingVal;
          if((existingVal=t[hash]) == null){
            if(insertHere == -1){
              insertHere=hash;
            }
            break insertInTable;
          }else{
            if(existingVal == DELETED){
              insertHere=hash;
            }
            if((hash=hash + 1 & expectedTableLength) == initialHash){
              break insertInTable;
            }
          }
        }
        insert(t,insertHere,v);
      }
    }
    private void removeVal(double v){
      final long[] expectedTable;
      int expectedTableLength;
      long tableVal;
      int hash=getTableHash(tableVal=getTableVal(v))
          & (expectedTableLength=(expectedTable=(long[])this.expectedTable).length - 1);
      for(;;){
        if(expectedTable[hash] == tableVal){
          expectedTable[hash]=0x7ffc000000000000L; // deleted
          break;
        }
        hash=hash + 1 & expectedTableLength;
      }
      --expectedSize;
    }
    private void removeVal(float v){
      final int[] expectedTable;
      int expectedTableLength;
      int tableVal;
      int hash=getTableHash(tableVal=getTableVal(v))
          & (expectedTableLength=(expectedTable=(int[])this.expectedTable).length - 1);
      for(;;){
        if(expectedTable[hash] == tableVal){
          expectedTable[hash]=0x7fe00000; // deleted
          break;
        }
        hash=hash + 1 & expectedTableLength;
      }
      --expectedSize;
    }
    private void removeVal(Object v){
      final Object[] expectedTable;
      int expectedTableLength;
      int hash=getTableHash(v=getTableVal(v))
          & (expectedTableLength=(expectedTable=(Object[])this.expectedTable).length - 1);
      for(;;){
        if(v.equals(expectedTable[hash])){
          expectedTable[hash]=DELETED; // deleted
          break;
        }
        hash=hash + 1 & expectedTableLength;
      }
      --expectedSize;
    }
    private void updateRemoveState(DataType inputType,Object inputVal,FunctionCallType funtionCallType){
      switch(dataType){
      case FLOAT:{
        float v;
        switch(inputType){
        case BOOLEAN:
          v=(boolean)inputVal?1.0f:0.0f;
          break;
        case BYTE:
          v=(byte)inputVal;
          break;
        case CHAR:
          v=(char)inputVal;
          break;
        case SHORT:
          v=(short)inputVal;
          break;
        case INT:
          v=(int)inputVal;
          break;
        case LONG:
          v=(long)inputVal;
          break;
        case FLOAT:
          v=(float)inputVal;
          break;
        default:
          throw DataType.invalidDataType(inputType);
        }
        removeVal(v);
        break;
      }
      case DOUBLE:{
        double v;
        switch(inputType){
        case BOOLEAN:
          v=(boolean)inputVal?1.0d:0.0d;
          break;
        case BYTE:
          v=(byte)inputVal;
          break;
        case CHAR:
          v=(char)inputVal;
          break;
        case SHORT:
          v=(short)inputVal;
          break;
        case INT:
          v=(int)inputVal;
          break;
        case LONG:
          v=(long)inputVal;
          break;
        case FLOAT:
          v=(float)inputVal;
          break;
        case DOUBLE:
          v=(double)inputVal;
          break;
        default:
          throw DataType.invalidDataType(inputType);
        }
        removeVal(v);
        break;
      }
      case REF:{
        removeVal(inputVal);
        break;
      }
      default:
        throw DataType.invalidDataType(dataType);
      }
      ++expectedModCount;
    }
    private void verifyStructuralIntegrity(AbstractOpenAddressHashSet<?> set){
      final HashSet<Object> encounteredVals=new HashSet<>(set.size);
      switch(dataType){
      case FLOAT:{
        int[] table;
        if((table=((FloatOpenAddressHashSet)set).table) != null){
          int i=table.length;
          // check that it is a power of 2
          Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
          Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
          while(--i >= 0){
            int v;
            switch(v=table[i]){
            case 0: // empty
            case 0x7fe00000: // deleted
              break;
            default:
              Assertions.assertTrue(encounteredVals.add(v));
            }
          }
        }
        break;
      }
      case DOUBLE:{
        long[] table;
        if((table=((DoubleOpenAddressHashSet)set).table) != null){
          int i=table.length;
          // check that it is a power of 2
          Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
          Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
          while(--i >= 0){
            long v;
            if((v=table[i]) != 0 && v != 0x7ffc000000000000L){
              Assertions.assertTrue(encounteredVals.add(v));
            }
          }
        }
        break;
      }
      case REF:{
        Object[] table;
        if((table=((RefOpenAddressHashSet<?>)set).table) != null){
          int i=table.length;
          // check that it is a power of 2
          Assertions.assertEquals(1,i >>> Integer.numberOfTrailingZeros(i));
          Assertions.assertEquals((int)(i * set.loadFactor),set.maxTableSize);
          while(--i >= 0){
            Object v;
            if((v=table[i]) != null && v != DELETED){
              Assertions.assertTrue(encounteredVals.add(v));
            }
          }
        }
        break;
      }
      default:
        throw DataType.invalidDataType(dataType);
      }
      Assertions.assertEquals(encounteredVals.size(),set.size);
    }
  }
  private interface QueryTest{
    static final int[] sizes=new int[]{0,1,2,3,4,5,8,13,16,21,32,34,55,64,89,128,129,144,233,256,257,377,512,610,987};
    static final double[] positions=new double[]{-1,0,0.25,0.5,0.75,1};
    boolean callMethod(OpenAddressHashSetMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
        QueryVal.QueryValModification modification);
    default void runAllTests(String testName){
      for(final var loadFactor:LOAD_FACTORS){
        if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
          for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            for(final var queryVal:QueryVal.values()){
              queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                castTypesToInputTypes.forEach((castType,inputTypes)->{
                  inputTypes.forEach(inputType->{
                    final boolean queryCanReturnTrue
                        =queryVal.queryCanReturnTrue(modification,castType,inputType,collectionType);
                    for(final var checkedType:CheckedType.values()){
                      for(final var size:sizes){
                        for(final var position:positions){
                          if(position >= 0){
                            if(!queryCanReturnTrue){
                              continue;
                            }
                            switch(size){
                            case 3:
                              if(position == 0.5d){
                                break;
                              }
                            case 2:
                              if(position == 1.0d){
                                break;
                              }
                            case 1:
                              if(position == 0.0d){
                                break;
                              }
                            case 0:
                              continue;
                            case 4:
                              if(position != 0.5d){
                                break;
                              }
                            default:
                              continue;
                            }
                          }
                          TestExecutorService.submitTest(()->runTest(collectionType,queryVal,modification,inputType,
                              castType,checkedType,size,loadFactor,position));
                        }
                      }
                    }
                  });
                });
              });
            }
          }
        }
      }
      TestExecutorService.completeAllTests(testName);
    }
    @SuppressWarnings("unchecked") default void runTest(DataType collectionType,QueryVal queryVal,
        QueryVal.QueryValModification modification,DataType inputType,QueryCastType castType,CheckedType checkedType,
        int setSize,float loadFactor,double position){
      final var monitor=new OpenAddressHashSetMonitor(collectionType,checkedType,0,loadFactor);
      boolean expectedResult;
      if(position < 0){
        expectedResult=false;
        switch(collectionType){
        case FLOAT:
          queryVal.initDoesNotContain((OmniSet.OfFloat)monitor.set,setSize,0,modification);
          break;
        case DOUBLE:
          queryVal.initDoesNotContain((OmniSet.OfDouble)monitor.set,setSize,0,modification);
          break;
        case REF:
          queryVal.initDoesNotContain((OmniSet.OfRef<Object>)monitor.set,setSize,0,modification);
          break;
        default:
          throw DataType.invalidDataType(collectionType);
        }
      }else{
        expectedResult=true;
        switch(collectionType){
        case FLOAT:
          queryVal.initContains((OmniSet.OfFloat)monitor.set,setSize,0,position,modification);
          break;
        case DOUBLE:
          queryVal.initContains((OmniSet.OfDouble)monitor.set,setSize,0,position,modification);
          break;
        case REF:
          queryVal.initContains((OmniSet.OfRef<Object>)monitor.set,setSize,0,position,modification,inputType);
          break;
        default:
          throw DataType.invalidDataType(collectionType);
        }
      }
      monitor.updateCollectionState();
      final boolean actualResult=callMethod(monitor,queryVal,inputType,castType,modification);
      Assertions.assertEquals(expectedResult,actualResult);
    }
  }
  private static final double[] RANDOM_THRESHOLDS=new double[]{0.01,0.05,0.10,0.25,0.50,0.75,0.90,0.95,0.99};
  private static final double[] NON_RANDOM_THRESHOLD=new double[]{0.5};
  private static final EnumSet<SetInitializationSequence> VALID_INIT_SEQS
      =EnumSet.of(SetInitializationSequence.Empty,SetInitializationSequence.AddTrue,SetInitializationSequence.AddFalse,
          SetInitializationSequence.AddTrueAndFalse,SetInitializationSequence.AddPrime,
          SetInitializationSequence.AddFibSeq,SetInitializationSequence.AddMinByte,SetInitializationSequence.FillWord0,
          SetInitializationSequence.FillWord1,SetInitializationSequence.FillWord2,SetInitializationSequence.FillWord3);
  private static final int[] CONSTRUCTOR_INITIAL_CAPACITIES=new int[5 + 29 * 3 + 2];
  private static final float[] LOAD_FACTORS
      =new float[]{0.1f,0.25f,0.5f,0.75f,0.9f,0.95f,1.0f,1.1f,2.0f,0.0f,-1f,-.75f,-.5f,Float.NaN};
  private static final int[] GENERAL_PURPOSE_INITIAL_CAPACITIES
      =new int[]{0,1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192};
  static{
    CONSTRUCTOR_INITIAL_CAPACITIES[0]=0;
    CONSTRUCTOR_INITIAL_CAPACITIES[1]=1;
    CONSTRUCTOR_INITIAL_CAPACITIES[2]=2;
    int i=2;
    for(int pow=2;pow <= 30;++pow){
      CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) - 1;
      CONSTRUCTOR_INITIAL_CAPACITIES[++i]=1 << pow;
      CONSTRUCTOR_INITIAL_CAPACITIES[++i]=(1 << pow) + 1;
    }
    CONSTRUCTOR_INITIAL_CAPACITIES[++i]=OmniArray.MAX_ARR_SIZE;
    CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MAX_VALUE;
    CONSTRUCTOR_INITIAL_CAPACITIES[++i]=-1;
    CONSTRUCTOR_INITIAL_CAPACITIES[++i]=Integer.MIN_VALUE;
  }
  private static void testadd_valHelper(DataType collectionType,DataType inputType,CheckedType checkedType,
      float loadFactor,int initialCapacity,FunctionCallType functionCallType){
    final var monitor=new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor);
    for(long i=0;i < 1000;++i){
      final Object inputVal=inputType.convertValUnchecked(i);
      testadd_valHelper(collectionType,inputType,functionCallType,monitor,inputVal);
    }
  }
  private static void testadd_valHelper(DataType collectionType,DataType inputType,FunctionCallType functionCallType,
      final OpenAddressHashSetMonitor monitor,final Object inputVal){
    boolean alreadyContains;
    switch(collectionType){
    case FLOAT:{
      final var set=(FloatOpenAddressHashSet)monitor.set;
      switch(inputType){
      case BOOLEAN:
        alreadyContains=set.contains((boolean)inputVal);
        break;
      case BYTE:
        alreadyContains=set.contains((byte)inputVal);
        break;
      case CHAR:
        alreadyContains=set.contains((char)inputVal);
        break;
      case SHORT:
        alreadyContains=set.contains((short)inputVal);
        break;
      case INT:
        alreadyContains=set.contains((int)inputVal);
        break;
      case LONG:
        alreadyContains=set.contains((long)inputVal);
        break;
      case FLOAT:
        alreadyContains=set.contains((float)inputVal);
        break;
      default:
        throw DataType.invalidDataType(inputType);
      }
      break;
    }
    case DOUBLE:{
      final var set=(DoubleOpenAddressHashSet)monitor.set;
      switch(inputType){
      case BOOLEAN:
        alreadyContains=set.contains((boolean)inputVal);
        break;
      case BYTE:
        alreadyContains=set.contains((byte)inputVal);
        break;
      case CHAR:
        alreadyContains=set.contains((char)inputVal);
        break;
      case SHORT:
        alreadyContains=set.contains((short)inputVal);
        break;
      case INT:
        alreadyContains=set.contains((int)inputVal);
        break;
      case LONG:
        alreadyContains=set.contains((long)inputVal);
        break;
      case FLOAT:
        alreadyContains=set.contains((float)inputVal);
        break;
      case DOUBLE:
        alreadyContains=set.contains((double)inputVal);
        break;
      default:
        throw DataType.invalidDataType(inputType);
      }
      break;
    }
    case REF:{
      final var set=(RefOpenAddressHashSet<?>)monitor.set;
      alreadyContains=set.contains(inputVal);
      break;
    }
    default:
      throw DataType.invalidDataType(collectionType);
    }
    Assertions.assertNotEquals(alreadyContains,monitor.verifyAdd(inputVal,inputType,functionCallType));
  }
  private static void testConstructor_floatHelper(float loadFactor,DataType collectionType,CheckedType checkedType){
    if(loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
      new OpenAddressHashSetMonitor(collectionType,checkedType,loadFactor).verifyCollectionState();
    }else{
      Assertions.assertThrows(IllegalArgumentException.class,
          ()->new OpenAddressHashSetMonitor(collectionType,checkedType,loadFactor));
    }
  }
  private static void testConstructor_intfloatHelper(int initialCapacity,float loadFactor,DataType collectionType,
      CheckedType checkedType){
    if(initialCapacity >= 0 && loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
      new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor).verifyCollectionState();
    }else{
      Assertions.assertThrows(IllegalArgumentException.class,
          ()->new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor));
    }
  }
  private static void testConstructor_intHelper(int initialCapacity,DataType collectionType,CheckedType checkedType){
    if(initialCapacity >= 0){
      new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity).verifyCollectionState();
    }else{
      Assertions.assertThrows(IllegalArgumentException.class,
          ()->new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity));
    }
  }
  private static void testforEach_ConsumerHelper(DataType collectionType,MonitoredFunctionGen functionGen,
      FunctionCallType functionCallType,long randSeed,CheckedType checkedType,SetInitializationSequence initSeq){
    final var monitor=initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
    if(functionGen.expectedException == null || monitor.size() == 0){
      monitor.verifyForEach(functionGen,functionCallType,randSeed);
    }else{
      Assertions.assertThrows(functionGen.expectedException,
          ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
      monitor.verifyCollectionState();
    }
  }
  private static void testItrforEachRemaining_ConsumerHelper(float loadFactor,int initCapacity,int itrScenario,
      IllegalModification preMod,MonitoredFunctionGen functionGen,FunctionCallType functionCallType,long randSeed,
      DataType collectionType,CheckedType checkedType,SetInitializationSequence initSeq){
    final var setMonitor
        =initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor));
    final var itrMonitor=setMonitor.getMonitoredIterator();
    switch(itrScenario){
    case 1:
      itrMonitor.iterateForward();
      break;
    case 2:
      itrMonitor.iterateForward();
      itrMonitor.remove();
      break;
    case 3:
      for(int i=0;i < 13 && itrMonitor.hasNext();++i){
        itrMonitor.iterateForward();
      }
      break;
    default:
    }
    final int numLeft=itrMonitor.getNumLeft();
    itrMonitor.illegalMod(preMod);
    final Class<? extends Throwable> expectedException
        =numLeft == 0?null:preMod.expectedException == null?functionGen.expectedException:preMod.expectedException;
    if(expectedException == null){
      itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed);
    }else{
      Assertions.assertThrows(expectedException,
          ()->itrMonitor.verifyForEachRemaining(functionGen,functionCallType,randSeed));
      itrMonitor.verifyIteratorState();
      setMonitor.verifyCollectionState();
    }
  }
  private static void testItrnext_voidHelper(float loadFactor,int initialCapacity,IllegalModification preMod,
      DataType outputType,DataType collectionType,CheckedType checkedType,SetInitializationSequence initSeq){
    final var setMonitor
        =initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initialCapacity,loadFactor));
    final var itrMonitor=setMonitor.getMonitoredIterator();
    itrMonitor.illegalMod(preMod);
    if(preMod.expectedException == null){
      while(itrMonitor.hasNext()){
        itrMonitor.verifyNext(outputType);
      }
      Assertions.assertFalse(itrMonitor.getIterator().hasNext());
      if(checkedType.checked){
        Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
      }
    }else{
      Assertions.assertThrows(preMod.expectedException,()->itrMonitor.verifyNext(outputType));
    }
    itrMonitor.verifyIteratorState();
    setMonitor.verifyCollectionState();
  }
  private static void testItrremove_voidHelper(OpenAddressHashSetMonitor setMonitor,int itrCount,
      IteratorRemoveScenario itrRemoveScenario,IllegalModification preMod){
    final var itrMonitor=setMonitor.getMonitoredIterator();
    for(int i=0;i < itrCount;++i){
      itrMonitor.iterateForward();
    }
    if(itrRemoveScenario == IteratorRemoveScenario.PostRemove){
      itrMonitor.remove();
    }
    itrMonitor.illegalMod(preMod);
    final Class<? extends Throwable> expectedException
        =itrRemoveScenario.expectedException == null?preMod.expectedException:itrRemoveScenario.expectedException;
    if(expectedException == null){
      for(;;){
        itrMonitor.verifyRemove();
        if(!itrMonitor.hasNext()){
          break;
        }
        itrMonitor.iterateForward();
      }
    }else{
      Assertions.assertThrows(expectedException,()->itrMonitor.verifyRemove());
      itrMonitor.verifyIteratorState();
      setMonitor.verifyCollectionState();
    }
  }
  private static void testremoveIf_PredicateHelper(DataType collectionType,MonitoredRemoveIfPredicateGen filterGen,
      FunctionCallType functionCallType,long randSeed,double threshold,CheckedType checkedType,
      SetInitializationSequence initSeq){
    final var monitor=initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
    final var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,threshold,randSeed);
    final int sizeBefore=monitor.size();
    if(filterGen.expectedException == null || sizeBefore == 0){
      final boolean result=monitor.verifyRemoveIf(filter,functionCallType);
      if(sizeBefore == 0b00){
        Assertions.assertFalse(result);
      }else{
        switch(filterGen){
        case Random:
          Assertions.assertEquals(filter.numRemoved != 0,result);
          break;
        case RemoveAll:
          Assertions.assertTrue(monitor.set.isEmpty());
          Assertions.assertTrue(result);
          break;
        case RemoveFalse:
          Assertions.assertFalse(monitor.set.contains(false));
          break;
        case RemoveNone:
          Assertions.assertFalse(result);
          Assertions.assertFalse(monitor.set.isEmpty());
          break;
        case RemoveTrue:
          Assertions.assertFalse(monitor.set.contains(true));
          break;
        default:
          throw new UnsupportedOperationException("Unknown filterGen " + filterGen);
        }
      }
    }else{
      Assertions.assertThrows(filterGen.expectedException,()->monitor.verifyRemoveIf(filter,functionCallType));
      monitor.verifyCollectionState();
    }
  }
  @org.junit.jupiter.api.Test public void testadd_val(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var functionCallType:FunctionCallType.values()){
        if(collectionType != DataType.REF || functionCallType != FunctionCallType.Boxed){
          for(final float loadFactor:LOAD_FACTORS){
            if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
              for(final var inputType:collectionType.mayBeAddedTo()){
                for(final var checkedType:CheckedType.values()){
                  for(final int initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                    TestExecutorService.submitTest(()->testadd_valHelper(collectionType,inputType,checkedType,
                        loadFactor,initialCapacity,functionCallType));
                  }
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testadd_val");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testclear_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifyClear();
    };
    test.runAllTests("OpenAddressHashSetTest.testclear_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testclone_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifyClone();
    };
    test.runAllTests("OpenAddressHashSetTest.testclone_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testConstructor_float(){
    
      for(final var checkedType:CheckedType.values()){
        for(final float loadFactor:LOAD_FACTORS){
          if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            TestExecutorService.submitTest(()->testConstructor_floatHelper(loadFactor,collectionType,checkedType));
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_float");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testConstructor_int(){
    
      for(final var checkedType:CheckedType.values()){
        for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
          if(checkedType.checked || initialCapacity >= 0){
            for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
            TestExecutorService.submitTest(()->testConstructor_intHelper(initialCapacity,collectionType,checkedType));
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_int");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testConstructor_intfloat(){
    
      for(final var checkedType:CheckedType.values()){
        for(final int initialCapacity:CONSTRUCTOR_INITIAL_CAPACITIES){
          if(checkedType.checked || initialCapacity >= 0){
            for(final float loadFactor:LOAD_FACTORS){
              if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
                for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                TestExecutorService.submitTest(
                    ()->testConstructor_intfloatHelper(initialCapacity,loadFactor,collectionType,checkedType));
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_intfloat");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testConstructor_void(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var checkedType:CheckedType.values()){
        TestExecutorService
            .submitTest(()->new OpenAddressHashSetMonitor(collectionType,checkedType).verifyCollectionState());
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testConstructor_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testcontains_val(){
    final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,inputType,
        castType,modification);
    test.runAllTests("OpenAddressHashSetTest.testcontains_val");
  }
  @org.junit.jupiter.api.Test public void testforEach_Consumer(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var functionCallType:FunctionCallType.values()){
        if(collectionType != DataType.REF || functionCallType != FunctionCallType.Boxed){
          for(final var functionGen:StructType.OpenAddressHashSet.validMonitoredFunctionGens){
            for(final var initSeq:VALID_INIT_SEQS){
              final int size
                  =initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
              final long randSeedBound=functionGen.randomized && size > 1 && !functionCallType.boxed?100:0;
              for(final var checkedType:CheckedType.values()){
                if(checkedType.checked || functionGen.expectedException == null || size == 0){
                  LongStream.rangeClosed(0,randSeedBound)
                      .forEach(randSeed->TestExecutorService.submitTest(()->testforEach_ConsumerHelper(collectionType,
                          functionGen,functionCallType,randSeed,checkedType,initSeq)));
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testforEach_Consumer");
  }
  @org.junit.jupiter.api.Test public void testhashCode_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifyHashCode();
    };
    test.runAllTests("OpenAddressHashSetTest.testhashCode_void");
  }
  @org.junit.jupiter.api.Test public void testisEmpty_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifyIsEmpty();
    };
    test.runAllTests("OpenAddressHashSetTest.testisEmpty_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testiterator_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .getMonitoredIterator().verifyIteratorState();
    };
    test.runAllTests("OpenAddressHashSetTest.testiterator_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testItrclone_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->initSeq
        .initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
        .getMonitoredIterator().verifyClone();
    test.runAllTests("OpenAddressHashSetTest.testItrclone_void");
  }
  //@Disabled
  @org.junit.jupiter.api.Test public void testItrforEachRemaining_Consumer(){
    for(final var loadFactor:LOAD_FACTORS){
      if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
        
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var functionCallType:FunctionCallType.values()){
        if(collectionType != DataType.REF || functionCallType != FunctionCallType.Boxed){
          for(final var checkedType:CheckedType.values()){
            for(final var initSeq:VALID_INIT_SEQS){
              int sizeScenario;
              switch(initSeq){
              case Empty:
                sizeScenario=0;
                break;
              case AddPrime:
              case AddTrue:
              case AddFalse:
              case AddMinByte:
                sizeScenario=1;
                break;
              case AddTrueAndFalse:
              case FillWord0:
              case FillWord1:
              case FillWord2:
              case FillWord3:
                sizeScenario=2;
                break;
              case AddFibSeq:
                sizeScenario=3;
                break;
              default:
                throw new UnsupportedOperationException("Unknown initSeq " + initSeq);
              }
              for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                if(checkedType.checked || functionGen.expectedException == null || sizeScenario == 0){
                  for(final var preMod:IteratorType.AscendingItr.validPreMods){
                    if(checkedType.checked || preMod.expectedException == null || sizeScenario == 0){
                      int itrScenarioMax=0;
                      if(sizeScenario > 1){
                        if(sizeScenario > 2){
                          itrScenarioMax=3;
                        }else{
                          itrScenarioMax=2;
                        }
                      }
                      IntStream.rangeClosed(0,itrScenarioMax).forEach(itrScenario->{
                        LongStream.rangeClosed(0,preMod.expectedException == null && functionGen.randomized
                            && sizeScenario > 1 && itrScenario == 0?100:0).forEach(randSeed->{
                              for(final var initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                                    TestExecutorService
                                        .submitTest(()->testItrforEachRemaining_ConsumerHelper(loadFactor,initCapacity,
                                            itrScenario,preMod,functionGen,functionCallType,randSeed,collectionType,
                                            checkedType,initSeq));
                              }
          
                            });
                            
                      });
                    }
                  }
                }
              }
            }
          }
        }
      }
    
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrforEachRemaining_Consumer");
  }
  @org.junit.jupiter.api.Test public void testItrhasNext_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      final var setMonitor
          =initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor));
      final var itrMonitor=setMonitor.getMonitoredIterator();
      final var setSize=setMonitor.size();
      for(int i=0;i < setSize;++i){
        Assertions.assertTrue(itrMonitor.verifyHasNext());
        itrMonitor.iterateForward();
      }
      Assertions.assertFalse(itrMonitor.verifyHasNext());
    };
    test.runAllTests("OpenAddressHashSetTest.testItrhasNext_void");
  }
  //@Disabled
  @org.junit.jupiter.api.Test public void testItrnext_void(){
    for(final var loadFactor:LOAD_FACTORS){
      if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
        
          for(final var checkedType:CheckedType.values()){
            for(final var preMod:IteratorType.AscendingItr.validPreMods){
              if(checkedType.checked || preMod.expectedException == null){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      
      for(final var outputType:collectionType.validOutputTypes()){
        
        for(final var initialCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                    for(final var initSeq:VALID_INIT_SEQS){
                      TestExecutorService.submitTest(()->testItrnext_voidHelper(loadFactor,initialCapacity,preMod,
                          outputType,collectionType,checkedType,initSeq));
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrnext_void");
  }
  //@Disabled
  @org.junit.jupiter.api.Test public void testItrremove_void(){
    for(final float loadFactor:LOAD_FACTORS){
      if(loadFactor > 0.f && loadFactor <= 1.0f && loadFactor == loadFactor){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var checkedType:CheckedType.values()){
        for(final var initSeq:VALID_INIT_SEQS){
          for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
            if((!initSeq.isEmpty || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                && (checkedType.checked || itrRemoveScenario.expectedException == null)){
              for(final var preMod:IteratorType.AscendingItr.validPreMods){
                if(checkedType.checked || preMod.expectedException == null){
                  final var monitor=initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
                  final int setSize=monitor.size();
                  int itrOffset,itrBound;
                  if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                    itrOffset=itrBound=0;
                  }else{
                    itrOffset=1;
                    itrBound=setSize;
                  }
                  IntStream.rangeClosed(itrOffset,itrBound).forEach(itrCount->{

                        for(final int initCapacity:GENERAL_PURPOSE_INITIAL_CAPACITIES){
                          TestExecutorService.submitTest(()->testItrremove_voidHelper(
                              initSeq.initialize(
                                  new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor)),
                              itrCount,itrRemoveScenario,preMod));
                        }

                  });
                }
              }
            }
          }
        }
      }
    }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testItrremove_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testMASSIVEtoString(){
    int initCapacity;
    OpenAddressHashSetMonitor monitor;
    initCapacity=OmniArray.MAX_ARR_SIZE / 17 + 1;
    monitor=new OpenAddressHashSetMonitor(DataType.FLOAT,CheckedType.UNCHECKED,initCapacity + 1,.75f);
    final var set=(OmniSet.IntInput<?>)monitor.set;
    set.add(0);
    int pPrev;
    int prev;
    set.add(pPrev=1);
    set.add(prev=2);
    int numAdded=3;
    for(;;){
      final int curr=pPrev + prev;
      if(curr >= 128){
        break;
      }
      set.add(curr);
      ++numAdded;
      pPrev=prev;
      prev=curr;
    }
    int curr=128;
    for(;;){
      set.add(curr);
      if(++numAdded == initCapacity){
        break;
      }
      ++curr;
    }
    monitor.updateCollectionState();
    monitor.verifyToString();
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testReadAndWrite(){
    final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSeq)->initSeq
        .initialize(new OpenAddressHashSetMonitor(collectionType,checkedType)).verifyReadAndWrite(functionGen);
    test.runAllTests("OpenAddressHashSetTest.testReadAndWrite");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testremoveIf_Predicate(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var functionCallType:FunctionCallType.values()){
        if(collectionType != DataType.REF || functionCallType != FunctionCallType.Boxed){
          for(final var initSeq:VALID_INIT_SEQS){
            final int setSize
                =initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
            for(final var filterGen:StructType.OpenAddressHashSet.validMonitoredRemoveIfPredicateGens){
              final long randSeedBound;
              final double[] thresholdArr;
              if(filterGen.randomized && setSize > 1 && !functionCallType.boxed){
                randSeedBound=100;
                thresholdArr=RANDOM_THRESHOLDS;
              }else{
                randSeedBound=0;
                thresholdArr=NON_RANDOM_THRESHOLD;
              }
              for(final var checkedType:CheckedType.values()){
                if(checkedType.checked || filterGen.expectedException == null || setSize == 0){
                  LongStream.rangeClosed(0,randSeedBound)
                      .forEach(randSeed->DoubleStream.of(thresholdArr).forEach(
                          threshold->TestExecutorService.submitTest(()->testremoveIf_PredicateHelper(collectionType,
                              filterGen,functionCallType,randSeed,threshold,checkedType,initSeq))));
                }
              }
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testremoveIf_Predicate");
  }
  //@Disabled
  @org.junit.jupiter.api.Test public void testremoveVal_val(){
    final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,inputType,
        castType,modification);
    test.runAllTests("OpenAddressHashSetTest.testremoveVal_val");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testsetLoadFactor_float(){
    
      for(final var checkedType:CheckedType.values()){
        
          for(final float loadFactor:LOAD_FACTORS){
            if(checkedType.checked || loadFactor == loadFactor && loadFactor <= 1.0f && loadFactor > 0){
              for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
                for(final var initSeq:VALID_INIT_SEQS){
              TestExecutorService
                  .submitTest(()->initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType))
                      .verifySetLoadFactor(loadFactor));
            }
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testsetLoadFactor_float");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testsize_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifySize();
    };
    test.runAllTests("OpenAddressHashSetTest.testsize_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testtoArray_IntFunction(){
    final MonitoredFunctionGenTest test=(collectionType,functionGen,checkedType,initSeq)->{
      final var monitor=initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType));
      if(functionGen.expectedException == null){
        monitor.verifyToArray(functionGen);
      }else{
        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
        monitor.verifyCollectionState();
      }
    };
    test.runAllTests("OpenAddressHashSetTest.testToArray_IntFunction");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testtoArray_ObjectArray(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var initSeq:VALID_INIT_SEQS){
        final int size=initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,CheckedType.UNCHECKED)).size();
        for(final var checkedType:CheckedType.values()){
          for(int arrSize=0,increment=Math.max(1,size / 10),bound=size + increment + 2;arrSize <= bound;
              arrSize+=increment){
            final Object[] paramArr=new Object[arrSize];
            TestExecutorService.submitTest(()->initSeq
                .initialize(new OpenAddressHashSetMonitor(collectionType,checkedType)).verifyToArray(paramArr));
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testtoArray_ObjectArray");
  }
  @org.junit.jupiter.api.Test public void testtoArray_void(){
    for(final var collectionType:StructType.OpenAddressHashSet.validDataTypes){
      for(final var outputType:collectionType.validOutputTypes()){
        for(final var checkedType:CheckedType.values()){
          for(final var initSeq:VALID_INIT_SEQS){
            TestExecutorService.submitTest(()->{
              outputType.verifyToArray(initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType)));
            });
          }
        }
      }
    }
    TestExecutorService.completeAllTests("OpenAddressHashSetTest.testtoArray_void");
  }
  // @Disabled
  @org.junit.jupiter.api.Test public void testtoString_void(){
    final BasicTest test=(loadFactor,initCapacity,collectionType,checkedType,initSeq)->{
      initSeq.initialize(new OpenAddressHashSetMonitor(collectionType,checkedType,initCapacity,loadFactor))
          .verifyToString();
    };
    test.runAllTests("OpenAddressHashSetTest.testtoString_void");
  }
  @org.junit.jupiter.api.AfterEach public void verifyAllExecuted(){
    int numTestsRemaining;
    if((numTestsRemaining=TestExecutorService.getNumRemainingTasks()) != 0){
      System.err.println("Warning: there were " + numTestsRemaining + " tests that were not completed");
    }
    TestExecutorService.reset();
  }
}
