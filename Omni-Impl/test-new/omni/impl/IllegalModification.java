package omni.impl;

import java.util.ConcurrentModificationException;
import java.util.Set;
public enum IllegalModification{
    NoMod(null){
    },
    ModCollection(ConcurrentModificationException.class){
    },
    ModParent(ConcurrentModificationException.class){
    },
    ModRoot(ConcurrentModificationException.class){
    },
    ModItr(ConcurrentModificationException.class){
    };
    public final Class<? extends Throwable> expectedException;
    IllegalModification(Class<? extends Throwable> expectedException){
        this.expectedException=expectedException;
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
