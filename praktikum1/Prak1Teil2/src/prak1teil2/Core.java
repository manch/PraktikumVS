package prak1teil2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Arrays;

public class Core {

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
    private final int headerLenght = 32;
    private final int seqOffset = 2;
    private final int dataSizeOffset = 6;
    private final int fileNameOffset = 10;
	
    public Core(Operation op, Errorcode err, int sequencenumber, int datablocksize, String filename, byte[] data) {
        this.op = op;
	this.err = err;
	this.sequencenumber = sequencenumber;
	this.datablocksize = datablocksize;
	this.filename = filename;
	this.data = data;
    }

    public Core(){};
	
    public void deserialize(byte[] b) throws Exception {
        if (true) {
            //Operationcode
            if ((char) b[0] == 'r') {
                this.op = Operation.READ;
            } else if ((char) b[0] == 'w') {
                this.op = Operation.WRITE;
            } else if ((char) b[0] == 'd') {
                this.op = Operation.DELETE;
            } else if ((char) b[0] == 'n') {
                this.op = Operation.NOTFIRSTBLOCK;
            }

            //Errorcode
            if ((char) b[1] == 'e') {
                this.err = Errorcode.ERROR;
            } else if ((char) b[1] == 'n') {
                this.err = Errorcode.NOTHING;
            }

            //Sequencenumber
            byte[] seqBA = new byte[4];
            System.arraycopy(b, 2, seqBA, 0, 4);
            this.sequencenumber = byte2int(seqBA);

            //Datablocksize
            byte[] dataBA = new byte[4];
            System.arraycopy(b, 6, dataBA, 0, 4);
            this.datablocksize = byte2int(dataBA);

            //Filename
            char[] fileCA = new char[22];
            for (int x = 0; x < 22; x++) {
                fileCA[x] = (char) b[x + 10];
            }
            this.filename = String.valueOf(fileCA);

            //Data
            System.out.println(b.length);
            this.data = new byte[b.length - 32];
            System.arraycopy(b, 32, this.data, 0, b.length-32);
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
       
	//Data
        byte[] retVal = new byte[headerLenght + this.data.length];
        
        //Header
        retVal[0] = op.get();
        retVal[1] = err.get();
        
        //Sequencenumber
        System.arraycopy(int2byte(sequencenumber), 0, retVal, seqOffset, 4);
        
        //Datablocksize
        System.arraycopy(int2byte(datablocksize), 0, retVal, dataSizeOffset, 4);
        
        //Filename
        char[] filecharname = filename.toCharArray();
        int lengthoffilename = filecharname.length;
        if (lengthoffilename > 22) {        
            lengthoffilename = 22;	//Maximale Länge
        }
        for (int x = 0; x < lengthoffilename; x++) {
            retVal[x + fileNameOffset] = (byte) filecharname[x];
        }
        System.arraycopy(this.data, 0, retVal, headerLenght, this.data.length);
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
