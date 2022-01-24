package net.natade.app;

import java.io.File;
import java.util.regex.Pattern;

import net.natade.util.ArgumentAnalysis;
import net.natade.util.io.FileReader;
import net.natade.util.io.FileUtil;
import net.natade.util.io.FileWriter;
import net.natade.util.io.PetternFileFilter;
import net.natade.util.string.Code;

class ExtractText {

	/**
	 * 指定したテキスト列からテキストのみを抽出する コメントアウトされた部分は無視する
	 * 
	 * @param text
	 * @return
	 */
	public static String extractStringFromCode(String text) {
		Code code = new Code(text);
		code.removeComment();
		return code.getExtractedString();
	}

	/**
	 * 指定したファイルを読み込み、テキストのみ抽出して、指定したファイルに書き込む
	 * 
	 * @param input_file
	 * @param output_file
	 */
	public static void extractTextFromFile(File input_file, File output_file) {
		FileReader reader = new FileReader(input_file);
		String input_text = reader.getString();
		reader.close();

		FileWriter writer = new FileWriter(output_file);
		String output_text = ExtractText.extractStringFromCode(input_text);
		writer.setLength(0);
		writer.setString(output_text);
		writer.close();
	}

	/**
	 * メイン関数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ArgumentAnalysis args_data = new ArgumentAnalysis(args);
		
		// 引数のチェック
		String input_folder_string = args_data.getValue(Pattern.compile("-src", Pattern.CASE_INSENSITIVE));
		String output_folder_string = args_data.getValue(Pattern.compile("-out", Pattern.CASE_INSENSITIVE));
		String extensin_string = args_data.getValue(Pattern.compile("-extensin", Pattern.CASE_INSENSITIVE));
		if ((input_folder_string == null) || (output_folder_string == null)) {
			System.out.println("入力フォルダと、出力フォルダが指定する必要があります。");
			System.out.println("src=[" + input_folder_string + "]");
			System.out.println("out=[" + output_folder_string + "]");
			System.exit(1);
		}
		File input_folder = new File(input_folder_string);
		File output_folder = new File(output_folder_string);
		if (!input_folder.isDirectory()) {
			System.out.println("入力フォルダはフォルダを設定する必要があります。");
			System.out.println("src=[" + input_folder_string + "]");
			System.exit(2);
		}
		Pattern extensin_pattern = null;
		if (extensin_string == null) {
			extensin_pattern = Pattern.compile("\\.(c|cpp|cs|java|js|json)$", Pattern.CASE_INSENSITIVE);
		} else {
			extensin_pattern = Pattern.compile(extensin_string, Pattern.CASE_INSENSITIVE);
		}

		// 処理開始
		File[] codelist = FileUtil.findList(input_folder, new PetternFileFilter(extensin_pattern));

		for (int i = 0; i < codelist.length; i++) {
			System.out.printf("[%d/%d] %s\n", i + 1, codelist.length, codelist[i].getAbsoluteFile());
			String codename = codelist[i].getAbsolutePath();
			int substring_index = (int) input_folder.getAbsolutePath().length();

			File inputfile = codelist[i];
			File outputfile = new File(
					output_folder.getAbsolutePath() + codename.substring(substring_index) + ".string");

			ExtractText.extractTextFromFile(inputfile, outputfile);
		}

		// 処理終了
		System.exit(0);
	}

}
