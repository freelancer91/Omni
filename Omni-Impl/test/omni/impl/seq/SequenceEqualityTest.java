package omni.impl.seq;

import omni.impl.DataType;
import omni.impl.MonitoredList;
import omni.impl.StructType;

public class SequenceEqualityTest{
  
   static MonitoredList<?> getMonitoredList(DataType dataType,StructType structType,int size,SequenceInitialization initSeq){
     switch(structType) {
    case ArrList:
      switch(dataType) {
      case BOOLEAN:
      //TODO
        break;
      case BYTE:
      //TODO
        break;
      case CHAR:
      //TODO
        break;
      case DOUBLE:
      //TODO
        break;
      case FLOAT:
      //TODO
        break;
      case INT:
      //TODO
        break;
      case LONG:
      //TODO
        break;
      case REF:
      //TODO
        break;
      case SHORT:
      //TODO
        break;
      default:
        throw dataType.invalid();
      }
      break;
    case ArrSubList:
      switch(dataType) {
      case BOOLEAN:
      //TODO
        break;
      case BYTE:
      //TODO
        break;
      case CHAR:
      //TODO
        break;
      case DOUBLE:
      //TODO
        break;
      case FLOAT:
      //TODO
        break;
      case INT:
      //TODO
        break;
      case LONG:
      //TODO
        break;
      case REF:
      //TODO
        break;
      case SHORT:
      //TODO
        break;
      default:
        throw dataType.invalid();
      }
      break;
    case DblLnkList:
      switch(dataType) {
      case BOOLEAN:
      //TODO
        break;
      case BYTE:
      //TODO
        break;
      case CHAR:
      //TODO
        break;
      case DOUBLE:
      //TODO
        break;
      case FLOAT:
      //TODO
        break;
      case INT:
      //TODO
        break;
      case LONG:
      //TODO
        break;
      case REF:
      //TODO
        break;
      case SHORT:
      //TODO
        break;
      default:
        throw dataType.invalid();
      }
      break;
    case DblLnkSubList:
      switch(dataType) {
      case BOOLEAN:
      //TODO
        break;
      case BYTE:
      //TODO
        break;
      case CHAR:
      //TODO
        break;
      case DOUBLE:
      //TODO
        break;
      case FLOAT:
      //TODO
        break;
      case INT:
      //TODO
        break;
      case LONG:
      //TODO
        break;
      case REF:
        break;
      case SHORT:
      //TODO
        break;
      default:
        throw dataType.invalid();
      }
      break;
    case PackedBooleanArrList:
      if(dataType!=DataType.BOOLEAN) {
        throw dataType.invalid();
      }
    //TODO
      break;
    case PackedBooleanArrSubList:
      if(dataType!=DataType.BOOLEAN) {
        throw dataType.invalid();
      }
      //TODO
      break;
    default:
      throw structType.invalid();
     }
   }
  
}
