package omni.util;
public interface PeekAndPollIfc<E>{
    E peek();
    E poll();
    interface BooleanOutput<E>extends ByteOutput<E>,CharOutput<E>{
        boolean peekBoolean();
        boolean pollBoolean();
    }
    interface ByteOutput<E>extends ShortOutput<E>{
        byte peekByte();
        byte pollByte();
    }
    interface CharOutput<E>extends IntOutput<E>{
        char peekChar();
        char pollChar();
    }
    interface DoubleOutput<E>extends PeekAndPollIfc<E>{
        double peekDouble();
        double pollDouble();
    }
    interface FloatOutput<E>extends DoubleOutput<E>{
        float peekFloat();
        float pollFloat();
    }
    interface IntOutput<E>extends LongOutput<E>{
        int peekInt();
        int pollInt();
    }
    interface LongOutput<E>extends FloatOutput<E>{
        long peekLong();
        long pollLong();
    }
    interface ShortOutput<E>extends IntOutput<E>{
        short peekShort();
        short pollShort();
    }
}
