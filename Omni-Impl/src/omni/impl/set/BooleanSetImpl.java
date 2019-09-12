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
import omni.api.OmniNavigableSet;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;
import omni.util.TypeUtil;
public class BooleanSetImpl implements OmniNavigableSet.OfBoolean,Cloneable,Serializable{
    private static final byte CHECKED_MASK=0b00100000;
    private static final byte REVERSE_MASK=0b00000100;
    private static final byte TRUE_MASK   =0b00000010;
    private static final byte FALSE_MASK  =0b00000001;
    public static BooleanSetImpl newCheckedAscending(){
        return new BooleanSetImpl(CHECKED_MASK);
    }
    public static BooleanSetImpl newCheckedDescending(){
        return new BooleanSetImpl(CHECKED_MASK|REVERSE_MASK);
    }
    public static BooleanSetImpl newUncheckedAscending(){
        return new BooleanSetImpl();
    }
    public static BooleanSetImpl newUncheckedDescending(){
        return new BooleanSetImpl(REVERSE_MASK);
    }
    private static int reverseCompare(boolean v1,boolean v2){
        return v1 == v2?0:v1?-1:1;
    }
    transient int state;
    private BooleanSetImpl(){
    }
    private BooleanSetImpl(int state){
        this.state=state;
    }
    @Override
    public boolean add(boolean val){
        int state;
        if((state=this.state) != (state&=val?TRUE_MASK:FALSE_MASK)){
            this.state=state;
            return true;
        }
        return false;
    }
    @Override
    public boolean add(Boolean e){
        return this.add((boolean)e);
    }
    @Override
    public boolean booleanCeiling(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case TRUE_MASK:
            return true;
        case REVERSE_MASK|TRUE_MASK:
        case TRUE_MASK|FALSE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return val;
        default:
            return false;
        }
    }
    @Override
    public boolean booleanFloor(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            return true;
        case TRUE_MASK:
            // ascending contains true
        case TRUE_MASK|FALSE_MASK:
            // ascending full
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            return val;
        default:
            return false;
        }
    }
    @Override
    public Boolean ceiling(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            if(val){
                break;
            }
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            return Boolean.FALSE;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            if(!val){
                break;
            }
        case TRUE_MASK:
            // ascending contains true
            return Boolean.TRUE;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            return val;
        default:
            // empty
        }
        return null;
    }
    @Override
    public void clear(){
        state&=CHECKED_MASK|REVERSE_MASK;
    }
    @Override
    public Object clone(){
        return new BooleanSetImpl(state);
    }
    @Override
    public BooleanComparator comparator(){
        if((state & REVERSE_MASK) == 0){
            return Boolean::compare;
        }else{
            return BooleanSetImpl::reverseCompare;
        }
    }
    @Override
    public boolean contains(boolean val){
        return (state & (val?TRUE_MASK:FALSE_MASK)) != 0;
    }
    @Override
    public boolean contains(double val){
        final long bits;
        if((bits=Double.doubleToRawLongBits(val)) == 0L || bits == Long.MIN_VALUE){
            return (state & FALSE_MASK) != 0;
        }else if(bits == TypeUtil.DBL_TRUE_BITS){
            return (state & TRUE_MASK) != 0;
        }
        return false;
    }
    @Override
    public boolean contains(float val){
        switch(Float.floatToRawIntBits(val)){
        case 0:
        case Integer.MIN_VALUE:
            return (state & FALSE_MASK) != 0;
        case TypeUtil.FLT_TRUE_BITS:
            return (state & TRUE_MASK) != 0;
        default:
            return false;
        }
    }
    @Override
    public boolean contains(int val){
        switch(val){
        case 0:
            return (state & FALSE_MASK) != 0;
        case 1:
            return (state & TRUE_MASK) != 0;
        default:
            return false;
        }
    }
    @Override
    public boolean contains(long val){
        if(val == 0L){
            return (state & FALSE_MASK) != 0;
        }else if(val == 1L){
            return (state & TRUE_MASK) != 0;
        }
        return false;
    }
    @Override
    public boolean contains(Object val){
        switch(state & (TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            return containsFalseState(val);
        case TRUE_MASK:
            return containsTrueState(val);
        case TRUE_MASK|FALSE_MASK:
            return containsFullState(val);
        default:
            return false;
        }
    }
    @Override
    public OmniIterator.OfBoolean descendingIterator(){
        switch(this.state>>>2) {
        case CHECKED_MASK>>>2:
            return new CheckedDescendingItr(this);
        case REVERSE_MASK>>>2:
            return new UncheckedAscendingItr(this);
        case (CHECKED_MASK|REVERSE_MASK)>>>2:
            return new CheckedAscendingItr(this);
        default:
            return new UncheckedDescendingItr(this);  
        }
    }
    @Override
    public OmniNavigableSet.OfBoolean descendingSet(){
        return new ReverseView(this);
    }
    @Override
    public boolean firstBoolean(){
        switch(state&(REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case TRUE_MASK|FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return false;
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return true;
        default:
            throw new NoSuchElementException();
        }
    }
    @Override
    public Boolean floor(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case REVERSE_MASK|FALSE_MASK:
            if(val){
                break;
            }
        case FALSE_MASK:
            return Boolean.FALSE;
        case TRUE_MASK:
            if(!val){
                break;
            }
        case REVERSE_MASK|TRUE_MASK:
            return Boolean.TRUE;
        case TRUE_MASK|FALSE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return val;
        default:
        }
        return null;
    }
    @Override
    public void forEach(BooleanConsumer action){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            action.accept(false);
        case TRUE_MASK:
            // ascending contains true
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            action.accept(true);
            break;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            action.accept(true);
        case FALSE_MASK:
            // ascending contains false
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            action.accept(false);
        default:
            // do nothing
        }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            action.accept(Boolean.FALSE);
        case TRUE_MASK:
            // ascending contains true
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            action.accept(Boolean.TRUE);
            break;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            action.accept(Boolean.TRUE);
        case FALSE_MASK:
            // ascending contains false
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            action.accept(Boolean.FALSE);
        default:
            // do nothing
        }
    }
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        // TODO Auto-generated method stub
        return null;
    }
    private static abstract class TrueView implements OmniNavigableSet.OfBoolean,Cloneable,Serializable{
        final BooleanSetImpl root;
        private TrueView(BooleanSetImpl root) {
            this.root=root;
        }
        @Override public void forEach(BooleanConsumer action) {
            if((root.state&TRUE_MASK)!=0) {
                action.accept(true);
            }
        }
        @Override public boolean contains(boolean val) {
           return val && (root.state&TRUE_MASK)!=0;
        }
        @Override public boolean contains(long val) {
            return val==1L && (root.state&TRUE_MASK)!=0;
         }
        @Override public boolean contains(double val) {
            return val==1D && (root.state&TRUE_MASK)!=0;
         }
        @Override public boolean contains(float val) {
            return val==1F && (root.state&TRUE_MASK)!=0;
         }
        @Override public boolean contains(int val) {
            return val==1 && (root.state&TRUE_MASK)!=0;
         }
        @Override public boolean removeIf(BooleanPredicate filter) {
            final int rootState;
            final BooleanSetImpl root;
            if(((rootState=(root=this.root).state)&TRUE_MASK)!=0 && filter.test(true)) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeIf(Predicate<? super Boolean> filter) {
            final int rootState;
            final BooleanSetImpl root;
            if(((rootState=(root=this.root).state)&TRUE_MASK)!=0 && filter.test(Boolean.TRUE)) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public void clear() {
            root.state&=CHECKED_MASK|REVERSE_MASK|FALSE_MASK;
        }
        @Override public boolean add(Boolean val) {
            return this.add((boolean)val);
        }
        @Override public boolean add(boolean val) {
            if(val) {
                final int rootState;
                final BooleanSetImpl root;
                return (rootState=(root=this.root).state)!=(root.state=rootState|TRUE_MASK);
            }
            throw new IllegalArgumentException("The value "+val+" is outside the range of this subset");
        }
        @Override public Object clone() {
            return new BooleanSetImpl(root.state&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
        }
        @Override public void forEach(Consumer<? super Boolean> action) {
            if((root.state&TRUE_MASK)!=0) {
                action.accept(Boolean.TRUE);
            }
        }
        @Override public boolean lastBoolean() {
            if((root.state&TRUE_MASK)!=0) {
                return true;
            }
            throw new NoSuchElementException();
        }
        @Override public boolean firstBoolean() {
            if((root.state&TRUE_MASK)!=0) {
                return true;
            }
            throw new NoSuchElementException();
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            //TODO
            return null;
        }
        @Override public OmniIterator.OfBoolean descendingIterator(){
            //TODO
            return null;
        }
        @Override public OmniIterator.OfBoolean iterator(){
            //TODO
            return null;
        }
        @Override public boolean isEmpty() {
            return (root.state&TRUE_MASK)==0;
        }
        @Override public int size() {
            return root.state>>>1&1;
        }
        @Override public boolean removeVal(boolean val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val && ((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(int val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==1 && ((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(long val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==1L && ((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(float val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==1F && ((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(double val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==1D && ((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean contains(Object val) {
                if((root.state&TRUE_MASK)!=0) {
                    if(val instanceof Boolean){
                        return (boolean)val;
                    }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                        return ((Number)val).intValue() == 1;
                    }else if(val instanceof Float){
                        return (float)val == 1F;
                    }else if(val instanceof Double){
                        return (double)val == 1D;
                    }else if(val instanceof Long){
                        return (long)val == 1L;
                    }else if(val instanceof Character){
                        return (char)val==1;
                    }
                }
                return false;
            
            
            
        }
        @Override public boolean remove(Object val) {
            final BooleanSetImpl root;
            final int rootState;
            remove:for(;;) {
                if(((rootState=(root=this.root).state)&TRUE_MASK)!=0) {
                    if(val instanceof Boolean){
                        if((boolean)val){
                            break remove;
                        }
                    }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                        if(((Number)val).intValue() == 1){
                            break remove;
                        }
                    }else if(val instanceof Float){
                        if((float)val == 1F){
                            break remove;
                        }
                    }else if(val instanceof Double){
                        if((double)val == 1D){
                            break remove;
                        }
                    }else if(val instanceof Long){
                        if((long)val == 1L){
                            break remove;
                        }
                    }else if(val instanceof Character){
                        if((char)val == 1){
                            break remove;
                        }
                    }
                }
                return false;
            }
            root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
            return true;
            
        }
        @Override public boolean lowerBoolean(boolean val) {
            return false;
        }
        @Override public Boolean lower(boolean val) {
            return null;
        }
        @Override public boolean higherBoolean(boolean val) {
            return !val && (root.state&(REVERSE_MASK|TRUE_MASK))==(REVERSE_MASK|TRUE_MASK);
        }
        @Override public Boolean higher(boolean val) {
            if(!val && (root.state&(REVERSE_MASK|TRUE_MASK))==(REVERSE_MASK|TRUE_MASK)){
                return Boolean.TRUE;
            }
            return null;
        }
        @Override public boolean booleanFloor(boolean val) {
            switch(root.state>>>1&(REVERSE_MASK|TRUE_MASK)>>>1){
            case TRUE_MASK>>>1:
               return val;
            case (REVERSE_MASK|TRUE_MASK)>>>1:
               return true;
            default:
               return false;
            }
        }
        @Override public boolean booleanCeiling(boolean val) {
            switch(root.state>>>1&(REVERSE_MASK|TRUE_MASK)>>>1){
            case (REVERSE_MASK|TRUE_MASK)>>>1:
                return val;
            case TRUE_MASK>>>1:
                return true;
            default:
                return false;
            }
            
        }
        @Override public Boolean ceiling(boolean val) {
            switch(root.state>>>1&(REVERSE_MASK|TRUE_MASK)>>>1){
            case (REVERSE_MASK|TRUE_MASK)>>>1:
              if(!val) {
                  break;
              }
            case TRUE_MASK>>>1:
                return Boolean.TRUE;
            default:
            }
            return null;
        }
        @Override public Boolean floor(boolean val) {
            switch(root.state>>>1&(REVERSE_MASK|TRUE_MASK)>>>1){
            case TRUE_MASK>>>1:
              if(!val) {
                  break;
              }
            case (REVERSE_MASK|TRUE_MASK)>>>1:
               return Boolean.TRUE;
            default:
            }
            return null;
        }
        @Override public boolean pollFirstBoolean() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return false;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
        }
        @Override public boolean pollLastBoolean() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return false;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return true;
            }
        }
        @Override public byte pollFirstByte() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Byte.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public byte pollLastByte() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Byte.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public char pollFirstChar() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Character.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public char pollLastChar() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Character.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public short pollFirstShort() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Short.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public short pollLastShort() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Short.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public int pollFirstInt() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Integer.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public int pollLastInt() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Integer.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1;
            }
        }
        @Override public long pollFirstLong() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Long.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1L;
            }
        }
        @Override public long pollLastLong() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Long.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1L;
            }
        }
        @Override public float pollFirstFloat() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Float.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1F;
            }
        }
        @Override public float pollLastFloat() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Float.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1F;
            }
        }
        @Override public double pollFirstDouble() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Double.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1D;
            }
        }
        @Override public double pollLastDouble() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return Double.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return 1D;
            }
        }
        @Override public Boolean pollFirst() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return null;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return Boolean.TRUE;
            }
        }
        @Override public Boolean pollLast() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&TRUE_MASK)==0) {
                return null;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                return Boolean.TRUE;
            }
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
            //TODO
            return null;
        }
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(IntFunction<T[]> arrConstructor) {
            if((root.state&TRUE_MASK)==0) {
                return arrConstructor.apply(0);
            }else {
                final T[] arr;
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
                return arr;
            }
        }
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] arr) {
            if((root.state&TRUE_MASK)==0) {
                if(arr.length!=0) {
                    arr[0]=null;
                }
            }else {
                (arr=OmniArray.uncheckedArrResize(1,arr))[0]=(T)Boolean.TRUE;
            }
            return arr;
        }
        @Override
        public boolean[] toBooleanArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfBoolean.DEFAULT_ARR;
            }else {
                return new boolean[] {true};
            }
        }
        @Override
        public Boolean[] toArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
            }else {
                return new Boolean[] {Boolean.TRUE};
            }
        }
        @Override
        public byte[] toByteArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfByte.DEFAULT_ARR;
            }else {
                return new byte[] {1};
            }
        }
        @Override
        public char[] toCharArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfChar.DEFAULT_ARR;
            }else {
                return new char[] {1};
            }
        }
        @Override
        public short[] toShortArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfShort.DEFAULT_ARR;
            }else {
                return new short[] {1};
            }
        }
        @Override
        public int[] toIntArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfInt.DEFAULT_ARR;
            }else {
                return new int[] {1};
            }
        }
        @Override
        public long[] toLongArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfLong.DEFAULT_ARR;
            }else {
                return new long[] {1L};
            }
        }
        @Override
        public float[] toFloatArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfFloat.DEFAULT_ARR;
            }else {
                return new float[] {1F};
            }
        }
        @Override
        public double[] toDoubleArray() {
            if((root.state&TRUE_MASK)==0) {
                return OmniArray.OfDouble.DEFAULT_ARR;
            }else {
                return new double[] {1D};
            }
        }
    }
    
    private static abstract class FalseView implements OmniNavigableSet.OfBoolean,Cloneable,Serializable{
        final BooleanSetImpl root;
        private FalseView(BooleanSetImpl root) {
            this.root=root;
        }
        @Override public void forEach(BooleanConsumer action) {
            if((root.state&FALSE_MASK)!=0) {
                action.accept(false);
            }
        }
        @Override public boolean contains(boolean val) {
           return !val && (root.state&FALSE_MASK)!=0;
        }
        @Override public boolean contains(long val) {
            return val==0L && (root.state&FALSE_MASK)!=0;
         }
        @Override public boolean contains(double val) {
            return val==0D && (root.state&FALSE_MASK)!=0;
         }
        @Override public boolean contains(float val) {
            return val==0F && (root.state&FALSE_MASK)!=0;
         }
        @Override public boolean contains(int val) {
            return val==0 && (root.state&FALSE_MASK)!=0;
         }
        @Override public boolean removeIf(BooleanPredicate filter) {
            final int rootState;
            final BooleanSetImpl root;
            if(((rootState=(root=this.root).state)&FALSE_MASK)!=0 && filter.test(false)) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeIf(Predicate<? super Boolean> filter) {
            final int rootState;
            final BooleanSetImpl root;
            if(((rootState=(root=this.root).state)&FALSE_MASK)!=0 && filter.test(Boolean.FALSE)) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public void clear() {
            root.state&=CHECKED_MASK|REVERSE_MASK|TRUE_MASK;
        }
        @Override public boolean add(Boolean val) {
            return this.add((boolean)val);
        }
        @Override public boolean add(boolean val) {
            if(!val) {
                final int rootState;
                final BooleanSetImpl root;
                return (rootState=(root=this.root).state)!=(root.state=rootState|FALSE_MASK);
            }
            throw new IllegalArgumentException("The value "+val+" is outside the range of this subset");
        }
        @Override public Object clone() {
            return new BooleanSetImpl(root.state&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK));
        }
        @Override public void forEach(Consumer<? super Boolean> action) {
            if((root.state&FALSE_MASK)!=0) {
                action.accept(Boolean.FALSE);
            }
        }
        @Override public boolean lastBoolean() {
            if((root.state&FALSE_MASK)!=0) {
                return false;
            }
            throw new NoSuchElementException();
        }
        @Override public boolean firstBoolean() {
            if((root.state&FALSE_MASK)!=0) {
                return false;
            }
            throw new NoSuchElementException();
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            //TODO
            return null;
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            //TODO
            return null;
        }
        @Override public OmniIterator.OfBoolean descendingIterator(){
            //TODO
            return null;
        }
        @Override public OmniIterator.OfBoolean iterator(){
            //TODO
            return null;
        }
        @Override public boolean isEmpty() {
            return (root.state&FALSE_MASK)==0;
        }
        @Override public int size() {
            return root.state&1;
        }
        @Override public boolean removeVal(boolean val) {
            final BooleanSetImpl root;
            final int rootState;
            if(!val && ((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(int val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==0 && ((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(long val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==0L && ((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(float val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==0F && ((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean removeVal(double val) {
            final BooleanSetImpl root;
            final int rootState;
            if(val==0D && ((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return true;
            }
            return false;
        }
        @Override public boolean contains(Object val) {
                if((root.state&FALSE_MASK)!=0) {
                    if(val instanceof Boolean){
                        return !(boolean)val;
                    }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                        return ((Number)val).intValue() == 0;
                    }else if(val instanceof Float){
                        return (float)val == 0F;
                    }else if(val instanceof Double){
                        return (double)val == 0D;
                    }else if(val instanceof Long){
                        return (long)val == 0L;
                    }else if(val instanceof Character){
                        return (char)val==0;
                    }
                }
                return false;
            
            
            
        }
        @Override public boolean remove(Object val) {
            final BooleanSetImpl root;
            final int rootState;
            remove:for(;;) {
                if(((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                    if(val instanceof Boolean){
                        if(!(boolean)val){
                            break remove;
                        }
                    }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                        if(((Number)val).intValue() == 0){
                            break remove;
                        }
                    }else if(val instanceof Float){
                        if((float)val == 0F){
                            break remove;
                        }
                    }else if(val instanceof Double){
                        if((double)val == 0D){
                            break remove;
                        }
                    }else if(val instanceof Long){
                        if((long)val == 0L){
                            break remove;
                        }
                    }else if(val instanceof Character){
                        if((char)val == 0){
                            break remove;
                        }
                    }
                }
                return false;
            }
            root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
            return true;
            
        }
        @Override public boolean lowerBoolean(boolean val) {
            return false;
        }
        @Override public Boolean lower(boolean val) {
            if(val && (root.state&(REVERSE_MASK|FALSE_MASK))==FALSE_MASK) {
                return Boolean.FALSE;
            }
            return null;
        }
        @Override public boolean higherBoolean(boolean val) {
            return false;
        }
        @Override public Boolean higher(boolean val) {
            if(val && (root.state&(REVERSE_MASK|FALSE_MASK))==(REVERSE_MASK|FALSE_MASK)) {
                return Boolean.FALSE;
            }
            return null;
        }
        @Override public boolean booleanFloor(boolean val) {
            return false;
        }
        @Override public boolean booleanCeiling(boolean val) {
            return false;
        }
        @Override public Boolean ceiling(boolean val) {
            switch(root.state&(REVERSE_MASK|FALSE_MASK)){
            case FALSE_MASK:
                if(val) {
                    break;
                }
            case REVERSE_MASK|FALSE_MASK:
                return Boolean.FALSE;
            
            default:
            }
            return null;
        }
        @Override public Boolean floor(boolean val) {
            switch(root.state&(REVERSE_MASK|FALSE_MASK)){
            case REVERSE_MASK|FALSE_MASK:
                if(val) {
                   break; 
                }
            case FALSE_MASK:
                return Boolean.FALSE;
            default:
            }
            return null;
        }
        @Override public boolean pollFirstBoolean() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
            }
            return false;
        }
        @Override public boolean pollLastBoolean() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)!=0) {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
            }
            return false;
        }
        @Override public byte pollFirstByte() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Byte.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public byte pollLastByte() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Byte.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public char pollFirstChar() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Character.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public char pollLastChar() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Character.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public short pollFirstShort() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Short.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public short pollLastShort() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Short.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public int pollFirstInt() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Integer.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public int pollLastInt() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Integer.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0;
            }
        }
        @Override public long pollFirstLong() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Long.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0L;
            }
        }
        @Override public long pollLastLong() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Long.MIN_VALUE;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0L;
            }
        }
        @Override public float pollFirstFloat() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Float.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0F;
            }
        }
        @Override public float pollLastFloat() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Float.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0F;
            }
        }
        @Override public double pollFirstDouble() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Double.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0D;
            }
        }
        @Override public double pollLastDouble() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return Double.NaN;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return 0D;
            }
        }
        @Override public Boolean pollFirst() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return null;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return Boolean.FALSE;
            }
        }
        @Override public Boolean pollLast() {
            final BooleanSetImpl root;
            final int rootState;
            if(((rootState=(root=this.root).state)&FALSE_MASK)==0) {
                return null;
            }else {
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                return Boolean.FALSE;
            }
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
            //TODO
            return null;
        }
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(IntFunction<T[]> arrConstructor) {
            if((root.state&FALSE_MASK)==0) {
                return arrConstructor.apply(0);
            }else {
                final T[] arr;
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
                return arr;
            }
        }
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] arr) {
            if((root.state&FALSE_MASK)==0) {
                if(arr.length!=0) {
                    arr[0]=null;
                }
            }else {
                (arr=OmniArray.uncheckedArrResize(1,arr))[0]=(T)Boolean.FALSE;
            }
            return arr;
        }
        @Override
        public boolean[] toBooleanArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfBoolean.DEFAULT_ARR;
            }else {
                return new boolean[] {false};
            }
        }
        @Override
        public Boolean[] toArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
            }else {
                return new Boolean[] {Boolean.FALSE};
            }
        }
        @Override
        public byte[] toByteArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfByte.DEFAULT_ARR;
            }else {
                return new byte[] {0};
            }
        }
        @Override
        public char[] toCharArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfChar.DEFAULT_ARR;
            }else {
                return new char[] {0};
            }
        }
        @Override
        public short[] toShortArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfShort.DEFAULT_ARR;
            }else {
                return new short[] {0};
            }
        }
        @Override
        public int[] toIntArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfInt.DEFAULT_ARR;
            }else {
                return new int[] {0};
            }
        }
        @Override
        public long[] toLongArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfLong.DEFAULT_ARR;
            }else {
                return new long[] {0L};
            }
        }
        @Override
        public float[] toFloatArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfFloat.DEFAULT_ARR;
            }else {
                return new float[] {0F};
            }
        }
        @Override
        public double[] toDoubleArray() {
            if((root.state&FALSE_MASK)==0) {
                return OmniArray.OfDouble.DEFAULT_ARR;
            }else {
                return new double[] {0D};
            }
        }
    }
    
    private static abstract class EmptyView implements OmniNavigableSet.OfBoolean,Cloneable,Serializable{

        @Override
        public abstract Object clone();
        @Override public void forEach(BooleanConsumer action) {
            //nothing to do
        }
        @Override public boolean contains(boolean val) {
            return false;
        }
        @Override public boolean contains(long val) {
            return false;
            }
        @Override public boolean contains(double val) {
            return false;
            }
        @Override public boolean contains(float val) {
            return false;
            }
        @Override public boolean contains(int val) {
            return false;
            }
        @Override public boolean removeIf(BooleanPredicate filter) {
            return false;
        }
        @Override public boolean removeIf(Predicate<? super Boolean> filter) {
            return false;
            }
        @Override public void clear() {
            //nothing to do
        }
        @Override public boolean add(Boolean val) {
            return this.add((boolean)val);
        }
        @Override public boolean add(boolean val) {
            throw new IllegalArgumentException("The value "+val+" is outside the range of this subset");
        }
       
        @Override public void forEach(Consumer<? super Boolean> action) {
            //nothing to do
        }
        @Override public boolean lastBoolean() {
            throw new NoSuchElementException();
        }
        @Override public boolean firstBoolean() {
            throw new NoSuchElementException();
            }
        @Override public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            throw new IllegalArgumentException("The element "+fromElement+" is outside the range of allowed values for this subset");
        }
        @Override public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            throw new IllegalArgumentException("The element "+toElement+" is outside the range of allowed values for this subset");
            }
        @Override public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            throw new IllegalArgumentException("The range ["+fromElement+","+toElement+"] is not valid for this subset");
            }
        
        @Override public OmniIterator.OfBoolean descendingIterator(){
            //TODO
            return null;
        }
        @Override public OmniIterator.OfBoolean iterator(){
            //TODO
            return null;
        }
        @Override public boolean isEmpty() {
            return true;
        }
        @Override public int size() {
            return 0;
        }
        @Override public boolean removeVal(boolean val) {
            return false;
        }
        @Override public boolean removeVal(int val) {
            return false;
            }
        @Override public boolean removeVal(long val) {
            return false;
            }
        @Override public boolean removeVal(float val) {
            return false;
            }
        @Override public boolean removeVal(double val) {
            return false;
            }
        @Override public boolean contains(Object val) {
            return false;
            }
        @Override public boolean remove(Object val) {
            return false;
            }
        @Override public boolean lowerBoolean(boolean val) {
            return false;
        }
        @Override public Boolean lower(boolean val) {
            return null;
        }
        @Override public boolean higherBoolean(boolean val) {
            return false;
        }
        @Override public Boolean higher(boolean val) {
            return null;
            }
        @Override public boolean booleanFloor(boolean val) {
            return false;
        }
        @Override public boolean booleanCeiling(boolean val) {
            return false;
        }
        @Override public Boolean ceiling(boolean val) {
            return null;
            }
        @Override public Boolean floor(boolean val) {
            return null;
            }
        @Override public boolean pollFirstBoolean() {
            return false;
        }
        @Override public boolean pollLastBoolean() {
            return false;
            }
        @Override public byte pollFirstByte() {
            return Byte.MIN_VALUE;
        }
        @Override public byte pollLastByte() {
            return Byte.MIN_VALUE;
            }
        @Override public char pollFirstChar() {
            return Character.MIN_VALUE;
        }
        @Override public char pollLastChar() {
            return Character.MIN_VALUE;
            }
        @Override public short pollFirstShort() {
            return Short.MIN_VALUE;
        }
        @Override public short pollLastShort() {
            return Short.MIN_VALUE;
            }
        @Override public int pollFirstInt() {
            return Integer.MIN_VALUE;
        }
        @Override public int pollLastInt() {
            return Integer.MIN_VALUE;
            }
        @Override public long pollFirstLong() {
            return Long.MIN_VALUE;
        }
        @Override public long pollLastLong() {
            return Long.MIN_VALUE;
            }
        @Override public float pollFirstFloat() {
            return Float.NaN;
        }
        @Override public float pollLastFloat() {
            return Float.NaN;
            }
        @Override public double pollFirstDouble() {
            return Double.NaN;
        }
        @Override public double pollLastDouble() {
            return Double.NaN;
            }
        @Override public Boolean pollFirst() {
            return null;
        }
        @Override public Boolean pollLast() {
            return null;
            }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
            //TODO
            return null;
        }
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor) {
            return arrConstructor.apply(0);
        }
        @Override
        public <T> T[] toArray(T[] arr) {
            if(arr.length!=0) {
                arr[0]=null;
            }
            return arr;
        }
        @Override
        public boolean[] toBooleanArray() {
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
        @Override
        public Boolean[] toArray() {
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
            }
        @Override
        public byte[] toByteArray() {
            return OmniArray.OfByte.DEFAULT_ARR;
            }
        @Override
        public char[] toCharArray() {
            return OmniArray.OfChar.DEFAULT_ARR;
            }
        @Override
        public short[] toShortArray() {
            return OmniArray.OfShort.DEFAULT_ARR;
            }
        @Override
        public int[] toIntArray() {
            return OmniArray.OfInt.DEFAULT_ARR;
            }
        @Override
        public long[] toLongArray() {
            return OmniArray.OfLong.DEFAULT_ARR;
            }
        @Override
        public float[] toFloatArray() {
            return OmniArray.OfFloat.DEFAULT_ARR;
            }
        @Override
        public double[] toDoubleArray() {
            return OmniArray.OfDouble.DEFAULT_ARR;
            }
    }
    
    
    
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        //TODO
        return null;
    }
    @Override
    public Boolean higher(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case TRUE_MASK:
        case TRUE_MASK|FALSE_MASK:
            if(val){
                break;
            }
            return Boolean.TRUE;
        case REVERSE_MASK|FALSE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            if(!val){
                break;
            }
            return Boolean.FALSE;
        default:
        }
        return null;
    }
    @Override
    public boolean higherBoolean(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case TRUE_MASK:
            // ascending contains true
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            return !val;
        default:
            return false;
        }
    }
    @Override
    public boolean isEmpty(){
        return (state & (TRUE_MASK|FALSE_MASK)) == 0;
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        switch(this.state>>>2) {
        case CHECKED_MASK>>>2:
            return new CheckedAscendingItr(this);
        case REVERSE_MASK>>>2:
            return new UncheckedDescendingItr(this);
        case (CHECKED_MASK|REVERSE_MASK)>>>2:
            return new CheckedDescendingItr(this);
        default:
            return new UncheckedAscendingItr(this);  
        }
    }
    @Override
    public boolean lastBoolean(){
        switch(state&(REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return false;
        case TRUE_MASK:
        case TRUE_MASK|FALSE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return true;
        default:
            throw new NoSuchElementException();
        }
    }
    @Override
    public Boolean lower(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case TRUE_MASK|FALSE_MASK:
            if(!val){
                break;
            }
            return Boolean.FALSE;
        case REVERSE_MASK|TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            if(val){
                break;
            }
            return Boolean.TRUE;
        default:
        }
        return null;
    }
    @Override
    public boolean lowerBoolean(boolean val){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            return val;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            return !val;
        default:
            return false;
        }
    }
    @Override
    public Boolean pollFirst(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return Boolean.FALSE;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return Boolean.TRUE;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return Boolean.FALSE;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return Boolean.TRUE;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return Boolean.FALSE;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return Boolean.TRUE;
        default:
            // empty
            return null;
        }
    }
    @Override
    public boolean pollFirstBoolean(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return false;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return true;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return false;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return true;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return true;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
        default:
            // empty
            return false;
        }
    }
    @Override
    public byte pollFirstByte(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1;
        default:
            // empty
            return Byte.MIN_VALUE;
        }
    }
    @Override
    public char pollFirstChar(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
        default:
            // empty
            return Character.MIN_VALUE;
        }
    }
    @Override
    public double pollFirstDouble(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0D;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1D;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0D;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1D;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0D;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1D;
        default:
            // empty
            return Double.NaN;
        }
    }
    @Override
    public float pollFirstFloat(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0F;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1F;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0F;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1F;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0F;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1F;
        default:
            // empty
            return Float.NaN;
        }
    }
    @Override
    public int pollFirstInt(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1;
        default:
            // empty
            return Integer.MIN_VALUE;
        }
    }
    @Override
    public long pollFirstLong(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0L;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1L;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0L;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1L;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0L;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1L;
        default:
            // empty
            return Long.MIN_VALUE;
        }
    }
    @Override
    public short pollFirstShort(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=TRUE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|FALSE_MASK;
            return 1;
        default:
            // empty
            return Short.MIN_VALUE;
        }
    }
    @Override
    public Boolean pollLast(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return Boolean.FALSE;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return Boolean.TRUE;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return Boolean.FALSE;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return Boolean.TRUE;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return Boolean.TRUE;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return Boolean.FALSE;
        default:
            // empty
            return null;
        }
    }
    @Override
    public boolean pollLastBoolean(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return false;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return true;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return false;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return true;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return true;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
        default:
            // empty
            return false;
        }
    }
    @Override
    public byte pollLastByte(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0;
        default:
            // empty
            return Byte.MIN_VALUE;
        }
    }
    @Override
    public char pollLastChar(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
        default:
            // empty
            return Character.MIN_VALUE;
        }
    }
    @Override
    public double pollLastDouble(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0D;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1D;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0D;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1D;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1D;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0D;
        default:
            // empty
            return Double.NaN;
        }
    }
    @Override
    public float pollLastFloat(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0F;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1F;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0F;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1F;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1F;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0F;
        default:
            // empty
            return Float.NaN;
        }
    }
    @Override
    public int pollLastInt(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0;
        default:
            // empty
            return Integer.MIN_VALUE;
        }
    }
    @Override
    public long pollLastLong(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0L;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1L;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0L;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1L;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1L;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0L;
        default:
            // empty
            return Long.MIN_VALUE;
        }
    }
    @Override
    public short pollLastShort(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            // ascending contains false
            state=0b000;
            return 0;
        case TRUE_MASK:
            // ascending contains true
            state=0b000;
            return 1;
        case REVERSE_MASK|FALSE_MASK:
            // descending contains false
            state=REVERSE_MASK;
            return 0;
        case REVERSE_MASK|TRUE_MASK:
            // descending contains true
            state=REVERSE_MASK;
            return 1;
        case TRUE_MASK|FALSE_MASK:
            // ascending full
            state=FALSE_MASK;
            return 1;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            // descending full
            state=REVERSE_MASK|TRUE_MASK;
            return 0;
        default:
            // empty
            return Short.MIN_VALUE;
        }
    }
    @Override
    public boolean remove(Object val){
        final int state;
        switch((state=this.state) & (TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            return removeFalseState(state,val);
        case TRUE_MASK:
            return removeTrueState(state,val);
        case TRUE_MASK|FALSE_MASK:
            return removeFullState(state,val);
        default:
            return false;
        }
    }
    @Override
    public boolean removeIf(BooleanPredicate filter){
        final int state;
        switch((state=this.state) & (TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            if(filter.test(false)){
                this.state=state & (CHECKED_MASK|REVERSE_MASK);
                return true;
            }
            break;
        case TRUE_MASK:
            if(filter.test(true)){
                this.state=state & (CHECKED_MASK|REVERSE_MASK);
                return true;
            }
            break;
        case TRUE_MASK|FALSE_MASK:
            if(filter.test(false)){
                if(filter.test(true)){
                    this.state=state & (CHECKED_MASK|REVERSE_MASK);
                }else{
                    this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                }
                return true;
            }else{
                if(filter.test(true)){
                    this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                    return true;
                }
            }
        default:
        }
        return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter){
        final int state;
        switch((state=this.state) & (TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
            if(filter.test(Boolean.FALSE)){
                this.state=state & (CHECKED_MASK|REVERSE_MASK);
                return true;
            }
            break;
        case TRUE_MASK:
            if(filter.test(Boolean.TRUE)){
                this.state=state & (CHECKED_MASK|REVERSE_MASK);
                return true;
            }
            break;
        case TRUE_MASK|FALSE_MASK:
            if(filter.test(Boolean.FALSE)){
                if(filter.test(Boolean.TRUE)){
                    this.state=state & (CHECKED_MASK|REVERSE_MASK);
                }else{
                    this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                }
                return true;
            }else{
                if(filter.test(Boolean.TRUE)){
                    this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                    return true;
                }
            }
        default:
        }
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        final int state;
        return (state=this.state) != (this.state=state & (val?CHECKED_MASK|REVERSE_MASK|FALSE_MASK:CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
    }
    @Override
    public boolean removeVal(double val){
        final long bits;
        if((bits=Double.doubleToRawLongBits(val)) == 0L || bits == Long.MIN_VALUE){
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
        }else if(bits == TypeUtil.DBL_TRUE_BITS){
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK));
        }
        return false;
    }
    @Override
    public boolean removeVal(float val){
        switch(Float.floatToRawIntBits(val)){
        case 0:
        case Integer.MIN_VALUE:
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
        case TypeUtil.FLT_TRUE_BITS:
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK));
        default:
            return false;
        }
    }
    @Override
    public boolean removeVal(int val){
        switch(val){
        case 0:
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
        case 1:
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK));
        default:
            return false;
        }
    }
    @Override
    public boolean removeVal(long val){
        if(val == 0L){
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK));
        }else if(val == 1L){
            final int state;
            return (state=this.state) != (this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK));
        }
        return false;
    }
    @Override
    public int size(){
        switch(state & (TRUE_MASK|FALSE_MASK)){
        case 0b00:
            return 0;
        case FALSE_MASK:
        case TRUE_MASK:
            return 1;
        default:
            return 2;
        }
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
            boolean toInclusive){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public Boolean[] toArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new Boolean[]{Boolean.FALSE};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new Boolean[]{Boolean.TRUE};
        case TRUE_MASK|FALSE_MASK:
            return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
        default:
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final T[] arr;
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
            break;
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
            break;
        case TRUE_MASK|FALSE_MASK:
            (arr=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
            arr[1]=(T)Boolean.TRUE;
            break;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            (arr=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
            arr[1]=(T)Boolean.FALSE;
            break;
        default:
            return arrConstructor.apply(0);
        }
        return arr;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] dst){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
            break;
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
            break;
        case TRUE_MASK|FALSE_MASK:
            (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
            dst[1]=(T)Boolean.TRUE;
            break;
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
            dst[1]=(T)Boolean.FALSE;
            break;
        default:
            if(dst.length != 0){
                dst[0]=null;
            }
        }
        return dst;
    }
    @Override
    public boolean[] toBooleanArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new boolean[]{false};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new boolean[]{true};
        case TRUE_MASK|FALSE_MASK:
            return new boolean[]{false,true};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new boolean[]{true,false};
        default:
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
    }
    @Override
    public byte[] toByteArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new byte[]{0};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new byte[]{1};
        case TRUE_MASK|FALSE_MASK:
            return new byte[]{0,1};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new byte[]{1,0};
        default:
            return OmniArray.OfByte.DEFAULT_ARR;
        }
    }
    @Override
    public char[] toCharArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new char[]{0};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new char[]{1};
        case TRUE_MASK|FALSE_MASK:
            return new char[]{0,1};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new char[]{1,0};
        default:
            return OmniArray.OfChar.DEFAULT_ARR;
        }
    }
    @Override
    public double[] toDoubleArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new double[]{0D};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new double[]{1D};
        case TRUE_MASK|FALSE_MASK:
            return new double[]{0D,1D};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new double[]{1D,0D};
        default:
            return OmniArray.OfDouble.DEFAULT_ARR;
        }
    }
    @Override
    public float[] toFloatArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new float[]{0F};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new float[]{1F};
        case TRUE_MASK|FALSE_MASK:
            return new float[]{0F,1F};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new float[]{1F,0F};
        default:
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
    }
    @Override
    public int[] toIntArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new int[]{0};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new int[]{1};
        case TRUE_MASK|FALSE_MASK:
            return new int[]{0,1};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new int[]{1,0};
        default:
            return OmniArray.OfInt.DEFAULT_ARR;
        }
    }
    @Override
    public long[] toLongArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new long[]{0L};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new long[]{1L};
        case TRUE_MASK|FALSE_MASK:
            return new long[]{0L,1L};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new long[]{1L,0L};
        default:
            return OmniArray.OfLong.DEFAULT_ARR;
        }
    }
    @Override
    public short[] toShortArray(){
        switch(state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
        case FALSE_MASK:
        case REVERSE_MASK|FALSE_MASK:
            return new short[]{0};
        case TRUE_MASK:
        case REVERSE_MASK|TRUE_MASK:
            return new short[]{1};
        case TRUE_MASK|FALSE_MASK:
            return new short[]{0,1};
        case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
            return new short[]{1,0};
        default:
            return OmniArray.OfShort.DEFAULT_ARR;
        }
    }
    private boolean containsFalseState(Object val){
        if(val instanceof Boolean){
            return !(boolean)val;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
            return ((Number)val).intValue() == 0;
        }else if(val instanceof Float){
            return (float)val == 0F;
        }else if(val instanceof Double){
            return (double)val == 0D;
        }else if(val instanceof Long){
            return (long)val == 0L;
        }else if(val instanceof Character){
            return (char)val == 0;
        }
        return false;
    }
    private boolean containsFullState(Object val){
        if(val instanceof Boolean){
            return true;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
            return (((Number)val).intValue() & 0xfffffffe) == 0;
        }else if(val instanceof Float){
            switch(Float.floatToRawIntBits((float)val)){
            case 0:
            case Integer.MIN_VALUE:
            case TypeUtil.FLT_TRUE_BITS:
                return true;
            default:
            }
        }else if(val instanceof Double){
            final long bits;
            return (bits=Double.doubleToRawLongBits((double)val)) == 0L || bits == Long.MIN_VALUE
                    || bits == TypeUtil.DBL_TRUE_BITS;
        }else if(val instanceof Long){
            return ((long)val & 0xfffffffffffffffeL) == 0L;
        }else if(val instanceof Character){
            return ((char)val & 0xfffffffe) == 0;
        }
        return false;
    }
    private boolean containsTrueState(Object val){
        if(val instanceof Boolean){
            return (boolean)val;
        }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
            return ((Number)val).intValue() == 1;
        }else if(val instanceof Float){
            return (float)val == 1F;
        }else if(val instanceof Double){
            return (double)val == 1D;
        }else if(val instanceof Long){
            return (long)val == 1L;
        }else if(val instanceof Character){
            return (char)val == 1;
        }
        return false;
    }
    private void readObject(ObjectInputStream ois) throws IOException{
        state=ois.readUnsignedByte();
    }
    private boolean removeFalseState(int state,Object val){
        removeFalse:for(;;){
            if(val instanceof Boolean){
                if(!(boolean)val){
                    break removeFalse;
                }
            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                if(((Number)val).intValue() == 0){
                    break removeFalse;
                }
            }else if(val instanceof Float){
                if((float)val == 0F){
                    break removeFalse;
                }
            }else if(val instanceof Double){
                if((double)val == 0D){
                    break removeFalse;
                }
            }else if(val instanceof Long){
                if((long)val == 0L){
                    break removeFalse;
                }
            }else if(val instanceof Character){
                if((char)val == 0){
                    break removeFalse;
                }
            }
            return false;
        }
        this.state=state &(CHECKED_MASK|REVERSE_MASK);
        return true;
    }
    private boolean removeFullState(int state,Object val){
        removeFalse:for(;;){
            removeTrue:for(;;){
                if(val instanceof Boolean){
                    if((boolean)val){
                        break removeTrue;
                    }else{
                        break removeFalse;
                    }
                }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                    switch(((Number)val).intValue()){
                    case 0:
                        break removeFalse;
                    case 1:
                        break removeTrue;
                    default:
                    }
                }else if(val instanceof Float){
                    switch(Float.floatToRawIntBits((float)val)){
                    case 0:
                    case Integer.MIN_VALUE:
                        break removeFalse;
                    case TypeUtil.FLT_TRUE_BITS:
                        break removeTrue;
                    default:
                    }
                }else if(val instanceof Double){
                    final long bits;
                    if((bits=Double.doubleToRawLongBits((double)val)) == 0L || bits == Long.MIN_VALUE){
                        break removeFalse;
                    }else if(bits == TypeUtil.DBL_TRUE_BITS){
                        break removeTrue;
                    }
                }else if(val instanceof Long){
                    final long l;
                    if((l=(long)val) == 0L){
                        break removeFalse;
                    }else if(l == 1L){
                        break removeTrue;
                    }
                }else if(val instanceof Character){
                    switch(((Character)val).charValue()){
                    case 0:
                        break removeFalse;
                    case 1:
                        break removeTrue;
                    default:
                    }
                }
                return false;
            }
            this.state=state & (CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
            return true;
        }
        this.state=state & (CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
        return true;
    }
    private boolean removeTrueState(int state,Object val){
        removeTrue:for(;;){
            if(val instanceof Boolean){
                if((boolean)val){
                    break removeTrue;
                }
            }else if(val instanceof Integer || val instanceof Byte || val instanceof Short){
                if(((Number)val).intValue() == 1){
                    break removeTrue;
                }
            }else if(val instanceof Float){
                if((float)val == 1F){
                    break removeTrue;
                }
            }else if(val instanceof Double){
                if((double)val == 1D){
                    break removeTrue;
                }
            }else if(val instanceof Long){
                if((long)val == 1L){
                    break removeTrue;
                }
            }else if(val instanceof Character){
                if((char)val == 1){
                    break removeTrue;
                }
            }
            return false;
        }
        this.state=state & (CHECKED_MASK|REVERSE_MASK);
        return true;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.writeByte(state);
    }
    private static class ReverseView implements OmniNavigableSet.OfBoolean,Serializable,Cloneable{
        private static final long serialVersionUID=1L;
        private final BooleanSetImpl root;
        private ReverseView(BooleanSetImpl root){
            this.root=root;
        }
        @Override
        public boolean add(boolean val){
            return root.add(val);
        }
        @Override
        public boolean add(Boolean e){
            return root.add((boolean)e);
        }
        @Override
        public boolean booleanCeiling(boolean val){
            return root.booleanFloor(val);
        }
        @Override
        public boolean booleanFloor(boolean val){
            return root.booleanCeiling(val);
        }
        @Override
        public Boolean ceiling(boolean val){
            return root.floor(val);
        }
        @Override
        public void clear(){
            root.clear();
        }
        @Override
        public Object clone(){
            final int state;
            return new BooleanSetImpl(((state=root.state) & REVERSE_MASK) == 0?state | REVERSE_MASK:state & (CHECKED_MASK|TRUE_MASK|FALSE_MASK));
        }
        @Override
        public BooleanComparator comparator(){
            if((root.state & REVERSE_MASK) == 0){
                return BooleanSetImpl::reverseCompare;
            }else{
                return Boolean::compare;
            }
        }
        @Override
        public boolean contains(boolean val){
            return root.contains(val);
        }
        @Override
        public boolean contains(double val){
            return root.contains(val);
        }
        @Override
        public boolean contains(float val){
            return root.contains(val);
        }
        @Override
        public boolean contains(int val){
            return root.contains(val);
        }
        @Override
        public boolean contains(long val){
            return root.contains(val);
        }
        @Override
        public boolean contains(Object val){
            return root.contains(val);
        }
        @Override
        public OmniIterator.OfBoolean descendingIterator(){
            final BooleanSetImpl root;
            switch((root=this.root).state>>>2) {
            case CHECKED_MASK>>>2:
                return new CheckedAscendingItr(root);
            case REVERSE_MASK>>>2:
                return new UncheckedDescendingItr(root);
            case (CHECKED_MASK|REVERSE_MASK)>>>2:
                return new CheckedDescendingItr(root);
            default:
                return new UncheckedAscendingItr(root);  
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            return root;
        }
        @Override
        public boolean firstBoolean(){
            return root.lastBoolean();
        }
        @Override
        public Boolean floor(boolean val){
            return root.ceiling(val);
        }
        @Override
        public void forEach(BooleanConsumer action){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case TRUE_MASK|FALSE_MASK:
                action.accept(true);
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                action.accept(false);
                break;
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                action.accept(false);
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                action.accept(true);
            default:
            }
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case TRUE_MASK|FALSE_MASK:
                action.accept(Boolean.TRUE);
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                action.accept(Boolean.FALSE);
                break;
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                action.accept(Boolean.FALSE);
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                action.accept(Boolean.TRUE);
            default:
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Boolean higher(boolean val){
            return root.lower(val);
        }
        @Override
        public boolean higherBoolean(boolean val){
            return root.lowerBoolean(val);
        }
        @Override
        public boolean isEmpty(){
            return root.isEmpty();
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            final BooleanSetImpl root;
            switch((root=this.root).state>>>2) {
            case CHECKED_MASK>>>2:
                return new CheckedDescendingItr(root);
            case REVERSE_MASK>>>2:
                return new UncheckedAscendingItr(root);
            case (CHECKED_MASK|REVERSE_MASK)>>>2:
                return new CheckedAscendingItr(root);
            default:
                return new UncheckedDescendingItr(root);  
            }
        }
        @Override
        public boolean lastBoolean(){
            return root.firstBoolean();
        }
        @Override
        public Boolean lower(boolean val){
            return root.higher(val);
        }
        @Override
        public boolean lowerBoolean(boolean val){
            return root.higherBoolean(val);
        }
        @Override
        public Boolean pollFirst(){
            return root.pollLast();
        }
        @Override
        public boolean pollFirstBoolean(){
            return root.pollLastBoolean();
        }
        @Override
        public byte pollFirstByte(){
            return root.pollLastByte();
        }
        @Override
        public char pollFirstChar(){
            return root.pollLastChar();
        }
        @Override
        public double pollFirstDouble(){
            return root.pollLastDouble();
        }
        @Override
        public float pollFirstFloat(){
            return root.pollLastFloat();
        }
        @Override
        public int pollFirstInt(){
            return root.pollLastInt();
        }
        @Override
        public long pollFirstLong(){
            return root.pollLastLong();
        }
        @Override
        public short pollFirstShort(){
            return root.pollLastShort();
        }
        @Override
        public Boolean pollLast(){
            return root.pollFirst();
        }
        @Override
        public boolean pollLastBoolean(){
            return root.pollFirstBoolean();
        }
        @Override
        public byte pollLastByte(){
            return root.pollFirstByte();
        }
        @Override
        public char pollLastChar(){
            return root.pollFirstChar();
        }
        @Override
        public double pollLastDouble(){
            return root.pollFirstDouble();
        }
        @Override
        public float pollLastFloat(){
            return root.pollFirstFloat();
        }
        @Override
        public int pollLastInt(){
            return root.pollFirstInt();
        }
        @Override
        public long pollLastLong(){
            return root.pollFirstLong();
        }
        @Override
        public short pollLastShort(){
            return root.pollFirstShort();
        }
        @Override
        public boolean remove(Object val){
            return root.remove(val);
        }
        @Override
        public boolean removeIf(BooleanPredicate filter){
            return root.removeIf(filter);
        }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter){
            return root.removeIf(filter);
        }
        @Override
        public boolean removeVal(boolean val){
            return root.removeVal(val);
        }
        @Override
        public boolean removeVal(double val){
            return root.removeVal(val);
        }
        @Override
        public boolean removeVal(float val){
            return root.removeVal(val);
        }
        @Override
        public boolean removeVal(int val){
            return root.removeVal(val);
        }
        @Override
        public boolean removeVal(long val){
            return root.removeVal(val);
        }
        @Override
        public int size(){
            return root.size();
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
                boolean toInclusive){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            // TODO Auto-generated method stub
            return null;
        }
        @Override
        public Boolean[] toArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new Boolean[]{Boolean.FALSE};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new Boolean[]{Boolean.TRUE};
            case TRUE_MASK|FALSE_MASK:
                return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
            default:
                return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
            }
        }
        @SuppressWarnings({"unchecked"})
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            final T[] arr;
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
                break;
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
                break;
            case TRUE_MASK|FALSE_MASK:
                (arr=arrConstructor.apply(2))[0]=(T)Boolean.TRUE;
                arr[1]=(T)Boolean.FALSE;
                break;
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                (arr=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
                arr[1]=(T)Boolean.TRUE;
                break;
            default:
                return arrConstructor.apply(0);
            }
            return arr;
        }
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(T[] dst){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
                break;
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
                break;
            case TRUE_MASK|FALSE_MASK:
                (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
                dst[1]=(T)Boolean.FALSE;
                break;
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
                dst[1]=(T)Boolean.TRUE;
                break;
            default:
                if(dst.length != 0){
                    dst[0]=null;
                }
            }
            return dst;
        }
        @Override
        public boolean[] toBooleanArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new boolean[]{false};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new boolean[]{true};
            case TRUE_MASK|FALSE_MASK:
                return new boolean[]{true,false};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new boolean[]{false,true};
            default:
                return OmniArray.OfBoolean.DEFAULT_ARR;
            }
        }
        @Override
        public byte[] toByteArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new byte[]{0};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new byte[]{1};
            case TRUE_MASK|FALSE_MASK:
                return new byte[]{1,0};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new byte[]{0,1};
            default:
                return OmniArray.OfByte.DEFAULT_ARR;
            }
        }
        @Override
        public char[] toCharArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new char[]{0};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new char[]{1};
            case TRUE_MASK|FALSE_MASK:
                return new char[]{1,0};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new char[]{0,1};
            default:
                return OmniArray.OfChar.DEFAULT_ARR;
            }
        }
        @Override
        public double[] toDoubleArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new double[]{0D};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new double[]{1D};
            case TRUE_MASK|FALSE_MASK:
                return new double[]{1D,0D};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new double[]{0D,1D};
            default:
                return OmniArray.OfDouble.DEFAULT_ARR;
            }
        }
        @Override
        public float[] toFloatArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new float[]{0F};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new float[]{1F};
            case TRUE_MASK|FALSE_MASK:
                return new float[]{1F,0F};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new float[]{0F,1F};
            default:
                return OmniArray.OfFloat.DEFAULT_ARR;
            }
        }
        @Override
        public int[] toIntArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new int[]{0};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new int[]{1};
            case TRUE_MASK|FALSE_MASK:
                return new int[]{1,0};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new int[]{0,1};
            default:
                return OmniArray.OfInt.DEFAULT_ARR;
            }
        }
        @Override
        public long[] toLongArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new long[]{0L};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new long[]{1L};
            case TRUE_MASK|FALSE_MASK:
                return new long[]{1L,0L};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new long[]{0L,1L};
            default:
                return OmniArray.OfLong.DEFAULT_ARR;
            }
        }
        @Override
        public short[] toShortArray(){
            switch(root.state & (REVERSE_MASK|TRUE_MASK|FALSE_MASK)){
            case FALSE_MASK:
            case REVERSE_MASK|FALSE_MASK:
                return new short[]{0};
            case TRUE_MASK:
            case REVERSE_MASK|TRUE_MASK:
                return new short[]{1};
            case TRUE_MASK|FALSE_MASK:
                return new short[]{1,0};
            case REVERSE_MASK|TRUE_MASK|FALSE_MASK:
                return new short[]{0,1};
            default:
                return OmniArray.OfShort.DEFAULT_ARR;
            }
        }
        private Object writeReplace(){
            final int state;
            return new BooleanSetImpl(((state=root.state) & REVERSE_MASK) == 0?state | REVERSE_MASK:state & (CHECKED_MASK|TRUE_MASK|FALSE_MASK));
        }
    }
    private static class UncheckedAscendingItr extends AbstractBooleanItr{
        final BooleanSetImpl root;
        int itrState;
        private UncheckedAscendingItr(BooleanSetImpl root) {
            this.root=root;
            this.itrState=root.state&(TRUE_MASK|FALSE_MASK);
        }
        private UncheckedAscendingItr(UncheckedAscendingItr orig) {
            this.root=orig.root;
            this.itrState=orig.itrState;
        }
        @Override public Object clone() {
            return new UncheckedAscendingItr(this);
        }
        @Override public boolean hasNext() {
            return this.itrState!=0;
        }
        @Override public boolean nextBoolean() {
            switch(this.itrState) {
            case TRUE_MASK: //true remains, so return it
                this.itrState=0b00;
                return true;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so return false
                this.itrState=TRUE_MASK;
                return false;
            default:  //the only remaining valid state is FALSE_MASK, so return false
                this.itrState=0b00;
                return false;
            }
            
        }
        @Override public void remove() {
            final var root=this.root;
            if(itrState==TRUE_MASK) {
                //we have iterated over false, so remove it
                root.state&=CHECKED_MASK|REVERSE_MASK|TRUE_MASK;
            }else {
                //the only other valid itrState is 0b00
                final int rootState;
                if(((rootState=root.state)&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                  //remove true
                    root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                }else {
                    //the only valid root states are TRUE_MASK and FALSE_MASK. Remove them
                    root.state=rootState&(CHECKED_MASK|REVERSE_MASK);
                }
            }
        }
        @Override public void forEachRemaining(BooleanConsumer action) {
            switch(itrState) {
            default: //no remaining elements
                return;
            case FALSE_MASK: //false remains, so iterate over it
                action.accept(false);
                break;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so iterate over both
                action.accept(false);
            case TRUE_MASK: //true remains, so iterate over it
                action.accept(true);
            }
            this.itrState=0b00;
        }
        @Override public void forEachRemaining(Consumer<? super Boolean> action) {
            switch(itrState) {
            default: //no remaining elements
                return;
            case FALSE_MASK: //false remains, so iterate over it
                action.accept(Boolean.FALSE);
                break;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so iterate over both
                action.accept(Boolean.FALSE);
            case TRUE_MASK: //true remains, so iterate over it
                action.accept(Boolean.TRUE);
            }
            this.itrState=0b00;
        }
    }
    private static class CheckedAscendingItr extends AbstractBooleanItr{ 
        final BooleanSetImpl root;
        int itrState;
        private CheckedAscendingItr(BooleanSetImpl root) {
            this.root=root;
            this.itrState=root.state&(TRUE_MASK|FALSE_MASK);
        }
        private CheckedAscendingItr(CheckedAscendingItr orig) {
            this.root=orig.root;
            this.itrState=orig.itrState;
        }
        @Override public Object clone() {
            return new CheckedAscendingItr(this);
        }
        @Override public boolean hasNext() {
            return (this.itrState&(TRUE_MASK|FALSE_MASK))!=0;
        }
        @Override public boolean nextBoolean() {
            switch((this.itrState&(TRUE_MASK|FALSE_MASK)|root.state<<2)&(TRUE_MASK|FALSE_MASK|(TRUE_MASK|FALSE_MASK)<<2)) {
            case TRUE_MASK<<2|TRUE_MASK: //true remains
            case (TRUE_MASK|FALSE_MASK)<<2|TRUE_MASK: //we have iterated over false, true remains
                this.itrState=TRUE_MASK<<2; //return true and set the return flag
                return true;
            case (TRUE_MASK|FALSE_MASK)<<2|TRUE_MASK|FALSE_MASK: //true and false remain
                this.itrState=TRUE_MASK|FALSE_MASK<<2; //return false and set the return flag
                break;
            case 0b0000: //the set and the iterator are both depleted
            case FALSE_MASK<<2:  //the set contains false and we have already iterated over it
            case TRUE_MASK<<2:   //the set contains true and we have already iterated over it
            case (TRUE_MASK|FALSE_MASK)<<2: //the set is full and we have iterated over all of it already
                throw new NoSuchElementException();
            default:  //all other states indicate that concurrent modification took place
                throw new ConcurrentModificationException();
            case FALSE_MASK<<2|FALSE_MASK: //false remains
                this.itrState=FALSE_MASK<<2;
            }
            return false;
        }
        @Override public void forEachRemaining(BooleanConsumer action) {
            switch(this.itrState&(TRUE_MASK|FALSE_MASK)) {
            default:
                return;
            case FALSE_MASK:
                //false remains
                if((root.state&(TRUE_MASK|FALSE_MASK))==FALSE_MASK) {
                    action.accept(false);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK:
                //true remains
                if((root.state&TRUE_MASK)!=0) {
                    action.accept(true);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK|FALSE_MASK:
                //both remain
                if((root.state&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                    action.accept(false);
                    action.accept(true);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
            }
            //the expected state did not match, indicating concurrent modification
            throw new ConcurrentModificationException();
        }
        @Override public void forEachRemaining(Consumer<? super Boolean> action) {
            switch(this.itrState&(TRUE_MASK|FALSE_MASK)) {
            default:
                return;
            case FALSE_MASK:
                //false remains
                if((root.state&(TRUE_MASK|FALSE_MASK))==FALSE_MASK) {
                    action.accept(Boolean.FALSE);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK:
                //true remains
                if((root.state&TRUE_MASK)!=0) {
                    action.accept(Boolean.TRUE);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK|FALSE_MASK:
                //both remain
                if((root.state&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                    action.accept(Boolean.FALSE);
                    action.accept(Boolean.TRUE);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
            }
            //the expected state did not match, indicating concurrent modification
            throw new ConcurrentModificationException();
        }
        @Override public void remove() {
            final BooleanSetImpl root;
            final int rootState;
            switch(this.itrState|((rootState=(root=this.root).state)&(TRUE_MASK|FALSE_MASK))<<4) {
            case (TRUE_MASK|FALSE_MASK)<<4|FALSE_MASK<<2|TRUE_MASK: //we have just iterated over false, true remains, so remove false
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                this.itrState=TRUE_MASK;
                return;
            case FALSE_MASK<<4|FALSE_MASK<<2: //we have just iterated over false, so remove it
            case TRUE_MASK<<4|TRUE_MASK<<2: //we have just iterated over true, so remove it
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK);
                break;
            case (TRUE_MASK|FALSE_MASK)<<4|TRUE_MASK<<2: //we have just iterated over true, remove it
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                break;
            case FALSE_MASK<<2: //we just iterated over false, but the root state no longer contains it
            case FALSE_MASK<<2|TRUE_MASK: //we have just iterated over false, true remains, but the root state is empty
            case TRUE_MASK<<2: //we have just iterated over true but the root no longer contains it
            case FALSE_MASK<<4|FALSE_MASK<<2|TRUE_MASK: //we have just iterated over false, true remains, but true is missing from the root state
            case FALSE_MASK<<4|TRUE_MASK<<2: //we have just iterated over true but it is missing in the root, instead false is in the root
            case TRUE_MASK<<4|FALSE_MASK<<2: //we have just iterated over false but it is missing in the root, instead true is in the root
            case TRUE_MASK<<4|FALSE_MASK<<2|TRUE_MASK: //we have just iterated over false, true remains, but false is absent from the root
            case (TRUE_MASK|FALSE_MASK)<<4 | FALSE_MASK<<2: //we have just iterated over false and expected no remaining elements. however, true is in the root as well
                throw new ConcurrentModificationException();
            default: //all other states are either unreachable or indicate improper iteration
                throw new IllegalStateException();
            }
            this.itrState=0b00;
            return;
        }
    }
    private static class UncheckedDescendingItr extends UncheckedAscendingItr{
        private UncheckedDescendingItr(BooleanSetImpl root) {
            super(root);
        }
        private UncheckedDescendingItr(UncheckedDescendingItr orig) {
            super(orig);
        }
        @Override public Object clone() {
            return new UncheckedDescendingItr(this);
        }
        @Override public boolean nextBoolean() {
            switch(this.itrState) {
            case FALSE_MASK: //false remains, so return it
                this.itrState=0b00;
                return false;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so return true
                this.itrState=FALSE_MASK;
                return true;
            default:  //the only remaining valid state is TRUE_MASK, so return true
                this.itrState=0b00;
                return true;
            }
            
        }
        @Override public void remove() {
            final var root=this.root;
            if(itrState==FALSE_MASK) {
                //we have iterated over true, so remove it
                root.state&=CHECKED_MASK|REVERSE_MASK|FALSE_MASK;
            }else {
                //the only other valid itrState is 0b00
                final int rootState;
                if(((rootState=root.state)&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                  //remove false
                    root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                }else {
                    //the only valid root states are TRUE_MASK and FALSE_MASK. Remove them
                    root.state=rootState&(CHECKED_MASK|REVERSE_MASK);
                }
            }
        }
        @Override public void forEachRemaining(BooleanConsumer action) {
            switch(itrState) {
            default: //no remaining elements
                return;
            case TRUE_MASK: //true remains, so iterate over it
                action.accept(true);
                break;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so iterate over both
                action.accept(true);
            case FALSE_MASK: //false remains, so iterate over it
                action.accept(false);
            }
            this.itrState=0b00;
        }
        @Override public void forEachRemaining(Consumer<? super Boolean> action) {
            switch(itrState) {
            default: //no remaining elements
                return;
            case TRUE_MASK: //true remains, so iterate over it
                action.accept(Boolean.TRUE);
                break;
            case TRUE_MASK|FALSE_MASK: //true and false remain, so iterate over both
                action.accept(Boolean.TRUE);
            case FALSE_MASK: //false remains, so iterate over it
                action.accept(Boolean.FALSE);
            }
            this.itrState=0b00;
        }
    }
    private static class CheckedDescendingItr extends CheckedAscendingItr{ 
        private CheckedDescendingItr(BooleanSetImpl root) {
            super(root);
        }
        private CheckedDescendingItr(CheckedDescendingItr orig) {
            super(orig);
        }
        @Override public Object clone() {
            return new CheckedDescendingItr(this);
        }
        @Override public boolean nextBoolean() {
            switch((this.itrState&(TRUE_MASK|FALSE_MASK)|root.state<<2)&(TRUE_MASK|FALSE_MASK|(TRUE_MASK|FALSE_MASK)<<2)) {
            case FALSE_MASK<<2|FALSE_MASK: //false remains
            case (TRUE_MASK|FALSE_MASK)<<2|FALSE_MASK: //we have iterated over true, false remains
                this.itrState=FALSE_MASK<<2; //return false and set the return flag
                return false;
            case (TRUE_MASK|FALSE_MASK)<<2|TRUE_MASK|FALSE_MASK: //true and false remain
                this.itrState=FALSE_MASK|TRUE_MASK<<2; //return true and set the return flag
                break;
            case 0b0000: //the set and the iterator are both depleted
            case FALSE_MASK<<2:  //the set contains false and we have already iterated over it
            case TRUE_MASK<<2:   //the set contains true and we have already iterated over it
            case (TRUE_MASK|FALSE_MASK)<<2: //the set is full and we have iterated over all of it already
                throw new NoSuchElementException();
            default:  //all other states indicate that concurrent modification took place
                throw new ConcurrentModificationException();
            case TRUE_MASK<<2|TRUE_MASK: //true remains
                this.itrState=TRUE_MASK<<2;
            }
            return true;
        }
        @Override public void forEachRemaining(BooleanConsumer action) {
            switch(this.itrState&(TRUE_MASK|FALSE_MASK)) {
            default:
                return;
            case FALSE_MASK:
                //false remains
                if((root.state&(TRUE_MASK|FALSE_MASK))==FALSE_MASK) {
                    action.accept(false);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK:
                //true remains
                if((root.state&TRUE_MASK)!=0) {
                    action.accept(true);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK|FALSE_MASK:
                //both remain
                if((root.state&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                    action.accept(true);
                    action.accept(false);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
            }
            //the expected state did not match, indicating concurrent modification
            throw new ConcurrentModificationException();
        }
        @Override public void forEachRemaining(Consumer<? super Boolean> action) {
            switch(this.itrState&(TRUE_MASK|FALSE_MASK)) {
            default:
                return;
            case FALSE_MASK:
                //false remains
                if((root.state&(TRUE_MASK|FALSE_MASK))==FALSE_MASK) {
                    action.accept(Boolean.FALSE);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK:
                //true remains
                if((root.state&TRUE_MASK)!=0) {
                    action.accept(Boolean.TRUE);
                    this.itrState=TRUE_MASK<<2; //set the return flag
                    return;
                }
                break;
            case TRUE_MASK|FALSE_MASK:
                //both remain
                if((root.state&(TRUE_MASK|FALSE_MASK))==(TRUE_MASK|FALSE_MASK)) {
                    action.accept(Boolean.TRUE);
                    action.accept(Boolean.FALSE);
                    this.itrState=FALSE_MASK<<2; //set the return flag
                    return;
                }
            }
            //the expected state did not match, indicating concurrent modification
            throw new ConcurrentModificationException();
        }
        @Override public void remove() {
            final BooleanSetImpl root;
            final int rootState;
            switch(this.itrState|((rootState=(root=this.root).state)&(TRUE_MASK|FALSE_MASK))<<4) {
            case (TRUE_MASK|FALSE_MASK)<<4|TRUE_MASK<<2|FALSE_MASK: //we have just iterated over true, false remains, so remove true
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|FALSE_MASK);
                this.itrState=FALSE_MASK;
                return;
            case FALSE_MASK<<4|FALSE_MASK<<2: //we have just iterated over false, so remove it
            case TRUE_MASK<<4|TRUE_MASK<<2: //we have just iterated over true, so remove it
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK);
                break;
            case (TRUE_MASK|FALSE_MASK)<<4|FALSE_MASK<<2: //we have just iterated over false, remove it
                root.state=rootState&(CHECKED_MASK|REVERSE_MASK|TRUE_MASK);
                break;
            case FALSE_MASK<<2: //we just iterated over false, but the root state no longer contains it
            case TRUE_MASK<<2|FALSE_MASK: //we have just iterated over true, false remains, but the root state is empty
            case TRUE_MASK<<2: //we have just iterated over true but the root no longer contains it
            case TRUE_MASK<<4|TRUE_MASK<<2|FALSE_MASK: //we have just iterated over true, false remains, but false is missing from the root state
            case FALSE_MASK<<4|TRUE_MASK<<2: //we have just iterated over true but it is missing in the root, instead false is in the root
            case TRUE_MASK<<4|FALSE_MASK<<2: //we have just iterated over false but it is missing in the root, instead true is in the root
            case FALSE_MASK<<4|TRUE_MASK<<2|FALSE_MASK: //we have just iterated over true, false remains, but true is absent from the root
            case (TRUE_MASK|FALSE_MASK)<<4 | TRUE_MASK<<2: //we have just iterated over true and expected no remaining elements. however, false is in the root as well
                throw new ConcurrentModificationException();
            default: //all other states are either unreachable or indicate improper iteration
                throw new IllegalStateException();
            }
            this.itrState=0b00;
            return;
        }
    }
}
