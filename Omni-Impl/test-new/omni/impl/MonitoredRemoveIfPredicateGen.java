package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Random;

public enum MonitoredRemoveIfPredicateGen{
    Random(null,true){
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
    },
    RemoveTrue(null,false){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
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
                    }
                    throw new UnsupportedOperationException("RemoveTrue not valid for " + val);
                }
            };
        }
    },
    RemoveFalse(null,false){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
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
                    }
                    throw new UnsupportedOperationException("RemoveFalse not valid for " + val);
                }
            };
        }
    },
    RemoveAll(null,false){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            return new MonitoredRemoveIfPredicate(){
                @Override
                protected boolean testImpl(){
                    return true;
                }
            };
        }
    },
    RemoveNone(null,false){
        @Override
        public MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
                double threshold,long randSeed){
            return new MonitoredRemoveIfPredicate(){
                @Override
                protected boolean testImpl(){
                    return false;
                }
            };
        }
    },
    Throw(IndexOutOfBoundsException.class,true){
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
    },
    ModCollection(ConcurrentModificationException.class,true){
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
    },
    ModParent(ConcurrentModificationException.class,true){
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
    ModRoot(ConcurrentModificationException.class,true){
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
    ThrowModCollection(ConcurrentModificationException.class,true){
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
    ThrowModParent(ConcurrentModificationException.class,true){
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
    ThrowModRoot(ConcurrentModificationException.class,true){
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
    public final Class<? extends Throwable> expectedException;
    public final boolean randomized;
    MonitoredRemoveIfPredicateGen(Class<? extends Throwable> expectedException,boolean randomized){
      this.expectedException=expectedException;
      this.randomized=randomized;
    }
    public abstract MonitoredRemoveIfPredicate getMonitoredRemoveIfPredicate(MonitoredCollection<?> collection,
            double threshold,long randSeed);
}
