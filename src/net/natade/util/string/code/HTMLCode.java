package net.natade.util.string.code;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.natade.util.string.StringUTF32;

public class HTMLCode extends Code {

	public HTMLCode() {
		super();
	}

	public HTMLCode(String code_text) {
		super(code_text);
	}

	public Code create(String code_text) {
		return new HTMLCode(code_text);
	}

	/**
	 * 文字実体参照からの変換
	 * 
	 * @param text
	 * @return
	 */
	private static String decodeCharacterEntityReference(String text) {
		String out = text;
		out = out.replaceAll("&[Ll][Tt];", "<");
		out = out.replaceAll("&[Gg][Tt];", ">");
		out = out.replaceAll("&[Aa][Mm][Pp];", "&");
		out = out.replaceAll("&[Qq][Uu][Oo][Tt];", "\"");
		out = out.replaceAll("&[Nn][Bb][Ss][Pp];", " ");
		return out;
	}

	/**
	 * 数値文字参照からの変換
	 * 
	 * @param text
	 * @return
	 */
	private static String decodeNumericCharacterReference(String text) {
		// 10進数と16進数
		Pattern pattern = Pattern.compile("&#(\\d+);|&#([\\da-fA-F]+);");
		Matcher matcher = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		// 変換後の文字へ置換する
		while (matcher.find()) {
			StringBuffer buf = new StringBuffer();
			if (matcher.group(1) != null) {
				// 10 進数
				buf.append(Integer.parseInt(matcher.group(1)));
			} else {
				// 16 進数
				buf.append(Integer.parseInt(matcher.group(2), 16));
			}
			matcher.appendReplacement(sb, buf.toString());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	public StringUTF32 getExtractedString() {
		// コメントがないコードから、tagを除去した文字列を作成する
		int[] data = this.getRemoveComment().createUTF32Array();
		ArrayList<Integer> char_array = new ArrayList<Integer>();
		boolean isTag = false;
		for (int i = 0; i < data.length; i++) {
			int moji = data[i];
			// コメントではない部分
			if (!isTag) {
				if (moji == '<') {
					isTag = true;
				} else {
					char_array.add(moji);
				}
			} else {
				if (moji == '>') {
					isTag = false;
				}
				// 改行部分は追加する
				if (isDiverted(moji)) {
					char_array.add(moji);
				}
			}
		}

		int[] int_array = char_array.stream().mapToInt(x -> x).toArray();
		String text = new StringUTF32(int_array).toString();
		text = HTMLCode.decodeCharacterEntityReference(text);
		text = HTMLCode.decodeNumericCharacterReference(text);
		return new StringUTF32(text.replaceAll(" +", " ").replaceAll("\n | \n", "\n"));
	}

	public StringUTF32 getRemoveComment() {
		int[] data = this.code;
		System.arraycopy(this.code, 0, data, 0, this.code.length);

		boolean isCommnet = false;
		int comment_num = 0;

		for (int i = 0; i < data.length; i++) {
			int moji = data[i];

			// コメントではない部分
			if (!isCommnet) {
				if ((comment_num == 0) && (moji == '<')) {
					comment_num++;
				} else if ((comment_num == 1) && (moji == '!')) {
					comment_num++;
				} else if ((comment_num == 2) && (moji == '-')) {
					comment_num++;
				} else if ((comment_num == 3) && (moji == '-')) {
					// <!--
					comment_num = 0;
					data[i - 3] = ' ';
					data[i - 2] = ' ';
					data[i - 1] = ' ';
					data[i] = ' ';
					isCommnet = true;
				} else {
					comment_num = 0;
				}
			} else {
				// コメントの部分
				if ((comment_num == 0) && (moji == '-')) {
					comment_num++;
				} else if ((comment_num == 1) && (moji == '-')) {
					comment_num++;
				} else if ((comment_num == 2) && (moji == '>')) {
					// -->
					comment_num = 0;
					isCommnet = false;
				} else {
					comment_num = 0;
				}
				if (!isDiverted(moji)) {
					data[i] = ' ';
				}
			}
		}

		return new StringUTF32(data);
	}

}
