package com.linsolve.optimization.algebraiccomponents;



public class Vector 
{
	
	private Double[] t;
	
	public Vector(Double[] t) {
		super();
		this.t = t;
	}



	public Double[] getT() {
		return t;
	}



	public void setT(Double[] t) {
		this.t = t;
	}

	public static void display_vector(Vector v)
	{
		for(int i=0; i<v.t.length;i++)
		{
			System.out.print(" [ "+v.t[i]+" ] ");
			System.out.println();
		}
	}
	
	public static void display_vector_h(Vector v)
	{
		System.out.print("[");
		for(int i=0; i<v.t.length;i++)
		{
			System.out.print(v.t[i]);
			System.out.print("|");
		}
		System.out.print("]");
		System.out.println();
	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
