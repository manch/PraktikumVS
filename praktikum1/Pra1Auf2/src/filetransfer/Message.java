package filetransfer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class Message {

	public enum Operation {
		READ('r'), WRITE('w'), DELETE('d'), NOTFIRSTBLOCK('n');
		
		private byte b;
		
		private Operation(char c) {
			this.b = (byte) c;
		}
		
		public byte get() {
			return b;
		}
		
		
	}
	
	public enum Errorcode {
		ERROR('e'), NOTHING('n');
		
		private byte b;
		
		private Errorcode(char c) {
			this.b = (byte) c;
		}
		
		public byte get() {
			return b;
		}
	}
	
	private Operation op;
	private Errorcode err;
	private int sequencenumber;
	private int datablocksize;
	private String filename;
	
	private byte[] data;
	
	
	
	

	public Message(Operation op, Errorcode err, int sequencenumber, int datablocksize, String filename, byte[] data) {
		this.op = op;
		this.err = err;
		this.sequencenumber = sequencenumber;
		this.datablocksize = datablocksize;
		this.filename = filename;
		this.data = data;
	}

	public Message(byte[] b) throws Exception {
		if (true) {
			//Operationcode
			if ((char)b[0] == 'r') {
				this.op = Operation.READ;
			} else if ((char)b[0] == 'w') {
				this.op = Operation.WRITE;
			} else if ((char)b[0] == 'd') {
				this.op = Operation.DELETE;
			} else if ((char)b[0] == 'n') {
				this.op = Operation.NOTFIRSTBLOCK;
			}
			
			//Errorcode
			if ((char)b[1] == 'e') {
				this.err = Errorcode.ERROR;
			} else if ((char)b[1] == 'n') {
				this.err = Errorcode.NOTHING;
			}
			
			//Sequencenumber
			byte[] seqBA = new byte[4];
			for(int x=0 ; x<4 ; x++)
			{
				seqBA[x] = b[x+2];
			}
			this.sequencenumber = byte2int(seqBA);
			
			//Datablocksize
			byte[] dataBA = new byte[4];
			for(int x=0 ; x<4 ; x++)
			{
				dataBA[x] = b[x+6];
			}
			this.datablocksize = byte2int(dataBA);
			
			//Filename
			char[] fileCA = new char[22];
			for(int x=0 ; x<22 ; x++)
			{
				fileCA[x] = (char)b[x+10];
			}
			this.filename = String.valueOf(fileCA);
			
			//Data
			System.out.println(b.length);
			this.data = new byte[b.length-32];
			for(int i=0; i<data.length;i++) {
				this.data[i] = b[i+32];
			}
		}
	}
	
	public byte[] int2byte (int value) throws Exception
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeInt(value);
		return baos.toByteArray();
	}
	
	public int byte2int (byte[] value) throws Exception
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(value);
		DataInputStream dis = new DataInputStream(bais);
		return dis.readInt();
	}
	
	public byte[] serialize() throws Exception {
		byte[] header = new byte[32];
		
		header[0] = op.get();
		header[1] = err.get();
		
		//Sequencenumber
		byte [] tmp = int2byte(sequencenumber);
		for(int x=0 ; x<4 ; x++)
		{
			header[x+2] = tmp[x];
		}
		
		//Datablocksize
		tmp = int2byte(datablocksize);
		for(int x=0 ; x<4 ; x++)
		{
			header[x+6] = tmp[x];
		}
		//Filename
		char[] filecharname = filename.toCharArray();
		int lengthoffilename = filecharname.length;
		if(lengthoffilename>22)
		{
			lengthoffilename=22;
		}
		for(int x=0; x<lengthoffilename; x++)
		{
			header[x+10] = (byte) filecharname[x];
		}
		//System.out.println(Arrays.toString(header));
		
		//Data
		byte[] retVal = new byte[header.length + this.data.length];
		
		System.arraycopy(header, 0, retVal, 0, header.length);
		System.arraycopy(this.data, 0, retVal, header.length, this.data.length);
		System.out.println(Arrays.toString(retVal));
		
		return retVal;
		
	}

	public Operation getOp() {
		return op;
	}

	public Errorcode getErr() {
		return err;
	}

	public int getSequencenumber() {
		return sequencenumber;
	}

	public int getDatablocksize() {
		return datablocksize;
	}

	public String getFilename() {
		return filename;
	}
	
	public byte[] getData() {
		return data;
	}
}
