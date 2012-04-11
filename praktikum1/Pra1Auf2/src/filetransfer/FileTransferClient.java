package filetransfer;

import java.awt.TrayIcon.MessageType;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.util.Arrays;

import util.UdpConnection;

public class FileTransferClient {

	public static final String clientPath = "/home/chris/tmp/client/";
	public static final int maxBlockSize = 4;
	
	public static void main(String[] args) throws Exception {
		String host = "localhost";
		int port = 1337; 
		
		System.out.println("open connection");
		UdpConnection udpc = new UdpConnection(new InetSocketAddress(host, port));
		System.out.println("send request");
		
		FileInputStream fis = new FileInputStream(clientPath+"test.txt");
		DataInputStream dis = new DataInputStream(fis);
		
		byte[] dataBA = new byte[dis.available()];
		dis.read(dataBA);
		
		int msgCount = dataBA.length/maxBlockSize;
		int lastMsgSize = dataBA.length%maxBlockSize;
		if(lastMsgSize != 0) {
			msgCount++;
		}
		
		for(int i=0; i<msgCount; i++) {
			byte [] seqDataBA;
			if(i == msgCount-1 && lastMsgSize != 0) {
				seqDataBA = new byte[lastMsgSize];
			} else {
				seqDataBA = new byte[maxBlockSize];
			}
			
			System.arraycopy(dataBA, i*maxBlockSize, seqDataBA, 0, seqDataBA.length);
			
			System.out.println("Sequencenumber: " + (msgCount-i));
			System.out.println("Messagesize: " + seqDataBA.length);
			System.out.println("Data: " + Arrays.toString(seqDataBA));
			Message.Operation op;
			if(i==0) {
				op = Message.Operation.WRITE;
			} else {
				op = Message.Operation.NOTFIRSTBLOCK;
			}
			
			Message m = new Message(op, Message.Errorcode.NOTHING, msgCount-i, seqDataBA.length, "test.txt",seqDataBA);
			udpc.sendRequest(m.serialize());
			
		}
				
		
		//System.out.println("receiving response");
		//byte[] resp = udpc.receiveResponse();
		//Message msg = new Message(resp);
		//System.out.println("Der Server antwortet (Haben): ");
	
	}
}
