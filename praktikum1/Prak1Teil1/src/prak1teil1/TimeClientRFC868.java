/*
 * 05. 2. 12
 */
package prak1teil1;

import java.io.IOException;

public class TimeClientRFC868 {
    static Core coreOwnServer;
    static Core coreOtherServer;
	
    public static void main(String[] args) throws IOException {
        coreOtherServer = new Core("ntp0.nl.net",37);
	coreOwnServer = new Core("localhost", 4711);
	System.out.println("Der mein Server antwortet: " + coreOwnServer.getDate());//formatTime(network.receiveResponse()));
	System.out.println("Der andere Server antwortet: " + coreOtherServer.getDate());//formatTime(network.receiveResponse()));
    }
}
