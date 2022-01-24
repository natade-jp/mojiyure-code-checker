package net.natade.util.io;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * バイト列と文字列との関係クラス
 * 
 * @author natade
 */
public class ByteString {

	/**
	 * BOM を取り除く
	 * 
	 * @param binary 文字列のバイト配列
	 * @return
	 */
	private static byte[] removeBOM(byte[] binary) {
		// utf-8
		if ((binary.length >= 3)
				&& ((binary[0] == (byte) 0xEF) && (binary[1] == (byte) 0xBB) && (binary[2] == (byte) 0xBF))) {
			byte[] output = new byte[binary.length - 3];
			System.arraycopy(binary, 3, output, 0, output.length);
			return output;
		}
		// utf-16le
		if ((binary.length >= 2) && ((binary[0] == (byte) 0xFF) && (binary[1] == (byte) 0xFE))) {
			byte[] output = new byte[binary.length - 2];
			System.arraycopy(binary, 2, output, 0, output.length);
			return output;
		}
		// utf-16be
		if ((binary.length >= 2) && ((binary[0] == (byte) 0xFE) && (binary[1] == (byte) 0xFF))) {
			byte[] output = new byte[binary.length - 2];
			System.arraycopy(binary, 2, output, 0, output.length);
			return output;
		}
		return binary;
	}

	/**
	 * BOM を取り付ける
	 * 
	 * @param binary      文字列のバイト配列
	 * @param charsetName エンコードタイプ
	 * @return
	 */
	private static byte[] addBOM(byte[] binary, String charsetName) {
		// utf-8
		if (charsetName.toLowerCase().matches("utf[-_]?8")) {
			if ((binary.length >= 3)
					&& ((binary[0] == (byte) 0xEF) && (binary[1] == (byte) 0xBB) && (binary[2] == (byte) 0xBF))) {
				return binary;
			}
			byte[] output = new byte[binary.length + 3];
			output[0] = (byte) 0xEF;
			output[1] = (byte) 0xBB;
			output[2] = (byte) 0xBF;
			System.arraycopy(binary, 0, output, 3, binary.length);
			return output;
		}
		// utf-16le
		if (charsetName.toLowerCase().matches("utf[-_]?16le|unicodelittleunmarked")) {
			if ((binary.length >= 2) && ((binary[0] == (byte) 0xFF) && (binary[1] == (byte) 0xFE))) {
				return binary;
			}
			byte[] output = new byte[binary.length + 2];
			output[0] = (byte) 0xFF;
			output[1] = (byte) 0xFE;
			System.arraycopy(binary, 0, output, 2, binary.length);
			return output;
		}
		// utf-16be
		if (charsetName.toLowerCase().matches("utf[-_]?16be|unicodebigunmarked")) {
			if ((binary.length >= 2) && ((binary[0] == (byte) 0xFE) && (binary[1] == (byte) 0xFF))) {
				return binary;
			}
			byte[] output = new byte[binary.length + 2];
			output[0] = (byte) 0xFE;
			output[1] = (byte) 0xFF;
			System.arraycopy(binary, 0, output, 2, binary.length);
			return output;
		}
		return binary;
	}

	/**
	 * 指定した文字列からUTF-8のバイト配列を返します
	 * 
	 * @param in 文字列
	 * @return byte配列
	 */
	public static byte[] getByte(String in) {
		return ByteString.getByte(in, "utf-8", false);
	}

	/**
	 * 指定した文字列からバイト配列を返します。BOMは付けません。
	 * 
	 * @param in          文字列
	 * @param charsetName エンコードタイプ
	 * @return byte配列
	 */
	public static byte[] getByte(String in, String charsetName) {
		return ByteString.getByte(in, charsetName, false);
	}

	/**
	 * 指定した文字列からバイト配列を返します
	 * 
	 * @param in          文字列
	 * @param charsetName エンコードタイプ
	 * @param withBOM     BOM 付きかどうか
	 * @return byte配列
	 */
	public static byte[] getByte(String in, String charsetName, boolean withBOM) {
		String charset = charsetName;
		if (charsetName.toLowerCase().matches("utf[-_]?8")) {
			charset = "UTF8";
		}
		if (charsetName.toLowerCase().matches("utf[-_]?16le|unicodelittleunmarked")) {
			charset = "UnicodeLittleUnmarked";
		}
		if (charsetName.toLowerCase().matches("utf[-_]?16be|unicodebigunmarked")) {
			charset = "UnicodeBigUnmarked";
		}
		if (charsetName.toLowerCase().matches("cp932|ms932|sjis|shift_jis|shift-jis")) {
			charset = "MS932";
		}
		try {
			byte[] tmp = in.getBytes(charset);
			if (withBOM) {
				return ByteString.addBOM(tmp, charsetName);
			}
			return tmp;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 指定した文字列からUTF-8のバイト配列を返します
	 * 
	 * @param in 文字列の配列
	 * @return byte配列
	 */
	public static byte[] getByte(String in[]) {
		return ByteString.getByte(in, "utf-8", false);
	}

	/**
	 * 指定した文字列からバイト配列を返します。BOMは付けません。
	 * 
	 * @param in          文字列の配列
	 * @param charsetName エンコードタイプ
	 * @return byte配列
	 */
	public static byte[] getByte(String in[], String charsetName) {
		return ByteString.getByte(in, charsetName, false);
	}

	/**
	 * 指定した文字列からバイト配列を返します
	 * 
	 * @param in          文字列の配列
	 * @param charsetName エンコードタイプ
	 * @param withBOM     BOM 付きかどうか
	 * @return byte配列
	 */
	public static byte[] getByte(String in[], String charsetName, boolean withBOM) {
		if (charsetName.toLowerCase().matches("cp932|ms932|sjis|shift_jis|shift-jis")) {
			return ByteString.getByte(String.join("\r\n", in), charsetName, withBOM);
		} else {
			return ByteString.getByte(String.join("\n", in), charsetName, withBOM);
		}
	}

	/**
	 * 指定したバイト配列から、Stringに変換します。
	 * - 失敗した場合は NULL を返します。
	 * - 改行コードは LF のみとなります。
	 * 
	 * @param binary      文字列のバイト配列
	 * @param charsetName エンコードタイプ
	 * @return 文字列
	 */
	private static String getStringFromByteWithTest(byte[] binary, String charsetName) {
		try {
			String tmp = new String(binary, charsetName);
			byte[] string_byte = tmp.getBytes(charsetName);
			if (!Arrays.equals(binary, string_byte)) {
				return null;
			}
			return tmp.replaceAll("\r?\n", "\n");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 指定したバイト配列から、Stringに変換します。
	 *  - 失敗した場合は NULL を返します。
	 *  - 改行コードは LF のみとなります。
	 * 
	 * @param binary 文字列のバイト配列
	 * @return 文字列
	 */
	public static String getStringFromByte(byte[] binary) {
		if (binary.length >= 2) {
			if ((binary[0] == (byte) 0xFF) && (binary[1] == (byte) 0xFE)) {
				return ByteString.getStringFromByte(ByteString.removeBOM(binary), "UnicodeLittleUnmarked");
			}
			if ((binary[0] == (byte) 0xFE) && (binary[1] == (byte) 0xFF)) {
				return ByteString.getStringFromByte(ByteString.removeBOM(binary), "UnicodeBigUnmarked");
			}
		}
		if (binary.length >= 3) {
			if ((binary[0] == (byte) 0xEF) && (binary[1] == (byte) 0xBB) && (binary[2] == (byte) 0xBF)) {
				return ByteString.getStringFromByte(ByteString.removeBOM(binary), "UTF8");
			}
		}
		String output = null;
		output = ByteString.getStringFromByteWithTest(binary, "UTF8");
		if (output != null) {
			return output;
		}
		output = ByteString.getStringFromByteWithTest(binary, "MS932");
		if (output != null) {
			return output;
		}
		output = ByteString.getStringFromByteWithTest(binary, System.getProperty("file.encoding"));
		if (output != null) {
			return output;
		}
		output = ByteString.getStringFromByteWithTest(binary, "SJIS");
		if (output != null) {
			return output;
		}
		output = ByteString.getStringFromByteWithTest(binary, "ASCII");
		if (output != null) {
			return output;
		}
		return null;
	}

	/**
	 * 指定したバイト配列から、Stringに変換します。 - 失敗した場合は NULL を返します。 - 改行コードは LF のみとなります。
	 * 
	 * @param binary      文字列のバイト配列
	 * @param charsetName エンコードタイプ
	 * @return 文字列
	 */
	public static String getStringFromByte(byte[] binary, String charsetName) {
		try {
			String tmp = new String(binary, charsetName);
			return tmp.replaceAll("\r?\n", "\n");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 指定したバイト配列から文字列を読み込み、1行ごとの文字列配列に変換します。 - 失敗した場合は NULL を返します。
	 * 
	 * @param binary 文字列のバイト配列
	 * @return 文字列の配列
	 */
	public static String[] getStringArrayFromByte(byte[] binary) {
		String x = ByteString.getStringFromByte(binary);
		if (x == null) {
			return null;
		}
		return x.split("\n");
	}

	/**
	 * 指定したバイト配列から文字列を読み込み、1行ごとの文字列配列に変換します。 - 失敗した場合は NULL を返します。
	 * 
	 * @param binary      文字列のバイト配列
	 * @param charsetName エンコードタイプ
	 * @return 文字列の配列
	 */
	public static String[] getStringArrayFromByte(byte[] binary, String charsetName) {
		String x = ByteString.getStringFromByte(binary, charsetName);
		if (x == null) {
			return null;
		}
		return x.split("\n");
	}

}
