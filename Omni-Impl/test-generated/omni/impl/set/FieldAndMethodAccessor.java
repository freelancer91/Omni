package omni.impl.set;
import java.io.ObjectOutput;
import java.io.Externalizable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import omni.impl.AbstractBooleanItr;
import omni.impl.AbstractByteItr;
import java.io.ObjectOutputStream;
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
  static void setIntValue(Field field,Object obj,int val){
    try{
      field.setInt(obj,val);
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
  private static void writeObjectHelper(Object obj,ObjectOutputStream oos,Method writeObjectMethod) throws IOException{
      try {
          writeObjectMethod.invoke(obj,oos);
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
  private static void writeReplaceHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod) throws IOException{
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
  private static void writeReplaceHelper(Object obj,ObjectOutputStream oos,Method writeReplaceMethod,Method writeObjectMethod) throws IOException{
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
    writeObjectHelper(replacement,oos,writeObjectMethod);
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
  static Object callMethod(Method method,Object obj,Object...params){
    try{
      return method.invoke(obj,params);
    }catch(InvocationTargetException|IllegalAccessException e){
      throw new Error(e);
    }
  }
  private static final char DOLLARSIGN=(char)36;
  static interface RefOpenAddressHashSet
    extends AbstractOpenAddressHashSet{
    public static final Object DELETED=getValue(prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet","DELETED"),null);
    public static final Object NULL=getValue(prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet","NULL"),null);
    public static int size(Object obj){
      return ((omni.impl.set.RefOpenAddressHashSet<?>)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.RefOpenAddressHashSet<?>)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.RefOpenAddressHashSet<?>)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static Object[] table(Object obj){
      return ((omni.impl.set.RefOpenAddressHashSet<?>)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.RefOpenAddressHashSet<?> root(Object obj){
        return (omni.impl.set.RefOpenAddressHashSet<?>)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends RefOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.RefOpenAddressHashSet.Checked<?>)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.RefOpenAddressHashSet.Checked<?>)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.RefOpenAddressHashSet.Checked<?>)obj).loadFactor;
      }
      public static Object[] table(Object obj){
        return ((omni.impl.set.RefOpenAddressHashSet.Checked<?>)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.RefOpenAddressHashSet.Checked<?>)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.RefOpenAddressHashSet.Checked<?> root(Object obj){
          return (omni.impl.set.RefOpenAddressHashSet.Checked<?>)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.RefOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface AbstractBooleanSet{
    static final AbstractBooleanItr EMPTY_ITR=omni.impl.set.AbstractBooleanSet.EMPTY_ITR;
    static final omni.impl.set.AbstractBooleanSet.AscendingEmptyView CHECKED_EMPTY_ASCENDING_HEAD=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD;
    static final omni.impl.set.AbstractBooleanSet.DescendingEmptyView CHECKED_EMPTY_DESCENDING_HEAD=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD;
    static final omni.impl.set.AbstractBooleanSet.AscendingEmptyView CHECKED_EMPTY_ASCENDING_MIDDLE=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE;
    static final omni.impl.set.AbstractBooleanSet.DescendingEmptyView CHECKED_EMPTY_DESCENDING_MIDDLE=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE;
    static final omni.impl.set.AbstractBooleanSet.AscendingEmptyView CHECKED_EMPTY_ASCENDING_TAIL=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL;
    static final omni.impl.set.AbstractBooleanSet.DescendingEmptyView CHECKED_EMPTY_DESCENDING_TAIL=omni.impl.set.AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL;
    static final omni.impl.set.AbstractBooleanSet.AscendingEmptyView UNCHECKED_EMPTY_ASCENDING=omni.impl.set.AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING;
    static final omni.impl.set.AbstractBooleanSet.DescendingEmptyView UNCHECKED_EMPTY_DESCENDING=omni.impl.set.AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING;
  }
  static interface BooleanSetImpl extends AbstractBooleanSet{
    public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
    public static omni.impl.set.BooleanSetImpl clone(Object obj){
      return (omni.impl.set.BooleanSetImpl)((omni.impl.set.BooleanSetImpl)obj).clone();
    }
    public static int state(Object obj){
      return ((omni.impl.set.BooleanSetImpl)obj).state;
    }
    static interface Checked extends BooleanSetImpl{
      public static omni.impl.set.BooleanSetImpl.Checked clone(Object obj){
        return (omni.impl.set.BooleanSetImpl.Checked)((omni.impl.set.BooleanSetImpl.Checked)obj).clone();
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int state(Object obj){
        return ((omni.impl.set.BooleanSetImpl)obj).state;
      }
    }
    static interface Descending extends BooleanSetImpl{
      public static omni.impl.set.BooleanSetImpl.Descending clone(Object obj){
        return (omni.impl.set.BooleanSetImpl.Descending)((omni.impl.set.BooleanSetImpl.Descending)obj).clone();
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int state(Object obj){
        return ((omni.impl.set.BooleanSetImpl)obj).state;
      }
      static interface Checked extends Descending{
        public static omni.impl.set.BooleanSetImpl.Descending.Checked clone(Object obj){
          return (omni.impl.set.BooleanSetImpl.Descending.Checked)((omni.impl.set.BooleanSetImpl.Descending.Checked)obj).clone();
        }
        public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
          ((Externalizable)obj).writeExternal(oos);
        }
        public static int state(Object obj){
          return ((omni.impl.set.BooleanSetImpl)obj).state;
        }
      }
    }
    static interface AbstractFullView extends AbstractBooleanSet{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AbstractFullView","root");
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
    }
    static interface DescendingView extends AbstractFullView{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"DescendingView","writeReplace");
      public static Method cloneMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"DescendingView","clone");
      public static omni.impl.set.BooleanSetImpl.Descending clone(Object obj){
        return (omni.impl.set.BooleanSetImpl.Descending)callMethod(cloneMethod,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
          writeReplaceHelper(obj,oos,writeReplaceMethod);
        }
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static interface Checked extends DescendingView{
        static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"DescendingView"+DOLLARSIGN+"Checked","writeReplace");
  	  static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"DescendingView"+DOLLARSIGN+"Checked"+DOLLARSIGN+"SerializationIntermediate","writeObject",ObjectOutputStream.class);
        public static Method cloneMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"DescendingView"+DOLLARSIGN+"Checked","clone");
        public static omni.impl.set.BooleanSetImpl.Descending.Checked clone(Object obj){
          return (omni.impl.set.BooleanSetImpl.Descending.Checked)callMethod(cloneMethod,obj);
        }
        public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
          writeReplaceHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
        }
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
      }
    }
    static interface AscendingView extends AbstractFullView{
      static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AscendingView","writeReplace");
      public static Method cloneMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AscendingView","clone");
      public static omni.impl.set.BooleanSetImpl clone(Object obj){
        return (omni.impl.set.BooleanSetImpl)callMethod(cloneMethod,obj);
      }
      public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
          writeReplaceHelper(obj,oos,writeReplaceMethod);
        }
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static interface Checked extends AscendingView{
        static final Method writeReplaceMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AscendingView"+DOLLARSIGN+"Checked","writeReplace");
    	  static final Method writeObjectMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AscendingView"+DOLLARSIGN+"Checked"+DOLLARSIGN+"SerializationIntermediate","writeObject",ObjectOutputStream.class);
        public static Method cloneMethod=prepareMethodForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AscendingView"+DOLLARSIGN+"Checked","clone");
        public static omni.impl.set.BooleanSetImpl.Checked clone(Object obj){
          return (omni.impl.set.BooleanSetImpl.Checked)callMethod(cloneMethod,obj);
        }
        public static void writeObject(Object obj,ObjectOutputStream oos) throws IOException{
          writeReplaceHelper(obj,oos,writeReplaceMethod,writeObjectMethod);
        }
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
      }
    }
    static interface AbstractSingleView extends AbstractBooleanSet{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"AbstractFullView","root");
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
    }
    static interface UncheckedTrueView extends AbstractSingleView{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static interface Descending extends UncheckedTrueView{
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
      }
      static interface Checked extends UncheckedTrueView{
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
        static interface Descending extends Checked{
          static omni.impl.set.BooleanSetImpl root(Object obj){
            return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
          }
        }
      }
    }
    static interface UncheckedFalseView extends AbstractSingleView{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static interface Descending extends UncheckedFalseView{
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
      }
      static interface Checked extends UncheckedFalseView{
        static omni.impl.set.BooleanSetImpl root(Object obj){
          return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
        }
        static interface Descending extends Checked{
          static omni.impl.set.BooleanSetImpl root(Object obj){
            return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
          }
        }
      }
    }
    static interface UncheckedAscendingFullItr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"UncheckedAscendingFullItr","root");
      static final Field itrStateField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"UncheckedAscendingFullItr","itrState");
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface CheckedAscendingFullItr extends UncheckedAscendingFullItr{
      static final Field expectedRootStateField=prepareFieldForClassName("omni.impl.set.BooleanSetImpl"+DOLLARSIGN+"CheckedAscendingFullItr","expectedRootState");
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int expectedRootState(Object obj){
        return getIntValue(expectedRootStateField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface UncheckedDescendingFullItr extends UncheckedAscendingFullItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface CheckedDescendingFullItr extends CheckedAscendingFullItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int expectedRootState(Object obj){
        return getIntValue(expectedRootStateField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface UncheckedTrueItr extends UncheckedAscendingFullItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface UncheckedFalseItr extends UncheckedTrueItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface CheckedTrueItr extends UncheckedAscendingFullItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
    static interface CheckedFalseItr extends CheckedTrueItr{
      static omni.impl.set.BooleanSetImpl root(Object obj){
        return (omni.impl.set.BooleanSetImpl)getValue(rootField,obj);
      }
      static int itrState(Object obj){
        return getIntValue(itrStateField,obj);
      }
    }
  }
  //TODO ByteSet
  static interface CharOpenAddressHashSet
    extends AbstractIntegralTypeOpenAddressHashSet{
    public static long word0(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).word0;
    }
    public static long word1(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).word1;
    }
    public static long word2(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).word2;
    }
    public static long word3(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).word3;
    }
    public static int tableSize(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).tableSize;
    }
    public static int size(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static char[] table(Object obj){
      return ((omni.impl.set.CharOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.CharOpenAddressHashSet root(Object obj){
        return (omni.impl.set.CharOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends CharOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static long word0(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).word0;
      }
      public static long word1(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).word1;
      }
      public static long word2(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).word2;
      }
      public static long word3(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).word3;
      }
      public static int tableSize(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).tableSize;
      }
      public static char[] table(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.CharOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.CharOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.CharOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.CharOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface ShortOpenAddressHashSet
    extends AbstractIntegralTypeOpenAddressHashSet{
    public static long word0(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).word0;
    }
    public static long word1(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).word1;
    }
    public static long word2(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).word2;
    }
    public static long word3(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).word3;
    }
    public static int tableSize(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).tableSize;
    }
    public static int size(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static short[] table(Object obj){
      return ((omni.impl.set.ShortOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.ShortOpenAddressHashSet root(Object obj){
        return (omni.impl.set.ShortOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends ShortOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static long word0(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).word0;
      }
      public static long word1(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).word1;
      }
      public static long word2(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).word2;
      }
      public static long word3(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).word3;
      }
      public static int tableSize(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).tableSize;
      }
      public static short[] table(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.ShortOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.ShortOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.ShortOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.ShortOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface IntOpenAddressHashSet
    extends AbstractIntegralTypeOpenAddressHashSet{
    public static long word0(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).word0;
    }
    public static long word1(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).word1;
    }
    public static long word2(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).word2;
    }
    public static long word3(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).word3;
    }
    public static int tableSize(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).tableSize;
    }
    public static int size(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static int[] table(Object obj){
      return ((omni.impl.set.IntOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.IntOpenAddressHashSet root(Object obj){
        return (omni.impl.set.IntOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends IntOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static long word0(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).word0;
      }
      public static long word1(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).word1;
      }
      public static long word2(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).word2;
      }
      public static long word3(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).word3;
      }
      public static int tableSize(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).tableSize;
      }
      public static int[] table(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.IntOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.IntOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.IntOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.IntOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface LongOpenAddressHashSet
    extends AbstractIntegralTypeOpenAddressHashSet{
    public static long word0(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).word0;
    }
    public static long word1(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).word1;
    }
    public static long word2(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).word2;
    }
    public static long word3(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).word3;
    }
    public static int tableSize(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).tableSize;
    }
    public static int size(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static long[] table(Object obj){
      return ((omni.impl.set.LongOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.LongOpenAddressHashSet root(Object obj){
        return (omni.impl.set.LongOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends LongOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static long word0(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).word0;
      }
      public static long word1(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).word1;
      }
      public static long word2(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).word2;
      }
      public static long word3(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).word3;
      }
      public static int tableSize(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).tableSize;
      }
      public static long[] table(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.LongOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.LongOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.LongOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.LongOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface FloatOpenAddressHashSet
    extends AbstractOpenAddressHashSet{
    public static int size(Object obj){
      return ((omni.impl.set.FloatOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.FloatOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.FloatOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static int[] table(Object obj){
      return ((omni.impl.set.FloatOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.FloatOpenAddressHashSet root(Object obj){
        return (omni.impl.set.FloatOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends FloatOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.FloatOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.FloatOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.FloatOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static int[] table(Object obj){
        return ((omni.impl.set.FloatOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.FloatOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.FloatOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.FloatOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.FloatOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface DoubleOpenAddressHashSet
    extends AbstractOpenAddressHashSet{
    public static int size(Object obj){
      return ((omni.impl.set.DoubleOpenAddressHashSet)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.DoubleOpenAddressHashSet)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.DoubleOpenAddressHashSet)obj).loadFactor;
    }
    public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
      ((Externalizable)obj).writeExternal(oos);
    }
      public static long[] table(Object obj){
      return ((omni.impl.set.DoubleOpenAddressHashSet)obj).table;
    }
    public static interface Itr{
      static final Field rootField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Itr","root");
      public static omni.impl.set.DoubleOpenAddressHashSet root(Object obj){
        return (omni.impl.set.DoubleOpenAddressHashSet)getValue(rootField,obj);
      }
      static final Field offsetField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Itr","offset");
      public static int offset(Object obj){
        return getIntValue(offsetField,obj);
      }
    }
    public static interface Checked extends DoubleOpenAddressHashSet{
      public static void writeObject(Object obj,ObjectOutput oos) throws IOException{
        ((Externalizable)obj).writeExternal(oos);
      }
      public static int size(Object obj){
        return ((omni.impl.set.DoubleOpenAddressHashSet.Checked)obj).size;
      }
      public static int maxTableSize(Object obj){
        return ((omni.impl.set.DoubleOpenAddressHashSet.Checked)obj).maxTableSize;
      }
      public static float loadFactor(Object obj){
        return ((omni.impl.set.DoubleOpenAddressHashSet.Checked)obj).loadFactor;
      }
      public static long[] table(Object obj){
        return ((omni.impl.set.DoubleOpenAddressHashSet.Checked)obj).table;
      }
      public static int modCount(Object obj){
        return ((omni.impl.set.DoubleOpenAddressHashSet.Checked)obj).modCount;
      }
      public static interface Itr{
        static final Field rootField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","root");
        public static omni.impl.set.DoubleOpenAddressHashSet.Checked root(Object obj){
          return (omni.impl.set.DoubleOpenAddressHashSet.Checked)getValue(rootField,obj);
        }
        static final Field offsetField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","offset");
        public static int offset(Object obj){
          return getIntValue(offsetField,obj);
        }
        static final Field modCountField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","modCount");
        public static int modCount(Object obj){
          return getIntValue(modCountField,obj);
        }
        static final Field lastRetField=prepareFieldForClassName("omni.impl.set.DoubleOpenAddressHashSet"+DOLLARSIGN+"Checked"+DOLLARSIGN+"Itr","lastRet");
        public static int lastRet(Object obj){
          return getIntValue(lastRetField,obj);
        }
      }
    }
  }
  static interface AbstractOpenAddressHashSet{
    public static int size(Object obj){
      return ((omni.impl.set.AbstractOpenAddressHashSet<?>)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.AbstractOpenAddressHashSet<?>)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.AbstractOpenAddressHashSet<?>)obj).loadFactor;
    }
  }
  static interface AbstractIntegralTypeOpenAddressHashSet extends AbstractOpenAddressHashSet{
    public static int size(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).size;
    }
    public static int maxTableSize(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).maxTableSize;
    }
    public static float loadFactor(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).loadFactor;
    }
    public static long word0(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).word0;
    }
    public static long word1(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).word1;
    }
    public static long word2(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).word2;
    }
    public static long word3(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).word3;
    }
    public static int tableSize(Object obj){
      return ((omni.impl.set.AbstractIntegralTypeOpenAddressHashSet<?>)obj).tableSize;
    }
  }
}
