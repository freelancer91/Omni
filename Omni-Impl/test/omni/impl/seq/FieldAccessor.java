package omni.impl.seq;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import omni.api.OmniList;
final class FieldAccessor{
    private FieldAccessor(){
        super();
    }
    private static final Field MODIFIERS_FIELD;
    static{
        try{
            MODIFIERS_FIELD=Field.class.getDeclaredField("modifiers");
            MODIFIERS_FIELD.setAccessible(true);
        }catch(NoSuchFieldException | SecurityException e){
            throw new ExceptionInInitializerError(e);
        }
    }
    static Field prepareFieldForObj(Object obj,String fieldName){
        return prepareFieldForClass(obj.getClass(),fieldName);
    }

    static Field prepareFieldForClassName(String className,String fieldName){
        try{
            return prepareFieldForClass(Class.forName(className),fieldName);
        }catch(ClassNotFoundException e){
            throw new ExceptionInInitializerError(e);
        }
    }

    static Object getValue(Field field,Object obj){
        try{
            return field.get(obj);
        }catch(IllegalArgumentException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }
    static int getIntValue(Field field,Object obj){
        try{
            return field.getInt(obj);
        }catch(IllegalArgumentException | IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    static Field prepareFieldForClass(Class<?> clazz,String fieldName){
        try{
            Field field=clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            MODIFIERS_FIELD.setInt(field,field.getModifiers() & ~Modifier.FINAL);
            return field;
        }catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e){
            throw new ExceptionInInitializerError(e);
        }
    }
    static interface RefArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.RefArrSeq<?>)obj).size;
        }
        public static Object[] arr(Object obj)
        {
            return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
        }
        interface UncheckedList extends RefArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.UncheckedList<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.UncheckedList<?>)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.RefArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.UncheckedList<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.UncheckedList<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.RefArrSeq.CheckedList<?>)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.CheckedList<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.CheckedList<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.CheckedList<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.CheckedList<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends RefArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.UncheckedStack<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.UncheckedStack<?>)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.RefArrSeq.CheckedStack<?>)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.RefArrSeq.CheckedStack<?> parent(Object obj){
                    return (omni.impl.seq.RefArrSeq.CheckedStack<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.RefArrSeq.CheckedList<?> root(Object obj){
                return (omni.impl.seq.RefArrSeq.CheckedList<?>)getValue(rootField,obj);
            }
            static OmniList.OfRef<?> parent(Object obj){
                return (OmniList.OfRef<?>)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList$Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfRef<?> parent(Object obj){
                    return (OmniList.OfRef<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfRef<?> parent(Object obj){
                    return (OmniList.OfRef<?>)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.RefArrSeq.UncheckedList<?> root(Object obj){
                return (omni.impl.seq.RefArrSeq.UncheckedList<?>)getValue(rootField,obj);
            }
            static OmniList.OfRef<?> parent(Object obj){
                return (OmniList.OfRef<?>)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq$UncheckedSubList$Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfRef<?> parent(Object obj){
                    return (OmniList.OfRef<?>)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.RefArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfRef<?> parent(Object obj){
                    return (OmniList.OfRef<?>)getValue(parentField,obj);
                }
            }
        }
    }
    static interface BooleanArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.BooleanArrSeq)obj).size;
        }
        public static boolean[] arr(Object obj){
            return ((omni.impl.seq.BooleanArrSeq)obj).arr;
        }
        interface UncheckedList extends BooleanArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.BooleanArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends BooleanArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedStack$Itr","cursor");
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedStack$Itr","parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.BooleanArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$CheckedStack$Itr","modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.BooleanArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.BooleanArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfBoolean parent(Object obj){
                return (OmniList.OfBoolean)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$CheckedSubList$Itr","parent");
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$CheckedSubList$Itr","cursor");
                static final Field modCountField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$CheckedSubList$Itr","modCount");
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$CheckedSubList$Itr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfBoolean parent(Object obj){
                    return (OmniList.OfBoolean)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfBoolean parent(Object obj){
                    return (OmniList.OfBoolean)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedSubList",
                    "root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.BooleanArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.BooleanArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfBoolean parent(Object obj){
                return (OmniList.OfBoolean)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedSubList$Itr","parent");
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedSubList$Itr","cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfBoolean parent(Object obj){
                    return (OmniList.OfBoolean)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfBoolean parent(Object obj){
                    return (OmniList.OfBoolean)getValue(parentField,obj);
                }
            }
        }
    }
    static interface ByteArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.ByteArrSeq)obj).size;
        }
        public static byte[] arr(Object obj){
            return ((omni.impl.seq.ByteArrSeq)obj).arr;
        }
        interface UncheckedList extends ByteArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.ByteArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.ByteArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends ByteArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.ByteArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ByteArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.ByteArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.ByteArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.ByteArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfByte parent(Object obj){
                return (OmniList.OfByte)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList$Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfByte parent(Object obj){
                    return (OmniList.OfByte)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfByte parent(Object obj){
                    return (OmniList.OfByte)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.ByteArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.ByteArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfByte parent(Object obj){
                return (OmniList.OfByte)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq$UncheckedSubList$Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfByte parent(Object obj){
                    return (OmniList.OfByte)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.ByteArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfByte parent(Object obj){
                    return (OmniList.OfByte)getValue(parentField,obj);
                }
            }
        }
    }
    static interface CharArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.CharArrSeq)obj).size;
        }
        public static char[] arr(Object obj){
            return ((omni.impl.seq.CharArrSeq)obj).arr;
        }
        interface UncheckedList extends CharArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.CharArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.CharArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends CharArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.CharArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.CharArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.CharArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.CharArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.CharArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfChar parent(Object obj){
                return (OmniList.OfChar)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList$Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfChar parent(Object obj){
                    return (OmniList.OfChar)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfChar parent(Object obj){
                    return (OmniList.OfChar)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.CharArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.CharArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfChar parent(Object obj){
                return (OmniList.OfChar)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq$UncheckedSubList$Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfChar parent(Object obj){
                    return (OmniList.OfChar)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.CharArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfChar parent(Object obj){
                    return (OmniList.OfChar)getValue(parentField,obj);
                }
            }
        }
    }
    static interface ShortArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.ShortArrSeq)obj).size;
        }
        public static short[] arr(Object obj){
            return ((omni.impl.seq.ShortArrSeq)obj).arr;
        }
        interface UncheckedList extends ShortArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.ShortArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends ShortArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.ShortArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.ShortArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.ShortArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.ShortArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.ShortArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfShort parent(Object obj){
                return (OmniList.OfShort)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq$CheckedSubList$Itr","modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfShort parent(Object obj){
                    return (OmniList.OfShort)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfShort parent(Object obj){
                    return (OmniList.OfShort)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.ShortArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.ShortArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfShort parent(Object obj){
                return (OmniList.OfShort)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq$UncheckedSubList$Itr","parent");
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq$UncheckedSubList$Itr","cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfShort parent(Object obj){
                    return (OmniList.OfShort)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfShort parent(Object obj){
                    return (OmniList.OfShort)getValue(parentField,obj);
                }
            }
        }
    }
    static interface IntArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.IntArrSeq)obj).size;
        }
        public static int[] arr(Object obj){
            return ((omni.impl.seq.IntArrSeq)obj).arr;
        }
        interface UncheckedList extends IntArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.IntArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.IntArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends IntArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.IntArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.IntArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.IntArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.IntArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.IntArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfInt parent(Object obj){
                return (OmniList.OfInt)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList$Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfInt parent(Object obj){
                    return (OmniList.OfInt)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfInt parent(Object obj){
                    return (OmniList.OfInt)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.IntArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.IntArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfInt parent(Object obj){
                return (OmniList.OfInt)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq$UncheckedSubList$Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfInt parent(Object obj){
                    return (OmniList.OfInt)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.IntArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfInt parent(Object obj){
                    return (OmniList.OfInt)getValue(parentField,obj);
                }
            }
        }
    }
    static interface LongArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.LongArrSeq)obj).size;
        }
        public static long[] arr(Object obj){
            return ((omni.impl.seq.LongArrSeq)obj).arr;
        }
        interface UncheckedList extends LongArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.LongArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.LongArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends LongArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.LongArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.LongArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.LongArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.LongArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.LongArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfLong parent(Object obj){
                return (OmniList.OfLong)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList$Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfLong parent(Object obj){
                    return (OmniList.OfLong)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfLong parent(Object obj){
                    return (OmniList.OfLong)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.LongArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.LongArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfLong parent(Object obj){
                return (OmniList.OfLong)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq$UncheckedSubList$Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfLong parent(Object obj){
                    return (OmniList.OfLong)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.LongArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfLong parent(Object obj){
                    return (OmniList.OfLong)getValue(parentField,obj);
                }
            }
        }
    }
    static interface FloatArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.FloatArrSeq)obj).size;
        }
        public static float[] arr(Object obj){
            return ((omni.impl.seq.FloatArrSeq)obj).arr;
        }
        interface UncheckedList extends FloatArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.FloatArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends FloatArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.FloatArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.FloatArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.FloatArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.FloatArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.FloatArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfFloat parent(Object obj){
                return (OmniList.OfFloat)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq$CheckedSubList$Itr","modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$CheckedSubList$Itr",
                        "lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfFloat parent(Object obj){
                    return (OmniList.OfFloat)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfFloat parent(Object obj){
                    return (OmniList.OfFloat)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.FloatArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.FloatArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfFloat parent(Object obj){
                return (OmniList.OfFloat)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq$UncheckedSubList$Itr","parent");
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq$UncheckedSubList$Itr","cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfFloat parent(Object obj){
                    return (OmniList.OfFloat)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfFloat parent(Object obj){
                    return (OmniList.OfFloat)getValue(parentField,obj);
                }
            }
        }
    }
    static interface DoubleArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.DoubleArrSeq)obj).size;
        }
        public static double[] arr(Object obj){
            return ((omni.impl.seq.DoubleArrSeq)obj).arr;
        }
        interface UncheckedList extends DoubleArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedList$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.UncheckedList)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$UncheckedList$ListItr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.UncheckedList parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.UncheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
            }
        }
        interface CheckedList extends UncheckedList{
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.DoubleArrSeq.CheckedList)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedList$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedList$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedList$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedList$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.CheckedList parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.CheckedList)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedStack extends DoubleArrSeq{
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedStack$Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            public static int modCount(Object obj){
                return ((omni.impl.seq.DoubleArrSeq.CheckedStack)obj).modCount;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedStack$Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedStack$Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedStack$Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedStack$Itr",
                        "modCount");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.DoubleArrSeq.CheckedStack parent(Object obj){
                    return (omni.impl.seq.DoubleArrSeq.CheckedStack)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface CheckedSubList{
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList",
                    "size");
            static int modCount(Object obj){
                return getIntValue(modCountField,obj);
            }
            static omni.impl.seq.DoubleArrSeq.CheckedList root(Object obj){
                return (omni.impl.seq.DoubleArrSeq.CheckedList)getValue(rootField,obj);
            }
            static OmniList.OfDouble parent(Object obj){
                return (OmniList.OfDouble)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList$Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$CheckedSubList$Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$CheckedSubList$Itr","modCount");
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$CheckedSubList$Itr","lastRet");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfDouble parent(Object obj){
                    return (OmniList.OfDouble)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
            interface ListItr extends Itr{
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfDouble parent(Object obj){
                    return (OmniList.OfDouble)getValue(parentField,obj);
                }
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int modCount(Object obj){
                    return getIntValue(modCountField,obj);
                }
            }
        }
        interface UncheckedSubList{
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq$UncheckedSubList",
                    "size");
            static omni.impl.seq.DoubleArrSeq.UncheckedList root(Object obj){
                return (omni.impl.seq.DoubleArrSeq.UncheckedList)getValue(rootField,obj);
            }
            static OmniList.OfDouble parent(Object obj){
                return (OmniList.OfDouble)getValue(parentField,obj);
            }
            static int rootOffset(Object obj){
                return getIntValue(rootOffsetField,obj);
            }
            static int size(Object obj){
                return getIntValue(sizeField,obj);
            }
            interface Itr{
                static final Field parentField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$UncheckedSubList$Itr","parent");
                static final Field cursorField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$UncheckedSubList$Itr","cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfDouble parent(Object obj){
                    return (OmniList.OfDouble)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq$UncheckedSubList$ListItr","lastRet");
                static int lastRet(Object obj){
                    return getIntValue(lastRetField,obj);
                }
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfDouble parent(Object obj){
                    return (OmniList.OfDouble)getValue(parentField,obj);
                }
            }
        }
    }
}
