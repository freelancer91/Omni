package omni.impl;

import java.util.ConcurrentModificationException;

public enum MonitoredObjectGen{
    NoThrow(null,0){
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new MonitoredObject();
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new MonitoredObject(val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new MonitoredObject();
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new MonitoredObject(val);
        }
    },
    ModCollection(ConcurrentModificationException.class,0){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModCollection);

                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModParent(ConcurrentModificationException.class,2){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModParent);

                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModRoot(ConcurrentModificationException.class,1){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModRoot);

                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ThrowIOB(IndexOutOfBoundsException.class,0){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    throw new IndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ThrowAIOB(IllegalArgumentException.class,0){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModCollectionThrowIOB(ConcurrentModificationException.class,0){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModCollection);
                    throw new IndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModCollectionThrowAIOB(ConcurrentModificationException.class,0){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModCollection);
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModParentThrowIOB(ConcurrentModificationException.class,2){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModParent);
                    throw new IndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModParentThrowAIOB(ConcurrentModificationException.class,2){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModParent);
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModRootThrowIOB(ConcurrentModificationException.class,1){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModRoot);
                    throw new IndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },
    ModRootThrowAIOB(ConcurrentModificationException.class,1){
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            Impl(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
                super(monitoredCollection,throwSwitch);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
                super(monitoredCollection,val,throwSwitch);
            }
            @Override
            protected void throwingCall(){
                if(throwSwitch.doThrow){
                    throwSwitch.doThrow=false;
                    monitoredCollection.illegalMod(IllegalModification.ModRoot);
                    throw new ArrayIndexOutOfBoundsException();
                }
            }
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new Impl(collection);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new Impl(collection,val);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch){
            return new Impl(collection,throwSwitch);
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,ThrowSwitch throwSwitch){
            return new Impl(collection,val,throwSwitch);
        }
    },;
    public final Class<? extends Throwable> expectedException;
    public final int minDepth;
    MonitoredObjectGen(Class<? extends Throwable> expectedException,int minDepth){
        this.expectedException=expectedException;
        this.minDepth=minDepth;
    }
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection);
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val);
    private static abstract class ModifyingMonitoredObj extends MonitoredObject{
        final MonitoredCollection<?> monitoredCollection;
        final ThrowSwitch throwSwitch;
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection){
            super();
            this.monitoredCollection=monitoredCollection;
            this.throwSwitch=new ThrowSwitch(true);
        }
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection,int val){
            super(val);
            this.monitoredCollection=monitoredCollection;
            this.throwSwitch=new ThrowSwitch(true);
        }
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection,ThrowSwitch throwSwitch){
            super();
            this.monitoredCollection=monitoredCollection;
            this.throwSwitch=throwSwitch;
        }
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection,int val,ThrowSwitch throwSwitch){
            super(val);
            this.monitoredCollection=monitoredCollection;
            this.throwSwitch=throwSwitch;
        }
    }
    public static class ThrowSwitch{
        public boolean doThrow;
        public ThrowSwitch(){
            doThrow=false;
        }
        public ThrowSwitch(boolean doThrow){
            this.doThrow=doThrow;
        }
    }
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val,
            ThrowSwitch throwSwitch);
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,ThrowSwitch throwSwitch);
}
