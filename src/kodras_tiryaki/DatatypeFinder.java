package kodras_tiryaki;

import com.change_vision.jude.api.inf.model.IERDatatype;
import com.change_vision.jude.api.inf.model.IERSchema;

/**
 * online: https://github.com/ChangeVision/astah-db-reverse-plugin/blob/master/src/main/java/com/change_vision/astah/extension/plugin/dbreverse/reverser/finder/DatatypeFinder.java
 * zuletzt besucht am: 13.02.2015
 * zuletzt aktualisiert: 2012
 * @author kompiro
 */
public class DatatypeFinder {

    private IERSchema schema;

    public DatatypeFinder(IERSchema schema) {
        this.schema = schema;
    }

    public IERDatatype find(String name) {
        if(name == null) throw new IllegalArgumentException("name is null");
        IERDatatype[] datatypes = schema.getDatatypes();
        for (IERDatatype datatype : datatypes) {
            if (datatype.getName().equalsIgnoreCase(name)) {
                return datatype;
            }
        }
        return null;
    }
}