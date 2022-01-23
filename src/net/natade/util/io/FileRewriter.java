package net.natade.util.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * ファイルの読み書きクラス
 * @author natade
 */
public class FileRewriter implements Rewriter {

	private String filename;
	private RandomAccessFile rewriter = null;
	private byte[] zerobuffer = new byte[0];
	private byte[] buffer = new byte[8];
	private int seek = 0;
	private int seekstart = 0;

	public FileRewriter(String filename) {
		this.setFile(filename);
	}

	public FileRewriter(File file) {
		this.setFile(file.getAbsolutePath());
	}

	/**
	 * ファイルを設定します。
	 * @param filename
	 * @return 成功でtrue
	 */
	private boolean setFile(String filename) {
		this.close();
		boolean out = true;
		this.filename = filename;
		try {
			File file = new File(this.filename);
			if(file.getParentFile().mkdirs() == false) {
				System.out.println("フォルダを作成できない[" + file.getParentFile() + "]");
				return false;
			}
			this.rewriter = new RandomAccessFile(file, "rw");
		}
		catch (FileNotFoundException e) {
			this.close();
			e.printStackTrace();
	    	out = false;
		}
		return(out);
	}

	/**
	 * 初期化処理を行います。
	 */
	public void close() {
		try {
			if(this.rewriter != null) {
				this.rewriter.close();
				this.rewriter = null;
			}
		}
		catch (Exception e) {
		}
		this.seek = 0;
		this.seekstart = 0;
	}

	/**
	 * 位置を設定する。
	 * @param writepoint 書きこむ・読み込む位置
	 * @param writesize どれだけ書きこむ・読み込むか
	 */
	private void seekPoint(int writepoint,int writesize){
		int nowpoint = this.seekstart + this.seek;
		//以前書き込んだ位置と違うのでシークする。
		if(nowpoint != writepoint) {
			this.seekstart = writepoint;
			this.seek = 0;
			try {
				this.rewriter.seek(this.seekstart);
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		this.seek += writesize;
	}


	public boolean setLength(int filesize) {
		if(this.rewriter!=null) {
			try {
				this.rewriter.setLength(filesize);
				return(true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return(false);
	}

	public int getLength() {
		if(this.rewriter!=null) {
			try {
				return((int)this.rewriter.length());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return(-1);
	}

	/**
	 * 指定した位置にbufferのデータを書き込む
	 * @param writepoint
	 * @param size
	 */
	private void flushBuffer(int writepoint,int size) {
		this.seekPoint(writepoint,size);
		try {
			this.rewriter.write(buffer,0,size);
		}
		catch (FileNotFoundException e) {/*e.printStackTrace();*/}
		catch (IOException e) {/*e.printStackTrace();*/}
	}


	public int writeByteArray(int writepoint,byte[] binary,int offset,int size) {
		if(this.rewriter==null) {
			return(-1);
		}
		this.seekPoint(writepoint,size);
		try {
			this.rewriter.write(binary,offset,size);
		}
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		return(size);
	}

	public boolean setByteArray(byte[] data) {
		return(this.writeByteArray(0, data, 0, data.length) != -1);
	}

	public int setByteArray(byte[] data,int offset) {
		return(this.writeByteArray(offset, data, 0, data.length));
	}

	public int setString(String data) {
		return(this.setString(data, 0));
	}
	
	public int setString(String data,int offset) {
		byte[] text = ByteString.getByte(data);
		this.setByteArray(text, offset);
		return(text.length);
	}

	public int setStringArray(String[] data,int offset) {
		byte[] text = ByteString.getByte(data);
		this.setByteArray(text, offset);
		return(text.length);
	}

	public int setFloat64(double data,int offset) {
		ByteValue.setFloat64(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_DOUBLE);
		return(ByteValue.SIZE_DOUBLE);
	}

	public int setFloat32(double data,int offset) {
		ByteValue.setFloat32(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_FLOAT);
		return(ByteValue.SIZE_FLOAT);
	}

	public int setInt64(long data,int offset) {
		ByteValue.setInt64(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_LONG);
		return(ByteValue.SIZE_LONG);
	}

	public int setInt32(long data,int offset) {
		ByteValue.setInt32(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_INT);
		return(ByteValue.SIZE_INT);
	}

	public int setInt16(long data,int offset) {
		ByteValue.setInt16(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_SHORT);
		return(ByteValue.SIZE_SHORT);
	}

	public int setUint16(long data,int offset) {
		ByteValue.setUint16(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_UNSIGNED_SHORT);
		return(ByteValue.SIZE_UNSIGNED_SHORT);
	}

	public int setInt8(long data, int offset) {
		ByteValue.setInt8(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_BYTE);
		return(ByteValue.SIZE_BYTE);
	}

	public int setUint8(long data, int offset) {
		ByteValue.setUint8(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_UNSIGNED_BYTE);
		return(ByteValue.SIZE_UNSIGNED_BYTE);
	}

	public int setBoolean(boolean data, int offset) {
		ByteValue.setBoolean(buffer, data, 0);
		this.flushBuffer(offset, ByteValue.SIZE_BOOLEAN);
		return(ByteValue.SIZE_BOOLEAN);
	}

	public String getString(int offset,int size) {
		return(ByteString.getStringFromByte(this.getByteArray(offset, size)));
	}

	public String[] getStringArray(int offset,int size) {
		return(ByteString.getStringArrayFromByte(this.getByteArray(offset, size)));
	}

	public String getString() {
		return(this.getString(0,this.getLength()));
	}

	public String[] getStringArray() {
		return(this.getStringArray(0,this.getLength()));
	}

	public byte[] getByteArray(int offset,int size) {
		byte[] binary = new byte[size];
		if(!this.testGetByteArray(binary, offset, size)) {
			return(this.zerobuffer);
		}
		return(binary);
	}

	public byte[] getByteArray() {
		return(this.getByteArray(0, this.getLength()));
	}
	
	public boolean testGetByteArray(byte[] binary,int offset,int size) {
		return(size == this.readByteArray(offset, binary, 0, size));
	}

	public int readByteArray(int readpoint, byte[] binary, int offset, int size) {
		if(this.rewriter==null) {
			System.out.println("getByte で初期化していない。");
			return(0);
		}
		if(readpoint + size > this.getLength()) {
			System.out.println("getByte でロードする位置がファイルの長さより長いです。");
			return(0);
		}
		if(offset + size > binary.length) {
			System.out.println("getByte で用意したバッファより大きいサイズをロードしようとしています。");
			return(0);
		}
		seekPoint(readpoint, size);
		try {
			this.rewriter.read(binary,offset,size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(size);
	}

	/**
	 * 8バイトまで限定のロード。
	 * 戻り値のbyte列は既に初期化済みなので高速。
	 * @param offset
	 * @param size
	 * @return
	 */
	private byte[] getByte8(int offset,int size) {
		seekPoint(offset, size);
		if(this.rewriter==null) {
			try {
				this.rewriter.read(buffer,0,size);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return(buffer);
	}

	public double getFloat64(int offset) {
		return(ByteValue.getFloat64(this.getByte8(offset,ByteValue.SIZE_DOUBLE), 0));
	}

	public float getFloat32(int offset) {
		return(ByteValue.getFloat32(this.getByte8(offset,ByteValue.SIZE_FLOAT), 0));
	}

	public long getInt64(int offset) {
		return(ByteValue.getInt64(this.getByte8(offset,ByteValue.SIZE_LONG), 0));
	}

	public int getInt32(int offset) {
		return(ByteValue.getInt32(this.getByte8(offset,ByteValue.SIZE_INT), 0));
	}

	public short getInt16(int offset) {
		return(ByteValue.getInt16(this.getByte8(offset,ByteValue.SIZE_SHORT), 0));
	}

	public int getUint16(int offset) {
		return(ByteValue.getUint16(this.getByte8(offset,ByteValue.SIZE_UNSIGNED_SHORT), 0));
	}

	public byte getInt8(int offset) {
		return(ByteValue.getInt8(this.getByte8(offset,ByteValue.SIZE_BYTE), 0));
	}

	public int getUint8(int offset) {
		return(ByteValue.getUint8(this.getByte8(offset,ByteValue.SIZE_UNSIGNED_BYTE), 0));
	}

	public boolean getBoolean(int offset) {
		return(ByteValue.getBoolean(this.getByte8(offset,ByteValue.SIZE_BOOLEAN), 0));
	}

	public boolean isReader() {
		return(true);
	}

	public boolean isRewriter() {
		return(true);
	}

	public boolean isWriter() {
		return(true);
	}

}
