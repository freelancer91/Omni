package omni.impl.seq;
import omni.function.ByteConsumer;
import omni.function.BytePredicate;
import java.util.Arrays;
import java.util.function.Consumer;
import java.io.IOException;
import omni.util.TypeConversionUtil;
import org.junit.jupiter.api.Assertions;
import omni.impl.ByteInputTestArgType;
import omni.impl.ByteOutputTestArgType;
import org.junit.jupiter.params.provider.Arguments;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import omni.util.OmniArray;
import omni.impl.ByteDblLnkNode;
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
import omni.impl.seq.AbstractByteSeqMonitor.CheckedType;
import omni.impl.seq.AbstractByteSeqMonitor.PreModScenario;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceLocation;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceContentsScenario;
import omni.impl.seq.AbstractByteSeqMonitor.IterationScenario;
import omni.impl.seq.AbstractByteSeqMonitor.ItrRemoveScenario;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredFunctionGen;
import omni.impl.seq.AbstractByteSeqMonitor.MonitoredRemoveIfPredicateGen;
import java.nio.file.Files;
import omni.impl.seq.AbstractByteSeqMonitor.SequenceVerificationItr;
import omni.api.OmniCollection;
import omni.api.OmniListIterator;
import java.util.ArrayList;
import omni.api.OmniDeque;
@SuppressWarnings({"rawtypes","unchecked"})
@Tag("DblLnkSeq")
@Execution(ExecutionMode.CONCURRENT)
public class ByteDblLnkSeqTest{
 static enum NestedType{
    LISTDEQUE(true),
    SUBLIST(false);
    final boolean rootType;
    NestedType(boolean rootType){
      this.rootType=rootType;
    }
  }
  private static class SeqMonitor extends AbstractByteSeqMonitor<ByteDblLnkSeq>{
    private static final ByteDblLnkSeq[] EMPTY_PARENTS=new ByteDblLnkSeq[0];
    final NestedType nestedType;
    final ByteDblLnkSeq[] parents;
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
            this.seq=new ByteDblLnkSeq.CheckedList();
          }
          else
          {
            this.seq=new ByteDblLnkSeq.UncheckedList();
          }
          this.parentPreAlloc=0;
          this.parentPostAlloc=0;
          this.parents=EMPTY_PARENTS;
          this.parentOffsets=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentModCounts=OmniArray.OfInt.DEFAULT_ARR;
          this.expectedParentSizes=OmniArray.OfInt.DEFAULT_ARR;
          break;
        case SUBLIST:
          var rootHead=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MIN_VALUE));
          var currHead=rootHead;
          for(int i=1;i<10;++i)
          {
            currHead=currHead.next=new ByteDblLnkNode(currHead,TypeConversionUtil.convertTobyte(Integer.MIN_VALUE+i));
          }
          var rootTail=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MAX_VALUE));
          var currTail=rootTail;
          for(int i=1;i<10;++i)
          {
            currTail=currTail.prev=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MAX_VALUE-i),currTail);
          }
          currHead.next=currTail;
          currTail.prev=currHead;
          ByteDblLnkSeq root;
          if(checkedType.checked)
          {
            root=new ByteDblLnkSeq.CheckedList(rootHead,20,rootTail);
          }
          else
          {
            root=new ByteDblLnkSeq.UncheckedList(rootHead,20,rootTail);
          }
          this.parents=new ByteDblLnkSeq[2];
          this.parents[1]=root;
          this.parents[0]=(ByteDblLnkSeq)root.subList(5,15);
          this.seq=(ByteDblLnkSeq)parents[0].subList(5,5);
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
          this.seq=new ByteDblLnkSeq.CheckedList();
        }
        else
        {
          this.seq=new ByteDblLnkSeq.UncheckedList();
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
        ByteDblLnkNode rootHead=null;
        ByteDblLnkNode rootTail=null;
        var currHead=rootHead;
        var currTail=rootTail;
        if(totalPreAlloc!=0)
        {
          rootHead=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MIN_VALUE));
          currHead=rootHead;
          for(int i=1;i<totalPreAlloc;++i)
          {
            currHead=currHead.next=new ByteDblLnkNode(currHead,TypeConversionUtil.convertTobyte(Integer.MIN_VALUE+i));
          }
        }
        if(totalPostAlloc!=0)
        {
          rootTail=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MAX_VALUE));
          currTail=rootTail;
          for(int i=1;i<totalPreAlloc;++i)
          {
            currTail=currTail.prev=new ByteDblLnkNode(TypeConversionUtil.convertTobyte(Integer.MAX_VALUE-i),currTail);
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
        ByteDblLnkSeq root;
        int rootSize=totalPreAlloc+totalPostAlloc;
        if(checkedType.checked)
        {
          root=new ByteDblLnkSeq.CheckedList(rootHead,rootSize,rootTail);
        }
        else
        {
          root=new ByteDblLnkSeq.UncheckedList(rootHead,rootSize,rootTail);
        }
        this.parents=new ByteDblLnkSeq[parentPreAllocs.length];
        this.parentOffsets=new int[parentPreAllocs.length];
        this.expectedParentModCounts=new int[parentPreAllocs.length];
        this.expectedParentSizes=new int[parentPreAllocs.length];
        this.expectedParentSizes[parentPreAllocs.length-1]=rootSize;
        this.parents[parentPreAllocs.length-1]=root;
        for(int i=parentPreAllocs.length-1;--i>=0;)
        {
          int fromIndex=parentPreAllocs[i+1];
          int toIndex=expectedParentSizes[i+1]-parentPostAllocs[i+1];
          parents[i]=(ByteDblLnkSeq)parents[i+1].subList(fromIndex,toIndex);
          parentOffsets[i]=fromIndex;
          expectedParentSizes[i]=toIndex-fromIndex;
        }
        int fromIndex=parentPreAllocs[0];
        int toIndex=expectedParentSizes[0]-parentPostAllocs[0];
        this.seq=(ByteDblLnkSeq)parents[0].subList(fromIndex,toIndex);
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
    AbstractByteSeqMonitor.AbstractItrMonitor getDescendingItrMonitor(){
      if(checkedType.checked)
      {
        return new CheckedDescendingItrMonitor();
      }
      else
      {
        return new UncheckedDescendingItrMonitor();
      }
    }
    AbstractByteSeqMonitor.AbstractItrMonitor getListItrMonitor(){
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
    AbstractByteSeqMonitor.AbstractItrMonitor getListItrMonitor(int index){
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
      ByteDblLnkNode curr;
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
         ByteInputTestArgType.ARRAY_TYPE.verifyVal(expectedVal,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
    SequenceVerificationItr verifyPreAlloc(){
      int rootPreAlloc;
      ByteDblLnkNode curr;
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
        ByteInputTestArgType.ARRAY_TYPE.verifyVal(v,curr.val);
      }
      return new DblLnkSeqVerificationItr(curr,this);
    }
     void illegalAdd(PreModScenario preModScenario){
       switch(preModScenario)
       {
         case ModSeq:
           ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
           verifyAddition();
           break;
         case ModParent:
           int index;
           ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-2],0);
           ++expectedParentSizes[index];
           ++expectedParentModCounts[index];
           ++expectedParentSizes[++index];
           ++expectedParentModCounts[++index];
           break;
         case ModRoot:
           ByteInputTestArgType.ARRAY_TYPE.callCollectionAdd(parents[index=parents.length-1],0);
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
      var builder=new StringBuilder("ByteDblLnkSeq").append(checkedType.checked?"Checked":"Unchecked");
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
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedStack.modCount(root));
            }
            break;
          case LIST:
            if(checkedType.checked){
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
            }
            break;
          case SUBLIST:
            OmniList.OfByte actualSeqParent;
            ByteArrSeq actualSeqRoot;
            int actualSeqSize;
            OmniList.OfByte actualParentParent;
            ByteArrSeq  actualParentRoot;
            int actualParentSize;
            if(checkedType.checked){
              actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(seq);
              actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(seq);
              actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.parent(parent);
              actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.size(parent);
              Assertions.assertEquals(expectedSeqModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(seq));
              Assertions.assertEquals(expectedParentModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.modCount(parent));
              Assertions.assertEquals(expectedRootModCount,FieldAndMethodAccessor.ByteArrSeq.CheckedList.modCount(root));
            }else{
              actualSeqParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(seq);
              actualSeqRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(seq);
              actualSeqSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(seq);
              actualParentParent=(OmniList.OfByte)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.parent(parent);
              actualParentRoot=(ByteArrSeq)FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.root(parent);
              actualParentSize=FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.size(parent);
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
        Assertions.assertEquals(expectedRootSize+parentPreAlloc+parentPostAlloc+rootPreAlloc+rootPostAlloc,FieldAndMethodAccessor.ByteArrSeq.size(root));
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
    void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfByte clone){
    //TODO
    throw new UnsupportedOperationException();
    /*
      boolean retVal;
      if(functionCallType==FunctionCallType.Boxed){
        retVal=seq.removeIf((Predicate)pred);
      }
      else
      {
        retVal=seq.removeIf((BytePredicate)pred);
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
          Assertions.assertEquals(seqItr.nextByte(),cloneItr.nextByte());
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
            FieldAndMethodAccessor.ByteArrSeq.CheckedList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedList.writeObject(seq,oos);
          }
          break;
        case STACK:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedStack.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.writeObject(seq,oos);
          }
          break;
        case SUBLIST:
          if(checkedType.checked){
            FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.writeObject(seq,oos);
          }else{
            FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.writeObject(seq,oos);
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
            return FieldAndMethodAccessor.ByteArrSeq.CheckedList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedList.readObject(seq,ois);
          }
        case STACK:
          if(checkedType.checked){
            return FieldAndMethodAccessor.ByteArrSeq.CheckedStack.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedStack.readObject(seq,ois);
          }
        case SUBLIST:
          if(checkedType.checked){
            return FieldAndMethodAccessor.ByteArrSeq.CheckedSubList.readObject(seq,ois);
          }else{
            return FieldAndMethodAccessor.ByteArrSeq.UncheckedSubList.readObject(seq,ois);
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
      ByteDblLnkNode curr;
      final SeqMonitor seqMonitor;
      private DblLnkSeqVerificationItr(ByteDblLnkNode curr,SeqMonitor seqMonitor)
      {
        this.seqMonitor=seqMonitor;
        this.curr=curr;
      }
      SequenceVerificationItr verifyNaturalAscending(int v,ByteInputTestArgType inputArgType,int length){
        return verifyAscending(v,inputArgType,length);
      }
      @Override SequenceVerificationItr verifyPostAlloc(int expectedVal){
        for(int i=0,bound=seqMonitor.parentPostAlloc+seqMonitor.getRootPostAlloc();i<bound;++i){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,expectedVal);
        }
        return this;
      }
      @Override void verifyLiteralIndexAndIterate(byte val){
        ByteDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        Assertions.assertEquals(val,curr.val);
      }
      @Override void verifyIndexAndIterate(ByteInputTestArgType inputArgType,int val){
        ByteDblLnkNode curr;
        this.curr=(curr=this.curr).next;
        inputArgType.verifyVal(val,curr.val);
      }
      @Override SequenceVerificationItr getPositiveOffset(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        return new DblLnkSeqVerificationItr(ByteDblLnkNode.iterateAscending(this.curr,i),seqMonitor);
      }
      @Override SequenceVerificationItr skip(int i){
        if(i<0){
          throw new Error("offset cannot be negative: "+i);
        }
        this.curr=ByteDblLnkNode.iterateAscending(this.curr,i);
        return this;
      }
      @Override public boolean equals(Object val){
        final DblLnkSeqVerificationItr that;
        return val==this || (val instanceof DblLnkSeqVerificationItr && (that=(DblLnkSeqVerificationItr)val).curr==this.curr);
      }
      @Override SequenceVerificationItr verifyRootPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc;i<rootPostAlloc;++i,++v){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
      @Override SequenceVerificationItr verifyParentPostAlloc(){
        for(int i=0,rootPostAlloc=seqMonitor.getRootPostAlloc(),v=Integer.MAX_VALUE-rootPostAlloc-seqMonitor.parentPostAlloc;i<seqMonitor.parentPostAlloc;++i,++v){
          verifyIndexAndIterate(ByteInputTestArgType.ARRAY_TYPE,v);
        }
        return this;
      }
    }
    class UncheckedSubAscendingItrMonitor extends UncheckedAscendingItrMonitor
    {
      UncheckedSubAscendingItrMonitor(){
        super();
      }
      ByteDblLnkNode getNewCurr(){
        if(expectedCurr!=null)
        {
          if(expectedCurr!=seq.tail){
            return expectedCurr.next;
          }
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedAscendingItrMonitor extends AbstractByteSeqMonitor.AbstractItrMonitor{
      ByteDblLnkNode expectedCurr;
      UncheckedAscendingItrMonitor(){
        this(ItrType.Itr,seq.iterator(),seq.head);
      }
      private UncheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfByte itr,ByteDblLnkNode expectedCurr){
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
          itr.forEachRemaining((ByteConsumer)action);
        }
        this.expectedCurr=null;
      }
      SeqMonitor getSeqMonitor(){
        return SeqMonitor.this;
      }
      ByteDblLnkNode getNewCurr(){
        if(expectedCurr!=null)
        {
          return expectedCurr.next;
        }
        return null;
      }
      void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedCurr=newCurr;
      }
      void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextByte();
        expectedCurr=newCurr;
      }
      void remove(){
        itr.remove();
        verifyRemoval();
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.AscendingItr.parent(itr),seq);
      }
    }
    class UncheckedDescendingItrMonitor extends UncheckedAscendingItrMonitor{
      UncheckedDescendingItrMonitor(){
        super(ItrType.DescendingItr,((OmniDeque.OfByte)seq).descendingIterator(),seq.tail);
      }
      ByteDblLnkNode getNewCurr(){
        ByteDblLnkNode newCurr=null;
        if(expectedCurr!=null)
        {
          newCurr=expectedCurr.prev;
        }
        return newCurr;
      }
      void verifyIteratorState(){
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.DescendingItr.parent(itr),seq);
      }
    }
    class CheckedDescendingItrMonitor extends UncheckedDescendingItrMonitor{
      ByteDblLnkNode expectedLastRet;
      int expectedCurrIndex;
      CheckedDescendingItrMonitor(){
        super();
        this.expectedCurrIndex=expectedSeqSize;
      }
      void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
        var newCurr=getNewCurr();
        outputType.verifyItrNext(itr,expectedVal);
        expectedLastRet=expectedCurr;
        expectedCurr=newCurr;
        --expectedCurrIndex;
      }
       void iterateForward(){
        var newCurr=getNewCurr();
        itr.nextByte();
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
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.lastRet(itr),expectedLastRet);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.DescendingItr.parent(itr),seq);
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed)
        {
          itr.forEachRemaining((Consumer)action);
        }
        else
        {
          itr.forEachRemaining((ByteConsumer)action);
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
        ByteDblLnkNode newLastRet=seq.tail;
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ByteConsumer)action);
        }
        if(expectedCurrIndex<parentSize){
          this.expectedLastRet=newLastRet;
          this.expectedCurrIndex=parentSize;
          this.expectedCurr=newLastRet.next;
        }
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class UncheckedBidirectionalItrMonitor extends AbstractByteSeqMonitor.AbstractItrMonitor{
      ByteDblLnkNode expectedCurr;
      int expectedCurrIndex;
      ByteDblLnkNode expectedLastRet;
      UncheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),expectedSeqModCount);
        this.expectedCurr=seq.head;
        this.expectedCurrIndex=0;
      }
      UncheckedBidirectionalItrMonitor(ItrType itrType,OmniIterator.OfByte itr,int currIndex)
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
            this.expectedCurr=ByteDblLnkNode.uncheckedIterateDescending(seq.tail,parentSize-1);
          }
        }else{
          this.expectedCurr=ByteDblLnkNode.iterateAscending(seq.head,currIndex);
        }
      }
      UncheckedBidirectionalItrMonitor(int index){
        this(ItrType.ListItr,seq.listIterator(index),index);
      }
      ByteDblLnkNode getNewNextNode(){
        if(expectedCurr==null)
        {
          return null;
        }
        return expectedCurr.next;
      }
      ByteDblLnkNode getNewPrevNode(){
        if(expectedCurr==null){
          return seq.tail;
        }
        return expectedCurr.prev;
      }
      void iterateReverse(){
        ByteDblLnkNode newLastRet=getNewPrevNode();
        ((OmniListIterator.OfByte)itr).previousByte();
        this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void verifyPrevious(int expectedVal,ByteOutputTestArgType outputType){
        ByteDblLnkNode newLastRet=getNewPrevNode();
        outputType.verifyItrPrevious(((OmniListIterator.OfByte)itr),expectedVal);
         this.expectedLastRet=newLastRet;
        this.expectedCurr=newLastRet;
        --this.expectedCurrIndex;
      }
      void add(int v,ByteInputTestArgType inputArgType){
        inputArgType.callListItrAdd(((OmniListIterator.OfByte)itr),v);
        ++this.expectedCurrIndex;
        this.expectedLastRet=null;
        verifyAddition();
      }
      void forEachRemaining(MonitoredConsumer action,FunctionCallType functionCallType){
        if(functionCallType==FunctionCallType.Boxed){
          itr.forEachRemaining((Consumer)action);
        }else
        {
          itr.forEachRemaining((ByteConsumer)action);
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
      void verifyNext(int expectedVal,ByteOutputTestArgType outputType){
        var newCurr=getNewNextNode();
        outputType.verifyItrNext(itr,expectedVal);
        this.expectedLastRet=expectedCurr;
        this.expectedCurr=newCurr;
        ++this.expectedCurrIndex;
      }
      void iterateForward(){
        var newCurr=getNewNextNode();
        itr.nextByte();
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
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.UncheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
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
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedSubAscendingItrMonitor extends CheckedBidirectionalItrMonitor{
      CheckedSubAscendingItrMonitor(){
        super();
      }
      CheckedSubAscendingItrMonitor(int index){
        super(index);
      }
      ByteDblLnkNode getNewNextNode(){
        if(expectedCurr!=null &&expectedCurrIndex<expectedSeqSize)
        {
          return expectedCurr.next;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedSubList.AscendingItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedBidirectionalItrMonitor extends CheckedAscendingItrMonitor{
      CheckedBidirectionalItrMonitor(int index){
        super(ItrType.ListItr,seq.listIterator(index),index);
      }
      CheckedBidirectionalItrMonitor(){
        super(ItrType.ListItr,seq.listIterator(),0);
      }
      void add(int v,ByteInputTestArgType inputArgType){
        super.add(v,inputArgType);
        ++expectedItrModCount;
      }
      ByteDblLnkNode getNewPrevNode(){
        if(expectedCurrIndex!=0){
          if(expectedCurr==null){
            return seq.tail;
          }
          return expectedCurr.prev;
        }
        return null;
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.BidirectionalItr.lastRet(itr),expectedLastRet);
      }
    }
    class CheckedAscendingItrMonitor extends UncheckedBidirectionalItrMonitor{
      CheckedAscendingItrMonitor(){
        super(ItrType.Itr,seq.iterator(),0);
      }
      CheckedAscendingItrMonitor(ItrType itrType,OmniIterator.OfByte itr,int index){
        super(itrType,itr,index);
      }
      void verifyIteratorState(){
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.currIndex(itr),expectedCurrIndex);
        Assertions.assertEquals(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.modCount(itr),expectedItrModCount);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.parent(itr),seq);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.curr(itr),expectedCurr);
        Assertions.assertSame(FieldAndMethodAccessor.ByteDblLnkSeq.CheckedList.AscendingItr.lastRet(itr),expectedLastRet);
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
          itr.forEachRemaining((ByteConsumer)action);
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
