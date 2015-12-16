package org.lt.calc.conc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SubCalcUnit extends AbstractCalcUnit {
	
	private CalcUnit left;
	
	private CalcUnit right;
	
	public SubCalcUnit(CalcUnit left, CalcUnit right) {
		
		this.left = left;
		this.right = right;
	}
	
	public int calc(final ExecutorService executorService) throws Exception {
		
		Future<Integer> future1 = getFuture(left, executorService);
		Future<Integer> future2 = getFuture(right, executorService);
		
		return future1.get() - future2.get();
	}
	
}
