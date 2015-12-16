package org.lt.calc.conc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public abstract class AbstractCalcUnit implements CalcUnit {

	Future<Integer> getFuture(final CalcUnit unit, final ExecutorService executorService) {
		
		return executorService.submit(new Callable<Integer>() {
			public Integer call() throws Exception {
				return unit.calc(executorService);
			}
		});
	}
}
