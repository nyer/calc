package org.lt.calc.conc;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lt.calc.lexer.Token;
import org.lt.calc.lexer.TokenType;
import org.lt.calc.lexer.Tokenizer;

/**
 * Calculator calculate concurrently
 * @author leiting
 *
 */
public class ConcCalc {

	private Tokenizer tokenizer;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
			
	public ConcCalc(String express) {
		
		this.tokenizer = new Tokenizer(express);
	}
	
	public int eval() throws Exception {
		
		CalcUnit calcUnit = null;
		while (tokenizer.hasMore()) {
			calcUnit = statement();
		}
		
		return calcUnit.calc(executorService);
	}
	
	CalcUnit statement()  throws Exception {
		return subAdd();
	}
	
	CalcUnit subAdd() throws Exception {
		CalcUnit result = multiDiv();
		while (tokenizer.peek(TokenType.SUB) || tokenizer.peek(TokenType.ADD)) {
			if (tokenizer.accept(TokenType.ADD)) {
				result = new AddCalcUnit(result, multiDiv());
			} else if (tokenizer.accept(TokenType.SUB)){
				result = new SubCalcUnit(result, multiDiv());
			}
		}
		
		return result;
	}
	
	CalcUnit multiDiv() throws Exception {
		CalcUnit result = value();
		while (tokenizer.peek(TokenType.MULTI) || tokenizer.peek(TokenType.DIV)) {
			if (tokenizer.accept(TokenType.MULTI)) {
				result = new MultiCalcUnit(result, value());
			} else if (tokenizer.accept(TokenType.DIV)){
				result = new DivCalcUnit(result, value());
			}
		}
		
		return result;
	}
	
	CalcUnit value() throws Exception {
		
		while (tokenizer.accept(TokenType.OPEN_PARATHESIS)) {
			CalcUnit result = statement();
			tokenizer.expect(TokenType.CLOSE_PARATHESIS);
			
			return result;
		}

		Token token = tokenizer.expect(TokenType.NUMBER);
		return new NumberCalcUnit(Integer.parseInt(token.getStr()));
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {

			try {
				ConcCalc calc = new ConcCalc(scanner.nextLine());
				System.out.println(calc.eval());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

