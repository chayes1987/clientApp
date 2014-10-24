import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MyClientDatagramSocket extends DatagramSocket {
	private static final int MAX_LEN = 100;

	public MyClientDatagramSocket() throws SocketException{
		super();
	}
	
	public void sendRequest(InetAddress receiverHost, int receiverPort, String message) throws IOException {
        byte[] sendBuffer = message.getBytes();
        this.send(new DatagramPacket(sendBuffer, sendBuffer.length, receiverHost, receiverPort));
	}

	public String response() throws IOException {		
        byte[] receiveBuffer = new byte[MAX_LEN];
        this.receive(new DatagramPacket(receiveBuffer, MAX_LEN));
        return new String(receiveBuffer);
	}
}
