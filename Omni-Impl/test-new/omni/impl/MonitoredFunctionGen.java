package omni.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;
import java.util.Random;

public enum MonitoredFunctionGen{
    NoThrow(null){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new MonitoredFunction();
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor);
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream);
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream);
        }
    },
    ModCollection(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                boolean alreadyModded=false;
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex && !alreadyModded){
                        collectionMonitor.illegalMod(IllegalModification.ModCollection);
                        alreadyModded=true;
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
    },
    ModParent(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                boolean alreadyModded=false;
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex && !alreadyModded){
                        collectionMonitor.illegalMod(IllegalModification.ModParent);
                        alreadyModded=true;
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModParent);
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                }
            };
        }
    },
    ModRoot(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                boolean alreadyModded=false;
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex && !alreadyModded){
                        collectionMonitor.illegalMod(IllegalModification.ModRoot);
                        alreadyModded=true;
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModRoot);
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                }
            };
        }
    },
    ThrowIOB(IndexOutOfBoundsException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex){
                        throw new IndexOutOfBoundsException();
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ThrowIOBModCollection(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex){
                        collectionMonitor.illegalMod(IllegalModification.ModCollection);
                        throw new IndexOutOfBoundsException();
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ThrowIOBModParent(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex){
                        collectionMonitor.illegalMod(IllegalModification.ModParent);
                        throw new IndexOutOfBoundsException();
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },
    ThrowIOBModRoot(ConcurrentModificationException.class){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new MonitoredFunction(){
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex){
                        collectionMonitor.illegalMod(IllegalModification.ModRoot);
                        throw new IndexOutOfBoundsException();
                    }
                }
            };
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor){
                @Override
                protected void throwingCall(){
                    collectionMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void preModCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }
            };
        }
    },;
    public final Class<? extends Throwable> expectedException;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
    }
    public abstract MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed);
    public abstract MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(
            MonitoredCollection<?> collectionMonitor);
    public abstract MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
            InputStream inputStream) throws IOException;
    public abstract MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
            OutputStream inputStream) throws IOException;
}
