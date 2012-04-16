package prak1teil1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Date;

public class TimeServer {
    static int serverPort = 4711;
    static Connection network;
    static Core core;
    
    public static void main(String[] args) throws SocketException, IOException {
        network = new Connection(serverPort);
        core = new Core();
	while(true){
            network.receiveRequest();
            network.sendResponse(core.getTime());
	}
    }	
}
