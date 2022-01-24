package net.natade.util.io;

/**
 * バイト列と数値との関係クラス リトルエンディアンで統一しています。
 * 
 * @author natade
 */
public final class ByteValue {

	/**
	 * double 型のサイズ
	 */
	final static public int SIZE_DOUBLE = 8;

	/**
	 * float 型のサイズ
	 */
	final static public int SIZE_FLOAT = 4;

	/**
	 * long 型のサイズ
	 */
	final static public int SIZE_LONG = 8;

	/**
	 * int 型のサイズ
	 */
	final static public int SIZE_INT = 4;

	/**
	 * short 型のサイズ
	 */
	final static public int SIZE_SHORT = 2;

	/**
	 * unsigned short 型のサイズ
	 */
	final static public int SIZE_UNSIGNED_SHORT = 2;

	/**
	 * byte 型のサイズ
	 */
	final static public int SIZE_BYTE = 1;

	/**
	 * unsigned byte 型のサイズ
	 */
	final static public int SIZE_UNSIGNED_BYTE = 2;

	/**
	 * boolean 型のサイズ
	 */
	final static public int SIZE_BOOLEAN = 1;

	/**
	 * 8バイトのdouble型の実数を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setFloat64(byte[] binary, double data, int offset) {
		ByteValue.setInt64(binary, Double.doubleToRawLongBits(data), offset);
	}

	/**
	 * 8バイトのdouble型の実数を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static double getFloat64(byte[] binary, int offset) {
		return (Double.longBitsToDouble(ByteValue.getInt64(binary, offset)));
	}

	/**
	 * 4バイトのfloat型の実数を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setFloat32(byte[] binary, double data, int offset) {
		ByteValue.setInt32(binary, Float.floatToRawIntBits((float) data), offset);
	}

	/**
	 * 4バイトのfloat型の実数を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static float getFloat32(byte[] binary, int offset) {
		return (Float.intBitsToFloat(ByteValue.getInt32(binary, offset)));
	}

	/**
	 * 8バイトのlong型の整数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setInt64(byte[] binary, long data, int offset) {
		binary[offset++] = (byte) (data >> 0);
		binary[offset++] = (byte) (data >> 8);
		binary[offset++] = (byte) (data >> 16);
		binary[offset++] = (byte) (data >> 24);
		binary[offset++] = (byte) (data >> 32);
		binary[offset++] = (byte) (data >> 40);
		binary[offset++] = (byte) (data >> 48);
		binary[offset++] = (byte) (data >> 56);
	}

	/**
	 * 8バイトのlong型の整数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static long getInt64(byte[] binary, int offset) {
		long l = 0;
		l |= ((binary[offset++]) & 0xffL) << 0;
		l |= ((binary[offset++]) & 0xffL) << 8;
		l |= ((binary[offset++]) & 0xffL) << 16;
		l |= ((binary[offset++]) & 0xffL) << 24;
		l |= ((binary[offset++]) & 0xffL) << 32;
		l |= ((binary[offset++]) & 0xffL) << 40;
		l |= ((binary[offset++]) & 0xffL) << 48;
		l |= ((binary[offset++]) & 0xffL) << 56;
		return l;
	}

	/**
	 * 4バイトのint型の整数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setInt32(byte[] binary, long data, int offset) {
		binary[offset++] = (byte) (data >> 0);
		binary[offset++] = (byte) (data >> 8);
		binary[offset++] = (byte) (data >> 16);
		binary[offset++] = (byte) (data >> 24);
	}

	/**
	 * 4バイトのint型の整数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static int getInt32(byte[] binary, int offset) {
		int l = 0;
		l |= ((binary[offset++]) & 0xff) << 0;
		l |= ((binary[offset++]) & 0xff) << 8;
		l |= ((binary[offset++]) & 0xff) << 16;
		l |= ((binary[offset++]) & 0xff) << 24;
		return l;
	}

	/**
	 * 2バイトのshort型の整数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setInt16(byte[] binary, long data, int offset) {
		short l = (short) data;
		binary[offset++] = (byte) ((l >> 0) & 0xff);
		binary[offset++] = (byte) ((l >> 8) & 0xff);
	}

	/**
	 * 2バイトのshort型の整数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static short getInt16(byte[] binary, int offset) {
		int l = 0;
		l |= (binary[offset++] & 0xff) << 0;
		l |= (binary[offset++] & 0xff) << 8;
		return (short) l;
	}

	/**
	 * 2バイトのunsigned short型の正数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setUint16(byte[] binary, long data, int offset) {
		binary[offset++] = (byte) ((data >> 0) & 0xff);
		binary[offset++] = (byte) ((data >> 8) & 0xff);
	}

	/**
	 * 2バイトのunsigned short型の正数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static int getUint16(byte[] binary, int offset) {
		int l = 0;
		l |= (binary[offset++] & 0xff) << 0;
		l |= (binary[offset++] & 0xff) << 8;
		return l;
	}

	/**
	 * 1バイトのbyte型の整数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setInt8(byte[] binary, long data, int offset) {
		binary[offset] = (byte) data;
	}

	/**
	 * 1バイトのbyte型の整数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static byte getInt8(byte[] binary, int offset) {
		return binary[offset];
	}

	/**
	 * 1バイトのunsigned byte型の正数値を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setUint8(byte[] binary, long data, int offset) {
		binary[offset] = (byte) (data & 0xff);
	}

	/**
	 * 1バイトのunsigned byte型の正数値を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static int getUint8(byte[] binary, int offset) {
		return binary[offset] & 0xff;
	}

	/**
	 * 1バイトのboolean型を書き込みます。
	 * 
	 * @param binary
	 * @param data
	 * @param offset
	 */
	final public static void setBoolean(byte[] binary, boolean data, int offset) {
		binary[offset] = (byte) (data ? 1 : 0);
	}

	/**
	 * 1バイトのboolean型を読み込みます。
	 * 
	 * @param binary
	 * @param offset
	 * @return
	 */
	final public static boolean getBoolean(byte[] binary, int offset) {
		return binary[offset] == ((byte) 0) ? false : true;
	}

	/**
	 * double型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryDouble(double data) {
		byte[] binary = new byte[ByteValue.SIZE_DOUBLE];
		ByteValue.setFloat64(binary, data, 0);
		return (binary);
	}

	/**
	 * float型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryFloat(double data) {
		byte[] binary = new byte[ByteValue.SIZE_FLOAT];
		ByteValue.setFloat32(binary, data, 0);
		return (binary);
	}

	/**
	 * long型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryLong(long data) {
		byte[] binary = new byte[ByteValue.SIZE_LONG];
		ByteValue.setInt64(binary, data, 0);
		return (binary);
	}

	/**
	 * int型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryInt(long data) {
		byte[] binary = new byte[ByteValue.SIZE_INT];
		ByteValue.setInt32(binary, data, 0);
		return (binary);
	}

	/**
	 * short型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryShort(long data) {
		byte[] binary = new byte[ByteValue.SIZE_SHORT];
		ByteValue.setInt16(binary, data, 0);
		return (binary);
	}

	/**
	 * unsigned short型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryUnsignedShort(long data) {
		byte[] binary = new byte[ByteValue.SIZE_UNSIGNED_SHORT];
		ByteValue.setUint16(binary, data, 0);
		return (binary);
	}

	/**
	 * byte型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryByte(long data) {
		byte[] binary = new byte[ByteValue.SIZE_BYTE];
		ByteValue.setInt8(binary, data, 0);
		return (binary);
	}

	/**
	 * unsigned byte型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryUnsignedByte(long data) {
		byte[] binary = new byte[ByteValue.SIZE_UNSIGNED_BYTE];
		ByteValue.setUint8(binary, data, 0);
		return (binary);
	}

	/**
	 * boolean型のバイナリを取得する。
	 * 
	 * @param data
	 * @return
	 */
	final public static byte[] getBinaryBoolean(boolean data) {
		byte[] binary = new byte[ByteValue.SIZE_BOOLEAN];
		ByteValue.setBoolean(binary, data, 0);
		return (binary);
	}
}
