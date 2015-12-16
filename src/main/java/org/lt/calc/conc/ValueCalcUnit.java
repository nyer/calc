package org.lt.calc.conc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ValueCalcUnit extends AbstractCalcUnit {
	
	private CalcUnit value;
	
	public ValueCalcUnit(CalcUnit value) {
		
		this.value = value;
	}
	
	public int calc(final ExecutorService executorService) throws Exception {
		
		Future<Integer> future = getFuture(value, executorService);
		
		return future.get();
	}
	
}
