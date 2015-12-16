package org.lt.calc.conc;

import java.util.concurrent.ExecutorService;

public interface CalcUnit {
	
	int calc(ExecutorService executorService) throws Exception ;
}
