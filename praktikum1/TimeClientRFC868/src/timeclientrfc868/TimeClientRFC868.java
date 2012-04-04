/*
 * 05. 2. 12
 */
package timeclientrfc868;

import java.io.IOException;

public class TimeClientRFC868 {
	static Core coreOwnServer;
	static Core coreOtherServer;
	
	public static void main(String[] args) throws IOException {
	    //String host = "ntp0.nl.net";
	    // String host = "ntp2.curie.fr"; //ntp2: Requests may be blocked
	    // String host = "ptbtime1.ptb.de";  // Zeitserver Physikalisch-Technische
		//int port = 37;                    // Bundesanstalt (PTB), Server auf Port 37
	    
	    //DatagramSocket socket = new DatagramSocket();
	    //byte msg[] = new byte[4];         // Feld für Protokolldateneinheit
	    //InetAddress addr = InetAddress.getByName(host);
		

		//static String host = "ntp0.nl.net";
		//static int port = 37;
		
		
		coreOtherServer = new Core("ntp0.nl.net",37);
	    coreOwnServer = new Core("localhost", 4711);
		/*DatagramPacket packet = new DatagramPacket(msg, 0, addr, port); // Laenge 0
	    socket.send(packet);
	    System.out.println("Die Zeit wird angefragt bei " + host + " Port " + port);
	    packet = new DatagramPacket(msg, msg.length);                   // Laenge 4
	    socket.receive(packet);
	    */
	    System.out.println("Der mein Server antwortet: " + coreOwnServer.getDate());//formatTime(network.receiveResponse()));
	    System.out.println("Der andere Server antwortet: " + coreOtherServer.getDate());//formatTime(network.receiveResponse()));
	    //System.out.println("Der Server antwortet: " + formatTime(packet.getData()));
	    //System.out.println("Das ist fuer das Praktikum:\t" + formatTime2(packet.getData()));
	    //socket.close();
  }

  // Time Server liefert C-Typ unsigned int (4 Byte)
  // RFC868 beginnt am 1.1.1900 in s, Java am 1.1.1970 in ms
  // Für Java in long wandeln (8 Byte) sowie Offset und Skalierung beachten
/*  private static Date formatTime(byte[] t) {
    long ptbZeit = 0;
    ptbZeit |= (((t[0] & 0xff) << 24) & 0xffffffffL);
    ptbZeit |= (((t[1] & 0xff) << 16) & 0xffffffffL);
    ptbZeit |= (((t[2] & 0xff) << 8) & 0xffffffffL);
    ptbZeit |= ((t[3] & 0xff) & 0xffffffffL);
    System.out.println("ptbZeit:\t" + ptbZeit);
    return new Date((ptbZeit - 2208988800L) * 1000); // Korrektur für Date()
  }
  /*
  private static Date formatTime2(byte[] t){
    ByteArrayInputStream bais = new ByteArrayInputStream(t);
    long ptbZeit = 0;
    int shift = 24;
    long tmp = 0;
    while((tmp = bais.read()) != -1){
    	ptbZeit |= tmp << shift;
    	shift -= 8;
    }
    return new Date((ptbZeit - 2208988800L) * 1000);
  }
*/
}
