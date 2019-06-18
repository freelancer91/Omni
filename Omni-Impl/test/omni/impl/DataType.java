package omni.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import omni.api.OmniCollection;
import omni.api.OmniDeque;
import omni.api.OmniIterator;
import omni.api.OmniList;
import omni.api.OmniListIterator;
import omni.api.OmniQueue;
import omni.api.OmniStack;
import omni.util.PeekAndPollIfc;
import omni.util.TypeConversionUtil;

public enum DataType{
    Boolean(boolean.class,Boolean.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToboolean(valToConvert);
        }
        @Override
        public void verifyItrNext(DataType itrDataType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniIterator.OfBoolean)itr).nextBoolean());
        }
        @Override
        public void verifyListItrPrevious(DataType itrDataType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniListIterator.OfBoolean)itr).previousBoolean());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniList.OfBoolean)list).getBoolean(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniStack.OfBoolean)stack).popBoolean());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniList.OfBoolean)list).removeBooleanAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((PeekAndPollIfc.BooleanOutput<?>)col).peekBoolean());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((PeekAndPollIfc.BooleanOutput<?>)col).pollBoolean());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniQueue.OfBoolean)queue).booleanElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniQueue.OfBoolean)queue).removeBoolean());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).getFirstBoolean());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).getLastBoolean());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).removeFirstBoolean());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).removeLastBoolean());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).pollFirstBoolean());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).pollLastBoolean());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).peekFirstBoolean());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?false:TypeConversionUtil.convertToboolean(expectedVal),
                    ((OmniDeque.OfBoolean)deque).peekLastBoolean());
        }
    },
    Byte(byte.class,Byte.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTobyte(valToConvert);
        }
        private byte getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTobyteboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Char:
            case Short:
            case Int:
            case Long:
            case Float:
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.ByteOutput<?>)itr).nextByte());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.ByteOutput<?>)itr).previousByte());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.ByteOutput<?>)list).getByte(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.ByteOutput<?>)stack).popByte());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.ByteOutput<?>)list).removeByteAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.ByteOutput<?>)col).peekByte());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.ByteOutput<?>)col).pollByte());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.ByteOutput<?>)queue).byteElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.ByteOutput<?>)queue).removeByte());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).getFirstByte());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).getLastByte());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).removeFirstByte());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).removeLastByte());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).pollFirstByte());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).pollLastByte());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).peekFirstByte());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Byte.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ByteOutput<?>)deque).peekLastByte());
        }
    },
    Char(char.class,Character.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTochar(valToConvert);
        }
        private char getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTocharboolean(expectedVal);
            case Char:
                return TypeConversionUtil.convertTochar(expectedVal);
            case Byte:
            case Short:
            case Int:
            case Long:
            case Float:
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.CharOutput<?>)itr).nextChar());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.CharOutput<?>)itr).previousChar());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.CharOutput<?>)list).getChar(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.CharOutput<?>)stack).popChar());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.CharOutput<?>)list).removeCharAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((PeekAndPollIfc.CharOutput<?>)col).peekChar());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((PeekAndPollIfc.CharOutput<?>)col).pollChar());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.CharOutput<?>)queue).charElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.CharOutput<?>)queue).removeChar());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.CharOutput<?>)deque).getFirstChar());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.CharOutput<?>)deque).getLastChar());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.CharOutput<?>)deque).removeFirstChar());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.CharOutput<?>)deque).removeLastChar());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((OmniDeque.CharOutput<?>)deque).pollFirstChar());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((OmniDeque.CharOutput<?>)deque).pollLastChar());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((OmniDeque.CharOutput<?>)deque).peekFirstChar());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(
                    expectEmpty?java.lang.Character.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                            ((OmniDeque.CharOutput<?>)deque).peekLastChar());
        }
    },
    Short(short.class,Short.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToshort(valToConvert);
        }
        private short getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertToshortboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Short:
                return TypeConversionUtil.convertToshort(expectedVal);
            case Char:
            case Int:
            case Long:
            case Float:
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.ShortOutput<?>)itr).nextShort());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.ShortOutput<?>)itr).previousShort());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.ShortOutput<?>)list).getShort(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.ShortOutput<?>)stack).popShort());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.ShortOutput<?>)list).removeShortAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.ShortOutput<?>)col).peekShort());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.ShortOutput<?>)col).pollShort());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.ShortOutput<?>)queue).shortElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.ShortOutput<?>)queue).removeShort());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).getFirstShort());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).getLastShort());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).removeFirstShort());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).removeLastShort());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).pollFirstShort());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).pollLastShort());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).peekFirstShort());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Short.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.ShortOutput<?>)deque).peekLastShort());
        }
    },
    Int(int.class,Integer.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToint(valToConvert);
        }
        private int getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTointboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Char:
                return TypeConversionUtil.convertTochar(expectedVal);
            case Short:
                return TypeConversionUtil.convertToshort(expectedVal);
            case Int:
                return TypeConversionUtil.convertToint(expectedVal);
            case Long:
            case Float:
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.IntOutput<?>)itr).nextInt());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.IntOutput<?>)itr).previousInt());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.IntOutput<?>)list).getInt(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.IntOutput<?>)stack).popInt());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.IntOutput<?>)list).removeIntAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.IntOutput<?>)col).peekInt());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.IntOutput<?>)col).pollInt());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.IntOutput<?>)queue).intElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.IntOutput<?>)queue).removeInt());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).getFirstInt());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).getLastInt());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).removeFirstInt());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).removeLastInt());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).pollFirstInt());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).pollLastInt());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).peekFirstInt());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Integer.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.IntOutput<?>)deque).peekLastInt());
        }
    },
    Long(long.class,Long.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTolong(valToConvert);
        }
        private long getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTolongboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Char:
                return TypeConversionUtil.convertTochar(expectedVal);
            case Short:
                return TypeConversionUtil.convertToshort(expectedVal);
            case Int:
                return TypeConversionUtil.convertToint(expectedVal);
            case Long:
                return TypeConversionUtil.convertTolong(expectedVal);
            case Float:
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.LongOutput<?>)itr).nextLong());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.LongOutput<?>)itr).previousLong());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.LongOutput<?>)list).getLong(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.LongOutput<?>)stack).popLong());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.LongOutput<?>)list).removeLongAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.LongOutput<?>)col).peekLong());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.LongOutput<?>)col).pollLong());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.LongOutput<?>)queue).longElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.LongOutput<?>)queue).removeLong());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).getFirstLong());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).getLastLong());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).removeFirstLong());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).removeLastLong());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).pollFirstLong());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).pollLastLong());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).peekFirstLong());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Long.MIN_VALUE:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.LongOutput<?>)deque).peekLastLong());
        }
    },
    Float(float.class,Float.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTofloat(valToConvert);
        }
        private float getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTofloatboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Char:
                return TypeConversionUtil.convertTochar(expectedVal);
            case Short:
                return TypeConversionUtil.convertToshort(expectedVal);
            case Int:
                return TypeConversionUtil.convertToint(expectedVal);
            case Long:
                return TypeConversionUtil.convertTolong(expectedVal);
            case Float:
                return TypeConversionUtil.convertTofloat(expectedVal);
            case Double:
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.FloatOutput<?>)itr).nextFloat());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.FloatOutput<?>)itr).previousFloat());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.FloatOutput<?>)list).getFloat(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.FloatOutput<?>)stack).popFloat());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.FloatOutput<?>)list).removeFloatAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.FloatOutput<?>)col).peekFloat());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.FloatOutput<?>)col).pollFloat());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.FloatOutput<?>)queue).floatElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.FloatOutput<?>)queue).removeFloat());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).getFirstFloat());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).getLastFloat());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).removeFirstFloat());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).removeLastFloat());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).pollFirstFloat());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).pollLastFloat());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).peekFirstFloat());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Float.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.FloatOutput<?>)deque).peekLastFloat());
        }
    },
    Double(double.class,Double.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertTodouble(valToConvert);
        }
        private double getExpectedOutputVal(DataType inputType,int expectedVal){
            switch(inputType){
            case Boolean:
                return TypeConversionUtil.convertTodoubleboolean(expectedVal);
            case Byte:
                return TypeConversionUtil.convertTobyte(expectedVal);
            case Char:
                return TypeConversionUtil.convertTochar(expectedVal);
            case Short:
                return TypeConversionUtil.convertToshort(expectedVal);
            case Int:
                return TypeConversionUtil.convertToint(expectedVal);
            case Long:
                return TypeConversionUtil.convertTolong(expectedVal);
            case Float:
                return TypeConversionUtil.convertTofloat(expectedVal);
            case Double:
                return TypeConversionUtil.convertTodouble(expectedVal);
            case Object:
            }
            throw new Error("Invalid input type " + inputType);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniIterator.DoubleOutput<?>)itr).nextDouble());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniListIterator.DoubleOutput<?>)itr).previousDouble());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.DoubleOutput<?>)list).getDouble(index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniStack.DoubleOutput<?>)stack).popDouble());
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniList.DoubleOutput<?>)list).removeDoubleAt(index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.DoubleOutput<?>)col).peekDouble());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((PeekAndPollIfc.DoubleOutput<?>)col).pollDouble());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.DoubleOutput<?>)queue).doubleElement());
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniQueue.DoubleOutput<?>)queue).removeDouble());
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).getFirstDouble());
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).getLastDouble());
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).removeFirstDouble());
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            Assertions.assertEquals(getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).removeLastDouble());
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).pollFirstDouble());
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).pollLastDouble());
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).peekFirstDouble());
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?java.lang.Double.NaN:getExpectedOutputVal(inputType,expectedVal),
                    ((OmniDeque.DoubleOutput<?>)deque).peekLastDouble());
        }
    },
    Object(Object.class,Object.class){
        @Override
        public java.lang.Object convertVal(int valToConvert){
            return TypeConversionUtil.convertToObject(valToConvert);
        }
        @Override
        public void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal){
            Assertions.assertEquals(inputType.convertVal(expectedVal),itr.next());
        }
        @Override
        public void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal){
            Assertions.assertEquals(inputType.convertVal(expectedVal),itr.previous());
        }
        @Override
        public void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(list.getClass(),"get",int.class),list,index));
        }
        @Override
        public void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(stack.getClass(),"pop"),stack));
        }
        @Override
        public void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(list.getClass(),"remove",int.class),list,index));
        }
        @Override
        public void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),((PeekAndPollIfc<?>)col).peek());
        }
        @Override
        public void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty){
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),((PeekAndPollIfc<?>)col).poll());
        }
        @Override
        public void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(queue.getClass(),"element"),queue));
        }
        @Override
        public void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(queue.getClass(),"remove"),queue));
        }
        @Override
        public void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"getFirst"),deque));
        }
        @Override
        public void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"getLast"),deque));
        }
        @Override
        public void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"removeFirst"),deque));
        }
        @Override
        public void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"removeLast"),deque));
        }
        @Override
        public void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"pollFirst"),deque));
        }
        @Override
        public void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"pollLast"),deque));
        }
        @Override
        public void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"peekFirst"),deque));
        }
        @Override
        public void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty){
            // TODO make a way to call this method without reflection
            Assertions.assertEquals(expectEmpty?null:inputType.convertVal(expectedVal),
                    invokeMethod(lookUpMethod(deque.getClass(),"peekLast"),deque));
        }
    };

    public final Set<DataType> mayBeCastTo;
    public final Set<DataType> mayBeCastFrom;
    public final Set<QueryInputVal> mayContainVals;
    public final Class<?> preferredInputType;
    public final Class<?> boxedType;


    DataType(Class<?> preferredInputType,Class<?> boxedType){
        this.mayBeCastTo=initMayBeCastTo(this);
        this.mayBeCastFrom=initMayBeCastFrom(this);
        this.mayContainVals=initMayContainVals(this);
        this.preferredInputType=preferredInputType;
        this.boxedType=boxedType;
    }
    public abstract Object convertVal(int valToConvert);
    public abstract void verifyItrNext(DataType inputType,OmniIterator<?> itr,int expectedVal);
    public abstract void verifyListItrPrevious(DataType inputType,OmniListIterator<?> itr,int expectedVal);
    public abstract void verifyListGet(DataType inputType,OmniList list,int index,int expectedVal);
    public abstract void verifyStackPop(DataType inputType,OmniStack stack,int expectedVal);
    public abstract void verifyListRemoveAt(DataType inputType,OmniList list,int index,int expectedVal);
    public abstract void verifyPeek(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty);
    public abstract void verifyPoll(DataType inputType,OmniCollection col,int expectedVal,boolean expectEmpty);
    public abstract void verifyQueueElement(DataType inputType,OmniQueue queue,int expectedVal);
    public abstract void verifyQueueRemove(DataType inputType,OmniQueue queue,int expectedVal);
    public abstract void verifyDequeGetFirst(DataType inputType,OmniDeque deque,int expectedVal);
    public abstract void verifyDequeGetLast(DataType inputType,OmniDeque deque,int expectedVal);
    public abstract void verifyDequeRemoveFirst(DataType inputType,OmniDeque deque,int expectedVal);
    public abstract void verifyDequeRemoveLast(DataType inputType,OmniDeque deque,int expectedVal);
    public abstract void verifyDequePollFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty);
    public abstract void verifyDequePollLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty);
    public abstract void verifyDequePeekFirst(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty);
    public abstract void verifyDequePeekLast(DataType inputType,OmniDeque deque,int expectedVal,boolean expectEmpty);



    private static Set<QueryInputVal> initMayContainVals(DataType dataType){
        Stream.Builder<QueryInputVal> builder=Stream.builder();
        for(var queryInputVal:QueryInputVal.values()){
            if(queryInputVal.dataTypesWhichCanContain.contains(dataType)){
                builder.accept(queryInputVal);
            }
        }
        return builder.build().collect(Collectors.toUnmodifiableSet());
    }

    private static Set<DataType> initMayBeCastFrom(DataType dataType){
        switch(dataType) {
        case Boolean:
            return Set.of(DataType.Boolean);
        case Byte:
            return Set.of(DataType.Byte,DataType.Boolean);
        case Char:
            return Set.of(DataType.Char,DataType.Boolean);
        case Short:
            return Set.of(DataType.Short,DataType.Byte,DataType.Boolean);
        case Int:
            return Set.of(DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
        case Long:
            return Set.of(DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
        case Float:
            return Set.of(DataType.Float,DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
        case Double:
            return Set.of(DataType.Double,DataType.Float,DataType.Long,DataType.Int,DataType.Short,DataType.Char,DataType.Byte,DataType.Boolean);
        case Object:
            return Set.of(DataType.Object);
        }
        throw new Error("Unknown dataType " + dataType);
    }
    private static Set<DataType> initMayBeCastTo(DataType dataType){
        switch(dataType) {
        case Boolean:
            return Set.of(DataType.values());
        case Byte:
            return Set.of(DataType.Byte,DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
        case Char:
            return Set.of(DataType.Char,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
        case Short:
            return Set.of(DataType.Short,DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
        case Int:
            return Set.of(DataType.Int,DataType.Long,DataType.Float,DataType.Double,DataType.Object);
        case Long:
            return Set.of(DataType.Long,DataType.Float,DataType.Double,DataType.Object);
        case Float:
            return Set.of(DataType.Float,DataType.Double,DataType.Object);
        case Double:
            return Set.of(DataType.Double,DataType.Object);
        case Object:
            return Set.of(DataType.Object);
        }
        throw new Error("Unknown dataType " + dataType);
    }
    private static Object invokeMethod(Method method,Object obj,Object...params){
        try{
            return method.invoke(obj,params);
        }catch(IllegalAccessException | IllegalArgumentException e){
            throw new Error(e);
        }catch(InvocationTargetException e){
            var cause=e.getCause();
            if(cause instanceof RuntimeException){
                throw(RuntimeException)cause;
            }
            if(cause instanceof Error){
                throw(Error)cause;
            }
            throw new Error(cause);
        }
    }
    private static Method lookUpMethod(Class<?> clazz,String methodName,Class<?>...paramTypes){
        try{
            Method method=clazz.getMethod(methodName,paramTypes);
            method.setAccessible(true);
            return method;
        }catch(NoSuchMethodException | SecurityException e){
            throw new Error(e);
        }
    }
}
