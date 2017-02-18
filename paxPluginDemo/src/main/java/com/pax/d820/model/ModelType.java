package com.pax.d820.model;

public class ModelType {

	private String type;
	private boolean isSelect;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public ModelType(String type, boolean isSelect) {
		super();
		this.type = type;
		this.isSelect = isSelect;
	}

	public ModelType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
