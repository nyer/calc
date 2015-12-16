package org.lt.calc.lexer;

public class Tokenizer {
	
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
