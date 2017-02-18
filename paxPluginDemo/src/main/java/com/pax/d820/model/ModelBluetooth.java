package com.pax.d820.model;

import java.io.Serializable;

public class ModelBluetooth implements Serializable {

	private String name;
	private String identifier;
	private boolean isPrint;
	private boolean isPos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public ModelBluetooth() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isPrint() {
		return isPrint;
	}

	public void setPrint(boolean isPrint) {
		this.isPrint = isPrint;
	}

	public boolean isPos() {
		return isPos;
	}

	public void setPos(boolean isPos) {
		this.isPos = isPos;
	}

	public ModelBluetooth(String name, String identifier, boolean isPrint,
			boolean isPos) {
		super();
		this.name = name;
		this.identifier = identifier;
		this.isPrint = isPrint;
		this.isPos = isPos;
	}

}
