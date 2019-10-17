package omni.impl.set;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import omni.api.OmniIterator;
import omni.api.OmniNavigableSet;
import omni.api.OmniNavigableSet.OfBoolean;
import omni.function.BooleanComparator;
import omni.impl.CheckedType;
import omni.impl.DataType;
import omni.impl.FunctionCallType;
import omni.impl.IteratorRemoveScenario;
import omni.impl.IteratorType;
import omni.impl.MonitoredCollection;
import omni.impl.MonitoredFunction;
import omni.impl.MonitoredFunctionGen;
import omni.impl.MonitoredNavigableSet;
import omni.impl.MonitoredObjectOutputStream;
import omni.impl.MonitoredRemoveIfPredicate;
import omni.impl.MonitoredRemoveIfPredicateGen.PredicateGenCallType;
import omni.impl.MonitoredSortedSet;
import omni.impl.QueryCastType;
import omni.impl.QueryVal;
import omni.impl.QueryVal.QueryValModification;
import omni.impl.set.BooleanSetImpl.Checked;
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
          	  switch(this.expectedItrState&0b11) {
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

        final StructType structType;
        
        SingleViewItrMonitor(StructType structType,BooleanSetImplMonitor root,OmniIterator.OfBoolean itr,IteratorType itrType,int expectedItrState){
            super(root,itr,itrType,expectedItrState);
            this.structType=structType;
        }
        
        @Override
        public void updateItrNextState(){
            if(root.checkedType.checked) {
                expectedItrState=0b01;
            }else {
                expectedItrState=0b00;
            }
            if(structType==StructType.BooleanSetTrueView) {
                lastRet=1;
            }else {
                lastRet=0;
            }
        }

        @Override
        public void verifyNextResult(DataType outputType,Object result){
            Assertions.assertEquals(outputType.convertVal(structType==StructType.BooleanSetTrueView),result);
            
        }

        @Override
        public void updateItrRemoveState(){
            this.lastRet=-1;
            if(root.checkedType.checked) {
                expectedItrState=0b00;
            }
            if(structType==StructType.BooleanSetTrueView) {
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
                Assertions.assertEquals(structType==StructType.BooleanSetTrueView,function.get(0));
                if(root.checkedType.checked) {
                    expectedItrState=0b01;
                }else {
                    expectedItrState=0b00;
                }
                if(structType==StructType.BooleanSetTrueView) {
                    this.lastRet=1;
                }else {
                    this.lastRet=0;
                }
            }
            
        }
    
        @Override
        public void verifyCloneHelper(Object clone){
          Assertions.assertEquals(this.expectedItrState,FieldAndMethodAccessor.BooleanSetImpl.UncheckedAscendingFullItr.itrState(itr));
          Assertions.assertSame(this.root.set,FieldAndMethodAccessor.BooleanSetImpl.UncheckedAscendingFullItr.root(itr));

        }
    }
    
    
    
    private static class BooleanSetImplMonitor extends AbstractBooleanSetMonitor{
        int expectedState;
        private static BooleanSetImpl getCopyConstructorSet(CheckedType checkedType,SortOrder sortOrder,Collection<?> collection,Class<? extends Collection<?>> collectionClass) {
          Class<? extends BooleanSetImpl> clazz;
          switch(sortOrder) {
          case Ascending:
        	  if(checkedType.checked) {
                  clazz=BooleanSetImpl.Checked.class;
              }else {
                  clazz=BooleanSetImpl.class;
              }
        	  break;
          case Descending:
        	  if(checkedType.checked) {
                  clazz=BooleanSetImpl.Descending.Checked.class;
              }else {
                  clazz=BooleanSetImpl.Descending.class;
              }
        	  break;
          default:
        	  throw sortOrder.invalid();
          }
         
          try{
              return clazz.getDeclaredConstructor(collectionClass).newInstance(collection);
          }catch(InstantiationException | IllegalAccessException | IllegalArgumentException
                  | InvocationTargetException | NoSuchMethodException | SecurityException e){
              throw new Error(e);
          }
        }
        
        private static BooleanSetImpl getRoot(CheckedType checkedType,SortOrder sortOrder, int expectedState) {
        	switch(sortOrder) {
        	case Ascending:
        		if(checkedType.checked) {
        			return new BooleanSetImpl.Checked(expectedState);
        		}else {
        			return new BooleanSetImpl(expectedState);
        		}
        	case Descending:
        		if(checkedType.checked) {
        			return new BooleanSetImpl.Descending.Checked(expectedState);
        		}else {
        			return new BooleanSetImpl.Descending(expectedState);
        		}
        	default:
        		throw sortOrder.invalid();
        	}
        }
        private static BooleanSetImpl getRoot(CheckedType checkedType,SortOrder sortOrder) {
        	switch(sortOrder) {
        	case Ascending:
        		if(checkedType.checked) {
        			return new BooleanSetImpl.Checked();
        		}else {
        			return new BooleanSetImpl();
        		}
        	case Descending:
        		if(checkedType.checked) {
        			return new BooleanSetImpl.Descending.Checked();
        		}else {
        			return new BooleanSetImpl.Descending();
        		}
        	default:
        		throw sortOrder.invalid();
        	}
        }
        
      BooleanSetImplMonitor(CheckedType checkedType,SortOrder sortOrder,Collection<?> collection,Class<? extends Collection<?>> collectionClass){
          super(getCopyConstructorSet(checkedType,sortOrder,collection,collectionClass),checkedType,sortOrder);
          this.updateCollectionState();
      
      }
      BooleanSetImplMonitor(CheckedType checkedType,SortOrder sortOrder){
    	  this(getRoot(checkedType,sortOrder),0,checkedType,sortOrder);
      }
      private void validateAscendingHigher(Object actual,Object input,DataType outputType) {
    	  switch(expectedState) {
    	  case 0b00:
    		  Assertions.assertEquals(outputType.defaultVal, actual);
    		  break;
    	  case 0b01:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    		  case CHAR:
    			  Assertions.assertEquals(outputType.defaultVal, actual);
    			  break;
    		  case BYTE:
    			  if((byte)input<0) {
    				  Assertions.assertEquals((byte)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<0) {
    				  Assertions.assertEquals((short)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<0) {
    				  Assertions.assertEquals((int)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<0) {
    				  Assertions.assertEquals((long)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<0) {
    				  Assertions.assertEquals((float)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<0) {
    				  Assertions.assertEquals((double)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  
    		  default:
    			  throw outputType.invalid();
    		  }
    		  break;
    	  case 0b10:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals(Boolean.TRUE, actual);
    			  }
    			  break;
    		  case BYTE:
    			  if((byte)input<1) {
    				  Assertions.assertEquals((byte)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<1) {
    				  Assertions.assertEquals((char)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<1) {
    				  Assertions.assertEquals((short)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<1) {
    				  Assertions.assertEquals((int)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<1) {
    				  Assertions.assertEquals((long)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<1) {
    				  Assertions.assertEquals((float)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<1) {
    				  Assertions.assertEquals((double)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  default:
    			  throw outputType.invalid();
    		  }
    	  case 0b11:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals(Boolean.TRUE, actual);
    			  }
    			  break;
    		  case BYTE:
    			  switch(Integer.signum((byte)input)) {
    			  case -1:
    				  Assertions.assertEquals((byte)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((byte)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input==0) {
    				  Assertions.assertEquals((char)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  switch(Integer.signum((short)input)) {
    			  case -1:
    				  Assertions.assertEquals((short)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((short)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  switch(Integer.signum((int)input)) {
    			  case -1:
    				  Assertions.assertEquals((int)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((int)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  switch(Long.signum((long)input)) {
    			  case -1:
    				  Assertions.assertEquals((long)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((long)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    		  {
    			  float f;
    			  if((f=(float)input)<0) {
    				  Assertions.assertEquals((float)0.0, actual);
    			  }else if(f<1) {
    				  Assertions.assertEquals((float)1.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  case DOUBLE:
    		  {
    			  double d;
    			  if((d=(double)input)<0) {
    				  Assertions.assertEquals((double)0.0, actual);
    			  }else if(d<1) {
    				  Assertions.assertEquals((double)1.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  }
    	  default:
    		  throw illegalState();
    	  }
      }
      private void validateAscendingLower(Object actual,Object input,DataType outputType) {
    	  switch(expectedState) {
    	  case 0b00:
    		  Assertions.assertEquals(outputType.defaultVal, actual);
    		  break;
    	  case 0b01:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(Boolean.FALSE, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case BYTE:
    			  if((byte)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((byte)0, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((char)0, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((short)0, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((int)0, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((long)0, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((float)0, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<=0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((double)0, actual);
    			  }
    			  break;
    		  
    		  default:
    			  throw outputType.invalid();
    		  }
    		  break;
    	  case 0b10:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  Assertions.assertEquals(outputType.defaultVal, actual);
    			  break;
    		  case BYTE:
    			  if((byte)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((byte)1, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((char)1, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((short)1, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((int)1, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((long)1, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((float)1, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<=1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((double)1, actual);
    			  }
    			  break;
    		  default:
    			  throw outputType.invalid();
    		  }
    	  case 0b11:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(Boolean.FALSE, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case BYTE:
    			  switch(Integer.signum(((byte)input)-1)){
    				  case 1:
    					  Assertions.assertEquals((byte)1, actual);
    					  break;
    				  case 0:
    					  Assertions.assertEquals((byte)0, actual);
    					  break;
    				  default:
    					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  switch(Integer.signum(((char)input)-1)){
				  case 1:
					  Assertions.assertEquals((char)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((char)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  switch(Integer.signum(((short)input)-1)){
				  case 1:
					  Assertions.assertEquals((short)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((short)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  switch(Integer.signum(((int)input)-1)){
				  case 1:
					  Assertions.assertEquals((int)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((int)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  switch(Long.signum(((long)input)-1)){
				  case 1:
					  Assertions.assertEquals((long)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((long)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    		  {
    			  float f;
    			  if((f=(float)input)!=f || f>1) {
    				  Assertions.assertEquals((float)1.0, actual);
    			  }else if(f>0) {
    				  Assertions.assertEquals((float)0.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  case DOUBLE:
    		  {
    			  double d;
    			  if((d=(double)input)!=d || d>1) {
    				  Assertions.assertEquals((double)1.0, actual);
    			  }else if(d>0) {
    				  Assertions.assertEquals((double)0.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  }
    	  default:
    		  throw illegalState();
    	  }
      }
      private void validateAscendingCeiling(Object actual,Object input,DataType outputType) {
    	  switch(expectedState) {
    	  case 0b00:
    		  Assertions.assertEquals(outputType.defaultVal, actual);
    		  break;
    	  case 0b01:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals(Boolean.FALSE, actual);
    			  }
    			  break;
    		  case BYTE:
    			  if((byte)input<=0) {
    				  Assertions.assertEquals((byte)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<=0) {
    				  Assertions.assertEquals((char)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<=0) {
    				  Assertions.assertEquals((short)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<=0) {
    				  Assertions.assertEquals((int)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<=0) {
    				  Assertions.assertEquals((long)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<=0) {
    				  Assertions.assertEquals((float)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<=0) {
    				  Assertions.assertEquals((double)0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  
    		  default:
    			  throw outputType.invalid();
    		  }
    		  break;
    	  case 0b10:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  Assertions.assertEquals(Boolean.TRUE, actual);
    		  case BYTE:
    			  if((byte)input<=1) {
    				  Assertions.assertEquals((byte)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<=1) {
    				  Assertions.assertEquals((char)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<=1) {
    				  Assertions.assertEquals((short)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<=1) {
    				  Assertions.assertEquals((int)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<=1) {
    				  Assertions.assertEquals((long)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<=1) {
    				  Assertions.assertEquals((float)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<=1) {
    				  Assertions.assertEquals((double)1, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  default:
    			  throw outputType.invalid();
    		  }
    	  case 0b11:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  Assertions.assertEquals((boolean)input, actual);
    			  break;
    		  case BYTE:
    			  switch(Integer.signum((byte)input-1)) {
    			  case -1:
    				  Assertions.assertEquals((byte)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((byte)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  switch(Integer.signum((char)input-1)) {
    			  case -1:
    				  Assertions.assertEquals((char)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((char)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  switch(Integer.signum((short)input-1)) {
    			  case -1:
    				  Assertions.assertEquals((short)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((short)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  switch(Integer.signum((int)input-1)) {
    			  case -1:
    				  Assertions.assertEquals((int)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((int)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  switch(Long.signum((long)input-1)) {
    			  case -1:
    				  Assertions.assertEquals((long)0, actual);
    				  break;
    			  case 0:
    				  Assertions.assertEquals((long)1, actual);
    				  break;
    			  default:
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    		  {
    			  float f;
    			  if((f=(float)input)<=0) {
    				  Assertions.assertEquals((float)0.0, actual);
    			  }else if(f<=1) {
    				  Assertions.assertEquals((float)1.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  case DOUBLE:
    		  {
    			  double d;
    			  if((d=(double)input)<=0) {
    				  Assertions.assertEquals((double)0.0, actual);
    			  }else if(d<=1) {
    				  Assertions.assertEquals((double)1.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  }
    	  default:
    		  throw illegalState();
    	  }
      }
      private void validateAscendingFloor(Object actual,Object input,DataType outputType) {
    	  switch(expectedState) {
    	  case 0b00:
    		  Assertions.assertEquals(outputType.defaultVal, actual);
    		  break;
    	  case 0b01:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  Assertions.assertEquals(Boolean.FALSE, actual);
    			  break;
    		  case BYTE:
    			  if((byte)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((byte)0, actual);
    			  }
    			  break;
    		  case CHAR:
    			  Assertions.assertEquals((char)0, actual);
    			  break;
    		  case SHORT:
    			  if((short)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((short)0, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((int)0, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((long)0, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((float)0, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<0) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((double)0, actual);
    			  }
    			  break;
    		  
    		  default:
    			  throw outputType.invalid();
    		  }
    		  break;
    	  case 0b10:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  if((boolean)input) {
    				  Assertions.assertEquals(Boolean.TRUE, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case BYTE:
    			  if((byte)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((byte)1, actual);
    			  }
    			  break;
    		  case CHAR:
    			  if((char)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((char)1, actual);
    			  }
    			  break;
    		  case SHORT:
    			  if((short)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((short)1, actual);
    			  }
    			  break;
    		  case INT:
    			  if((int)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((int)1, actual);
    			  }
    			  break;
    		  case LONG:
    			  if((long)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((long)1, actual);
    			  }
    			  break;
    		  case FLOAT:
    			  if((float)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((float)1, actual);
    			  }
    			  break;
    		  case DOUBLE:
    			  if((double)input<1) {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }else {
    				  Assertions.assertEquals((double)1, actual);
    			  }
    			  break;
    		  default:
    			  throw outputType.invalid();
    		  }
    	  case 0b11:
    		  switch(outputType) {
    		  case BOOLEAN:
    		  case REF:
    			  Assertions.assertEquals((boolean)input, actual);
    			  break;
    		  case BYTE:
    			  switch(Integer.signum(((byte)input))){
    				  case 1:
    					  Assertions.assertEquals((byte)1, actual);
    					  break;
    				  case 0:
    					  Assertions.assertEquals((byte)0, actual);
    					  break;
    				  default:
    					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case CHAR:
    			  switch(Integer.signum(((char)input))){
				  case 1:
					  Assertions.assertEquals((char)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((char)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case SHORT:
    			  switch(Integer.signum(((short)input))){
				  case 1:
					  Assertions.assertEquals((short)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((short)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case INT:
    			  switch(Integer.signum(((int)input))){
				  case 1:
					  Assertions.assertEquals((int)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((int)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case LONG:
    			  switch(Long.signum(((long)input))){
				  case 1:
					  Assertions.assertEquals((long)1, actual);
					  break;
				  case 0:
					  Assertions.assertEquals((long)0, actual);
					  break;
				  default:
					  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  case FLOAT:
    		  {
    			  float f;
    			  if((f=(float)input)!=f || f>=1) {
    				  Assertions.assertEquals((float)1.0, actual);
    			  }else if(f>=0) {
    				  Assertions.assertEquals((float)0.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  case DOUBLE:
    		  {
    			  double d;
    			  if((d=(double)input)!=d || d>=1) {
    				  Assertions.assertEquals((double)1.0, actual);
    			  }else if(d>=0) {
    				  Assertions.assertEquals((double)0.0, actual);
    			  }else {
    				  Assertions.assertEquals(outputType.defaultVal, actual);
    			  }
    			  break;
    		  }
    		  }
    	  default:
    		  throw illegalState();
    	  }
      }
      
      
      @Override public Object verifyFirst(DataType outputType) {
    	  Object actualFirst=callFirst(outputType);
    	  boolean expected;
    	  switch(expectedState) {
    	  case 0b01:
    		  expected=false;
    		  break;
    	  case 0b10:
    		  expected=true;
    		  break;
    	  case 0b11:
    		  switch(this.sortOrder) {
    		  case Ascending:
    			  expected=false;
    			  break;
    		  case Descending:
    			  expected=true;
    			  break;
    		  default:
    			  throw sortOrder.invalid();
    		  }
    		  break;
    	  default:
    		  throw illegalState();
    	  }
    	  Assertions.assertEquals(outputType.convertVal(expected),actualFirst);
          return actualFirst;
      }
      @Override public Object verifyLast(DataType outputType) {
    	  Object actualLast=callLast(outputType);
    	  boolean expected;
    	  switch(expectedState) {
    	  case 0b01:
    		  expected=false;
    		  break;
    	  case 0b10:
    		  expected=true;
    		  break;
    	  case 0b11:
    		  switch(this.sortOrder) {
    		  case Ascending:
    			  expected=true;
    			  break;
    		  case Descending:
    			  expected=false;
    			  break;
    		  default:
    			  throw sortOrder.invalid();
    		  }
    		  break;
    	  default:
    		  throw illegalState();
    	  }
    	  Assertions.assertEquals(outputType.convertVal(expected),actualLast);
          return actualLast;
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
        
        BooleanSetImplMonitor(CheckedType checkedType,SortOrder sortOrder,int expectedState){
        	this(getRoot(checkedType,sortOrder,expectedState),expectedState,checkedType,sortOrder);
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

		@Override
		public Object verifyLower(Object input, DataType outputType) {
			Object actual=callLower(outputType, input);
			switch(sortOrder) {
			case Ascending:
				validateAscendingLower(actual,input,outputType);
				break;
			case Descending:
				validateAscendingHigher(actual, input, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}

		@Override
		public Object verifyHigher(Object input, DataType outputType) {
			Object actual=callHigher(outputType, input);
			switch(sortOrder) {
			case Ascending:
				validateAscendingHigher(actual,input,outputType);
				break;
			case Descending:
				validateAscendingLower(actual, input, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}

		@Override
		public Object verifyCeiling(Object input, DataType outputType) {
			Object actual=callCeiling(outputType, input);
			switch(sortOrder) {
			case Ascending:
				validateAscendingCeiling(actual,input,outputType);
				break;
			case Descending:
				validateAscendingFloor(actual, input, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}

		@Override
		public Object verifyFloor(Object input, DataType outputType) {
			Object actual=callFloor(outputType, input);
			switch(sortOrder) {
			case Ascending:
				validateAscendingFloor(actual,input,outputType);
				break;
			case Descending:
				validateAscendingCeiling(actual, input, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		private void validateAscendingPollFirst(Object actual,DataType outputType) {
			switch(expectedState) {
			case 0b00:
				Assertions.assertEquals(outputType.defaultVal, actual);
				break;
			case 0b01:
				Assertions.assertEquals(outputType.convertVal(false), actual);
				expectedState=0b00;
				break;
			case 0b10:
				Assertions.assertEquals(outputType.convertVal(true), actual);
				expectedState=0b00;
				break;
			case 0b11:
				Assertions.assertEquals(outputType.convertVal(false), actual);
				expectedState=0b10;
				break;
			default:
				throw illegalState();
			}
		}
		private void validateAscendingPollLast(Object actual,DataType outputType) {
			switch(expectedState) {
			case 0b00:
				Assertions.assertEquals(outputType.defaultVal, actual);
				break;
			case 0b01:
				Assertions.assertEquals(outputType.convertVal(false), actual);
				expectedState=0b00;
				break;
			case 0b10:
				Assertions.assertEquals(outputType.convertVal(true), actual);
				expectedState=0b00;
				break;
			case 0b11:
				Assertions.assertEquals(outputType.convertVal(true), actual);
				expectedState=0b01;
				break;
			default:
				throw illegalState();
			}
		}
		@Override
		public Object verifyPollFirst(DataType outputType) {
			Object ret=this.callPollFirst(outputType);
			switch(sortOrder) {
			case Ascending:
				validateAscendingPollFirst(ret,outputType);
				break;
			case Descending:
				validateAscendingPollLast(ret,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return ret;
		}

		@Override
		public Object verifyPollLast(DataType outputType) {
			Object ret=this.callPollLast(outputType);
			switch(sortOrder) {
			case Ascending:
				validateAscendingPollLast(ret,outputType);
				break;
			case Descending:
				validateAscendingPollFirst(ret,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return ret;
		}

		@Override
		public MonitoredIterator<? extends OmniIterator.OfBoolean,OmniNavigableSet.OfBoolean> descendingIterator() {
			OmniIterator.OfBoolean itr=set.descendingIterator();
            IteratorType iType;
            switch(this.sortOrder) {
            case Ascending:
                iType=IteratorType.DescendingItr;;
                break;
            case Descending:
                iType=IteratorType.AscendingItr;
                break;
            default:
                throw this.sortOrder.invalid();
            }
            if(this.expectedState==0b00) {
                return new EmptyItrMonitor(itr,this,iType);
            }
            return new FullItrMonitor(this,itr,iType,this.expectedState);
		}

		@Override
		public AbstractBooleanSetMonitor descendingSet() {
			try
			{  
				var descendingSet=new FullViewMonitor(set.descendingSet(),this,checkedType,sortOrder==SortOrder.Ascending?SortOrder.Descending:SortOrder.Ascending);
				descendingSet.verifyCollectionState();
				return descendingSet;
			}finally {
				verifyCollectionState();
			}
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, boolean fromInclusive,
				Object toElement, boolean toInclusive, DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			if(fromB){
				if(toB) {
					if(fromInclusive) {
						var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
						if(toInclusive) {
							subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
						}else {
							subSetMonitor=new EmptyViewMonitor(subSet,this,sortOrder==SortOrder.Ascending?1:2,checkedType,sortOrder);
						}
					}else {
						if(toInclusive) {
							subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),this,sortOrder==SortOrder.Ascending?2:1,checkedType,sortOrder);
						}else {
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						}
					}
				}else {
					if(fromInclusive) {
						if(toInclusive) {
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							switch(sortOrder) {
							case Ascending:
								subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
								break;
							case Descending:
								Assertions.assertSame(subSet,set);
								return this;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(checkedType.checked) {
									Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
								}
								return null;
							case Descending:
								subSetMonitor=new SingleViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),this,StructType.BooleanSetTrueView,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						switch(sortOrder) {
						case Ascending:
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						case Descending:
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							if(toInclusive) {
								subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
							}else {
								subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
				}
			}else {
				if(toB) {
					if(fromInclusive) {
						if(toInclusive) {
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							switch(sortOrder) {
							case Ascending:
								Assertions.assertSame(subSet,set);
								return this;
							case Descending:
								subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								subSetMonitor=new SingleViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),this,StructType.BooleanSetFalseView,checkedType,sortOrder);
								break;
							case Descending:
								if(checkedType.checked) {
									Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
								}
								return null;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						switch(sortOrder) {
						case Ascending:
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							if(toInclusive) {
								subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
							}else {
								subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
							}
							break;
						case Descending:
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						default:
							throw sortOrder.invalid();
						}
					}
				}else {
					if(fromInclusive) {
						var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
						if(toInclusive) {
							subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
							
						}else {
							subSetMonitor=new EmptyViewMonitor(subSet,this,sortOrder==SortOrder.Ascending?0:1,checkedType,sortOrder);
						}
					}else {
						if(toInclusive) {
							subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),this,sortOrder==SortOrder.Ascending?1:0,checkedType,sortOrder);
						}else {
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						}
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callHeadSet(toB,inclusive,functionCallType);
			if(toB) {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,2,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,0,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callTailSet(fromB,inclusive,functionCallType);
			if(fromB) {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
						
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,2,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,0,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> subSet(Object fromElement, Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callSubSet(fromB,toB,functionCallType);

			if(fromB){
				if(toB) {
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(toB) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,this,1,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> headSet(Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callHeadSet(toB,functionCallType);
			if(toB) {
				switch(sortOrder) {
				case Ascending:
					Assertions.assertSame(subSet,set);
					return this;
				case Descending:
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
					break;
				default:
					throw sortOrder.invalid();
				}
			}else {
				switch(sortOrder) {
				case Ascending:
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
					break;
				case Descending:
					Assertions.assertSame(subSet,set);
					return this;
				default:
					throw sortOrder.invalid();
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> tailSet(Object fromElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callTailSet(fromB,functionCallType);
			if(fromB) {
				switch(sortOrder) {
				case Ascending:
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetTrueView,checkedType,sortOrder);
					break;
				case Descending:
					Assertions.assertSame(subSet,set);
					return this;
					
				default:
					throw sortOrder.invalid();
				}
			}else {
				switch(sortOrder) {
				case Ascending:
					Assertions.assertSame(subSet,set);
					return this;
				case Descending:
					subSetMonitor=new SingleViewMonitor(subSet,this,StructType.BooleanSetFalseView,checkedType,sortOrder);
					break;
				default:
					throw sortOrder.invalid();
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

        
    }
    private static class FullViewMonitor extends AbstractBooleanSetMonitor{
        final BooleanSetImplMonitor root;
        FullViewMonitor(OmniNavigableSet.OfBoolean set,BooleanSetImplMonitor root,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.root=root;
        }
        public Object verifyClone(){
            final Object clone;
            try{
            	switch(this.sortOrder) {
            	case Ascending:
            		if(checkedType.checked) {
            			clone=FieldAndMethodAccessor.BooleanSetImpl.AscendingView.Checked.clone(set);
            		}else {
            			clone=FieldAndMethodAccessor.BooleanSetImpl.AscendingView.clone(set);
            		}
            		break;
            	case Descending:
            		if(checkedType.checked) {
            			clone=FieldAndMethodAccessor.BooleanSetImpl.DescendingView.Checked.clone(set);
            		}else {
            			clone=FieldAndMethodAccessor.BooleanSetImpl.DescendingView.clone(set);
            		}
            		break;
            	default:
            		throw sortOrder.invalid();
            	}
                
            }finally{
                verifyCollectionState();
            }
            verifyClone(clone);
            return clone;
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
		@Override
		public Object verifyLower(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingLower(actual, input, outputType);
				break;
			case Descending:
				root.validateAscendingHigher(actual,input,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public Object verifyHigher(Object input, DataType outputType) {
			Object actual=callHigher(outputType,input);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingHigher(actual, input, outputType);
				break;
			case Descending:
				root.validateAscendingLower(actual,input,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public Object verifyCeiling(Object input, DataType outputType) {
			Object actual=callCeiling(outputType,input);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingCeiling(actual, input, outputType);
				break;
			case Descending:
				root.validateAscendingFloor(actual,input,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public Object verifyFloor(Object input, DataType outputType) {
			Object actual=callFloor(outputType,input);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingFloor(actual, input, outputType);
				break;
			case Descending:
				root.validateAscendingCeiling(actual,input,outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public Object verifyPollFirst(DataType outputType) {
			Object actual=callPollFirst(outputType);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingPollFirst(actual, outputType);
				break;
			case Descending:
				root.validateAscendingPollLast(actual, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public Object verifyPollLast(DataType outputType) {
			Object actual=callPollLast(outputType);
			switch(sortOrder) {
			case Ascending:
				root.validateAscendingPollLast(actual, outputType);
				break;
			case Descending:
				root.validateAscendingPollFirst(actual, outputType);
				break;
			default:
				throw sortOrder.invalid();
			}
			return actual;
		}
		@Override
		public MonitoredIterator<? extends OmniIterator.OfBoolean, OfBoolean> descendingIterator() {
			OmniIterator.OfBoolean itr=set.descendingIterator();
            IteratorType iType;
            switch(this.sortOrder) {
            case Ascending:
                iType=IteratorType.DescendingItr;;
                break;
            case Descending:
                iType=IteratorType.AscendingItr;
                break;
            default:
                throw this.sortOrder.invalid();
            }
            if(root.expectedState==0b00) {
                return new EmptyItrMonitor(itr,this,iType);
            }
            return new FullItrMonitor(root,itr,iType,root.expectedState);
		}
		@Override
		public AbstractBooleanSetMonitor descendingSet() {
			try {
				Assertions.assertSame(set.descendingSet(), root.set);
			}finally {
				verifyCollectionState();
			}
			return root;
		}
		
		@Override
		public Object verifyFirst(DataType outputType) {
	    	  Object actualFirst=callFirst(outputType);
	    	  boolean expected;
	    	  switch(root.expectedState) {
	    	  case 0b01:
	    		  expected=false;
	    		  break;
	    	  case 0b10:
	    		  expected=true;
	    		  break;
	    	  case 0b11:
	    		  switch(this.sortOrder) {
	    		  case Ascending:
	    			  expected=false;
	    			  break;
	    		  case Descending:
	    			  expected=true;
	    			  break;
	    		  default:
	    			  throw sortOrder.invalid();
	    		  }
	    		  break;
	    	  default:
	    		  throw root.illegalState();
	    	  }
	    	  Assertions.assertEquals(outputType.convertVal(expected),actualFirst);
	          return actualFirst;
	      }
		@Override
		public Object verifyLast(DataType outputType) {
	    	  Object actualLast=callLast(outputType);
	    	  boolean expected;
	    	  switch(root.expectedState) {
	    	  case 0b01:
	    		  expected=false;
	    		  break;
	    	  case 0b10:
	    		  expected=true;
	    		  break;
	    	  case 0b11:
	    		  switch(this.sortOrder) {
	    		  case Ascending:
	    			  expected=true;
	    			  break;
	    		  case Descending:
	    			  expected=false;
	    			  break;
	    		  default:
	    			  throw sortOrder.invalid();
	    		  }
	    		  break;
	    	  default:
	    		  throw root.illegalState();
	    	  }
	    	  Assertions.assertEquals(outputType.convertVal(expected),actualLast);
	          return actualLast;
	      }

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, boolean fromInclusive,
				Object toElement, boolean toInclusive, DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			if(fromB){
				if(toB) {
					if(fromInclusive) {
						var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
						if(toInclusive) {
							subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
						}else {
							subSetMonitor=new EmptyViewMonitor(subSet,root,sortOrder==SortOrder.Ascending?1:2,checkedType,sortOrder);
						}
					}else {
						if(toInclusive) {
							subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?2:1,checkedType,sortOrder);
						}else {
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						}
					}
				}else {
					if(fromInclusive) {
						if(toInclusive) {
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							switch(sortOrder) {
							case Ascending:
								subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
								break;
							case Descending:
								Assertions.assertSame(subSet,set);
								return this;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(checkedType.checked) {
									Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
								}
								return null;
							case Descending:
								subSetMonitor=new SingleViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,StructType.BooleanSetTrueView,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						switch(sortOrder) {
						case Ascending:
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						case Descending:
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							if(toInclusive) {
								subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
							}else {
								subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
				}
			}else {
				if(toB) {
					if(fromInclusive) {
						if(toInclusive) {
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							switch(sortOrder) {
							case Ascending:
								Assertions.assertSame(subSet,set);
								return this;
							case Descending:
								subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								subSetMonitor=new SingleViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,StructType.BooleanSetFalseView,checkedType,sortOrder);
								break;
							case Descending:
								if(checkedType.checked) {
									Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
								}
								return null;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						switch(sortOrder) {
						case Ascending:
							var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
							if(toInclusive) {
								subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
							}else {
								subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
							}
							break;
						case Descending:
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						default:
							throw sortOrder.invalid();
						}
					}
				}else {
					if(fromInclusive) {
						var subSet=callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType);
						if(toInclusive) {
							subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
							
						}else {
							subSetMonitor=new EmptyViewMonitor(subSet,root,sortOrder==SortOrder.Ascending?0:1,checkedType,sortOrder);
						}
					}else {
						if(toInclusive) {
							subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?1:0,checkedType,sortOrder);
						}else {
							if(checkedType.checked) {
								Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
							}
							return null;
						}
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callHeadSet(toB,inclusive,functionCallType);
			if(toB) {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,2,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,0,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callTailSet(fromB,inclusive,functionCallType);
			if(fromB) {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
						
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,2,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(inclusive) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
						break;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,0,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> subSet(Object fromElement, Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callSubSet(fromB,toB,functionCallType);

			if(fromB){
				if(toB) {
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
				}else {
					switch(sortOrder) {
					case Ascending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
						break;
					case Descending:
						Assertions.assertSame(subSet,set);
						return this;
					default:
						throw sortOrder.invalid();
					}
				}
			}else {
				if(toB) {
					switch(sortOrder) {
					case Ascending:
						Assertions.assertSame(subSet,set);
						return this;
					case Descending:
						subSetMonitor=new EmptyViewMonitor(subSet,root,1,checkedType,sortOrder);
						break;
					default:
						throw sortOrder.invalid();
					}
				}else {
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> headSet(Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callHeadSet(toB,functionCallType);
			if(toB) {
				switch(sortOrder) {
				case Ascending:
					Assertions.assertSame(subSet,set);
					return this;
				case Descending:
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
					break;
				default:
					throw sortOrder.invalid();
				}
			}else {
				switch(sortOrder) {
				case Ascending:
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
					break;
				case Descending:
					Assertions.assertSame(subSet,set);
					return this;
				default:
					throw sortOrder.invalid();
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean,Boolean> tailSet(Object fromElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			AbstractBooleanSetMonitor subSetMonitor;
			var subSet=callTailSet(fromB,functionCallType);
			if(fromB) {
				switch(sortOrder) {
				case Ascending:
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetTrueView,checkedType,sortOrder);
					break;
				case Descending:
					Assertions.assertSame(subSet,set);
					return this;
					
				default:
					throw sortOrder.invalid();
				}
			}else {
				switch(sortOrder) {
				case Ascending:
					Assertions.assertSame(subSet,set);
					return this;
				case Descending:
					subSetMonitor=new SingleViewMonitor(subSet,root,StructType.BooleanSetFalseView,checkedType,sortOrder);
					break;
				default:
					throw sortOrder.invalid();
				}
			}
			subSetMonitor.verifyCollectionState();
			return subSetMonitor;
		}

        
    }
    private static class EmptyViewMonitor extends AbstractBooleanSetMonitor{
        final int position;
        final BooleanSetImplMonitor root;
        EmptyViewMonitor(OmniNavigableSet.OfBoolean set,BooleanSetImplMonitor root,int position,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.position=position;
            this.root=root;
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
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_HEAD);
                        break;
                    case 1:
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_MIDDLE);
                        break;
                    case 2:
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_ASCENDING_TAIL);
                        break;
                    default:
                        throw new IllegalStateException();
                    }
                }else {
                    Assertions.assertSame(set,AbstractBooleanSet.UNCHECKED_EMPTY_ASCENDING);
                }
                break;
            case Descending:
                if(checkedType.checked) {
                    switch(position) {
                    case 0:
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_TAIL);
                        break;
                    case 1:
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_MIDDLE);
                        break;
                    case 2:
                        Assertions.assertSame(set,AbstractBooleanSet.CHECKED_EMPTY_DESCENDING_HEAD);
                        break;
                    default:
                        throw new IllegalStateException();
                    }
                }else {
                    Assertions.assertSame(set,AbstractBooleanSet.UNCHECKED_EMPTY_DESCENDING);
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

		@Override
		public Object verifyLower(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public Object verifyHigher(Object input, DataType outputType) {
			Object actual=callHigher(outputType,input);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public Object verifyCeiling(Object input, DataType outputType) {
			Object actual=callCeiling(outputType,input);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public Object verifyFloor(Object input, DataType outputType) {
			Object actual=callFloor(outputType,input);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public Object verifyPollFirst(DataType outputType) {
			Object actual=callPollFirst(outputType);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public Object verifyPollLast(DataType outputType) {
			Object actual=callPollLast(outputType);
			Assertions.assertEquals(outputType.defaultVal, actual);
			return actual;
		}

		@Override
		public MonitoredIterator<? extends OmniIterator<Boolean>, OfBoolean> descendingIterator() {
			return new EmptyItrMonitor(set.descendingIterator(), this, sortOrder==SortOrder.Ascending?IteratorType.DescendingItr:IteratorType.AscendingItr);
		}

		@Override
		public AbstractBooleanSetMonitor descendingSet() {
			try {
				var descendingSet=new EmptyViewMonitor(set.descendingSet(),root,position,checkedType,sortOrder==SortOrder.Ascending?SortOrder.Descending:SortOrder.Ascending);
				descendingSet.verifyCollectionState();
				return descendingSet;
			}finally {
				verifyCollectionState();
			}
		}

		@Override
		public Object verifyFirst(DataType outputType) {
			callFirst(outputType);
			throw new UnsupportedOperationException();
		}

		@Override
		public Object verifyLast(DataType outputType) {
			callLast(outputType);
			throw new UnsupportedOperationException();
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, Object toElement,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			outer: for(;;) {
				switch(position) {
				case 0:
				case 2:
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(fromB && !toB) {
							break outer;
						}
						break;
					case Descending:
						if(!fromB && toB) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,toB,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callSubSet(fromB,toB,functionCallType), set);
			return this;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			outer: for(;;) {
				switch(position) {
				case 0:
					if(!toB && sortOrder==SortOrder.Descending) {
						break outer;
					}
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(!toB) {
							break outer;
						}
						break;
					case Descending:
						if(toB) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				case 2:
					if(toB && sortOrder==SortOrder.Ascending) {
						break outer;
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callHeadSet(toB,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callHeadSet(toB,functionCallType), set);
			return this;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			outer: for(;;) {
				switch(position) {
				case 0:
					if(!fromB && sortOrder==SortOrder.Ascending) {
						break outer;
					}
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(fromB) {
							break outer;
						}
						break;
					case Descending:
						if(!fromB) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				case 2:
					if(fromB && sortOrder==SortOrder.Descending) {
						break outer;
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callTailSet(fromB,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callTailSet(fromB,functionCallType), set);
			return this;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, boolean fromInclusive,
				Object toElement, boolean toInclusive, DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			outer: for(;;) {
				switch(position) {
				case 0:
					if(!toB && !fromB) {
						switch(sortOrder) {
						case Ascending:
							if(fromInclusive && !toInclusive) {
								break outer;
							}
							break;
						case Descending:
							if(!fromInclusive && toInclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(fromB == fromInclusive && toB ^ toInclusive) {
							break outer;
						}
						break;
					case Descending:
						if(fromB ^ fromInclusive && toB == toInclusive) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				case 2:
					if(toB && fromB) {
						switch(sortOrder) {
						case Ascending:
							if(!fromInclusive && toInclusive) {
								break outer;
							}
							break;
						case Descending:
							if(fromInclusive && !toInclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType), set);
			return this;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			outer: for(;;) {
				switch(position) {
				case 0:
					if(!toB) {
						switch(sortOrder) {
						case Ascending:
							if(!inclusive) {
								break outer;
							}
							break;
						case Descending:
							if(inclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(toB ^ inclusive) {
							break outer;
						}
						break;
					case Descending:
						if(toB == inclusive) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				case 2:
					if(toB) {
						switch(sortOrder) {
						case Ascending:
							if(inclusive) {
								break outer;
							}
							break;
						case Descending:
							if(!inclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callHeadSet(toB,inclusive,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callHeadSet(toB,inclusive,functionCallType), set);
			return this;
		}

		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			outer: for(;;) {
				switch(position) {
				case 0:
					if(!fromB) {
						switch(sortOrder) {
						case Ascending:
							if(inclusive) {
								break outer;
							}
							break;
						case Descending:
							if(!inclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				case 1:
					switch(sortOrder) {
					case Ascending:
						if(fromB == inclusive) {
							break outer;
						}
						break;
					case Descending:
						if(fromB ^ inclusive) {
							break outer;
						}
						break;
					default:
						throw sortOrder.invalid();
					}
					break;
				case 2:
					if(fromB) {
						switch(sortOrder) {
						case Ascending:
							if(!inclusive) {
								break outer;
							}
							break;
						case Descending:
							if(inclusive) {
								break outer;
							}
							break;
						default:
							throw sortOrder.invalid();
						}
					}
					break;
				default:
					throw new IllegalStateException("position = "+position);
				}
				if(checkedType.checked) {
					Assertions.assertThrows(IllegalArgumentException.class, ()->callTailSet(fromB,inclusive,functionCallType));
				}
				return null;
			}
			Assertions.assertSame(callTailSet(fromB,inclusive,functionCallType), set);
			return this;
		}
        
    }
    private static class SingleViewMonitor extends AbstractBooleanSetMonitor{
        final BooleanSetImplMonitor root;
        final StructType structType;
        private int getMask() {
            if(structType==StructType.BooleanSetTrueView) {
                return 0b10;
            }else {
                return 0b01;
            }
        }
        SingleViewMonitor(OmniNavigableSet.OfBoolean set,BooleanSetImplMonitor root,StructType structType,CheckedType checkedType,SortOrder sortOrder){
            super(set,checkedType,sortOrder);
            this.root=root;
            this.structType=structType;
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
                return outputType.convertVal(structType==StructType.BooleanSetTrueView);
            }
            throw new UnsupportedOperationException("iterationIndex = "+iterationIndex+", viewedVal = "+(structType==StructType.BooleanSetTrueView)+", state = "+root.expectedState);
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
            return new SingleViewItrMonitor(structType,root,itr,iType,checkedType.checked?0b10:0b01);
           
        }
        @Override
        public StructType getStructType(){
            return structType;
        }
        @Override
        public void modCollection(){
            int mask=getMask();
            if((root.expectedState&mask)!=0){
                root.expectedState&=~mask;
                set.removeVal(structType==StructType.BooleanSetTrueView);
            }else {
                root.expectedState|=mask;
                set.add(structType==StructType.BooleanSetTrueView);
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
                Assertions.assertFalse(filter.removedVals.contains(structType!=StructType.BooleanSetTrueView));
                Assertions.assertFalse(filter.retainedVals.contains(structType!=StructType.BooleanSetTrueView));
                Assertions.assertNotEquals(filter.removedVals.contains(structType==StructType.BooleanSetTrueView),filter.retainedVals.contains(structType==StructType.BooleanSetTrueView));
                Assertions.assertEquals(1,filter.numCalls);
                if(result) {
                    Assertions.assertEquals(1,filter.numRemoved);
                    Assertions.assertEquals(0,filter.numRetained);
                    Assertions.assertTrue(filter.removedVals.contains(structType==StructType.BooleanSetTrueView));
                    root.expectedState&=~mask;
                }else {
                    Assertions.assertEquals(0,filter.numRemoved);
                    Assertions.assertEquals(1,filter.numRetained);
                    Assertions.assertFalse(filter.removedVals.contains(structType==StructType.BooleanSetTrueView));
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
		@Override
		public Object verifyLower(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			if(((structType==StructType.BooleanSetTrueView?0b10:0b01)&root.expectedState)==0) {
				Assertions.assertEquals(outputType.defaultVal, actual);
			}else {
				int viewedInt=structType==StructType.BooleanSetTrueView?1:0;
				switch(sortOrder) {
				case Ascending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							if((boolean)input) {
								Assertions.assertEquals(Boolean.FALSE, actual);
							}else {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}
						}
						break;
					case BYTE:
						if((byte)input>viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input>viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input>viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input>viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input>viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<=viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((float)viewedInt, actual);
							
						}
						break;
					case DOUBLE:
						if((double)input<=viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((double)viewedInt, actual);
						}
						break;
					default:
						throw outputType.invalid();
					}
					break;
				case Descending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							if((boolean)input) {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}else {
								Assertions.assertEquals(Boolean.TRUE, actual);
							}
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case BYTE:
						if((byte)input<viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input<viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input<viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input<viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input<viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<viewedInt) {
							Assertions.assertEquals((float)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case DOUBLE:
						if((double)input<viewedInt) {
							Assertions.assertEquals((double)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					}
					break;
				default:
					throw sortOrder.invalid();
				}
				
			}
			return actual;
		}
		@Override
		public Object verifyHigher(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			if(((structType==StructType.BooleanSetTrueView?0b10:0b01)&root.expectedState)==0) {
				Assertions.assertEquals(outputType.defaultVal, actual);
			}else {
				int viewedInt=structType==StructType.BooleanSetTrueView?1:0;
				switch(sortOrder) {
				case Ascending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							if((boolean)input) {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}else {
								Assertions.assertEquals(Boolean.TRUE, actual);
							}
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case BYTE:
						if((byte)input<viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input<viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input<viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input<viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input<viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<viewedInt) {
							Assertions.assertEquals((float)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case DOUBLE:
						if((double)input<viewedInt) {
							Assertions.assertEquals((double)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						
						break;
					default:
						throw outputType.invalid();
					}
					break;
				case Descending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							if((boolean)input) {
								Assertions.assertEquals(Boolean.FALSE, actual);
							}else {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}
						}
						break;
					case BYTE:
						if((byte)input>viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input>viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input>viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input>viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input>viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<=viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((float)viewedInt, actual);
						}
						break;
					case DOUBLE:
						if((double)input<=viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((double)viewedInt, actual);
						}
						break;
					}
					break;
				default:
					throw sortOrder.invalid();
				}
				
			}
			return actual;
		}
		@Override
		public Object verifyCeiling(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			if(((structType==StructType.BooleanSetTrueView?0b10:0b01)&root.expectedState)==0) {
				Assertions.assertEquals(outputType.defaultVal, actual);
			}else {
				int viewedInt=structType==StructType.BooleanSetTrueView?1:0;
				switch(sortOrder) {
				case Ascending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							Assertions.assertEquals(Boolean.TRUE, actual);
						}else {
							if((boolean)input) {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}else {
								Assertions.assertEquals(Boolean.FALSE, actual);
							}
						}
						break;
					case BYTE:
						if((byte)input<=viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input<=viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input<=viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input<=viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input<=viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<=viewedInt) {
							Assertions.assertEquals((float)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case DOUBLE:
						if((double)input<=viewedInt) {
							Assertions.assertEquals((double)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						
						break;
					default:
						throw outputType.invalid();
					}
					break;
				case Descending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							if((boolean)input) {
								Assertions.assertEquals(Boolean.TRUE, actual);
							}else {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}
							
						}else {
							Assertions.assertEquals(Boolean.FALSE, actual);
						}
						break;
					case BYTE:
						if((byte)input>=viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input>=viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input>=viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input>=viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input>=viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((float)viewedInt, actual);
						}
						break;
					case DOUBLE:
						if((double)input<viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((double)viewedInt, actual);
						}
						break;
					}
					break;
				default:
					throw sortOrder.invalid();
				}
				
			}
			return actual;
		}
		@Override
		public Object verifyFloor(Object input, DataType outputType) {
			Object actual=callLower(outputType,input);
			if(((structType==StructType.BooleanSetTrueView?0b10:0b01)&root.expectedState)==0) {
				Assertions.assertEquals(outputType.defaultVal, actual);
			}else {
				int viewedInt=structType==StructType.BooleanSetTrueView?1:0;
				switch(sortOrder) {
				case Ascending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							if((boolean)input) {
								Assertions.assertEquals(Boolean.TRUE, actual);
							}else {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}
							
						}else {
							Assertions.assertEquals(Boolean.FALSE, actual);
						}
						break;
					case BYTE:
						if((byte)input>=viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input>=viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input>=viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input>=viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input>=viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((float)viewedInt, actual);
							
						}
						break;
					case DOUBLE:
						if((double)input<viewedInt) {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}else {
							Assertions.assertEquals((double)viewedInt, actual);
						}
						break;
					default:
						throw outputType.invalid();
					}
					break;
				case Descending:
					switch(outputType) {
					case BOOLEAN:
					case REF:
						if(structType==StructType.BooleanSetTrueView) {
							Assertions.assertEquals(Boolean.TRUE, actual);
						}else {
							if((boolean)input) {
								Assertions.assertEquals(outputType.defaultVal, actual);
							}else {
								Assertions.assertEquals(Boolean.FALSE, actual);
							}
						}
						break;
					case BYTE:
						if((byte)input<=viewedInt) {
							Assertions.assertEquals((byte)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case CHAR:
						if((char)input<=viewedInt) {
							Assertions.assertEquals((char)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case SHORT:
						if((short)input<=viewedInt) {
							Assertions.assertEquals((short)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case INT:
						if((int)input<=viewedInt) {
							Assertions.assertEquals((int)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case LONG:
						if((long)input<=viewedInt) {
							Assertions.assertEquals((long)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case FLOAT:
						if((float)input<=viewedInt) {
							Assertions.assertEquals((float)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					case DOUBLE:
						if((double)input<=viewedInt) {
							Assertions.assertEquals((double)viewedInt, actual);
						}else {
							Assertions.assertEquals(outputType.defaultVal, actual);
						}
						break;
					}
					break;
				default:
					throw sortOrder.invalid();
				}
				
			}
			return actual;
		}
		@Override
		public Object verifyPollFirst(DataType outputType) {
			Object result=callPollFirst(outputType);
			if((root.expectedState&(structType==StructType.BooleanSetTrueView?0b10:0b01))==0) {
				Assertions.assertEquals(outputType.defaultVal, result);
			}else {
				Assertions.assertEquals(outputType.convertVal(structType==StructType.BooleanSetTrueView), result);
				if(structType==StructType.BooleanSetTrueView) {
					root.expectedState&=0b01;
				}else {
					root.expectedState&=0b10;
				}
			}
			return result;
		}
		@Override
		public Object verifyPollLast(DataType outputType) {
			Object result=callPollLast(outputType);
			if((root.expectedState&(structType==StructType.BooleanSetTrueView?0b10:0b01))==0) {
				Assertions.assertEquals(outputType.defaultVal, result);
			}else {
				Assertions.assertEquals(outputType.convertVal(structType==StructType.BooleanSetTrueView), result);
				if(structType==StructType.BooleanSetTrueView) {
					root.expectedState&=0b01;
				}else {
					root.expectedState&=0b10;
				}
			}
			return result;
		}
		@Override
		public MonitoredIterator<? extends OmniIterator<Boolean>, OfBoolean> descendingIterator() {
			var itr=set.descendingIterator();
			if((root.expectedState&(structType==StructType.BooleanSetTrueView?0b10:0b01))==0) {
				switch(sortOrder) {
				case Ascending:
					return new EmptyItrMonitor(itr, root, IteratorType.DescendingItr);
				case Descending:
					return new EmptyItrMonitor(itr, root, IteratorType.AscendingItr);
				default:
					throw sortOrder.invalid();
				}
			}else {
				switch(sortOrder) {
				case Ascending:
					return new SingleViewItrMonitor(structType,root,itr,IteratorType.DescendingItr,checkedType.checked?0b10:0b01);
				case Descending:
					return new SingleViewItrMonitor(structType,root,itr,IteratorType.DescendingItr,checkedType.checked?0b10:0b01);
				default:
					throw sortOrder.invalid();
				}
			}
		}
		@Override
		public AbstractBooleanSetMonitor descendingSet() {
			try {
				var descendingSet= new SingleViewMonitor(set.descendingSet(), root, structType, checkedType, sortOrder==SortOrder.Ascending?SortOrder.Descending:SortOrder.Ascending);
				descendingSet.verifyCollectionState();
				return descendingSet;
			}finally {
				verifyCollectionState();
			}
		}
		@Override
		public Object verifyFirst(DataType outputType) {
			Object ret=callFirst(outputType);
			Assertions.assertEquals(outputType.convertVal(structType==StructType.BooleanSetTrueView),ret);
			return ret;
		}
		@Override
		public Object verifyLast(DataType outputType) {
			Object ret=callLast(outputType);
			Assertions.assertEquals(outputType.convertVal(structType==StructType.BooleanSetTrueView),ret);
			return ret;
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, Object toElement,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(fromB){
						if(toB) {
							if(structType==StructType.BooleanSetTrueView) {
								break isSame;
							}else {
								break throwIAE;
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,toB,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Descending:
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						if(toB) {
							switch(sortOrder) {
							case Ascending:
								break throwIAE;
							case Descending:
								subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,toB,functionCallType),root,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}else {
							if(structType==StructType.BooleanSetFalseView) {
								break isSame;

							}
							break throwIAE;						
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callSubSet(fromB,toB,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,toB,functionCallType));
			}
			return null;
			
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(toB) {
						if(structType==StructType.BooleanSetTrueView) {
							break isSame;
						}
						switch(sortOrder) {
						case Ascending:
							break throwIAE;
						case Descending:
							subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,functionCallType),root,1,checkedType,sortOrder);
							break;
						default:
							throw sortOrder.invalid();
						}
					}else {
						switch(sortOrder) {
						case Ascending:
							if(structType==StructType.BooleanSetFalseView) {
								break isSame;
							}
							subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,functionCallType),root,1,checkedType,sortOrder);
							break;
						case Descending:
							if(structType==StructType.BooleanSetFalseView) {
								break isSame;
							}
							break throwIAE;
						default:
							throw sortOrder.invalid();
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callHeadSet(toB,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callHeadSet(toB,functionCallType));
			}
			return null;
			
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, DataType inputType,
				FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(fromB) {
						switch(sortOrder) {
						case Ascending:
							if(structType==StructType.BooleanSetTrueView) {
								break isSame;
							}
							subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,functionCallType),root,1,checkedType,sortOrder);
							break;
						case Descending:
							if(structType==StructType.BooleanSetTrueView) {
								break isSame;
							}
							break throwIAE;
						default:
							throw sortOrder.invalid();
						}
					}else {
						switch(sortOrder) {
						case Descending:
							if(structType==StructType.BooleanSetFalseView) {
								break isSame;
							}
							subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,functionCallType),root,1,checkedType,sortOrder);
							break;
						case Ascending:
							if(structType==StructType.BooleanSetFalseView) {
								break isSame;
							}
							break throwIAE;
						default:
							throw sortOrder.invalid();
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callTailSet(fromB,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callTailSet(fromB,functionCallType));
			}
			return null;
			
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> subSet(Object fromElement, boolean fromInclusive,
				Object toElement, boolean toInclusive, DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType, fromElement);
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(fromB){
						if(toB) {
							if(fromInclusive) {
								if(toInclusive) {
									if(structType==StructType.BooleanSetTrueView) {
										break isSame;
									}else {
										break throwIAE;
									}
								}else {
									if(structType==StructType.BooleanSetTrueView) {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?1:2,checkedType,sortOrder);
									}else {
										if(sortOrder==SortOrder.Ascending) {
											subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										}else {
											break throwIAE;
										}
									}
								}
							}else {
								if(toInclusive) {
									if(structType==StructType.BooleanSetTrueView) {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?2:1,checkedType,sortOrder);
									}else {
										if(sortOrder==SortOrder.Descending) {
											subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										}else {
											break throwIAE;
										}
									}
								}else {
									break throwIAE;
								}
							}
						}else {
							if(fromInclusive) {
								if(toInclusive) {
									switch(sortOrder) {
									case Ascending:
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										break;
									case Descending:
										break throwIAE;
									default:
										throw sortOrder.invalid();
									}
								}else {
									switch(sortOrder) {
									case Descending:
										if(structType==StructType.BooleanSetTrueView) {
											break isSame;
										}
									case Ascending:
										break throwIAE;
									default:
										throw sortOrder.invalid();
									}
								}
							}else {
								switch(sortOrder) {
								case Descending:
									if(toInclusive) {
										if(structType==StructType.BooleanSetFalseView) {
											break isSame;
										}
									}else {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										break;
									}
								case Ascending:
									break throwIAE;	
								default:
									throw sortOrder.invalid();
								}
							}
						}
					}else {
						if(toB) {
							if(fromInclusive) {
								if(toInclusive) {
									switch(sortOrder) {
									case Ascending:
										break throwIAE;
									case Descending:
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										break;
									default:
										throw sortOrder.invalid();
									}
								}else {
									switch(sortOrder) {
									case Ascending:
										if(structType==StructType.BooleanSetFalseView) {
											break isSame;

										}
									case Descending:
										break throwIAE;
									default:
										throw sortOrder.invalid();
									}
								}
							}else {
								switch(sortOrder) {
								case Ascending:
									if(!toInclusive) {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);
										break;
									}
									if(structType==StructType.BooleanSetTrueView) {
										break isSame;

									}
								case Descending:
									break throwIAE;
								default:
									throw sortOrder.invalid();
								}
							}
						}else {
							if(fromInclusive) {
								if(toInclusive) {
									if(structType==StructType.BooleanSetFalseView) {
										break isSame;

									}
									break throwIAE;						
								}else {
									if(structType==StructType.BooleanSetFalseView) {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?0:1,checkedType,sortOrder);

									}else {
										if(sortOrder==SortOrder.Descending) {
											subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);

										}else {
											break throwIAE;
										}
									}
								}
							}else {
								if(toInclusive) {
									if(structType==StructType.BooleanSetFalseView) {
										subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,sortOrder==SortOrder.Ascending?1:0,checkedType,sortOrder);

									}else {
										if(sortOrder==SortOrder.Ascending) {
											subSetMonitor=new EmptyViewMonitor(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),root,1,checkedType,sortOrder);

										}else {
											break throwIAE;

										}
									}
								}else {
									break throwIAE;
								}
							}
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callSubSet(fromB,fromInclusive,toB,toInclusive,functionCallType));
			}
			return null;
			
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> headSet(Object toElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean toB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,toElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(toB) {
						if(inclusive) {
							if(structType==StructType.BooleanSetTrueView) {
								break isSame;
							}
							switch(sortOrder) {
							case Ascending:
								break throwIAE;
							case Descending:
								subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Descending:
								if(structType==StructType.BooleanSetTrueView) {
									subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,2,checkedType,sortOrder);
									break;
								}
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						if(inclusive) {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Descending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetFalseView) {
									subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,0,checkedType,sortOrder);
									break;
								}
								break throwIAE;
							case Descending:
								if(structType==StructType.BooleanSetTrueView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callHeadSet(toB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callHeadSet(toB,inclusive,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callHeadSet(toB,inclusive,functionCallType));
			}
			return null;
			
		}
		@Override
		public MonitoredNavigableSet<OfBoolean, Boolean> tailSet(Object fromElement, boolean inclusive,
				DataType inputType, FunctionCallType functionCallType) {
			boolean fromB=(boolean)DataType.BOOLEAN.convertValUnchecked(inputType,fromElement);
			throwIAE:for(;;) {
				isSame:for(;;) {
					AbstractBooleanSetMonitor subSetMonitor;
					if(fromB) {
						if(inclusive) {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetTrueView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Descending:
								if(structType==StructType.BooleanSetTrueView) {
									break isSame;
								}
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetTrueView) {
									subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,2,checkedType,sortOrder);
									break;
								}
								break throwIAE;
							case Descending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							default:
								throw sortOrder.invalid();
							}
						}
					}else {
						if(inclusive) {
							switch(sortOrder) {
							case Descending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Ascending:
								if(structType==StructType.BooleanSetFalseView) {
									break isSame;
								}
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}else {
							switch(sortOrder) {
							case Ascending:
								if(structType==StructType.BooleanSetTrueView) {
									break isSame;
								}
								subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,1,checkedType,sortOrder);
								break;
							case Descending:
								if(structType==StructType.BooleanSetFalseView) {
									subSetMonitor=new EmptyViewMonitor(callTailSet(fromB,inclusive,functionCallType),root,0,checkedType,sortOrder);
									break;
								}
								break throwIAE;
							default:
								throw sortOrder.invalid();
							}
						}
					}
					subSetMonitor.verifyCollectionState();
					return subSetMonitor;
				}
				Assertions.assertSame(callTailSet(fromB,inclusive,functionCallType),set);
				return this;
			}
			if(checkedType.checked) {
				Assertions.assertThrows(IllegalArgumentException.class, ()->callTailSet(fromB,inclusive,functionCallType));
			}
			return null;
			
		}
        
    }
    private static abstract class AbstractBooleanSetMonitor implements MonitoredNavigableSet<OmniNavigableSet.OfBoolean,Boolean>{
        final OmniNavigableSet.OfBoolean set;
        final CheckedType checkedType;
        final SortOrder sortOrder;
        AbstractBooleanSetMonitor(OmniNavigableSet.OfBoolean set,CheckedType checkedType,SortOrder sortOrder){
            this.set=set;
            this.checkedType=checkedType;
            this.sortOrder=sortOrder;
           
        }
        public abstract AbstractBooleanSetMonitor descendingSet();
        Object verifyClone() {
        	throw new UnsupportedOperationException();
        }
        @Override
        public Object verifyComparator() {
        	BooleanComparator comparator=set.comparator();
        	Assertions.assertEquals(0, comparator.compare(Boolean.FALSE,Boolean.FALSE));
			Assertions.assertEquals(0, comparator.compare(Boolean.TRUE, Boolean.TRUE));
			Assertions.assertEquals(0, comparator.compare(false, false));
			Assertions.assertEquals(0, comparator.compare(true, true));
        	switch(sortOrder) {
			case Ascending:
				Assertions.assertTrue(comparator.compare(false, true)>0);
				Assertions.assertTrue(comparator.compare(true, false)<0);
				Assertions.assertTrue(comparator.compare(Boolean.FALSE, Boolean.TRUE)>0);
				Assertions.assertTrue(comparator.compare(Boolean.TRUE, Boolean.FALSE)<0);
				break;
			case Descending:
				Assertions.assertTrue(comparator.compare(false, true)<0);
				Assertions.assertTrue(comparator.compare(true, false)>0);
				Assertions.assertTrue(comparator.compare(Boolean.FALSE, Boolean.TRUE)<0);
				Assertions.assertTrue(comparator.compare(Boolean.TRUE, Boolean.FALSE)>0);
				break;
			default:
				throw sortOrder.invalid();
        	
        	}
        	return comparator;
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
   

    private static final EnumSet<SetInitialization> VALID_INIT_SEQS=EnumSet.of(SetInitialization.Empty,
            SetInitialization.AddTrue,SetInitialization.AddFalse,
            SetInitialization.AddTrueAndFalse);
    
    
    
    
    
   
    private static  MonitoredNavigableSet<OmniNavigableSet.OfBoolean,Boolean> getMonitoredSet(BooleanSetImplMonitor root,int invert,int inclusiveLo,int inclusiveHi){
    	MonitoredNavigableSet<OmniNavigableSet.OfBoolean,Boolean> ret;
      switch(root.sortOrder) {
      case Ascending:
    	  switch(inclusiveLo) {
    	  case 0:
    		  switch(inclusiveHi) {
    		  case -1:
    			  ret= root.subSet(false, true, false, false, DataType.BOOLEAN, FunctionCallType.Unboxed);
    			  break;
    		  case 0:
    			  ret= root.subSet(false, true, false, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  case 1:
    			  ret= root.subSet(false, true, true,true, DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  default:
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  case 1:
    		  switch(inclusiveHi) {
    		  case 0:
    			  ret=root.subSet(true, true, false, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  case 1:
    			  ret=root.subSet(true, true, true, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  default:
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  case 2:
    		  if(inclusiveHi==1) {
    			  ret=root.subSet(true, false, true, true,DataType.BOOLEAN,FunctionCallType.Unboxed); 
    		  }else {
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  default:
    		  throw new UnsupportedOperationException();
    	  }
    	  break;
      case Descending:
    	  switch(inclusiveLo) {
    	  case 0:
    		  switch(inclusiveHi) {
    		  case -1:
    			  ret= root.subSet(false, false, false, true, DataType.BOOLEAN, FunctionCallType.Unboxed);
    			  break;
    		  case 0:
    			  ret= root.subSet(false, true, false, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  case 1:
    			  ret= root.subSet(true, true, false,true, DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  default:
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  case 1:
    		  switch(inclusiveHi) {
    		  case 0:
    			  ret=root.subSet(false, true, true, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  case 1:
    			  ret=root.subSet(true, true, true, true,DataType.BOOLEAN,FunctionCallType.Unboxed);
    			  break;
    		  default:
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  case 2:
    		  if(inclusiveHi==1) {
    			  ret=root.subSet(true, true, true, false,DataType.BOOLEAN,FunctionCallType.Unboxed); 
    		  }else {
    			  throw new UnsupportedOperationException();
    		  }
    		  break;
    	  default:
    		  throw new UnsupportedOperationException();
    	  }
    	  break;
      default:
    	  throw root.sortOrder.invalid();
      }
      if(invert==1) {
    	  return ret.descendingSet();
      }
      return ret;
    }
    
    
    
    @Test
    public void testadd_val(){
        final var mayBeAddedTo=DataType.BOOLEAN.mayBeAddedTo();
        for(final var initSet:VALID_INIT_SEQS){
            for(final var inputType:mayBeAddedTo){
                for(final var checkedType:CheckedType.values()){
                	for(final var functionCallType:FunctionCallType.values()){
                		for(var sortOrder:SortOrder.values()) {
                			for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
                				final int invert=tmpInvert;
                				for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
                					final int inclusiveLo=tmpInclLo;
                					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
                						final int inclusiveHi=tmpInclHi;
                						for(int tmpInputVal=0;tmpInputVal<=1;++tmpInputVal) {
                							if(checkedType.checked || (tmpInputVal>=inclusiveLo && tmpInputVal<=inclusiveHi)) {
                								final int inputVal=tmpInputVal;
                								TestExecutorService.submitTest(()->{
                    								var monitor=getMonitoredSet(initSet.initialize(new BooleanSetImplMonitor(checkedType,sortOrder)),invert,inclusiveLo,inclusiveHi);
                    								if(inputVal>=inclusiveLo && inputVal<=inclusiveHi) {
                    									monitor.verifyAdd(inputVal==1,inputType,functionCallType);
                    								}else {
                    									Assertions.assertThrows(IllegalArgumentException.class, ()->monitor.verifyAdd(inputVal==1, inputType, functionCallType));
                    								}
                    							});
                							}
                						}
                					}
                				}
                			}
                		}
                    }
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testadd_val");
    }
    
    @Test
    public void testclear_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).verifyClear();
    	test.runAllTests("BooleanSetImplTest.testclear_void");
    }
    
    @Test
    public void testclone_void(){
    	for(var checkedType:CheckedType.values()) {
    		for(var sortOrder:SortOrder.values()) {
    			for(int tmpState=0b00;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					TestExecutorService.submitTest(()->{
    						AbstractBooleanSetMonitor monitor=new BooleanSetImplMonitor(checkedType,sortOrder,state);
    						if(invert==1) {
    							monitor=monitor.descendingSet();
    						}
    						monitor.verifyClone();
    					});
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testclone_void");
    }
    
    
    
    @Test
    public void testConstructor_int(){
    	for(var checkedType:CheckedType.values()) {
    		for(var sortOrder:SortOrder.values()) {
    			for(int tmpState=0b00;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				TestExecutorService.submitTest(()->{
    					new BooleanSetImplMonitor(checkedType,sortOrder,state).verifyCollectionState();
    				});
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_int");
    }
    
    @Test
    public void testConstructor_void(){
    	for(var sortOrder:SortOrder.values()) {
    		for(final var checkedType:CheckedType.values()){
                TestExecutorService.submitTest(()->new BooleanSetImplMonitor(checkedType,sortOrder).verifyCollectionState());
            }
    	}
        TestExecutorService.completeAllTests("BooleanSetImplTest.testConstructor_void");
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testConstructor_Collection() {
        for(var checkedType:CheckedType.values()) {
        	for(var sortOrder:SortOrder.values()) {
	            for(var collectionClass:DataType.BOOLEAN.validCollectionConstructorClasses) {
	                TestExecutorService.submitTest(()->{
	                    Collection<?> collectionParam=MonitoredCollection.getConstructorCollectionParam(DataType.BOOLEAN,(Class<? extends Collection<?>>)collectionClass);
	                    new BooleanSetImplMonitor(checkedType,sortOrder,collectionParam,(Class<? extends Collection<?>>)collectionClass).verifyCollectionState();
	                });
	            }
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
                            for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed) {
                            	final long randSeed=tmpRandSeed;
                            	for(var sortOrder:SortOrder.values()) {
                            		for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
                            			final int invert=tmpInvert;
                            			for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
                        					final int inclusiveLo=tmpInclLo;
                        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
                        						final int inclusiveHi=tmpInclHi;
                        						TestExecutorService.submitTest(()->{
                    								var monitor=getMonitoredSet(initSet.initialize(new BooleanSetImplMonitor(checkedType,sortOrder)),invert,inclusiveLo,inclusiveHi);
                                                    if(functionGen.expectedException == null || monitor.isEmpty()){
                                                        monitor.verifyForEach(functionGen,functionCallType,randSeed);
                                                    }else{
                                                        Assertions.assertThrows(functionGen.expectedException,
                                                                ()->monitor.verifyForEach(functionGen,functionCallType,randSeed));
                                                        monitor.verifyCollectionState();
                                                    }
                                                });
                        					}
                            			}
                            			
                            		}
                            		
                            	}
                            	
                            }
                        }
                    }
                }
            }
        }

        TestExecutorService.completeAllTests("BooleanSetImplTest.testforEach_Consumer");
    }
    
    
    @Test
    public void testsubSet_valbooleanvalboolean() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
        								for(var from=0;from<=1;++from) {
        									for(var fromInclusive=0;fromInclusive<=1;++fromInclusive) {
        										for(var to=0;to<=1;++to) {
        											for(var toInclusive=0;toInclusive<=1;++toInclusive) {
            											monitor.subSet(from==1, fromInclusive==1, to==1, toInclusive==1, DataType.BOOLEAN, functionCallType);
            										}
        										}
        										
        									}
        								}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testsubSet_valbooleanvalboolean");
    }
    
    @Test
    public void testheadSet_valboolean() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
										for(var to=0;to<=1;++to) {
											for(var inclusive=0;inclusive<=1;++inclusive) {
												monitor.headSet(to==1, inclusive==1, DataType.BOOLEAN, functionCallType);
											}
										}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testheadSet_valboolean");
    }
    @Test
    public void testtailSet_valboolean() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
										for(var from=0;from<=1;++from) {
											for(var inclusive=0;inclusive<=1;++inclusive) {
												monitor.tailSet(from==1, inclusive==1, DataType.BOOLEAN, functionCallType);
											}
										}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testtailSet_valboolean");
    }
    @Test
    public void testheadSet_val() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
										for(var to=0;to<=1;++to) {
											monitor.headSet(to==1, DataType.BOOLEAN, functionCallType);
										}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testheadSet_val");
    }
    @Test
    public void testtailSet_val() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
										for(var from=0;from<=1;++from) {
											monitor.tailSet(from==1, DataType.BOOLEAN, functionCallType);
										}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testtailSet_val");
    }
    
    @Test
    public void testdescendingSet_void() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
									monitor.descendingSet();
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testdescendingSet_void");
    }
    
    @Test
    public void testsubSet_valval() {
    	for(var sortOrder:SortOrder.values()) {
    		for(var checkedType:CheckedType.values()) {
    			for(int tmpState=0;tmpState<=0b11;++tmpState) {
    				final int state=tmpState;
    				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
    					final int invert=tmpInvert;
    					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
        					final int inclusiveLo=tmpInclLo;
        					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
        						final int inclusiveHi=tmpInclHi;
        						TestExecutorService.submitTest(()->{
        							var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state),invert,inclusiveLo,inclusiveHi);
        							for(var functionCallType:FunctionCallType.values()) {
        								for(var from=0;from<=1;++from) {
    										for(var to=0;to<=1;++to) {
        										monitor.subSet(from==1,to==1, DataType.BOOLEAN, functionCallType);
    										}
        								}
        							}
        						});
    						}
    					}
    				}
    			}
    		}
    	}
    	TestExecutorService.completeAllTests("BooleanSetImplTest.testsubSet_valval");
    }
    
    
    
    @Test
    public void testhashCode_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).verifyHashCode();
    	test.runAllTests("BooleanSetImplTest.testhashCode_void");
    }
    
    @Test
    public void testisEmpty_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).verifyIsEmpty();
    	test.runAllTests("BooleanSetImplTest.testisEmpty_void");
    }
    
    @Test
    public void testequals_Object(){
    	//TODO flesh this out
        final BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->{
            try {
                Assertions.assertFalse(getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi)
                        .getCollection().equals(null));
            }catch(NotYetImplementedException e) {
                //do nothing
            }
           
            };
        test.runAllTests("BooleanSetImplTest.testequals_Object");
    }
    
    @Test
    public void testiterator_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).getMonitoredIterator().verifyIteratorState();

        test.runAllTests("BooleanSetImplTest.testiterator_void");
    }
    @Test
    public void testItrclone_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).getMonitoredIterator().verifyClone();

        test.runAllTests("BooleanSetImplTest.testItrclone_void");
    }
    
    @Test
    public void testItrforEachRemaining_Consumer(){
    	//TODO subsets and descending sets
        for(final var checkedType:CheckedType.values()){
            for(var initSet:VALID_INIT_SEQS){
                for(final var functionGen:IteratorType.AscendingItr.validMonitoredFunctionGens){
                    if(checkedType.checked || functionGen.expectedException == null || initSet.isEmpty){
                        for(final var preMod:IteratorType.AscendingItr.validPreMods){
                            if(checkedType.checked || preMod.expectedException == null || initSet.isEmpty){
                            	final int itrScenarioMax;
                            	switch(initSet) {
                            	case AddTrueAndFalse:
                            		itrScenarioMax=3;
                            		break;
                            	case AddTrue:
                            	case AddFalse:
                            		itrScenarioMax=1;
                            		break;
                                default:
                                	itrScenarioMax=0;
                            	}
                                for(int tmpItrScenario=0;tmpItrScenario<=itrScenarioMax;++tmpItrScenario) {
                                  final int itrScenario=tmpItrScenario;
                                  final long randMax= preMod.expectedException == null && functionGen.randomized
                                      && initSet == SetInitialization.AddTrueAndFalse
                                      && itrScenario == 0?100:0;
                                  for(long tmpRandSeed=0;tmpRandSeed<=randMax;++tmpRandSeed) {
                                    final long randSeed=tmpRandSeed;
                                    for(final var functionCallType:FunctionCallType.values()){
                                      TestExecutorService.submitTest(()->{
                                          final var setMonitor=initSet
                                                  .initialize(new BooleanSetImplMonitor(checkedType));
                                          final var itrMonitor=setMonitor.getMonitoredIterator();
                                          int adjustedState;
                                          switch(itrScenario){
                                          case 1:
                                        	  if(itrMonitor.hasNext()) {
                                        		  itrMonitor.iterateForward();
                                        		  if(itrMonitor.hasNext()) {
                                            		  itrMonitor.iterateForward();
                                            	  }
                                        	  }
                                        	  adjustedState=0b00;
                                        	  break;
                                          case 2:
                                              itrMonitor.iterateForward();
                                              adjustedState=0b10;
                                              break;
                                          case 3:
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
                                      });
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
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->{
    		final var setMonitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi);
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
    	//TODO subsets and descending sets
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
                        }else if(initSet!=SetInitialization.Empty){
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
    	//TODO subsets and descending sets
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
    	
    	for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
            for(final var checkedType:CheckedType.values()){
                if(checkedType.checked || functionGen.expectedException == null){
                	for(var sortOrder:SortOrder.values()) {
                		for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
            				final int invert=tmpInvert;
    						for(final var initSet:VALID_INIT_SEQS){
    							if(functionGen==MonitoredFunctionGen.ModCollection
    									&& checkedType.checked
    									&& sortOrder==SortOrder.Ascending
    									&& invert==1
    									&& initSet==SetInitialization.Empty) {
    								TestExecutorService.suspend();
    							}
                                //TestExecutorService.submitTest(()->{
                                	var monitor=getMonitoredSet(initSet.initialize(new BooleanSetImplMonitor(checkedType,sortOrder)),invert,0,1);
                                    if(functionGen.expectedException==null) {
                                        Assertions.assertDoesNotThrow(()->monitor.verifyReadAndWrite(functionGen));
                                    }else {
                                        
                                        Assertions.assertThrows(functionGen.expectedException,()->monitor.verifyReadAndWrite(functionGen));
                                    }
                                //});
                            }
                		}
                		
                	}
                    
                }
            }
        }
        TestExecutorService.completeAllTests("BooleanSetImplTest.testReadAndWrite");
    }
    
    @Test
    public void testremoveIf_Predicate(){
    	//TODO subsets and descending sets
        for(final var filterGen:StructType.BooleanSetImpl.validMonitoredRemoveIfPredicateGens){
            for(final var initSet:VALID_INIT_SEQS){
                for(final var functionCallType:FunctionCallType.values()){
                    final long randSeedBound=filterGen.predicateGenCallType==PredicateGenCallType.Randomized
                            && initSet == SetInitialization.AddTrueAndFalse && !functionCallType.boxed?100:0;
                    for(final var checkedType:CheckedType.values()){
                        if(checkedType.checked || filterGen.expectedException == null || initSet.isEmpty){
                            for(long tmpRandSeed=0;tmpRandSeed<=randSeedBound;++tmpRandSeed) {
                            	final long randSeed=tmpRandSeed;
                            	TestExecutorService.submitTest(()->{
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
                                });
                            }
                            	
              
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
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).verifySize();
    	test.runAllTests("BooleanSetImplTest.testsize_void");
    }
    
    @Test
    public void testtoArray_IntFunction(){
        final MonitoredFunctionGenTest test=(functionGen,checkedType,initSet,sortOrder,invert,inclusiveLo,inclusiveHi)->{
        	var monitor=getMonitoredSet(initSet.initialize(new BooleanSetImplMonitor(checkedType,sortOrder)),invert,inclusiveLo,inclusiveHi);
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
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->{
    		int bound;
    		switch(state) {
    		case 0b00:
    			bound=2;
    			break;
    		case 0b01:
    			
    		case 0b10:
    			bound=3;
    			break;
    		case 0b11:
    			bound=4;
    			break;
    		default:
    			throw new IllegalStateException("state = "+state);
    		}
    		var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi);
    		switch(monitor.getStructType()) {
    		case BooleanSetEmpty:
    			bound=2;
    			break;
    		case BooleanSetTrueView:
    		case BooleanSetFalseView:
    			if(bound>3) {
    				bound=3;
    			}
    			break;
    		default:
    			
    		}
    		for(int arrSize=0;arrSize <= bound;++arrSize){
                monitor.verifyToArray(new Object[arrSize]);
            }
    	
    	};
        test.runAllTests("BooleanSetImplTest.testtoArray_ObjectArray");
    }
    @Test
    public void testtoArray_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->{
    		var monitor=getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi);
            for(var outputType:DataType.BOOLEAN.validOutputTypes()) {
                outputType.verifyToArray(monitor);
            }
        };
        test.runAllTests("BooleanSetImplTest.testtoArray_void");
    }
    @Test
    public void testtoString_void(){
    	BasicTest test=(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi)->getMonitoredSet(new BooleanSetImplMonitor(checkedType,sortOrder,state), invert, inclusiveLo, inclusiveHi).verifyToString();

        test.runAllTests("BooleanSetImplTest.testtoString_void");
    }
    private static interface BasicTest{
        private void runAllTests(String testName){
        	for(var checkedType:CheckedType.values()) {
        		for(var sortOrder:SortOrder.values()) {
        			for(int tmpState=0b00;tmpState<=0b11;++tmpState) {
        				final int state=tmpState;
        				for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
        					final int invert=tmpInvert;
        					for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
            					final int inclusiveLo=tmpInclLo;
            					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
            						final int inclusiveHi=tmpInclHi;
            						TestExecutorService.submitTest(()->runTest(checkedType,sortOrder,state,invert,inclusiveLo,inclusiveHi));
            					}
        					}
        				}
        			}
        		}
        	}
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(CheckedType checkedType,SortOrder sortOrder,int state,int invert,int inclusiveLo,int inclusiveHi);
    }
    private static interface MonitoredFunctionGenTest{
        private void runAllTests(String testName){
            for(final var functionGen:StructType.BooleanSetImpl.validMonitoredFunctionGens){
                for(final var checkedType:CheckedType.values()){
                    if(checkedType.checked || functionGen.expectedException == null){
                    	for(var sortOrder:SortOrder.values()) {
                    		for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
                				final int invert=tmpInvert;
                				for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
                					final int inclusiveLo=tmpInclLo;
                					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
                						final int inclusiveHi=tmpInclHi;
                						for(final var initSet:VALID_INIT_SEQS){
                                            TestExecutorService.submitTest(()->runTest(functionGen,checkedType,initSet,sortOrder,invert,inclusiveLo,inclusiveHi));
                                        }
                					}
                				}
                    		}
                    		
                    	}
                        
                    }
                }
            }
            TestExecutorService.completeAllTests(testName);
        }
        void runTest(MonitoredFunctionGen functionGen,CheckedType checkedType,SetInitialization initSet,SortOrder sortOrder,int invert,int inclusiveLo,int inclusiveHi);
    }
    private static interface QueryTest{
        boolean callMethod(MonitoredNavigableSet<OmniNavigableSet.OfBoolean,Boolean> monitor,QueryVal queryVal,DataType inputType,QueryCastType castType,
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
                                	for(var sortOrder:SortOrder.values()) {
                                		for(final var initSet:VALID_INIT_SEQS){
                                			for(int tmpInvert=0;tmpInvert<=1;++tmpInvert) {
                                				final int invert=tmpInvert;
                                				for(int tmpInclLo=0;tmpInclLo<=2;++tmpInclLo) {
                                					final int inclusiveLo=tmpInclLo;
                                					for(int tmpInclHi=inclusiveLo-1;tmpInclHi<=1;++tmpInclHi) {
                                						final int inclusiveHi=tmpInclHi;
                                						TestExecutorService.submitTest(()->runTest(queryCanReturnTrue,queryVal,
                                                                modification,inputType,castType,checkedType,sortOrder,invert,inclusiveLo,inclusiveHi,initSet));
                                					}
                                				}
                                				
                                			}
                                            
                                        }
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
                DataType inputType,QueryCastType castType,CheckedType checkedType,SortOrder sortOrder,int invert,int inclusiveLo,int inclusiveHi,SetInitialization initSet){
            boolean expectedResult;
            final boolean booleanInputVal;
            if(queryCanReturnTrue){
                booleanInputVal=queryVal.getBooleanVal(modification);
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
            	booleanInputVal=false;
                expectedResult=false;
            }
            MonitoredNavigableSet<OmniNavigableSet.OfBoolean,Boolean> monitor=getMonitoredSet(initSet.initialize(new BooleanSetImplMonitor(checkedType,sortOrder)), invert, inclusiveLo, inclusiveHi);
            if(expectedResult) {
            	switch(monitor.getStructType()) {
            	case BooleanSetEmpty:
            		expectedResult=false;
            		break;
            	case BooleanSetTrueView:
            		expectedResult=booleanInputVal;
            		break;
            	case BooleanSetFalseView:
            		expectedResult=!booleanInputVal;
            	default:
            	}
            }
           
            
            final boolean actualResult=callMethod(monitor,queryVal,
                    inputType,
                    castType,modification);
            Assertions.assertEquals(expectedResult,actualResult);
        }
    }
    
}
