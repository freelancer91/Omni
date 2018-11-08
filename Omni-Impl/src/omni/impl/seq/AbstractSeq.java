package omni.impl.seq;
public abstract class AbstractSeq{
    public transient int size;
    protected AbstractSeq(){
        super();
    }
    protected AbstractSeq(int size){
        super();
        this.size=size;
    }
    public void clear(){
        size=0;
    }
    @Override
    public abstract Object clone();
    @Override
    public abstract boolean equals(Object val);
    @Override
    public abstract int hashCode();
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    @Override
    public abstract String toString();
    static abstract class Of16BitPrimitive extends AbstractSeq{
        public Of16BitPrimitive(){
            super();
        }
        public Of16BitPrimitive(int size){
            super(size);
        }
        public boolean contains(byte val){
            return contains((short)val);
        }
        protected abstract boolean contains(short val);
    }
    static abstract class OfSignedIntegralPrimitive extends AbstractSeq{
        public OfSignedIntegralPrimitive(){
            super();
        }
        public OfSignedIntegralPrimitive(int size){
            super(size);
        }
        public boolean contains(byte val){
            return contains((int)val);
        }
        public boolean contains(char val){
            return contains((int)val);
        }
        protected abstract boolean contains(int val);
    }
}
