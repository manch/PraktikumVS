package timeclientrfc868;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 *
 * @author Peter, 14. 10. 11
 * Klasse organisiert den Zugriff auf die Java-API für UDP.
 * Über die Schnittstelle zur Anwendung werden die Dateneinheiten des
 * Anwendungsprotokolls als Bytefelder angenommen und zurückgegeben.
 * Über die Schnittstelle zum Transportprotokoll UDP werden Datagramme gesendet
 * und empfangen.
 * Es kann zu einer Zeit nur eine Request-Response-Beziehung verwaltet werden.
 */
public class Connection {

  InetSocketAddress server;//, client;
  DatagramSocket clientSocket;// ,serverSocket;
  byte[] data = new byte[4];
  DatagramPacket packet = new DatagramPacket(data, data.length);
  int dgramLength;

  /*public UdpConnection(int serverPort) // Konstruktor ServerSocket
    throws SocketException, IOException {
    serverSocket = new DatagramSocket(serverPort); // immer localhost
  }*/

  public Connection(InetSocketAddress server) // Konstruktor ClientSocket
    throws SocketException, IOException {
    clientSocket = new DatagramSocket(); // localhost, Port beliebig
    this.server = server;                // Adresse Serversocket
  }

  /*public byte[] receiveRequest() throws IOException {
    packet.setData(data);                // Empfangspuffer rücksetzen
    serverSocket.receive(packet);        // Warten auf Request
    client = new InetSocketAddress(packet.getAddress(), packet.getPort());
    dgramLength = packet.getLength();    // Anzahl empfangener Bytes
    byte[] result = new byte[dgramLength];
    System.arraycopy(packet.getData(), 0, result, 0, dgramLength);
    return result;
  }

  public void sendResponse(byte[] message) throws IOException {
    packet.setSocketAddress(client);     // An Client adressieren
    packet.setData(message, 0, message.length);  // In Datagramm packen
    serverSocket.send(packet);
  }*/

  public void sendRequest(byte[] message) throws SocketException, IOException {
    packet.setSocketAddress(server);     // An Server adressieren
    packet.setData(message, 0, message.length);  // In Datagramm packen
    clientSocket.send(packet);
  }

  public byte[] receiveResponse() throws IOException {
    packet.setData(data);                // Empfangspuffer rücksetzen
    clientSocket.receive(packet);        // Auf Antwort warten
    dgramLength = packet.getLength();    // Anzahl empfangener Bytes
    byte[] result = new byte[dgramLength];
    System.arraycopy(packet.getData(), 0, result, 0, dgramLength);
    return result;
  }
}
