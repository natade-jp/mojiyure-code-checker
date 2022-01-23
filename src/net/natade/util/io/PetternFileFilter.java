package net.natade.util.io;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * 正規表現用のファイルフィルタ
 * @author natade
 */
public class PetternFileFilter implements FileFilter {
	
	/**
	 * 正規表現
	 */
	Pattern pattern = null;

	/**
	 * 正規表現を指定して初期化する
	 * @param pattern 対象のファイルを表す正規表現
	 */
	public PetternFileFilter(Pattern pattern) {
		this.pattern = pattern;
	}
	
	public boolean accept(File pathname) {
		return this.pattern.matcher(pathname.getAbsolutePath()).find();
	}

}
