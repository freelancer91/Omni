package omni.impl;

import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.api.OmniSortedSet;

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
  MonitoredIterator<? extends OmniIterator<E>,SET> descendingIterator();
  MonitoredNavigableSet<SET,E> descendingSet();
  
  MonitoredNavigableSet<SET,E> subSetHelper(SET subSet,Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,DataType inputType,FunctionCallType functionCallType);

  MonitoredNavigableSet<SET,E> headSetHelper(SET headSet,Object toElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);

  MonitoredNavigableSet<SET,E> tailSetHelper(SET tailSet,Object fromElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType);

  
  
  @SuppressWarnings("unchecked")
  default SET callSubSet(Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  fromElement=dataType.convertValUnchecked(inputType,fromElement);
	  toElement=dataType.convertValUnchecked(inputType, toElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfBoolean)set).subSet((Boolean)fromElement, fromInclusive,(Boolean)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfBoolean)set).subSet((boolean)fromElement, fromInclusive,(boolean)toElement,toInclusive);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfByte)set).subSet((Byte)fromElement, fromInclusive,(Byte)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfByte)set).subSet((byte)fromElement, fromInclusive,(byte)toElement,toInclusive);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfChar)set).subSet((Character)fromElement, fromInclusive,(Character)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfChar)set).subSet((char)fromElement, fromInclusive,(char)toElement,toInclusive);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfShort)set).subSet((Short)fromElement, fromInclusive,(Short)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfShort)set).subSet((short)fromElement, fromInclusive,(short)toElement,toInclusive);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfInt)set).subSet((Integer)fromElement, fromInclusive,(Integer)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfInt)set).subSet((int)fromElement, fromInclusive,(int)toElement,toInclusive);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfLong)set).subSet((Long)fromElement,fromInclusive, (Long)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfLong)set).subSet((long)fromElement,fromInclusive, (long)toElement,toInclusive);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfFloat)set).subSet((Float)fromElement, fromInclusive,(Float)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfFloat)set).subSet((float)fromElement, fromInclusive,(float)toElement,toInclusive);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfDouble)set).subSet((Double)fromElement,fromInclusive, (Double)toElement,toInclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfDouble)set).subSet((double)fromElement, fromInclusive,(double)toElement,toInclusive);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniNavigableSet.OfRef<Object>)set).subSet(fromElement,fromInclusive, toElement,toInclusive);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  @SuppressWarnings("unchecked")
  default SET callHeadSet(Object toElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  toElement=dataType.convertValUnchecked(inputType, toElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfBoolean)set).headSet((Boolean)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfBoolean)set).headSet( (boolean)toElement,inclusive);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfByte)set).headSet((Byte)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfByte)set).headSet((byte)toElement,inclusive);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfChar)set).headSet((Character)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfChar)set).headSet((char)toElement,inclusive);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfShort)set).headSet((Short)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfShort)set).headSet((short)toElement,inclusive);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfInt)set).headSet((Integer)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfInt)set).headSet((int)toElement,inclusive);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfLong)set).headSet((Long)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfLong)set).headSet((long)toElement,inclusive);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfFloat)set).headSet((Float)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfFloat)set).headSet((float)toElement,inclusive);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfDouble)set).headSet((Double)toElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfDouble)set).headSet((double)toElement,inclusive);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniNavigableSet.OfRef<Object>)set).headSet(toElement,inclusive);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  @SuppressWarnings("unchecked")
  default SET callTailSet(Object fromElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType) {
	  DataType dataType=this.getDataType();
	  fromElement=dataType.convertValUnchecked(inputType, fromElement);
	  SET set=this.getCollection();

	  switch(dataType) {
	  case BOOLEAN:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfBoolean)set).tailSet((Boolean)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfBoolean)set).tailSet( (boolean)fromElement,inclusive);
		  }
	  case BYTE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfByte)set).tailSet((Byte)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfByte)set).tailSet((byte)fromElement,inclusive);
		  }
	  case CHAR:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfChar)set).tailSet((Character)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfChar)set).tailSet((char)fromElement,inclusive);
		  }
	  case SHORT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfShort)set).tailSet((Short)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfShort)set).tailSet((short)fromElement,inclusive);
		  }
	  case INT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfInt)set).tailSet((Integer)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfInt)set).tailSet((int)fromElement,inclusive);
		  }
	  case LONG:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfLong)set).tailSet((Long)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfLong)set).tailSet((long)fromElement,inclusive);
		  }
	  case FLOAT:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfFloat)set).tailSet((Float)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfFloat)set).tailSet((float)fromElement,inclusive);
		  }
	  case DOUBLE:
		  if(functionCallType.boxed) {
			  return (SET)((OmniNavigableSet.OfDouble)set).tailSet((Double)fromElement,inclusive);
		  }else {
			  return (SET)((OmniNavigableSet.OfDouble)set).tailSet((double)fromElement,inclusive);
		  }
	  case REF:
		  if(functionCallType.boxed) {
			  throw DataType.cannotBeBoxed();
		  }else {
			  return (SET)((OmniNavigableSet.OfRef<Object>)set).tailSet(fromElement,inclusive);
		  }
	  default:
		  throw dataType.invalid();
	  }
  }
  default MonitoredNavigableSet<SET,E> subSet(Object fromElement,boolean fromInclusive,Object toElement,boolean toInclusive,DataType inputType,FunctionCallType functionCallType){
	  return subSetHelper(this.callSubSet(fromElement, fromInclusive, toElement, toInclusive,inputType,functionCallType),fromElement,fromInclusive,toElement,toInclusive,inputType,functionCallType);
  }

  default MonitoredNavigableSet<SET,E> headSet(Object toElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType){
	  return headSetHelper(this.callHeadSet(toElement, inclusive,inputType,functionCallType),toElement,inclusive,inputType,functionCallType);
  }
  default MonitoredNavigableSet<SET,E> tailSet(Object fromElement,boolean inclusive,DataType inputType,FunctionCallType functionCallType){
	  return tailSetHelper(this.callTailSet(fromElement, inclusive,inputType,functionCallType),fromElement,inclusive,inputType,functionCallType);
  }
  default MonitoredNavigableSet<SET,E> subSet(Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return subSetHelper(this.callSubSet(fromElement, toElement, inputType, functionCallType),fromElement,true,toElement,true,inputType,functionCallType);
  }

  default MonitoredNavigableSet<SET,E> headSet(Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return headSetHelper(this.callHeadSet(toElement, inputType, functionCallType),toElement,true,inputType,functionCallType);

  }
  default MonitoredNavigableSet<SET,E> tailSet(Object fromElement,DataType inputType,FunctionCallType functionCallType){
	  return tailSetHelper(this.callTailSet(fromElement, inputType, functionCallType),fromElement,true,inputType,functionCallType);
  }
  
  
  default MonitoredNavigableSet<SET,E> subSetHelper(SET subSet,Object fromElement,Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return subSetHelper(subSet,fromElement,true,toElement,true,inputType,functionCallType);
  }

  default MonitoredNavigableSet<SET,E> headSetHelper(SET headSet,Object toElement,DataType inputType,FunctionCallType functionCallType){
	  return headSetHelper(headSet,toElement,true,inputType,functionCallType);
  }

  default MonitoredNavigableSet<SET,E> tailSetHelper(SET  tailSet,Object fromElement,DataType inputType,FunctionCallType functionCallType){
	  return tailSetHelper(tailSet,fromElement,true,inputType,functionCallType);
  }
  

  
}
