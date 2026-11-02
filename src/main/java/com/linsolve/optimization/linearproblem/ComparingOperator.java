package com.linsolve.optimization.linearproblem;

public enum ComparingOperator {
	LESSERTHAN(1,"<="),
	GREATERTHAN(-1,">="),
	EQUALS(0,"=");	
	private int code;
	private String libelle;
	private ComparingOperator(int code, String libelle)
	{
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
