package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Random;

public enum MonitoredRemoveIfPredicateGen{
    RemoveSpecificIndices(null,PredicateGenCallType.IndexSpecific){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            return new MonitoredRemoveIfPredicate(){
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
                double threshold,long randSeed){
            throw new UnsupportedOperationException();
            }
    },
    Random(null,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            Random rand=new Random(randSeed);
            return new MonitoredRemoveIfPredicate(){
                @Override
                protected boolean testImpl(){
                    return rand.nextDouble() >= threshold;
                }
            };
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        
    },
    RemoveTrue(null,PredicateGenCallType.NonRandomized){

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(){
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
                double threshold,long randSeed){
            return getMonitoredRemoveIfPredicate(collection);
            }

    },
    RemoveFalse(null,PredicateGenCallType.NonRandomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(){

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
                double threshold,long randSeed){
            return getMonitoredRemoveIfPredicate(collection);
            }
        
        
    },
    RemoveAll(null,PredicateGenCallType.NonRandomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(){
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
                double threshold,long randSeed){
            return getMonitoredRemoveIfPredicate(collection);
            }
    },
    RemoveNone(null,PredicateGenCallType.NonRandomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){
            return new MonitoredRemoveIfPredicate(){
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
                double threshold,long randSeed){
            return getMonitoredRemoveIfPredicate(collection);
            }
    },
    Throw(IndexOutOfBoundsException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
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

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection,0.5,0);
            }
        
        
    },
    ModCollection(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
                boolean alreadyModded=false;
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
            return getMonitoredRemoveIfPredicate(collection,0.5,0);
            }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){
            return getMonitoredRemoveIfPredicate(collection,0.5,0);
            }
    },
    ModParent(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
                boolean alreadyModded=false;
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
    },
    ModRoot(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
                boolean alreadyModded=false;
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
    },
    ThrowModCollection(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
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
    ThrowModParent(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
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
    ThrowModRoot(ConcurrentModificationException.class,PredicateGenCallType.Randomized){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }

        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                boolean compliment,long removeIndicesBitSet){

            return getMonitoredRemoveIfPredicate(collection,0.5,0);
        
        }
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            int numExpectedCalls;
            if(collection.getDataType() == DataType.BOOLEAN){
                var c=collection.getCollection();
                if(c.contains(true)){
                    if(c.contains(false)){
                        numExpectedCalls=2;
                    }else{
                        numExpectedCalls=1;
                    }
                }else{
                    if(c.contains(false)){
                        numExpectedCalls=1;
                    }else{
                        numExpectedCalls=0;
                    }
                }
            }else{
                numExpectedCalls=collection.size();
            }
            Random rand=new Random(randSeed);
            final int throwIndex=(int)(rand.nextDouble() * numExpectedCalls);
            return new MonitoredRemoveIfPredicate(){
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
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,PredicateGenCallType predicateGenCallType){
        this.expectedException=expectedException;
        this.predicateGenCallType=predicateGenCallType;
    }
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection);
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,boolean compliment,long removeIndicesBitSet);
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
            double threshold,long randSeed);
    
    public static enum PredicateGenCallType{
        Randomized,
        NonRandomized,
        IndexSpecific;
    }
    
}
