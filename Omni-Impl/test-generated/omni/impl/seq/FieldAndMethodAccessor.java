package omni.impl.seq;
import java.io.ObjectOutputStream;
import java.io.Externalizable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;
import omni.api.OmniList;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
final class FieldAndMethodAccessor{
  private FieldAndMethodAccessor(){
    super();
  }
  private static final VarHandle MODIFIER_HANDLE;
  static
  {
    try
    {
      var lookup = MethodHandles.privateLookupIn(Field.class, MethodHandles.lookup());
      MODIFIER_HANDLE = lookup.findVarHandle(Field.class, "modifiers", int.class);
    }
    catch(IllegalAccessException|NoSuchFieldException e)
    {
      throw new ExceptionInInitializerError(e);
    }
  }
  private static void makeNonFinal(Field field){
    int mods=field.getModifiers();
    if(Modifier.isFinal(mods))
    {
      MODIFIER_HANDLE.set(field,mods&~Modifier.FINAL);
    }
  }
  /*
  private static final Field MODIFIERS_FIELD;
  static{
    try{
      MODIFIERS_FIELD=Field.class.getDeclaredField("modifiers");
      MODIFIERS_FIELD.setAccessible(true);
    }catch(NoSuchFieldException | SecurityException e){
        throw new ExceptionInInitializerError(e);
    }
  }
  */
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
  static void setIntValue(Field field,Object obj,int val){
    try{
      field.setInt(obj,val);
    }catch(IllegalArgumentException | IllegalAccessException e){
      throw new RuntimeException(e);
    }
  }
  static int incrementIntValue(Field field,Object obj){
    try{
      int v;
      field.setInt(obj,v=field.getInt(obj)+1);
      return v;
    }catch(IllegalArgumentException | IllegalAccessException e){
      throw new RuntimeException(e);
    }
  }
  static Field prepareFieldForClass(Class<?> clazz,String fieldName){
    try{
      Field field=clazz.getDeclaredField(fieldName);
      makeNonFinal(field);
      field.setAccessible(true);
      return field;
    }catch(NoSuchFieldException | SecurityException | IllegalArgumentException e){
      for(var field:clazz.getDeclaredFields()){
        System.err.println(field);
      }
      throw new ExceptionInInitializerError(e);
    }
  } 
  static Method prepareMethodForClass(Class<?> clazz,String methodName,Class<?>...params){
    try{
      Method method=clazz.getDeclaredMethod(methodName,params);
      method.setAccessible(true);
      return method;
    }catch(NoSuchMethodException e) {
      throw new ExceptionInInitializerError(e);
    }
  }
  private static void writeObjectHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod) throws IOException{
    Object replacement=null;
    try{
      replacement=writeReplaceMethod.invoke(obj);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
    ((Externalizable)replacement).writeExternal(oos);
  }
  private static void writeObjectHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod,Method writeObjectMethod) throws IOException{
    Object replacement=null;
    try{
      replacement=writeReplaceMethod.invoke(obj);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
    try{
      writeObjectMethod.invoke(replacement,oos);
    }catch(IllegalAccessException e){
      throw new Error(e);
    }catch(InvocationTargetException e){
      var cause=e.getCause();
      if(cause instanceof RuntimeException){
        throw (RuntimeException)cause;
      }else if(cause instanceof IOException){
        throw (IOException)cause;
      }
      throw new Error(e);
    }
  }
  private static final char DOLLARSIGN=(char)36;
  static interface RefDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.RefDblLnkSeq<?>)obj).size;
    }
    public static omni.impl.seq.RefDblLnkSeq.Node<?> head(Object obj){
      return ((omni.impl.seq.RefDblLnkSeq<?>)obj).head;
    }
    public static omni.impl.seq.RefDblLnkSeq.Node<?> tail(Object obj){
      return ((omni.impl.seq.RefDblLnkSeq<?>)obj).tail;
    }
    interface UncheckedList extends RefDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)obj).size;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)obj).head;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.RefDblLnkSeq.UncheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.RefDblLnkSeq.UncheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.RefDblLnkSeq.UncheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.CheckedList<?>)obj).size;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.CheckedList<?>)obj).head;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq.CheckedList<?>)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.RefDblLnkSeq.CheckedList<?>)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.RefDblLnkSeq.CheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.CheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.RefDblLnkSeq.CheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.CheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.RefDblLnkSeq.CheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.CheckedList<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends RefDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).head;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).tail;
      }
      public static omni.impl.seq.RefDblLnkSeq.UncheckedList<?> root(Object obj){
        return (omni.impl.seq.RefDblLnkSeq.UncheckedList<?>)getValue(rootField,obj);
      }
      public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
        return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends RefDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).head;
      }
      public static omni.impl.seq.RefDblLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefDblLnkSeq<?>)obj).tail;
      }
      public static omni.impl.seq.RefDblLnkSeq.CheckedList<?> root(Object obj){
        return (omni.impl.seq.RefDblLnkSeq.CheckedList<?>)getValue(rootField,obj);
      }
      public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
        return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.RefDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.RefDblLnkSeq<?> parent(Object obj){
          return (omni.impl.seq.RefDblLnkSeq<?>)getValue(parentField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(currField,obj);
        }
        public static omni.impl.seq.RefDblLnkSeq.Node<?> lastRet(Object obj){
          return (omni.impl.seq.RefDblLnkSeq.Node<?>)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface RefSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).size;
    }
    public static omni.impl.seq.RefSnglLnkSeq.Node<?> head(Object obj){
      return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).head;
    }
    interface UncheckedStack extends RefSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.RefSnglLnkSeq.UncheckedStack<?> parent(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.UncheckedStack<?>)getValue(parentField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> prev(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(prevField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(currField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> next(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends RefSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).head;
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq.UncheckedQueue<?>)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.RefSnglLnkSeq.UncheckedQueue<?> parent(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.UncheckedQueue<?>)getValue(parentField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> prev(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(prevField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(currField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> next(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq.CheckedStack<?>)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.CheckedStack<?> parent(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.CheckedStack<?>)getValue(parentField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> prev(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(prevField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(currField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> next(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> tail(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq.CheckedQueue<?>)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).size;
      }
      public static omni.impl.seq.RefSnglLnkSeq.Node<?> head(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq<?>)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.RefSnglLnkSeq.CheckedQueue<?>)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.CheckedQueue<?> parent(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.CheckedQueue<?>)getValue(parentField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> prev(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(prevField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> curr(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(currField,obj);
        }
        static omni.impl.seq.RefSnglLnkSeq.Node<?> next(Object obj){
          return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.RefSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.RefSnglLnkSeq.Node<?> prev(Object obj){
        return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(prevField,obj);
      }
      static omni.impl.seq.RefSnglLnkSeq.Node<?> curr(Object obj){
        return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(currField,obj);
      }
      static omni.impl.seq.RefSnglLnkSeq.Node<?> next(Object obj){
        return (omni.impl.seq.RefSnglLnkSeq.Node<?>)getValue(nextField,obj);
      }
    }
  }
  static interface RefArrSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.RefArrSeq<?>)obj).size;
    }
    public static Object[] arr(Object obj){
      return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
    }
    interface UncheckedList extends RefArrSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefArrSeq<?>)obj).size;
      }
      public static Object[] arr(Object obj){
        return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.RefArrSeq.UncheckedList<?> parent(Object obj){
          return (omni.impl.seq.RefArrSeq.UncheckedList<?>)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.RefArrSeq<?>)obj).size;
      }
      public static Object[] arr(Object obj){
        return ((omni.impl.seq.RefArrSeq<?>)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.RefArrSeq.UncheckedStack<?> parent(Object obj){
          return (omni.impl.seq.RefArrSeq.UncheckedStack<?>)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfRef<?> parent(Object obj){
          return (OmniList.OfRef<?>)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.RefArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface BooleanDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.BooleanDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.BooleanDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.BooleanDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.BooleanDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.BooleanDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends BooleanDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.BooleanDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.BooleanDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.BooleanDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.BooleanDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.BooleanDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.BooleanDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.BooleanDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends BooleanDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.BooleanDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
        return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends BooleanDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.BooleanDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.BooleanDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
        return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.BooleanDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.BooleanDblLnkSeq parent(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.BooleanDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.BooleanDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface BooleanSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.BooleanSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.BooleanSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.BooleanSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends BooleanSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.BooleanSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends BooleanSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.BooleanSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.BooleanSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.BooleanSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.BooleanSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.BooleanSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.BooleanSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.BooleanSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.BooleanSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.BooleanSnglLnkSeq.Node)getValue(nextField,obj);
      }
    }
  }
  static interface PackedBooleanArrDeq{
    public static int head(Object obj){
      return ((omni.impl.seq.PackedBooleanArrDeq)obj).head;
    }
    public static int tail(Object obj){
      return ((omni.impl.seq.PackedBooleanArrDeq)obj).tail;
    }
    public static long[] words(Object obj){
      return ((omni.impl.seq.PackedBooleanArrDeq)obj).words;
    }
    public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
    static interface AbstractDeqItr{
      static int cursor(Object obj){
          return ((omni.impl.seq.AbstractBooleanArrDeq.AbstractDeqItr)obj).cursor;
      }
    }
    static interface Checked extends PackedBooleanArrDeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int head(Object obj){
        return ((omni.impl.seq.PackedBooleanArrDeq)obj).head;
      }
      public static int tail(Object obj){
        return ((omni.impl.seq.PackedBooleanArrDeq)obj).tail;
      }
      public static long[] words(Object obj){
        return ((omni.impl.seq.PackedBooleanArrDeq)obj).words;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.PackedBooleanArrDeq.Checked)obj).modCount;
      }
      public interface AscendingItr extends AbstractDeqItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrDeq"+DOLLARSIGN+"Checked"+DOLLARSIGN+"AscendingItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrDeq"+DOLLARSIGN+"Checked"+DOLLARSIGN+"AscendingItr","modCount");
        static final Field rootField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrDeq"+DOLLARSIGN+"Checked"+DOLLARSIGN+"AscendingItr","root");
        static int cursor(Object obj){
          return ((omni.impl.seq.AbstractBooleanArrDeq.AbstractDeqItr)obj).cursor;
        }
        static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.PackedBooleanArrDeq.Checked root(Object obj){
          return (omni.impl.seq.PackedBooleanArrDeq.Checked)getValue(rootField,obj);
        }
      }
      public interface DescendingItr extends AscendingItr{
        static int cursor(Object obj){
          return ((omni.impl.seq.AbstractBooleanArrDeq.AbstractDeqItr)obj).cursor;
        }
        static omni.impl.seq.PackedBooleanArrDeq root(Object obj){
          return (omni.impl.seq.PackedBooleanArrDeq)getValue(rootField,obj);
        }
        static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    public interface AscendingItr  extends AbstractDeqItr{
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrDeq"+DOLLARSIGN+"AscendingItr","root");
      static int cursor(Object obj){
        return ((omni.impl.seq.AbstractBooleanArrDeq.AbstractDeqItr)obj).cursor;
      }
      static omni.impl.seq.PackedBooleanArrDeq root(Object obj){
        return (omni.impl.seq.PackedBooleanArrDeq)getValue(rootField,obj);
      }
    }
    public interface DescendingItr extends AscendingItr{
      static int cursor(Object obj){
        return ((omni.impl.seq.AbstractBooleanArrDeq.AbstractDeqItr)obj).cursor;
      }
      static omni.impl.seq.PackedBooleanArrDeq root(Object obj){
        return (omni.impl.seq.PackedBooleanArrDeq)getValue(rootField,obj);
      }
    }
  }
  static interface PackedBooleanArrSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.PackedBooleanArrSeq)obj).size;
    }
    public static long[] words(Object obj){
      return ((omni.impl.seq.PackedBooleanArrSeq)obj).words;
    }
    interface UncheckedList extends PackedBooleanArrSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).size;
      }
      public static long[] words(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).words;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.PackedBooleanArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.PackedBooleanArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.UncheckedList)getValue(parentField,obj);
        }
        static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).size;
      }
      public static long[] words(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).words;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq.CheckedList)obj).modCount;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.PackedBooleanArrSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.CheckedList)getValue(parentField,obj);
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
        static omni.impl.seq.PackedBooleanArrSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.CheckedList)getValue(parentField,obj);
        }
        static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedStack extends PackedBooleanArrSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).size;
      }
      public static long[] words(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).words;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.PackedBooleanArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).size;
      }
      public static long[] words(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq)obj).words;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.PackedBooleanArrSeq.CheckedStack)obj).modCount;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.PackedBooleanArrSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.PackedBooleanArrSeq.CheckedStack)getValue(parentField,obj);
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
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      static omni.impl.seq.PackedBooleanArrSeq.CheckedList root(Object obj){
        return (omni.impl.seq.PackedBooleanArrSeq.CheckedList)getValue(rootField,obj);
      }
      static OmniList.OfBoolean parent(Object obj){
        return (OmniList.OfBoolean)getValue(parentField,obj);
      }
      static int rootOffset(Object obj){
        return getIntValue(rootOffsetField,obj);
      }
      static int size(Object obj){
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
      static omni.impl.seq.PackedBooleanArrSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.PackedBooleanArrSeq.UncheckedList)getValue(rootField,obj);
      }
      static OmniList.OfBoolean parent(Object obj){
        return (OmniList.OfBoolean)getValue(parentField,obj);
      }
      static int rootOffset(Object obj){
        return getIntValue(rootOffsetField,obj);
      }
      static int size(Object obj){
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfBoolean parent(Object obj){
          return (OmniList.OfBoolean)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.PackedBooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface BooleanArrSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.BooleanArrSeq)obj).size;
    }
    public static boolean[] arr(Object obj){
      return ((omni.impl.seq.BooleanArrSeq)obj).arr;
    }
    interface UncheckedList extends BooleanArrSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanArrSeq)obj).size;
      }
      public static boolean[] arr(Object obj){
        return ((omni.impl.seq.BooleanArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.BooleanArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.BooleanArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.BooleanArrSeq)obj).size;
      }
      public static boolean[] arr(Object obj){
        return ((omni.impl.seq.BooleanArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.BooleanArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.BooleanArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfBoolean parent(Object obj){
          return (OmniList.OfBoolean)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.BooleanArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface ByteDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.ByteDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.ByteDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.ByteDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.ByteDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.ByteDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends ByteDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.ByteDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.ByteDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ByteDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.ByteDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.ByteDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.ByteDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.ByteDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends ByteDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.ByteDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.ByteDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
        return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends ByteDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.ByteDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.ByteDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.ByteDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
        return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ByteDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.ByteDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ByteDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ByteDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface ByteSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.ByteSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.ByteSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.ByteSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends ByteSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.ByteSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends ByteSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.ByteSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ByteSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.ByteSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ByteSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.ByteSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.ByteSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.ByteSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.ByteSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.ByteSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteArrSeq)obj).size;
      }
      public static byte[] arr(Object obj){
        return ((omni.impl.seq.ByteArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.ByteArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ByteArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ByteArrSeq)obj).size;
      }
      public static byte[] arr(Object obj){
        return ((omni.impl.seq.ByteArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.ByteArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.ByteArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfByte parent(Object obj){
          return (OmniList.OfByte)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ByteArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface CharDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.CharDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.CharDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.CharDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.CharDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.CharDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends CharDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.CharDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.CharDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.CharDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.CharDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.CharDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.CharDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.CharDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends CharDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.CharDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.CharDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
        return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends CharDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.CharDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.CharDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.CharDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
        return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.CharDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.CharDblLnkSeq parent(Object obj){
          return (omni.impl.seq.CharDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.CharDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.CharDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface CharSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.CharSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.CharSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.CharSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends CharSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.CharSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends CharSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.CharSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.CharSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.CharSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.CharSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.CharSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.CharSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.CharSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.CharSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.CharSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharArrSeq)obj).size;
      }
      public static char[] arr(Object obj){
        return ((omni.impl.seq.CharArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.CharArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.CharArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.CharArrSeq)obj).size;
      }
      public static char[] arr(Object obj){
        return ((omni.impl.seq.CharArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.CharArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.CharArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfChar parent(Object obj){
          return (OmniList.OfChar)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.CharArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface ShortDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.ShortDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.ShortDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.ShortDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.ShortDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.ShortDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends ShortDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.ShortDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.ShortDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ShortDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.ShortDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.ShortDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.ShortDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.ShortDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends ShortDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.ShortDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.ShortDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
        return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends ShortDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.ShortDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.ShortDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.ShortDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
        return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.ShortDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.ShortDblLnkSeq parent(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.ShortDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.ShortDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface ShortSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.ShortSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.ShortSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.ShortSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends ShortSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.ShortSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends ShortSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.ShortSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.ShortSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.ShortSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.ShortSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.ShortSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.ShortSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.ShortSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.ShortSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.ShortSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortArrSeq)obj).size;
      }
      public static short[] arr(Object obj){
        return ((omni.impl.seq.ShortArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.ShortArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.ShortArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.ShortArrSeq)obj).size;
      }
      public static short[] arr(Object obj){
        return ((omni.impl.seq.ShortArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.ShortArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.ShortArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfShort parent(Object obj){
          return (OmniList.OfShort)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.ShortArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface IntDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.IntDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.IntDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.IntDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.IntDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.IntDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends IntDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.IntDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.IntDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.IntDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.IntDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.IntDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.IntDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.IntDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends IntDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.IntDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.IntDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
        return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends IntDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.IntDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.IntDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.IntDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
        return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.IntDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.IntDblLnkSeq parent(Object obj){
          return (omni.impl.seq.IntDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.IntDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.IntDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface IntSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.IntSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.IntSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.IntSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends IntSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.IntSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends IntSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.IntSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.IntSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.IntSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.IntSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.IntSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.IntSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.IntSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.IntSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.IntSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntArrSeq)obj).size;
      }
      public static int[] arr(Object obj){
        return ((omni.impl.seq.IntArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.IntArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.IntArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.IntArrSeq)obj).size;
      }
      public static int[] arr(Object obj){
        return ((omni.impl.seq.IntArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.IntArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.IntArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfInt parent(Object obj){
          return (OmniList.OfInt)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.IntArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface LongDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.LongDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.LongDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.LongDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.LongDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.LongDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends LongDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.LongDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.LongDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.LongDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.LongDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.LongDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.LongDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.LongDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends LongDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.LongDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.LongDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
        return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends LongDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.LongDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.LongDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.LongDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
        return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.LongDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.LongDblLnkSeq parent(Object obj){
          return (omni.impl.seq.LongDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.LongDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.LongDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface LongSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.LongSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.LongSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.LongSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends LongSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.LongSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends LongSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.LongSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.LongSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.LongSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.LongSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.LongSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.LongSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.LongSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.LongSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.LongSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongArrSeq)obj).size;
      }
      public static long[] arr(Object obj){
        return ((omni.impl.seq.LongArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.LongArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.LongArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.LongArrSeq)obj).size;
      }
      public static long[] arr(Object obj){
        return ((omni.impl.seq.LongArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.LongArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.LongArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfLong parent(Object obj){
          return (OmniList.OfLong)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.LongArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface FloatDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.FloatDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.FloatDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.FloatDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.FloatDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.FloatDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends FloatDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.FloatDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.FloatDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.FloatDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.FloatDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.FloatDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.FloatDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.FloatDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends FloatDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.FloatDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.FloatDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
        return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends FloatDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.FloatDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.FloatDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.FloatDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
        return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.FloatDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.FloatDblLnkSeq parent(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.FloatDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.FloatDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface FloatSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.FloatSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.FloatSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.FloatSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends FloatSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.FloatSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends FloatSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.FloatSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.FloatSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.FloatSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.FloatSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.FloatSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.FloatSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.FloatSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.FloatSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.FloatSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatArrSeq)obj).size;
      }
      public static float[] arr(Object obj){
        return ((omni.impl.seq.FloatArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.FloatArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.FloatArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.FloatArrSeq)obj).size;
      }
      public static float[] arr(Object obj){
        return ((omni.impl.seq.FloatArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.FloatArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.FloatArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfFloat parent(Object obj){
          return (OmniList.OfFloat)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.FloatArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
  static interface DoubleDblLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.DoubleDblLnkSeq)obj).size;
    }
    public static omni.impl.seq.DoubleDblLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.DoubleDblLnkSeq)obj).head;
    }
    public static omni.impl.seq.DoubleDblLnkSeq.Node tail(Object obj){
      return ((omni.impl.seq.DoubleDblLnkSeq)obj).tail;
    }
    interface UncheckedList extends DoubleDblLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.UncheckedList)obj).size;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.UncheckedList)obj).head;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.UncheckedList)obj).tail;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.DoubleDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.DoubleDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.DoubleDblLnkSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.UncheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedList extends UncheckedList {
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.CheckedList)obj).size;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.CheckedList)obj).head;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq.CheckedList)obj).tail;
      }
      public static int modCount(Object obj){
         return ((omni.impl.seq.DoubleDblLnkSeq.CheckedList)obj).modCount;
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","currIndex");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"DescendingItr","modCount");
        public static omni.impl.seq.DoubleDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface DescendingItr extends AscendingItr{
        public static omni.impl.seq.DoubleDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.DoubleDblLnkSeq.CheckedList parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.CheckedList)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
    interface UncheckedSubList extends DoubleDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","writeReplace");
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList","parentOffset");
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.UncheckedList root(Object obj){
        return (omni.impl.seq.DoubleDblLnkSeq.UncheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
        return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"AscendingItr","curr");
        public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
      }
    }
    interface CheckedSubList extends DoubleDblLnkSeq{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList","writeReplace");
      static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","writeObject",ObjectOutputStream.class);
      static final Method readResolveMethod=prepareMethodForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"SerializableSubList","readResolve");
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field parentOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList","parentOffset");
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).head;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleDblLnkSeq)obj).tail;
      }
      public static omni.impl.seq.DoubleDblLnkSeq.CheckedList root(Object obj){
        return (omni.impl.seq.DoubleDblLnkSeq.CheckedList)getValue(rootField,obj);
      }
      public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
        return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
      }
      public static int parentOffset(Object obj){
        return getIntValue(parentOffsetField,obj);
      }
      public static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      interface AscendingItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","parent");
        static final Field currField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","curr");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","modCount");
        static final Field currIndexField=prepareFieldForClassName("omni.impl.seq.DoubleDblLnkSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"BidirectionalItr","currIndex");
        public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
      interface BidirectionalItr extends AscendingItr{
        public static omni.impl.seq.DoubleDblLnkSeq parent(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq)getValue(parentField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(currField,obj);
        }
        public static omni.impl.seq.DoubleDblLnkSeq.Node lastRet(Object obj){
          return (omni.impl.seq.DoubleDblLnkSeq.Node)getValue(lastRetField,obj);
        }
        public static int currIndex(Object obj){
          return getIntValue(currIndexField,obj);
        }
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
      }
    }
  }
  static interface DoubleSnglLnkSeq{
    public static int size(Object obj){
      return ((omni.impl.seq.DoubleSnglLnkSeq)obj).size;
    }
    public static omni.impl.seq.DoubleSnglLnkSeq.Node head(Object obj){
      return ((omni.impl.seq.DoubleSnglLnkSeq)obj).head;
    }
    interface UncheckedStack extends DoubleSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).head;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.DoubleSnglLnkSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.UncheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface UncheckedQueue extends DoubleSnglLnkSeq{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).head;
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq.UncheckedQueue)obj).tail;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"UncheckedQueue"+DOLLARSIGN+"Itr","this"+DOLLARSIGN+"1");
        static omni.impl.seq.DoubleSnglLnkSeq.UncheckedQueue parent(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.UncheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq.CheckedStack)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.CheckedStack parent(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.CheckedStack)getValue(parentField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface CheckedQueue extends UncheckedQueue{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node tail(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq.CheckedQueue)obj).tail;
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).size;
      }
      public static omni.impl.seq.DoubleSnglLnkSeq.Node head(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq)obj).head;
      }
      public static int modCount(Object obj){
        return ((omni.impl.seq.DoubleSnglLnkSeq.CheckedQueue)obj).modCount;
      }
      interface Itr extends AbstractItr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","parent");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"CheckedQueue"+DOLLARSIGN+"Itr","modCount");
        static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.CheckedQueue parent(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.CheckedQueue)getValue(parentField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node prev(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(prevField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node curr(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(currField,obj);
        }
        static omni.impl.seq.DoubleSnglLnkSeq.Node next(Object obj){
          return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(nextField,obj);
        }
      }
    }
    interface AbstractItr{
      static final Field prevField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"AbstractItr","prev");
      static final Field currField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"AbstractItr","curr");
      static final Field nextField=prepareFieldForClassName("omni.impl.seq.DoubleSnglLnkSeq"+DOLLARSIGN+"AbstractItr","next");
      static omni.impl.seq.DoubleSnglLnkSeq.Node prev(Object obj){
        return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(prevField,obj);
      }
      static omni.impl.seq.DoubleSnglLnkSeq.Node curr(Object obj){
        return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(currField,obj);
      }
      static omni.impl.seq.DoubleSnglLnkSeq.Node next(Object obj){
        return (omni.impl.seq.DoubleSnglLnkSeq.Node)getValue(nextField,obj);
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleArrSeq)obj).size;
      }
      public static double[] arr(Object obj){
        return ((omni.impl.seq.DoubleArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.DoubleArrSeq.UncheckedList parent(Object obj){
          return (omni.impl.seq.DoubleArrSeq.UncheckedList)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedList"+DOLLARSIGN+"ListItr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedList"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.seq.DoubleArrSeq)obj).size;
      }
      public static double[] arr(Object obj){
        return ((omni.impl.seq.DoubleArrSeq)obj).arr;
      }
      interface Itr{
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedStack"+DOLLARSIGN+"Itr","parent");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static omni.impl.seq.DoubleArrSeq.UncheckedStack parent(Object obj){
          return (omni.impl.seq.DoubleArrSeq.UncheckedStack)getValue(parentField,obj);
        }
      }
    }
    interface CheckedStack extends UncheckedStack{
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
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
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","cursor");
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","parent");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","lastRet");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedStack"+DOLLARSIGN+"Itr","modCount");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","modCount");
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList","rootOffset");
      static int modCount(Object obj){
        return getIntValue(modCountField,obj);
      }
      static int incrementModCount(Object obj){
        return incrementIntValue(modCountField,obj);
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static final Field modCountField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","modCount");
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"CheckedSubList"+DOLLARSIGN+"Itr","lastRet");
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
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        writeObjectHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
      }
      static final Field rootField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList","root");
      static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList","parent");
      static final Field rootOffsetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList","rootOffset");
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
        return ((omni.impl.AbstractOmniCollection<?>)obj).size;
      }
      interface Itr{
        static final Field parentField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","parent");
        static final Field cursorField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"Itr","cursor");
        static int cursor(Object obj){
          return getIntValue(cursorField,obj);
        }
        static OmniList.OfDouble parent(Object obj){
          return (OmniList.OfDouble)getValue(parentField,obj);
        }
      }
      interface ListItr extends Itr{
        static final Field lastRetField=prepareFieldForClassName("omni.impl.seq.DoubleArrSeq"+DOLLARSIGN+"UncheckedSubList"+DOLLARSIGN+"ListItr","lastRet");
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
