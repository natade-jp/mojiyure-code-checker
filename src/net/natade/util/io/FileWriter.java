package net.natade.util.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 書き込み用クラス
 * 
 * @author natade
 */
public class FileWriter implements Writer {

	/**
	 * BufferedInputStreamに用いるバッファサイズ
	 */
	private static int defaultBufferSize = 65536;

	/**
	 * 指定したバイナリを指定した名前で保存します。
	 * 
	 * @param binary
	 * @param filename
	 * @return 成功でtrue
	 */
	private static boolean writeByteArray(byte[] binary, String filename) {
		FileOutputStream outputstream = null;
		BufferedOutputStream bufferedstream = null;
		boolean out = true;
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			outputstream = new FileOutputStream(file);
			bufferedstream = new BufferedOutputStream(outputstream, defaultBufferSize);
			bufferedstream.write(binary);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			out = false;
		} catch (IOException e) {
			e.printStackTrace();
			out = false;
		} finally {
			try {
				if (bufferedstream != null) {
					bufferedstream.flush();
					bufferedstream.close();
					bufferedstream = null;
				}
				if (outputstream != null) {
					outputstream.flush();
					outputstream.close();
					outputstream = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				out = false;
			}
		}
		return out;
	}

	private String filename;
	private RandomAccessFile writer = null;
	private byte[] buffer = new byte[8];
	private int seek = 0;
	private int seekstart = 0;

	/**
	 * ファイルを書き込むクラスを初期化します。 使用後は close を実行してください。
	 * 
	 * @param filename
	 */
	public FileWriter(String filename) {
		this.setFile(filename);
	}

	/**
	 * ファイルを書き込むクラスを初期化します。 使用後は close を実行してください。
	 * 
	 * @param file
	 */
	public FileWriter(File file) {
		this.setFile(file.getAbsolutePath());
	}

	/**
	 * ファイルを設定します。
	 * 
	 * @param filename
	 */
	private void setFile(String filename) {
		this.close();
		this.filename = filename;
		try {
			File file = new File(filename);
			file.getParentFile().mkdirs();
			this.writer = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 位置を設定する。
	 * 
	 * @param writepoint 書きこむ位置
	 * @param writesize  書き込みサイズ
	 */
	private void makeBufferedOutputStream(int writepoint, int writesize) {
		int nowpoint = this.seekstart + this.seek;
		// 以前書き込んだ位置と違うのでシークする。
		if (nowpoint != writepoint) {
			this.seekstart = writepoint;
			this.seek = 0;
			try {
				this.writer.seek(writepoint);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		this.seek += writesize;
	}

	/**
	 * 指定した位置にbufferのデータを書き込む
	 * 
	 * @param writepoint
	 * @param size
	 */
	private void flushBuffer(int writepoint, int size) {
		this.makeBufferedOutputStream(writepoint, size);
		try {
			this.writer.write(this.buffer, 0, size);
		} catch (FileNotFoundException e) {
			/* e.printStackTrace(); */} catch (IOException e) {
			/* e.printStackTrace(); */}
	}

	/**
	 * 初期化処理を行います。
	 */
	public void close() {
		try {
			if (this.writer != null) {
				this.writer.close();
				this.writer = null;
			}
		} catch (Exception e) {
		}
		this.seek = 0;
		this.seekstart = 0;
	}

	/**
	 * @param writepoint 書きこむ位置
	 * @param binary
	 * @param offset     binaryのオフセット
	 * @param size
	 * @return 書き込んだ長さ
	 */
	public int writeByteArray(int writepoint, byte[] binary, int offset, int size) {
		if (this.writer == null) {
			return (-1);
		}
		this.makeBufferedOutputStream(writepoint, size);
		try {
			this.writer.write(binary, offset, size);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (size);
	}

	public boolean setLength(int filesize) {
		if (this.writer != null) {
			try {
				this.writer.setLength(filesize);
				return (true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (false);
	}

	public int getLength() {
		if (this.writer != null) {
			try {
				return ((int) this.writer.length());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (-1);
	}

	public boolean setByteArray(byte[] binary) {
		return (FileWriter.writeByteArray(binary, this.filename));
	}

	public int setByteArray(byte[] data, int offset) {
		return (this.writeByteArray(offset, data, 0, data.length));
	}

	public int setString(String data) {
		return (this.setString(data, 0));
	}

	public int setString(String data, int offset) {
		byte[] text = ByteString.getByte(data);
		this.setByteArray(text, offset);
		return (text.length);
	}

	public int setStringArray(String[] data, int offset) {
		byte[] text = ByteString.getByte(data);
		this.setByteArray(text, offset);
		return (text.length);
	}

	public int setFloat64(double data, int offset) {
		ByteValue.setFloat64(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_DOUBLE);
		return (ByteValue.SIZE_DOUBLE);
	}

	public int setFloat32(double data, int offset) {
		ByteValue.setFloat32(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_FLOAT);
		return (ByteValue.SIZE_FLOAT);
	}

	public int setInt64(long data, int offset) {
		ByteValue.setInt64(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_LONG);
		return (ByteValue.SIZE_LONG);
	}

	public int setInt32(long data, int offset) {
		ByteValue.setInt32(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_INT);
		return (ByteValue.SIZE_INT);
	}

	public int setInt16(long data, int offset) {
		ByteValue.setInt16(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_SHORT);
		return (ByteValue.SIZE_SHORT);
	}

	public int setUint16(long data, int offset) {
		ByteValue.setUint16(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_UNSIGNED_SHORT);
		return (ByteValue.SIZE_UNSIGNED_SHORT);
	}

	public int setInt8(long data, int offset) {
		ByteValue.setInt8(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_BYTE);
		return (ByteValue.SIZE_BYTE);
	}

	public int setUint8(long data, int offset) {
		ByteValue.setUint8(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_UNSIGNED_BYTE);
		return (ByteValue.SIZE_UNSIGNED_BYTE);
	}

	public int setBoolean(boolean data, int offset) {
		ByteValue.setBoolean(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_BOOLEAN);
		return (ByteValue.SIZE_BOOLEAN);
	}

	public boolean isReader() {
		return (false);
	}

	public boolean isRewriter() {
		return (false);
	}

	public boolean isWriter() {
		return (true);
	}

}
