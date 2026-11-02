package com.linsolve.optimization.exceptions.components;


public class MatrixConstructionException_001 extends RuntimeException
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	
	 public MatrixConstructionException_001(MatrixConstructionEnum matrixConstructionEnum) 
    {
        super(matrixConstructionEnum.getMessage());
        this.code = matrixConstructionEnum.getCode();
    }

	 public Integer getCode() {
		 return code;
	 }

	 public void setCode(Integer code) {
		 this.code = code;
	 }
	 

}
