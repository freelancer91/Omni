package omni.impl;

import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;

public interface MonitoredNavigableSet<SET extends OmniNavigableSet<E>,E> extends MonitoredSortedSet<SET>{
   @SuppressWarnings("unchecked") default Object callPollFirst(DataType outputType) {
    final var set=getCollection();
    switch(outputType) {
    case BOOLEAN:
      return ((OmniNavigableSet.OfBoolean)set).pollFirstBoolean();
    case BYTE:
      return ((OmniNavigableSet.ByteOutput<E>)set).pollFirstByte();
    case CHAR:
      return ((OmniNavigableSet.CharOutput<E>)set).pollFirstChar();
    case DOUBLE:
      return ((OmniNavigableSet.DoubleOutput<E>)set).pollFirstDouble();
    case FLOAT:
      return ((OmniNavigableSet.FloatOutput<E>)set).pollFirstFloat();
    case INT:
      return ((OmniNavigableSet.IntOutput<E>)set).pollFirstInt();
    case LONG:
      return ((OmniNavigableSet.LongOutput<E>)set).pollFirstLong();
    case REF:
      return set.pollFirst();
    case SHORT:
      return ((OmniNavigableSet.ShortOutput<E>)set).pollFirstShort();
    default:
      throw outputType.invalid();
    }
  }
   @SuppressWarnings("unchecked") default Object callPollLast(DataType outputType) {
     final var set=getCollection();
     switch(outputType) {
     case BOOLEAN:
       return ((OmniNavigableSet.OfBoolean)set).pollLastBoolean();
     case BYTE:
       return ((OmniNavigableSet.ByteOutput<E>)set).pollLastByte();
     case CHAR:
       return ((OmniNavigableSet.CharOutput<E>)set).pollLastChar();
     case DOUBLE:
       return ((OmniNavigableSet.DoubleOutput<E>)set).pollLastDouble();
     case FLOAT:
       return ((OmniNavigableSet.FloatOutput<E>)set).pollLastFloat();
     case INT:
       return ((OmniNavigableSet.IntOutput<E>)set).pollLastInt();
     case LONG:
       return ((OmniNavigableSet.LongOutput<E>)set).pollLastLong();
     case REF:
       return set.pollLast();
     case SHORT:
       return ((OmniNavigableSet.ShortOutput<E>)set).pollLastShort();
     default:
       throw outputType.invalid();
     }
   }
  
  @SuppressWarnings("unchecked") default Object callLower(DataType outputType,Object val) {
    final var set=getCollection();
    switch(outputType) {
    case BOOLEAN:
      return ((OmniNavigableSet.OfBoolean)set).lowerBoolean((boolean)val);
    case BYTE:
      return ((OmniNavigableSet.ByteOutput<E>)set).lowerByte((byte)val);
    case CHAR:
      return ((OmniNavigableSet.CharOutput<E>)set).lowerChar((char)val);
    case DOUBLE:
      return ((OmniNavigableSet.DoubleOutput<E>)set).lowerDouble((double)val);
    case FLOAT:
      return ((OmniNavigableSet.FloatOutput<E>)set).lowerFloat((float)val);
    case INT:
      return ((OmniNavigableSet.IntOutput<E>)set).lowerInt((int)val);
    case LONG:
      return ((OmniNavigableSet.LongOutput<E>)set).lowerLong((long)val);
    case REF:
      return set.lower((E)val);
    case SHORT:
      return ((OmniNavigableSet.ShortOutput<E>)set).lowerShort((short)val);
    default:
      throw outputType.invalid();
    }
  }
  @SuppressWarnings("unchecked") default Object callHigher(DataType outputType,Object val) {
    final var set=getCollection();
    switch(outputType) {
    case BOOLEAN:
      return ((OmniNavigableSet.OfBoolean)set).higherBoolean((boolean)val);
    case BYTE:
      return ((OmniNavigableSet.ByteOutput<E>)set).higherByte((byte)val);
    case CHAR:
      return ((OmniNavigableSet.CharOutput<E>)set).higherChar((char)val);
    case DOUBLE:
      return ((OmniNavigableSet.DoubleOutput<E>)set).higherDouble((double)val);
    case FLOAT:
      return ((OmniNavigableSet.FloatOutput<E>)set).higherFloat((float)val);
    case INT:
      return ((OmniNavigableSet.IntOutput<E>)set).higherInt((int)val);
    case LONG:
      return ((OmniNavigableSet.LongOutput<E>)set).higherLong((long)val);
    case REF:
      return set.higher((E)val);
    case SHORT:
      return ((OmniNavigableSet.ShortOutput<E>)set).higherShort((short)val);
    default:
      throw outputType.invalid();
    }
  }
  
  @SuppressWarnings("unchecked") default Object callFloor(DataType outputType,Object val) {
    final var set=getCollection();
    switch(outputType) {
    case BOOLEAN:
      return ((OmniNavigableSet.OfBoolean)set).booleanFloor((boolean)val);
    case BYTE:
      return ((OmniNavigableSet.ByteOutput<E>)set).byteFloor((byte)val);
    case CHAR:
      return ((OmniNavigableSet.CharOutput<E>)set).charFloor((char)val);
    case DOUBLE:
      return ((OmniNavigableSet.DoubleOutput<E>)set).doubleFloor((double)val);
    case FLOAT:
      return ((OmniNavigableSet.FloatOutput<E>)set).floatFloor((float)val);
    case INT:
      return ((OmniNavigableSet.IntOutput<E>)set).intFloor((int)val);
    case LONG:
      return ((OmniNavigableSet.LongOutput<E>)set).longFloor((long)val);
    case REF:
      return set.floor((E)val);
    case SHORT:
      return ((OmniNavigableSet.ShortOutput<E>)set).shortFloor((short)val);
    default:
      throw outputType.invalid();
    }
  }
  @SuppressWarnings("unchecked") default Object callCeiling(DataType outputType,Object val) {
    final var set=getCollection();
    switch(outputType) {
    case BOOLEAN:
      return ((OmniNavigableSet.OfBoolean)set).booleanCeiling((boolean)val);
    case BYTE:
      return ((OmniNavigableSet.ByteOutput<E>)set).byteCeiling((byte)val);
    case CHAR:
      return ((OmniNavigableSet.CharOutput<E>)set).charCeiling((char)val);
    case DOUBLE:
      return ((OmniNavigableSet.DoubleOutput<E>)set).doubleCeiling((double)val);
    case FLOAT:
      return ((OmniNavigableSet.FloatOutput<E>)set).floatCeiling((float)val);
    case INT:
      return ((OmniNavigableSet.IntOutput<E>)set).intCeiling((int)val);
    case LONG:
      return ((OmniNavigableSet.LongOutput<E>)set).longCeiling((long)val);
    case REF:
      return set.ceiling((E)val);
    case SHORT:
      return ((OmniNavigableSet.ShortOutput<E>)set).shortCeiling((short)val);
    default:
      throw outputType.invalid();
    }
  }
  
  
  Object verifyLower(Object input,DataType outputType);
  Object verifyHigher(Object input,DataType outputType);
  Object verifyCeiling(Object input,DataType outputType);
  Object verifyFloor(Object input,DataType outputType);
  Object verifyPollFirst(DataType outputType);
  Object verifyPollLast(DataType outputType);
  MonitoredIterator<OmniIterator<E>,SET> descendingIterator();
  MonitoredNavigableSet<SET,E> descendingSet();
  default MonitoredNavigableSet<SET,E> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType){
    return subSet(fromElement,true,toElement,true,inputType,functionCallType);
  }
  MonitoredNavigableSet<SET,E> subSet(Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,DataType inputType,FunctionCallType functionCallType);
  default MonitoredNavigableSet<SET,E> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType){
    return headSet(toElement,true,inputType,functionCallType);
  }
  MonitoredNavigableSet<SET,E> headSet(Object toElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);
  default MonitoredNavigableSet<SET,E> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType){
    return tailSet(fromElement,true,inputType,functionCallType);
  }
  MonitoredNavigableSet<SET,E> tailSet(Object fromElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);
}
