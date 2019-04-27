package omni.impl.seq;
import omni.function.ShortConsumer;
import omni.function.ShortPredicate;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.ShortInputTestArgType;
import omni.impl.ShortOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.ShortDblLnkNode;
import omni.api.OmniIterator;
import java.util.function.Predicate;
import omni.impl.FunctionCallType;
import omni.impl.QueryCastType;
import java.io.File;
import omni.api.OmniStack;
import omni.util.TypeUtil;
import org.junit.jupiter.api.Tag;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Externalizable;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import omni.impl.seq.AbstractShortSeqMonitor.CheckedType;
import omni.impl.seq.AbstractShortSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractShortSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractShortSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractShortSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractShortSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractShortSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractShortSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class ShortDblLnkSeqTest{
@FunctionalInterface
  interface ArgBuilder{
    void buildArgs(Stream.Builder<Arguments> streamBuilder,NestedType nestedType,CheckedType checkedType);
    static Stream<Arguments> buildSeqArgs(ArgBuilder argBuilder){
      Stream.Builder<Arguments> streamBuilder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          argBuilder.buildArgs(streamBuilder,nestedType,checkedType);
        }
      }
      return streamBuilder.build();
    }
  }
  static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractShortSeqMonitor<ShortDblLnkSeq>{
    private static final ShortDblLnkSeq[] EMPTY_PARENTS=new ShortDblLnkSeq[0];
    final NestedType nestedType;
    final ShortDblLnkSeq[] parents;
    final int[] parentOffsets;
    final int[] expectedParentModCounts;
    final int[] expectedParentSizes;
    final int parentPreAlloc;
    final int parentPostAlloc;
    SeqMonitor(CheckedType checkedType,NestedType nestedType,ShortDblLnkNode head,int seqSize,ShortDblLnkNode tail){
      super(checkedType);
      this.nestedType=nestedType;
      this.parentPreAlloc=0;
      this.parentPostAlloc=0;
      this.expectedSeqSize=seqSize;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new ShortDblLnkSeq.CheckedList(head,seqSize,tail):new ShortDblLnkSeq.UncheckedList(head,seqSize,tail);
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          this.parents=new ShortDblLnkSeq[]{checkedType.checked?new ShortDblLnkSeq.CheckedList(head,seqSize,tail):new ShortDblLnkSeq.UncheckedList(head,seqSize,tail)};
          this.parentOffsets=new int[]{0};
          this.expectedParentModCounts=new int[0];
          this.expectedParentSizes=new int[]{seqSize};
          this.seq=(ShortDblLnkSeq)this.parents[0].subList(0,seqSize);
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
    }
    SeqMonitor(CheckedType checkedType,NestedType nestedType){
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType){
        case LISTDEQUE:
          this.seq=checkedType.checked?new ShortDblLnkSeq.CheckedList():new ShortDblLnkSeq.UncheckedList();
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i){
            currHead=currHead.next=new ShortDblLnkNode(currHead,TypeConversionUtil.convertToshort(Integer.MIN_VALUE+i));
          }
          var rootTail=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i){
            currTail=currTail.prev=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          ShortDblLnkSeq root;
          root=checkedType.checked?new ShortDblLnkSeq.CheckedList(rootHead,20,rootTail):new ShortDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          this.parents=new ShortDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(ShortDblLnkSeq)root.subList(5,15);
          this.seq=(ShortDblLnkSeq)parents[0].subList(5,5);
          this.parentOffsets=new int[]{5,5};
          this.expectedParentSizes=new int[]{10,20};
          this.expectedParentModCounts=new int[]{0,0};
          this.parentPreAlloc=5;
          this.parentPostAlloc=5;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SeqMonitor(CheckedType checkedType,int[] parentPreAllocs,int[] parentPostAllocs){
      super(checkedType);
      Assertions.assertEquals(parentPreAllocs.length,parentPostAllocs.length);
      if(parentPreAllocs.length==0){
        this.parentPreAlloc=0;
        this.parentPostAlloc=0;
        this.nestedType=NestedType.LISTDEQUE;
        this.seq=checkedType.checked?new ShortDblLnkSeq.CheckedList():new ShortDblLnkSeq.UncheckedList();
        this.parents=EMPTY_PARENTS;
        this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
      }else{
        this.nestedType=NestedType.SUBLIST;
        int totalPreAlloc=0;
        int totalPostAlloc=0;
        for(int i=0;i<parentPreAllocs.length;++i){
          totalPreAlloc+=parentPreAllocs[i];
          totalPostAlloc+=parentPostAllocs[i];
        }
        this.parentPreAlloc=totalPreAlloc-parentPreAllocs[parentPreAllocs.length-1];
        this.parentPostAlloc=totalPostAlloc-parentPostAllocs[parentPostAllocs.length-1];
        ShortDblLnkNode rootHead=null;
        ShortDblLnkNode rootTail=null;
        var currHead=rootHead;
        var currTail=rootTail;
        if(totalPreAlloc!=0){
          rootHead=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MIN_VALUE));
          currHead=rootHead;
          for(int i=1;i<totalPreAlloc;++i){
            currHead=currHead.next=new ShortDblLnkNode(currHead,TypeConversionUtil.convertToshort(Integer.MIN_VALUE+i));
          }
        }
        if(totalPostAlloc!=0){
          rootTail=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MAX_VALUE));
          currTail=rootTail;
          for(int i=1;i<totalPreAlloc;++i){
            currTail=currTail.prev=new ShortDblLnkNode(TypeConversionUtil.convertToshort(Integer.MAX_VALUE-i),currTail);
          }
        }
        if(currHead!=null){
          if(currTail!=null){
            currHead.next=currTail;
            currTail.prev=currHead;
          }else{
            rootTail=currHead;
          }
        }else if(currTail!=null){
          rootHead=currTail;
        }
        ShortDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        root=checkedType.checked?new ShortDblLnkSeq.CheckedList(rootHead,rootSize,rootTail):new ShortDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        this.parents=new ShortDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length-1;--i>=0;){
          int fromIndex=parentPreAllocs[i+1];
          int toIndex=expectedParentSizes[i+1]-parentPostAllocs[i+1];
          parents[i]=(ShortDblLnkSeq)parents[i+1].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(ShortDblLnkSeq)parents[0].subList(fromIndex,toIndex);
        this.expectedSeqSize=toIndex-fromIndex;
        Assertions.assertEquals(0,this.expectedSeqSize);
      }
    }
    AbstractItrMonitor getItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedAscendingItrMonitor():new UncheckedAscendingItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedSubAscendingItrMonitor():new UncheckedSubAscendingItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractShortSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      return checkedType.checked?new CheckedDescendingItrMonitor():new UncheckedDescendingItrMonitor();
    }
    AbstractShortSeqMonitor.AbstractItrMonitor getListItrMonitor(){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor():new UncheckedBidirectionalItrMonitor();
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor():new UncheckedBidirectionalSubItrMonitor();
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractShortSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
      switch(nestedType){
        case LISTDEQUE:
          return checkedType.checked?new CheckedBidirectionalItrMonitor(index):new UncheckedBidirectionalItrMonitor(index);
        case SUBLIST:
          return checkedType.checked?new CheckedBidirectionalSubItrMonitor(index):new UncheckedBidirectionalSubItrMonitor(index);
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      int rootPreAlloc;
      ShortDblLnkNode curr;
      switch(nestedType){
        case LISTDEQUE:
          curr=seq.head;
          rootPreAlloc=0;
          break;
        case SUBLIST:
          curr=parents[parents.length-1].head;
          rootPreAlloc=parentOffsets[parentOffsets.length-1];
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc;offset<bound;++offset,curr=curr.next){
         ShortInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      ShortDblLnkNode curr;
      switch(nestedType){
        case LISTDEQUE:
          curr=seq.head;
          rootPreAlloc=0;
          break;
        case SUBLIST:
          curr=parents[parents.length-1].head;
          rootPreAlloc=parentOffsets[parentOffsets.length-1];
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
      int offset=0;
      for(int bound=offset+rootPreAlloc+parentPreAlloc,v=Integer.MIN_VALUE;offset<bound;++offset,++v,curr=curr.next){
        ShortInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
    void illegalAdd(PreModScenario preModScenario){
      switch(preModScenario)
      {
        case ModSeq:
          ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
          verifyAddition();
          break;
        case ModParent:
          int index;
          ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
          ++expectedParentSizes[++index];
          ++expectedParentModCounts[++index];
          break;
        case ModRoot:
          ShortInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
          ++expectedParentSizes[index];
          ++expectedParentModCounts[index];
        case NoMod:
          break;
        default:
          throw new Error("Unknown preModScenario "+preModScenario);
      }
    }
    void verifyAddition(){
      ++expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        ++expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
    public String toString(){
      var builder=new StringBuilder("ShortDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType){
        case LISTDEQUE:
          builder.append("List{").append(expectedSeqSize);
          break;
        case SUBLIST:
          builder.append("SubList{").append(expectedSeqSize).append(',').append(Arrays.toString(parentOffsets)).append(',').append(Arrays.toString(expectedParentSizes));
          break;
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
      return builder.append('}').toString();
    }
    void verifyStructuralIntegrity(){
      Assertions.assertEquals(expectedSeqSize,seq.size);
      switch(nestedType){
        case LISTDEQUE:{
          if(checkedType.checked){
            Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.modCount(seq));
          }
          var head=seq.head;
          var tail=seq.tail;
          Assertions.assertNull(head.prev);
          Assertions.assertNull(tail.next);
          var curr=head;
          for(int count=expectedSeqSize;--count>=0;){
            var next=curr.next;
            Assertions.assertSame(next.prev,curr);
            curr=next;
          }
          Assertions.assertSame(curr,tail);
          break;
        }
        case SUBLIST:{
          if(checkedType.checked){
            Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCount(seq));
            if(parents.length==1){
              Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(seq));
            }else{
              Assertions.assertSame(parents[0],FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(seq));
            }
            Assertions.assertSame(parents[parents.length-1],FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.root(seq));
          }else{
            if(parents.length==1){
              Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(seq));
            }else{
              Assertions.assertSame(parents[0],FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(seq));
            }
            Assertions.assertSame(parents[parents.length-1],FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.root(seq));
          }
          int parentIndex=parents.length-1;
          ShortDblLnkSeq root=parents[parentIndex];
          var head=root.head;
          var tail=root.tail;
          Assertions.assertNull(head.prev);
          Assertions.assertNull(tail.next);
          var curr=head;
          for(;;){
            Assertions.assertEquals(expectedParentSizes[parentIndex],parents[parentIndex].size);
            int currParentOffset=parentOffsets[parentIndex];
            //TODO verify fields
            for(;--currParentOffset>=0;){
              var next=curr.next;
              Assertions.assertSame(next.prev,curr);
              curr=next;
            }
            if(checkedType.checked){
              Assertions.assertEquals(expectedParentModCounts[parentIndex],parentIndex==parents.length-1?FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.modCount(parents[parentIndex]):FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.modCount(parents[parentIndex]));
            }
            if(parentIndex==0){
              Assertions.assertSame(seq.head,curr);
              break;
            }else{
              Assertions.assertSame(parents[--parentIndex].head,curr);
            }
            if(checkedType.checked){
              if(parentIndex==parents.length-2){
                Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(parents[parentIndex]));
              }else{
                Assertions.assertSame(parents[parentIndex+1],FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.parent(parents[parentIndex]));
              }
              Assertions.assertSame(parents[parents.length-1],FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.root(parents[parentIndex]));
            }else{
              if(parentIndex==parents.length-2){
                Assertions.assertNull(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(parents[parentIndex]));
              }else{
                Assertions.assertSame(parents[parentIndex+1],FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.parent(parents[parentIndex]));
              }
              Assertions.assertSame(parents[parents.length-1],FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.root(parents[parentIndex]));
            }
          }
          for(int count=expectedSeqSize;--count>=0;){
            var next=curr.next;
            Assertions.assertSame(next.prev,curr);
            curr=next;
          }
          Assertions.assertSame(seq.tail,curr);
          int currChildSize=expectedSeqSize;
          for(;;){
            int currParentSize=expectedParentSizes[parentIndex];
            int currPostAlloc=currParentSize-currChildSize-parentOffsets[parentIndex];
            for(;--currPostAlloc>=0;){
              var next=curr.next;
              Assertions.assertSame(next.prev,curr);
              curr=next;
            }
            Assertions.assertSame(parents[parentIndex].tail,curr);
            if(++parentIndex==parents.length){
              break;
            }
            currChildSize=currParentSize;
          }
          break;
        }
        default:
          throw new Error("Unknown nestedType "+nestedType);
      }
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        ++expectedParentModCounts[i];
      }
    }
    void verifyBatchRemove(int numRemoved){
      expectedSeqSize-=numRemoved;
      for(int i=0,bound=parents.length;i<bound;++i){
        expectedParentSizes[i]-=numRemoved;
      }
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      switch(nestedType){
        case LISTDEQUE:
          if(checkedType.checked){
            FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i){
        --expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
    private int getRootPostAlloc(){
      var expectedParentSizes=this.expectedParentSizes;
      switch(expectedParentSizes.length){
        default:
          return expectedParentSizes[expectedParentSizes.length-1]-expectedParentSizes[expectedParentSizes.length-2]-parentOffsets[parentOffsets.length-1];
        case 1:
          return expectedParentSizes[0]-expectedSeqSize-parentOffsets[0];
        case 0:
         return 0;
      }
    }
    private static class DblLnkSeqVerificationItr extends SequenceVerificationItr{
      ShortDblLnkNode curr;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(ShortDblLnkNode curr,SeqMonitor seqMonitor){
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,ShortInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.getRootPostAlloc();i<bound;++i){
          verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(short val){
        ShortDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        Assertions.assertEquals(val,curr.val);
      }
      @Override void reverseAndVerifyIndex(ShortInputTestArgType inputArgType,int val){
        inputArgType.verifyVal(val,(curr=curr.prev).val);
      }
      @Override void verifyIndexAndIterate(ShortInputTestArgType inputArgType,int val){
        ShortDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        inputArgType.verifyVal(val,curr.val);
      }
      @Override SequenceVerificationItr getOffset(int i){
        return new DblLnkSeqVerificationItr(i<0?ShortDblLnkNode.uncheckedIterateDescending(this.curr,i):ShortDblLnkNode.iterateAscending(this.curr,i),seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        ShortDblLnkNode curr;
        this.curr=i<0?ShortDblLnkNode.uncheckedIterateDescending(this.curr,i):ShortDblLnkNode.iterateAscending(this.curr,i);
        return this;
      }
      @Override public boolean equals(Object val){
        return val==this || (val instanceof DblLnkSeqVerificationItr && ((DblLnkSeqVerificationItr)val).curr==this.curr);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
          verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(ShortInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      ShortDblLnkNode getNewCurr(){
        if(expectedCurr!=null && expectedCurr!=seq.tail){
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractShortSeqMonitor.AbstractItrMonitor{
      ShortDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfShort itr,ShortDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ShortConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      ShortDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.next:null;
      }
      void verifyNext(int expectedVal,ShortOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextShort();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfShort)seq).descendingIterator(),seq.tail);
      }
      ShortDblLnkNode getNewCurr(){
        return expectedCurr!=null?expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      ShortDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,ShortOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextShort();
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
      void remove(){
        super.remove();
        this.expectedLastRet=null;
        ++expectedItrModCount;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ShortConsumer)action);
        }
        if(expectedCurrIndex>0){
          this.expectedLastRet=seq.head;
          this.expectedCurrIndex=0;
          this.expectedCurr=null;
        }
      }
    }
    class UncheckedBidirectionalSubItrMonitor extends UncheckedBidirectionalItrMonitor{
      UncheckedBidirectionalSubItrMonitor(){
        super();
      }
      UncheckedBidirectionalSubItrMonitor(int index){
        super(index);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        ShortDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ShortConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=newLastRet.next;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractShortSeqMonitor.AbstractItrMonitor{
      ShortDblLnkNode expectedCurr;
      int expectedCurrIndex;
      ShortDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfShort itr,int currIndex){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurrIndex=currIndex;
        int parentSize=expectedSeqSize;
        if((parentSize-=currIndex)<=currIndex){
          switch(parentSize){
          case 0:
            this.expectedCurr=null;
            break;
          case 1:
            this.expectedCurr=seq.tail;
            break;
          default:
            this.expectedCurr=ShortDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=ShortDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      ShortDblLnkNode getNewNextNode(){
        return expectedCurr==null?null:expectedCurr.next;
      }
      ShortDblLnkNode getNewPrevNode(){
        return expectedCurr==null?seq.tail:expectedCurr.prev;
      }
      void iterateReverse(){
        ShortDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfShort)itr).previousShort();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,ShortOutputTestArgType outputType){
        ShortDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfShort)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,ShortInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfShort)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ShortConsumer)action);
        }
        if(expectedCurr!=null){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=expectedSeqSize;
          this.expectedCurr=null;
        }
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      void verifyNext(int expectedVal,ShortOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextShort();
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void remove(){
        int newCurrIndex=this.expectedCurrIndex;
        if(expectedLastRet!=null && expectedLastRet.next==expectedCurr){
          --newCurrIndex;
        }
        itr.remove();
        verifyRemoval();
        this.expectedCurrIndex=newCurrIndex;
        this.expectedLastRet=null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalSubItrMonitor extends CheckedSubAscendingItrMonitor{
      CheckedBidirectionalSubItrMonitor(){
        super();
      }
      CheckedBidirectionalSubItrMonitor(int index){
        super(index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      ShortDblLnkNode getNewNextNode(){
        return expectedCurr!=null &&expectedCurrIndex<expectedSeqSize?expectedCurr.next:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,ShortInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      ShortDblLnkNode getNewPrevNode(){
        return expectedCurrIndex!=0?expectedCurr==null?seq.tail:expectedCurr.prev:null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfShort itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ShortDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
      }
      void remove(){
        super.remove();
        ++expectedItrModCount;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        int currIndex=this.expectedCurrIndex;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ShortConsumer)action);
        }
        if(currIndex<parentSize){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
    }
  }
}
