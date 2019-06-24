package omni.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;
import java.util.Random;
import omni.impl.MonitoredCollection.MonitoredIterator;

public enum MonitoredFunctionGen{
    NoThrow(null,false){
      class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return NoThrow;
        }
        protected void throwingCall(){
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            return new Impl();
        }
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
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
    ModCollection(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ModCollection;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
            boolean alreadyModded=false;
            private static final long serialVersionUID=1L;
            @Override
            protected void throwingCall(){
              if(this.size() >= throwIndex && !alreadyModded){
                iteratorMonitor.illegalMod(IllegalModification.ModCollection);
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
    ModParent(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ModParent;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
            boolean alreadyModded=false;
            private static final long serialVersionUID=1L;
            @Override
            protected void throwingCall(){
              if(this.size() >= throwIndex && !alreadyModded){
                iteratorMonitor.illegalMod(IllegalModification.ModParent);
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
    ModRoot(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ModRoot;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
            boolean alreadyModded=false;
            private static final long serialVersionUID=1L;
            @Override
            protected void throwingCall(){
              if(this.size() >= throwIndex && !alreadyModded){
                iteratorMonitor.illegalMod(IllegalModification.ModRoot);
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
    ThrowIOB(IndexOutOfBoundsException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ThrowIOB;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
                private static final long serialVersionUID=1L;
                @Override
                protected void throwingCall(){
                    if(this.size() >= throwIndex){
                        throw new IndexOutOfBoundsException();
                    }
                }
            };
        }
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
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
    ThrowIOBModCollection(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ThrowIOBModCollection;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
            private static final long serialVersionUID=1L;
            @Override
            protected void throwingCall(){
                if(this.size() >= throwIndex){
                    iteratorMonitor.illegalMod(IllegalModification.ModCollection);
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
    ThrowIOBModParent(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ThrowIOBModParent;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
          final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
          return new Impl(){
            private static final long serialVersionUID=1L;
            @Override
            protected void throwingCall(){
                if(this.size() >= throwIndex){
                    iteratorMonitor.illegalMod(IllegalModification.ModParent);
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
    ThrowIOBModRoot(ConcurrentModificationException.class,false){
      abstract class Impl extends MonitoredFunction {
        private static final long serialVersionUID=1L;

        public MonitoredFunctionGen getMonitoredFunctionGen() {
          return ThrowIOBModRoot;
        }
      }
        @Override
        public MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed){
            int numExpectedCalls=collectionMonitor.size();
            final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
            return new Impl(){
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
        @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls){
           final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
           return new Impl(){
             private static final long serialVersionUID=1L;
             @Override
             protected void throwingCall(){
                 if(this.size() >= throwIndex){
                     iteratorMonitor.illegalMod(IllegalModification.ModRoot);
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
      @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,
          int numExpectedCalls){
        final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
        return new MonitoredFunction(){
          boolean alreadyModded=false;
          private static final long serialVersionUID=1L;
          public MonitoredFunctionGen getMonitoredFunctionGen() {
            return ModItr;
          }
          @Override
          protected void throwingCall(){
            if(this.size() >= throwIndex && !alreadyModded){
              iteratorMonitor.illegalMod(IllegalModification.ModItr);
              alreadyModded=true;
          }
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
      @Override public MonitoredFunction getMonitoredFunction(MonitoredIterator<?,?> iteratorMonitor,long randSeed,
          int numExpectedCalls){
        final int throwIndex=(int)(new Random(randSeed).nextDouble() * numExpectedCalls);
        return new MonitoredFunction(){
          private static final long serialVersionUID=1L;
          public MonitoredFunctionGen getMonitoredFunctionGen() {
            return ThrowIOBModItr;
          }
          @Override
          protected void throwingCall(){
            if(this.size() >= throwIndex){
              iteratorMonitor.illegalMod(IllegalModification.ModItr);
              throw new IndexOutOfBoundsException();
          }
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
    public final boolean appliesToItr;
    MonitoredFunctionGen(Class<? extends Throwable> expectedException,boolean appliesToItr){
        this.expectedException=expectedException;
        this.appliesToItr=appliesToItr;
    }
    public abstract MonitoredFunction getMonitoredFunction(MonitoredCollection.MonitoredIterator<?,?> iteratorMonitor,long randSeed,int numExpectedCalls);
    public abstract MonitoredFunction getMonitoredFunction(MonitoredCollection<?> collectionMonitor,long randSeed);
    public abstract MonitoredArrayConstructor<Object> getMonitoredArrayConstructor(
            MonitoredCollection<?> collectionMonitor);
    public abstract MonitoredObjectInputStream getMonitoredObjectInputStream(MonitoredCollection<?> collection,
            InputStream inputStream) throws IOException;
    public abstract MonitoredObjectOutputStream getMonitoredObjectOutputStream(MonitoredCollection<?> collection,
            OutputStream inputStream) throws IOException;
}
