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
        public boolean removeVal(byte val){
            return removeVal((short)val);
        }
        protected abstract boolean contains(short val);
        protected abstract boolean removeVal(short val);
    }
    static abstract class OfDouble extends AbstractSeq{
        public OfDouble(){
            super();
        }
        public OfDouble(int size){
            super(size);
        }
        public boolean contains(byte val){
            return contains((int)val);
        }
        public boolean contains(char val){
            return contains((int)val);
        }
        public boolean contains(short val){
            return contains((int)val);
        }
        public boolean removeVal(byte val){
            return removeVal((int)val);
        }
        public boolean removeVal(char val){
            return removeVal((int)val);
        }
        public boolean removeVal(short val){
            return removeVal((int)val);
        }
        protected abstract boolean contains(int val);
        protected abstract boolean removeVal(int val);
    }
    static abstract class OfFloat extends AbstractSeq{
        public OfFloat(){
            super();
        }
        public OfFloat(int size){
            super(size);
        }
        public boolean contains(byte val){
            return containsRawInt(val);
        }
        public boolean contains(char val){
            return containsRawInt(val);
        }
        public boolean contains(short val){
            return containsRawInt(val);
        }
        public boolean removeVal(byte val){
            return removeRawInt(val);
        }
        public boolean removeVal(char val){
            return removeRawInt(val);
        }
        public boolean removeVal(short val){
            return removeRawInt(val);
        }
        protected abstract boolean containsRawInt(int val);
        protected abstract boolean removeRawInt(int val);
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
        public boolean removeVal(byte val){
            return removeVal((int)val);
        }
        public boolean removeVal(char val){
            return removeVal((int)val);
        }
        protected abstract boolean contains(int val);
        protected abstract boolean removeVal(int val);
    }
}
