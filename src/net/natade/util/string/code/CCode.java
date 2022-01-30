package net.natade.util.string.code;

import java.util.ArrayList;

import net.natade.util.string.StringUTF32;

public class CCode extends Code {

	public CCode() {
		super();
	}
	
	public CCode(String code_text) {
		super(code_text);
	}

	public Code create(String code_text) {
		return new CCode(code_text);
	}

	public StringUTF32 getExtractedString() {
		int[] data = this.getRemoveComment().createUTF32Array();
		ArrayList<Integer> char_array = new ArrayList<Integer>();
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
					continue;
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
					continue;
				}
			} else {
				if (moji == '\'') {
					istextB = true;
					data[i] = ' ';
				}
			}

			// 改行部分は追加する
			if (isDiverted(moji)) {
				char_array.add(moji);
			}
		}

		int[] int_array = char_array.stream().mapToInt(x -> x).toArray();
		return new StringUTF32(int_array);
	}

	public StringUTF32 getRemoveComment() {
		int[] data = this.code;
		System.arraycopy(this.code, 0, data, 0, this.code.length);
		
		boolean istextA = false;
		boolean istextB = false;
		boolean isescape = false;
		boolean commentA1 = false;
		boolean commentA2 = false;
		boolean commentB2 = false;
		boolean commentB3 = false;
		for (int i = 0; i < data.length; i++) {
			int moji = data[i];
			
			if (!commentA2 && !commentB2) {
				// 文字列（ダブルクォーテーション）
				if (istextA) {
					if (isescape) {
						isescape = false;
						continue;
					} else {
						// 文字列内部
						if (moji == '\\') {
							isescape = true;
						} else if (moji == '\"') {
							istextA = false;
						}
						continue;
					}
				} else {
					if (moji == '\"') {
						istextA = true;
						continue;
					}
				}
	
				// 文字列（シングルクォーテーション）
				if (istextB) {
					if (isescape) {
						isescape = false;
						continue;
					} else {
						// 文字列内部
						if (moji == '\\') {
							isescape = true;
						} else if (moji == '\'') {
							istextB = false;
						}
						continue;
					}
				} else {
					if (moji == '\'') {
						istextB = true;
						continue;
					}
				}
			}
			
			// コメント /*
			if (commentB2) {
				// コメント内部
				if (commentB3) {
					if (moji == '/') {
						// */ なのでコメント解除
						commentB2 = false;
						commentB3 = false;
						data[i] = ' ';
						continue;
					}
				}
				if (moji == '*') {
					commentB3 = true;
				}
				else {
					commentB3 = false;
				}
				if (!isDiverted(moji)) {
					data[i] = ' ';
				}
				continue;
			}

			// コメント //
			if (commentA2) {
				// コメント内部

				if (!isDiverted(moji)) {
					data[i] = ' ';
				}
				else {
					// 改行があったらコメントを解除
					commentA1 = false;
					commentA2 = false;
				}
				continue;
			} else if (commentA1) {
				if (moji == '/') {
					// //
					commentA2 = true;
					data[i - 1] = ' ';
					data[i] = ' ';
					continue;
				} else if (moji == '*') {
					// /*
					commentB2 = true;
					commentA1 = false;
					data[i - 1] = ' ';
					data[i] = ' ';
					continue;
				} else {
					// コメントではない
					commentA1 = false;
				}
			} else {
				if (moji == '/') {
					commentA1 = true;
					continue;
				}
			}
		}

		return new StringUTF32(data);
	}
	
}
