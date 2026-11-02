package com.linsolve.optimization.linearproblem;

public enum ExtremumEnum {
	
	MAXIMIZE(1,"MAXIMIZE"),
	MINIMIZE(-1,"MINIMIZE");
	private int code;
	private String libelle;

	private ExtremumEnum(int code, String libelle) {
		this.code = code;
		this.libelle = libelle;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}	

}
