package omni.impl.seq;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ConcurrentModificationException;
import java.util.Set;
import omni.api.OmniCollection;
import omni.impl.DataType;
import omni.impl.IOType;
public interface SeqMonitor{
    DataType getDataType();
    OmniCollection getSequence();
    private Object invokeMethod(OmniCollection seq,Method method,Object...args){
        try{
            return method.invoke(seq,args);
        }catch(IllegalAccessException | IllegalArgumentException e){
            throw new Error(e);
        }catch(InvocationTargetException e){
            Throwable t=e.getCause();
            if(t instanceof RuntimeException){
                throw(RuntimeException)t;
            }
            throw new Error(e);
        }
    }
    private Method lookUpMethod(OmniCollection seq,String methodName,Class<?>...argTypes){
        try{
            return seq.getClass().getMethod(methodName,argTypes);
        }catch(NoSuchMethodException | SecurityException e){
            throw new Error(e);
        }
    }
    default boolean add(Object valToAdd,Class<?> valClass){
        final OmniCollection seq;
        return (boolean)invokeMethod(seq=getSequence(),lookUpMethod(seq,"add",valClass),valToAdd);
    }
    default boolean add(int valToAdd){
        final IOType preferredIOType;
        return add((preferredIOType=getDataType().preferredIOType).convertToInputVal(valToAdd),preferredIOType.clazz);
    }
    default boolean add(int valToAdd,IOType ioType){
        return add(ioType.convertToInputVal(valToAdd),ioType.clazz);
    }
    default void add(int index,Object valToAdd,Class<?> valClass){
        final OmniCollection seq;
        invokeMethod(seq=getSequence(),lookUpMethod(seq,"add",int.class,valClass),index,valToAdd);
    }

    default void add(int index,int valToAdd){
        final IOType preferredIOType;
        add(index,(preferredIOType=getDataType().preferredIOType).convertToInputVal(valToAdd),preferredIOType.clazz);
    }
    default void add(int index,int valToAdd,IOType ioType){
        add(index,ioType.convertToInputVal(valToAdd),ioType.clazz);
    }
    default void push(Object valToAdd,Class<?> valClass){
        final OmniCollection seq;
        invokeMethod(seq=getSequence(),lookUpMethod(seq,"push",valClass),valToAdd);
    }

    default void push(int valToAdd){
        final IOType preferredIOType;
        push((preferredIOType=getDataType().preferredIOType).convertToInputVal(valToAdd),preferredIOType.clazz);
    }
    default void push(int valToAdd,IOType ioType){
        push(ioType.convertToInputVal(valToAdd),ioType.clazz);
    }
    default boolean removeVal(Object valToRemove,Class<?> valClass){
        final OmniCollection seq;
        return (boolean)invokeMethod(seq=getSequence(),
                lookUpMethod(seq,valClass == Object.class?"remove":"removeVal",valClass),valToRemove);
    }
    default boolean contains(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (boolean)invokeMethod(seq=getSequence(),lookUpMethod(seq,"contains",valClass),valToSearch);
    }
    default boolean removeFirstOccurrence(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (boolean)invokeMethod(seq=getSequence(),lookUpMethod(seq,"removeFirstOccurrence",valClass),valToSearch);
    }
    default boolean removeLastOccurrence(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (boolean)invokeMethod(seq=getSequence(),lookUpMethod(seq,"removeLastOccurrence",valClass),valToSearch);
    }
    default int search(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (int)invokeMethod(seq=getSequence(),lookUpMethod(seq,"search",valClass),valToSearch);
    }
    default int indexOf(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (int)invokeMethod(seq=getSequence(),lookUpMethod(seq,"indexOf",valClass),valToSearch);
    }
    default int lastIndexOf(Object valToSearch,Class<?> valClass){
        final OmniCollection seq;
        return (int)invokeMethod(seq=getSequence(),lookUpMethod(seq,"lastIndexOf",valClass),valToSearch);
    }
    default void clear(){
        getSequence().clear();
    }
    default int size(){
        return getSequence().size();
    }
    default boolean isEmpty(){
        return getSequence().isEmpty();
    }
    // TODO forEach
    // TODO removeIf
    // TODO toArray methods
    // TODO get methods
    // TODO set methods
    // TODO put methods
    // TODO peek methods
    // TODO poll methods
    // TODO pop methods
    // TODO removeAt methods
    // TODO other methods
    static enum ItrType{
        Itr(ItrState.Unmodified,ItrState.PostRemove,ItrState.PostNextHasNext,ItrState.PostNextNoNext),
        ListItr(ItrState.Unmodified,ItrState.PostRemove,ItrState.PostNextHasNext,ItrState.PostNextNoNext,
                ItrState.PostPreviousHasPrevious,ItrState.PostPreviousNoPrevious);
        final Set<ItrState> itrStates;
        ItrType(ItrState...itrStates){
            this.itrStates=Set.of(itrStates);
        }
    }
    static enum NestedType{
        LIST(new QueryOperation[]{QueryOperation.contains,QueryOperation.removeVal,QueryOperation.indexOf,
                QueryOperation.lastIndexOf},new ItrType[]{ItrType.Itr,ItrType.ListItr},new PreMod[]{PreMod.NoMod},
                new PreMod[]{PreMod.NoMod,PreMod.ModSeq}),
        STACK(new QueryOperation[]{QueryOperation.contains,QueryOperation.removeVal,QueryOperation.search},
                new ItrType[]{ItrType.Itr},new PreMod[]{PreMod.NoMod},new PreMod[]{PreMod.NoMod,PreMod.ModSeq}),
        SUBLIST(new QueryOperation[]{QueryOperation.contains,QueryOperation.removeVal,QueryOperation.indexOf,
                QueryOperation.lastIndexOf},new ItrType[]{ItrType.Itr,ItrType.ListItr},
                new PreMod[]{PreMod.NoMod,PreMod.ModParent,PreMod.ModRoot},
                new PreMod[]{PreMod.NoMod,PreMod.ModSeq,PreMod.ModParent,PreMod.ModRoot}),
        QUEUE(new QueryOperation[]{QueryOperation.contains,QueryOperation.removeVal},new ItrType[]{ItrType.Itr},
                new PreMod[]{PreMod.NoMod},new PreMod[]{PreMod.NoMod,PreMod.ModSeq});
        final Set<QueryOperation> queryOps;
        final Set<ItrType> itrTypes;
        final Set<PreMod> preMods;
        final Set<PreMod> itrPreMods;
        NestedType(QueryOperation[] queryOps,ItrType[] itrTypes,PreMod[] preMods,PreMod[] itrPreMods){
            this.queryOps=Set.of(queryOps);
            this.itrTypes=Set.of(itrTypes);
            this.preMods=Set.of(preMods);
            this.itrPreMods=Set.of(itrPreMods);
        }
    }
    static enum QueryReturnType{
        BooleanType(false,boolean.class),IntType(-1,int.class);
        final Object negativeReturn;
        final Class<?> clazz;
        QueryReturnType(Object negativeReturn,Class<?> clazz){
            this.negativeReturn=negativeReturn;
            this.clazz=clazz;
        }
    }
    static enum QueryOperation{
        contains(QueryReturnType.BooleanType),search(QueryReturnType.IntType),indexOf(QueryReturnType.IntType),
        lastIndexOf(QueryReturnType.IntType),removeVal(QueryReturnType.BooleanType),
        removeFirstOccurrence(QueryReturnType.BooleanType),removeLastOccurrence(QueryReturnType.BooleanType);
        final QueryReturnType returnType;
        QueryOperation(QueryReturnType returnType){
            this.returnType=returnType;
        }
    }
    NestedType getNestedType();
    static enum ItrState{
        Unmodified,PostAdd,PostRemove,PostNextHasNext,PostPreviousHasPrevious,PostNextNoNext,PostPreviousNoPrevious;
    }
    static enum StructType{
        ARRSEQ(NestedType.LIST,NestedType.STACK,NestedType.SUBLIST),SNGLLNKSEQ(NestedType.STACK,NestedType.QUEUE);
        final Set<NestedType> nestedTypes;
        StructType(NestedType...nestedTypes){
            this.nestedTypes=Set.of(nestedTypes);
        }
    }
    StructType getStructType();
    static enum SeqLoc{
        BEGINNING(null,true),MIDDLE(null,false),END(null,false),IOBLO(IndexOutOfBoundsException.class,true),
        IOBHI(IndexOutOfBoundsException.class,true);
        final Class<? extends Throwable> expectedException;
        final boolean validForEmpty;
        SeqLoc(Class<? extends Throwable> expectedException,boolean validForEmpty){
            this.expectedException=expectedException;
            this.validForEmpty=validForEmpty;
        }
    }
    static enum PreMod{
        ModSeq(ConcurrentModificationException.class),ModParent(ConcurrentModificationException.class),
        ModRoot(ConcurrentModificationException.class),NoMod(null);
        final Class<? extends Throwable> expectedException;
        PreMod(Class<? extends Throwable> expectedException){
            this.expectedException=expectedException;
        }
    }
    interface ItrMonitor{
        SeqMonitor getParent();
        ItrType getItrType();
        default DataType getDataType(){
            return getParent().getDataType();
        }
    }
}
