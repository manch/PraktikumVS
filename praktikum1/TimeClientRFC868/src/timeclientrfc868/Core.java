package timeclientrfc868;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Date;

public class Core {
	static InetSocketAddress server;
	static Connection network;
	static byte[] msg = new byte[4];
	static Date date;
	
	
	public Core(){
		
	}
	public Core(String host, int port) throws SocketException, IOException{
		server = new InetSocketAddress(InetAddress.getByName(host),port);
	    network = new Connection(server);
	    
	}
	public Date getDate() throws SocketException, IOException{
		network.sendRequest(msg);
	    formatTime(network.receiveResponse());
		return date;
	}
	public static byte[] serialize(long time)
	{
		
		byte msg[] = new byte[4];
		msg[0] = (byte)((time)>>24);
		msg[1] = (byte)((time)>>16);
		msg[2] = (byte)((time)>>8);
		msg[3] = (byte)(time);
		return msg;
	}
	private void formatTime(byte[] t){
	    ByteArrayInputStream bais = new ByteArrayInputStream(t);
	    long ptbZeit = 0;
	    int shift = 24;
	    long tmp = 0;
	    while((tmp = bais.read()) != -1){
	    	ptbZeit |= tmp << shift;
	    	shift -= 8;
	    }
	    date = new Date(((ptbZeit) - 2208988800L) * 1000);
	  }
}
