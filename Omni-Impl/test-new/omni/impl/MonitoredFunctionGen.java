package omni.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;
import java.util.Random;

public enum MonitoredFunctionGen{
    NoThrow(null,false){
        class Impl extends MonitoredFunction {
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return NoThrow;
            }
            @Override
            protected void throwingCall(){
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl();
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl();
        }
        @Override
        public MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            return new MonitoredArrayConstructor<>(collectionMonitor);
        }
        @Override
        public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            return new MonitoredObjectInputStream(inputStream) {

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return NoThrow;
                }

                @Override protected void throwingCall(){}

            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream) {
                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return NoThrow;
                }

                @Override protected void throwingCall(){}
            };
        }
    },
    ModCollection(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ModCollection;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModCollection);
                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModCollection);
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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModCollection;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModCollection;
                }
            };
        }
    },
    ModParent(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ModParent;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModParent);
                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModParent);
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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModParent;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModParent;
                }
            };
        }
    },
    ModRoot(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ModRoot;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModRoot);

                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModRoot);

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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModRoot;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ModRoot;
                }
            };
        }
    },
    ThrowIOB(IndexOutOfBoundsException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ThrowIOB;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    throw new IndexOutOfBoundsException();

                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    throw new IndexOutOfBoundsException();

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
                protected void throwingCall(){
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOB;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOB;
                }
            };
        }
    },
    ThrowIOBModCollection(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ThrowIOBModCollection;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();

                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();

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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModCollection;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModCollection;
                }
            };
        }
    },
    ThrowIOBModParent(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ThrowIOBModParent;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();

                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();

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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModParent;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModParent;
                }
            };
        }
    },
    ThrowIOBModRoot(ConcurrentModificationException.class,true){
        abstract class Impl extends AbstractRandomizedImpl{
            Impl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
                super(randSeed,monitoredIterator);
            }
            Impl(long randSeed,MonitoredCollection<?> monitoredCollection){
                super(randSeed,monitoredCollection);
            }
            private static final long serialVersionUID=1L;

            @Override
            public MonitoredFunctionGen getMonitoredFunctionGen() {
                return ThrowIOBModRoot;
            }
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl(randSeed,collectionMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    collectionMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();

                }
            };
        }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new Impl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();

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
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModRoot;
                }
            };
        }
        @Override
        public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream outputStream) throws IOException{
            return new MonitoredObjectOutputStream(outputStream){
                @Override
                protected void throwingCall(){
                    collection.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }

                @Override public MonitoredFunctionGen getMonitoredFunctionGen(){
                    return ThrowIOBModRoot;
                }
            };
        }

    },
    ModItr(ConcurrentModificationException.class,true){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new AbstractRandomizedImpl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                public MonitoredFunctionGen getMonitoredFunctionGen() {
                    return ModItr;
                }
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModItr);
                }
            };
        }

        @Override public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredArrayConstructor<Object>
        getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream inputStream) throws IOException{
            throw new UnsupportedOperationException();
        }

    },
    ThrowIOBModItr(ConcurrentModificationException.class,true){
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
                long randSeed){
            return new AbstractRandomizedImpl(randSeed,iteratorMonitor){
                private static final long serialVersionUID=1L;
                @Override
                public MonitoredFunctionGen getMonitoredFunctionGen() {
                    return ThrowIOBModItr;
                }
                @Override
                void throwHelper(){
                    iteratorMonitor.illegalMod(IllegalModification.ModItr);
                    throw new IndexOutOfBoundsException();
                }
            };
        }

        @Override public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredArrayConstructor<Object>
        getMonitoredArrayConstructor(MonitoredCollection<?> collectionMonitor){
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
                InputStream inputStream) throws IOException{
            throw new UnsupportedOperationException();
        }

        @Override public MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
                OutputStream inputStream) throws IOException{
            throw new UnsupportedOperationException();
        }
    };
    public final Class<? extends Throwable> expectedException;
    public final boolean randomized;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean randomized){
        this.expectedException=expectedException;
        this.randomized=randomized;
    }

    public abstract MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,
            long randSeed);
    public abstract MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed);
    public abstract MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(
            MonitoredCollection<?> collectionMonitor);
    public abstract MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
            InputStream inputStream) throws IOException;
    public abstract MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
            OutputStream inputStream) throws IOException;
    private abstract static class AbstractRandomizedImpl extends MonitoredFunction{
        private static final long serialVersionUID=1L;
        final int throwingIndex;
        boolean alreadyTriggered;
        AbstractRandomizedImpl(long randSeed,MonitoredCollection<?> monitoredCollection){
            this.throwingIndex=new Random(randSeed).nextInt(monitoredCollection.size() + 1);
        }
        AbstractRandomizedImpl(long randSeed,MonitoredCollection.MonitoredIterator<?,?> monitoredIterator){
            this.throwingIndex=new Random(randSeed).nextInt(monitoredIterator.getNumLeft() + 1);
        }
        abstract void throwHelper();
        @Override
        protected void throwingCall(){
            if(this.size() >= throwingIndex && !alreadyTriggered){
                throwHelper();
                alreadyTriggered=true;
            }
        }
    }
}
