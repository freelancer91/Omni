package omni.impl;

import java.util.EnumSet;
public enum QueryMethod{
    Contains,
    RemoveVal,
    RemoveFirstOccurrence,
    RemoveLastOccurrence,
    IndexOf,
    LastIndexOf,
    Search;

    public static final EnumSet<QueryMethod> BASIC_COLLECTION_METHODS=EnumSet.of(QueryMethod.Contains,
            QueryMethod.RemoveVal);
    public static final EnumSet<QueryMethod> STACK_METHODS=EnumSet.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.Search);
    public static final EnumSet<QueryMethod> LIST_METHODS=EnumSet.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.IndexOf,QueryMethod.LastIndexOf);
    public static final EnumSet<QueryMethod> DEQUE_METHODS=EnumSet.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,QueryMethod.RemoveLastOccurrence);
    public static final EnumSet<QueryMethod> LISTDEQUE_METHODS=EnumSet.of(QueryMethod.Contains,QueryMethod.RemoveVal,
            QueryMethod.IndexOf,QueryMethod.LastIndexOf,QueryMethod.Search,QueryMethod.RemoveFirstOccurrence,
            QueryMethod.RemoveLastOccurrence);
}
