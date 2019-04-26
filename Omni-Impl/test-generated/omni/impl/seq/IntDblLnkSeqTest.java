package omni.impl.seq;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.IntInputTestArgType;
import omni.impl.IntOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.IntDblLnkNode;
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
import omni.impl.seq.AbstractIntSeqMonitor.CheckedType;
import omni.impl.seq.AbstractIntSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.AbstractIntSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractIntSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractIntSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractIntSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class IntDblLnkSeqTest{
 static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractIntSeqMonitor<IntDblLnkSeq>{
    private static final IntDblLnkSeq[] EMPTY_PARENTS=new IntDblLnkSeq[0];
    final NestedType nestedType;
    final IntDblLnkSeq[] parents;
    final int[] parentOffsets;
    final int[] expectedParentModCounts;
    final int[] expectedParentSizes;
    final int parentPreAlloc;
    final int parentPostAlloc;
    SeqMonitor(CheckedType checkedType,NestedType nestedType)
    {
      super(checkedType);
      this.nestedType=nestedType;
      switch(nestedType)
      {
        case LISTDEQUE:
          if(checkedType.checked)
          {
            this.seq=new IntDblLnkSeq.CheckedList();
          }
          else
          {
            this.seq=new IntDblLnkSeq.UncheckedList();
          }
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i)
          {
            currHead=currHead.next=new IntDblLnkNode(currHead,TypeConversionUtil.convertToint(Integer.MIN_VALUE+i));
          }
          var rootTail=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i)
          {
            currTail=currTail.prev=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          IntDblLnkSeq root;
          if(checkedType.checked)
          {
            root=new IntDblLnkSeq.CheckedList(rootHead,20,rootTail);
          }
          else
          {
            root=new IntDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          }
          this.parents=new IntDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(IntDblLnkSeq)root.subList(5,15);
          this.seq=(IntDblLnkSeq)parents[0].subList(5,5);
          this.parentOffsets=new int[]{5,5};
          this.expectedParentSizes=new int[]{10,20};
          this.expectedParentModCounts=new int[]{0,0};
          this.parentPreAlloc=5;
          this.parentPostAlloc=5;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SeqMonitor(CheckedType checkedType,int[] parentPreAllocs,int[] parentPostAllocs)
    {
      super(checkedType);
      Assertions.assertEquals(parentPreAllocs.length,parentPostAllocs.length);
      if(parentPreAllocs.length==0)
      {
        this.parentPreAlloc=0;
        this.parentPostAlloc=0;
        this.nestedType=NestedType.LISTDEQUE;
        if(checkedType.checked)
        {
          this.seq=new IntDblLnkSeq.CheckedList();
        }
        else
        {
          this.seq=new IntDblLnkSeq.UncheckedList();
        }
        this.parents=EMPTY_PARENTS;
        this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
        this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
      }
      else
      {
        this.nestedType=NestedType.SUBLIST;
        int totalPreAlloc=0;
        int totalPostAlloc=0;
        for(int i=0;i<parentPreAllocs.length;++i)
        {
          totalPreAlloc+=parentPreAllocs[i];
          totalPostAlloc+=parentPostAllocs[i];
        }
        this.parentPreAlloc=totalPreAlloc-parentPreAllocs[parentPreAllocs.length-1];
        this.parentPostAlloc=totalPostAlloc-parentPostAllocs[parentPostAllocs.length-1];
        IntDblLnkNode rootHead=null;
        IntDblLnkNode rootTail=null;
        var currHead=rootHead;
        var currTail=rootTail;
        if(totalPreAlloc!=0)
        {
          rootHead=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MIN_VALUE));
          currHead=rootHead;
          for(int i=1;i<totalPreAlloc;++i)
          {
            currHead=currHead.next=new IntDblLnkNode(currHead,TypeConversionUtil.convertToint(Integer.MIN_VALUE+i));
          }
        }
        if(totalPostAlloc!=0)
        {
          rootTail=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MAX_VALUE));
          currTail=rootTail;
          for(int i=1;i<totalPreAlloc;++i)
          {
            currTail=currTail.prev=new IntDblLnkNode(TypeConversionUtil.convertToint(Integer.MAX_VALUE-i),currTail);
          }
        }
        if(currHead!=null)
        {
          if(currTail!=null)
          {
            currHead.next=currTail;
            currTail.prev=currHead;
          }
          else
          {
            rootTail=currHead;
          }
        }
        else
        {
          if(currTail!=null)
          {
            rootHead=currTail;
          }
        }
        IntDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        if(checkedType.checked)
        {
          root=new IntDblLnkSeq.CheckedList(rootHead,rootSize,rootTail);
        }
        else
        {
          root=new IntDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        }
        this.parents=new IntDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length-1;--i>=0;)
        {
          int fromIndex=parentPreAllocs[i+1];
          int toIndex=expectedParentSizes[i+1]-parentPostAllocs[i+1];
          parents[i]=(IntDblLnkSeq)parents[i+1].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(IntDblLnkSeq)parents[0].subList(fromIndex,toIndex);
        this.expectedSeqSize=toIndex-fromIndex;
        Assertions.assertEquals(0,this.expectedSeqSize);
      }
    }
    AbstractItrMonitor getItrMonitor()
    {
      switch(nestedType)
      {
        case LISTDEQUE:
          if(checkedType.checked)
          {
            return new CheckedAscendingItrMonitor();
          }
          else
          {
            return new UncheckedAscendingItrMonitor();
          }
        case SUBLIST:
          if(checkedType.checked)
          {
            return new CheckedSubAscendingItrMonitor();
          }
          else
          {
            return  new UncheckedSubAscendingItrMonitor();
          }
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractIntSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      if(checkedType.checked)
      {
        return new CheckedDescendingItrMonitor();
      }
      else
      {
        return new UncheckedDescendingItrMonitor();
      }
    }
    AbstractIntSeqMonitor.AbstractItrMonitor getListItrMonitor(){
      switch(nestedType)
      {
        case LISTDEQUE:
          if(checkedType.checked)
          {
            return new CheckedBidirectionalItrMonitor();
          }
          else
          {
            return new UncheckedBidirectionalItrMonitor();
          }
        case SUBLIST:
          if(checkedType.checked)
          {
            return new CheckedBidirectionalSubItrMonitor();
          }
          else
          {
            return  new UncheckedBidirectionalSubItrMonitor();
          }
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    AbstractIntSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
      switch(nestedType)
      {
        case LISTDEQUE:
          if(checkedType.checked)
          {
            return new CheckedBidirectionalItrMonitor(index);
          }
          else
          {
            return new UncheckedBidirectionalItrMonitor(index);
          }
        case SUBLIST:
          if(checkedType.checked)
          {
            return new CheckedBidirectionalSubItrMonitor(index);
          }
          else
          {
            return  new UncheckedBidirectionalSubItrMonitor(index);
          }
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    SequenceVerificationItr verifyPreAlloc(int expectedVal){
      int rootPreAlloc;
      IntDblLnkNode curr;
      switch(nestedType)
      {
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
         IntInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      IntDblLnkNode curr;
      switch(nestedType)
      {
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
        IntInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
     void illegalAdd(PreModScenario preModScenario){
       switch(preModScenario)
       {
         case ModSeq:
           IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
           verifyAddition();
           break;
         case ModParent:
           int index;
           IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
           ++expectedParentSizes[index];
           ++expectedParentModCounts[index];
           ++expectedParentSizes[++index];
           ++expectedParentModCounts[++index];
           break;
         case ModRoot:
           IntInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
           ++expectedParentSizes[index];
           ++expectedParentModCounts[index];
         case NoMod:
           break;
         default:
           throw new Error("Unknown preModScenario "+preModScenario);
       }
    }
    void verifyAddition()
    {
      ++expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i)
      {
        ++expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
    public String toString(){
      var builder=new StringBuilder("IntDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
      switch(nestedType)
      {
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
    /*
        switch(nestedType){
          case STACK:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfInt actualSeqParent;
            IntArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfInt actualParentParent;
            IntArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.IntArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.IntArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.IntArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfInt)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(IntArrSeq)FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.size(parent);
            }
            Assertions.assertSame(root,actualSeqRoot);
            Assertions.assertSame(root,actualParentRoot);
            Assertions.assertSame(parent,actualSeqParent);
            Assertions.assertNull(actualParentParent);
            Assertions.assertEquals(expectedSeqSize,actualSeqSize);
            Assertions.assertEquals(expectedParentSize+parentPreAlloc+parentPostAlloc,actualParentSize);
            break;
          default:
            throw new Error("Unknown nestedType "+nestedType);
        }
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.IntArrSeq.size(root));
      */
      //TODO
       throw new UnsupportedOperationException();
    }
    void verifyFunctionalModification(){
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i)
      {
        ++expectedParentModCounts[i];
      }
    }
    void clear(){
    int seqSize=expectedSeqSize;
      seq.clear();
      if(seqSize!=0){
        expectedSeqSize=0;
        ++expectedSeqModCount;
        for(int i=0,bound=expectedParentModCounts.length;i<bound;++i)
        {
          expectedParentSizes[i]-=seqSize;
          ++expectedParentModCounts[i];
        }
      }
    }
    void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfInt clone){
    //TODO
    throw new UnsupportedOperationException();
    /*
      boolean retVal;
      if(functionCallType==FunctionCallType.Boxed){
        retVal=seq.removeIf((Predicate)pred);
      }
      else
      {
        retVal=seq.removeIf((IntPredicate)pred);
      }
      if(retVal){
        ++expectedSeqModCount;
        ++expectedParentModCount;
        ++expectedRootModCount;
        int numRemoved;
        numRemoved=pred.numRemoved;
        for(var removedVal:pred.removedVals){
          Assertions.assertFalse(seq.contains(removedVal));
        }
        expectedSeqSize-=numRemoved;
        expectedParentSize-=numRemoved;
        expectedRootSize-=numRemoved;
        if(expectedNumRemoved!=-1){
          Assertions.assertEquals(expectedNumRemoved,numRemoved);
        }
      }else{
        Assertions.assertEquals(expectedSeqSize,clone.size());
        var seqItr=seq.iterator();
        var cloneItr=clone.iterator();
        for(int i=0;i<expectedSeqSize;++i){
          Assertions.assertEquals(seqItr.nextInt(),cloneItr.nextInt());
        }
      }
      verifyStructuralIntegrity();
      */
    }
    void writeObject(ObjectOutputStream oos) throws IOException{
      //TODO
      throw new UnsupportedOperationException();
    /*
      switch(nestedType){
        case LIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.IntArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.writeObject(seq,oos);
          }
          break;
        default:
          throw new Error("unknown nested type "+nestedType);
      }
      */
    }
    Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException{
      //TODO
      throw new UnsupportedOperationException();
    /*
      switch(nestedType){
        case LIST:
          if(checkedType.checked){
            return FieldAndMethodAccessor.IntArrSeq.CheckedList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.IntArrSeq.UncheckedList.readObject(seq,ois);
          }
        case STACK:
          if(checkedType.checked){
            return FieldAndMethodAccessor.IntArrSeq.CheckedStack.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.IntArrSeq.UncheckedStack.readObject(seq,ois);
          }
        case SUBLIST:
          if(checkedType.checked){
            return FieldAndMethodAccessor.IntArrSeq.CheckedSubList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.IntArrSeq.UncheckedSubList.readObject(seq,ois);
          }
        default:
          throw new Error("unknown nested type "+nestedType);
      }
      */
    }
    void verifyRemoval(){
      --expectedSeqSize;
      ++expectedSeqModCount;
      for(int i=0,bound=expectedParentModCounts.length;i<bound;++i)
      {
        --expectedParentSizes[i];
        ++expectedParentModCounts[i];
      }
    }
     private int getRootPostAlloc(){
        var expectedParentSizes=this.expectedParentSizes;
        switch(expectedParentSizes.length)
        {
          default:
            return expectedParentSizes[expectedParentSizes.length-1]-expectedParentSizes[expectedParentSizes.length-2]-parentOffsets[parentOffsets.length-1];
          case 1:
            return expectedParentSizes[0]-expectedSeqSize-parentOffsets[0];
          case 0:
           return 0;
        }
      }
    private static class DblLnkSeqVerificationItr extends SequenceVerificationItr
    {
      IntDblLnkNode curr;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(IntDblLnkNode curr,SeqMonitor seqMonitor)
      {
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,IntInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.getRootPostAlloc();i<bound;++i){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(int val){
        IntDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        Assertions.assertEquals(val,curr.val);
      }
      @Override void verifyIndexAndIterate(IntInputTestArgType inputArgType,int val){
        IntDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        inputArgType.verifyVal(val,curr.val);
      }
      @Override SequenceVerificationItr getPositiveOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        return new DblLnkSeqVerificationItr(IntDblLnkNode.iterateAscending(this.curr,i),seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        this.curr=IntDblLnkNode.iterateAscending(this.curr,i);
        return this;
      }
      @Override public boolean equals(Object val){
        final DblLnkSeqVerificationItr that;
        return val==this || (val instanceof DblLnkSeqVerificationItr && (that=(DblLnkSeqVerificationItr)val).curr==this.curr);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(IntInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor
    {
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      IntDblLnkNode getNewCurr(){
        if(expectedCurr!=null)
        {
          if(expectedCurr!=seq.tail){
            return expectedCurr.next;
          }
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractIntSeqMonitor.AbstractItrMonitor{
      IntDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfInt itr,IntDblLnkNode expectedCurr){
        super(itrType,itr,expectedSeqModCount);
        this.expectedCurr=expectedCurr;
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed)
        {
          itr.forEachRemaining((Consumer)action);
        }
        else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      IntDblLnkNode getNewCurr(){
        if(expectedCurr!=null)
        {
          return expectedCurr.next;
        }
        return null;
      }
      void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextInt();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfInt)seq).descendingIterator(),seq.tail);
      }
      IntDblLnkNode getNewCurr(){
        IntDblLnkNode newCurr=null;
        if(expectedCurr!=null)
        {
          newCurr=expectedCurr.prev;
        }
        return newCurr;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      IntDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
       void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextInt();
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
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed)
        {
          itr.forEachRemaining((Consumer)action);
        }
        else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(expectedCurrIndex>0)
        {
          this.expectedLastRet=seq.head;
          this.expectedCurrIndex=0;
          this.expectedCurr=null;
        }
      }
    }
    class UncheckedBidirectionalSubItrMonitor extends UncheckedBidirectionalItrMonitor
    {
      UncheckedBidirectionalSubItrMonitor(){
        super();
      }
      UncheckedBidirectionalSubItrMonitor(int index){
        super(index);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        int parentSize=expectedSeqSize;
        IntDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=newLastRet.next;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractIntSeqMonitor.AbstractItrMonitor{
      IntDblLnkNode expectedCurr;
      int expectedCurrIndex;
      IntDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int currIndex)
      {
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
            this.expectedCurr=IntDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=IntDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      IntDblLnkNode getNewNextNode(){
        if(expectedCurr==null)
        {
          return null;
        }
        return expectedCurr.next;
      }
      IntDblLnkNode getNewPrevNode(){
        if(expectedCurr==null){
          return seq.tail;
        }
        return expectedCurr.prev;
      }
      void iterateReverse(){
        IntDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfInt)itr).previousInt();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,IntOutputTestArgType outputType){
        IntDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfInt)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,IntInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfInt)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((IntConsumer)action);
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
      void verifyNext(int expectedVal,IntOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextInt();
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
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
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
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      IntDblLnkNode getNewNextNode(){
        if(expectedCurr!=null &&expectedCurrIndex<expectedSeqSize)
        {
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,IntInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      IntDblLnkNode getNewPrevNode(){
        if(expectedCurrIndex!=0){
          if(expectedCurr==null){
            return seq.tail;
          }
          return expectedCurr.prev;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfInt itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.IntDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
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
          itr.forEachRemaining((IntConsumer)action);
        }
        if(currIndex<parentSize){
          this.expectedLastRet=seq.tail;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=null;
        }
      }
    }
  }
  @FunctionalInterface
  interface ArgBuilder{
    void buildArgs(Stream.Builder<Arguments> streamBuilder,NestedType nestedType,CheckedType checkedType,PreModScenario preModScenario);
    static Stream<Arguments> buildSeqArgs(ArgBuilder argBuilder){
      Stream.Builder<Arguments> streamBuilder=Stream.builder();
      for(var nestedType:NestedType.values()){
        for(var checkedType:CheckedType.values()){
          for(var preModScenario:PreModScenario.values()){
            if(preModScenario.expectedException==null || (checkedType.checked && preModScenario.appliesToSubList && !nestedType.rootType))
            {
              argBuilder.buildArgs(streamBuilder,nestedType,checkedType,preModScenario);
            }
          }
        }
      }
      return streamBuilder.build();
    }
  }
}
