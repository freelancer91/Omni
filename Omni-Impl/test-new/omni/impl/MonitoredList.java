package omni.impl;

import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.impl.QueryVal.QueryValModification;
public interface MonitoredList<ITR extends OmniIterator<?>,LST extends OmniList<?>>extends MonitoredSequence<LST>{
    default void verifyAdd(int index,Object inputVal,DataType inputType,FunctionCallType functionCallType){
        var collection=getCollection();
        try{
            inputType.callListAdd(index,inputVal,collection,functionCallType);
            updateAddState(index,inputVal,inputType);
        }finally{
            verifyCollectionState();
        }
    }
    @Override
    default void updateAddState(Object inputVal,DataType inputType){
        updateAddState(size(),inputVal,inputType);
    }
    void updateAddState(int index,Object inputVal,DataType inputType);
    default int verifyIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        try{
            return queryCastType.callindexOf(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyLastIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
            QueryValModification modification){
        try{
            return queryCastType.calllastIndexOf(getCollection(),queryVal.getInputVal(inputType,modification),
                    inputType);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyThrowingIndexOf(MonitoredObjectGen monitoredObjectGen){
        Object inputVal=monitoredObjectGen.getMonitoredObject(this);
        try{
            return QueryCastType.ToObject.callindexOf(getCollection(),inputVal,DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    default int verifyThrowingLastIndexOf(MonitoredObjectGen monitoredObjectGen){
        Object inputVal=monitoredObjectGen.getMonitoredObject(this);
        try{
            return QueryCastType.ToObject.calllastIndexOf(getCollection(),inputVal,DataType.REF);
        }finally{
            verifyCollectionState();
        }
    }
    void verifyPutResult(int index,Object input,DataType inputType);
    void verifyGetResult(int index,Object result,DataType outputType);
    default Object verifySet(int index,Object inputVal,FunctionCallType functionCallType){
        Object result;
        try{
            var dataType=getDataType();
            result=dataType.callListSet(index,inputVal,getCollection(),functionCallType);
            verifyGetResult(index,result,dataType);
            verifyPutResult(index,inputVal,dataType);
        }finally{
            verifyCollectionState();
        }
        return result;
    }
    default Object verifyGet(int index,DataType outputType) {
        Object result;
        try {
            result=outputType.callListGet(index,this);
        }finally{
            verifyCollectionState();
        }
        verifyGetResult(index,result,outputType);
        return result;
    }
    default void verifyPut(int index,Object inputVal,DataType inputType,FunctionCallType functionCallType){
        try{
            inputType.callListPut(index,inputVal,getCollection(),functionCallType);
            verifyPutResult(index,inputVal,inputType);
        }finally{
            verifyCollectionState();
        }
    }
    //    interface MonitoredListIterator<ITR extends OmniListIterator<?>,LST extends OmniList<?>>extends MonitoredIterator<ITR,LST>{
    //        @Override
    //        MonitoredList<ITR,LST> getMonitoredCollection();
    //        @Override
    //        ITR getIterator();
    //        void iterateReverse();
    //        boolean hasPrevious();
    //        Object verifyPrevious(DataType outputType);
    //        boolean verifyHasPrevious();
    //        int nextIndex();
    //        int previousIndex();
    //        int verifyNextIndex();
    //        int verifyPreviousIndex();
    //        void verifyAdd(Object inputVal,DataType inputType,boolean boxed);
    //    }
    //    MonitoredListIterator<OmniListIterator<?>,LST> getMonitoredListIterator();
    //    MonitoredListIterator<OmniListIterator<?>,LST> getMonitoredListIterator(int index);
    //    @SuppressWarnings("unchecked") default Object verifyGet(int index,DataType outputType) {
    //
    //      Object result;
    //      try {
    //        var collection=getCollection();
    //      switch(outputType) {
    //      case BOOLEAN:
    //        result=((OmniList.OfBoolean)collection).getBoolean(index);
    //        break;
    //      case BYTE:
    //        result=((OmniList.ByteOutput<?>)collection).getByte(index);
    //        break;
    //      case CHAR:
    //        result=((OmniList.CharOutput<?>)collection).getChar(index);
    //        break;
    //      case SHORT:
    //        result=((OmniList.ShortOutput<?>)collection).getShort(index);
    //        break;
    //      case INT:
    //        result=((OmniList.IntOutput<?>)collection).getInt(index);
    //        break;
    //      case LONG:
    //        result=((OmniList.LongOutput<?>)collection).getLong(index);
    //        break;
    //      case FLOAT:
    //        result=((OmniList.FloatOutput<?>)collection).getFloat(index);
    //        break;
    //      case DOUBLE:
    //        result=((OmniList.DoubleOutput<?>)collection).getDouble(index);
    //        break;
    //      case REF:
    //        var dataType=getDataType();
    //        switch(dataType) {
    //        case BOOLEAN:
    //          result=((OmniList.OfBoolean)collection).get(index);
    //          break;
    //        case BYTE:
    //          result=((OmniList.OfByte)collection).get(index);
    //          break;
    //        case CHAR:
    //          result=((OmniList.OfChar)collection).get(index);
    //          break;
    //        case SHORT:
    //          result=((OmniList.OfShort)collection).get(index);
    //          break;
    //        case INT:
    //          result=((OmniList.OfInt)collection).get(index);
    //          break;
    //        case LONG:
    //          result=((OmniList.OfLong)collection).get(index);
    //          break;
    //        case FLOAT:
    //          result=((OmniList.OfFloat)collection).get(index);
    //          break;
    //        case DOUBLE:
    //          result=((OmniList.OfDouble)collection).get(index);
    //          break;
    //        case REF:
    //          result=((OmniList.OfRef<Object>)collection).get(index);
    //          break;
    //        default:
    //          throw dataType.invalid();
    //        }
    //        break;
    //      default:
    //        throw outputType.invalid();
    //      }
    //      }finally {
    //        verifyCollectionState();
    //      }
    //
    //      return result;
    //    }
    //    @SuppressWarnings("unchecked") default Object verifySet(int index,int inputVal,boolean boxed) {
    //      Object result;
    //      try {
    //        var collection=getCollection();
    //        var dataType=getDataType();
    //        switch(dataType) {
    //        case BOOLEAN:
    //          if(boxed) {
    //            result=((OmniList.OfBoolean)collection).set(index,(Boolean)(boolean)((inputVal&1)!=0));
    //          }else {
    //            result=((OmniList.OfBoolean)collection).set(index,(boolean)((inputVal&1)!=0));
    //          }
    //          break;
    //        case BYTE:
    //          if(boxed) {
    //            result=((OmniList.OfByte)collection).set(index,(Byte)(byte)inputVal);
    //          }else {
    //            result=((OmniList.OfByte)collection).set(index,(byte)inputVal);
    //          }
    //          break;
    //        case CHAR:
    //          if(boxed) {
    //            result=((OmniList.OfChar)collection).set(index,(Character)(char)inputVal);
    //          }else {
    //            result=((OmniList.OfChar)collection).set(index,(char)inputVal);
    //          }
    //          break;
    //        case SHORT:
    //          if(boxed) {
    //            result=((OmniList.OfShort)collection).set(index,(Short)(short)inputVal);
    //          }else {
    //            result=((OmniList.OfShort)collection).set(index,(short)inputVal);
    //          }
    //          break;
    //        case INT:
    //          if(boxed) {
    //            result=((OmniList.OfInt)collection).set(index,(Integer)(int)inputVal);
    //          }else {
    //            result=((OmniList.OfInt)collection).set(index,(int)inputVal);
    //          }
    //          break;
    //        case LONG:
    //          if(boxed) {
    //            result=((OmniList.OfLong)collection).set(index,(Long)(long)inputVal);
    //          }else {
    //            result=((OmniList.OfLong)collection).set(index,(long)inputVal);
    //          }
    //          break;
    //        case FLOAT:
    //          if(boxed) {
    //            result=((OmniList.OfFloat)collection).set(index,(Float)(float)inputVal);
    //          }else {
    //            result=((OmniList.OfFloat)collection).set(index,(float)inputVal);
    //          }
    //          break;
    //        case DOUBLE:
    //          if(boxed) {
    //            result=((OmniList.OfDouble)collection).set(index,(Double)(double)inputVal);
    //          }else {
    //            result=((OmniList.OfDouble)collection).set(index,(double)inputVal);
    //          }
    //          break;
    //        case REF:
    //          if(boxed) {
    //            throw DataType.cannotBeBoxed();
    //          }
    //          result=((OmniList.OfRef<Object>)collection).set(index,inputVal);
    //          break;
    //        default:
    //          throw dataType.invalid();
    //        }
    //      }finally {
    //        verifyCollectionState();
    //      }
    //      return result;
    //    }
    //    @SuppressWarnings("unchecked") default void verifyPut(int index,int inputVal,boolean boxed) {
    //      try {
    //        var collection=getCollection();
    //        var dataType=getDataType();
    //        switch(dataType) {
    //        case BOOLEAN:
    //          if(boxed) {
    //            ((OmniList.OfBoolean)collection).put(index,(Boolean)(boolean)((inputVal&1)!=0));
    //          }else {
    //            ((OmniList.OfBoolean)collection).put(index,(boolean)((inputVal&1)!=0));
    //          }
    //          break;
    //        case BYTE:
    //          if(boxed) {
    //            ((OmniList.OfByte)collection).put(index,(Byte)(byte)inputVal);
    //          }else {
    //            ((OmniList.OfByte)collection).put(index,(byte)inputVal);
    //          }
    //          break;
    //        case CHAR:
    //          if(boxed) {
    //            ((OmniList.OfChar)collection).put(index,(Character)(char)inputVal);
    //          }else {
    //            ((OmniList.OfChar)collection).put(index,(char)inputVal);
    //          }
    //          break;
    //        case SHORT:
    //          if(boxed) {
    //            ((OmniList.OfShort)collection).put(index,(Short)(short)inputVal);
    //          }else {
    //            ((OmniList.OfShort)collection).put(index,(short)inputVal);
    //          }
    //          break;
    //        case INT:
    //          if(boxed) {
    //            ((OmniList.OfInt)collection).put(index,(Integer)(int)inputVal);
    //          }else {
    //            ((OmniList.OfInt)collection).put(index,(int)inputVal);
    //          }
    //          break;
    //        case LONG:
    //          if(boxed) {
    //            ((OmniList.OfLong)collection).put(index,(Long)(long)inputVal);
    //          }else {
    //            ((OmniList.OfLong)collection).put(index,(long)inputVal);
    //          }
    //          break;
    //        case FLOAT:
    //          if(boxed) {
    //            ((OmniList.OfFloat)collection).put(index,(Float)(float)inputVal);
    //          }else {
    //            ((OmniList.OfFloat)collection).put(index,(float)inputVal);
    //          }
    //          break;
    //        case DOUBLE:
    //          if(boxed) {
    //            ((OmniList.OfDouble)collection).put(index,(Double)(double)inputVal);
    //          }else {
    //            ((OmniList.OfDouble)collection).put(index,(double)inputVal);
    //          }
    //          break;
    //        case REF:
    //          if(boxed) {
    //            throw DataType.cannotBeBoxed();
    //          }
    //          ((OmniList.OfRef<Object>)collection).put(index,inputVal);
    //          break;
    //        default:
    //          throw dataType.invalid();
    //        }
    //      }finally {
    //        verifyCollectionState();
    //      }
    //    }
    //    void updateRemoveIndexState(int index);
    //    void updateAddState(int index,Object inputVal,DataType inputType);
    //    default void verifyAdd(int index,Object inputVal,DataType inputType,boolean boxed) {
    //      try {
    //      var collection=getCollection();
    //      switch(inputType) {
    //      case BOOLEAN:{
    //        var cast=(OmniList.BooleanInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Boolean)inputVal);
    //        }else {
    //          cast.add(index,(boolean)inputVal);
    //        }
    //        break;
    //      }
    //      case BYTE:{
    //        var cast=(OmniList.ByteInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Byte)inputVal);
    //        }else {
    //          cast.add(index,(byte)inputVal);
    //        }
    //        break;
    //      }
    //      case CHAR:{
    //        var cast=(OmniList.CharInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Character)inputVal);
    //        }else {
    //          cast.add(index,(char)inputVal);
    //        }
    //        break;
    //      }
    //      case SHORT:{
    //        var cast=(OmniList.ShortInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Short)inputVal);
    //        }else {
    //          cast.add(index,(short)inputVal);
    //        }
    //        break;
    //      }
    //      case INT:{
    //        var cast=(OmniList.IntInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Integer)inputVal);
    //        }else {
    //          cast.add(index,(int)inputVal);
    //        }
    //        break;
    //      }
    //      case LONG:{
    //        var cast=(OmniList.LongInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Long)inputVal);
    //        }else {
    //          cast.add(index,(long)inputVal);
    //        }
    //        break;
    //      }
    //      case FLOAT:{
    //        var cast=(OmniList.FloatInput<?>)collection;
    //        if(boxed) {
    //          cast.add(index,(Float)inputVal);
    //        }else {
    //          cast.add(index,(float)inputVal);
    //        }
    //        break;
    //      }
    //      case DOUBLE:{
    //        var cast=(OmniList.OfDouble)collection;
    //        if(boxed) {
    //          cast.add(index,(Double)inputVal);
    //        }else {
    //          cast.add(index,(double)inputVal);
    //        }
    //        break;
    //      }
    //      case REF:{
    //        @SuppressWarnings("unchecked") var cast=(OmniList.OfRef<Object>)collection;
    //        if(boxed) {
    //          throw DataType.cannotBeBoxed();
    //        }else {
    //          cast.add(index,inputVal);
    //        }
    //        break;
    //      }
    //      }
    //      updateAddState(inputVal,inputType);
    //      }finally {
    //      verifyCollectionState();
    //      }
    //    }
    //    @SuppressWarnings("unchecked") default Object verifyRemoveAtIndex(int index,DataType outputType) {
    //      Object result;
    //      try {
    //        var collection=getCollection();
    //        switch(outputType) {
    //        case BOOLEAN:{
    //          result=((OmniList.OfBoolean)collection).removeBooleanAt(index);
    //          break;
    //        }
    //        case BYTE:{
    //          result=((OmniList.ByteOutput<?>)collection).removeByteAt(index);
    //          break;
    //        }
    //        case CHAR:{
    //          result=((OmniList.CharOutput<?>)collection).removeCharAt(index);
    //          break;
    //        }
    //        case SHORT:{
    //          result=((OmniList.ShortOutput<?>)collection).removeShortAt(index);
    //          break;
    //        }
    //        case INT:{
    //          result=((OmniList.IntOutput<?>)collection).removeIntAt(index);
    //          break;
    //        }
    //        case LONG:{
    //          result=((OmniList.LongOutput<?>)collection).removeLongAt(index);
    //          break;
    //        }
    //        case FLOAT:{
    //          result=((OmniList.FloatOutput<?>)collection).removeFloatAt(index);
    //          break;
    //        }
    //        case DOUBLE:{
    //          result=((OmniList.DoubleOutput<?>)collection).removeDoubleAt(index);
    //          break;
    //        }
    //        case REF:{
    //          var dataType=getDataType();
    //          switch(dataType) {
    //          case BOOLEAN:{
    //            result=((OmniList.OfBoolean)collection).remove(index);
    //            break;
    //          }
    //          case BYTE:{
    //            result=((OmniList.OfByte)collection).remove(index);
    //            break;
    //          }
    //          case CHAR:{
    //            result=((OmniList.OfChar)collection).remove(index);
    //            break;
    //          }
    //          case SHORT:{
    //            result=((OmniList.OfShort)collection).remove(index);
    //            break;
    //          }
    //          case INT:{
    //            result=((OmniList.OfInt)collection).remove(index);
    //            break;
    //          }
    //          case LONG:{
    //            result=((OmniList.OfLong)collection).remove(index);
    //            break;
    //          }
    //          case FLOAT:{
    //            result=((OmniList.OfFloat)collection).remove(index);
    //            break;
    //          }
    //          case DOUBLE:{
    //            result=((OmniList.OfDouble)collection).remove(index);
    //            break;
    //          }
    //          case REF:{
    //            result=((OmniList.OfRef<Object>)collection).remove(index);
    //            break;
    //          }
    //          default:
    //            throw dataType.invalid();
    //          }
    //        }
    //        default:
    //          throw outputType.invalid();
    //        }
    //        updateRemoveIndexState(index);
    //      }finally {
    //        verifyCollectionState();
    //      }
    //      return result;
    //    }
    //    default int verifyIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
    //            QueryVal.QueryValModification modification) {
    //      try {
    //        return queryCastType.callindexOf(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
    //      }finally {
    //        verifyCollectionState();
    //      }
    //    }
    //    default int verifyLastIndexOf(QueryVal queryVal,DataType inputType,QueryCastType queryCastType,
    //            QueryVal.QueryValModification modification) {
    //      try {
    //        return queryCastType.calllastIndexOf(getCollection(),queryVal.getInputVal(inputType,modification),inputType);
    //      }finally {
    //        verifyCollectionState();
    //      }
    //    }
    //    default OmniList<?> verifySubList(int fromIndex,int toIndex){
    //      try {
    //        return getCollection().subList(fromIndex,toIndex);
    //      }finally {
    //        verifyCollectionState();
    //      }
    //    }
    //    default void verifyReplaceAll(MonitoredFunctionGen functionGen,FunctionCallType functionCallType) {
    //      //TODO
    //
    //    }
    //
    //    @Override default void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
    //      // TODO Auto-generated method stub
    //
    //    }
    //    @Override default void verifyArrayIsCopy(Object arr){
    //      // TODO Auto-generated method stub
    //
    //    }
    //    @Override default void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
    //      // TODO Auto-generated method stub
    //
    //    }
    //    @Override default SequenceVerificationItr getVerificationItr(){
    //      // TODO Auto-generated method stub
    //      return null;
    //    }
    //    @Override default void updateAddState(Object inputVal,DataType inputType){
    //      // TODO Auto-generated method stub
    //
    //    }
    //    @Override default void updateRemoveValState(Object inputVal,DataType inputType){
    //      // TODO Auto-generated method stub
    //
    //    }
    //    @Override default boolean isEmpty(){
    //      return size()==0;
    //    }

    // verify sort
}
