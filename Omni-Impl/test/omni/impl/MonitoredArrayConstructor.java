package omni.impl;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.function.IntFunction;
import omni.api.OmniCollection;
import omni.util.TypeConversionUtil;
public class MonitoredArrayConstructor<E> implements IntFunction<E[]>{
    public int numCalls;
    public final Class<E> clazz;
    public final Class<? extends Throwable> expectedException;
    public MonitoredArrayConstructor(Class<E> clazz){
        this.clazz=clazz;
        this.expectedException=null;
    }
    private MonitoredArrayConstructor(Class<E> clazz,Class<? extends Throwable> expectedException){
        this.clazz=clazz;
        this.expectedException=expectedException;
    }
    @SuppressWarnings("unchecked")
    @Override
    public E[] apply(int value){
        ++numCalls;
        return (E[])Array.newInstance(clazz,value);
    }
    public static class Throwing<E>extends MonitoredArrayConstructor<E>{
        public Throwing(Class<E> clazz){
            super(clazz,IndexOutOfBoundsException.class);
        }
        @Override
        public E[] apply(int value){
            ++numCalls;
            throw new IndexOutOfBoundsException();
        }
    }
    public static class Modding<E>extends MonitoredArrayConstructor<E>{
        public final OmniCollection modThis;
        public Modding(Class<E> clazz,OmniCollection modThis){
            super(clazz,ConcurrentModificationException.class);
            this.modThis=modThis;
        }
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public E[] apply(int value){
            ++numCalls;
            if(modThis instanceof OmniCollection.OfRef){
                ((OmniCollection.OfRef)modThis).add(TypeConversionUtil.convertToObject(0));
            }else{
                ((OmniCollection.OfPrimitive)modThis).add(false);
            }
            return (E[])Array.newInstance(clazz,value);
        }
    }
    public static class ModdingAndThrowing<E>extends Modding<E>{
        public ModdingAndThrowing(Class<E> clazz,OmniCollection modThis){
            super(clazz,modThis);
        }
        @SuppressWarnings({"unchecked","rawtypes"})
        @Override
        public E[] apply(int value){
            ++numCalls;
            if(modThis instanceof OmniCollection.OfRef){
                ((OmniCollection.OfRef)modThis).add(TypeConversionUtil.convertToObject(0));
            }else{
                ((OmniCollection.OfPrimitive)modThis).add(false);
            }
            throw new IndexOutOfBoundsException();
        }
    }
}
