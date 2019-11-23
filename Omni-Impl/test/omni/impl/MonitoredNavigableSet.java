package omni.impl;

import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;

public interface MonitoredNavigableSet<SET extends OmniNavigableSet<E>,E> extends MonitoredSortedSet<SET,E>{
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
  MonitoredIterator<? extends OmniIterator<E>,SET> descendingIterator();
  
  
  @SuppressWarnings("unchecked")
default SET callSubSet(Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,FunctionCallType functionCallType){
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniNavigableSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Boolean)fromElement, fromInclusive,(Boolean)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((boolean)fromElement, fromInclusive,(boolean)toElement,toInclusive);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniNavigableSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Byte)fromElement, fromInclusive,(Byte)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((byte)fromElement, fromInclusive,(byte)toElement,toInclusive);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniNavigableSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Character)fromElement, fromInclusive,(Character)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((char)fromElement, fromInclusive,(char)toElement,toInclusive);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniNavigableSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Short)fromElement, fromInclusive,(Short)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((short)fromElement, fromInclusive,(short)toElement,toInclusive);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniNavigableSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Integer)fromElement, fromInclusive,(Integer)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((int)fromElement,fromInclusive, (int)toElement,toInclusive);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniNavigableSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Long)fromElement,fromInclusive, (Long)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((long)fromElement,fromInclusive, (long)toElement,toInclusive);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniNavigableSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Float)fromElement, fromInclusive,(Float)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((float)fromElement, fromInclusive,(float)toElement,toInclusive);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniNavigableSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Double)fromElement, fromInclusive,(Double)toElement,toInclusive);
			  }else {
				  return (SET)set.subSet((double)fromElement, fromInclusive,(double)toElement,toInclusive);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().subSet((E)fromElement, fromInclusive,(E)toElement,toInclusive);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  
  @SuppressWarnings("unchecked")
  default SET callHeadSet(Object toElement,boolean inclusive,FunctionCallType functionCallType) {
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniNavigableSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Boolean)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((boolean)toElement,inclusive);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniNavigableSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Byte)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((byte)toElement,inclusive);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniNavigableSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Character)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((char)toElement,inclusive);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniNavigableSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Short)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((short)toElement,inclusive);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniNavigableSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Integer)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((int)toElement,inclusive);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniNavigableSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Long)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((long)toElement,inclusive);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniNavigableSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Float)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((float)toElement,inclusive);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniNavigableSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Double)toElement,inclusive);
			  }else {
				  return (SET)set.headSet((double)toElement,inclusive);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().headSet((E)toElement,inclusive);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  @SuppressWarnings("unchecked")
  default SET callTailSet(Object fromElement,boolean inclusive,FunctionCallType functionCallType) {
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniNavigableSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Boolean)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((boolean)fromElement,inclusive);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniNavigableSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Byte)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((byte)fromElement,inclusive);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniNavigableSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Character)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((char)fromElement,inclusive);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniNavigableSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Short)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((short)fromElement,inclusive);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniNavigableSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Integer)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((int)fromElement,inclusive);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniNavigableSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Long)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((long)fromElement,inclusive);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniNavigableSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Float)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((float)fromElement,inclusive);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniNavigableSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Double)fromElement,inclusive);
			  }else {
				  return (SET)set.tailSet((double)fromElement,inclusive);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().tailSet((E)fromElement,inclusive);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  
  
  
  
  MonitoredNavigableSet<SET,E> descendingSet();
  MonitoredNavigableSet<SET,E> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType);

  MonitoredNavigableSet<SET,E> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType);
  MonitoredNavigableSet<SET,E> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType);


  MonitoredNavigableSet<SET,E> subSet(Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,DataType inputType,FunctionCallType functionCallType);

  MonitoredNavigableSet<SET,E> headSet(Object toElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);
  MonitoredNavigableSet<SET,E> tailSet(Object fromElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);

}
