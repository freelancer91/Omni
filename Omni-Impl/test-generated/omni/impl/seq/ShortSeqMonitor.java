package omni.impl.seq;
import omni.util.TypeConversionUtil;
import omni.util.OmniArray;
import omni.api.OmniCollection;
import omni.api.OmniList;
import org.junit.jupiter.api.Assertions;
import omni.impl.ShortInputTestArgType;
class ShortSeqMonitor
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
  final OmniCollection.OfShort root;
  final OmniCollection.OfShort parent;
  final OmniCollection.OfShort seq;
  PreModScenario activePreModScenario;
  int expectedRootSize;
  int expectedParentSize;
  int expectedSeqSize;
  int expectedRootModCount;
  int expectedParentModCount;
  int expectedSeqModCount;
  ShortSeqMonitor(final StructType structType, final NestedType nestedType,final boolean checked)
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
          short[] arr=new short[20];
          int i=0,v=Integer.MIN_VALUE;
          for(;i<5;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertToshort(v);
          }
          for(;i<10;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertToshort(v);
          }
          v=Integer.MAX_VALUE-10;
          for(;i<15;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertToshort(v);
          }
          for(;i<20;++i,++v)
          {
            arr[i]=TypeConversionUtil.convertToshort(v);
          }
          if(checked)
          {
            this.root=new ShortArrSeq.CheckedList(20,arr);
          }
          else
          {
            this.root=new ShortArrSeq.UncheckedList(20,arr);
          }
          this.parent=((OmniList.OfShort)root).subList(5,15);
          this.seq=((OmniList.OfShort)parent).subList(5,5);
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
                this.root=new ShortArrSeq.CheckedStack();
              }
              else
              {
                this.root=new ShortArrSeq.UncheckedStack();
              }
              break;
            case LIST:
              if(checked)
              {
                this.root=new ShortArrSeq.CheckedList();
              }
              else
              {
                this.root=new ShortArrSeq.UncheckedList();
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
  ShortSeqMonitor(final StructType structType, final NestedType nestedType,final boolean checked,final int initialCapacity,final int rootPreAlloc,final int parentPreAlloc,final int parentPostAlloc,final int rootPostAlloc)
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
        short[] arr;
        if(rootSize==0)
        {
          switch(initialCapacity)
          {
            case 0:
              arr=null;
              break;
            case OmniArray.DEFAULT_ARR_SEQ_CAP:
              arr=OmniArray.OfShort.DEFAULT_ARR;
              break;
            default:
              arr=new short[initialCapacity];
          }
        }
        else
        {
          arr=new short[Math.max(initialCapacity,rootSize)];
        }
        int i=0,v=Integer.MIN_VALUE;
        for(int bound=i+rootPreAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertToshort(v);
        }
        for(int bound=i+parentPreAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertToshort(v);
        }
        v=Integer.MAX_VALUE-rootPostAlloc-parentPostAlloc;
        for(int bound=i+parentPostAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertToshort(v);
        }
        for(int bound=i+rootPostAlloc;i<bound;++i,++v)
        {
          arr[i]=TypeConversionUtil.convertToshort(v);
        }
        if(nestedType==NestedType.STACK)
        {
          if(checked)
          {
            this.root=new ShortArrSeq.CheckedStack(rootSize,arr);
          }
          else
          {
            this.root=new ShortArrSeq.UncheckedStack(rootSize,arr);
          }
        }
        else
        {
          if(checked)
          {
            this.root=new ShortArrSeq.CheckedList(rootSize,arr);
          }
          else
          {
            this.root=new ShortArrSeq.UncheckedList(rootSize,arr);
          }
        }
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
    switch(nestedType)
    {
      case SUBLIST:
        this.parent=((OmniList.OfShort)root).subList(rootPreAlloc,rootPreAlloc+parentPreAlloc+parentPostAlloc);
        this.seq=((OmniList.OfShort)parent).subList(parentPreAlloc,parentPreAlloc);
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
    public abstract void verifyIndexAndIterate(ShortInputTestArgType inputArgType,int val);
    public abstract SeqMonitorItr getPositiveOffset(int i);
    public abstract boolean equals(Object val);
    private static class OfArrSeq extends SeqMonitorItr
    {
      short[] arr;
      int offset;
      private OfArrSeq(int offset,short[] arr)
      {
        this.arr=arr;
      }
      public void verifyIndexAndIterate(ShortInputTestArgType inputArgType,int val)
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
        var arr=((ShortArrSeq)root).arr;
        int offset=0;
        for(int bound=offset+rootPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v)
        {
          ShortInputTestArgType.ARRAY_TYPE.verifyVal(v,arr[offset]);
        }
        return new SeqMonitorItr.OfArrSeq(offset,arr);
      }
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
  public SeqMonitorItr verifyAscending(SeqMonitorItr monitorItr,ShortInputTestArgType inputArgType,int length)
  {
    for(int i=0,v=0;i<length;++i,++v)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyDescending(SeqMonitorItr monitorItr,ShortInputTestArgType inputArgType,int length)
  {
    for(int i=0,v=length;i<length;++i)
    {
      monitorItr.verifyIndexAndIterate(inputArgType,--v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyMidPointInsertion(SeqMonitorItr monitorItr,ShortInputTestArgType inputArgType,final int length)
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
      monitorItr.verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyRootPostAlloc(SeqMonitorItr monitorItr)
  {
    for(int i=0,v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
      monitorItr.verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
    }
    return monitorItr;
  }
  public SeqMonitorItr verifyIllegalAdd(SeqMonitorItr monitorItr)
  {
    monitorItr.verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,0);
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
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        ++expectedSeqModCount;
        ++expectedSeqSize;
        activePreModScenario=preModScenario;
        break;
      case ModParent:
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(parent,0);
        ++expectedRootModCount;
        ++expectedRootSize;
        ++expectedParentModCount;
        ++expectedParentSize;
        activePreModScenario=preModScenario;
        break;
      case ModRoot:
        ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(root,0);
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
  public void add(int index,int val,ShortInputTestArgType inputArgType)
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
    add(index,val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public boolean add(int val,ShortInputTestArgType inputArgType)
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
    return add(val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public void push(int val,ShortInputTestArgType inputArgType)
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
    push(val,ShortInputTestArgType.ARRAY_TYPE);
  }
  public String toString()
  {
    StringBuilder builder=new StringBuilder();
    switch(structType)
    {
      case ARRSEQ:
        builder.append("ShortArrSeq.").append(checked?"Checked":"Unchecked");
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
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checked)
            {
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedList.modCount(root));
            }
          case SUBLIST:
            OmniList.OfShort actualSeqParent;
            ShortArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfShort actualParentParent;
            ShortArrSeq  actualParentRoot;
            int actualParentSize;
            if(checked)
            {
              actualSeqParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.ShortArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAccessor.ShortArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAccessor.ShortArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAccessor.ShortArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAccessor.ShortArrSeq.CheckedList.modCount(root));
            }
            else
            {
              actualSeqParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAccessor.ShortArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfShort)FieldAccessor.ShortArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(ShortArrSeq)FieldAccessor.ShortArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAccessor.ShortArrSeq.UncheckedSubList.size(parent);
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
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAccessor.ShortArrSeq.size(root));
        break;
      default:
        throw new Error("Unknown structType "+structType);
    }
  }
}
