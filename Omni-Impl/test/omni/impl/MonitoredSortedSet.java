package omni.impl;


import omni.api.OmniSortedSet;

public interface MonitoredSortedSet<SET extends OmniSortedSet<E>,E> extends MonitoredSet<SET>{
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
  
  
  @SuppressWarnings("unchecked")
  default SET callSubSet(Object fromElement,Object toElement,FunctionCallType functionCallType){
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniSortedSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Boolean)fromElement, (Boolean)toElement);
			  }else {
				  return (SET)set.subSet((boolean)fromElement, (boolean)toElement);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniSortedSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Byte)fromElement, (Byte)toElement);
			  }else {
				  return (SET)set.subSet((byte)fromElement, (byte)toElement);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniSortedSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Character)fromElement, (Character)toElement);
			  }else {
				  return (SET)set.subSet((char)fromElement, (char)toElement);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniSortedSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Short)fromElement, (Short)toElement);
			  }else {
				  return (SET)set.subSet((short)fromElement, (short)toElement);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniSortedSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Integer)fromElement, (Integer)toElement);
			  }else {
				  return (SET)set.subSet((int)fromElement, (int)toElement);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniSortedSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Long)fromElement, (Long)toElement);
			  }else {
				  return (SET)set.subSet((long)fromElement, (long)toElement);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniSortedSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Float)fromElement, (Float)toElement);
			  }else {
				  return (SET)set.subSet((float)fromElement, (float)toElement);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniSortedSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.subSet((Double)fromElement, (Double)toElement);
			  }else {
				  return (SET)set.subSet((double)fromElement, (double)toElement);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().subSet((E)fromElement, (E)toElement);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  
  @SuppressWarnings("unchecked")
  default SET callHeadSet(Object toElement,FunctionCallType functionCallType) {
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniSortedSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Boolean)toElement);
			  }else {
				  return (SET)set.headSet((boolean)toElement);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniSortedSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Byte)toElement);
			  }else {
				  return (SET)set.headSet((byte)toElement);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniSortedSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Character)toElement);
			  }else {
				  return (SET)set.headSet((char)toElement);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniSortedSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Short)toElement);
			  }else {
				  return (SET)set.headSet((short)toElement);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniSortedSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Integer)toElement);
			  }else {
				  return (SET)set.headSet((int)toElement);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniSortedSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Long)toElement);
			  }else {
				  return (SET)set.headSet((long)toElement);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniSortedSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Float)toElement);
			  }else {
				  return (SET)set.headSet((float)toElement);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniSortedSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.headSet((Double)toElement);
			  }else {
				  return (SET)set.headSet((double)toElement);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().headSet((E)toElement);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  @SuppressWarnings("unchecked")
  default SET callTailSet(Object fromElement,FunctionCallType functionCallType) {
	  try {
		  final var dataType=getDataType();
		  switch(dataType) {
		  case BOOLEAN:
		  {
			  final var set=(OmniSortedSet.OfBoolean)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Boolean)fromElement);
			  }else {
				  return (SET)set.tailSet((boolean)fromElement);
			  }
		  }
		  case BYTE:
		  {
			  final var set=(OmniSortedSet.OfByte)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Byte)fromElement);
			  }else {
				  return (SET)set.tailSet((byte)fromElement);
			  }
		  }
		  case CHAR:
		  {
			  final var set=(OmniSortedSet.OfChar)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Character)fromElement);
			  }else {
				  return (SET)set.tailSet((char)fromElement);
			  }
		  }
		  case SHORT:
		  {
			  final var set=(OmniSortedSet.OfShort)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Short)fromElement);
			  }else {
				  return (SET)set.tailSet((short)fromElement);
			  }
		  }
		  case INT:
		  {
			  final var set=(OmniSortedSet.OfInt)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Integer)fromElement);
			  }else {
				  return (SET)set.tailSet((int)fromElement);
			  }
		  }
		  case LONG:
		  {
			  final var set=(OmniSortedSet.OfLong)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Long)fromElement);
			  }else {
				  return (SET)set.tailSet((long)fromElement);
			  }
		  }
		  case FLOAT:
		  {
			  final var set=(OmniSortedSet.OfFloat)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Float)fromElement);
			  }else {
				  return (SET)set.tailSet((float)fromElement);
			  }
		  }
		  case DOUBLE:
		  {
			  final var set=(OmniSortedSet.OfDouble)getCollection();
			  if(functionCallType.boxed) {
				  return (SET)set.tailSet((Double)fromElement);
			  }else {
				  return (SET)set.tailSet((double)fromElement);
			  }
		  }
		  case REF:
			  if(functionCallType.boxed) {
				  throw DataType.cannotBeBoxed();
			  }else {
				  return (SET)getCollection().tailSet((E)fromElement);
			  }
		  default:
			  throw dataType.invalid();
		  }
	  }finally {
		  verifyCollectionState();
	  }
	  
  }
  
  Object verifyComparator();

  MonitoredSortedSet<SET,E> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType);
  
  
  MonitoredSortedSet<SET,E> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType);

  
  MonitoredSortedSet<SET,E> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType);

  Object verifyFirst(DataType outputType);
  Object verifyLast(DataType outputType);
}
