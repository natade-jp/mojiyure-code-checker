package net.natade.util;

/**
 * utf-16(String) を utf-32 (int)として扱います
 * @author natade
 */
final public class StringUTF32 {

	/**
	 * utf-32 の数値データ
	 */
	private int[] utf32 = null;

	/**
	 * utf-16 の文字列データ
	 */
	private String utf16 = null;

	/**
	 * 文字列を初期化する
	 * @param text 初期化用の文字列
	 */
	public StringUTF32(String text) {
		this.utf16 = text;
		this.utf32 = toUTF32FromUTF16(text);
	}

	/**
	 * 文字列を初期化する
	 * @param codepoint_array
	 */
	public StringUTF32(int[] codepoint_array) {
		this.utf16 = toUTF16FromUTF32(codepoint_array);
		this.utf32 = new int[codepoint_array.length];
		System.arraycopy(codepoint_array, 0, this.utf32, 0, codepoint_array.length);
	}

	/**
	 * utf-32 の数値配列を返す
	 * @return
	 */
	public int[] createUTF32Array() {
		int[] output = new int[this.utf32.length];
		System.arraycopy(this.utf32, 0, output, 0, this.utf32.length);
		return	output;
	}

	/**
	 * 文字列を返す
	 */
	public String toString() {
		return utf16;
	}

	/**
	 * 文字列が等しいかどうかを調べる
	 */
	public boolean equals(Object object) {
		if(object == this) {
			return(true);
		}
		if(!(object instanceof StringUTF32)) {
			return(false);
		}
		int[] array_src = this.utf32;
		int[] array_tgt = ((StringUTF32)object).utf32;
		if(array_src.length != array_tgt.length) {
			return(false);
		}
		int length = array_tgt.length;
		for(int i = 0;i < length;i++) {
			if(array_src[i] != array_tgt[i]) {
				return(false);
			}
		}
		return(true);
	}

	/**
	 * 文字列型からint型を生成します。
	 * @param text
	 * @return
	 */
	static private int[] toUTF32FromUTF16(String text) {
		int textlength = text.length();
		for(int i=0,j=text.length();i<j;i++) {
			if(Character.isHighSurrogate(text.charAt(i))) {
				textlength--;
				i++;
			}
		}
		int[] out = new int[textlength];
		for(int i=0, j=text.length(), p=0; i<j; i++, p++) {
			int c = text.charAt(i);
			if(!Character.isHighSurrogate(text.charAt(i))) {
				out[p] = c;
			}
			else {
				out[p] = StringUTF32.toCodePoint(c,text.charAt(i + 1));
				i++;
			}
		}
		return(out);
	}

	/**
	 * int型から文字列型を生成します。
	 * @param text
	 * @return
	 */
	static private String toUTF16FromUTF32(int[] text) {
		StringBuilder out = new StringBuilder();
		for(int i=0,j=text.length;i<j;i++) {
			out.append(Character.toChars(text[i]));
		}
		return(out.toString());
	}

	/**
	 * コードポイントの値を作成する
	 * @param high
	 * @param low
	 * @return
	 */
	static private int toCodePoint(int high,int low) {
		high-= 0xD800;
		high<<= 10;
		low -= 0xDC00;
		low |= high;
		low += 0x10000;
		return(low);
	}

	/**
	 * 文字列を検索する
	 * @param in
	 * @param target
	 * @param offset
	 * @return
	 */
	static private int indexOf(int[] in,int[] target,int offset) {
		final int ilength = in.length - target.length + 1;
		final int tlength = target.length;
		//高速化のために頭の文字だけ配列からだしておく
		final int sento = target[0];
		if(offset >= ilength) {
			return(-1);
		}
		//検索対象が1文字の場合
		int i;
		if(tlength==0) {
			for(i = offset;i < ilength;i++) {
				if(in[i]==sento) {
					return(i);
				}
			}
			return(-1);
		}
		//検索対象が複数の場合
		int j;
		for(i = offset;i < ilength;i++) {
			if(in[i]==sento) {
				i++;
				for(j = 1;j < tlength;j++,i++) {
					if(in[i]!=target[j]) {
						break;
					}
				}
				if(j==tlength) {
					return(i - tlength);
				}
				else {
					i -= j;
				}
			}
		}
		return(-1);
	}

	/**
	 * 文字列を検索する
	 * @param in
	 * @param target
	 * @param offset
	 * @return
	 */
	static private int indexOf(StringUTF32 in, StringUTF32 target, int offset) {
		return(StringUTF32.indexOf(in.utf32, target.utf32, offset));
	}

	/**
	 * 文字列を検索する
	 * @param target
	 * @param offset
	 * @return
	 */
	public int indexOf(StringUTF32 target, int offset) {
		return StringUTF32.indexOf(this, target, offset);
	}

	/**
	 * 文字列を全て置換する
	 * @param in
	 * @param target
	 * @param replacement
	 * @return
	 */
	static private int[] replaceAll(int[] in, int[] target, int[] replacement) {
		int[] out = new int[in.length];
		int ilength = in.length;
		int tlength = target.length;
		int rlength = replacement.length;
		int inpoint = 0,outpoint = 0,index;
		while(true) {
			index = indexOf(in,target,inpoint);
			if(index == -1) {
				System.arraycopy(in, inpoint, out, outpoint,ilength - inpoint);
				outpoint += ilength - inpoint;
				break;
			}
			System.arraycopy(in, inpoint, out, outpoint, index - inpoint);
			outpoint += index - inpoint;
			inpoint  += index - inpoint;
			System.arraycopy(replacement, 0, out, outpoint, rlength);
			inpoint  += tlength;
			outpoint += rlength;
		}
		if(outpoint!=ilength) {
			int[] out2 = new int[outpoint];
			System.arraycopy(out, 0, out2, 0, outpoint);
			return(out2);
		}
		return(out);
	}

	/**
	 * 文字列を全て置換する
	 * @param in
	 * @param target
	 * @param replacement
	 * @return
	 */
	static private StringUTF32 replaceAll(StringUTF32 in, StringUTF32 target, StringUTF32 replacement) {
		return(new StringUTF32(StringUTF32.replaceAll(in.utf32, target.utf32, replacement.utf32)));
	}

	/**
	 * 文字列を全て置換する
	 * @param target
	 * @param replacement
	 * @return
	 */
	public StringUTF32 replaceAll(StringUTF32 target, StringUTF32 replacement) {
		return StringUTF32.replaceAll(this, target, replacement);
	}

}
