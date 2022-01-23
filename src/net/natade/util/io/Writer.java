package net.natade.util.io;

/**
 * バイトオーダはByteValueクラスに依存します。
 * @author natade
 */
public interface Writer {

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
	 * データの長さを設定します。
	 * @param length
	 * @return trueで成功
	 */
	public boolean setLength(int length);

	/**
	 * 指定した位置にデータを書き込む
	 * @param writepoint writerのoffset
	 * @param binary
	 * @param offset binaryのoffset
	 * @param size
	 * @return 書き込んだサイズ
	 */
	public int writeByteArray(int writepoint,byte[] binary,int offset,int size);

	/**
	 * 指定した位置に文字列を書き込む。
	 * @param data
	 * @return 書き込んだサイズ
	 */
	public int setString(String data);

	/**
	 * 指定した位置に文字列を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setString(String data,int offset);

	/**
	 * 指定した位置に文字列を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setStringArray(String[] data,int offset);

	/**
	 * 指定したデータを設定する(元のデータは初期化されます)
	 * @param binary
	 * @return trueで成功
	 */
	public boolean setByteArray(byte[] binary);

	/**
	 * 指定した位置にデータを書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setByteArray(byte[] data,int offset);

	/**
	 * 指定した位置にdouble型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setFloat64(double data,int offset);

	/**
	 * 指定した位置にfloat型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setFloat32(double data,int offset);

	/**
	 * 指定した位置にlong型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setInt64(long data,int offset);

	/**
	 * 指定した位置にint型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setInt32(long data,int offset);

	/**
	 * 指定した位置にshort型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setInt16(long data,int offset);

	/**
	 * 指定した位置にunsigned short型の値を書き込む。
	 * @param data
	 * @param offset
	 * @return 書き込んだサイズ
	 */
	public int setUint16(long data,int offset);

	/**
	 * 指定した位置にbyte型の値を書き込む。
	 * @param binary
	 * @param data
	 * @return 書き込んだサイズ
	 */
	public int setInt8(long data,int offset);

	/**
	 * 指定した位置にunsigned byte型の値を書き込む。
	 * @param binary
	 * @param data
	 * @return 書き込んだサイズ
	 */
	public int setUint8(long data,int offset);
	
	/**
	 * 指定した位置にboolean型の値を書き込む。
	 * @param binary
	 * @param data
	 * @return 書き込んだサイズ
	 */
	public int setBoolean(boolean data,int offset);

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
