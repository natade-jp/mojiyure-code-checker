package net.natade.util.string.code;

import net.natade.util.string.StringUTF32;

/**
 * ソースコードを扱うクラス
 * 
 * @author natade
 */
public abstract class Code {

	/**
	 * ソースコードの文字列
	 */
	protected int[] code = new int[0];

	/**
	 * 指定した文字コードが改行か調べる
	 * 
	 * @param code
	 * @return
	 */
	protected static boolean isDiverted(int code) {
		switch (code) {
		case 0x000D:// CR Carriage Return
		case 0x000A:// LF Line Feed
		case 0x0085:// NEL Next Line
		case 0x000B:// VT Vertical Tab
		case 0x000C:// FF Form Feed
		case 0x2028:// LS Line Separator
		case 0x2029:// PS Paragraph Separator
			return (true);
		}
		;
		return (false);
	}

	/**
	 * クラス作成のための用意
	 */
	public Code() {
	}
	
	/**
	 * ソースコード用のデータを用意する
	 * 
	 * @param code_text
	 */
	public Code(String code_text) {
		this.code = (new StringUTF32(code_text)).createUTF32Array();
	}

	/**
	 * 内部のデータから文字列を作成する
	 */
	public String toString() {
		return (new StringUTF32(this.code)).toString();
	}

	/**
	 * コード型を作成する
	 */
	public abstract Code create(String code_text);
	
	/**
	 * 文字列のみ出力する
	 */
	public abstract StringUTF32 getExtractedString();
	
	/**
	 * 内部のコードから、コメントアウトされている箇所を除去する
	 */
	public abstract StringUTF32 getRemoveComment();



}
