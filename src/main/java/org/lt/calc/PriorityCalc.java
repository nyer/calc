package org.lt.calc;

import java.util.Scanner;

import org.lt.calc.lexer.Token;
import org.lt.calc.lexer.TokenType;
import org.lt.calc.lexer.Tokenizer;

/**
 * Calculator with Priority
 * @author leiting
 *
 */
public class PriorityCalc {

	private Tokenizer tokenizer;
	
	public PriorityCalc(String express) {
		
		this.tokenizer = new Tokenizer(express);
	}
	
	public int eval() {
		
		int result = 0;
		while (tokenizer.hasMore()) {
			result = statement();
		}
		
		return result;
	}
	
	int statement() {
		return subAdd();
	}
	
	int subAdd() {
		int result = multiDiv();
		while (tokenizer.peek(TokenType.SUB) || tokenizer.peek(TokenType.ADD)) {
			if (tokenizer.accept(TokenType.ADD)) {
				result += multiDiv();
			} else if (tokenizer.accept(TokenType.SUB)){
				result -= multiDiv();
			}
		}
		
		return result;
	}
	
	int multiDiv() {
		int result = value();
		while (tokenizer.peek(TokenType.MULTI) || tokenizer.peek(TokenType.DIV)) {
			if (tokenizer.accept(TokenType.MULTI)) {
				result *= value();
			} else if (tokenizer.accept(TokenType.DIV)){
				result /= value();
			}
		}
		
		return result;
	}
	
	int value() {
		
		while (tokenizer.accept(TokenType.OPEN_PARATHESIS)) {
			int result = statement();
			tokenizer.expect(TokenType.CLOSE_PARATHESIS);
			
			return result;
		}

		Token token = tokenizer.expect(TokenType.NUMBER);
		return Integer.parseInt(token.getStr());
	}
	
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {

			try {
				PriorityCalc calc = new PriorityCalc(scanner.nextLine());
				System.out.println(calc.eval());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}


