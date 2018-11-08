package omni.util;
public interface PeekAndPollIfc<E>{
    E peek();
    E poll();
    interface BooleanInput{
        boolean peekBoolean();
        boolean pollBoolean();
    }
    interface ByteInput{
        byte peekByte();
        byte pollByte();
    }
    interface CharInput{
        char peekChar();
        char pollChar();
    }
    interface DoubleInput{
        double peekDouble();
        double pollDouble();
    }
    interface FloatInput{
        float peekFloat();
        float pollFloat();
    }
    interface IntInput{
        int peekInt();
        int pollInt();
    }
    interface LongInput{
        long peekLong();
        long pollLong();
    }
    interface ShortInput{
        short peekShort();
        short pollShort();
    }
}
