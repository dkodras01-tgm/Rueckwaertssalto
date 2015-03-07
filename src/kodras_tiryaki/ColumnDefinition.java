package kodras_tiryaki;

public class ColumnDefinition {
	private String FIELD;
	private String TYPE;
	private String NULL;
	private String KEY;
	private String DEFAULT;
	private String EXTRA;

	public ColumnDefinition(String FIELD, String TYPE, String NULL, String KEY,
			String DEFAULT, String EXTRA) {
		super();
		this.FIELD = FIELD;
		this.TYPE = TYPE;
		this.NULL = NULL;
		this.KEY = KEY;
		this.DEFAULT = DEFAULT;
		this.EXTRA = EXTRA;
	}

	public String getFIELD() {
		return FIELD;
	}

	public void setFIELD(String fIELD) {
		FIELD = fIELD;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getNULL() {
		return NULL;
	}

	public void setNULL(String nULL) {
		NULL = nULL;
	}

	public String getKEY() {
		return KEY;
	}

	public void setKEY(String kEY) {
		KEY = kEY;
	}

	public String getDEFAULT() {
		return DEFAULT;
	}

	public void setDEFAULT(String dEFAULT) {
		DEFAULT = dEFAULT;
	}

	public String getEXTRA() {
		return EXTRA;
	}

	public void setEXTRA(String eXTRA) {
		EXTRA = eXTRA;
	}

	@Override
	public String toString() {
		return "ColumnDefinition [FIELD=" + FIELD + ", TYPE=" + TYPE
				+ ", NULL=" + NULL + ", KEY=" + KEY + ", DEFAULT=" + DEFAULT
				+ ", EXTRA=" + EXTRA + "]";
	}
}