package timeclientrfc868;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Date;

public class TimeServer {
	static int serverPort = 4711;
	static Connection network;
	static Core core;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws SocketException 
	 */
	public static void main(String[] args) throws SocketException, IOException {
		// TODO Auto-generated method stub
	    network = new Connection(serverPort);
		core = new Core();
	    while(true){
			network.receiveRequest();
			Date date = new Date();
			long time = date.getTime();
		    time = ((time / 1000) + 2208988800L);
		    network.sendResponse(core.serialize(time));
		}
	}
	
}
