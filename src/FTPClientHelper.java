import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class FTPClientHelper {
	private MyClientDatagramSocket mySocket;
	private InetAddress serverHost;
	private int serverPort;

	public FTPClientHelper(String hostName, int portNum) throws SocketException, UnknownHostException {
		this.serverHost = InetAddress.getByName(hostName);
  		this.serverPort = portNum;
		this.mySocket = new MyClientDatagramSocket(); 
	} 
	
	public String sendRequest(String message) throws SocketException, IOException {
		mySocket.sendRequest(serverHost, serverPort, message);
		return mySocket.response().trim();
	}

	public void done() throws SocketException {
		mySocket.close();
	} 
}
