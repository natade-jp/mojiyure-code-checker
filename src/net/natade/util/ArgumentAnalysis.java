package net.natade.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 引数を解析する
 * 
 * @author natade
 */
public class ArgumentAnalysis {

	/**
	 * 引数
	 */
	String[] args = new String[0];

	/**
	 * 初期化する
	 * 
	 * @param args[] 引数
	 */
	public ArgumentAnalysis(String[] args) {
		this.args = args;
	}

	/**
	 * 指定した値が入っている配列番号を調べる
	 * 
	 * @param key
	 * @return 配列番号(-1 は発見なし)
	 */
	public int findValue(Pattern key) {
		for (int i = 0; i < this.args.length; i++) {
			Matcher match = key.matcher(this.args[i]);
			if (match.find()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 指定した値が入っている配列番号を調べる
	 * 
	 * @param key
	 * @return 配列番号(-1 は発見なし)
	 */
	public int findValue(String key) {
		for (int i = 0; i < this.args.length; i++) {
			if (this.args[i] == key) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 指定した番号の文字列を取得する
	 * 
	 * @param num
	 * @return 存在しない場合は null を返す
	 */
	public String getValue(int num) {
		if ((num < 0) || (this.args.length <= num)) {
			return null;
		}
		return this.args[num];
	}

	/**
	 * 指定したキーの後ろの文字列を取得する
	 * 
	 * @param key
	 * @return 存在しない場合は null を返す
	 */
	public String getValue(Pattern key) {
		int num = this.findValue(key);
		return num < 0 ? null : this.getValue(this.findValue(key) + 1);
	}

	/**
	 * 指定したキーの後ろの文字列を取得する
	 * 
	 * @param key
	 * @return 存在しない場合は null を返す
	 */
	public String getValue(String key) {
		int num = this.findValue(key);
		return num < 0 ? null : this.getValue(this.findValue(key) + 1);
	}

}
