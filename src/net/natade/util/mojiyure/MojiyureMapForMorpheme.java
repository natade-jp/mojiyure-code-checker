package net.natade.util.mojiyure;

import java.util.HashMap;

import com.atilika.kuromoji.unidic.Token;

/**
 * 形態素分析した結果を入れる辞書
 * @author natade
 */
public class MojiyureMapForMorpheme {

	/**
	 * 名詞
	 * フォルダ, フォルダー
	 */
	private HashMap<String, String> token_map_meishi = null;

	/**
	 * 形容詞
	 * 美味しい寿司, おいしい寿司
	 */
	private HashMap<String, String> token_map_keiyoushi = null;

	/**
	 * 動詞
	 * 行う, 行なう
	 */
	private HashMap<String, String> token_map_doushi = null;

	/**
	 * 副詞
	 * 少し, すこし
	 */
	private HashMap<String, String> token_map_fukushi = null;

	/**
	 * 連体詞
	 * 大きな, おおきな
	 */
	private HashMap<String, String> token_map_rentaishi = null;
	
	/**
	 * 初期化する
	 */
	public MojiyureMapForMorpheme() {
		this.init(); 
	}

	/**
	 * 初期化する
	 */
	private void init() {
		this.token_map_meishi		= new HashMap<String, String>();
		this.token_map_keiyoushi	= new HashMap<String, String>();
		this.token_map_doushi		= new HashMap<String, String>();
		this.token_map_fukushi		= new HashMap<String, String>();
		this.token_map_rentaishi	= new HashMap<String, String>();
	}
	
	/**
	 * 指定した品詞用の辞書を取得する
	 * @param token
	 * @return 存在しない場合はNULL
	 */
	private HashMap<String, String> getMap(Token token) {
		String part_type = token.getPartOfSpeechLevel1();

		if(part_type.equals("名詞")) {
			return this.token_map_meishi;
		}
		else if(part_type.equals("形容詞")) {
			return this.token_map_keiyoushi;
		}
		else if(part_type.equals("動詞")) {
			return this.token_map_doushi;
		}
		else if(part_type.equals("副詞")) {
			return this.token_map_fukushi;
		}
		else if(part_type.equals("連体詞")) {
			return this.token_map_rentaishi;
		}
		return null;
	}
	
	/**
	 * 指定したデータが文字揺れしている可能性があった場合はヒントを文字列で返す
	 * 可能性がない場合は、辞書へ登録され、戻り値は NULL を返す。
	 * @param token
	 * @return
	 */
	public String getHintMojiyure(Token token) {
		
		/*
		 * 
		// 検索ヒット箇所
		System.out.println("ヒット："+ token.getSurface());
		
		// 名詞, 動詞, 形容詞 など
		System.out.println("品詞："+ token.getPartOfSpeechLevel1());

		// 読み（カタカナ表記）
		System.out.println("ヨミ："+ token.getPronunciation());

		// 正規化された読み（フォルダーとフォルダは「フォルダー」になる）
		System.out.println("正規化：" + token.getLemmaReadingForm());
		
		System.out.println(token.getAllFeatures());
		System.out.println(token.getConjugationForm());
		System.out.println(token.getConjugationType());
		System.out.println(token.getFinalSoundAlterationForm());
		System.out.println(token.getFinalSoundAlterationType());
		System.out.println(token.getInitialSoundAlterationForm());
		System.out.println(token.getInitialSoundAlterationType());
		System.out.println(token.getLanguageType());
		System.out.println(token.getLemma());
		System.out.println(token.getLemmaReadingForm());
		System.out.println(token.getPartOfSpeechLevel1());
		System.out.println(token.getPartOfSpeechLevel2());
		System.out.println(token.getPartOfSpeechLevel3());
		System.out.println(token.getPartOfSpeechLevel4());
		System.out.println(token.getPosition());
		System.out.println(token.getPronunciation());
		System.out.println(token.getPronunciationBaseForm());
		System.out.println(token.getSurface());
		System.out.println(token.getWrittenBaseForm());
		System.out.println(token.getWrittenForm());
		*/

		HashMap<String, String> map = this.getMap(token);
		
		// 特定の品詞をしない
		if(map == null) {
			return null;
		}
		String written_string = token.getSurface();
		String normalized_string = token.getLemmaReadingForm();
		String key_string = null;
		
		// 対応していない文字は処理をしない
		if(normalized_string.equals("*")) {
			return null;
		}
		
		// 名詞以外は書き方が揺れる場合があるので読み仮名を考慮する
		if(token.getPartOfSpeechLevel1().equals("名詞")) {
			key_string = normalized_string;
		}
		else {
			String yomigana_string = token.getPronunciation();
			key_string = yomigana_string + "_" + normalized_string;
		}
		
		String hit_string = map.get(key_string);

		// 存在しない場合は登録して終了
		if(hit_string == null) {
			map.put(key_string, written_string);
			return null;
		}
		
		// 書き方が同じであれば文字揺れしていない
		if(written_string.equals(hit_string)) {
			return null;
		}
		
		return written_string + " != " + hit_string + "";
		
	}
	

}
