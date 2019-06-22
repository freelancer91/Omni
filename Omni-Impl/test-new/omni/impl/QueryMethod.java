package omni.impl;

import java.util.Set;
public enum QueryMethod{
    Contains,
    RemoveVal,
    RemoveFirstOccurrence,
    RemoveLastOccurrence,
    IndexOf,
    LastIndexOf,
    Search;

    public static final Set<QueryMethod> BASIC_COLLECTION_METHODS=Set.of(QueryMethod.Contains,QueryMethod.RemoveVal);
    public static final Set<QueryMethod> STACK_METHODS=Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.Search);
    public static final Set<QueryMethod> LIST_METHODS=Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.IndexOf,QueryMethod.LastIndexOf);
    public static final Set<QueryMethod> DEQUE_METHODS=Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,QueryMethod.RemoveLastOccurrence);
    public static final Set<QueryMethod> LISTDEQUE_METHODS=Set.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.IndexOf,QueryMethod.LastIndexOf,QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,
            QueryMethod.RemoveLastOccurrence);
}
