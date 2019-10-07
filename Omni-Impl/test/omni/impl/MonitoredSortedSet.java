package omni.impl;

import omni.api.OmniSortedSet;

public interface MonitoredSortedSet<SET extends OmniSortedSet<?>> extends MonitoredSet<SET>{
  default Object callFirst(DataType outputType) {
    switch(outputType) {
    case BOOLEAN:
      return ((OmniSortedSet.OfBoolean)getCollection()).firstBoolean();
    case BYTE:
      return ((OmniSortedSet.ByteOutput<?>)getCollection()).firstByte();
    case CHAR:
      return ((OmniSortedSet.CharOutput<?>)getCollection()).firstChar();
    case DOUBLE:
      return ((OmniSortedSet.DoubleOutput<?>)getCollection()).firstDouble();
    case FLOAT:
      return ((OmniSortedSet.FloatOutput<?>)getCollection()).firstFloat();
    case INT:
      return ((OmniSortedSet.IntOutput<?>)getCollection()).firstInt();
    case LONG:
      return ((OmniSortedSet.LongOutput<?>)getCollection()).firstLong();
    case REF:
      return ((OmniSortedSet<?>)getCollection()).first();
    case SHORT:
      return ((OmniSortedSet.ShortOutput<?>)getCollection()).firstShort();
    default:
      throw outputType.invalid();
    }
  }
  default Object callLast(DataType outputType) {
    switch(outputType) {
    case BOOLEAN:
      return ((OmniSortedSet.OfBoolean)getCollection()).lastBoolean();
    case BYTE:
      return ((OmniSortedSet.ByteOutput<?>)getCollection()).lastByte();
    case CHAR:
      return ((OmniSortedSet.CharOutput<?>)getCollection()).lastChar();
    case DOUBLE:
      return ((OmniSortedSet.DoubleOutput<?>)getCollection()).lastDouble();
    case FLOAT:
      return ((OmniSortedSet.FloatOutput<?>)getCollection()).lastFloat();
    case INT:
      return ((OmniSortedSet.IntOutput<?>)getCollection()).lastInt();
    case LONG:
      return ((OmniSortedSet.LongOutput<?>)getCollection()).lastLong();
    case REF:
      return ((OmniSortedSet<?>)getCollection()).last();
    case SHORT:
      return ((OmniSortedSet.ShortOutput<?>)getCollection()).lastShort();
    default:
      throw outputType.invalid();
    }
  }
  
  Object verifyComparator();
  MonitoredSortedSet<SET> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType);
  MonitoredSortedSet<SET> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType);
  MonitoredSortedSet<SET> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType);
  Object verifyFirst(DataType outputType);
  Object verifyLast(DataType outputType);
}
