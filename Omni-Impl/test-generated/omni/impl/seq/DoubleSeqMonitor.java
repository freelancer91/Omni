package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import org.junit.jupiter.api.Assertions;
import omni.impl.DoubleInputTestArgType;
class DoubleSeqMonitor
{
  static enum StructType
  {
    ARRSEQ;
  }
  static enum NestedType
  {
    LIST,
    STACK,
    SUBLIST;
  }
  static enum PreModScenario
  {
    ModSeq,
    ModParent,
    ModRoot,
    NoMod;
  }
  static enum FunctionExceptionScenario
  {
    ModItr,
    ModItrThrow,
    ModSeq,
    ModSeqThrow,
    ModParent,
    ModParentThrow,
    ModRoot,
    ModRootThrow,
    Throw,
    NoMod;
  }
  final StructType structType;
  final NestedType nestedType;
  final boolean checked;
  final int initialCapacity;
  final int rootPreAlloc;
  final int parentPreAlloc;
  final int parentPostAlloc;
  final int rootPostAlloc;
  final OmniCollection.OfDouble root;
  final OmniCollection.OfDouble parent;
  final OmniCollection.OfDouble seq;
  PreModScenario activePreModScenario;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  DoubleSeqMonitor(final StructType structType, final NestedType nestedType,final boolean checked)
  {
    this.structType=structType;
    this.nestedType=nestedType;
    this.checked=checked;
    this.initialCapacity=OmniArray.DEFAULT_ARR_SEQ_CAP;
    switch(structType)
    {
      case ARRSEQ:
        if(nestedType==NestedType.SUBLIST)
        {
          this.rootPreAlloc=5;
          this.parentPreAlloc=5;
          this.parentPostAlloc=5;
          this.rootPostAlloc=5;
          double[] arr=new double[20];
          int i=0,v=Integer.MIN_VALUE;
          for(;i<5;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertTodouble(v);
          }
          for(;i<10;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertTodouble(v);
          }
          v=Integer.MAX_VALUE-10;
          for(;i<15;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertTodouble(v);
          }
          for(;i<20;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertTodouble(v);
          }
          if(checked)
          {
            this.root=new DoubleArrSeq.CheckedList(20,arr);
          }
          else
          {
            this.root=new DoubleArrSeq.UncheckedList(20,arr);
          }
          this.parent=((OmniList.OfDouble)root).subList(5,15);
          this.seq=((OmniList.OfDouble)parent).subList(5,5);
        }
        else
        {
          this.rootPreAlloc=0;
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.rootPostAlloc=0;
          switch(nestedType)
          {
            case STACK:
              if(checked)
              {
                this.root=new DoubleArrSeq.CheckedStack();
              }
              else
              {
                this.root=new DoubleArrSeq.UncheckedStack();
              }
              break;
            case LIST:
              if(checked)
              {
                this.root=new DoubleArrSeq.CheckedList();
              }
              else
              {
                this.root=new DoubleArrSeq.UncheckedList();
              }
              break;
            default:
              throw new Error("Unknown nestedType "+nestedType);
          }
          this.parent=root;
          this.seq=root;
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  DoubleSeqMonitor(final StructType structType, final NestedType nestedType,final boolean checked,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc)
  {
    this.structType=structType;
    this.nestedType=nestedType;
    this.checked=checked;
    this.initialCapacity=initialCapacity;
    this.rootPreAlloc=rootPreAlloc;
    this.parentPreAlloc=parentPreAlloc;
    this.parentPostAlloc=parentPostAlloc;
    this.rootPostAlloc=rootPostAlloc;
    switch(structType)
    {
      case ARRSEQ:
        int rootSize=rootPreAlloc+parentPreAlloc+parentPostAlloc+rootPostAlloc;
        double[] arr;
        if(rootSize==0)
        {
          switch(initialCapacity)
          {
            case 0:
              arr=null;
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              arr=OmniArray.OfDouble.DEFAULT_ARR;
              break;
            default:
              arr=new double[initialCapacity];
          }
        }
        else
        {
          arr=new double[Math.max(initialCapacity,rootSize)];
        }
        int i=0,v=Integer.MIN_VALUE;
        for(int bound=i+rootPreAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertTodouble(v);
        }
        for(int bound=i+parentPreAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertTodouble(v);
        }
        v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc;
        for(int bound=i+parentPostAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertTodouble(v);
        }
        for(int bound=i+rootPostAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertTodouble(v);
        }
        if(nestedType==NestedType.STACK)
        {
          if(checked)
          {
            this.root=new DoubleArrSeq.CheckedStack(rootSize,arr);
          }
          else
          {
            this.root=new DoubleArrSeq.UncheckedStack(rootSize,arr);
          }
        }
        else
        {
          if(checked)
          {
            this.root=new DoubleArrSeq.CheckedList(rootSize,arr);
          }
          else
          {
            this.root=new DoubleArrSeq.UncheckedList(rootSize,arr);
          }
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    switch(nestedType)
    {
      case SUBLIST:
        this.parent=((OmniList.OfDouble)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfDouble)parent).subList(parentPreAlloc,parentPreAlloc);
        break;
      case LIST:
      case STACK:
        this.parent=root;
        this.seq=root;
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
  }
  public static abstract class SeqMonitorItr
  {
    public abstract void verifyIndexAndIterate(DoubleInputTestArgType inputArgType,int val);
    public abstract SeqMonitorItr getPositiveOffset(int i);
    public abstract boolean equals(Object val);
    private static class OfArrSeq extends SeqMonitorItr
    {
      double[] arr;
      int offset;
      private OfArrSeq(int offset,double[] arr)
      {
        this.arr=arr;
      }
      public void verifyIndexAndIterate(DoubleInputTestArgType inputArgType,int val)
      {
        inputArgType.verifyVal(val,arr[offset++]);
      }
      public SeqMonitorItr getPositiveOffset(int i){
        if(i<0)
        {
          throw new Error("offset cannot be negative: "+i);
        }
        return new OfArrSeq(i+offset,arr);
      }
      public boolean equals(Object val){
        final OfArrSeq that;
        return val==this || (val instanceof OfArrSeq && (that=(OfArrSeq)val).arr==this.arr && that.offset==this.offset);
      }
    }
  }
  public SeqMonitorItr verifyPreAlloc()
  {
    switch(structType)
    {
      case ARRSEQ:
      {
        var arr=((DoubleArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v)
        {
          DoubleInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
        }
        return new SeqMonitorItr.OfArrSeq(offset,arr);
      }
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,DoubleInputTestArgType inputArgType,int length)
  {
    for(int i=0,v=0;i<length;++i,++v)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,DoubleInputTestArgType inputArgType,int length)
  {
    for(int i=0,v=length;i<length;++i)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,--v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,DoubleInputTestArgType inputArgType,final int length)
  {
    int halfLength=length/2;
    int i=0;
    for(int v=1;i<halfLength;++i,v+=2)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    halfLength=length-halfLength;
    for(int v=length-2;i<halfLength;++i,v-=2)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyParentPostAlloc(SeqMonitorItr monitorItr)
  {
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc;i<parentPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(DoubleInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyRootPostAlloc(SeqMonitorItr monitorItr)
  {
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(DoubleInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyIllegalAdd(SeqMonitorItr monitorItr)
  {
    monitorItr.verifyIndexAndIterate(DoubleInputTestArgType.ARRAY_TYPE,0);
    return monitorItr;
  }
  public void illegalAdd(PreModScenario preModScenario)
  {
    if(activePreModScenario!=null)
    {
      throw new Error("Cannot modify sequence with "+preModScenario+" because it has already been modified with "+activePreModScenario);
    }
    switch(preModScenario){
      case ModSeq:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        activePreModScenario=preModScenario;
        break;
      case ModParent:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        activePreModScenario=preModScenario;
        break;
      case ModRoot:
        DoubleInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        activePreModScenario=preModScenario;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public void add(int index,int val,DoubleInputTestArgType inputArgType)
  {
    inputArgType.callListAdd(seq,index,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void add(int index,int val)
  {
    add(index,val,DoubleInputTestArgType.ARRAY_TYPE);
  }
  public boolean add(int val,DoubleInputTestArgType inputArgType)
  {
    boolean ret=inputArgType.callCollectionAdd(seq,val);
    if(ret)
    {
      ++expectedRootSize;
      ++expectedParentSize;
      ++expectedSeqSize;
      ++expectedRootModCount;
      ++expectedParentModCount;
      ++expectedSeqModCount;
    }
    return ret;
  }
  public boolean add(int val)
  {
    return add(val,DoubleInputTestArgType.ARRAY_TYPE);
  }
  public void push(int val,DoubleInputTestArgType inputArgType)
  {
    inputArgType.callStackPush(seq,val);
    ++expectedRootSize;
    ++expectedParentSize;
    ++expectedSeqSize;
    ++expectedRootModCount;
    ++expectedParentModCount;
    ++expectedSeqModCount;
  }
  public void push(int val)
  {
    push(val,DoubleInputTestArgType.ARRAY_TYPE);
  }
  public String toString()
  {
    StringBuilder builder=new StringBuilder();
    switch(structType)
    {
      case ARRSEQ:
        builder.append("DoubleArrSeq.").append(checked?"Checked":"Unchecked");
        switch(nestedType)
        {
          case STACK:
            builder.append("Stack{").append(initialCapacity);
            break;
          case LIST:
            builder.append("List{").append(initialCapacity);
            break;
          case SUBLIST:
            builder.append("SubList{").append(rootPreAlloc).append(',').append(parentPreAlloc).append(',').append(parentPostAlloc).append(',').append(rootPostAlloc);
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    return builder.append('}').toString();
  }
  public void verifyStructuralIntegrity()
  {
    switch(structType)
    {
      case ARRSEQ:
        switch(nestedType)
        {
          case STACK:
            if(checked)
            {
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checked)
            {
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedList.modCount(root));
            }
          case SUBLIST:
            OmniList.OfDouble actualSeqParent;
            DoubleArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfDouble actualParentParent;
            DoubleArrSeq  actualParentRoot;
            int actualParentSize;
            if(checked)
            {
              actualSeqParent=(OmniList.OfDouble)FieldAccessor.DoubleArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(DoubleArrSeq)FieldAccessor.DoubleArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.DoubleArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfDouble)FieldAccessor.DoubleArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(DoubleArrSeq)FieldAccessor.DoubleArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAccessor.DoubleArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAccessor.DoubleArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAccessor.DoubleArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.DoubleArrSeq.CheckedList.modCount(root));
            }
            else
            {
              actualSeqParent=(OmniList.OfDouble)FieldAccessor.DoubleArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(DoubleArrSeq)FieldAccessor.DoubleArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.DoubleArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfDouble)FieldAccessor.DoubleArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(DoubleArrSeq)FieldAccessor.DoubleArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAccessor.DoubleArrSeq.UncheckedSubList.size(parent);
            }
            Assertions.assertSame(root,actualSeqRoot);
            Assertions.assertSame(root,actualParentRoot);
            Assertions.assertSame(parent,actualSeqParent);
            Assertions.assertNull(actualParentParent);
            Assertions.assertEquals(expectedSeqSize,actualSeqSize);
            Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
            break;
          default:
            throw new Error("Unknown structType "+structType);
        }
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.DoubleArrSeq.size(root));
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
}
