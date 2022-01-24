package net.natade.util.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * ファイルの読み込みクラス 以下のような優先度で読み込む 実際のファイル＞リソースファイル＞システムリソースファイル
 * 
 * @author natade
 */
public class FileReader implements Reader {

	/**
	 * BufferedInputStreamに用いるバッファサイズ
	 */
	private static int defaultBufferSize = 65536;

	/**
	 * ファイルが存在しません。
	 */
	private static int NONEXIST = 0;

	/**
	 * ファイルが存在します。
	 */
	private static int EXIST_FILE = 1;

	/**
	 * リソースにファイルが存在します。
	 */
	private static int EXIST_RESOURCE = 2;

	/**
	 * システムリソースにファイルが存在します。
	 */
	private static int EXIST_SYSTEM_RESOURCE = 3;

	private static int getSizeByFromFile(String filename) {
		int filesize = 0;
		File file = new File(filename);
		filesize = (int) file.length();
		if (!file.exists()) {
			filesize = -1;
		}
		return (filesize);
	}

	private static int getSizeByFromResource(String filename) {
		int filesize = 0;
		InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
		try {
			filesize = (int) stream.available();
		} catch (NullPointerException e) {
			filesize = -1;
		} catch (FileNotFoundException e) {
			filesize = -1;
		} catch (IOException e) {
			filesize = -1;
		} finally {
			try {
				stream.close();
			} catch (NullPointerException e) {
				filesize = -1;
			} catch (IOException e) {
				filesize = -1;
			}
		}
		stream = null;
		return (filesize);
	}

	private static int getSizeByFromSystemResource(String filename) {
		int filesize = 0;
		InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
		try {
			filesize = (int) stream.available();
		} catch (NullPointerException e) {
			filesize = -1;
		} catch (FileNotFoundException e) {
			filesize = -1;
		} catch (IOException e) {
			filesize = -1;
		} finally {
			try {
				stream.close();
			} catch (NullPointerException e) {
				filesize = -1;
			} catch (IOException e) {
				filesize = -1;
			}
		}
		stream = null;
		return (filesize);
	}

	/**
	 * ファイルサイズを取得します。
	 * 
	 * @return
	 */
	public int getSize() {
		return FileReader.getSize(this.filename);
	}

	/**
	 * ファイルサイズを取得します。
	 * 
	 * @param filename
	 * @return
	 */
	private static int getSize(String filename) {
		int filesize = -1;
		filesize = FileReader.getSizeByFromFile(filename);
		if (filesize == -1) {
			filesize = FileReader.getSizeByFromResource(filename);
		}
		if (filesize == -1) {
			filesize = FileReader.getSizeByFromSystemResource(filename);
		}
		return (filesize);
	}

	/**
	 * ファイルが存在するか調べます。
	 * 
	 * @return
	 */
	public boolean isExist() {
		return FileReader.isExist(this.filename);
	}

	/**
	 * ファイルが存在するか調べます。
	 * 
	 * @param filename
	 * @return
	 */
	private static boolean isExist(String filename) {
		return (NONEXIST != FileReader.getExistType(filename));
	}

	/**
	 * ファイルがどこに存在するか調べます。
	 * 
	 * @return
	 */
	public int getFileType() {
		return FileReader.getExistType(this.filename);
	}

	/**
	 * ファイルがどこに存在するか調べます。
	 * 
	 * @param filename
	 * @return
	 */
	private static int getExistType(String filename) {
		if (getSizeByFromFile(filename) != -1) {
			return (EXIST_FILE);
		}
		if (getSizeByFromResource(filename) != -1) {
			return (EXIST_RESOURCE);
		}
		if (getSizeByFromSystemResource(filename) != -1) {
			return (EXIST_SYSTEM_RESOURCE);
		}
		return (NONEXIST);
	}

	private static InputStream getInputStreamByFromFile(String filename) {
		int length = getSize(filename);
		if (length == -1) {
			return (null);
		}
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			stream = null;
		}
		return (stream);
	}

	private static InputStream getInputStreamByFromResource(String filename) {
		int length = getSize(filename);
		if (length == -1) {
			return (null);
		}
		return (Thread.currentThread().getContextClassLoader().getResourceAsStream(filename));
	}

	private static InputStream getInputStreamByFromSystemResource(String filename) {
		int length = getSize(filename);
		if (length == -1) {
			return (null);
		}
		return (ClassLoader.getSystemResourceAsStream(filename));
	}

	private static InputStream getInputStream(String filename) {
		InputStream stream;
		stream = getInputStreamByFromFile(filename);
		if (stream == null) {
			stream = getInputStreamByFromResource(filename);
		}
		if (stream == null) {
			stream = getInputStreamByFromSystemResource(filename);
		}
		return (stream);
	}

	/**
	 * 指定したパスにあるファイルのバイト列を取得します。
	 * 
	 * @param filename
	 * @return
	 */
	private static byte[] getByte(String filename) {
		int length = getSize(filename);
		if (length == -1) {
			return (null);
		}
		InputStream inputstream = getInputStream(filename);
		if (inputstream == null) {
			return (null);
		}
		BufferedInputStream bufferedstream = null;
		byte[] out = null;
		try {
			bufferedstream = new BufferedInputStream(inputstream, defaultBufferSize);
			byte[] binary = new byte[length];
			bufferedstream.read(binary);
			out = binary;
		} catch (FileNotFoundException e) {
			/* e.printStackTrace(); */} catch (IOException e) {
			/* e.printStackTrace(); */} finally {
			try {
				if (bufferedstream != null) {
					bufferedstream.close();
				}
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception e) {
			}
		}
		return (out);
	}

	private String filename;
	private InputStream inputstream;
	private BufferedInputStream bufferedstream;
	private byte[] buffer = new byte[8];
	private byte[] zerobuffer = new byte[0];
	private int seek = 0;
	private int seekstart = 0;
	private int type = 0;

	/**
	 * ファイルを読み込むクラスを初期化します。 使用後は close を実行してください。
	 * 
	 * @param filename
	 */
	public FileReader(String filename) {
		this.setFile(filename);
	}

	/**
	 * ファイルを読み込むクラスを初期化します。 使用後は close を実行してください。
	 * 
	 * @param file
	 */
	public FileReader(File file) {
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
		this.type = FileReader.getExistType(filename);
		this.inputstream = getInputStream(filename);
		if (this.type == FileReader.EXIST_FILE) {
			this.bufferedstream = new BufferedInputStream(this.inputstream, defaultBufferSize);
		}
	}

	/**
	 * ファイルからロード用。
	 * 
	 * @param readpoint
	 * @param readsize
	 */
	private void makeBufferedInputStream(int readpoint, int readsize) {
		// ファイルからロード用。
		// バッファより大きくシーク位置が移動したら、バッファリングを作り直す。
		if (this.type == EXIST_FILE) {
			int nowpoint = this.seekstart + this.seek;
			if (nowpoint != readpoint) {
				// バッファより大きくシーク位置が移動していないので、スキップする。
				if ((nowpoint < readpoint) && (readpoint < nowpoint + defaultBufferSize)) {
					int skip = readpoint - nowpoint;
					try {
						this.bufferedstream.skip(skip);
					} catch (IOException e) {
						e.printStackTrace();
					}
					this.seek += skip;
				} else {
					close();
					this.seekstart = readpoint;
					try {
						this.inputstream = getInputStream(filename);
						FileInputStream file = (FileInputStream) this.inputstream;
						file.getChannel().position(this.seekstart);
						this.bufferedstream = new BufferedInputStream(this.inputstream, defaultBufferSize);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			this.seek += readsize;
		}
		// リソース関連は、FileInputStreamが使えないので、部分的にシークできない。
		// そこでskipを用いる。よって以前の読み込んだ位置より前だと、ロードしなおす。
		// というかBufferedInputStreamを使うと、スキップがうまくいかない場合がある。
		else {
			this.seek = readpoint - this.seek;
			// if(this.seek<0) {
			this.close();
			this.inputstream = getInputStream(filename);
			this.seek = readpoint;
			// }
			try {
				if (this.seek != 0) {
					this.inputstream.skip(this.seek);
				}
			} catch (FileNotFoundException e) {
				/* e.printStackTrace(); */} catch (IOException e) {
				/* e.printStackTrace(); */}
			this.seek = readpoint + readsize;
		}
	}

	/**
	 * 指定した位置のバイト列を取得できるかテストします。
	 * 
	 * @param binary 書き込みたいバイト配列(0から書き込む)
	 * @param offset ファイルのオフセット
	 * @param size   ファイルから読み込む長さ
	 * @return
	 */
	public boolean testGetByteArray(byte[] binary, int offset, int size) {
		return (size == this.readByteArray(offset, binary, 0, size));
	}

	/**
	 * 指定した位置のバイト列を取得します。
	 * 
	 * @param readpoint readerのoffset
	 * @param binary    書き込みたいバイト配列
	 * @param offset    バイト配列のoffset
	 * @param size      読み込みたいサイズ
	 * @return 読み込んだサイズ
	 */
	public int readByteArray(int readpoint, byte[] binary, int offset, int size) {
		if (this.inputstream == null) {
			System.out.println("FileReader.getByte で初期化していない。");
			return (0);
		}
		if (readpoint + size > this.getLength()) {
			System.out.println("FileReader.getByte でロードする位置がファイルの長さより長いです。");
			return (0);
		}
		if (offset + size > binary.length) {
			System.out.println("FileReader.getByte で用意したバッファより大きいサイズをロードしようとしています。");
			return (0);
		}
		makeBufferedInputStream(readpoint, size);
		if (this.type == FileReader.EXIST_FILE) {
			try {
				this.bufferedstream.read(binary, offset, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.inputstream.read(binary, offset, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (size);
	}

	private void closeBufferedStream() {
		try {
			if (bufferedstream != null) {
				bufferedstream.close();
				bufferedstream = null;
			}
		} catch (Exception e) {
		}
	}

	private void closeInputStream() {
		try {
			if (inputstream != null) {
				inputstream.close();
				inputstream = null;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 初期化処理を行います。
	 */
	public void close() {
		this.closeBufferedStream();
		this.closeInputStream();
		this.seek = 0;
		this.seekstart = 0;
	}

	public int getLength() {
		return (FileReader.getSize(this.filename));
	}

	/**
	 * 8バイトまで限定のロード。 戻り値のbyte列は既に初期化済みなので高速。
	 * 
	 * @param offset
	 * @param size
	 * @return
	 */
	private byte[] getByte8(int offset, int size) {
		makeBufferedInputStream(offset, size);
		if (this.type == FileReader.EXIST_FILE) {
			try {
				this.bufferedstream.read(buffer, 0, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.inputstream.read(buffer, 0, size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return (buffer);
	}

	public byte[] getByteArray(int offset, int size) {
		byte[] binary = new byte[size];
		if (!this.testGetByteArray(binary, offset, size)) {
			return (this.zerobuffer);
		}
		return (binary);
	}

	public byte[] getByteArray() {
		return (FileReader.getByte(this.filename));
	}

	public String getString(int offset, int size) {
		return (ByteString.getStringFromByte(this.getByteArray(offset, size)));
	}

	public String getString() {
		return (this.getString(0, this.getLength()));
	}

	public String[] getStringArray(int offset, int size) {
		return (ByteString.getStringArrayFromByte(this.getByteArray(offset, size)));
	}

	public String[] getStringArray() {
		return (this.getStringArray(0, this.getLength()));
	}

	public double getFloat64(int offset) {
		return (ByteValue.getFloat64(this.getByte8(offset, ByteValue.SIZE_DOUBLE), 0));
	}

	public float getFloat32(int offset) {
		return (ByteValue.getFloat32(this.getByte8(offset, ByteValue.SIZE_FLOAT), 0));
	}

	public long getInt64(int offset) {
		return (ByteValue.getInt64(this.getByte8(offset, ByteValue.SIZE_LONG), 0));
	}

	public int getInt32(int offset) {
		return (ByteValue.getInt32(this.getByte8(offset, ByteValue.SIZE_INT), 0));
	}

	public short getInt16(int offset) {
		return (ByteValue.getInt16(this.getByte8(offset, ByteValue.SIZE_SHORT), 0));
	}

	public int getUint16(int offset) {
		return (ByteValue.getUint16(this.getByte8(offset, ByteValue.SIZE_UNSIGNED_SHORT), 0));
	}

	public byte getInt8(int offset) {
		return (ByteValue.getInt8(this.getByte8(offset, ByteValue.SIZE_BYTE), 0));
	}

	public int getUint8(int offset) {
		return (ByteValue.getUint8(this.getByte8(offset, ByteValue.SIZE_UNSIGNED_BYTE), 0));
	}

	public boolean getBoolean(int offset) {
		return (ByteValue.getBoolean(this.getByte8(offset, ByteValue.SIZE_BOOLEAN), 0));
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
