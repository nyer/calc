package org.lt.calc.lexer;

public class Token {
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
