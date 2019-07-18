package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Set;
public enum IllegalModification{
    NoMod(null,0){
    },
    ModCollection(ConcurrentModificationException.class,0){
    },
    ModParent(ConcurrentModificationException.class,2){
    },
    ModRoot(ConcurrentModificationException.class,1){
    },
    ModItr(ConcurrentModificationException.class,0){
    };
    public final Class<? extends Throwable> expectedException;
    public final int minDepth;
    IllegalModification(Class<? extends Throwable> expectedException,int minDepth){
        this.expectedException=expectedException;
        this.minDepth=minDepth;
    }
    public final UnsupportedOperationException invalid(){
        return new UnsupportedOperationException("Invalid IllegalModification " + this);
    }
    public static final Set<IllegalModification> SUBLIST_ITERATOR_MODS=Set.of(IllegalModification.NoMod,
            IllegalModification.ModCollection,IllegalModification.ModParent,IllegalModification.ModRoot,
            IllegalModification.ModItr);
    public static final Set<IllegalModification> BASIC_ITERATOR_MODS=Set.of(IllegalModification.NoMod,
            IllegalModification.ModCollection,IllegalModification.ModItr);
    public static final Set<IllegalModification> BASIC_COLLECTION_MODS=Set.of(IllegalModification.NoMod,
            IllegalModification.ModCollection);
    public static final Set<IllegalModification> SUBLIST_MODS=Set.of(IllegalModification.NoMod,
            IllegalModification.ModCollection,IllegalModification.ModParent,IllegalModification.ModRoot);
}
