package net.natade.util.string;

import java.util.ArrayList;

/**
 * ソースコードを扱うクラス
 * 
 * @author natade
 */
public class Code {

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
	 * コメントがないコードから、文字列のみ出力する - 事前にコメントを除去した後に実行して下さい
	 */
	public String getExtractedString() {
		ArrayList<Integer> char_array = new ArrayList<Integer>();
		int[] data = this.code;
		boolean istextA = false;
		boolean istextB = false;
		boolean isescape = false;
		for (int i = 0; i < data.length; i++) {
			int moji = data[i];

			// 文字列（ダブルクォーテーション）
			if (istextA) {
				if (isescape) {
					isescape = false;
					data[i] = ' ';
				} else {
					// 文字列内部
					if (moji == '\\') {
						isescape = true;
					} else if (moji == '\"') {
						istextA = false;
					} else {
						char_array.add(moji);
					}
				}
			} else {
				if (moji == '\"') {
					istextA = true;
				}
			}

			// 文字列（シングルクォーテーション）
			if (istextB) {
				if (isescape) {
					isescape = false;
					data[i] = ' ';
				} else {
					// 文字列内部
					if (moji == '\\') {
						isescape = true;
					} else if (moji == '\'') {
						istextB = false;
					} else {
						char_array.add(moji);
					}
				}
			} else {
				if (moji == '\'') {
					istextB = true;
					data[i] = ' ';
				}
			}

			if (isDiverted(moji)) {
				char_array.add(moji);
			}
		}

		int[] int_array = char_array.stream().mapToInt(x -> x).toArray();
		return (new StringUTF32(int_array)).toString();
	}

	/**
	 * 内部のコードから、コメントアウトされている箇所を除去する
	 */
	public void removeComment() {
		int[] data = this.code;
		boolean commentA1 = false;
		boolean commentA2 = false;
		boolean commentB2 = false;
		boolean commentB3 = false;
		for (int i = 0; i < data.length; i++) {
			// コメント /*
			if (commentB2) {
				// コメント内部
				if (commentB3) {
					if (data[i] == '/') {
						commentB2 = false;
						commentB3 = false;
						data[i] = ' ';
						continue;
					} else {
						commentB3 = false;
					}
				} else {
					if (data[i] == '*') {
						commentB3 = true;
						data[i] = ' ';
						continue;
					}
				}
				if (isDiverted(data[i])) {
					continue;
				}
				data[i] = ' ';
				continue;
			}

			// コメント //
			if (commentA2) {
				// コメント内部
				if (isDiverted(data[i])) {
					commentA1 = false;
					commentA2 = false;
					continue;
				}
				data[i] = ' ';
				continue;
			} else if (commentA1) {
				if (data[i] == '/') {
					commentA2 = true;
					data[i] = ' ';
					continue;
				} else if (data[i] == '*') {
					commentB2 = true;
					commentA1 = false;
					data[i] = ' ';
					continue;
				} else {
					commentA1 = false;
				}
			} else {
				if (data[i] == '/') {
					commentA1 = true;
					data[i] = ' ';
					continue;
				}
			}
		}
	}

	/**
	 * コメントがないコードから文字列を削除する - 事前にコメントを除去した後に実行して下さい
	 */
	public void removeString() {
		int[] data = this.code;
		boolean istextA = false;
		boolean istextB = false;
		boolean isescape = false;
		for (int i = 0; i < data.length; i++) {
			// 文字列（ダブルクォーテーション）
			if (istextA) {
				if (isescape) {
					isescape = false;
					data[i] = ' ';
					continue;
				} else {
					// 文字列内部
					if (data[i] == '\\') {
						isescape = true;
					} else if (data[i] == '\"') {
						istextA = false;
					}
					data[i] = ' ';
					continue;
				}
			} else {
				if (data[i] == '\"') {
					istextA = true;
					data[i] = ' ';
					continue;
				}
			}

			// 文字列（シングルクォーテーション）
			if (istextB) {
				if (isescape) {
					isescape = false;
					data[i] = ' ';
					continue;
				} else {
					// 文字列内部
					if (data[i] == '\\') {
						isescape = true;
					} else if (data[i] == '\'') {
						istextB = false;
					}
					data[i] = ' ';
					continue;
				}
			} else {
				if (data[i] == '\'') {
					istextB = true;
					data[i] = ' ';
					continue;
				}
			}
		}
	}

}
