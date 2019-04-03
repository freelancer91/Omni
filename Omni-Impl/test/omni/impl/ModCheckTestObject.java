package omni.impl;

import omni.api.OmniCollection;
import omni.util.TypeConversionUtil;
@SuppressWarnings({"rawtypes","unchecked"})
public class ModCheckTestObject{
    public int numCalls;
    @Override
    public boolean equals(Object val) {
        ++numCalls;
        throw new IndexOutOfBoundsException();
    }
    @Override
    public int hashCode(){
        ++numCalls;
        throw new IndexOutOfBoundsException();
    }
    @Override
    public String toString() {
        ++numCalls;
        throw new IndexOutOfBoundsException();
    }
    public static class Modding extends ModCheckTestObject{
        public final OmniCollection.OfRef col;
        public Modding(OmniCollection.OfRef<?> col){
            this.col=col;
        }
        @Override
        public boolean equals(Object val) {
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            return val == this;
        }
        @Override
        public int hashCode(){
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            return System.identityHashCode(this);
        }
        @Override
        public String toString() {
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            return getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(this));
        }
    }
    public static class ModdingAndThrowing extends Modding{
        public ModdingAndThrowing(OmniCollection.OfRef<?> col){
            super(col);
        }
        @Override
        public boolean equals(Object val) {
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            throw new IndexOutOfBoundsException();
        }
        @Override
        public int hashCode(){
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            throw new IndexOutOfBoundsException();
        }
        @Override
        public String toString() {
            ++numCalls;
            col.add(TypeConversionUtil.convertToObject(0));
            throw new IndexOutOfBoundsException();
        }
    }
}
