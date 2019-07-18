package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Random;

public enum MonitoredRemoveIfPredicateGen{
    RemoveSpecificIndices(null,PredicateGenCallType.IndexSpecific,0){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            
            
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
                @Override
                protected boolean testImpl(){
                   int numCalls=this.numCalls-1;
                   if(numCalls>64) {
                       return compliment;
                   }
                   return (1L<<numCalls&removeIndicesBitSet)!=0!=compliment;
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            throw new UnsupportedOperationException();
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,Random rand){
            throw new UnsupportedOperationException();
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

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,new Random(0));
        
        }
        
    },
    RemoveTrue(null,PredicateGenCallType.NonRandomized,0){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(MonitoredRemoveIfPredicate.calculateNumExpetedCalls(collection)){
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
                @Override
                protected boolean testImpl(Object val){
                    if(val instanceof Boolean){
                        return ((Boolean)val).booleanValue();
                    }else if(val instanceof Character){
                        return ((Character)val).charValue() == 1;
                    }else if(val instanceof Long){
                        return ((Long)val).longValue() == 1L;
                    }else if(val instanceof Number){
                        return ((Number)val).doubleValue() == 1.0;
                    }else if(val == null){
                        return false;
                    }
                    throw new UnsupportedOperationException("RemoveTrue not valid for " + val);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection);
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
                @Override
                protected boolean testImpl(Object val){
                    if(val instanceof Boolean){
                        return !((Boolean)val).booleanValue();
                    }else if(val instanceof Character){
                        return ((Character)val).charValue() == 0;
                    }else if(val instanceof Long){
                        return ((Long)val).longValue() == 0L;
                    }else if(val instanceof Number){
                        return ((Number)val).doubleValue() == 0.0;
                    }else if(val == null){
                        return false;
                    }
                    throw new UnsupportedOperationException("RemoveFalse not valid for " + val);
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection);
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
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection);
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
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection);
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

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
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

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
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

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
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

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
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
                boolean compliment,long removeIndicesBitSet){

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
                boolean compliment,long removeIndicesBitSet){

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
                boolean compliment,long removeIndicesBitSet){

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
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,boolean compliment,long removeIndicesBitSet);
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
            double threshold,Random rand);
    
    public static enum PredicateGenCallType{
        Randomized,
        NonRandomized,
        IndexSpecific;
    }
    
}
