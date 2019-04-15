package omni.impl.seq;
import org.junit.jupiter.api.Assertions;
import omni.function.FloatConsumer;
import omni.impl.FloatInputTestArgType;
import omni.impl.FloatOutputTestArgType;
import omni.function.FloatPredicate;
import java.util.function.Consumer;
import omni.impl.FunctionCallType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import omni.api.OmniCollection;
import java.util.function.Predicate;
import omni.impl.FloatSnglLnkNode;
@SuppressWarnings({"rawtypes","unchecked"})
class FloatSnglLnkSeqMonitor implements FloatSeqMonitor
{
  static enum NestedType
  {
    QUEUE,
    STACK;
  }
  NestedType nestedType;
  CheckedType checkedType;
  final OmniCollection.OfFloat seq;
  int expectedSeqSize;
  int expectedSeqModCount;
  FloatSnglLnkSeqMonitor(NestedType nestedType,CheckedType checkedType)
  {
    this.nestedType=nestedType;
    this.checkedType=checkedType;
    switch(nestedType)
    {
      case QUEUE:
        this.seq=checkedType.checked?new FloatSnglLnkSeq.CheckedQueue():new FloatSnglLnkSeq.UncheckedQueue();
        break;
      case STACK:
        this.seq=checkedType.checked?new FloatSnglLnkSeq.CheckedStack():new FloatSnglLnkSeq.UncheckedStack();
        break;
      default:
        throw new Error("unknown nested type "+nestedType);
    }
  }
  public int getExpectedSeqSize(){
    return this.expectedSeqSize;
  }
  public void illegalAdd(PreModScenario preModScenario)
  {
    switch(preModScenario){
      case ModSeq:
        FloatInputTestArgType.ARRAY_TYPE.callCollectionAdd(seq,0);
        ++expectedSeqModCount;
        ++expectedSeqSize;
        break;
      case NoMod:
        break;
      default:
        throw new Error("Unknown preModScenario "+preModScenario);
    }
  }
  public boolean add(int val,FloatInputTestArgType inputArgType){
    boolean ret=inputArgType.callCollectionAdd(seq,val);
    if(ret){
      ++expectedSeqSize;
      ++expectedSeqModCount;
    }
    return ret;
  }
  public void push(int val,FloatInputTestArgType inputArgType){
    inputArgType.callStackPush(seq,val);
    ++expectedSeqSize;
    ++expectedSeqModCount;
  }
  public String toString(){
    StringBuilder builder=new StringBuilder();
    builder.append("FloatSnglLnkSeq.").append(checkedType.checked?"Checked":"Unchecked");
    switch(nestedType){
      case STACK:
        builder.append("Stack");
        break;
      case QUEUE:
        builder.append("Queue");
        break;
      default:
        throw new Error("Unknown nestedType "+nestedType);
    }
    return builder.toString();
  }
  public boolean isEmpty()
  {
    return seq.isEmpty();
  }
  public void forEach(MonitoredConsumer action,FunctionCallType functionCallType){
    if(functionCallType==FunctionCallType.Boxed){
      seq.forEach((Consumer)action);
    }else
    {
      seq.forEach((FloatConsumer)action);
    }
  }
  public void clear(){
    int seqSize=expectedSeqSize;
    seq.clear();
    if(seqSize!=0){
      expectedSeqSize=0;
      ++expectedSeqModCount;
    }
  }
  public void pop(int expectedVal,FloatOutputTestArgType outputType){
    outputType.verifyStackPop(seq,expectedVal);
    --expectedSeqSize;
    ++expectedSeqModCount;
  }
  public void poll(int expectedVal,FloatOutputTestArgType outputType){
    outputType.verifyStackPoll(seq,expectedSeqSize,expectedVal);
    if(expectedSeqSize!=0){
      --expectedSeqSize;
      ++expectedSeqModCount;
    }
  }
  public void verifyRemoveIf(MonitoredRemoveIfPredicate pred,FunctionCallType functionCallType,int expectedNumRemoved,OmniCollection.OfFloat clone){
    boolean retVal;
    if(functionCallType==FunctionCallType.Boxed)
    {
      retVal=seq.removeIf((Predicate)pred);
    }
    else
    {
      retVal=seq.removeIf((FloatPredicate)pred);
    }
    if(retVal)
    {
      ++expectedSeqModCount;
      int numRemoved;
      numRemoved=pred.numRemoved;
      for(var removedVal:pred.removedVals)
      {
        Assertions.assertFalse(seq.contains(removedVal));
      }
      expectedSeqSize-=numRemoved;
      if(expectedNumRemoved!=-1){
        Assertions.assertEquals(expectedNumRemoved,numRemoved);
      }
    }else{
      Assertions.assertEquals(expectedSeqSize,clone.size());
      var seqItr=seq.iterator();
      var cloneItr=clone.iterator();
      for(int i=0;i<expectedSeqSize;++i){
        Assertions.assertEquals(seqItr.nextFloat(),cloneItr.nextFloat());
      }
    }
    verifyStructuralIntegrity();
  }
  public void writeObject(ObjectOutputStream oos) throws IOException
  {
    oos.writeObject(seq);
  }
  public Object readObject(ObjectInputStream ois) throws IOException,ClassNotFoundException
  {
    return ois.readObject();
  }
  public ItrMonitor getItrMonitor(){
    //TODO
    return null;
  }
  public SequenceVerificationItr verifyPreAlloc(){
    //TODO
    return null;
  }
  public SequenceVerificationItr verifyPreAlloc(int expectedVal){
    //TODO
    return null;
  }
  public void verifyStructuralIntegrity()
  {
    Assertions.assertEquals(expectedSeqSize,((FloatSnglLnkSeq)seq).size);
    if(checkedType.checked)
    {
      switch(nestedType)
      {
        case QUEUE:
          Assertions.assertEquals(expectedSeqModCount,((FloatSnglLnkSeq.CheckedQueue)seq).modCount);
          break;
        case STACK:
          Assertions.assertEquals(expectedSeqModCount,((FloatSnglLnkSeq.CheckedStack)seq).modCount);
          break;
        default:
          throw new Error("Unknown nested type "+nestedType);
      }
    }
    if(expectedSeqSize==0)
    {
      Assertions.assertNull(((FloatSnglLnkSeq)seq).head);
    }
    else
    {
      FloatSnglLnkNode node;
      Assertions.assertNotNull(node=((FloatSnglLnkSeq)seq).head);
      while(--expectedSeqSize!=0)
      {
        Assertions.assertNotNull(node=node.next);
      }
      Assertions.assertNull(node.next);
    }
  }
}
