package kodras_tiryaki;

import com.change_vision.jude.api.inf.editor.ERModelEditor;
import com.change_vision.jude.api.inf.model.IEREntity;
import com.change_vision.jude.api.inf.model.IERModel;
import com.change_vision.jude.api.inf.model.IERSchema;
import com.change_vision.jude.api.inf.project.ProjectAccessor;

public class ERAdapter {
	private static ERModelEditor editor;
	private static ProjectAccessor prjAccessor;
	private static IEREntity[] entities;
	private static IERModel model;
	private static IERSchema schema;
	
	public ERModelEditor getEditor() {
		return editor;
	}
	
	public void setEditor(ERModelEditor editor) {
		ERAdapter.editor = editor;
	}
	
	public ProjectAccessor getPrjAccessor() {
		return prjAccessor;
	}
	
	public void setPrjAccessor(ProjectAccessor prjAccessor) {
		ERAdapter.prjAccessor = prjAccessor;
	}
	
	public IEREntity[] getEntities() {
		return entities;
	}
	
	public void setEntities(IEREntity[] entities) {
		ERAdapter.entities = entities;
	}
	
	public IERModel getModel() {
		return model;
	}
	
	public void setModel(IERModel model) {
		ERAdapter.model = model;
	}
	
	public IERSchema getSchema() {
		return schema;
	}
	
	public void setSchema(IERSchema schema) {
		ERAdapter.schema = schema;
	}
}