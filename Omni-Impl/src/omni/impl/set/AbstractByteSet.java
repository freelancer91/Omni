package omni.impl.set;

import java.util.Set;
import omni.api.OmniSet;

abstract class AbstractByteSet implements OmniSet.OfByte{
  
    
    @Override public boolean add(Byte val) {
        return add((byte)val);
    }
    @Override public boolean equals(Object val){
        if(val==this){
          return true;
        }
        if(val instanceof Set){
          //TODO optimize
          final int size;
          if((size=this.size())==0){
            return ((Set<?>)val).isEmpty();
          }
          final Set<?> set;
          if(size==(set=(Set<?>)val).size()){
            if(val instanceof OmniSet){
              if(val instanceof OmniSet.OfByte){
                return ((OmniSet.OfByte)set).containsAll(this);
              }else if(val instanceof OmniSet.OfRef){
                return ((OmniSet.OfRef<?>)set).containsAll(this);
              }
            }else{
              return set.containsAll(this);
            }
          }
        }
        return false;
      }
    
}
