package omni.impl.seq;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Externalizable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import omni.api.OmniList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
final class FieldAndMethodAccessor
{
  private FieldAndMethodAccessor(){
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
    static Method prepareMethodForObj(Object obj,String methodName,Class<?>...params){
      return prepareMethodForClass(obj.getClass(),methodName,params);
    }
    static Field prepareFieldForClassName(String className,String fieldName){
        try{
            return prepareFieldForClass(Class.forName(className),fieldName);
        }catch(ClassNotFoundException e){
            throw new ExceptionInInitializerError(e);
        }
    }
    static Method prepareMethodForClassName(String className,String methodName,Class<?>...params){
        try{
          return prepareMethodForClass(Class.forName(className),methodName,params);
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
    static Method prepareMethodForClass(Class<?> clazz,String methodName,Class<?>...params){
        try
        {
          Method method=clazz.getDeclaredMethod(methodName,params);
          method.setAccessible(true);
          return method;
        }
        catch(NoSuchMethodException e)
        {
          throw new ExceptionInInitializerError(e);
        }
    }
    private static final char DOLLARSIGN=(char)36;
    static interface RefArrSeq{
        public static int size(Object obj){
            return ((omni.impl.seq.RefArrSeq<?>)obj).size;
        }
        public static Object[] arr(Object obj)
        {
            return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
        }
        interface UncheckedList extends RefArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).size;
            }
            public static Object[] arr(Object obj){
                return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static boolean[] arr(Object obj)
        {
            return ((omni.impl.seq.BooleanArrSeq)obj).arr;
        }
        interface UncheckedList extends BooleanArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).size;
            }
            public static boolean[] arr(Object obj){
                return ((omni.impl.seq.BooleanArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static omni.impl.seq.BooleanArrSeq.UncheckedStack parent(Object obj){
                    return (omni.impl.seq.BooleanArrSeq.UncheckedStack)getValue(parentField,obj);
                }
            }
        }
        interface CheckedStack extends UncheckedStack{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "modCount");
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "lastRet");
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfBoolean parent(Object obj){
                    return (OmniList.OfBoolean)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static byte[] arr(Object obj)
        {
            return ((omni.impl.seq.ByteArrSeq)obj).arr;
        }
        interface UncheckedList extends ByteArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).size;
            }
            public static byte[] arr(Object obj){
                return ((omni.impl.seq.ByteArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static char[] arr(Object obj)
        {
            return ((omni.impl.seq.CharArrSeq)obj).arr;
        }
        interface UncheckedList extends CharArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).size;
            }
            public static char[] arr(Object obj){
                return ((omni.impl.seq.CharArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static short[] arr(Object obj)
        {
            return ((omni.impl.seq.ShortArrSeq)obj).arr;
        }
        interface UncheckedList extends ShortArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).size;
            }
            public static short[] arr(Object obj){
                return ((omni.impl.seq.ShortArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfShort parent(Object obj){
                    return (OmniList.OfShort)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static int[] arr(Object obj)
        {
            return ((omni.impl.seq.IntArrSeq)obj).arr;
        }
        interface UncheckedList extends IntArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).size;
            }
            public static int[] arr(Object obj){
                return ((omni.impl.seq.IntArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static long[] arr(Object obj)
        {
            return ((omni.impl.seq.LongArrSeq)obj).arr;
        }
        interface UncheckedList extends LongArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).size;
            }
            public static long[] arr(Object obj){
                return ((omni.impl.seq.LongArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static float[] arr(Object obj)
        {
            return ((omni.impl.seq.FloatArrSeq)obj).arr;
        }
        interface UncheckedList extends FloatArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).size;
            }
            public static float[] arr(Object obj){
                return ((omni.impl.seq.FloatArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfFloat parent(Object obj){
                    return (OmniList.OfFloat)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
        public static double[] arr(Object obj)
        {
            return ((omni.impl.seq.DoubleArrSeq)obj).arr;
        }
        interface UncheckedList extends DoubleArrSeq{
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr",
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
                        "omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
            public static int size(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).size;
            }
            public static double[] arr(Object obj){
                return ((omni.impl.seq.DoubleArrSeq)obj).arr;
            }
            interface Itr{
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr",
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
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              ((Externalizable)obj).writeExternal(oos);
            }
            public static Object readObject(Object obj,ObjectInputStream oos) throws IOException,ClassNotFoundException
            {
              ((Externalizable)obj).readExternal(oos);
              return obj;
            }
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
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
                        "lastRet");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr",
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "modCount");
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "modCount");
                static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr",
                        "lastRet");
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
            static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
            static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
            static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
            public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException
            {
              Object replacement=null;
              try
              {
                replacement=writeReplaceMethod.invoke(obj);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
              try
              {
                writeObjectMethod.invoke(replacement,oos);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                throw new Error(e);
              }
            }
            public static Object readObject(Object obj,ObjectInputStream ois) throws IOException,ClassNotFoundException
            {
              Object replacement=ois.readObject();
              try
              {
                return readResolveMethod.invoke(replacement);
              }
              catch(IllegalAccessException e)
              {
                throw new Error(e);
              }
              catch(InvocationTargetException e)
              {
                var cause=e.getCause();
                if(cause instanceof RuntimeException)
                {
                  throw (RuntimeException)cause;
                }
                if(cause instanceof IOException)
                {
                  throw (IOException)cause;
                }
                if(cause instanceof ClassNotFoundException)
                {
                  throw (ClassNotFoundException)cause;
                }
                throw new Error(e);
              }
            }
            static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
            static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "parent");
            static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList",
                    "rootOffset");
            static final Field sizeField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList",
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
                static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "parent");
                static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr",
                        "cursor");
                static int cursor(Object obj){
                    return getIntValue(cursorField,obj);
                }
                static OmniList.OfDouble parent(Object obj){
                    return (OmniList.OfDouble)getValue(parentField,obj);
                }
            }
            interface ListItr extends Itr{
                static final Field lastRetField=prepareFieldForClassName(
                        "omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
