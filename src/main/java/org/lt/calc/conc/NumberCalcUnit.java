package org.lt.calc.conc;

import java.util.concurrent.ExecutorService;

public class NumberCalcUnit extends AbstractCalcUnit {

	private int num;
	
	public NumberCalcUnit(int num) {
		this.num = num;
	}
	
	public int calc(ExecutorService executorService) throws Exception {
		
		return num;
	}
}
