package omni.impl.set;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.function.BooleanComparator;
import omni.function.BooleanConsumer;
import omni.function.BooleanPredicate;
import omni.util.NotYetImplementedException;
import omni.util.OmniArray;

public class BooleanSetImpl implements OmniNavigableSet.OfBoolean,Cloneable,Serializable{
    public static BooleanSetImpl newUncheckedAscending() {
        return new BooleanSetImpl();
    }
    public static BooleanSetImpl newUncheckedDescending() {
        return new BooleanSetImpl(0b0100);
    }
    public static BooleanSetImpl newCheckedAscending() {
        return new BooleanSetImpl(0b1000);
    }
    public static BooleanSetImpl newCheckedDescending() {
        return new BooleanSetImpl(0b1100);
    }
    
    
    
    transient int state;
    
    private BooleanSetImpl(){
    }
    
    private BooleanSetImpl(int state){
        this.state=state;
    }
    @Override
    public Object clone() {
        return new BooleanSetImpl(this.state);
    }
    @Override
    public boolean lowerBoolean(boolean val) {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            return val;
        case 0b111:
            //descending full
        case 0b110:
            //descending contains true
            return !val;
        default:
            return false;
        }
    }
    @Override
    public boolean higherBoolean(boolean val) {
        switch(this.state&0b0111) {
        case 0b010:
            //ascending contains true
        case 0b011:
            //ascending full
            return !val;
        default:
            return false;
        }
    }
    @Override
    public boolean booleanFloor(boolean val) {
        switch(this.state&0b0111) {
        case 0b110:
            //descending contains true
            return true;
        case 0b010:
            //ascending contains true
        case 0b011:
            //ascending full
        case 0b111:
            //descending full
            return val;
        default:
            return false;
        }
    }
    @Override
    public Boolean lower(boolean val){
        switch(this.state&0b0111) {
        case 0b001:
        case 0b011:
            if(!val) {
                break;
            }
            return Boolean.FALSE;
        case 0b110:
        case 0b111:
            if(val) {
                break;
            }
            return Boolean.TRUE;
        default:
        }
        return null;
    }
    @Override
    public Boolean floor(boolean val){
        switch(this.state&0b0111){
        case 0b101:
            if(val) {
                break;
            }
        case 0b001:
            return Boolean.FALSE;
        case 0b010:
            if(!val) {
               break;
            }
        case 0b110:
            return Boolean.TRUE;
        case 0b011:
        case 0b111:
            return val;
        default:
        }
        return null;
    }
    @Override
    public Boolean higher(boolean val){
        switch(this.state&0b0111) {
        case 0b010:
        case 0b011:
            if(val) {
                break;
            }
            return Boolean.TRUE;
        case 0b101:
        case 0b111:
            if(!val) {
                break;
            }
            return Boolean.FALSE;
        default:
        }
        return null;
    }
    @Override public Boolean ceiling(boolean val) {
        switch(this.state&0b111) {
        case 0b001:
            //ascending contains false
            if(val) {
                break;
            }
        case 0b101:
            //descending contains false
            return Boolean.FALSE;
        case 0b110:
            //descending contains true
            if(!val) {
                break;
            }
        case 0b010:
            //ascending contains true
            return Boolean.TRUE;
        case 0b011:
            //ascending full
        case 0b111:
            //descending full
            return val;
        default:
            //empty
        }
        return null;
    }
    @Override public boolean booleanCeiling(boolean val) {
        switch(this.state&0b0111) {
        case 0b010:
            //ascending contains true
            return true;
        case 0b110:
            //descending contains true
        case 0b011:
            //ascending full
        case 0b111:
            //descending full
            return val;
        default:
            return false;
        }
    }
    @Override
    public boolean pollLastBoolean() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return false;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return true;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return false;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return true;
        case 0b011:
            //ascending full
            this.state=0b001;
            return true;
        case 0b111:
            //descending full
            this.state=0b110;
        default:
            //empty
            return false;
        }
    }
    @Override
    public boolean pollFirstBoolean() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return false;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return true;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return false;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return true;
        case 0b111:
            //descending full
            this.state=0b101;
            return true;
        case 0b011:
            //ascending full
            this.state=0b010;
        default:
            //empty
            return false;
        }
    }
    @Override
    public byte pollLastByte() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0;
        default:
            //empty
            return Byte.MIN_VALUE;
        }
    }
    @Override
    public byte pollFirstByte() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1;
        default:
            //empty
            return Byte.MIN_VALUE;
        }
    }
    @Override
    public char pollLastChar() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1;
        case 0b111:
            //descending full
            this.state=0b110;
        default:
            //empty
            return Character.MIN_VALUE;
        }
    }
    @Override
    public char pollFirstChar() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b010;
        default:
            //empty
            return Character.MIN_VALUE;
        }
    }
    @Override
    public short pollLastShort() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0;
        default:
            //empty
            return Short.MIN_VALUE;
        }
    }
    @Override
    public short pollFirstShort() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1;
        default:
            //empty
            return Short.MIN_VALUE;
        }
    }
    @Override
    public int pollLastInt() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0;
        default:
            //empty
            return Integer.MIN_VALUE;
        }
    }
    @Override
    public int pollFirstInt() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1;
        default:
            //empty
            return Integer.MIN_VALUE;
        }
    }
    @Override
    public long pollLastLong() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0L;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1L;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0L;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1L;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1L;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0L;
        default:
            //empty
            return Long.MIN_VALUE;
        }
    }
    @Override
    public long pollFirstLong() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0L;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1L;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0L;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1L;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0L;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1L;
        default:
            //empty
            return Long.MIN_VALUE;
        }
    }
    @Override
    public float pollLastFloat() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0F;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1F;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0F;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1F;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1F;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0F;
        default:
            //empty
            return Float.NaN;
        }
    }
    @Override
    public float pollFirstFloat() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0F;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1F;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0F;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1F;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0F;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1F;
        default:
            //empty
            return Float.NaN;
        }
    }
    @Override
    public double pollLastDouble() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0D;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1D;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0D;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1D;
        case 0b011:
            //ascending full
            this.state=0b001;
            return 1D;
        case 0b111:
            //descending full
            this.state=0b110;
            return 0D;
        default:
            //empty
            return Double.NaN;
        }
    }
    @Override
    public double pollFirstDouble() {
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return 0D;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return 1D;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return 0D;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return 1D;
        case 0b011:
            //ascending full
            this.state=0b010;
            return 0D;
        case 0b111:
            //descending full
            this.state=0b101;
            return 1D;
        default:
            //empty
            return Double.NaN;
        }
    }
    @Override
    public Boolean pollFirst(){
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return Boolean.FALSE;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return Boolean.TRUE;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return Boolean.FALSE;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return Boolean.TRUE;
        case 0b011:
            //ascending full
            this.state=0b010;
            return Boolean.FALSE;
        case 0b111:
            //descending full
            this.state=0b101;
            return Boolean.TRUE;
        default:
            //empty
            return null;
        }
    }
    @Override
    public Boolean pollLast(){
        switch(this.state&0b0111) {
        case 0b001:
            //ascending contains false
            this.state=0b000;
            return Boolean.FALSE;
        case 0b010:
            //ascending contains true
            this.state=0b000;
            return Boolean.TRUE;
        case 0b101:
            //descending contains false
            this.state=0b100;
            return Boolean.FALSE;
        case 0b110:
            //descending contains true
            this.state=0b100;
            return Boolean.TRUE;
        case 0b011:
            //ascending full
            this.state=0b001;
            return Boolean.TRUE;
        case 0b111:
            //descending full
            this.state=0b110;
            return Boolean.FALSE;
        default:
            //empty
            return null;
        }
    }
    @Override
    public int size() {
        switch(this.state&0b0011) {
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
    public OmniNavigableSet.OfBoolean descendingSet(){
        return new ReverseView(this);
    }
    private static class ReverseView implements OmniNavigableSet.OfBoolean,Serializable,Cloneable{
        private static final long serialVersionUID=1L;
        private final BooleanSetImpl root;
        
        private ReverseView(BooleanSetImpl root){
            this.root=root;
        }
        
        //TODO read and write
        
        @Override
        public void forEach(Consumer<? super Boolean> action) {
            //TODO
        }
        @Override
        public boolean removeIf(Predicate<? super Boolean> filter) {
            return root.removeIf(filter);
        }
        
        @Override
        public Object clone() {
            //TODO
            return null;
        }
        
        @Override
        public byte pollFirstByte(){
            return root.pollLastByte();
        }

        @Override
        public byte pollLastByte(){
            return root.pollFirstByte();
        }

        @Override
        public short pollFirstShort(){
            return root.pollLastShort();
        }

        @Override
        public short pollLastShort(){
            return root.pollFirstShort();
        }

        @Override
        public int pollFirstInt(){
            return root.pollLastInt();
        }

        @Override
        public int pollLastInt(){
            return root.pollFirstInt();
        }

        @Override
        public long pollFirstLong(){
            return root.pollLastLong();
        }

        @Override
        public long pollLastLong(){
            return root.pollFirstLong();
        }

        @Override
        public float pollFirstFloat(){
            return root.pollLastFloat();
        }

        @Override
        public float pollLastFloat(){
            return root.pollFirstFloat();
        }

        @Override
        public double pollFirstDouble(){
            return root.pollLastDouble();
        }

        @Override
        public double pollLastDouble(){
            return root.pollFirstDouble();
        }

        @Override
        public void clear(){
            root.clear();
        }

        @Override
        public boolean contains(Object val){
            return root.contains(val);
        }
        @Override
        public boolean remove(Object val){
            return root.remove(val);
        }

        @Override
        public boolean contains(boolean val){
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
        public boolean contains(float val){
            return root.contains(val);
            }

        @Override
        public boolean contains(double val){
            return root.contains(val);
            }

     



        @Override
        public boolean removeVal(boolean val){
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
        public boolean removeVal(float val){
            return root.removeVal(val);
            }

        @Override
        public boolean removeVal(double val){
            return root.removeVal(val);
            }

     
        @Override
        public boolean isEmpty(){
            return root.isEmpty();
        }

        @Override
        public int size(){
            return root.size();
        }

        @Override
        public <T> T[] toArray(IntFunction<T[]> arrConstructor){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public <T> T[] toArray(T[] dst){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean add(Boolean e){
            return root.add((boolean)e);
        }

      

        @Override
        public Boolean pollFirst(){
            return root.pollLast();
        }

        @Override
        public Boolean pollLast(){
            return root.pollFirst();
        }

       

        @Override
        public double[] toDoubleArray(){
            // TODO Auto-generated method stub
            return null;
        }

     

        @Override
        public float[] toFloatArray(){
            // TODO Auto-generated method stub
            return null;
        }

       

        @Override
        public long[] toLongArray(){
            // TODO Auto-generated method stub
            return null;
        }

       

        @Override
        public int[] toIntArray(){
            // TODO Auto-generated method stub
            return null;
        }

    

        @Override
        public short[] toShortArray(){
            // TODO Auto-generated method stub
            return null;
        }

      

        @Override
        public byte[] toByteArray(){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public char pollFirstChar(){
            return root.pollLastChar();
        }

        @Override
        public char pollLastChar(){
            return root.pollFirstChar();
        }

       

        @Override
        public char[] toCharArray(){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public BooleanComparator comparator(){
            return comparatorHelper((root.state&0b0100)!=0);
        }

        @Override
        public boolean firstBoolean(){
            return root.lastBoolean();
        }

        @Override
        public boolean lastBoolean(){
            return root.firstBoolean();
        }


        @Override
        public boolean add(boolean val){
            return root.add(val);
        }

        @Override
        public void forEach(BooleanConsumer action){
            // TODO Auto-generated method stub
            
        }

        @Override
        public boolean removeIf(BooleanPredicate filter){
            return root.removeIf(filter);
        }

        @Override
        public Boolean[] toArray(){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean[] toBooleanArray(){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniIterator.OfBoolean iterator(){
            return root.descendingIterator();
        }

        @Override
        public OmniIterator.OfBoolean descendingIterator(){
            return root.iterator();
        }

        @Override
        public OmniNavigableSet.OfBoolean descendingSet(){
            return root;
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniNavigableSet.OfBoolean headSet(boolean toElement){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean pollFirstBoolean(){
            return root.pollLastBoolean();
        }

        @Override
        public boolean pollLastBoolean(){
            return root.pollFirstBoolean();
        }

        @Override
        public Boolean lower(boolean val){
            return root.higher(val);
        }

        @Override
        public Boolean floor(boolean val){
            return root.ceiling(val);
        }

        @Override
        public Boolean ceiling(boolean val){
            return root.floor(val);
        }

        @Override
        public Boolean higher(boolean val){
            return root.lower(val);
        }

        @Override
        public boolean lowerBoolean(boolean val){
            return root.higherBoolean(val);
        }

        @Override
        public boolean booleanFloor(boolean val){
            return root.booleanCeiling(val);
        }

        @Override
        public boolean booleanCeiling(boolean val){
            return root.booleanFloor(val);
        }

        @Override
        public boolean higherBoolean(boolean val){
            return root.lowerBoolean(val);
        }
        
    }
    @Override
    public OmniNavigableSet.OfBoolean tailSet(boolean fromElement){
        //TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniNavigableSet.OfBoolean tailSet(boolean fromElement,boolean inclusive){
        //TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniIterator.OfBoolean descendingIterator(){
        //TODO
        throw new NotYetImplementedException();
    }
    @Override
    public OmniIterator.OfBoolean iterator(){
        //TODO
        throw new NotYetImplementedException();
    }
    @Override
    public Boolean[] toArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new Boolean[] {Boolean.FALSE};
        case 0b010:
        case 0b110:
            return new Boolean[] {Boolean.TRUE};
        case 0b011:
            return new Boolean[] {Boolean.FALSE,Boolean.TRUE};
        case 0b111:
            return new Boolean[] {Boolean.TRUE,Boolean.FALSE};
        default:
            return OmniArray.OfBoolean.DEFAULT_BOXED_ARR;
        }
    }
    @Override
    public boolean[] toBooleanArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new boolean[] {false};
        case 0b010:
        case 0b110:
            return new boolean[] {true};
        case 0b011:
            return new boolean[] {false,true};
        case 0b111:
            return new boolean[] {true,false};
        default:
            return OmniArray.OfBoolean.DEFAULT_ARR;
        }
    }
    @Override
    public byte[] toByteArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new byte[] {0};
        case 0b010:
        case 0b110:
            return new byte[] {1};
        case 0b011:
            return new byte[] {0,1};
        case 0b111:
            return new byte[] {1,0};
        default:
            return OmniArray.OfByte.DEFAULT_ARR;
        }
    }
    @Override
    public short[] toShortArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new short[] {0};
        case 0b010:
        case 0b110:
            return new short[] {1};
        case 0b011:
            return new short[] {0,1};
        case 0b111:
            return new short[] {1,0};
        default:
            return OmniArray.OfShort.DEFAULT_ARR;
        }
    }
    @Override
    public char[] toCharArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new char[] {0};
        case 0b010:
        case 0b110:
            return new char[] {1};
        case 0b011:
            return new char[] {0,1};
        case 0b111:
            return new char[] {1,0};
        default:
            return OmniArray.OfChar.DEFAULT_ARR;
        }
    }
    @Override
    public int[] toIntArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new int[] {0};
        case 0b010:
        case 0b110:
            return new int[] {1};
        case 0b011:
            return new int[] {0,1};
        case 0b111:
            return new int[] {1,0};
        default:
            return OmniArray.OfInt.DEFAULT_ARR;
        }
    }
    @Override
    public long[] toLongArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new long[] {0L};
        case 0b010:
        case 0b110:
            return new long[] {1L};
        case 0b011:
            return new long[] {0L,1L};
        case 0b111:
            return new long[] {1L,0L};
        default:
            return OmniArray.OfLong.DEFAULT_ARR;
        }
    }
    @Override
    public float[] toFloatArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new float[] {0F};
        case 0b010:
        case 0b110:
            return new float[] {1F};
        case 0b011:
            return new float[] {0F,1F};
        case 0b111:
            return new float[] {1F,0F};
        default:
            return OmniArray.OfFloat.DEFAULT_ARR;
        }
    }
    @Override
    public double[] toDoubleArray() {
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            return new double[] {0D};
        case 0b010:
        case 0b110:
            return new double[] {1D};
        case 0b011:
            return new double[] {0D,1D};
        case 0b111:
            return new double[] {1D,0D};
        default:
            return OmniArray.OfDouble.DEFAULT_ARR;
        }
    }

    private static BooleanComparator comparatorHelper(boolean ascending) {
        if(ascending) {
            return Boolean::compare;
        }else {
            return (v1,v2)->v1 == v2 ? 0 : v1 ? -1 : 1;
        }
    }
    
    @Override
    public BooleanComparator comparator() {
        return comparatorHelper((this.state&0b0100)==0);
    }
    
    @Override
    public void forEach(BooleanConsumer action) {
        switch(this.state&0b0111) {
        case 0b011:
            //ascending full
            action.accept(false);
        case 0b010:
            //ascending contains true
        case 0b110:
            //descending contains true
            action.accept(true);
            break;
        case 0b111:
            //descending full
            action.accept(true);
        case 0b001:
            //ascending contains false
        case 0b101:
            //descending contains false
            action.accept(false);
        default:
            //do nothing
        }
    }
    @Override
    public void forEach(Consumer<? super Boolean> action) {
        switch(this.state&0b0111) {
        case 0b011:
            //ascending full
            action.accept(Boolean.FALSE);
        case 0b010:
            //ascending contains true
        case 0b110:
            //descending contains true
            action.accept(Boolean.TRUE);
            break;
        case 0b111:
            //descending full
            action.accept(Boolean.TRUE);
        case 0b001:
            //ascending contains false
        case 0b101:
            //descending contains false
            action.accept(Boolean.FALSE);
        default:
            //do nothing
        }
    }
    
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeByte(this.state);
    }
    private void readObject(ObjectInputStream ois) throws IOException {
        this.state=ois.readUnsignedByte();
    }
    @Override
    public void clear(){
        this.state&=0b1100;
    }
    @Override
    public boolean contains(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean contains(boolean val){
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(int val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean contains(long val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean contains(float val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean contains(double val){
        // TODO Auto-generated method stub
        return false;
    }
    
    
    @Override
    public boolean remove(Object val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean removeVal(boolean val){
        // TODO Auto-generated method stub
        return false;
    }
   
    @Override
    public boolean removeVal(int val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean removeVal(long val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean removeVal(float val){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean removeVal(double val){
        // TODO Auto-generated method stub
        return false;
    }
    
    @Override
    public boolean isEmpty(){
        return (this.state&0b11)==0;
    }
    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(IntFunction<T[]> arrConstructor){
        final T[] arr;
        switch(this.state&0b0111) {
        case 0b001:
        case 0b101:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.FALSE;
            break;
        case 0b010:
        case 0b110:
            (arr=arrConstructor.apply(1))[0]=(T)Boolean.TRUE;
            break;
        case 0b011:
            (arr=arrConstructor.apply(2))[0]=(T)Boolean.FALSE;
            arr[1]=(T)Boolean.TRUE;
            break;
        case 0b111:
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
        switch(this.state&0b111) {
        case 0b001:
        case 0b101:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.FALSE;
            break;
        case 0b010:
        case 0b110:
            (dst=OmniArray.uncheckedArrResize(1,dst))[0]=(T)Boolean.TRUE;
            break;
        case 0b011:
            (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.FALSE;
            dst[1]=(T)Boolean.TRUE;
            break;
        case 0b111:
            (dst=OmniArray.uncheckedArrResize(2,dst))[0]=(T)Boolean.TRUE;
            dst[1]=(T)Boolean.FALSE;
            break;
        default:
            if(dst.length!=0) {
                dst[0]=null;
            }
        }
        return dst;
    }
    @Override
    public boolean add(Boolean e){
        return this.add((boolean)e);
    }
   
    
   
    @Override
    public boolean firstBoolean(){
        switch(state) {
        case 0b0001:
        case 0b0011:
        case 0b0101:
        case 0b1001:
        case 0b1011:
        case 0b1101:
            return false;
        case 0b0010:
        case 0b0110:
        case 0b0111:
        case 0b1010:
        case 0b1110:
        case 0b1111:
            return true;
        default:
            throw new NoSuchElementException();
        }
    }
    @Override
    public boolean lastBoolean(){
        switch(state) {
        case 0b0001:
        case 0b0101:
        case 0b0111:
        case 0b1001:
        case 0b1101:
        case 0b1111:
            return false;
        case 0b0010:
        case 0b0011:
        case 0b0110:
        case 0b1010:
        case 0b1011:
        case 0b1110:
            return true;
        default:
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean add(boolean val){
       int state;
       if((state=this.state)!=(state&=val?0b10:0b01)) {
           this.state=state;
           return true;
       }
       return false;
    }
    @Override
    public boolean removeIf(BooleanPredicate filter){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean removeIf(Predicate<? super Boolean> filter){
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean fromInclusive,boolean toElement,boolean toInclusive){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement,boolean inclusive){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniNavigableSet.OfBoolean subSet(boolean fromElement,boolean toElement){
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public OmniNavigableSet.OfBoolean headSet(boolean toElement){
        // TODO Auto-generated method stub
        return null;
    }

}
