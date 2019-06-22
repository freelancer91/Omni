package omni.impl;

import java.util.Set;
public enum IllegalModification{
    NoMod{
    },
    ModCollection{
    },
    ModParent{
    },
    ModRoot{
    },
    ModItr{
    };
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
