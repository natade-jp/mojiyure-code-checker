package net.natade.app;

import java.io.File;
import java.util.regex.Pattern;

import net.natade.util.ArgumentAnalysis;
import net.natade.util.io.FileUtil;
import net.natade.util.io.PetternFileFilter;

class ExtractText {

	public static void main(String[] args) {
		ArgumentAnalysis args_data = new ArgumentAnalysis(args);
		String input_folder_string = args_data.getValue(Pattern.compile("-src", Pattern.CASE_INSENSITIVE));
		String output_folder_string = args_data.getValue(Pattern.compile("-out", Pattern.CASE_INSENSITIVE));
		String extensin_string = args_data.getValue(Pattern.compile("-extensin", Pattern.CASE_INSENSITIVE));
		
		if((input_folder_string == null) || (output_folder_string == null)) {
			System.out.println("入力フォルダと、出力フォルダが指定する必要があります。");
			System.out.println("src=[" + input_folder_string + "]");
			System.out.println("out=[" + output_folder_string + "]");
			System.exit(1);
		}
		
		File input_folder = new File(input_folder_string);
		File output_folder = new File(output_folder_string);
		if(!input_folder.isDirectory()) {
			System.out.println("入力フォルダはフォルダを設定する必要がある。[" + input_folder.getAbsolutePath() + "]");
			System.out.println("src=[" + input_folder_string + "]");
			System.exit(2);
		}
		
		Pattern extensin_pattern = null;
		if(extensin_string == null) {
			extensin_pattern = Pattern.compile("\\.(c|cpp|cs|java|js)$", Pattern.CASE_INSENSITIVE);
		}
		else {
			extensin_pattern = Pattern.compile(extensin_string, Pattern.CASE_INSENSITIVE);
		}
		
		File[] codelist = FileUtil.findList(input_folder, new PetternFileFilter(extensin_pattern));
		System.out.println(input_folder.getAbsolutePath());
		
		for(int i = 0; i < codelist.length; i++) {
			String codename = codelist[i].getAbsolutePath();
			int substring_index = (int) input_folder.getAbsolutePath().length();
			
			File inputfile = codelist[i];
			File outputfile = new File(output_folder.getAbsolutePath() + codename.substring(substring_index));
			
			System.out.println(inputfile.getAbsolutePath());
			System.out.println(outputfile.getAbsolutePath());
		}
		
		System.out.println("ok");
		System.exit(0);
	}
	
}
