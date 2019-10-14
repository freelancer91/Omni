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
  @SuppressWarnings("unchecked")
  default SET callSubSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  fromElement=dataType.convertValUnchecked(inputType,fromElement);
	  toElement=dataType.convertValUnchecked(inputType, toElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfBoolean)set).subSet((Boolean)fromElement, (Boolean)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfBoolean)set).subSet((boolean)fromElement, (boolean)toElement);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfByte)set).subSet((Byte)fromElement, (Byte)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfByte)set).subSet((byte)fromElement, (byte)toElement);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfChar)set).subSet((Character)fromElement, (Character)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfChar)set).subSet((char)fromElement, (char)toElement);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfShort)set).subSet((Short)fromElement, (Short)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfShort)set).subSet((short)fromElement, (short)toElement);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfInt)set).subSet((Integer)fromElement, (Integer)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfInt)set).subSet((int)fromElement, (int)toElement);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfLong)set).subSet((Long)fromElement, (Long)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfLong)set).subSet((long)fromElement, (long)toElement);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfFloat)set).subSet((Float)fromElement, (Float)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfFloat)set).subSet((float)fromElement, (float)toElement);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfDouble)set).subSet((Double)fromElement, (Double)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfDouble)set).subSet((double)fromElement, (double)toElement);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniSortedSet.OfRef<Object>)set).subSet(fromElement, toElement);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  @SuppressWarnings("unchecked")
  default SET callHeadSet(Object toElement,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  toElement=dataType.convertValUnchecked(inputType, toElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfBoolean)set).headSet((Boolean)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfBoolean)set).headSet( (boolean)toElement);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfByte)set).headSet((Byte)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfByte)set).headSet((byte)toElement);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfChar)set).headSet((Character)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfChar)set).headSet((char)toElement);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfShort)set).headSet((Short)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfShort)set).headSet((short)toElement);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfInt)set).headSet((Integer)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfInt)set).headSet((int)toElement);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfLong)set).headSet((Long)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfLong)set).headSet((long)toElement);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfFloat)set).headSet((Float)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfFloat)set).headSet((float)toElement);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfDouble)set).headSet((Double)toElement);
		  }else {
			  return (SET)((OmniSortedSet.OfDouble)set).headSet((double)toElement);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniSortedSet.OfRef<Object>)set).headSet(toElement);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  @SuppressWarnings("unchecked")
  default SET callTailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  fromElement=dataType.convertValUnchecked(inputType, fromElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfBoolean)set).tailSet((Boolean)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfBoolean)set).tailSet( (boolean)fromElement);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfByte)set).tailSet((Byte)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfByte)set).tailSet((byte)fromElement);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfChar)set).tailSet((Character)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfChar)set).tailSet((char)fromElement);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfShort)set).tailSet((Short)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfShort)set).tailSet((short)fromElement);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfInt)set).tailSet((Integer)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfInt)set).tailSet((int)fromElement);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfLong)set).tailSet((Long)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfLong)set).tailSet((long)fromElement);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfFloat)set).tailSet((Float)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfFloat)set).tailSet((float)fromElement);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniSortedSet.OfDouble)set).tailSet((Double)fromElement);
		  }else {
			  return (SET)((OmniSortedSet.OfDouble)set).tailSet((double)fromElement);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniSortedSet.OfRef<Object>)set).tailSet(fromElement);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  default MonitoredSortedSet<SET> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return subSetHelper(callSubSet(fromElement, toElement, inputType, functionCallType),fromElement,toElement,inputType,functionCallType);
  }
  
  default MonitoredSortedSet<SET> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return headSetHelper(callHeadSet( toElement, inputType, functionCallType),toElement,inputType,functionCallType);
  }
 
  default MonitoredSortedSet<SET> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType){
	  return tailSetHelper(callTailSet( fromElement, inputType, functionCallType),fromElement,inputType,functionCallType);
  }
  MonitoredSortedSet<SET> subSetHelper(SET subSet,Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType);

  MonitoredSortedSet<SET> headSetHelper(SET headSet,Object toElement,DataType inputType,FunctionCallType functionCallType);

  MonitoredSortedSet<SET> tailSetHelper(SET tailSet,Object fromElement,DataType inputType,FunctionCallType functionCallType);

  Object verifyFirst(DataType outputType);
  Object verifyLast(DataType outputType);
}
