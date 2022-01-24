package net.natade.util.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class FileUtil {

	/**
	 * 指定したファイル配下のファイルとフォルダのリストを作成する
	 * 
	 * @param target フォルダ
	 * @param filter ファイルのフィルタ
	 * @return 配下のファイル
	 */
	static public File[] findList(File target, FileFilter filter) {
		ArrayList<File> list = new ArrayList<File>();
		FileUtil.createFileListFunction(target, filter, list);
		return list.toArray(new File[list.size()]);
	}

	/**
	 * ファイルのリストを再帰的に作成する
	 * 
	 * @param target   ファイルかフォルダ
	 * @param filter   ファイルのフィルタ
	 * @param filelist 追加先のリスト
	 */
	static private void createFileListFunction(File target, FileFilter filter, ArrayList<File> filelist) {
		if (filter.accept(target)) {
			filelist.add(target);
		}
		if (target.isDirectory()) {
			File[] files = target.listFiles();
			if (files == null) {
				return;
			}
			for (File file : files) {
				FileUtil.createFileListFunction(file, filter, filelist);
			}
		}
	}

}
