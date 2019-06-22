package omni.impl;

public enum MonitoredObjectGen{
    NoThrow{
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection){
            return new MonitoredObject();
        }
        @Override
        public MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val){
            return new MonitoredObject(val);
        }
    },
    ModCollection{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                if(!alreadyModified){
                    monitoredCollection.illegalMod(IllegalModification.ModCollection);
                    alreadyModified=true;
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
    },
    ModParent{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                if(!alreadyModified){
                    monitoredCollection.illegalMod(IllegalModification.ModParent);
                    alreadyModified=true;
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
    },
    ModRoot{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                if(!alreadyModified){
                    monitoredCollection.illegalMod(IllegalModification.ModRoot);
                    alreadyModified=true;
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
    },
    ThrowIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                throw new IndexOutOfBoundsException();
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
    },
    ThrowAIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                throw new ArrayIndexOutOfBoundsException();
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
    },
    ModCollectionThrowIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModCollection);
                throw new IndexOutOfBoundsException();
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
    },
    ModCollectionThrowAIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModCollection);
                throw new ArrayIndexOutOfBoundsException();
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
    },
    ModParentThrowIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModParent);
                throw new IndexOutOfBoundsException();
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
    },
    ModParentThrowAIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModParent);
                throw new ArrayIndexOutOfBoundsException();
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
    },
    ModRootThrowIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModRoot);
                throw new IndexOutOfBoundsException();
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
    },
    ModRootThrowAIOB{
        class Impl extends ModifyingMonitoredObj{
            Impl(MonitoredCollection<?> monitoredCollection){
                super(monitoredCollection);
            }
            Impl(MonitoredCollection<?> monitoredCollection,int val){
                super(monitoredCollection,val);
            }
            @Override
            protected void throwingCall(){
                monitoredCollection.illegalMod(IllegalModification.ModRoot);
                throw new ArrayIndexOutOfBoundsException();
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
    },;
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection);
    public abstract MonitoredObject getMonitoredObject(MonitoredCollection<?> collection,int val);
    private static abstract class ModifyingMonitoredObj extends MonitoredObject{
        final MonitoredCollection<?> monitoredCollection;
        boolean alreadyModified=false;
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection){
            super();
            this.monitoredCollection=monitoredCollection;
        }
        ModifyingMonitoredObj(MonitoredCollection<?> monitoredCollection,int val){
            super(val);
            this.monitoredCollection=monitoredCollection;
        }
    }
}
