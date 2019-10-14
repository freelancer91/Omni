package omni.impl.set;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.api.OmniNavigableSet.OfBoolean;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IllegalModification;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen.PredicateGenCallType;
import omni.impl.MonitoredSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.SortOrder;
import omni.impl.StructType;
import omni.util.NotYetImplementedException;
import omni.util.TestExecutorService;

public class BooleanSetImplTest{
  
    private static class EmptyItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator.OfBoolean,OmniNavigableSet.OfBoolean>{
        final OmniIterator.OfBoolean itr;
        final IteratorType itrType;
        final AbstractBooleanSetMonitor root;

        EmptyItrMonitor(OmniIterator.OfBoolean itr,AbstractBooleanSetMonitor root,IteratorType itrType){
            this.itr=itr;
            this.root=root;
            this.itrType=itrType;
        }
        @Override
        public Object verifyClone(){
            final OmniIterator.OfBoolean itr=getIterator();
            final Object clone;
            try{
                clone=itr.clone();
            }finally{
                verifyIteratorState();
                getMonitoredCollection().verifyCollectionState();
            }
            Assertions.assertSame(itr,clone);
            verifyCloneHelper(clone);
            return clone;
        }
        @Override
        public boolean nextWasJustCalled(){
            return false;
        }

        @Override
        public void updateItrNextState(){
            //nothing to do
        }

        @Override
        public void verifyNextResult(DataType outputType,Object result){
            throw new UnsupportedOperationException();
        }

        @Override
        public void updateItrRemoveState(){
            throw new UnsupportedOperationException();
        }

        @Override
        public omni.api.OmniIterator.OfBoolean getIterator(){
            return itr;
        }

        @Override
        public IteratorType getIteratorType(){
            return itrType;
        }

        @Override
        public MonitoredCollection<? extends OfBoolean> getMonitoredCollection(){
            return root;
        }

        @Override
        public boolean hasNext(){
            return false;
        }

        @Override
        public int getNumLeft(){
            return 0;
        }

        @Override
        public void verifyForEachRemaining(MonitoredFunction function){
            Assertions.assertTrue(function.isEmpty());
        }
        @Override
        public void verifyIteratorState() {
           Assertions.assertSame(itr,AbstractBooleanSet.EMPTY_ITR);
        }
        @Override
        public void verifyCloneHelper(Object clone){
            Assertions.assertSame(clone,itr);
        }
        
    }
    private static class FullItrMonitor implements MonitoredCollection.MonitoredIterator<OmniIterator.OfBoolean,OmniNavigableSet.OfBoolean>{
        final BooleanSetImplMonitor root;
        final OmniIterator.OfBoolean itr;
        final IteratorType itrType;
        int expectedItrState;
        int expectedRootState;
        int lastRet;
        
        FullItrMonitor(BooleanSetImplMonitor root,OmniIterator.OfBoolean itr,IteratorType itrType,int expectedItrState){
            this.root=root;
            this.itr=itr;
            this.itrType=itrType;
            this.expectedItrState=expectedItrState;
            this.expectedRootState=expectedItrState&0b11;
            this.lastRet=-1;
        }
        
        @Override
        public boolean nextWasJustCalled(){
            return lastRet!=-1;
        }

        @Override
        public void updateItrNextState(){
          if(root.checkedType.checked) {
        	  switch(this.expectedItrState&0b11) {
        	  case 0b001:
        		  this.expectedItrState=0b100;
        		  this.lastRet=0;
        		  break;
        	  case 0b010:
        		  this.expectedItrState=0b100;
        		  this.lastRet=1;
        		  break;
        	  default:
        		  switch(itrType) {
        		  case AscendingItr:
        			  this.expectedItrState=0b110;
        			  this.lastRet=0;
        			  break;
        		  case DescendingItr:
        			  this.expectedItrState=0b101;
        			  this.lastRet=1;
        			  break;
        		  default:
        			  throw itrType.invalid();
        		  }
        		  break;
        	  }
          }else {
        	  switch(this.expectedItrState){
        	  default:
        		  switch(itrType) {
        		  case AscendingItr:
        			  this.expectedItrState=0b10;
                      this.lastRet=0;
        			  break;
        		  case DescendingItr:
        			  this.expectedItrState=0b01;
                      this.lastRet=1;
        			  break;
        		  default:
        			  throw itrType.invalid();
        		  }
        		  break;
        	  case 0b01:
        		  this.expectedItrState=0b00;
                  this.lastRet=0;
                  break;
        	  case 0b10:
        		  this.expectedItrState=0b00;
                  this.lastRet=1;
        	  
        	  }
          }
        }

        @Override
        public void verifyNextResult(DataType outputType,Object result){
        	boolean expected;
        	if(root.checkedType.checked) {
          	  switch(this.expectedItrState) {
          	  case 0b001:
          		  expected=false;
          		  break;
          	  case 0b010:
                  expected=true;
          		  break;
          	  default:
          		  switch(itrType) {
          		  case AscendingItr:
          			  expected=false;
          			  break;
          		  case DescendingItr:
                      expected=true;
          			  break;
          		  default:
          			  throw itrType.invalid();
          		  }
          		  break;
          	  }
            }else {
          	  switch(this.expectedItrState){
          	  default:
          		  switch(itrType) {
          		  case AscendingItr:
          			  expected=false;
          			  break;
          		  case DescendingItr:
                      expected=true;
          			  break;
          		  default:
          			  throw itrType.invalid();
          		  }
          		  break;
          	  case 0b01:
          		  expected=false;
                    break;
          	  case 0b10:
                    expected=true;
          	  
          	  }
            }
           
           Assertions.assertEquals(outputType.convertVal(expected),result);
           
        }

        @Override
        public void updateItrRemoveState(){
            lastRet=-1;
            if(root.checkedType.checked) {
            	switch(itrType) {
            	case AscendingItr:
            		if(expectedItrState==0b110) {
            			root.expectedState&=0b10;
            			expectedRootState&=0b10;
            			expectedItrState=0b010;
            		}else {
            			if(root.expectedState==0b11) {
            				root.expectedState=0b01;
            				expectedRootState=0b01;
            			}else {
            				root.expectedState=0b00;
            				expectedRootState=0b00;
            			}
            			expectedItrState=0b000;
            		}
            		break;
            	case DescendingItr:
            		if(expectedItrState==0b101) {
            			root.expectedState&=0b01;
            			expectedRootState&=0b01;
            			expectedItrState=0b001;
            		}else {
            			if(root.expectedState==0b11) {
            				root.expectedState=0b10;
            				expectedRootState=0b10;
            			}else {
            				root.expectedState=0b00;
            				expectedRootState=0b00;
            			}
            			expectedItrState=0b000;
            		}
            		break;
                default:
                	throw itrType.invalid();
            	}
            }else {
                switch(itrType) {
                case AscendingItr:
                    if(expectedItrState==0){
                        root.expectedState-=(root.expectedState&0b10)==0?0b01:0b10;
                      }else{
                        root.expectedState=0b10;
                      }
                    break;
                case DescendingItr:
                    if(expectedItrState==0){
                        root.expectedState-=(root.expectedState&0b01)==0?0b10:0b01;
                      }else{
                        root.expectedState=0b01;
                      }
                    break;
                default:
                    throw itrType.invalid();
                }
                expectedRootState=root.expectedState;
            }
        }

        @Override
        public omni.api.OmniIterator.OfBoolean getIterator(){
            return itr;
        }

        @Override
        public IteratorType getIteratorType(){
            return itrType;
        }

        @Override
        public MonitoredCollection<? extends OmniNavigableSet.OfBoolean> getMonitoredCollection(){
            return root;
        }

        @Override
        public boolean hasNext(){
            if(root.checkedType.checked) {
            	return (this.expectedItrState&0b11)!=0;
            }else {
                return expectedItrState!=0;
            }
        }

        @Override
        public int getNumLeft(){
        	return Integer.bitCount(expectedItrState&0b11);
        }

        @Override
        public void verifyForEachRemaining(MonitoredFunction function){
            if(root.checkedType.checked) {
                switch(itrType) {
                case AscendingItr:
                	switch(expectedItrState&0b11){
            	    default:
            	      Assertions.assertTrue(function.isEmpty());
            	      return;
                    case 0b11:
                      Assertions.assertEquals(2, function.size());
                      Assertions.assertEquals(Boolean.FALSE,function.get(0));
                      Assertions.assertEquals(Boolean.TRUE,function.get(1));
                      lastRet=1;
                      break;
                    case 0b10:
                      Assertions.assertEquals(1, function.size());
                      Assertions.assertEquals(Boolean.TRUE,function.get(0));
                      lastRet=1;
                      break;
                    case 0b01:
	                  Assertions.assertEquals(1, function.size());
	                  Assertions.assertEquals(Boolean.FALSE,function.get(0));
	                  lastRet=0;
            	    }
                    break;
                case DescendingItr:
                	switch(expectedItrState&0b11){
            	    default:
            	      Assertions.assertTrue(function.isEmpty());
            	      return;
                    case 0b11:
                      Assertions.assertEquals(2, function.size());
                      Assertions.assertEquals(Boolean.TRUE,function.get(0));
                      Assertions.assertEquals(Boolean.FALSE,function.get(1));
                      lastRet=1;
                      break;
                    case 0b10:
                      Assertions.assertEquals(1, function.size());
                      Assertions.assertEquals(Boolean.TRUE,function.get(0));
                      lastRet=1;
                      break;
                    case 0b01:
	                  Assertions.assertEquals(1, function.size());
	                  Assertions.assertEquals(Boolean.FALSE,function.get(0));
	                  lastRet=0;
            	    }
                    break;
                default:
                    throw itrType.invalid();
                }
                this.expectedItrState=0b100;
            }else {
                switch(expectedItrState) {
                case 0b11:
                    Assertions.assertEquals(2,function.size());
                    switch(itrType) {
                    case AscendingItr:
                        Assertions.assertEquals(Boolean.FALSE,function.get(0));
                        Assertions.assertEquals(Boolean.TRUE,function.get(1));
                        lastRet=1;
                        break;
                    case DescendingItr:
                        Assertions.assertEquals(Boolean.TRUE,function.get(0));
                        Assertions.assertEquals(Boolean.FALSE,function.get(1));
                        lastRet=0;
                        break;
                    default:
                        throw itrType.invalid();
                    }
                    break;
                case 0b10:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
                    lastRet=1;
                    break;
                case 0b01:
                    Assertions.assertEquals(1,function.size());
                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
                    lastRet=0;
                    break;
                case 0b00:
                    Assertions.assertTrue(function.isEmpty());
                    break;
                default:
                    throw new IllegalStateException("expectedItrState = "+expectedItrState);
                }
                expectedItrState=0b00;
            }
        }

        @Override
        public void verifyCloneHelper(Object clone){
          Assertions.assertEquals(this.expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.UncheckedAscendingFullItr.itrState(itr));
          Assertions.assertSame(this.root.set,FieldAndMethodAccessor.BooleanSetImpl.UncheckedAscendingFullItr.root(itr));
          if(root.checkedType.checked) {
        	  Assertions.assertEquals(expectedRootState,FieldAndMethodAccessor.BooleanSetImpl.CheckedAscendingFullItr.expectedRootState(itr));
          }
        }
        
    }
    
    private static class SingleViewItrMonitor extends FullItrMonitor{

        final boolean viewedVal;
        
        SingleViewItrMonitor(boolean viewedVal,BooleanSetImplMonitor root,OmniIterator.OfBoolean itr,IteratorType itrType,int expectedItrState){
            super(root,itr,itrType,expectedItrState);
            this.viewedVal=viewedVal;
        }
        
        @Override
        public void updateItrNextState(){
            if(root.checkedType.checked) {
                expectedItrState=0b01;
            }else {
                expectedItrState=0b00;
            }
            if(viewedVal) {
                lastRet=1;
            }else {
                lastRet=0;
            }
        }

        @Override
        public void verifyNextResult(DataType outputType,Object result){
            Assertions.assertEquals(outputType.convertVal(viewedVal),result);
            
        }

        @Override
        public void updateItrRemoveState(){
            this.lastRet=-1;
            if(root.checkedType.checked) {
                expectedItrState=0b00;
            }
            if(viewedVal) {
                root.expectedState&=0b01;
            }else {
                root.expectedState&=0b10;
            }
        }

        @Override
        public boolean hasNext(){
            if(root.checkedType.checked) {
                return expectedItrState==0b10;
            }else {
                return expectedItrState!=0;
            }
        }

        @Override
        public int getNumLeft(){
            if(hasNext()) {
                return 1;
            }
            return 0;
        }

        @Override
        public void verifyForEachRemaining(MonitoredFunction function){
            if(hasNext()) {
                Assertions.assertEquals(1,function.size());
                Assertions.assertEquals(viewedVal,function.get(0));
                if(root.checkedType.checked) {
                    expectedItrState=0b01;
                }else {
                    expectedItrState=0b00;
                }
                if(viewedVal) {
                    this.lastRet=1;
                }else {
                    this.lastRet=0;
                }
            }
            
        }
    }
    
    private static class BooleanSetImplMonitor extends AbstractBooleanSetMonitor{
        int expectedState;
        private static BooleanSetImpl getCopyConstructorSet(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass) {
          Class<? extends BooleanSetImpl> clazz;
          if(checkedType.checked) {
              clazz=BooleanSetImpl.Checked.class;
          }else {
              clazz=BooleanSetImpl.class;
          }
          try{
              return clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
          }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
                  | InvocationTargetException | NoSuchMethodException | SecurityException e){
              throw new Error(e);
          }
        }
        
      BooleanSetImplMonitor(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
          super(getCopyConstructorSet(checkedType,collection,collectionClass),checkedType,SortOrder.Ascending);
          this.updateCollectionState();
      
      }
        
        public Object verifyClone(){
            final Object clone;
            try{
                clone=FieldAndMethodAccessor.BooleanSetImpl.clone(set);
            }finally{
                verifyCollectionState();
            }
            verifyClone(clone);
            return clone;
          }
        
        BooleanSetImplMonitor(CheckedType checkedType,int expectedState){
            this(checkedType.checked?new BooleanSetImpl.Checked(expectedState):new BooleanSetImpl(expectedState),expectedState,checkedType,SortOrder.Ascending);
        }
        BooleanSetImplMonitor(CheckedType checkedType){
            this(checkedType.checked?new BooleanSetImpl.Checked():new BooleanSetImpl(),0,checkedType,SortOrder.Ascending);
        }
        
        BooleanSetImplMonitor(BooleanSetImpl set,int expectedState,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.expectedState=expectedState;
        }
        private IllegalStateException illegalState() {
            return new IllegalStateException("state = "+expectedState);
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            Assertions.assertEquals(DataType.BOOLEAN,inputType);
            switch(expectedState) {
            case 0b00:
                Assertions.assertTrue(result);
                if((boolean)inputVal) {
                    expectedState=0b10;
                }else {
                    expectedState=0b01;
                }
                break;
            case 0b01:
                Assertions.assertEquals(result,(boolean)inputVal);
                if(result) {
                    expectedState=0b11;
                }
                break;
            case 0b10:
                Assertions.assertEquals(!result,(boolean)inputVal);
                if(result) {
                    expectedState=0b11;
                }
                break;
            case 0b11:
                Assertions.assertFalse(result);
                break;
            default:
                throw illegalState();
            }
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            boolean boolVal=(boolean)DataType.BOOLEAN.convertVal(inputType,queryVal.getInputVal(inputType,modification));
            switch(expectedState) {
            case 0b01:
                Assertions.assertFalse(boolVal);
                expectedState=0b00;
                break;
            case 0b10:
                Assertions.assertTrue(boolVal);
                expectedState=0b00;
                break;
            case 0b11:
                if(boolVal) {
                    expectedState=0b01;
                }else {
                    expectedState=0b10;
                }
                break;
            default:
                throw illegalState();
            }
        }
        private Object get(int iterationIndex,DataType outputType,SortOrder sortOrder)
        {
            switch(expectedState) {
            case 0b00:
                throw new UnsupportedOperationException("iteration index = "+iterationIndex);
            case 0b01:
                if(iterationIndex==0) {
                    return outputType.convertVal(false);
                }
                throw new UnsupportedOperationException("iteration index = "+iterationIndex);
            case 0b10:
                if(iterationIndex==0) {
                    return outputType.convertVal(true);
                }
                throw new UnsupportedOperationException("iteration index = "+iterationIndex);
            case 0b11:
                switch(iterationIndex) {
                case 0:
                    switch(sortOrder) {
                    case Ascending:
                        return outputType.convertVal(false);
                    case Descending:
                        return outputType.convertVal(true);
                    default:
                        throw sortOrder.invalid();
                    }
                case 1:
                    switch(sortOrder) {
                    case Ascending:
                        return outputType.convertVal(true);
                    case Descending:
                        return outputType.convertVal(false);
                    default:
                        throw sortOrder.invalid();
                    }
                default:
                    throw new UnsupportedOperationException("iteration index = "+iterationIndex);
                }
            default:
                throw illegalState();
            }
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            return get(iterationIndex,outputType,sortOrder);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OmniNavigableSet.OfBoolean> getMonitoredIterator(IteratorType itrType){
            OmniIterator.OfBoolean itr;
            IteratorType iType;
            switch(this.sortOrder) {
            case Ascending:
            {
                iType=itrType;
                switch(itrType) {
                case AscendingItr:
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            case Descending:
            {
                switch(itrType) {
                case AscendingItr:
                    iType=IteratorType.DescendingItr;
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    iType=IteratorType.AscendingItr;
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            default:
                throw this.sortOrder.invalid();
            }
            if(this.expectedState==0b00) {
                return new EmptyItrMonitor(itr,this,iType);
            }
            return new FullItrMonitor(this,itr,iType,this.expectedState);
        }
        @Override
        public StructType getStructType(){
            return StructType.BooleanSetImpl;
        }
        @Override
        public void modCollection(){
            switch(expectedState) {
            case 0b00:
                set.add(false);
                expectedState=0b01;
                break;
            case 0b01:
                set.removeVal(false);
                set.add(true);
                expectedState=0b10;
                break;
            case 0b10:
                set.add(false);
                expectedState=0b11;
                break;
            case 0b11:
                set.clear();
                expectedState=0b00;
                break;
            default:
                throw illegalState();
            }
        }
        @Override
        public int size(){
            switch(expectedState) {
            case 0b00:
                return 0;
            case 0b01:
            case 0b10:
                return 1;
            case 0b11:
                return 2;
            default:
                throw illegalState();
            }
        }
        @Override
        public void updateClearState(){
            expectedState=0b00;
        }
        @Override
        public void updateCollectionState(){
            expectedState=FieldAndMethodAccessor.BooleanSetImpl.state(set);
        }
        @Override
        public void verifyCollectionState(){
            Assertions.assertEquals(expectedState,FieldAndMethodAccessor.BooleanSetImpl.state(set));
        }
        @Override
        public void verifyClone(Object clone){
            Assertions.assertNotSame(set,clone);
            switch(sortOrder) {
            case Ascending:
                if(checkedType.checked) {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Checked);
                }else {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Descending);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Checked);
                }
                break;
            case Descending:
                if(checkedType.checked) {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Descending.Checked);
                }else {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Descending);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Descending.Checked);
                }
                break;
            default:
                throw sortOrder.invalid();
            }
            Assertions.assertEquals(expectedState,((BooleanSetImpl)clone).state);
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            
            switch(expectedState) {
            case 0b00:
                Assertions.assertFalse(result);
                Assertions.assertTrue(filter.removedVals.isEmpty());
                Assertions.assertTrue(filter.retainedVals.isEmpty());
                Assertions.assertEquals(0,filter.numCalls);
                Assertions.assertEquals(0,filter.numRemoved);
                Assertions.assertEquals(0,filter.numRetained);
                break;
            case 0b01:
                Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
                Assertions.assertNotEquals(filter.removedVals.contains(Boolean.FALSE),filter.retainedVals.contains(Boolean.FALSE));
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                    expectedState=0b00;
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                }
                break;
            case 0b10:
                Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
                Assertions.assertNotEquals(filter.removedVals.contains(Boolean.TRUE),filter.retainedVals.contains(Boolean.TRUE));
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
                    expectedState=0b00;
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                }
                break;
            case 0b11:
                Assertions.assertNotEquals(filter.removedVals.contains(Boolean.TRUE),filter.retainedVals.contains(Boolean.TRUE));
                Assertions.assertNotEquals(filter.removedVals.contains(Boolean.FALSE),filter.retainedVals.contains(Boolean.FALSE));
                Assertions.assertEquals(2,filter.numCalls);
                if(result) {
                    if(filter.removedVals.contains(Boolean.TRUE)) {
                        if(filter.removedVals.contains(Boolean.FALSE)) {
                            Assertions.assertEquals(0,filter.numRetained);
                            Assertions.assertEquals(2,filter.numRemoved);
                            expectedState=0b00;
                        }else {
                            Assertions.assertEquals(1,filter.numRetained);
                            Assertions.assertEquals(1,filter.numRemoved);
                            expectedState=0b01;
                        }
                    }else {
                        Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
                        Assertions.assertEquals(1,filter.numRetained);
                        Assertions.assertEquals(1,filter.numRemoved);
                        expectedState=0b10;
                    }
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(2,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
                }
                break;
            default:
                throw illegalState();
            }
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            switch(sortOrder) {
            case Ascending:
                if(checkedType.checked) {
                    FieldAndMethodAccessor.BooleanSetImpl.Checked.writeObject(set,oos);
                }else {
                    FieldAndMethodAccessor.BooleanSetImpl.writeObject(set,oos);
                }
                break;
            case Descending:
                if(checkedType.checked) {
                    FieldAndMethodAccessor.BooleanSetImpl.Descending.Checked.writeObject(set,oos);
                }else {
                    FieldAndMethodAccessor.BooleanSetImpl.Descending.writeObject(set,oos);
                }
                break;
            default:
                throw sortOrder.invalid();
            
            }
           
        }
        
    }
    private static class FullViewMonitor extends AbstractBooleanSetMonitor{
        final BooleanSetImplMonitor root;
        FullViewMonitor(OmniNavigableSet.OfBoolean set,BooleanSetImplMonitor root,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.root=root;
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            root.updateAddState(inputVal,inputType,result);
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            root.removeFromExpectedState(inputType,queryVal,modification);
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            return root.get(iterationIndex,outputType,root.sortOrder.reverse());
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OfBoolean> getMonitoredIterator(IteratorType itrType){
            OmniIterator.OfBoolean itr;
            IteratorType iType;
            switch(sortOrder) {
            case Ascending:
            {
                iType=itrType;
                switch(itrType) {
                case AscendingItr:
                    
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            case Descending:
            {
                
                switch(itrType) {
                case AscendingItr:
                    iType=IteratorType.DescendingItr;
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    iType=IteratorType.AscendingItr;
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            default:
                throw root.sortOrder.invalid();
            }
            if(root.expectedState==0b00) {
                return new EmptyItrMonitor(itr,root,iType);
            }
            return new FullItrMonitor(root,itr,iType,root.expectedState);
        }
       
        @Override
        public StructType getStructType(){
            return StructType.BooleanSetFullView;
        }
        @Override
        public void modCollection(){
            root.modCollection();
        }
        @Override
        public int size(){
            return root.size();
        }
        @Override
        public void updateClearState(){
            root.updateClearState();
        }
        @Override
        public void updateCollectionState(){
            root.updateCollectionState();
        }
        @Override
        public void verifyCollectionState(){
            root.verifyCollectionState();
            Assertions.assertSame(root.set,FieldAndMethodAccessor.BooleanSetImpl.AbstractFullView.root(set));
        }
        @Override
        public void verifyClone(Object clone){
            Assertions.assertNotSame(set,clone);
            Assertions.assertNotSame(root.set,clone);
            switch(root.sortOrder) {
            case Descending:
                if(checkedType.checked) {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Checked);
                }else {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Descending);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Checked);
                }
                break;
            case Ascending:
                if(checkedType.checked) {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Descending.Checked);
                }else {
                    Assertions.assertTrue(clone instanceof BooleanSetImpl.Descending);
                    Assertions.assertFalse(clone instanceof BooleanSetImpl.Descending.Checked);
                }
                break;
            default:
                throw sortOrder.invalid();
            }
            Assertions.assertEquals(root.expectedState,((BooleanSetImpl)clone).state);
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            root.verifyRemoveIf(result,filter);
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            switch(sortOrder) {
            case Ascending:
                if(checkedType.checked) {
                    FieldAndMethodAccessor.BooleanSetImpl.AscendingView.Checked.writeObject(set,oos);
                }else {
                    FieldAndMethodAccessor.BooleanSetImpl.AscendingView.writeObject(set,oos);
                }
                break;
            case Descending:
                if(checkedType.checked) {
                    FieldAndMethodAccessor.BooleanSetImpl.DescendingView.Checked.writeObject(set,oos);
                }else {
                    FieldAndMethodAccessor.BooleanSetImpl.DescendingView.writeObject(set,oos);
                }
                break;
            default:
                throw sortOrder.invalid();
            
            }
        }
        
    }
    private static class EmptyViewMonitor extends AbstractBooleanSetMonitor{
        final int position;
        EmptyViewMonitor(OmniNavigableSet.OfBoolean set,int position,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.position=position;
        }
       
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            throw new UnsupportedOperationException();
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            throw new UnsupportedOperationException();
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            throw new UnsupportedOperationException();
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OfBoolean> getMonitoredIterator(IteratorType itrType){
            OmniIterator.OfBoolean itr;
            IteratorType iType;
            switch(this.sortOrder) {
            case Ascending:
            {
                iType=itrType;
                switch(itrType) {
                case AscendingItr:
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            case Descending:
            {
                switch(itrType) {
                case AscendingItr:
                    iType=IteratorType.DescendingItr;
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    iType=IteratorType.AscendingItr;
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            }
            default:
                throw this.sortOrder.invalid();
            }
            return new EmptyItrMonitor(itr,this,iType);
        }

        @Override
        public StructType getStructType(){
            return StructType.BooleanSetEmpty;
        }
        @Override
        public void modCollection(){
            throw new UnsupportedOperationException();
        }
        @Override
        public int size(){
            return 0;
        }
        @Override
        public void updateClearState(){
            //nothing to do
        }
        @Override
        public void updateCollectionState(){
            //nothing to do
        }
        @Override
        public void verifyCollectionState(){
            switch(sortOrder) {
            case Ascending:
                if(checkedType.checked) {
                    switch(position) {
                    case 0:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD);
                        break;
                    case 1:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE);
                        break;
                    case 2:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL);
                        break;
                    default:
                        throw new IllegalStateException();
                    }
                }else {
                    Assertions.assertSame(this,AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING);
                }
                break;
            case Descending:
                if(checkedType.checked) {
                    switch(position) {
                    case 0:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD);
                        break;
                    case 1:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE);
                        break;
                    case 2:
                        Assertions.assertSame(this,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL);
                        break;
                    default:
                        throw new IllegalStateException();
                    }
                }else {
                    Assertions.assertSame(this,AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING);
                }
                break;
            default:
                throw sortOrder.invalid();
            }
        }
        @Override
        public void verifyClone(Object clone){
            throw new UnsupportedOperationException();
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            Assertions.assertFalse(result);
            Assertions.assertEquals(0,filter.numCalls);
            Assertions.assertEquals(0,filter.numRemoved);
            Assertions.assertEquals(0,filter.numRetained);
            Assertions.assertTrue(filter.removedVals.isEmpty());
            Assertions.assertTrue(filter.retainedVals.isEmpty());
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            throw new UnsupportedOperationException();
        }
        
    }
    private static class SingleViewMonitor extends AbstractBooleanSetMonitor{
        final BooleanSetImplMonitor root;
        final boolean viewedVal;
        private int getMask() {
            if(viewedVal) {
                return 0b10;
            }else {
                return 0b01;
            }
        }
        SingleViewMonitor(OmniNavigableSet.OfBoolean set,BooleanSetImplMonitor root,boolean viewedVal,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.root=root;
            this.viewedVal=viewedVal;
        }
        @Override
        public void updateAddState(Object inputVal,DataType inputType,boolean result){
            if(result) {
                root.expectedState|=getMask();
            }
        }
        @Override
        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
            root.expectedState&=~getMask();
        }
        @Override
        public Object get(int iterationIndex,DataType outputType){
            if((root.expectedState&getMask())!=0) {
                return outputType.convertVal(viewedVal);
            }
            throw new UnsupportedOperationException("iterationIndex = "+iterationIndex+", viewedVal = "+viewedVal+", state = "+root.expectedState);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OfBoolean> getMonitoredIterator(IteratorType itrType){
            OmniIterator.OfBoolean itr;
            IteratorType iType;
            switch(sortOrder) {
            case Ascending:
                iType=itrType;
                switch(itrType) {
                case AscendingItr:
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            case Descending:
                switch(itrType) {
                case AscendingItr:
                    iType=IteratorType.DescendingItr;
                    itr=set.iterator();
                    break;
                case DescendingItr:
                    iType=IteratorType.AscendingItr;
                    itr=set.descendingIterator();
                    break;
                default:
                    throw itrType.invalid();
                }
                break;
            default:
                throw sortOrder.invalid();
            }
            if(isEmpty()) {
                return new EmptyItrMonitor(itr,root,iType);
            }
            return new SingleViewItrMonitor(viewedVal,root,itr,iType,checkedType.checked?0b10:0b01);
           
        }
        @Override
        public StructType getStructType(){
            return StructType.BooleanSetSingleView;
        }
        @Override
        public void modCollection(){
            int mask=getMask();
            if((root.expectedState&mask)!=0){
                root.expectedState&=~mask;
                set.removeVal(viewedVal);
            }else {
                root.expectedState|=mask;
                set.add(viewedVal);
            }
        }
        @Override
        public int size(){
            if((root.expectedState&getMask())!=0){
                return 1;
            }
            return 0;
        }
        @Override
        public void updateClearState(){
            root.expectedState&=~getMask();
        }
        @Override
        public void updateCollectionState(){
            root.updateCollectionState();
        }
        @Override
        public void verifyCollectionState(){
            root.verifyCollectionState();
        }
        @Override
        public void verifyClone(Object clone){
            throw new UnsupportedOperationException();
        }
        @Override
        public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
            int mask=getMask();
            if((root.expectedState&mask)!=0) {
                Assertions.assertFalse(filter.removedVals.contains(!viewedVal));
                Assertions.assertFalse(filter.retainedVals.contains(!viewedVal));
                Assertions.assertNotEquals(filter.removedVals.contains(viewedVal),filter.retainedVals.contains(viewedVal));
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(viewedVal));
                    root.expectedState&=~mask;
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(viewedVal));
                }
            }else {
                Assertions.assertFalse(result);
                Assertions.assertTrue(filter.removedVals.isEmpty());
                Assertions.assertTrue(filter.retainedVals.isEmpty());
                Assertions.assertEquals(0,filter.numCalls);
                Assertions.assertEquals(0,filter.numRemoved);
                Assertions.assertEquals(0,filter.numRetained);
            }
            
        }
        @Override
        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
            //nothing to do
        }
        @Override
        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
            throw new UnsupportedOperationException();
        }
        
    }
    private static abstract class AbstractBooleanSetMonitor implements MonitoredSet<OmniNavigableSet.OfBoolean>{
        final OmniNavigableSet.OfBoolean set;
        final CheckedType checkedType;
        final SortOrder sortOrder;
        AbstractBooleanSetMonitor(OmniNavigableSet.OfBoolean set,CheckedType checkedType,SortOrder sortOrder){
            this.set=set;
            this.checkedType=checkedType;
            this.sortOrder=sortOrder;
           
        }
        @Override
        public CheckedType getCheckedType(){
            return checkedType;
        }
        @Override
        public OmniNavigableSet.OfBoolean getCollection(){
            return set;
        }
        @Override
        public DataType getDataType(){
            return DataType.BOOLEAN;
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OfBoolean> getMonitoredIterator(){
            return getMonitoredIterator(IteratorType.AscendingItr);
        }
        @Override
        public MonitoredIterator<? extends OmniIterator<?>,OfBoolean> getMonitoredIterator(int index,
                IteratorType itrType){
            var itr=getMonitoredIterator(itrType);
            for(int i=0;i<index;++i) {
                itr.iterateForward();
            }
            return itr;
        }
    }
   
    
//    private static class BooleanSetImplMonitor implements MonitoredSet<BooleanSetImpl>{
//        final CheckedType checkedType;
//        final BooleanSetImpl set;
//        int expectedState;
//        BooleanSetImplMonitor(CheckedType checkedType){
//            this.checkedType=checkedType;
//            this.expectedState=0;
//            if(checkedType.checked){
//                this.set=new BooleanSetImpl.Checked();
//            }else{
//                this.set=new BooleanSetImpl();
//            }
//        }
//        BooleanSetImplMonitor(CheckedType checkedType,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
//            this.checkedType=checkedType;
//            Class<? extends BooleanSetImpl> clazz;
//            if(checkedType.checked) {
//                clazz=BooleanSetImpl.Checked.class;
//            }else {
//                clazz=BooleanSetImpl.class;
//            }
//            try{
//                this.set=clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
//            }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
//                    | InvocationTargetException | NoSuchMethodException | SecurityException e){
//                throw new Error(e);
//            }
//            this.updateCollectionState();
//            
//        }
//        BooleanSetImplMonitor(CheckedType checkedType,int expectedState){
//            this.checkedType=checkedType;
//            this.expectedState=expectedState;
//            if(checkedType.checked) {
//                this.set=new BooleanSetImpl.Checked(expectedState);
//            }else {
//                this.set=new BooleanSetImpl(expectedState);
//            }
//        }
//
//        @Override
//        public Object get(int iterationIndex,DataType outputType) {
//            var itr=set.iterator();
//            while(iterationIndex>0) {
//                itr.nextBoolean();
//            }
//            return outputType.convertVal(itr.nextBoolean());
//        }
//        
//        
//        @Override
//        public void updateAddState(Object inputVal,DataType inputType,boolean result){
//            boolean v=(boolean)inputVal;
//            expectedState|=v?2:1;
//        }
//
//        @Override public CheckedType getCheckedType(){
//            return this.checkedType;
//        }
//
//        @Override public BooleanSetImpl getCollection(){
//            return set;
//        }
//
//        @Override public DataType getDataType(){
//            return DataType.BOOLEAN;
//        }
//
//        @Override public MonitoredIterator<OmniIterator.OfBoolean,BooleanSetImpl> getMonitoredIterator(){
//            if(checkedType.checked) {
//                return new CheckedMonitoredItr();
//            }
//            return new UncheckedMonitoredItr();
//        }
//        @Override public MonitoredIterator<? extends OmniIterator<?>,BooleanSetImpl> getMonitoredIterator(IteratorType itrType){
//            if(itrType!=IteratorType.AscendingItr) {
//                throw itrType.invalid();
//            }
//            return getMonitoredIterator();
//        }
//        @Override public MonitoredIterator<? extends OmniIterator<?>,BooleanSetImpl> getMonitoredIterator(int index,IteratorType itrType){
//            var itrMonitor=getMonitoredIterator(itrType);
//            while(--index>=0 && itrMonitor.hasNext()) {
//                itrMonitor.iterateForward();
//            }
//            return itrMonitor;
//        }
//        @Override public StructType getStructType(){
//            return StructType.BooleanSetImpl;
//        }
//
//        @Override public boolean isEmpty(){
//            return expectedState==0;
//        }
//
//        @Override public void modCollection(){
//            set.state=expectedState=expectedState+1&0b11;
//        }
//
//        @Override public int size(){
//            return Integer.bitCount(expectedState);
//        }
//
//        @Override public void updateCollectionState(){
//            expectedState=set.state;
//        }
//
//        @Override public void verifyCollectionState(){
//            Assertions.assertEquals(expectedState,set.state);
//        }
//
//        @Override public void verifyClone(Object clone){
//            var cast=(BooleanSetImpl)clone;
//            Assertions.assertEquals(checkedType.checked,cast instanceof BooleanSetImpl.Checked);
//            Assertions.assertEquals(set.state,cast.state);
//        }
//
//        @Override
//        public void removeFromExpectedState(DataType inputType,QueryVal queryVal,QueryValModification modification){
//            switch(expectedState) {
//            case 0b01:
//            case 0b10:
//                expectedState=0b00;
//                break;
//            default:
//                boolean v=(boolean)queryVal.getInputVal(DataType.BOOLEAN,modification);
//                if(v){
//                    expectedState=0b01;
//                }else {
//                    expectedState=0b10;
//                }
//            }
//        }
//
//
//
//        @Override public void verifyRemoveIf(boolean result,MonitoredRemoveIfPredicate filter){
//            switch(expectedState) {
//            case 0b00:
//                Assertions.assertEquals(0,filter.numCalls);
//                Assertions.assertEquals(0,filter.numRemoved);
//                Assertions.assertEquals(0,filter.numRetained);
//                Assertions.assertTrue(filter.removedVals.isEmpty());
//                Assertions.assertTrue(filter.retainedVals.isEmpty());
//                Assertions.assertFalse(result);
//                Assertions.assertEquals(0b00,set.state);
//                break;
//            case 0b01:
//                Assertions.assertEquals(1,filter.numCalls);
//                if(result) {
//                    Assertions.assertEquals(1,filter.numRemoved);
//                    Assertions.assertEquals(0,filter.numRetained);
//                    Assertions.assertTrue(filter.removedVals.contains(Boolean.FALSE));
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
//                    Assertions.assertNotEquals(expectedState,set.state);
//                    Assertions.assertEquals(0b00,set.state);
//                }else {
//                    Assertions.assertEquals(0,filter.numRemoved);
//                    Assertions.assertEquals(1,filter.numRetained);
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
//                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
//                    Assertions.assertEquals(0b01,set.state);
//                }
//                break;
//            case 0b10:
//                Assertions.assertEquals(1,filter.numCalls);
//                if(result) {
//                    Assertions.assertEquals(1,filter.numRemoved);
//                    Assertions.assertEquals(0,filter.numRetained);
//                    Assertions.assertTrue(filter.removedVals.contains(Boolean.TRUE));
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
//                    Assertions.assertEquals(0b00,set.state);
//                }else {
//                    Assertions.assertEquals(0,filter.numRemoved);
//                    Assertions.assertEquals(1,filter.numRetained);
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.TRUE));
//                    Assertions.assertFalse(filter.removedVals.contains(Boolean.FALSE));
//                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
//                    Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
//                    Assertions.assertEquals(0b10,set.state);
//                }
//                break;
//            default:
//                Assertions.assertEquals(2,filter.numCalls);
//                if(result) {
//                    if(filter.removedVals.contains(Boolean.TRUE)) {
//                        if(filter.removedVals.contains(Boolean.FALSE)) {
//                            Assertions.assertEquals(0b00,set.state);
//                            Assertions.assertTrue(filter.retainedVals.isEmpty());
//                            Assertions.assertEquals(2,filter.removedVals.size());
//                        }else{
//                            Assertions.assertEquals(0b01,set.state);
//                            Assertions.assertEquals(1,filter.removedVals.size());
//                            Assertions.assertEquals(1,filter.retainedVals.size());
//                            Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
//                            Assertions.assertFalse(filter.retainedVals.contains(Boolean.TRUE));
//                        }
//                    }else {
//                        Assertions.assertEquals(0b10,set.state);
//                        Assertions.assertEquals(1,filter.removedVals.size());
//                        Assertions.assertEquals(1,filter.retainedVals.size());
//                        Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
//                        Assertions.assertFalse(filter.retainedVals.contains(Boolean.FALSE));
//                    }
//                }else {
//                    Assertions.assertEquals(2,filter.numRetained);
//                    Assertions.assertEquals(0,filter.numRemoved);
//                    Assertions.assertTrue(filter.removedVals.isEmpty());
//                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.TRUE));
//                    Assertions.assertTrue(filter.retainedVals.contains(Boolean.FALSE));
//                    Assertions.assertEquals(2,filter.retainedVals.size());
//                }
//            }
//            expectedState=set.state;
//        }
//
//        @Override
//        public void verifyArrayIsCopy(Object arr,boolean emptyArrayMayBeSame){
//            //nothing to do
//        }
//
//        abstract class AbstractMonitoredItr implements MonitoredSet.MonitoredSetIterator<OmniIterator.OfBoolean,BooleanSetImpl>{
//
//            final OmniIterator.OfBoolean itr;
//            int expectedItrState;
//            AbstractMonitoredItr(){
//                itr=set.iterator();
//                expectedItrState=expectedState;
//            }
//
//            @Override public OmniIterator.OfBoolean getIterator(){
//                return itr;
//            }
//
//            @Override public MonitoredCollection<BooleanSetImpl> getMonitoredCollection(){
//                return BooleanSetImplMonitor.this;
//            }
//        }
//
//        class UncheckedMonitoredItr extends AbstractMonitoredItr{
//            @Override
//            public boolean nextWasJustCalled(){
//                switch(expectedItrState){
//                case 0b00:
//                    return expectedState != 0b00;
//                case 0b10:
//                    return expectedState == 0b11;
//                default:
//                    return false;
//                }
//            }
//
//            @Override public boolean hasNext(){
//                return expectedItrState!=0b00;
//            }
//
//            @Override public int getNumLeft(){
//                return Integer.bitCount(expectedItrState);
//            }
//
//            @Override public void verifyForEachRemaining(MonitoredFunction function){
//                switch(expectedItrState) {
//                case 0b00:
//                    Assertions.assertTrue(function.isEmpty());
//                    break;
//                case 0b01:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
//                    break;
//                case 0b10:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
//                    break;
//                default:
//                    Assertions.assertEquals(2,function.size());
//                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
//                    Assertions.assertEquals(Boolean.TRUE,function.get(1));
//                }
//                expectedItrState=0b00;
//            }
//
//
//            @Override
//            public void updateItrRemoveState(){
//                if(expectedItrState == 0b00){
//                    if(expectedState == 0b11){
//                        expectedState=0b01;
//                    }else{
//                        expectedState=0b00;
//                    }
//                }else{
//                    expectedState=0b10;
//                }
//            }
//
//            @Override
//            public void verifyCloneHelper(Object clone){
//                Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Itr.root(clone));
//                Assertions.assertEquals(expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.Itr.itrState(clone));
//            }
//
//
//            @Override
//            public void updateItrNextState(){
//                if(expectedItrState == 0b11){
//                    expectedItrState=0b10;
//                }else{
//                    expectedItrState=0b00;
//                }
//            }
//
//            @Override
//            public void verifyNextResult(DataType outputType,Object result){
//                Assertions.assertEquals(outputType.convertVal(expectedItrState == 0b10),result);
//            }
//
//
//
//        }
//
//        class CheckedMonitoredItr extends AbstractMonitoredItr{
//
//
//            @Override public boolean hasNext(){
//                switch(expectedItrState){
//                case 0b0001:
//                case 0b0010:
//                case 0b0011:
//                case 0b0110:
//                case 0b0111:
//                    return true;
//                default:
//                    return false;
//                }
//            }
//
//            @Override public int getNumLeft(){
//                switch(expectedItrState) {
//                case 0b0011:
//                    return 2;
//                case 0b0001:
//                case 0b0010:
//                case 0b0110:
//                case 0b0111:
//                    return 1;
//                default:
//                    return 0;
//                }
//            }
//            @Override
//            public void verifyNextResult(DataType outputType,Object result){
//                boolean expected;
//                switch(expectedItrState){
//                case 0b0010:
//                case 0b0110:
//                case 0b0111:
//                    expected=true;
//                    break;
//                default:
//                    expected=false;
//                }
//                Assertions.assertEquals(outputType.convertVal(expected),result);
//            }
//            @Override public void verifyForEachRemaining(MonitoredFunction function){
//                switch(expectedItrState){
//                case 0b0001:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
//                    expectedItrState=0b0101;
//                    break;
//                case 0b0010:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
//                    expectedItrState=0b1010;
//                    break;
//                case 0b0011:
//                    Assertions.assertEquals(2,function.size());
//                    Assertions.assertEquals(Boolean.FALSE,function.get(0));
//                    Assertions.assertEquals(Boolean.TRUE,function.get(1));
//                    expectedItrState=0b1111;
//                    break;
//                case 0b0110:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
//                    expectedItrState=0b1110;
//                    break;
//                case 0b0111:
//                    Assertions.assertEquals(1,function.size());
//                    Assertions.assertEquals(Boolean.TRUE,function.get(0));
//                    expectedItrState=0b1111;
//                    break;
//                default:
//                    Assertions.assertTrue(function.isEmpty());
//                }
//            }
//
//
//            @Override
//            public boolean nextWasJustCalled(){
//                switch(expectedItrState){
//                case 0b0101:
//                case 0b0111:
//                case 0b1010:
//                case 0b1110:
//                case 0b1111:
//                    return true;
//                default:
//                    return false;
//                }
//            }
//            @Override
//            public void updateItrRemoveState(){
//                switch(expectedItrState){
//                default:
//                    expectedState=0b00;
//                    expectedItrState=0b0100;
//                    break;
//                case 0b0111:
//                    expectedState=0b10;
//                    expectedItrState=0b0110;
//                    break;
//                case 0b1010:
//                    expectedState=0b00;
//                    expectedItrState=0b1000;
//                    break;
//                case 0b1110:
//                    expectedState=0b00;
//                    expectedItrState=0b1100;
//                    break;
//                case 0b1111:
//                    expectedState=0b01;
//                    expectedItrState=0b1101;
//                }
//            }
//
//            @Override
//            public void verifyCloneHelper(Object clone){
//                Assertions.assertSame(set,FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.root(clone));
//                Assertions.assertEquals(expectedItrState,
//                        FieldAndMethodAccessor.BooleanSetImpl.Checked.Itr.itrState(clone));
//            }
//
//
//            @Override
//            public void updateItrNextState(){
//                switch(expectedItrState){
//                default:
//                    this.expectedItrState=0b0101;
//                    break;
//                case 0b0010:
//                    this.expectedItrState=0b1010;
//                    break;
//                case 0b0011:
//                    this.expectedItrState=0b0111;
//                    break;
//                case 0b0110:
//                    this.expectedItrState=0b1110;
//                    break;
//                case 0b0111:
//                    this.expectedItrState=0b1111;
//                    break;
//                }
//            }
//
//
//
//        }
//
//        @Override public void updateClearState() {
//            expectedState=0;
//        }
//        @Override
//        public void writeObjectImpl(MonitoredObjectOutputStream oos) throws IOException{
//            set.writeExternal(oos);
//        }
//    }
    private static final EnumSet<SetInitialization> VALID_INIT_SEQS=EnumSet.of(SetInitialization.Empty,
            SetInitialization.AddTrue,SetInitialization.AddFalse,
            SetInitialization.AddTrueAndFalse);
    private static final boolean[] POSSIBLE_VALS=new boolean[]{false,true};
    
    @Test
    public void testadd_val(){
        final var mayBeAddedTo=DataType.BOOLEAN.mayBeAddedTo();
        for(final var initSet:VALID_INIT_SEQS){
            for(final var inputType:mayBeAddedTo){
                for(final var checkedType:CheckedType.values()){
                    for(final var inputVal:POSSIBLE_VALS){
                        for(final var functionCallType:FunctionCallType.values()){
                            TestExecutorService.submitTest(()->{
                                var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                Assertions.assertEquals(!monitor.set.contains(inputVal),
                                        monitor.verifyAdd(inputVal,inputType,functionCallType));
                            });
                        }
                    }

                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testadd_val");
    }
    
    @Test
    public void testclear_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyClear();
        test.runAllTests("BooleanSetImplTest.testclear_void");
    }
    
    @Test
    public void testclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyClone();
        test.runAllTests("BooleanSetImplTest.testclone_void");
    }
    
    
    
    @Test
    public void testConstructor_int(){
        BasicTest test=(checkedType,initSet)->{
            int state;
            switch(initSet){
            case Empty:
                state=0b00;
                break;
            case AddFalse:
                state=0b01;
                break;
            case AddTrue:
                state=0b10;
                break;
            case AddTrueAndFalse:
                state=0b11;
                break;
            default:
                throw initSet.invalid();
            }
            initSet.initialize(new BooleanSetImplMonitor(checkedType,state)).verifyCollectionState();
        };
        test.runAllTests("BooleanSetImplTest.testConstructor_int");
    }
    
    @Test
    public void testConstructor_void(){
        for(final var checkedType:CheckedType.values()){
            TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType).verifyCollectionState());
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_void");
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_Collection() {
        for(var checkedType:CheckedType.values()) {
            for(var collectionClass:DataType.BOOLEAN.validCollectionConstructorClasses) {
                TestExecutorService.submitTest(()->{
                    Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(DataType.BOOLEAN,(Class<? extends Collection<?>>)collectionClass);
                    new BooleanSetImplMonitor(checkedType,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
                });
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_Collection");
    }
    
    @Test
    public void testcontains_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyContains(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testcontains_val");
    }
    
    @Test
    public void testforEach_Consumer(){
        for(final var initSet:VALID_INIT_SEQS){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=functionGen.randomized
                            && initSet == SetInitialization.AddTrueAndFalse && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || functionGen.expectedException == null || initSet.isEmpty){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->{
                                        final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                        if(functionGen.expectedException == null || initSet.isEmpty){
                                            monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                        }else{
                                            Assertions.assertThrows(functionGen.expectedException,
                                                    ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                                            monitor.verifyCollectionState();
                                        }
                                    }));
                        }
                    }
                }
            }
        }

        TestExecutorService.completeAllTests("BooleanSetImplTest.testforEach_Consumer");
    }
    
    @Test
    public void testhashCode_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyHashCode();
        test.runAllTests("BooleanSetImplTest.testhashCode_void");
    }
    
    @Test
    public void testisEmpty_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyIsEmpty();
        test.runAllTests("BooleanSetImplTest.testisEmpty_void");
    }
    
    @Test
    public void testequals_Object(){
        final BasicTest test=(checkedType,initSet)->{
            try {
                Assertions.assertFalse(initSet.initialize(new BooleanSetImplMonitor(checkedType))
                        .getCollection().equals(null));
            }catch(NotYetImplementedException e) {
                //do nothing
            }
           
            };
        test.runAllTests("BooleanSetImplTest.testequals_Object");
    }
    
    @Test
    public void testiterator_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .getMonitoredIterator()
                .verifyIteratorState();
        test.runAllTests("BooleanSetImplTest.testiterator_void");
    }
    
    @Test
    public void testItrclone_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .getMonitoredIterator()
                .verifyClone();
        test.runAllTests("BooleanSetImplTest.testItrclone_void");
    }
    
    @Test
    public void testItrforEachRemaining_Consumer(){
        for(final var checkedType:CheckedType.values()){
            for(var initSet:VALID_INIT_SEQS){
                for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || initSet.isEmpty){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null || initSet.isEmpty){
                                int itrScenarioMax=0;
                                if(initSet == SetInitialization.AddTrueAndFalse){
                                    itrScenarioMax=2;
                                }
                                for(int tmpItrScenario=0;tmpItrScenario<=itrScenarioMax;++tmpItrScenario) {
                                  final int itrScenario=tmpItrScenario;
                                  final long randMax= preMod.expectedException == null && functionGen.randomized
                                      && initSet == SetInitialization.AddTrueAndFalse
                                      && itrScenario == 0?100:0;
                                  for(long tmpRandSeed=0;tmpRandSeed<=randMax;++tmpRandSeed) {
                                    final long randSeed=tmpRandSeed;
                                    for(final var functionCallType:FunctionCallType.values()){
                                      //TestExecutorService.submitTest(()->{
                                      if(checkedType.checked
                                          &&initSet==SetInitialization.AddFalse
                                          &&functionGen==MonitoredFunctionGen.ModCollection
                                          &&preMod==IllegalModification.ModCollection
                                          &&itrScenario==0
                                          &&randSeed==0
                                          &&functionCallType==FunctionCallType.Boxed) {
                                        TestExecutorService.suspend();
                                      }
                                          final var setMonitor=initSet
                                                  .initialize(new BooleanSetImplMonitor(checkedType));
                                          final var itrMonitor=setMonitor.getMonitoredIterator();
                                          int adjustedState;
                                          switch(itrScenario){
                                          case 1:
                                              itrMonitor.iterateForward();
                                              adjustedState=0b10;
                                              break;
                                          case 2:
                                              itrMonitor.iterateForward();
                                              itrMonitor.remove();
                                              adjustedState=0b10;
                                              break;
                                          default:
                                              switch(initSet){
                                              case AddFalse:
                                                  adjustedState=0b01;
                                                  break;
                                              case AddTrue:
                                                  adjustedState=0b10;
                                                  break;
                                              case AddTrueAndFalse:
                                                  adjustedState=0b11;
                                                  break;
                                              case Empty:
                                                  adjustedState=0b00;
                                                  break;
                                              default:
                                                  throw initSet.invalid();
                                              }
                                          }
                                          itrMonitor.illegalMod(preMod);
                                          final Class<? extends Throwable> expectedException=adjustedState == 0b00
                                                  ?null
                                                          :preMod.expectedException == null
                                                          ?functionGen.expectedException
                                                                  :preMod.expectedException;
                                          if(expectedException == null){
                                              itrMonitor.verifyForEachRemaining(functionGen,
                                                      functionCallType,randSeed);
                                          }else{
                                              Assertions.assertThrows(expectedException,
                                                      ()->itrMonitor.verifyForEachRemaining(functionGen,
                                                              functionCallType,randSeed));
                                              itrMonitor.verifyIteratorState();
                                              setMonitor.verifyCollectionState();
                                          }
                                      //});
                                  }
                                  }
                                }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrforEachRemaining_Consmer");
    }
    
    @Test
    public void testItrhasNext_void(){
        BasicTest test=(checkedType,initSet)->{
            final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            final var itrMonitor=setMonitor.getMonitoredIterator();
            final var setSize=setMonitor.size();
            for(int i=0;i < setSize;++i){
                Assertions.assertTrue(itrMonitor.verifyHasNext());
                itrMonitor.iterateForward();
            }
            Assertions.assertFalse(itrMonitor.verifyHasNext());
        };
        test.runAllTests("BooleanSetImplTest.testItrhasNext_void");
    }
    
    @Test
    public void testItrnext_void(){
        var outputTypes=DataType.BOOLEAN.validOutputTypes();
        for(var checkedType:CheckedType.values()) {
            for(var preMod:IteratorType.AscendingItr.validPreMods) {
                if(checkedType.checked || preMod.expectedException==null) {
                    for(var initSet:VALID_INIT_SEQS) {
                        if(preMod.expectedException==null) {
                            TestExecutorService.submitTest(()->{
                                final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                for(var outputType:outputTypes) {
                                    var itrMonitor=setMonitor.getMonitoredIterator();
                                    while(itrMonitor.hasNext()) {
                                        itrMonitor.verifyNext(outputType);
                                    }
                                    Assertions.assertFalse(itrMonitor.getIterator().hasNext());
                                    if(checkedType.checked) {
                                        Assertions.assertThrows(NoSuchElementException.class,()->itrMonitor.verifyNext(outputType));
                                    }
                                }
                            });
                        }else {
                            for(var outputType:outputTypes) {
                                TestExecutorService.submitTest(()->{
                                    final var setMonitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                    var itrMonitor=setMonitor.getMonitoredIterator();
                                    itrMonitor.illegalMod(preMod);
                                    Assertions.assertThrows(preMod.expectedException,()->itrMonitor.verifyNext(outputType));
                                });
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrnext_void");
    }
    
    @Test
    public void testItrremove_void(){
        for(final var checkedType:CheckedType.values()){
            for(var initSet:VALID_INIT_SEQS){
                final int setSize;
                switch(initSet){
                case Empty:
                    setSize=0;
                    break;
                case AddTrue:
                case AddFalse:
                    setSize=1;
                    break;
                case AddTrueAndFalse:
                    setSize=2;
                    break;
                default:
                    throw initSet.invalid();
                }
                for(final var itrRemoveScenario:IteratorType.AscendingItr.validItrRemoveScenarios){
                    if((setSize != 0 || itrRemoveScenario == IteratorRemoveScenario.PostInit)
                            && (checkedType.checked || itrRemoveScenario.expectedException == null)){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null){
                                int itrOffset,itrBound;
                                if(itrRemoveScenario == IteratorRemoveScenario.PostInit){
                                    itrOffset=itrBound=0;
                                }else{
                                    itrOffset=1;
                                    itrBound=setSize;
                                }
                                for(var itrCountTmp=itrOffset;itrCountTmp<=itrBound;++itrCountTmp) {
                                    final int itrCount=itrCountTmp;
                                        TestExecutorService.submitTest(()->{
                                            final var setMonitor=initSet
                                                    .initialize(new BooleanSetImplMonitor(checkedType));
                                            final var itrMonitor=setMonitor.getMonitoredIterator();
                                            for(int i=0;i < itrCount;++i){
                                                itrMonitor.iterateForward();
                                            }
                                            itrRemoveScenario.initialize(itrMonitor);
                                            itrMonitor.illegalMod(preMod);
                                            final Class<? extends Throwable> expectedException=itrRemoveScenario.expectedException == null
                                                    ?preMod.expectedException
                                                            :itrRemoveScenario.expectedException;
                                            if(expectedException == null){
                                                for(;;){
                                                    itrMonitor.verifyRemove();
                                                    if(!itrMonitor.hasNext()){
                                                        break;
                                                    }
                                                    itrMonitor.iterateForward();
                                                }
                                            }else{
                                                
                                                Assertions.assertThrows(expectedException,
                                                        ()->itrMonitor.verifyRemove());
                                                itrMonitor.verifyIteratorState();
                                                setMonitor.verifyCollectionState();
                                            }
                                        });
                                     }
                            }
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testItrremove_void");
    }
    
    @Test
    public void testReadAndWrite(){
        TestExecutorService.setNumWorkers(1);
        MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            if(functionGen.expectedException==null) {
                Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
            }else {
                
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
            }
        };
        test.runAllTests("BooleanSetImplTest.testReadAndWrite");
    }
    
    @Test
    public void testremoveIf_Predicate(){
        for(final var filterGen:StructType.BooleanSetImpl.validMonitoredRemoveIfPredicateGens){
            for(final var initSet:VALID_INIT_SEQS){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=filterGen.predicateGenCallType==PredicateGenCallType.Randomized
                            && initSet == SetInitialization.AddTrueAndFalse && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || filterGen.expectedException == null || initSet.isEmpty){
                            LongStream.rangeClosed(0,randSeedBound).forEach(
                                    randSeed->TestExecutorService.submitTest(()->{
                                        final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
                                        int state=monitor.expectedState;
                                        var filter=filterGen.getMonitoredRemoveIfPredicate(monitor,0.5,new Random(randSeed));
                                        if(filterGen.expectedException == null || state == 0b00){
                                            final boolean result=monitor.verifyRemoveIf(filter,functionCallType);
                                            if(state == 0b00){
                                                Assertions.assertFalse(result);
                                            }else{
                                                switch(filterGen){
                                                case RemoveAll:
                                                    Assertions.assertTrue(monitor.set.isEmpty());
                                                    Assertions.assertTrue(result);
                                                    break;
                                                case RemoveFalse:
                                                    Assertions.assertFalse(monitor.set.contains(false));
                                                    Assertions.assertEquals((state & 0b01) != 0,result);
                                                    break;
                                                case RemoveNone:
                                                    Assertions.assertFalse(result);
                                                    Assertions.assertFalse(monitor.set.isEmpty());
                                                    break;
                                                case RemoveTrue:
                                                    Assertions.assertFalse(monitor.set.contains(true));
                                                    Assertions.assertEquals((state & 0b10) != 0,result);
                                                    break;
                                                default:
                                                    throw filterGen.invalid();
                                                }
                                            }
                                        }else{
                                            Assertions.assertThrows(filterGen.expectedException,
                                                    ()->monitor.verifyRemoveIf(filter,functionCallType));
                                            monitor.verifyCollectionState();
                                        }
                                    }));
                        }
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testremoveIf_Predicate");
    }
    
    @Test
    public void testremoveVal_val(){
        final QueryTest test=(monitor,queryVal,inputType,castType,modification)->monitor.verifyRemoveVal(queryVal,
                inputType,castType,modification);
        test.runAllTests("BooleanSetImplTest.testremoveVal_val");
    }
    
    @Test
    public void testsize_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifySize();
        test.runAllTests("BooleanSetImplTest.testsize_void");
    }
    
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,initSet)->{
            final var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            if(functionGen.expectedException == null){
                monitor.verifyToArray(functionGen);
            }else{
                Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyToArray(functionGen));
                monitor.verifyCollectionState();
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_IntFunction");
    }
    
    @Test
    public void testtoArray_ObjectArray(){
        final BasicTest test=(checkedType,initSet)->{
            int bound;
            switch(initSet){
            case Empty:
                bound=2;
                break;
            case AddTrue:
            case AddFalse:
                bound=3;
                break;
            case AddTrueAndFalse:
                bound=4;
                break;
            default:
                throw initSet.invalid();
            }
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            for(int arrSize=0;arrSize <= bound;++arrSize){
                monitor.verifyToArray(new Object[arrSize]);
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_ObjectArray");
    }
    
    @Test
    public void testtoArray_void(){
        final BasicTest test=(checkedType,initSet)->{
            var monitor=initSet.initialize(new BooleanSetImplMonitor(checkedType));
            for(var outputType:DataType.BOOLEAN.validOutputTypes()) {
                outputType.verifyToArray(monitor);
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_void");
    }
    
    @Test
    public void testtoString_void(){
        final BasicTest test=(checkedType,initSet)->initSet.initialize(new BooleanSetImplMonitor(checkedType))
                .verifyToString();
        test.runAllTests("BooleanSetImplTest.testtoString_void");
    }
    private static interface BasicTest{
        private void runAllTests(String testName){
            for(final var checkedType:CheckedType.values()){
                for(final var initSet:VALID_INIT_SEQS){
                    TestExecutorService.submitTest(()->runTest(checkedType,initSet));
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(CheckedType checkedType,SetInitialization initSet);
    }
    private static interface MonitoredFunctionGenTest{
        private void runAllTests(String testName){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var checkedType:CheckedType.values()){
                    if(checkedType.checked || functionGen.expectedException == null){
                        for(final var initSet:VALID_INIT_SEQS){
                            TestExecutorService.submitTest(()->runTest(functionGen,checkedType,initSet));
                        }
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(MonitoredFunctionGen functionGen,CheckedType checkedType,SetInitialization initSet);
    }
    private static interface QueryTest{
        boolean callMethod(BooleanSetImplMonitor monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
                QueryVal.QueryValModification modification);
        private void runAllTests(String testName){
            for(final var queryVal:QueryVal.values()){
                if(DataType.BOOLEAN.isValidQueryVal(queryVal)){
                    queryVal.validQueryCombos.forEach((modification,castTypesToInputTypes)->{
                        castTypesToInputTypes.forEach((castType,inputTypes)->{
                            inputTypes.forEach(inputType->{
                                final boolean queryCanReturnTrue=queryVal.queryCanReturnTrue(modification,castType,
                                        inputType,DataType.BOOLEAN);
                                for(final var checkedType:CheckedType.values()){
                                    for(final var initSet:VALID_INIT_SEQS){
                                        TestExecutorService.submitTest(()->runTest(queryCanReturnTrue,queryVal,
                                                modification,inputType,castType,checkedType,initSet));
                                    }
                                }
                            });
                        });
                    });
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        private void runTest(boolean queryCanReturnTrue,QueryVal queryVal,QueryVal.QueryValModification modification,
                DataType inputType,QueryCastType castType,CheckedType checkedType,SetInitialization initSet){
            boolean expectedResult;
            if(queryCanReturnTrue){
                final boolean booleanInputVal=queryVal.getBooleanVal(modification);
                switch(initSet){
                case Empty:
                    expectedResult=false;
                    break;
                case AddTrue:
                    expectedResult=booleanInputVal;
                    break;
                case AddFalse:
                    expectedResult=!booleanInputVal;
                    break;
                case AddTrueAndFalse:
                    expectedResult=true;
                    break;
                default:
                    throw initSet.invalid();
                }
            }else{
                expectedResult=false;
            }
            final boolean actualResult=callMethod(initSet.initialize(new BooleanSetImplMonitor(checkedType)),queryVal,
                    inputType,
                    castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
    
}
