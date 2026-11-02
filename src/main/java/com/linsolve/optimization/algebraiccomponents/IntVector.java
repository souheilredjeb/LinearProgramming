package com.linsolve.optimization.algebraiccomponents;

public class IntVector {
	
	private Integer[] int_vect;

	public IntVector(Integer[] int_vect) {
		super();
		this.int_vect = int_vect;
	}
	
	

	public IntVector() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer[] getInt_vect() {
		return int_vect;
	}

	public void setInt_vect(Integer[] int_vect) {
		this.int_vect = int_vect;
	}
	
	public static void display_vector(IntVector v)
	{
		for(int i=0; i<v.getInt_vect().length;i++)
		{
			System.out.print(" [ "+v.getInt_vect()[i]+" ] ");
			System.out.println();
		}
	}

}
