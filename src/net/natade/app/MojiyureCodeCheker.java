package net.natade.app;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import com.atilika.kuromoji.unidic.Token;
import com.atilika.kuromoji.unidic.Tokenizer;

import net.natade.util.ArgumentAnalysis;
import net.natade.util.io.FileReader;
import net.natade.util.io.FileUtil;
import net.natade.util.io.PetternFileFilter;
import net.natade.util.mojiyure.MojiyureMapForMorpheme;

public class MojiyureCodeCheker {

	/**
	 * 形態素分析エンジン
	 */
	static Tokenizer tokenizer = new Tokenizer();

	/**
	 * 形態素分析エンジンを用いた文字揺れチェック用のマップ
	 */
	static MojiyureMapForMorpheme morpheme_map = new MojiyureMapForMorpheme();

	/**
	 * 形態素分析を用いて、文字揺れをチェックする
	 * 
	 * @param line_number その文字がある行番号
	 * @param target_text 調べたい文字列
	 */
	public static void checkUsingMorphemeDictionary(int line_number, String target_text) {
		List<Token> tokens = tokenizer.tokenize(target_text);
		for (Token token : tokens) {
			// チェックしてヒントを出す
			String hint = morpheme_map.getHintMojiyure(token);
			if (hint != null) {
				System.out.printf("%d行目[%d][%s] : %s\n", line_number + 1, token.getPosition(),
						token.getPartOfSpeechLevel1(), hint);
			}
		}
	}

	/**
	 * 指定したファイルを読み込み、文字揺れをチェックする
	 * 
	 * @param target_file 調べたいファイル
	 */
	public static void checkMojiyureFromFile(File target_file) {
		FileReader reader = new FileReader(target_file);
		String[] text_array = reader.getStringArray();
		reader.close();
		for (int i = 0; i < text_array.length; i++) {
			// 空白以外を調べる
			if (text_array[i].length() != 0) {
				MojiyureCodeCheker.checkUsingMorphemeDictionary(i, text_array[i]);
			}
		}
	}

	/**
	 * メイン関数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArgumentAnalysis args_data = new ArgumentAnalysis(args);
		String input_folder_string = args_data.getValue(Pattern.compile("-src", Pattern.CASE_INSENSITIVE));

		if (input_folder_string == null) {
			System.out.println("入力フォルダを指定する必要があります。");
			System.out.println("src=[" + input_folder_string + "]");
			System.exit(1);
		}

		File input_folder = new File(input_folder_string);
		if (!input_folder.isDirectory()) {
			System.out.println("入力フォルダはフォルダを設定する必要がある。[" + input_folder.getAbsolutePath() + "]");
			System.out.println("src=[" + input_folder_string + "]");
			System.exit(2);
		}

		Pattern extensin_pattern = Pattern.compile("\\.string$", Pattern.CASE_INSENSITIVE);
		File[] codelist = FileUtil.findList(input_folder, new PetternFileFilter(extensin_pattern));

		System.out.println("start");

		for (int i = 0; i < codelist.length; i++) {
			System.out.printf("[%d/%d] %s\n", i + 1, codelist.length, codelist[i].getAbsoluteFile());
			MojiyureCodeCheker.checkMojiyureFromFile(codelist[i]);
		}

		System.out.println("end");
		System.exit(0);
	}
}
