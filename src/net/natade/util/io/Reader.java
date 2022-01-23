package net.natade.util.io;

/**
 * バイトオーダはByteValueクラスに依存します。
 * @author natade
 */
public interface Reader {

	/**
	 * データを長さを取得します。
	 * @return
	 */
	public int getLength();

	/**
	 * データを閉じます。
	 * @return
	 */
	public void close();

	/**
	 * 指定した位置のバイト列を取得します。
	 * @param readpoint readerのoffset
	 * @param binary 書き込みたいバイト配列
	 * @param offset バイト配列のoffset
	 * @param size 読み込みたいサイズ
	 * @return 読み込んだサイズ
	 */
	public int readByteArray(int readpoint,byte[] binary,int offset,int size);

	/**
	 * 指定した位置のバイト列を取得できるかテストします。
	 * @param binary 書き込みたいバイト配列(0から書き込む)
	 * @param offset ファイルのオフセット
	 * @param size ファイルから読み込む長さ
	 * @return
	 */
	public boolean testGetByteArray(byte[] binary,int offset,int size);

	/**
	 * 文字を取得します。
	 * @return
	 */
	public String getString();

	/**
	 * 指定した位置の文字を取得します。
	 * @param offset
	 * @param size
	 * @return
	 */
	public String getString(int offset,int size);

	/**
	 * 改行ごとの文字配列を取得します。
	 * @return
	 */
	public String[] getStringArray();

	/**
	 * 指定した箇所から改行ごとの文字配列を取得します。
	 * @param offset
	 * @param size
	 * @return
	 */
	public String[] getStringArray(int offset,int size);

	/**
	 * データを取得します。
	 * @return
	 */
	public byte[] getByteArray();

	/**
	 * 指定した位置のバイト列を取得します。
	 * @param offset
	 * @param size
	 * @return
	 */
	public byte[] getByteArray(int offset,int size);
	
	/**
	 * 指定した位置のdouble型の値を取得します。
	 * @param offset
	 * @return
	 */
	public double getFloat64(int offset);

	/**
	 * 指定した位置のfloat型の値を取得します。
	 * @param offset
	 * @return
	 */
	public float getFloat32(int offset);

	/**
	 * 指定した位置のlong型の値を取得します。
	 * @param offset
	 * @return
	 */
	public long getInt64(int offset);

	/**
	 * 指定した位置のint型の値を取得します。
	 * @param offset
	 * @return
	 */
	public int getInt32(int offset);

	/**
	 * 指定した位置のshort型の値を取得します。
	 * @param offset
	 * @return
	 */
	public short getInt16(int offset);

	/**
	 * 指定した位置のunsigned short型の値を取得します。
	 * @param offset
	 * @return
	 */
	public int getUint16(int offset);

	/**
	 * 指定した位置のbyte型の値を取得します。
	 * @param offset
	 * @return
	 */
	public byte getInt8(int offset);

	/**
	 * 指定した位置のunsigned byte型の値を取得します。
	 * @param offset
	 * @return
	 */
	public int getUint8(int offset);

	/**
	 * 指定した位置のboolean型の値を取得します。
	 * @param offset
	 * @return
	 */
	public boolean getBoolean(int offset);

	/**
	 * 読み込み機能を持っているか
	 * @return
	 */
	public boolean isReader();

	/**
	 * 書き込み機能を持っているか
	 * @return
	 */
	public boolean isWriter();

	/**
	 * 読み書き機能を持っているか
	 * @return
	 */
	public boolean isRewriter();
}
