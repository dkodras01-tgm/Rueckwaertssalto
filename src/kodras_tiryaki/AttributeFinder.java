package kodras_tiryaki;

import com.change_vision.jude.api.inf.model.IERAttribute;
import com.change_vision.jude.api.inf.model.IEREntity;

/**
 * online: https://github.com/ChangeVision/astah-db-reverse-plugin/blob/master/src/main/java/com/change_vision/astah/extension/plugin/dbreverse/reverser/finder/DatatypeFinder.java
 * zuletzt besucht am: 14.02.2015
 * zuletzt aktualisiert: 2012
 * @author kompiro
 */
public class AttributeFinder {

    public IERAttribute find(IEREntity entity, String name) {
        if(entity == null) throw new IllegalArgumentException("entity is null");
        if(name == null) throw new IllegalArgumentException("name is null");
        
        IERAttribute[] primaryKeys = entity.getPrimaryKeys();
        for (IERAttribute pk : primaryKeys) {
            if(pk.getName().equals(name)) return pk;
        }
        IERAttribute[] nonPrimaryKeys = entity.getNonPrimaryKeys();
        for (IERAttribute nonPK : nonPrimaryKeys) {
            if(nonPK.getName().equals(name)) return nonPK;
        }
        return null;
    }

}