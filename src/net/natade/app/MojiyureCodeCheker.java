package net.natade.app;

import java.util.List;

import com.atilika.kuromoji.unidic.Token;
import com.atilika.kuromoji.unidic.Tokenizer;

public class MojiyureCodeCheker {
	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer() ;
		List<Token> tokens = tokenizer.tokenize("テストコマンドです。");
		for (Token token : tokens) {
			System.out.println(token.getAllFeatures());
		}
	}
}
