package omni.impl;

import java.util.Set;
import omni.util.TypeUtil;

public enum QueryInputVal{
  ObjectNull ,
  BoxedBooleanNull ,
  BoxedByteNull ,
  BoxedCharacterNull ,
  BoxedShortNull ,
  BoxedIntegerNull ,
  BoxedLongNull ,
  BoxedFloatNull ,
  BoxedDoubleNull ,
  
  PrimitiveBooleanFalse ,
  BoxedBooleanFalse ,
  ObjectBooleanFalse ,
  PrimitiveBooleanTrue ,
  BoxedBooleanTrue ,
  ObjectBooleanTrue ,
  
  PrimitiveBytePos0 ,
  PrimitiveCharPos0 ,
  PrimitiveShortPos0 ,
  PrimitiveIntPos0 ,
  PrimitiveLongPos0 ,
  PrimitiveFloatPos0 ,
  PrimitiveDoublePos0 ,

  BoxedBytePos0 ,
  BoxedCharPos0 ,
  BoxedShortPos0 ,
  BoxedIntPos0 ,
  BoxedLongPos0 ,
  BoxedFloatPos0 ,
  BoxedDoublePos0 ,

  ObjectBytePos0 ,
  ObjectCharPos0 ,
  ObjectShortPos0 ,
  ObjectIntPos0 ,
  ObjectLongPos0 ,
  ObjectFloatPos0 ,
  ObjectDoublePos0 ,
  
  PrimitiveByteNeg0 ,
  PrimitiveCharNeg0 ,
  PrimitiveShortNeg0 ,
  PrimitiveIntNeg0 ,
  PrimitiveLongNeg0 ,
  PrimitiveFloatNeg0 ,
  PrimitiveDoubleNeg0 ,

  BoxedByteNeg0 ,
  BoxedCharNeg0 ,
  BoxedShortNeg0 ,
  BoxedIntNeg0 ,
  BoxedLongNeg0 ,
  BoxedFloatNeg0 ,
  BoxedDoubleNeg0 ,

  ObjectByteNeg0 ,
  ObjectCharNeg0 ,
  ObjectShortNeg0 ,
  ObjectIntNeg0 ,
  ObjectLongNeg0 ,
  ObjectFloatNeg0 ,
  ObjectDoubleNeg0 ,
  
  PrimitiveBytePos1 ,
  PrimitiveCharPos1 ,
  PrimitiveShortPos1 ,
  PrimitiveIntPos1 ,
  PrimitiveLongPos1 ,
  PrimitiveFloatPos1 ,
  PrimitiveDoublePos1 ,

  BoxedBytePos1 ,
  BoxedCharPos1 ,
  BoxedShortPos1 ,
  BoxedIntPos1 ,
  BoxedLongPos1 ,
  BoxedFloatPos1 ,
  BoxedDoublePos1 ,

  ObjectBytePos1 ,
  ObjectCharPos1 ,
  ObjectShortPos1 ,
  ObjectIntPos1 ,
  ObjectLongPos1 ,
  ObjectFloatPos1 ,
  ObjectDoublePos1 ,
  
  PrimitiveByteNeg1 ,
  PrimitiveShortNeg1 ,
  PrimitiveIntNeg1 ,
  PrimitiveLongNeg1 ,
  PrimitiveFloatNeg1 ,
  PrimitiveDoubleNeg1 ,

  BoxedByteNeg1 ,
  BoxedShortNeg1 ,
  BoxedIntNeg1 ,
  BoxedLongNeg1 ,
  BoxedFloatNeg1 ,
  BoxedDoubleNeg1 ,

  ObjectByteNeg1 ,
  ObjectShortNeg1 ,
  ObjectIntNeg1 ,
  ObjectLongNeg1 ,
  ObjectFloatNeg1 ,
  ObjectDoubleNeg1 ,
  
  PrimitiveBytePos2 ,
  PrimitiveCharPos2 ,
  PrimitiveShortPos2 ,
  PrimitiveIntPos2 ,
  PrimitiveLongPos2 ,
  PrimitiveFloatPos2 ,
  PrimitiveDoublePos2 ,

  BoxedBytePos2 ,
  BoxedCharPos2 ,
  BoxedShortPos2 ,
  BoxedIntPos2 ,
  BoxedLongPos2 ,
  BoxedFloatPos2 ,
  BoxedDoublePos2 ,

  ObjectBytePos2 ,
  ObjectCharPos2 ,
  ObjectShortPos2 ,
  ObjectIntPos2 ,
  ObjectLongPos2 ,
  ObjectFloatPos2 ,
  ObjectDoublePos2 ,
  
  PrimitiveCharMaxBytePlus1 ,
  PrimitiveShortMaxBytePlus1 ,
  PrimitiveIntMaxBytePlus1 ,
  PrimitiveLongMaxBytePlus1 ,
  PrimitiveFloatMaxBytePlus1 ,
  PrimitiveDoubleMaxBytePlus1 ,

  BoxedCharMaxBytePlus1 ,
  BoxedShortMaxBytePlus1 ,
  BoxedIntMaxBytePlus1 ,
  BoxedLongMaxBytePlus1 ,
  BoxedFloatMaxBytePlus1 ,
  BoxedDoubleMaxBytePlus1 ,

  ObjectCharMaxBytePlus1 ,
  ObjectShortMaxBytePlus1 ,
  ObjectIntMaxBytePlus1 ,
  ObjectLongMaxBytePlus1 ,
  ObjectFloatMaxBytePlus1 ,
  ObjectDoubleMaxBytePlus1 ,
  
  PrimitiveShortMinByteMinus1 ,
  PrimitiveIntMinByteMinus1 ,
  PrimitiveLongMinByteMinus1 ,
  PrimitiveFloatMinByteMinus1 ,
  PrimitiveDoubleMinByteMinus1 ,

  BoxedShortMinByteMinus1 ,
  BoxedIntMinByteMinus1 ,
  BoxedLongMinByteMinus1 ,
  BoxedFloatMinByteMinus1 ,
  BoxedDoubleMinByteMinus1 ,

  ObjectShortMinByteMinus1 ,
  ObjectIntMinByteMinus1 ,
  ObjectLongMinByteMinus1 ,
  ObjectFloatMinByteMinus1 ,
  ObjectDoubleMinByteMinus1 ,
  
  PrimitiveIntMaxCharPlus1 ,
  PrimitiveLongMaxCharPlus1 ,
  PrimitiveFloatMaxCharPlus1 ,
  PrimitiveDoubleMaxCharPlus1 ,

  BoxedIntMaxCharPlus1 ,
  BoxedLongMaxCharPlus1 ,
  BoxedFloatMaxCharPlus1 ,
  BoxedDoubleMaxCharPlus1 ,

  ObjectIntMaxCharPlus1 ,
  ObjectLongMaxCharPlus1 ,
  ObjectFloatMaxCharPlus1 ,
  ObjectDoubleMaxCharPlus1 ,
  
  PrimitiveCharMaxShortPlus1 ,
  PrimitiveIntMaxShortPlus1 ,
  PrimitiveLongMaxShortPlus1 ,
  PrimitiveFloatMaxShortPlus1 ,
  PrimitiveDoubleMaxShortPlus1 ,

  BoxedCharMaxShortPlus1 ,
  BoxedIntMaxShortPlus1 ,
  BoxedLongMaxShortPlus1 ,
  BoxedFloatMaxShortPlus1 ,
  BoxedDoubleMaxShortPlus1 ,

  ObjectCharMaxShortPlus1 ,
  ObjectIntMaxShortPlus1 ,
  ObjectLongMaxShortPlus1 ,
  ObjectFloatMaxShortPlus1 ,
  ObjectDoubleMaxShortPlus1 ,
  
  PrimitiveIntMinShortMinus1 ,
  PrimitiveLongMinShortMinus1 ,
  PrimitiveFloatMinShortMinus1 ,
  PrimitiveDoubleMinShortMinus1 ,

  BoxedIntMinShortMinus1 ,
  BoxedLongMinShortMinus1 ,
  BoxedFloatMinShortMinus1 ,
  BoxedDoubleMinShortMinus1 ,

  ObjectIntMinShortMinus1 ,
  ObjectLongMinShortMinus1 ,
  ObjectFloatMinShortMinus1 ,
  ObjectDoubleMinShortMinus1 ,
  
  PrimitiveIntMaxSafeIntPlus1 ,
  PrimitiveLongMaxSafeIntPlus1 ,
  PrimitiveFloatMaxSafeIntPlus1 ,
  PrimitiveDoubleMaxSafeIntPlus1 ,

  BoxedIntMaxSafeIntPlus1 ,
  BoxedLongMaxSafeIntPlus1 ,
  BoxedFloatMaxSafeIntPlus1 ,
  BoxedDoubleMaxSafeIntPlus1 ,

  ObjectIntMaxSafeIntPlus1 ,
  ObjectLongMaxSafeIntPlus1 ,
  ObjectFloatMaxSafeIntPlus1 ,
  ObjectDoubleMaxSafeIntPlus1 ,
  
  PrimitiveIntMinSafeIntMinus1 ,
  PrimitiveLongMinSafeIntMinus1 ,
  PrimitiveFloatMinSafeIntMinus1 ,
  PrimitiveDoubleMinSafeIntMinus1 ,

  BoxedIntMinSafeIntMinus1 ,
  BoxedLongMinSafeIntMinus1 ,
  BoxedFloatMinSafeIntMinus1 ,
  BoxedDoubleMinSafeIntMinus1 ,

  ObjectIntMinSafeIntMinus1 ,
  ObjectLongMinSafeIntMinus1 ,
  ObjectFloatMinSafeIntMinus1 ,
  ObjectDoubleMinSafeIntMinus1 ,
  
  PrimitiveLongMaxIntPlus1 ,
  PrimitiveFloatMaxIntPlus1 ,
  PrimitiveDoubleMaxIntPlus1 ,

  BoxedLongMaxIntPlus1 ,
  BoxedFloatMaxIntPlus1 ,
  BoxedDoubleMaxIntPlus1 ,

  ObjectLongMaxIntPlus1 ,
  ObjectFloatMaxIntPlus1 ,
  ObjectDoubleMaxIntPlus1 ,
  
  PrimitiveLongMinIntMinus1 ,
  PrimitiveFloatMinIntMinus1 ,
  PrimitiveDoubleMinIntMinus1 ,

  BoxedLongMinIntMinus1 ,
  BoxedFloatMinIntMinus1 ,
  BoxedDoubleMinIntMinus1 ,

  ObjectLongMinIntMinus1 ,
  ObjectFloatMinIntMinus1 ,
  ObjectDoubleMinIntMinus1 ,
  
  PrimitiveLongMaxSafeLongPlus1 ,
  PrimitiveFloatMaxSafeLongPlus1 ,
  PrimitiveDoubleMaxSafeLongPlus1 ,

  BoxedLongMaxSafeLongPlus1 ,
  BoxedFloatMaxSafeLongPlus1 ,
  BoxedDoubleMaxSafeLongPlus1 ,

  ObjectLongMaxSafeLongPlus1 ,
  ObjectFloatMaxSafeLongPlus1 ,
  ObjectDoubleMaxSafeLongPlus1 ,
  
  PrimitiveLongMinSafeLongMinus1 ,
  PrimitiveFloatMinSafeLongMinus1 ,
  PrimitiveDoubleMinSafeLongMinus1 ,

  BoxedLongMinSafeLongMinus1 ,
  BoxedFloatMinSafeLongMinus1 ,
  BoxedDoubleMinSafeLongMinus1 ,

  ObjectLongMinSafeLongMinus1 ,
  ObjectFloatMinSafeLongMinus1 ,
  ObjectDoubleMinSafeLongMinus1 ,
  
  PrimitiveFloatMaxLongPlus1 ,
  PrimitiveDoubleMaxLongPlus1 ,

  BoxedFloatMaxLongPlus1 ,
  BoxedDoubleMaxLongPlus1 ,

  ObjectFloatMaxLongPlus1 ,
  ObjectDoubleMaxLongPlus1 ,
  
  PrimitiveFloatMinLongMinus1 ,
  PrimitiveDoubleMinLongMinus1 ,

  BoxedFloatMinLongMinus1 ,
  BoxedDoubleMinLongMinus1 ,

  ObjectFloatMinLongMinus1 ,
  ObjectDoubleMinLongMinus1 ,
  
  PrimitiveFloatMaxFloat ,
  PrimitiveDoubleMaxFloat ,

  BoxedFloatMaxFloat ,
  BoxedDoubleMaxFloat ,

  ObjectFloatMaxFloat ,
  ObjectDoubleMaxFloat ,
  
  PrimitiveFloatMinFloat ,
  PrimitiveDoubleMinFloat ,

  BoxedFloatMinFloat ,
  BoxedDoubleMinFloat ,

  ObjectFloatMinFloat ,
  ObjectDoubleMinFloat ,
  
  PrimitiveDoubleMaxDouble ,

  BoxedDoubleMaxDouble ,

  ObjectDoubleMaxDouble ,
  
  PrimitiveDoubleMinDouble ,

  BoxedDoubleMinDouble ,

  ObjectDoubleMinDouble ,
  
  PrimitiveFloatPosInfinity ,
  PrimitiveDoublePosInfinity ,

  BoxedFloatPosInfinity ,
  BoxedDoublePosInfinity ,

  ObjectFloatPosInfinity ,
  ObjectDoublePosInfinity ,
  
  PrimitiveFloatNegInfinity ,
  PrimitiveDoubleNegInfinity ,

  BoxedFloatNegInfinity ,
  BoxedDoubleNegInfinity ,

  ObjectFloatNegInfinity,
  ObjectDoubleNegInfinity,
  
  PrimitiveFloatNaN,
  PrimitiveDoubleNaN,

  BoxedFloatNaN,
  BoxedDoubleNaN,

  ObjectFloatNaN,
  ObjectDoubleNaN;
  
  public final Object val;
  public final Object notEqualsVal;
  public final Set<DataType> dataTypesWhichCanContain;
  
  QueryInputVal(){
    this.val=initVal(this);
    this.notEqualsVal=initNotEqualsVal(this);
    this.dataTypesWhichCanContain=initDataTypesWhichCanContain(this);
  }
  private static Set<DataType> initDataTypesWhichCanContain(QueryInputVal inputVal){
    switch(inputVal) {
    case BoxedBooleanFalse:
    case BoxedBooleanTrue:
    case BoxedByteNeg0:
    case BoxedBytePos0:
    case BoxedBytePos1:
    case BoxedCharNeg0:
    case BoxedCharPos0:
    case BoxedCharPos1:
    case BoxedDoubleNeg0:
    case BoxedDoublePos0:
    case BoxedDoublePos1:
    case BoxedFloatNeg0:
    case BoxedFloatPos0:
    case BoxedFloatPos1:
    case BoxedIntNeg0:
    case BoxedIntPos0:
    case BoxedIntPos1:
    case BoxedLongNeg0:
    case BoxedLongPos0:
    case BoxedLongPos1:
    case BoxedShortNeg0:
    case BoxedShortPos0:
    case BoxedShortPos1:
    case ObjectByteNeg0:
    case ObjectBytePos0:
    case ObjectBytePos1:
    case ObjectCharNeg0:
    case ObjectCharPos0:
    case ObjectCharPos1:
    case ObjectDoubleNeg0:
    case ObjectFloatNeg0:
    case ObjectFloatPos0:
    case ObjectFloatPos1:
    case ObjectIntNeg0:
    case ObjectIntPos0:
    case ObjectIntPos1:
    case ObjectLongNeg0:
    case ObjectLongPos0:
    case ObjectLongPos1:
    case ObjectDoublePos0:
    case ObjectDoublePos1:
    case ObjectShortNeg0:
    case ObjectShortPos0:
    case ObjectShortPos1:
    case PrimitiveByteNeg0:
    case PrimitiveBooleanFalse:
    case PrimitiveBooleanTrue:
    case ObjectBooleanFalse:
    case ObjectBooleanTrue:
    case PrimitiveCharNeg0:
    case PrimitiveBytePos0:
    case PrimitiveBytePos1:
    case PrimitiveCharPos0:
    case PrimitiveCharPos1:
    case PrimitiveDoubleNeg0:
    case PrimitiveDoublePos0:
    case PrimitiveDoublePos1:
    case PrimitiveFloatNeg0:
    case PrimitiveFloatPos0:
    case PrimitiveFloatPos1:
    case PrimitiveIntNeg0:
    case PrimitiveIntPos0:
    case PrimitiveIntPos1:
    case PrimitiveLongNeg0:
    case PrimitiveLongPos0:
    case PrimitiveLongPos1:
    case PrimitiveShortNeg0:
    case PrimitiveShortPos0:
    case PrimitiveShortPos1:
      return Set.of(DataType.values());
    case BoxedBooleanNull:
    case BoxedByteNull:
    case BoxedCharacterNull:
    case BoxedDoubleNull:
    case BoxedFloatNull:
    case BoxedIntegerNull:
    case BoxedLongNull:  
    case BoxedShortNull:
    case ObjectNull:
      return Set.of(DataType.Object);
    case BoxedBytePos2:
    case BoxedCharPos2:
    case BoxedDoublePos2:
    case BoxedFloatPos2:
    case BoxedIntPos2:
    case BoxedLongPos2:
    case BoxedShortPos2:
    case ObjectBytePos2:
    case ObjectCharPos2:
    case ObjectDoublePos2:
    case ObjectFloatPos2:
    case ObjectIntPos2:
    case ObjectLongPos2:
    case PrimitiveCharPos2:
    case ObjectShortPos2:
    case PrimitiveBytePos2:
    case PrimitiveDoublePos2:
    case PrimitiveFloatPos2:
    case PrimitiveIntPos2:
    case PrimitiveLongPos2:
    case PrimitiveShortPos2:
      return Set.of(DataType.Object,DataType.Byte,DataType.Char,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedByteNeg1:
    case BoxedDoubleNeg1:
    case BoxedFloatNeg1:
    case BoxedIntNeg1:
    case BoxedLongNeg1:
    case BoxedShortNeg1:
    case ObjectByteNeg1:
    case ObjectDoubleNeg1:
    case ObjectFloatNeg1:
    case ObjectIntNeg1:
    case ObjectLongNeg1:
    case ObjectShortNeg1:
    case PrimitiveByteNeg1:
    case PrimitiveDoubleNeg1:
    case PrimitiveFloatNeg1:
    case PrimitiveIntNeg1:
    case PrimitiveLongNeg1:
    case PrimitiveShortNeg1:
      return Set.of(DataType.Object,DataType.Byte,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedCharMaxBytePlus1:
    case ObjectCharMaxBytePlus1:
    case PrimitiveCharMaxBytePlus1:
    case BoxedDoubleMaxBytePlus1:
    case BoxedFloatMaxBytePlus1:
    case BoxedIntMaxBytePlus1:
    case BoxedLongMaxBytePlus1:
    case BoxedShortMaxBytePlus1:
    case ObjectDoubleMaxBytePlus1:
    case ObjectFloatMaxBytePlus1:
    case ObjectIntMaxBytePlus1:
    case ObjectLongMaxBytePlus1:
    case ObjectShortMaxBytePlus1:
    case PrimitiveDoubleMaxBytePlus1:
    case PrimitiveFloatMaxBytePlus1:
    case PrimitiveIntMaxBytePlus1:
    case PrimitiveLongMaxBytePlus1:
    case PrimitiveShortMaxBytePlus1:
      return Set.of(DataType.Object,DataType.Char,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedDoubleMinByteMinus1:
    case BoxedFloatMinByteMinus1:
    case BoxedIntMinByteMinus1:
    case BoxedLongMinByteMinus1:
    case BoxedShortMinByteMinus1:
    case ObjectDoubleMinByteMinus1:
    case ObjectFloatMinByteMinus1:
    case ObjectIntMinByteMinus1:
    case ObjectLongMinByteMinus1:
    case ObjectShortMinByteMinus1:
    case PrimitiveDoubleMinByteMinus1:
    case PrimitiveFloatMinByteMinus1:
    case PrimitiveIntMinByteMinus1:
    case PrimitiveLongMinByteMinus1:
    case PrimitiveShortMinByteMinus1:
      return Set.of(DataType.Object,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedCharMaxShortPlus1:
    case ObjectCharMaxShortPlus1:
    case PrimitiveCharMaxShortPlus1:
    case BoxedIntMaxShortPlus1:
    case ObjectIntMaxShortPlus1:
    case PrimitiveIntMaxShortPlus1:
    case BoxedLongMaxShortPlus1:
    case ObjectLongMaxShortPlus1:
    case PrimitiveLongMaxShortPlus1:
    case BoxedFloatMaxShortPlus1:
    case ObjectFloatMaxShortPlus1:
    case PrimitiveFloatMaxShortPlus1:
    case BoxedDoubleMaxShortPlus1:
    case ObjectDoubleMaxShortPlus1:
    case PrimitiveDoubleMaxShortPlus1:
      return Set.of(DataType.Object,DataType.Char,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedDoubleMaxCharPlus1:
    case BoxedFloatMaxCharPlus1:
    case BoxedIntMaxCharPlus1:
    case BoxedLongMaxCharPlus1:
    case ObjectDoubleMaxCharPlus1:
    case ObjectFloatMaxCharPlus1:
    case ObjectIntMaxCharPlus1:
    case ObjectLongMaxCharPlus1:
    case PrimitiveDoubleMaxCharPlus1:
    case PrimitiveFloatMaxCharPlus1:
    case PrimitiveIntMaxCharPlus1:
    case PrimitiveLongMaxCharPlus1:
      return Set.of(DataType.Object,DataType.Int,DataType.Long,DataType.Float,DataType.Double);
    case BoxedDoubleMaxDouble:
    case ObjectDoubleMaxDouble:
    case PrimitiveDoubleMaxDouble:
    case BoxedDoubleMinDouble:
    case ObjectDoubleMinDouble:
    case PrimitiveDoubleMinDouble:
    case BoxedDoubleMinLongMinus1:
    case BoxedDoubleMaxLongPlus1:
    case ObjectDoubleMinLongMinus1:
    case ObjectDoubleMaxLongPlus1:
    case PrimitiveDoubleMinLongMinus1:
    case PrimitiveDoubleMaxLongPlus1:
      return Set.of(DataType.Object,DataType.Double);
    case BoxedDoubleMaxFloat:
    case ObjectDoubleMaxFloat:
    case PrimitiveDoubleMaxFloat:
    case BoxedDoubleMinFloat:
    case ObjectDoubleMinFloat:
    case PrimitiveDoubleMinFloat:
    case BoxedDoubleNaN:
    case BoxedDoubleNegInfinity:
    case BoxedDoublePosInfinity:
    case BoxedFloatMaxFloat:
    case BoxedFloatMinFloat:
    case BoxedFloatNaN:
    case BoxedFloatNegInfinity:
    case BoxedFloatPosInfinity:
    case ObjectDoubleNaN:
    case ObjectDoubleNegInfinity:
    case ObjectDoublePosInfinity:
    case ObjectFloatMaxFloat:
    case PrimitiveFloatNaN:
    case PrimitiveDoubleNaN:
    case ObjectFloatNaN:
    case ObjectFloatMinFloat:
    case ObjectFloatNegInfinity:
    case ObjectFloatPosInfinity:
    case PrimitiveDoubleNegInfinity:
    case PrimitiveDoublePosInfinity:
    case PrimitiveFloatMaxFloat:
    case PrimitiveFloatMinFloat:
    case PrimitiveFloatNegInfinity:
    case PrimitiveFloatPosInfinity:
      return Set.of(DataType.Object,DataType.Float,DataType.Double);
    case BoxedDoubleMaxIntPlus1:
    case BoxedDoubleMinIntMinus1:
    case BoxedLongMaxIntPlus1:
    case BoxedLongMinIntMinus1:
    case ObjectDoubleMaxIntPlus1:
    case ObjectDoubleMinIntMinus1:
    case ObjectLongMaxIntPlus1:
    case ObjectLongMinIntMinus1:
    case PrimitiveDoubleMaxIntPlus1:
    case PrimitiveDoubleMinIntMinus1:
    case PrimitiveLongMaxIntPlus1:
    case PrimitiveLongMinIntMinus1:
      return Set.of(DataType.Object,DataType.Long,DataType.Double);
    case BoxedDoubleMaxSafeIntPlus1:
    case BoxedDoubleMinSafeIntMinus1:
    case BoxedLongMaxSafeIntPlus1:
    case BoxedLongMinSafeIntMinus1:
    case BoxedIntMaxSafeIntPlus1:
    case BoxedIntMinSafeIntMinus1:
    case ObjectDoubleMaxSafeIntPlus1:
    case ObjectDoubleMinSafeIntMinus1:
    case ObjectLongMaxSafeIntPlus1:
    case ObjectLongMinSafeIntMinus1:
    case ObjectIntMaxSafeIntPlus1:
    case ObjectIntMinSafeIntMinus1:
    case PrimitiveDoubleMaxSafeIntPlus1:
    case PrimitiveDoubleMinSafeIntMinus1:
    case PrimitiveLongMaxSafeIntPlus1:
    case PrimitiveLongMinSafeIntMinus1:
    case PrimitiveIntMaxSafeIntPlus1:
    case PrimitiveIntMinSafeIntMinus1:
      return Set.of(DataType.Object,DataType.Int,DataType.Long,DataType.Double);
    case BoxedDoubleMaxSafeLongPlus1:
      break;
    case BoxedDoubleMinSafeLongMinus1:
      break;
    case BoxedDoubleMinShortMinus1:
      break;
    case BoxedFloatMaxIntPlus1:
      break;
    case BoxedFloatMaxLongPlus1:
      break;
    case BoxedFloatMaxSafeIntPlus1:
      break;
    case BoxedFloatMaxSafeLongPlus1:
      break;
    case BoxedFloatMinIntMinus1:
      break;
    case BoxedFloatMinLongMinus1:
      break;
    case BoxedFloatMinSafeIntMinus1:
      break;
    case BoxedFloatMinSafeLongMinus1:
      break;
    case BoxedFloatMinShortMinus1:
      break;
    case BoxedIntMinShortMinus1:
      break;
    case BoxedLongMaxSafeLongPlus1:
      break;
    case BoxedLongMinSafeLongMinus1:
      break;
    case BoxedLongMinShortMinus1:
      break;
    case ObjectDoubleMaxSafeLongPlus1:
      break;
    case ObjectDoubleMinSafeLongMinus1:
      break;
    case ObjectDoubleMinShortMinus1:
      break;
    case ObjectFloatMaxIntPlus1:
      break;
    case ObjectFloatMaxLongPlus1:
      break;
    case ObjectFloatMaxSafeIntPlus1:
      break;
    case ObjectFloatMaxSafeLongPlus1:
      break;
    case ObjectFloatMinIntMinus1:
      break;
    case ObjectFloatMinLongMinus1:
      break;
    case ObjectFloatMinSafeIntMinus1:
      break;
    case ObjectFloatMinSafeLongMinus1:
      break;
    case ObjectFloatMinShortMinus1:
      break;
    case ObjectIntMinShortMinus1:
      break;
    case ObjectLongMaxSafeLongPlus1:
      break;
    case ObjectLongMinSafeLongMinus1:
      break;
    case ObjectLongMinShortMinus1:
      break;
    case PrimitiveDoubleMaxSafeLongPlus1:
      break;
    case PrimitiveDoubleMinSafeLongMinus1:
      break;
    case PrimitiveDoubleMinShortMinus1:
      break;
    case PrimitiveFloatMaxIntPlus1:
      break;
    case PrimitiveFloatMaxLongPlus1:
      break;
    case PrimitiveFloatMaxSafeIntPlus1:
      break;
    case PrimitiveFloatMaxSafeLongPlus1:
      break;
    case PrimitiveFloatMinIntMinus1:
      break;
    case PrimitiveFloatMinLongMinus1:
      break;
    case PrimitiveFloatMinSafeIntMinus1:
      break;
    case PrimitiveFloatMinSafeLongMinus1:
      break;
    case PrimitiveFloatMinShortMinus1:
      break;
    case PrimitiveIntMinShortMinus1:
      break;
    case PrimitiveLongMaxSafeLongPlus1:
      break;
    case PrimitiveLongMinSafeLongMinus1:
      break;
    case PrimitiveLongMinShortMinus1:
      break;
    default:
      break;
    
    }
    //TODO
    throw new UnsupportedOperationException();
  }
  
  private static Object initNotEqualsVal(QueryInputVal inputVal) {
    switch(inputVal) {
    case BoxedBooleanNull:
    case BoxedBooleanTrue:
    case BoxedByteNeg1:
    case BoxedByteNull:
    case BoxedBytePos1:
    case BoxedBytePos2:
    case BoxedCharMaxBytePlus1:
    case BoxedCharMaxShortPlus1:
    case BoxedCharPos1:
    case BoxedCharPos2:
    case BoxedCharacterNull:
    case BoxedDoubleMaxBytePlus1:
    case BoxedDoubleMaxCharPlus1:
    case BoxedDoubleMaxDouble:
    case BoxedDoubleMaxFloat:
    case BoxedDoubleMaxIntPlus1:
    case BoxedDoubleMaxLongPlus1:
    case BoxedDoubleMaxSafeIntPlus1:
    case BoxedDoubleMaxSafeLongPlus1:
    case BoxedDoubleMaxShortPlus1:
    case BoxedDoubleMinByteMinus1:
    case BoxedDoubleMinDouble:
    case BoxedDoubleMinFloat:
    case BoxedDoubleMinIntMinus1:
    case BoxedDoubleMinLongMinus1:
    case BoxedDoubleMinSafeIntMinus1:
    case BoxedDoubleMinSafeLongMinus1:
    case BoxedDoubleMinShortMinus1:
    case BoxedDoubleNaN:
    case BoxedDoubleNeg1:
    case BoxedDoubleNegInfinity:
    case BoxedDoubleNull:
    case BoxedDoublePos1:
    case BoxedDoublePos2:
    case BoxedDoublePosInfinity:
    case BoxedFloatMaxBytePlus1:
    case BoxedFloatMaxCharPlus1:
    case BoxedFloatMaxFloat:
    case BoxedFloatMaxIntPlus1:
    case BoxedFloatMaxLongPlus1:
    case BoxedFloatMaxSafeIntPlus1:
    case BoxedFloatMaxSafeLongPlus1:
    case BoxedFloatMaxShortPlus1:
    case BoxedFloatMinByteMinus1:
    case BoxedFloatMinFloat:
    case BoxedFloatMinIntMinus1:
    case BoxedFloatMinLongMinus1:
    case BoxedFloatMinSafeIntMinus1:
    case BoxedFloatMinSafeLongMinus1:
    case BoxedFloatMinShortMinus1:
    case BoxedFloatNaN:
    case BoxedFloatNeg1:
    case BoxedFloatNegInfinity:
    case BoxedFloatNull:
    case BoxedFloatPos1:
    case BoxedFloatPos2:
    case BoxedFloatPosInfinity:
    case BoxedIntMaxBytePlus1:
    case BoxedIntMaxCharPlus1:
    case BoxedIntMaxSafeIntPlus1:
    case BoxedIntMaxShortPlus1:
    case BoxedIntMinByteMinus1:
    case BoxedIntMinSafeIntMinus1:
    case BoxedIntMinShortMinus1:
    case BoxedIntNeg1:
    case BoxedIntPos1:
    case BoxedIntPos2:
    case BoxedIntegerNull:
    case BoxedLongMaxBytePlus1:
    case BoxedLongMaxCharPlus1:
    case BoxedLongMaxIntPlus1:
    case BoxedLongMaxSafeIntPlus1:
    case BoxedLongMaxSafeLongPlus1:
    case BoxedLongMaxShortPlus1:
    case BoxedLongMinByteMinus1:
    case BoxedLongMinIntMinus1:
    case BoxedLongMinSafeIntMinus1:
    case BoxedLongMinSafeLongMinus1:
    case BoxedLongMinShortMinus1:
    case BoxedLongNeg1:
    case BoxedLongNull:
    case BoxedLongPos1:
    case BoxedLongPos2:
    case BoxedShortMaxBytePlus1:
    case BoxedShortMinByteMinus1:
    case BoxedShortNeg1:
    case BoxedShortNull:
    case BoxedShortPos1:
    case BoxedShortPos2:
    case ObjectBooleanTrue:
    case ObjectByteNeg1:
    case ObjectBytePos1:
    case ObjectBytePos2:
    case ObjectCharMaxBytePlus1:
    case ObjectCharMaxShortPlus1:
    case ObjectCharPos1:
    case ObjectCharPos2:
    case ObjectDoubleMaxBytePlus1:
    case ObjectDoubleMaxCharPlus1:
    case ObjectDoubleMaxDouble:
    case ObjectDoubleMaxFloat:
    case ObjectDoubleMaxIntPlus1:
    case ObjectDoubleMaxLongPlus1:
    case ObjectDoubleMaxSafeIntPlus1:
    case ObjectDoubleMaxSafeLongPlus1:
    case ObjectDoubleMaxShortPlus1:
    case ObjectDoubleMinByteMinus1:
    case ObjectDoubleMinDouble:
    case ObjectDoubleMinFloat:
    case ObjectDoubleMinIntMinus1:
    case ObjectDoubleMinLongMinus1:
    case ObjectDoubleMinSafeIntMinus1:
    case ObjectDoubleMinSafeLongMinus1:
    case ObjectDoubleMinShortMinus1:
    case ObjectDoubleNaN:
    case ObjectDoubleNeg1:
    case ObjectDoubleNegInfinity:
    case ObjectDoublePos1:
    case ObjectDoublePos2:
    case ObjectDoublePosInfinity:
    case ObjectFloatMaxBytePlus1:
    case ObjectFloatMaxCharPlus1:
    case ObjectFloatMaxFloat:
    case ObjectFloatMaxIntPlus1:
    case ObjectFloatMaxLongPlus1:
    case ObjectFloatMaxSafeIntPlus1:
    case ObjectFloatMaxSafeLongPlus1:
    case ObjectFloatMaxShortPlus1:
    case ObjectFloatMinByteMinus1:
    case ObjectFloatMinFloat:
    case ObjectFloatMinIntMinus1:
    case ObjectFloatMinLongMinus1:
    case ObjectFloatMinSafeIntMinus1:
    case ObjectFloatMinSafeLongMinus1:
    case ObjectFloatMinShortMinus1:
    case ObjectFloatNaN:
    case ObjectFloatNeg1:
    case ObjectFloatNegInfinity:
    case ObjectFloatPos1:
    case ObjectFloatPos2:
    case ObjectFloatPosInfinity:
    case ObjectIntMaxBytePlus1:
    case ObjectIntMaxCharPlus1:
    case ObjectIntMaxSafeIntPlus1:
    case ObjectIntMaxShortPlus1:
    case ObjectIntMinByteMinus1:
    case ObjectIntMinSafeIntMinus1:
    case ObjectIntMinShortMinus1:
    case ObjectIntNeg1:
    case ObjectIntPos1:
    case ObjectIntPos2:
    case ObjectLongMaxBytePlus1:
    case ObjectLongMaxCharPlus1:
    case ObjectLongMaxIntPlus1:
    case ObjectLongMaxSafeIntPlus1:
    case ObjectLongMaxSafeLongPlus1:
    case ObjectLongMaxShortPlus1:
    case ObjectLongMinByteMinus1:
    case ObjectLongMinIntMinus1:
    case ObjectLongMinSafeIntMinus1:
    case ObjectLongMinSafeLongMinus1:
    case ObjectLongMinShortMinus1:
    case ObjectLongNeg1:
    case ObjectLongPos1:
    case ObjectLongPos2:
    case ObjectNull:
    case ObjectShortMaxBytePlus1:
    case ObjectShortMinByteMinus1:
    case ObjectShortNeg1:
    case ObjectShortPos1:
    case ObjectShortPos2:
    case PrimitiveBooleanTrue:
    case PrimitiveByteNeg1:    
    case PrimitiveBytePos1:
    case PrimitiveBytePos2:
    case PrimitiveCharMaxBytePlus1:
    case PrimitiveCharMaxShortPlus1:
    case PrimitiveCharPos1:
    case PrimitiveCharPos2:
    case PrimitiveDoubleMaxBytePlus1:
    case PrimitiveDoubleMaxCharPlus1:
    case PrimitiveDoubleMaxDouble:
    case PrimitiveDoubleMaxFloat:
    case PrimitiveDoubleMaxIntPlus1:
    case PrimitiveDoubleMaxLongPlus1:
    case PrimitiveDoubleMaxSafeIntPlus1:
    case PrimitiveDoubleMaxSafeLongPlus1:
    case PrimitiveDoubleMaxShortPlus1:
    case PrimitiveDoubleMinByteMinus1:
    case PrimitiveDoubleMinDouble:
    case PrimitiveDoubleMinFloat:
    case PrimitiveDoubleMinIntMinus1:
    case PrimitiveDoubleMinLongMinus1:
    case PrimitiveDoubleMinSafeIntMinus1:
    case PrimitiveDoubleMinSafeLongMinus1:
    case PrimitiveDoubleMinShortMinus1:
    case PrimitiveDoubleNaN:
    case PrimitiveDoubleNeg1:
    case PrimitiveDoubleNegInfinity:
    case PrimitiveDoublePos1:
    case PrimitiveDoublePos2:
    case PrimitiveDoublePosInfinity:
    case PrimitiveFloatMaxBytePlus1:
    case PrimitiveFloatMaxCharPlus1:
    case PrimitiveFloatMaxFloat:
    case PrimitiveFloatMaxIntPlus1:
    case PrimitiveFloatMaxLongPlus1:
    case PrimitiveFloatMaxSafeIntPlus1:
    case PrimitiveFloatMaxSafeLongPlus1:
    case PrimitiveFloatMaxShortPlus1:
    case PrimitiveFloatMinByteMinus1:
    case PrimitiveFloatMinFloat:
    case PrimitiveFloatMinIntMinus1:
    case PrimitiveFloatMinLongMinus1:
    case PrimitiveFloatMinSafeIntMinus1:
    case PrimitiveFloatMinSafeLongMinus1:
    case PrimitiveFloatMinShortMinus1:
    case PrimitiveFloatNaN:
    case PrimitiveFloatNeg1:
    case PrimitiveFloatNegInfinity:
    case PrimitiveFloatPos1:
    case PrimitiveFloatPos2:
    case PrimitiveFloatPosInfinity:
    case PrimitiveIntMaxBytePlus1:
    case PrimitiveIntMaxCharPlus1:
    case PrimitiveIntMaxSafeIntPlus1:
    case PrimitiveIntMaxShortPlus1:
    case PrimitiveIntMinByteMinus1:
    case PrimitiveIntMinSafeIntMinus1:
    case PrimitiveIntMinShortMinus1:
    case PrimitiveIntNeg1:
    case PrimitiveIntPos1:
    case PrimitiveIntPos2:
    case PrimitiveLongMaxBytePlus1:
    case PrimitiveLongMaxCharPlus1:
    case PrimitiveLongMaxIntPlus1:
    case PrimitiveLongMaxSafeIntPlus1:
    case PrimitiveLongMaxSafeLongPlus1:
    case PrimitiveLongMaxShortPlus1:
    case PrimitiveLongMinByteMinus1:
    case PrimitiveLongMinIntMinus1:
    case PrimitiveLongMinSafeIntMinus1:
    case PrimitiveLongMinSafeLongMinus1:
    case PrimitiveLongMinShortMinus1:
    case PrimitiveLongNeg1:
    case PrimitiveLongPos1:
    case PrimitiveLongPos2:
    case PrimitiveShortMaxBytePlus1:
    case PrimitiveShortMinByteMinus1:
    case PrimitiveShortNeg1:
    case PrimitiveShortPos1:
    case PrimitiveShortPos2:
      return Boolean.FALSE;
    case BoxedBooleanFalse:
    case BoxedBytePos0:
    case BoxedByteNeg0:
    case BoxedCharPos0:
    case BoxedCharNeg0:
    case BoxedDoublePos0:
    case BoxedDoubleNeg0:
    case BoxedFloatPos0:
    case BoxedFloatNeg0:
    case BoxedIntPos0:
    case BoxedIntNeg0:
    case BoxedShortPos0:
    case BoxedShortNeg0:
    case BoxedLongPos0:
    case BoxedLongNeg0:
    case ObjectBooleanFalse:
    case ObjectBytePos0:
    case ObjectByteNeg0:
    case ObjectCharPos0:
    case ObjectCharNeg0:
    case ObjectDoublePos0:
    case ObjectDoubleNeg0:
    case ObjectFloatPos0:
    case ObjectFloatNeg0:
    case ObjectIntPos0:
    case ObjectIntNeg0:
    case ObjectShortPos0:
    case ObjectShortNeg0:
    case ObjectLongPos0:
    case ObjectLongNeg0:
    case PrimitiveBooleanFalse:
    case PrimitiveBytePos0:
    case PrimitiveByteNeg0:
    case PrimitiveCharPos0:
    case PrimitiveCharNeg0:
    case PrimitiveDoublePos0:
    case PrimitiveDoubleNeg0:
    case PrimitiveFloatPos0:
    case PrimitiveFloatNeg0:
    case PrimitiveIntPos0:
    case PrimitiveIntNeg0:
    case PrimitiveShortPos0:
    case PrimitiveShortNeg0:
    case PrimitiveLongPos0:
    case PrimitiveLongNeg0:
      return Boolean.TRUE;
    default:
      throw new Error("Unknown input val "+inputVal);
    }
  }
  
  private static final Object initVal(QueryInputVal inputVal) {
    switch(inputVal) {
    case BoxedBooleanNull:
    case BoxedByteNull:
    case BoxedCharacterNull:
    case BoxedShortNull:
    case BoxedIntegerNull:
    case BoxedLongNull:
    case BoxedFloatNull:
    case BoxedDoubleNull:
    case ObjectNull:
      return null;
    case BoxedBooleanFalse:
    case ObjectBooleanFalse:
    case PrimitiveBooleanFalse:
      return false;
    case BoxedBooleanTrue:
    case ObjectBooleanTrue:
    case PrimitiveBooleanTrue:
      return true;
    case BoxedByteNeg0:
    case BoxedBytePos0:
    case ObjectByteNeg0:
    case ObjectBytePos0:
    case PrimitiveByteNeg0:
    case PrimitiveBytePos0:
      return (byte)0;
    case BoxedByteNeg1:
    case ObjectByteNeg1:
    case PrimitiveByteNeg1:
      return (byte)-1;
    case BoxedBytePos1:
    case ObjectBytePos1:
    case PrimitiveBytePos1:
      return (byte)1;
    case BoxedBytePos2:
    case ObjectBytePos2:
    case PrimitiveBytePos2:
      return (byte)2;
    case BoxedCharNeg0:
    case BoxedCharPos0:
    case ObjectCharNeg0:
    case ObjectCharPos0:
    case PrimitiveCharNeg0:
    case PrimitiveCharPos0:
      return (char)0;
    case BoxedCharPos1:
    case ObjectCharPos1:
    case PrimitiveCharPos1:
      return (char)1;
    case BoxedCharPos2:
    case ObjectCharPos2:
    case PrimitiveCharPos2:
      return (char)2;
    case BoxedCharMaxBytePlus1:
    case ObjectCharMaxBytePlus1:
    case PrimitiveCharMaxBytePlus1:
      return (char)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedCharMaxShortPlus1:
    case ObjectCharMaxShortPlus1:
    case PrimitiveCharMaxShortPlus1:
      return (char)(((int)Short.MAX_VALUE)+(int)1);
    case BoxedShortPos0:
    case BoxedShortNeg0:
    case ObjectShortPos0:
    case ObjectShortNeg0:
    case PrimitiveShortPos0:
    case PrimitiveShortNeg0:
      return (short)0;
    case BoxedShortPos1:
    case ObjectShortPos1:
    case PrimitiveShortPos1:
      return (short)1;
    case BoxedShortNeg1:
    case ObjectShortNeg1:
    case PrimitiveShortNeg1:
      return (short)-1;
    case BoxedShortPos2:
    case ObjectShortPos2:
    case PrimitiveShortPos2:
      return (short)2;
    case BoxedShortMaxBytePlus1:
    case ObjectShortMaxBytePlus1:
    case PrimitiveShortMaxBytePlus1:
      return (short)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedShortMinByteMinus1:
    case ObjectShortMinByteMinus1:
    case PrimitiveShortMinByteMinus1:
      return (short)(((int)Byte.MIN_VALUE)-(int)1);
    case BoxedIntPos0:
    case BoxedIntNeg0:
    case ObjectIntPos0:
    case ObjectIntNeg0:
    case PrimitiveIntPos0:
    case PrimitiveIntNeg0:
      return (int)0;
    case BoxedIntPos1:
    case ObjectIntPos1:
    case PrimitiveIntPos1:
      return (int)1;
    case BoxedIntNeg1:
    case ObjectIntNeg1:
    case PrimitiveIntNeg1:
      return (int)-1;
    case BoxedIntPos2:
    case ObjectIntPos2:
    case PrimitiveIntPos2:
      return (int)2;  
    case BoxedIntMaxBytePlus1:
    case ObjectIntMaxBytePlus1:
    case PrimitiveIntMaxBytePlus1:
      return (int)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedIntMinByteMinus1:
    case ObjectIntMinByteMinus1:
    case PrimitiveIntMinByteMinus1:
      return (int)(((int)Byte.MIN_VALUE)-(int)1);
    case BoxedIntMaxShortPlus1:
    case ObjectIntMaxShortPlus1:
    case PrimitiveIntMaxShortPlus1:
      return (int)(((int)Short.MAX_VALUE)+(int)1);
    case BoxedIntMinShortMinus1:
    case ObjectIntMinShortMinus1:
    case PrimitiveIntMinShortMinus1:
      return (int)(((int)Short.MIN_VALUE)-(int)1);
    case BoxedIntMaxCharPlus1:
    case ObjectIntMaxCharPlus1:
    case PrimitiveIntMaxCharPlus1:
      return (int)(((int)Character.MAX_VALUE)+(int)1);
    case BoxedIntMaxSafeIntPlus1:
    case ObjectIntMaxSafeIntPlus1:
    case PrimitiveIntMaxSafeIntPlus1:
      return (int)(((int)TypeUtil.MAX_SAFE_INT)+(int)1);
    case BoxedIntMinSafeIntMinus1:
    case ObjectIntMinSafeIntMinus1:
    case PrimitiveIntMinSafeIntMinus1:
      return (int)(((int)TypeUtil.MIN_SAFE_INT)-(int)1);
    case BoxedLongPos0:
    case BoxedLongNeg0:
    case ObjectLongPos0:
    case ObjectLongNeg0:
    case PrimitiveLongPos0:
    case PrimitiveLongNeg0:
      return (long)0;
    case BoxedLongPos1:
    case ObjectLongPos1:
    case PrimitiveLongPos1:
      return (long)1;
    case BoxedLongNeg1:
    case ObjectLongNeg1:
    case PrimitiveLongNeg1:
      return (long)-1;
    case BoxedLongPos2:
    case ObjectLongPos2:
    case PrimitiveLongPos2:
      return (long)2;  
    case BoxedLongMaxBytePlus1:
    case ObjectLongMaxBytePlus1:
    case PrimitiveLongMaxBytePlus1:
      return (long)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedLongMinByteMinus1:
    case ObjectLongMinByteMinus1:
    case PrimitiveLongMinByteMinus1:
      return (long)(((int)Byte.MIN_VALUE)-(int)1);
    case BoxedLongMaxShortPlus1:
    case ObjectLongMaxShortPlus1:
    case PrimitiveLongMaxShortPlus1:
      return (long)(((int)Short.MAX_VALUE)+(int)1);
    case BoxedLongMinShortMinus1:
    case ObjectLongMinShortMinus1:
    case PrimitiveLongMinShortMinus1:
      return (long)(((int)Short.MIN_VALUE)-(int)1);
    case BoxedLongMaxCharPlus1:
    case ObjectLongMaxCharPlus1:
    case PrimitiveLongMaxCharPlus1:
      return (long)(((int)Character.MAX_VALUE)+(int)1);
    case BoxedLongMaxSafeIntPlus1:
    case ObjectLongMaxSafeIntPlus1:
    case PrimitiveLongMaxSafeIntPlus1:
      return (long)(((int)TypeUtil.MAX_SAFE_INT)+(int)1);
    case BoxedLongMinSafeIntMinus1:
    case ObjectLongMinSafeIntMinus1:
    case PrimitiveLongMinSafeIntMinus1:
      return (long)(((int)TypeUtil.MIN_SAFE_INT)-(int)1);
    case BoxedLongMaxIntPlus1:
    case ObjectLongMaxIntPlus1:
    case PrimitiveLongMaxIntPlus1:
      return (long)(((long)Integer.MAX_VALUE)+(long)1);
    case BoxedLongMinIntMinus1:
    case ObjectLongMinIntMinus1:
    case PrimitiveLongMinIntMinus1:
      return (long)(((long)Integer.MIN_VALUE)-(long)1);
    case BoxedLongMaxSafeLongPlus1:
    case ObjectLongMaxSafeLongPlus1:
    case PrimitiveLongMaxSafeLongPlus1:
      return (long)(((long)TypeUtil.MAX_SAFE_LONG)+(long)1);
    case BoxedLongMinSafeLongMinus1:
    case ObjectLongMinSafeLongMinus1:
    case PrimitiveLongMinSafeLongMinus1:
      return (long)(((long)TypeUtil.MIN_SAFE_LONG)-(long)1);
    case BoxedFloatPos0:
    case ObjectFloatPos0:
    case PrimitiveFloatPos0:
      return 0.0f;
    case BoxedFloatNeg0:
    case ObjectFloatNeg0:
    case PrimitiveFloatNeg0:
      return -0.0f;
    case BoxedFloatPos1:
    case ObjectFloatPos1:
    case PrimitiveFloatPos1:
      return 1.0f;
    case BoxedFloatNeg1:
    case ObjectFloatNeg1:
    case PrimitiveFloatNeg1:
      return -1.0f;
    case BoxedFloatPos2:
    case ObjectFloatPos2:
    case PrimitiveFloatPos2:
      return 2.0f;
    case BoxedFloatMaxBytePlus1:
    case ObjectFloatMaxBytePlus1:
    case PrimitiveFloatMaxBytePlus1:
      return (float)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedFloatMinByteMinus1:
    case ObjectFloatMinByteMinus1:
    case PrimitiveFloatMinByteMinus1:
      return (float)(((int)Byte.MIN_VALUE)-(int)1);
    case BoxedFloatMaxShortPlus1:
    case ObjectFloatMaxShortPlus1:
    case PrimitiveFloatMaxShortPlus1:
      return (float)(((int)Short.MAX_VALUE)+(int)1);
    case BoxedFloatMinShortMinus1:
    case ObjectFloatMinShortMinus1:
    case PrimitiveFloatMinShortMinus1:
      return (float)(((int)Short.MIN_VALUE)-(int)1);
    case BoxedFloatMaxCharPlus1:
    case ObjectFloatMaxCharPlus1:
    case PrimitiveFloatMaxCharPlus1:
      return (float)(((int)Character.MAX_VALUE)+(int)1);
    case BoxedFloatMaxSafeIntPlus1:
    case ObjectFloatMaxSafeIntPlus1:
    case PrimitiveFloatMaxSafeIntPlus1:
      return (float)(((int)TypeUtil.MAX_SAFE_INT)+(int)1);
    case BoxedFloatMinSafeIntMinus1:
    case ObjectFloatMinSafeIntMinus1:
    case PrimitiveFloatMinSafeIntMinus1:
      return (float)(((int)TypeUtil.MIN_SAFE_INT)-(int)1);
    case BoxedFloatMaxIntPlus1:
    case ObjectFloatMaxIntPlus1:
    case PrimitiveFloatMaxIntPlus1:
      return (float)(((long)Integer.MAX_VALUE)+(long)1);
    case BoxedFloatMinIntMinus1:
    case ObjectFloatMinIntMinus1:
    case PrimitiveFloatMinIntMinus1:
      return (float)(((long)Integer.MIN_VALUE)-(long)1);
    case BoxedFloatMaxSafeLongPlus1:
    case ObjectFloatMaxSafeLongPlus1:
    case PrimitiveFloatMaxSafeLongPlus1:
      return (float)(((long)TypeUtil.MAX_SAFE_LONG)+(long)1);
    case BoxedFloatMinSafeLongMinus1:
    case ObjectFloatMinSafeLongMinus1:
    case PrimitiveFloatMinSafeLongMinus1:
      return (float)(((long)TypeUtil.MIN_SAFE_LONG)-(long)1);
    case BoxedFloatMaxLongPlus1:
    case ObjectFloatMaxLongPlus1:
    case PrimitiveFloatMaxLongPlus1:
      return (float)(((float)Long.MAX_VALUE)+(float)1);
    case BoxedFloatMinLongMinus1:
    case ObjectFloatMinLongMinus1:
    case PrimitiveFloatMinLongMinus1:
      return (float)(((float)Long.MIN_VALUE)-(float)1);  
    case BoxedFloatMaxFloat:
    case ObjectFloatMaxFloat:
    case PrimitiveFloatMaxFloat:
      return (float)Float.MAX_VALUE;
    case BoxedFloatMinFloat:
    case ObjectFloatMinFloat:
    case PrimitiveFloatMinFloat:
      return (float)Float.MIN_VALUE;
    case BoxedFloatPosInfinity:
    case ObjectFloatPosInfinity:
    case PrimitiveFloatPosInfinity:
      return (float)Float.POSITIVE_INFINITY;
    case BoxedFloatNegInfinity:
    case ObjectFloatNegInfinity:
    case PrimitiveFloatNegInfinity:
      return (float)Float.NEGATIVE_INFINITY;
    case BoxedFloatNaN:
    case ObjectFloatNaN:
    case PrimitiveFloatNaN:
      return (float)Float.NaN;
    case BoxedDoublePos0:
    case ObjectDoublePos0:
    case PrimitiveDoublePos0:
      return 0.0d;
    case BoxedDoubleNeg0:
    case ObjectDoubleNeg0:
    case PrimitiveDoubleNeg0:
      return -0.0d;
    case BoxedDoublePos1:
    case ObjectDoublePos1:
    case PrimitiveDoublePos1:
      return 1.0d;
    case BoxedDoubleNeg1:
    case ObjectDoubleNeg1:
    case PrimitiveDoubleNeg1:
      return -1.0d;
    case BoxedDoublePos2:
    case ObjectDoublePos2:
    case PrimitiveDoublePos2:
      return 2.0d;
    case BoxedDoubleMaxBytePlus1:
    case ObjectDoubleMaxBytePlus1:
    case PrimitiveDoubleMaxBytePlus1:
      return (double)(((int)Byte.MAX_VALUE)+(int)1);
    case BoxedDoubleMinByteMinus1:
    case ObjectDoubleMinByteMinus1:
    case PrimitiveDoubleMinByteMinus1:
      return (double)(((int)Byte.MIN_VALUE)-(int)1);
    case BoxedDoubleMaxShortPlus1:
    case ObjectDoubleMaxShortPlus1:
    case PrimitiveDoubleMaxShortPlus1:
      return (double)(((int)Short.MAX_VALUE)+(int)1);
    case BoxedDoubleMinShortMinus1:
    case ObjectDoubleMinShortMinus1:
    case PrimitiveDoubleMinShortMinus1:
      return (double)(((int)Short.MIN_VALUE)-(int)1);
    case BoxedDoubleMaxCharPlus1:
    case ObjectDoubleMaxCharPlus1:
    case PrimitiveDoubleMaxCharPlus1:
      return (double)(((int)Character.MAX_VALUE)+(int)1);
    case BoxedDoubleMaxSafeIntPlus1:
    case ObjectDoubleMaxSafeIntPlus1:
    case PrimitiveDoubleMaxSafeIntPlus1:
      return (double)(((int)TypeUtil.MAX_SAFE_INT)+(int)1);
    case BoxedDoubleMinSafeIntMinus1:
    case ObjectDoubleMinSafeIntMinus1:
    case PrimitiveDoubleMinSafeIntMinus1:
      return (double)(((int)TypeUtil.MIN_SAFE_INT)-(int)1);
    case BoxedDoubleMaxIntPlus1:
    case ObjectDoubleMaxIntPlus1:
    case PrimitiveDoubleMaxIntPlus1:
      return (double)(((long)Integer.MAX_VALUE)+(long)1);
    case BoxedDoubleMinIntMinus1:
    case ObjectDoubleMinIntMinus1:
    case PrimitiveDoubleMinIntMinus1:
      return (double)(((long)Integer.MIN_VALUE)-(long)1);
    case BoxedDoubleMaxSafeLongPlus1:
    case ObjectDoubleMaxSafeLongPlus1:
    case PrimitiveDoubleMaxSafeLongPlus1:
      return (double)(((long)TypeUtil.MAX_SAFE_LONG)+(long)1);
    case BoxedDoubleMinSafeLongMinus1:
    case ObjectDoubleMinSafeLongMinus1:
    case PrimitiveDoubleMinSafeLongMinus1:
      return (double)(((long)TypeUtil.MIN_SAFE_LONG)-(long)1);
    case BoxedDoubleMaxLongPlus1:
    case ObjectDoubleMaxLongPlus1:
    case PrimitiveDoubleMaxLongPlus1:
      return (double)(((double)Long.MAX_VALUE)+(double)1);
    case BoxedDoubleMinLongMinus1:
    case ObjectDoubleMinLongMinus1:
    case PrimitiveDoubleMinLongMinus1:
      return (double)(((double)Long.MIN_VALUE)-(double)1);  
    case BoxedDoubleMaxFloat:
    case ObjectDoubleMaxFloat:
    case PrimitiveDoubleMaxFloat:
      return (double)Float.MAX_VALUE;
    case BoxedDoubleMinFloat:
    case ObjectDoubleMinFloat:
    case PrimitiveDoubleMinFloat:
      return (double)Float.MIN_VALUE;
    case BoxedDoublePosInfinity:
    case ObjectDoublePosInfinity:
    case PrimitiveDoublePosInfinity:
      return (double)Double.POSITIVE_INFINITY;
    case BoxedDoubleNegInfinity:
    case ObjectDoubleNegInfinity:
    case PrimitiveDoubleNegInfinity:
      return (double)Double.NEGATIVE_INFINITY;
    case BoxedDoubleNaN:
    case ObjectDoubleNaN:
    case PrimitiveDoubleNaN:
      return (double)Double.NaN;
    case BoxedDoubleMaxDouble:
    case ObjectDoubleMaxDouble:
    case PrimitiveDoubleMaxDouble:
      return (double)Double.MAX_VALUE;
    case BoxedDoubleMinDouble:
    case ObjectDoubleMinDouble:
    case PrimitiveDoubleMinDouble:
      return (double)Double.MIN_VALUE;
    default:
      throw new Error("Unknown input val "+inputVal);
    }
  }
  
  
  
}
