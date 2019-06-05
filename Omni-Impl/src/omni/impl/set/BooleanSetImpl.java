package omni.impl.set;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniSet;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class BooleanSetImpl implements OmniSet.OfBoolean,Cloneable,Serializable{
    private static final long serialVersionUID=1L;
    transient int state;
    public BooleanSetImpl(){
        super();
    }
    private BooleanSetImpl(int state){
        this.state=state;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.writeByte(state);
    }
    private void readObject(ObjectInputStream ois) throws IOException{
        this.state=ois.readUnsignedByte();
    }
    @Override
    public void clear(){
        this.state=0b00;
    }
    @Override
    public Object clone(){
        return new BooleanSetImpl(this.state);
    }
    @Override
    public String toString(){
        switch(state){
        case 0b00:
            return "[]";
        case 0b01:
            return "[false]";
        case 0b10:
            return "[true]";
        default:
            return "[false, true]";
        }
    }
    @Override
    public int hashCode(){
        switch(state){
        case 0b00:
            return 0;
        case 0b01:
            return 1237;
        case 0b10:
            return 1231;
        default:
            return 1231 + 1237;
        }
    }
    @Override
    public boolean equals(Object val){
        // TODO
        return false;
    }
    @Override
    public boolean contains(Object val){
        switch(state){
        case 0b01:
            if(val instanceof Boolean){
                return !(Boolean)val;
            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                return ((Number)val).intValue() == 0;
            }else if(val instanceof Float){
                return (float)val == 0;
            }else if(val instanceof Double){
                return (double)val == 0;
            }else if(val instanceof Long){
                return 0L == (long)val;
            }else if(val instanceof Character){
                return 0 == (char)val;
            }
            break;
        case 0b10:
            if(val instanceof Boolean){
                return (Boolean)val;
            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                return ((Number)val).intValue() == 1;
            }else if(val instanceof Float){
                return (float)val == 1;
            }else if(val instanceof Double){
                return (double)val == 0;
            }else if(val instanceof Long){
                return 1L == (long)val;
            }else if(val instanceof Character){
                return 1 == (char)val;
            }
            break;
        default:
            if(val instanceof Boolean){
                return true;
            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                int v;
                return (v=((Number)val).intValue()) == 1 || v == 0;
            }else if(val instanceof Float){
                switch(Float.floatToRawIntBits((float)val)){
                case 0:
                case TypeUtil.FLT_TRUE_BITS:
                case Integer.MIN_VALUE:
                    return true;
                default:
                    return false;
                }
            }else if(val instanceof Double){
                long bits;
                return (bits=Double.doubleToRawLongBits((double)val)) == 0L || bits == Long.MIN_VALUE
                        || bits == TypeUtil.DBL_TRUE_BITS;
            }else if(val instanceof Long){
                long v;
                return (v=(long)val) == 0L || v == 1L;
            }else if(val instanceof Character){
                int v;
                return (v=(char)val) == 0 || v == 1;
            }
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean contains(boolean val){
        switch(state){
        case 0b00:
            return false;
        case 0b01:
            return !val;
        case 0b10:
            return val;
        default:
            return true;
        }
    }
    @Override
    public boolean contains(int val){
        switch(state){
        case 0b00:
            return false;
        case 0b01:
            return val == 0;
        case 0b10:
            return val == 1;
        default:
            return val == 0 || val == 1;
        }
    }
    @Override
    public boolean contains(long val){
        switch(state){
        case 0b00:
            return false;
        case 0b01:
            return val == 0L;
        case 0b10:
            return val == 1L;
        default:
            return val == 0L || val == 1L;
        }
    }
    @Override
    public boolean contains(float val){
        switch(state){
        case 0b01:
            return val == 0;
        case 0b10:
            return val == 1;
        default:
            switch(Float.floatToRawIntBits(val)){
            case 0:
            case Integer.MIN_VALUE:
            case TypeUtil.FLT_TRUE_BITS:
                return true;
            }
        case 0b00:
            return false;
        }
    }
    @Override
    public boolean contains(double val){
        switch(state){
        case 0b00:
            return false;
        case 0b01:
            return val == 0;
        case 0b10:
            return val == 1;
        default:
            long bits;
            return (bits=Double.doubleToRawLongBits(val)) == 0L || bits == Long.MIN_VALUE
                    || bits == TypeUtil.DBL_TRUE_BITS;
        }
    }
    @Override
    public boolean remove(Object val){
        clearAll:for(;;){
            setFalse:for(;;){
                setTrue:for(;;){
                    switch(state){
                    case 0b01:
                        if(val instanceof Boolean){
                            if(!(Boolean)val){
                                break clearAll;
                            }
                        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                            if(((Number)val).intValue() == 0){
                                break clearAll;
                            }
                        }else if(val instanceof Float){
                            if((float)val == 0){
                                break clearAll;
                            }
                        }else if(val instanceof Double){
                            if((double)val == 0){
                                break clearAll;
                            }
                        }else if(val instanceof Long){
                            if(0L == (long)val){
                                break clearAll;
                            }
                        }else if(val instanceof Character){
                            if(0 == (char)val){
                                break clearAll;
                            }
                        }
                        break;
                    case 0b10:
                        if(val instanceof Boolean){
                            if((Boolean)val){
                                break clearAll;
                            }
                        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                            if(((Number)val).intValue() == 1){
                                break clearAll;
                            }
                        }else if(val instanceof Float){
                            if((float)val == 1){
                                break clearAll;
                            }
                        }else if(val instanceof Double){
                            if((double)val == 1){
                                break clearAll;
                            }
                        }else if(val instanceof Long){
                            if(1L == (long)val){
                                break clearAll;
                            }
                        }else if(val instanceof Character){
                            if(1 == (char)val){
                                break clearAll;
                            }
                        }
                        break;
                    default:
                        if(val instanceof Boolean){
                            if((boolean)val){
                                break setFalse;
                            }
                            break setTrue;
                        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                            switch(((Number)val).intValue()){
                            case 0:
                                break setTrue;
                            case 1:
                                break setFalse;
                            default:
                            }
                        }else if(val instanceof Float){
                            switch(Float.floatToRawIntBits((float)val)){
                            case 0:
                            case Integer.MIN_VALUE:
                                break setTrue;
                            case TypeUtil.FLT_TRUE_BITS:
                                break setFalse;
                            default:
                            }
                        }else if(val instanceof Double){
                            long bits;
                            if((bits=Double.doubleToRawLongBits((double)val)) == 0L || bits == Long.MIN_VALUE){
                                break setTrue;
                            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                                break setFalse;
                            }
                        }else if(val instanceof Long){
                            long v;
                            if((v=(long)val) == 0L){
                                break setTrue;
                            }else if(v == 1L){
                                break setFalse;
                            }
                        }else if(val instanceof Character){
                            switch((char)val){
                            case 0:
                                break setTrue;
                            case 1:
                                break setFalse;
                            default:
                            }
                        }
                    case 0b00:
                    }
                    return false;
                }
    this.state=0b10;
    return true;
            }
    this.state=0b01;
    return true;
        }
    this.state=0b00;
    return true;
    }
    @Override
    public boolean removeVal(boolean val){
        switch(state){
        case 0b01:
            if(!val){
                this.state=0b00;
                return true;
            }
            break;
        case 0b10:
            if(val){
                this.state=0b00;
                return true;
            }
            break;
        default:
            if(val){
                this.state=0b01;
            }else{
                this.state=0b10;
            }
            return true;
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean removeVal(int val){
        switch(state){
        case 0b01:
            if(val == 0){
                this.state=0b00;
                return true;
            }
            break;
        case 0b10:
            if(val == 1){
                this.state=0b00;
                return true;
            }
            break;
        default:
            switch(val){
            case 0:
                this.state=0b10;
                return true;
            case 1:
                this.state=0b01;
                return true;
            default:
            }
            break;
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean removeVal(long val){
        switch(state){
        case 0b01:
            if(val == 0L){
                this.state=0b00;
                return true;
            }
            break;
        case 0b10:
            if(val == 1L){
                this.state=0b00;
                return true;
            }
            break;
        default:
            if(val == 0L){
                this.state=0b10;
                return true;
            }else if(val == 1L){
                this.state=0b01;
                return true;
            }
            break;
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean removeVal(float val){
        switch(state){
        case 0b01:
            if(val == 0){
                this.state=0b00;
                return true;
            }
            break;
        case 0b10:
            if(val == 1){
                this.state=0b00;
                return true;
            }
            break;
        default:
            switch(Float.floatToRawIntBits(val)){
            case 0:
            case Integer.MIN_VALUE:
                this.state=0b10;
                return true;
            case TypeUtil.FLT_TRUE_BITS:
                this.state=0b01;
                return true;
            default:
            }
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean removeVal(double val){
        switch(state){
        case 0b01:
            if(val == 0){
                this.state=0b00;
                return true;
            }
            break;
        case 0b10:
            if(val == 1){
                this.state=0b00;
                return true;
            }
            break;
        default:
            long bits;
            if((bits=Double.doubleToRawLongBits(val)) == 0 || bits == Long.MIN_VALUE){
                this.state=0b10;
                return true;
            }else if(bits == TypeUtil.DBL_TRUE_BITS){
                this.state=0b01;
                return true;
            }
            break;
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean isEmpty(){
        return this.state == 0b00;
    }
    @Override
    public int size(){
        switch(this.state){
        case 0b00:
            return 0;
        case 0b01:
        case 0b10:
            return 1;
        default:
            return 2;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        T[] arr;
        switch(state){
        case 0b00:
            return arrConstructor.apply(0);
        case 0b10:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
            return arr;
        default:
            (arr=arrConstructor.apply(2))[1]=(T)Boolean.TRUE;
            break;
        case 0b01:
            arr=arrConstructor.apply(1);
        }
        arr[0]=(T)Boolean.FALSE;
        return arr;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] dst){
        switch(state){
        case 0b00:
            if(dst.length != 0){
                dst[0]=null;
            }
            return dst;
        case 0b10:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
            return dst;
        default:
            (dst=OmniArray.uncheckedArrResize(2,dst))[1]=(T)Boolean.TRUE;
            break;
        case 0b01:
            dst=OmniArray.uncheckedArrResize(1,dst);
        }
        dst[0]=(T)Boolean.FALSE;
        return dst;
    }
    @Override
    public boolean add(boolean val){
        switch(state){
        case 0b00:
            this.state=val?0b10:0b01;
            return true;
        case 0b01:
            if(val){
                this.state=0b11;
                return true;
            }
            break;
        case 0b10:
            if(!val){
                this.state=0b11;
                return true;
            }
        default:
        }
        return false;
    }
    @Override
    public boolean add(Boolean val){
        return add((boolean)val);
    }
    @Override
    public void forEach(BooleanConsumer action){
        switch(state){
        case 0b11:
            action.accept(false);
        case 0b10:
            action.accept(true);
            break;
        case 0b01:
            action.accept(false);
        case 0b00:
        }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        switch(state){
        case 0b11:
            action.accept(Boolean.FALSE);
        case 0b10:
            action.accept(Boolean.TRUE);
            break;
        case 0b01:
            action.accept(Boolean.FALSE);
        case 0b00:
        }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter){
        switch(state){
        case 0b11:
            if(filter.test(false)){
                this.state=filter.test(true)?0b00:0b10;
                return true;
            }else if(filter.test(true)){
                this.state=0b01;
                return true;
            }
            break;
        case 0b10:
            if(filter.test(true)){
                this.state=0b00;
                return true;
            }
            break;
        case 0b01:
            if(filter.test(false)){
                this.state=0b00;
                return true;
            }
        case 0b00:
        }
        return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter){
        switch(state){
        case 0b11:
            if(filter.test(Boolean.FALSE)){
                this.state=filter.test(Boolean.TRUE)?0b00:0b10;
                return true;
            }else if(filter.test(Boolean.TRUE)){
                this.state=0b01;
                return true;
            }
            break;
        case 0b10:
            if(filter.test(Boolean.TRUE)){
                this.state=0b00;
                return true;
            }
            break;
        case 0b01:
            if(filter.test(Boolean.FALSE)){
                this.state=0b00;
                return true;
            }
        case 0b00:
        }
        return false;
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        return new Itr(this);
    }
    private static class Itr extends AbstractBooleanItr{
        private final BooleanSetImpl root;
        private int itrState;
        private Itr(BooleanSetImpl root){
            this.root=root;
            this.itrState=root.state;
        }
        @Override
        public void remove(){
            if(itrState == 0b00){
                final BooleanSetImpl root;
                if((root=this.root).state == 0b11){
                    root.state=0b01;
                }else{
                    root.state=0b00;
                }
            }else{
                // itrState assumed to be 0b10, meaning previous return was false and root state
                // is 0b11
                root.state=0b10;
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action){
            switch(itrState){
            default:
                action.accept(false); // assumed root state is 0b11
            case 0b10:
                action.accept(true); // assumed root state is either 0b11 or 0b10
                break;
            case 0b01:
                action.accept(false); // assumed root state is 0b01;
            case 0b00:
                // iterator is depleted
            }
            this.itrState=0b00;
        }
        @Override
        public void forEachRemaining(BooleanConsumer action){
            switch(itrState){
            default:
                action.accept(Boolean.FALSE); // assumed root state is 0b11
            case 0b10:
                action.accept(Boolean.TRUE); // assumed root state is either 0b11 or 0b10
                break;
            case 0b01:
                action.accept(Boolean.FALSE); // assumed root state is 0b01;
            case 0b00:
                // iterator is depleted
            }
            this.itrState=0b00;
        }
        @Override
        public boolean nextBoolean(){
            switch(itrState){
            default:
                this.itrState=0b10; // assumed root state is 0b11
                return false;
            case 0b01:
                this.itrState=0b00; // assumed root state is 0b01
                return false;
            case 0b10:
                this.itrState=0b00; // assumed root state is either 0b11 or 0b10
                return true;
            }
        }
        @Override
        public boolean hasNext(){
            return this.itrState != 0b00;
        }
    }
    @Override
    public Boolean[] toArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        case 0b01:
            return new Boolean[]{Boolean.FALSE};
        case 0b10:
            return new Boolean[]{Boolean.TRUE};
        default:
            return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
        }
    }
    @Override
    public boolean[] toBooleanArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfBoolean.DEFAULT_ARR;
        case 0b01:
            return new boolean[]{false};
        case 0b10:
            return new boolean[]{true};
        default:
            return new boolean[]{false,true};
        }
    }
    @Override
    public double[] toDoubleArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfDouble.DEFAULT_ARR;
        case 0b01:
            return new double[]{0};
        case 0b10:
            return new double[]{1};
        default:
            return new double[]{0,1};
        }
    }
    @Override
    public float[] toFloatArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfFloat.DEFAULT_ARR;
        case 0b01:
            return new float[]{0};
        case 0b10:
            return new float[]{1};
        default:
            return new float[]{0,1};
        }
    }
    @Override
    public long[] toLongArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfLong.DEFAULT_ARR;
        case 0b01:
            return new long[]{0};
        case 0b10:
            return new long[]{1};
        default:
            return new long[]{0,1};
        }
    }
    @Override
    public int[] toIntArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfInt.DEFAULT_ARR;
        case 0b01:
            return new int[]{0};
        case 0b10:
            return new int[]{1};
        default:
            return new int[]{0,1};
        }
    }
    @Override
    public short[] toShortArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfShort.DEFAULT_ARR;
        case 0b01:
            return new short[]{0};
        case 0b10:
            return new short[]{1};
        default:
            return new short[]{0,1};
        }
    }
    @Override
    public byte[] toByteArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfByte.DEFAULT_ARR;
        case 0b01:
            return new byte[]{0};
        case 0b10:
            return new byte[]{1};
        default:
            return new byte[]{0,1};
        }
    }
    @Override
    public char[] toCharArray(){
        switch(state){
        case 0b00:
            return OmniArray.OfChar.DEFAULT_ARR;
        case 0b01:
            return new char[]{0};
        case 0b10:
            return new char[]{1};
        default:
            return new char[]{0,1};
        }
    }
    public static class Checked extends BooleanSetImpl{
        Checked(){
            super();
        }
        Checked(int state){
            super(state);
        }
        private void checkModCount(int expectedState){
            int actualState;
            if((actualState=this.state) != expectedState){
                throw new ConcurrentModificationException(
                        "Expected state " + expectedState + " but was " + actualState);
            }
        }
        @Override
        public Object clone(){
            return new Checked(state);
        }
        @Override
        public boolean equals(Object val){
            // TODO Auto-generated method stub
            return super.equals(val);
        }
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            T[] arr;
            switch(state){
            case 0b00:
                try{
                    return arrConstructor.apply(0);
                }finally{
                    checkModCount(0b00);
                }
            case 0b10:
                try{
                    (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
                }finally{
                    checkModCount(0b10);
                }
                return arr;
            default:
                try{
                    (arr=arrConstructor.apply(2))[1]=(T)Boolean.TRUE;
                }finally{
                    checkModCount(0b11);
                }
                break;
            case 0b01:
                try{
                    arr=arrConstructor.apply(1);
                }finally{
                    checkModCount(0b01);
                }
            }
            arr[0]=(T)Boolean.FALSE;
            return arr;
        }
        @Override
        public void forEach(BooleanConsumer action){
            switch(state){
            case 0b11:
                try{
                    action.accept(false);
                    action.accept(true);
                }finally{
                    checkModCount(0b11);
                }
                break;
            case 0b10:
                try{
                    action.accept(true);
                }finally{
                    checkModCount(0b10);
                }
                break;
            case 0b01:
                try{
                    action.accept(false);
                }finally{
                    checkModCount(0b01);
                }
            case 0b00:
            }
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            switch(state){
            case 0b11:
                try{
                    action.accept(Boolean.FALSE);
                    action.accept(Boolean.TRUE);
                }finally{
                    checkModCount(0b11);
                }
                break;
            case 0b10:
                try{
                    action.accept(Boolean.TRUE);
                }finally{
                    checkModCount(0b10);
                }
                break;
            case 0b01:
                try{
                    action.accept(Boolean.FALSE);
                }finally{
                    checkModCount(0b01);
                }
            case 0b00:
            }
        }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            switch(state){
            case 0b11:
                boolean removeFalse=filter.test(false);
                boolean removeTrue=filter.test(true);
                checkModCount(0b11);
                if(removeFalse){
                    this.state=removeTrue?0b00:0b10;
                    return true;
                }else if(removeTrue){
                    this.state=0b01;
                    return true;
                }
                break;
            case 0b10:
                removeTrue=filter.test(true);
                checkModCount(0b10);
                if(removeTrue){
                    this.state=0b00;
                    return true;
                }
                break;
            case 0b01:
                removeFalse=filter.test(false);
                checkModCount(0b01);
                if(removeFalse){
                    this.state=0b00;
                    return true;
                }
            case 0b00:
            }
            return false;
        }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            switch(state){
            case 0b11:
                boolean removeFalse=filter.test(Boolean.FALSE);
                boolean removeTrue=filter.test(Boolean.TRUE);
                checkModCount(0b11);
                if(removeFalse){
                    this.state=removeTrue?0b00:0b10;
                    return true;
                }else if(removeTrue){
                    this.state=0b01;
                    return true;
                }
                break;
            case 0b10:
                removeTrue=filter.test(Boolean.TRUE);
                checkModCount(0b10);
                if(removeTrue){
                    this.state=0b00;
                    return true;
                }
                break;
            case 0b01:
                removeFalse=filter.test(Boolean.FALSE);
                checkModCount(0b01);
                if(removeFalse){
                    this.state=0b00;
                    return true;
                }
            case 0b00:
            }
            return false;
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new CheckedItr(this);
        }
        private static class CheckedItr extends AbstractBooleanItr{
            private final Checked root;
            private int itrState;
            CheckedItr(Checked root){
                this.root=root;
                this.itrState=root.state;
            }
            @Override
            public void remove(){
                int itrState;
                final Checked root;
                (root=this.root).checkModCount((itrState=this.itrState) & 0b11);
                switch(itrState){
                case 0b0101: // remove false
                    root.state=0b00;
                    this.itrState=0b0100;
                    return;
                case 0b0111: // remove false
                    root.state=0b10;
                    this.itrState=0b0110;
                    return;
                case 0b1010: // remove true
                    root.state=0b00;
                    this.itrState=0b1000;
                    return;
                case 0b1110: // remove false
                    root.state=0b00;
                    this.itrState=0b1100;
                    return;
                case 0b1111: // remove true
                    root.state=0b01;
                    this.itrState=0b1101;
                    return;
                default:
                    throw new IllegalStateException();
                }
            }
            @Override
            public void forEachRemaining(Consumer<? super Boolean> action){
                switch(this.itrState){
                case 0b0001:
                    try{
                        action.accept(Boolean.FALSE);
                    }finally{
                        root.checkModCount(0b01);
                    }
                    itrState=0b0101;
                    break;
                case 0b0010:
                    try{
                        action.accept(Boolean.TRUE);
                    }finally{
                        root.checkModCount(0b10);
                    }
                    itrState=0b1010;
                    break;
                case 0b0011:
                    try{
                        action.accept(Boolean.FALSE);
                        action.accept(Boolean.TRUE);
                    }finally{
                        root.checkModCount(0b11);
                    }
                    itrState=0b1111;
                    break;
                case 0b0110:
                    try{
                        action.accept(Boolean.TRUE);
                    }finally{
                        root.checkModCount(0b10);
                    }
                    itrState=0b1110;
                    break;
                case 0b0111:
                    try{
                        action.accept(Boolean.TRUE);
                    }finally{
                        root.checkModCount(0b11);
                    }
                    itrState=0b1111;
                default:
                }
            }
            @Override
            public void forEachRemaining(BooleanConsumer action){
                switch(this.itrState){
                case 0b0001:
                    try{
                        action.accept(false);
                    }finally{
                        root.checkModCount(0b01);
                    }
                    itrState=0b0101;
                    break;
                case 0b0010:
                    try{
                        action.accept(true);
                    }finally{
                        root.checkModCount(0b10);
                    }
                    itrState=0b1010;
                    break;
                case 0b0011:
                    try{
                        action.accept(false);
                        action.accept(true);
                    }finally{
                        root.checkModCount(0b11);
                    }
                    itrState=0b1111;
                    break;
                case 0b0110:
                    try{
                        action.accept(true);
                    }finally{
                        root.checkModCount(0b10);
                    }
                    itrState=0b1110;
                    break;
                case 0b0111:
                    try{
                        action.accept(true);
                    }finally{
                        root.checkModCount(0b11);
                    }
                    itrState=0b1111;
                default:
                }
            }
            @Override
            public boolean nextBoolean(){
                int itrState;
                root.checkModCount((itrState=this.itrState) & 0b11);
                switch(itrState){
                case 0b0001:
                    this.itrState=0b0101;
                    return false;
                case 0b0010:
                    this.itrState=0b1010;
                    return true;
                case 0b0011:
                    this.itrState=0b0111;
                    return false;
                case 0b0110:
                    this.itrState=0b1110;
                    return true;
                case 0b0111:
                    this.itrState=0b1111;
                    return true;
                default:
                    throw new NoSuchElementException();
                }
            }
            @Override
            public boolean hasNext(){
                switch(itrState){
                case 0b0001:
                case 0b0010:
                case 0b0011:
                case 0b0110:
                case 0b0111:
                    return true;
                default:
                    return false;
                }
            }
        }
    }
}
