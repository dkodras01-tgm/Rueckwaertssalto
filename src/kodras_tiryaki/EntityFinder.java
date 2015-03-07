package kodras_tiryaki;

import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERSchema;

/**
 * online: https://github.com/ChangeVision/astah-db-reverse-plugin/blob/master/src/main/java/com/change_vision/astah/extension/plugin/dbreverse/reverser/finder/DatatypeFinder.java
 * zuletzt besucht am: 13.02.2015
 * zuletzt aktualisiert: 2012
 * @author kompiro
 */
public class EntityFinder {

    private IERSchema schema;

    public EntityFinder(IERSchema schema) {
        this.schema = schema;
    }

    public IEREntity find(String name) {
        if (name == null) throw new IllegalArgumentException("name is null");
        for (IEREntity entity : schema.getEntities()) {
            if (entity.getName().equals(name)) {
                return entity;
            }
        }
        return null;
    }

}