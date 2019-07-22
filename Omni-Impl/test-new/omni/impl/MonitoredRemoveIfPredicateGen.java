package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.Random;

public enum MonitoredRemoveIfPredicateGen{
    RemoveFirst(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal=collection.isEmpty()?null:collection.get(0);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal=collection.isEmpty()?null:collection.get(0);
                }
                @Override
                protected boolean testImpl(double val){
                    return ((Object)val).equals(matchVal);
                }
                @Override
                protected boolean testImpl(float val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(long val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(int val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(short val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(char val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(byte val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(boolean val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(Object val) {
                    return Objects.equals(val,matchVal);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
        
    },
    RemoveLast(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal=collection.isEmpty()?null:collection.get(collection.size()-1);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal=collection.isEmpty()?null:collection.get(collection.size()-1);
                }
                @Override
                protected boolean testImpl(double val){
                    return ((Object)val).equals(matchVal);
                }
                @Override
                protected boolean testImpl(float val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(long val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(int val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(short val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(char val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(byte val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(boolean val){
                    return ((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(Object val) {
                    return Objects.equals(val,matchVal);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
            
        },
    RemoveFirstAndLast(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal1=collection.isEmpty()?null:collection.get(0);
                Object matchVal2=collection.isEmpty()?null:collection.get(collection.size()-1);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal1=collection.isEmpty()?null:collection.get(0);
                    matchVal2=collection.isEmpty()?null:collection.get(collection.size()-1);
                }
                @Override
                protected boolean testImpl(double val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(float val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(long val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(int val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(short val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(char val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(byte val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(boolean val){
                    Object v;
                    return (v=val).equals(matchVal1)||v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(Object val) {
                    return Objects.equals(val,matchVal1)||Objects.equals(val,matchVal2);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
            
        },
    RemoveAllButFirst(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal=collection.isEmpty()?null:collection.get(0);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal=collection.isEmpty()?null:collection.get(0);
                }
                @Override
                protected boolean testImpl(double val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(float val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(long val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(int val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(short val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(char val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(byte val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(boolean val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(Object val) {
                    return !Objects.equals(val,matchVal);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
            
        },
    RemoveAllButLast(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal=collection.isEmpty()?null:collection.get(collection.size()-1);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal=collection.isEmpty()?null:collection.get(collection.size()-1);
                }
                @Override
                protected boolean testImpl(double val){
                    return !((Object)val).equals(matchVal);
                }
                @Override
                protected boolean testImpl(float val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(long val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(int val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(short val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(char val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(byte val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(boolean val){
                    return !((Object)val).equals(matchVal);
                    }
                @Override
                protected boolean testImpl(Object val) {
                    return !Objects.equals(val,matchVal);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
            
        },
    RemoveAllButFirstAndLast(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)) {
                Object matchVal1=collection.isEmpty()?null:collection.get(0);
                Object matchVal2=collection.isEmpty()?null:collection.get(collection.size()-1);
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    matchVal1=collection.isEmpty()?null:collection.get(0);
                    matchVal2=collection.isEmpty()?null:collection.get(collection.size()-1);
                }
                @Override
                protected boolean testImpl(double val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(float val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(long val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(int val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(short val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(char val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(byte val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(boolean val){
                    Object v;
                    return !(v=val).equals(matchVal1)&&!v.equals(matchVal2);
                }
                @Override
                protected boolean testImpl(Object val) {
                    return !Objects.equals(val,matchVal1)&&!Objects.equals(val,matchVal2);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,java.util.Random rand){
            return getMonitoredRemoveIfPredicate(collection);
        }
            
        },
    Random(null,PredicateGenCallType.Randomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl(){
                    return rand.nextDouble() >= threshold;
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        }

        
    },
    RemoveTrue(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl() {
                    return false;
                }
                @Override
                protected boolean testImpl(double val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(float val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(long val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(int val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(short val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(char val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(byte val){
                    return val == 1;
                }
                @Override
                protected boolean testImpl(boolean val){
                    return val;
                }
            };
        }


        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            
            return getMonitoredRemoveIfPredicate(collection);
            }

    },
    RemoveFalse(null,PredicateGenCallType.NonRandomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl() {
                    return false;
                }
                @Override
                protected boolean testImpl(double val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(float val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(long val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(int val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(short val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(char val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(byte val){
                    return val == 0;
                }
                @Override
                protected boolean testImpl(boolean val){
                    return !val;
                }
            };
        }


        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
        
        
    },
    RemoveAll(null,PredicateGenCallType.NonRandomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl(){
                    return true;
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
    },
    RemoveNone(null,PredicateGenCallType.NonRandomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl(){
                    return false;
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            return getMonitoredRemoveIfPredicate(collection);
            }
    },
    Throw(IndexOutOfBoundsException.class,PredicateGenCallType.Randomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex){
                        throw new IndexOutOfBoundsException();
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        
        }

        
        
    },
    ModCollection(ConcurrentModificationException.class,PredicateGenCallType.Randomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                boolean alreadyModded=false;
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    alreadyModded=false;
                    throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex && !alreadyModded){
                        collection.illegalMod(IllegalModification.ModCollection);
                        alreadyModded=true;
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
            }

    },
    ModParent(ConcurrentModificationException.class,PredicateGenCallType.Randomized,2){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                boolean alreadyModded=false;
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    alreadyModded=false;
                    throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex && !alreadyModded){
                        collection.illegalMod(IllegalModification.ModParent);
                        alreadyModded=true;
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
            }

    },
    ModRoot(ConcurrentModificationException.class,PredicateGenCallType.Randomized,1){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                boolean alreadyModded=false;
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    alreadyModded=false;
                    throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex && !alreadyModded){
                        collection.illegalMod(IllegalModification.ModRoot);
                        alreadyModded=true;
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
            }

    },
    ThrowModCollection(ConcurrentModificationException.class,PredicateGenCallType.Randomized,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random  rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
           
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    this.throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex){
                        collection.illegalMod(IllegalModification.ModCollection);
                        throw new IndexOutOfBoundsException();
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
    },
    ThrowModParent(ConcurrentModificationException.class,PredicateGenCallType.Randomized,2){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        
        }


        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random  rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
           
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    this.throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex){
                        collection.illegalMod(IllegalModification.ModParent);
                        throw new IndexOutOfBoundsException();
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
    },
    ThrowModRoot(ConcurrentModificationException.class,PredicateGenCallType.Randomized,1){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random  rand){
            int numExpectedCalls=MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection);
            int tmpThrowIndex=(int)(rand.nextDouble() * numExpectedCalls);
           
            return new MonitoredRemoveIfPredicate(numExpectedCalls){
                int throwIndex=tmpThrowIndex;
                @Override
                public void reset(MonitoredCollection<?> collection) {
                    super.reset(collection);
                    this.throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
                }
                
                @Override
                protected boolean testImpl(){
                    if(numCalls >= throwIndex){
                        collection.illegalMod(IllegalModification.ModRoot);
                        throw new IndexOutOfBoundsException();
                    }
                    return rand.nextDouble() >= threshold;
                }

            };
        }
    };
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid MonitoredRemoveIfPredicateGen " + this);
    }
    public final Class<? extends Throwable> expectedException;
    public final PredicateGenCallType predicateGenCallType;
    public final int minDepth;
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,PredicateGenCallType predicateGenCallType,int minDepth){
        this.expectedException=expectedException;
        this.predicateGenCallType=predicateGenCallType;
        this.minDepth=minDepth;
    }
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection);
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
            double threshold,Random rand);
    
    public static enum PredicateGenCallType{
        Randomized,
        NonRandomized,
    }
    
}
