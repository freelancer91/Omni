package omni.impl.set;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniCollection;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.impl.AbstractBooleanItr;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;
public class BooleanSetImpl2 extends AbstractBooleanSet implements OmniNavigableSet.OfBoolean,Serializable,Cloneable{
    private static final long serialVersionUID=1L;
    transient int state;
    public BooleanSetImpl2(){
        super();
    }
    public BooleanSetImpl2(BooleanSetImpl2 that){
        super();
        state=that.state;
    }
    public BooleanSetImpl2(Collection<? extends Boolean> that){
        super();
        // TODO optimize
        this.addAll(that);
    }
    public BooleanSetImpl2(OmniCollection.OfBoolean that){
        super();
        // TODO optimize
        this.addAll(that);
    }
    public BooleanSetImpl2(OmniCollection.OfRef<? extends Boolean> that){
        super();
        // TODO optimize
        this.addAll(that);
    }
    BooleanSetImpl2(int state){
        this.state=state;
    }
    @Override
    public boolean booleanCeiling(boolean val){
        switch(state){
        case 0b11:
            return val;
        case 0b10:
            return true;
        default:
            return false;
        }
    }
    @Override
    public boolean booleanFloor(boolean val){
        return val && (state & 0b10) != 0;
    }
    @Override
    public byte byteCeiling(byte val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 2){
                return 1;
            }
            break;
        case 0b01:
            if(val < 1){
                return 0;
            }
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public byte byteFloor(byte val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public Boolean ceiling(boolean val){
        switch(state){
        case 0b11:
        case 0b10:
            return Boolean.TRUE;
        case 0b01:
            if(!val){
                return Boolean.FALSE;
            }
        default:
        }
        return null;
    }
    @Override
    public char charCeiling(char val){
        switch(state){
        case 0b11:
            if(val == 1){
                return 1;
            }
            break;
        case 0b10:
            if(val < 2){
                return 1;
            }
            break;
        default:
        }
        return 0;
    }
    @Override
    public char charFloor(char val){
        if(val >= 1 && (state & 0b10) != 0){
            return 1;
        }
        return 0;
    }
    @Override
    public void clear(){
        state=0;
    }
    @Override
    public Object clone(){
        return new BooleanSetImpl2(state);
    }
    @Override
    public BooleanComparator comparator(){
        return Boolean::compare;
    }
    @Override
    public OmniIterator.OfBoolean descendingIterator(){
        return new UncheckedDescendingItr(this);
    }
    @Override
    public OmniNavigableSet.OfBoolean descendingSet(){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public double doubleCeiling(double val){
        switch(state){
        case 0b11:
            if(val <= 0){
                return 0;
            }
        case 0b10:
            if(val <= 1){
                return 1;
            }
            break;
        case 0b01:
            if(val <= 0){
                return 0;
            }
        default:
        }
        return Double.NaN;
    }
    @Override
    public double doubleFloor(double val){
        switch(state){
        case 0b11:
            if(val >= 1){
                return 1;
            }
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Double.NaN;
    }
    @Override
    public boolean firstBoolean(){
        return (state & 0b01) == 0;
    }
    @Override
    public float floatCeiling(float val){
        switch(state){
        case 0b11:
            if(val <= 0){
                return 0;
            }
        case 0b10:
            if(val <= 1){
                return 1;
            }
            break;
        case 0b01:
            if(val <= 0){
                return 0;
            }
        default:
        }
        return Float.NaN;
    }
    @Override
    public float floatFloor(float val){
        switch(state){
        case 0b11:
            if(val >= 1){
                return 1;
            }
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Float.NaN;
    }
    @Override
    public Boolean floor(boolean val){
        switch(state){
        case 0b11:
            return val;
        case 0b01:
            return Boolean.FALSE;
        case 0b10:
            if(val){
                return Boolean.TRUE;
            }
        default:
        }
        return null;
    }
    @Override
    public void forEach(BooleanConsumer action){
        switch(state){
        case 0b01:
            action.accept(false);
            break;
        case 0b11:
            action.accept(false);
        case 0b10:
            action.accept(true);
        default:
        }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action){
        switch(state){
        case 0b01:
            action.accept(Boolean.FALSE);
            break;
        case 0b11:
            action.accept(Boolean.FALSE);
        case 0b10:
            action.accept(Boolean.TRUE);
        default:
        }
    }
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public Boolean higher(boolean val){
        if(!val && (state & 0b10) != 0){
            return Boolean.TRUE;
        }
        return null;
    }
    @Override
    public boolean higherBoolean(boolean val){
        return !val && (state & 0b10) != 0;
    }
    @Override
    public byte higherByte(byte val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public char higherChar(char val){
        if(val == 0 && (state & 0b10) != 0){
            return 1;
        }
        return 0;
    }
    @Override
    public double higherDouble(double val){
        switch(state){
        case 0b11:
            if(val < 0){
                return 0;
            }
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Double.NaN;
    }
    @Override
    public float higherFloat(float val){
        switch(state){
        case 0b11:
            if(val < 0){
                return 0;
            }
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Float.NaN;
    }
    @Override
    public int higherInt(int val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long higherLong(long val){
        switch(state){
        case 0b11:
            switch(Long.signum(val)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public short higherShort(short val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 1){
                return 1;
            }
            break;
        case 0b01:
            if(val < 0){
                return 0;
            }
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public int intCeiling(int val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 2){
                return 1;
            }
            break;
        case 0b01:
            if(val < 1){
                return 0;
            }
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public int intFloor(int val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public boolean isEmpty(){
        return state == 0;
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        return new UncheckedAscendingItr(this);
    }
    @Override
    public boolean lastBoolean(){
        return (state & 0b10) != 0;
    }
    @Override
    public long longCeiling(long val){
        switch(state){
        case 0b11:
            switch(Long.signum(val - 1)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 2){
                return 1;
            }
            break;
        case 0b01:
            if(val < 1){
                return 0;
            }
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public long longFloor(long val){
        switch(state){
        case 0b11:
            switch(Long.signum(val)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public Boolean lower(boolean val){
        if(val) {
            if((state&0b01)!=0) {
                return Boolean.FALSE;
            }
        }else {
            
        }
        return null;
//        if(val && (state & 0b01) != 0){
//            return Boolean.FALSE;
//        }
//        return null;
    }
    @Override
    public boolean lowerBoolean(boolean val){
        return false;
    }
    @Override
    public byte lowerByte(byte val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public char lowerChar(char val){
        if(val>1) {
            if((state&0b10)!=0) {
                return 1;
            }
            return 0;
            
        }else if(val==1) {
            return 0;
        }else if(val==0) {
            return 0;
        }else {
            return 0;
        }
//        if(val > 1 && (state & 0b10) != 0){
//            return 1;
//        }
//        return 0;
    }
    @Override
    public double lowerDouble(double val){
        switch(state){
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b11:
            if(val > 1){
                return 1;
            }
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Double.NaN;
    }
    @Override
    public float lowerFloat(float val){
        switch(state){
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b11:
            if(val > 1){
                return 1;
            }
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Float.NaN;
    }
    @Override
    public int lowerInt(int val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long lowerLong(long val){
        switch(state){
        case 0b11:
            switch(Long.signum(val - 1)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public short lowerShort(short val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b10:
            if(val > 1){
                return 1;
            }
            break;
        case 0b01:
            if(val > 0){
                return 0;
            }
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public Boolean pollFirst(){
        switch(state){
        case 0b11:
            state=0b10;
            return Boolean.FALSE;
        case 0b10:
            state=0b00;
            return Boolean.TRUE;
        case 0b01:
            state=0b00;
            return Boolean.FALSE;
        default:
        }
        return null;
    }
    @Override
    public boolean pollFirstBoolean(){
        switch(state){
        case 0b11:
            state=0b10;
            break;
        case 0b10:
            state=0b00;
            return true;
        case 0b01:
            state=0b00;
        default:
        }
        return false;
    }
    @Override
    public byte pollFirstByte(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public char pollFirstChar(){
        switch(state){
        case 0b10:
            state=0b00;
            return 1;
        case 0b11:
            state=0b10;
            break;
        case 0b01:
            state=0b00;
        default:
        }
        return 0;
    }
    @Override
    public double pollFirstDouble(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Double.NaN;
    }
    @Override
    public float pollFirstFloat(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Float.NaN;
    }
    @Override
    public int pollFirstInt(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long pollFirstLong(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public short pollFirstShort(){
        switch(state){
        case 0b11:
            state=0b10;
            return 0;
        case 0b10:
            state=0b00;
            return 1;
        case 0b01:
            state=0b00;
            return 0;
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public Boolean pollLast(){
        switch(state){
        case 0b11:
            state=0b01;
            return Boolean.TRUE;
        case 0b10:
            state=0b00;
            return Boolean.FALSE;
        case 0b01:
            state=0b00;
            return Boolean.TRUE;
        default:
        }
        return null;
    }
    @Override
    public boolean pollLastBoolean(){
        switch(state){
        case 0b10:
            state=0b00;
        default:
            return false;
        case 0b11:
            state=0b01;
            break;
        case 0b01:
            state=0b00;
        }
        return true;
    }
    @Override
    public byte pollLastByte(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Byte.MIN_VALUE;
    }
    @Override
    public char pollLastChar(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b01:
            state=0b00;
            return 1;
        case 0b10:
            state=0b00;
        default:
        }
        return 0;
    }
    @Override
    public double pollLastDouble(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Double.NaN;
    }
    @Override
    public float pollLastFloat(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Float.NaN;
    }
    @Override
    public int pollLastInt(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Integer.MIN_VALUE;
    }
    @Override
    public long pollLastLong(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Long.MIN_VALUE;
    }
    @Override
    public short pollLastShort(){
        switch(state){
        case 0b11:
            state=0b01;
            return 1;
        case 0b10:
            state=0b00;
            return 0;
        case 0b01:
            state=0b00;
            return 1;
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public boolean removeIf(BooleanPredicate filter){
        switch(state){
        case 0b01:
            if(filter.test(false)){
                break;
            }
            return false;
        case 0b10:
            if(filter.test(true)){
                break;
            }
            return false;
        case 0b11:
            if(filter.test(false)){
                if(filter.test(true)){
                    break;
                }else{
                    state=0b10;
                    return true;
                }
            }else if(filter.test(true)){
                state=0b01;
                return true;
            }
        default:
            return false;
        }
        state=0b00;
        return true;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter){
        switch(state){
        case 0b01:
            if(filter.test(Boolean.FALSE)){
                break;
            }
            return false;
        case 0b10:
            if(filter.test(Boolean.TRUE)){
                break;
            }
            return false;
        case 0b11:
            if(filter.test(Boolean.FALSE)){
                if(filter.test(Boolean.TRUE)){
                    break;
                }else{
                    state=0b10;
                    return true;
                }
            }else if(filter.test(Boolean.TRUE)){
                state=0b01;
                return true;
            }
        default:
            return false;
        }
        state=0b00;
        return true;
    }
    @Override
    public short shortCeiling(short val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val - 1)){
            case -1:
                return 0;
            case 0:
                return 1;
            default:
            }
            break;
        case 0b10:
            if(val < 2){
                return 1;
            }
            break;
        case 0b01:
            if(val < 1){
                return 0;
            }
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public short shortFloor(short val){
        switch(state){
        case 0b11:
            switch(Integer.signum(val)){
            case 1:
                return 1;
            case 0:
                return 0;
            default:
            }
            break;
        case 0b01:
            if(val >= 0){
                return 0;
            }
            break;
        case 0b10:
            if(val >= 1){
                return 1;
            }
        default:
        }
        return Short.MIN_VALUE;
    }
    @Override
    public int size(){
        switch(state){
        case 0b00:
            return 0;
        case 0b01:
        case 0b10:
            return 1;
        default:
            return 2;
        }
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        // TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
            boolean toInclusive){
        // TODO
        throw new NotYetImplementedException();
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
        switch(state){
        case 0b01:
            return new Boolean[]{Boolean.FALSE};
        case 0b10:
            return new Boolean[]{Boolean.TRUE};
        case 0b11:
            return new Boolean[]{Boolean.FALSE,Boolean.TRUE};
        default:
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final T[] arr;
        switch(state){
        case 0b01:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
            break;
        case 0b10:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
            break;
        case 0b11:
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
        switch(state){
        case 0b01:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
            break;
        case 0b10:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
            break;
        case 0b11:
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
        switch(state){
        case 0b01:
            return new boolean[]{false};
        case 0b10:
            return new boolean[]{true};
        case 0b11:
            return new boolean[]{false,true};
        default:
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
    }
    @Override
    public byte[] toByteArray(){
        switch(state){
        case 0b01:
            return new byte[]{0};
        case 0b10:
            return new byte[]{1};
        case 0b11:
            return new byte[]{0,1};
        default:
            return OmniArray.OfByte.DEFAULT_ARR;
        }
    }
    @Override
    public char[] toCharArray(){
        switch(state){
        case 0b01:
            return new char[]{0};
        case 0b10:
            return new char[]{1};
        case 0b11:
            return new char[]{0,1};
        default:
            return OmniArray.OfChar.DEFAULT_ARR;
        }
    }
    @Override
    public double[] toDoubleArray(){
        switch(state){
        case 0b01:
            return new double[]{0D};
        case 0b10:
            return new double[]{1D};
        case 0b11:
            return new double[]{0D,1D};
        default:
            return OmniArray.OfDouble.DEFAULT_ARR;
        }
    }
    @Override
    public float[] toFloatArray(){
        switch(state){
        case 0b01:
            return new float[]{0F};
        case 0b10:
            return new float[]{1F};
        case 0b11:
            return new float[]{0F,1F};
        default:
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
    }
    @Override
    public int[] toIntArray(){
        switch(state){
        case 0b01:
            return new int[]{0};
        case 0b10:
            return new int[]{1};
        case 0b11:
            return new int[]{0,1};
        default:
            return OmniArray.OfInt.DEFAULT_ARR;
        }
    }
    @Override
    public long[] toLongArray(){
        switch(state){
        case 0b01:
            return new long[]{0L};
        case 0b10:
            return new long[]{1L};
        case 0b11:
            return new long[]{0L,1L};
        default:
            return OmniArray.OfLong.DEFAULT_ARR;
        }
    }
    @Override
    public short[] toShortArray(){
        switch(state){
        case 0b01:
            return new short[]{0};
        case 0b10:
            return new short[]{1};
        case 0b11:
            return new short[]{0,1};
        default:
            return OmniArray.OfShort.DEFAULT_ARR;
        }
    }
    @Override
    boolean addFalse(){
        final int state;
        if(((state=this.state) & 0b01) != 0){
            this.state=state | 0b01;
            return true;
        }
        return false;
    }
    @Override
    boolean addTrue(){
        final int state;
        if(((state=this.state) & 0b10) != 0){
            this.state=state | 0b10;
            return true;
        }
        return false;
    }
    @Override
    boolean containsFalse(){
        return (state & 0b01) != 0;
    }
    @Override
    boolean containsTrue(){
        return (state & 0b10) != 0;
    }
    @Override
    boolean removeFalse(){
        final int state;
        if(((state=this.state) & 0b01) != 0){
            this.state=state & 0b10;
            return true;
        }
        return false;
    }
    @Override
    boolean removeTrue(){
        final int state;
        if(((state=this.state) & 0b10) != 0){
            this.state=state & 0b01;
            return true;
        }
        return false;
    }
    private void readObject(ObjectInputStream ois) throws IOException{
        state=ois.readUnsignedByte();
    }
    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.writeByte(state);
    }
    public static class Checked extends BooleanSetImpl2{
        private static final long serialVersionUID=1L;
        public Checked(){
            super();
        }
        public Checked(BooleanSetImpl2 that){
            super(that);
        }
        public Checked(Collection<? extends Boolean> that){
            super(that);
        }
        public Checked(OmniCollection.OfBoolean that){
            super(that);
        }
        public Checked(OmniCollection.OfRef<? extends Boolean> that){
            super(that);
        }
        Checked(int state){
            super(state);
        }
        @Override
        public Object clone(){
            return new Checked(state);
        }
        @Override
        public OmniIterator.OfBoolean descendingIterator(){
            return new CheckedDescendingItr(this);
        }
        @Override public OmniNavigableSet.OfBoolean descendingSet(){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public boolean firstBoolean(){
            switch(state){
            case 0b10:
                return true;
            case 0b00:
                throw new NoSuchElementException();
            default:
                return false;
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new CheckedAscendingItr(this);
        }
        @Override
        public boolean lastBoolean(){
            switch(state){
            case 0b01:
                return false;
            case 0b00:
                throw new NoSuchElementException();
            default:
                return true;
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
                boolean toInclusive){
            // TODO
            throw new NotYetImplementedException();
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
    }
    public static class CheckedDescending extends Descending{
        private static final long serialVersionUID=1L;
        public CheckedDescending(){
            super();
        }
        public CheckedDescending(BooleanSetImpl2 that){
            super(that);
        }
        public CheckedDescending(Collection<? extends Boolean> that){
            super(that);
        }
        public CheckedDescending(OmniCollection.OfBoolean that){
            super(that);
        }
        public CheckedDescending(OmniCollection.OfRef<? extends Boolean> that){
            super(that);
        }
        CheckedDescending(int state){
            super(state);
        }
        @Override
        public Object clone(){
            return new CheckedDescending(state);
        }
        @Override
        public OmniIterator.OfBoolean descendingIterator(){
            return new CheckedAscendingItr(this);
        }
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public boolean firstBoolean(){
            switch(state){
            case 0b01:
                return false;
            case 0b00:
                throw new NoSuchElementException();
            default:
                return true;
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new CheckedDescendingItr(this);
        }
        @Override
        public boolean lastBoolean(){
            switch(state){
            case 0b10:
                return true;
            case 0b00:
                throw new NoSuchElementException();
            default:
                return false;
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            // TODO
            throw new NotYetImplementedException();
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
                boolean toInclusive){
            // TODO
            throw new NotYetImplementedException();
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
    }
    public static class Descending extends BooleanSetImpl2{
        private static final long serialVersionUID=1L;
        private static int reverseCompare(boolean val1,boolean val2){
            if(val1 == val2){
                return 0;
            }
            if(val2){
                return 1;
            }
            return -1;
        }
        public Descending(){
            super();
        }
        public Descending(BooleanSetImpl2 that){
            super(that);
        }
        public Descending(Collection<? extends Boolean> that){
            super(that);
        }
        public Descending(OmniCollection.OfBoolean that){
            super(that);
        }
        public Descending(OmniCollection.OfRef<? extends Boolean> that){
            super(that);
        }
        Descending(int state){
            super(state);
        }
        @Override
        public boolean booleanCeiling(boolean val){
            return super.booleanFloor(val);
        }
        @Override
        public boolean booleanFloor(boolean val){
            return super.booleanCeiling(val);
        }
        @Override
        public byte byteCeiling(byte val){
            return super.byteFloor(val);
        }
        @Override
        public byte byteFloor(byte val){
            return super.byteCeiling(val);
        }
        @Override
        public Boolean ceiling(boolean val){
            return super.floor(val);
        }
        @Override
        public char charCeiling(char val){
            return super.charFloor(val);
        }
        @Override
        public char charFloor(char val){
            return super.charCeiling(val);
        }
        @Override
        public Object clone(){
            return new Descending(state);
        }
        @Override
        public BooleanComparator comparator(){
            return Descending::reverseCompare;
        }
        @Override
        public OmniIterator.OfBoolean descendingIterator(){
            return new UncheckedAscendingItr(this);
        }
        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            // TODO Auto-generated method stub
            return super.descendingSet();
        }
        @Override
        public double doubleCeiling(double val){
            return super.doubleFloor(val);
        }
        @Override
        public double doubleFloor(double val){
            return super.doubleCeiling(val);
        }
        @Override
        public boolean firstBoolean(){
            return super.lastBoolean();
        }
        @Override
        public float floatCeiling(float val){
            return super.floatFloor(val);
        }
        @Override
        public float floatFloor(float val){
            return super.floatCeiling(val);
        }
        @Override
        public Boolean floor(boolean val){
            return super.ceiling(val);
        }
        @Override
        public void forEach(BooleanConsumer action){
            switch(state){
            case 0b10:
                action.accept(true);
                break;
            case 0b11:
                action.accept(true);
            case 0b01:
                action.accept(false);
            default:
            }
        }
        @Override
        public void forEach(Consumer<? super Boolean> action){
            switch(state){
            case 0b10:
                action.accept(Boolean.TRUE);
                break;
            case 0b11:
                action.accept(Boolean.TRUE);
            case 0b01:
                action.accept(Boolean.FALSE);
            default:
            }
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            // TODO Auto-generated method stub
            return super.headSet(toElement);
        }
        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            // TODO Auto-generated method stub
            return super.headSet(toElement,inclusive);
        }
        @Override
        public Boolean higher(boolean val){
            return super.lower(val);
        }
        @Override
        public boolean higherBoolean(boolean val){
            return false;
        }
        @Override
        public byte higherByte(byte val){
            return super.lowerByte(val);
        }
        @Override
        public char higherChar(char val){
            return super.lowerChar(val);
        }
        @Override
        public double higherDouble(double val){
            return super.lowerDouble(val);
        }
        @Override
        public float higherFloat(float val){
            return super.lowerFloat(val);
        }
        @Override
        public int higherInt(int val){
            return super.lowerInt(val);
        }
        @Override
        public long higherLong(long val){
            return super.lowerLong(val);
        }
        @Override
        public short higherShort(short val){
            return super.lowerShort(val);
        }
        @Override
        public int intCeiling(int val){
            return super.intFloor(val);
        }
        @Override
        public int intFloor(int val){
            return super.intCeiling(val);
        }
        @Override
        public OmniIterator.OfBoolean iterator(){
            return new UncheckedDescendingItr(this);
        }
        @Override
        public boolean lastBoolean(){
            return super.firstBoolean();
        }
        @Override
        public long longCeiling(long val){
            return super.longFloor(val);
        }
        @Override
        public long longFloor(long val){
            return super.longCeiling(val);
        }
        @Override
        public Boolean lower(boolean val){
            return super.higher(val);
        }
        @Override
        public boolean lowerBoolean(boolean val){
            return super.higherBoolean(val);
        }
        @Override
        public byte lowerByte(byte val){
            return super.higherByte(val);
        }
        @Override
        public char lowerChar(char val){
            return super.higherChar(val);
        }
        @Override
        public double lowerDouble(double val){
            return super.higherDouble(val);
        }
        @Override
        public float lowerFloat(float val){
            return super.higherFloat(val);
        }
        @Override
        public int lowerInt(int val){
            return super.higherInt(val);
        }
        @Override
        public long lowerLong(long val){
            return super.higherLong(val);
        }
        @Override
        public short lowerShort(short val){
            return super.higherShort(val);
        }
        @Override
        public Boolean pollFirst(){
            return super.pollLast();
        }
        @Override
        public boolean pollFirstBoolean(){
            return super.pollLastBoolean();
        }
        @Override
        public byte pollFirstByte(){
            return super.pollLastByte();
        }
        @Override
        public char pollFirstChar(){
            return super.pollLastChar();
        }
        @Override
        public double pollFirstDouble(){
            return super.pollLastDouble();
        }
        @Override
        public float pollFirstFloat(){
            return super.pollLastFloat();
        }
        @Override
        public int pollFirstInt(){
            return super.pollLastInt();
        }
        @Override
        public long pollFirstLong(){
            return super.pollLastLong();
        }
        @Override
        public short pollFirstShort(){
            return super.pollLastShort();
        }
        @Override
        public Boolean pollLast(){
            return super.pollFirst();
        }
        @Override
        public boolean pollLastBoolean(){
            return super.pollFirstBoolean();
        }
        @Override
        public byte pollLastByte(){
            return super.pollFirstByte();
        }
        @Override
        public char pollLastChar(){
            return super.pollFirstChar();
        }
        @Override
        public double pollLastDouble(){
            return super.pollFirstDouble();
        }
        @Override
        public float pollLastFloat(){
            return super.pollFirstFloat();
        }
        @Override
        public int pollLastInt(){
            return super.pollFirstShort();
        }
        @Override
        public long pollLastLong(){
            return super.pollFirstLong();
        }
        @Override
        public short pollLastShort(){
            return super.pollFirstShort();
        }
        @Override
        public short shortCeiling(short val){
            return super.shortFloor(val);
        }
        @Override
        public short shortFloor(short val){
            return super.shortCeiling(val);
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            // TODO Auto-generated method stub
            return super.subSet(fromElement,toElement);
        }
        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,
                boolean toInclusive){
            // TODO Auto-generated method stub
            return super.subSet(fromElement,fromInclusive,toElement,toInclusive);
        }
        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            // TODO Auto-generated method stub
            return super.tailSet(fromElement);
        }
        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            // TODO Auto-generated method stub
            return super.tailSet(fromElement,inclusive);
        }
        @Override
        public Boolean[] toArray(){
            switch(state){
            case 0b01:
                return new Boolean[]{Boolean.FALSE};
            case 0b10:
                return new Boolean[]{Boolean.TRUE};
            case 0b11:
                return new Boolean[]{Boolean.TRUE,Boolean.FALSE};
            default:
                return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
            }
        }
        @SuppressWarnings("unchecked")
        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            final T[] arr;
            switch(state){
            case 0b01:
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
                break;
            case 0b10:
                (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
                break;
            case 0b11:
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
            switch(state){
            case 0b01:
                (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
                break;
            case 0b10:
                (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
                break;
            case 0b11:
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
            switch(state){
            case 0b01:
                return new boolean[]{false};
            case 0b10:
                return new boolean[]{true};
            case 0b11:
                return new boolean[]{true,false};
            default:
                return OmniArray.OfBoolean.DEFAULT_ARR;
            }
        }
        @Override
        public byte[] toByteArray(){
            switch(state){
            case 0b01:
                return new byte[]{0};
            case 0b10:
                return new byte[]{1};
            case 0b11:
                return new byte[]{1,0};
            default:
                return OmniArray.OfByte.DEFAULT_ARR;
            }
        }
        @Override
        public char[] toCharArray(){
            switch(state){
            case 0b01:
                return new char[]{0};
            case 0b10:
                return new char[]{1};
            case 0b11:
                return new char[]{1,0};
            default:
                return OmniArray.OfChar.DEFAULT_ARR;
            }
        }
        @Override
        public double[] toDoubleArray(){
            switch(state){
            case 0b01:
                return new double[]{0D};
            case 0b10:
                return new double[]{1D};
            case 0b11:
                return new double[]{1D,0D};
            default:
                return OmniArray.OfDouble.DEFAULT_ARR;
            }
        }
        @Override
        public float[] toFloatArray(){
            switch(state){
            case 0b01:
                return new float[]{0F};
            case 0b10:
                return new float[]{1F};
            case 0b11:
                return new float[]{1F,0F};
            default:
                return OmniArray.OfFloat.DEFAULT_ARR;
            }
        }
        @Override
        public int[] toIntArray(){
            switch(state){
            case 0b01:
                return new int[]{0};
            case 0b10:
                return new int[]{1};
            case 0b11:
                return new int[]{1,0};
            default:
                return OmniArray.OfInt.DEFAULT_ARR;
            }
        }
        @Override
        public long[] toLongArray(){
            switch(state){
            case 0b01:
                return new long[]{0L};
            case 0b10:
                return new long[]{1L};
            case 0b11:
                return new long[]{1L,0L};
            default:
                return OmniArray.OfLong.DEFAULT_ARR;
            }
        }
        @Override
        public short[] toShortArray(){
            switch(state){
            case 0b01:
                return new short[]{0};
            case 0b10:
                return new short[]{1};
            case 0b11:
                return new short[]{1,0};
            default:
                return OmniArray.OfShort.DEFAULT_ARR;
            }
        }
    }
    private static class CheckedAscendingItr extends AbstractBooleanItr{
        transient final BooleanSetImpl2 root;
        transient int itrState;
        private CheckedAscendingItr(BooleanSetImpl2 root){
            this.root=root;
            itrState=root.state;
        }
        private CheckedAscendingItr(CheckedAscendingItr itr){
            root=itr.root;
            itrState=itr.itrState;
        }
        @Override
        public Object clone(){
            return new CheckedAscendingItr(this);
        }
        @Override
        public void forEachRemaining(BooleanConsumer action){
            switch(root.state << 4 | itrState){
            case 0b010001: // false remains, so iterate over it and set the lastRet flag to false
                action.accept(false);
                itrState=0b0100;
                break;
            case 0b110011: // true and false remain, so iterate over them and set the lastRet flag to true
                action.accept(false);
            case 0b100010: // true remains, so iterate over it and set the lastRet flag to true
            case 0b110110: // we have already iterated over false, true remains, so iterate over it and set
                           // the lastRet flag to true
                action.accept(true);
                itrState=0b1000;
            case 0b000000:
            case 0b010000:
            case 0b010100:
            case 0b101000:
            case 0b111000:
                // iteration has ended
                break;
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action){
            switch(root.state << 4 | itrState){
            case 0b010001: // false remains, so iterate over it and set the lastRet flag to false
                action.accept(Boolean.FALSE);
                itrState=0b0100;
                break;
            case 0b110011: // true and false remain, so iterate over them and set the lastRet flag to true
                action.accept(Boolean.FALSE);
            case 0b100010: // true remains, so iterate over it and set the lastRet flag to true
            case 0b110110: // we have already iterated over false, true remains, so iterate over it and set
                           // the lastRet flag to true
                action.accept(Boolean.TRUE);
                itrState=0b1000;
            case 0b000000:
            case 0b010000:
            case 0b010100:
            case 0b101000:
            case 0b111000:
                // iteration has ended
                break;
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            }
        }
        @Override
        public boolean hasNext(){
            return (itrState & 0b11) != 0;
        }
        @Override
        public boolean nextBoolean(){
            switch(root.state << 4 | itrState){
            case 0b000000:
            case 0b010000:
            case 0b010100:
            case 0b101000:
            case 0b111000:
                // iteration has ended
                throw new NoSuchElementException();
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            case 0b100010: // true remains, so return true
            case 0b110110: // we have already iterated over false, true remains, so return true
                itrState=0b1000;
                return true;
            case 0b010001: // false remains, so return false
                itrState=0b0100;
                break;
            case 0b110011: // true and false remain, so return false
                itrState=0b0110;
            }
            return false;
        }
        @Override
        public void remove(){
            final BooleanSetImpl2 root;
            switch((root=this.root).state << 4 | itrState){
            case 0b000100: // expected root to contain false
            case 0b000110: // expected root to be full
            case 0b001000: // expected root to contain true
            case 0b010110: // expected root to be full
            case 0b011000: // expected root to contain true
            case 0b100100: // expected root to contain only false
            case 0b100110: // expected root to be full
            case 0b110100: // expected root to contain only false
                throw new ConcurrentModificationException();
            default:
                // all other states are impossible or indicate improper iteration
                throw new IllegalStateException();
            case 0b110110: // iterator still has true left, we just returned false, so remove false
                root.state=0b10;
                itrState=0b0010;
                return;
            case 0b010100: // iterator depleted, just returned false, so remove false
            case 0b101000: // iterator depleted, just returned true, so remove true
                root.state=0b00;
                break;
            case 0b111000: // iterator depleted, we just returned true, so remove true
                root.state=0b01;
            }
            itrState=0b0000;
            return;
        }
    }
    private static class CheckedDescendingItr extends CheckedAscendingItr{
        private CheckedDescendingItr(BooleanSetImpl2 root){
            super(root);
        }
        private CheckedDescendingItr(CheckedAscendingItr itr){
            super(itr);
        }
        @Override
        public Object clone(){
            return new CheckedDescendingItr(this);
        }
        @Override
        public void forEachRemaining(BooleanConsumer action){
            switch(root.state << 4 | itrState){
            case 0b100010: // true remains, so iterate over it and set the lastRet flag to true
                action.accept(true);
                itrState=0b1000;
                break;
            case 0b110011: // true and false remain, so iterate over them and set the lastRet flag to false
                action.accept(true);
            case 0b010001: // false remains, so iterate over it and set the lastRet flag to false
            case 0b111001: // we have already iterated over true, false remains, so iterate over it and set
                           // the lastRet flag to false
                action.accept(false);
                itrState=0b0100;
            case 0b000000:
            case 0b010100:
            case 0b100000:
            case 0b101000:
            case 0b110100:
                // iteration has ended
                break;
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            }
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action){
            switch(root.state << 4 | itrState){
            case 0b100010: // true remains, so iterate over it and set the lastRet flag to true
                action.accept(Boolean.TRUE);
                itrState=0b1000;
                break;
            case 0b110011: // true and false remain, so iterate over them and set the lastRet flag to false
                action.accept(Boolean.TRUE);
            case 0b010001: // false remains, so iterate over it and set the lastRet flag to false
            case 0b111001: // we have already iterated over true, false remains, so iterate over it and set
                           // the lastRet flag to false
                action.accept(Boolean.FALSE);
                itrState=0b0100;
            case 0b000000:
            case 0b010100:
            case 0b100000:
            case 0b101000:
            case 0b110100:
                // iteration has ended
                break;
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            }
        }
        @Override
        public boolean nextBoolean(){
            switch(root.state << 4 | itrState){
            case 0b000000:
            case 0b010100:
            case 0b100000:
            case 0b101000:
            case 0b110100:
                // iteration has ended
                throw new NoSuchElementException();
            default:
                // all other states are either impossible or indicate concurrent modification
                throw new ConcurrentModificationException();
            case 0b010001: // false remains, so return false
            case 0b111001: // we have already iterated over true, false remains, so return false
                itrState=0b0100;
                return false;
            case 0b100010: // true remains, so return true
                itrState=0b1000;
                break;
            case 0b110011: // true and false remain, so return true;
                itrState=0b1001;
            }
            return true;
        }
        @Override
        public void remove(){
            final BooleanSetImpl2 root;
            switch((root=this.root).state << 4 | itrState){
            case 0b000100: // expected root to contain false
            case 0b001000: // expected root to contain true
            case 0b001001: // expected root to be full
            case 0b011000: // expected root to contain only true
            case 0b011001: // expected root to be full
            case 0b100100: // expected root to contain false
            case 0b101001: // expected root to be full
            case 0b111000: // expected root to contain only true
                throw new ConcurrentModificationException();
            default:
                // all other states are impossible or indicate improper iteration
                throw new IllegalStateException();
            case 0b111001: // iterator still has false left, we just returned true, so remove true
                root.state=0b01;
                itrState=0b0001;
                return;
            case 0b010100: // iterator depleted, just returned false, so remove false
            case 0b101000: // iterator depleted, just returned true, so remove true
                root.state=0b00;
                break;
            case 0b110100: // iterator depleted, we just returned false, so remove false
                root.state=0b10;
            }
            itrState=0b0000;
            return;
        }
    }
    private static class UncheckedAscendingItr extends AbstractBooleanItr{
        transient final BooleanSetImpl2 root;
        transient int itrState;
        private UncheckedAscendingItr(BooleanSetImpl2 root){
            this.root=root;
            itrState=root.state;
        }
        private UncheckedAscendingItr(UncheckedAscendingItr itr){
            root=itr.root;
            itrState=itr.itrState;
        }
        @Override
        public Object clone(){
            return new UncheckedAscendingItr(this);
        }
        @Override
        public void forEachRemaining(BooleanConsumer action){
            switch(itrState){
            case 0b11:
                action.accept(false);
            case 0b10:
                action.accept(true);
                break;
            case 0b01:
                action.accept(false);
            default:
            }
            itrState=0b00;
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action){
            switch(itrState){
            case 0b11:
                action.accept(Boolean.FALSE);
            case 0b10:
                action.accept(Boolean.TRUE);
                break;
            case 0b01:
                action.accept(Boolean.FALSE);
            default:
            }
            itrState=0b00;
        }
        @Override
        public boolean hasNext(){
            return itrState != 0;
        }
        @Override
        public boolean nextBoolean(){
            switch(itrState){
            case 0b10:
                itrState=0b00;
                return true;
            case 0b11:
                itrState=0b10;
                break;
            default:
                itrState=0b00;
            }
            return false;
        }
        @Override
        public void remove(){
            final BooleanSetImpl2 root;
            switch(itrState & (root=this.root).state << 2){
            case 0b1100:
                root.state=0b01;
                break;
            case 0b1110:
                root.state=0b10;
                break;
            default:
                root.state=0b00;
            }
        }
    }
    private static class UncheckedDescendingItr extends UncheckedAscendingItr{
        private UncheckedDescendingItr(BooleanSetImpl2 root){
            super(root);
        }
        private UncheckedDescendingItr(UncheckedDescendingItr itr){
            super(itr);
        }
        @Override
        public Object clone(){
            return new UncheckedDescendingItr(this);
        }
        @Override
        public void forEachRemaining(BooleanConsumer action){
            switch(itrState){
            case 0b11:
                action.accept(true);
            case 0b01:
                action.accept(false);
                break;
            case 0b10:
                action.accept(true);
            default:
            }
            itrState=0b00;
        }
        @Override
        public void forEachRemaining(Consumer<? super Boolean> action){
            switch(itrState){
            case 0b11:
                action.accept(Boolean.TRUE);
            case 0b01:
                action.accept(Boolean.FALSE);
                break;
            case 0b10:
                action.accept(Boolean.TRUE);
            default:
            }
            itrState=0b00;
        }
        @Override
        public boolean nextBoolean(){
            switch(itrState){
            default:
                itrState=0b00;
                return false;
            case 0b11:
                itrState=0b01;
                break;
            case 0b10:
                itrState=0b00;
            }
            return true;
        }
        @Override
        public void remove(){
            final BooleanSetImpl2 root;
            switch(itrState & (root=this.root).state << 2){
            case 0b1100:
                root.state=0b10;
                break;
            case 0b1101:
                root.state=0b01;
                break;
            default:
                root.state=0b00;
            }
        }
    }
//    private static class ReverseView extends AbstractBooleanSet{
//        final BooleanSetImpl2 root;
//        private ReverseView(BooleanSetImpl2 root) {
//            this.root=root;
//        }
//        @Override
//        public int size() {
//            return root.size();
//        }
//        @Override
//        public boolean isEmpty() {
//            return root.isEmpty();
//        }
//        @Override
//        public void clear() {
//            root.clear();
//        }
//        @Override
//        public boolean removeIf(BooleanPredicate filter) {
//            return root.removeIf(filter);
//        }
//        @Override
//        public boolean removeIf(Predicate<? super Boolean> filter) {
//            return root.removeIf(filter);
//        }
//        @Override
//        public Object clone() {
//            return new Descending(root.state);
//        }
//        @Override
//        public byte[] toByteArray() {
//            
//        }
//        
//        
//        
//    }
}