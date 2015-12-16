package org.lt.calc;

import java.util.Scanner;

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


enum TokenType {
	NUMBER,
	SUB,
	ADD,
	MULTI,
	OPEN_PARATHESIS,
	CLOSE_PARATHESIS,
	DIV
}

class Token {
	int start;
	
	int end;
	
	TokenType type;
	
	String str;
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getStr() {
		return str;
	}

	@Override
	public String toString() {
		return "Token [start=" + start + ", end=" + end + ", type=" + type + ", str=" + str + "]";
	}
	
	
}

class Tokenizer {
	
	private String express;
	
	private int length;
	private int pos;
	
	public Tokenizer(String express) {
		this.express = express;
		
		this.length = express.length();
		this.pos = 0;
	}
	
	public Token expect(TokenType tokenType) {
		if (hasMore() == false) {
			throw new RuntimeException("Unexpected EOF");
		}
		
		Token token = peekNext();
		if (token.getType() == tokenType) {
			accept(token);
			return token;
		} else {
			throw new RuntimeException(tokenType + " Expected, but " + token + " is found");
		}
	}
	
	public void accept(Token token) {
		this.pos = token.getEnd();
	}
	
	public boolean accept(TokenType tokenType) {
		
		if (hasMore() == false) {
			return false;
		} else {
			Token token = peekNext();
			if (token.getType() == tokenType) {
				
				accept(token);
				return true;
			} else {
				return false;
			}
		}
	}
	
	public boolean peek(TokenType tokenType) {
		
		if (hasMore() == false) {
			return false;
		} else {
			Token token = peekNext();
			return token.getType() == tokenType;
		}
	}
	
	private Token peekNext() {
		
		int cur = pos;
		Token token = new Token();
		TokenType tokenType = null;
		while (cur < length) {
			char ch = express.charAt(cur ++);
			switch (ch) {
			case '+':
				tokenType = TokenType.ADD;
				break;
			case '-':
				tokenType = TokenType.SUB;
				break;
			case '*':
				tokenType = TokenType.MULTI;
				break;
			case '/':
				tokenType = TokenType.DIV;
				break;
			case '(':
				tokenType = TokenType.OPEN_PARATHESIS;
				break;
			case ')':
				tokenType = TokenType.CLOSE_PARATHESIS;
				break;
			case ' ':
				break;
			default:
				if (ch >= '0' && ch <= '9') {

					tokenType = TokenType.NUMBER;
					while (cur < length) {
						ch = express.charAt(cur);
						if (ch < '0' || ch > '9') {
							break;
						}
						cur ++;
					}
				} else {
					throw new RuntimeException("Illegal char '" + ch + "' at " + (cur -1));
				}
			}
			
			if (tokenType != null) {
				break;
			}
		}
		
		token = new Token();
		token.start = this.pos;
		token.end = cur;
		token.str = express.substring(token.start, token.end);
		token.type = tokenType;
		
		return token;
	}
	
	
	
	public boolean hasMore() {
		
		return pos < length;
	}
}
